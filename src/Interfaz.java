import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Interfaz {
    public static Scanner scanner = new Scanner(System.in);
    public static GestionFichero gestionFichero = new GestionFichero();

    //Método para mostrar el menú principal de la aplicación
    public static void main(String[] args) {
        int opcion = -1;
        do {
            System.out.println("\nOpciones:\n");
            System.out.println("1. Añadir pregunta");
            System.out.println("2. Ver lista de preguntas");
            System.out.println("3. Buscar una pregunta");
            System.out.println("4. Modificar una pregunta");
            System.out.println("5. Borrar una pregunta");
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
                    
                    break;
                case 4:
                    
                    break;
                case 5:
                    
                    break;
                case 6:
                    
                    break;
                case 7:
                    
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
        String pregunta = "";
        scanner.nextLine();

        // Pregunta por la pregunta, esta no puede tener mas de 600 caracteres
        System.out.println("\nIntroduzca la pregunta: ");
        
        do {
            pregunta = scanner.nextLine();

            if (pregunta.length() > 600) {
                System.out.println("La pregunta no puede tener mas de 600 caracteres");
            }
        } while (pregunta.length() > 600);

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

                    if (respuestaCorrecta.length() > 200) {
                        System.out.println("La respuesta no puede tener mas de 200 caracteres");
                    }
                } while (respuestaCorrecta.length() > 200);    
                break;
            case 2: // Pregunta con respuesta multiple
                // La respuesta no puede tener mas de 199 caracteres y las opciones no mas de 199 caracteres
                // Este tenvien elige aleatioriamente la posicion de las opciones
                String[] arrayOpciones = new String[3];
                System.out.println("\nIntroduzca la respuesta correcta:");
                scanner.nextLine();
                do {
                    respuestaCorrecta = scanner.nextLine();

                    if (respuestaCorrecta.length() > 199) {
                        System.out.println("La respuesta no puede tener mas de 199 caracteres");
                    }
                } while (respuestaCorrecta.length() > 199);   

                arrayOpciones[0] = respuestaCorrecta;

                for (int i = 0; i < 2; i++) {
                    System.out.println("\nIntroduzca una opcion:");
                    String opcion = "";
                    do {
                        opcion = scanner.nextLine();
    
                        if (opcion.length() > 199) {
                            System.out.println("La opcion no puede tener mas de 199 caracteres");
                        }
                    } while (opcion.length() > 199);   

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

        // Añade la pregunta al fichero y muestra un mensaje de error si ha habido alguno
        int mensajeError = gestionFichero.añadirPregunta(pregunta, categoria, tipo, respuestaCorrecta, opciones);

        if (mensajeError == 0) {
            System.out.println("\nLa pregunta se ha añadido correctamente");
        } else {
            System.out.println("\nHa habido un error al añadir la pregunta");
        }
    }

    /**
     * Opcion 2 Ver lista de preguntas
     * Este metodo se encarga de mostrar todas las preguntas que hay en el fichero.
     */
    public static void verListaPreguntas() {
        ArrayList<String[]> listaPreguntas = gestionFichero.listaPreguntas();

        if (listaPreguntas == null) {
            System.out.println("\nLo siento, algo ha ido mal al intentar leer el fichero de preguntas y respuestas.");
            System.out.println("No hay preguntas en el fichero");
        } else {
            for (int i = 0; i < listaPreguntas.size(); i++) {
                System.out.println("\nPregunta numero " + (i + 1) + ":");

                mostrarPregunta(listaPreguntas.get(i));
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
}
