import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    
        startButton.setBounds(400, 350, 400, 50);
        viewGuide.setBounds(400, 450, 400, 50);
        startLabel.setFont(new Font("sans-serif", Font.BOLD, 55));
        startLabel.setBounds(350, 200,550, 100);

        this.setLayout(null);
        // this.setSize(1000, 1000);
        this.add(startButton);
        this.add(viewGuide);
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
            guidePage guidePage = new guidePage(frame);
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
