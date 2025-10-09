import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Actor {
  private Random random = new Random();
  public Enemy(Cell inLoc) {
    loc = inLoc;
    color = Color.RED;
    display = new ArrayList<Polygon>();
    Polygon body = new Polygon();
    body.addPoint(loc.x + 8, loc.y + 7);
    body.addPoint(loc.x + 27, loc.y + 7);
    body.addPoint(loc.x + 27, loc.y + 25);
    body.addPoint(loc.x + 8, loc.y + 25);
    display.add(body);
  }

  @Override
  public void step() {
    //random direction
    int dir = random.nextInt(4);
    int newCol = loc.col - 'A';
    int newRow = loc.row;
    switch(dir) {
      case 0: newRow -= 4; break; // up
      case 1: newRow += 4; break; // down
      case 2: newCol -= 4; break; // left
      case 3: newCol += 4; break; // right
    }
    //check area
    if(newCol >= 0 && newCol < 20 && newRow >= 0 && newRow < 20) {
      //calc new loc
      Cell newLoc = new Cell((char)(newCol + 'A'), newRow, 
                            10 + Cell.size * newCol, 
                            10 + Cell.size * newRow);
      
      //update anchor
      int dx = newLoc.x - loc.x;
      int dy = newLoc.y - loc.y;
      for(Polygon p : display) {
        p.translate(dx, dy);
      }

      //update loc
      loc = newLoc;
    }
  }
}
