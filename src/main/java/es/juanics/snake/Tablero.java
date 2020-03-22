package es.juanics.snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Tablero extends Pane {//LA CLASE TABLERO HEREDA LAS PROPIEDADES, MÉTODOS,... DE LA CLASE PANE (extends)
//*****La clase en sí es el panel
    

    private Snake snake1;//Crear un objeto de tipo Snake
    private Timeline timelineSnake;   
    public SnakeGame snakeGame;   //objeto de tipo SnakeGame para poder usar todos los métodos y parámetros de SnakeGame
    private int filas;//Guardará filas totales de la Matriz(desde SnakeGame)
    private int columnas;//Guardará columnas totales de la Matriz(desde SnakeGame)
    private int matFilaIni;//posición inicial de la cabeza en la FILA dentro de la matriz
    private int matColumnaIni;//posición inicial de la cabeza en la COLUMNA dentro de la matriz    
     
    private Apple apple1;//Crear un objeto de tipo Apple  
     
     
    public Tablero(int width, int height) {//pone la serpiente en el tablero //Método constructor (new Tablero)
      
        //Pongo el tablero al ancho y alto de la escena
        this.setWidth(width);
        this.setHeight(height);      
        
        snakeGame = new SnakeGame();//Objeto SnakeGame para crear la matriz del tablero
        
        //pone la serpiente en el tablero
        snake1 = new Snake(1);
        this.getChildren().add(snake1);
        
        //Le paso las medidas de la escena y el tablero a SnakeGame para que calcule el tamaño de la Matriz
        snakeGame.tamañoMatriz(width, height);
                    
        //PINTAR LA CUADRÍCULA DEL TABLERO SEGÚN LAS FILAS Y COLUMNAS DE LA MATRIZ
        //Primero obtengo los datos que necesito de las otras Clases
        filas = snakeGame.getNumFilasMatriz();
        columnas = snakeGame.getNumColumnasMatriz();
        
        for(int i=0;i<=columnas;i++){
            Line line = new Line(App.TAM_PIEZA_SNAKE*i, 0, App.TAM_PIEZA_SNAKE*i, App.TAM_PIEZA_SNAKE*filas);//X inicial, Y inicial, X final, Y final) La separación será el tamaño del visor de la serpiente
            this.getChildren().add(line); //añade la linea a esta misma clase Tablero (que es el panel)
        }
        for(int i=0;i<=filas;i++){
            Line line = new Line(0, App.TAM_PIEZA_SNAKE*i, App.TAM_PIEZA_SNAKE*columnas, App.TAM_PIEZA_SNAKE*i);//X inicial, Y inicial, X final, Y final) La separación será el tamaño del visor de la serpiente
            this.getChildren().add(line); //añade la linea a esta misma clase Tablero (que es el panel)
        }
        
        
        
        //pongo a 1 la posición que corresponde lógicamente a la cabeza de la serpiente en la matriz          
        matFilaIni = (int)(filas/2);//Calculo la fila que cae a la mitad de la Matriz
        matColumnaIni = (int)(columnas/2);//Calculo la columna que cae a la mitad de la Matriz
        
        //LE PASO LA POSICIÓN INICIAL DE LA MATRIZ A SnakeGame para que vaya cambiando posicones Actuales de fila y columna al pulsar teclas
        snakeGame.inicioFilaColActual(matFilaIni, matColumnaIni);
        
        System.out.println("Mitad filas: "+matFilaIni);
        System.out.println("Mitad columnas: "+matColumnaIni);
                
        snakeGame.matrizTablero[matFilaIni][matColumnaIni] = App.NUM_HEAD;//Pongo el 1 a mitad de la matriz que corresponde a la Cabeza de la serpiente      
        snakeGame.mostrarMatrizConsola();//Muestro la matriz del tablero en la consola
               
        //Colocar Imagen de la cabeza de la serpiente a la mitad de la matriz
        snake1.setLayoutX(App.TAM_PIEZA_SNAKE*matColumnaIni);
        snake1.setLayoutY(App.TAM_PIEZA_SNAKE*matFilaIni);
        
        
        //--------------MANZANA
        //Colocar la manzana en un sitio aleatorio donde no esté la serpiente
        //La X corresponde a las columnas y la Y corresponde a las filas   
        //Llamo al método de abajo. Lo volveremos a usar en SnakeGame cada vez que la serpiente se coma la manzana
        snakeGame.setAppleRandom();//Calculamos posición de la manzana y la colocamos en la matriz con un 4 sin pisar la serpiente
        apple1 = new Apple();
        this.setImageApple();
        this.getChildren().add(apple1);   
    }   
    
    public void setImageApple(){
        apple1.setLayoutX(App.TAM_PIEZA_SNAKE*snakeGame.appleCol);
        apple1.setLayoutY(App.TAM_PIEZA_SNAKE*snakeGame.appleFil);       
    }
    
    public void snakeMovement(int direccion) {// MOVIMIENTO GRÁFICO DE LA SERPIENTE
        //El timeline se para cuando llamamos a la función si ya se había creado
        //la primera vez. Si no, crea un timeline nuevo cada vez que se llame a la función y se pisan.
        if (timelineSnake != null) {
            timelineSnake.stop();
        }
        timelineSnake = new Timeline(//Sirve para lo que lo que metamos aquí. Podemos utilizar varios TimeLine con diferentes velocidades para diferentes cosas
                // 0.017 ~= 60 FPS (equivalencia de segundos a Frames por Segundo)
                new KeyFrame(Duration.seconds(0.008), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent ae) {//Sólo puede haber un handle en el timeline

                        switch (direccion) {//Según la tecla pulsada
                            case App.D_LEFT:// la serpiente se moverá a la izquierda
                                snake1.setLayoutX(snake1.getLayoutX() - 1);
                                snake1.setHead(App.D_LEFT);//cambio el sentido de la cabeza de la serpiente                                
                                break;
                            case App.D_RIGHT:// la serpiente se moverá a la derecha
                                snake1.setLayoutX(snake1.getLayoutX() + 1);
                                snake1.setHead(App.D_RIGHT);//cambio el sentido de la cabeza de la serpiente
                                break;
                            case App.D_DOWN:// la serpiente se moverá abajo
                                snake1.setLayoutY(snake1.getLayoutY() + 1);
                                snake1.setHead(App.D_DOWN);//cambio el sentido de la cabeza de la serpiente
                                break;
                            case App.D_UP:// la serpiente se moverá arriba
                                snake1.setLayoutY(snake1.getLayoutY() - 1);
                                snake1.setHead(App.D_UP);//cambio el sentido de la cabeza de la serpiente
                                break;
                        }
                        //System.out.println("Direccion: " + direccion + " X: " + snake1.getLayoutX()+" Y: " + snake1.getLayoutY());                        
                    }
                })
        );
        timelineSnake.setCycleCount(App.TAM_PIEZA_SNAKE);//Llama al método setCycleCount(Timeline.INDEFINITE)
        timelineSnake.play(); //Llama al método Play para echar a andar la animación       
    }
}
           