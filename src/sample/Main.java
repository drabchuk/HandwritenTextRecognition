package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.nio.ch.SocketAdaptor;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene(root, 281, 113));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
