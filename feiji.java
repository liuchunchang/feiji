package club;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class feiji<first> extends Frame implements KeyListener, Runnable {
    private Image iBuffer;
    private Graphics gBuffer;

    private int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    Image feiji = Toolkit.getDefaultToolkit().getImage("./java/src/image/hero.png");
    Image enemy = Toolkit.getDefaultToolkit().getImage("./java/src/image/enemy.png");
    Image feiji_bg1 = Toolkit.getDefaultToolkit().getImage("./java/src/image/feiji_bg.jpg");
    Image[] explosions;

    Image feiji_bg2 = feiji_bg1;
    Image bullet = Toolkit.getDefaultToolkit().getImage("./java/src/image/bullet.png");
    Image bullets[] = {bullet, bullet, bullet, bullet, bullet, bullet, bullet, bullet, bullet, bullet};
    int[] zidan_x = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] zidan_y = {-40, -40, -40, -40, -40, -40, -40, -40, -40, -40};
    int[] zidan_roll_Y = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    double feiji_x = 190, feiji_y = 600;
    int enemy_x = 150, enemy_y = 40;
    int bg_x = 410;
    int bg_y = 700;
    int roll_Y = 0;
    int baoza_x, baoza_y;
    int enemy_boolen = 2;
    Boolean zidan[] = {false, false, false, false, false, false, false, false, false, false};
    int bullet_shu = 0;
    int run = 0;
    int yinle = 0;
    int baozashu = 0;

    feiji() {
        setResizable(false);
        setVisible(true);
        setBounds((width - 410) / 2, (height - 700) / 2, 410, 700);
        addKeyListener(this);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.out.println("退出");
                System.exit(0);
            }
        });
        explosions = new Image[]{Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion1.png"),
                Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion2.png"), Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion3.png"),
                Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion4.png"),
                Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion5.png"), Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion6.png"),
                Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion7.png"), Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion8.png"),
                Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion9.png"), Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion10.png"),
                Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion11.png"), Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion12.png"),
                Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion13.png"), Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion14.png"),
                Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion15.png"), Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion16.png"),
                Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion17.png"), Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion18.png"),
                Toolkit.getDefaultToolkit().getImage("./java/src/image/explosion19.png")};
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(feiji_bg1, 0, roll_Y, bg_x, bg_y, this);
        g.drawImage(feiji_bg2, 0, -bg_y + roll_Y, bg_x, bg_y, this);
        g.drawImage(feiji, (int) feiji_x, (int) feiji_y, 50, 50, this);
        g.drawImage(enemy, enemy_x, enemy_y, this);
        if (baozashu < 18) {
            baozashu++;
            g.drawImage(explosions[baozashu], baoza_x, baoza_y, 100, 100, this);
        }
        for (int i = 0; i < 10; i++) {
            if ((zidan_x[i] > enemy_x) && (zidan_x[i] < (enemy_x + 120)) && ((zidan_y[i] - zidan_roll_Y[i]) > enemy_y) && ((zidan_y[i] - zidan_roll_Y[i]) < (enemy_y + 60))) {
                baoza_x = zidan_x[i] - 30;
                baoza_y = zidan_y[i] - zidan_roll_Y[i] - 20;
                zidan_x[i] = -100;
                zidan_y[i] = -100;
                baozashu = 0;
                yinle++;
                System.out.println(yinle);
                System.out.println("(" + enemy_x + "," + enemy_y + ")");
            }
        }

        for (int i = 0; i < 10; i++) {
            if (zidan_roll_Y[i] < 2000 && zidan_x[i] != -100 && zidan_y[i] != -100) {
                g.drawImage(bullets[i], zidan_x[i], zidan_y[i] - zidan_roll_Y[i], 20, 30, this);
                zidan_roll_Y[i] = zidan_roll_Y[i] + 5;
            }
        }

        if (run > 30) {
            if (enemy_x > 320 || enemy_x < -30) {
                enemy_boolen = -enemy_boolen;
            }
            enemy_x = enemy_x + enemy_boolen;
            if (roll_Y < bg_y) {
                roll_Y = roll_Y + 3;
            } else if (roll_Y >= bg_y) {
                roll_Y = 0;
            }
        }

        for (int i = 0; i < 10; i++) {
            if (zidan[i]) {
                zidan_x[i] = (int) feiji_x + 17;
                zidan_y[i] = (int) feiji_y;
                zidan_roll_Y[i] = 0;
                zidan[i] = false;
            }
        }
        run++;

    }

    public void update(Graphics scr) {
//        System.out.println("方法使用成功");
        if (iBuffer == null) {
            iBuffer = createImage(this.getSize().width, this.getSize().height);
            gBuffer = iBuffer.getGraphics();
        }
        gBuffer.setColor(getBackground());
        gBuffer.fillRect(0, 0, this.getSize().width, this.getSize().height);
        paint(gBuffer);
        scr.drawImage(iBuffer, 0, 0, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //null
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String keyText = KeyEvent.getKeyText(e.getKeyCode());
//        System.out.println(keyText);
        if ((keyText.equals("向下箭头")) || (keyText.equals("S"))) {
            if (feiji_y < 640) {
                feiji_y = feiji_y + 5;
            }
        } else if ((keyText.equals("向上箭头")) || (keyText.equals("W"))) {
            if (feiji_y > 30) {
                feiji_y = feiji_y - 5;
            }
        } else if ((keyText.equals("向左箭头")) || (keyText.equals("A"))) {
            if (feiji_x > 5) {
                feiji_x = feiji_x - 5;
            }

        } else if ((keyText.equals("向右箭头")) || (keyText.equals("D"))) {
            if (feiji_x < 350) {
                feiji_x = feiji_x + 5;
            }
        }
        if (keyText.equals("空格")) {
            if (bullet_shu == 10) {
                bullet_shu = 0;
            }
            zidan[bullet_shu] = true;
            bullet_shu++;


        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //null
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (yinle >= 10) {
                break;
            }
            repaint();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JLabel label = new JLabel("恭喜你，你打赢了飞机");
        label.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        JOptionPane.showMessageDialog(null, label, "胜利", JOptionPane.PLAIN_MESSAGE);

    }

    public static void main(String[] args) {
        // write your code here
        feiji feiji = new feiji();
        Thread t1 = new Thread(feiji);
        t1.start();
    }
}
