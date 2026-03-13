# Travel Booking System

A **Travel Booking System** application designed to simplify the management of **customers, itineraries, and bookings** for travel agencies.  
The system automates manual processes and provides a simple interface for managing travel reservations.

---

# Overview

The Travel Booking System helps travel agency employees organize travel data efficiently.  
It allows users to **create, manage, and delete customers, itineraries, and bookings** while ensuring **data integrity and seat availability**.

All application data is stored in **JSON files**, making the system lightweight and easy to maintain.

---

# Features

## Customer Management
- Add new customers
- Delete customers
- View customer information
- Store customer details:
  - Name
  - Phone number
  - Email

## Itinerary Management
- Add itineraries
- Delete itineraries
- View itinerary information
- Store itinerary details:
  - Destination
  - Date
  - Available seats

## Booking Management
- Create bookings linking customers with itineraries
- Delete bookings
- View existing bookings
- Automatic seat availability checking

## Data Storage
- All data is stored in **JSON files**
- Ensures persistence and easy data retrieval

---

# User Guide

## 1. Add Customer

1. Click **Add Customer**
2. Enter the following information:
   - Name
   - Phone number
   - Email
3. Click **OK**
4. The customer will appear in the customer table.

---

## 2. Add Itinerary

1. Click **Add Itinerary**
2. Enter:
   - Destination
   - Date
   - Available seats
3. Click **OK**
4. The itinerary will appear in the itinerary table.

---

## 3. Create Booking

1. Click **Create Booking**
2. Select:
   - A customer
   - An itinerary
3. Enter the booking date
4. Click **OK**

The booking will be created **only if seats are available**.

---

## 4. Delete Data

1. Select a customer, itinerary, or booking
2. Click **Delete**
3. Confirm the deletion

> ⚠️ Deletion is **not allowed** if dependencies exist (for example, a customer with an active booking).

---

# System Functionalities

The system provides the following core functionalities:

- **Data Creation**  
  Customers and itineraries are created through dialog windows.

- **Real-Time Data Display**  
  Tables automatically update after every action.

- **Seat Availability Control**  
  The system checks if seats are available before creating a booking.

- **Safe Deletion**  
  Dependency checks prevent inconsistent data.

- **JSON Data Storage**  
  All information is stored in JSON files for easy access and reuse.

---

# Algorithmic Solutions

## 1. Friendly ComboBox Display

**Problem:**  
ComboBoxes displayed raw object references instead of readable information.

**Solution:**  
The `toString()` method was overridden in the `Customer` and `Itinerary` classes.

Example output:   
Name: John Doe - Phone: 123456789   

---

## 2. Dependency Check Before Deletion

**Problem:**  
Deleting customers or itineraries with active bookings would cause inconsistencies.

**Solution:**  
Java Streams were used to check dependencies.

Example method:   
stream().anyMatch()   

If a booking exists, the deletion is blocked.

---

## 3. Seat Availability Management

**Problem:**  
Prevent overbooking of itineraries.

**Solution:**  
Before confirming a booking, the system compares:

- Existing bookings for the itinerary
- Available seats

If no seats are available, the booking is rejected.

---

# UML Diagrams

## Class Diagram

The class diagram shows the relationship between the main classes:

- `Customer`
- `Itinerary`
- `Booking`

A **Customer** can have multiple **Bookings**, each connected to an **Itinerary**.

---
<img width="975" height="772" alt="image" src="https://github.com/user-attachments/assets/14c94b36-a684-4a3e-84a6-d4a38f772b61" />

---

## Sequence Diagram

The sequence diagram illustrates the booking creation process:

1. User selects a customer
2. User selects an itinerary
3. System checks seat availability
4. Booking is created and stored

---

<img width="974" height="839" alt="image" src="https://github.com/user-attachments/assets/5904b426-29f4-4356-90f0-21bed7ef294b" />

---

# Conclusion

The **Travel Booking System** provides a simple and reliable solution for managing travel reservations.

The system:

- Automates booking management
- Ensures data consistency
- Prevents overbooking
- Stores data using JSON
- Uses UML diagrams to document the system design
