import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
  //keep steps running after added button
  private Canvas canvas;
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
    canvas = new Canvas();
    //add restart button
    JButton re = new JButton("Restart");
    re.addActionListener(e -> canvas.stage.reset());
    JPanel pan1 = new JPanel(new BorderLayout());
    pan1.add(canvas, BorderLayout.CENTER);
    pan1.add(re, BorderLayout.SOUTH);
    this.setContentPane(pan1);
    this.pack();
    this.setVisible(true);
  }

  public void run() {
    //separate thread
    Thread stepThread = new Thread(() -> {
      while(true) {
        //canvas = (Canvas)getContentPane(); no longer needed
        if(!canvas.stage.isGameOver() && !canvas.stage.isWin()){
          canvas.stage.step();
        }
        try {
          Thread.sleep(300);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    stepThread.start();
    
    while(true) {
      repaint();
      try {
        //7ms for 144fps, 16ms for 60fps, 33ms for 30fps
        Thread.sleep(7);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
} 