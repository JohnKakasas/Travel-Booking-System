import java.time.LocalDate;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.layout.HBox;

/**
 * Κλάση που υλοποιεί το σύστημα ταξιδιωτικών κρατήσεων με γραφικό περιβάλλον.
 * Διαχειρίζεται πελάτες, δρομολόγια και κρατήσεις, αποθηκεύοντας δεδομένα σε JSON.
 */
public class TravelBookingSystem extends Application {

    private List<Customer> customers = new ArrayList<>();
    private List<Itinerary> itineraries = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private TableView<Customer> customerTable = new TableView<>();
    private TableView<Itinerary> itineraryTable = new TableView<>();
    private TableView<Booking> bookingTable = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Σύστημα Ταξιδιωτικών Κρατήσεων");

        // Φόρτωση δεδομένων από JSON αρχεία
        customers = DataManager.loadCustomers();
        itineraries = DataManager.loadItineraries();
        bookings = DataManager.loadBookings();

        // Δημιουργία UI στοιχείων
        Label mainLabel = new Label("Καλώς ήρθατε στο Σύστημα Ταξιδιωτικών Κρατήσεων");
        mainLabel.setId("main-label");
        Button addCustomerButton = new Button("Προσθήκη Πελάτη");
        Button addItineraryButton = new Button("Προσθήκη Δρομολογίου");
        Button makeBookingButton = new Button("Δημιουργία Κράτησης");
        Button deleteCustomerButton = new Button("Διαγραφή Πελάτη");
        Button deleteItineraryButton = new Button("Διαγραφή Δρομολογίου");
        Button deleteBookingButton = new Button("Διαγραφή Κράτησης");

        addCustomerButton.getStyleClass().add("button");
        deleteCustomerButton.getStyleClass().add("delete-button");
        addItineraryButton.getStyleClass().add("button");
        deleteItineraryButton.getStyleClass().add("delete-button");
        makeBookingButton.getStyleClass().add("button");
        deleteBookingButton.getStyleClass().add("delete-button");

        // Πίνακας πελατών
        Label customerLabel = new Label("Πελάτες");
        customerLabel.setId("title-label");
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Όνομα");
        nameColumn.setPrefWidth(100);
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        customerTable.getColumns().addAll(nameColumn, emailColumn);
        customerTable.setItems(FXCollections.observableArrayList(customers));

