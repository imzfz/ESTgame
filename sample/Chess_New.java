package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by zfz on 2017/7/8.
 */
public class Chess_New extends Rectangle {
    private static final int RED = 0;
    private static final int BLUE = 1;
    private static final int WHITE= -1;
    private int color = WHITE;
    private int column = -1;
    private int row = -1;
    private String num ;


    //棋子类
    public Chess_New(){

    }

    public Chess_New(int x , int y){
        this.column = x;
        this.row = y;
    }

    public void draw(int color, String num){
        this.color = color;
        if(color == RED) {
            this.fillProperty().set(Color.RED);
            this.num = num;
        }
        else if (color == BLUE){
            this.fillProperty().set(Color.BLUE);
            this.num = num;
        }
        else{
            this.fillProperty().set(Color.WHITE);
            this.num = num;
        }
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColumn() {
        return column;
    }

    public void setX(int x) {
        this.column = x;
    }

    public int getRow() {
        return row;
    }

    public void setY(int y) {
        this.row = y;
    }
}
