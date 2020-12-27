package Model;

import java.util.ArrayList;
import java.util.List;

public class Mouse extends Animal {
    public Mouse(int color, Coordinate coordinate,int value) {
        super(color, 1, true, "Mouse", coordinate, "src/image/chuot" + color + ".jpg",value);
        this.positionValue = new double[][]{
                {8,8,8,0,8,8,8},
                {9,9,9,9,8,8,8},
                {10,10,10,9,8,8,8},
                {11,12,12,10,9,9,8},
                {12,12,12,11,9,9,8},
                {13,12,12,11,9,9,8},
                {13,13,13,13,11,11,10},
                {13,13,13,50,13,12,11},
                {13,13,50,this.MAX,50,13,11}
        };
        if(color == 1){
            this.positionValue = super.reserveaValue(this.positionValue);
        }
    }

    @Override
    public boolean checkPiece(Piece p){
        Piece q = this.getCoordinate().getTypeOfLand();
        if (p.isAnimal() && p.color != this.color) {
            if (p instanceof Elephant){
                if(q instanceof Water)
                    return false;
                return true;
            }
            if (p.point <= this.point)
                return true;
            else
                return false;
        }

        if (p.isAnimal() && p.color == this.color)
            return false;

        if (p.color == this.color && p.getCoordinate().getTypeOfLand() instanceof Cave)
            return false;
        if (move[p.getCoordinate().getX()][p.getCoordinate().getY()] == 3){
            return false;
        }

        return true;
    }
}
