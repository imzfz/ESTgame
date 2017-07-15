package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;


/**
 * Created by zfz on 17/2/2.
 */
class Back {
    //0 is white  1 is red  2 is blue
    static final int WHITE = 0;
    static final int RED = 1;
    static final int BLUE = 2;
    static int red = 1, blue = 1;
    static int board[][] = new int[5][5];                                      //record chessboard
    static int number[][] = new int [5][5];                                    //record number of chess
    static int turn = 1;                            //1 is red  2 is blue
    static int fromI = -1, fromJ = -1, toI = -1, toJ = -1;

    static Rectangle tempchess;
    static Roll roll = new Roll();

    public Back(){}

    public static void setBoard(){
        board[toI][toJ] = board[fromI][fromJ];
        number[toI][toJ] = number[fromI][fromJ];
        board[fromI][fromJ] = 0;
        number[fromI][fromJ] = 0;
    }

}
