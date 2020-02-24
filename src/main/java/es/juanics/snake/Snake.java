package es.juanics.snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;


public class Snake extends Group{
    static final short TAM_PIEZA_SNAKE=40;
    
    public Snake(int parte){// parte 1 es cabeza, parte 2 es cuerpo, parte 3 es cola
        Image snakeHead = new Image(getClass().getResourceAsStream("/images/Snakehead.png"));
        Image snakeBody = new Image(getClass().getResourceAsStream("/images/Snakebody.png"));        
        Image snakeTail = new Image(getClass().getResourceAsStream("/images/Snaketail.png"));
        
        
        
        if(parte==1){//CABEZA SNAKE
            ImageView snakeHeadView = new ImageView(snakeHead);
            this.getChildren().add(snakeHeadView);
        }else if(parte==2){//CUERPO SNAKE
            ImageView snakeBodyView = new ImageView(snakeBody);
            this.getChildren().add(snakeBodyView);            
        }else{//COLA SNAKE
            ImageView snakeTailView = new ImageView(snakeTail);
            this.getChildren().add(snakeTailView);            
        }

    }
    
//    public void snakeMovement(Scene scene){
//
//        //CUANDO LAS TECLAS SON PULSADAS
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {//Llama al método setOnKeyPressed. Cuando detecte que se pulsa una tecla en la escena (se puede hacer que en vez que en la escena se detecte cuando pulse dentro de un campo de texto)
//            public void handle(final KeyEvent keyEvent) {
//                switch (keyEvent.getCode()) {//Según la tecla pulsada
//                        case LEFT:// el dinosaurio se moverá a la izquierda
//                            Timeline timelineI = new Timeline(//Sirve para lo que lo que metamos aquí. Podemos utilizar varios TimeLine con diferentes velocidades para diferentes cosas
//                                // 0.017 ~= 60 FPS (equivalencia de segundos a Frames por Segundo)
//                                new KeyFrame(Duration.seconds(0.017), new EventHandler<ActionEvent>() {
//                                    public void handle(ActionEvent ae) {//Sólo puede haber un handle en el timeline
//                                        this. //Esta ficha que utiliza el método. Ya sabe cual es
//                                    
//                                    }
//                                })                
//                            );
//                            timelineI.setCycleCount(Timeline.INDEFINITE);//Llama al método setCycleCount (para que la animación siga indefinidamente
//                            timelineI.play(); //Llama al método Play para echar a andar la animación
//                            break;    
//                }
//            }
//        });
//    }
        
}
