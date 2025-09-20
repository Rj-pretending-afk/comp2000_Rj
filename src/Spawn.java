import java.util.List;
import java.util.Random;

public class Spawn{
    private Random random;
    private Stage stage;
    private Grid grid;
    private List<Actor> actors;
    private List<Bomb> bombs;
    private List<PowerUp> powerups;

    public Spawn(Stage s,Grid g,List<Actor> a,List<Bomb> b,List<PowerUp> p){
        stage = s;
        grid = g;
        actors = a;
        bombs = b;
        powerups = p;
        random = new Random();
    }

    public void spawnBomb(){
        int col = random.nextInt(20);
        int row = random.nextInt(20);
        Cell target = grid.cellAtColRow(col, row).get();
        boolean Used = false;
        for(Actor a : actors){
            if(a.loc.col == target.col && a.loc.row == target.row){
                Used = true;
                break;
            }
        }
        if(!Used){
            bombs.add(new Bomb(target));
        }
    }
    public void spawnPowerUp(){
        int col = random.nextInt(20);
        int row = random.nextInt(20);
        Cell target = grid.cellAtColRow(col, row).get();
        boolean Used = false;
        for(Actor a : actors){
            if(a.loc.col == target.col && a.loc.row == target.row){
                Used = true;
                break;
            }
        }
        if(!Used){
            powerups.add(new PowerUp(target));
        }
    }
}

