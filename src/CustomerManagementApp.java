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
        mersin.children.add(new TreeNode("K.Maraş", 7));
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
        frame.setSize(800, 700); // Çerçeve boyutu
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Ekranın ortasında açılması
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // Üst görsel
        ImageIcon headerIcon = new ImageIcon(Icons.class.getResource("/Resources/skydoves_logo.jpg"));
        int headerWidth = 500; // Daha büyük genişlik
        int headerHeight = 200; // Daha büyük yükseklik
        Image scaledHeaderImage = headerIcon.getImage().getScaledInstance(headerWidth, headerHeight, Image.SCALE_SMOOTH);
        headerIcon = new ImageIcon(scaledHeaderImage);

        JLabel headerLabel = new JLabel(headerIcon);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortalanmış görsel

        // Panel ayarları
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Panelin arka plan rengini ayarla
        panel.setBackground(Color.decode("#ADD8E6")); // Açık mavi


        // Görsel ekleme
        panel.add(Box.createVerticalStrut(20)); // Üst boşluk
        panel.add(headerLabel);
        panel.add(Box.createVerticalStrut(20)); // Görsel alt boşluk

        // Küçük ikon boyutları
        int buttonIconSize = 50;

        // İkonları yükle ve ölçeklendir
        ImageIcon addCustomerIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon addShipmentIcon = new ImageIcon(Icons.class.getResource("/Resources/kargoekle.jpg"));
        ImageIcon viewAllCustomersIcon = new ImageIcon(Icons.class.getResource("/Resources/tümmüsterilerigor.jpg"));

        Image customerImage = addCustomerIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH);
        addCustomerIcon = new ImageIcon(customerImage);

        Image shipmentImage = addShipmentIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH);
        addShipmentIcon = new ImageIcon(shipmentImage);

        Image viewAllCustomersImage = viewAllCustomersIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH);
        viewAllCustomersIcon = new ImageIcon(viewAllCustomersImage);

        // Butonları oluşturma

        // Üstteki resmin genişliğini referans olarak alıyoruz
        int buttonWidth = headerWidth; // headerWidth, üstteki resmin genişliği (300)
        int buttonHeight = 50; // Sabit bir yükseklik

        // Butonlar için ortak boyut ayarı
        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);


        JButton addCustomerButton = new JButton(addCustomerIcon);
        addCustomerButton.setText("Müşteri Ekle");
        addCustomerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addCustomerButton.setBackground(Color.decode("#ffe5ec"));
        addCustomerButton.setForeground(Color.decode("#043565"));
        addCustomerButton.setMaximumSize(buttonSize);
        addCustomerButton.setPreferredSize(buttonSize);
        addCustomerButton.setMinimumSize(buttonSize);

        JButton addShipmentButton = new JButton(addShipmentIcon);
        addShipmentButton.setText("Gönderi Ekle");
        addShipmentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addShipmentButton.setBackground(Color.decode("#ffe5ec"));
        addShipmentButton.setForeground(Color.decode("#043565"));
        addShipmentButton.setMaximumSize(buttonSize);
        addShipmentButton.setPreferredSize(buttonSize);
        addShipmentButton.setMinimumSize(buttonSize);

        JButton viewAllCustomersButton = new JButton(viewAllCustomersIcon);
        viewAllCustomersButton.setText("Tüm Müşterileri Gör");
        viewAllCustomersButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewAllCustomersButton.setBackground(Color.decode("#ffe5ec"));
        viewAllCustomersButton.setForeground(Color.decode("#043565"));
        viewAllCustomersButton.setMaximumSize(buttonSize);
        viewAllCustomersButton.setPreferredSize(buttonSize);
        viewAllCustomersButton.setMinimumSize(buttonSize);

        JButton viewCustomerHistoryButton = new JButton("Müşteri Geçmişini Gör(Son 5 Gönderi)");
        viewCustomerHistoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewCustomerHistoryButton.setBackground(Color.decode("#ffe5ec"));
        viewCustomerHistoryButton.setForeground(Color.decode("#043565"));
        viewCustomerHistoryButton.setMaximumSize(buttonSize);
        viewCustomerHistoryButton.setPreferredSize(buttonSize);
        viewCustomerHistoryButton.setMinimumSize(buttonSize);


        JButton updateShipmentStatusButton = new JButton("Gönderi Durumunu Güncelle");
        updateShipmentStatusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateShipmentStatusButton.setBackground(Color.decode("#ffe5ec"));
        updateShipmentStatusButton.setForeground(Color.decode("#043565"));
        updateShipmentStatusButton.setMaximumSize(buttonSize);
        updateShipmentStatusButton.setPreferredSize(buttonSize);
        updateShipmentStatusButton.setMinimumSize(buttonSize);


        JButton checkShipmentStatusButton = new JButton("Kargo Durumu Sorgula");
        checkShipmentStatusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkShipmentStatusButton.setBackground(Color.decode("#ffe5ec"));
        checkShipmentStatusButton.setForeground(Color.decode("#043565"));
        checkShipmentStatusButton.setMaximumSize(buttonSize);
        checkShipmentStatusButton.setPreferredSize(buttonSize);
        checkShipmentStatusButton.setMinimumSize(buttonSize);


        JButton searchShipmentButton = new JButton("Gönderi Ara");
        searchShipmentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchShipmentButton.setBackground(Color.decode("#ffe5ec"));
        searchShipmentButton.setForeground(Color.decode("#043565"));
        searchShipmentButton.setMaximumSize(buttonSize);
        searchShipmentButton.setPreferredSize(buttonSize);
        searchShipmentButton.setMinimumSize(buttonSize);


        JButton deleteShipmentButton = new JButton("Gönderi Sil");
        deleteShipmentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteShipmentButton.setBackground(Color.decode("#ffe5ec"));
        deleteShipmentButton.setForeground(Color.decode("#043565"));
        deleteShipmentButton.setMaximumSize(buttonSize);
        deleteShipmentButton.setPreferredSize(buttonSize);
        deleteShipmentButton.setMinimumSize(buttonSize);

        JButton viewShipmentStackButton = new JButton("Tüm Gönderileri Listele(Sipariş Sırasına Göre)");
        viewShipmentStackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewShipmentStackButton.setBackground(Color.decode("#ffe5ec"));
        viewShipmentStackButton.setForeground(Color.decode("#043565"));
        viewShipmentStackButton.setMaximumSize(buttonSize);
        viewShipmentStackButton.setPreferredSize(buttonSize);
        viewShipmentStackButton.setMinimumSize(buttonSize);

        JButton sortShipmentsButton = new JButton("Tüm Gönderileri Listele(Teslimat Süresine Göre)");
        sortShipmentsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sortShipmentsButton.setBackground(Color.decode("#ffe5ec"));
        sortShipmentsButton.setForeground(Color.decode("#043565"));
        sortShipmentsButton.setMaximumSize(buttonSize);
        sortShipmentsButton.setPreferredSize(buttonSize);
        sortShipmentsButton.setMinimumSize(buttonSize);


        JButton showDeliveryRouteButton = new JButton("Rota Ağaç Şeması");
        showDeliveryRouteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        showDeliveryRouteButton.setBackground(Color.decode("#ffe5ec"));
        showDeliveryRouteButton.setForeground(Color.decode("#043565"));
        showDeliveryRouteButton.setMaximumSize(buttonSize);
        showDeliveryRouteButton.setPreferredSize(buttonSize);
        showDeliveryRouteButton.setMinimumSize(buttonSize);

        JButton printRoutesFromShipmentsButton = new JButton("Gönderi Rotası");
        printRoutesFromShipmentsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        printRoutesFromShipmentsButton.setBackground(Color.decode("#ffe5ec"));
        printRoutesFromShipmentsButton.setForeground(Color.decode("#043565"));
        printRoutesFromShipmentsButton.setMaximumSize(buttonSize);
        printRoutesFromShipmentsButton.setPreferredSize(buttonSize);
        printRoutesFromShipmentsButton.setMinimumSize(buttonSize);



        // Butonları panele ekleme
        panel.add(addCustomerButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(addShipmentButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(viewAllCustomersButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(viewCustomerHistoryButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(updateShipmentStatusButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(checkShipmentStatusButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(searchShipmentButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(deleteShipmentButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(viewShipmentStackButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(sortShipmentsButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(showDeliveryRouteButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(printRoutesFromShipmentsButton);
        panel.add(Box.createVerticalStrut(10));

        // ActionListener ekleme
        addCustomerButton.addActionListener(e -> addCustomerDialog());
        addShipmentButton.addActionListener(e -> addShipmentDialog());
        viewAllCustomersButton.addActionListener(e -> viewAllCustomers());
        viewCustomerHistoryButton.addActionListener(e -> viewCustomerHistory());
        updateShipmentStatusButton.addActionListener(e -> updateShipmentStatus());
        checkShipmentStatusButton.addActionListener(e -> checkShipmentStatus());
        searchShipmentButton.addActionListener(e -> searchShipment());
        deleteShipmentButton.addActionListener(e -> deleteShipment());
        viewShipmentStackButton.addActionListener(e -> listAllShipments());
        sortShipmentsButton.addActionListener(e -> sortShipmentsByDeliveryTime());
        showDeliveryRouteButton.addActionListener(e -> showDeliveryRoute());

        printRoutesFromShipmentsButton.addActionListener(e -> printRoutesFromShipments());

        // Frame'e panel ekleme
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

        JLabel nameLabel = new JLabel("Adı-Soyadı:");
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
                        selectedCustomer.addShipment(shipment, this);

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

                // Müşterinin gönderi geçmişi LinkedList olarak alınıyor
                LinkedList<Shipment> shipments = customer.getShipmentStack(); // ShipmentStack alıyoruz

                // ListIterator ile listenin sonuna gidiyoruz
                ListIterator<Shipment> iterator = shipments.listIterator(shipments.size());  // Son elemana başlatıyoruz
                int count = 0;

                // Son 5 gönderiyi alıyoruz
                while (iterator.hasPrevious() && count < 5) {
                    Shipment shipment = iterator.previous();  // Ters yönde ilerliyoruz
                    history.append(shipment).append("\n");
                    count++;
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
    // Global Stack tanımlaması (Bu sınıfın içinde tanımlayın, örneğin, ana yönetim sınıfınızda)
    private Stack<Shipment> allShipmentsStack = new Stack<>();
    // Gönderiyi global stack'e ekleme metodu
    public void addShipmentToGlobalStack(Shipment shipment) {
        allShipmentsStack.push(shipment); // Gönderi stack'e ekleniyor
    }

    public void listAllShipments() {
        StringBuilder allShipmentsContent = new StringBuilder("Tüm Gönderiler:\n");

        // Eğer stack boşsa, kullanıcıya bildiriyoruz
        if (allShipmentsStack.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hiç gönderi bulunamadı.");
        } else {
            // Stack'teki gönderileri sırasıyla alıp ekrana yazdırıyoruz
            while (!allShipmentsStack.isEmpty()) {
                Shipment shipment = allShipmentsStack.pop(); // En son eklenen gönderi ilk olarak alınır (LIFO)
                allShipmentsContent.append(shipment).append("\n");
            }

            // Gönderileri ekrana yazdırıyoruz
            JOptionPane.showMessageDialog(null, allShipmentsContent.toString());
        }
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


    public void checkShipmentStatus() {
        // Kullanıcının oluşturduğu gönderilerden seçim yapabileceği bir pencere
        JFrame dialog = new JFrame("Kargo Durumu Sorgula");
        dialog.setSize(500, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Kullanıcının tüm kargolarını listelemek için bir JComboBox
        JComboBox<String> shipmentSelector = new JComboBox<>();
        Map<String, Shipment> shipmentMap = new HashMap<>();

        // Kullanıcıya ait tüm kargoları ekle
        for (Customer customer : customers) {
            for (Shipment shipment : customer.getShipmentHistory()) {
                String display = "ID: " + shipment.getShipmentID() + ", Şehir: " + shipment.getCity();
                shipmentSelector.addItem(display);
                shipmentMap.put(display, shipment);
            }
        }

        JButton checkButton = new JButton("Durumu Kontrol Et");

        panel.add(new JLabel("Kargonuzu Seçin:"));
        panel.add(shipmentSelector);
        panel.add(checkButton);

        dialog.add(panel);
        dialog.setVisible(true);

        // Seçim sonrası işlem yapma
        checkButton.addActionListener(e -> {
            String selectedItem = (String) shipmentSelector.getSelectedItem();
            if (selectedItem != null) {
                Shipment selectedShipment = shipmentMap.get(selectedItem);

                if (selectedShipment != null) {
                    // Teslim durumu kontrolü
                    String status = selectedShipment.getDeliveryStatus();
                    if ("Teslim Edildi".equalsIgnoreCase(status)) {
                        JOptionPane.showMessageDialog(dialog, "Seçilen kargo teslim edilmiştir.");
                    } else {
                        // Teslim edilmemiş kargolar için kalan gün hesaplama
                        int remainingDays = selectedShipment.getDeliveryTime() - selectedShipment.getPassedDays();
                        if (remainingDays > 0) {
                            JOptionPane.showMessageDialog(dialog,
                                    "Seçilen kargo henüz teslim edilmedi.\nTeslim edilmesine kalan gün sayısı: " + remainingDays);
                        } else {
                            JOptionPane.showMessageDialog(dialog,
                                    "Seçilen kargo teslim edilmesi gereken süreyi aşmıştır!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Kargo bulunamadı!");
                }
            }
        });
    }



    // Teslim edilmiş kargoları ID'ye göre binary search ile bulma
    private void searchDeliveredShipments() {
        // Tüm teslim edilmiş kargoları bir listeye ekle
        List<Shipment> deliveredShipments = new ArrayList<>();
        for (Customer customer : customers) {
            for (Shipment shipment : customer.getShipmentHistory()) {
                if ("Teslim Edildi".equalsIgnoreCase(shipment.getDeliveryStatus())) {
                    deliveredShipments.add(shipment);
                }
            }
        }
        // ID'ye göre sıralama
        deliveredShipments.sort(Comparator.comparingInt(Shipment::getShipmentID));

        // Kullanıcıdan arama yapmak için ID al
        String input = JOptionPane.showInputDialog("Aramak istediğiniz kargo ID'sini girin:");
        try {
            int searchID = Integer.parseInt(input);

            // Binary search
            int index = binarySearch(deliveredShipments, searchID);
            if (index != -1) {
                Shipment shipment = deliveredShipments.get(index);
                JOptionPane.showMessageDialog(null, "Kargo Bulundu:\n" + shipment);
            } else {
                JOptionPane.showMessageDialog(null, "Kargo bulunamadı.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Geçerli bir ID giriniz.");
        }
    }


    // Binary search algoritması
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
        return -1; // Bulunamadı
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

        // Sonuçları göster
        StringBuilder result = new StringBuilder("Teslim Edilmemiş Kargolar (Teslimat Süresine Göre):\n");
        for (Shipment shipment : undeliveredShipments) {
            result.append("ID: ").append(shipment.getShipmentID())
                    .append(", Şehir: ").append(shipment.getCity())
                    .append(", Teslimat Süresi: ").append(shipment.getDeliveryTime())
                    .append(" gün\n");
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }



    // Merge Sort algoritması
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

            app.initializeCityTree();
            app.showGUI();

        });
    }
}