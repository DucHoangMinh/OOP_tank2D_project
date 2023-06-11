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

	private String bulletShootDir1 = "";//hướng của viên đạn
	private String bulletShootDir2 = "";
	private Bullet tank1Bullet = null;
	private Bullet tank2Bullet = null;
	private boolean play = true;

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

		if(play){
			if(tank1Bullet != null && tank1.isPlayer_shoot())//Xử lý đạn tank1
			{
				//Nếu hướng của đạn đang là rỗng,thì set cho hướng của đạn chính bằng hướng của xe tăng ngay lúc đó
				if(bulletShootDir1.equals(""))
				{
					if(tank1.isPlayer_up())
					{					
						bulletShootDir1 = "up";
					}
					else if(tank1.isPlayer_down())
					{					
						bulletShootDir1 = "down";
					}
					else if(tank1.isPlayer_right())
					{				
						bulletShootDir1 = "right";
					}
					else if(tank1.isPlayer_left())
					{			
						bulletShootDir1 = "left";
					}
				}
				//Hướng của đạn đã được xác định
				//dùng hàm move để tịnh tiến 
				//hàm draw để vẽ lại đường đạn sau khi tịnh tiến
				else
				{
					tank1Bullet.move(bulletShootDir1);
					tank1Bullet.draw(g);
				}
				//Xử lý khi phát hiện người chơi 2 có trúng đạn của người chơi 1 không
				if(new Rectangle(tank1Bullet.getX(), tank1Bullet.getY(), 10, 10).intersects(new Rectangle(tank2.getPlayerX(),tank2.getPlayerY(), 50, 50)))
				{
					//Nếu chúng đạn thì tăng điểm người 1,trừ mạng người 2,hủy luôn viên đạn người 1...
					tank1.setScore(tank1.getScore() + 10);
					tank2.setHp(tank2.getHp() - 1);
					tank1Bullet = null;
					tank1.setPlayer_shoot(false);
					bulletShootDir1 = "";
				}
				
				//Dùng hàm checkCollision vs checkSolidCollision để kiểm tra đạn dính tường hay không
				if(br.checkCollision(tank1Bullet.getX(), tank1Bullet.getY())
						|| br.checkSolidCollision(tank1Bullet.getX(), tank1Bullet.getY()))
				{
					//nếu đạn dính tường,hủy bỏ viên đạn
					tank1Bullet = null;
					tank1.setPlayer_shoot(false);
					bulletShootDir1 = "";			
				}
	
				//Cái này để xem nếu viên đạn đi ra ngoài phạm vi Frame,thì hủy bỏ đường đạn
				if(tank1Bullet.getY() < 1 
						|| tank1Bullet.getY() > 750
						|| tank1Bullet.getX() < 1
						|| tank1Bullet.getX() > 985)
				{
					tank1Bullet = null;
					tank1.setPlayer_shoot(false);
					bulletShootDir1 = "";
				}
			}


			if(tank2Bullet != null && tank2.isPlayer_shoot())//Xử lý đạn tank2
			{
				//Nếu hướng của đạn đang là rỗng,thì set cho hướng của đạn chính bằng hướng của xe tăng ngay lúc đó
				if(bulletShootDir2.equals(""))
				{
					if(tank2.isPlayer_up())
					{					
						bulletShootDir2 = "up";
					}
					else if(tank2.isPlayer_down())
					{					
						bulletShootDir2 = "down";
					}
					else if(tank2.isPlayer_right())
					{				
						bulletShootDir2 = "right";
					}
					else if(tank2.isPlayer_left())
					{			
						bulletShootDir2 = "left";
					}
				}
				else
				{
					tank2Bullet.move(bulletShootDir2);
					tank2Bullet.draw(g);
				}
				if(new Rectangle(tank2Bullet.getX(), tank2Bullet.getY(), 10, 10).intersects(new Rectangle(tank1.getPlayerX(),tank1.getPlayerY(), 50, 50)))
				{
					//Nếu chúng đạn thì tăng điểm người 1,trừ mạng người 2,hủy luôn viên đạn người 1...
					tank2.setScore(tank1.getScore() + 10);
					tank1.setHp(tank2.getHp() - 1);
					tank2Bullet = null;
					tank2.setPlayer_shoot(false);
					bulletShootDir2 = "";
				}
				
				//Dùng hàm checkCollision để kiểm tra đạn dính tường hay không
				if(br.checkCollision(tank2Bullet.getX(), tank2Bullet.getY())
						|| br.checkSolidCollision(tank2Bullet.getX(), tank2Bullet.getY()))
				{
					//nếu đạn dính tường,hủy bỏ viên đạn
					tank2Bullet = null;
					tank2.setPlayer_shoot(false);
					bulletShootDir2 = "";				
				}
	
				//Cái này để xem nếu viên đạn đi ra ngoài phạm vi Frame,thì hủy bỏ đường đạn
				if(tank2Bullet.getY() < 1 
						|| tank2Bullet.getY() > 750
						|| tank2Bullet.getX() < 1
						|| tank2Bullet.getX() > 985)
				{
					tank2Bullet = null;
					tank2.setPlayer_shoot(false);
					bulletShootDir2 = "";
				}
			}

		}


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
			if(e.getKeyCode()== KeyEvent.VK_SPACE && (tank1.getHp() == 0 || tank2.getHp() == 0))
			{
				br = new brick();
				tank1.setPlayerX(50);
				tank1.setPlayerY(550);	
				tank1.setPlayer_down(false);
				tank1.setPlayer_left(false);
				tank1.setPlayer_right(false);
				tank1.setPlayer_up(true);	
				
				tank2.setPlayerX(900);
				tank2.setPlayerY(150);	
				tank2.setPlayer_up(false);
				tank2.setPlayer_right(false);
				tank2.setPlayer_left(false);
				tank2.setPlayer_down(true);	
				
				tank1.setScore(0);
				tank1.setHp(5);
				tank2.setScore(0);
				tank2.setHp(5);
				play = true;
				repaint();
			}

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

			if(e.getKeyCode()== KeyEvent.VK_U)
			{
				if(!tank1.isPlayer_shoot())
				{
					if(tank1.isPlayer_up())
					{					
						tank1Bullet = new Bullet(tank1.getPlayerX() + 20, tank1.getPlayerY());
					}
					else if(tank1.isPlayer_down())
					{					
						tank1Bullet = new Bullet(tank1.getPlayerX() + 20, tank1.getPlayerY() + 40);
					}
					else if(tank1.isPlayer_right())
					{				
						tank1Bullet = new Bullet(tank1.getPlayerX() + 40, tank1.getPlayerY() + 20);
					}
					else if(tank1.isPlayer_left())
					{			
						tank1Bullet = new Bullet(tank1.getPlayerX(), tank1.getPlayerY() + 20);
					}
					
					tank1.setPlayer_shoot(true);
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

			if(e.getKeyCode()== KeyEvent.VK_M)
			{
				if(!tank2.isPlayer_shoot())
				{
					if(tank2.isPlayer_up())
					{					
						tank2Bullet = new Bullet(tank2.getPlayerX() + 20, tank2.getPlayerY());
					}
					else if(tank2.isPlayer_down())
					{					
						tank2Bullet = new Bullet(tank2.getPlayerX() + 20, tank2.getPlayerY() + 40);
					}
					else if(tank2.isPlayer_right())
					{				
						tank2Bullet = new Bullet(tank2.getPlayerX() + 40, tank2.getPlayerY() + 20);
					}
					else if(tank2.isPlayer_left())
					{			
						tank2Bullet = new Bullet(tank2.getPlayerX(), tank2.getPlayerY() + 20);
					}
					
					tank2.setPlayer_shoot(true);
				}
			}
		}
	}
}
	
	
	