import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        System.out.println("Connected to the chat server");

        new Thread(new MessageReceiver(socket)).start();

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        String input;
        while ((input = userInput.readLine()) != null) {
            out.println(input);
        }
    }

    static class MessageReceiver implements Runnable {
        private Socket socket;

        public MessageReceiver(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Message: " + message);
                }
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        }
    }
}
