package ru.company.onetwo33.homework6.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    private static final Logger logger = LogManager.getLogger(BaseAuthService.class);

    public BaseAuthService() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void start() {
        logger.info("Сервис аутентификации запущен");
    }

    @Override
    public String getNickByLoginPass(String login, String pass) throws SQLException, ClassNotFoundException {
        ru.company.onetwo33.homework2.User user = null;

        Connection conn = ru.company.onetwo33.homework2.SingletonConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
        stmt.setString(1, login);
        stmt.setString(2, pass);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            user = new ru.company.onetwo33.homework2.User().userBuilder(rs);
        }

        if (user != null) return user.getNick();
        else
            return null;
    }

    @Override
    public void stop() {
        logger.info("Сервис аутентификации остановлен");
    }
}
