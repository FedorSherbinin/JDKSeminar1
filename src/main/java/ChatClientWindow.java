import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChatClientWindow extends JFrame {
    private JTextField txtLogin, txtServerIp, txtPort;
    private JPasswordField txtPassword;
    private JTextArea txtChatArea;
    private JTextField txtMessageField;
    private JButton btnConnect, btnSend;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private String currentUser = "";
    private boolean isConnected = false;
    private ServerWindow serverWindow;

    public ChatClientWindow(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;
        setTitle("Чат-клиент");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(5, 2));
        txtLogin = new JTextField();
        txtPassword = new JPasswordField();
        txtServerIp = new JTextField();
        txtPort = new JTextField();
        btnConnect = new JButton("Подключиться");
        topPanel.add(new JLabel("Логин:"));
        topPanel.add(txtLogin);
        topPanel.add(new JLabel("Пароль:"));
        topPanel.add(txtPassword);
        topPanel.add(new JLabel("IP сервера:"));
        topPanel.add(txtServerIp);
        topPanel.add(new JLabel("Порт:"));
        topPanel.add(txtPort);
        topPanel.add(btnConnect);

        add(topPanel, BorderLayout.NORTH);

        txtChatArea = new JTextArea();
        txtChatArea.setEditable(false);
        add(new JScrollPane(txtChatArea), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        txtMessageField = new JTextField();
        btnSend = new JButton("Отправить");
        bottomPanel.add(txtMessageField, BorderLayout.CENTER);
        bottomPanel.add(btnSend, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        JPanel userPanel = new JPanel(new BorderLayout());
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userPanel.add(new JScrollPane(userList), BorderLayout.CENTER);
        add(userPanel, BorderLayout.WEST);

        populateUserList();

        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        txtMessageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedUser = userList.getSelectedValue();
                if (selectedUser != null) {
                    currentUser = selectedUser;
                }
            }
        });
    }

    private void connectToServer() {
        if (!serverWindow.isServerRunning()) {
            JOptionPane.showMessageDialog(this, "Сервер не запущен. Подключение невозможно.");
            return;
        }

        String login = txtLogin.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String serverIp = txtServerIp.getText().trim();
        String portText = txtPort.getText().trim();

        if (login.isEmpty() || password.isEmpty() || serverIp.isEmpty() || portText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, заполните все поля.");
            return;
        }

        int port;
        try {
            port = Integer.parseInt(portText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Неверный номер порта.");
            return;
        }

        isConnected = true;
        loadChatHistory();
        JOptionPane.showMessageDialog(this, "Подключение к серверу успешно!");
    }

    private void sendMessage() {
        if (!isConnected) {
            JOptionPane.showMessageDialog(this, "Необходимо подключиться к серверу для отправки сообщений.");
            return;
        }

        if (!serverWindow.isServerRunning()) {
            JOptionPane.showMessageDialog(this, "Сервер остановлен. Отправка сообщений невозможна.");
            return;
        }

        String message = txtMessageField.getText().trim();
        if (message.isEmpty()) {
            return;
        }

        if (currentUser.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Пожалуйста, выберите пользователя.");
            return;
        }

        String formattedMessage = currentUser + ": " + message;
        appendMessage(formattedMessage);
        saveChatHistory(formattedMessage);
        txtMessageField.setText("");
    }

    private void appendMessage(String message) {
        txtChatArea.append(message + "\n");
        txtChatArea.setCaretPosition(txtChatArea.getDocument().getLength());
    }

    private void loadChatHistory() {
        File historyFile = new File("chat_history.txt");
        if (historyFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    appendMessage(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveChatHistory(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("chat_history.txt", true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для заполнения списка пользователей
    private void populateUserList() {
        // Пример списка пользователей
        List<String> users = new ArrayList<>();
        users.add("Василий");
        users.add("Иван");
        users.add("Николай");

        for (String user : users) {
            userListModel.addElement(user);
        }
    }
}
