import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Stage {
  Grid grid;
  List<Enemy> enemies;
  List<Bomb> bombs;
  List<PowerUp> powerups;
  private Spawn spawner;
  private int stepCount;
  private static final int bombInterval = 10;
  private static final int powerupInterval = 30;
  private Click clicker;

  public Stage() {
    grid = new Grid();
    enemies = new ArrayList<Enemy>();
    bombs = new ArrayList<Bomb>();
    powerups = new ArrayList<PowerUp>();
    stepCount = 0;
    spawner = new Spawn(this,grid,enemies,bombs,powerups);
    clicker = new Click(grid, enemies, bombs, powerups);
    enemies.add(new Enemy(grid.cellAtColRow(0, 0).get()));
    enemies.add(new Enemy(grid.cellAtColRow(19, 19).get()));
    enemies.add(new Enemy(grid.cellAtColRow(0, 19).get()));
    enemies.add(new Enemy(grid.cellAtColRow(19, 0).get()));
  }

  public void paint(Graphics g, Point mouseLoc) {
    grid.paint(g, mouseLoc);
    for(Enemy e: enemies) {
      e.paint(g);
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
    //show game status
    g.setColor(Color.BLACK);
    g.drawString("Step: " + stepCount, 740, 10);
    g.drawString("Click range: " + clicker.getRange(), 740, 60);
    if(clicker.isGameOver()){
      g.setColor(Color.RED);
      g.drawString("<<<GAME OVER>>>", 400, 360);
    }
  }

  //step() every actor
  public void step() {
    if(clicker.isGameOver()){
      return;
    }
    stepCount++;
    for(Enemy e: enemies) {
      e.step();
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
    //remove expired items
    bombs.removeIf(b -> b.isExpired());
    powerups.removeIf(p -> p.isExpired());
  }

  //call click handler
  public void handleClick(Point p) {
    clicker.handleClick(p);
  }
  public boolean isGameOver(){
    return clicker.isGameOver();
  }
}
