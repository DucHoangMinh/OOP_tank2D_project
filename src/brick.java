import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class brick {
	//2 Mảng lưu hoành độ và tung độ của các viên gạch cứng
	//Kích thước các viên gạch (và cả xe tăng đều là 50*50px)
	//Trục tọa độ là đỉnh trên bên trái của Frame
	//Ví dụ : viên gạch đầu tiên có tọa độ x = 350,y = 0 sẽ cách cạnh trên cùng 50px,và cách cạnh bên trái 50px
	int solidBricksXPos[] = {350,600,350,600,200,350,600,850,200,350,600,850,200,350,550,600,850,200,	
		350,850,0,50,750,800,850,600,200,600,100,150,200,600,100,350,400,	
		600,750,100,350,600,650,700,750,900,950,100,350,100,150,200,350,400,450,200,	
		650,700,750,800,850};	
	int solidBricksYPos[] = {0,0,50,50,100,100,100,100,150,150,150,150,200,200,200,200,200,250,	
		250,250,300,300,300,300,300,350,400,400,450,450,450,450,500,500,500,	
		500,500,550,550,550,550,550,550,550,550,600,600,650,650,650,650,650,650,700,	
		700,700,700,700,700};
	
	//Mảng lưu vị trí (tọa độ + tung độ) của nước
	int bricksXPos[] = {0,50,0,50,700,750,700,750,450,500,450,500,100,150,100,150};
	int bricksYPos[] = {50,50,100,100,100,100,150,150,300,300,350,350,700,700,750,750};
	
	//Mảng lưu tọa độ của cây
	int treebricksXpod[]={100,150,250,300,600,200,350,600,900,950,350,750,750,500,550,650,850,0,50,650,850};
    int treebricksYpod[]={100,100,100,100,250,300,300,300,300,300,350,350,450,500,500,600,600,650,650,650,650};
	//Mảng lưu item	
	int itembricksXpod[]={450,900,100,700,450,150,500,950};	
	int itembricksYpod[]={0,0,200,250,450,500,650,700};
	
	
	//Mảng lưu trạng thái của các viên gạch,ở đây đang là 23 cây (sau này sẽ thêm nhiều cây hơn để tăng tương tác giữa ng chơi vs map)
	//Khi bắt đầu trò chơi,tất cả "cây" đều được hiện lên thì trạng thái của nó là 1
	int brickON[] = new int[21];
	int itemON[] = new int[8];
	

	//3 loại biến này để lưu hình ảnh của nước, gạch cứng, cây
	private ImageIcon breakBrickImage;
	private ImageIcon solidBrickImage;
	private ImageIcon treeBrickImage;
	private ImageIcon itemBrickImage;	

	//Khai báo địa chỉ hình ảnh của nước, gạch cứng, cây
	public brick()	
	{
		breakBrickImage=new ImageIcon("water1.png");
		solidBrickImage=new ImageIcon("wall.jpg");	
		treeBrickImage=new ImageIcon("tree2new.png");
		itemBrickImage=new ImageIcon("item_1.png");

		//Vòng lặp này set cho trạng thái tất cả "cây" là 1,tức là cho trạng thái của chúng đang là hiện lên
		for(int i=0; i < brickON.length;i++)
		{
			brickON[i] = 1;
		}
		for(int i=0; i < itemON.length;i++)	
		{	
			itemON[i] = 1;	
		}
	}
	
	//vẽ "nước"
	public void draw(Component c, Graphics g)
	{
		for(int i=0; i< bricksXPos.length;i++)
		{
				breakBrickImage.paintIcon(c, g, bricksXPos[i],bricksYPos[i]);
		}
	}

	//Cũng như cái trên nma để vẽ gạch cứng
	public void drawSolids(Component c, Graphics g)
	{
		for(int i=0; i< solidBricksXPos.length;i++)
		{			
			solidBrickImage.paintIcon(c, g, solidBricksXPos[i],solidBricksYPos[i]);
		}
	}
	// ve item	
	public void drawitem(Component c, Graphics g)	
	{	
		for(int i=0; i< itembricksXpod.length;i++)	
		{		
			if(itemON[i]==1)			
				itemBrickImage.paintIcon(c, g, itembricksXpod[i],itembricksYpod[i]);	
		}	
	}

	//Phương thức draw lặp qua mảng brickON 1 lần để xem viên gạch nào có trạng thái là 1,thì nó sẽ được chính thức
		//vẽ lên màn hình chính
	//vẽ cây(gạch mềm)
	public void drawTree(Component c, Graphics g)
	{
		for(int i=0; i< treebricksYpod.length;i++)
		{	
			if(brickON[i]==1)		
				treeBrickImage.paintIcon(c, g, treebricksXpod[i],treebricksYpod[i]);
		}
	}
	
	
	// 2 Phương thức dưỡi đây dùng để kiểm tra xem có sự va chạm giữa đạn và gạch, cây hay không
	//Hai hàm dưới đây có đối số truyền vào là x,y để sau này truyền vào tọa độ và tung độ của viên đạn
	public boolean checkCollision(int x, int y)
	{
		boolean collided = false;//Biến này lưu trạng thái có va chạm không,nếu có set là true và trả về
									//Không thì để là false
		for(int i=0; i< brickON.length;i++)
		{
			if(brickON[i]==1)
			{
				//Anh em tự search và tìm hiểu lớp Rectangle.Đại khái,lớp này sẽ tạo ra một hình chữ nhật tại tọa độ x,y và kích thước width,height
				//Lớp này cũng hộ trợ một phương thức cực kỳ tiện là intersects để kiểm tra hai Rectangle có chạm vào nhau không,có thì trả về true
				if(new Rectangle(x, y, 10, 10).intersects(new Rectangle(treebricksXpod[i], treebricksYpod[i], 50, 50)))
				{	
					//Nếu có va chạm,set cho trạng thái của cây bị va chạm trạng thái là 0,tức là nó sẽ không xuất hiện trên bản đồ nữa
					brickON[i] = 0;
					collided = true;
					break;
				}
			}
		}
		
		return collided;
	}
	// public boolean checktreeCollision(int x, int y)	
	// {	
	// 	boolean collided = false;//Biến này lưu trạng thái có va chạm không,nếu có set là true và trả về	
	// 								//Không thì để là false	
	// 	for(int i=0; i< brickON.length;i++)	
	// 	{	
	// 		if(brickON[i]==1)	
	// 		{	
	// 			//Anh em tự search và tìm hiểu lớp Rectangle.Đại khái,lớp này sẽ tạo ra một hình chữ nhật tại tọa độ x,y và kích thước width,height	
	// 			//Lớp này cũng hộ trợ một phương thức cực kỳ tiện là intersects để kiểm tra hai Rectangle có chạm vào nhau không,có thì trả về true	
	// 			if(new Rectangle(x, y, 50, 50).intersects(new Rectangle(treebricksXpod[i], treebricksYpod[i], 50, 50)))	
	// 			{		
	// 				//Nếu có va chạm,set cho trạng thái của cây bị va chạm trạng thái là 0,tức là nó sẽ không xuất hiện trên bản đồ nữa	
						
	// 				collided = true;	
	// 				break;	
	// 			}	
	// 		}	
	// 	}	
			
	// 	return collided;	
	// }
	
	//Tương tự như cái trên,cái này cũng kiểm tra va chạm của đạn với gạch cứng.Tuy nhiên,nếu có va chạm,gạch
	//cứng sẽ không biến mất như cây
	public boolean checkSolidCollision(int x, int y)
	{
		boolean collided = false;
		for(int i=0; i< solidBricksXPos.length;i++)
		{		
			if(new Rectangle(x, y, 10, 10).intersects(new Rectangle(solidBricksXPos[i], solidBricksYPos[i], 50, 50)))
			{		
				collided = true;
				break;
			}			
		}
		return collided;
	}
	//check cay chan tank	
	// public boolean checktreeCollision(int x, int y)	
	// {	
	// 	boolean collided = false;	
	// 	for(int i=0; i< treebricksXpod.length;i++)	
	// 	{			
	// 		if(new Rectangle(x, y, 50, 50).intersects(new Rectangle(treebricksXpod[i], treebricksYpod[i], 50, 50)))	
	// 		{			
	// 			collided = true;	
	// 			break;	
	// 		}				
	// 	}	
	// 	return collided;	
	// }	
	//check ban vao item	
	public boolean checkitemCollision(int x, int y)	
	{	
		boolean collided = false;//Biến này lưu trạng thái có va chạm không,nếu có set là true và trả về	
									//Không thì để là false	
		for(int i=0; i< itemON.length;i++)	
		{	
			if(itemON[i]==1)	
			{	
				if(new Rectangle(x,y, 50, 50).intersects(new Rectangle(itembricksXpod[i], itembricksYpod[i], 50, 50)))	
				{		
					//Nếu có va chạm,set cho trạng thái của item bị va chạm trạng thái là 0,tức là nó sẽ không xuất hiện trên bản đồ nữa	
					itemON[i] = 0;	
					collided = true;	
					break;	
				}	
			}	
		}	
			
		return collided;	
	} 
}
