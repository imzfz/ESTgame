package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static sample.Back.*;

/**
 * Created by zfz on 17/2/1.
 * 棋盘和棋子的属性
 */
class Chess extends Rectangle{
    int tag;
    StackPane sp;
    Label num = new Label("1");
    Rectangle chess;
    DoubleProperty width = new SimpleDoubleProperty(80);
    DoubleProperty height = new SimpleDoubleProperty(80);
    IntegerProperty color = new SimpleIntegerProperty(-1);
    static GridPane gp = new GridPane();
    static Label tempnum;
    int x, y;

    /**
     * 初始化棋盘，注册响应事件
     */
    public Chess(int i, int j) {
        x = i;
        y = j;
        chess = new Rectangle();
        sp = new StackPane();
        chess.heightProperty().bind(height);
        chess.widthProperty().bind(width);
        setColorandnumber(chess, i, j);
        gp.add(sp, i, j);
        //line of rectangle
        gp.setStyle("-fx-grid-lines-visible:true");
        sp.setOnMouseClicked(e -> {
            //记录from的棋子信息
            if(Judge.getFromI() == -1 && Judge.getFromJ() == -1 && !Judge.isFinished()) {
                if(whichMove()) {
                    tempnum = num;
                    tempchess = chess;
                    Judge.getFrom(i, j);
                    Judge.setSp(this);
                    color.set(-1);
                }
            }
            else if(Judge.getToI() == -1 && Judge.getToJ() == -1 && !Judge.isFinished()) {
                int tempTag = Judge.getSp().tag;
                Judge.getTo(i, j);
                gp.getChildren().remove(Judge.getSp());
                gp.getChildren().add(Judge.getSp());
                this.tag = tempTag;
            }
            if(Judge.move() && !Judge.isFinished()){
                moveChange();
            }
        });
    }



    /**
     * 调整大小
     */
    public void setSize(Scene s){
        s.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                width.set((s.getWidth() - 100) / 5);
                height.set((s.getHeight() - 75) / 5);
            }
        });
    }


    public void changeColor(){
        color.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (color.get() == 1) {
                    chess.fillProperty().set(Color.RED);
                }
                else if (color.get() == 2) {
                    chess.fillProperty().set(Color.BLUE);
                }
                else if(color.get() == 0){
                    chess.fillProperty().set(Color.WHITE);
                }
            }
        });
/*
        num.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

            }
        });
*/
    }

    /**
     * 初始化棋子编号以及颜色
     */
    public void setColorandnumber(Rectangle chess, int i, int j){
        if(i < 3 && j < 3 - i){
            tag = red;
            num.setText("" + red);                                            //num label
            sp.getChildren().addAll(chess, num);
            board[i][j] = RED;
            number[i][j] = red;
            red++;
        }
        else if(i >= 2 && j >= 4 - i + 2){
            tag = blue;
            num.setText("" + blue);
            sp.getChildren().addAll(chess, num);
            board[i][j] = BLUE;
            number[i][j] = blue;
            blue++;
        }
        else {
            tag = -1;
            num.setText("");
            sp.getChildren().addAll(chess, num);
            board[i][j] = WHITE;
        }
    }

    /**
     * 变更时候减少某个颜色的计数，实现棋子移动
     */
    public void moveChange(){
        if(board[toI][toJ] == 1){
            red--;
        }

        if(board[toI][toJ] == 2){
            blue--;
        }

        //改变数字填充，颜色填充，实现棋子移动
        color.set(board[fromI][fromJ]);
        num.setText(tempnum.getText());
        tempnum.textProperty().set("");
        tempchess.fillProperty().set(Color.WHITE);
        //设置后端棋盘数据
        setBoard();
        //设置from和to为初始值
        fromI = -1;
        fromJ = -1;
        toI = -1;
        toJ = -1;
    }

    public boolean whichMove(){
        int exist = 0;
        int up = 6, down = 0;
        if(roll.getNum() == this.tag){
            return true;
        }

        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (number[i][j] == roll.getNum() && board[i][j] == Judge.whosTurn()) {
                    System.out.print(this.tag + "--" + number[i][j]);
                    return false;
                }
                if (up > number[i][j] && number[i][j] > roll.getNum() && board[i][j] == Judge.whosTurn()) {
                    up = number[i][j];
                }
                if (down < number[i][j] && number[i][j] < roll.getNum() && board[i][j] == Judge.whosTurn()) {
                    down = number[i][j];
                }
            }
        }
        System.out.print(up + "==" + down + "==" + this.tag);
        return up == this.tag || down == this.tag;
    }

    public IntegerProperty getColor(){
        return color;
    }

}
