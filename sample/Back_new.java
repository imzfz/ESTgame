package sample;

/**
 * Created by zfz on 2017/7/8.
 */
public class Back_new {
    private int backNum[][] = new int[5][5];
    private int backColor[][] = new int[5][5];
    private int redCount = 6;
    private int blueCount = 6;


    //后端记录棋盘
    public int getBackColor(int i, int j) {
        return backColor[i][j];
    }

    public void setBackColor(int i, int j, int backColor) {
        this.backColor[i][j] = backColor;
    }

    public int getBackNum(int i, int j) {
        return backNum[i][j];
    }

    public void setBackNum(int i, int j, int backNum) {
        this.backNum[i][j] = backNum;
    }

    public int getRedCount() {
        return redCount;
    }

    public void setRedCount() {
        this.redCount--;
    }

    public int getBlueCount() {
        return blueCount;
    }

    public void setBlueCount() {
        this.blueCount--;
    }
}
