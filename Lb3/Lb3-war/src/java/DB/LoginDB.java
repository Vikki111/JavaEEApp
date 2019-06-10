
package DB;

import beans.Login;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LoginDB {
    private final String jdbcURL = "jdbc:postgresql://localhost:5432/loginDB";
    private final String jdbcUsername = "postgres";
    private final String jdbcPassword = "132";
    
    private static final String INSERT_LOGIN_SQL = "INSERT INTO login (username, pass) VALUES(?, ?);";
    private static final String SELECT_LOGIN_BY_ID = "select * from login where id =?";
    private static final String SELECT_LOGIN_BY_USERNAME = "select * from login where lower(username) = LOWER(?) ";
    private static final String SELECT_LOGIN_BY_PASS = "select * from login where lower(pass) = LOWER(?) ";
    private static final String SELECT_ALL = "select * from login";
    private static final String DELETE_LOGIN_SQL = "delete from login where id = ?;";
    private static final String UPDATE_LOGIN_SQL = "update login set username = ?,pass= ? where id = ?;";
    
    public LoginDB() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public List<Login> selectAll() {

        List <Login> users = new ArrayList <> ();
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String pass = rs.getString("pass");
                users.add(new Login(id, username, pass));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public void insert(Login login) throws SQLException {
        System.out.println(INSERT_LOGIN_SQL);
        try (Connection connection = getConnection(); 
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOGIN_SQL)) {
            preparedStatement.setString(1, login.getUsername());
            preparedStatement.setString(2, login.getPass());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Login selectById(int id) {
        Login login = null;
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LOGIN_BY_ID);) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String pass = rs.getString("pass");
                login = new Login(id, username, pass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return login;
    }
    
    public List<Login> selectByUsername(String param) {
        List<Login> list = new ArrayList<>();
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LOGIN_BY_USERNAME);) {
            preparedStatement.setString(1, param);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String pass = rs.getString("pass");
                list.add(new Login(id, username, pass));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
     public List<Login> selectByPass(String param) {
        List<Login> list = new ArrayList<>();
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LOGIN_BY_PASS);) {
            preparedStatement.setString(1, param);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String pass = rs.getString("pass");
                list.add(new Login(id, username, pass));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean delete(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); 
                PreparedStatement statement = connection.prepareStatement(DELETE_LOGIN_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean update(Login login) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); 
                PreparedStatement statement = connection.prepareStatement(UPDATE_LOGIN_SQL);) {
            statement.setString(1, login.getUsername());
            statement.setString(2, login.getPass());
            statement.setInt(3, login.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

}
