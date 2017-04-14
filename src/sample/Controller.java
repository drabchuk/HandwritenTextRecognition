package sample;

import classes.CustomImage;
import classes.Parser;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import nn.NeuralNetwork;
import vlad.reader.NeuralReader;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Controller {

    @FXML
    private Button chooseButton, chooseXButton, chooseYButton, startTestButton, nextImgButton;

    @FXML
    private ImageView randomDidgitImg;

    @FXML
    private Label recognizedLabel;

    @FXML
    private javafx.scene.control.TextField resultError;


    private File imgFile;
    private Desktop desktop = Desktop.getDesktop();

    private NeuralNetwork nn;
    private byte[][] imgs;
    private byte[] labels;

    final static int WIDTH = 28;
    final static int HEIGHT = 28;


    @FXML
    public void initialize() {


        chooseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open NN File");
                File file = fileChooser.showOpenDialog(new Stage());
                nn = NeuralReader.getReader().start(file);
            }
        });

        chooseXButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open X File");
                File file = fileChooser.showOpenDialog(new Stage());
                try {
                    imgs = Parser.parseX(new RandomAccessFile(file, "rw"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        chooseYButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Y File");
                File file = fileChooser.showOpenDialog(new Stage());
                try {
                    labels = Parser.parseY(new RandomAccessFile(file, "rw"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        startTestButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // CODE FOR TEST BUTTON
                double[] prediction = new double[10];
                double[] example = new double[784];
                double sumError = 0.;
                for (int i = 0; i < imgs.length; i++) {
                    for (int j = 0; j < 784; j++) {
                        example[j] = ((double) imgs[i][j]) / 128.;
                    }
                    prediction = nn.predict(example);
                    int maxId = 0;
                    double maxNum = 0.0;
                    for (int j = 0; j < 10; j++) {
                        if (maxNum < prediction[j]) {
                            maxNum = prediction[j];
                            maxId = j;
                        }
                    }
                    if (maxId != labels[i]) sumError++;
                }
                sumError /= labels.length;
                resultError.setText(String.valueOf(sumError));
            }
        });

        nextImgButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                byte[][] pixels = new byte[HEIGHT][WIDTH];
                for (int i = 0; i < HEIGHT; i++) {
                    for (int j = 0; j < WIDTH; j++) {
                        pixels[i][j] = (byte) i;
                    }
                }
                int imgNum = (int) (Math.random() * (imgs.length - 1));
                CustomImage customImg = new CustomImage(imgs[imgNum]);
                randomDidgitImg.setImage(customImg.getWritableImage());

                double[] example = new double[784];

                for (int j = 0; j < 784; j++) {
                    example[j] = ((double) imgs[imgNum][j]) / 128.;
                }
                double[] prediction = nn.predict(example);
                int maxId = 0;
                double maxNum = 0.0;
                for (int j = 0; j < 10; j++) {
                    if (maxNum < prediction[j]) {
                        maxNum = prediction[j];
                        maxId = j;
                    }
                }
                recognizedLabel.setText(String.valueOf(maxId)
                );

            }
        });
    }
}
