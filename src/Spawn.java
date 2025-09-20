import java.util.List;
import java.util.Random;

public class Spawn{
    private Random random;
    private Stage stage;
    private Grid grid;
    private List<Enemy> enemies;
    private List<Bomb> bombs;
    private List<PowerUp> powerups;

    public Spawn(Stage s,Grid g,List<Enemy> e,List<Bomb> b,List<PowerUp> p){
        stage = s;
        grid = g;
        enemies = e;
        bombs = b;
        powerups = p;
        random = new Random();
    }

    public void spawnBomb(){
        int col = random.nextInt(20);
        int row = random.nextInt(20);
        Cell target = grid.cellAtColRow(col, row).get();
        boolean Used = false;
        for(Enemy e : enemies){
            if(e.loc.col == target.col && e.loc.row == target.row){
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
        for(Enemy e : enemies){
            if(e.loc.col == target.col && e.loc.row == target.row){
                Used = true;
                break;
            }
        }
        if(!Used){
            powerups.add(new PowerUp(target));
        }
    }
}

