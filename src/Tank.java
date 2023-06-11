import javax.swing.ImageIcon;
import java.awt.*;

public class Tank{

    //tọa độ của tank
    private int playerX;
    private int playerY;

    //máu của tank
    private int hp;

    //điểm của tank
    private int score;

    //hướng của tank
    private boolean player_up;
    private boolean player_down;
    private boolean player_left;
    private boolean player_right;

    //trạng thái của tank có đang bắn hay không, khi đang true không thể bắn tiếp, ngăn ngừa việc bắn như ak :))))
    private boolean player_shoot;

    //hình ảnh của tank
    private ImageIcon player_image;
    private int player1Y;

    //getter vs setter
    public int getPlayerX() {
        return this.playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return this.playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isPlayer_up() {
        return this.player_up;
    }

    public boolean getPlayer_up() {
        return this.player_up;
    }

    public void setPlayer_up(boolean player_up) {
        this.player_up = player_up;
    }

    public boolean isPlayer_down() {
        return this.player_down;
    }

    public boolean getPlayer_down() {
        return this.player_down;
    }

    public void setPlayer_down(boolean player_down) {
        this.player_down = player_down;
    }

    public boolean isPlayer_left() {
        return this.player_left;
    }

    public boolean getPlayer_left() {
        return this.player_left;
    }

    public void setPlayer_left(boolean player_left) {
        this.player_left = player_left;
    }

    public boolean isPlayer_right() {
        return this.player_right;
    }

    public boolean getPlayer_right() {
        return this.player_right;
    }

    public void setPlayer_right(boolean player_right) {
        this.player_right = player_right;
    }

    public boolean isPlayer_shoot() {
        return this.player_shoot;
    }

    public boolean getPlayer_shoot() {
        return this.player_shoot;
    }

    public void setPlayer_shoot(boolean player_shoot) {
        this.player_shoot = player_shoot;
    }

    public ImageIcon getPlayer_image() {
        return this.player_image;
    }

    public void setPlayer_image(ImageIcon player_image) {
        this.player_image = player_image;
    }

    
    //constructor
    public Tank(int playerX, int playerY, int hp, int score, boolean player_up, boolean player_down, boolean player_left, boolean player_right, boolean player_shoot) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.hp = hp;
        this.score = score;
        this.player_up = player_up;
        this.player_down = player_down;
        this.player_left = player_left;
        this.player_right = player_right;
        this.player_shoot = player_shoot;
    }
    
    //phương thức vẽ tank 4 hướng
    public void paintTank1(Graphics g){
       if(player_up)
            player_image = new ImageIcon("player1_tank_up.png");
        else if(player_down)
            player_image = new ImageIcon("player1_tank_down.png");
        else if(player_left)
            player_image = new ImageIcon("player1_tank_left.png");
        else
            player_image = new ImageIcon("player1_tank_right.png");


    }

    public void paintTank2(Graphics g){
        if(player_up)
             player_image = new ImageIcon("player2_tank_up.png");
         else if(player_down)
             player_image = new ImageIcon("player2_tank_down.png");
         else if(player_left)
             player_image = new ImageIcon("player2_tank_left.png");
         else
             player_image = new ImageIcon("player2_tank_right.png");
 
 
     }

    

}
