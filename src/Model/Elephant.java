package Model;

import java.util.ArrayList;
import java.util.List;

public class Elephant extends Animal {
    public Elephant(int color, Coordinate coordinate, int value) {
        super(color, 8, true, "Elephant", coordinate, "src/image/voi" + color + ".jpg",value);
        this.positionValue = new double[][]{
                {11,11,11,0,11,11,11},
                {11,11,11,11,11,11,11},
                {12,14,14,14,14,10,10},
                {12,0,0,12,0,0,12},
                {14,0,0,14,0,0,14},
                {16,0,0,16,0,0,16},
                {18,20,20,30,20,20,18},
                {25,25,30,50,30,25,25},
                {25,30,50,this.MAX,50,30,25}
        };
        if(color == 1){
            this.positionValue = super.reserveaValue(this.positionValue);
        }
    }

    @Override
    public boolean checkPiece(Piece p){
        if (p.isAnimal() && p.color != this.color) {
            if (p instanceof Mouse){
                return false;
            }
            if (p.point <= this.point)
                return true;
            else
                return false;
        }

        if (p.isAnimal() && p.color == this.color)
            return false;

        if (p instanceof Water) {
            return false;
        }
        if (p.color == this.color && p.getCoordinate().getTypeOfLand() instanceof Cave)
            return false;

        if (move[p.getCoordinate().getX()][p.getCoordinate().getY()] == 3){
            return false;
        }
        return true;
    }
}
