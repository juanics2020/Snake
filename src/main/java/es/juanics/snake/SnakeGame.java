package es.juanics.snake;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.effect.Light.Point;


public class SnakeGame {
    //LA MATRIZ DEL TABLERO TENDRÁ TANTAS FILAS Y COLUMNAS COMO SEA POSIBLE
    //TENIENDO EN CUENTA EL TAMAÑO DE LA ESCENA Y EL TAMAÑO DE LOS VISORES DE LA SERPIENTE
    //SE CALCULARÁN LAS FILAS Y COLUMNAS PARA QUE COINCIDA EL MOVIMIENTO GRÁFICO DE LA SERPIENTE CON EL MOVIMIENTO DE LA MATRIZ
    
    
    private static int filas = 0;//variable para total filas matriz   
    private static int columnas = 0;//variable para total columnas matriz
    //Declaro la matriz del tablero por ahora sin filas ni columnas]
    static int [][] matrizTablero;//matrizTablero[6][10] = 1; //Poner un 1 en la posición fila 6, columna 10
        
    public static int filaActual;//posición actual de la cabeza en la FILA dentro de la matriz //PARA QUE SÓLO LAS CREE UNA VEZ PONEMOS ESTÁTICA (si no las crea de 0 siempre que entra)
    public static int columnaActual;//posición actual de la cabeza en la COLUMNA dentro de la matriz //PARA QUE SÓLO LAS CREE UNA VEZ PONEMOS ESTÁTICA (si no las crea de 0 siempre que entra)  
                 
    public static int appleCol;//Columna aleatoria de la manzana
    public static int appleFil;//Fila aleatoria de la manzana 
    
    public static boolean eaten = false; //static para que no cambie (si no, crearía una variable nueva cada vez que se use)
    public static boolean dead = false;//Cuando en la matriz se muerda la serpiente ella misma, muere. Guardaremos el estado.
   
    public static ArrayList<Point>arrayListCuerpo = new ArrayList();//ArrayList que guardará dos punteros (posición fila, posición columna) del cuerpo
    private static double arrayXvacia = App.NUM_EMPTY;
    private static double arrayYvacia =  App.NUM_EMPTY;
    private static double arrayDireccion =  App.D_DOWN;
    public static int contadorArray = 0;
    
 
    //Calcula cuantas FILAS Y COLUMNAS tiene la Matriz usando el ancho y alto de la escena y el tamaño del visor de la serpiente
    public void tamañoMatriz(int sceneWidth, int sceneHeight){
        //Llamamos a la función dentro de Snake que le pasa el tamaño que tendrán los visores para calcular el tamaño de la matriz 
        
        //Calculamos filas y columnas según alto y ancho de la escena
        filas = (int)(sceneHeight/App.TAM_PIEZA_SNAKE);
        columnas = (int)(sceneWidth/App.TAM_PIEZA_SNAKE);
        //Declaro la matriz del tablero  ej [13 filas hacia abajo][21 columnas hacia la derecha]
        matrizTablero = new int [filas][columnas];//FILAS Y COLUMNAS TOTALES QUE TENDRÁ LA MATRIZ
        
        System.out.println("");
        System.out.println("Tamaño Snake: "+App.TAM_PIEZA_SNAKE+", Nº FILAS TOTALES: "+filas+", Nº COLUMNAS TOTALES: "+columnas);              
    }
    
    
    //Al inicio del juego rellenaremos la matriz de 0 escepto la mitad de la matriz que será un 1 donde está la cabeza de la serpiente  
    //(1º Rellenamos las celdas de la matriz de 0)
    public SnakeGame (){
        for(int f=0; f<filas; f++){
            for(int c=0; c<columnas; c++){
                matrizTablero[f][c] = App.NUM_EMPTY;//Ponemos la cel de la matriz vacía a 0
            }        
        }
    } 
    
    
    public void inicioFilaColActual(int filIn, int colIn){//La serpiente comienza a mita de la pantalla. Le paso la fila del centro y la columna del centro
        filaActual = filIn; //Pongo estas variables a la posición inicial, pero después irán cambiando según se mueva la serpiente
        columnaActual = colIn;               
        System.out.println("FILA ACTUAL: "+filaActual+", COLUMNA ACTUAL: "+columnaActual);         
    }
    
