import java.util.LinkedList;
import java.util.Stack;

class Customer {
    private int customerID;
    private String name;
    private LinkedList<Shipment> shipmentHistory;
    private Stack<Shipment> shipmentStack;

    public Customer(int customerID, String name) {
        this.customerID = customerID;
        this.name = name;
        this.shipmentHistory = new LinkedList<>();
        this.shipmentStack = new Stack<>();
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

    public Stack<Shipment> getShipmentStack() {
        return shipmentStack;
    }

    public void addShipment(Shipment shipment) {
        int index = 0;
        while (index < shipmentHistory.size() && shipmentHistory.get(index).getDate().compareTo(shipment.getDate()) < 0) {
            index++;
        }
        shipmentHistory.add(index, shipment);
        shipmentStack.push(shipment);
        if (shipmentStack.size() > 5) {
            shipmentStack.remove(0);
        }
    }
}
