package miniprojtemplate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class GameOverStage {
    private Group group;
    private Scene scene;
    private GraphicsContext gc;
    private Canvas canvas;
    private Stage stage;

    //background image for lose
    public final Image lose = new Image("images/lose.jpg",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
    //background image for win
    public final Image win = new Image ("images/win.jpg", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT,false,false);

    GameOverStage(boolean status, int score, Stage stage){ //constructor
    	this.stage = stage;
        this.group = new Group();
        this.scene = new Scene(group, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
        this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
        this.gc = canvas.getGraphicsContext2D();
        this.setProperties(status, score);

    }

    private void setProperties(boolean status, int score){

        this.gc.fillRect(0,0,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
        this.gc.setFill(Color.WHITE);

        //checker if the status of the game is false(lose)
        if(!status) {
            this.gc.drawImage(this.lose, 0, 0); //draw the "lose" background image in the canvas
            Font theFont = Font.font("Showcard Gothic",FontWeight.BOLD,50); //set font type, style, and size
            this.gc.setFont(theFont);

            this.gc.fillText("Game Over!", GameStage.WINDOW_WIDTH * 0.3, GameStage.WINDOW_HEIGHT * 0.3);
            this.gc.fillText("Score: " + score, GameStage.WINDOW_WIDTH * 0.35, GameStage.WINDOW_HEIGHT * 0.4);

        } else {
            this.gc.drawImage(this.win, 0, 0); //draw the "win" background image in the canvas
            Font theFont = Font.font("Showcard Gothic",FontWeight.BOLD,30);//set font type, style, and size
            this.gc.setFont(theFont);
            //set the text, and x and y position of the text
            this.gc.fillText("Congratultions! You won!", GameStage.WINDOW_WIDTH*0.22, GameStage.WINDOW_HEIGHT*0.3);                        //add a hello world to location x=60,y=50
            this.gc.fillText("Score: " + score, GameStage.WINDOW_WIDTH * 0.42, GameStage.WINDOW_HEIGHT * 0.43);
        }

        Button mainMenu = new Button("Main Menu"); //create a button "Main Menu"
        Button exitBtn = new Button("Exit Game"); //create a button "Exit Game"


        //set the x and y layout (position) of the mainMenu button
        mainMenu.setLayoutX(GameStage.WINDOW_WIDTH*0.442);
        mainMenu.setLayoutY(GameStage.WINDOW_HEIGHT*0.5);
        //set the color of the mainMenu button
        mainMenu.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));
        //set the x and y layout (position) of the exitBtn button
        exitBtn.setLayoutX(GameStage.WINDOW_WIDTH*0.447);
        exitBtn.setLayoutY(GameStage.WINDOW_HEIGHT*0.6);
        //set the color of the exitBtn button
        exitBtn.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, null, null)));

        //call the shadowButton method and pass mainMenu/exitBtn button as parameters
        this.shadowButton(mainMenu);
        this.shadowButton(exitBtn);

        //add the canvas, mainMenu button, and exitBtn as children of group
        group.getChildren().addAll(this.canvas,mainMenu,exitBtn);

        //when the mainMenu button is clicked, it will go back to the main menu
        mainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	GameStage gamestage = new GameStage();
                gamestage.setStage(stage);
            }
        });

        //when the exitBtn is clicked, the app will exit
        exitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    //getter for scene
    Scene getScene(){
        return this.scene;
    }


    private void shadowButton(Button button){
    	DropShadow shadow = new DropShadow(); //create a drop shadow for the button
    	shadow.setColor(Color.AQUA); //set the color of the shadow
    	//when the cursor is hovered over the button, the shadow of the button will show
		button.addEventHandler(MouseEvent.MOUSE_ENTERED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            button.setEffect(shadow);
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


}