    //(2º Mostramos lo que tiene la matriz en la consola)
    public void mostrarMatrizConsola(){// 6 filas (0 a 12) y 7 (0 a 20) columnas
        System.out.println("");//PARA QUE DEJE UN SALTO DE LÍNEA EN LA CONSOLA ANTES DE LA MATRIZ       
        for(int f=0; f<filas; f++){
            for(int c=0; c<columnas; c++){
                System.out.print(matrizTablero[f][c]); // mostraría en consola los 0
            }
            System.out.println("");//Salto de columna
        }
        System.out.println("");//PARA QUE DEJE UN SALTO DE LÍNEA EN LA CONSOLA DESPUÉS DE LA MATRIZ
    }  
    
    public int getNumFilasMatriz(){//Método para devolver Nº de Filas de la Matriz
        return filas;
    }
    public int getNumColumnasMatriz(){//Método para devolver Nº de Columnas de la Matriz
        return columnas;
    }

    
    //POSICIONES ALEATORIA PARA LA MANZANA
    public int setRandomAppleCol(){//Obtener una columna de la matriz aleatoria
        Random randomX = new Random();
        int rX = randomX.nextInt(columnas);//La X se calcula con las columnas
        return rX;
    }
    public int setRandomAppleFil(){//Obtener una fila de la matriz aleatoria
        Random randomY = new Random();
        int rY = randomY.nextInt(filas);//La X se calcula con las filas
        return rY;
    }
    
    public void setAppleRandom(){//PONER LA MANZANA ALEATORIA SIN QUE PISE LA SERPIENTE (cabeza 1, cuerpo 2, cola 3)   
        do{//Buscar posición aleatoria para la manzana hasta que no pise la serpiente en la matriz
            appleCol = this.setRandomAppleCol();
            appleFil = this.setRandomAppleFil();
            System.out.println("Manzana Alearoia en matriz: "+appleFil+", "+appleCol);
        }while (this.matrizTablero[appleFil][appleCol]==App.NUM_HEAD || this.matrizTablero[appleFil][appleCol]==App.NUM_BODY || this.matrizTablero[appleFil][appleCol]==App.NUM_TAIL);    
        //La cabeza es 1, el cuerpo es 2 y la cola es 3
        
        this.matrizTablero[appleFil][appleCol]=App.NUM_APPLE;//*********Pongo un 4 en la matriz donde esté la manzana    
        //this.mostrarMatrizConsola();//Muestro la matriz del tablero en la consola
        //Ponemos la manzana gráficamente en el tablero
        //En la matriz es una fila menos(0 a 12) y una columna menos (0-20)
        System.out.println("POSICIÓN ALEATORIA MANZANA: "+"(fila)"+(appleFil+1)+"(columna)"+(appleCol+1)+"-----MATRIZ UNO MENOS -> FILA (0-12) Y COLUMNA (0-20)");           
        System.out.println("");
    }
    
    
    public void appleEatenM(){
        App.puntuacion++;
        App.textScore.setText(String.valueOf(App.puntuacion));       
        System.out.println("SE HA COMIDO LA MANZANA ----- OOOOOOOO");
        System.out.println("Puntuación: "+App.puntuacion);
        System.out.println("*****************************************************");
        this.setAppleRandom(); 
    }
    
    public void emptyApple(){//Cuando reinicie hay que vaciar la celda de la manzana en la matriz
        matrizTablero[appleFil][appleCol] = App.NUM_EMPTY;
    }
    
    
    
