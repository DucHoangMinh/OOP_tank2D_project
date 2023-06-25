import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.Objects;

public class startMenu extends JPanel implements ActionListener{
    private boolean toGame = false;
    private JFrame frame;
    
    public startMenu(JFrame frame){
        this.frame = frame;
        
        JButton startButton = new JButton("Vào trò chơi");
        JButton viewGuide = new JButton("Xem hướng dẫn");
        JLabel startLabel = new JLabel("Xe tăng đối kháng");
        JLabel menuLabel = new JLabel();
        
        ImageIcon tankMenu = new ImageIcon("tank menu.png");
        Image image1 = tankMenu.getImage();
        Image newimage1 = image1.getScaledInstance(1236, 780, java.awt.Image.SCALE_SMOOTH);
        menuLabel.setIcon(new ImageIcon(newimage1));

        startLabel.setBounds(350, 200,550, 100);
        startButton.setBounds(400, 350, 400, 50);
        viewGuide.setBounds(400, 450, 400, 50);
        menuLabel.setBounds(0, -70, 1250, 900);
        

        this.setLayout(null);
        // this.setSize(1000, 1000)
        this.add(startButton);
        this.add(viewGuide);
        this.add(menuLabel);
        this.add(startLabel);

        startButton.addActionListener(e -> {
            this.setVisible(false);
            Gameplay GamePlay = new Gameplay();
            frame.add(GamePlay);
            frame.repaint();
            GamePlay.requestFocus();
        });

        viewGuide.addActionListener(e -> {
            this.setVisible(false);
            guidePage guidePage;
            guidePage = new guidePage(frame);
            frame.add(guidePage);
            frame.repaint();
            guidePage.requestFocus();
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
