package es.juanics.snake;

import java.util.Random;


public class SnakeGame {
    //LA MATRIZ DEL TABLERO TENDRÁ TANTAS FILAS Y COLUMNAS COMO SEA POSIBLE
    //TENIENDO EN CUENTA EL TAMAÑO DE LA ESCENA Y EL TAMAÑO DE LOS VISORES DE LA SERPIENTE
    //SE CALCULARÁN LAS FILAS Y COLUMNAS PARA QUE COINCIDA EL MOVIMIENTO GRÁFICO DE LA SERPIENTE CON EL MOVIMIENTO DE LA MATRIZ
    
    
    private static int filas = 0;//variable para filas matriz   
    private static int columnas = 0;//variable para columnas matriz
    //Declaro la matriz del tablero por ahora sin filas ni columnas]
    static int [][] matrizTablero;//matrizTablero[6][10] = 1; //Poner un 1 en la posición fila 6, columna 10
    
    //Creamos un objeto Snake para utilizar la función que nos da su tamaño
    private Snake snake1 = new Snake(App.NUM_HEAD);
        
    private static int filaActual;//posición actual de la cabeza en la FILA dentro de la matriz //PARA QUE SÓLO LAS CREE UNA VEZ PONEMOS ESTÁTICA (si no las crea de 0 siempre que entra)
    private static int columnaActual;//posición actual de la cabeza en la COLUMNA dentro de la matriz //PARA QUE SÓLO LAS CREE UNA VEZ PONEMOS ESTÁTICA (si no las crea de 0 siempre que entra)  
                 
    public static int appleCol;//Columna aleatoria de la manzana
    public static int appleFil;//Fila aleatoria de la manzana 
    
    //public static int puntuacion = 0;
    private static boolean eaten = false; //static para que no cambie (si no, crearía una variable nueva cada vez que se use)
   
    
    
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
                matrizTablero[f][c] = App.NUM_EMPTY;//Ponemos la cel de la matriz vacía
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
                System.out.print(matrizTablero[f][c]); // mostraría en consola los puntitos
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
        this.setAppleRandom();       
        System.out.println("Puntuación: "+App.puntuacion);
        System.out.println("SE HA COMIDO LA MANZANA");           
    }
    
    public void emptyApple(){//Cuando reinicie hay que vaciar la celda de la manzana en la matriz
        matrizTablero[appleFil][appleCol] = App.NUM_EMPTY;
    }
    
    public boolean matrixMovement(int direccion) {  //MOVIEMIENTO LÓGICO DE LA SERPIENTE EN LA MATRIZ
        System.out.println("DENTRO SWITCH ----> FILA ACTUAL: "+filaActual+", COLUMNA ACTUAL: "+columnaActual);
                    
        switch (direccion) {//Según la tecla pulsada
            case App.D_LEFT: // la matriz se moverá a la IZQUIERDA(sólo la COLUMNA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0               
                columnaActual --;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come///
                    //this.appleEatenM();
                    eaten = true;
                }else{
                    eaten = false;
                }                
                //SERPIENTE EN LA MATRIZ A LA NUEVA POSICIÓN                                              
                matrizTablero[filaActual][columnaActual] = App.NUM_HEAD;//La nueva posición la pongo en 1                                           
                break;


            case App.D_RIGHT: // la matriz se moverá a la DERECHA(sólo la COLUMNA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0               
                columnaActual ++;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come///
                    //this.appleEatenM();                   
                    eaten = true;
                }else{
                    eaten = false;
                }               
                //SERPIENTE EN LA MATRIZ A LA NUEVA POSICIÓN              
                matrizTablero[filaActual][columnaActual] = App.NUM_HEAD;//La nueva posición la pongo en 1
                break;


            case App.D_DOWN: // la matriz se moverá ABAJO(sólo la FILA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0               
                filaActual ++;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come///
                    //this.appleEatenM();                   
                    eaten = true;
                }else{
                    eaten = false;
                }               
                //SERPIENTE EN LA MATRIZ A LA NUEVA POSICIÓN                
                matrizTablero[filaActual][columnaActual] = App.NUM_HEAD;//La nueva posición la pongo en 1
                break;


            case App.D_UP: // la matriz se moverá ARRIBA(sólo la FILA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0                
                filaActual --;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come///
                    //this.appleEatenM();                   
                    eaten = true;
                }else{
                    eaten = false;
                }               
                //SERPIENTE EN LA MATRIZ A LA NUEVA POSICIÓN               
                matrizTablero[filaActual][columnaActual] = App.NUM_HEAD;//La nueva posición la pongo en 1
                break;
        }
        this.mostrarMatrizConsola();//Cada vez que se mueva muestro la matriz actualizada en la consola
        return eaten;
    }
}
