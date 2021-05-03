package game;

import static java.lang.Math.abs;

public class Samotnik
{
    int[][] engboard = {
            {-1, -1, 1, 1, 1, -1, -1},
            {-1, -1, 1, 1, 1, -1, -1},
            {1 ,  1, 1, 1, 1,  1,  1},
            {1 ,  1, 1, 0, 1,  1,  1},
            {1 ,  1, 1, 1, 1,  1,  1},
            {-1, -1, 1, 1, 1, -1, -1},
            {-1, -1, 1, 1, 1, -1, -1}
            };

    int[][] eurboard = {
            {-1, -1, 1, 1, 1, -1, -1},
            {-1,  1, 1, 1, 1,  1, -1},
            {1 ,  1, 1, 1, 1,  1,  1},
            {1 ,  1, 1, 0, 1,  1,  1},
            {1 ,  1, 1, 1, 1,  1,  1},
            {-1,  1, 1, 1, 1,  1, -1},
            {-1, -1, 1, 1, 1, -1, -1}
    };

    int[][] board = engboard;

    public void tryToMove(int xs, int ys, int xd, int yd)
    {
        if(possible(xs, ys, xd, yd)) {
            move(xs, ys, xd, yd);
        }
    }

    public boolean possible(int xs, int ys, int xd, int yd)
    {
        return board[xs][ys] == 1 && board[xd][yd] == 0 && board[(xs + xd)/2][(ys + yd)/2] == 1
                && ((abs(xs - xd) == 2 && abs(xs - xd) == 0) || (abs(xs - xd) == 0 && abs(xs - xd) == 2));
    }

    public void move(int xs, int ys, int xd, int yd)
    {
        this.setClear(xs, ys);
        this.setClear(((xs + xd)/2), ((ys + yd)/2));
        this.setTaken(xd, yd);
    }

    public void setClear(int x, int y)
    {
        board[x][y] = 0;
    }

    public void setTaken(int x, int y)
    {
        board[x][y] = 1;
    }
}
