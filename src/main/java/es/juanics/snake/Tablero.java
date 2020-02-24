
package es.juanics.snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Tablero extends Pane {
    
    
    
        Snake snake1 = new Snake(1);
        root.getChildren().add(snake1);
        snake1.setLayoutX(0);
        snake1.setLayoutY(0);
    
    
    public void snakeMovement(Scene scene, Snake snake1){

    //CUANDO LAS TECLAS SON PULSADAS
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {//Llama al método setOnKeyPressed. Cuando detecte que se pulsa una tecla en la escena (se puede hacer que en vez que en la escena se detecte cuando pulse dentro de un campo de texto)
        public void handle(final KeyEvent keyEvent) {
            switch (keyEvent.getCode()) {//Según la tecla pulsada
                    case LEFT:// el dinosaurio se moverá a la izquierda
                        Timeline timelineI = new Timeline(//Sirve para lo que lo que metamos aquí. Podemos utilizar varios TimeLine con diferentes velocidades para diferentes cosas
                            // 0.017 ~= 60 FPS (equivalencia de segundos a Frames por Segundo)
                            new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent ae) {//Sólo puede haber un handle en el timeline
                                    this //Esta ficha que utiliza el método. Ya sabe cual es

                                }
                            })                
                        );
                        timelineI.setCycleCount(Timeline.INDEFINITE);//Llama al método setCycleCount (para que la animación siga indefinidamente
                        timelineI.play(); //Llama al método Play para echar a andar la animación
                        break;    
                }
            }
        });
    }
    
    
    
}
