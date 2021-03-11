package ru.company.onetwo33.homework2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {

    private final List<User> usersFromDb;

    public BaseAuthService() throws SQLException, ClassNotFoundException {
        Connection conn = SingletonConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");
        usersFromDb = new ArrayList<>();

        while (rs.next()) {
            User user = new User().userBuilder(rs);
            usersFromDb.add(user);
        }
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public String getNickByLoginPass(String login, String pass) {
        for (User user : usersFromDb) {
            if (user.getLogin().equals(login) && user.getPassword().equals(pass)) return user.getNick();
        }
        return null;
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }
}
