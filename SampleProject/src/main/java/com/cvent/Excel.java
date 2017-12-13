package com.cvent;

/**
 * Created by a.srivastava on 7/2/17.
 */
public class Excel {
    
    private int H;
    private char W;

    private final int[][] sheet;

    public static void main(String[] args) {
        Excel ex = new Excel(3, 'C');
        ex.sum(1, 'A', new String[]{"A2"});
        ex.set(2, 'A', 1);
        ex.get(1, 'A');
    }
    public Excel(int H, char W) {
        checkArguments(H, W);
        this.H = H;
        this.W = W;
        sheet = new int[H][W - 'A' + 1];
    }

    public void set(int row, char column, int val) {
        checkArguments(row, column);
        sheet[row-1][column - 'A'] = val;
    }

    public int get(int row, char column) {
        checkArguments(row, column);
        return sheet[row-1][column - 'A'];
    }

    public int sum(int row, char column, String[] strs) {
        checkArguments(row, column);
        int sum = 0;
        for (String s : strs) {
            int index = 0;
            char[] vals = s.toCharArray();
            if (vals.length == 2) {
                sum += sheet[Character.getNumericValue(vals[index+1])-1][vals[index] - 'A'];
                index += 2;
            } else {
                int startHeight = Character.getNumericValue(vals[index+1])-1;
                int endHeight = Character.getNumericValue(vals[index+4])-1;
                int startWidth = vals[index] - 'A';
                int endWidth = vals[index+3] - 'A';
                for (int h  = startHeight; h <= endHeight; h++) {
                    for (int w  = startWidth; w <= endWidth; w++) {
                        sum += sheet[h][w];
                    }
                }
                
            }
        }
        set(row, column, sum);
        return sum;
    }
    private void checkArguments(int row, char column) {
        if (row < 1 || row > 26) {
            new IllegalArgumentException("Incorrect Row :" + row);
        } else if (column < 'A' || column > 'Z') {
            new IllegalArgumentException("Incorrect Column :" + column);
        }
    }
}
