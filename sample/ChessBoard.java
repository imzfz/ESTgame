package sample;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


/**
 * Created by zfz on 2017/7/8.
 */
public class ChessBoard {
    private final static GridPane gp = new GridPane();
    private static int redNum = 1;
    private static int blueNum = 1;
    private static boolean isChosen = false;
    private Back_new back = new Back_new();
    private Judge_New judge = new Judge_New(back);
    private static ArrayList<Chess_New> chessArray = new ArrayList<>();
    private static ArrayList<StackPane> paneArray = new ArrayList<>();

    //绘制棋盘
    public ChessBoard(){
        double width = 80;
        double height = 80;
        StackPane sp;
        Chess_New chess;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                chess = new Chess_New(j, i);
                chess.setWidth(width);
                chess.setHeight(height);
                colorSetup(j, i, chess);
                sp = new StackPane();
                sp.getChildren().addAll(chess, new Label("" + chess.getNum()));
                gp.add(sp, j, i);
                sp.setPrefSize(width, height);
                gp.setStyle("-fx-grid-lines-visible:true");
                move(sp, chess);
                chessArray.add(chess);
                paneArray.add(sp);
            }
        }

        Setup.setBack(back);
        gp.setPadding(new Insets(5, 5, 5, 5));
    }

    public static GridPane getGp(){
        return gp;
    }

    public void colorSetup(int i, int j, Chess_New chess){
        if(i < 3 && j < 3 - i){
            chess.draw(0, "" + redNum);
            back.setBackColor(j, i, 0);
            back.setBackNum(j, i, redNum);
            redNum++;
        }
        else if(i >= 2 && j >= 4 - i + 2){
            chess.draw(1, "" + blueNum);
            back.setBackColor(j, i, 1);
            back.setBackNum(j, i, blueNum);
            blueNum++;
        }
        else {
            chess.draw(-1, " ");
            back.setBackColor(j, i, -1);
        }

    }


    //事件响应
    public void move(StackPane sp, Chess_New chess){
        sp.setOnMouseClicked(e -> {
            System.out.println("front color:" + chess.getColor() + "front num:" + chess.getNum());
            System.out.println("front column:" + chess.getColumn() + "front row:" + chess.getRow());
            if(!isChosen && chess.getColor() != -1){
                Judge_New.setFromChess(chess);
                Judge_New.setFromPane(sp);
                isChosen = true;
            }
            else if(isChosen) {
                Judge_New.setToChess(chess);
                Judge_New.setToPane(sp);
                if (Setup.isSetup()) {
                    judge.exchange(this);
                } else {
                    judge.doMove(this);
                }
            }
        });
    }

    public static void setIsChosen(){
        isChosen = false;
    }

    public static Chess_New getChess(int color, String num){
        for(Chess_New c : chessArray){
            if(c.getColor() == color && c.getNum().equals(num)){
                return c;
            }
        }
        return null;
    }

    public static Chess_New getChess(int x, int y){
        System.out.println(x + "  " + y);
        for(Chess_New c : chessArray){
            if(c.getColumn() == x && c.getRow() == y){
                System.out.println(c.getColor() + " " + c.getRow() + " " + c.getColumn());
                return c;
            }
        }
        return null;
    }
    public static StackPane getSp(int x, int y){
        return paneArray.get(x * 5 + y);
    }

    public static boolean hasDice(int color, String num){
        for(Chess_New e : chessArray){
            if(e.getColor() == color && e.getNum().equals(num)){
                return true;
            }
        }
        return false;
    }
}
