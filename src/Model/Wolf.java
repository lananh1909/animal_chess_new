package Model;

import java.util.List;

public class Wolf extends Animal {
    public Wolf(int color, Coordinate coordinate,int value) {
        super(color, 4, true, "Wolf", coordinate, "src/image/soi" + color + ".jpg",value);
        this.positionValue = new double[][] {
                {8,8,8,0,12,12,8},
                {8,8,8,8,13,12,8},
                {8,8,8,8,10,8,8},
                {8,0,0,8,0,0,8},
                {8,0,0,8,0,0,8},
                {9,0,0,10,0,0,9},
                {9,10,11,15,11,10,9},
                {10,11,15,50,15,11,10},
                {11,15,50,this.MAX,50,15,11}
        };
        if(color == 1)
            this.positionValue = super.reserveaValue(this.positionValue);
    }

}
