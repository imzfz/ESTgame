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
            {2, 3, 3, 3, 3},
            {2, 3, 6, 6, 6},
            {2, 3, 6, 8, 8},
            {2, 3, 6, 8, 100}};
    private static int chess_Value[][] = new int[5][5];
//    private static int dice = 0;
    private int dice;
    private  Back_new back;

    public AI(Back_new back){
        this.back = back;
    }

    public void assess(int dice){
        int i = 0;
        int j = 0;
        for(int ii = 0; ii < 5; ii++){
            for(int jj = 0; jj < 5; jj++){
                if(dice == back.getBackNum(ii, jj) && back.getBackColor(ii, jj) == 0){
                    i = ii;
                    j = jj;
                    System.out.println("i : " + i + " j : " + j);
                }
            }
        }

        if(i + 1 < 5 && back.getBackColor(i + 1, j) == -1){
            chess_Value[i + 1][j] += 3;
            System.out.println("step 1 ok");
        }

        if(i + 1 < 5 && back.getBackColor(i + 1, j) == back.getBackColor(i, j)){
            chess_Value[i + 1][j] += 1;
            System.out.println("step 11 ok");
        }

        if(i + 1 < 5 && back.getBackColor(i + 1, j) != back.getBackColor(i, j) && back.getBackColor(i + 1, j) != -1){
            chess_Value[i + 1][j] += 5;
            System.out.println("step 111 ok");
        }

        if(j + 1 < 5 && back.getBackColor(i, j + 1) == -1){
            chess_Value[i][j + 1] += 3;
            System.out.println("step 2 ok");
        }

        if(j + 1 < 5 && back.getBackColor(i, j + 1) == back.getBackColor(i, j)){
            chess_Value[i][j + 1] += 1;
            System.out.println("step 22 ok");
        }

        if(j + 1 < 5 && back.getBackColor(i, j + 1) != back.getBackColor(i, j) && back.getBackColor(i, j + 1 ) != -1){
            chess_Value[i][j + 1] += 5;
            System.out.println("step 22 ok");
        }

        if(i + 1 < 5 && j + 1 < 5 && back.getBackColor(i + 1, j + 1) == -1){
            chess_Value[i + 1][j + 1] += 3;
            System.out.println("step 3 ok");
        }

        if(i + 1 < 5 && j + 1 < 5 && back.getBackColor(i + 1, j + 1) == back.getBackColor(i, j)){
            chess_Value[i + 1][j + 1] += 1;
            System.out.println("step 33 ok");
        }

        if(i + 1 < 5 && j + 1 < 5 && back.getBackColor(i + 1, j + 1) == back.getBackColor(i, j) && back.getBackColor(i + 1, j + 1) != -1){
            chess_Value[i + 1][j + 1] += 5;
            System.out.println("step 333 ok");
        }

        if(i + 1 < 5){
            chess_Value[i + 1][j] += board_Value[i + 1][j];
        }

        if(j + 1 < 5){
            chess_Value[i][j + 1] += board_Value[i][j + 1];
        }

        if(i + 1 < 5 && j + 1 < 5){
            chess_Value[i + 1][j + 1] += board_Value[i + 1][j + 1];
        }

        for(int ii = 0; ii < 5; ii++){
            for(int jj = 0; jj < 5; jj++){
                System.out.print(chess_Value[ii][jj] + "\t");
            }
            System.out.println();
        }
    }



    public int[] getBest(){
        int t = 0;
        int pos[] = new int[2];
        int ii, jj;
        int i = 0, j = 0;
        for(ii = 0; ii < 5; ii++){
            for(jj = 0; jj < 5; jj++){
                if(t <chess_Value[ii][jj]){
                    t = chess_Value[ii][jj];
                    pos[0] = i;
                    pos[1] = j;
                }
            }
        }
        return pos;
    }

    public void AImove(){
        assess(getDice());
        int[] best = getBest();
        /**
         * problem
         */
        move(best);
        Judge_New.setTurn();
    }

    public void setDice(int dice){
        this.dice = dice;
        AImove();
    }

    public int getDice(){
        System.out.println("dice : " + dice);
        return dice;
    }

    public void move(int[] best){
        Chess_New fromChess = ChessBoard.getChess(Judge_New.getTurn(), "" + getDice());
        Chess_New toChess = ChessBoard.getChess(best[0], best[1]);
        String from = fromChess.getNum();
        String to = toChess.getNum();
        int fromColumn = fromChess.getColumn();
        int toColumn = toChess.getColumn();
        int fromRow = fromChess.getRow();
        int toRow = toChess.getRow();
        int toward = 0;
        StackPane fromPane = ChessBoard.getSp(fromRow, fromColumn);
        StackPane toPane = ChessBoard.getSp(toRow, toColumn);

        Color color = Color.WHITE;
        if(to.equals(" ")){
            to = "0";
        }
        if(Judge_New.getTurn() == 0){
            color = Color.RED;
            toward = 1;
            System.out.println("ccccccccccc");
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
                Judge_New.setTurn();
                System.out.println("aaaaaaaaaaaaaaa");
            }
        }
        if (toChess.getColumn() == fromChess.getColumn()) {
            if (toChess.getRow() == fromChess.getRow() + toward) {
                Judge_New.count();
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
                Judge_New.setTurn();
                System.out.println("bbbbbbbbbbbbbb");
            }
        }
        System.out.println("from row : " + fromRow + "from column : " + fromColumn);
        System.out.println("from : " + from + " to : " + to);
    }
}
