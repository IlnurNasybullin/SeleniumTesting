package repository;

import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestUserRepository implements Repository<User> {

    public static final String removeQuery = "DELETE FROM \"%s\" WHERE \"%s\" = ?";

    private Connection connection;

    private String tableName;
    private String emailColumn;

    public TestUserRepository(Connection connection, String tableName, String emailColumn) {
        this.connection = connection;
        this.tableName = tableName;
        this.emailColumn = emailColumn;
    }

    public boolean remove(User user) throws SQLException {
        String query = String.format(removeQuery, tableName, emailColumn);
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getEmail());

        return statement.executeUpdate() > 0;
    }
}
