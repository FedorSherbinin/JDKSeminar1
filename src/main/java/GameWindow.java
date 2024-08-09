import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private static final int WIDTH = 555;
    private static final int HEIGHT = 507;

    private JButton btnStart, btnExit;
    private SettingWindow settingWindow;
    private Map map;

    public GameWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setTitle("TicTacToe");
        setResizable(false);

        // Инициализация кнопок и других компонентов
        btnStart = new JButton("New Game");
        btnExit = new JButton("Exit");
        settingWindow = new SettingWindow(this);
        map = new Map();

        // Обработчик кнопки "Exit"
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        GameWindow.this,
                        "Вы уверены, что хотите выйти?",
                        "Подтверждение выхода",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Обработчик кнопки "New Game"
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingWindow.setVisible(true);
            }
        });

        // Создание панели для кнопок и добавление их в окно
        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnExit);

        add(panBottom, BorderLayout.SOUTH);
        add(map);

        setVisible(true);
    }

    // Метод для запуска новой игры с заданными параметрами
    public void startNewGame(int mode, int sizeX, int sizeY, int winLen) {
        map.startNewGame(mode, sizeX, sizeY, winLen);
    }
}
