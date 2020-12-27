package Model;

public class Coordinate {
	private int X;
	private int Y;

	public Coordinate(int _X, int _Y) {
		this.X = _X;
		this.Y = _Y;
	}

	public int getX() {
		return this.X;
	}

	public void setX(int _X) {
		this.X = _X;
	}

	public int getY() {
		return this.Y;
	}

	public void setY(int _Col) {
		this.Y = _Col;
	}

	public Piece getTypeOfLand() {
		Y = this.getY();
		X = this.getX();
		if (X == 3 && Y == 0) {
			return new Cave(2, this, 0);
		}
		if (X == 3 && Y == 8) {
			return new Cave(1, this, 0);
		}
		if (((X == 2) || (X == 4)) && Y == 0)
			return new Trap(2, this, 0);
		if (((X == 2) || (X == 4)) && Y == 8)
			return new Trap(1, this, 0);
		if (X == 3 && Y == 1)
			return new Trap(2, this, 0);
		if (X == 3 && Y == 7)
			return new Trap(1, this, 0);
		if (((X >= 1 && X <= 2) || (X >= 4 && X <= 5)) && (Y >= 3 && Y <= 5)) {
			return new Water(this);
		}
		return new Glass(this);
	}
}
