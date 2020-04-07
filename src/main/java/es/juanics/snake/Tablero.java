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

//PARTE GRÁFICA
public class Tablero extends Pane {//LA CLASE TABLERO HEREDA LAS PROPIEDADES, MÉTODOS,... DE LA CLASE PANE (extends)
//*****La clase en sí es el panel
       
    private int filas = 0;//variable para total filas matriz   
    private int columnas = 0;//variable para total columnas matriz

    private String dificultad;//Guardará la dificultad
    private double velocidad;//Guardará la velocidad
    
    private Snake snake1;//Guardaremos un objeto de tipo Snake (Grupo que creamos en la clase Snake)
    private Timeline timelineSnake;//Moverá la serpiente
    private SnakeGame snakeGame;   //objeto de tipo SnakeGame para poder usar todos los métodos y parámetros de SnakeGame
    private int matFilaIni;//posición inicial de la cabeza en la FILA dentro de la matriz
    private int matColumnaIni;//posición inicial de la cabeza en la COLUMNA dentro de la matriz    
     
    private Apple apple1;//Crear un objeto de tipo Apple  
    private byte siguienteDireccion = App.D_DOWN; //Cuando pulse las teclas guardará la siguiente direccion (comenzará hacia abajo)
    private byte direccionActual = App.D_DOWN;//Cogerá la nueva dirección que le den las teclas (comenzará hacia abajo)

    //ArrayList que guardará las imágenes para las imágenes de las serpientes
    //(de tipo Snake para decirle que parte de la serpiente quiero crear o cambiar)
    private ArrayList<Snake>arrayListImagenes = new ArrayList();
    //Nos indicará si se acaba de comer la manzana, porque hay que esperar un paso para poner la nueva imagen
    private boolean pendienteCrecimiento = false;
    private int puntero;//Nos devolverá el número del puntero que corresponde al nuevo puntero(creado en arrayListCuerpo)
    
    private int totalManzanasComer;//GUARDARÁ EL TOTAL DE LAS MANZANAS QUE HAY QUE COMER
    private int manzanasPorSubida;//GUARDARÁ CUÁNTAS MANZANAS HAY QUE COMER (*5)
    
    
    
