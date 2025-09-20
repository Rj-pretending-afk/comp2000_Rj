import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Stage {
  Grid grid;
  List<Actor> actors;
  List<Bomb> bombs;
  private int stepCount;
  private static final int bombInterval = 10;
  private Random random;

  public Stage() {
    grid = new Grid();
    actors = new ArrayList<Actor>();
    bombs = new ArrayList<Bomb>();
    stepCount = 0;
    random = new Random();
    actors.add(new Enemy(grid.cellAtColRow(0, 0).get()));
    actors.add(new Dog(grid.cellAtColRow(0, 15).get()));  
  }

  public void paint(Graphics g, Point mouseLoc) {
    grid.paint(g, mouseLoc);
    for(Actor a: actors) {
      a.paint(g);
    }
    for (Bomb b: bombs) {
      b.paint(g);
    }

    Optional<Cell> underMouse = grid.cellAtPoint(mouseLoc);
    if(underMouse.isPresent()) {
      Cell hoverCell = underMouse.get();
      g.setColor(Color.DARK_GRAY);
      g.drawString(String.valueOf(hoverCell.col) + String.valueOf(hoverCell.row), 740, 30);
    }
  }

  //step() every actor
  public void step() {
    stepCount++;
    for(Actor a: actors) {
      a.step();
    }
    for(Bomb b: bombs) {
      b.step();
    }
    //when interval reached
    if(stepCount % bombInterval == 0) {
      bombSpawn();
    }
  }
  private void bombSpawn() {
    //random loc
    int col = random.nextInt(20);
    int row = random.nextInt(20);
    //new bomb
    bombs.add(new Bomb(grid.cellAtColRow(col, row).get()));
    //remove expired bombs
    bombs.removeIf(b -> b.isExpired());
  }
}
