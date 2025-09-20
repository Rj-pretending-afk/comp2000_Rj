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
    enemies.add(new Enemy(grid.cellAtColRow(3, 3).get()));
    enemies.add(new Enemy(grid.cellAtColRow(17, 17).get()));
    enemies.add(new Enemy(grid.cellAtColRow(3, 17).get()));
    enemies.add(new Enemy(grid.cellAtColRow(17, 3).get()));
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
      g.drawString(String.valueOf(hoverCell.col) + String.valueOf(hoverCell.row), 740, 20);
    }
    //show game status
    g.setColor(Color.BLACK);
    g.setFont(g.getFont().deriveFont(18f));
    g.drawString("Powerup stay 10 steps, stackable.",740, 50);
    g.drawString("Game over if click on bomb.", 740, 75);
    g.drawString("Kill all enemies for the win.", 740, 100);
    if(clicker.isGameOver()){
      g.setColor(Color.RED);
      //set font just for game over
      g.setFont(g.getFont().deriveFont(65f));
      g.drawString("<<<GAME OVER>>>", 300, 380);
    }
    if(clicker.isWin()){
      g.setColor(Color.GREEN);
      //set font just for win
      g.setFont(g.getFont().deriveFont(65f));
      g.drawString("<<<YOU WIN>>>", 300, 380);
    }
  }

  //step() every actor
  public void step() {
    if(clicker.isGameOver() || clicker.isWin()){
      return;
    }
    stepCount++;
    clicker.step();
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
  public boolean isWin(){
    return clicker.isWin();
  }

  public void reset(){
    grid = new Grid();
    enemies.clear();
    bombs.clear();
    powerups.clear();
    stepCount = 0;
    clicker.reset();
    spawner = new Spawn(this,grid,enemies,bombs,powerups);
    enemies.add(new Enemy(grid.cellAtColRow(3, 3).get()));
    enemies.add(new Enemy(grid.cellAtColRow(17, 17).get()));
    enemies.add(new Enemy(grid.cellAtColRow(3, 17).get()));
    enemies.add(new Enemy(grid.cellAtColRow(17, 3).get()));
    enemies.add(new Enemy(grid.cellAtColRow(10, 10).get()));
  }
}