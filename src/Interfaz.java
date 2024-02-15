import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Esta clase se encarga de mostrar el menú principal de la aplicación y de gestionar todas las opciones que se pueden realizar.
 * 
 * @version 1.0
 * @since 1.0
 */
public class Interfaz {
    public static Scanner scanner = new Scanner(System.in);
    public static GestionFichero gestionFichero = new GestionFichero();

    /**
     * Este metodo se encarga de mostrar el menú principal de la aplicación y de gestionar todas las opciones que se pueden realizar.
     * 
     * @param args Argumentos que se pueden pasar al ejecutar la aplicación. No se utilizan.
     */
    public static void main(String[] args) {
        int opcion = -1;
        do {
            System.out.println("\nOpciones:\n");
            System.out.println("1. Añadir pregunta");
            System.out.println("2. Ver lista de preguntas");
            System.out.println("3. Buscar una pregunta");
            System.out.println("4. Modificar una pregunta");
            System.out.println("5. Modificar el estado de una pregunta");
            System.out.println("6. Elegir pregunta al azar");
            System.out.println("7. Salir\n");

            do {
                System.out.println("Por favor, elija una de las opciónes anteriores: ");
                opcion = introducirNumero();
                if ((opcion < 1) || (opcion > 7)) {
                    System.out.println("Opcion no valida");
                }
            } while ((opcion == -1) || (opcion < 1) || (opcion > 7));

            switch (opcion) {
                case 1:
                    añadirPregunta();
                    break;
                case 2:
                    verListaPreguntas();
                    break;
                case 3:
                    buscarPregunta();
                    break;
                case 4:
                    modificarPregunta();
                    break;
                case 5:
                    modificarEstadoPregunta();
                    break;
                case 6:
                        elegirPreguntaAlAzar();
                    break;
                default:
                    
                    break;
            }
        } while (opcion != 7);
    }

    /**
     * Este metodo se encarga de leer un numero por teclado y devolverlo.
     * 
     * @return Devuelve el numero introducido por teclado. Si no es un numero devuelve -1.
     */
    public static int introducirNumero() {
        int num = -1;

        try {
            num = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Solo es posible introducir numeros");
            scanner.nextLine();
        }

        return num;
    }

    /**
     * 
     * Opcion 1 Añadir pregunta
     * Este metodo se encarga de preguntar todos los datos necesarios de una pregunta para despues añadirla al fichero.
     */
    public static void añadirPregunta() {
        String pregunta = introducirPregunta();
        
        // Mustra las categorias de preguntas que se pueden añadir
        System.out.println("\nDe que categoria es la pregunta: ");

        System.out.println("\n1. Geografía");
        System.out.println("2. Entretenimiento");
        System.out.println("3. Historia");
        System.out.println("4. Arte y Literatura");
        System.out.println("5. Deportes y Ocio");
        System.out.println("6. Ciencia y Naturaleza");
        int categoria;

        do {
            System.out.println("\nPor favor, elija una de las opciónes anteriores: ");
            categoria = introducirNumero();
        } while (categoria < 1 || categoria > 6);

        // Mustra los tipos de preguntas que se pueden añadir
        System.out.println("\nDe que tipo es la pregunta: ");

        System.out.println("\n1. Pregunta simple");
        System.out.println("2. Pregunta con respuesta multiple");
        System.out.println("3. Pregunta de si o no");

        int tipo = -1;

        do {
            System.out.println("\nPor favor, elija una de las opciónes anteriores: ");
            tipo = introducirNumero();
        } while (tipo < 1 || tipo > 3);

        String[] respuestaYOpciones = introducirRespuestaYOpciones(tipo);

        // Añade la pregunta al fichero y muestra un mensaje de error si ha habido alguno
        int mensajeError = gestionFichero.añadirPregunta(pregunta, categoria, tipo, respuestaYOpciones[0], respuestaYOpciones[1]);

        if (mensajeError == 0) {
            System.out.println("\nLa pregunta se ha añadido correctamente");
        } else {
            System.out.println("\nHa habido un error al añadir la pregunta");
        }
    }

    /**
     * Este metodo se encarga de pedir una pregunta por teclado.
     * 
     * @return Devuelve la pregunta introducida por teclado.
     */
    public static String introducirPregunta() {
        String pregunta = "";
        scanner.nextLine();

        // Pregunta por la pregunta, esta no puede tener mas de 600 caracteres
        System.out.println("\nIntroduzca la pregunta: ");
        
        do {
            pregunta = scanner.nextLine();

            if (pregunta.length() > 300) {
                System.out.println("La pregunta no puede tener mas de 300 caracteres");
            }
        } while (pregunta.length() > 300);

        return pregunta;
    }

