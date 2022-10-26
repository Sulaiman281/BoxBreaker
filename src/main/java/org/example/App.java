package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.game.BoxBroker;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Box Breaker");
        stage.setScene(new Scene(new BoxBroker().getRoot()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}