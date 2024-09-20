package org.example.secondbindproject;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import au.com.bytecode.opencsv.CSVWriter;

public class HomeController implements Initializable {

    // Buttons
    @FXML
    private Button addNewBtn;
    @FXML
    private Button exportBtn;
    @FXML
    private Button searchBtn;

    // Columns
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> dateCol;
    @FXML
    private TableColumn<Book, String> genreCol;
    @FXML
    private TableColumn<Book, Double> isbnCol;
    @FXML
    private TableColumn<Book, String> titleCol;

    // Text Fields
    @FXML
    private TextField searchField;

    // Choice Boxes
    @FXML
    private ChoiceBox<String> searchFilter;

    // Choices
    private final String[] choices = {"title", "author", "genre", "date", "isbn"};

    // Tables
    @FXML
    private TableView<Book> tableView;

    // List filter variable
    private FilteredList<Book> filteredList;

    // Database Connection Object
    DBConnect conn;

    // Initial setup for home page
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        searchFilter.getItems().addAll(choices);

        conn = new DBConnect();
        List<Book> books = conn.getData();

        // Set the items of the TableView
        tableView.setItems(FXCollections.observableArrayList(books));

        // Set the cell value factories for each column
        authorCol.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        dateCol.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        genreCol.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        isbnCol.setCellValueFactory(cellData -> cellData.getValue().isbnProperty().asObject());
        titleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        filteredList = new FilteredList<>(tableView.getItems(), p -> true);
        tableView.setItems(filteredList);

        searchFilter.getSelectionModel().selectedItemProperty().addListener(this::choiceBoxChanged);
        searchField.textProperty().addListener(this::searchFieldChanged);
    }

    // When 'Add New' button is pressed, this method is use
    // Sends user to book submission page
    @FXML
    private void toSubmission(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("submission-view.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // Method to export CSV file (Export Button)
    // .csv is exported to the project folder (i.e where the pom.xml file is for example)
    @FXML
    private void exportCSV(ActionEvent event) throws IOException {
        try {
            ObservableList<Book> data = tableView.getItems();

            CSVWriter writer = new CSVWriter(new FileWriter("export.csv"));

            String[] header = {"Title", "Author", "Genre", "Date", "ISBN"};
            writer.writeNext(header);

            for (Book book : data) {
                String[] row = {String.valueOf(book.getTitle()), String.valueOf(book.getAuthor()),
                        String.valueOf(book.getGenre()), String.valueOf(book.getDate()), String.valueOf(book.getIsbn())};
                writer.writeNext(row);
            }

            writer.close();

            System.out.println("Data exported to export.csv");
        } catch (IOException ex) {
            System.err.println("Error exporting data: " + ex.getMessage());
        }

    }

    // Helper methods for the search filter function
    private void choiceBoxChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String selectedChoice = newValue.toString();
        String searchText = searchField.getText().trim().toLowerCase();

        Predicate<Book> predicate = getPredicate(selectedChoice, searchText);
        filteredList.setPredicate(predicate);
    }

    private void searchFieldChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String selectedChoice = searchFilter.getSelectionModel().getSelectedItem().toString();
        String searchText = newValue.trim().toLowerCase();

        Predicate<Book> predicate = getPredicate(selectedChoice, searchText);
        filteredList.setPredicate(predicate);
    }

    private Predicate<Book> getPredicate(String selectedChoice, String searchText) {
        switch (selectedChoice) {
            case "title":
                return book -> String.valueOf(book.getTitle()).toLowerCase().contains(searchText);
            case "author":
                return book -> String.valueOf(book.getAuthor()).toLowerCase().contains(searchText);
            case "genre":
                return book -> String.valueOf(book.getGenre()).toLowerCase().contains(searchText);
            case "date":
                return book -> String.valueOf(book.getDate()).toLowerCase().contains(searchText);
            case "isbn":
                return book -> book.getIsbn().toString().contains(searchText);
            default:
                return p -> true;
        }
    }



}
