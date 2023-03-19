package mc.uofa.cat6172.customjoinmessages;

//This whole thing was written by ChatGPT

import java.sql.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class SQLMessageStorage {

    private Connection conn;
    private MessageDigest digest;

    public SQLMessageStorage(String filename) throws SQLException {
        try {
            // Connect to the database
            conn = DriverManager.getConnection("jdbc:sqlite:" + filename);

            // Create the table if it doesn't exist
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS mytable (id INTEGER PRIMARY KEY, value TEXT)");

            // Create the hash function
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private int hash(String s) {
        byte[] hashBytes = digest.digest(s.getBytes(StandardCharsets.UTF_8));
        return new java.math.BigInteger(1, hashBytes).intValue();
    }

    public String getItem(String key) throws SQLException {
        int intKey = hash(key);
        PreparedStatement selectStmt = conn.prepareStatement("SELECT value FROM mytable WHERE id = ?");
        selectStmt.setInt(1, intKey);
        ResultSet result = selectStmt.executeQuery();
        if (result.next()) {
            return result.getString("value");
        } else {
            return null;
        }
    }

    public void setItem(String key, String value) throws SQLException {
        int intKey = hash(key);
        PreparedStatement insertStmt = conn.prepareStatement("INSERT OR REPLACE INTO mytable (id, value) VALUES (?, ?)");
        insertStmt.setInt(1, intKey);
        insertStmt.setString(2, value);
        insertStmt.executeUpdate();
    }

    public void removeItem(String key) throws SQLException {
        int intKey = hash(key);
        PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM mytable WHERE id = ?");
        deleteStmt.setInt(1, intKey);
        deleteStmt.executeUpdate();
    }

    public boolean checkItem(String key) throws SQLException {
        int intKey = hash(key);
        PreparedStatement selectStmt = conn.prepareStatement("SELECT COUNT(*) FROM mytable WHERE id = ?");
        selectStmt.setInt(1, intKey);
        ResultSet result = selectStmt.executeQuery();
        return result.getInt(1) > 0;
    }

    public Set<String> listKeys() throws SQLException {
        Set<String> keys = new HashSet<>();
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT id FROM mytable");
        while (result.next()) {
            keys.add(Integer.toString(result.getInt("id")));
        }
        return keys;
    }

    public void close() throws SQLException {
        conn.close();
    }
}
