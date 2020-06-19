package snake_2d;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
 
public class Main extends JFrame {
 
    public Main() {
        createAndShowGUI();
    }
 
    private void createAndShowGUI() {
 
        GameDrawer gameDrawer = new GameDrawer();
 
        add(gameDrawer);
 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
 
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
 
}