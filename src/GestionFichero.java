import java.io.RandomAccessFile;

public class GestionFichero {
    private static final String NOMBRE_FICHERO = "fichero.bin";
    public static final int TAMAÑO_PREGUNTA = 1412;

    /**
     * Este metodo añade una pregunta al fichero de preguntas y respuestas, si el fichero no existe lo crea.
     * Este tiene un formato de 5 campos: idPregunta (int), pregunta (String), tipo(int), respuestaCorrecta (String), opciones (String), estado (int) 
     * Este tiene un tamaño de 1412 bytes.
     * 
     * @param pregunta
     * @param tipo
     * @param respuestaCorrecta
     * @param opciones
     * 
     * @return Devuelve 0 si se ha añadido correctamente, 1 si ha habido un error.
     */
    public int añadirPregunta(String pregunta, int tipo, String respuestaCorrecta, String opciones) {
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

    public void listaPreguntas() {
        RandomAccessFile file = null;

        try {
            file = new RandomAccessFile(NOMBRE_FICHERO, "r");
            while (file.getFilePointer() < file.length()) {
                System.out.println("Id: " + file.readInt());
                byte[] pregunta = new byte[600];
                file.read(pregunta);
                System.out.println("Pregunta: " + new String(pregunta));
                System.out.println("Tipo: " + file.readInt());
                byte[] respuesta = new byte[200];
                file.read(respuesta);
                System.out.println("Respuesta correcta: " + new String(respuesta));
                byte[] opcion = new byte[600];
                file.read(opcion);
                System.out.println("Opciones: " + new String(opcion));
                System.out.println("Borredo: " + file.readInt());
            }
            file.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}