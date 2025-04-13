public class Itinerary {
    private String destination;
    private String date;
    private int availableSeats;

    public Itinerary() {} // Απαιτείται για το Jackson

    public Itinerary(String destination, String date, int availableSeats) {
        this.destination = destination;
        this.date = date;
        this.availableSeats = availableSeats;
    }

    // Getters και Setters
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
@Override
    public String toString() {
        return destination + " - " + date; // Εμφανίζει προορισμό και ημερομηνία
    }
}