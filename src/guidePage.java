import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class guidePage extends JPanel{
    private JFrame frame;
    public guidePage(JFrame frame){
        this.frame = frame;
        
        JButton backButton = new JButton("Main Menu");
        JButton switchButton = new JButton("Next");
        JLabel guideLabel = new JLabel();
        
        ImageIcon tankGuide = new ImageIcon("tank guide.png");
        Image image = tankGuide.getImage();
        Image newimage = image.getScaledInstance(1236, 780, java.awt.Image.SCALE_SMOOTH);
        guideLabel.setIcon(new ImageIcon(newimage));
        
        guideLabel.setBounds(0, -70, 1250, 900);
        switchButton.setBounds(900, 657, 140, 60);
        backButton.setBounds(1070, 657, 140, 60);
        
        backButton.setFont(new Font("Calibri", Font.BOLD, 20));
        //backButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        backButton.setBorderPainted(false);

        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                backButton.setBorder(BorderFactory.createLineBorder(Color.blue, 2)); //màu viền của button
                backButton.setBorderPainted(true);
            }

            public void mouseExited(MouseEvent e){
                backButton.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                backButton.setBorderPainted(false);
            }
        });

        this.add(backButton);
        this.add(switchButton);
        this.add(guideLabel);
        this.setLayout(null);

        backButton.addActionListener(e -> {
            this.setVisible(false);
            startMenu startMenu = new startMenu(frame);
            frame.add(startMenu);
            frame.repaint(); //yêu cầu vẽ lại để cập nhật giao diện
            startMenu.requestFocus(); //tập trung vào đối tượng startMenu
        });

        switchButton.addActionListener(e -> {
            this.setVisible(false);


        });
    }
}
