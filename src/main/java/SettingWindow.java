import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingWindow extends JFrame {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 600;

    private JButton btnStart;
    private JSlider fieldSizeSlider;
    private JSlider winLengthSlider;
    private JLabel lblSelectedFieldSize;
    private JLabel lblSelectedWinLength;
    private JRadioButton rbHumanVsComputer;
    private JRadioButton rbHumanVsHuman;
    private GameWindow map; // Используем Map здесь

    public SettingWindow(GameWindow map) {
        this.map = map; // Инициализируем поле

        setTitle("Настройки игры");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Центрируем окно относительно экрана
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(0, 1));

        JLabel lblGameMode = new JLabel("Выберите режим игры:");
        add(lblGameMode);

        rbHumanVsComputer = new JRadioButton("Человек против компьютера", true);
        rbHumanVsHuman = new JRadioButton("Человек против человека");

        ButtonGroup gameModeGroup = new ButtonGroup();
        gameModeGroup.add(rbHumanVsComputer);
        gameModeGroup.add(rbHumanVsHuman);

        add(rbHumanVsComputer);
        add(rbHumanVsHuman);

        JLabel lblFieldSize = new JLabel("Выберите размеры поля:");
        add(lblFieldSize);

        lblSelectedFieldSize = new JLabel("Установленный размер поля: 3");
        add(lblSelectedFieldSize);

        fieldSizeSlider = new JSlider(3, 10, 3);
        fieldSizeSlider.setMajorTickSpacing(1);
        fieldSizeSlider.setPaintTicks(true);
        fieldSizeSlider.setPaintLabels(true);

        fieldSizeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = fieldSizeSlider.getValue();
                lblSelectedFieldSize.setText("Установленный размер поля: " + value);

                // Обновляем максимальное значение winLengthSlider
                winLengthSlider.setMaximum(value);
                if (winLengthSlider.getValue() > value) {
                    winLengthSlider.setValue(value);
                    lblSelectedWinLength.setText("Установленная длина: " + value);
                }
            }
        });
        add(fieldSizeSlider);

        JLabel lblWinLength = new JLabel("Выберите длину для победы:");
        add(lblWinLength);

        lblSelectedWinLength = new JLabel("Установленная длина: 3");
        add(lblSelectedWinLength);

        winLengthSlider = new JSlider(3, 10, 3);
        winLengthSlider.setMajorTickSpacing(1);
        winLengthSlider.setPaintTicks(true);
        winLengthSlider.setPaintLabels(true);

        winLengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = winLengthSlider.getValue();
                lblSelectedWinLength.setText("Установленная длина: " + value);
            }
        });
        add(winLengthSlider);

        btnStart = new JButton("Start new game");
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mode = rbHumanVsComputer.isSelected() ? 0 : 1;
                int fieldSize = fieldSizeSlider.getValue();
                int winLength = winLengthSlider.getValue();

                // Запускаем новую игру с актуальными значениями
                map.startNewGame(mode, fieldSize, fieldSize, winLength);
                setVisible(false); // Скрываем окно настроек после начала игры
            }
        });
        add(btnStart);
    }
}
