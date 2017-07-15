package sample;

import javafx.scene.layout.StackPane;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by zfz on 17/2/2.
 */
class Judge extends Back{
    static Chess chess;

    public Judge(){
        new Info();
        roll.go();
    }



    /**
     * 返回当前回合
     */
    public static int whosTurn(){
        return turn;
    }


    /**
     * 改变回合
     */
    public static void setTurn(){
        turn = turn == 1 ? 2 : 1;
    }


    /**
     *获取from的信息
     */
    public static int getFrom (int i, int j){
        if(whosTurn() == board[i][j]) {
            fromI = i;
            fromJ = j;
            log.println("from color is " + board[i][j] + " number is " + number[i][j]);
        }
        return board[i][j];
    }


    /**
     *获取to的信息
     */
    public static int getTo(int i, int j){
        toI = i;
        toJ = j;
        log.println("to color is " + board[i][j] + " number is " + number[i][j]);
        return board[i][j];
    }


    /**
     * 判断是否可以移动 并且初始化from和to 交换turn
     */
    public static boolean move(){
        if(canMove()){
            log.println("from: (" + fromI + " " + fromJ + ")");
            log.println("to: (" + toI + " " + toJ + ")");
            log.println("success!");
            setTurn();
            Info.setInfo(getString());
            roll.go();
            return true;
        }
        return false;
    }


    /**
     *判断是否在本回合获取了正确的from和to点坐标
     */
    public static boolean canMove() {
        boolean conditionRed = (toI == fromI + 1 && (toJ == fromJ + 1 || toJ == fromJ)) || (toJ == fromJ + 1 && ((toI == fromI + 1 || toI == fromI)));
        boolean conditionBlue = (toI == fromI - 1 && (toJ == fromJ - 1 || toJ == fromJ)) || (toJ == fromJ - 1 && ((toI == fromI - 1 || toI == fromI)));
        boolean allRight = !(fromI == -1 || fromJ == -1 || toI == -1 || toJ == -1);

        if (allRight && !isFinished()) {
            if (conditionRed && whosTurn() == 1) {
                return true;
            }

            else if (conditionBlue && whosTurn() == 2) {
                return true;
            }

            else{
                log.print("error");
                fromI = -1;
                fromJ = -1;
                toI = -1;
                toJ = -1;
            }
        }
        return false;
    }

    /**
     * 判断是否结束
     */
    public static boolean isFinished(){
        if(board[4][4] == 1 || board[0][0] == 2){
            return true;
        }

        if(red == 0 || blue == 0){
            return true;
        }
        return false;
    }

    public static void setSp(Chess c){
        chess = c;
    }

    public static Chess getSp(){
        return chess;
    }


    public static int getFromI() {
        return fromI;
    }

    public static int getFromJ() {
        return fromJ;
    }

    public static int getToI() {
        return toI;
    }

    public static int getToJ() {
        return toJ;
    }

    /**
     * 获取每一步的信息组成字符串传递给显示区
     */
    public static String getString(){
        String turn;
        turn = whosTurn() == 1 ? "Blue" : "Red";
        return turn  + " (" + fromI + "," + fromJ + ") to " +  " (" + toI + "," + toJ + ")\n" + "=======\n";
    }
}
