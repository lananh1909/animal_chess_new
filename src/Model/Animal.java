package Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Animal extends Piece {
	public final double MAX = 10000000;
	
	protected double[][] positionValue;

	public int[][] move = new int[7][9];

	public double getPoValue() {
		return this.positionValue[this.getCoordinate().getY()][this.getCoordinate().getX()];
	}

	public double[][] reserveaValue(double[][] positionValue){
		double[][] newArr = new double[9][7];
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 7; j++){
				newArr[8-i][6-j] = - positionValue[i][j];
			}
		}
		return newArr;
	}

	public Animal(int color, int point, boolean isAnimal, String name, Coordinate coordinate, String image, int value) {
		super(color, point, isAnimal, name, coordinate, image, value);
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 9; j++){
				move[i][j] = 0;
			}
		}
	}

	public boolean checkTrap(Piece p){
		if(p.isAnimal() && this.color != p.getColor()){
			Coordinate co = p.getCoordinate();
			Piece type = co.getTypeOfLand();
			if(type instanceof Trap && type.getColor()==this.getColor()){
				return true;
			}
		}
		return false;
	}

	public void resetMove(){
		for(int i = 0; i < 7; i++){
			for(int j = 0; j < 9; j++){
				move[i][j] = 0;
			}
		}
	}

	public boolean checkPiece(Piece p) {
		if (p.getCoordinate().getTypeOfLand() instanceof Water) {
			return false;
		}

		if (p.isAnimal() && p.color != this.color) {
			if (p.point <= this.point)
				return true;
			else
				return false;
		}

		if (p.isAnimal() && p.color == this.color)
			return false;

		if (p.color == this.color && p.getCoordinate().getTypeOfLand() instanceof Cave)
			return false;

		return true;
	}

	public List<Coordinate> getPossibleMove(Piece[][] board) {
		Coordinate co = this.getCoordinate();
		int X = co.getX();
		int Y = co.getY();
		List<Coordinate> possibleMove = new ArrayList<>();

		if ((X + 1) <= 6) {
			Piece right = board[X + 1][Y];
			if (checkPiece(right) || checkTrap(right)) {
				possibleMove.add(right.getCoordinate());
			}
		}

		if ((X - 1) >=0) {
			Piece left = board[X - 1][Y];
			if (checkPiece(left) || checkTrap(left)) {
				possibleMove.add(left.getCoordinate());
			}
		}
		
		if ((Y - 1) >=0) {
			Piece top = board[X][Y-1];
			if (checkPiece(top) || checkTrap(top)) {
				possibleMove.add(top.getCoordinate());
			}
		}
		
		if ((Y + 1) <=8) {
			Piece bottom = board[X][Y+1];
			if (checkPiece(bottom) || checkTrap(bottom)) {
				possibleMove.add(bottom.getCoordinate());
			}
		}

		return possibleMove;
	}

	// glass = 10, water = 20, trap = 30, cave = 40

}
