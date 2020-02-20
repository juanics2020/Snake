package es.juanics.snake;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();//contenedor principal(stackpane)
        var scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
        
        Snake snake1 = new Snake(1);
        root.getChildren().add(snake1);
    }

    public static void main(String[] args) {
        launch();
    }

}