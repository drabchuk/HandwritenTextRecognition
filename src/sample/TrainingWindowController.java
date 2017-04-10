package sample;

import classes.Parser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nn.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static nn.algebra.Alg.rand;

public class TrainingWindowController {

    @FXML
    private TableColumn<TrainingView, String> nameColumn;

    @FXML
    private TableColumn<TrainingView, TextField> valueColumn;

    @FXML
    private TableView trainingTable;

    @FXML
    private TextField depthField;

    @FXML
    private Button chooseXButton;

    @FXML
    private Button chooseYButton;

    @FXML
    private Button loadTrainButton;

    private ObservableList<TrainingView> trainingProperties = FXCollections.observableArrayList();

    private byte[][] imgs;
    private byte[] labels;;


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
            for (int i = 0; i < Integer.parseInt(newValue) - 2; i++) {
                trainingProperties.add(new TrainingView("Size " + i, ""));
            }
            trainingProperties.add(new TrainingView("Output", ""));
            trainingTable.setItems(trainingProperties);

        });

        chooseYButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open X File");
                try {
                    labels = Parser.parseY(new RandomAccessFile(new File(fileChooser.showOpenDialog(new Stage()).toURI()) ,"rw"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                Image img = new Image(imgFile.toURI().toString());
              //  imageViewer.setImage(img);
            }
        });

        chooseXButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open X File");
                try {
                    imgs = Parser.parseX(new RandomAccessFile(new File(fileChooser.showOpenDialog(new Stage()).toURI()) ,"rw"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                Image img = new Image(imgFile.toURI().toString());
                //  imageViewer.setImage(img);
            }
        });

        loadTrainButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
    }

}
