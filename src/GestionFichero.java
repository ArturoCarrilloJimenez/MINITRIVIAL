import java.io.RandomAccessFile;

/**
 * 
 * Esta clase se encarga de gestionar el fichero de preguntas y respuestas.
 * 
 * @version 1.0
 * @since 1.0
 */
public class GestionFichero {
    private static final String NOMBRE_FICHERO = "fichero.bin";
    public static final int TAMAÑO_PREGUNTA = 1416;

    /**
     * Este metodo añade una pregunta al fichero de preguntas y respuestas, si el fichero no existe lo crea.
     * Este tiene un formato de 6 campos: idPregunta (int), pregunta (String), categoria(int), tipo(int), respuestaCorrecta (String), opciones (String), estado (int) 
     * Este tiene un tamaño de 1416 bytes.
     * 
     * @param pregunta
     * @param categoria
     * @param tipo
     * @param respuestaCorrecta
     * @param opciones
     * 
     * @return Devuelve 0 si se ha añadido correctamente, 1 si ha habido un error.
     */
    public int añadirPregunta(String pregunta, int categoria, int tipo, String respuestaCorrecta, String opciones) {
        int mensajeError = 0;

        RandomAccessFile file = null;

        // Array de bytes para que la pregunta ocupe siempre 600 bytes
        byte[] preguntaByte = new byte[600];

        // Asigna los datos de pregunta
        for (int i = 0; i < preguntaByte.length; i++) {
            if (pregunta.length() > i) {
                preguntaByte[i] = (byte) pregunta.charAt(i);
            }
            else {
                preguntaByte[i] = 0;
            }
        }
                                                    
        // Array de bytes para que la respuesta correcta ocupa siempre 200 bytes
        byte[] respuestaCorrectaByte = new byte[200];
        
        // Asigna los datos de la respuesta
        for (int i = 0; i < respuestaCorrectaByte.length; i++) {
            if (respuestaCorrecta.length() > i) {
                respuestaCorrectaByte[i] = (byte) respuestaCorrecta.charAt(i);
            }
            else {
                respuestaCorrectaByte[i] = 0;
            }
        }

        // Array de bytes para que las opciones ocupen siempre 600 bytes
        byte[] opcionesByte = new byte[600];

        // Asigna los datos de las opciones
        for (int i = 0; i < opcionesByte.length; i++) {
            if (opciones.length() > i) {
                opcionesByte[i] = (byte) opciones.charAt(i);
            }
            else {
                opcionesByte[i] = 0;
            }
        }


        int id = getId();

        try {
            file = new RandomAccessFile(NOMBRE_FICHERO, "rw");

            file.seek(file.length());

            file.writeInt(id);
            file.write(preguntaByte);
            file.writeInt(categoria);
            file.writeInt(tipo);
            file.write(respuestaCorrectaByte);
            file.write(opcionesByte);
            file.writeInt(0);

            file.close();
        } catch (Exception e) {
            mensajeError = 1;
        }

        return mensajeError;
    }

    /**
     * Este metodo salta 1 pregunta (1412 byte) y cuanta cuantas preguntas hay en el fichero.
     * Una vez que ha contado las preguntas, devuelve el numero de preguntas.
     * Si el fichero no existe devuelve 1.
     * 
     * @param id Este es el identificador de la pregunta que se quiere buscar.
     * @return Devuelve el numero de preguntas que hay en el fichero mas 1 (id).
     */
    private int getId() {
        int id = 1;

        RandomAccessFile file = null;
        
        try {
            file = new RandomAccessFile(NOMBRE_FICHERO, "r");
            while (file.getFilePointer() < file.length()) {
                file.seek(id * TAMAÑO_PREGUNTA);
                id++;
            }
            file.close();
        } catch (Exception e) {
            id = 1;
        }

        return id;
    }

