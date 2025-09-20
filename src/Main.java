import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
    public static void main(String[] args) throws Exception {
      Main window = new Main();
      window.run();
    }

    class Canvas extends JPanel {
      Stage stage = new Stage();
      public Canvas() {
        setPreferredSize(new Dimension(1024, 720));
        //change mouse behavior
        addMouseListener(new MouseAdapter(){
          @Override
          public void mouseClicked(MouseEvent e){stage.handleClick(e.getPoint());}
        });
      }

      @Override
      public void paint(Graphics g) {
        stage.paint(g, getMousePosition());
      }
    }

    private Main() {
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Canvas canvas = new Canvas();
      this.setContentPane(canvas);
      this.pack();
      this.setVisible(true);
    }

    public void run() {
      //separate thread
      Thread stepThread = new Thread(() -> {
        while(true) {
          Canvas canvas = (Canvas)getContentPane();
          if(!canvas.stage.isGameOver()){
            canvas.stage.step();
          }
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      });
      stepThread.start();
      
      while(true) {
        repaint();
        try {
          Thread.sleep(35);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  } 