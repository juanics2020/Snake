//QUEDA PONER LAS IMÁGENES DEL CUERPO Y LA COLA
//CUANDO MUERA AL DARLE AL CONTINUAR DEBEMOS QUITAR LAS IMÁGENES


//Cuando se reinicia la partida el puntero de la cabeza no se actualiza


//Revisar public y private



/*REGLAS DEL JUEGO:

1º EL JUGADOR ELIGE EL NIVEL DE DIFICULTAD QUE DESEA DE 1 A 5 (VELOCIDAD DE MOVIEMIENTO DE LA SERPIENTE
2º SI CHOCA (SALE DE LOS LÍMITES) SE LE PREGUNTA QUE SI QUIERE CONTINUAR:
    - SI QUIERE CONTINUAR, VUELVE A EMPEZAR CON DIFICULTAD 1 Y PUNTUACIÓN 0
    - SI NO QUIERE CONTINUAR, SALE DE LA APLICACIÓN
3º A MEDIDA QUE COME MANZANAS SUBEN LOS PUNTOS Y SUBE DE NIVEL:
    NIVEL INICIAL: DIFICULTAD QUE ELIJA EL JUGADOR
    CADA 10 MANZANAS SUBE UN NIVEL DE DIFICULTAD
    SI COME 50 MANZANAS GANA EL JUEGO Y SALE UNA PANTALLA PARA FELICITARLO

*****SI EL USUARIO COMIENZA EN 5 PUEDE LLEGAR HASTA EL NIVEL 10 DE DIFICULTAD ANTES DE GANAR


Cada vez que se coma una manzana se añadirá una pieza al cuerpo,la primera piza tendrá la imagen de la cola
la segunda piza intercambiará la cola por el cuerpo

*/


//Static -> Se puede usar la variable o constante en otras clases, poniendo el nombre de la clase, no el nombre del objeto.
//Cuando no depende de cada objeto. Se crea una vez nada más, no se crea varias veces en el objeto.
//Lo mismo para los métodos haciéndolos estáticos

//Si no le pongo PUBLIC en la declaración de una clase ej: Label puntuacion; sólo lo vería en las clases que estén dentro de la carpeta (en el mismo paquete/proyecto)
//Si le pongo PUBLIC se vería en cualquier paquete (otros proyectos)

//Para App no puedo crear un objeto porque crearíamos otra aplicación nueva, no la que estamos usando

//******** ORDENAR LLAVES Y FORMATOS: SOURCE, FORMAT!!!!!!!!!***** ORGANIZA TODAS LAS CLASES
//En algún lugar hay que almacenarlas posiciones del cuerpo. Los arrays tienen un tamaño fijo.
//Hay que usar los ArrayList(para que vaya creciendo (posiciones fila y columna). 



