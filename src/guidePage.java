import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class guidePage extends JPanel{
    private JFrame frame;
    public guidePage(JFrame frame){
        this.frame = frame;
        JButton backButton = new JButton("Quay về trang chủ");
        JLabel guideLabel1 = new JLabel();
        JLabel guideLabel2 = new JLabel();

        ImageIcon banphim = new ImageIcon("banphim.png");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();

        panel1.setBounds(0, 0, 625, 900);
        panel1.setBackground(Color.GREEN);

        panel2.setBounds(625, 0, 625, 900);
        panel2.setBackground(Color.BLUE);
        panel2.setLayout(null);
        
        guideLabel2.setIcon(banphim);
        guideLabel2.setText("ALo alo");
        guideLabel2.setBounds(100,0,500,500);

        panel2.add(guideLabel2);

        this.add(panel1);
        this.add(panel2);

        guideLabel1.setText("<html>Đây là hướng dẫn dòng 1<br/>Đây là hướng dẫn dòng 2 <br/>Đây là hướng dẫn dòng 3 <br/>Dây là hướng dẫn dòng 4</html>");
        guideLabel1.setFont(new Font("sans-serif", Font.BOLD, 30));
        this.add(backButton);
        this.add(guideLabel1);
        this.setLayout(null);
        backButton.addActionListener(e -> {
            this.setVisible(false);
            startMenu startMenu = new startMenu(frame);
            frame.add(startMenu);
            frame.repaint();
            startMenu.requestFocus();
        });
    }

    
}
