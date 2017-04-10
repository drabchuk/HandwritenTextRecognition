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
import nn.optimazers.FletcherReeves;
import nn.optimazers.Function;
import nn.optimazers.Gradient;
import nn.optimazers.NNFunc;
import nn.perceptrons.DFFNNTrainer;
import nn.perceptrons.DeepFeedforwardNN;

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

    @FXML
    private Button saveNN;

    private ObservableList<TrainingView> trainingProperties = FXCollections.observableArrayList();

    private DeepFeedforwardNN nn;
    private byte[][] imgs;
    private byte[] labels;
    private int depth;
    private int[] sizes;


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

        chooseYButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open X File");
                try {
                    labels = Parser.parseY(new RandomAccessFile(new File(fileChooser.showOpenDialog(new Stage()).toURI()), "rw"));
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
                    imgs = Parser.parseX(new RandomAccessFile(new File(fileChooser.showOpenDialog(new Stage()).toURI()), "rw"));
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
                double[][] lbls = new double[labels.length][10];
                for (int i = 0; i < labels.length; i++) {
                    lbls[i][labels[i]] = 1.;
                }
                double[][] images = new double[imgs.length][784];
                for (int i = 0; i < imgs.length; i++) {
                    for (int j = 0; j < 784; j++) {
                        images[i][j] = (double) imgs[i][j] / 128.;
                    }
                }

                int[] sizes = new int[depth];

                for (int i = 0; i < depth; i++) {
                    sizes[i] = Integer.parseInt(valueColumn.getCellObservableValue(i).getValue().getText());
                }

                nn = new DeepFeedforwardNN(depth, sizes);
                DFFNNTrainer trainer = new DFFNNTrainer(nn, images, lbls);
                Function f = new NNFunc(trainer);
                //Gradient g = new GradientDescent(trainer.reshapeTheta(), 1000);
                //Gradient g = new FletcherReeves(trainer.reshapeTheta(), 1e-3, 0.1);
                Gradient g = new FletcherReeves(trainer.reshapeTheta(), 1e-2, 0.5, 10);
                double[] min = g.optimize(f);
                System.out.println("Learning completed.");
                trainer.setTheta(min);

            }
        });
        saveNN.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open X File");
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
