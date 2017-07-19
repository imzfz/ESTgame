package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Created by zfz on 2017/7/8.
 */
public class Setup {
    private StackPane area = new StackPane();
    private static boolean isSetup = true;
    private static Back_new back;



    //右侧边栏
    //TODO
    public Setup(){
        VBox list = new VBox();
        HBox option = new HBox();
        ToggleGroup tg = new ToggleGroup();
        RadioButton youFirst = new RadioButton("对方先手");
        RadioButton meFirst = new RadioButton("我方先手");
        TextField number = new TextField();
        option.getChildren().addAll(youFirst, meFirst);
        Button alter = new Button("预设棋盘");
        Button start = new Button("开始");
        Button reset = new Button("悔棋");
        Button go = new Button("行棋");

        area.getChildren().add(list);
        list.getChildren().addAll(alter, option, start, number, go, reset);
        tg.getToggles().addAll(youFirst, meFirst);

        area.setPadding(new Insets(5, 10, 5, 5));
        list.setSpacing(10);
        list.setAlignment(Pos.TOP_CENTER);

        alter.setDisable(true);
        reset.setDisable(true);
        go.setDisable(true);

        action(alter, start, reset, go, tg, number);
    }

    public StackPane getArea(){
        return area;
    }

    public void action(Button alter, Button start, Button reset, Button go, ToggleGroup tg, TextField number){
        alter.setOnMouseClicked(e -> {
            isSetup = true;
            start.setDisable(false);
            alter.setDisable(true);
            reset.setDisable(true);
            go.setDisable(true);
            Judge_New.setFromChess(null);
            Judge_New.setToChess(null);
            ChessBoard.setIsChosen();
        });

        start.setOnMouseClicked(e -> {
            isSetup = false;
            alter.setDisable(false);
            start.setDisable(true);
            reset.setDisable(false);
            go.setDisable(false);
            ((RadioButton)(tg.getToggles().get(0))).setDisable(true);
            ((RadioButton)(tg.getToggles().get(1))).setDisable(true);
            Judge_New.setFromChess(null);
            Judge_New.setToChess(null);
            ChessBoard.setIsChosen();
            if(Judge_New.getTurn() == 0 && tg.getToggles().get(0).isSelected()) {
     //           Judge_New.setTurn();
            }
        });

        go.setOnMouseClicked(e -> {
     //       Judge_New.getDice(Integer.parseInt(number.getText()));

            if(Judge_New.getTurn() == 0 && tg.getToggles().get(1).isSelected()) {
                new AI(back, 0).setDice(Integer.parseInt(number.getText()));
            }
            else if(Judge_New.getTurn() == 1 && tg.getToggles().get(0).isSelected()) {
                new AI(back, 1).setDice(Integer.parseInt(number.getText()));
            }
        });
    }

    public static boolean isSetup() {
        return isSetup;
    }

    public static void setBack(Back_new back){
        Setup.back = back;
    }
}
