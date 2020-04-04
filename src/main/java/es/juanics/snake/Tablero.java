package es.juanics.snake;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class Tablero extends Pane {//LA CLASE TABLERO HEREDA LAS PROPIEDADES, MÉTODOS,... DE LA CLASE PANE (extends)
//*****La clase en sí es el panel
    
    
    private int filas = 0;//variable para total filas matriz   
    private int columnas = 0;//variable para total columnas matriz

    private String dificultad;
    private double velocidad;
    
    private Snake snake1;//Crear un objeto de tipo Snake
    private Timeline timelineSnake;//Moverá la cabeza
    public SnakeGame snakeGame;   //objeto de tipo SnakeGame para poder usar todos los métodos y parámetros de SnakeGame
    private int matFilaIni;//posición inicial de la cabeza en la FILA dentro de la matriz
    private int matColumnaIni;//posición inicial de la cabeza en la COLUMNA dentro de la matriz    
     
    private static Apple apple1;//Crear un objeto de tipo Apple  
    private int siguienteDireccion = App.D_DOWN; //Cuando pulse las teclas guardará la siguiente direccion (comenzará hacia abajo)
    private int direccionActual = App.D_DOWN;//Cogerá la nueva dirección que le den las teclas (comenzará hacia abajo)

    //ArrayList que guardará las imágenes para las imágenes de las serpientes (de tipo Snake para decirle cuál quiero cambiar)
    private static ArrayList<Snake>arrayListImagenes = new ArrayList();
    private boolean pendienteCrecimiento = false;
    private int puntero;
    
    
    
    public Tablero(int width, int height, int filas, int columnas, String dificultad, double velocidad) {//pone la serpiente en el tablero //Método constructor (new Tablero)
        this.filas = filas; //****le ponemos this para que distinga que es la variable de esta clase, no la que le paso que se llama igual
        this.columnas = columnas;
        this.dificultad = dificultad;
        this.velocidad = velocidad;
        //Pongo el tablero al ancho y alto de la escena
        this.setWidth(width);
        this.setHeight(height);      
        
        snakeGame = new SnakeGame(this.filas,this.columnas);//Objeto SnakeGame para crear la matriz del tablero
        
        //pone la serpiente en el tablero
        snake1 = new Snake(1);
        arrayListImagenes.add(snake1);
        this.getChildren().add(snake1);
        
                    
        //PINTAR LA CUADRÍCULA DEL TABLERO SEGÚN LAS FILAS Y COLUMNAS DE LA MATRIZ       
        for(int i=0;i<=this.columnas;i++){
            Line line = new Line(App.TAM_PIEZA_SNAKE*i, 0, App.TAM_PIEZA_SNAKE*i, App.TAM_PIEZA_SNAKE*this.filas);//X inicial, Y inicial, X final, Y final) La separación será el tamaño del visor de la serpiente
            line.setStroke(Color.DARKGOLDENROD);
            this.getChildren().add(line); //añade la linea a esta misma clase Tablero (que es el panel)
        }
        for(int i=0;i<=this.filas;i++){
            Line line = new Line(0, App.TAM_PIEZA_SNAKE*i, App.TAM_PIEZA_SNAKE*this.columnas, App.TAM_PIEZA_SNAKE*i);//X inicial, Y inicial, X final, Y final) La separación será el tamaño del visor de la serpiente
            line.setStroke(Color.DARKGOLDENROD);
            this.getChildren().add(line); //añade la linea a esta misma clase Tablero (que es el panel)
        }
        
        
        
        //pongo a 1 la posición que corresponde lógicamente a la cabeza de la serpiente en la matriz          
        matFilaIni = (int)(this.filas/2);//Calculo la fila que cae a la mitad de la Matriz
        matColumnaIni = (int)(this.columnas/2);//Calculo la columna que cae a la mitad de la Matriz
        
        //LE PASO LA POSICIÓN INICIAL DE LA MATRIZ A SnakeGame para que vaya cambiando posicones Actuales de fila y columna al pulsar teclas
        snakeGame.inicioFilaColActual(matFilaIni, matColumnaIni);
        
        System.out.println("Mitad filas: "+matFilaIni);
        System.out.println("Mitad columnas: "+matColumnaIni);   
        
        //Llamos al método para meter el primer puntero de la cabeza en el ArrayList de la serpiente en la posición inicial de la cabeza
        int punt  = snakeGame.setPunteroArrayList(matFilaIni, matColumnaIni, App.D_DOWN);
        System.out.println("Puntero Cabeza Serpiente: "+punt);
        
        //Colocar Imagen de la cabeza de la serpiente a la mitad de la matriz
        arrayListImagenes.get(0).setLayoutX(App.TAM_PIEZA_SNAKE*matColumnaIni);
        arrayListImagenes.get(0).setLayoutY((App.TAM_PIEZA_SNAKE*matFilaIni)-App.TAM_PIEZA_SNAKE);//Le resto la imagen porque la Y de la imgaen es la esquina superior
               
        //--------------MANZANA
        //Colocar la manzana en un sitio aleatorio donde no esté la serpiente
        //La X corresponde a las columnas y la Y corresponde a las filas   
        //Llamo al método de abajo. Lo volveremos a usar en SnakeGame cada vez que la serpiente se coma la manzana
        snakeGame.setAppleRandom();//Calculamos posición de la manzana y la colocamos en la matriz con un 4 sin pisar la serpiente
        apple1 = new Apple();
        this.setImageApple();
        this.getChildren().add(apple1); 
            
        snakeGame.mostrarMatrizConsola();//Muestro la matriz del tablero en la consola
    }   
    
    public void setImageApple(){
        apple1.setLayoutX(App.TAM_PIEZA_SNAKE*snakeGame.appleCol);
        apple1.setLayoutY(App.TAM_PIEZA_SNAKE*snakeGame.appleFil);       
    }
    
    
    public void reiniciar(){
        snakeGame.eaten = false;
        snakeGame.dead = false;
        snakeGame.puntuacion = 0;
        dificultad = "1";
        velocidad = ((Double.valueOf(dificultad)*0.5)+0.5);
        App.textScore.setText(String.valueOf(snakeGame.puntuacion));
        App.textDificulty.setText(dificultad);
              
        //LE PASO LA POSICIÓN INICIAL DE LA MATRIZ A SnakeGame para que vaya cambiando posicones Actuales de fila y columna al pulsar teclas
        snakeGame.inicioFilaColActual(matFilaIni, matColumnaIni);
        snakeGame.matrizTablero[matFilaIni][matColumnaIni] = App.NUM_HEAD;//Pongo el 1 a mitad de la matriz que corresponde a la Cabeza de la serpiente      
        
        
        //PONGO LA MATRIZ A 0 EN TODAS LAS POSICIONES DE LA CABEZA, EL CUERPO Y COLA DE LA SERPIENTE
        for(int c=(snakeGame.arrayListCuerpo.size()-1); c>=0; c--){                               
            snakeGame.matrizTablero[(int)snakeGame.arrayListCuerpo.get(c).getX()][(int)snakeGame.arrayListCuerpo.get(c).getY()] = App.NUM_EMPTY;            
        } 
                    
        //ELIMINO TODOS LOS PUNTEROS DEL ARRAYLIST DE LA SERPIENTE ESCEPTO LA CABEZA
        for(int c=(snakeGame.arrayListCuerpo.size()-1); c>0; c--){                               
            snakeGame.arrayListCuerpo.remove(c);    
        }                  

//        //OTRA FORMA DE VACIAR EL ARRAYLIST        
//        snakeGame.arrayListCuerpo.clear();
//        snakeGame.setPunteroArrayList(matFilaIni, matColumnaIni);


        //ELIMINO TODOS LOS PUNTEROS DEL ARRAYLIST DEL CUERPO DE LA SERPIENTE ESCEPTO LA CABEZA
        for(int c=(arrayListImagenes.size()-1); c>0; c--){                                                
            this.getChildren().remove(arrayListImagenes.get(c));//primero quito el elemento del tablero
            arrayListImagenes.remove(c);//y luegoo lo quito del arraylist
        } 


        //COLOCAR LAS POSICIONES INICIALES EN EL PUNTERO DE LA CABEZA EN ARRAYLIST
        snakeGame.arrayListCuerpo.get(0).setX(matColumnaIni);//OJO, que la X guarda la columna y la Y la fila
        snakeGame.arrayListCuerpo.get(0).setY(matFilaIni);
        snakeGame.arrayListCuerpo.get(0).setZ(App.D_DOWN);
        
        //El contador para hacer cambios en punteros a 0 (porque la cabeza no la borro del array)
        //Siempre 1 menos que el tamaño del arrayListCuerpo
        snakeGame.contadorArray=0;
          
        //Colocar Imagen de la cabeza de la serpiente a la mitad de la matriz
        arrayListImagenes.get(0).setLayoutX(App.TAM_PIEZA_SNAKE*matColumnaIni);
        arrayListImagenes.get(0).setLayoutY((App.TAM_PIEZA_SNAKE*matFilaIni)-App.TAM_PIEZA_SNAKE);//Le resto la imagen porque la Y de la imgaen es la esquina superior
        arrayListImagenes.get(0).setHead(App.D_DOWN);
        
        direccionActual = App.D_DOWN;
        siguienteDireccion= App.D_DOWN;
        //Para comenzar primero el retardo al comenzar la partida y después salta automáticamente a mover la serpiente
        
        snakeGame.emptyApple();
        snakeGame.setAppleRandom();
        this.setImageApple();
        
        snakeGame.mostrarArrayListConsola();
        snakeGame.mostrarMatrizConsola();//Muestro la matriz del tablero en la consola
               
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
                arrayListImagenes.get(0).setHead(App.D_LEFT);//cambio el sentido de la cabeza de la serpiente                                
                break;
            case App.D_RIGHT:// la serpiente se moverá a la derecha
                arrayListImagenes.get(0).setHead(App.D_RIGHT);//cambio el sentido de la cabeza de la serpiente
                break;
            case App.D_DOWN:// la serpiente se moverá abajo);
                arrayListImagenes.get(0).setHead(App.D_DOWN);//cambio el sentido de la cabeza de la serpiente
                break;
            case App.D_UP:// la serpiente se moverá arriba                            
                arrayListImagenes.get(0).setHead(App.D_UP);//cambio el sentido de la cabeza de la serpiente
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
    
    public void deadSnake(){
        System.out.println("HAS MUERTO");
        this.panelVisible();//Oculta el panel principal y muestra el panel de continuar

        App.botonYes.setOnAction((ActionEvent en) -> {//SI PULSAMOS EL BOTÓN SÍ
            panelInvisible();//Muestra el panel principal y oculta el panel de continuar
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------Vamos a Continuar");
            this.reiniciar();
        });
        App.botonNO.setOnAction((ActionEvent en) -> {
            System.exit(0);
        });
    }
    
    
    
    //Le paso la parte del cuerpo (2 cuerpo o 3 cola) y el puntero del arrayListCuerpo que le corresponde
    public void setNewSnakeIntoArray(int parte, int puntero){
        //arrayListImagenes siempre tiene un elemento menos que arrayListCuerpo(la cabeza)
        
        //pone la nueva parte en el array de las imágenes de la serpiente
        //El array 0 será la cola la primera vez. La cabeza no entra dentro del array de imágenes
        //Después intercambiaremos la cola por el cuerpo y ya permanecerá en último lugar (arrayListImagenes.size()-1)
        Snake snakeV = new Snake(parte);
        //Ponemos la parte en la posición que le corresponde a su puntero
        snakeV.setLayoutX(App.TAM_PIEZA_SNAKE*snakeGame.arrayListCuerpo.get(puntero).getY());
        snakeV.setLayoutY(App.TAM_PIEZA_SNAKE*snakeGame.arrayListCuerpo.get(puntero).getX());//-App.TAM_PIEZA_SNAKE);//Le resto la imagen porque la Y de la imgaen es la esquina superior
        
      
        arrayListImagenes.add(snakeV);
        this.getChildren().add(snakeV);//Añado el elemento al tablero
        //this.getChildren().add(arrayListImagenes.get(arrayListImagenes.size()-1));//Añado el elemento al tablero
        
    }
  
    
    //(Mostramos lo que tiene el arraylist de el cuerpo en la consola)
    public void mostrarCuerpoConsola(){       
        
        System.out.println("******************************************************"); 
        for(int c=0; c<arrayListImagenes.size(); c++){
            System.out.println("-----Tamaño ArrayList(NºPARTES CUERPO): "+arrayListImagenes.size());
            System.out.println("Imagen Parte ("+c+") X: "+arrayListImagenes.get(c).getLayoutX()); 
            System.out.println("Imagen Parte ("+c+") Y: "+arrayListImagenes.get(c).getLayoutY());
            System.out.println("Imagen Parte ("+c+") DIRECCIÓN: "+(int)snakeGame.arrayListCuerpo.get(c).getZ());
        }
        System.out.println("******************************************************");             
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
            new KeyFrame(Duration.seconds(0.008/velocidad), new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {//Sólo puede haber un handle en el timeline
                    //Si se ha comido la manzana la vuelvo a colocar la imagen en su lugar correspondiente
                    //Si no coloco la imagen cuando llega aquí, quita la manzana antes de hacer la pausa y no se nota el efecto
                    if(snakeGame.eaten == true){//La manzana desaparece
                        apple1.setVisible(false);
                    }
                    //***DENTRO DEL TIMELINE NO DEJA LLAMAR A LOS MÉTODOS CON THIS*****OJO!!!!!

                    //arrayListImagenes tine los mismos elementos que Cuerpo
                    for(int c=0; c<arrayListImagenes.size(); c++){
                        //muevo la cola según la posición de la parte anterior
                        if(snakeGame.arrayListCuerpo.get(c).getZ()==App.D_DOWN){
                            arrayListImagenes.get(c).setLayoutY(arrayListImagenes.get(c).getLayoutY() + 1); 

                        }else if(snakeGame.arrayListCuerpo.get(c).getZ()==App.D_UP){
                            arrayListImagenes.get(c).setLayoutY(arrayListImagenes.get(c).getLayoutY() - 1); 

                        }else if((snakeGame.arrayListCuerpo.get(c).getZ()==App.D_LEFT)){
                            arrayListImagenes.get(c).setLayoutX(arrayListImagenes.get(c).getLayoutX() - 1); 

                        }else if(snakeGame.arrayListCuerpo.get(c).getZ()==App.D_RIGHT){
                            arrayListImagenes.get(c).setLayoutX(arrayListImagenes.get(c).getLayoutX() + 1); 
                        }
                    }

                }
            })
        );
        timelineSnake.setCycleCount(App.TAM_PIEZA_SNAKE);
        timelineSnake.play(); //Llama al método Play para echar a andar la animación       
        


        //CUANDO TERMINE EL TIMELINE COMPROBAMOS LÍMITES, SI SE HA COMIDO LA MANZANA Y LO VOLVEMOS A ARRANCAR
        timelineSnake.setOnFinished(event -> {
            if(pendienteCrecimiento){               
                if(snakeGame.arrayListCuerpo.size()>=1){
                    setNewSnakeIntoArray(App.NUM_BODY, puntero);
                    //hay que asignarle posición a la manzana aquí, porque si le damos posición antes el nuevo puntero puede salir donde estaba la manzana
                    snakeGame.setAppleRandom();
                    apple1.setImage(apple1.appleImage);// Ponemos la manzana normal otra vez
                    apple1.setVisible(true);
                    setImageApple(); //colocamos la manzana en su nuevo sitio  
                }
                pendienteCrecimiento = false;
            }
            //MOSTRAMOS PUNTEROS ARRAYLIST Y MATRIZ
            mostrarCuerpoConsola();
            snakeGame.mostrarArrayListConsola();
            snakeGame.mostrarMatrizConsola();//Cada vez que se mueva muestro la matriz actualizada en la consola 
         
            direccionActual = siguienteDireccion;//Sel a he pasado por el método next Direction desde el App
            //HAGO ESTE SWITCH PARA QUE CUANDO HAGA LA PAUSA QUEDE MIRANDO A DONDE ESTABA LA MANZANA CUANDO SE LA HA COMIDO
            this.switchImagenSnake(direccionActual);                                  
            try {//Si no choca con los límites
                //Hacer el movimiento lógico en la matriz en SnakeGame (nos pasará si se come la manzana o no)
                snakeGame.matrixMovement(direccionActual);               
                //SI MUERE TIENE QUE SALTAR CONTINUAR
                if(snakeGame.dead){                    
                    System.out.println("TE HAS MORDIDO!!!! --- :(");
                    this.deadSnake();
                }else{//Si no muere                    
                    this.snakeMovement(direccionActual);//Hacer el movimiento de la snake1 en el Tablero
                    if (snakeGame.eaten == true){//Si se come la manzana movemos la imagen a la posición nueva
                        pendienteCrecimiento = true;
                        puntero = snakeGame.setPunteroArrayList(snakeGame.filaActual, snakeGame.columnaActual, direccionActual);                            
 
                        timelineSnake.stop();
                        apple1.setImage(apple1.appleImageBitten); //Cambiar imagen manzana mordida
                        timelineSnake.setDelay(Duration.seconds(0.3));
                        timelineSnake.play();
                        
                        snakeGame.appleEatenM();
                        App.textScore.setText(String.valueOf(snakeGame.puntuacion)); 
                        
                        //SUBIR DE NIVEL
                        if(snakeGame.puntuacion==10 || snakeGame.puntuacion==20 || snakeGame.puntuacion==30 || snakeGame.puntuacion==40){ //SI COME 10 MANZANAS SUBE LA DIFICULTAD Y LA VELOCIDAD
                            dificultad=  Integer.toString(Integer.valueOf(dificultad)+1);
                            App.textDificulty.setText(dificultad);
                            velocidad = velocidad+0.5;
                            System.out.println("HAS SUBIDO DE NIVEL: "+" [DIFICULTAD: "+dificultad+"] "+"[VELOCIDAD: "+velocidad+"]");
                        }else if(snakeGame.puntuacion==50){
                            timelineSnake.stop();
                            App.textP.setText(String.valueOf(snakeGame.puntuacion));//Actualizo marcador puntuación de la ventana ganar
                            App.textD.setText(dificultad);//Actualizo marcador dificultad de la ventana ganar                        
                            App.paneWin.setVisible(true);
                            System.out.println("¡¡¡ENHORABUENA HAS GANADO!!!");
                        }

                    }else{
                        //Cada vez que se mueve la serpiente hay que ir actualizando los punteros del arraylist del cuerpo de la serpiente.
                        snakeGame.cambioPunteros(snakeGame.filaActual,snakeGame.columnaActual, direccionActual); 
                    }
                }
                               
            } catch(ArrayIndexOutOfBoundsException e) { //SI choca con los llímites MUERE******
                timelineSnake.stop();
                this.deadSnake();
            }            
        });    
    }  
}
           