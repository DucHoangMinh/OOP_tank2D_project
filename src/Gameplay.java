import java.util.*;
import java.awt.event.*;
import java.security.Key;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

public class Gameplay extends JPanel implements ActionListener 
{
	private brick br;

	private Timer timer;
	private int delay=8;
	
	private boolean impact;

	//khai báo đối tượng tank với các thuộc tính tương ứng
	Tank tank1 = new Tank(50, 550, 5, 0, true, false, false, false, false);
	Tank tank2 = new Tank(900, 150, 5, 0, false, true, false, false, false);
	
	private Control controlTank1;
	private Control_nother controlTank2;


	public Gameplay()
	{	
		//Nhét tường vào frame (đoạn này vẫn chưa hiện lên tường đâu,phải paint nó nữa)
		br = new brick();

		controlTank1 = new Control();
		controlTank2 = new Control_nother();

		setFocusable(true);

		addKeyListener(controlTank1);
		addKeyListener(controlTank2);

		timer = new Timer(delay, this);
		timer.start();
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

		//Vẽ 2 xe tank
		tank1.paintTank1(g);
		tank1.getPlayer_image().paintIcon(this, g, tank1.getPlayerX(), tank1.getPlayerY());

		tank2.paintTank2(g);
		tank2.getPlayer_image().paintIcon(this, g, tank2.getPlayerX(), tank2.getPlayerY());

		


	}

	@Override
	public void actionPerformed(ActionEvent e) {	
		timer.start();
		repaint();
	}

	private class Control implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		public void keyReleased(KeyEvent e) {}
		
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_W){
				tank1.setPlayer_up(true);
				tank1.setPlayer_down(false);
				tank1.setPlayer_left(false);
				tank1.setPlayer_right(false);

				if(tank1.getPlayerY()>5){
					tank1.setPlayerY(tank1.getPlayerY()-10);
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_S){
				tank1.setPlayer_up(false);
				tank1.setPlayer_down(true);
				tank1.setPlayer_left(false);
				tank1.setPlayer_right(false);

				if(tank1.getPlayerY()<700){
					tank1.setPlayerY(tank1.getPlayerY()+10);
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_A){
				tank1.setPlayer_up(false);
				tank1.setPlayer_down(false);
				tank1.setPlayer_left(true);
				tank1.setPlayer_right(false);

				if(tank1.getPlayerX()>5){
					tank1.setPlayerX(tank1.getPlayerX()-10);
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_D){
				tank1.setPlayer_up(false);
				tank1.setPlayer_down(false);
				tank1.setPlayer_left(false);
				tank1.setPlayer_right(true);

				if(tank1.getPlayerX()<950){
					tank1.setPlayerX(tank1.getPlayerX()+10);
				}
			}

		}

	}

	private class Control_nother implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {

		}
		public void keyReleased(KeyEvent e) {}
		
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_UP){
				tank2.setPlayer_up(true);
				tank2.setPlayer_down(false);
				tank2.setPlayer_left(false);
				tank2.setPlayer_right(false);

				if(tank2.getPlayerY()>5){
					tank2.setPlayerY(tank2.getPlayerY()-10);
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN){
				tank2.setPlayer_up(false);
				tank2.setPlayer_down(true);
				tank2.setPlayer_left(false);
				tank2.setPlayer_right(false);

				if(tank2.getPlayerY()<700){
					tank2.setPlayerY(tank2.getPlayerY()+10);
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT){
				tank2.setPlayer_up(false);
				tank2.setPlayer_down(false);
				tank2.setPlayer_left(true);
				tank2.setPlayer_right(false);

				if(tank2.getPlayerX()>5){
					tank2.setPlayerX(tank2.getPlayerX()-10);
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT){
				tank2.setPlayer_up(false);
				tank2.setPlayer_down(false);
				tank2.setPlayer_left(false);
				tank2.setPlayer_right(true);

				if(tank2.getPlayerX()<950){
					tank2.setPlayerX(tank2.getPlayerX()+10);
				}
			}
		}
	}
}
	
	
	