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
        
        Tablero tablero = new Tablero();
        root.getChildren().add(tablero);
        
//        Snake snake1 = new Snake(1);
//        root.getChildren().add(snake1);
//        snake1.setLayoutX(0);
//        snake1.setLayoutY(0);
//        
//        snake1.snakeMovement(scene, snake1);//quiere hacer el movimiento de la snake1
    }

    public static void main(String[] args) {
        launch();
    }

}