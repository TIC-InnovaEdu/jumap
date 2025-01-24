package cliente;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteJuego {
    private static final String HOST = "localhost"; // Dirección del servidor
    private static final int PUERTO = 5000; // Puerto del servidor

    public static void main(String[] args) {
        System.out.println("Conectando al servidor...");

        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conexión establecida con el servidor.");
            System.out.println("Mensaje del servidor: " + entrada.readLine());

            String mensajeCliente;
            do {
                System.out.print("Escribe un mensaje (o SALIR para desconectarte): ");
                mensajeCliente = scanner.nextLine();
                salida.println(mensajeCliente);

                if (!mensajeCliente.equalsIgnoreCase("SALIR")) {
                    String respuesta = entrada.readLine();
                    System.out.println("Servidor dice: " + respuesta);
                }
            } while (!mensajeCliente.equalsIgnoreCase("SALIR"));

            System.out.println("Desconectando del servidor...");
        } catch (IOException e) {
            System.err.println("Error al conectar o en la comunicación: " + e.getMessage());
        }
    }
}