    /**
     * Este metodo se encarga de pedir una respuesta y las opciones de una pregunta por teclado.
     * 
     * @param tipo Tipo de pregunta que se va a introducir.
     * @return Devuelve un array de String con la respuesta y las opciones de la pregunta.
     */
    public static String[] introducirRespuestaYOpciones(int tipo) {
        String respuestaCorrecta = "";
        String opciones = "";

        // Segun el tipo de pregunta, se pide una respuesta u otra
        switch (tipo) {
            case 1: // Pregunta simple
                // La respuesta no puede tener mas de 200 caracteres
                System.out.println("\nIntroduzca la respuesta correcta:");
                scanner.nextLine();
                do {
                    respuestaCorrecta = scanner.nextLine();

                    if (respuestaCorrecta.length() > 100) {
                        System.out.println("La respuesta no puede tener mas de 100 caracteres");
                    }
                } while (respuestaCorrecta.length() > 100);    
                break;
            case 2: // Pregunta con respuesta multiple
                // La respuesta no puede tener mas de 199 caracteres y las opciones no mas de 199 caracteres
                // Este tenvien elige aleatioriamente la posicion de las opciones
                String[] arrayOpciones = new String[3];
                System.out.println("\nIntroduzca la respuesta correcta:");
                scanner.nextLine();
                do {
                    respuestaCorrecta = scanner.nextLine();

                    if (respuestaCorrecta.length() > 99) {
                        System.out.println("La respuesta no puede tener mas de 99 caracteres");
                    }
                } while (respuestaCorrecta.length() > 99);   

                arrayOpciones[0] = respuestaCorrecta;

                for (int i = 0; i < 2; i++) {
                    System.out.println("\nIntroduzca una opcion:");
                    String opcion = "";
                    do {
                        opcion = scanner.nextLine();
    
                        if (opcion.length() > 99) {
                            System.out.println("La opcion no puede tener mas de 99 caracteres");
                        }
                    } while (opcion.length() > 99);   

                    arrayOpciones[i + 1] = opcion;
                }

                // Este elige aleatoriamente la posicion de las opciones
                int[] opcionesPosiciones = {0 , 1, 2};
                Set<Integer> posicionesUsadas = new HashSet<>();
                for (int i = 0; i < arrayOpciones.length; i++) {
                    int posicionGenerada;
                    do {
                        posicionGenerada = (int) (Math.random() * 3);
                    } while (posicionesUsadas.contains(posicionGenerada));
        
                    opcionesPosiciones[i] = posicionGenerada;
                    posicionesUsadas.add(posicionGenerada);
                }

                // Guarda en un String las opciones para guardarlos en el fichero
                for (int i = 0;i < opcionesPosiciones.length;i++) {
                    if (i < (opcionesPosiciones.length -1)) {
                        opciones += arrayOpciones[opcionesPosiciones[i]] + "|";
                    }
                    else {opciones += arrayOpciones[opcionesPosiciones[i]];}
                }
                break;
            case 3: // Pregunta de si o no
                scanner.nextLine();
                System.out.println();

                do {
                    System.out.println("Introduzca la respuesta correcta (si/no):");
                    respuestaCorrecta = scanner.nextLine();
                } while (respuestaCorrecta.equals("si") == false && respuestaCorrecta.equalsIgnoreCase("no") == false);

                opciones = "si|no";
                break;
            default:
                break;
        }

        return new String[] {respuestaCorrecta, opciones};
    }

    /**
     * Opcion 2 Ver lista de preguntas
     * Este metodo se encarga de mostrar todas las preguntas que hay en el fichero.
     */
    public static void verListaPreguntas() {
        String[][] listaPreguntas = gestionFichero.listaPreguntas();

        if (listaPreguntas == null) {
            System.out.println("\nLo siento, algo ha ido mal al intentar leer el fichero de preguntas y respuestas.");
            System.out.println("No hay preguntas en el fichero");
        } else {
            for (int i = 0; i < listaPreguntas.length; i++) {
                System.out.println("\nPregunta numero " + (i + 1) + ":");

                String[] pregunta = listaPreguntas[i];

                mostrarPregunta(pregunta);
            }
        }
    }

