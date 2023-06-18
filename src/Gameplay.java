import java.util.*;
import java.util.ResourceBundle.Control;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.security.Key;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.awt.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;

public class Gameplay extends JPanel implements ActionListener 
{
	private brick br;

	private Timer timer;
	private int delay=5;
	
	private boolean impact_up1 = true;
	private boolean impact_down1 = true;
	private boolean impact_right1 = true;
	private boolean impact_left1 = true;

	private boolean impact_up2 = true;
	private boolean impact_down2 = true;
	private boolean impact_right2 = true;
	private boolean impact_left2 = true;

	//khai báo đối tượng tank với các thuộc tính tương ứng
	private Tank tank1 = new Tank(50, 550, 5, 0, true, false, false, false, false);
	private Tank tank2 = new Tank(100, 200, 5, 0, false, true, false, false, false);

	
	
	private String bulletShootDir1 = "";//hướng của viên đạn
	private String bulletShootDir2 = "";
	private Bullet tank1Bullet = null;
	private Bullet tank2Bullet = null;
	private boolean play = true;
	
	private Control controlTank1;
	private Control_nother controlTank2;

	
	public Gameplay()
	{	
		//Nhét tường vào frame (đoạn này vẫn chưa hiện lên tường đâu,phải paint nó nữa)
		br = new brick();

		controlTank1 = new Control();
		controlTank2 = new Control_nother();

		setFocusable(true);
		setFocusTraversalKeysEnabled(true);

		addKeyListener(controlTank1);
		addKeyListener(controlTank2);

		timer = new Timer(delay, this);
		timer.start();
	}
	

	public void paint(Graphics g)
	{    		
		//Vẽ phần chơi game
		g.setColor(Color.black);
		g.fillRect(0, 0, 1100, 1000);
		
		//Vẽ nền phần điểm số tay phải
		g.setColor(Color.WHITE);
		g.fillRect(1000, 0, 340, 800);

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
	
				//Nếu viên đạn đi ra ngoài phạm vi Frame,thì hủy bỏ đường đạn
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
					tank2.setScore(tank2.getScore() + 10);
					tank1.setHp(tank1.getHp() - 1);
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
	
				//Nếu viên đạn đi ra ngoài phạm vi Frame,thì hủy bỏ đường đạn
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
				
			//Hàm check xe đụng tường cứng, true thì cho thực hiện lệnh di chuyển, false thì không cho đi tiếp
				for(int i = 0; i < br.solidBricksXPos.length; i++){
					if(new Rectangle(tank1.getPlayerX(), tank1.getPlayerY() + 10, 50, 50)
						.intersects(new Rectangle(br.solidBricksXPos[i], br.solidBricksYPos[i], 50, 50))
					|| new Rectangle(tank1.getPlayerX(),tank1.getPlayerY(),50,50).intersects(new Rectangle(tank2.getPlayerX(), tank2.getPlayerY(), 50, 50))){
						if(tank1.getPlayer_up()){
							impact_up1 = false;
							impact_down1 = true;
							impact_left1 = true;
							impact_right1 = true;
						}
						else if(tank1.getPlayer_down()){
							impact_up1 = true;
							impact_down1 = false;
							impact_left1 = true;
							impact_right1 = true;
						}
						else if(tank1.getPlayer_left()){
							impact_up1 = true;
							impact_down1 = true;
							impact_left1 = false;
							impact_right1 = true;
						}
						else if(tank1.getPlayer_right()){
							impact_up1 = true;
							impact_down1 = true;
							impact_left1 = true;
							impact_right1 = false;
						}
					}
				}

				for(int i = 0; i < br.solidBricksXPos.length; i++){
					if(new Rectangle(tank2.getPlayerX(), tank2.getPlayerY() + 10, 50, 50).intersects(new Rectangle(br.solidBricksXPos[i], br.solidBricksYPos[i], 50, 50))
					|| new Rectangle(tank1.getPlayerX(),tank1.getPlayerY(),50,50).intersects(new Rectangle(tank2.getPlayerX(), tank2.getPlayerY(), 50, 50))){
						if(tank2.getPlayer_up()){
							impact_up2 = false;
							impact_down2 = true;
							impact_left2 = true;
							impact_right2 = true;
						}
						else if(tank2.getPlayer_down()){
							impact_up2 = true;
							impact_down2 = false;
							impact_left2 = true;
							impact_right2 = true;
						}
						else if(tank2.getPlayer_left()){
							impact_up2 = true;
							impact_down2 = true;
							impact_left2 = false;
							impact_right2 = true;
						}
						else if(tank2.getPlayer_right()){
							impact_up2 = true;
							impact_down2 = true;
							impact_left2 = true;
							impact_right2 = false;
						}
					}
				}
		}
		//Vẽ phần bảng điểm bên tay phải
		g.setColor(Color.black);
		g.setFont(new Font("sans-serif",Font.BOLD, 15));
		g.drawString("Scores", 1070,60);
		g.drawString("Player 1:  "+ tank1.getScore(), 1030,90);
		g.drawString("Player 2:  "+ tank2.getScore(), 1030,120);
		// g.drawRect(1050, 100, tank1.getHp()*20, 20);;
		
