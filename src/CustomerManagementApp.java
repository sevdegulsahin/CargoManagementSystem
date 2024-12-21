import Resources.Icons;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.PriorityQueue;

public class CustomerManagementApp {
    private LinkedList<Customer> customers = new LinkedList<>();
    private Map<String, Integer> cityDistances = new TreeMap<>();
    private TreeNode cityTreeRoot;
    private int shipmentIDCounter = 1000;

    public CustomerManagementApp() {
        initializeCityTree();
    }
    // Şehir ağacında İstanbul'dan hedef şehre kadar olan yolu bulan fonksiyon
    public List<String> getPathToCity(String targetCity) {
        List<String> path = new ArrayList<>();
        if (findPath(cityTreeRoot, targetCity, path)) {
            return path;
        } else {
            return Collections.singletonList("Hedef şehir bulunamadı!");
        }
    }

    // Yardımcı fonksiyon: Şehir yolunu bulur
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
            return root;  // Şehir bulunduğunda, ilgili düğümü döndür
        }
        // Çocuklar üzerinde arama yap
        for (TreeNode child : root.children) {
            TreeNode result = findCityNode(city, child);
            if (result != null) {
                return result;
            }
        }
        return null;  // Şehir bulunamazsa null döndür
    }
    private void initializeCityTree() {
        cityTreeRoot = new TreeNode("Istanbul", 1); // Root şehri derinliği 0

        // Birinci seviye şehirler
        TreeNode ankara = new TreeNode("Ankara", 2);
        TreeNode kastamonu = new TreeNode("Kastamonu", 2);
        TreeNode bursa = new TreeNode("Bursa", 2);

        // Ankara'nın alt şehirleri
        TreeNode mersin = new TreeNode("Mersin", 4);
        TreeNode kayseri = new TreeNode("Kayseri", 4);
        ankara.children.add(mersin);
        ankara.children.add(kayseri);

        // Izmir'in alt şehirleri
        TreeNode tokat = new TreeNode("Tokat", 4);
        TreeNode giresun = new TreeNode("Giresun", 4);
        kastamonu.children.add(tokat);
        kastamonu.children.add(giresun);

        // Bursa'nın alt şehirleri
        TreeNode i̇zmir = new TreeNode("İzmir", 4);
        TreeNode canakkale = new TreeNode("Çanakkale", 4);
        bursa.children.add(i̇zmir);
        bursa.children.add(canakkale);

        // Daha alt seviyedeki şehirler
        mersin.children.add(new TreeNode("Kahramanmaraş", 7));
        mersin.children.add(new TreeNode("Hatay", 7));

        kayseri.children.add(new TreeNode("Malatya", 7));
        kayseri.children.add(new TreeNode("Elazığ", 7));

        tokat.children.add(new TreeNode("Erzincan", 7));
        tokat.children.add(new TreeNode("Sivas", 7));

        giresun.children.add(new TreeNode("Erzurum", 7));
        giresun.children.add(new TreeNode("Rize", 7));

        i̇zmir.children.add(new TreeNode("Muğla", 7));
        i̇zmir.children.add(new TreeNode("Antalya", 7));

        // Şehirleri kök düğüme ekleme
        cityTreeRoot.children.add(ankara);
        cityTreeRoot.children.add(kastamonu);
        cityTreeRoot.children.add(bursa);

        int maxDepth = cityTreeRoot.calculateDepth(cityTreeRoot, 0);
        System.out.println("Ağaç yapısının derinliği: " + maxDepth);
    }

    // Teslimat süresi hesaplamak için bir fonksiyon
    public int getDeliveryTimeForCity(String cityName) {
        TreeNode cityNode = findCityNode(cityName, cityTreeRoot);
        if (cityNode != null) {
            return cityNode.deliveryTime; // Derinlik ile ilişkilendirilmiş teslimat süresi
        } else {
            System.out.println("City not found!");
            return -1; // Şehir bulunamadıysa -1 döndür
        }
    }




    public void printRoutesFromShipments() {
        // Müşteri listesi kontrolü
        if (customers.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hiç müşteri kaydı yok!");
            return;
        }

        // Müşteri seçimi için bir JComboBox oluştur
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
                    JOptionPane.showMessageDialog(null, "Seçilen müşterinin gönderisi yok!");
                    return;
                }

                // Gönderi seçimi için bir JComboBox oluştur
                DefaultComboBoxModel<String> shipmentModel = new DefaultComboBoxModel<>();
                for (Shipment shipment : selectedCustomer.getShipmentHistory()) {
                    shipmentModel.addElement("ID: " + shipment.getShipmentID() + " - Şehir: " + shipment.getCity());
                }

                JComboBox<String> shipmentComboBox = new JComboBox<>(shipmentModel);
                int shipmentResult = JOptionPane.showConfirmDialog(null, shipmentComboBox, "Gönderi Seçin", JOptionPane.OK_CANCEL_OPTION);

                if (shipmentResult == JOptionPane.OK_OPTION) {
                    // Seçilen gönderi
                    String selectedShipmentText = (String) shipmentComboBox.getSelectedItem();
                    String cityName = selectedShipmentText.split(" - Şehir: ")[1].trim();

                    // Rotayı hesapla ve göster
                    List<String> path = getPathToCity(cityName);
                    StringBuilder routeMessage = new StringBuilder("Teslimat rotası:\n");
                    for (String city : path) {
                        routeMessage.append(city).append("\n");
                    }

                    JOptionPane.showMessageDialog(null, routeMessage.toString());
                }
            }
        }
    }





    public void showGUI() {
        JFrame frame = new JFrame("Müşteri Yönetimi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));  // Dikey olarak BoxLayout ekledik.

        // Kare ikon boyutları
        int buttonSize = 100;  // Butonların ikonlarının kare boyutu 100x100 olacak şekilde sabitlendi.

        // İkonları yükle ve kare boyutlarda ölçeklendir
        ImageIcon addCustomerIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon addShipmentIcon = new ImageIcon(Icons.class.getResource("/Resources/kargoekle.jpg"));
        ImageIcon viewAllCustomersIcon = new ImageIcon(Icons.class.getResource("/Resources/tümmüsterilerigor.jpg"));  // Yeni ikon

        Image customerImage = addCustomerIcon.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
        addCustomerIcon = new ImageIcon(customerImage);

        Image shipmentImage = addShipmentIcon.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
        addShipmentIcon = new ImageIcon(shipmentImage);

        Image viewAllCustomersImage = viewAllCustomersIcon.getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH);
        viewAllCustomersIcon = new ImageIcon(viewAllCustomersImage);

        JButton addCustomerButton = new JButton(addCustomerIcon);  // sadece ikon atanır
        addCustomerButton.setText("Müşteri Ekle");  // metin eklenir

        JButton addShipmentButton = new JButton(addShipmentIcon);
        addShipmentButton.setText("Gönderi Ekle");  // metin eklenir

        JButton viewAllCustomersButton = new JButton(viewAllCustomersIcon);
        viewAllCustomersButton.setText("Tüm Müşterileri Gör");  // metin eklenir

        JButton viewCustomerHistoryButton = new JButton("Müşteri Geçmişini Gör");
        JButton updateShipmentStatusButton = new JButton("Gönderi Durumunu Güncelle");
        JButton searchShipmentButton = new JButton("Gönderi Ara");
        JButton deleteShipmentButton = new JButton("Gönderi Sil");
        JButton viewShipmentStackButton = new JButton("Son 5 Gönderiyi Gör");
        JButton showDeliveryRouteButton = new JButton("Teslimat Rotasını Göster");

        panel.add(addCustomerButton);
        panel.add(addShipmentButton);
        panel.add(viewAllCustomersButton);  // Buton eklendi
        panel.add(viewCustomerHistoryButton);
        panel.add(updateShipmentStatusButton);
        panel.add(searchShipmentButton);
        panel.add(deleteShipmentButton);
        panel.add(viewShipmentStackButton);
        panel.add(showDeliveryRouteButton);

        addCustomerButton.addActionListener(e -> addCustomerDialog());
        addShipmentButton.addActionListener(e -> addShipmentDialog());
        viewAllCustomersButton.addActionListener(e -> viewAllCustomers());
        viewCustomerHistoryButton.addActionListener(e -> viewCustomerHistory());
        updateShipmentStatusButton.addActionListener(e -> updateShipmentStatus());
        searchShipmentButton.addActionListener(e -> searchShipment());
        deleteShipmentButton.addActionListener(e -> deleteShipment());
        viewShipmentStackButton.addActionListener(e -> viewShipmentStack());
        showDeliveryRouteButton.addActionListener(e -> showDeliveryRoute());
        JButton sortShipmentsButton = new JButton("Gönderileri Teslim Süresine Göre Sırala");
        panel.add(sortShipmentsButton);
        sortShipmentsButton.addActionListener(e -> sortShipmentsByDeliveryTime());

// Yeni butonu burada tanımlıyoruz
        JButton printRoutesButton = new JButton("Teslimat Rotalarını Yazdır");
        panel.add(printRoutesButton);
        printRoutesButton.addActionListener(e -> {
            String targetCity = JOptionPane.showInputDialog("Hedef şehri girin:");
            if (targetCity != null && !targetCity.trim().isEmpty()) {
                List<String> path = getPathToCity(targetCity.trim());
                StringBuilder routeMessage = new StringBuilder("Teslimat rotası:\n");
                for (String city : path) {
                    routeMessage.append(city).append("\n");
                }
                JOptionPane.showMessageDialog(null, routeMessage.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Geçerli bir şehir giriniz!");
            }
        });
        JButton printRoutesFromShipmentsButton = new JButton("Gönderi Rotası");
        panel.add(printRoutesFromShipmentsButton);
        printRoutesFromShipmentsButton.addActionListener(e -> printRoutesFromShipments());





        frame.add(panel);
        frame.setVisible(true);
    }
    public void sortShipmentsByDeliveryTime() {
        // PriorityQueue, teslim süresine göre sıralamak için kullanılır.
        PriorityQueue<Shipment> shipmentQueue = new PriorityQueue<>(Comparator.comparingInt(Shipment::getDeliveryTime));

        // Her müşteri için gönderi geçmişi ekleyin
        for (Customer customer : customers) {
            shipmentQueue.addAll(customer.getShipmentHistory());
        }

        // Sıralı gönderileri gösterme
        StringBuilder sortedShipments = new StringBuilder("Teslim Süresine Göre Sıralı Gönderiler:\n");
        while (!shipmentQueue.isEmpty()) {
            sortedShipments.append(shipmentQueue.poll()).append("\n");
        }
        JOptionPane.showMessageDialog(null, sortedShipments.toString());
    }


    public void addCustomerDialog() {
        JFrame dialog = new JFrame("Müşteri Ekle");
        dialog.setSize(300, 200);

        JLabel nameLabel = new JLabel("Adı:");
        JTextField nameField = new JTextField();
        JButton addButton = new JButton("Ekle");
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(new JLabel());
        panel.add(addButton);

        dialog.add(panel);

        addButton.addActionListener(e -> {
            try {
                int id = new Random().nextInt(10000);
                String name = nameField.getText();
                Customer newCustomer = new Customer(id, name);
                customers.add(newCustomer);

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

        DefaultComboBoxModel<String> customerComboBoxModel = new DefaultComboBoxModel<>();
        for (Customer customer : customers) {
            customerComboBoxModel.addElement("ID: " + customer.getCustomerID() + " - " + customer.getName());
        }

        JComboBox<String> customerComboBox = new JComboBox<>(customerComboBoxModel);

        JLabel cityLabel = new JLabel("Teslimat Şehri:");

        // Şehirleri almak için helper fonksiyonu çağır
        java.util.List<String> cityNames = getAllCityNames(cityTreeRoot);
        JComboBox<String> cityComboBox = new JComboBox<>(cityNames.toArray(new String[0]));

        JButton addButton = new JButton("Ekle");

        JScrollPane scrollPane = new JScrollPane(cityComboBox);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(customerLabel);
        panel.add(customerComboBox);
        panel.add(cityLabel);
        panel.add(scrollPane);
        panel.add(new JLabel());
        panel.add(addButton);

        dialog.add(panel);

        addButton.addActionListener(e -> {
            try {
                // Seçilen müşteri bilgilerini al
                String selectedCustomerText = (String) customerComboBox.getSelectedItem();
                int customerID = Integer.parseInt(selectedCustomerText.split(" - ")[0].replace("ID: ", "").trim());
                Customer selectedCustomer = null;
                for (Customer customer : customers) {
                    if (customer.getCustomerID() == customerID) {
                        selectedCustomer = customer;
                        break;
                    }
                }

                // Seçilen şehir bilgilerini al
                String city = (String) cityComboBox.getSelectedItem();

                // Teslimat süresi için getDeliveryTimeForCity fonksiyonunu çağır
                int deliveryTime = getDeliveryTimeForCity(city);

                if (deliveryTime != -1) {  // Eğer şehir geçerli ise
                    TreeNode cityNode = findCityNode(city, cityTreeRoot);
                    if (cityNode != null) {
                        // Şehirdeki kargo sayısını artır
                        cityNode.cargoCount++;

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDate = sdf.format(new Date());
                        // Gönderi oluştur ve müşteri geçmişine ekle
                        Shipment shipment = new Shipment(shipmentIDCounter++, currentDate, "Beklemede", deliveryTime, city, selectedCustomer);
                        selectedCustomer.addShipment(shipment);

                        JOptionPane.showMessageDialog(dialog, "Gönderi başarıyla eklendi.\nTeslimat Süresi: " + shipment.getDeliveryTime() + " gün.\nGönderi ID: " + shipment.getShipmentID());
                        dialog.dispose();
                    } else {

                        JOptionPane.showMessageDialog(dialog, "Geçersiz şehir adı.");
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Geçersiz şehir adı.");
                } dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Gönderi eklerken hata oluştu.");
            }
        });

        dialog.setVisible(true);
    }

    // CityTree'deki tüm şehirleri alacak fonksiyon
    private java.util.List<String> getAllCityNames(TreeNode node) {
        List<String> cityNames = new ArrayList<>();
        // Eğer şehir mevcutsa, ismini ekle
        if (node != null) {
            cityNames.add(node.city);
            // Çocuk düğümlere de aynı işlemi yap
            for (TreeNode child : node.children) {
                cityNames.addAll(getAllCityNames(child)); // Rekürsif olarak alt şehirleri al
            }
        }
        return cityNames;
    }



    public void viewAllCustomers() {
        StringBuilder customersList = new StringBuilder("Tüm Müşteriler:\n");
        for (Customer customer : customers) {
            customersList.append("ID: ").append(customer.getCustomerID()).append(", Ad: ").append(customer.getName()).append("\n");
        }
        JOptionPane.showMessageDialog(null, customersList.toString());
    }

    public void viewCustomerHistory() {
        String customerName = JOptionPane.showInputDialog("Geçmişi görmek istediğiniz müşteri adını girin:");
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(customerName)) {
                StringBuilder history = new StringBuilder("Müşteri Geçmişi:\n");
                for (Shipment shipment : customer.getShipmentHistory()) {
                    history.append(shipment).append("\n");
                }
                JOptionPane.showMessageDialog(null, history.toString());
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Müşteri bulunamadı.");
    }

    public void updateShipmentStatus() {
        String shipmentID = JOptionPane.showInputDialog("Durumunu güncellemek istediğiniz gönderi ID'sini girin:");
        for (Customer customer : customers) {
            for (Shipment shipment : customer.getShipmentHistory()) {
                if (String.valueOf(shipment.getShipmentID()).equals(shipmentID)) {
                    String status = JOptionPane.showInputDialog("Yeni durum:");
                    shipment.deliveryStatus = status;
                    JOptionPane.showMessageDialog(null, "Durum başarıyla güncellendi.");
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Gönderi bulunamadı.");
    }

    public void searchShipment() {
        String shipmentID = JOptionPane.showInputDialog("Aradığınız gönderi ID'sini girin:");
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
        String shipmentID = JOptionPane.showInputDialog("Silmek istediğiniz gönderi ID'sini girin:");
        for (Customer customer : customers) {
            Iterator<Shipment> iterator = customer.getShipmentHistory().iterator();
            while (iterator.hasNext()) {
                Shipment shipment = iterator.next();
                if (String.valueOf(shipment.getShipmentID()).equals(shipmentID)) {
                    iterator.remove();
                    JOptionPane.showMessageDialog(null, "Gönderi başarıyla silindi.");
                    return;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Gönderi bulunamadı.");
    }

    public void viewShipmentStack() {
        StringBuilder stackContent = new StringBuilder("Son 5 Gönderi:\n");
        for (Customer customer : customers) {
            if (!customer.getShipmentStack().isEmpty()) {
                for (Shipment shipment : customer.getShipmentStack()) {
                    stackContent.append(shipment).append("\n");
                }
                JOptionPane.showMessageDialog(null, stackContent.toString());
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Son 5 gönderi bulunamadı.");
    }

    public void showDeliveryRoute() {
        JFrame frame = new JFrame("Teslimat Rotası - Şehirler Ağaç Yapısı");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        CityTreeVisualizationPanel treePanel = new CityTreeVisualizationPanel(cityTreeRoot);
        frame.add(new JScrollPane(treePanel));

        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerManagementApp app = new CustomerManagementApp();

            app.initializeCityTree();
            app.showGUI();

        });
    }
}
