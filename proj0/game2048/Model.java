package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True off game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        //移动所有tilt
        //计算分值
        //检查是否change
        //思路:先解决向上合并.
        //move(col,row,tile)把tilt放到col,row,返回是不是合并了,
        //那就先每一列检查能否向上合并吧
        //题目建议从上往下遍历,这是对的
        //合成规则:1.相同合并 2.新生成的不会合并 3.同方向优先合并
        //0
        //0
        //0
        //2  j=0
        //先检查是不是空的,是的话标记,下面有就会移上来
        //branch1:空了:不用管,直接进入下一个,因为不需要特别标出来
        //branch2:没空,开始向下找,下一个立即不是就不动,其他情况(22xx,2xx2)要合成
        //或许要写一个方法来排好序,然后直接在这里面塞进去.
        //上面的只要管往上面挤,下面的自己会分辨是不是null或者要不要merge
        board.setViewingPerspective(side);
        int size=this.board.size();
        boolean[][] merged = new boolean[size][size];
        int[] array = new int[size];
        for (int i = 0; i <size; i++) {//列
            for (int j = size-1; j >=0; j--) {//行
                boolean isMoved = false;
                int m=-1;
                if(board.tile(i,j)==null) {//这个是空位就不管
                    continue;
                } else if (board.tile(i,j)!=null){//i,j如果不是空位,就先考虑向上移 条件:一路到底
                    if(j==size-1){ //边界条件!!!最上面就不用管它
                        continue;
                    }   for (m=j+1; m<=size-1;m++){//对于i,j 向上检索,碰到!=null就看合不合并,否则就移到之下的null
                        if(board.tile(i,m)==null){//null继续贪心,看看还有没有null
                            continue;
                        }
                        else if(merged[i][m]==false&&board.tile(i,m).value()==board.tile(i,j).value()){
                            board.move(i,m,board.tile(i,j));//相同!!!合并了
                            score += board.tile(i,m).value();//这里是:没合并,并且相等
                            merged[i][m]=true;
                            isMoved=true;
                            changed=true;
                            break;
                        }
                        else if(merged[i][m]==true||board.tile(i,m).value()!=board.tile(i,j).value()){
                            break;//从此时的m向下检索  //这里逻辑是值不相等,但是少了相等,但合并了的情况
                        }
                        }
                    if(!isMoved){
                        if(m-1==size-1){
                            board.move(i,m-1,board.tile(i,j));
                            isMoved =true;
                            changed=true;
                        }
                        else
                        for (int k = m-1; k >j ; k--) {//移到m以下第一个null
                            if(board.tile(i,k)==null){
                                board.move(i,k,board.tile(i,j));
                                isMoved =true;
                                changed=true;
                                break;
                            }
                        }
                    }
                    }

                }
        }
        board.setViewingPerspective(Side.NORTH);
        checkGameOver();

        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * *///board上面只要有一个空的就返回true
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.//down
        //先得到b的size吧
        int size=b.size();
        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {
                if(b.tile(i,j)==null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.down
        int size=b.size();
        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {
                if(b.tile(i,j)!=null&&b.tile(i,j).value()==MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        int size=b.size();
        if(emptySpaceExists(b))//the first way
        {
            return true;
        }
        //if(maxTileExists(b)){
          //  return  true;
        //}有没有这个到底有没有关系...
        //遍历检查上下左右有没有一样的,有则可以移动,return true  并且此时没有null了
        //边界问题?往右上角找:i找i+1,j找j+1 等于size-1的时候不找
        for(int i=0;i<size;i++) {
            for (int j = 0; j < size; j++) {//内循环,开始找
                if(j!=size-1) {
                    if (b.tile(i, j).value() ==b.tile(i,j+1).value()) {//向上找,前提是没到最后一行
                        return true;
                    }
                }
                if(i!=size-1) {
                    if (b.tile(i, j).value() ==b.tile(i+1, j).value()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