        // Πίνακας δρομολογίων
        Label itineraryLabel = new Label("Δρομολόγια");
        itineraryLabel.setId("title-label");
        TableColumn<Itinerary, String> destinationColumn = new TableColumn<>("Προορισμός");
        destinationColumn.setPrefWidth(150);
        destinationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestination()));
        TableColumn<Itinerary, String> dateColumn = new TableColumn<>("Ημερομηνία");
        dateColumn.setPrefWidth(150);
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
        TableColumn<Itinerary, Integer> seatsColumn = new TableColumn<>("Διαθέσιμες Θέσεις");
        seatsColumn.setPrefWidth(180);
        seatsColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAvailableSeats()).asObject());
        itineraryTable.getColumns().addAll(destinationColumn, dateColumn, seatsColumn);
        itineraryTable.setItems(FXCollections.observableArrayList(itineraries));

        // Πίνακας κρατήσεων
        Label bookingLabel = new Label("Κρατήσεις");
        bookingLabel.setId("title-label");
        TableColumn<Booking, String> customerColumn = new TableColumn<>("Πελάτης");
        customerColumn.setPrefWidth(100);
        customerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
        TableColumn<Booking, String> itineraryColumn = new TableColumn<>("Δρομολόγιο");
        itineraryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItinerary().getDestination() + " - " + cellData.getValue().getItinerary().getDate()));
        TableColumn<Booking, String> bookingDateColumn = new TableColumn<>("Ημερομηνία Κράτησης");
        bookingDateColumn.setPrefWidth(250);
        bookingDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookingDate()));
        bookingTable.getColumns().addAll(customerColumn, itineraryColumn, bookingDateColumn);
        bookingTable.setItems(FXCollections.observableArrayList(bookings));

        customerTable.getStyleClass().add("table-view");
        itineraryTable.getStyleClass().add("table-view");
        bookingTable.getStyleClass().add("table-view");

        // Σύνθεση του layout
        HBox customerButtons = new HBox(10, addCustomerButton, deleteCustomerButton);
        HBox itineraryButtons = new HBox(10, addItineraryButton, deleteItineraryButton);
        HBox bookingButtons = new HBox(10, makeBookingButton, deleteBookingButton);
        VBox vbox = new VBox(20, 
            mainLabel,  
            customerLabel, customerButtons, customerTable, 
            itineraryLabel, itineraryButtons, itineraryTable, 
            bookingLabel, bookingButtons, bookingTable
        );

        Scene scene = new Scene(vbox, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        // Ορισμός event handlers για τα κουμπιά
        addCustomerButton.setOnAction(e -> addCustomer());
        addItineraryButton.setOnAction(e -> addItinerary());
        makeBookingButton.setOnAction(e -> makeBooking());
        deleteCustomerButton.setOnAction(e -> deleteCustomer());
        deleteItineraryButton.setOnAction(e -> deleteItinerary());
        deleteBookingButton.setOnAction(e -> deleteBooking());
    }

    /**
     * Προσθέτει έναν νέο πελάτη μέσω διαλόγου.
     */
    private void addCustomer() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Προσθήκη Πελάτη");
        dialog.setHeaderText("Εισαγωγή στοιχείων πελάτη");
        dialog.setContentText("Όνομα:");
        String name = dialog.showAndWait().orElse("");
        dialog.setContentText("Email:");
        String email = dialog.showAndWait().orElse("");
        if (!name.isEmpty() && !email.isEmpty()) {
            Customer customer = new Customer(name, email);
            customers.add(customer);
            customerTable.setItems(FXCollections.observableArrayList(customers));
            DataManager.saveCustomers(customers);
        }
    }

    /**
     * Διαγράφει τον επιλεγμένο πελάτη, ελέγχοντας πρώτα αν έχει κρατήσεις.
     */
    private void deleteCustomer() {
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            // Έλεγχος για υπάρχουσες κρατήσεις
            boolean hasBookings = bookings.stream().anyMatch(b -> b.getCustomer().equals(selectedCustomer));
            if (hasBookings) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Ο πελάτης έχει κρατήσεις. Διαγράψτε πρώτα τις κρατήσεις.");
                alert.showAndWait();
                return;
            }
            customers.remove(selectedCustomer);
            DataManager.saveCustomers(customers);
            customerTable.setItems(FXCollections.observableArrayList(customers));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Παρακαλώ επιλέξτε έναν πελάτη για διαγραφή.");
            alert.showAndWait();
        }
    }

    /**
     * Προσθέτει ένα νέο δρομολόγιο μέσω διαλόγου με DatePicker για την ημερομηνία.
     */
    private void addItinerary() {
        DialogPane dialogPane = new DialogPane();
        TextField destinationField = new TextField();
        DatePicker datePicker = new DatePicker();
        TextField seatsField = new TextField();

        dialogPane.setContent(new VBox(10,
            new Label("Προορισμός:"), destinationField,
            new Label("Ημερομηνία:"), datePicker,
            new Label("Διαθέσιμες Θέσεις:"), seatsField
        ));
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Προσθήκη Δρομολογίου");
        dialog.setHeaderText("Εισαγωγή στοιχείων δρομολογίου");
        dialog.setDialogPane(dialogPane);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String destination = destinationField.getText();
            LocalDate selectedDate = datePicker.getValue();
            String seats = seatsField.getText();

            if (!destination.isEmpty() && selectedDate != null && !seats.isEmpty()) {
                String date = selectedDate.toString();
                Itinerary itinerary = new Itinerary(destination, date, Integer.parseInt(seats));
                itineraries.add(itinerary);
                itineraryTable.setItems(FXCollections.observableArrayList(itineraries));
                DataManager.saveItineraries(itineraries);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Παρακαλώ συμπληρώστε όλα τα πεδία!");
                alert.showAndWait();
            }
        }
    }

    /**
     * Διαγράφει το επιλεγμένο δρομολόγιο, ελέγχοντας πρώτα αν έχει κρατήσεις.
     */
    private void deleteItinerary() {
        Itinerary selectedItinerary = itineraryTable.getSelectionModel().getSelectedItem();
        if (selectedItinerary != null) {
            // Έλεγχος για υπάρχουσες κρατήσεις
            boolean hasBookings = bookings.stream().anyMatch(b -> b.getItinerary().equals(selectedItinerary));
            if (hasBookings) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Το δρομολόγιο έχει κρατήσεις. Διαγράψτε πρώτα τις κρατήσεις.");
                alert.showAndWait();
                return;
            }
            itineraries.remove(selectedItinerary);
            DataManager.saveItineraries(itineraries);
            itineraryTable.setItems(FXCollections.observableArrayList(itineraries));
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Παρακαλώ επιλέξτε ένα δρομολόγιο για διαγραφή.");
            alert.showAndWait();
        }
    }

    /**
     * Δημιουργεί μια νέα κράτηση, ελέγχοντας τη διαθεσιμότητα θέσεων.
     */
    private void makeBooking() {
        if (customers.isEmpty() || itineraries.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Πρέπει να υπάρχουν πελάτες και δρομολόγια!");
            alert.showAndWait();
            return;
        }

        ComboBox<Customer> customerComboBox = new ComboBox<>(FXCollections.observableArrayList(customers));
        ComboBox<Itinerary> itineraryComboBox = new ComboBox<>(FXCollections.observableArrayList(itineraries));
        DatePicker datePicker = new DatePicker();

        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(new VBox(10,
            new Label("Επιλέξτε Πελάτη:"), customerComboBox,
            new Label("Επιλέξτε Δρομολόγιο:"), itineraryComboBox,
            new Label("Επιλέξτε Ημερομηνία Κράτησης:"), datePicker
        ));
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Δημιουργία Κράτησης");

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Customer selectedCustomer = customerComboBox.getValue();
            Itinerary selectedItinerary = itineraryComboBox.getValue();
            LocalDate selectedDate = datePicker.getValue();

            if (selectedCustomer == null || selectedItinerary == null || selectedDate == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Παρακαλώ συμπληρώστε όλα τα πεδία!");
                alert.showAndWait();
                return;
            }

            // Έλεγχος διαθεσιμότητας θέσεων
            if (selectedItinerary.getAvailableSeats() <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Δεν υπάρχουν διαθέσιμες θέσεις για αυτό το δρομολόγιο!");
                alert.showAndWait();
                return;
            }

            String bookingDate = selectedDate.toString();
            Booking booking = new Booking(selectedCustomer, selectedItinerary, bookingDate);
            bookings.add(booking);
            DataManager.saveBookings(bookings);

            selectedItinerary.setAvailableSeats(selectedItinerary.getAvailableSeats() - 1);
            DataManager.saveItineraries(itineraries);

            itineraryTable.refresh();
            bookingTable.setItems(FXCollections.observableArrayList(bookings));

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Η κράτηση δημιουργήθηκε επιτυχώς!");
            alert.showAndWait();
        }
    }

    /**
     * Διαγράφει την επιλεγμένη κράτηση και επαναφέρει τη θέση στο δρομολόγιο.
     */
    private void deleteBooking() {
        Booking selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            bookings.remove(selectedBooking);
            DataManager.saveBookings(bookings);
            bookingTable.setItems(FXCollections.observableArrayList(bookings));
            Itinerary itinerary = selectedBooking.getItinerary();
            itinerary.setAvailableSeats(itinerary.getAvailableSeats() + 1);
            DataManager.saveItineraries(itineraries);
            itineraryTable.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Παρακαλώ επιλέξτε μια κράτηση για διαγραφή.");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}