package org.example.secondbindproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Objects;

public class SubmissionController {

    // Text Fields
    @FXML
    private TextField authorField;
    @FXML
    private TextField dateField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField isbnField;
    @FXML
    private TextField titleField;

    // Buttons
    @FXML
    private Button submitBtn;
    @FXML
    private Button backBtn;

    // Labels
    @FXML
    private Label alertLabel;

    // Sends user to home page (Back Button)
    @FXML
    private void toHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-view.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    // Submits book to database
    @FXML
    private void submitBook(ActionEvent event) throws IOException, ParseException {
        DBConnect obj = new DBConnect();

        if (textFieldsEmpty()){
            alertLabel.setText("MUST FILL ALL TEXT FIELDS");
            alertLabel.setVisible(true);
        }else{
            alertLabel.setText("Submitted");
            alertLabel.setTextFill(Color.GREEN);
            alertLabel.setVisible(true);


            String title = titleField.getText();
            String author = authorField.getText();
            String genre = genreField.getText();
            String date = dateField.getText();
            String isbnF = isbnField.getText();

            if (isbnF.charAt(isbnF.length()-1) != '.'){
                isbnF = isbnF +".";
            }
            Double isbn = Double.parseDouble(isbnF);

            obj.addBook(title,author,genre,date,isbn);

            titleField.clear();
            authorField.clear();
            genreField.clear();
            dateField.clear();
            isbnField.clear();

        }
    }

    // Checks to make sure no text fields are empty upon submission
    private boolean textFieldsEmpty() {

        return titleField.getText().trim().isEmpty() ||
                authorField.getText().trim().isEmpty() ||
                genreField.getText().trim().isEmpty() ||
                dateField.getText().trim().isEmpty() ||
                isbnField.getText().trim().isEmpty();
    }

}

