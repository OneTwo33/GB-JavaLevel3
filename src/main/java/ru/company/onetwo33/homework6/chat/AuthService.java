package ru.company.onetwo33.homework6.chat;

import java.sql.SQLException;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass) throws SQLException, ClassNotFoundException;
    void stop();
}
