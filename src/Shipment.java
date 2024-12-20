
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

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
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

    @Override
    public String toString() {
        return "Shipment ID: " + shipmentID + ", Date: " + date + ", Status: " + deliveryStatus + ", Delivery Time: " + deliveryTime + " days, City: " + city + ", Customer: " + customer.getName();
    }

    @Override
    public int compareTo(Shipment other) {
        return Integer.compare(this.deliveryTime, other.deliveryTime);
    }
}