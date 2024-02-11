import java.util.Scanner;

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
                    gestionFichero.listaPreguntas();
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

        // Mustra los tipos de preguntas que se pueden añadir
        System.out.println("\nDe que tipo es la pregunta: ");

        System.out.println("\n1. Pregunta simple");
        System.out.println("2. Pregunta con respuesta multiple");
        System.out.println("3. Pregunta de si o no");

        System.out.println("\nPor favor, elija una de las opciónes anteriores: ");
        int tipo = -1;

        do {
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
                System.out.println("\nIntroduzca la respuesta correcta:");
                scanner.nextLine();
                do {
                    respuestaCorrecta = scanner.nextLine();

                    if (respuestaCorrecta.length() > 199) {
                        System.out.println("La respuesta no puede tener mas de 199 caracteres");
                    }
                } while (respuestaCorrecta.length() > 199);   

                opciones += respuestaCorrecta + "|";

                for (int i = 0; i < 2; i++) {
                    System.out.println("\nIntroduzca una opcion:");
                    String opcion = "";
                    do {
                        opcion = scanner.nextLine();
    
                        if (opcion.length() > 199) {
                            System.out.println("La opcion no puede tener mas de 199 caracteres");
                        }
                    } while (opcion.length() > 199);   

                    opciones += opcion + "|";
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
        int mensajeError = gestionFichero.añadirPregunta(pregunta, tipo, respuestaCorrecta, opciones);

        if (mensajeError == 0) {
            System.out.println("\nLa pregunta se ha añadido correctamente");
        } else {
            System.out.println("\nHa habido un error al añadir la pregunta");
        }
    }
}
