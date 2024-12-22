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
            // UIManager renk ayarları
            UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
            UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı
            UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz
            UIManager.put("Button.background", Color.decode("#ffe5ec"));  // OK ve Cancel butonlarının arka plan rengi (pembe)
            UIManager.put("Button.foreground", Color.decode("#012a4a"));  // Buton yazı rengi (lacivert)

            JOptionPane.showMessageDialog(null, "Hiç müşteri kaydı yok!", "Hata", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Seçilen müşterinin gönderisi yok!", "Hata", JOptionPane.ERROR_MESSAGE);
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

                    // UIManager renk ayarları
                    UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
                    UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı
                    UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz
                    UIManager.put("Button.background", Color.decode("#ffe5ec"));  // OK ve Cancel butonlarının arka plan rengi (pembe)
                    UIManager.put("Button.foreground", Color.decode("#012a4a"));  // Buton yazı rengi (lacivert)

                    JOptionPane.showMessageDialog(null, routeMessage.toString(), "Teslimat Rotası", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
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
        if (shipmentQueue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Hiç gönderi bulunmamaktadır.", "Uyarı", JOptionPane.WARNING_MESSAGE);
        } else {
            while (!shipmentQueue.isEmpty()) {
                sortedShipments.append(shipmentQueue.poll()).append("\n");
            }

            // UIManager renk ayarları
            UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
            UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı
            UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz
            UIManager.put("Button.background", Color.decode("#ffe5ec"));  // OK ve Cancel butonlarının arka plan rengi (pembe)
            UIManager.put("Button.foreground", Color.decode("#012a4a"));  // Buton yazı rengi (lacivert)

            JOptionPane.showMessageDialog(null, sortedShipments.toString(), "Sıralı Gönderiler", JOptionPane.INFORMATION_MESSAGE);
        }
    }




    public void addCustomerDialog() {
        JFrame dialog = new JFrame("Müşteri Ekle");
        dialog.setSize(300, 200);

        JLabel nameLabel = new JLabel("Adı-Soyadı:");
        nameLabel.setForeground(Color.decode("#FFFFFF")); // Yazı rengini beyaz yapar (hex kodu: #FFFFFF)
        JTextField nameField = new JTextField();
        JButton addButton = new JButton("Ekle");

        // Butonun arka plan ve yazı rengini ayarlıyoruz
        addButton.setBackground(Color.decode("#ffe5ec")); // Arka plan pembe
        addButton.setForeground(Color.decode("#012a4a")); // lacivert


        JPanel panel = new JPanel(new GridLayout(2, 2));

        // Panelin arka plan rengini ayarlıyoruz
        panel.setBackground(Color.decode("#012a4a")); // lacivert


        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(new JLabel()); // Boş alan
        panel.add(addButton);

        dialog.add(panel);

        // Pencerenin ortada açılması
        dialog.setLocationRelativeTo(null);

        addButton.addActionListener(e -> {
            Set<Integer> customerIds = new HashSet<>(); // Benzersiz ID'leri tutmak için

            try {
                Random random = new Random();
                int id;

                // Benzersiz bir ID bulunana kadar döngü
                do {
                    id = 10000 + random.nextInt(90000); // 10000-99999 arasında sayı üretir
                } while (customerIds.contains(id));

                customerIds.add(id); // Yeni ID'yi listeye ekle

                String name = nameField.getText();
                Customer newCustomer = new Customer(id, name);
                customers.add(newCustomer);

                // UIManager ile JOptionPane'in arka plan ve yazı rengini ayarlıyoruz
                UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
                UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı
                UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz

                // Butonların arka plan ve yazı rengini ayarlama
                UIManager.put("Button.background", Color.decode("#ffe5ec"));  // OK ve Cancel butonlarının arka plan rengi (yeşil)
                UIManager.put("Button.foreground", Color.decode("#012a4a"));              // Buton yazı rengi (beyaz)

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

        // Etiketlerin yazı rengini beyaz yapıyoruz
        JLabel customerLabel = new JLabel("Müşteri Seç:");
        customerLabel.setForeground(Color.decode("#FFFFFF")); // Beyaz yazı rengi

        // Müşteri ComboBox
        DefaultComboBoxModel<String> customerComboBoxModel = new DefaultComboBoxModel<>();
        for (Customer customer : customers) {
            customerComboBoxModel.addElement("ID: " + customer.getCustomerID() + " - " + customer.getName());
        }
        JComboBox<String> customerComboBox = new JComboBox<>(customerComboBoxModel);
        customerComboBox.setBackground(Color.decode("#ffe5ec")); // Pembe arka plan
        customerComboBox.setForeground(Color.decode("#012a4a")); // Lacivert yazı rengi

        // Şehir etiketinin yazı rengini beyaz yapıyoruz
        JLabel cityLabel = new JLabel("Teslimat Şehri:");
        cityLabel.setForeground(Color.decode("#FFFFFF")); // Beyaz yazı rengi

        // Şehirler için ComboBox
        java.util.List<String> cityNames = getAllCityNames(cityTreeRoot);
        JComboBox<String> cityComboBox = new JComboBox<>(cityNames.toArray(new String[0]));
        cityComboBox.setBackground(Color.decode("#ffe5ec")); // Pembe arka plan
        cityComboBox.setForeground(Color.decode("#012a4a")); // Lacivert yazı rengi

        // Ekle butonunun arka plan rengini pembe, yazı rengini lacivert yapıyoruz
        JButton addButton = new JButton("Ekle");
        addButton.setBackground(Color.decode("#ffe5ec")); // Pembe arka plan
        addButton.setForeground(Color.decode("#012a4a")); // Lacivert yazı rengi

        // Panelin arka plan rengini lacivert yapıyoruz
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBackground(Color.decode("#012a4a")); // Lacivert arka plan

        // Komboları ve butonu panele ekliyoruz
        panel.add(customerLabel);
        panel.add(customerComboBox);
        panel.add(cityLabel);
        panel.add(new JScrollPane(cityComboBox)); // Scrollpane ile şehir ComboBox'ı
        panel.add(new JLabel()); // Boş alan
        panel.add(addButton);

        dialog.add(panel);

        // Pencerenin ortada açılması
        dialog.setLocationRelativeTo(null);

        // UIManager ile JOptionPane arka planı ve yazı rengini ayarlıyoruz
        UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
        UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz

        // Butonların arka plan ve yazı rengini ayarlıyoruz
        UIManager.put("Button.background", Color.decode("#ffe5ec"));  // OK ve Cancel butonlarının arka plan rengi (pembe)
        UIManager.put("Button.foreground", Color.decode("#012a4a"));   // Buton yazı rengi (lacivert)

        // Ekle butonuna tıklama işlemi
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

                // Teslimat süresi için fonksiyonu çağırıyoruz
                int deliveryTime = getDeliveryTimeForCity(city);

                if (deliveryTime != -1) {  // Eğer şehir geçerli ise
                    TreeNode cityNode = findCityNode(city, cityTreeRoot);
                    if (cityNode != null) {
                        // Şehirdeki kargo sayısını artırıyoruz
                        cityNode.cargoCount++;

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDate = sdf.format(new Date());
                        // Gönderi oluşturuyoruz ve müşteri geçmişine ekliyoruz
                        Shipment shipment = new Shipment(shipmentIDCounter++, currentDate, "Beklemede", deliveryTime, city, selectedCustomer);
                        selectedCustomer.addShipment(shipment, this);

                        // Başarı mesajını gösteriyoruz
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

        // JOptionPane arka plan ve yazı rengini ayarlıyoruz
        UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
        UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz

        // Mesaj kutusunu gösteriyoruz
        JOptionPane.showMessageDialog(null, customersList.toString());
    }





    public void updateShipmentStatus() {
        // Gönderi seçim ekranı
        DefaultComboBoxModel<String> shipmentComboBoxModel = new DefaultComboBoxModel<>();
        for (Customer customer : customers) {
            for (Shipment shipment : customer.getShipmentHistory()) {
                // Gönderi bilgilerini ComboBox'a ekleyin
                shipmentComboBoxModel.addElement("Müşteri: " + customer.getName() + " - Şehir: " + shipment.getCity() + " - Durum: " + shipment.deliveryStatus);
            }
        }

        JComboBox<String> shipmentComboBox = new JComboBox<>(shipmentComboBoxModel);
        shipmentComboBox.setBackground(Color.decode("#ffe5ec")); // ComboBox arka plan rengi pembe
        shipmentComboBox.setForeground(Color.decode("#012a4a")); // ComboBox yazı rengi lacivert

        // Durum değiştirme seçenekleri için bir JComboBox
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{
                "İşleme Alındı", "Teslimatta", "Teslim Edildi"
        });
        statusComboBox.setBackground(Color.decode("#ffe5ec")); // ComboBox arka plan rengi pembe
        statusComboBox.setForeground(Color.decode("#012a4a")); // ComboBox yazı rengi lacivert

        // ComboBox'u ekleyeceğimiz bir JPanel oluşturuyoruz
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Dikey yerleşim
        panel.setBackground(Color.decode("#012a4a")); // Panel arka plan rengi lacivert

        JLabel shipmentLabel = new JLabel("Gönderiyi Seçin:");
        shipmentLabel.setForeground(Color.WHITE); // Yazı rengi beyaz
        panel.add(shipmentLabel);
        panel.add(shipmentComboBox);

        JLabel statusLabel = new JLabel("Yeni Durumu Seçin:");
        statusLabel.setForeground(Color.WHITE); // Yazı rengi beyaz
        panel.add(Box.createVerticalStrut(10)); // Biraz boşluk ekler
        panel.add(statusLabel);
        panel.add(statusComboBox);

        // JDialog oluşturuluyor ve ayarlanıyor
        JDialog dialog = new JDialog();
        dialog.setTitle("Durumunu Güncelle");
        dialog.setSize(400, 250); // Pencere boyutunu ayarlıyoruz
        dialog.setLocationRelativeTo(null); // Pencereyi ekranın ortasında açar
        dialog.setModal(true); // Dialog modal hale geliyor, yani başka bir pencereye geçilemiyor

        // JPanel'i JDialog'e ekliyoruz
        dialog.add(panel);

        // Butonlar ekleniyor
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode("#012a4a")); // Buton panelinin arka planı lacivert
        JButton okButton = new JButton("Tamam");
        JButton cancelButton = new JButton("İptal");

        // Buton renk ayarları
        okButton.setBackground(Color.decode("#ffe5ec"));  // OK butonunun arka plan rengi pembe
        okButton.setForeground(Color.decode("#012a4a"));  // OK butonunun yazı rengi lacivert
        cancelButton.setBackground(Color.decode("#ffe5ec"));  // Cancel butonunun arka planı pembe
        cancelButton.setForeground(Color.decode("#012a4a"));  // Cancel butonunun yazı rengi lacivert

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        // Butonları panel'e ekliyoruz
        panel.add(Box.createVerticalStrut(10)); // Biraz daha boşluk
        panel.add(buttonPanel);

        // JOptionPane için UIManager renk ayarları
        UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
        UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz
        UIManager.put("Button.background", Color.decode("#ffe5ec"));  // OK ve Cancel butonlarının arka plan rengi (pembe)
        UIManager.put("Button.foreground", Color.decode("#012a4a"));  // Buton yazı rengi (lacivert)

        // Tamam butonuna tıklanınca işlem yapılır
        okButton.addActionListener(e -> {
            String selectedShipmentText = (String) shipmentComboBox.getSelectedItem();
            if (selectedShipmentText != null) {
                String[] shipmentDetails = selectedShipmentText.split(" - ");
                String customerName = shipmentDetails[0].replace("Müşteri: ", "");
                String deliveryCity = shipmentDetails[1].replace("Şehir: ", "");

                // Seçilen müşteri ve şehir bilgilerini kullanarak gönderiyi bul
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

                    // Gönderiler ComboBox'unu güncelle
                    shipmentComboBoxModel.removeAllElements();
                    for (Customer customer : customers) {
                        for (Shipment shipment : customer.getShipmentHistory()) {
                            shipmentComboBoxModel.addElement("Müşteri: " + customer.getName() + " - Şehir: " + shipment.getCity() + " - Durum: " + shipment.deliveryStatus);
                        }
                    }

                    JOptionPane.showMessageDialog(dialog, "Durum başarıyla güncellendi.\nYeni Durum: " + newStatus);

                    // Dialog'u kapat
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Seçilen gönderi bulunamadı.");
                }
            }
        });

        // İptal butonuna tıklanınca dialog kapanır
        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true); // Dialog'u görünür yapıyoruz
    }


    public void viewCustomerHistory() {
        // Müşterilerin adlarını ComboBox'ta göstermek için model oluşturuluyor
        DefaultComboBoxModel<String> customerComboBoxModel = new DefaultComboBoxModel<>();
        for (Customer customer : customers) {
            customerComboBoxModel.addElement(customer.getName());
        }

        // JComboBox oluşturuluyor
        JComboBox<String> customerComboBox = new JComboBox<>(customerComboBoxModel);

        // JOptionPane için UIManager renk ayarları
        UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
        UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz
        UIManager.put("Button.background", Color.decode("#ffe5ec"));  // OK ve Cancel butonlarının arka plan rengi (pembe)
        UIManager.put("Button.foreground", Color.decode("#012a4a"));  // Buton yazı rengi (lacivert)

        int option = JOptionPane.showConfirmDialog(null, customerComboBox, "Geçmişi görmek istediğiniz müşteriyi seçin:", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String selectedCustomerName = (String) customerComboBox.getSelectedItem();
            if (selectedCustomerName != null) {
                // Seçilen müşteri bulunuyor
                for (Customer customer : customers) {
                    if (customer.getName().equalsIgnoreCase(selectedCustomerName)) {
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

                        // Mesaj kutusunu renkli olarak gösteriyoruz
                        showCustomMessageDialog(history.toString(), "Müşteri Geçmişi", Color.decode("#012a4a"), Color.WHITE);
                        return;
                    }
                }
            }
        }
        // Müşteri bulunamadığında gösterilecek hata mesajı
        showCustomMessageDialog("Müşteri bulunamadı.", "Hata", Color.RED, Color.WHITE);
    }

    // Özelleştirilmiş renkli mesaj kutusu metodu
    private void showCustomMessageDialog(String message, String title, Color bgColor, Color textColor) {
        JPanel panel = new JPanel();
        panel.setBackground(bgColor); // Arka plan rengini ayarlıyoruz

        JLabel label = new JLabel(message);
        label.setForeground(textColor); // Yazı rengini ayarlıyoruz
        label.setFont(new Font("Arial", Font.PLAIN, 14)); // Font düzenlemesi
        panel.add(label);

        // Mesaj kutusunu göster
        JOptionPane.showMessageDialog(null, panel, title, JOptionPane.PLAIN_MESSAGE);
    }



    public void searchShipment() {
        // JOptionPane için UIManager renk ayarları
        UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
        UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz
        UIManager.put("Button.background", Color.decode("#ffe5ec"));  // OK ve Cancel butonlarının arka plan rengi (pembe)
        UIManager.put("Button.foreground", Color.decode("#012a4a"));  // Buton yazı rengi (lacivert)

        // Kullanıcıdan gönderi ID'si alınıyor
        String shipmentID = JOptionPane.showInputDialog(null, "Aradığınız gönderi ID'sini girin:");

        // Gönderi araması yapılıyor
        for (Customer customer : customers) {
            for (Shipment shipment : customer.getShipmentHistory()) {
                if (String.valueOf(shipment.getShipmentID()).equals(shipmentID)) {
                    JOptionPane.showMessageDialog(null, shipment);  // Gönderiyi ekrana yazdırıyoruz
                    return;
                }
            }
        }

        // Eğer gönderi bulunamazsa
        JOptionPane.showMessageDialog(null, "Gönderi bulunamadı.");
    }



    public void deleteShipment() {
        // JOptionPane için UIManager renk ayarları
        UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
        UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz
        UIManager.put("Button.background", Color.decode("#ffe5ec"));  // OK ve Cancel butonlarının arka plan rengi (pembe)
        UIManager.put("Button.foreground", Color.decode("#012a4a"));  // Buton yazı rengi (lacivert)

        String shipmentID = JOptionPane.showInputDialog("Silmek istediğiniz gönderi ID'sini girin:");

        // Eğer kullanıcı hiçbir şey girmediyse
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
                    // Silme başarılı olduğunda bilgi mesajı
                    JOptionPane.showMessageDialog(null,
                            "Gönderi başarıyla silindi.",
                            "Başarılı",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
            if (shipmentFound) {
                break; // Gönderi bulunduysa, dışarıdaki döngüyü sonlandır
            }
        }

        // Gönderi bulunamazsa hata mesajı
        if (!shipmentFound) {
            JOptionPane.showMessageDialog(null,
                    "Gönderi bulunamadı.",
                    "Hata",
                    JOptionPane.ERROR_MESSAGE);
        }
    }



    // Global Stack tanımlaması (Bu sınıfın içinde tanımlayın, örneğin, ana yönetim sınıfınızda)
    private Stack<Shipment> allShipmentsStack = new Stack<>();
    // Gönderiyi global stack'e ekleme metodu
    public void addShipmentToGlobalStack(Shipment shipment) {
        allShipmentsStack.push(shipment); // Gönderi stack'e ekleniyor
    }


    public void listAllShipments() {
        StringBuilder allShipmentsContent = new StringBuilder("Tüm Gönderiler:\n");

        // JOptionPane için UIManager renk ayarları
        UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
        UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz
        UIManager.put("Button.background", Color.decode("#ffe5ec"));  // OK ve Cancel butonlarının arka plan rengi (pembe)
        UIManager.put("Button.foreground", Color.decode("#012a4a"));  // Buton yazı rengi (lacivert)

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

            // Gönderileri ekrana yazdırıyoruz
            JTextArea textArea = new JTextArea(allShipmentsContent.toString());
            textArea.setEditable(false); // Metni düzenlenemez yapıyoruz
            textArea.setFont(new Font("Arial", Font.PLAIN, 14));
            textArea.setForeground(Color.decode("#FFFFFF")); // Yazı rengi beyaz
            textArea.setBackground(Color.decode("#012a4a")); // Arka plan rengi lacivert
            textArea.setBorder(BorderFactory.createLineBorder(Color.decode("#ffe5ec"), 1)); // Kenarlık rengi pembe

            // ScrollPane ile metin alanını kaydırılabilir yapıyoruz
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400)); // Boyutunu ayarlıyoruz

            JOptionPane.showMessageDialog(null, scrollPane, "Tüm Gönderiler", JOptionPane.INFORMATION_MESSAGE);
        }
    }



    public void showDeliveryRoute() {
        JFrame frame = new JFrame("Teslimat Rotası - Şehirler Ağaç Yapısı");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Ana çerçeve arka plan rengi lacivert
        frame.getContentPane().setBackground(Color.decode("#012a4a")); // Arka planı lacivert yapıyoruz

        CityTreeVisualizationPanel treePanel = new CityTreeVisualizationPanel(cityTreeRoot);

        // ScrollPane'in arka plan rengini beyaz yapalım
        JScrollPane scrollPane = new JScrollPane(treePanel);
        scrollPane.getViewport().setBackground(Color.white); // ScrollPane'in arka planı beyaz

        // Çerçeve ve panelin boyutlandırma
        frame.add(scrollPane);

        frame.pack();
        frame.setSize(1800, 800);

        // Ekranın ortasında açılması için:
        frame.setLocationRelativeTo(null);

        // UIManager renk ayarları
        UIManager.put("OptionPane.background", Color.decode("#012a4a")); // Arka plan rengi lacivert
        UIManager.put("Panel.background", Color.decode("#012a4a")); // Panel arka planı lacivert
        UIManager.put("OptionPane.messageForeground", Color.decode("#FFFFFF")); // Yazı rengi beyaz
        UIManager.put("Button.background", Color.decode("#ffe5ec"));  // OK ve Cancel butonlarının arka plan rengi (pembe)
        UIManager.put("Button.foreground", Color.decode("#012a4a"));  // Buton yazı rengi (lacivert)

        // Çerçevenin görsel stilini iyileştirelim
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.decode("#012a4a"), 3)); // Çerçeve etrafına lacivert kenarlık ekleyelim

        // İçerik panelinin yazı rengini beyaz yapalım
        JLabel label = new JLabel("Teslimat Rotası - Şehirler Ağaç Yapısı");
        label.setForeground(Color.WHITE); // Yazı rengini beyaz yapalım
        frame.add(label, BorderLayout.NORTH);

        // Pencereyi görünür kıl
        frame.setVisible(true);
    }




    public void checkShipmentStatus() {
        // Kullanıcının oluşturduğu gönderilerden seçim yapabileceği bir pencere
        JFrame dialog = new JFrame("Kargo Durumu Sorgula");
        dialog.setSize(500, 300);

        // Pencereyi ekranın ortasına yerleştirin
        dialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.decode("#012a4a")); // Panel arka plan rengi lacivert

        // Ortalanmış bir JLabel oluşturun
        JLabel titleLabel = new JLabel("Kargonuzu Seçin");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Yazıyı yatayda ortalar
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT); // BoxLayout için ortalar
        titleLabel.setForeground(Color.WHITE); // Başlık rengi beyaz

        // Kullanıcının tüm kargolarını listelemek için bir JComboBox
        JComboBox<String> shipmentSelector = new JComboBox<>();
        Map<String, Shipment> shipmentMap = new HashMap<>();

        // Kullanıcıya ait tüm kargoları ekle
        for (Customer customer : customers) {
            for (Shipment shipment : customer.getShipmentHistory()) {
                // Müşteri adı, Kargo ID ve Şehir bilgilerini ekle
                String display = "Müşteri: " + customer.getName() + ", ID: " + shipment.getShipmentID() + ", Şehir: " + shipment.getCity();
                shipmentSelector.addItem(display);
                shipmentMap.put(display, shipment);
            }
        }

        // Butonlar
        JButton checkButton = new JButton("Durumu Kontrol Et");
        JButton sortUndeliveredButton = new JButton("Teslim Edilmemiş Kargoları Sırala");
        JButton searchDeliveredButton = new JButton("Teslim Edilmiş Kargoları Ara");

        // ComboBox ve Buton renkleri
        shipmentSelector.setBackground(Color.decode("#ffe5ec")); // ComboBox arka planı pembe
        shipmentSelector.setForeground(Color.decode("#012a4a")); // ComboBox yazı rengi lacivert

        checkButton.setBackground(Color.decode("#ffe5ec")); // Buton arka planı pembe
        checkButton.setForeground(Color.decode("#012a4a")); // Buton yazı rengi lacivert
        sortUndeliveredButton.setBackground(Color.decode("#ffe5ec"));
        sortUndeliveredButton.setForeground(Color.decode("#012a4a"));
        searchDeliveredButton.setBackground(Color.decode("#ffe5ec"));
        searchDeliveredButton.setForeground(Color.decode("#012a4a"));

        // Panel'e bileşenleri ekle
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10)); // Boşluk ekler
        panel.add(shipmentSelector);
        panel.add(Box.createVerticalStrut(10)); // Boşluk ekler
        panel.add(checkButton);
        panel.add(sortUndeliveredButton);
        panel.add(searchDeliveredButton);

        dialog.add(panel);
        dialog.setVisible(true);

        // Durumu kontrol et butonu
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

        // Teslim edilmemiş kargoları sıralama butonu
        sortUndeliveredButton.addActionListener(e -> sortUndeliveredShipments());

        // Teslim edilmiş kargoları arama butonu
        searchDeliveredButton.addActionListener(e -> {
            // Kullanıcıdan kargo ID'sini al
            String input = JOptionPane.showInputDialog("Aramak istediğiniz kargo ID'sini girin:");

            // Cancel butonuna basıldığında input null olur
            if (input == null) {
                return; // Hiçbir işlem yapma ve fonksiyonu sonlandır
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

                // Kargoları ID'ye göre sıralayın
                deliveredShipments.sort(Comparator.comparingInt(Shipment::getShipmentID));

                // Binary Search ile arama yapın
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
            GUI gui =new GUI();
            app.initializeCityTree();
            gui.showGUI();

        });
    }
}