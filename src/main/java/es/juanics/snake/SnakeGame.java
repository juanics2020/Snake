package es.juanics.snake;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.effect.Light.Point;

//PARTE LÓGICA
public class SnakeGame {
    //LA MATRIZ DEL TABLERO TENDRÁ TANTAS FILAS Y COLUMNAS COMO LE INDIQUE EL USUARIO
    //EL TAMAÑO DE LA ESCENA SE HA CALCULADO PREVIAMENTE CON LAS FILAS Y COLUMNAS QUE INDICÓ EL USUARIO
    //EL TABLERO SE AJUSTA GRÁFICAMENTE TAMBIÉN CON ESTAS FILAS Y COLUMNAS
    //ASÍ EL MOVIMIENTO GRÁFICO DE LA SERPIENTE COINCIDIRÁ CON EL MOVIMIENTO INTERNO DE LA MATRIZ
    
    public int puntuacion = 0;//va incrementado a medida que come manzanas
    private int filas = 0;//variable para total filas matriz   
    private int columnas = 0;//variable para total columnas matriz
    //Declaro la matriz del tablero por ahora sin filas ni columnas[][]
    public static int [][] matrizTablero;//Ahora mismo no tiene tamaño y está vacía
        
    public static int filaActual;//posición actual de la cabeza en la FILA dentro de la matriz
    //PARA QUE SÓLO LAS CREE UNA VEZ PONEMOS ESTÁTICA (si no, las crea de 0 siempre que entra)
    public static int columnaActual;//posición actual de la cabeza en la COLUMNA dentro de la matriz
    //PARA QUE SÓLO LAS CREE UNA VEZ PONEMOS ESTÁTICA (si no, las crea de 0 siempre que entra)     
    
    public static int appleCol;//Columna aleatoria de la manzana
    public static int appleFil;//Fila aleatoria de la manzana 
    
    public static boolean eaten = false; //static para que no cambie (si no, crearía una variable nueva cada vez que se use)
    public static boolean dead = false;//Cuando en la matriz se muerda la serpiente ella misma, muere. Guardaremos el estado.
   
    public static ArrayList<Point>arrayListCuerpo = new ArrayList();//ArrayList que guardará dos punteros (posición fila, posición columna) del cuerpo
    private double arrayXvacia = App.NUM_EMPTY;
    private double arrayYvacia =  App.NUM_EMPTY;
    private double arrayDireccion =  App.D_DOWN;
    public static int contadorArray = 0;

    
    
    //Al inicio del juego rellenaremos la matriz de 0 (más abajo pondremos la mitad de la matriz a 1 donde está la cabeza de la serpiente) 
    //1º Rellenamos las celdas de la matriz de 0. Más alante pondremos la cabeza y la manzana
    public SnakeGame (int filas, int columnas){ //******NO DEJA USAR DIRECTAMENTE App.filas_totales ni App.columnas_totales
        this.filas = filas;
        this.columnas = columnas;
        
        matrizTablero = new int [this.filas][this.columnas];//LE DECIMOS LAS FILAS Y COLUMNAS TOTALES QUE TENDRÁ LA MATRIZ
        //Tener en cuenta que internamente la matriz siempre es uno menos. Ej: 13 filas (0-12) y 21 columnas (0-20)
        
        System.out.println("");
        System.out.println("Nº FILAS TOTALES: "+this.filas+", Nº COLUMNAS TOTALES: "+this.columnas);
        
        for(int f=0; f<filas; f++){
            for(int c=0; c<columnas; c++){
                matrizTablero[f][c] = App.NUM_EMPTY;//Ponemos la celda de la matriz vacía a 0
            }        
        }
    } 
    
    //Guardo la posción inicial en la que moenzará la serpiente, a mitad de la pantalla. Le paso la fila del centro y la columna del centro
    public void inicioFilaColActual(int filIn, int colIn){
        filaActual = filIn; //Pongo estas variables a la posición inicial, pero después irán cambiando según se mueva la serpiente
        columnaActual = colIn;               
        System.out.println("FILA ACTUAL: "+filaActual+", COLUMNA ACTUAL: "+columnaActual);         
    }
    
