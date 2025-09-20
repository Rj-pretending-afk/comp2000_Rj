import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

public class PowerUp extends Actor {
  private int age; //how long powerup placed
  private static final int lifespan = 50; //powerup lifespan
  public PowerUp(Cell inLoc) {
    loc = inLoc;
    age = 0;
    color = new Color(255, 255, 200); //light yellow
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
    Polygon l1 = new Polygon();
    l1.addPoint(loc.x + 20, loc.y + 5);
    l1.addPoint(loc.x + 20, loc.y + 10);
    l1.addPoint(loc.x + 10, loc.y + 30);
    l1.addPoint(loc.x + 5, loc.y + 30);
    l1.addPoint(loc.x + 5, loc.y + 25);
    l1.addPoint(loc.x + 15, loc.y + 5);
    display.add(l1);

    //second step
    if (age >= 2){
      Polygon l2 = new Polygon();
      l2.addPoint(loc.x + 15, loc.y + 5);
      l2.addPoint(loc.x + 20, loc.y + 5);
      l2.addPoint(loc.x + 30, loc.y + 25);
      l2.addPoint(loc.x + 30, loc.y + 30);
      l2.addPoint(loc.x + 25, loc.y + 30);
      l2.addPoint(loc.x + 15, loc.y + 10);
      display.add(l2);
    }
    if (age >= 3){
      color = Color.YELLOW;
    }
  }
  public boolean isExpired(){
    return age >= lifespan;
  }

  public boolean isReady(){
    return age >= 3;
  }
}
