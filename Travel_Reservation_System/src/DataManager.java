import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String CUSTOMERS_FILE = "customers.json";
    private static final String ITINERARIES_FILE = "itineraries.json";
    private static final String BOOKINGS_FILE = "bookings.json";

    public static void saveCustomers(List<Customer> customers) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(CUSTOMERS_FILE), customers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Customer> loadCustomers() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(CUSTOMERS_FILE);
            if (file.exists()) {
                return mapper.readValue(file, new TypeReference<List<Customer>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void saveItineraries(List<Itinerary> itineraries) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(ITINERARIES_FILE), itineraries);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Itinerary> loadItineraries() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(ITINERARIES_FILE);
            if (file.exists()) {
                return mapper.readValue(file, new TypeReference<List<Itinerary>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void saveBookings(List<Booking> bookings) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(BOOKINGS_FILE), bookings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Booking> loadBookings() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(BOOKINGS_FILE);
            if (file.exists()) {
                return mapper.readValue(file, new TypeReference<List<Booking>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}