    /**
     * Este metodo se encarga de mostrar una pregunta con todos sus datos.
     * 
     * @param pregunta Array de String con los datos de la pregunta.
     */
    public  static void mostrarPregunta(String[] pregunta) {
        System.out.println("\nID: " + pregunta[0]);
        System.out.println("Pregunta: " + pregunta[1]);

        System.out.print("Categoria: ");
        int categoria = Integer.parseInt(pregunta[2]);
        switch (categoria) {
            case 1:
                System.out.println("Geografía");
                break;
            case 2:
                System.out.println("Entretenimiento");
                break;
            case 3:
                System.out.println("Historia");
                break; 
            case 4:
                System.out.println("Arte y Literatura");
                break;
            case 5:
                System.out.println("Deportes y Ocio");
                break;
            case 6:
                System.out.println("Ciencia y Naturaleza");
                break;
            default:
                break;
        }

        System.out.print("Tipo: ");
        int tipo = Integer.parseInt(pregunta[3]);
        switch (tipo) {
            case 1:
                System.out.println("Pregunta simple");
                break;
            case 2:
                System.out.println("Pregunta con respuesta multiple");
                break;
            case 3:
                System.out.println("Pregunta de si o no");
                break; 
            default:
                break;
        }

        System.out.println("Respuesta correcta: " + pregunta[4]);

        if (!pregunta[5].equals("")) {
            String[] separatedStrings = pregunta[5].split("\\|");

            for (int i = 0; i < separatedStrings.length; i++) {
                System.out.print("Opcion " + (i + 1) + ": ");
                System.out.println(separatedStrings[i]);
            }
        }

        System.out.print("Estado: ");
        if (pregunta[6].equals("0")) {
            System.out.println("Activa");
        } else {
            System.out.println("Inactiva");
        }
    }

    /**
     * Opcion 3 Buscar una pregunta
     * Este metodo se encarga de buscar una pregunta
     * Esta puede ser buscada por su id
     */
    public static void buscarPregunta() {
        System.out.println("\nPor que parametro deseas buscar la pregunta");

        System.out.println("\n1. Id");

        int opcion;

        do {
            System.out.println("\nPor favor, elija una de las opciónes anteriores: ");
            opcion = introducirNumero();
        } while (opcion < 1 || opcion > 1);

        switch (opcion) {
            case 1:
                int id;
                do {
                    System.out.print("Introduce el Id: ");
                    id = introducirNumero();
                    if (id < 1) {
                        System.out.println("Solo es posible introducir un id mayor o igual a 1");
                    }
                } while (id < 1);

                String[] pregunta = gestionFichero.buscarId(id);

                if (pregunta != null) {mostrarPregunta(pregunta);}
                else {System.out.println("No se ha encontrado ninguna pregunta con ese Id.");}

                break;
            default:
                break;
        }
    }

    /**
     * Opcion 4 Modificar una pregunta
     * Este metodo se encarga de buscar una pregunta por su id y modificarla
     */
    public static void modificarPregunta() {
        int id;

        System.out.print("Introduce el Id: ");
        id = introducirNumero();
        if (id < 1) {
            System.out.println("Solo es posible introducir un id mayor o igual a 1");
        }

        String[] pregunta = gestionFichero.buscarId(id);

        if (pregunta != null) {
            mostrarPregunta(pregunta);

            System.out.println("\nQue parametro deseas modificar");

            System.out.println("\n1. Pregunta");
            System.out.println("2. Respuesta");

            int opcion;

            do {
                System.out.println("Seleciona una de las opciones anteriores: ");
                opcion = introducirNumero();
            } while (opcion < 1 || opcion > 2);

            switch (opcion) {
                case 1:
                    String nuevaPregunta = introducirPregunta();
                    
                    int mensajeError = gestionFichero.modificarPregunta(id, nuevaPregunta);

                    if (mensajeError == 0) {
                        System.out.println("La pregunta se ha modificado correctamente");
                    } else {
                        System.out.println("Ha habido un error al modificar la pregunta");
                    }
                    break;
                case 2:
                    String[] respuestaYOpciones = introducirRespuestaYOpciones(Integer.parseInt(pregunta[3]));

                    int mensajeError2 = gestionFichero.modificarRespuestaYOpciones(id, respuestaYOpciones[0], respuestaYOpciones[1]);

                    if (mensajeError2 == 0) {
                        System.out.println("La respuesta se ha modificado correctamente");
                    } else {
                        System.out.println("Ha habido un error al modificar la respuesta");
                    }
                    break;
                default:
                    break;
            }
        }
        else {System.out.println("No se ha encontrado ninguna pregunta con ese Id.");}
    }

