package sample;

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
    private Button chooseButton;

    @FXML
    private Button chooseXButton;

    @FXML
    private Button chooseYButton;

    @FXML
    private ImageView imageViewer;


    private File imgFile;
    private Desktop desktop = Desktop.getDesktop();

    private byte[][] imgs;
    private byte[] labels;;

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
                    imgs = Parser.parseX(new RandomAccessFile(file ,"rw"));
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
                    labels = Parser.parseY(new RandomAccessFile(file ,"rw"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
