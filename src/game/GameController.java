package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import Model.Animal;
import Model.Cat;
import Model.Cave;
import Model.Cheetah;
import Model.Coordinate;
import Model.Dog;
import Model.Elephant;
import Model.Glass;
import Model.Lion;
import Model.Mouse;
import Model.Piece;
import Model.Tiger;
import Model.Trap;
import Model.Water;
import Model.Wolf;
import game.BotController.MinimaxAlphaBeta;
import game.BotController.MinimaxMove;
import game.BotController.PlayerMinimax;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import sun.awt.geom.AreaOp;

public class GameController implements Initializable {
	private int turn = 1;

	@FXML
	private GridPane boardGame;
	@FXML
	private AnchorPane player;
	@FXML
	private AnchorPane playerImage;
	@FXML
	private AnchorPane AI;
	@FXML
	private AnchorPane AIImage;

	private static Piece[][] board = new Piece[7][9];

	private List<Coordinate> swapList = new ArrayList<>();
	ArrayList<Node> listNode = new ArrayList();
	
	private ArrayList<Animal> playerAnimals = new ArrayList<Animal>();
	private ArrayList<Animal> robotAnimals = new ArrayList<Animal>();
	
	public void getAllAnimal(Piece[][] board) {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j].isAnimal()) {
					if (board[i][j].getColor() == 1)
						playerAnimals.add((Animal) board[i][j]);
					else
						robotAnimals.add((Animal) board[i][j]);
				}
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AIImage.setStyle("-fx-background-image: url('/image/lionImage.png');");
		playerImage.setStyle("-fx-background-image: url('/image/playerImage.png');");
		initBoard();
		initMap();
		
		getAllAnimal(board);
