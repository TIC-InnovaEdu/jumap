package servidor;

import java.io.*;
import java.net.*;

public class ServidorJuego {
    private static final int PUERTO = 5000; // Puerto donde escucha el servidor

    public static void main(String[] args) {
        System.out.println("Servidor iniciado. Esperando conexiones...");

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            while (true) {
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                // Crear un hilo para manejar la conexión del cliente
                new Thread(new ManejadorCliente(cliente)).start();
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}

// Clase para manejar las conexiones de los clientes
class ManejadorCliente implements Runnable {
    private Socket cliente;

    public ManejadorCliente(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true)) {

            salida.println("Conexión exitosa. Puede iniciar el juego.");
            String mensaje;

            while ((mensaje = entrada.readLine()) != null) {
                if (mensaje.equalsIgnoreCase("SALIR")) {
                    System.out.println("El cliente se ha desconectado.");
                    break;
                }
                System.out.println("Mensaje del cliente: " + mensaje);
            }
        } catch (IOException e) {
            System.err.println("Error en la conexión con el cliente: " + e.getMessage());
        } finally {
            try {
                cliente.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
