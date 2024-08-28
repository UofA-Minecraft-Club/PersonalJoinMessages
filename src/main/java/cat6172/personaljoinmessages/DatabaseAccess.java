package cat6172.personaljoinmessages;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public abstract class DatabaseAccess {
    protected Connection connection;
    protected final String tableName;

    public DatabaseAccess(String connectionString, String tableName) {
        this.tableName = tableName;
        try {
            connect(connectionString);
            initializeTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected abstract void connect(String connectionString) throws SQLException;

    protected abstract void initializeTable() throws SQLException;

    protected void putValue(String name, String column, String value) throws SQLException {
        if (exists(name)) {
            String updateQuery = "UPDATE " + tableName + " SET " + column + " = ? WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, value);
            statement.setString(2, name);
            statement.executeUpdate();
        } else {
            String insertQuery = "INSERT INTO " + tableName + " (name, " + column + ") VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, name);
            statement.setString(2, value);
            statement.executeUpdate();
        }
    }

    protected boolean exists(String name) throws SQLException {
        String selectQuery = "SELECT name FROM " + tableName + " WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(selectQuery);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    public void putJoin(String name, String join) throws SQLException {
        putValue(name, "login", join);
    }

    public void putQuit(String name, String quit) throws SQLException {
        putValue(name, "quit", quit);
    }

    protected String getValue(String name, String column) throws SQLException {
        String selectQuery = "SELECT " + column + " FROM " + tableName + " WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(selectQuery);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(column);
        }
        return null;
    }

    public String getJoin(String name) throws SQLException {
        return getValue(name, "login");
    }

    public String getQuit(String name) throws SQLException {
        return getValue(name, "quit");
    }

    public int deleteEntriesWithNullValues() throws SQLException {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE login IS NULL AND quit IS NULL";
        Statement statement = connection.createStatement();
        return statement.executeUpdate(deleteQuery);
    }

    public Set<String> getKeysWithNonNullJoin() throws SQLException {
        Set<String> keys = new HashSet<>();
        String selectQuery = "SELECT name FROM " + tableName + " WHERE login IS NOT NULL";
        PreparedStatement statement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String key = resultSet.getString("name");
            keys.add(key);
        }
        return keys;
    }

    public Set<String> getKeysWithNonNullQuit() throws SQLException {
        Set<String> keys = new HashSet<>();
        String selectQuery = "SELECT name FROM " + tableName + " WHERE quit IS NOT NULL";
        PreparedStatement statement = connection.prepareStatement(selectQuery);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String key = resultSet.getString("name");
            keys.add(key);
        }
        return keys;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
