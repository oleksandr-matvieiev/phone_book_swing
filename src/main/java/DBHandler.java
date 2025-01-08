import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DBHandler {

    private Connection connection;

    public DBHandler() {
        // obtaining a DB connection
        try {
//            Properties props = readProperties();
//            String connString = props.getProperty("db_connection");

            String connString = "jdbc:sqlite:identifier.sqlite";
            log.info("Connecting to {}...", connString);
            connection = DriverManager.getConnection(connString);
            PreparedStatement stat = connection.prepareStatement("SELECT sqlite_version()");
            ResultSet set = stat.getResultSet();
            stat.executeQuery();
            set.next();

            log.info("Success! DB version - {}", set.getString(1));
        } catch (SQLException ex) {
            log.error("An error occurred, closing connection...");
            try {
                // close if there is a problem
                connection.close();
                log.error("Disconnected");
            } catch (SQLException e) {
                log.error("", e);
            }
        }
    }

    public Object[][] getNicknames() {
        String sql = "SELECT nickname FROM contacts";
        List<Object[]> 
                contactsList = new ArrayList<>();
        Object[][] result = new Object[0][];
        try {
            PreparedStatement stat = connection.prepareStatement(sql);
            ResultSet set = stat.getResultSet();
            stat.executeQuery();
            while (set.next()) {
                Object[] contact = new Object[1];
                contact[0] = set.getString(1);  // nickname
                contactsList.add(contact);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (!contactsList.isEmpty()) {
            return contactsList.toArray(result);
        } else {
            return new Object[][]{};
        }
    }

    public void deleteContactByNickname(String nickname) {
        log.debug("Delete", nickname);
        String sql = "DELETE FROM contacts WHERE nickname = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(sql);
            stat.setString(1, nickname);
            stat.executeUpdate();
        } catch (SQLException e) {
            log.info("Smth went wrong for {}", nickname);

        }
    }

    public HashMap<String, String> getContactByNickname(String nickname) {
        log.debug("Retrieving contact with nickname = {}", nickname);

        HashMap<String, String> map = new HashMap<>();
        String sql = "SELECT * FROM contacts WHERE nickname = ?";
        try {
            PreparedStatement stat = connection.prepareStatement(sql);
            stat.setString(1, nickname);
            ResultSet set = stat.getResultSet();
            stat.executeQuery();
            while (set.next()) {
                map.put("name", set.getString("name"));
                map.put("surname", set.getString("surname"));
                map.put("nickname", set.getString("nickname"));
                map.put("phone", set.getString("number"));
            }
        } catch (SQLException e) {
            log.info("Smth went wrong for {}", nickname);
            log.debug(e.getMessage());
        }

        return map;
    }

    public void addContact(String name, String surname, String nickname, String phone) {
        log.debug("Adding new contact with nickname = {}", nickname);

        String sql = "INSERT INTO contacts (name, surname, nickname, number) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stat = connection.prepareStatement(sql);
            stat.setString(1, name);
            stat.setString(2, surname);
            stat.setString(3, nickname);
            stat.setString(4, phone);
            stat.executeUpdate();
        } catch (SQLException e) {
            log.info("Something went wrong while adding a new contact");
            log.debug(e.getMessage());
        }
    }


    private Properties readProperties() {
        Properties props = new Properties();
        try (FileReader reader = new FileReader("application.properties")) {
            props.load(reader);
        } catch (IOException ex) {
            log.error("Smth went wrong with properties");
            log.debug("", ex);
        }
        return props;
    }

}
