import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

class Shipment implements Comparable<Shipment> {
    private int shipmentID; // Kargo ID'si
    private String date; // Gönderim tarihi, "yyyy-MM-dd" formatında
    String deliveryStatus; // Teslimat durumu (örneğin: "Teslim Edildi" veya "Teslim Edilmedi")
    private int deliveryTime; // Teslimat süresi (gün olarak tahmin edilen süre)
    private String city; // Gönderim şehri
    private Customer customer; // Kargonun sahibi olan müşteri

    // Constructor
    public Shipment(int shipmentID, String date, String deliveryStatus, int deliveryTime, String city, Customer customer) {
        this.shipmentID = shipmentID;
        this.date = date;
        this.deliveryStatus = deliveryStatus;
        this.deliveryTime = deliveryTime;
        this.city = city;
        this.customer = customer;
    }

    // Gönderim tarihinden itibaren geçen gün sayısını hesaplar
    public int getPassedDays() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate shipmentDate = LocalDate.parse(date, formatter); // Tarihi parse eder
        LocalDate currentDate = LocalDate.now(); // Bugünün tarihini alır
        return (int) ChronoUnit.DAYS.between(shipmentDate, currentDate); // Geçen günleri hesaplar
    }

    // Teslim edilmesine kalan gün sayısını hesaplar
    public int getRemainingDays() {
        int passedDays = getPassedDays(); // Geçen gün sayısını alır
        return deliveryTime - passedDays; // Tahmini teslim süresinden geçen günleri çıkarır
    }

    // Getter ve Setter'lar
    public int getShipmentID() {
        return shipmentID;
    }

    public String getDate() {
        return date;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public String getCity() {
        return city;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    // toString metodu, kargonun bilgilerini okunabilir formatta döner
    @Override
    public String toString() {
        return "Shipment ID: " + shipmentID + ", Date: " + date + ", Status: " + deliveryStatus
                + ", Delivery Time: " + deliveryTime + " days, City: " + city + ", Customer: " + customer.getName();
    }

    // compareTo metodu, kargoları teslim süresine göre sıralar
    @Override
    public int compareTo(Shipment other) {
        return Integer.compare(this.deliveryTime, other.deliveryTime);
    }
}
