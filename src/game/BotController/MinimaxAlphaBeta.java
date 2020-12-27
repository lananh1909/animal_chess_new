package game.BotController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.Animal;
import Model.Cave;
import Model.Coordinate;
import Model.Piece;

public class MinimaxAlphaBeta {
	public static double count=0;
	
	ArrayList<Animal> playerAnimals;
	ArrayList<Animal> robotAnimals;
	private Piece[][] board;

	public MinimaxAlphaBeta(Piece[][] board, ArrayList<Animal> playerAnimals, ArrayList<Animal> robotAnimals) {
		this.board = board;
		this.playerAnimals=playerAnimals;
		this.robotAnimals=robotAnimals;
	}
	
	
	public ArrayList<Coordinate> minimaxRoot(int depth, boolean isFindMax) {
		ArrayList<Coordinate> bestBotMove= new ArrayList<Coordinate>();
		double bestValue= -9999999999.0;
		Random rand= new Random();
		Animal random= robotAnimals.get(rand.nextInt(robotAnimals.size()-1));
		Coordinate bestAnimal = new Coordinate(random.getCoordinate().getX(), random.getCoordinate().getY());
		Coordinate bestMove = new Coordinate(random.getCoordinate().getX(), random.getCoordinate().getY());
		
		for (int i=0 ; i< robotAnimals.size(); i++) {
			List<Coordinate> listSwap = new ArrayList<>();
			List<Coordinate> possibleMoves= robotAnimals.get(i).getPossibleMove(board);
			listSwap.add(new Coordinate(robotAnimals.get(i).getCoordinate().getX(), robotAnimals.get(i).getCoordinate().getY()));
//			System.out.println("add chose animal:"+ listSwap.get(0).getX()+"-"+ listSwap.get(0).getY()+ robotAnimals.get(i).getClass());
			
			for (Coordinate co2: possibleMoves) {
				if(co2.getTypeOfLand() instanceof Cave){
					bestAnimal.setX(robotAnimals.get(i).getCoordinate().getX());
					bestAnimal.setY(robotAnimals.get(i).getCoordinate().getY());
					bestMove.setX(co2.getX());
					bestMove.setY(co2.getY());
					bestBotMove.add(bestAnimal);
					bestBotMove.add(bestMove);
					return bestBotMove;
				}

				listSwap.add(new Coordinate(co2.getX(), co2.getY()));
//				System.out.println("add chose possible move:"+ listSwap.get(1).getX()+"-"+ listSwap.get(1).getY());

				
				if (board[co2.getX()][co2.getY()] instanceof Animal) {
					Animal animalTarget= (Animal)board[co2.getX()][co2.getY()];
					int indexOfAnimalTarget = playerAnimals.indexOf(animalTarget);
					swapRobot(listSwap.get(0), listSwap.get(1));
					playerAnimals.remove(animalTarget);
					double value = minimax(depth - 1, -9999999, 9999999, !isFindMax);
					double r = rand.nextInt(2);
					if (value > bestValue) {
						bestAnimal.setX(listSwap.get(0).getX()); 
						bestAnimal.setY(listSwap.get(0).getY());
						bestMove.setX(listSwap.get(1).getX()); 
						bestMove.setY(listSwap.get(1).getY());
//						System.out.println("bestAnimal :"+bestAnimal.getX()+"-"+bestAnimal.getY());
//						System.out.println("bestMove :"+bestMove.getX()+"-"+bestMove.getY());
						bestValue = value;
					} else if (value == bestValue && r == 0) {
						bestAnimal.setX(listSwap.get(0).getX());
						bestAnimal.setY(listSwap.get(0).getY());
						bestMove.setX(listSwap.get(1).getX());
						bestMove.setY(listSwap.get(1).getY());
//						System.out.println("bestAnimal :"+bestAnimal.getX()+"-"+bestAnimal.getY());
//						System.out.println("bestMove :"+bestMove.getX()+"-"+bestMove.getY());
						bestValue = value;
					}
					playerAnimals.add(indexOfAnimalTarget, animalTarget);
					swapRobot(listSwap.get(1), listSwap.get(0), animalTarget);
				}
				
				else {
					swapRobot(listSwap.get(0), listSwap.get(1));
					double value = minimax(depth - 1,-9999999, 9999999, !isFindMax);
					double r = rand.nextInt(2);
					if (value > bestValue) {
						bestAnimal.setX(listSwap.get(0).getX()); 
						bestAnimal.setY(listSwap.get(0).getY());
						bestMove.setX(co2.getX()); 
						bestMove.setY(co2.getY());
//						System.out.println("bestAnimal :"+bestAnimal.getX()+"-"+bestAnimal.getY());
//						System.out.println("bestMove :"+bestMove.getX()+"-"+bestMove.getY());
						bestValue = value;
					} else if (value == bestValue && r == 0) {
						bestAnimal.setX(listSwap.get(0).getX());
						bestAnimal.setY(listSwap.get(0).getY());
						bestMove.setX(listSwap.get(1).getX());
						bestMove.setY(listSwap.get(1).getY());
//						System.out.println("bestAnimal :"+bestAnimal.getX()+"-"+bestAnimal.getY());
//						System.out.println("bestMove :"+bestMove.getX()+"-"+bestMove.getY());
						bestValue = value;
					}
					swapRobot(listSwap.get(1), listSwap.get(0));
				}
				
				listSwap.remove(1);
			}
			listSwap.remove(0);
			possibleMoves= null;
		}
		bestBotMove.add(bestAnimal);
		bestBotMove.add(bestMove);
		return bestBotMove;
	}
	
public double minimax(int depth, double alpha, double beta,  boolean isFindMax) {
		count++;
		if(depth == 0) {
			return evaluateBoard(board);
		}
		if (isFindMax) {
			double bestValue= -9999999999.0;
			for (int i=0 ; i< robotAnimals.size(); i++) {
				List<Coordinate> listSwap = new ArrayList<>();
				List<Coordinate> possibleMoves= robotAnimals.get(i).getPossibleMove(board);
				listSwap.add(new Coordinate(robotAnimals.get(i).getCoordinate().getX(), robotAnimals.get(i).getCoordinate().getY()));
				
				for (Coordinate co2: possibleMoves) {
					listSwap.add(new Coordinate(co2.getX(), co2.getY()));
					if(co2.getTypeOfLand() instanceof Cave){
						swapRobot(listSwap.get(0), listSwap.get(1));
						double value = evaluateBoard(board) * depth;
						swapRobot(listSwap.get(1), listSwap.get(0));
						listSwap.remove(1);
						listSwap.remove(0);
						possibleMoves= null;
						return value;
					}
					
					if (board[co2.getX()][co2.getY()] instanceof Animal) {
						Animal animalTarget= (Animal)board[co2.getX()][co2.getY()];
						int indexOfAnimalTarget = playerAnimals.indexOf(animalTarget);
						swapRobot(listSwap.get(0), listSwap.get(1));
						playerAnimals.remove(animalTarget);
						double value = minimax(depth - 1, alpha, beta, !isFindMax);
						if (value > bestValue) {
							bestValue = value;
//							System.out.println("best value "+String.valueOf(depth)+" : " + robotAnimals.get(i).getClass()+" "+ bestValue);
						}
						alpha=Math.max(bestValue, alpha);

						playerAnimals.add(indexOfAnimalTarget, animalTarget);
						swapRobot(listSwap.get(1), listSwap.get(0), animalTarget);
						if (beta<= alpha) {
							listSwap.remove(1);
							listSwap.remove(0);
							possibleMoves= null;
							return bestValue;
						}

					}
					
					else {
						swapRobot(listSwap.get(0), listSwap.get(1));
						double value = minimax(depth - 1, alpha, beta, !isFindMax);
						if (value > bestValue) {
							bestValue = value;
//							System.out.println("best value "+String.valueOf(depth)+" : "+ robotAnimals.get(i).getClass()+" "+ bestValue);
						}
						alpha=Math.max(bestValue, alpha);

						swapRobot(listSwap.get(1), listSwap.get(0));
						if (beta<= alpha) {
							listSwap.remove(1);
							listSwap.remove(0);
							possibleMoves= null;
							return bestValue;
						}
					}
					listSwap.remove(1);
				}
				listSwap.remove(0);
				possibleMoves= null;
			}
			return bestValue;
		}
		else {
			double bestValue= 9999999999.0;
			for (int i=0 ; i< playerAnimals.size(); i++) {
				
				List<Coordinate> listSwap = new ArrayList<>();
				List<Coordinate> possibleMoves= playerAnimals.get(i).getPossibleMove(board);
				listSwap.add(new Coordinate(playerAnimals.get(i).getCoordinate().getX(), playerAnimals.get(i).getCoordinate().getY()));
				
				for (Coordinate co2: possibleMoves) {

					listSwap.add(new Coordinate(co2.getX(), co2.getY()));

					if(co2.getTypeOfLand() instanceof Cave){
						swapRobot(listSwap.get(0), listSwap.get(1));
						double value = evaluateBoard(board) * depth;
						//System.out.println(value);
						swapRobot(listSwap.get(1), listSwap.get(0));
						listSwap.remove(1);
						listSwap.remove(0);
						possibleMoves= null;
						return value;
					}
					
					if (board[co2.getX()][co2.getY()] instanceof Animal) {
						Animal animalTarget= (Animal)board[co2.getX()][co2.getY()];
						int indexOfAnimalTarget = robotAnimals.indexOf(animalTarget);
						swapRobot(listSwap.get(0), listSwap.get(1));
						robotAnimals.remove(animalTarget);
						double value = minimax(depth - 1, alpha, beta, !isFindMax);
						if (value < bestValue) {
							bestValue = value;
//							System.out.println("best value "+String.valueOf(depth)+" : " + robotAnimals.get(i).getClass()+" "+ bestValue);
						}
						beta=Math.min(bestValue, beta);
						swapRobot(listSwap.get(1), listSwap.get(0), animalTarget);
						robotAnimals.add(indexOfAnimalTarget, animalTarget);
						if (beta<= alpha) {
							listSwap.remove(1);
							listSwap.remove(0);
							possibleMoves= null;
							return bestValue;
						}

					}
					
					else {
						swapRobot(listSwap.get(0), listSwap.get(1));
						double value = minimax(depth - 1, alpha, beta, !isFindMax);
						if (value < bestValue) {
							bestValue = value;
//							System.out.println("best value "+String.valueOf(depth)+" : " + robotAnimals.get(i).getClass()+" "+ bestValue);
						}
						beta=Math.min(bestValue, beta);
						swapRobot(listSwap.get(1), listSwap.get(0));
						if (beta<= alpha) {
							listSwap.remove(1);
							listSwap.remove(0);
							possibleMoves= null;
							return bestValue;
						}

					}
					listSwap.remove(1);
				}
				listSwap.remove(0);
				possibleMoves= null;
			}
			return bestValue;
		}
	}
	
	public double evaluateBoard(Piece[][] board) {
		double valueBoard = 0;
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 9; j++) {
				if(board[i][j] instanceof Animal) {
					valueBoard += board[i][j].getValue() + ((Animal) board[i][j]).getPoValue();

				}
			}
		}
		return valueBoard;
	}
	
	
	public void swapRobot(Coordinate co1, Coordinate co2) { // swap in robot
		Piece p = board[co1.getX()][co1.getY()];
		p.getCoordinate().setX(co2.getX());
		p.getCoordinate().setY(co2.getY());

		board[co2.getX()][co2.getY()] = p;

		board[co1.getX()][co1.getY()] = co1.getTypeOfLand();
	}
	
	public void swapRobot(Coordinate co1, Coordinate co2, Animal animalTarget) { // swap in robot
		Piece p = board[co1.getX()][co1.getY()];
		p.getCoordinate().setX(co2.getX());
		p.getCoordinate().setY(co2.getY());

		board[co2.getX()][co2.getY()] = p;

		board[co1.getX()][co1.getY()] = animalTarget;
	}
}
