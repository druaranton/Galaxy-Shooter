package miniprojtemplate;

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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Instructions {
    private Group group;
    private GameStage gameStage;
    private Scene scene;
    private GraphicsContext gc;
    private Canvas canvas;
    private Stage stage;

    //background image for the instructions window
    public final Image bg = new Image("images/instructions.jpg",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);


    Instructions(GameStage gs,Stage stage){ //constructor
    	this.stage = stage;
    	this.gameStage = gs;
        this.group = new Group();
        this.scene = new Scene(group, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
        this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
        this.gc = canvas.getGraphicsContext2D();
        this.setProperties();
    }

    private void setProperties(){

            this.gc.fillRect(0,0,GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
            this.gc.drawImage(this.bg, 0, 0); //draw the background image in the canvas
            Font theFont = Font.font("Showcard Gothic",FontWeight.BOLD,50);//set font type, style and size
            this.gc.setFont(theFont);

            this.gc.setFill(Color.WHITE); //set the font color
            this.gc.fillText("Instructions", GameStage.WINDOW_WIDTH*0.29, GameStage.WINDOW_HEIGHT*0.15); //set the content of the text as well as the x and y location

            Button back = new Button("Back"); //instantiate a new button "back"
            back.setTextFill(Color.DARKBLUE); //textcolor on the button
    		back.setBackground(new Background(new BackgroundFill(Color.CORNSILK, null, null))); //color of the button
            //set x and y layout of the button
    		back.setLayoutX(20);
            back.setLayoutY(20);

            //instantiate a new Text
            Text t = new Text(20,GameStage.WINDOW_HEIGHT*0.25, "INSTRUCTIONS");
            t.setFill(Color.ALICEBLUE); //set the color of the text
            t.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,15)); //set the font type, style and size of the text
            t.setText("1. The main goal of this game is to survive for 60 seconds.\n2. Asteroids appear at the right side of the screen every 5 seconds. The player must destroy or avoid the asteroids.\n3. When the spaceship collides with an asteroid, the asteroid will be destroyed but the strength of the ship will be lessened by 30.\n4. To shoot, press the spacebar.\n5. To move, press the arrow keys.\n6. For every small asteroid destroyed, a point will be gained.\n7. A giant asteroid, with an initial health of 3000 will spawn at the 30-second mark. This type of asteroid will only be destroyed when its health reaches 0.\n8. When the spaceship collides with the giant asteroid, the strength of the spaceship is reduced by 50.\n9. A point will also be gained when the giant asteroid gets destroyed. When the strength of the ship reaches 0, the ship dies and the player loses.\n10. Power-ups will appear randomly at random locations to help the spaceship survive.\n11. The fuel powerup, will add 50 to the strength of the spaceship while the star powerup, will make the spaceship invulnerable to asteroids for 3 seconds.");
            t.setWrappingWidth(GameStage.WINDOW_WIDTH-30); //set the width constraint for the text
            t.setTextAlignment(TextAlignment.JUSTIFY); //set the horizontal allignment of the text

            //instantiate a drop shadow
            DropShadow shadow = new DropShadow();
            shadow.setColor(Color.GOLD); //set the color of the dropshadow

    		//when the cursor is hovered over the button, the shadow will show
    		back.addEventHandler(MouseEvent.MOUSE_ENTERED,
    			    new EventHandler<MouseEvent>() {
    			        @Override public void handle(MouseEvent e) {
    			            back.setEffect(shadow);
    			        }
    			});
    			//Removing the shadow when the mouse cursor is off
    			back.addEventHandler(MouseEvent.MOUSE_EXITED,
    			    new EventHandler<MouseEvent>() {
    			        @Override public void handle(MouseEvent e) {
    			            back.setEffect(null);
    			        }
    			});
            this.addEventHandler(back);

            //add the canvas, text "t" and button "back" as children of the group
            group.getChildren().addAll(this.canvas,t,back);


    }

    //event handler for button; when the button is clicked, it will got back to the main menu
    private void addEventHandler(Button btn) {
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent arg0) {
            	stage.setScene(gameStage.getScene());
            }
        });

    }

    //getter for the scene
    Scene getScene(){
        return this.scene;
    }


}