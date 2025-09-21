import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

public abstract class Actor implements Steppable{
  Color color;
  Cell loc;
  List<Polygon> display;

  public void paint(Graphics g) {
    //copied list to avoid ConcurrentModificationException
    List<Polygon> displayCopy = new ArrayList<>(display);
    for(Polygon p: displayCopy) {
      g.setColor(color);
      g.fillPolygon(p);
      g.setColor(Color.GRAY);
      g.drawPolygon(p);
    }
  }

  @Override
  public void step() {
  // nothing should be happening default
  }
}
