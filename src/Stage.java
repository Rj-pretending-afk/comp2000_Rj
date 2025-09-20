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
  private static final int bombInterval = 3;
  private static final int powerupInterval = 10;
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
    enemies.add(new Enemy(grid.cellAtColRow(10, 10).get()));
  }

  public void paint(Graphics g, Point mouseLoc) {
    grid.paint(g, mouseLoc);
    //do a list copy to fix the thread conflict
    List<Enemy> eCopy = new ArrayList<>(enemies);
    List<Bomb> bCopy = new ArrayList<>(bombs);
    List<PowerUp> pCopy = new ArrayList<>(powerups);
    for(Enemy e: eCopy) {
      e.paint(g);
    }
    for (Bomb b: bCopy) {
      b.paint(g);
    }
    for (PowerUp p: pCopy) {
      p.paint(g);
    }

    Optional<Cell> underMouse = grid.cellAtPoint(mouseLoc);
    if(underMouse.isPresent()) {
      Cell hoverCell = underMouse.get();
      g.setColor(new Color(64, 64, 64, 128));
      //make the hovercell scale with clickRange
      int centerCol = hoverCell.col - 'A';
      int centerRow = hoverCell.row;
      int range = clicker.getRange();
      for(int col = centerCol - (range - 1); col <= centerCol + (range - 1); col++) {
        for(int row = centerRow - (range - 1); row <= centerRow + (range - 1); row++) {
          if(col >= 0 && col < 20 && row >= 0 && row < 20) {
            Cell c = grid.cellAtColRow(col, row).get();
            g.fillRect(c.x, c.y, c.width, c.height);
            }
        }
      }
      g.drawString(String.valueOf(hoverCell.col) + String.valueOf(hoverCell.row), 740, 30);
    }
    //show game status
    g.setColor(Color.BLACK);
    g.drawString("Step: " + stepCount, 740, 10);
    g.drawString("Click range: " + clicker.getRange(), 740, 60);
    if(clicker.isGameOver()){
      g.setColor(Color.RED);
      //set font just for game over
      g.setFont(g.getFont().deriveFont(56f));
      g.drawString("<<<GAME OVER>>>", 300, 380);
      g.setFont(g.getFont().deriveFont(12f));
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
