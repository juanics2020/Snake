/*LA SERPIENTE SE DESCUADRA CUANDO SE PULSAN DOS TECLAS JUNTAS O MUY SEGUIDAS
(NO DA TIEMPO DE QUE TERMINE EL MOVIMIENTO DE LA PULSACIÓN DE UNA TECLA CUANDO LE DAMOS A OTRA)*/


package es.juanics.snake;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class App extends Application {
    //CONSTANTES GENERALES
    public final int SCENE_WIDTH=882;//SEGÚN TAMAÑO MATRIZ
    public final int SCENE_HEIGHT=546;
    public static final int TAM_PIEZA_SNAKE=42;//Tamaño que tendrán los visores de las imágenes (Tamaño grafico de cada celda)
 
    //CONSTANTES PARA DIRECCIONES
    public static final int D_RIGHT = 1;//Constantes que para comparar la direcciones en switch
    public static final int D_LEFT = -1;//Ponemos static para poder usarlas en otras clases
    public static final int D_UP = -2;
    public static final int D_DOWN = 2;
    
    //CONSTANTES PARA MATRIZ TABLERO
    public static final int NUM_EMPTY = 0;
    public static final int NUM_HEAD = 1;
    public static final int NUM_BODY = 2;
    public static final int NUM_TAIL = 3;
    public static final int NUM_APPLE = 4;
    
    private int direccion = 0;
    
    
    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();//contenedor principal(stackpane)
        var scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);//Para que el usuario no pueda cambiar el tamaño de la pantalla
        stage.show();
        stage.setTitle("SNAKE");
        scene.setFill(Color.LIGHTYELLOW);
        
        //el tablero ya contiene la cabeza de la serpiente y la manzana
        Tablero tablero = new Tablero(SCENE_WIDTH, SCENE_HEIGHT);//Le paso las medidas al tablero
        root.getChildren().add(tablero);
       
        SnakeGame snakeGame = new SnakeGame();       
        
        //CUANDO LAS TECLAS SON PULSADAS
        //Llama al método setOnKeyPressed. Cuando detecte que se pulsa una tecla en la escena (se puede hacer que en vez que en la escena se detecte cuando pulse dentro de un campo de texto)
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {//Según la tecla pulsada
                    //¡¡¡1LEFT, RIGHT, DOWN Y UP ES COMO SE LLAMAN LAS TECLAS (NO LAS DIRECCIONES DIRECTAMENTE)!!!!
                    case LEFT:// el dinosaurio se moverá a la izquierda
                        direccion=-1;
                        break;
                    case RIGHT:// el dinosaurio se moverá a la izquierda
                        direccion=1;
                        break;
                    case DOWN:// el dinosaurio se moverá a la izquierda
                        direccion=2;
                        break;
                    case UP:// el dinosaurio se moverá a la izquierda
                        direccion=-2;
                        break;
                }
                tablero.snakeMovement(direccion);//Hacer el movimiento de la snake1 en el Tablero
                snakeGame.matrixMovement(direccion);//Hacer el movimiento lógico en la matriz en SnakeGame
//                snakeGame.mostrarMatrizConsola();//Cada vez que se mueva muestro la matriz actualizada en la consola
                direccion=0;//Si no se pone se pisa
            }
        });         
    }

    public static void main(String[] args) {
        launch();
    }

}