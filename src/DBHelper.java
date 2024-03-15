import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public void createTable(){
      try (Connection conn = DriverManager.getConnection(DB_URL); Statement stmt = conn.createStatement()){
          String sql = "CREATE TABLE IF NOT EXISTS users (userName VARCHAR(50) PRIMARY KEY, pwd VARCHAR(50), lastname VARCHAR(50), firstname VARCHAR(50))";
          stmt.execute(sql);


      }catch (SQLException e){
          e.printStackTrace();
      }

    }

    public void registerUser(User user){
        try(Connection conn = DriverManager.getConnection(DB_URL); PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (userName, pwd, lastName, firstName) VALUES (?, ?, ?, ?)")){
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getPwd());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getFirstName());

            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(DB_URL); Statement statement = connection.createStatement()){
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String username = resultSet.getString("username");
                String pwd = resultSet.getString("pwd");
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                User user = new User(username, pwd, lastName, firstName);
                userList.add(user);
            }
            return userList;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;


    }

    public User getUserByUsername(String userName, String password){
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE userName = ? AND pwd =?")) {

            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String pwd = rs.getString("pwd");
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                return new User(userName, pwd, lastName, firstName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