    /**
     * Opcion 5 Modificar el estado de una pregunta
     * Este metodo se encarga de buscar una pregunta por su id y modificar su estado
     */
    public static void modificarEstadoPregunta() {
        int id;

        do {
            System.out.print("Introduce el Id: ");
            id = introducirNumero();
            if (id < 1) {
                System.out.println("Solo es posible introducir un id mayor o igual a 1");
            }
        } while (id < 1);

        String[] pregunta = gestionFichero.buscarId(id);

        if (pregunta != null) {
            mostrarPregunta(pregunta);

            int estado;

            System.out.println("\nQue estado deseas que tenga la pregunta");
            System.out.println("\n1. Activa");
            System.out.println("2. Inactiva\n");

            do {
                System.out.print("Introduce el estado: ");
                estado = introducirNumero();
                if (estado < 1 || estado > 2) {
                    System.out.println("Solo es posible introducir uno de los estados anteriores");
                }
            } while (estado < 1 || estado > 2);

            int mensajeError = gestionFichero.modificarEstado(id,(estado - 1));

            if (mensajeError == 0) {
                System.out.println("Se a canviado el estado de la pregunta correctamente");
            } else {
                System.out.println("No se a canviado el estado de la pregunta");
            }
        }
        else {System.out.println("No se ha encontrado ninguna pregunta con ese Id.");}
    }

    /**
     * Opcion 6 Elegir pregunta al azar
     * Este metodo se encarga de elegir una pregunta al azar
     */
    public static void elegirPreguntaAlAzar() {
        System.out.println("\nQue tipo de pregunta quieres que sea");
        System.out.println("\n1. Pregunta aleatoria");
        System.out.println("2. Pregunta por categoria");

        int opcion;
        do {
            System.out.println("\nPor favor, elija una de las opciónes anteriores: ");
            opcion = introducirNumero();
        } while (opcion < 1 || opcion > 2);

        switch (opcion) {
            case 1:
                String[] pregunta = gestionFichero.preguntaAleatoria();

                preguntaYRespuesta(pregunta);
                break;
            case 2:
                System.out.println("\nDe que categoria deseas que sea la pregunta");

                System.out.println("\n1. Geografía");
                System.out.println("2. Entretenimiento");
                System.out.println("3. Historia");
                System.out.println("4. Arte y Literatura");
                System.out.println("5. Deportes y Ocio");
                System.out.println("6. Ciencia y Naturaleza\n");

                System.out.println();
                int categoria;

                do {
                    System.out.println("Introduce una de las categorias anteriores");
                    categoria = introducirNumero();
                } while (categoria < 1  || categoria > 7);

                String[] preguntaPorCategoria = gestionFichero.aleatoriaPorCategoria(categoria);

                preguntaYRespuesta(preguntaPorCategoria);
                break;
            default:
                break;
        }
    }

    /**
     * Este metodo muestra la pregunta y le pide al usuario la respuesta
     * Este muestra si la pregunta correcta o no
     * 
     * @param pregunta Array de String con todos los datos de la pregunta
     * 
     */
    public static void preguntaYRespuesta(String[] pregunta) {
        if (pregunta != null) {
            System.out.println("\nLa pregunta elegida es:");
            System.out.println(pregunta[1]);
            System.out.println();

            if (!pregunta[5].equals("")) {
                String[] separatedStrings = pregunta[5].split("\\|");

                for (int i = 0; i < separatedStrings.length; i++) {
                    System.out.println("Opcion  " + (i+1) + ": " + separatedStrings[i] );
                }
            }
            
            System.out.println("\nIntroduze la respuesta correcta:");
            scanner.nextLine(); //Para saltar un salto de linea
            String respuesta = scanner.nextLine();

            byte[] respuestaByte = new byte[200];

            // Asigna los datos de la respuesta
            for (int i = 0; i < respuestaByte.length; i++) {
                if (respuesta.length() > i) {
                    respuestaByte[i] = (byte) respuesta.charAt(i);
                }
                else {
                    respuestaByte[i] = 0;
                }
            }

            respuesta = new String(respuestaByte);

            if (respuesta.equals(pregunta[4])) {
                System.out.println("\nLa respuesta es correcta");
            } else {
                System.out.println("\nLa respuesta es incorrecta");
                System.out.println("\nLa respuesta correcta es: " + pregunta[4]);
            }
            
        } else {
            System.out.println("No hay preguntas en el fichero");
        }
    }
}