//		while (!checkWinner()){
//			if (turn == 1) {
//				playerMove();
//				turn = 2;
//			} else if (turn == 2){
//				minimaxBotMove();
//				turn = 1;
//			}
//		}
	}

	@FXML
	public void addGridEvent() {
		if (turn == 1) {
//			playerMove();
//			turn = 2;
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			boardGame.getChildren().forEach(piece -> {
				piece.setOnMouseClicked(new EventHandler<Event>() {
					@Override
					public void handle(Event event) {
						Node clickedNode = ((MouseEvent) event).getPickResult().getIntersectedNode();
						int X = GridPane.getColumnIndex(clickedNode);
						int Y = GridPane.getRowIndex(clickedNode);
						System.out.println(X + "-" + Y);
						System.out.println(swapList.size() + "\n");

						// neu click chon con vat
						if (board[X][Y].isAnimal() && board[X][Y].getColor() == turn) {
							Animal a = (Animal) board[X][Y];
							// neu chua chon thu thi chon thu
							if (swapList.isEmpty()) {
								swapList.add(new Coordinate(X, Y));
								System.out.println(
										"ban da chon con" + swapList.get(0).getX() + "-" + swapList.get(0).getY());
								listNode.add(clickedNode);
								listNode.get(0).setEffect(new DropShadow(30, Color.YELLOW));
								showPossibleMove(a.getPossibleMove(board));
							}
							// neu chon thu roi thi an
							else if (swapList.size() == 1) {
								Animal b = (Animal) board[swapList.get(0).getX()][swapList.get(0).getY()];
								hidePossibleMove(b.getPossibleMove(board));

								swapList.remove(0);
								swapList.add(new Coordinate(X, Y));
								System.out.println(swapList.size());
								listNode.get(0).setEffect(null);
								listNode.remove(0);
								listNode.add(clickedNode);
								listNode.get(0).setEffect(new DropShadow(30, Color.YELLOW));

								showPossibleMove(a.getPossibleMove(board));
							}
						} else {
							if (swapList.isEmpty()) {
								System.out.println("khong the chon dia hinh");
							} else if (swapList.size() == 1) {
								swapList.add(new Coordinate(X, Y));

								playerMove(); // move animal

								listNode.get(0).setEffect(null);
								listNode.remove(0);

								if (turn==2) minimaxBotMove();
								turn =1;
							}
						}
					}
				});
			});
		} // end if turn == 1
//		else if (turn == 2){
//			minimaxBotMove();
//			turn = 1;
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

	public boolean checkWinner(){
		if(board[3][0] instanceof Animal){
			System.out.println("May thang");
			return true;
		}
		if(board[3][8] instanceof Animal){
			System.out.println("Player thang");
			return true;
		}
		return false;
	}

	public void playerMove() {
//		PlayerMinimax minimaxRoot = new PlayerMinimax(board, playerAnimals, robotAnimals);
//		ArrayList<Coordinate> minimaxBotMove = minimaxRoot.minimaxRoot(5, false);
//		Animal a = (Animal) board[minimaxBotMove.get(0).getX()][minimaxBotMove.get(0).getY()];
//
//		if(board[minimaxBotMove.get(1).getX()][minimaxBotMove.get(1).getY()] instanceof Animal) {
//			robotAnimals.remove(board[minimaxBotMove.get(1).getX()][minimaxBotMove.get(1).getY()]);
//		}
//		swap(minimaxBotMove.get(0), minimaxBotMove.get(1));
//		initPiece(minimaxBotMove.get(0));
//		initPiece(minimaxBotMove.get(1));
//		System.out.println("Nguoi choi da di chuyen "+board[minimaxBotMove.get(1).getX()][minimaxBotMove.get(1).getY()].getClass() + " tu :"+minimaxBotMove.get(0).getX() + "-" +
//				minimaxBotMove.get(0).getY()+"\tden"+minimaxBotMove.get(1).getX()+"-"+minimaxBotMove.get(1).getY());
//		minimaxBotMove.remove(1);
//		minimaxBotMove.remove(0);
		Piece p = board[swapList.get(0).getX()][swapList.get(0).getY()];
		Animal a = (Animal) p;
		hidePossibleMove(a.getPossibleMove(board));
		List<Coordinate> possibleMoves = a.getPossibleMove(board);
		for (Coordinate possibleMove : possibleMoves) {
			System.out.println("check: " + possibleMove.getX() + "-" + possibleMove.getY());
			System.out.println("check: " + swapList.get(1).getX() + "-" + swapList.get(1).getY() + "\n");
			if (possibleMove.getX() == swapList.get(1).getX() && possibleMove.getY() == swapList.get(1).getY()) {
				if (board[swapList.get(1).getX()][swapList.get(1).getY()] instanceof Animal) {
					robotAnimals.remove(board[swapList.get(1).getX()][swapList.get(1).getY()]);
				}
				swap(swapList.get(0), swapList.get(1)); // attack animal

				initPiece(swapList.get(0));
				initPiece(swapList.get(1));

				System.out.println("da di chuyen toi " + swapList.get(1).getX() + "-" + swapList.get(1).getY());
				swapList.remove(1);
				swapList.remove(0);

				return;
			}
		}
		swapList.remove(1);
		swapList.remove(0);
		System.out.println("nuoc di khong hop le ... vui long chon lai");
	}
	
	public void minimaxBotMove() {
		
//		MinimaxMove minimaxRoot= new MinimaxMove(board, playerAnimals, robotAnimals);
		MinimaxAlphaBeta minimaxRoot= new MinimaxAlphaBeta(board, playerAnimals, robotAnimals);
		ArrayList<Coordinate> minimaxBotMove = minimaxRoot.minimaxRoot(3, true);
		Animal a = (Animal) board[minimaxBotMove.get(0).getX()][minimaxBotMove.get(0).getY()];
		
		if(board[minimaxBotMove.get(1).getX()][minimaxBotMove.get(1).getY()] instanceof Animal) {
			playerAnimals.remove(board[minimaxBotMove.get(1).getX()][minimaxBotMove.get(1).getY()]);
		}
		
		swap(minimaxBotMove.get(0), minimaxBotMove.get(1));
		
		initPiece(minimaxBotMove.get(0));
		initPiece(minimaxBotMove.get(1));
		
		System.out.println("tong so nut da duyet= "+ minimaxRoot.count);
		
		System.out.println("ro bot da di chuyen "+board[minimaxBotMove.get(1).getX()][minimaxBotMove.get(1).getY()].getClass() + " tu :"+minimaxBotMove.get(0).getX() + "-" + 
				minimaxBotMove.get(0).getY()+"\tden"+minimaxBotMove.get(1).getX()+"-"+minimaxBotMove.get(1).getY());
		if(minimaxBotMove.get(1).getTypeOfLand() instanceof Cave){
			System.out.println("Máy thắng!!!!");
			return;
		}
		minimaxBotMove.remove(1);
		minimaxBotMove.remove(0);
		
	}
	
	public void swap(Coordinate co1, Coordinate co2) {
		Piece p = board[co1.getX()][co1.getY()];
		p.getCoordinate().setX(co2.getX());
		p.getCoordinate().setY(co2.getY());

		board[co2.getX()][co2.getY()] = p;

		board[co1.getX()][co1.getY()] = co1.getTypeOfLand();
		if (turn == 1) {
			turn = 2;
		} else
			turn = 1;
	}
	

	public void setImage(String url, int X, int Y) {

		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream(url);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Image image = new Image(inputstream);
		ImageView pieceImage = new ImageView();
		pieceImage.getStyleClass().add("imgView");
		pieceImage.setImage(image);
		boardGame.add(pieceImage, X, Y);
	}

	public void initPiece(Coordinate co) {
		setImage(board[co.getX()][co.getY()].getImage(), co.getX(), co.getY());

	}

	public void initMap() {
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 9; j++) {
				setImage(board[i][j].getImage(), i, j);
			}
		}
	}

	public static void initBoard1() {
		board[0][0] = new Glass(new Coordinate(0, 0));
		board[1][0] = new Glass(new Coordinate(1, 0));
		board[2][0] = new Trap(2, new Coordinate(2, 0), 0);
		board[3][0] = new Cave(2, new Coordinate(3, 0), 0);
		board[4][0] = new Trap(2, new Coordinate(4, 0), 0);
		board[5][0] = new Glass(new Coordinate(5, 0));
		board[6][0] = new Glass(new Coordinate(6, 0));

		board[0][1] = new Glass(new Coordinate(0, 1));
		board[1][1] = new Glass(new Coordinate(1, 1));
		board[2][1] = new Dog(2, new Coordinate(2, 1), 400);
		board[3][1] = new Trap(2, new Coordinate(3, 1),0);
		board[4][1] = new Wolf(2, new Coordinate(4, 1),300);
		board[5][1] = new Glass(new Coordinate(5, 1));
		board[6][1] = new Cat(2, new Coordinate(6, 1),200);

		board[0][2] = new Glass(new Coordinate(0, 2));
		board[1][2] = new Glass(new Coordinate(1, 2));
		board[2][2] = new Glass(new Coordinate(2, 2));
		board[3][2] = new Glass(new Coordinate(3, 2));
		board[4][2] = new Glass(new Coordinate(4, 2));
		board[5][2] = new Elephant(2, new Coordinate(5, 2),1000);
		board[6][2] = new Glass(new Coordinate(6, 2));

		board[0][3] = new Glass(new Coordinate(0, 3));
		board[1][3] = new Water(new Coordinate(1, 3));
		board[2][3] = new Mouse(2, new Coordinate(2, 3), 500);
		board[3][3] = new Glass(new Coordinate(3, 3));
		board[4][3] = new Water(new Coordinate(4, 3));
		board[5][3] = new Water(new Coordinate(5, 3));
		board[6][3] = new Glass(new Coordinate(6, 3));

		board[0][4] = new Glass(new Coordinate(0, 4));
		board[1][4] = new Water(new Coordinate(1, 4));
		board[2][4] = new Water(new Coordinate(2, 4));
		board[3][4] = new Glass(new Coordinate(3, 4));
		board[4][4] = new Water(new Coordinate(4, 4));
		board[5][4] = new Water(new Coordinate(5, 4));
		board[6][4] = new Glass(new Coordinate(6, 4));

		board[0][5] = new Glass(new Coordinate(0, 5));
		board[1][5] = new Water(new Coordinate(1, 5));
		board[2][5] = new Water(new Coordinate(2, 5));
		board[3][5] = new Glass(new Coordinate(3, 5));
		board[4][5] = new Water(new Coordinate(4, 5));
		board[5][5] = new Water(new Coordinate(5, 5));
		board[6][5] = new Glass(new Coordinate(6, 5));

		board[0][6] = new Glass(new Coordinate(0, 6));
		board[1][6] = new Glass(new Coordinate(1, 6));
		board[2][6] = new Glass(new Coordinate(2, 6));
		board[3][6] = new Cheetah(2, new Coordinate(3, 6), 500);
		board[4][6] = new Glass(new Coordinate(4, 6));
		board[5][6] = new Glass(new Coordinate(5, 6));
		board[6][6] = new Glass(new Coordinate(6, 6));

		board[0][7] = new Glass(new Coordinate(0, 7));
		board[1][7] = new Glass(new Coordinate(1, 7));
		board[2][7] = new Glass(new Coordinate(2, 7));
		board[3][7] = new Trap(1, new Coordinate(3, 7),0);
		board[4][7] = new Wolf(1, new Coordinate(4, 7),-300);
		board[5][7] = new Elephant(1, new Coordinate(5, 7), -1000);
		board[6][7] = new Glass(new Coordinate(6, 7));

		board[0][8] = new Glass(new Coordinate(0, 8));
		board[1][8] = new Lion(2, new Coordinate(1, 8), 900);
		board[2][8] = new Trap(1, new Coordinate(2, 8),0);
		board[3][8] = new Cave(1, new Coordinate(3, 8),0);
		board[4][8] = new Dog(1, new Coordinate(4, 8),-400);
		board[5][8] = new Glass(new Coordinate(5, 8));
		board[6][8] = new Tiger(2, new Coordinate(6, 8), 800);
	}

	public static void initBoard() {
		board[0][0] = new Lion(2, new Coordinate(0, 0), 900);
		board[1][0] = new Glass(new Coordinate(1, 0));
		board[2][0] = new Trap(2, new Coordinate(2, 0), 0);
		board[3][0] = new Cave(2, new Coordinate(3, 0), 0);
		board[4][0] = new Trap(2, new Coordinate(4, 0), 0);
		board[5][0] = new Glass(new Coordinate(5, 0));
		board[6][0] = new Tiger(2, new Coordinate(6, 0),800);

		board[0][1] = new Glass(new Coordinate(0, 1));
		board[1][1] = new Dog(2, new Coordinate(1, 1), 300);
		board[2][1] = new Glass(new Coordinate(2, 1));
		board[3][1] = new Trap(2, new Coordinate(3, 1),0);
		board[4][1] = new Glass(new Coordinate(4, 1));
		board[5][1] = new Cat(2, new Coordinate(5, 1),200);
		board[6][1] = new Glass(new Coordinate(6, 1));

		board[0][2] = new Mouse(2, new Coordinate(0, 2),500);
		board[1][2] = new Glass(new Coordinate(1, 2));
		board[2][2] = new Cheetah(2, new Coordinate(2, 2),500);
		board[3][2] = new Glass(new Coordinate(3, 2));
		board[4][2] = new Wolf(2, new Coordinate(4, 2),400);
		board[5][2] = new Glass(new Coordinate(5, 2));
		board[6][2] = new Elephant(2, new Coordinate(6, 2),1000);

		board[0][3] = new Glass(new Coordinate(0, 3));
		board[1][3] = new Water(new Coordinate(1, 3));
		board[2][3] = new Water(new Coordinate(2, 3));
		board[3][3] = new Glass(new Coordinate(3, 3));
		board[4][3] = new Water(new Coordinate(4, 3));
		board[5][3] = new Water(new Coordinate(5, 3));
		board[6][3] = new Glass(new Coordinate(6, 3));

		board[0][4] = new Glass(new Coordinate(0, 4));
		board[1][4] = new Water(new Coordinate(1, 4));
		board[2][4] = new Water(new Coordinate(2, 4));
		board[3][4] = new Glass(new Coordinate(3, 4));
		board[4][4] = new Water(new Coordinate(4, 4));
		board[5][4] = new Water(new Coordinate(5, 4));
		board[6][4] = new Glass(new Coordinate(6, 4));

		board[0][5] = new Glass(new Coordinate(0, 5));
		board[1][5] = new Water(new Coordinate(1, 5));
		board[2][5] = new Water(new Coordinate(2, 5));
		board[3][5] = new Glass(new Coordinate(3, 5));
		board[4][5] = new Water(new Coordinate(4, 5));
		board[5][5] = new Water(new Coordinate(5, 5));
		board[6][5] = new Glass(new Coordinate(6, 5));

		board[0][6] = new Elephant(1, new Coordinate(0, 6),-1000);
		board[1][6] = new Glass(new Coordinate(1, 6));
		board[2][6] = new Wolf(1, new Coordinate(2, 6),-400);
		board[3][6] = new Glass(new Coordinate(3, 6));
		board[4][6] = new Cheetah(1, new Coordinate(4, 6),-500);
		board[5][6] = new Glass(new Coordinate(5, 6));
		board[6][6] = new Mouse(1, new Coordinate(6, 6),-500);

		board[0][7] = new Glass(new Coordinate(0, 7));
		board[1][7] = new Cat(1, new Coordinate(1, 7),-200);
		board[2][7] = new Glass(new Coordinate(2, 7));
		board[3][7] = new Trap(1, new Coordinate(3, 7),0);
		board[4][7] = new Glass(new Coordinate(4, 7));
		board[5][7] = new Dog(1, new Coordinate(5, 7),-300);
		board[6][7] = new Glass(new Coordinate(6, 7));

		board[0][8] = new Tiger(1, new Coordinate(0, 8),-800);
		board[1][8] = new Glass(new Coordinate(1, 8));
		board[2][8] = new Trap(1, new Coordinate(2, 8),0);
		board[3][8] = new Cave(1, new Coordinate(3, 8),0);
		board[4][8] = new Trap(1, new Coordinate(4, 8),0);
		board[5][8] = new Glass(new Coordinate(5, 8));
		board[6][8] = new Lion(1, new Coordinate(6, 8),-900);
	}
	
	@FXML
	public void startGame(ActionEvent e) throws IOException {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Boardgame.fxml"));
		Parent root = (Parent) loader.load();
		GameController gameController = loader.getController();
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	@FXML
	public void returnToMainTemplate(ActionEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Template.fxml"));
		Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(parent);
		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		stage.setScene(scene);
	}
	
	@FXML
	public void instruction(ActionEvent e) {
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Instruction.fxml"));
		Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Scene scene = new Scene(parent);
		scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
		stage.setScene(scene);
	}
	
	@FXML
	public void surrender(ActionEvent e) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText("G�");
		alert.show();
	}

	public Node getNodeByRowColumnIndex(final int row, final int column) {
		Node result = null;
		ObservableList<Node> childrens = boardGame.getChildren();
		for (Node node : childrens) {
			if (boardGame.getRowIndex(node) == row && boardGame.getColumnIndex(node) == column) {
				result = node;
				break;
			}
		}
		return result;
	}

	public void showPossibleMove(List<Coordinate> possibleMove) {
		for (Coordinate coordinate : possibleMove) {
			Circle cr = new Circle();
			cr.setFill(Color.RED);
			cr.setRadius(5);
			getNodeByRowColumnIndex(coordinate.getY(), coordinate.getX()).setEffect(new DropShadow(15, Color.RED));
			boardGame.add(cr, coordinate.getX(), coordinate.getY());
		}
	}

	public void hidePossibleMove(List<Coordinate> possibleMove) {
		for (Coordinate coordinate : possibleMove) {
			getNodeByRowColumnIndex(coordinate.getY(), coordinate.getX()).setEffect(null);
		}
		ObservableList<Node> childrens = boardGame.getChildren();
		List<Node> listNode = new ArrayList<Node>();
		System.out.println("hu" + childrens.size());
		for (Node node : childrens) {
			if (node instanceof Circle) {
				listNode.add(node);
			}
		}
		for (int i = 0; i < listNode.size(); i++) {
			boardGame.getChildren().remove(listNode.get(i));
		}
	}
	
	public void khongBietDatTenTheNaoLuon() {
		if(turn == 1) {
			player.setStyle("-fx-opacity: 1.0");
			AI.setStyle("-fx-opacity: 0.3");
		} else {
			AI.setStyle("-fx-opacity: 1.0");
			player.setStyle("-fx-opacity: 0.3");
		}
	}
}
