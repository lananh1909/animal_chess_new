package Model;

import java.util.List;

public class Cheetah extends Animal {
    public Cheetah(int color, Coordinate coordinate, int value) {
        super(color, 5, true, "Cheetah", coordinate, "src/image/bao" + color + ".jpg", value);
        this.positionValue = new double[][]{
                {9,9,9,0,9,9,9},
                {9,9,9,9,9,9,9},
                {9,9,10,10,9,9,9},
                {10,0,0,13,0,0,10},
                {11,0,0,14,0,0,11},
                {12,0,0,15,0,0,12},
                {13,13,14,15,14,13,13},
                {13,14,15,50,15,14,13},
                {14,15,50,this.MAX,50,15,14}
        };
        if(color == 1)
            this.positionValue = super.reserveaValue(this.positionValue);
    }

}
