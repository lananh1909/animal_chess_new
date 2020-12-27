package Model;

import java.util.List;

public class Piece {
//    public static final int GLASS = 10;
//    public static final int WATER = 20;
//    public static final int TRAP = 30;
//    public static final int CAVE = 40;

    protected int color;
    protected int point;
    private boolean isAnimal;
    private boolean isInTheTrap;
    protected String name;
    protected Coordinate coordinate;
    protected int value;
    
    
    
    public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	protected  String image;
    
    public String getImage() {
    	return this.image;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public boolean isAnimal() {
        return isAnimal;
    }

    public void setAnimal(boolean animal) {
        isAnimal = animal;
    }

    public boolean isInTheTrap() {
        return isInTheTrap;
    }

    public void setInTheTrap(boolean inTheTrap) {
        isInTheTrap = inTheTrap;
    }
    
    public String getName() {
    	return this.name;
    }

    public Piece(int color, int point, boolean isAnimal, String name, Coordinate coordinate, String image, int value) {
        this.color = color;
        this.point = point;
        this.isAnimal = isAnimal;
        this.name = name;
        this.coordinate = coordinate;
        this.image = image;
        this.value= value;
    }

    public Piece(int _color, Coordinate _coordinate, String image, int value) {
        this(_color, 0, false, "", _coordinate, image, value);
    }


//    public int getTypeOfLand(){
//        Coordinate add = this.coordinate;
//        int X = add.getX();
//        int Y = add.getY();
//        if((X == 0 || X == 8) && Y ==3){
//            return CAVE;
//        }
//        if((X == 0 || X == 8) && (Y == 2 || Y == 4)){
//            return TRAP;
//        }
//        if((X == 1 || X == 7) && Y ==3){
//            return TRAP;
//        }
//        if((X >= 3 && X <= 5) && ((Y >= 1 && Y <= 2) || (Y >= 4 && Y <= 5))){
//            return WATER;
//        }
//        return GLASS;
//    }
}
