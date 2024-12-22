import Resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.PriorityQueue;

public class CustomerManagementApp {
    private LinkedList<Customer> customers = new LinkedList<>();

    private TreeNode cityTreeRoot;
    private int shipmentIDCounter = 1000;

    public CustomerManagementApp() {
        initializeCityTree();
    }
    // Şehir ağacında İstanbul'dan hedef şehre kadar olan yolu bulan fonksiyonn
    public List<String> getPathToCity(String targetCity) {
        List<String> path = new ArrayList<>();
        if (findPath(cityTreeRoot, targetCity, path)) {
            return path;
        } else {
            return Collections.singletonList("Hedef şehir bulunamadı?.....!");
        }
    }
    private boolean findPath(TreeNode current, String targetCity, List<String> path) {
        if (current == null) {
            return false;
        }
        path.add(current.city);
        if (current.city.equals(targetCity)) {
            return true;
        }
        for (TreeNode child : current.children) {
            if (findPath(child, targetCity, path)) {
                return true;
            }
        }
        path.remove(path.size() - 1);
        return false;
    }

    // Şehir ağacında ilgili şehir düğümünü arayan fonksiyon
    private TreeNode findCityNode(String city, TreeNode root) {
        if (root == null) {
            return null;
        }
        if (root.city.equals(city)) {
            return root;
        }
        for (TreeNode child : root.children) {
            TreeNode result = findCityNode(city, child);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private void initializeCityTree() {
        // İstanbul'un ID'si 34
        cityTreeRoot = new TreeNode(34, "Istanbul", 1);

        // Birinci seviye şehirler (Plaka kodlarına göre ID'ler)
        TreeNode ankara = new TreeNode(6, "Ankara", 2);
        TreeNode kastamonu = new TreeNode(37, "Kastamonu", 2);
        TreeNode bursa = new TreeNode(16, "Bursa", 2);

        // Ankara'nın alt şehirleri
        TreeNode mersin = new TreeNode(33, "Mersin", 4);
        TreeNode kayseri = new TreeNode(38, "Kayseri", 4);
        ankara.children.add(mersin);
        ankara.children.add(kayseri);

        // Kastamonu'nun alt şehirleri
        TreeNode tokat = new TreeNode(60, "Tokat", 4);
        TreeNode giresun = new TreeNode(28, "Giresun", 4);
        kastamonu.children.add(tokat);
        kastamonu.children.add(giresun);

        // Bursa'nın alt şehirleri
        TreeNode izmir = new TreeNode(35, "İzmir", 4);
        TreeNode canakkale = new TreeNode(17, "Çanakkale", 4);
        bursa.children.add(izmir);
        bursa.children.add(canakkale);

        // Daha alt seviyedeki şehirler
        mersin.children.add(new TreeNode(46, "K.Maraş", 7));
        mersin.children.add(new TreeNode(31, "Hatay", 7));

        kayseri.children.add(new TreeNode(44, "Malatya", 7));
        kayseri.children.add(new TreeNode(23, "Elazığ", 7));

        tokat.children.add(new TreeNode(24, "Erzincan", 7));
        tokat.children.add(new TreeNode(58, "Sivas", 7));

        giresun.children.add(new TreeNode(25, "Erzurum", 7));
        giresun.children.add(new TreeNode(53, "Rize", 7));

        izmir.children.add(new TreeNode(48, "Muğla", 7));
        izmir.children.add(new TreeNode(7, "Antalya", 7));

        // Şehirleri kök düğüme ekliyoruz
        cityTreeRoot.children.add(ankara);
        cityTreeRoot.children.add(kastamonu);
        cityTreeRoot.children.add(bursa);

        // En derinliği hesapla
        int maxDepth = cityTreeRoot.calculateDepth(cityTreeRoot, 0);
    }




    // Teslimat süresi hesaplamak için bir fonksiyon
    public int getDeliveryTimeForCity(String cityName) {
        TreeNode cityNode = findCityNode(cityName, cityTreeRoot);
        if (cityNode != null) {
            return cityNode.deliveryTime; // Derinlik ile ilişkilendirilmiş teslimat süresi
        } else {
            System.out.println("City not found!");
            return -1;
        }
    }
    public void printRoutesFromShipments() {
        // Müşteri listesi kontrolü
        if (customers.isEmpty()) {
            // UIManager renk ayarları
            UIManager.put("OptionPane.background", Color.decode("#012a4a"));
            UIManager.put("Panel.background", Color.decode("#012a4a"));
            UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
            UIManager.put("Button.background", Color.decode("#ffe5ec"));
            UIManager.put("Button.foreground", Color.decode("#012a4a"));

            JOptionPane.showMessageDialog(null, "Hiç müşteri kaydı yok!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultComboBoxModel<String> customerModel = new DefaultComboBoxModel<>();
        for (Customer customer : customers) {
            customerModel.addElement("ID: " + customer.getCustomerID() + " - " + customer.getName());
        }

        JComboBox<String> customerComboBox = new JComboBox<>(customerModel);
        int result = JOptionPane.showConfirmDialog(null, customerComboBox, "Müşteri Seçin", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            // Seçilen müşteri
            String selectedCustomerText = (String) customerComboBox.getSelectedItem();
            int customerID = Integer.parseInt(selectedCustomerText.split(" - ")[0].replace("ID: ", "").trim());

            Customer selectedCustomer = null;
            for (Customer customer : customers) {
                if (customer.getCustomerID() == customerID) {
                    selectedCustomer = customer;
                    break;
                }
            }

            if (selectedCustomer != null) {
                // Gönderi listesi kontrolü
                if (selectedCustomer.getShipmentHistory().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Seçilen müşterinin gönderisi yok!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                DefaultComboBoxModel<String> shipmentModel = new DefaultComboBoxModel<>();
                for (Shipment shipment : selectedCustomer.getShipmentHistory()) {
                    shipmentModel.addElement("ID: " + shipment.getShipmentID() + " - Şehir: " + shipment.getCity());
                }

                JComboBox<String> shipmentComboBox = new JComboBox<>(shipmentModel);
                int shipmentResult = JOptionPane.showConfirmDialog(null, shipmentComboBox, "Gönderi Seçin", JOptionPane.OK_CANCEL_OPTION);

                if (shipmentResult == JOptionPane.OK_OPTION) {
                    String selectedShipmentText = (String) shipmentComboBox.getSelectedItem();
                    String cityName = selectedShipmentText.split(" - Şehir: ")[1].trim();
                    // Rotayı hesapla ve göster
                    List<String> path = getPathToCity(cityName);
                    StringBuilder routeMessage = new StringBuilder("Teslimat rotası:\n");
                    for (String city : path) {
                        routeMessage.append(city).append("\n");
                    }

                    UIManager.put("OptionPane.background", Color.decode("#012a4a"));
                    UIManager.put("Panel.background", Color.decode("#012a4a"));
                    UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
                    UIManager.put("Button.background", Color.decode("#ffe5ec"));
                    UIManager.put("Button.foreground", Color.decode("#012a4a"));

                    JOptionPane.showMessageDialog(null, routeMessage.toString(), "Teslimat Rotası", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    public void sortShipmentsByDeliveryTime() {
        // PriorityQueue, teslim süresine göre sıralamak için kullanılır.
        PriorityQueue<Shipment> shipmentQueue = new PriorityQueue<>(Comparator.comparingInt(Shipment::getDeliveryTime));
        // Her müşteri için gönderi geçmişi ekler
        for (Customer customer : customers) {
            shipmentQueue.addAll(customer.getShipmentHistory());
        }
        StringBuilder sortedShipments = new StringBuilder("Teslim Süresine Göre Sıralı Gönderiler:\n");
        if (shipmentQueue.isEmpty()) {
            UIManager.put("OptionPane.background", Color.decode("#012a4a"));
            UIManager.put("Panel.background", Color.decode("#012a4a"));
            UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
            UIManager.put("Button.background", Color.decode("#ffe5ec"));
            UIManager.put("Button.foreground", Color.decode("#012a4a"));
            JOptionPane.showMessageDialog(null, "Hiç gönderi bulunmamaktadır.", "Uyarı", JOptionPane.WARNING_MESSAGE);
        } else {
            while (!shipmentQueue.isEmpty()) {
                sortedShipments.append(shipmentQueue.poll()).append("\n");
            }
            UIManager.put("OptionPane.background", Color.decode("#012a4a"));
            UIManager.put("Panel.background", Color.decode("#012a4a"));
            UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
            UIManager.put("Button.background", Color.decode("#ffe5ec"));
            UIManager.put("Button.foreground", Color.decode("#012a4a"));

            JOptionPane.showMessageDialog(null, sortedShipments.toString(), "Sıralı Gönderiler", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void addCustomerDialog() {
        JFrame dialog = new JFrame("Müşteri Ekle");
        dialog.setSize(300, 200);

        JLabel nameLabel = new JLabel("Adı-Soyadı:");
        nameLabel.setForeground(Color.decode("#FFFFFF"));
        JTextField nameField = new JTextField();
        JButton addButton = new JButton("Ekle");

        addButton.setBackground(Color.decode("#ffe5ec"));
        addButton.setForeground(Color.decode("#012a4a"));

        JPanel panel = new JPanel(new GridLayout(2, 2));

        panel.setBackground(Color.decode("#012a4a"));

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(new JLabel());
        panel.add(addButton);
        dialog.add(panel);
        dialog.setLocationRelativeTo(null);

        addButton.addActionListener(e -> {
            Set<Integer> customerIds = new HashSet<>(); // Benzersiz ID'leri tutmak için

            try {
                Random random = new Random();
                int id;
                do {
                    id = 10000 + random.nextInt(90000);
                } while (customerIds.contains(id));

                customerIds.add(id);

                String name = nameField.getText();
                Customer newCustomer = new Customer(id, name);
                customers.add(newCustomer);

                UIManager.put("OptionPane.background", Color.decode("#012a4a"));
                UIManager.put("Panel.background", Color.decode("#012a4a"));
                UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
                UIManager.put("Button.background", Color.decode("#ffe5ec"));
                UIManager.put("Button.foreground", Color.decode("#012a4a"));

                JOptionPane.showMessageDialog(dialog, "Müşteri başarıyla eklendi.\nMüşteri ID: " + id);
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Müşteri eklerken hata oluştu.");
            }
        });

        dialog.setVisible(true);
    }
    public void addShipmentDialog() {

        JFrame dialog = new JFrame("Gönderi Ekle");
        dialog.setSize(400, 300);

        JLabel customerLabel = new JLabel("Müşteri Seç:");
        customerLabel.setForeground(Color.decode("#FFFFFF"));

        DefaultComboBoxModel<String> customerComboBoxModel = new DefaultComboBoxModel<>();
        for (Customer customer : customers) {
            customerComboBoxModel.addElement("ID: " + customer.getCustomerID() + " - " + customer.getName());
        }
        JComboBox<String> customerComboBox = new JComboBox<>(customerComboBoxModel);
        customerComboBox.setBackground(Color.decode("#ffe5ec"));
        customerComboBox.setForeground(Color.decode("#012a4a"));

        JLabel cityLabel = new JLabel("Teslimat Şehri:");
        cityLabel.setForeground(Color.decode("#FFFFFF"));

        java.util.List<String> cityNames = getAllCityNames(cityTreeRoot);
        JComboBox<String> cityComboBox = new JComboBox<>(cityNames.toArray(new String[0]));
        cityComboBox.setBackground(Color.decode("#ffe5ec"));
        cityComboBox.setForeground(Color.decode("#012a4a"));

        JButton addButton = new JButton("Ekle");
        addButton.setBackground(Color.decode("#ffe5ec"));
        addButton.setForeground(Color.decode("#012a4a"));

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBackground(Color.decode("#012a4a"));

        panel.add(customerLabel);
        panel.add(customerComboBox);
        panel.add(cityLabel);
        panel.add(new JScrollPane(cityComboBox));
        panel.add(new JLabel());
        panel.add(addButton);

        dialog.add(panel);
        dialog.setLocationRelativeTo(null);
        UIManager.put("OptionPane.background", Color.decode("#012a4a"));
        UIManager.put("Panel.background", Color.decode("#012a4a"));
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
        UIManager.put("Button.background", Color.decode("#ffe5ec"));
        UIManager.put("Button.foreground", Color.decode("#012a4a"));

        addButton.addActionListener(e -> {
            try {
                String selectedCustomerText = (String) customerComboBox.getSelectedItem();
                int customerID = Integer.parseInt(selectedCustomerText.split(" - ")[0].replace("ID: ", "").trim());
                Customer selectedCustomer = null;
                for (Customer customer : customers) {
                    if (customer.getCustomerID() == customerID) {
                        selectedCustomer = customer;
                        break;
                    }
                }
                String city = (String) cityComboBox.getSelectedItem();

                int deliveryTime = getDeliveryTimeForCity(city);

                if (deliveryTime != -1) {
                    TreeNode cityNode = findCityNode(city, cityTreeRoot);
                    if (cityNode != null) {
                        cityNode.cargoCount++;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDate = sdf.format(new Date());
                        Shipment shipment = new Shipment(shipmentIDCounter++, currentDate, "Beklemede", deliveryTime, city, selectedCustomer);
                        selectedCustomer.addShipment(shipment, this);
                        JOptionPane.showMessageDialog(dialog, "Gönderi başarıyla eklendi.\nTeslimat Süresi: " + shipment.getDeliveryTime() + " gün.\nGönderi ID: " + shipment.getShipmentID());
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Geçersiz şehir adı.");
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Geçersiz şehir adı.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Gönderi eklerken hata oluştu.");
            }
        });

        dialog.setVisible(true);
    }
    // CityTree'deki tüm şehirleri alacak fonksiyon
    private java.util.List<String> getAllCityNames(TreeNode node) {
        List<String> cityNames = new ArrayList<>();
        if (node != null) {
            cityNames.add(node.city);
            for (TreeNode child : node.children) {
                cityNames.addAll(getAllCityNames(child));
            }
        }
        return cityNames;
    }
    public void viewAllCustomers() {
        StringBuilder customersList = new StringBuilder("Tüm Müşteriler:\n");
        for (Customer customer : customers) {
            customersList.append("ID: ").append(customer.getCustomerID()).append(", Ad: ").append(customer.getName()).append("\n");
        }
        UIManager.put("OptionPane.background", Color.decode("#012a4a"));
        UIManager.put("Panel.background", Color.decode("#012a4a"));
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
        JOptionPane.showMessageDialog(null, customersList.toString());
    }
    public void updateShipmentStatus() {
        // Gönderi seçim ekranı
        DefaultComboBoxModel<String> shipmentComboBoxModel = new DefaultComboBoxModel<>();
        for (Customer customer : customers) {
            for (Shipment shipment : customer.getShipmentHistory()) {
                shipmentComboBoxModel.addElement("Müşteri: " + customer.getName() + " - Şehir: " + shipment.getCity() + " - Durum: " + shipment.deliveryStatus);
            }
        }

        JComboBox<String> shipmentComboBox = new JComboBox<>(shipmentComboBoxModel);
        shipmentComboBox.setBackground(Color.decode("#ffe5ec"));
        shipmentComboBox.setForeground(Color.decode("#012a4a"));

        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{
                "İşleme Alındı", "Teslimatta", "Teslim Edildi"
        });
        statusComboBox.setBackground(Color.decode("#ffe5ec"));
        statusComboBox.setForeground(Color.decode("#012a4a"));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.decode("#012a4a"));

        JLabel shipmentLabel = new JLabel("Gönderiyi Seçin:");
        shipmentLabel.setForeground(Color.WHITE);
        panel.add(shipmentLabel);
        panel.add(shipmentComboBox);

        JLabel statusLabel = new JLabel("Yeni Durumu Seçin:");
        statusLabel.setForeground(Color.WHITE);
        panel.add(Box.createVerticalStrut(10));
        panel.add(statusLabel);
        panel.add(statusComboBox);

        JDialog dialog = new JDialog();
        dialog.setTitle("Durumunu Güncelle");
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.add(panel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode("#012a4a"));
        JButton okButton = new JButton("Tamam");
        JButton cancelButton = new JButton("İptal");

        okButton.setBackground(Color.decode("#ffe5ec"));
        okButton.setForeground(Color.decode("#012a4a"));
        cancelButton.setBackground(Color.decode("#ffe5ec"));
        cancelButton.setForeground(Color.decode("#012a4a"));

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);

        UIManager.put("OptionPane.background", Color.decode("#012a4a"));
        UIManager.put("Panel.background", Color.decode("#012a4a"));
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
        UIManager.put("Button.background", Color.decode("#ffe5ec"));
        UIManager.put("Button.foreground", Color.decode("#012a4a"));

        okButton.addActionListener(e -> {
            String selectedShipmentText = (String) shipmentComboBox.getSelectedItem();
            if (selectedShipmentText != null) {
                String[] shipmentDetails = selectedShipmentText.split(" - ");
                String customerName = shipmentDetails[0].replace("Müşteri: ", "");
                String deliveryCity = shipmentDetails[1].replace("Şehir: ", "");

                Shipment selectedShipment = null;
                for (Customer customer : customers) {
                    if (customer.getName().equalsIgnoreCase(customerName)) {
                        for (Shipment shipment : customer.getShipmentHistory()) {
                            if (shipment.getCity().equalsIgnoreCase(deliveryCity)) {
                                selectedShipment = shipment;
                                break;
                            }
                        }
                    }
                    if (selectedShipment != null) break;
                }

                // Durum güncelleme işlemi
                if (selectedShipment != null) {
                    String newStatus = (String) statusComboBox.getSelectedItem();
                    selectedShipment.deliveryStatus = newStatus;

                    shipmentComboBoxModel.removeAllElements();
                    for (Customer customer : customers) {
                        for (Shipment shipment : customer.getShipmentHistory()) {
                            shipmentComboBoxModel.addElement("Müşteri: " + customer.getName() + " - Şehir: " + shipment.getCity() + " - Durum: " + shipment.deliveryStatus);
                        }
                    }

                    JOptionPane.showMessageDialog(dialog, "Durum başarıyla güncellendi.\nYeni Durum: " + newStatus);

                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Seçilen gönderi bulunamadı.");
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
    public void viewCustomerHistory() {
        DefaultComboBoxModel<String> customerComboBoxModel = new DefaultComboBoxModel<>();
        for (Customer customer : customers) {
            customerComboBoxModel.addElement(customer.getName());
        }

        JComboBox<String> customerComboBox = new JComboBox<>(customerComboBoxModel);

        UIManager.put("OptionPane.background", Color.decode("#012a4a"));
        UIManager.put("Panel.background", Color.decode("#012a4a"));
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
        UIManager.put("Button.background", Color.decode("#ffe5ec"));
        UIManager.put("Button.foreground", Color.decode("#012a4a"));

        int option = JOptionPane.showConfirmDialog(
                null,
                customerComboBox,
                "Geçmişi görmek istediğiniz müşteriyi seçin:",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            String selectedCustomerName = (String) customerComboBox.getSelectedItem();
            if (selectedCustomerName != null) {
                for (Customer customer : customers) {
                    if (customer.getName().equalsIgnoreCase(selectedCustomerName)) {
                        StringBuilder history = new StringBuilder("Müşteri Geçmişi:\n\n");

                        // Müşterinin gönderi geçmişi LinkedList olarak alınıyor
                        LinkedList<Shipment> shipments = customer.getShipmentStack();

                        // ListIterator ile listenin sonuna gidiyoruz
                        ListIterator<Shipment> iterator = shipments.listIterator(shipments.size());
                        int count = 0;

                        // Son 5 gönderiyi alıyoruz
                        while (iterator.hasPrevious() && count < 5) {
                            Shipment shipment = iterator.previous();
                            history.append("- ").append(shipment).append("\n"); // Liste biçimi için tire eklendi
                            count++;
                        }

                        // Gönderileri düzgün şekilde gösteriyoruz
                        JTextArea textArea = new JTextArea(history.toString());
                        textArea.setEditable(false);
                        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
                        textArea.setForeground(Color.WHITE);
                        textArea.setBackground(Color.decode("#012a4a"));
                        textArea.setBorder(BorderFactory.createLineBorder(Color.decode("#ffe5ec"), 1));

                        JScrollPane scrollPane = new JScrollPane(textArea);
                        scrollPane.setPreferredSize(new Dimension(400, 200));

                        JOptionPane.showMessageDialog(null, scrollPane, "Müşteri Geçmişi", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
            }
        }
        showCustomMessageDialog("Müşteri bulunamadı.", "Hata", Color.RED, Color.WHITE);
    }

    private void showCustomMessageDialog(String message, String title, Color bgColor, Color textColor) {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        JLabel label = new JLabel(message);
        label.setForeground(textColor);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label);
        JOptionPane.showMessageDialog(null, panel, title, JOptionPane.PLAIN_MESSAGE);
    }

    public void searchShipment() {
        UIManager.put("OptionPane.background", Color.decode("#012a4a"));
        UIManager.put("Panel.background", Color.decode("#012a4a"));
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
        UIManager.put("Button.background", Color.decode("#ffe5ec"));
        UIManager.put("Button.foreground", Color.decode("#012a4a"));

        String shipmentID = JOptionPane.showInputDialog(null, "Aradığınız gönderi ID'sini girin:");

        // Gönderi araması yapılıyor
        for (Customer customer : customers) {
            for (Shipment shipment : customer.getShipmentHistory()) {
                if (String.valueOf(shipment.getShipmentID()).equals(shipmentID)) {
                    JOptionPane.showMessageDialog(null, shipment);
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Gönderi bulunamadı.");
    }

    public void deleteShipment() {
        UIManager.put("OptionPane.background", Color.decode("#012a4a"));
        UIManager.put("Panel.background", Color.decode("#012a4a"));
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
        UIManager.put("Button.background", Color.decode("#ffe5ec"));
        UIManager.put("Button.foreground", Color.decode("#012a4a"));

        String shipmentID = JOptionPane.showInputDialog("Silmek istediğiniz gönderi ID'sini girin:");

        if (shipmentID == null || shipmentID.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Lütfen geçerli bir gönderi ID'si girin.",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kargo ID'sine göre gönderiyi bulup silme işlemi
        boolean shipmentFound = false;

        for (Customer customer : customers) {
            Iterator<Shipment> iterator = customer.getShipmentHistory().iterator();
            while (iterator.hasNext()) {
                Shipment shipment = iterator.next();
                if (String.valueOf(shipment.getShipmentID()).equals(shipmentID)) {
                    iterator.remove();
                    shipmentFound = true;
                    break;
                }
            }
            if (shipmentFound) {
                break;
            }
        }

        // Stack'ten silme
        if (shipmentFound) {
            Stack<Shipment> tempStack = new Stack<>();
            while (!allShipmentsStack.isEmpty()) {
                Shipment shipment = allShipmentsStack.pop();
                if (!String.valueOf(shipment.getShipmentID()).equals(shipmentID)) {
                    tempStack.push(shipment);
                }
            }
            while (!tempStack.isEmpty()) {
                allShipmentsStack.push(tempStack.pop());
            }

            JOptionPane.showMessageDialog(null,
                    "Gönderi başarıyla silindi.",
                    "Başarılı",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Gönderi bulunamadı.",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    // Global Stack tanımlaması
    private Stack<Shipment> allShipmentsStack = new Stack<>();
    // Gönderiyi global stack'e ekleme metodu
    public void addShipmentToGlobalStack(Shipment shipment) {
        allShipmentsStack.push(shipment); // Gönderi stack'e ekleniyor
    }

    public void listAllShipments() {
        StringBuilder allShipmentsContent = new StringBuilder("Tüm Gönderiler:\n");

        UIManager.put("OptionPane.background", Color.decode("#012a4a"));
        UIManager.put("Panel.background", Color.decode("#012a4a"));
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
        UIManager.put("Button.background", Color.decode("#ffe5ec"));
        UIManager.put("Button.foreground", Color.decode("#012a4a"));

        // Eğer stack boşsa, kullanıcıya bildiriyoruz
        if (allShipmentsStack.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Hiç gönderi bulunamadı.",
                    "Bilgi",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Geçici bir liste kullanarak stack'in içeriğini koruyoruz
            Stack<Shipment> tempStack = new Stack<>();

            while (!allShipmentsStack.isEmpty()) {
                Shipment shipment = allShipmentsStack.pop();
                allShipmentsContent.append(shipment).append("\n");
                tempStack.push(shipment); // Elemanı geçici stack'e ekliyoruz
            }

            // Geçici stack'teki elemanları orijinal stack'e geri yüklüyoruz
            while (!tempStack.isEmpty()) {
                allShipmentsStack.push(tempStack.pop());
            }

            JTextArea textArea = new JTextArea(allShipmentsContent.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 14));
            textArea.setForeground(Color.decode("#FFFFFF"));
            textArea.setBackground(Color.decode("#012a4a"));
            textArea.setBorder(BorderFactory.createLineBorder(Color.decode("#ffe5ec"), 1));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400)); //
            JOptionPane.showMessageDialog(null, scrollPane, "Tüm Gönderiler", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void showDeliveryRoute() {
        JFrame frame = new JFrame("Teslimat Rotası - Şehirler Ağaç Yapısı");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setBackground(Color.decode("#012a4a"));
        CityTreeVisualizationPanel treePanel = new CityTreeVisualizationPanel(cityTreeRoot);
        JScrollPane scrollPane = new JScrollPane(treePanel);
        scrollPane.getViewport().setBackground(Color.white);

        frame.add(scrollPane);
        frame.pack();
        frame.setSize(1800, 800);
        frame.setLocationRelativeTo(null);

        UIManager.put("OptionPane.background", Color.decode("#012a4a"));
        UIManager.put("Panel.background", Color.decode("#012a4a"));
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF"));
        UIManager.put("Button.background", Color.decode("#ffe5ec"));
        UIManager.put("Button.foreground", Color.decode("#012a4a"));

        frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.decode("#012a4a"), 3));

        JLabel label = new JLabel("Teslimat Rotası - Şehirler Ağaç Yapısı");
        label.setForeground(Color.WHITE); // Yazı rengini beyaz yapalım
        frame.add(label, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    public void checkShipmentStatus() {
        JFrame dialog = new JFrame("Kargo Durumu Sorgula");
        dialog.setSize(500, 300);
        dialog.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.decode("#012a4a"));

        JLabel titleLabel = new JLabel("Kargonuzu Seçin");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);

        JComboBox<String> shipmentSelector = new JComboBox<>();
        Map<String, Shipment> shipmentMap = new HashMap<>();

        // Kullanıcıya ait tüm kargoları ekler
        for (Customer customer : customers) {
            for (Shipment shipment : customer.getShipmentHistory()) {
                String display = "Müşteri: " + customer.getName() + ", ID: " + shipment.getShipmentID() + ", Şehir: " + shipment.getCity();
                shipmentSelector.addItem(display);
                shipmentMap.put(display, shipment);
            }
        }

        JButton checkButton = new JButton("Durumu Kontrol Et");
        JButton sortUndeliveredButton = new JButton("Teslim Edilmemiş Kargoları Sırala");
        JButton searchDeliveredButton = new JButton("Teslim Edilmiş Kargoları Ara");

        shipmentSelector.setBackground(Color.decode("#ffe5ec"));
        shipmentSelector.setForeground(Color.decode("#012a4a"));

        checkButton.setBackground(Color.decode("#ffe5ec"));
        checkButton.setForeground(Color.decode("#012a4a"));
        sortUndeliveredButton.setBackground(Color.decode("#ffe5ec"));
        sortUndeliveredButton.setForeground(Color.decode("#012a4a"));
        searchDeliveredButton.setBackground(Color.decode("#ffe5ec"));
        searchDeliveredButton.setForeground(Color.decode("#012a4a"));

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(shipmentSelector);
        panel.add(Box.createVerticalStrut(10));
        panel.add(checkButton);
        panel.add(sortUndeliveredButton);
        panel.add(searchDeliveredButton);

        dialog.add(panel);
        dialog.setVisible(true);

        checkButton.addActionListener(e -> {
            String selectedItem = (String) shipmentSelector.getSelectedItem();
            if (selectedItem != null) {
                Shipment selectedShipment = shipmentMap.get(selectedItem);

                if (selectedShipment != null) {
                    String status = selectedShipment.getDeliveryStatus();
                    switch (status.toLowerCase()) {
                        case "teslim edildi":
                            JOptionPane.showMessageDialog(dialog, "Seçilen kargo teslim edilmiştir.");
                            break;
                        case "işleme alındı":
                            JOptionPane.showMessageDialog(dialog, "Seçilen kargo işleme alınmıştır. Teslimat bekleniyor.");
                            break;
                        case "teslimatta":
                            JOptionPane.showMessageDialog(dialog, "Seçilen kargo teslimatta. Yakında ulaşacaktır.");
                            break;
                        default:
                            int remainingDays = selectedShipment.getDeliveryTime() - selectedShipment.getPassedDays();
                            if (remainingDays > 0) {
                                JOptionPane.showMessageDialog(dialog,
                                        "Seçilen kargo henüz teslim edilmedi.\nTeslim edilmesine kalan gün sayısı: " + remainingDays);
                            } else {
                                JOptionPane.showMessageDialog(dialog,
                                        "Seçilen kargo teslim edilmesi gereken süreyi aşmıştır!");
                            }
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Kargo bulunamadı!");
                }
            }
        });

        sortUndeliveredButton.addActionListener(e -> sortUndeliveredShipments());

        searchDeliveredButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Aramak istediğiniz kargo ID'sini girin:");

            if (input == null) {
                return;
            }
            try {
                int searchID = Integer.parseInt(input);
                List<Shipment> deliveredShipments = new ArrayList<>();
                for (Customer customer : customers) {
                    for (Shipment shipment : customer.getShipmentHistory()) {
                        if ("Teslim Edildi".equalsIgnoreCase(shipment.getDeliveryStatus())) {
                            deliveredShipments.add(shipment);
                        }
                    }
                }

                deliveredShipments.sort(Comparator.comparingInt(Shipment::getShipmentID));

                // Binary Search ile arama yapar
                int index = binarySearch(deliveredShipments, searchID);
                if (index != -1) {
                    JOptionPane.showMessageDialog(dialog, "Kargo bulundu: " + deliveredShipments.get(index));
                } else {
                    JOptionPane.showMessageDialog(dialog, "Kargo bulunamadı.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Geçersiz ID formatı. Lütfen geçerli bir sayı girin.");
            }
        });
    }

    private int binarySearch(List<Shipment> list, int searchID) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid).getShipmentID() == searchID) {
                return mid;
            }
            if (list.get(mid).getShipmentID() < searchID) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    // Teslim edilmemiş kargoları teslimat süresine göre sıralama
    private void sortUndeliveredShipments() {
        // Teslim edilmemiş kargoları bir listeye ekle
        List<Shipment> undeliveredShipments = new ArrayList<>();
        for (Customer customer : customers) {
            for (Shipment shipment : customer.getShipmentHistory()) {
                if (!"Teslim Edildi".equalsIgnoreCase(shipment.getDeliveryStatus())) {
                    undeliveredShipments.add(shipment);
                }
            }
        }

        // Teslimat süresine göre merge sort ile sıralama
        mergeSort(undeliveredShipments, 0, undeliveredShipments.size() - 1);

        StringBuilder result = new StringBuilder("Teslim Edilmemiş Kargolar (Teslimat Süresine Göre):\n");
        for (Shipment shipment : undeliveredShipments) {
            result.append("ID: ").append(shipment.getShipmentID())
                    .append(", Şehir: ").append(shipment.getCity())
                    .append(", Teslimat Süresi: ").append(shipment.getDeliveryTime())
                    .append(" gün\n");
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }
    private void mergeSort(List<Shipment> list, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(list, left, mid);
            mergeSort(list, mid + 1, right);

            merge(list, left, mid, right);
        }
    }
    private void merge(List<Shipment> list, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<Shipment> leftList = new ArrayList<>();
        List<Shipment> rightList = new ArrayList<>();

        for (int i = 0; i < n1; ++i)
            leftList.add(list.get(left + i));
        for (int j = 0; j < n2; ++j)
            rightList.add(list.get(mid + 1 + j));

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftList.get(i).getDeliveryTime() <= rightList.get(j).getDeliveryTime()) {
                list.set(k, leftList.get(i));
                i++;
            } else {
                list.set(k, rightList.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            list.set(k, leftList.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, rightList.get(j));
            j++;
            k++;
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerManagementApp app = new CustomerManagementApp();
            GUI gui =new GUI();
            app.initializeCityTree();
            gui.showGUI();

        });
    }
}