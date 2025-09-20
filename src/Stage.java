import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Stage {
  Grid grid;
  List<Actor> actors;
  List<Bomb> bombs;
  List<PowerUp> powerups;
  private Spawn spawner;
  private int stepCount;
  private static final int bombInterval = 10;
  private static final int powerupInterval = 30;

  public Stage() {
    grid = new Grid();
    actors = new ArrayList<Actor>();
    bombs = new ArrayList<Bomb>();
    powerups = new ArrayList<PowerUp>();
    stepCount = 0;
    spawner = new Spawn(this,grid,actors,bombs,powerups);
    actors.add(new Enemy(grid.cellAtColRow(0, 0).get()));
  }

  public void paint(Graphics g, Point mouseLoc) {
    grid.paint(g, mouseLoc);
    for(Actor a: actors) {
      a.paint(g);
    }
    for (Bomb b: bombs) {
      b.paint(g);
    }
    for (PowerUp p: powerups) {
      p.paint(g);
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
    for(PowerUp p: powerups) {
      p.step();
    }
    //when interval reached
    if(stepCount % bombInterval == 0) {
      spawner.spawnBomb();
    }
    if(stepCount % powerupInterval == 0) {
      spawner.spawnPowerUp();
    }
  }
}
