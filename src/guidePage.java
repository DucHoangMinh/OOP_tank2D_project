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
        
        JButton backButton = new JButton("Trang chủ");
        JButton switchButton = new JButton("Chuyển trang 1/2");
        JLabel guideLabel = new JLabel();
        
        //Setup ảnh
        ImageIcon tankFire = new ImageIcon("./img/tank fire.png");
        Image image = tankFire.getImage();
        Image newimage = image.getScaledInstance(1236, 780, java.awt.Image.SCALE_SMOOTH); //3 dòng trước đó để setup ảnh có kích thước hợp lý với label
        guideLabel.setIcon(new ImageIcon(newimage));
        
        //Set 
        guideLabel.setBounds(0, -70, 1250, 900);
        switchButton.setBounds(900, 657, 140, 60);
        backButton.setBounds(1070, 657, 140, 60);
        
        //Setup các thuộc tính của button "Trang chủ"
        backButton.setBackground(new Color(207, 177, 139));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setForeground(Color.WHITE); //màu chữ trong button

        //phương thức khi trỏ chuột vào button thì thực hiện mouseEntered, khi không trỏ nữa thì thực hiện mouseExited
        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                backButton.setBackground(new Color(250, 200, 100));
                backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5)); //màu viền của button
                backButton.setBorderPainted(true); //set trạng thái của màu viền xem true hay false
            }

            public void mouseExited(MouseEvent e){
                backButton.setBackground(new Color(207, 177, 139));
                backButton.setBorderPainted(false);
            }
        });

        //Setup các thuộc tính của button "Chuyển trang", tương tự "Trang chủ"
        switchButton.setBackground(new Color(207, 177, 139));
        switchButton.setFont(new Font("Arial", Font.BOLD, 13));
        switchButton.setForeground(Color.WHITE); 
        switchButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                switchButton.setBackground(new Color(250, 200, 100));
                switchButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5)); 
                switchButton.setBorderPainted(true);
            }

            public void mouseExited(MouseEvent e){
                switchButton.setBackground(new Color(207, 177, 139));
                switchButton.setBorderPainted(false);
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
            guidePage2 guidePage2 = new guidePage2(frame);
            frame.add(guidePage2);
            frame.repaint();
            frame.requestFocus();
        });
    }
}