    /**
     * Este metodo muestra todas las preguntas que hay en el fichero.
     * 
     * @param arrayList Este es una matriz de preguntas que se quiere mostrar.
     * 
     * @return Devuelve null si no hay preguntas, si hay preguntas devuelve una matriz de preguntas.
     */
    public String[][] listaPreguntas() {
        String[][] arrayList = null;
        RandomAccessFile file = null;

        try {
            file = new RandomAccessFile(NOMBRE_FICHERO, "r");

            arrayList = new String[(int)(file.length() / TAMAÑO_PREGUNTA)][7];
            int cont = 0;
            while (file.getFilePointer() < file.length()) {

                arrayList[cont][0] = String.valueOf(file.readInt()); //ID

                byte[] pregunta = new byte[600];
                file.read(pregunta);
                arrayList[cont][1] = new String(pregunta); //Pregunta

                arrayList[cont][2] = String.valueOf(file.readInt()); //CategoriaRespuesta

                arrayList[cont][3] = String.valueOf(file.readInt()); //TipoRespuesta

                byte[] respuesta = new byte[200];
                file.read(respuesta);
                arrayList[cont][4] = new String(respuesta); //RespuestaCorrecta
                
                byte[] opcion = new byte[600];
                file.read(opcion);
                arrayList[cont][5] = new String(opcion); //Opciones

                arrayList[cont][6] = String.valueOf(file.readInt()); //Estado

                cont++;
            }

            file.close();
        } catch (Exception e) {
            arrayList = null;
        }

        return arrayList;
    }

    /** 
     * 
     * Metodo que busca una pregunta por su id y debuelve en array de String
     * 
     * @return Este devuelve null si no encuentra nada y si a encontrado la pregunta este devuelve un array de String
    */
    public String[] buscarId(int id) {
        String[] registro = null;

        RandomAccessFile file = null;
        
        try {
            file = new RandomAccessFile(NOMBRE_FICHERO, "r");

            file.seek((id - 1) * TAMAÑO_PREGUNTA);

            registro = new String[7];

            registro[0] = String.valueOf(file.readInt()); //ID

            byte[] pregunta = new byte[600];
            file.read(pregunta);
            registro[1] = new String(pregunta); //Pregunta

            registro[2] = String.valueOf(file.readInt()); //CategoriaRespuesta

            registro[3] = String.valueOf(file.readInt()); //TipoRespuesta

            byte[] respuesta = new byte[200];
            file.read(respuesta);
            registro[4] = new String(respuesta); //RespuestaCorrecta
            
            byte[] opcion = new byte[600];
            file.read(opcion);
            registro[5] = new String(opcion); //Opciones

            registro[6] = String.valueOf(file.readInt()); //Estado

            file.close();
        } catch (Exception e) {
            registro = null;
        }

        return registro;
    }

    /**
     * Este metodo modifica el estado de una pregunta.
     * 
     * @param id Este es el identificador de la pregunta que se quiere modificar.
     * @param pregunta Esta es la pregunta que se le quiere asignar a la pregunta.
     * 
     * @return Devuelve 0 si se ha modificado correctamente, 1 si ha habido un error.
     */
    public int modificarPregunta(int id, String pregunta) {
        int mensajeError = 0;

        RandomAccessFile file = null;

        try {
            file = new RandomAccessFile(NOMBRE_FICHERO, "rw");

            file.seek((id - 1) * TAMAÑO_PREGUNTA + 4);

            // Array de bytes para que la pregunta ocupe siempre 600 bytes
            byte[] preguntaByte = new byte[600];

            // Asigna los datos de pregunta
            for (int i = 0; i < preguntaByte.length; i++) {
                if (pregunta.length() > i) {
                    preguntaByte[i] = (byte) pregunta.charAt(i);
                }
                else {
                    preguntaByte[i] = 0;
                }
            }

            file.write(preguntaByte);

            file.close();
        } catch (Exception e) {
            mensajeError = -1;
        }

        return mensajeError;
    }