    //Pone la serpiente en el tablero gráficamente //Método constructor (new Tablero)
    //Al crearlo en App le pasamos todos esto parámetros que usaremos en esta clase
    public Tablero(int width, int height, int filas, int columnas, String dificultad, double velocidad) {
        this.filas = filas; //le ponemos this para que distinga que es la variable de esta clase, no la que le paso que se llama igual
        this.columnas = columnas;
        this.dificultad = dificultad;
        this.velocidad = velocidad;
        //Pongo el tablero al ancho y alto de la escena
        this.setWidth(width);
        this.setHeight(height);      
        
        snakeGame = new SnakeGame(this.filas,this.columnas);//Objeto SnakeGame para crear la matriz del tablero
        
        
        //CALCULO LAS MANZANAS A COMER POR NIVEL Y LAS MANZANAS TOTALES A COMER PARA GANAR EL JUEGO
        // Las manzanas que tiene que comer en cada nivel para subir serán las filas o las columnas (el que sea mayor)
        if(this.filas>this.columnas){
           manzanasPorSubida = this.filas; 
        }else{
           manzanasPorSubida = this.columnas;  
        }   
        
        totalManzanasComer = manzanasPorSubida*5;//El total de manzanas a comer serán las filas o columnas(el que sea mayor) * 5 (niveles)
        //Muestro en el marcador las manzanas totales que tiene que comer para ganar
        App.textTotalApples.setText(String.valueOf(totalManzanasComer));
        
        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
        System.out.println("TOTAL MANZANAS A COMER PARA GANAR: "+totalManzanasComer);
        System.out.println("MANZANAS POR SUBIDA DE NIVEL: "+manzanasPorSubida);
        System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
        System.out.println(" ");
        
        
        
        //pone la serpiente en el tablero
        snake1 = new Snake((byte)1);
        arrayListImagenes.add(snake1);
        this.getChildren().add(snake1);
        
                    
        //PINTAR LA CUADRÍCULA DEL TABLERO SEGÚN LAS FILAS Y COLUMNAS DE LA MATRIZ       
        for(int i=0;i<=this.columnas;i++){
            Line line = new Line(App.TAM_PIEZA_SNAKE*i, 0, App.TAM_PIEZA_SNAKE*i, App.TAM_PIEZA_SNAKE*this.filas);//X inicial, Y inicial, X final, Y final) La separación será el tamaño del visor de la serpiente
            line.setStroke(Color.LIGHTBLUE);
            this.getChildren().add(line); //añade la linea a esta misma clase Tablero (que es el panel)
        }
        for(int i=0;i<=this.filas;i++){
            Line line = new Line(0, App.TAM_PIEZA_SNAKE*i, App.TAM_PIEZA_SNAKE*this.columnas, App.TAM_PIEZA_SNAKE*i);//X inicial, Y inicial, X final, Y final) La separación será el tamaño del visor de la serpiente
            line.setStroke(Color.LIGHTBLUE);
            this.getChildren().add(line); //añade la linea a esta misma clase Tablero (que es el panel)
        }                
                
        matFilaIni = (int)(this.filas/2);//Calculo la fila que cae a la mitad de la Matriz
        matColumnaIni = (int)(this.columnas/2);//Calculo la columna que cae a la mitad de la Matriz
        
        //LE PASO LA POSICIÓN INICIAL DE LA MATRIZ A SnakeGame para que vaya cambiando posicones Actuales de fila y columna al pulsar teclas
        //Lo paso a la parte lógica
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
        //Llamo al método de abajo. Lo volveremos a usar en SnakeGame cada vez que la serpiente se coma la manzana
        snakeGame.setAppleRandom();//Calculamos posición de la manzana y la colocamos en la matriz con un 4 sin pisar la serpiente
        apple1 = new Apple();//Creamos un objeto Apple gráfico
        this.setImageApple();
        this.getChildren().add(apple1); 
            
        snakeGame.mostrarMatrizConsola();//Muestro la matriz del tablero en la consola
    }   
    
    //Ponemos gráficamente la manzana en el tablero
    public void setImageApple(){
        apple1.setLayoutX(App.TAM_PIEZA_SNAKE*snakeGame.appleCol);
        apple1.setLayoutY(App.TAM_PIEZA_SNAKE*snakeGame.appleFil);       
    }
    
