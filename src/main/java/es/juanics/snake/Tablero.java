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
     
    private static Apple apple1;//Crear un objeto de tipo Apple  
    private int siguienteDireccion = App.D_DOWN; //Cuando pulse las teclas guardará la siguiente direccion (comenzará hacia abajo)
    private int direccionActual = App.D_DOWN;//Cogerá la nueva dirección que le den las teclas (comenzará hacia abajo)
     
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
        snake1.setLayoutY((App.TAM_PIEZA_SNAKE*matFilaIni)-App.TAM_PIEZA_SNAKE);//Le resto la imagen porque la Y de la imgaen es la esquina superior
        
        
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
    
    
    public void reiniciar(){
        App.puntuacion=0;
        App.textScore.setText(String.valueOf(App.puntuacion));
        snakeGame.emptyApple();
        snakeGame.setAppleRandom();
        this.setImageApple();
        
        //LE PASO LA POSICIÓN INICIAL DE LA MATRIZ A SnakeGame para que vaya cambiando posicones Actuales de fila y columna al pulsar teclas
        snakeGame.inicioFilaColActual(matFilaIni, matColumnaIni);
        snakeGame.matrizTablero[matFilaIni][matColumnaIni] = App.NUM_HEAD;//Pongo el 1 a mitad de la matriz que corresponde a la Cabeza de la serpiente      
        snakeGame.mostrarMatrizConsola();//Muestro la matriz del tablero en la consola
            
        //Colocar Imagen de la cabeza de la serpiente a la mitad de la matriz
        snake1.setLayoutX(App.TAM_PIEZA_SNAKE*matColumnaIni);
        snake1.setLayoutY(App.TAM_PIEZA_SNAKE*matFilaIni);
        snake1.setHead(App.D_DOWN);
        
        direccionActual = App.D_DOWN;
        siguienteDireccion= App.D_DOWN;
    }
    

    public void panelVisible(){
        App.paneContinuar.setVisible(true);
    }
    
    public void panelInvisible(){
        App.paneContinuar.setVisible(false);
    }
    
    
    
    //PASAR DEL APP AL TABLERO LA DIRECCIÓN SIGUIENTE
    public void nextDirection(int direccion){
        siguienteDireccion = direccion;
        System.out.println("2. Siguiente DIRECCIÓN: "+siguienteDireccion);
    }
    
    
    public void snakeMovement(int direccion) {// MOVIMIENTO GRÁFICO DE LA SERPIENTE
        direccionActual = direccion; //La dirección que le digamos por teclado. Comienza hacia abajo
        
        //El timeline se para cuando llamamos a la función si ya se había creado
        //la primera vez. Si no, crea un timeline nuevo cada vez que se llame a la función y se pisan.
        if (timelineSnake != null) {
            timelineSnake.stop();
        }
        timelineSnake = new Timeline(//Sirve para lo que lo que metamos aquí. Podemos utilizar varios TimeLine con diferentes velocidades para diferentes cosas
                // 0.017 ~= 60 FPS (equivalencia de segundos a Frames por Segundo)
                new KeyFrame(Duration.seconds(0.008), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent ae) {//Sólo puede haber un handle en el timeline

                        switch (direccionActual) {//Según la tecla pulsada
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
                        //System.out.println("1. Direccion ACTUAL: " + direccion);                        
                    }
                })
        );
        timelineSnake.setCycleCount(App.TAM_PIEZA_SNAKE);//Llama al método setCycleCount(Timeline.INDEFINITE)
        timelineSnake.play(); //Llama al método Play para echar a andar la animación       
        timelineSnake.setOnFinished(event -> {//CUANDO TERMINE EL TIMELINE
            direccionActual = siguienteDireccion;//Sel a he pasado por el método next Direction desde el App            
            try {
                //Hacer el movimiento lógico en la matriz en SnakeGame (nos pasará si se come la manzana o no)
                boolean eaten = this.snakeGame.matrixMovement(direccionActual);
                this.snakeMovement(direccionActual);//Hacer el movimiento de la snake1 en el Tablero
                if (eaten == true){//Si se come la manzana movemos la imagen a la posición nueva
                    this.setImageApple();
                }
                               
            } catch(ArrayIndexOutOfBoundsException e) { //SI choca con los llímites MUERE******
                System.out.println("HAS MUERTO");
                timelineSnake.stop();
                this.panelVisible();//Oculta el panel principal y muestra el panel de continuar
                
                App.botonYes.setOnAction((ActionEvent en) -> {//SI PULSAMOS EL BOTÓN SÍ
                    panelInvisible();//Muestra el panel principal y oculta el panel de continuar
                    System.out.println("Vamos a Continuar");
                    this.reiniciar();
                    timelineSnake.play();
                });
                App.botonNO.setOnAction((ActionEvent en) -> {
                    System.exit(0);
                });
            } 
                     
	});
 
    }
}
           