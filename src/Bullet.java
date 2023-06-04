import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;


public class Bullet {
	private double x, y;
	public Bullet(double x, double y)
	{
		this.x = x;
		this.y = y;
	}//set tọa độ viên đạn
	
    
    public void move(String face)
	{
		if(face.equals("right"))
			x += 5;
		else if(face.equals("left"))
			x -= 5;
		else if(face.equals("up"))
			y -= 5;
		else
			y +=5;
	}//hàm di chuyển viên đạn với face là string hướng viên đạn
	

	public void draw(Graphics g)
	{
		g.setColor(Color.green);
		g.fillOval((int) x, (int) y, 10, 10);
	}//vẽ viên đạn
	
	
    public int getX()
	{
		return (int)x;
	}
	public int getY()
	{
		return (int)y;
	}


	private boolean playerShoot = false;//trạng thái bắn của viên đạn
	private String bulletShootDir = "";//hướng của viên đạn
	private Bullet playerBullet = null;
	private Tank tank1, tank2;


	public void Shoot(brick br,Tank tank1,Tank tank2){
		if(play){
			if(playerBullet != null && playerShoot)
			{
				//Nếu hướng của đạn đang là rỗng,thì set cho hướng của đạn chính bằng hướng của xe tăng ngay lúc đó
				if(bulletShootDir.equals(""))
				{
					if(playerup)
					{					
						bulletShootDir = "up";
					}
					else if(playerdown)
					{					
						bulletShootDir = "down";
					}
					else if(playerright)
					{				
						bulletShootDir = "right";
					}
					else if(playerleft)
					{			
						bulletShootDir = "left";
					}
				}
				//Hướng của đạn đã được xác định
				//dùng hàm move (được xây dựng trong lớp PlayerBullet) để tịnh tiến 
				//hàm draw để vẽ lại đường đạn sau khi tịnh tiến
				else
				{
					playerBullet.move(bulletShootDir);
					playerBullet.draw(g);
				}
				//Xử lý khi phát hiện người chơi 2 có trúng đạn của người chơi 1 không
				if(new Rectangle(playerBullet.getX(), playerBullet.getY(), 10, 10).intersects(new Rectangle(tank2.getPlayerX(),tank2.getPlayerY(), 50, 50)))
				{
					//Nếu chúng đạn thì tăng điểm người 1,trừ mạng người 2,hủy luôn viên đạn người 1...
					tank1.setScore(tank1.getScore() + 10);
					tank2.setHp(tank2.getHp() - 1);
					playerBullet = null;
					playerShoot = false;
					bulletShootDir = "";
				}

				//Check người chơi 1 chúng đạn người chơi 2
				if(new Rectangle(playerBullet.getX(), playerBullet.getY(), 10, 10).intersects(new Rectangle(tank1.getPlayerX(), tank1.getPlayerY(), 50, 50)))
				{
					//Nếu chúng đạn thì tăng điểm người 1,trừ mạng người 2,hủy luôn viên đạn người 1...
					tank2.setScore(tank2.getScore() + 10);
					tank1.setHp(tank1.getHp() - 1);
					playerBullet = null;
					playerShoot = false;
					bulletShootDir = "";
				}
				
				//Dùng hàm checkCollision để kiểm tra đạn dính tường hay không
				if(br.checkCollision(playerBullet.getX(), playerBullet.getY())
						|| br.checkSolidCollision(playerBullet.getX(), playerBullet.getY()))
				{
					//nếu đạn dính tường,hủy bỏ viên đạn
					playerBullet = null;
					playerShoot = false;
					bulletShootDir = "";				
				}
	
				//Cái này để xem nếu viên đạn đi ra ngoài phạm vi Frame,thì hủy bỏ đường đạn
				if(playerBullet.getY() < 1 
						|| playerBullet.getY() > 1000
						|| playerBullet.getX() < 1
						|| playerBullet.getX() > 1000)
				{
					playerBullet = null;
					playerShoot = false;
					bulletShootDir = "";
				}
			}
		}
	}


}

    
