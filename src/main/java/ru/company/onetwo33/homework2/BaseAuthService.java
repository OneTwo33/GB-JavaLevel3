package ru.company.onetwo33.homework2;

import java.sql.*;

public class BaseAuthService implements AuthService {

    public BaseAuthService() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void start() {
        System.out.println("Сервис аутентификации запущен");
    }

    @Override
    public String getNickByLoginPass(String login, String pass) throws SQLException, ClassNotFoundException {
        User user = null;

        Connection conn = SingletonConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
        stmt.setString(1, login);
        stmt.setString(2, pass);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            user = new User().userBuilder(rs);
        }

        if (user != null) return user.getNick();
        else
            return null;
    }

    @Override
    public void stop() {
        System.out.println("Сервис аутентификации остановлен");
    }
}
