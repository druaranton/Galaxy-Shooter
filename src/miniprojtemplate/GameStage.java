package miniprojtemplate;

import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameStage {
    public static final int WINDOW_HEIGHT = 500; //window height
    public static final int WINDOW_WIDTH = 800; //window width
    private Scene scene; //scene
    private Stage stage; //stage
    private Group root; //root
    private Canvas canvas; //canvas
    private ImageView imgView;
    private MediaPlayer mediaPlayer;
    private Media media;

    public final static String GAME_MUSIC = "src/music/game.wav";
    public final static String MENU_MUSIC = "src/music/menu.wav";
    public final Image bgGame = new Image("images/spaceBg.gif",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
    public final Image bg = new Image("images/spaces.jpg",500,800,false,false); //background of the menu
    private Scene splashScene; //splash scene
    //the class constructor
    GameStage() {
        this.root = new Group(); //initializes the root
        this.scene = new Scene( root ); //initializes the scene
        this.imgView = new ImageView(this.bgGame);
        //this.root.getChildren().add(imgView);
        this.canvas = new Canvas( GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT ); //initializes the canves
        this.root.getChildren().addAll(this.imgView,this.canvas); //adds the canvas as a child of the root
    }

    void setStage(Stage stage) { //method that sets the stage
        this.stage = stage; //initializes the stage
        stage.setTitle( "Galaxy Shooter" ); //sets the title of the stage

        this.initSplash(stage);            // initializes the Splash Screen with the New Game, instructions, and about button

        stage.setScene( this.splashScene ); //sets the scene
        stage.getIcons().add(Ship.SHIP_IMAGE); //change the icon of the stage
        stage.setResizable(false); //makes the window not resizable
        stage.show(); //shows the stage
    }

    private void initSplash(Stage stage) { //method that initializes the splash scene
    	this.media = new Media(Paths.get(MENU_MUSIC).toUri().toString());
    	this.musicPlay(media);
        StackPane root = new StackPane(); //ceates a stackpane
        root.getChildren().addAll(this.createCanvas(),this.createVBox()); //adds the canvas and vbox as the children of the stackpane
        this.splashScene = new Scene(root); //initializes the splash scene
    }

    private Canvas createCanvas() { //method that creates the canvas
        Canvas canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT); //creates a canvas
        GraphicsContext gc = canvas.getGraphicsContext2D(); //creates a graphics context for the canvas

        Image bg = new Image("images/welcome.jpg",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false); //welcome image
        gc.drawImage(bg, 0, 0); //draws the image into the canvas
        Font theFont = Font.font("Cooper Black",FontWeight.BOLD,100); //creates a font objct
        gc.setFont(theFont); //sets the font of the canvas
        gc.setFill(Color.WHITE); //sets the color
        gc.fillText("Galaxy Shooter", 10, 150); //writes text in the canvas
        return canvas; //returns the canvas
    }

    private VBox createVBox() { //method that creates the vbox
        VBox vbox = new VBox(); //creates a vbox
        vbox.setAlignment(Pos.CENTER); //sets the alignment to center
        vbox.setPadding(new Insets(10)); //sets the padding
        vbox.setSpacing(8); //sets the spacing

        Button b1 = new Button("New Game"); //new game button
        Button b2 = new Button("Instructions"); //instructions buttom
        Button b3 = new Button("About"); //about button
        b1.setTextFill(Color.DARKBLUE); //textcolor on the button
		b1.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null))); //color of the button
		b2.setTextFill(Color.DARKBLUE); //textcolor on the button
		b2.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null))); //color of the button
		b3.setTextFill(Color.DARKBLUE); //textcolor on the button
		b3.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null))); //color of the button
		this.shadowButton(b1); //creates a shadow
		this.shadowButton(b2); //creates a shadow
		this.shadowButton(b3); //creates a shadow

        vbox.getChildren().addAll(b1,b2,b3); //the buttons became children of the vbox

        b1.setOnAction(new EventHandler<ActionEvent>() { //method for the new game button
            @Override
            public void handle(ActionEvent e) {
            	musicStop();
                setGame(stage);        // changes the scene into the game scene

            }
        });

        b2.setOnAction(new EventHandler<ActionEvent>() { //method for the instructions button
            @Override
            public void handle(ActionEvent e) {
                Instructions ins = new Instructions(getGameStage(),stage);       // changes the scene into the game scene
                stage.setScene(ins.getScene());
            }
        });

        b3.setOnAction(new EventHandler<ActionEvent>() { //method for the about button
            @Override
            public void handle(ActionEvent e) {
                About about = new About(getGameStage(),stage);       // changes the scene into the game scene
                stage.setScene(about.getScene());
            }
        });

        return vbox; //returns the vbox
    }

    private void setGame(Stage stage) { //method that sets the game
        stage.setScene( this.scene );

        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        GameTimer gameTimer = new GameTimer(gc, scene, getGameStage()); //creates a game timer
        this.media = new Media(Paths.get(GAME_MUSIC).toUri().toString());
    	this.musicPlay(media);
        gameTimer.start(); //starts the game tumer

        //this.gc.drawImage(this.bg, 0, 0);

    }

    void flashGameOver(boolean status, int score) { //method for the game over
    	this.musicStop();
        GameOverStage gameover1 = new GameOverStage(status, score, stage); //creates a gameover stage
        stage.setScene(gameover1.getScene()); //sets the scene
    	//this.createVBox();
    	//stage.setScene(this.splashScene);

    }

    private void shadowButton(Button button){ //for the shadows of the buttons
    	DropShadow shadow = new DropShadow(); //creates a drop shadow
    	shadow.setColor(Color.AQUA); //sets the color of the shadow
		//b1.setEffect(shadow);
		button.addEventHandler(MouseEvent.MOUSE_ENTERED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            button.setEffect(shadow); //sets the effect
			        }
			});
			//Removing the shadow when the mouse cursor is off
			button.addEventHandler(MouseEvent.MOUSE_EXITED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            button.setEffect(null);
			        }
			});
    }
    void musicPlay(Media music) {
		this.mediaPlayer = new MediaPlayer(music);
		this.mediaPlayer.play();
	}
    void musicStop() {
    	this.mediaPlayer.stop();
    }

    Scene getScene(){ //getter of scene
        return this.splashScene;
    }
    GameStage getGameStage() {
    	return this;
    }



}