import javax.swing.*;

public class Snake extends JFrame {

    public Snake(){
        initScreen();
    }

    public void initScreen(){
        add(new GamePanel());
        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args){
        JFrame frame = new Snake();
        frame.setVisible(true);
    }

}
