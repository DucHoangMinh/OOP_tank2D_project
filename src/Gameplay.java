import java.util.*;
import java.util.ResourceBundle.Control;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.security.Key;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;	
import javax.sound.sampled.AudioSystem;	
import javax.sound.sampled.Clip;
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
	private Tank tank2 = new Tank(550, 0, 5, 0, false, true, false, false, false);
	
	private String bulletShootDir1 = "";//hướng của viên đạn
	private String bulletShootDir2 = "";
	private Bullet tank1Bullet = null;
	private Bullet tank2Bullet = null;
	private boolean play = true;
	private Control controlTank1;
	private Control_nother controlTank2;

	//Biến lưu trữ cái đếm ngược
	private int countDownSeconds = 60;
	private Timer cdTimer;

	public void countDonw(){
		 cdTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countDownSeconds--;
                if (countDownSeconds <= 0) {
                    cdTimer.stop();
                }
            }
        });
        cdTimer.start();
	}

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
		countDonw();
		Clip clip1 = null;	
        try {	
            File soundFile1 = new File("sound/Gameplay5.wav");	
            AudioInputStream audioIn1 = AudioSystem.getAudioInputStream(soundFile1);	
            clip1 = AudioSystem.getClip();	
            clip1.open(audioIn1);	
            clip1.start();	
            clip1.loop(Clip.LOOP_CONTINUOUSLY);	
        } catch (Exception e) {	
            e.printStackTrace();	
        }	
		final Clip finalclip1 = clip1;	

}
	
	public boolean checkTankHitBrick(int x,int y){
		for(int i = 0; i < br.bricksXPos.length; i++){
			if(new Rectangle(x, y, 50, 50).intersects(new
			Rectangle(br.bricksXPos[i], br.bricksYPos[i], 50, 50))){
				return true;
			} 
		}
		for(int i = 0; i < br.solidBricksXPos.length; i++){
			if(new Rectangle(x, y, 50, 50).intersects(new
			Rectangle(br.solidBricksXPos[i], br.solidBricksYPos[i], 50, 50))){
				return true;
			} 
		}
		// for(int i=0; i< brickON.length;i++){	
				
		// 		if(new Rectangle(x, y, 50, 50).intersects(new	
		// 	Rectangle(br.treebricksXPos[i], br.treebricksYPos[i], 50, 50))){	
		// 		return true;	
		// 	}	
		// 	}	
				
			
		
		return false;
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
		//ve item	
		br.drawitem(this,g);
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
					Clip fire = null;	
					try{	
						File soundFile2 = new File("sound/Fire.wav");	
						AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(soundFile2);	
						fire = AudioSystem.getClip();	
						fire.open(audioIn2);	
						fire.start();	
					} catch (Exception e) {	
						e.printStackTrace();	
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
					Clip explose = null;	
					try{	
						File soundFile3 = new File("sound/Explosion.wav");	
						AudioInputStream audioIn3 = AudioSystem.getAudioInputStream(soundFile3);	
						explose = AudioSystem.getClip();	
						explose.open(audioIn3);	
						explose.start();	
					} catch (Exception e) {	
						e.printStackTrace();	
					}		
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
					Clip explose = null;	
					try{	
						File soundFile3 = new File("sound/Explosion.wav");	
						AudioInputStream audioIn3 = AudioSystem.getAudioInputStream(soundFile3);	
						explose = AudioSystem.getClip();	
						explose.open(audioIn3);	
						explose.start();	
					} catch (Exception e) {	
						e.printStackTrace();	
					}	
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
					Clip fire = null;	
					try{	
						File soundFile2 = new File("sound/Fire.wav");	
						AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(soundFile2);	
						fire = AudioSystem.getClip();	
						fire.open(audioIn2);	
						fire.start();	
					} catch (Exception e) {	
						e.printStackTrace();	
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
					Clip explose = null;	
					try{	
						File soundFile3 = new File("sound/Explosion.wav");	
						AudioInputStream audioIn3 = AudioSystem.getAudioInputStream(soundFile3);	
						explose = AudioSystem.getClip();	
						explose.open(audioIn3);	
						explose.start();	
					} catch (Exception e) {	
						e.printStackTrace();	
					}
				}
				
				//Dùng hàm checkCollision để kiểm tra đạn dính tường hay không
				if(br.checkCollision(tank2Bullet.getX(), tank2Bullet.getY())
						|| br.checkSolidCollision(tank2Bullet.getX(), tank2Bullet.getY()))
				{
					//nếu đạn dính tường,hủy bỏ viên đạn
					tank2Bullet = null;
					tank2.setPlayer_shoot(false);
					bulletShootDir2 = "";	
					Clip explose = null;	
					try{	
						File soundFile3 = new File("sound/Explosion.wav");	
						AudioInputStream audioIn3 = AudioSystem.getAudioInputStream(soundFile3);	
						explose = AudioSystem.getClip();	
						explose.open(audioIn3);	
						explose.start();	
					} catch (Exception e) {	
						e.printStackTrace();	
					}			
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
		}
		//Vẽ phần bảng điểm bên tay phải
		g.setColor(Color.black);
		g.setFont(new Font("sans-serif",Font.BOLD, 15));
		g.drawString("Scores", 1070,60);
		g.drawString("Player 1:  "+ tank1.getScore(), 1030,90);
		g.drawString("Player 2:  "+ tank2.getScore(), 1030,120);
		// g.drawRect(1050, 100, tank1.getHp()*20, 20);;
		
		Font font = new Font("Arial", Font.BOLD, 25);
		g.setFont(font);
		g.drawString("Lives", 1085,175);
		//g.drawString("Player 1 : ", 1020, 210);
		File fileTank1 = new File("tank1up.png");
		Image imageTanImage;
		try {
			imageTanImage = ImageIO.read(fileTank1);
			g.drawImage(imageTanImage, 1020, 190, getFocusCycleRootAncestor());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.drawString("TIME REMAIN : " + countDownSeconds, 1015, 700);
		File file1 = new File("live_" + (tank1.getHp()  > 5 ? 5 : tank1.getHp())+ ".png");	
		try {	
			Image image = ImageIO.read(file1);	
			g.drawImage(image, 1090, 205, (tank1.getHp() > 5 ? 5 : tank1.getHp()) * 20, 20, getFocusCycleRootAncestor());	
		} catch (IOException e) {	
			// TODO Auto-generated catch block	
			e.printStackTrace();	
		}	
		//g.drawString("Player 2:  ", 1020,240);
		File fileTank2 = new File("player2_tank_up.png");
		Image imageTanImage2;
		try {
			imageTanImage2 = ImageIO.read(fileTank2);
			g.drawImage(imageTanImage2, 1020, 250, getFocusCycleRootAncestor());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file2 = new File("live_" + (tank2.getHp() > 5 ? 5 : tank2.getHp()) + ".png");	
		try {	
			Image image = ImageIO.read(file2);	
			g.drawImage(image, 1090, 265, (tank2.getHp() > 5 ? 5 : tank2.getHp() )* 20, 20, getFocusCycleRootAncestor());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Xử lý nếu mạng của người 1 hoặc người 2 chỉ còn 0 (chết)
		if(tank1.getHp() == 0 || tank2.getHp() == 0 || countDownSeconds <= 0)
		{
			
			g.fillRect(0,0,1000,1000);
			g.setColor(Color.white);
			g.setFont(new Font("san-serif",Font.BOLD, 60));
			g.drawString("Game Over", 300,300);
			g.drawString("Player " + (tank1.getScore() > tank2.getScore() ? "1" : "2") + " Won", 280,380);
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
			if(e.getKeyCode()== KeyEvent.VK_SPACE && ((tank1.getHp() == 0 || tank2.getHp() == 0) || countDownSeconds == 0))
			{
				br = new brick();
				tank1.setPlayerX(50);
				tank1.setPlayerY(550);	
				tank1.setPlayer_down(false);
				tank1.setPlayer_left(false);
				tank1.setPlayer_right(false);
				tank1.setPlayer_up(true);	
				
				tank2.setPlayerX(550);
				tank2.setPlayerY(0);	
				tank2.setPlayer_up(false);
				tank2.setPlayer_right(false);
				tank2.setPlayer_left(false);
				tank2.setPlayer_down(true);	
				
				tank1.setScore(0);
				tank1.setHp(5);
				tank2.setScore(0);
				tank2.setHp(5);
				countDownSeconds = 120;
				countDonw();
				play = true;
				repaint();
			}

			if(e.getKeyCode()==KeyEvent.VK_W){
				tank1.setPlayer_up(true);
				tank1.setPlayer_down(false);
				tank1.setPlayer_left(false);
				tank1.setPlayer_right(false);
				if(checkTankHitBrick(tank1.getPlayerX(),tank1.getPlayerY() - 10) == false){
					if(tank1.getPlayerY()>5 && impact_up1){
						tank1.setPlayerY(tank1.getPlayerY()-10);
					}
				}

				if(br.checkitemCollision(tank1.getPlayerX(),tank1.getPlayerY())){	
					tank1.setHp(tank1.getHp() >= 5 ? 5 : tank1.getHp() + 1);	
				}
				impact_up1 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_S){
				tank1.setPlayer_up(false);
				tank1.setPlayer_down(true);
				tank1.setPlayer_left(false);
				tank1.setPlayer_right(false);

				if(checkTankHitBrick(tank1.getPlayerX(),tank1.getPlayerY() + 10) == false){
					if(tank1.getPlayerY()<700 && impact_down1){
						tank1.setPlayerY(tank1.getPlayerY()+10);
					}
				}
				if(br.checkitemCollision(tank1.getPlayerX(),tank1.getPlayerY())){	
					tank1.setHp(tank1.getHp() >= 5 ? 5 : tank1.getHp() + 1);	
				}
				impact_down1 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_A){
				tank1.setPlayer_up(false);
				tank1.setPlayer_down(false);
				tank1.setPlayer_left(true);
				tank1.setPlayer_right(false);

				if(checkTankHitBrick(tank1.getPlayerX() - 10,tank1.getPlayerY()) == false){
					if(tank1.getPlayerX()>5 && impact_left1){
						tank1.setPlayerX(tank1.getPlayerX()-10);
					}
				}
				if(br.checkitemCollision(tank1.getPlayerX(),tank1.getPlayerY())){	
					tank1.setHp(tank1.getHp() >= 5 ? 5: tank1.getHp() + 1);	
				}
				impact_left1 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_D){
				tank1.setPlayer_up(false);
				tank1.setPlayer_down(false);
				tank1.setPlayer_left(false);
				tank1.setPlayer_right(true);

				if(checkTankHitBrick(tank1.getPlayerX() + 10,tank1.getPlayerY()) == false){
					if(tank1.getPlayerX()<950 && impact_right1){
						tank1.setPlayerX(tank1.getPlayerX()+10);
					}
				}
				if(br.checkitemCollision(tank1.getPlayerX(),tank1.getPlayerY())){	
					tank1.setHp(tank1.getHp() >= 5 ? 5 : tank1.getHp() + 1);	
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
			repaint();
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

				if(checkTankHitBrick(tank2.getPlayerX(),tank2.getPlayerY() - 10) == false){
					if(tank2.getPlayerY()>5 && impact_up2){
						tank2.setPlayerY(tank2.getPlayerY()-10);
					}
				}
				if(br.checkitemCollision(tank2.getPlayerX(),tank2.getPlayerY())){	
					tank2.setHp(tank2.getHp() >= 5 ? 5 : tank2.getHp() + 1);	
				}
				impact_up2 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN){
				tank2.setPlayer_up(false);
				tank2.setPlayer_down(true);
				tank2.setPlayer_left(false);
				tank2.setPlayer_right(false);

				if(checkTankHitBrick(tank2.getPlayerX(),tank2.getPlayerY() + 10) == false){
					if(tank2.getPlayerY()<700 && impact_down2){
						tank2.setPlayerY(tank2.getPlayerY()+10);
					}
				}
				if(br.checkitemCollision(tank2.getPlayerX(),tank2.getPlayerY())){	
					tank2.setHp(tank2.getHp() >= 5 ? 5 : tank2.getHp() + 1);	
				}
				impact_down2 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT){
				tank2.setPlayer_up(false);
				tank2.setPlayer_down(false);
				tank2.setPlayer_left(true);
				tank2.setPlayer_right(false);

				if(checkTankHitBrick(tank2.getPlayerX() - 10,tank2.getPlayerY()) == false){
					if(tank2.getPlayerX()>5 && impact_left2){
						tank2.setPlayerX(tank2.getPlayerX()-10);
					}
				}
				if(br.checkitemCollision(tank2.getPlayerX(),tank2.getPlayerY())){	
					tank2.setHp(tank2.getHp() >= 5 ? 5 : tank2.getHp() + 1);	
				}
				impact_left2 = true;
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT){
				tank2.setPlayer_up(false);
				tank2.setPlayer_down(false);
				tank2.setPlayer_left(false);
				tank2.setPlayer_right(true);
								
				if(checkTankHitBrick(tank2.getPlayerX() + 10,tank2.getPlayerY()) == false){
					if(tank2.getPlayerX()<950 && impact_right2){
						tank2.setPlayerX(tank2.getPlayerX()+10);
					}
				}
				if(br.checkitemCollision(tank2.getPlayerX(),tank2.getPlayerY())){	
					tank2.setHp(tank2.getHp() >= 5 ? 5 : tank2.getHp() + 1);	
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
			repaint();
		}
	}
}
	
	
	