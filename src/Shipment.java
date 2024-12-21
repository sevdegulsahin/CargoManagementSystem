import java.util.ArrayList;
import java.util.List;

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
    private String getRoute(TreeNode root, String targetCity) {
        List<String> path = new ArrayList<>();
        if (findPath(root, targetCity, path)) {
            return String.join(" -> ", path); // Rotayı "İstanbul -> Ankara -> İzmir" formatında döndür
        }
        return "Rota bulunamadı";
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

    @Override
    public String toString() {
        return "Shipment ID: " + shipmentID + ", Date: " + date + ", Status: " + deliveryStatus + ", Delivery Time: " + deliveryTime + " days, City: " + city + ", Customer: " + customer.getName();
    }

    @Override
    public int compareTo(Shipment other) {
        return Integer.compare(this.deliveryTime, other.deliveryTime);
    }
}