import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private final int SCREEN_WIDTH = 300;
    private final int SCREEN_HEIGHT = 300;

    private final int SIZE = 900;
    private final int PART_SIZE = 10;

    private int[] x_pos = new int[SIZE];
    private int[] y_pos = new int[SIZE];

    /**image variables*/
    private Image head;
    private Image body;
    private Image fruit;

    private int snake_part;
    private boolean stillRunning = true;   //game still on

    private int DEALAY = 140;

    public boolean move_right = true;
    public boolean move_left = false;
    public boolean move_up = false;
    public boolean move_down = false;

    /*variables for apple x and y position*/
    int fruit_x_pos;
    int fruit_y_pos;


    public GamePanel() {
        /*setup window interface*/
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        this.addKeyListener(this);

        initGamePanel();

        Timer tim = new Timer(DEALAY, this);
        tim.start();
    }

    /**initialize game*/
    public void initGamePanel() {
        loadIcons();
        startSnake();   //place snake on specified position on the field
        place_fruit();  // randomly place fruit on the field

    }

    /**loads in images on window*/
    public void loadIcons() {

        //10x10 images
        ImageIcon ref_head = new ImageIcon("img/snakehead.png");
        head = ref_head.getImage();

        ImageIcon ref_fruit = new ImageIcon("img/apple.png");
        fruit = ref_fruit.getImage();

        ImageIcon ref_body = new ImageIcon("img/snakepart.png");
        body = ref_body.getImage();
    }

    /**setup snake position on the field*/
    public void startSnake() {

        //snake body parts
        snake_part = 5;

        for (int i = 0; i < snake_part; i++) {
            x_pos[i] = 50 - i * 10;
            y_pos[i] = 50;
        }
    }

    /**display on window*/
    public void displayIcons(Graphics g) {

        //game is still running
        if (stillRunning) {
            for (int s = 0; s < snake_part; s++) {
                if (s == 0) {
                    g.drawImage(head, x_pos[s], y_pos[s], this);
                } else {
                    g.drawImage(body, x_pos[s], y_pos[s], this);
                    g.drawImage(fruit, fruit_x_pos, fruit_y_pos, this);
                }
            }
        }
        //game is over
        else{
            endGame(g);
        }
    }

    /**randomly place fruit on the field*/
    public void place_fruit(){
        int x_max = (int) (Math.random() * ((SCREEN_WIDTH/PART_SIZE)-1));
        fruit_x_pos = x_max*PART_SIZE;
        int y_max = (int) (Math.random() * ((SCREEN_HEIGHT/PART_SIZE)-1));
        fruit_y_pos = y_max*PART_SIZE;
    }

    /**method to move the snake*/
    public void moveSnake() {
        for (int z = snake_part; z > 0; z--) {
            //reverse the list e.g..[50,40,30,20,10]-->[10,20,30,40,50]
            x_pos[z] = x_pos[(z - 1)];
            y_pos[z] = y_pos[(z - 1)];
        }
        /*activate when key is pressed*/
        if (move_right) {
            x_pos[0] += PART_SIZE;
        }
        if (move_left) {
            x_pos[0] -= PART_SIZE;
        }
        if (move_up) {
            y_pos[0] -= PART_SIZE;
        }
        if (move_down) {
            y_pos[0] += PART_SIZE;
        }
    }

    /**detect collision and end game on event of collision*/
    public void findCollision(){

        /*snake collising on itself*/
        for(int z=snake_part; z>0;z--){
            if(x_pos[z]==x_pos[0] && y_pos[z]==y_pos[0]){
                stillRunning = false;break;
            }
        }

        /*snake colliding with left or right walls*/
        for(int x:x_pos){
            if(x<0){
                stillRunning = false;
            }
            else if(x>(SCREEN_WIDTH)){
                stillRunning = false;
            }
        }
        /*snake colliding with top or bottom walls*/
        for(int y:y_pos){
            if((y)<0){
                stillRunning = false;
            }else if(y>(SCREEN_HEIGHT)){
                stillRunning = false;
            }
        }
    }

    /**detect fruit icon, randomly place fruit and increase snake in size*/
    public void find_fruit(){
        if(x_pos[0] == fruit_x_pos && y_pos[0] == fruit_y_pos){
            //increase size of snake
            snake_part++;

            //randomly place fruit
            place_fruit();
        }
    }


    /** end for the game display message on the screen*/
    public void endGame(Graphics g){
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.GREEN);
        g.setFont(small);
        g.drawString(msg, (SCREEN_WIDTH - metr.stringWidth(msg)) / 2, SCREEN_HEIGHT / 2);


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        displayIcons(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(stillRunning){
            findCollision();
            find_fruit();
            moveSnake();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if((key==KeyEvent.VK_RIGHT) && (!move_left)){
            move_right = true;
            move_up = false;
            move_down = false;

        }
        if((key==KeyEvent.VK_LEFT) && !move_right){
            move_left = true;
            move_up = false;
            move_down = false;
        }

        if((key==KeyEvent.VK_UP) && !move_down){
            move_up = true;
            move_right = false;
            move_left = false;

        }
        if((key==KeyEvent.VK_DOWN) && !move_up){
            move_down = true;
            move_right = false;
            move_left = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}
