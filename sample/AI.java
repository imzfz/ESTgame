package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by zfz on 2017/7/12.
 */
public class AI {
    private static int board_Value[][] = {
            {2, 2, 2, 2, 2},
            {2, 0, 3, 3, 3},
            {2, 3, 6, 6, 6},
            {2, 3, 6, 8, 8},
            {2, 3, 6, 8, 100}};
    private int chess_Value[][] = new int[5][5];
    private int[] best;
    private int pos[] = new int[2];
    private int aiColor;
    private int value = 0;
    private int dice;
    private  Back_new back;
    private int dice1 = 0;

    public AI(Back_new back, int color){
        this.back = back;
        aiColor = color;
        int Value[][] = new int[5][5];
        if(aiColor == 1) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    Value[i][j] = board_Value[5 - i - 1][5 - j - 1];
                }
            }
            board_Value = Value;
        }
    }

    public void assess(int dice){
        int i = 0;
        int j = 0;
        int toward = 1;
        int pro = probability();
        for(int ii = 0; ii < 5; ii++){
            for(int jj = 0; jj < 5; jj++){
                if(dice == back.getBackNum(ii, jj) && back.getBackColor(ii, jj) == aiColor){
                    i = ii;
                    j = jj;
                    System.out.println("i : " + i + " j : " + j);
                }
            }
        }

        if(aiColor == 0){
            toward = 1;
        }
        else{
            toward = -1;
        }

        if(i + toward < 5 && back.getBackColor(i + toward, j) == -1){
            chess_Value[i + toward][j] += 3;
            System.out.println("step 1 ok");
        }

        if(i + toward < 5 && back.getBackColor(i + toward, j) == back.getBackColor(i, j)){
            chess_Value[i + toward][j] += 2;
            if(back.getBackNum(i + toward, j) >= 2 && back.getBackNum(i + toward, j) < 5){
                chess_Value[i + toward][j] += 2;
            }
            System.out.println("step 11 ok");
        }

        if(i + toward < 5 && back.getBackColor(i + toward, j) != back.getBackColor(i, j) && back.getBackColor(i + toward, j) != -1){
            chess_Value[i + toward][j] += 6;
            if(back.getBackNum(i + toward, j) == pro){
                chess_Value[i + toward][j] += 4;
            }
            System.out.println("step 111 ok");
        }

        if(j + toward < 5 && back.getBackColor(i, j + toward) == -1){
            chess_Value[i][j + toward] += 2;
            System.out.println("step 2 ok");
        }

        if(j + toward < 5 && back.getBackColor(i, j + toward) == back.getBackColor(i, j)){
            chess_Value[i][j + toward] += 1;
            if(back.getBackNum(i, j + toward) >= 2 && back.getBackNum(i, j + toward) < 5){
                chess_Value[i + toward][j] += 2;
            }
            System.out.println("step 22 ok");
        }

        if(j + toward < 5 && back.getBackColor(i, j + toward) != back.getBackColor(i, j) && back.getBackColor(i, j + toward ) != -1){
            chess_Value[i][j + toward] += 5;
            if(back.getBackNum(i, j + toward) == pro){
                chess_Value[i][j + toward] += 4;
            }
            System.out.println("step 222 ok");
        }

        if(i + toward < 5 && j + toward < 5 && back.getBackColor(i + toward, j + toward) == -1){
            chess_Value[i + toward][j + toward] += 4;
            System.out.println("step 3 ok");
        }

        if(i + toward < 5 && j + toward < 5 && back.getBackColor(i + toward, j + toward) == back.getBackColor(i, j)){
            chess_Value[i + toward][j + toward] += 2;
            if(back.getBackNum(i + toward, j + toward) >= 2 && back.getBackNum(i + toward, j + toward) < 5){
                chess_Value[i + toward][j] += 2;
            }
            System.out.println("step 33 ok");
        }

        if(i + toward < 5 && j + toward < 5 && back.getBackColor(i + toward, j + toward) != back.getBackColor(i, j) && back.getBackColor(i + toward, j + toward) != -1){
            chess_Value[i + toward][j + toward] += 6;
            if(back.getBackNum(i + toward, j + toward) == pro){
                chess_Value[i + toward][j + toward] += 4;
            }
            System.out.println("step 333 ok");
        }

        for(int m = 0; m < 3; m++){
            for(int n = 0; n < 3; n++){
                if(back.getBackColor(m, n) != aiColor && back.getBackColor(m, n) != -1){
                    if(i + toward == m && j == n){
                        chess_Value[m][n] += 3;
                        System.out.println("step 666 ok");
                    }
                    if(i == m && j + toward == n){
                        chess_Value[m][n] += 3;
                        System.out.println("step 666 ok");
                    }
                    if(i + toward == m && j + toward == n){
                        chess_Value[m][n] += 10;
                        System.out.println("step 666 ok");
                    }
                }
            }
        }

        if(i + toward < 5){
            chess_Value[i + toward][j] += board_Value[i + toward][j];
        }

        if(j + toward < 5){
            chess_Value[i][j + toward] += board_Value[i][j + toward];
        }

        if(i + toward < 5 && j + toward < 5){
            chess_Value[i + toward][j + toward] += board_Value[i + toward][j + toward];
        }

        for(int ii = 0; ii < 5; ii++){
            for(int jj = 0; jj < 5; jj++){
                System.out.print(chess_Value[ii][jj] + "\t");
            }
            System.out.println();
        }
    }



    public int[] getBest(){
        int ii, jj;
        for(ii = 0; ii < 5; ii++){
            for(jj = 0; jj < 5; jj++){
                if(value < chess_Value[ii][jj]){
                    System.out.println("value " + chess_Value[ii][jj]);
                    value = chess_Value[ii][jj];
                    pos[0] = ii;
                    pos[1] = jj;
                }
            }
        }
        return pos;
    }

    public void AImove(){
        assess(getDice());
        best = getBest();
 //       Judge_New.setTurn();
    }

    public void setDice(int dice){
        this.dice = dice;
        int value1 = 0;
        if(ChessBoard.hasDice(Judge_New.getTurn(), "" + dice)) {
            AImove();
            move(best);
            System.out.println("aaaaaa");
            return;
        }
        for(int m = 1; m < 6; m++) {
            if (dice + m <= 6 && ChessBoard.hasDice(Judge_New.getTurn(), "" + (dice + m))) {
                this.dice = dice + m;
                AImove();
                dice1 = this.dice;
                value1 = value;
                System.out.println("bbbbbb");
                break;
            }
        }
        for(int m = 1; m < 6; m++) {
            if (dice - m >= 1 && ChessBoard.hasDice(Judge_New.getTurn(), "" + (dice - m))) {
                this.dice = dice - m;
                AImove();
                if(value1 == value){
                    this.dice = dice1;
                    System.out.println("mmmmm");
                }
                System.out.println("cccccc");
                break;
            }
        }
        move(best);
    }

    public int getDice(){
        System.out.println("dice : " + dice);
        return dice;
    }

    public void move(int[] best){
        Chess_New fromChess = ChessBoard.getChess(Judge_New.getTurn(), "" + getDice());
        Chess_New toChess = ChessBoard.getChess(best[1], best[0]);
        String from = fromChess.getNum();
        String to = toChess.getNum();
        int fromColumn = fromChess.getColumn();
        int toColumn = toChess.getColumn();
        int fromRow = fromChess.getRow();
        int toRow = toChess.getRow();
        int toward = 0;
        StackPane fromPane = ChessBoard.getSp(fromRow, fromColumn);
        StackPane toPane = ChessBoard.getSp(toRow, toColumn);
        Judge_New.setFromChess(fromChess);
        Judge_New.setToChess(toChess);


        Color color = Color.WHITE;
        if(to.equals(" ")){
            to = "0";
        }
        if(Judge_New.getTurn() == 0){
            color = Color.RED;
            toward = 1;
        }
        if(Judge_New.getTurn() == 1){
            color = Color.BLUE;
            toward = -1;
        }
        System.out.println("redcount : " + back.getRedCount() + "  bluecount : " + back.getBlueCount());
        System.out.println(toRow + " " + toColumn + " " + fromRow + " " + fromColumn);
        System.out.println(toChess.getColor());

        if (toChess.getColumn() == fromChess.getColumn() + toward) {
            if (toChess.getRow() == fromChess.getRow() + toward || toChess.getRow() == fromChess.getRow()) {
                Judge_New.count();
                ((Label) (toPane.getChildren().get(1))).setText(fromChess.getNum());
                back.setBackNum(toRow, toColumn, Integer.parseInt(from));
                back.setBackNum(fromRow, fromColumn, 0);
                back.setBackColor(fromRow, fromColumn, -1);
                back.setBackColor(toRow, toColumn, fromChess.getColor());
                ((Label) (fromPane.getChildren().get(1))).setText("");

                toChess.setNum(fromChess.getNum());
                toChess.setColor(fromChess.getColor());
                toChess.fillProperty().set(color);
                fromChess.fillProperty().set(Color.WHITE);
                fromChess.setColor(-1);
                fromChess.setNum("0");
                Judge_New.setTurn();
            }
        }
        if (toChess.getColumn() == fromChess.getColumn()) {
            if (toChess.getRow() == fromChess.getRow() + toward) {
                Judge_New.count();
                back.setBackNum(toRow, toColumn, Integer.parseInt(from));
                back.setBackNum(fromRow, fromColumn, 0);
                back.setBackColor(fromRow, fromColumn, -1);
                back.setBackColor(toRow, toColumn, fromChess.getColor());
                toChess.setNum(fromChess.getNum());
                toChess.setColor(fromChess.getColor());
                toChess.fillProperty().set(color);
                ((Label) (fromPane.getChildren().get(1))).setText("");
                ((Label) (toPane.getChildren().get(1))).setText(fromChess.getNum());
                fromChess.fillProperty().set(Color.WHITE);
                fromChess.setColor(-1);
                fromChess.setNum("0");
                Judge_New.setTurn();
            }
        }
        Judge_New.win();
        System.out.println("from row : " + fromRow + "from column : " + fromColumn);
        System.out.println("from : " + from + " to : " + to);
    }

    public int probability(){
        int[] has = new int[7];
        double[] t = new double[7];
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j ++){
                if(back.getBackColor(i, j) != aiColor && back.getBackColor(i, j) != -1 && back.getBackNum(i, j) != 0){
                    has[back.getBackNum(i, j)] = 1;
                }
            }
        }


        for(int i = 1; i < 7; i++){
            int m = 0;
            int j;
            if(has[i] == 1){
                t[i] = (double)1/6;
                for(j = 1; j < 6; j++){
                    if(i + j <= 6 && has[i + j] != 1){
                        m++;
                    }
                    else{
                        break;
                    }
                }
                if(i == 6){

                }
                else if(i + j == 7){
                    t[i] += (m * (1/(double)6));
                }
                else if(m > 0){
                    t[i] += (m * (1/(double)6) * (1/(double)2));
                }

                m = 0;

                for(j = 1; j < 6; j++){
                    if(i - j >= 1 && has[i - j] != 1){
                        m++;
                    }
                    else{
                        break;
                    }
                }
                if(i == 1){

                }
                else if(i - j == 0){
                    t[i] += (m * (1/(double)6));
                }
                else if(m > 0){
                    t[i] += (m * (1/(double)6) * (1/(double)2));
                }
            }
        }
        double r = 0;
        int ii = 1;
        for(int i = 1; i < 7; i++){
            if(t[i] > r){
                r = t[i];
                ii = i;
            }
        }
        return ii;
    }
}
