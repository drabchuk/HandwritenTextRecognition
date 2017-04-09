package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;



public class MainController {

    @FXML
    private AnchorPane mainStage;

    @FXML
    public void initialize() {

    }

    @FXML
    public void goToTrainingView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("trainingWindow.fxml"));
        AnchorPane load = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Trainig View");
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToTestView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        AnchorPane load = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Trainig View");
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();
    }
}
