import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame {
    private JTextArea txtLog;
    private JButton btnStart, btnStop;
    private boolean isServerRunning = false;

    public ServerWindow() {
        setTitle("Управление сервером");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        txtLog = new JTextArea();
        txtLog.setEditable(false);
        add(new JScrollPane(txtLog), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        btnStart = new JButton("Запустить сервер");
        btnStop = new JButton("Остановить сервер");
        panel.add(btnStart);
        panel.add(btnStop);

        add(panel, BorderLayout.SOUTH);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerRunning) {
                    txtLog.append("Сервер уже запущен.\n");
                    return;
                }
                isServerRunning = true;
                txtLog.append("Сервер запущен.\n");
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerRunning) {
                    txtLog.append("Сервер не запущен.\n");
                    return;
                }
                isServerRunning = false;
                txtLog.append("Сервер остановлен.\n");
            }
        });
    }

    public boolean isServerRunning() {
        return isServerRunning;
    }
}