    //Cuando muera da la opción de reiniciar el juego con puntuación 0 y dificultad 1
    public void reiniciar(){
        snakeGame.eaten = false;
        snakeGame.dead = false;
        snakeGame.puntuacion = 0;
        dificultad = "1";
        velocidad = ((Double.valueOf(dificultad)*0.5)+0.5);//La velocidad se vuelve a calcular con la dificultad para el timeline
        App.textScore.setText(String.valueOf(snakeGame.puntuacion));//Actualizo los marcadores
        App.textDificulty.setText(dificultad);
                                
        //PONGO LA MATRIZ A 0 EN TODAS LAS POSICIONES DE LA CABEZA, EL CUERPO Y COLA DE LA SERPIENTE
        for(int c=(snakeGame.arrayListCuerpo.size()-1); c>=0; c--){                               
            snakeGame.matrizTablero[(int)snakeGame.arrayListCuerpo.get(c).getY()][(int)snakeGame.arrayListCuerpo.get(c).getX()] = App.NUM_EMPTY;            
        }        
        //LE PASO LA POSICIÓN INICIAL DE LA MATRIZ A SnakeGame para que vaya cambiando posicones Actuales de fila y columna al pulsar teclas
        snakeGame.inicioFilaColActual(matFilaIni, matColumnaIni);
        snakeGame.matrizTablero[matFilaIni][matColumnaIni] = App.NUM_HEAD;//Pongo el 1 a mitad de la matriz que corresponde a la Cabeza de la serpiente   
               
        
        //ELIMINO TODOS LOS PUNTEROS DEL ARRAYLIST DEL CUERPO DE LA SERPIENTE ESCEPTO LA CABEZA
        for(int c=(snakeGame.arrayListCuerpo.size()-1); c>0; c--){                               
            snakeGame.arrayListCuerpo.remove(c);    
        }                  
//        //OTRA FORMA DE VACIAR EL ARRAYLIST        
//        snakeGame.arrayListCuerpo.clear();
//        snakeGame.setPunteroArrayList(matFilaIni, matColumnaIni);


        //ELIMINO TODAS LAS IMÁGENES DEL ARRAYLIST DE LA SERPIENTE ESCEPTO LA CABEZA
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
    
    //MÉTODO PARA PARA QUE APAREZCA LA PANTALLA DE CONTINUAR CUANDO NOS HAN MATADO
    public void panelVisible(){
        App.paneContinuar.setVisible(true);
    }
    
    //MÉTODO PARA QUE DESAPAREZCA LA PANTALLA DE CONTINUAR CUANDO NOS HAN MATADO
    public void panelInvisible(){
        App.paneContinuar.setVisible(false);
    }
    
      
    //PASAR DEL APP AL TABLERO, LA DIRECCIÓN SIGUIENTE
    public void nextDirection(byte direccion){
        siguienteDireccion = direccion;
        System.out.println("Siguiente DIRECCIÓN: "+siguienteDireccion);
    }
    

    //PONE LA IMAGEN DE LA SERPIENTE EN LA DIRECCIÓN QUE LE CORRESPONDA  
    public void switchImagenSnake(byte direccion){      
        switch (direccionActual) {//Según la tecla pulsada
            case App.D_LEFT:// la serpiente se moverá a la izquierda
                arrayListImagenes.get(0).setHead(App.D_LEFT);//cambio el sentido de la cabeza de la serpiente                                
                break;
            case App.D_RIGHT:// la serpiente se moverá a la derecha
                arrayListImagenes.get(0).setHead(App.D_RIGHT);//cambio el sentido de la cabeza de la serpiente
                break;
            case App.D_DOWN:// la serpiente se moverá abajo
                arrayListImagenes.get(0).setHead(App.D_DOWN);//cambio el sentido de la cabeza de la serpiente
                break;
            case App.D_UP:// la serpiente se moverá arriba                            
                arrayListImagenes.get(0).setHead(App.D_UP);//cambio el sentido de la cabeza de la serpiente
                break;
        }           
    } 
    
    
    
    //AL INICIAR SIEMPRE LA PARTIDA HAY QUE HACER UN RETARDO ANTES DE QUE EMPIECE A MOVERSE LA SERPIENTE PARA DARLE TIEMPO AL USUARIO
    public void reatrdoInicioPartida(byte direccion){
        //Sirve para lo que lo que metamos aquí. Podemos utilizar varios TimeLine con diferentes velocidades para diferentes cosas
        Timeline timelineRetardo = new Timeline(
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
    
    //CUANDO MUERE, APARECE UNA VENTANA QUE PREGUNTA SI QUIERE CONTINUAR
    public void deadSnake(){
        System.out.println("++++++ HAS MUERTO ++++++");
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
    
    
    //Le paso el puntero del arrayListCuerpo que le corresponde a la nueva imagen
    public void setNewSnakeIntoArray(int puntero){  
        //pone la nueva parte en el array de las imágenes de la serpiente
        Snake snakeV = new Snake((byte)App.NUM_BODY);//La parte que ponemos siempre es el cuerpo, porque la cabeza se hace al principio
        //Ponemos la parte en la posición que le corresponde a su puntero
        snakeV.setLayoutX(App.TAM_PIEZA_SNAKE*snakeGame.arrayListCuerpo.get(puntero).getX());
        snakeV.setLayoutY(App.TAM_PIEZA_SNAKE*snakeGame.arrayListCuerpo.get(puntero).getY());
              
        arrayListImagenes.add(snakeV);//Añado la parte al arrayListImagenes
        this.getChildren().add(snakeV);//Añado el elemento al tablero        
    }
     
    //Mostramos en la consola donde se sitúan las imágenes del arraylist
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
    
   
    // MOVIMIENTO GRÁFICO DE LA SERPIENTE
    public void snakeMovement(byte direccion) {
        direccionActual = direccion; //La dirección que le digamos por teclado. Comienza hacia abajo
               
        //El timeline se para cuando llamamos a la función si ya se había creado
        //la primera vez. Si no, crea un timeline nuevo cada vez que se llame a la función y se pisan.
        if (timelineSnake != null) {
            timelineSnake.stop();            
        }
        //EL TIMELINE IRÁ MOVIENDO LA SERPIENTE DE CASILLA EN CASILLA
        timelineSnake = new Timeline(
            // 0.017 ~= 60 FPS (equivalencia de segundos a Frames por Segundo)
            //La velocidad se calcula según la dificultad que eligió el usuario y aumentará a medida que come manzanas
            new KeyFrame(Duration.seconds(0.008/velocidad), new EventHandler<ActionEvent>() {
                public void handle(ActionEvent ae) {//Sólo puede haber un handle en el timeline
                    if(snakeGame.eaten == true){//Si se ha comido la manzana desaparece
                        apple1.setVisible(false);
                    }
                    //***DENTRO DEL TIMELINE NO DEJA LLAMAR A LOS MÉTODOS CON THIS*****OJO!!!!!

                    //arrayListImagenes tine los mismos elementos que Cuerpo
                    //A cada paso moveremos todos los elementos del array de imágenes,
                    //un pixel más o menos según la dirección guardada que tuviera su puntero correspondiente
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
        //la serpiente se moverá de casilla en casilla (es decir el tamaño de cada casilla, 42 pixeles)
        timelineSnake.setCycleCount(App.TAM_PIEZA_SNAKE);
        timelineSnake.play(); //Llama al método Play para echar a andar la animación       
        


        //CUANDO TERMINE EL TIMELINE COMPROBAMOS LÍMITES, SI SE HA COMIDO LA MANZANA Y LO VOLVEMOS A ARRANCAR,...
        timelineSnake.setOnFinished(event -> {
            //Si al terminar de moverse una casilla se comió la manzana hay que esperar un movimiento para poner la imagen
            //La variable pendienteCrecimiento nos dice si todavía no se puso esa imagen
            if(pendienteCrecimiento){//Si tiene que crecer aún            
                if(snakeGame.arrayListCuerpo.size()>=1){//La haremos crecer sólo si no es la cabeza(la cabeza se hace al principio)
                    setNewSnakeIntoArray(puntero);//Pongo la imagen nueva en el array
                    //hay que asignarle posición a la manzana aquí, porque si le damos posición antes, el nuevo puntero puede salir donde estaba la manzana
                    snakeGame.setAppleRandom();//Le damos posición aleatoria a la manzana
                    apple1.setImage(apple1.appleImage);// Ponemos la manzana normal otra vez (no mordida)
                    apple1.setVisible(true);//La hacemos visible
                    setImageApple(); //Colocamos la manzana en su nuevo sitio  
                }
                pendienteCrecimiento = false;//Ya ha crecido
            }
            //MOSTRAMOS IMAGENES ARRAYLIST, PUNTEROS ARRAYLIST Y MATRIZ ACTUALIZADOS
            mostrarCuerpoConsola();
            snakeGame.mostrarArrayListConsola();
            snakeGame.mostrarMatrizConsola();
         
            direccionActual = siguienteDireccion;//Se la he pasado por el método next Direction desde el App
            //HAGO ESTE SWITCH PARA QUE CUANDO HAGA LA PAUSA QUEDE MIRANDO A DONDE ESTABA LA MANZANA CUANDO SE LA HA COMIDO
            this.switchImagenSnake(direccionActual);                                  
            try {//Si no choca con los límites (OUT OF BOUNDS)
                //Hacer el movimiento lógico en la matriz en SnakeGame (nos dirá si se come la manzana o no)
                snakeGame.matrixMovement(direccionActual);               
                //SI MUERE TIENE QUE SALTAR CONTINUAR
                if(snakeGame.dead){ //Si no ha chocado pero ha muerto, quiere decir que se ha mordido                   
                    System.out.println("TE HAS MORDIDO!!!! --- :(");
                    this.deadSnake();
                }else{//Si no muere sigue jugando                    
                    this.snakeMovement(direccionActual);//Hacer el movimiento gráfico de la snake1 en el Tablero
                    if (snakeGame.eaten == true){//Si se come la manzana haremos la parte lógica y la parte gráfica la haremos a la siguiente vuelta
                        pendienteCrecimiento = true;//Como se ha comido la manzana está pendiente de crecer gráficamente
                        //Creamos el puntero nuevo
                        puntero = snakeGame.setPunteroArrayList(snakeGame.filaActual, snakeGame.columnaActual, direccionActual);                            
 
                        timelineSnake.stop();//Paramos el timeline
                        apple1.setImage(apple1.appleImageBitten); //Cambiar imagen a manzana mordida
                        timelineSnake.setDelay(Duration.seconds(0.3)); //Le hacemos un retraso para que se vea bien que se ha comido la manzana
                        timelineSnake.play(); //Arrancamos el timeline otra vez
                        
                        snakeGame.appleEatenM();//Subimos la puntuación y actualizamos el marcador
                        App.textScore.setText(String.valueOf(snakeGame.puntuacion));
                        
                        //SUBIR DE NIVEL
                        //SI COME 10 MANZANAS SUBE LA DIFICULTAD Y LA VELOCIDAD
                        if(snakeGame.puntuacion==(manzanasPorSubida*1) || snakeGame.puntuacion==(manzanasPorSubida*2) || snakeGame.puntuacion==(manzanasPorSubida*3) || snakeGame.puntuacion==(manzanasPorSubida*4)){
                            dificultad=  Integer.toString(Integer.valueOf(dificultad)+1);
                            App.textDificulty.setText(dificultad);
                            velocidad = velocidad+0.5;
                            System.out.println("HAS SUBIDO DE NIVEL: "+" [DIFICULTAD: "+dificultad+"] "+"[VELOCIDAD: "+velocidad+"]");
                        }else if(snakeGame.puntuacion==totalManzanasComer){//Si llega a comer el total de las manzanas GANA EL JUEGO
                            timelineSnake.stop();
                            App.textP.setText(String.valueOf(snakeGame.puntuacion));//Actualizo marcador puntuación de la ventana ganar
                            App.textD.setText(dificultad);//Actualizo marcador dificultad de la ventana ganar                        
                            App.paneWin.setVisible(true);
                            System.out.println("¡¡¡ENHORABUENA HAS GANADO!!!");
                        }

                    }else{//Si no se ha comido la manzana actualizamos los punteros con las posiciones del anterior
                        //Cada vez que se mueve la serpiente hay que ir actualizando los punteros del arraylist del cuerpo de la serpiente.
                        snakeGame.cambioPunteros(snakeGame.filaActual,snakeGame.columnaActual, direccionActual); 
                    }
                }                               
            } catch(ArrayIndexOutOfBoundsException e) { //SI choca con los llímites MUERE******
                //e.printStackTrace();
                timelineSnake.stop();
                this.deadSnake();
            }            
        });    
    }  
}
           