package ru.company.onetwo33.homework3;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass);
    void stop();
}
