import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Main {
	public static void main(String[] args) {
		JFrame map=new JFrame();
        // Gameplay GamePlay = new Gameplay();
		startMenu start = new startMenu(map);
		map.setBounds(0, 0, 1250, 787);//kích cỡ
		map.setTitle("Tank 2D");	// tiêu đề
		// map.getContentPane().setBackground(Color.gray);//nền
		map.setResizable(false);//thu phóng
		map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//nút tắt
		map.setVisible(true);//thu phóng
        map.setLocationRelativeTo(null);// frame luôns ở chính giữa
        map.add(start);
        ImageIcon image= new ImageIcon("logo.png", null);// creat an ImageIcon
    	map.setIconImage(image.getImage());// logo
	}
}
