package sample;

import javafx.scene.control.TextField;

import javax.xml.soap.Text;

/**
 * Created by Микола on 04.04.2017.
 */
public class TrainingView {

    private String name;
    private TextField value;


    public String getName() {
        return name;
    }

    public TextField getValue() {
        return value;
    }

    public TrainingView(String name, String value) {

        this.name = name;
        this.value = new TextField(value);
    }
}
