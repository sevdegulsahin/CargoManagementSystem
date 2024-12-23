# Skydoves: Drone-Delivered Cargo Tracking System

## Overview

**Skydoves** is a desktop application developed in Java, designed to streamline customer and cargo management for drone-based delivery services. This project employs various data structures to efficiently handle customer records, prioritize shipments, and manage delivery routes.

The application features an intuitive interface, allowing users to perform key actions such as adding customers, tracking shipments, and visualizing delivery routes. It provides a comprehensive toolset for managing and tracking cargo in real-time, enhancing operational efficiency.



## Features

1. **Add New Customers**  
   Add customer information dynamically using a user-friendly form interface. Each customer is assigned a unique ID automatically.

2. **Add New Shipments**  
   Record shipment details, including recipient and delivery address. Delivery time and unique shipment IDs are generated upon submission.

3. **View All Customers**  
   List all customers registered in the system along with their respective IDs.

4. **View Customer History**  
   Access the shipment history of a selected customer.

5. **Update Shipment Status**  
   Modify the status of a shipment (e.g., "Delivered," "In Transit") with ease.

6. **Query Shipment Status**  
   Check the current status of a specific shipment and view pending and delivered cargo.

7. **Search Shipments by ID**  
   Quickly retrieve shipment details using the shipment ID.

8. **Delete Shipments**  
   Remove shipment records from the system with a single click.

9. **View All Shipments**  
   Display all registered shipments, including delivery status, recipient names, and shipment numbers.

10. **Sort Shipments by Delivery Time**  
    Organize shipments based on their delivery deadlines.

11. **Visualize Delivery Routes**  
    View delivery routes using a hierarchical tree diagram, which includes the number of shipments per city.

12. **Print Delivery Routes**  
    Generate a printable format of shipment delivery routes.



## Data Structures Used

- **Linked List**: For dynamic management of customer data.
- **Priority Queue**: To prioritize shipments based on delivery deadlines.
- **Tree Structure**: To represent hierarchical delivery routes between cities.
- **Stack**: For quick access to recent shipment history.
- **Sorting and Searching Algorithms**: Binary Search for delivered shipments, Merge Sort for pending shipments.



## Technical Details

### Performance

The application has been tested with various data sizes to ensure optimal performance. Algorithms used include:

- **Merge Sort** for undelivered shipments: O(n log n) complexity.
- **Priority Queue** for delivery prioritization: O(log n) complexity.
- **Binary Search** for shipment queries: O(log n) complexity.

### Suggestions for Future Enhancements

- Transitioning from linked lists to hash tables for faster customer data access.
- Using graphs instead of trees for more complex and cyclic delivery routes.
- Incorporating deque for shipment history for more flexibility.



## How to Use

Refer to the detailed [User Guide](./docs/user_guide.md) for step-by-step instructions on how to use each feature effectively.



## Contributors

- **Süeda Nur Sarıcan** 
- **Sevde Gül Şahin**   
- **Fatımanur Kantar**
