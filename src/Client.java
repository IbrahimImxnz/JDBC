import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private int serverPort;

    public Client(int serverPort){
        this.serverPort = serverPort;
    }

    public boolean logic(String userName, String pwd){
        try(Socket socket = new Socket(SERVER_ADDRESS, serverPort); ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
            out.writeObject(Actions.LOGIN);
            out.writeObject(userName);
            out.writeObject(pwd);

            RESPONSE response = (RESPONSE) in.readObject();
            return response == RESPONSE.SUCCESS;
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

        return false;

    }

    public boolean register(String userName, String pwd, String firstName, String lastName){
        try(Socket socket = new Socket(SERVER_ADDRESS, serverPort);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){

            out.writeObject(Actions.REGISTER);
            out.writeObject(userName);
            out.writeObject(pwd);
            out.writeObject(firstName);
            out.writeObject(lastName);

            RESPONSE response = (RESPONSE) in.readObject();
            return response == RESPONSE.SUCCESS;

        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }
}
