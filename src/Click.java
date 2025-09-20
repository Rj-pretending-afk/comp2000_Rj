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
    private boolean gameWin;
    //setup boost duration steps
    private int boostSteps = 0;
    private static final int duration = 10;
    public Click(Grid g, List<Enemy> e, List<Bomb> b, List<PowerUp> p){
        grid = g;
        enemies = e;
        bombs = b;
        powerups = p;
        clickRange = 1;
        gameOver = false;
        gameWin = false;
    }
    //handle click
    public void handleClick(Point clickPoint){
        if(gameOver || gameWin){
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
        //logic update, check bomb in range first
        for (int col = centerCol - (clickRange - 1); col <= centerCol + (clickRange - 1); col++) {
            for (int row = centerRow - (clickRange - 1); row <= centerRow + (clickRange - 1); row++) {
                if (col >= 0 && col < 20 && row >= 0 && row < 20) {
                    char colChar = (char)(col + 'A');
                    Iterator<Bomb> bombIt= bombs.iterator();
                    while(bombIt.hasNext()){
                        Bomb b = bombIt.next();
                        if(b.loc.col == colChar && b.loc.row == row && b.isReady()){
                            gameOver = true;
                            break;
                        }   
                    }
                }
            }
        }
        //check enemy 2nd
        for (int col = centerCol - (clickRange - 1); col <= centerCol + (clickRange - 1); col++) {
            for (int row = centerRow - (clickRange - 1); row <= centerRow + (clickRange - 1); row++) {
                if (col >= 0 && col < 20 && row >= 0 && row < 20) {
                    char colChar = (char)(col + 'A');
                    Iterator<Enemy> enemyIt= enemies.iterator();
                    while(enemyIt.hasNext()){
                        Enemy e = enemyIt.next();
                        if(e.loc.col == colChar && e.loc.row == row){
                            enemyIt.remove();
                            //win if all enemies removed
                            if (enemies.isEmpty()) {
                                gameWin = true;
                            }
                            return;
                        }   
                    }
                }
            }
        }
        //check powerup last
        for (int col = centerCol - (clickRange - 1); col <= centerCol + (clickRange - 1); col++) {
            for (int row = centerRow - (clickRange - 1); row <= centerRow + (clickRange - 1); row++) {
                if (col >= 0 && col < 20 && row >= 0 && row < 20) {
                    char colChar = (char)(col + 'A');
                    Iterator<PowerUp> powerIt= powerups.iterator();
                    while(powerIt.hasNext()){
                        PowerUp p = powerIt.next();
                        if(p.loc.col == colChar && p.loc.row == row && p.isReady()){
                            powerIt.remove();
                            clickRange++;
                            boostSteps = duration; //start timer
                            return;
                        }   
                    }
                }
            }
        }
    }
    public int getRange(){
        return clickRange;
    }
    public boolean isGameOver(){
        return gameOver;
    }
    public boolean isWin(){
        return gameWin;
    }
    public void reset(){
        clickRange = 1;
        gameOver = false;
        gameWin = false;
        boostSteps = 0;
    }
    public void step(){
        if(boostSteps > 0){
            boostSteps--;
            if(boostSteps == 0 && clickRange > 1){
                clickRange--;
            }
        }
    }
}
