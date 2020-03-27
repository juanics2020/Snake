package es.juanics.snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.Light.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private boolean eaten  = false; //Guardará la variable eaten de la clase SnakeGame que nos devuelve el método matrixMovement
    
    
    
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
            line.setStroke(Color.DARKGOLDENROD);
            this.getChildren().add(line); //añade la linea a esta misma clase Tablero (que es el panel)
        }
        for(int i=0;i<=filas;i++){
            Line line = new Line(0, App.TAM_PIEZA_SNAKE*i, App.TAM_PIEZA_SNAKE*columnas, App.TAM_PIEZA_SNAKE*i);//X inicial, Y inicial, X final, Y final) La separación será el tamaño del visor de la serpiente
            line.setStroke(Color.DARKGOLDENROD);
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
        
        //Llamos al método para meter el primer puntero de la cabeza en el ArrayList de la serpiente en la posición inicial de la cabeza
        snakeGame.setPunteroArrayList(matFilaIni, matColumnaIni);

        
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
        App.puntuacion = 0;
        App.dificultad = "1";
        App.velocidad = ((Double.valueOf(App.dificultad)*0.5)+0.5);
        App.textScore.setText(String.valueOf(App.puntuacion));
        App.textDificulty.setText(App.dificultad);
        snakeGame.emptyApple();
        snakeGame.setAppleRandom();
        this.setImageApple();
        
        //LE PASO LA POSICIÓN INICIAL DE LA MATRIZ A SnakeGame para que vaya cambiando posicones Actuales de fila y columna al pulsar teclas
        snakeGame.inicioFilaColActual(matFilaIni, matColumnaIni);
        snakeGame.matrizTablero[matFilaIni][matColumnaIni] = App.NUM_HEAD;//Pongo el 1 a mitad de la matriz que corresponde a la Cabeza de la serpiente      
        snakeGame.mostrarMatrizConsola();//Muestro la matriz del tablero en la consola
            
        //Colocar Imagen de la cabeza de la serpiente a la mitad de la matriz
        snake1.setLayoutX(App.TAM_PIEZA_SNAKE*matColumnaIni);
        snake1.setLayoutY((App.TAM_PIEZA_SNAKE*matFilaIni)-App.TAM_PIEZA_SNAKE);
        snake1.setHead(App.D_DOWN);
        
        direccionActual = App.D_DOWN;
        siguienteDireccion= App.D_DOWN;
        //Para comenzar primero el retardo al comenzar la partida y después salta automáticamente a mover la serpiente
        this.reatrdoInicioPartida(direccionActual);
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
    

    //PONE LA IMAGEN DE LA SERPIENTE EN LA DIRECCIÓN QUE LE CORRESPONDA  
    public void switchImagenSnake(int direccion){      
        switch (direccionActual) {//Según la tecla pulsada
            case App.D_LEFT:// la serpiente se moverá a la izquierda
                snake1.setHead(App.D_LEFT);//cambio el sentido de la cabeza de la serpiente                                
                break;
            case App.D_RIGHT:// la serpiente se moverá a la derecha
                snake1.setHead(App.D_RIGHT);//cambio el sentido de la cabeza de la serpiente
                break;
            case App.D_DOWN:// la serpiente se moverá abajo);
                snake1.setHead(App.D_DOWN);//cambio el sentido de la cabeza de la serpiente
                break;
            case App.D_UP:// la serpiente se moverá arriba                            
                snake1.setHead(App.D_UP);//cambio el sentido de la cabeza de la serpiente
                break;
        }           
    } 
    
    //AL INICIAR SIEMPRE LA PARTIDA HAY QUE HACER UN RETARDO ANTES DE QUE EMPIECE A MOVERSE LA SERPIENTE
    public void reatrdoInicioPartida(int direccion){
        Timeline timelineRetardo = new Timeline(//Sirve para lo que lo que metamos aquí. Podemos utilizar varios TimeLine con diferentes velocidades para diferentes cosas
                // 0.017 ~= 60 FPS (equivalencia de segundos a Frames por Segundo)
            new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {//Sólo puede haber un handle en el timeline
                    System.out.println("Retardo.....");
                }
            })
        );
        timelineRetardo.setCycleCount(1);
        timelineRetardo.play();
        //Una vez que termine el retardo del inicio comenzamos a mover la serpiente
        timelineRetardo.setOnFinished(eventR -> {          
            this.snakeMovement(direccion);                     
	});
    }
    
    
    public void snakeMovement(int direccion) {// MOVIMIENTO GRÁFICO DE LA SERPIENTE
        direccionActual = direccion; //La dirección que le digamos por teclado. Comienza hacia abajo
        
        //EL TIMELINE IRÁ MOVIENDO LA SERPIENTE UNA CASILLA
        //El timeline se para cuando llamamos a la función si ya se había creado
        //la primera vez. Si no, crea un timeline nuevo cada vez que se llame a la función y se pisan.
        if (timelineSnake != null) {
            timelineSnake.stop();
        }
        timelineSnake = new Timeline(//Sirve para lo que lo que metamos aquí. Podemos utilizar varios TimeLine con diferentes velocidades para diferentes cosas
                // 0.017 ~= 60 FPS (equivalencia de segundos a Frames por Segundo)
            new KeyFrame(Duration.seconds(0.008/App.velocidad), new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {//Sólo puede haber un handle en el timeline
                    //Si se ha comido la manzana la vuelvo a colocar la imagen en su lugar correspondiente
                    //Si no coloco la imagen cuando llega aquí, quita la manzana antes de hacer la pausa y no se nota el efecto
                    if(eaten== true){
                        apple1.setImage(apple1.appleImage);// Ponemos la manzana normal otra vez
                        setImageApple(); //colocamos la manzana en su nuevo sitio
                    }
                    //***DENTRO DEL TIMELINE NO DEJA LLAMAR A LOS MÉTODOS CON THIS***
                    //**OJO!!!!!
                    
                    //PRIMERO PONGO LA IMAGEN DE LA CABEZA DE LA SERPIENTE MIRANDO A LA DIRECCIÓN CORRESPONDIENTE
                    switchImagenSnake(direccionActual); 
                    //MUEVO LA IMAGEN DE LA SERPIENTE A DONDE CORRESPONDA
                    if(direccionActual==App.D_LEFT){
                        snake1.setLayoutX(snake1.getLayoutX() - 1);                        
                    }else if(direccionActual==App.D_RIGHT){
                        snake1.setLayoutX(snake1.getLayoutX() + 1);                   
                    }else if(direccionActual==App.D_DOWN){
                        snake1.setLayoutY(snake1.getLayoutY() + 1);                        
                    }else if(direccionActual==App.D_UP){
                        snake1.setLayoutY(snake1.getLayoutY() - 1);                       
                    }                                    
                }
            })
        );
        timelineSnake.setCycleCount(App.TAM_PIEZA_SNAKE);
        timelineSnake.play(); //Llama al método Play para echar a andar la animación       
        //CUANDO TERMINE EL TIMELINE COMPROBAMOS LÍMITES, SI SE HA COMIDO LA MANZANA Y LO VOLVEMOS A ARRANCAR
        timelineSnake.setOnFinished(event -> {          
            direccionActual = siguienteDireccion;//Sel a he pasado por el método next Direction desde el App
            //HAGO ESTE SWITCH PARA QUE CUANDO HAGA LA PAUSA QUEDE MIRANDO A DONDE ESTABA LA MANZANA CUANDO SE LA HA COMIDO
            this.switchImagenSnake(direccionActual);                                  
            try {//Si no choca con los límites
                //Hacer el movimiento lógico en la matriz en SnakeGame (nos pasará si se come la manzana o no)
                eaten = this.snakeGame.matrixMovement(direccionActual);
                this.snakeMovement(direccionActual);//Hacer el movimiento de la snake1 en el Tablero
                if (eaten == true){//Si se come la manzana movemos la imagen a la posición nueva
                    timelineSnake.stop();
                    apple1.setImage(apple1.appleImageBitten); //Cambiar imagen manzana mordida
                    timelineSnake.setDelay(Duration.seconds(0.3));
                    timelineSnake.play(); 
                    snakeGame.appleEatenM();
                    //SUBIR DE NIVEL
                    if(App.puntuacion==10 || App.puntuacion==20 || App.puntuacion==30 || App.puntuacion==40){ //SI COME 10 MANZANAS SUBE LA DIFICULTAD Y LA VELOCIDAD
                        App.dificultad=  Integer.toString(Integer.valueOf(App.dificultad)+1);
                        App.textDificulty.setText(App.dificultad);
                        App.velocidad = App.velocidad+0.5;
                        System.out.println("HAS SUBIDO DE NIVEL: "+" [DIFICULTAD: "+App.dificultad+"] "+"[VELOCIDAD: "+App.velocidad+"]");
                    }else if(App.puntuacion==50){
                        timelineSnake.stop();
                        App.textP.setText(String.valueOf(App.puntuacion));//Actualizo marcador puntuación de la ventana ganar
                        App.textD.setText(App.dificultad);//Actualizo marcador dificultad de la ventana ganar                        
                        App.paneWin.setVisible(true);
                        System.out.println("¡¡¡ENHORABUENA HAS GANADO!!!");
                    }
                }
                               
            } catch(ArrayIndexOutOfBoundsException e) { //SI choca con los llímites MUERE******
                System.out.println("HAS MUERTO");
                timelineSnake.stop();
                this.panelVisible();//Oculta el panel principal y muestra el panel de continuar
                
                App.botonYes.setOnAction((ActionEvent en) -> {//SI PULSAMOS EL BOTÓN SÍ
                    panelInvisible();//Muestra el panel principal y oculta el panel de continuar
                    System.out.println("Vamos a Continuar");
                    this.reiniciar();
                });
                App.botonNO.setOnAction((ActionEvent en) -> {
                    System.exit(0);
                });
            }                      
	});
    }
}
           