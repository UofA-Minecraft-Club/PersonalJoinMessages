package cat6172.personaljoinmessages;

import java.sql.*;

public class MySQLAccess extends DatabaseAccess {

    public MySQLAccess(String connectionString, String tableName) {
        super(connectionString, tableName);
    }

    @Override
    protected void connect(String connectionString) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initializeTable() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (name VARCHAR(255) PRIMARY KEY, login TEXT, quit TEXT)";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }
}
