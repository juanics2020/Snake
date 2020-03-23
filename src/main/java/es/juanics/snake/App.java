/*LA SERPIENTE SE DESCUADRA CUANDO SE PULSAN DOS TECLAS JUNTAS O MUY SEGUIDAS
(NO DA TIEMPO DE QUE TERMINE EL MOVIMIENTO DE LA PULSACIÓN DE UNA TECLA CUANDO LE DAMOS A OTRA)*/

//CUANDO REINICIA EL JUEGO EL PANEL SE QUEDA EN GRIS

package es.juanics.snake;

import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class App extends Application {
    //CONSTANTES GENERALES
    
    public final int SCENE_WIDTH = 882;//SEGÚN TAMAÑO MATRIZ
    public final int SCENE_HEIGHT = 546;
    public static final int TAM_PIEZA_SNAKE = 42;//Tamaño que tendrán los visores de las imágenes (Tamaño grafico de cada celda)
    private final int HBOX_HEIGHT = 29; 
    private final int SCENE_HEIGHT_MAS_VBOX = SCENE_HEIGHT + HBOX_HEIGHT;//ALTURA DE LA PANTALLA SUMANDO TABLERO Y HBOX(29). VBOX = TABLERO+HBOX
           
            
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
    public static int puntuacion = 0;
    VBox panePuntuacion;
    public static Text textScore;
    
    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();//contenedor principal(stackpane)
        var scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT_MAS_VBOX);
        stage.setScene(scene);
        stage.setResizable(false);//Para que el usuario no pueda cambiar el tamaño de la pantalla
        stage.show();
        stage.setTitle("SNAKE");
        scene.setFill(Color.LIGHTYELLOW);
        
        //VOBOX (HBOX  con text + puntuacion) + (Tablero)
        panePuntuacion = new VBox();        
        root.getChildren().add(panePuntuacion);
        
        HBox panePuntHor = new HBox();
        panePuntHor.setSpacing(10);
        //Texto de etiqueta para la puntuación
        Text textTitleScore = new Text("Score:");
        textTitleScore.setFont(Font.font("Arial Black", 20));
        textTitleScore.setFill(Color.BLACK);
        //Texto para la puntuación
        textScore = new Text(String.valueOf(puntuacion));
        textScore.setFont(Font.font("Arial Black", 20));
        textScore.setFill(Color.BLACK);
        
        panePuntHor.setAlignment(Pos.CENTER);
        panePuntHor.setMinHeight(HBOX_HEIGHT);
        panePuntHor.setMaxHeight(HBOX_HEIGHT);
        
        panePuntHor.getChildren().add(textTitleScore);
        panePuntHor.getChildren().add(textScore);
        panePuntuacion.getChildren().add(panePuntHor);
        
        //el tablero ya contiene la cabeza de la serpiente y la manzana
        Tablero tablero = new Tablero(SCENE_WIDTH, SCENE_HEIGHT);//Le paso las medidas al tablero
        panePuntuacion.getChildren().add(tablero);
        
        
       
        //NO CREARÉ UN SNAKEGAME NUEVO. SÓLO USARÉ EL QUE HE CREADO EN TABLERO PARA PORQUE SI NO TENDRÍA DOS SNAKEGAMES QUE NO COINCIDEN
        //SnakeGame snakeGame = new SnakeGame();
        
        
       
            //CUANDO LAS TECLAS SON PULSADAS
            //Llama al método setOnKeyPressed. Cuando detecte que se pulsa una tecla en la escena (se puede hacer que en vez que en la escena se detecte cuando pulse dentro de un campo de texto)
            scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                public void handle(final KeyEvent keyEvent) {

                    switch (keyEvent.getCode()) {//Según la tecla pulsada
                        //¡¡¡LEFT, RIGHT, DOWN Y UP ES COMO SE LLAMAN LAS TECLAS (NO LAS DIRECCIONES DIRECTAMENTE)!!!!
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
                    
                    
                    try {
                        boolean eaten = tablero.snakeGame.matrixMovement(direccion);//Hacer el movimiento lógico en la matriz en SnakeGame
                        tablero.snakeMovement(direccion);//Hacer el movimiento de la snake1 en el Tablero
                        if (eaten == true){//Si se come la manzana movemos la imagen a la posición nueva
                            tablero.setImageApple();
                        }
                        direccion=0;//Si no se pone se pisa
                    } catch(ArrayIndexOutOfBoundsException e) { //SI MUERE******
                        System.out.println("HAS MUERTO");
                        //ALERTA SI MUERE
                        Alert alert = new Alert(AlertType.CONFIRMATION, "Has muerto");                                         
                        alert.setTitle("CONTINUAR");
                        alert.setHeaderText(null);
                        alert.setContentText("¿QUIERES CONTINUAR?");
                        Optional<ButtonType> result  = alert.showAndWait();
                        
                        if (result.isPresent() && result.get() == ButtonType.OK) { //DESEA CONTINUAR
                            System.out.println("Vamos a Continuar");
                            tablero.reiniciar();
                        }else{//NO DESEA CONTINUAR
                            System.exit(0);
                        }
                    }
                }
            });                              
    }

    public static void main(String[] args) {
        launch();
    }

}