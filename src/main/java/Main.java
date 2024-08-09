import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        new GameWindow();
        // Создаем и отображаем окно управления сервером
        SwingUtilities.invokeLater(() -> {
            ServerWindow serverWindow = new ServerWindow();
            ChatClientWindow clientWindow = new ChatClientWindow(serverWindow);

            // Открытие окон рядом
            serverWindow.setLocation(0, 10);
            clientWindow.setLocation(serverWindow.getWidth(), 0);

            serverWindow.setVisible(true);
            clientWindow.setVisible(true);
        });
    }
}