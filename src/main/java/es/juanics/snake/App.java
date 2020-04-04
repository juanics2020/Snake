//Revisar public y private

/*REGLAS DEL JUEGO:
--------------------

1º EL JUGADOR ELIGE EL NIVEL DE DIFICULTAD QUE DESEA DE 1 A 5 (VELOCIDAD DE MOVIEMIENTO DE LA SERPIENTE),
EL NÚMERO DE FILAS Y COLUMNAS QUE QUIERE (para que el juego se adapte automáticamente a estas medidas)

2º SI CHOCA (SALE DE LOS LÍMITES) SE LE PREGUNTA QUE SI QUIERE CONTINUAR:
    - SI QUIERE CONTINUAR, VUELVE A EMPEZAR CON DIFICULTAD 1 Y PUNTUACIÓN 0
    - SI NO QUIERE CONTINUAR, SALE DE LA APLICACIÓN

3º A MEDIDA QUE COME MANZANAS SUBEN LOS PUNTOS Y SUBE DE NIVEL:
    NIVEL INICIAL: DIFICULTAD QUE ELIJA EL JUGADOR
    CADA 10 MANZANAS SUBE UN NIVEL DE DIFICULTAD
    SI COME 50 MANZANAS GANA EL JUEGO Y SALE UNA PANTALLA PARA FELICITARLO

*****SI EL USUARIO COMIENZA EN 5 PUEDE LLEGAR HASTA EL NIVEL 10 DE DIFICULTAD ANTES DE GANAR


Cada vez que se coma una manzana se añadirá una pieza al cuerpo
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

//Siempore es mejor hacer las clases independientes de las demás.
//Por ejemplo: Pasarle las variables por parámetros desde una clase a otra, sin usar el nombre de la otra clase delante (App.filas_totales)
//Mejor se lo paso por parámetros y lo guardo en una variable general


//****le ponemos this para que distinga que es la variable de esta clase, no la que le paso que se llama igual

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    
    
    //Alto y Ancho de la imagen de una parte de la serpiente
    public static final int TAM_PIEZA_SNAKE = 42;//Tamaño que tendrán los visores de las imágenes (Tamaño grafico de cada celda) TAMAÑO RECOMENDADO: 42
           
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
    
    
        //TAMAÑO RECOMENDADO MATRIZ: FILAS TOTALES 13 (0-12), COLUMNAS TOTALES 21 (0-20)
    public static int filas_totales = 13;
    public static int columnas_totales = 21;
    public static int scene_width = columnas_totales*TAM_PIEZA_SNAKE;//TAMAÑO RECOMENDADO: 882. SERÍA EL TAMAÑO DE LAS COLUMNAS DEL TABLERO * TAM_PIEZA_SNAKE
    public static int scene_height = filas_totales*TAM_PIEZA_SNAKE;//TAMAÑO RECOMENDADO: 546. SERÍA EL TAMAÑO DE LAS FILAS DEL TABLERO * TAM_PIEZA_SNAKE
    private static int hbox_height = (int)(scene_height/18.82758620689655); //TAMAÑO RECOMENDADO 29 : (SECENE_HEIGHT/18,82758620689655)
    private static int scene_height_mas_vbox = scene_height + hbox_height;//ALTURA DE LA PANTALLA SUMANDO TABLERO Y HBOX. (VBOX = TABLERO+HBOX)
          
    private int direccion = 2; //La serpiente comienza yendo hacia abajo
     
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
    private final String [] ARRAY_DIFICULTAD = {"1", "2", "3", "4", "5"};
    private final String [] ARRAY_FILAS = {"8", "9", "10", "11", "12","13","14","15","16","17","18","19","20"};
    private final String [] ARRAY_COLUMNAS = {"10", "11", "12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35"};
    
    //(DIFICULTAD 1: Velocidad 1)
    //(DIFICULTAD 2: Velocidad 1.5)
    //(DIFICULTAD 3: Velocidad 2)
    //(DIFICULTAD 4: Velocidad 2.5)
    //(DIFICULTAD 5: Velocidad 3)
    //La velocidad se usará en el timelineSnake de Tablero
    public static String dificultad;//La elige el usuario
    public static double velocidad = 1; //PARA EL TIMELINE DE LA SERPIENTE
    public static String filasList;//La elije el usuario
    public static String columnasList;//La elije el usuario
    
    
    
    @Override
    public void start(Stage stage) {
        
        
        //DIÁLOGO DE OPCIONES (CHOICE DIALOG) DIFICULTAD
        //**********************************************************************
        List<String> listaDificultad = Arrays.asList(ARRAY_DIFICULTAD);
        
        ChoiceDialog dialog = new ChoiceDialog(listaDificultad.get(0), listaDificultad);
        dialog.setTitle("DIFICULTAD");
        dialog.setHeaderText("Elige la dificultad");
       
        // Crear un icono para la imagen del diálogo
        ImageView serp = new ImageView("/images/Snakehead - iz.png");
        serp.setFitHeight(31.5);
        serp.setFitWidth(31.5);
        // Cambiar la imagen
        dialog.getDialogPane().setGraphic(serp);

        Optional<String> result = dialog.showAndWait();
        dificultad = "cancelled.";

        if (result.isPresent()) {//Si elije una opción se guardará la velocidad
            dificultad = result.get();
            velocidad = ((Double.valueOf(dificultad)*0.5)+0.5);
        }//Si no elije nada la velocidad seguirá siendo 1, como estaba inicializada       
        System.out.println("");
        System.out.println("Dificultad: " + dificultad+" ----- Velocidad: "+velocidad);
        //**********************************************************************
        
        
        //DIÁLOGO DE OPCIONES (CHOICE DIALOG) FILAS
        //**********************************************************************
        List<String> listaFilas = Arrays.asList(ARRAY_FILAS);
        
        ChoiceDialog dialogF = new ChoiceDialog(listaFilas.get(5), listaFilas);
        dialogF.setTitle("FILAS");
        dialogF.setHeaderText("Elige las filas");

        // Crear un icono para la imagen del diálogo
        ImageView filasImage = new ImageView("/images/filas.png");
        filasImage.setFitHeight(22);
        filasImage.setFitWidth(70);
        // Cambiar la imagen
        dialogF.getDialogPane().setGraphic(filasImage);
        
        Optional<String> result2 = dialogF.showAndWait();
        filasList = "cancelled.";

        if (result2.isPresent()) {//Si elije una opción se guardará la velocidad
            filasList = result2.get();
            filas_totales = Integer.parseInt(filasList);
        }//Si no elije nada la velocidad seguirá siendo 1, como estaba inicializada       
        System.out.println("Filas: "+filas_totales);
        scene_height = filas_totales*TAM_PIEZA_SNAKE;
        //**********************************************************************
        
        //DIÁLOGO DE OPCIONES (CHOICE DIALOG) COLUMNAS
        //**********************************************************************
        List<String> listaColumnas = Arrays.asList(ARRAY_COLUMNAS);
        
        ChoiceDialog dialogC = new ChoiceDialog(listaColumnas.get(11), listaColumnas);
        dialogC.setTitle("COLUMNAS");
        dialogC.setHeaderText("Elige las columnas");

        // Crear un icono para la imagen del diálogo
        ImageView columnasImage = new ImageView("/images/columnas.png");
        columnasImage.setFitHeight(70);
        columnasImage.setFitWidth(22);
        // Cambiar la imagen
        dialogC.getDialogPane().setGraphic(columnasImage);
        
        Optional<String> result3 = dialogC.showAndWait();
        columnasList = "cancelled.";

        if (result3.isPresent()) {//Si elije una opción se guardará la velocidad
            columnasList = result3.get();
            columnas_totales = Integer.parseInt(columnasList);
        }//Si no elije nada la velocidad seguirá siendo 1, como estaba inicializada       
        System.out.println("Columnas: "+columnas_totales);
        System.out.println("");
        scene_width = columnas_totales*TAM_PIEZA_SNAKE;
        //**********************************************************************
        
        hbox_height = (int)(scene_height/18.82758620689655); //TAMAÑO RECOMENDADO 29 : (SECENE_HEIGHT/18,82758620689655)
        scene_height_mas_vbox = scene_height + hbox_height;//ALTURA DE LA PANTALLA SUMANDO TABLERO Y HBOX. (VBOX = TABLERO+HBOX)
        
                
        
        StackPane root = new StackPane();//contenedor principal(stackpane)
        var scene = new Scene(root, scene_width, scene_height_mas_vbox);//*********************<--------------------------ME HACEN FALTA LOS DIALOGOS ANTES
        stage.setScene(scene);
        stage.setResizable(false);//Para que el usuario no pueda cambiar el tamaño de la pantalla
        stage.show();
        stage.setTitle("SNAKE");
        
        //CAMBIARLE EL ICONO A LA STAGE.
        //CUANDO SE ABRA EL JUEGO SALDRÁ EL ICONO DE LA CABEZA DE LA SERPIENTE EN LA BARRA DE TAREAS Y EL DIBUJITO AL LADO DEL TÍTULO
        stage.getIcons().add(new Image("/images/Snakehead - iz.png"));
        
        String style = "-fx-background-color: lightblue;"; //COLOR QUE LE VOY A DAR AL STACKPANE
        root.setStyle(style);
        
        System.out.println("WINDOW: "+scene.getFocusOwner());
        
        //VOBOX (HBOX  con text + puntuacion + text + dificultad) + (Tablero)
        //**********************************************************************
        panePuntuacion = new VBox();       
        root.getChildren().add(panePuntuacion);
    
        HBox panePuntHor = new HBox();
        panePuntHor.setSpacing(10);
        
        //---
        //Texto de etiqueta para la puntuación
        Text textTitleScore = new Text("Puntuación: ");
        textTitleScore.setFont(Font.font("Arial Black", 20));
        textTitleScore.setFill(Color.BLACK);
        //Texto para la puntuación
        textScore = new Text(String.valueOf(0));
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
        panePuntHor.setMinHeight(hbox_height);
        panePuntHor.setMaxHeight(hbox_height);
        
        panePuntHor.getChildren().add(textTitleScore);
        panePuntHor.getChildren().add(textScore);
        panePuntHor.getChildren().add(textTitleDificulty);
        panePuntHor.getChildren().add(textDificulty);
        panePuntuacion.getChildren().add(panePuntHor);
        //**********************************************************************
              
        //Tablero incluído en VBOX
        //el tablero ya contiene la cabeza de la serpiente y la manzana
        Tablero tablero = new Tablero(scene_width, scene_height, filas_totales, columnas_totales,dificultad, velocidad);//Le paso las variables al tablero
        
        ImageView tabImage = new ImageView("/images/laser-588995_960_720.jpg");//Fondo del tablero
        tabImage.setFitHeight(scene_height);
        tabImage.setFitWidth(scene_width);               
        tablero.getChildren().add(tabImage);
        tabImage.toBack();//Para poner la imagen de fondo
        
        
        panePuntuacion.getChildren().add(tablero);
        // Primero hacemos un retardo para que el usuario esté preparado y después se moverá la serpiente
        tablero.reatrdoInicioPartida(direccion);      
        
        
        
        //NO CREARÉ UN SNAKEGAME NUEVO. SÓLO USARÉ EL QUE HE CREADO EN TABLERO PARA PORQUE SI NO TENDRÍA DOS SNAKEGAMES QUE NO COINCIDEN
        //SnakeGame snakeGame = new SnakeGame();
         
        
        
        //Layout principal para VBOX (MENSAJE CONTINUAR)
        //**********************************************************************
        paneContinuar = new VBox();
        paneContinuar.setMinHeight(scene_height);
        paneContinuar.setTranslateY(0);       
        paneContinuar.setMinWidth(scene_width);
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
        paneWin.setMinHeight(scene_height);
        paneWin.setTranslateY(0);       
        paneWin.setMinWidth(scene_width);
        paneWin.setTranslateX(0);
        paneWin.setAlignment(Pos.CENTER);
        paneWin.setSpacing(100);
        paneWin.setStyle("-fx-background-color: #76cdd6;");
        root.getChildren().add(paneWin);
        //Texto de etiqueta de HAS GANADO
        Text textTitleWin = new Text("ENHORA BUENA, HAS GANADO");
        textTitleWin.setFont(Font.font("Cooper Black", (filas_totales*3)));//Para que el título grandre tenga una proporción con la medida de la pantalla
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
        textP = new Text(String.valueOf(0));//la dificultad que selecciona el usuario en el diálogo
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