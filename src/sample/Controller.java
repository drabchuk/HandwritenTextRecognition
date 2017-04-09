package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;


public class Controller {

    @FXML
    private Button chooseButton;

    @FXML
    private ImageView imageViewer;

    private File imgFile;
    private Desktop desktop = Desktop.getDesktop();

    @FXML
    public void initialize() {

        chooseButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                imgFile = fileChooser.showOpenDialog(new Stage());
                Image img = new Image(imgFile.toURI().toString());
                imageViewer.setImage(img);
            }
        });
    }

}
