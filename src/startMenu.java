import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;	
import javax.sound.sampled.*;	
import javax.sound.sampled.AudioInputStream;	
import javax.sound.sampled.AudioSystem;	
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.nimbus.State;

import java.util.Objects;

public class startMenu extends JPanel implements ActionListener{
    private boolean toGame = false;
    private JFrame frame;
    
    ImageIcon startImage = new ImageIcon("pngegg.png");
    Image image = startImage.getImage();
    Image resizedImage = image.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
    ImageIcon resizedImageIcon = new ImageIcon(resizedImage);

    ImageIcon bulletOnWall = new ImageIcon("bullet_on_wall.png");
    Image bulletOW = bulletOnWall.getImage();
    Image BOWresizedImage = bulletOW.getScaledInstance(300 , 300, Image.SCALE_SMOOTH);
    ImageIcon BOW = new ImageIcon(BOWresizedImage);

    public startMenu(JFrame frame){
        this.frame = frame;
        
        JButton startButton = new JButton("Vào trò chơi");
        JButton viewGuide = new JButton("Xem hướng dẫn");
        JLabel startLabel = new JLabel("Xe tăng đối kháng");
        JLabel imageLabel = new JLabel();
        JLabel imageLabel2 = new JLabel();
        imageLabel.setIcon(resizedImageIcon);
        imageLabel2.setIcon(BOW);

        Font font = new Font("Arial", Font.BOLD, 16);
        startButton.setFont(font);
        viewGuide.setFont(font);
        // Đặt màu nền
        Color backgroundColor = new Color(207,177,139);
        startButton.setBackground(backgroundColor);
        viewGuide.setBackground(backgroundColor);
        // Đặt màu chữ
        Color textColor = Color.WHITE;
        startButton.setForeground(textColor);
        viewGuide.setForeground(textColor);
        // Đặt viền
        startButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        viewGuide.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        startButton.setBounds(700, 350, 400, 50);
        viewGuide.setBounds(700, 450, 400, 50);
        imageLabel.setBounds(-10, 300, 600, 400);
        imageLabel2.setBounds(1050, -20, 300 , 300);
        startLabel.setFont(new Font("Tahoma", Font.BOLD, 55));
        startLabel.setBounds(650, 200,550, 100);

        this.setLayout(null);
        // this.setSize(1000, 1000);
        this.add(startLabel);
        this.add(startButton);
        this.add(viewGuide);
        this.add(imageLabel);
        this.add(imageLabel2);

        	
        Clip clip = null;	
        try {	
            File soundFile = new File("sound/startMenu.wav");	
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);	
            clip = AudioSystem.getClip();	
            clip.open(audioIn);	
            clip.start();	
            clip.loop(Clip.LOOP_CONTINUOUSLY);	
        } catch (Exception e) {	
            e.printStackTrace();	
        }	
        final Clip finalClip = clip; 	
        startButton.addActionListener(e -> {	
            finalClip.stop(); 	
            finalClip.close();	
            this.setVisible(false);	
            Gameplay GamePlay = new Gameplay();	
            frame.add(GamePlay);	
            frame.repaint();	
            GamePlay.requestFocus();	
        });	
        viewGuide.addActionListener(e -> {	
            finalClip.stop(); 	
            finalClip.close();	
            this.setVisible(false);	
            guidePage guidePage = new guidePage(frame);	
            frame.add(guidePage);	
            frame.repaint();	
            guidePage.requestFocus();	
        });
        
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