    /**
     * Este metodo modifica la respuesta correcta de una pregunta y las opciones de la pregunta.
     * 
     * @param id Este es el identificador de la pregunta que se quiere modificar.
     * @param respuestaCorrecta Esta es la respuesta correcta que se le quiere asignar a la pregunta.
     * @param opciones Estas son las opciones que se le quiere asignar a la pregunta.
     * 
     * @return Devuelve 0 si se ha modificado correctamente, 1 si ha habido un error.
     */
    public int modificarRespuestaYOpciones(int id, String respuestaCorrecta, String opciones) {
        int mensajeError = 0;

        RandomAccessFile file = null;

        try {
            file = new RandomAccessFile(NOMBRE_FICHERO, "rw");

            file.seek((id - 1) * TAMAÑO_PREGUNTA + 612);

            // Array de bytes para que la respuesta correcta ocupa siempre 200 bytes
            byte[] respuestaCorrectaByte = new byte[200];
            
            // Asigna los datos de la respuesta
            for (int i = 0; i < respuestaCorrectaByte.length; i++) {
                if (respuestaCorrecta.length() > i) {
                    respuestaCorrectaByte[i] = (byte) respuestaCorrecta.charAt(i);
                }
                else {
                    respuestaCorrectaByte[i] = 0;
                }
            }

            // Array de bytes para que las opciones ocupen siempre 600 bytes
            byte[] opcionesByte = new byte[600];

            // Asigna los datos de las opciones
            for (int i = 0; i < opcionesByte.length; i++) {
                if (opciones.length() > i) {
                    opcionesByte[i] = (byte) opciones.charAt(i);
                }
                else {
                    opcionesByte[i] = 0;
                }
            }

            file.write(respuestaCorrectaByte);
            file.write(opcionesByte);

            file.close();
        } catch (Exception e) {
            mensajeError = -1;
        }

        return mensajeError;
    }

    /**
     * Este metodo modifica el estado de una pregunta.
     * 
     * @param id Este es el identificador de la pregunta que se quiere modificar.
     * @param estado Este es el estado que se le quiere asignar a la pregunta.
     * 
     * @return Devuelve 0 si se ha modificado correctamente, 1 si ha habido un error.
     */
    public int modificarEstado(int id, int estado) {
        int mensajeError = 0;

        RandomAccessFile file = null;

        try {
            file = new RandomAccessFile(NOMBRE_FICHERO, "rw");

            file.seek((id - 1) * TAMAÑO_PREGUNTA + 1412);

            file.writeInt(estado);

            file.close();
        } catch (Exception e) {
            mensajeError = -1;
        }

        return mensajeError;
    }

    /**
     * Este metodo devuelve una pregunta aleatoria.
     * Si la pregunta esta desactivada vusca otra
     * 
     * @return Devuelve null si no hay preguntas, si hay preguntas devuelve un array de String.
     */
    public String[] preguntaAleatoria() {
        String[] registro = null;
        RandomAccessFile file = null;

        try {
            file = new RandomAccessFile(NOMBRE_FICHERO, "r");

            int numPreguntas = (int)(file.length() / TAMAÑO_PREGUNTA);
            int i = 0;
            int[] preguntas = new int[numPreguntas];

            // Genera un numero aleatorio y comprueba que no se repita
            do {
                int aleatorio = (int) (Math.random() * numPreguntas);
                preguntas[i] = aleatorio;

                for (int j = 0; j < i; j++) {
                    if (preguntas[j] == aleatorio) {
                        aleatorio = (int) (Math.random() * numPreguntas);
                        j = -1;
                    }
                }

                file.seek(aleatorio * TAMAÑO_PREGUNTA);

                registro = new String[7];

                registro[0] = String.valueOf(file.readInt()); //ID

                byte[] pregunta = new byte[600];
                file.read(pregunta);
                registro[1] = new String(pregunta); //Pregunta

                registro[2] = String.valueOf(file.readInt()); //CategoriaRespuesta

                registro[3] = String.valueOf(file.readInt()); //TipoRespuesta

                byte[] respuesta = new byte[200];
                file.read(respuesta);
                registro[4] = new String(respuesta); //RespuestaCorrecta
                
                byte[] opcion = new byte[600];
                file.read(opcion);
                registro[5] = new String(opcion); //Opciones

                registro[6] = String.valueOf(file.readInt()); //Estado

                if (Integer.parseInt(registro[6]) == 1) {
                    registro = null;
                }

                i++;
            } while (registro == null && i < numPreguntas);

            file.close();
        } catch (Exception e) {
            registro = null;
        }

        return registro;
    }

    /**
     * Metodo que busca una pregunta aleatoria por una categoria
     * 
     * @param categoria  la categoria de la pregunta a buscar
     * @return Este retorna un array de String con la pregunta si lo a encontrado, si no devuelve null
     */
    public String[] aleatoriaPorCategoria(int categoria) {
        String[] registro = null;

        int numPreguntas = getId() - 1;
        int i = 0;

        boolean encontrada = false;

        do {
            registro = preguntaAleatoria();

            if (Integer.parseInt(registro[2]) != categoria) {
                registro = null;
            }  else {encontrada = true;}

            i++;
        } while (i < numPreguntas  && !encontrada);

        return registro;
    }
}