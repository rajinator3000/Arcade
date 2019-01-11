package cs1302.arcade;

/**
 * The imnplemention of the Arcade that
 * allows the user to pick between 2 games,
 * Tetris and Minesweeper.
 *
 * @author Raj Patel and Logan Bryant
 */

import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javafx.scene.layout.BackgroundImage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ArcadeApp extends Application {
	//Initializes Variables
	Boolean[][] mineArray;
	String[][] checkArray;
	String initials = "Your Name";
	String initialsMine = "Your Name";
	VBox vboxHighScore = new VBox();
	private int tetrisHigh = 0;
	private int mineHigh = 0;
	private int mineHighTemp = 0;

	/**
	 * Runs the actual game so that the user is able
	 * to play the game. Creates an instance of the class.
	 */
	private void runMinesweeper() {
		MineSweeper minesweeper = new MineSweeper();

	}

	/**
	 * Runs the implementation for the game Tetris
	 * so that the user is able to play it. Contains
	 * pop-up box messages and the actual game window.
	 */
	private void runTetris() {
		int gameOverCount = 0;

		Thread thread = new Thread();
		JDialog f = new JDialog();
		JOptionPane.showMessageDialog(f, "Welcome to Tetris!\nHere is how to play:\nThe arrow keys control the piece.\nLeft arrow moves it left, right arrow moves it right"
				+ ", and the up arrow rotates the piece.\nThe down arrow will make the piece fall faster.");
		f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setSize(550, 590);
		Tetris game = new Tetris();
		game.init();
		f.add(game);
		f.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);

		//Adds keylisteners
		f.addKeyListener(game.getKey());
		//Thread that drops piece
		thread = new Thread() {
			int multiplierCount = 0;
			int brickFallCount = 0;
			int temp = gameOverCount;
			int brick = game.getBrickDropCount();
			int times = 0;
			int level = 1;

			@Override
			public void run() {
				while(true) {
					try {
						int difficulty = game.setDifficulty();
						Thread.sleep(difficulty);

						game.dropByOne();

						if(!game.getGameOver() && temp ==0) {
							if(game.getScore()>tetrisHigh) {
								tetrisHigh = game.getScore();
								JOptionPane.showMessageDialog(f, "High Score! Congratulations:");			//Display Dialog box if highscore has been beaten
								initials = JOptionPane.showInputDialog(f, "Enter initials here:");
								f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));

							} else {
								JOptionPane.showMessageDialog(f, "Game Over. Please click OK to exit.");
								temp++;
								f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
							}
						}
					} catch (Exception e ) {

					}
				}
			}
		};



		f.setMinimumSize(new Dimension(550,590));	//Sets min and max size of frame
		f.setMaximumSize(new Dimension(550,590));
		thread.start();
		f.setVisible(true);
	} // runTetris


	@Override
	/**
	 * @inheritDoc
	 *
	 * Sets up the first window that the user sees, the
	 * arcade window. Contains the delay with the intro
	 * animation and the buttons that go into each game
	 * (and images of each game).
	 */
	public void start(Stage stage) {
		//Setup
		BorderPane borderPane = new BorderPane();
		BorderPane borderPane2 = new BorderPane();
		Image background = new Image("http://i68.tinypic.com/11b68no.jpg");
		borderPane.setBackground(new Background(new BackgroundImage(background, null, null, null, null)));	//Adds background to scene

		//Button
		Button game1 = new Button();
		game1.setText("Tetris!");
		game1.setOnAction(e -> {

			runTetris(); //Runs tetris when button is clicked

		});

		Button game2 = new Button();
		game2.setText("Minesweeper!");
		game2.setOnAction(e-> {
			runMinesweeper(); //Runs minesweeper when button is clicked

		});

		//Images
		ImageView tetris = new ImageView(new Image("http://i67.tinypic.com/fem74l.png"));			//Sets images for game
		ImageView mineSweeper = new ImageView(new Image("http://i64.tinypic.com/9qgz8p.png"));
		tetris.setFitHeight(350);
		tetris.setFitWidth(350);
		mineSweeper.setFitHeight(350);
		mineSweeper.setFitWidth(350);

		//Hbox and Vbox
		VBox container = new VBox();
		HBox hbox = new HBox();
		HBox buttonBox = new HBox();
		hbox.setPadding(new Insets(130, 50, 50, 102));
		hbox.setSpacing(100.0);
		buttonBox.setPadding(new Insets(0, 50, 50, 230));
		buttonBox.setSpacing(360.0);
		hbox.getChildren().addAll(tetris,mineSweeper);		//Adds components to hbox
		buttonBox.getChildren().addAll(game1,game2);
		container.getChildren().addAll(hbox,buttonBox);
		borderPane.setCenter(container);

		//Menu Bar
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("File");
		Menu menu2 = new Menu("Options");
		Menu menu3 = new Menu("Help");
		Menu submenu = new Menu("Quit");
		Menu submenu2 = new Menu("Instructions");
		Menu submenu3 = new Menu("Highscore");
		menu.getItems().add(submenu);
		menu2.getItems().add(submenu3);
		menu3.getItems().add(submenu2);
		menuBar.getMenus().addAll(menu, menu2, menu3);
		menuBar.prefWidthProperty().bind(stage.widthProperty());
		borderPane.setTop(menuBar);
		submenu2.setOnAction(e -> {
			//Displays introduction dialog box
			Stage stage2 = new Stage();
			VBox vbox = new VBox();
			Label instructions = new Label("Welcome to Raj and Logan's arcade! \n We have two games to play, Breakout and Minesweeper. All you have to do \n is click the "
					+ "button of the desired game and play! \n Your highscore will appear in the highscore table under the options tab. \n Goodluck!");
			vbox.getChildren().addAll(instructions);
			stage2.setMaxHeight(200);
			stage2.setMinHeight(200);
			stage2.setMaxWidth(580);
			stage2.setMinWidth(580);
			stage2.initModality(Modality.APPLICATION_MODAL);
			stage2.setTitle("Instructions");
			stage2.setScene(new Scene(vbox));
			stage2.show();
		}); //Instructions menu
		submenu3.setOnAction(e -> {
			//Displays highscore board
			Stage table = new Stage();
			vboxHighScore = new VBox();
			vboxHighScore.getChildren().add(new Text("TETRIS HIGHSCORE: "));
			vboxHighScore.getChildren().add(new Text(initials + ": " + Integer.toString(tetrisHigh)));
			vboxHighScore.getChildren().add(new Text("\n"));
			vboxHighScore.getChildren().add(new Text("MINESWEEPER HIGHSCORE: "));
			vboxHighScore.getChildren().add(new Text(initialsMine + ": " + Integer.toString(mineHigh)));
			Scene scoreTable = new Scene(vboxHighScore);
			table.setScene(scoreTable);
			table.initModality(Modality.APPLICATION_MODAL);
			table.setTitle("Highscores");
			table.show();

		});
		submenu.setOnAction(e -> {
			//exits the platform
			Platform.exit();
			System.exit(0);
		}); //Exit menu

		//Scene Size
		Scene scene = new Scene(borderPane, 640, 480);
		Scene intro = new Scene(borderPane2, 640, 480);
		ImageView gif = new ImageView(new Image("https://i.makeagif.com/media/5-03-2018/DqybNo.gif")); //Adds animated intro
		gif.fitWidthProperty().bind(stage.widthProperty());
		gif.fitHeightProperty().bind(stage.heightProperty());
		borderPane2.setCenter(gif);
		stage.setScene(intro);
		stage.show();

		//SETTING INTRO
		KeyFrame initFrame = new KeyFrame(Duration.ZERO);
		KeyFrame endFrame = new KeyFrame(Duration.seconds(3));

		Timeline timeline = new Timeline(initFrame, endFrame);
		timeline.setCycleCount(1);
		timeline.setOnFinished(t->{
			stage.setScene(scene);
		});
		timeline.play();


		stage.setTitle("cs1302-arcade!");
		stage.sizeToScene();
		stage.show();

		//Sets sizes of stage
		stage.setMaxWidth(1024);
		stage.setMaxHeight(728);
		stage.setMinHeight(728);
		stage.setMinWidth(1024);


	} // start

	public static void main(String[] args) {
		try {
			Application.launch(args);
		} catch (UnsupportedOperationException e) {
			System.out.println(e);
			System.err.println("If this is a DISPLAY problem, then your X server connection");
			System.err.println("has likely timed out. This can generally be fixed by logging");
			System.err.println("out and logging back in.");
			System.exit(1);
		} // try
	} // main

} // ArcadeApp