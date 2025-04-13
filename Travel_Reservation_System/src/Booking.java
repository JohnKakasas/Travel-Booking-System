public class Booking {
    private Customer customer;
    private Itinerary itinerary;
    private String bookingDate;

    public Booking() {} // Απαιτείται για το Jackson

    public Booking(Customer customer, Itinerary itinerary, String bookingDate) {
        this.customer = customer;
        this.itinerary = itinerary;
        this.bookingDate = bookingDate;
    }

    // Getters και Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
}