		g.drawString("Lives", 1070,180);
		g.drawString("Player 1 : ", 1020, 210);
		// g.drawString("Player 1:  "+ tank1.getHp(), 1030,180);
		File file1 = new File("live_" + tank1.getHp() + ".png");
		try {
			Image image = ImageIO.read(file1);
			g.drawImage(image, 1090, 195, tank1.getHp() * 20, 20, getFocusCycleRootAncestor());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawString("Player 2:  ", 1020,240);
		File file2 = new File("live_" + tank2.getHp() + ".png");
		try {
			Image image = ImageIO.read(file2);
			g.drawImage(image, 1090, 225, tank2.getHp() * 20, 20, getFocusCycleRootAncestor());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Xử lý nếu mạng của người 1 hoặc người 2 chỉ còn 0 (chết)
		if(tank1.getHp() == 0 || tank2.getHp() == 0)
		{
			g.fillRect(0,0,1000,1000);
			g.setColor(Color.white);
			g.setFont(new Font("san-serif",Font.BOLD, 60));
			g.drawString("Game Over", 300,300);
			g.drawString("Player 2 Won", 280,380);
			play = false;
			g.setColor(Color.white);
			g.setFont(new Font("sans-serif",Font.BOLD, 30));
			g.drawString("(Space to Restart)",330,430);
		}
		g.dispose();
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

				if(tank1.getPlayerY()>5 && impact_up1){
					tank1.setPlayerY(tank1.getPlayerY()-10);
				}
				impact_up1 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_S){
				tank1.setPlayer_up(false);
				tank1.setPlayer_down(true);
				tank1.setPlayer_left(false);
				tank1.setPlayer_right(false);

				
				if(tank1.getPlayerY()<700 && impact_down1){
					tank1.setPlayerY(tank1.getPlayerY()+10);
				}
				impact_down1 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_A){
				tank1.setPlayer_up(false);
				tank1.setPlayer_down(false);
				tank1.setPlayer_left(true);
				tank1.setPlayer_right(false);


				if(tank1.getPlayerX()>5 && impact_left1){
					tank1.setPlayerX(tank1.getPlayerX()-10);
				}
				impact_left1 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_D){
				tank1.setPlayer_up(false);
				tank1.setPlayer_down(false);
				tank1.setPlayer_left(false);
				tank1.setPlayer_right(true);

				if(tank1.getPlayerX()<950 && impact_right1){
					tank1.setPlayerX(tank1.getPlayerX()+10);
				}
				impact_right1 = true;
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

				if(tank2.getPlayerY()>5 && impact_up2){
					tank2.setPlayerY(tank2.getPlayerY()-10);
				}
				impact_up2 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN){
				tank2.setPlayer_up(false);
				tank2.setPlayer_down(true);
				tank2.setPlayer_left(false);
				tank2.setPlayer_right(false);

				if(tank2.getPlayerY()<700 && impact_down2){
					tank2.setPlayerY(tank2.getPlayerY()+10);
				}
				impact_down2 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT){
				tank2.setPlayer_up(false);
				tank2.setPlayer_down(false);
				tank2.setPlayer_left(true);
				tank2.setPlayer_right(false);

				if(tank2.getPlayerX()>5 && impact_left2){
					tank2.setPlayerX(tank2.getPlayerX()-10);
				}
				impact_left2 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT){
				tank2.setPlayer_up(false);
				tank2.setPlayer_down(false);
				tank2.setPlayer_left(false);
				tank2.setPlayer_right(true);

				if(tank2.getPlayerX()<950 && impact_right2){
					tank2.setPlayerX(tank2.getPlayerX()+10);
				}
				impact_right2 = true;
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
	
	
	