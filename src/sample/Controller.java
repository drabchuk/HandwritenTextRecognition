package sample;

import classes.CustomImage;
import classes.Parser;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Controller {

    @FXML
    private Button chooseButton, chooseXButton, chooseYButton, startTestButton, nextImgButton;

    @FXML
    private ImageView randomDidgitImg;


    private File imgFile;
    private Desktop desktop = Desktop.getDesktop();

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

                CustomImage customImg = new CustomImage(imgs[(int) (Math.random() * (imgs.length - 1))]);
                randomDidgitImg.setImage(customImg.getWritableImage());
            }
        });
    }
}
