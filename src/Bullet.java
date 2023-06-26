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
}

    