package miniprojtemplate;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	protected Image img;
	protected int x, y, dx, dy;
	protected boolean visible;
	protected double width;
	protected double height;

	Sprite(int xPos, int yPos){
		this.x = xPos; //x position of the sprite
		this.y = yPos; //y position of the sprite
		this.visible = true; //set initial value of visible to true
	}

	//method to set the object's image
	protected void loadImage(Image img){
		try{
			this.img = img;
	        this.setSize();
		} catch(Exception e){}
	}

	//method to set the image to the image view node
	void render(GraphicsContext gc){
		gc.drawImage(this.img, this.x, this.y);

    }

	//method to set the object's width and height properties
	private void setSize(){
		this.width = this.img.getWidth();
	    this.height = this.img.getHeight();
	}
	//method that will check for collision of two sprites
	boolean collidesWith(Sprite rect2)	{
		Rectangle2D rectangle1 = this.getBounds();
		Rectangle2D rectangle2 = rect2.getBounds();

		return rectangle1.intersects(rectangle2);
	}
	//method that will return the bounds of an image
	private Rectangle2D getBounds(){
		return new Rectangle2D(this.x, this.y, this.width, this.height);
	}

	//method to return the image
	Image getImage(){
		return this.img;
	}
	//getter for x position
	int getX() {
    	return this.x;
	}

	//getter for y position
	int getY() {
    	return this.y;
	}

	//getter for visible value
	boolean getVisible(){
		return visible;
	}

	boolean isVisible(){
		if(visible) return true;
		return false;
	}

	//setter for dx
	void setDX(int dx){
		this.dx = dx;
	}

	//setter for dy
	void setDY(int dy){
		this.dy = dy;
	}

	//setter for the width
	void setWidth(double val){
		this.width = val;
	}

	//setter for the height
	void setHeight(double val){
		this.height = val;
	}

	//setter for the visible value
	void setVisible(boolean value){
		this.visible = value;
	}

	//method to adjust the value of Y
	double adjustY(int value) {
		if(this.height + value >= GameStage.WINDOW_HEIGHT){ //if the height of the sprite + the randomized y position is greater than or equal to the gamestage height
			return (value - this.height); //reduce the y value by the height of the sprite
		}
		return value;
	}

	//method to adjust the value of X
	double adjustX(int value){
		if(this.width + value >= GameStage.WINDOW_WIDTH){ //if the width of the sprite + the randomized x position is greater than or equal to the gamestage width
			return(value - this.width);//reduce the x value by the width of the sprite
		}

		return value;
	}


}