    //Mostramos lo que tiene la matriz en la consola
    public void mostrarMatrizConsola(){// 6 filas (0 a 12) y 7 (0 a 20) columnas
        System.out.println("");//PARA QUE DEJE UN SALTO DE LÍNEA EN LA CONSOLA ANTES DE LA MATRIZ       
        for(int f=0; f<filas; f++){
            for(int c=0; c<columnas; c++){
                System.out.print(matrizTablero[f][c]); // mostraría en consola los 0 o lo que tenga dentro (1 cabeza, 2 cuerpo, 3 cola, 4 manzana)
            }
            System.out.println("");//Salto de columna
        }
        System.out.println("");//PARA QUE DEJE UN SALTO DE LÍNEA EN LA CONSOLA DESPUÉS DE LA MATRIZ
    }  
    
    
    //POSICIONES ALEATORIA PARA LA MANZANA
    public int setRandomAppleCol(){//Obtener una columna de la matriz aleatoria
        Random randomX = new Random();
        int rX = randomX.nextInt(columnas);//La X se calcula con las columnas (de izquierda hacia derecha)
        return rX;
    }
    public int setRandomAppleFil(){//Obtener una fila de la matriz aleatoria
        Random randomY = new Random();
        int rY = randomY.nextInt(filas);//La Y se calcula con las filas (de arriba hacia ajabo)
        return rY;
    }
    
    public void setAppleRandom(){//PONER LA MANZANA ALEATORIA SIN QUE PISE LA SERPIENTE (cabeza 1, cuerpo 2, cola 3)   
        do{//Buscar posición aleatoria para la manzana hasta que no pise la serpiente en la matriz
            appleCol = this.setRandomAppleCol();//llamo a estos métodos y obtengo la columna y filas aleatorias
            appleFil = this.setRandomAppleFil();
            System.out.println("Manzana Alearoia en matriz: "+appleFil+", "+appleCol);
        }while (this.matrizTablero[appleFil][appleCol]==App.NUM_HEAD || this.matrizTablero[appleFil][appleCol]==App.NUM_BODY || this.matrizTablero[appleFil][appleCol]==App.NUM_TAIL);    
        //Lo repetirá hasta que encuentre un posición en la que no haya ni 1, ni 2, ni 3 en la matriz (para que no pise a la serpiente)
        
        //Pongo un 4 en la matriz donde esté la manzana con las posiciones aleatorias anteriores
        this.matrizTablero[appleFil][appleCol]=App.NUM_APPLE;
        System.out.println("POSICIÓN ALEATORIA MANZANA: "+"(fila)"+(appleFil+1)+"(columna)"+(appleCol+1)+" (MATRIZ UNO MENOS -> Ej: FILA 13 (0-12) Y COLUMNA 21 (0-20))");           
        System.out.println("");
    }
    
    //Cuando la manzana sea comida subirán los puntos. La parte gráfica en el tablero
    public void appleEatenM(){
        puntuacion++;              
        System.out.println("SE HA COMIDO LA MANZANA ----- OOOOOOOO");
        System.out.println("Puntuación: "+puntuacion);
        System.out.println("*****************************************************"); 
    }
    
