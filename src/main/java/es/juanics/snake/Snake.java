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
    
    Image snakeBodyVer;
    Image snakeBodyHor;
        
    Image snakeTailAb;
    Image snakeTailArr;
    Image snakeTailIz;
    Image snakeTailDer;   
    
    
    ImageView snakeHeadView;
    ImageView snakeBodyView;
    ImageView snakeTailView;
    
    
    public Snake(int parte){// parte 1 es cabeza, parte 2 es cuerpo, parte 3 es cola
        //CARGO IMÁGENES DE LA SERPIENTE Y HAGO TRES CLASES DE SERPIENTE CON TRES VISORES PARA CADA PARTE DEL CUERPO
        snakeHeadAb = new Image(getClass().getResourceAsStream("/images/Snakehead.png"));
        snakeHeadIz = new Image(getClass().getResourceAsStream("/images/Snakehead - iz.png"));
        snakeHeadDer = new Image(getClass().getResourceAsStream("/images/Snakehead - der.png"));
        snakeHeadArr = new Image(getClass().getResourceAsStream("/images/Snakehead - arr.png"));
        
        snakeBodyVer = new Image(getClass().getResourceAsStream("/images/Snakebody-Ver.png"));
        snakeBodyHor = new Image(getClass().getResourceAsStream("/images/Snakebody-Hor.png"));
        
        snakeTailAb = new Image(getClass().getResourceAsStream("/images/Snaketail-ab.png"));
        snakeTailArr = new Image(getClass().getResourceAsStream("/images/Snaketail-arr.png"));
        snakeTailIz = new Image(getClass().getResourceAsStream("/images/Snaketail-iz.png"));
        snakeTailDer = new Image(getClass().getResourceAsStream("/images/Snaketail-der.png"));
        
        
        //Haré que los visores midan 42 para poder establecer el tamaño de la matriz del tablero según tamño de escena    
        if(parte==App.NUM_HEAD){//CABEZA SNAKE en matriz será 1
            snakeHeadView = new ImageView(snakeHeadAb);
            snakeHeadView.setFitHeight(App.TAM_PIEZA_SNAKE);
            snakeHeadView.setFitWidth(App.TAM_PIEZA_SNAKE);
            this.getChildren().add(snakeHeadView);
                        
        }else if(parte==App.NUM_BODY){//CUERPO SNAKE en matriz será 2
            snakeBodyView = new ImageView(snakeBodyVer);
            snakeBodyView.setFitHeight(App.TAM_PIEZA_SNAKE);
            snakeBodyView.setFitWidth(App.TAM_PIEZA_SNAKE);
            this.getChildren().add(snakeBodyView);
            
        }else if(parte==App.NUM_TAIL){//COLA SNAKE en matriz será 3
            snakeTailView = new ImageView(snakeTailAb);
            snakeTailView.setFitHeight(App.TAM_PIEZA_SNAKE);
            snakeTailView.setFitWidth(App.TAM_PIEZA_SNAKE);            
            this.getChildren().add(snakeTailView);            
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
 
    
     
    //Poner la imagen del CUERPO según la dirección
    public void setBody (int direccion){
        switch (direccion) {//Según la tecla pulsada
            case App.D_RIGHT:// la serpiente se moverá a la derecha
                snakeBodyView.setImage(snakeBodyVer);
                break;
            case App.D_LEFT:// la serpiente se moverá a la izquierda
                snakeHeadView.setImage(snakeBodyVer);
                break;
            case App.D_DOWN:// la serpiente se moverá abajo
                snakeHeadView.setImage(snakeBodyHor);
                break;
            case App.D_UP:// la serpiente se moverá arriba
                snakeHeadView.setImage(snakeBodyHor);
                break;
        }
    }   
    

    
    //Poner la imagen de la COLA según la dirección
    public void setTail (int direccion){
        switch (direccion) {//Según la tecla pulsada
            case App.D_RIGHT:// la serpiente se moverá a la derecha
                snakeTailView.setImage(snakeTailDer);
                break;
            case App.D_LEFT:// la serpiente se moverá a la izquierda
                snakeTailView.setImage(snakeTailIz);
                break;
            case App.D_DOWN:// la serpiente se moverá abajo
                snakeTailView.setImage(snakeTailAb);
                break;
            case App.D_UP:// la serpiente se moverá arriba
                snakeTailView.setImage(snakeTailArr);
                break;
        }
    }      
    
}
