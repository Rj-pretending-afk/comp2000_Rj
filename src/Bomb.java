import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

public class Bomb extends Actor {
  private int age; //how long bomb placed
  private static final int lifespan = 30; //bomb lifespan
  public Bomb(Cell inLoc) {
    loc = inLoc;
    age = 0;
    color = Color.GRAY;
    display = new ArrayList<Polygon>();
    update();
  }

  @Override
  public void step() {
    age++;
    update();
  }

  private void update(){
    display.clear();

    //first step
    Polygon l1 = new Polygon();
    l1.addPoint(loc.x + 5, loc.y + 5);
    l1.addPoint(loc.x + 10, loc.y + 5);
    l1.addPoint(loc.x + 30, loc.y + 25);
    l1.addPoint(loc.x + 30, loc.y + 30);
    l1.addPoint(loc.x + 25, loc.y + 30);
    l1.addPoint(loc.x + 5, loc.y + 10);
    display.add(l1);

    //second step
    if (age >= 3){
      Polygon l2 = new Polygon();
      l2.addPoint(loc.x + 30, loc.y + 5);
      l2.addPoint(loc.x + 30, loc.y + 10);
      l2.addPoint(loc.x + 10, loc.y + 30);
      l2.addPoint(loc.x + 5, loc.y + 30);
      l2.addPoint(loc.x + 5, loc.y + 25);
      l2.addPoint(loc.x + 25, loc.y + 5);
      display.add(l2);
    }
    if (age >= 6){
      color = Color.BLACK;
    }
  }
  public boolean isExpired(){
    return age >= lifespan;
  }
}
