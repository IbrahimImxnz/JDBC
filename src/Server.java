import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Server {
    private static final int SERVER_PORT = 8888;
    private static final String DB_URL = "jdbc:sqlite:users.db";

    private DBHelper dbHelper;

    public Server() {
        dbHelper = new DBHelper();
    }

    private void service(Socket socket, Connection dbConnection) {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            Actions action = (Actions) in.readObject();

            if (action == Actions.REGISTER) {
                String userName = (String) in.readObject();
                String pwd = (String) in.readObject();
                String firstName = (String) in.readObject();
                String lastName = (String) in.readObject();



                dbHelper.registerUser(new User(userName, pwd, firstName, lastName));

                out.writeObject(RESPONSE.SUCCESS);
            } else if (action == Actions.LOGIN) {
                String userName = (String) in.readObject();
                String pwd = (String) in.readObject();

                User user = dbHelper.getUserByUsername(userName, pwd);

                if (user != null) {
                    out.writeObject(RESPONSE.SUCCESS);
                } else {
                    out.writeObject(RESPONSE.FAILURE);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
             Connection dbConnection = DriverManager.getConnection(DB_URL)) {

            dbHelper.createTable();

            System.out.println("Server gestartet. Warte auf Verbindungen...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client verbunden.");

                service(socket, dbConnection);

                socket.close();
                System.out.println("Client getrennt.");
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
