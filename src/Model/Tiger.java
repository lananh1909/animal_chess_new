package Model;

import java.util.ArrayList;
import java.util.List;

public class Tiger extends Animal {
    public Tiger(int color, Coordinate coordinate,int value) {
        super(color, 6, true, "Tiger", coordinate, "src/image/ho" + color + ".jpg", value);
        this.positionValue = new double[][]{
                {10,12,12,0,12,12,10},
                {12,12,12,12,12,14,12},
                {14,16,16,14,16,16,14},
                {15,0,0,15,0,0,15},
                {15,0,0,15,0,0,15},
                {15,0,0,15,0,0,15},
                {18,20,20,30,20,20,18},
                {25,25,30,50,30,25,25},
                {25,30,50,this.MAX,50,30,25}
        };
        if(color == 1)
            this.positionValue = super.reserveaValue(this.positionValue);
    }

    public boolean checkMove(Piece[][] board, Piece p){
        Coordinate co = p.getCoordinate();
        int x = co.getX();
        int y = co.getY();
        Coordinate curentCo = this.getCoordinate();
        int curentX = curentCo.getX();
        int curentY = curentCo.getY();
        if(x == curentX){
            if(y < curentY){
                return !(board[x][y+1] instanceof Mouse || board[x][y+2] instanceof Mouse || board[x][y+3] instanceof Mouse);
            }
            if(y > curentY){
                return !(board[x][y-1] instanceof Mouse || board[x][y-2] instanceof Mouse || board[x][y-3] instanceof Mouse);
            }
        }

        if(y == curentY){
            if(x < curentX){
                return !(board[x+1][y] instanceof Mouse || board[x+2][y] instanceof Mouse);
            }
            if(x > curentX){
                return !(board[x-1][y] instanceof Mouse || board[x-2][y] instanceof Mouse);
            }
        }
        return true;
    }

    @Override
    public List<Coordinate> getPossibleMove(Piece[][] board) {
        Coordinate co = this.getCoordinate();
        int X = co.getX();
        int Y = co.getY();
        List<Coordinate> possibleMove = new ArrayList<>();

        if ((X + 1) <= 6) {
            Piece right = board[X + 1][Y];
            Coordinate co1 = right.getCoordinate();
            if(co1.getTypeOfLand() instanceof Water){
                Piece posRight = board[X+3][Y];
                if(checkMove(board, posRight)){
                    if(checkPiece(posRight)){
                        possibleMove.add(posRight.getCoordinate());
                    }
                }
            } else {
                if (checkPiece(right) || checkTrap(right)) {
                    possibleMove.add(right.getCoordinate());
                }
            }
        }

        if ((X - 1) >=0) {
            Piece left = board[X - 1][Y];
            Coordinate co1 = left.getCoordinate();
            if(co1.getTypeOfLand() instanceof Water){
                Piece posLeft = board[X-3][Y];
                if(checkMove(board, posLeft)){
                    if(checkPiece(posLeft)){
                        possibleMove.add(posLeft.getCoordinate());
                    }
                }
            } else {
                if (checkPiece(left) || checkTrap(left)) {
                    possibleMove.add(left.getCoordinate());
                }
            }
        }

        if ((Y - 1) >=0) {
            Piece top = board[X][Y-1];
            Coordinate co1 = top.getCoordinate();
            if(co1.getTypeOfLand() instanceof Water){
                Piece posTop = board[X][Y-4];
                if(checkMove(board, posTop)){
                    if(checkPiece(posTop)){
                        possibleMove.add(posTop.getCoordinate());
                    }
                }
            } else {
                if (checkPiece(top) || checkTrap(top)) {
                    possibleMove.add(top.getCoordinate());
                }
            }
        }

        if ((Y + 1) <=8) {
            Piece bottom = board[X][Y+1];
            Coordinate co1 = bottom.getCoordinate();
            if(co1.getTypeOfLand() instanceof Water){
                Piece posBottom = board[X][Y+4];
                if(checkMove(board, posBottom)){
                    if(checkPiece(posBottom)){
                        possibleMove.add(posBottom.getCoordinate());
                    }
                }
            } else {
                if (checkPiece(bottom) || checkTrap(bottom)) {
                    possibleMove.add(bottom.getCoordinate());
                }
            }
        }
        return possibleMove;
    }
}
