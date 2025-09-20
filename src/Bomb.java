import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

public class Bomb extends Actor {
  public Bomb(Cell inLoc) {
    loc = inLoc;
    color = Color.BLACK;
    display = new ArrayList<Polygon>();
    
    Polygon l1 = new Polygon();
    l1.addPoint(loc.x + 5, loc.y + 5);
    l1.addPoint(loc.x + 10, loc.y + 5);
    l1.addPoint(loc.x + 30, loc.y + 25);
    l1.addPoint(loc.x + 30, loc.y + 30);
    l1.addPoint(loc.x + 25, loc.y + 30);
    l1.addPoint(loc.x + 5, loc.y + 10);
    
    Polygon l2 = new Polygon();
    l2.addPoint(loc.x + 30, loc.y + 5);
    l2.addPoint(loc.x + 30, loc.y + 10);
    l2.addPoint(loc.x + 10, loc.y + 30);
    l2.addPoint(loc.x + 5, loc.y + 30);
    l2.addPoint(loc.x + 5, loc.y + 25);
    l2.addPoint(loc.x + 25, loc.y + 5);
    
    display.add(l1);
    display.add(l2);
  }
}
