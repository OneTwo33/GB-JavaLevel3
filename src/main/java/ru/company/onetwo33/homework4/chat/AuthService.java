package ru.company.onetwo33.homework4.chat;

import java.sql.SQLException;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass) throws SQLException, ClassNotFoundException;
    void stop();
}
