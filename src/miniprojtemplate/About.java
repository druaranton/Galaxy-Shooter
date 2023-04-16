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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class About {
    private Group group;
    private GameStage gameStage;
    private Scene scene;
    private GraphicsContext gc;
    private Canvas canvas;
    private Stage stage;


    public final Image bg = new Image("images/about.jpg",GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT,false,false);
    public final Image aranton = new Image("images/aranton.jpg",100,100,false,false);
    public final Image demdem = new Image("images/demdem.jpg",100,100,false,false);

    About(GameStage gs, Stage stage){ //constructor
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
            //draw the background image in the canvas
            this.gc.drawImage(this.bg, 0, 0);
            //set font type, style and size
            Font theFont = Font.font("Showcard Gothic",FontWeight.BOLD,50);
            this.gc.setFont(theFont);


            this.gc.setFill(Color.WHITE); //set font color of text
            this.gc.fillText("About", GameStage.WINDOW_WIDTH*0.40, GameStage.WINDOW_HEIGHT*0.15); //set the content of the text, the x and y location
            //set another font type, style, and size
            Font theFont1 = Font.font("Comic Sans MS",FontWeight.BOLD,25);
            this.gc.setFont(theFont1);
            //set the content of the text, the x and y location
            this.gc.fillText("KNOW ABOUT THE DEVELOPERS!", GameStage.WINDOW_WIDTH*0.025, GameStage.WINDOW_HEIGHT*0.225);
            //draw the image of the developers in the canvas
            //set the picture and the x,y locations
            this.gc.drawImage(this.demdem, GameStage.WINDOW_WIDTH*0.025, 125);
            this.gc.drawImage(this.aranton, GameStage.WINDOW_WIDTH*0.025, 230);


            Button back = new Button("Back"); //create a new button, "back"
            back.setTextFill(Color.DARKBLUE); //textcolor on the button
    		back.setBackground(new Background(new BackgroundFill(Color.CORNSILK, null, null))); //set the background fill of the button
    		back.setLayoutX(20); //set the x location of the button
            back.setLayoutY(20); //set the y location of the button


            Text t = new Text(130,GameStage.WINDOW_HEIGHT*0.30, "ABOUT"); //text for the developer's information
            t.setFill(Color.ALICEBLUE); //set color of the text
            t.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,15)); //set font type, style and size
            //content of the the text
            t.setText("Grace Angelee Demdem is a BS Computer Science student at the University of the Philippines Los Baños. She enjoys listening to music, reading books and play mobile games. She is also fond of going out with friends once in a while to eat good food and drive around the city. She aspires to be a good web "
            		+ "developer. \n \nAndreau O. Aranton is a BS Computer Science student at the University of the Philippines Los Baños. He is a happy person who always likes to laugh. He also likes to eat, sleep, and surf the internet. Whenever he is in a bad situation, he always remembers the quote “This too shall pass”. His dream is to be a great and skillful software engineer.");
            t.setWrappingWidth(GameStage.WINDOW_WIDTH-150); //set the width constraint for the text
            t.setTextAlignment(TextAlignment.JUSTIFY); //set the horizontal text alignment


            //text for references title
            Text ref = new Text(GameStage.WINDOW_WIDTH*0.43, GameStage.WINDOW_HEIGHT*0.7, "REFERENCES");
            ref.setFill(Color.ALICEBLUE);
            ref.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,15));
            ref.setText("References");
            ref.setWrappingWidth(GameStage.WINDOW_WIDTH-150);
            ref.setTextAlignment(TextAlignment.JUSTIFY);

            //text for references body
            Text links = new Text (GameStage.WINDOW_WIDTH*0.025, GameStage.WINDOW_HEIGHT*0.74, "LINKS");
            links.setFill(Color.ALICEBLUE);
            links.setFont(Font.font("Comic Sans MS",FontWeight.NORMAL,13));
            links.setText("https://bit.ly/rockasteroid  | https://bit.ly/winningbackground | https://bit.ly/losebackground   https://bit.ly/instructbackground | https://bit.ly/aboutbackground | https://bit.ly/bossasteroid   https://bit.ly/31YGVbb | https://bit.ly/3GPrCRp | https://bit.ly/3s893Ur | https://bit.ly/323UlCX   https://bit.ly/3GJOPUN | https://bit.ly/3m9Ugof | https://bit.ly/3H1HiBd | https://bit.ly/3GJgoh8   https://bit.ly/3GLbsIv | https://bit.ly/3EZlxBg");
            links.setWrappingWidth(GameStage.WINDOW_WIDTH-60);
            links.setTextAlignment(TextAlignment.CENTER);


            DropShadow shadow = new DropShadow(); //create a shadow for the button
            shadow.setColor(Color.GOLD); //set color of the shadow

            //will add shadow when the cursor is hovered over the button
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

    		//call addEventHandler; pass the button, "back" as parameter
            this.addEventHandler(back);

            //add the canvas, text "t", text "ref", text "links" and button "back" as children of group
            group.getChildren().addAll(this.canvas, t,ref,links,back);

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