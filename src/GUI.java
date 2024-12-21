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
        panel.add(Box.createVerticalStrut(30)); // Görsel alt boşluk

        // Küçük ikon boyutları
        int buttonIconSize = 50;

        // İkonları yükle ve ölçeklendir
        ImageIcon addCustomerIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon addShipmentIcon = new ImageIcon(Icons.class.getResource("/Resources/kargoekle.jpg"));
        ImageIcon viewAllCustomersIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon viewCustomerHistoryIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon updateShipmentStatusIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon checkShipmentStatusIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon searchShipmentIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon deleteShipmentIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon viewShipmentStackIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon sortShipmentsIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon showDeliveryRouteIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));
        ImageIcon printRoutesFromShipmentsIcon = new ImageIcon(Icons.class.getResource("/Resources/musteriekle.jpg"));

        // İkonları boyutlandır
        addCustomerIcon = new ImageIcon(addCustomerIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));
        addShipmentIcon = new ImageIcon(addShipmentIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));
        viewAllCustomersIcon = new ImageIcon(viewAllCustomersIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));
        viewCustomerHistoryIcon = new ImageIcon(viewCustomerHistoryIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));
        updateShipmentStatusIcon = new ImageIcon(updateShipmentStatusIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));
        checkShipmentStatusIcon = new ImageIcon(checkShipmentStatusIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));
        searchShipmentIcon = new ImageIcon(searchShipmentIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));
        deleteShipmentIcon = new ImageIcon(deleteShipmentIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));
        viewShipmentStackIcon = new ImageIcon(viewShipmentStackIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));
        sortShipmentsIcon = new ImageIcon(sortShipmentsIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));
        showDeliveryRouteIcon = new ImageIcon(showDeliveryRouteIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));
        printRoutesFromShipmentsIcon = new ImageIcon(printRoutesFromShipmentsIcon.getImage().getScaledInstance(buttonIconSize, buttonIconSize, Image.SCALE_SMOOTH));

        // Butonlar için boyutlar
        int buttonWidth = buttonIconSize; // Buton genişliği
        int buttonHeight = buttonIconSize + 20; // Buton yüksekliği (ikon + yazı)

        // Butonlar için ortak boyut ayarı
        Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);

        // Butonları oluşturma ve düzenleme
        JButton addCustomerButton = new JButton(addCustomerIcon);
        addCustomerButton.setText("Müşteri Ekle");
        addCustomerButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addCustomerButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        addCustomerButton.setBackground(Color.decode("#ffe5ec"));
        addCustomerButton.setForeground(Color.decode("#043565"));
        addCustomerButton.setPreferredSize(buttonSize);


        JButton addShipmentButton = new JButton(addShipmentIcon);
        addShipmentButton.setText("Gönderi Ekle");
        addShipmentButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addShipmentButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        addShipmentButton.setBackground(Color.decode("#ffe5ec"));
        addShipmentButton.setForeground(Color.decode("#043565"));
        addShipmentButton.setPreferredSize(buttonSize);

        JButton viewAllCustomersButton = new JButton(viewAllCustomersIcon);
        viewAllCustomersButton.setText("Tüm Müşterileri Gör");
        viewAllCustomersButton.setHorizontalTextPosition(SwingConstants.CENTER);
        viewAllCustomersButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        viewAllCustomersButton.setBackground(Color.decode("#ffe5ec"));
        viewAllCustomersButton.setForeground(Color.decode("#043565"));
        viewAllCustomersButton.setPreferredSize(buttonSize);

        JButton viewCustomerHistoryButton = new JButton(viewCustomerHistoryIcon);
        viewCustomerHistoryButton.setText("Müşteri Geçmişini Gör");
        viewCustomerHistoryButton.setHorizontalTextPosition(SwingConstants.CENTER);
        viewCustomerHistoryButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        viewCustomerHistoryButton.setBackground(Color.decode("#ffe5ec"));
        viewCustomerHistoryButton.setForeground(Color.decode("#043565"));
        viewCustomerHistoryButton.setPreferredSize(buttonSize);

        JButton updateShipmentStatusButton = new JButton(updateShipmentStatusIcon);
        updateShipmentStatusButton.setText("Gönderi Durumunu Güncelle");
        updateShipmentStatusButton.setHorizontalTextPosition(SwingConstants.CENTER);
        updateShipmentStatusButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        updateShipmentStatusButton.setBackground(Color.decode("#ffe5ec"));
        updateShipmentStatusButton.setForeground(Color.decode("#043565"));
        updateShipmentStatusButton.setPreferredSize(buttonSize);

        JButton checkShipmentStatusButton = new JButton(checkShipmentStatusIcon);
        checkShipmentStatusButton.setText("Kargo Durumu Sorgula");
        checkShipmentStatusButton.setHorizontalTextPosition(SwingConstants.CENTER);
        checkShipmentStatusButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        checkShipmentStatusButton.setBackground(Color.decode("#ffe5ec"));
        checkShipmentStatusButton.setForeground(Color.decode("#043565"));
        checkShipmentStatusButton.setPreferredSize(buttonSize);

        JButton searchShipmentButton = new JButton(searchShipmentIcon);
        searchShipmentButton.setText("Gönderi Ara");
        searchShipmentButton.setHorizontalTextPosition(SwingConstants.CENTER);
        searchShipmentButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        searchShipmentButton.setBackground(Color.decode("#ffe5ec"));
        searchShipmentButton.setForeground(Color.decode("#043565"));
        searchShipmentButton.setPreferredSize(buttonSize);

        JButton deleteShipmentButton = new JButton(deleteShipmentIcon);
        deleteShipmentButton.setText("Gönderi Sil");
        deleteShipmentButton.setHorizontalTextPosition(SwingConstants.CENTER);
        deleteShipmentButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        deleteShipmentButton.setBackground(Color.decode("#ffe5ec"));
        deleteShipmentButton.setForeground(Color.decode("#043565"));
        deleteShipmentButton.setPreferredSize(buttonSize);

        JButton viewShipmentStackButton = new JButton(viewShipmentStackIcon);
        viewShipmentStackButton.setText("Tüm Gönderileri Listele");
        viewShipmentStackButton.setHorizontalTextPosition(SwingConstants.CENTER);
        viewShipmentStackButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        viewShipmentStackButton.setBackground(Color.decode("#ffe5ec"));
        viewShipmentStackButton.setForeground(Color.decode("#043565"));
        viewShipmentStackButton.setPreferredSize(buttonSize);

        JButton sortShipmentsButton = new JButton(sortShipmentsIcon);
        sortShipmentsButton.setText("Gönderileri Listele");
        sortShipmentsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        sortShipmentsButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        sortShipmentsButton.setBackground(Color.decode("#ffe5ec"));
        sortShipmentsButton.setForeground(Color.decode("#043565"));
        sortShipmentsButton.setPreferredSize(buttonSize);

        JButton showDeliveryRouteButton = new JButton(showDeliveryRouteIcon);
        showDeliveryRouteButton.setText("Teslimat Rotasını Göster");
        showDeliveryRouteButton.setHorizontalTextPosition(SwingConstants.CENTER);
        showDeliveryRouteButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        showDeliveryRouteButton.setBackground(Color.decode("#ffe5ec"));
        showDeliveryRouteButton.setForeground(Color.decode("#043565"));
        showDeliveryRouteButton.setPreferredSize(buttonSize);

        JButton printRoutesFromShipmentsButton = new JButton(printRoutesFromShipmentsIcon);
        printRoutesFromShipmentsButton.setText("Gönderi Rotasını Yazdır");
        printRoutesFromShipmentsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        printRoutesFromShipmentsButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        printRoutesFromShipmentsButton.setBackground(Color.decode("#ffe5ec"));
        printRoutesFromShipmentsButton.setForeground(Color.decode("#043565"));
        printRoutesFromShipmentsButton.setPreferredSize(buttonSize);

        // Butonlar için paneller oluşturma
        JPanel row1 = new JPanel();
        row1.setLayout(new GridLayout(1, 3, 0, 30)); // 3 sütun, 10px aralık
        row1.add(addCustomerButton);
        row1.add(addShipmentButton);
        row1.add(viewAllCustomersButton);

        JPanel row2 = new JPanel();
        row2.setLayout(new GridLayout(1, 3, 0, 30)); // 3 sütun, 10px aralık
        row2.add(viewCustomerHistoryButton);
        row2.add(updateShipmentStatusButton);
        row2.add(checkShipmentStatusButton);

        JPanel row3 = new JPanel();
        row3.setLayout(new GridLayout(1, 3, 0, 30)); // 3 sütun, 10px aralık
        row3.add(searchShipmentButton);
        row3.add(deleteShipmentButton);
        row3.add(viewShipmentStackButton);

        JPanel row4 = new JPanel();
        row4.setLayout(new GridLayout(1, 3, 0, 30)); // 3 sütun, 10px aralık
        row4.add(sortShipmentsButton);
        row4.add(showDeliveryRouteButton);
        row4.add(printRoutesFromShipmentsButton);

        // Panelleri ana panele ekleme
        panel.add(row1);
        panel.add(row2);
        panel.add(row3);
        panel.add(row4);

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
