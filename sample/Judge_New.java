package sample;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Created by zfz on 2017/7/8.
 */
public class Judge_New {
    private static Chess_New fromChess ;
    private static Chess_New toChess;
    private static StackPane fromPane;
    private static StackPane toPane;
    //red 0  blue 1
    private static int turn = 0;
    private static int dice;
    private static Back_new back;

    public Judge_New(Back_new back){
        Judge_New.back = back;
    }

    public Chess_New getFromChess() {
        return fromChess;
    }

    public static void setFromChess(Chess_New fromChess) {
        Judge_New.fromChess = fromChess;
    }

    public Chess_New getToChess() {
        return toChess;
    }

    public static void setToChess(Chess_New toChess) {
        Judge_New.toChess = toChess;
    }

    public static StackPane getFromPane() {
        return fromPane;
    }

    public static void setFromPane(StackPane fromPane) {
        Judge_New.fromPane = fromPane;
    }

    public static StackPane getToPane() {
        return toPane;
    }

    public static void setToPane(StackPane toPane) {
        Judge_New.toPane = toPane;
    }



    //开局前交换棋子
    public void exchange(ChessBoard board){
        String from = fromChess.getNum();
        String to = toChess.getNum();
        int fromColumn = fromChess.getColumn();
        int toColumn = toChess.getColumn();
        int fromRow = fromChess.getRow();
        int toRow = toChess.getRow();
        if(fromChess.getColor() == toChess.getColor() && !fromChess.equals(toChess)){
            ((Label)(fromPane.getChildren().get(1))).setText(to);
            ((Label)(toPane.getChildren().get(1))).setText(from);
            fromChess.setNum(to);
            toChess.setNum(from);
            back.setBackNum(fromRow, fromColumn, Integer.parseInt(to));
            back.setBackNum(toRow, toColumn, Integer.parseInt(from));
        }
        fromChess = null;
        toChess = null;
        ChessBoard.setIsChosen();
    }

    //开局后行棋
    public void doMove(ChessBoard board){
        if(!fromChess.equals(toChess) && fromChess.getColor() == 0 && getTurn() == 0){
            redMove();
        }
        if(!fromChess.equals(toChess) && fromChess.getColor() == 1 && getTurn() == 1){
            blueMove();
        }
        win();
        fromChess = null;
        toChess = null;
        ChessBoard.setIsChosen();
    }

    public void move(){
        String from = fromChess.getNum();
        String to = toChess.getNum();
        int fromColumn = fromChess.getColumn();
        int toColumn = toChess.getColumn();
        int fromRow = fromChess.getRow();
        int toRow = toChess.getRow();
        int toward = 0;

        Color color = Color.WHITE;
        if(to.equals(" ")){
            to = "0";
        }
        if(turn == 0){
            color = Color.RED;
            toward = 1;
        }
        if(turn == 1){
            color = Color.BLUE;
            toward = -1;
        }
        System.out.println("redcount : " + back.getRedCount() + "  bluecount : " + back.getBlueCount());
        if (toChess.getColumn() == fromChess.getColumn() + toward) {
            if (toChess.getRow() == fromChess.getRow() + toward || toChess.getRow() == fromChess.getRow()) {
                count();
                ((Label) (toPane.getChildren().get(1))).setText(fromChess.getNum());
                back.setBackNum(fromRow, fromColumn, Integer.parseInt(to));
                back.setBackNum(toRow, toColumn, Integer.parseInt(from));
                back.setBackColor(fromRow, fromColumn, -1);
                back.setBackColor(toRow, toColumn, fromChess.getColor());
                ((Label) (fromPane.getChildren().get(1))).setText("");

                toChess.setNum(fromChess.getNum());
                toChess.setColor(fromChess.getColor());
                toChess.fillProperty().set(color);
                fromChess.fillProperty().set(Color.WHITE);
                fromChess.setColor(-1);
                fromChess.setNum("0");
                setTurn();
            }
        }
        if (toChess.getColumn() == fromChess.getColumn()) {
            if (toChess.getRow() == fromChess.getRow() + toward) {
                count();
                back.setBackNum(fromRow, fromColumn, Integer.parseInt(to));
                back.setBackNum(toRow, toColumn, Integer.parseInt(from));
                back.setBackColor(fromRow, fromColumn, -1);
                back.setBackColor(toRow, toColumn, fromChess.getColor());
                toChess.setNum(fromChess.getNum());
                toChess.setColor(fromChess.getColor());
                toChess.fillProperty().set(Color.RED);
                ((Label) (fromPane.getChildren().get(1))).setText("");
                ((Label) (toPane.getChildren().get(1))).setText(fromChess.getNum());
                fromChess.fillProperty().set(Color.WHITE);
                fromChess.setColor(-1);
                fromChess.setNum("0");
                setTurn();
            }
        }
    }

    public void redMove(){
        move();
    }

    public void blueMove(){
        move();
    }

    public static int getTurn() {
        return turn;
    }

    public static void setTurn() {
        turn = turn == 0 ? 1 : 0;
    }

    public static void count(){
        if(toChess.getColor() == 0){
            back.setRedCount();
        }
        if(toChess.getColor() == 1){
            back.setBlueCount();
        }
    }

    public void win(){
        if(back.getBackColor(4, 4) == 0 || back.getBlueCount() == 0){
            System.out.println("red win");
        }
        if(back.getBackColor(0, 0) == 1 || back.getRedCount() == 0){
            System.out.println("blue win");
        }
        System.out.println("backboard num:");
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(back.getBackNum(i, j) + "\t");
            }
            System.out.println();
        }
        System.out.println("backboard color:");
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(back.getBackColor(i, j) + "\t");
            }
            System.out.println();
        }
    }
}
