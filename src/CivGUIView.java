import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import characters.CivCharacter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;


@SuppressWarnings("deprecation")
public class CivGUIView extends Application implements Observer{
	
	private static final int DIMENSION = 10;
	private static final Map<String, Image> iconsMap = getIcons();
	private CivModel model;
	private CivController controller;
	private BorderPane borderPane;
	private GridPane bigGridPane;
	private VBox vbox;
	private MenuBar menuBar;
	private TilePane tilePane;
	private String currChar;
	private int currRow;
	private int currCol;

	public static void main(String[] args) {
		Application.launch();
	}

	public CivGUIView() {
		model = new CivModel();
		controller = new CivController(model);
		borderPane = new BorderPane();
		menuBar = new MenuBar();
		bigGridPane = new GridPane();
		vbox = new VBox();
		tilePane = new TilePane();
		currChar = null;
		currRow = -1;
		currCol = -1;
		model.addObserver(this);
	}

	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("CIV");
		// Configure border pane structure
		borderPane.setTop(menuBar);
		borderPane.setCenter(bigGridPane);
		borderPane.setRight(vbox);
		borderPane.setBottom(tilePane);
		// Add contents to panes
		addMenuBar();
		addGridPane(primaryStage);
		addVBox();
		addTilePane();
		// Final scene
		Scene scene = new Scene(borderPane, 900, 700);
		primaryStage.setScene(scene);
		//Start the game
		primaryStage.show();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		model = (CivModel) o;
		for (int i=0; i<DIMENSION; i++) {
			for (int j=0; j<DIMENSION; j++) {
				CivCell cell = model.getCell(j, i);
				updateCell(i, j, cell);
			}
		}
		
	}

	private void addVBox() {
		GridPane charsPane = new GridPane();
		addCharsPane(charsPane);
		charsPane.setHgap(10);
		charsPane.setVgap(10);
		charsPane.setPadding(new Insets(5));
		vbox.getChildren().add(0, charsPane);
		vbox.setStyle("-fx-border-color: black;\n" +
                "-fx-border-insets: 2;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-style: dashed;\n");
		vbox.setPrefSize(250, 700);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
	}
	
	private void addCharsPane(GridPane charsPane) {
		spawnArcherBtn(charsPane);
		spawnCatapultBtn(charsPane);
		spawnGuardBtn(charsPane);
		spawnKnightBtn(charsPane);
		spawnWarriorBtn(charsPane);
		addEndBtn(charsPane);
	}
	
	private void spawnArcherBtn(GridPane charsPane) {
		Button archer = new Button("Archer");
		ImageView view = getSpawnView("archer");
		archer.setGraphic(view);
		charsPane.add(archer, 0, 0);
		archer.setOnAction((event) -> {
			controller.setSpawned();
			currChar = "Archer";
		});
		archer.setPrefHeight(48);
		archer.setPrefWidth(120);
	}
	
	private void spawnCatapultBtn(GridPane charsPane) {
		Button catapult = new Button("Catapult");
		ImageView view = getSpawnView("catapult");
		catapult.setGraphic(view);
		charsPane.add(catapult, 0, 1);
		catapult.setOnAction((event) -> {
			controller.setSpawned();
			currChar = "Catapult";
		});
		catapult.setPrefHeight(48);
		catapult.setPrefWidth(120);
	}
	
	private void spawnGuardBtn(GridPane charsPane) {
		Button guard = new Button("Guard");
		ImageView view = getSpawnView("guard");
		guard.setGraphic(view);
		charsPane.add(guard, 0, 2);
		guard.setOnAction((event) -> {
			controller.setSpawned();
			currChar = "Guard";
		});
		guard.setPrefHeight(48);
		guard.setPrefWidth(120);
	}
	
	private void spawnKnightBtn(GridPane charsPane) {
		Button knight = new Button("Knight");
		ImageView view = getSpawnView("knight");
		knight.setGraphic(view);
		charsPane.add(knight, 1, 0);
		knight.setOnAction((event) -> {
			controller.setSpawned();
			currChar = "Knight";
		});
		knight.setPrefHeight(48);
		knight.setPrefWidth(120);
	}
	
	private void spawnWarriorBtn(GridPane charsPane) {
		Button warrior = new Button("Warrior");
		ImageView view = getSpawnView("warrior");
		warrior.setGraphic(view);
		charsPane.add(warrior, 1, 1);
		warrior.setOnAction((event) -> {
			controller.setSpawned();
			currChar = "Warrior";
		});
		warrior.setPrefHeight(48);
		warrior.setPrefWidth(120);
	}
	
	private void addEndBtn(GridPane charsPane) {
		Button endBtn = new Button("End Button");
		endBtn.setStyle("-fx-font-size: 15px;\n"
				+ "    -fx-padding: 1px;\n"
				+ "    -fx-background-color: red;\n"
				+ "    -fx-border-size: 1px;\n"
				+ "    -fx-border-style: solid");
		endBtn.setPrefHeight(48);
		endBtn.setPrefWidth(120);
		charsPane.add(endBtn, 1, 2);
		endBtn.setOnAction((event) -> {
			controller.endTurn("Human");
			controller.computerMove();
			controller.endTurn("Computer");
		});
	}
	
	private ImageView getSpawnView(String character) {
		String url = "src/Icons/" + character +"_icon.png";
		File file = new File(url);
		Image img = new Image(file.toURI().toString());
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(40);
		imgView.setFitWidth(40);
		return imgView;
	}

	private void addTilePane() {
		int humanMoney = 0;
		int computerMoney = 0;
		Label score = new Label("Human: "+humanMoney+"$ - Computer: "+computerMoney+"$");
		score.setStyle("-fx-font-size: 15px;\n"
				+"		-fx-padding: 7px;\n");
		tilePane.getChildren().add(score);
		tilePane.setPrefHeight(100);
	}

	private void addGridPane(Stage primaryStage) {
		bigGridPane.setMaxWidth(630);
		bigGridPane.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(0), Insets.EMPTY)));
		bigGridPane.setPadding(new Insets(8));
		addStackPane(primaryStage);
	}

	private void addStackPane(Stage primaryStage) {
		for (int i = 0; i < DIMENSION; i++) {
			GridPane innerGrid = new GridPane();
			for (int j = 0; j < DIMENSION; j++) {
				StackPane stack = new StackPane();
				stack.setBorder(new Border(new BorderStroke(Color.BLACK, 
						BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				stack.setPadding(new Insets(10));
				File file = new File("src/icons/archer_icon.png");
				Image img = new Image(file.toURI().toString());
				ImageView imgView = new ImageView(img);
				imgView.setFitHeight(40);
				imgView.setFitWidth(40);
				imgView.setVisible(false);
				stack.getChildren().add(imgView);
				addEvent(stack, i, j, primaryStage);
				innerGrid.addRow(j, stack);
			}
			bigGridPane.addColumn(i, innerGrid);
		}
		
	}

	private void addEvent(StackPane stack, int i, int j, Stage primaryStage) {
		Popup popup = new Popup();
		stack.setOnMouseClicked((event) -> {
			if (event.getButton() == MouseButton.SECONDARY) {
				currRow = i;
				currCol = j;
				
				// String info = getInfo(i, j);
				String info = "This is a sample popup\nsecond line";
				Label label = new Label(info);
				label.setPadding(new Insets(5));
				label.setMinWidth(230);
				// linear-gradient(#808080, #707070)
				label.setStyle("\n"
						+ "    -fx-text-fill: white;\n"
						+ "    -fx-font-family: \"Courier New\";\n"
						+ "    -fx-font-weight: bold;\n"
						+ "    -fx-background-color: #b5651d;\n"
						+ "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
				popup.setX(primaryStage.getX()+655);
				popup.setY(primaryStage.getY()+55);
				popup.getContent().add(label);
				popup.show(primaryStage);
			}
		});
		stack.setOnMouseExited((event) -> {
			popup.hide();
		});
		
		stack.setOnMouseReleased((event) -> {
			stack.setEffect(null);
		});
		stack.setOnMousePressed((event) -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				stack.setEffect(new DropShadow());
				controller.handleClick(j, i, currChar);
			}
		});
	}

	private void addMenuBar() {
		// MenuItems
		MenuItem newGame = new MenuItem("New Game");
		// Menu
		Menu file = new Menu("File");
		file.getItems().add(newGame);
		// Menu bar
		menuBar.getMenus().add(file);
	}
	
	private void updateCell(int x, int y, CivCell cell) {
		GridPane rowPane = (GridPane) bigGridPane.getChildren().get(x);
		StackPane stack = (StackPane) rowPane.getChildren().get(y);
		String player = cell.getPlayer();
		if (player != null) {
			Color color = Color.WHITE;
			if (player.equals("Computer")) {
				color = Color.AQUA;
			}
			CivCharacter character = cell.getCharacter();
			String name = character.getName();
			Image img = iconsMap.get(name);
			ImageView imgView = new ImageView(img);
			imgView.setFitHeight(40);
			imgView.setFitWidth(40);
			imgView.setVisible(true);
			stack.getChildren().clear();
			stack.getChildren().add(imgView);
			stack.setBackground(new Background(new BackgroundFill(color, new CornerRadii(0), Insets.EMPTY)));;
		} else {
			ImageView imgView = (ImageView) stack.getChildren().get(0);
			imgView.setVisible(false);
			stack.setBackground(new Background(new BackgroundFill(Color.GREEN, new CornerRadii(0), Insets.EMPTY)));;
		}
	}
	
	private static Map<String, Image> getIcons(){
		Map<String, Image> mapIcons = new HashMap<>();
		File file_archer = new File("src/icons/archer_icon.png");
		Image img_archer = new Image(file_archer.toURI().toString());
		mapIcons.put("Archer", img_archer);
		File file_catapult = new File("src/icons/catapult_icon.png");
		Image img_catapult = new Image(file_catapult.toURI().toString());
		mapIcons.put("Catapult", img_catapult);
		File file_guard = new File("src/icons/guard_icon.png");
		Image img_guard = new Image(file_guard.toURI().toString());
		mapIcons.put("Guard", img_guard);
		File file_knight = new File("src/icons/knight_icon.png");
		Image img_knight = new Image(file_knight.toURI().toString());
		mapIcons.put("Knight", img_knight);
		File file_warrior = new File("src/icons/warrior_icon.png");
		Image img_warrior = new Image(file_warrior.toURI().toString());
		mapIcons.put("Warrior", img_warrior);
		return mapIcons;
	}

}
