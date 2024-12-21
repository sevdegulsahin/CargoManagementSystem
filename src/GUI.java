import Resources.Icons;

import javax.swing.*;
import java.awt.*;

public class GUI {
     CustomerManagementApp customerManagementApp = new CustomerManagementApp ();

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
        addCustomerButton.addActionListener(e -> customerManagementApp.addCustomerDialog());
        addShipmentButton.addActionListener(e -> customerManagementApp.addShipmentDialog());
        viewAllCustomersButton.addActionListener(e -> customerManagementApp.viewAllCustomers());
        viewCustomerHistoryButton.addActionListener(e -> customerManagementApp.viewCustomerHistory());
        updateShipmentStatusButton.addActionListener(e -> customerManagementApp.updateShipmentStatus());
        checkShipmentStatusButton.addActionListener(e -> customerManagementApp.checkShipmentStatus());
        searchShipmentButton.addActionListener(e -> customerManagementApp.searchShipment());
        deleteShipmentButton.addActionListener(e -> customerManagementApp.deleteShipment());
        viewShipmentStackButton.addActionListener(e -> customerManagementApp.listAllShipments());
        sortShipmentsButton.addActionListener(e -> customerManagementApp.sortShipmentsByDeliveryTime());
        showDeliveryRouteButton.addActionListener(e -> customerManagementApp.showDeliveryRoute());

        printRoutesFromShipmentsButton.addActionListener(e -> customerManagementApp.printRoutesFromShipments());

        // Frame'e panel ekleme
        frame.add(panel);
        frame.setVisible(true);
    }
}
