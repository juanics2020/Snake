package es.juanics.snake;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


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
}
