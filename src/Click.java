import java.awt.Point;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Click {
    private Grid grid;
    private List<Enemy> enemies;
    private List<Bomb> bombs;
    private List<PowerUp> powerups;
    private int clickRange;
    private boolean gameOver;

    public Click(Grid g, List<Enemy> e, List<Bomb> b, List<PowerUp> p){
        grid = g;
        enemies = e;
        bombs = b;
        powerups = p;
        clickRange = 1;
        gameOver = false;
    }
    //handle click
    public void handleClick(Point clickPoint){
        if(gameOver){
            return;
        }
        //find cell clicked
        Optional<Cell> target = grid.cellAtPoint(clickPoint);
        if(!target.isPresent()){
            return;
        }
        Cell center = target.get();
        int centerCol = center.col - 'A';
        int centerRow = center.row;

        //find cell in range base on clicked cell & range
        for(int col = centerCol - (clickRange - 1); col <= centerCol + (clickRange - 1); col++){
            for(int row = centerRow - (clickRange - 1); row <= centerRow + (clickRange - 1); row++){
                if(col >= 0 && col < 20 && row >= 0 && row < 20){
                    cellCheck(col,row);
                }
            }
        }
    }
    private void cellCheck(int col, int row){
        char colChar = (char)(col + 'A');

        //check bomb
        Iterator<Bomb> bombIt= bombs.iterator();
        while(bombIt.hasNext()){
            Bomb b = bombIt.next();
            if(b.loc.col == colChar && b.loc.row == row){
                gameOver = true;
                return;
            }
        }

        //check powerup
        Iterator<PowerUp> powerIt= powerups.iterator();
        while(powerIt.hasNext()){
            PowerUp p = powerIt.next();
            if(p.loc.col == colChar && p.loc.row == row){
                powerIt.remove();
                clickRange++;
                return;
            }
        }
        //check enemy
        Iterator<Enemy> enemyIt= enemies.iterator();
        while(enemyIt.hasNext()){
            Enemy e = enemyIt.next();
            if(e.loc.col == colChar && e.loc.row == row){
                gameOver = true;
                return;
            }
        }
    }
    public int getRange(){
        return clickRange;
    }
    public boolean isGameOver(){
        return gameOver;
    }
    public void reset(){
        clickRange = 1;
        gameOver = false;
    }
}
