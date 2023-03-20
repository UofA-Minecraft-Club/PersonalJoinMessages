package mc.uofa.cat6172.customjoinmessages;

//written with big assistance from ChatGPT

import java.sql.*;
import java.util.*;
import java.io.*;

public class SQLHashtable {
    private final Connection connection;
    private final String tableName;
    private final String keyFilename;
    public Set<String> keys;

    public SQLHashtable(String tableFilename, String tableName, String keyFilename) throws SQLException, IOException, ClassNotFoundException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + tableFilename);
        this.tableName = tableName;
        this.keyFilename = keyFilename;
        this.keys = new HashSet<>();
        try{
            loadKeys(keyFilename);
        } catch (FileNotFoundException e){
            saveKeys(keyFilename);
        }
        Statement statement = this.connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + this.tableName + " (key INTEGER, value TEXT)");
        statement.close();
    }

    public String getItem(String key) throws SQLException {
        int hashed_key = key.hashCode();
        PreparedStatement statement = this.connection.prepareStatement("SELECT value FROM " + this.tableName + " WHERE key = ?");
        statement.setInt(1, hashed_key);
        ResultSet result = statement.executeQuery();
        String value;
        if (result.next()) {
            value = result.getString("value");
        } else {
            value = null;
        }
        statement.close();
        result.close();
        return value;
    }

    public void setItem(String key, String item) throws SQLException, IOException {
        int hashed_key = key.hashCode();
        PreparedStatement statement = this.connection.prepareStatement("INSERT OR REPLACE INTO " + this.tableName + " (key, value) VALUES (?, ?)");
        statement.setInt(1, hashed_key);
        statement.setString(2, item);
        statement.executeUpdate();
        statement.close();
        this.keys.add(key);
        saveKeys(keyFilename);
    }

    public void removeItem(String key) throws SQLException, IOException {
        int hashed_key = key.hashCode();
        PreparedStatement statement = this.connection.prepareStatement("DELETE FROM " + this.tableName + " WHERE key = ?");
        statement.setInt(1, hashed_key);
        statement.executeUpdate();
        statement.close();
        this.keys.remove(key);
        saveKeys(keyFilename);
    }

    private void saveKeys(String filename) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        oos.writeObject(this.keys);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    private void loadKeys(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        this.keys = (Set<String>) ois.readObject();
        ois.close();
    }
}
