package cat6172.personaljoinmessages;

import java.io.File;
import java.sql.*;

public class SQLiteAccess extends DatabaseAccess {

    public SQLiteAccess(String connectionString, String tableName) {
        super(connectionString, tableName);
    }

    @Override
    protected void connect(String connectionString) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(connectionString);

            String dbName = connectionString.split(":")[2];
            File dbFile = new File(dbName);
            boolean fileExists = dbFile.exists();

            if (!fileExists) {
                connection.createStatement().execute("CREATE DATABASE " + dbName);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initializeTable() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (name TEXT PRIMARY KEY, login TEXT, quit TEXT)";
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
    }
}
