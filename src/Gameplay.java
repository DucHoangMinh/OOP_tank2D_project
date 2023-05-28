import java.util.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

public class Gameplay extends JPanel implements ActionListener 
{
	private brick br;
	
	
	public Gameplay()
	{	
		//Nhét tường vào frame (đoạn này vẫn chưa hiện lên tường đâu,phải paint nó nữa)
		br = new brick();
	}
	
	//Đoạn này vẽ tất cả mọi thứ hiện ra trước mắt người nhìn,anh em vào main chạy project để nó
	//hiện cái frame chính lên cho dễ hiểu nhá
	public void paint(Graphics g)
	{    		
		//Vẽ phần chơi game
		g.setColor(Color.black);
		g.fillRect(0, 0, 1000, 1000);
		
		//Vẽ phần bảng điểm bên tay phải
		//g.setColor(Color.DARK_GRAY);
		g.fillRect(660, 0, 140, 600);
		
		//Vẽ gạch cứng
		br.drawSolids(this, g);
		br.drawTree(this, g);
		
		//Vẽ gạch mềm
		br.draw(this, g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		repaint();
	}
}
	
	
	