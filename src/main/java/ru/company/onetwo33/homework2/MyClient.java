package ru.company.onetwo33.homework2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyClient extends JFrame {
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;

    private JTextField msgInputField;
    private JTextField loginField;
    private JTextField passField;
    private JTextArea chatArea;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean isAuthorized;
    private String myNick;

    public MyClient() {
        prepareGUI();
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public void start() throws IOException {
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            setAuthorized(false);
            Thread t = new Thread(() -> {
                try {
                    authentication();
                    readMessages();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        setAuthorized(false);
                        socket.close();
                        myNick = "";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.setDaemon(true);
            t.start();

            // Если не авторизовался в течении 120 сек, то отключаем
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(120_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!isAuthorized) {
                        closeConnection();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authentication() throws IOException {
        while (true) {
            String strFromClientHandler = in.readUTF();
            if (strFromClientHandler.startsWith("/authok")) {
                setAuthorized(true);
                break;
            }
            chatArea.append(strFromClientHandler);
            chatArea.append("\n");
        }
    }

    private void readMessages() throws IOException {
        while (true) {
            String strFromClientHandler = in.readUTF();
            if (strFromClientHandler.equalsIgnoreCase("/end")) break;
            chatArea.append(strFromClientHandler);
            chatArea.append("\n");
        }
    }

    public void closeConnection() {
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

    public void sendMessage() {
        if (!msgInputField.getText().trim().isEmpty()) {
            try {
                out.writeUTF(msgInputField.getText());
                msgInputField.setText("");
                msgInputField.grabFocus();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Ошибка отправки сообщения");
            }
        }
    }

    public void onAuthClick() throws IOException {
        if (socket == null || socket.isClosed()) {
            start();
        }
        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passField.getText());
            loginField.setText("");
            passField.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void prepareGUI() {
        // Параметры окна
        setBounds(600, 300, 500, 500);
        setTitle("Клиент");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Авторизация
        JPanel upperPanel = new JPanel(new BorderLayout());
        JButton btnAuthMsg = new JButton("Авторизоваться");
        upperPanel.add(btnAuthMsg, BorderLayout.EAST);
        loginField = new JTextField();
        passField = new JTextField();
        upperPanel.add(loginField, BorderLayout.NORTH);
        upperPanel.add(passField, BorderLayout.CENTER);
        add(upperPanel, BorderLayout.NORTH);
        btnAuthMsg.addActionListener(e -> {
            try {
                onAuthClick();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        });

        // Текстовое поле для вывода сообщений
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Нижняя панель с полем для ввода сообщений и кнопкой отправки сообщений
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton btnSendMsg = new JButton("Отправить");
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        msgInputField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(msgInputField, BorderLayout.CENTER);
        btnSendMsg.addActionListener(e -> sendMessage());
        msgInputField.addActionListener(e -> sendMessage());

        // Настраиваем действие на закрытие окна
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.writeUTF("/end");
                    closeConnection();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MyClient::new);
    }
}