import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

class Shipment implements Comparable<Shipment> {
    private int shipmentID;
    private String date;
    String deliveryStatus;
    private int deliveryTime;
    private String city;
    private Customer customer;

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

    @Override
    public String toString() {
        return "Kargo ID: " + shipmentID + ", Tarih: " + date + ", Durum: " + deliveryStatus
                + ", Teslimat Süresi: " + deliveryTime + " gün, Teslimat Şehri: " + city + ", Müşteri: " + customer.getName();
    }

    // compareTo metodu, kargoları teslim süresine göre sıralar
    @Override
    public int compareTo(Shipment other) {
        return Integer.compare(this.deliveryTime, other.deliveryTime);
    }
}