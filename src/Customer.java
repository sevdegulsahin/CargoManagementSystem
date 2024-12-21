import java.util.LinkedList;
import java.util.Stack;

class Customer {
    private int customerID;
    private String name;
    private LinkedList<Shipment> shipmentHistory;
    private LinkedList<Shipment> shipmentLinkedList;

    public Customer(int customerID, String name) {
        this.customerID = customerID;
        this.name = name;
        this.shipmentHistory = new LinkedList<>();
        this.shipmentLinkedList = new LinkedList<>();
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Shipment> getShipmentHistory() {
        return shipmentHistory;
    }

    public LinkedList<Shipment> getShipmentStack() {
        return shipmentLinkedList;
    }

    public void addShipment(Shipment shipment, CustomerManagementApp app) {
        // Gönderiyi tarih sırasına göre gönderi geçmişine ekliyoruz
        int index = 0;
        while (index < shipmentHistory.size() && shipmentHistory.get(index).getDate().compareTo(shipment.getDate()) < 0) {
            index++;
        }
        shipmentHistory.add(index, shipment); // Tarihe göre ekleme

        // Gönderiyi stack'e ekliyoruz (LinkedList)
        shipmentLinkedList.add(shipment);

        // Eğer stack'teki gönderi sayısı 5'i geçtiyse, en eski gönderiyi çıkarıyoruz
        if (shipmentLinkedList.size() > 5) {
            shipmentLinkedList.removeFirst(); // İlk öğeyi çıkarıyoruz (FIFO mantığı)
        }

        // Her gönderi eklendiğinde, global stack'e de eklenmesi sağlanıyor
        app.addShipmentToGlobalStack(shipment);
    }
}
