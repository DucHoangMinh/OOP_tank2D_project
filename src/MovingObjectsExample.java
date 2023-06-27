import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MovingObjectsExample extends JFrame {
    private JPanel canvas;
    private MovingObject tank1;
    private MovingObject tank2;
    private Control control;
    private Control1 control1;

    public MovingObjectsExample() {
        setTitle("Moving Objects Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        canvas = new JPanel();
        getContentPane().add(canvas);
        setVisible(true);

        tank1 = new MovingObject(100, 100, Color.RED, 3);
        tank2 = new MovingObject(200, 200, Color.BLUE, 2);

        control = new Control();
        addKeyListener(control);
        control1 = new Control1();
        addKeyListener(control1);
        setFocusable(true);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateObjects();
                repaint();
            }
        });
        timer.start();
    }

    private void updateObjects() {
        if (control.isTank1MovingUp()) {
            tank1.moveUp();
        } else if (control.isTank1MovingDown()) {
            tank1.moveDown();
        }

        if (control1.isTank2MovingUp()) {
            tank2.moveUp();
        } else if (control1.isTank2MovingDown()) {
            tank2.moveDown();
        }
    }

    private class Control implements KeyListener {
        private boolean tank1MovingUp;
        private boolean tank1MovingDown;

        public boolean isTank1MovingUp() {
            return tank1MovingUp;
        }

        public boolean isTank1MovingDown() {
            return tank1MovingDown;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_W) {
                tank1MovingUp = true;
            } else if (keyCode == KeyEvent.VK_S) {
                tank1MovingDown = true;
            } 
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_W) {
                tank1MovingUp = false;
            } else if (keyCode == KeyEvent.VK_S) {
                tank1MovingDown = false;
            } 
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }


    private class Control1 implements KeyListener {
        private boolean tank2MovingUp;
        private boolean tank2MovingDown;

        public boolean isTank2MovingUp() {
            return tank2MovingUp;
        }

        public boolean isTank2MovingDown() {
            return tank2MovingDown;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_UP) {
                tank2MovingUp = true;
            } else if (keyCode == KeyEvent.VK_DOWN) {
                tank2MovingDown = true;
            } 
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_UP) {
                tank2MovingUp = false;
            } else if (keyCode == KeyEvent.VK_DOWN) {
                tank2MovingDown = false;
            } 
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
    private class MovingObject {
        private int x;
        private int y;
        private Color color;
        private int speed;

        public MovingObject(int x, int y, Color color, int speed) {
            this.x = x;
            this.y = y;
            this.color = color;
            this.speed = speed;
        }
        public void moveUp() {
            y -= speed;
        }

        public void moveDown() {
            y += speed;
        }

        public void draw(Graphics2D g2d) {
            g2d.setColor(color);
            g2d.fillRect(x, y, 50, 50);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        tank1.draw(g2d);
        tank2.draw(g2d);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MovingObjectsExample();
            }
        });
    }
}