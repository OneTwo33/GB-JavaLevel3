package ru.company.onetwo33.homework2;

public interface AuthService {
    void start();
    String getNickByLoginPass(String login, String pass);
    void stop();
}
