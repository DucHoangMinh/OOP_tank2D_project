import java.util.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

public class Gameplay extends JPanel implements ActionListener 
{
	private brick br;
	
	//khai báo đối tượng tank với các thuộc tính tương ứng
	Tank tank1 = new Tank(50, 550, 5, 0, true, false, false, false, false);
	Tank tank2 = new Tank(900, 150, 5, 0, false, true, false, false, false);
	
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

		tank1.paintTank1(g);
		tank1.getPlayer_image().paintIcon(this, g, tank1.getPlayerX(), tank1.getPlayerY());

		tank2.paintTank2(g);
		tank2.getPlayer_image().paintIcon(this, g, tank2.getPlayerX(), tank2.getPlayerY());
	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		repaint();
	}
}
	
	
	