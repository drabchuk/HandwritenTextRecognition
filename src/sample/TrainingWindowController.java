package sample;

import classes.Parser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nn.perceptrons.DFFNNTrainer;
import nn.perceptrons.DeepFeedforwardNN;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static nn.data.ImgBrightnessMapper.map;

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

    @FXML
    private Button saveNN;

    @FXML
    private TextField criteriaField;

    @FXML
    private TextField stepLengthField;

    @FXML
    private TextField maxStepsField;

    @FXML
    private TextField maxTimeField;

    @FXML
    private Label loadXStatus;

    @FXML
    private Label loadYStatus;

    @FXML
    private Label learningStatus;

    private ObservableList<TrainingView> trainingProperties = FXCollections.observableArrayList();

    private DeepFeedforwardNN nn;
    private byte[][] imgs;
    private byte[] labels;
    private int depth;
    private int[] sizes;
    private double criteria;
    private double stepLength;
    private int maxSteps;
    private int maxTime;


    @FXML
    public void initialize() {

        trainingTable.setEditable(true);

        nameColumn.setCellValueFactory(new PropertyValueFactory<TrainingView, String>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<TrainingView, TextField>("value"));

        depthField.textProperty().addListener((observable, oldValue, newValue) -> {
            depth = Integer.parseInt(newValue);
            if (newValue.equals(""))
                newValue = "0";
            trainingProperties.removeAll();
            trainingProperties.clear();
            trainingProperties.add(new TrainingView("Input", ""));
            for (int i = 0; i < depth - 2; i++) {
                trainingProperties.add(new TrainingView("Size " + i, ""));
            }
            trainingProperties.add(new TrainingView("Output", ""));
            trainingTable.setItems(trainingProperties);

        });

        criteriaField.textProperty().addListener((observable, oldValue, newValue) -> {
            criteria = Double.parseDouble(newValue);
            if (newValue.equals(""))
                newValue = "0";

        });

        stepLengthField.textProperty().addListener((observable, oldValue, newValue) -> {
            stepLength = Double.parseDouble(newValue);
            if (newValue.equals(""))
                newValue = "0";

        });

        maxStepsField.textProperty().addListener((observable, oldValue, newValue) -> {
            maxSteps = Integer.parseInt(newValue);
            if (newValue.equals(""))
                newValue = "0";

        });

        maxTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
            maxTime = Integer.parseInt(newValue);
        });

        chooseYButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Y File");
                try {
                    loadYStatus.setText("loading...");
                    File file = new File(fileChooser.showOpenDialog(new Stage()).toURI());
                    labels = Parser.parseY(new RandomAccessFile(file, "rw"));
                    loadYStatus.setText(file.getName());
                } catch (IOException e) {
                    loadYStatus.setText("fail");
                    e.printStackTrace();
                }
            }
        });

        chooseXButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open X File");
                try {
                    loadXStatus.setText("loading...");
                    File file = new File(fileChooser.showOpenDialog(new Stage()).toURI());
                    imgs = Parser.parseX(new RandomAccessFile(file, "rw"));
                    loadXStatus.setText(file.getName());
                } catch (IOException e) {
                    loadXStatus.setText("fail");
                    e.printStackTrace();
                }
            }
        });

        loadTrainButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                learningStatus.setText("Learning started");

                double[][] lbls = new double[labels.length][10];
                for (int i = 0; i < labels.length; i++) {
                    lbls[i][labels[i]] = 1.;
                }
                double[][] images = new double[imgs.length][784];
                for (int i = 0; i < imgs.length; i++) {
                    for (int j = 0; j < 784; j++) {
                        images[i][j] = map(imgs[i][j]);
                    }
                }

                int[] sizes = new int[depth];

                for (int i = 0; i < depth; i++) {
                    sizes[i] = Integer.parseInt(valueColumn.getCellObservableValue(i).getValue().getText());
                }

                nn = new DeepFeedforwardNN(depth, sizes);
                DFFNNTrainer trainer = new DFFNNTrainer(nn, images, lbls);
                trainer.trainTimeBound(maxSteps, stepLength, maxTime);
                System.out.println("Learning completed.");
                learningStatus.setText("Learning completed.");

            }
        });
        saveNN.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File");
                File file =  fileChooser.showOpenDialog(new Stage());
                try {
                    nn.saveWeights(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
