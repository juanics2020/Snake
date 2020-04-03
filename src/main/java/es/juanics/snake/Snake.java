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
    
    Image snakeHeadAb;
    Image snakeHeadIz;
    Image snakeHeadDer;
    Image snakeHeadArr;
    
    Image snakeBody;
       
    
    ImageView snakeHeadView;
    ImageView snakeBodyView;

    
    
    public Snake(int parte){// parte 1 es cabeza, parte 2 es cuerpo, parte 3 es cola
        //CARGO IMÁGENES DE LA SERPIENTE Y HAGO TRES CLASES DE SERPIENTE CON TRES VISORES PARA CADA PARTE DEL CUERPO
        snakeHeadAb = new Image(getClass().getResourceAsStream("/images/Snakehead.png"));
        snakeHeadIz = new Image(getClass().getResourceAsStream("/images/Snakehead - iz.png"));
        snakeHeadDer = new Image(getClass().getResourceAsStream("/images/Snakehead - der.png"));
        snakeHeadArr = new Image(getClass().getResourceAsStream("/images/Snakehead - arr.png"));
        
        snakeBody = new Image(getClass().getResourceAsStream("/images/Cuerpo.png"));
        
        
        
        //Haré que los visores midan 42 para poder establecer el tamaño de la matriz del tablero según tamño de escena    
        if(parte==App.NUM_HEAD){//CABEZA SNAKE en matriz será 1
            snakeHeadView = new ImageView(snakeHeadAb);
            snakeHeadView.setFitHeight(App.TAM_PIEZA_SNAKE);
            snakeHeadView.setFitWidth(App.TAM_PIEZA_SNAKE);
            this.getChildren().add(snakeHeadView);
                        
        }else if(parte==App.NUM_BODY){//CUERPO SNAKE en matriz será 2
            snakeBodyView = new ImageView(snakeBody);
            snakeBodyView.setFitHeight(App.TAM_PIEZA_SNAKE);
            snakeBodyView.setFitWidth(App.TAM_PIEZA_SNAKE);
            this.getChildren().add(snakeBodyView);
                    
        }
    }
    
    
    
    //Poner la imagen de la CABEZA según la dirección
    public void setHead (int direccion){
        switch (direccion) {//Según la tecla pulsada
            case App.D_RIGHT:// la serpiente se moverá a la derecha
                snakeHeadView.setImage(snakeHeadDer);
                break;
            case App.D_LEFT:// la serpiente se moverá a la izquierda
                snakeHeadView.setImage(snakeHeadIz);
                break;
            case App.D_DOWN:// la serpiente se moverá abajo
                snakeHeadView.setImage(snakeHeadAb);
                break;
            case App.D_UP:// la serpiente se moverá arriba
                snakeHeadView.setImage(snakeHeadArr);
                break;
        }
    }
     
    
}
