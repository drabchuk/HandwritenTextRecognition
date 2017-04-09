package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.TableView;

public class TrainingWindowController {

    @FXML
    private TableColumn<TrainingView, String> nameColumn;

    @FXML
    private TableColumn<TrainingView, TextField> valueColumn;

    @FXML
    private TableView trainingTable;

    @FXML
    private TextField depthField;

    private ObservableList<TrainingView> trainingProperties = FXCollections.observableArrayList();


    @FXML
    public void initialize() {

        trainingTable.setEditable(true);

        nameColumn.setCellValueFactory(new PropertyValueFactory<TrainingView, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<TrainingView, TextField>("value"));

        depthField.textProperty().addListener((observable, oldValue, newValue )->{
            if (newValue.equals(""))
                newValue = "0";
            trainingProperties.removeAll();
            trainingProperties.clear();
            trainingProperties.add(new TrainingView("Input", ""));
            for (int i = 0; i < Integer.parseInt(newValue); i++) {
                trainingProperties.add(new TrainingView("Size " + i, ""));
            }
            trainingProperties.add(new TrainingView("Output", ""));
            trainingTable.setItems(trainingProperties);

        });

    }
}
