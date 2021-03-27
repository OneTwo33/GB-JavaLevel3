package ru.company.onetwo33.homework6.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private String name;

    public String getName() {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";

            executorService.execute(() -> { // Исользуем executorService
                try {
                    authentication();
                    socket.setSoTimeout(180_000); // Если от клиента не приходит сообщений 3 минуты, то отключаем его
                    readMessages();
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                }
            });
            executorService.shutdown();
        } catch (IOException e) {
            throw new RuntimeException("Проблема при создании обработчика клиента");
        }
    }

    private void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMsg(name + " вышел из чата");
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException, SQLException, ClassNotFoundException {
        while (true) {
            String strFromClient = in.readUTF();
            if (strFromClient.startsWith("/")) {
                if (strFromClient.equals("/end")) {
                    logger.info("Клиент отключился");
                    break; // закрыл клиента
                }
                if (strFromClient.startsWith("/w ")) { // отправил личное сообщение
                    String[] tokens = strFromClient.split("\\s");
                    String nick = tokens[1];
                    String msg = strFromClient.substring(4 + nick.length());
                    myServer.sendMsgToClient(this, nick, msg);
                    logger.info("Клиент отправил личное сообщение");
                }
                if (strFromClient.startsWith("/c ")) { // сменил ник
                    String[] tokens = strFromClient.split("\\s");
                    String newNick = tokens[1];

                    Connection conn = SingletonConnection.getConnection();
                    PreparedStatement stmt = conn.prepareStatement("UPDATE users SET nick = ? WHERE nick = ?");
                    stmt.setString(1, newNick);
                    stmt.setString(2, this.name);
                    stmt.executeUpdate();
                    myServer.broadcastMsg(this.name + " сменил ник на " + newNick);
                    name = newNick;
                    logger.info("Клиент сменил ник");
                }
                continue;
            }
            myServer.broadcastMsg(name + ": " + strFromClient);
            logger.info("Клиент отправил сообщение");
        }
    }

    private void authentication() throws IOException, SQLException, ClassNotFoundException {
        while (true) {
            String str = in.readUTF();
            if (str.startsWith("/auth")) {
                String[] parts = str.split("\\s");
                String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        sendMsg("/authok" + nick);
                        name = nick;
                        myServer.subscribe(this);
                        myServer.broadcastMsg(name + " зашел в чат");
                        return;
                    } else {
                        sendMsg("Учетная запись уже используется");
                    }
                } else {
                    sendMsg("Неверные логин/пароль");
                }
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