    //(Mostramos lo que tiene el arraylist de la serpiente en la consola)
    public void mostrarArrayListConsola(){
        //PARA QUE pinte **** SALTO DE LÍNEA EN LA CONSOLA ANTES DEL ARRAYLIST 
        System.out.println("******************************************************");
        for(int c=0; c<arrayListCuerpo.size(); c++){
            System.out.println("-----Tamaño ArrayList(nº punteros): "+arrayListCuerpo.size());
            System.out.println("Puntero ("+c+") Y (FILA): "+arrayListCuerpo.get(c).getY()); 
            System.out.println("Puntero ("+c+") X (COLUMNA): "+arrayListCuerpo.get(c).getX());
            System.out.println("Puntero ("+c+") Z (Dirección): "+(int)arrayListCuerpo.get(c).getZ());
        }
        System.out.println("******************************************************");             
    }
    
    
    public void setPunteroArrayList(int fila, int columna){
                
        //OJO!!!! LA Y GUARDA LAS FILAS Y LA X LAS COLUMNAS!!!!!<------------
        //Añado las posiciones de la cabeza de la serpiente al Arraylist que representará la serpiente
        Point p = new Point();
             
        if(arrayListCuerpo.isEmpty()){//Si está vacío no ha comenzado
            //Si no ha comenzado el juego es para la cabeza de la serpiente y será fila y columna
            p.setX(columna);         
            p.setY(fila);
            p.setZ(App.D_DOWN);
                     
        }else{ 
            //Antes de añadir uno nuevo, ordeno los punteros que ya tengo
            System.out.println("setPunteroArrayList: FILA "+columna+", COLUMNA "+fila);//hay que tener en cuanta que la X es COLUMNA y la Y es FILA
            cambioPunteros(fila,columna);
            //Si ya ha comenzado el juego las nuevas posiciones serán las posiciones del último puntero
            p.setX(arrayXvacia);         
            p.setY(arrayYvacia);
            p.setZ(arrayDireccion);
            //VARIABLE QUE SERVIRÁ DE CONTADOR EN LOS PASOS QUE DE LA SERPIENTE, PARA PODER GUARDAR LAS DIRECCIONES 
            contadorArray++; //Tendrá siempre el tamaño del array -1 (Nº de array -1 porque empieza en 0)
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<< contadorArray "+contadorArray+" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
      
        arrayListCuerpo.add(p);
        System.out.println("************************************************************************************************");
        System.out.println("NUEVO PUNTERO p Y("+p.getY()+") [FILA] - X("+p.getX()+") [COLUMNA] - Z("+(int)p.getZ()+") <DIRECCIÓN>");
        System.out.println("************************************************************************************************");
      
    }
    
    
    public void cambioPunteros(int filaActual, int columnaActual){
        //La COLA en el arrayList me da igual, será en la MATRIZ (3) y en el TABLERO gráficamente donde tendré que ir pasando la cola al final
        
        //Según la dirección, cada vez que se mueva la serpiente, las posiciones de los punteros X e Y se irán sumando o restando (posición X o Y = posición X o Y +1 o -1)
        
        //A partir de la cabeza, pongo el puntero en la posición del anterior.
        //Por último, la cabeza la pongo en fila y columna actual
        
        //La cabeza(0) la dejo para lo último porque le pondremos la posición fila y columna actual.Empiezo en 1 (el segundo puntero).
       
        
        //Pongo la posición que se queda vacia (donde estaba la cola antes de moverla) a 0 en la matriz
        //tamaño -1 sería el último elemento del array. Si tiene 3 de tamaño, el último elemento es el 2 (0-2)
        arrayXvacia=arrayListCuerpo.get(arrayListCuerpo.size()-1).getX(); 
        arrayYvacia=arrayListCuerpo.get(arrayListCuerpo.size()-1).getY();
        arrayDireccion=arrayListCuerpo.get(arrayListCuerpo.size()-1).getZ();
        
        matrizTablero[(int)arrayXvacia][(int)arrayYvacia] = App.NUM_EMPTY;
        
        for(int c=contadorArray; c>0; c--){ 
            //Pongo las nuevas posiciones en el ARRAYLIST
            arrayListCuerpo.get(c).setX(arrayListCuerpo.get(c-1).getX());
            arrayListCuerpo.get(c).setY(arrayListCuerpo.get(c-1).getY());
            arrayListCuerpo.get(c).setZ(arrayListCuerpo.get(c-1).getZ());
            
            //Pongo los 2 y el 3 en la MATRIZ (2:cuerpo, 3:cola) **El 1 de la cabeza se pone abajo enmatrizMovement
            //la posición en la matriz en la que estaba la cola la pongo a 0
          
            if(c==contadorArray){    //COLA            
                matrizTablero[(int)arrayListCuerpo.get(c).getX()][(int)arrayListCuerpo.get(c).getY()] = App.NUM_TAIL;
            }else{ //CUERPO
                matrizTablero[(int)arrayListCuerpo.get(c).getX()][(int)arrayListCuerpo.get(c).getY()] = App.NUM_BODY;
            }
            
        }           
        //Una vez que todos los punteros se han cambiado a la posición del anterior, pongo la cabeza a la posición actual, que es donde se ha movido en la matriz
        arrayListCuerpo.get(0).setX(filaActual);
        arrayListCuerpo.get(0).setY(columnaActual);
        //La dirección la guarda en matrixMovement
        System.out.println("cambioPunteros: FILA "+columnaActual+", COLUMNA "+filaActual);//hay que tener en cuanta que la X es COLUMNA y la Y es FILA
        
    //-->OTRO MÉTODO Cuando se coma la manzana el nuevo puntero(cola)(le pongo un 3 en matriz) lo pongo donde estaba el último puntero(poniéndole también un 2 en la matriz).
    
    }
    
    
    
    public void matrixMovement(int direccion) {  //MOVIEMIENTO LÓGICO DE LA SERPIENTE EN LA MATRIZ
        //CUANDO SE COMA LA MANZANA TIENE QUE APARECE UN NUEVO PUNTERO EN EL ARRAY LIST
        System.out.println("DENTRO SWITCH ----> FILA ACTUAL: "+filaActual+", COLUMNA ACTUAL: "+columnaActual);
                    
        switch (direccion) {//Según la tecla pulsada
            case App.D_LEFT: // la matriz se moverá a la IZQUIERDA(sólo la COLUMNA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0               
                columnaActual --;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come///
                    eaten = true;                    
                }else if(matrizTablero[filaActual][columnaActual] == App.NUM_BODY || matrizTablero[filaActual][columnaActual] == App.NUM_TAIL){
                    eaten = false;
                    dead = true;
                }else{
                    eaten = false;
                    dead = false;
                }                                                        
                break;


            case App.D_RIGHT: // la matriz se moverá a la DERECHA(sólo la COLUMNA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0               
                columnaActual ++;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come///                   
                    eaten = true;
                }else if(matrizTablero[filaActual][columnaActual] == App.NUM_BODY || matrizTablero[filaActual][columnaActual] == App.NUM_TAIL){
                    eaten = false;
                    dead = true;
                }else{
                    eaten = false;
                    dead = false;
                }               
                break;


            case App.D_DOWN: // la matriz se moverá ABAJO(sólo la FILA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0               
                filaActual ++;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come///                 
                    eaten = true;
                }else if(matrizTablero[filaActual][columnaActual] == App.NUM_BODY || matrizTablero[filaActual][columnaActual] == App.NUM_TAIL){
                    eaten = false;
                    dead = true;
                }else{
                    eaten = false;
                    dead = false;
                }               
                break;


            case App.D_UP: // la matriz se moverá ARRIBA(sólo la FILA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0                
                filaActual --;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come///                  
                    eaten = true;
                }else if(matrizTablero[filaActual][columnaActual] == App.NUM_BODY || matrizTablero[filaActual][columnaActual] == App.NUM_TAIL){
                    eaten = false;
                    dead = true;
                }else{
                    eaten = false;
                    dead = false;
                }              
                break;
        }
        //Pongo el puntero del array de la cabeza con la dirección actual
        arrayListCuerpo.get(0).setZ(direccion);
        //SERPIENTE EN LA MATRIZ A LA NUEVA POSICIÓN 
        matrizTablero[filaActual][columnaActual] = App.NUM_HEAD;//La nueva posición la pongo en 1
        System.out.println("DENTRO SWITCH segundo**** ----> FILA ACTUAL: "+filaActual+", COLUMNA ACTUAL: "+columnaActual);

    }
}
