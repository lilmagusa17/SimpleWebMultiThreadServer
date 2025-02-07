
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

final class SolicitudHttp implements Runnable {
    final static String CRLF = "\r\n";
    Socket socket;

    public SolicitudHttp(Socket socket) throws Exception {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            procesarSolicitud();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void procesarSolicitud() throws Exception {
        BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream()); // Usado para strings y bytes

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        String lineaDeLaSolicitudHttp = in.readLine();

        System.out.println("Request: " + lineaDeLaSolicitudHttp);
        StringTokenizer partesSolicitud = new StringTokenizer(lineaDeLaSolicitudHttp);

        String metodo = partesSolicitud.nextToken();
        String archivo = partesSolicitud.nextToken();
        System.out.println("Method: " + metodo);
        System.out.println("File: " + archivo);

        // Abre el archivo solicitado.
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("." + archivo);
        String estado = stateType(inputStream);
        System.out.println("State: "+estado);

        if(inputStream == null) {
            inputStream = ClassLoader.getSystemResourceAsStream("./404.html" );
            archivo = "/404.html";
        }

        File file = new File("src/main/resources" + archivo);
        int filesize = (int) file.length();

        enviarString("HTTP/1.1 "+ estado + CRLF, out);
        enviarString("Content-Type:" + contentType(archivo) + "charset=UTF-8" + CRLF,out);
        enviarString("Content-Length: " + filesize + CRLF,out);
        enviarString(CRLF,out);

        // Enviar el archivo solicitado.
        enviarBytes(inputStream, out);
        inputStream.close();

        // Cierra los streams y el socket.
        out.flush();
        cerrarConexion(in, out);

    }

    private static void enviarString(String line, OutputStream os) throws Exception {
        os.write(line.getBytes(StandardCharsets.UTF_8));
    }

    private static void enviarBytes(InputStream fis, OutputStream os) throws Exception {
        // Construye un buffer de 1KB para guardar los bytes cuando van hacia el socket.
        byte[] buffer = new byte[1024];
        int bytes = 0;

        // Copia el archivo solicitado hacia el output stream del socket.
        while ((bytes = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }
    }

    private static String contentType(String nombreArchivo) {
        if(nombreArchivo.endsWith(".htm") || nombreArchivo.endsWith(".html")) {
            return "text/html;";
        }
        if(nombreArchivo.endsWith(".jpg") || nombreArchivo.endsWith(".jpeg")) {
            return "image/jpeg;";
        }
        if(nombreArchivo.endsWith(".gif")) {
            return "image/gif;";
        }
        return "application/octet-stream";
    }

    private static String stateType(InputStream is) {
        if(is==null){
            return "404 Not Found";
        }else{
            return "200 OK";
        }
    }
    private void cerrarConexion(BufferedReader in, BufferedOutputStream out) throws IOException {
        in.close();
        out.close();
        socket.close();
    }

}