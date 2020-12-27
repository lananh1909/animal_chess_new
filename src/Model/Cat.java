package Model;

import java.util.List;

public class Cat extends Animal {

    public Cat(int color, Coordinate coordinate, int value) {
        super(color, 2, true, "Cat", coordinate, "src/image/meo" + color + ".jpg", value);
        this.positionValue = new double[][]{
                {8,8,8,0,8,8,8},
                {8,8,8,8,8,10,13},
                {8,8,8,8,10,10,10},
                {8,0,0,8,0,0,10},
                {8,0,0,8,0,0,10},
                {8,0,0,10,0,0,10},
                {10,11,11,15,11,11,10},
                {11,11,15,50,15,11,11},
                {11,15,50,this.MAX,50,15,11}
        };
        if(color == 1)
            this.positionValue = super.reserveaValue(this.positionValue);
    }

}
