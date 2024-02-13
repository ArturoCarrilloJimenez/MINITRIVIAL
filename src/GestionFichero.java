import java.io.RandomAccessFile;
import java.util.ArrayList;

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
     * Este metodo devuelve un ArrayList de Array de String con todas las preguntas que hay en el fichero.
     * Si el fichero no existe devuelve null.
     * 
     * @return Devuelve un ArrayList de Array de String con todas las preguntas que hay en el fichero.
     */
    public ArrayList<String[]> listaPreguntas() {
        ArrayList<String[]> arrayList = null;
        RandomAccessFile file = null;

        try {
            file = new RandomAccessFile(NOMBRE_FICHERO, "r");

            String[] registro = new String[7];
            arrayList = new ArrayList<String[]>();

            while (file.getFilePointer() < file.length()) {

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

                arrayList.add(registro);
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

            while (file.getFilePointer() < file.length()) {
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

            }

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
}