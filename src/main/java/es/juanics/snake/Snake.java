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
    final int TAM_PIEZA_SNAKE=42;
    //static final int TAM_PIEZA_SNAKE=42;
    
    public Snake(int parte){// parte 1 es cabeza, parte 2 es cuerpo, parte 3 es cola
        //CARGO IMÁGENES DE LA SERPIENTE Y HAGO TRES CLASES DE SERPIENTE CON TRES VISORES PARA CADA PARTE DEL CUERPO
        Image snakeHead = new Image(getClass().getResourceAsStream("/images/Snakehead.png"));
        Image snakeBody = new Image(getClass().getResourceAsStream("/images/Snakebody.png"));        
        Image snakeTail = new Image(getClass().getResourceAsStream("/images/Snaketail.png"));
        
        //Haré que los visores midan 42 para poder establecer el tamaño de la matriz del tablero según tamño de escena    
        if(parte==1){//CABEZA SNAKE
            ImageView snakeHeadView = new ImageView(snakeHead);
            snakeHeadView.setFitHeight(TAM_PIEZA_SNAKE);
            snakeHeadView.setFitWidth(TAM_PIEZA_SNAKE);
            this.getChildren().add(snakeHeadView);
                        
        }else if(parte==2){//CUERPO SNAKE
            ImageView snakeBodyView = new ImageView(snakeBody);
            snakeBodyView.setFitHeight(TAM_PIEZA_SNAKE);
            snakeBodyView.setFitWidth(TAM_PIEZA_SNAKE);
            this.getChildren().add(snakeBodyView);
            
        }else{//COLA SNAKE
            ImageView snakeTailView = new ImageView(snakeTail);
            snakeTailView.setFitHeight(TAM_PIEZA_SNAKE);
            snakeTailView.setFitWidth(TAM_PIEZA_SNAKE);            
            this.getChildren().add(snakeTailView);            
        }
    }
  
    public int tamañoSnake(){
        return TAM_PIEZA_SNAKE;
    }
    
}
