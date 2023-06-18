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
        JLabel guideLabel = new JLabel();
        ImageIcon help = new ImageIcon("help.png");
        JPanel panel = new JPanel();
        
        backButton.setBounds(500, 500, 200, 50);
        guideLabel.setBounds(50, -150, 1150, 1000);
        //guideLabel.setText("<html>Đây là hướng dẫn dòng 1<br/>Đây là hướng dẫn dòng 2 <br/>Đây là hướng dẫn dòng 3 <br/>Dây là hướng dẫn dòng 4</html>");
        //guideLabel.setFont(new Font("sans-serif", Font.BOLD, 30));
        guideLabel.setIcon(help);
        this.add(backButton);
        this.add(guideLabel);
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
