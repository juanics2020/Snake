package es.juanics.snake;


public class SnakeGame {
    //LA MATRIZ DEL TABLERO TENDRÁ TANTAS FILAS Y COLUMNAS COMO SEA POSIBLE
    //TENIENDO EN CUENTA EL TAMAÑO DE LA ESCENA Y EL TAMAÑO DE LOS VISORES DE LA SERPIENTE
    //SE CALCULARÁN LAS FILAS Y COLUMNAS PARA QUE COINCIDA EL MOVIMIENTO GRÁFICO DE LA SERPIENTE CON EL MOVIMIENTO DE LA MATRIZ
    
    
    private static int filas = 0;//variable para filas matriz   
    private static int columnas = 0;//variable para columnas matriz
    //Declaro la matriz del tablero por ahora sin filas ni columnas]
    static int [][] matrizTablero;//matrizTablero[6][10] = 1; //Poner un 1 en la posición fila 6, columna 10
    
    //Creamos un objeto Snake para utilizar la función que nos da su tamaño
    private Snake snake1 = new Snake(1);
    //Declaro una variable que guardará el tamaño del visor de la serpiente para usarlo en la función de tamañoMatriz
    private int tamSnakeV;
     
    private static int filaActual;//posición actual de la cabeza en la FILA dentro de la matriz //PARA QUE SÓLO LAS CREE UNA VEZ PONEMOS ESTÁTICA (si no las crea de 0 siempre que entra)
    private static int columnaActual;//posición actual de la cabeza en la COLUMNA dentro de la matriz //PARA QUE SÓLO LAS CREE UNA VEZ PONEMOS ESTÁTICA (si no las crea de 0 siempre que entra)  
              

    //Calcula cuantas FILAS Y COLUMNAS tiene la Matriz usando el ancho y alto de la escena y el tamaño del visor de la serpiente
    public void tamañoMatriz(int sceneWidth, int sceneHeight){
        //Llamamos a la función dentro de Snake que le pasa el tamaño que tendrán los visores para calcular el tamaño de la matriz 
        tamSnakeV = snake1.tamañoSnake();
        //Calculamos filas y columnas según alto y ancho de la escena
        filas = (int)(sceneHeight/tamSnakeV);
        columnas = (int)(sceneWidth/tamSnakeV);
        //Declaro la matriz del tablero  ej [13 filas hacia abajo][21 columnas hacia la derecha]
        matrizTablero = new int [filas][columnas];//FILAS Y COLUMNAS TOTALES QUE TENDRÁ LA MATRIZ
        
        System.out.println("Tamaño Snake: "+tamSnakeV+", Nº FILAS TOTALES: "+filas+", Nº COLUMNAS TOTALES: "+columnas);              
    }
    
    //Al inicio del juego rellenaremos la matriz de 0 escepto la mitad de la matriz que será un 1 donde está la cabeza de la serpiente  
    //(1º Rellenamos las celdas de la matriz de 0)
    public SnakeGame (){
        for(int f=0; f<filas; f++){
            for(int c=0; c<columnas; c++){
                matrizTablero[f][c] = 0;
            }        
        }
    } 
    
    
    public void inicioFilaColActual(int filIn, int colIn){
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

    
    public void matrixMovement(int direccion) {  
        System.out.println("DENTRO SWITCH ----> FILA ACTUAL: "+filaActual+", COLUMNA ACTUAL: "+columnaActual);
        switch (direccion) {//Según la tecla pulsada
            case App.D_LEFT: // la matriz se moverá a la IZQUIERDA(sólo la COLUMNA)
                matrizTablero[filaActual][columnaActual] = 0;//Pongo la posición actual a 0
                columnaActual --;
                matrizTablero[filaActual][columnaActual] = 1;//La nueva posición la pongo en 1                                           
                break;
            case App.D_RIGHT: // la matriz se moverá a la DERECHA(sólo la COLUMNA)
                matrizTablero[filaActual][columnaActual] = 0;//Pongo la posición actual a 0
                columnaActual ++;
                matrizTablero[filaActual][columnaActual] = 1;//La nueva posición la pongo en 1
                break;
            case App.D_DOWN: // la matriz se moverá ABAJO(sólo la FILA)
                matrizTablero[filaActual][columnaActual] = 0;//Pongo la posición actual a 0
                filaActual ++;
                matrizTablero[filaActual][columnaActual] = 1;//La nueva posición la pongo en 1
                break;
            case App.D_UP: // la matriz se moverá ARRIBA(sólo la FILA)
                matrizTablero[filaActual][columnaActual] = 0;//Pongo la posición actual a 0
                filaActual --;
                matrizTablero[filaActual][columnaActual] = 1;//La nueva posición la pongo en 1
                break;
        }
        this.mostrarMatrizConsola();//Cada vez que se mueva muestro la matriz actualizada en la consola
    }
}