package es.juanics.snake;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceDialog;
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
    
    
    private int direccion = 2; //La serpiente comienza yendo hacia abajo
    public static int puntuacion = 0;
    
    
    VBox panePuntuacion;   
    public static Text textScore; //muestra puntuación en el VBOX
    public static Text textDificulty;//muestra dificultad en el VBOX
    
    public static VBox paneContinuar;
    public static Button botonYes;
    public static Button botonNO;
    
    public static VBox paneWin;
    public static Text textP;
    public static Text textD;
    
    //Para diálogo de opciones (DIFICULTAD)
    //USAREMOS UN ARRAYLIST PARA EL DIÁLOGO DE ELEGIR LA DIFICULTAD.
    //EL ARRAYLIST LO CONVERTIREMOS EN UNA LISTA Y LA LISTA SE LA PASAREMOS AL DIÁLOGO
    private final String [] arrayDificultad = {"1", "2", "3", "4", "5"};
    //(DIFICULTAD 1: Velocidad 1)
    //(DIFICULTAD 2: Velocidad 1.5)
    //(DIFICULTAD 3: Velocidad 2)
    //(DIFICULTAD 4: Velocidad 2.5)
    //(DIFICULTAD 5: Velocidad 3)
    //La velocidad se usará en el timelineSnake de Tablero
    public static String dificultad;//La elige el usuario
    public static double velocidad = 1; 
    
    
    
    
    @Override
    public void start(Stage stage) {
               
        StackPane root = new StackPane();//contenedor principal(stackpane)
        var scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT_MAS_VBOX);
        stage.setScene(scene);
        stage.setResizable(false);//Para que el usuario no pueda cambiar el tamaño de la pantalla
        stage.show();
        stage.setTitle("SNAKE");
        
        
        //DIÁLOGO DE OPCIONES (CHOICE DIALOG)
        //**********************************************************************
        List<String> listaDificultad = Arrays.asList(arrayDificultad);
        
        ChoiceDialog dialog = new ChoiceDialog(listaDificultad.get(0), listaDificultad);
        dialog.setTitle("DIFICULTAD");
        dialog.setHeaderText("Elige la dificultad");

        Optional<String> result = dialog.showAndWait();
        dificultad = "cancelled.";

        if (result.isPresent()) {//Si elije una opción se guardará la velocidad
            dificultad = result.get();
            velocidad = ((Double.valueOf(dificultad)*0.5)+0.5);
        }//Si no elije nada la velocidad seguirá siendo 1, como estaba inicializada       
        System.out.println("");
        System.out.println("Dificultad: " + dificultad+" ----- Velocidad: "+velocidad);
        System.out.println("");
        //**********************************************************************
        
     
        
        //VOBOX (HBOX  con text + puntuacion + text + dificultad) + (Tablero)
        //**********************************************************************
        panePuntuacion = new VBox();
        String style = "-fx-background-color: lightyellow;"; //COLOR QUE LE VOY A DAR AL VBOX
        panePuntuacion.setStyle(style);
        root.getChildren().add(panePuntuacion);
        
        HBox panePuntHor = new HBox();
        panePuntHor.setSpacing(10);
        //---
        //Texto de etiqueta para la puntuación
        Text textTitleScore = new Text("Puntuación: ");
        textTitleScore.setFont(Font.font("Arial Black", 20));
        textTitleScore.setFill(Color.BLACK);
        //Texto para la puntuación
        textScore = new Text(String.valueOf(puntuacion));
        textScore.setFont(Font.font("Arial Black", 20));
        textScore.setFill(Color.RED);
        //---
        Text textTitleDificulty = new Text("Dificultad: ");
        textTitleDificulty.setFont(Font.font("Arial Black", 20));
        textTitleDificulty.setFill(Color.BLACK);
        //Texto para la puntuación
        textDificulty = new Text(dificultad);//la dificultad que selecciona el usuario en el diálogo
        textDificulty.setFont(Font.font("Arial Black", 20));
        textDificulty.setFill(Color.RED);             
        //---
        
        panePuntHor.setAlignment(Pos.CENTER);
        panePuntHor.setMinHeight(HBOX_HEIGHT);
        panePuntHor.setMaxHeight(HBOX_HEIGHT);
        
        panePuntHor.getChildren().add(textTitleScore);
        panePuntHor.getChildren().add(textScore);
        panePuntHor.getChildren().add(textTitleDificulty);
        panePuntHor.getChildren().add(textDificulty);
        panePuntuacion.getChildren().add(panePuntHor);
        //**********************************************************************
              
        //Tablero incluído en VBOX
        //el tablero ya contiene la cabeza de la serpiente y la manzana
        Tablero tablero = new Tablero(SCENE_WIDTH, SCENE_HEIGHT);//Le paso las medidas al tablero
        panePuntuacion.getChildren().add(tablero);
        // Primero hacemos un retardo para que el usuario esté preparado y después se moverá la serpiente
        tablero.reatrdoInicioPartida(direccion);
        
        //tablero.snakeMovement(direccion);//para que arranque la serpiente al iniciar el juego
  
        //NO CREARÉ UN SNAKEGAME NUEVO. SÓLO USARÉ EL QUE HE CREADO EN TABLERO PARA PORQUE SI NO TENDRÍA DOS SNAKEGAMES QUE NO COINCIDEN
        //SnakeGame snakeGame = new SnakeGame();
        
        
        
        //Layout principal para VBOX (MENSAJE CONTINUAR)
        //**********************************************************************
        paneContinuar = new VBox();
        paneContinuar.setMinHeight(SCENE_HEIGHT);
        paneContinuar.setTranslateY(0);       
        paneContinuar.setMinWidth(SCENE_WIDTH);
        paneContinuar.setTranslateX(0);
        paneContinuar.setAlignment(Pos.CENTER);
        paneContinuar.setSpacing(100);
        paneContinuar.setStyle("-fx-background-color: #fff980;");
        root.getChildren().add(paneContinuar);
        //Texto de etiqueta para la continuar
        Text textTitleCon = new Text("¿Do you want to continue?");
        textTitleCon.setFont(Font.font("Arial Black", 24));
        textTitleCon.setFill(Color.BLACK);
        paneContinuar.getChildren().add(textTitleCon);
        
        //AÑADIR LOS DOS BOTONES HORIZONTALES EN UN HBOX(DENTRO DEL VBOX)
        HBox paneBotones = new HBox();
        paneBotones.setAlignment(Pos.CENTER);
        paneBotones.setSpacing(20);
        //DOS BOTONES
        ButtonBar buttonBar = new ButtonBar();
        botonYes = new Button();
        ButtonBar.setButtonData(botonYes, ButtonBar.ButtonData.YES);
        botonYes.setText("YES");
        //botonYes.getOnMouseClicked();
        // Create the ButtonBar instance
        botonNO = new Button();
        ButtonBar.setButtonData(botonNO, ButtonBar.ButtonData.NO);
        botonNO.setText("NO");
        buttonBar.getButtons().addAll(botonYes, botonNO);
        //paneBotones.getChildren().add(botonYes);
        //paneBotones.getChildren().add(botonNO);
        paneBotones.getChildren().add(buttonBar);
        paneContinuar.getChildren().add(paneBotones);
        paneContinuar.setVisible(false);
        //**********************************************************************
        
        
        //Layout principal para VBOX (MENSAJE HAS GANADO)
        //**********************************************************************
        paneWin = new VBox();
        paneWin.setMinHeight(SCENE_HEIGHT);
        paneWin.setTranslateY(0);       
        paneWin.setMinWidth(SCENE_WIDTH);
        paneWin.setTranslateX(0);
        paneWin.setAlignment(Pos.CENTER);
        paneWin.setSpacing(100);
        paneWin.setStyle("-fx-background-color: #76cdd6;");
        root.getChildren().add(paneWin);
        //Texto de etiqueta de HAS GANADO
        Text textTitleWin = new Text("ENHORA BUENA, HAS GANADO");
        textTitleWin.setFont(Font.font("Cooper Black", 30));
        textTitleWin.setFill(Color.BLUE);
        paneWin.getChildren().add(textTitleWin);
        
        //HBOX PUNTUACIÓN
        HBox panePFW = new HBox();
        panePFW.setAlignment(Pos.CENTER);
        panePFW.setSpacing(20);
         //Texto de etiqueta de PUNTUACIÓN
        Text textTitlePuntW = new Text("Puntuación: ");
        textTitlePuntW.setFont(Font.font("Cooper Black", 25));
        textTitlePuntW.setFill(Color.BLACK);
        panePFW.getChildren().add(textTitlePuntW);
        //Texto PUNTUACIÓN
        textP = new Text(String.valueOf(puntuacion));//la dificultad que selecciona el usuario en el diálogo
        textP.setFont(Font.font("Cooper Black", 25));
        textP.setFill(Color.YELLOW);
        panePFW.getChildren().add(textP);
        
         //Texto de etiqueta de PUNTUACIÓN
        Text textTitleDifW = new Text("Dificultad: ");
        textTitleDifW.setFont(Font.font("Cooper Black", 25));
        textTitleDifW.setFill(Color.BLACK);
        panePFW.getChildren().add(textTitleDifW);
        //Texto PUNTUACIÓN
        textD = new Text(dificultad);//la dificultad que selecciona el usuario en el diálogo
        textD.setFont(Font.font("Cooper Black", 25));
        textD.setFill(Color.YELLOW);
        panePFW.getChildren().add(textD);
            
        paneWin.getChildren().add(panePFW);
        paneWin.setVisible(false);
        //**********************************************************************
              
        //CUANDO LAS TECLAS SON PULSADAS
        //Llama al método setOnKeyPressed. Cuando detecte que se pulsa una tecla en la escena (se puede hacer que en vez que en la escena se detecte cuando pulse dentro de un campo de texto)
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(final KeyEvent keyEvent) {

                switch (keyEvent.getCode()) {//Según la tecla pulsada
                    //¡¡¡LEFT, RIGHT, DOWN Y UP ES COMO SE LLAMAN LAS TECLAS (NO LAS DIRECCIONES DIRECTAMENTE)!!!!
                    case LEFT:// el dinosaurio se moverá a la izquierda
                        direccion = D_LEFT;

                        break;
                    case RIGHT:// el dinosaurio se moverá a la izquierda
                        direccion = D_RIGHT;
                        break;
                    case DOWN:// el dinosaurio se moverá a la izquierda
                        direccion = D_DOWN;
                        break;
                    case UP:// el dinosaurio se moverá a la izquierda
                        direccion = D_UP;
                        break;
                }
                tablero.nextDirection(direccion); 
                System.out.println("APP.DIRECCION: "+direccion);
            }
        });                              
    }
    
   
    
    
    public static void main(String[] args) {
        launch();
    }

}