    //Cuando reinicie hay que vaciar la celda de la manzana en la matriz
    public void emptyApple(){
        matrizTablero[appleFil][appleCol] = App.NUM_EMPTY;
    }
    
    
    //Mostramos lo que tiene el arraylist de la serpiente en la consola
    public void mostrarArrayListConsola(){
        //PARA QUE pinte **** SALTO DE LÍNEA EN LA CONSOLA ANTES DEL ARRAYLIST 
        System.out.println("******************************************************");
        //Hacer con todos los elementos del arraylist. Si tiene 7 elementos (0-6)
        for(int c=0; c<arrayListCuerpo.size(); c++){
            System.out.println("-----Tamaño ArrayList(Nº PUNTEROS): "+arrayListCuerpo.size());
            System.out.println("Puntero ("+c+") Y (FILA): "+arrayListCuerpo.get(c).getY());
            System.out.println("Puntero ("+c+") X (COLUMNA): "+arrayListCuerpo.get(c).getX()); 
            System.out.println("Puntero ("+c+") Z (Dirección): "+(int)arrayListCuerpo.get(c).getZ());
        }
        System.out.println("******************************************************");             
    }
    
    
    public int setPunteroArrayList(int fila, int columna, int direccion){                
        //OJO!!!! LA Y GUARDA LAS FILAS, Y LA X LAS COLUMNAS!!!!!<------------
        //arrayListCuerpo guardará punteros: X-columnas, Y-filas, Z: dirección de la parte actual de la serpiente
        //X:    de iz a der (columnas)
        //Y:    de arriba a abajo (filas)
        Point p = new Point();
             
        if(arrayListCuerpo.isEmpty()){//Si está vacío no ha comenzado (cabeza)
            //Añado las posiciones de la cabeza de la serpiente al Arraylist que representará la serpiente
            //Si no ha comenzado el juego es para la cabeza de la serpiente y será la fila y columna que le pase y dirección hacia abajo
            p.setX(columna);         
            p.setY(fila);
            p.setZ(App.D_DOWN);
            
        }else{ //Si ya ha comenzado el juego, será una parte del cuerpo
            //Antes de añadir uno nuevo, ordeno los punteros que ya tengo, cada uno coge las posiciones del anterior
            cambioPunteros(fila,columna, direccion);
            
            System.out.println("setPunteroArrayList: FILA "+columna+", COLUMNA "+fila);//hay que tener en cuanta que la X es COLUMNA y la Y es FILA           
            //Si ya ha comenzado el juego las nuevas posiciones serán las posiciones del último puntero
            //el nuevo puntero guarda las posiciones y dirección del que era el último 
            p.setX(arrayXvacia);      
            p.setY(arrayYvacia);
            p.setZ(arrayDireccion);
                       
            //VARIABLE QUE SERVIRÁ DE CONTADOR EN LOS PASOS QUE DE LA SERPIENTE, PARA PODER GUARDAR LAS DIRECCIONES 
            contadorArray++; //Tendrá siempre el tamaño del array -1 (tamaño del array -1 porque empieza en 0)
        }
      
        //Añado el nuevo puntero al arrayList
        arrayListCuerpo.add(p);
        //Si la serpiente tiene ya 3 partes incluída la cabeza, la que era la última parte (antes del nuevo puntero) la pongo en la matriz a 2.
        //Y el nuevo puntero que es la cola la pongo a 3

        //El nuevo puntero siempre es la cola en la matriz (escepto si es la cabeza)
        if(arrayListCuerpo.size()>1){//Si no solo está la cabeza
            if(arrayListCuerpo.size()>2){//Tiene al menos 3 partes ya. La pieza anterior a la cola la pongo a 2 en la matriz
                matrizTablero[(int)arrayListCuerpo.get(arrayListCuerpo.size()-2).getY()][(int)arrayListCuerpo.get(arrayListCuerpo.size()-2).getX()] = App.NUM_BODY;
            }
            //La última parte la pongo a 3 porque es la cola
            matrizTablero[(int)arrayListCuerpo.get(arrayListCuerpo.size()-1).getY()][(int)arrayListCuerpo.get(arrayListCuerpo.size()-1).getX()] = App.NUM_TAIL;
        }else{//si es la cabeza sóla y no hay más partes del cuerpo
            matrizTablero[fila][columna] = App.NUM_HEAD;//Pongo el 1 a mitad de la matriz que corresponde a la Cabeza de la serpiente 
        }
               
        System.out.println("*****************************************************************************************************");
        System.out.println("NUEVO PUNTERO "+(arrayListCuerpo.size()-1)+", Y("+p.getY()+") [FILA] - X("+p.getX()+") [COLUMNA] - Z("+(int)p.getZ()+") <DIRECCIÓN>");
        System.out.println("*****************************************************************************************************");
                
        return (contadorArray);//devuelve el número de puntero que se ha creado (índice)
    }
    
    
    public void cambioPunteros(int filaActual, int columnaActual, int direccion){
        //La COLA en el arrayList me da igual, será en la MATRIZ (3) donde tendré que ir pasando la cola al final        
        //Cada vez que se mueva la serpiente gráficamente en Tablero, lo harán según las posiciones de los punteros X e Y (sumando o restando)       
        //A partir de la cabeza, pongo el puntero en la posición del anterior.
        //Por último, la cabeza la pongo en fila y columna actual
        //La cabeza(0) la dejo para lo último porque le pondremos la posición fila y columna actual.Empiezo en 1 (el segundo puntero después de la cabeza)
              
        //Con la posición que se queda vacia (donde estaba la cola antes de moverla) haré dos cosas:
        //1º guardo las posiciones y dirección por si hay que crear un puntero nuevo en ese sitio (se ha comido la manzana)
        //2º  lo pongo a 0 en la matriz
        //tamaño del array -1 sería el último elemento del array. Si tiene 3 de tamaño, el último elemento es el 2 (0-2)
        arrayXvacia=arrayListCuerpo.get(arrayListCuerpo.size()-1).getX(); 
        arrayYvacia=arrayListCuerpo.get(arrayListCuerpo.size()-1).getY();
        arrayDireccion=arrayListCuerpo.get(arrayListCuerpo.size()-1).getZ();
        
        //Donde estaba la cola en la última posición se queda en 0 en la matriz
        matrizTablero[(int)arrayYvacia][(int)arrayXvacia] = App.NUM_EMPTY;
        
        //El 0 no porque la cabeza la asigno abajo con las posiciones actuales. Los demás punteros van cogiendo la posición del anterior
        for(int c=contadorArray; c>0; c--){
            //Pongo las nuevas posiciones en el ARRAYLIST (del puntero inmediatamente anterior)
            arrayListCuerpo.get(c).setX(arrayListCuerpo.get(c-1).getX());
            arrayListCuerpo.get(c).setY(arrayListCuerpo.get(c-1).getY());
            arrayListCuerpo.get(c).setZ(arrayListCuerpo.get(c-1).getZ());
            
            //Pongo los 2 y el 3 en la MATRIZ (2:cuerpo, 3:cola) **El 1 de la cabeza se pone abajo en matrizMovement (a medida que se mueve)
            //la posición en la matriz en la que estaba la cola la pongo a 0
            if(c==contadorArray){    //COLA            
                matrizTablero[(int)arrayListCuerpo.get(c).getY()][(int)arrayListCuerpo.get(c).getX()] = App.NUM_TAIL;
            }else{ //CUERPO
                matrizTablero[(int)arrayListCuerpo.get(c).getY()][(int)arrayListCuerpo.get(c).getX()] = App.NUM_BODY;
            }
            
        }           
        //Una vez que todos los punteros se han cambiado a la posición del anterior, pongo la cabeza a la posición actual, que es donde se ha movido en la matriz
        arrayListCuerpo.get(0).setX(columnaActual);
        arrayListCuerpo.get(0).setY(filaActual);
        arrayListCuerpo.get(0).setZ(direccion);
        //La dirección la guarda en matrixMovement
        System.out.println("cambioPunteros: FILA ACTUAL "+filaActual+", COLUMNA ACTUAL "+columnaActual);//hay que tener en cuanta que la X es COLUMNA y la Y es FILA           
    }
    
    
    
