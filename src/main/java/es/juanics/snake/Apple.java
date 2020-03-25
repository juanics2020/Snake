package es.juanics.snake;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Apple extends ImageView{
    
    public Image appleImage;
    public Image appleImageBitten;
    
    
    public Apple(){//Creamos la manzana (un visor con una imagen
        //MANZANA NORMAL
        appleImage = new Image(getClass().getResourceAsStream("/images/apple.png"));
        this.setImage(appleImage);
        this.setFitHeight(App.TAM_PIEZA_SNAKE);
        this.setFitWidth(App.TAM_PIEZA_SNAKE);
        
        //MANZANA MORDIDA
        appleImageBitten = new Image(getClass().getResourceAsStream("/images/appleBitten.png"));
    }
    
}