    public void matrixMovement(int direccion) {  //MOVIEMIENTO LÓGICO DE LA SERPIENTE EN LA MATRIZ
        //CUANDO SE COMA LA MANZANA TIENE QUE APARECE UN NUEVO PUNTERO EN EL ARRAY LIST
        System.out.println("DENTRO SWITCH (Antes del paso) ----> FILA ACTUAL: "+filaActual+", COLUMNA ACTUAL: "+columnaActual);
        
        switch (direccion) {//Según la tecla pulsada
            case App.D_LEFT: // la matriz se moverá a la IZQUIERDA(sólo la COLUMNA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0               
                columnaActual --;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come
                    eaten = true;   
                //SI LA SERPIENTE ESTÁ ENCIMA DE ELLA MISMA MUERE
                }else if(matrizTablero[filaActual][columnaActual] == App.NUM_BODY || matrizTablero[filaActual][columnaActual] == App.NUM_TAIL){
                    eaten = false;
                    dead = true;
                }else{//SI NO ESTÁ BIEN Y SIGUE
                    eaten = false;
                    dead = false;
                }                                                        
                break;


            case App.D_RIGHT: // la matriz se moverá a la DERECHA(sólo la COLUMNA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0               
                columnaActual ++;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come                
                    eaten = true;
                //SI LA SERPIENTE ESTÁ ENCIMA DE ELLA MISMA MUERE
                }else if(matrizTablero[filaActual][columnaActual] == App.NUM_BODY || matrizTablero[filaActual][columnaActual] == App.NUM_TAIL){
                    eaten = false;
                    dead = true;
                }else{//SI NO ESTÁ BIEN Y SIGUE
                    eaten = false;
                    dead = false;
                }               
                break;


            case App.D_DOWN: // la matriz se moverá ABAJO(sólo la FILA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0               
                filaActual ++;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come                 
                    eaten = true;
                //SI LA SERPIENTE ESTÁ ENCIMA DE ELLA MISMA MUERE
                }else if(matrizTablero[filaActual][columnaActual] == App.NUM_BODY || matrizTablero[filaActual][columnaActual] == App.NUM_TAIL){
                    eaten = false;
                    dead = true;
                }else{//SI NO ESTÁ BIEN Y SIGUE
                    eaten = false;
                    dead = false;
                }               
                break;


            case App.D_UP: // la matriz se moverá ARRIBA(sólo la FILA)
                matrizTablero[filaActual][columnaActual] = App.NUM_EMPTY;//Pongo la posición actual a 0                
                filaActual --;//Movemos una posición 
                //SI LA SERPIENTE ESTÁ ENCIMA DE LA MANZANA
                if (matrizTablero[filaActual][columnaActual] == App.NUM_APPLE){//Está encima de la manzana, se la come                  
                    eaten = true;
                //SI LA SERPIENTE ESTÁ ENCIMA DE ELLA MISMA MUERE
                }else if(matrizTablero[filaActual][columnaActual] == App.NUM_BODY || matrizTablero[filaActual][columnaActual] == App.NUM_TAIL){
                    eaten = false;
                    dead = true;
                }else{//SI NO ESTÁ BIEN Y SIGUE
                    eaten = false;
                    dead = false;
                }              
                break;
        }
        //SERPIENTE EN LA MATRIZ A LA NUEVA POSICIÓN 
        matrizTablero[filaActual][columnaActual] = App.NUM_HEAD;//La nueva posición la pongo en 1
        System.out.println("DENTRO SWITCH (Después del paso) ----> FILA ACTUAL: "+filaActual+", COLUMNA ACTUAL: "+columnaActual);
    }
}
