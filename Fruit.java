//*****************************************************************************
// Name: Weston Cox
// Date: 3/22/2024
// Description: This Fruit.java class extends Sprite and contains all the information
//    and methods for the Fruit object. The Fruit object moves in a random direction
//    and can be eaten by the Pacman object.
//*****************************************************************************

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Fruit extends Sprite 
{

	private static BufferedImage fruit_image = null;
	private boolean isValid = true;
	private final int FRUIT_WIDTH = 25;
	private final int FRUIT_HEIGHT = 25;
	private int SPEED = 3;
	private int prevX, prevY, direction;


//*******************************************************************
//! Constructors
//*******************************************************************

	public Fruit()
	{
		this.x = 100;
		this.y = 300;
		this.w = FRUIT_WIDTH;
		this.h = FRUIT_HEIGHT;
		prevX = this.x;
		prevY = this.y;
		
		if (fruit_image == null)
			fruit_image = View.loadImage("images/fruit1.png");
	
	}

	public Fruit(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.w = FRUIT_WIDTH;
		this.h = FRUIT_HEIGHT;
		prevX = this.x;
		prevY = this.y;

		Random rand = new Random();
		direction = rand.nextInt(4);

		if (fruit_image == null)
			fruit_image = View.loadImage("images/fruit1.png");

	}


//*******************************************************************
//! Methods
//*******************************************************************


//-------------------------------------------------------------------
// This method updates the fruit. It moves the fruit and returns a
// boolean value that is checked in model. If the boolean value is
// false, the fruit is removed from the arraylist.
//-------------------------------------------------------------------
	public boolean update()
	{
		moveFruit();
		horizontalWarp();
		return isValid;	
	}


//-------------------------------------------------------------------
// This method draws the fruit.
//-------------------------------------------------------------------
	public void draw(Graphics g, int scrollPosY)
	{
		g.drawImage(fruit_image, this.x, this.y - scrollPosY, this.w, this.h, null);
	
	}

//-------------------------------------------------------------------
// This method marshals the fruit.
//-------------------------------------------------------------------
	@Override
	public Json marshal()
	{
		Json ob = Json.newObject();
		ob.add("x", this.x);
		ob.add("y", this.y);
		ob.add("w", this.w);
		ob.add("h", this.h);
		ob.add("direction", this.direction);
		return ob;
	}


//-------------------------------------------------------------------
// This method unmarshals the fruit.
//-------------------------------------------------------------------
	Fruit(Json ob)
	{
		this.x = (int)ob.getLong("x");
		this.y = (int)ob.getLong("y");
		this.w = (int)ob.getLong("w");
		this.h = (int)ob.getLong("h");
		this.direction = (int)ob.getLong("direction");
	}


//-------------------------------------------------------------------
// This is the method that moves the fruit by chaning its x and y values.
//-------------------------------------------------------------------
	public void moveFruit()
	{
		prevX = this.x;
		prevY = this.y;

		if (direction == 0) // Down and to the right
		{
			this.x += SPEED;
			this.y += SPEED;
		}
		else if (direction == 1) // Down and to the left
		{
			this.x -= SPEED;
			this.y += SPEED;
		}
		else if (direction == 2) // Up and to the left
		{
			this.x -= SPEED;
			this.y -= SPEED;
		}
		else if (direction == 3) // Up and to the right
		{
			this.x += SPEED;
			this.y -= SPEED;
		}
	
	}


//-------------------------------------------------------------------
// This method warps the fruit to the other side of the screen horzontally.
//-------------------------------------------------------------------
	public void horizontalWarp()
	{
		if (this.x > Game.WINDOW_WIDTH)
		{
			this.x = 0 - this.w;
		}
		else if (this.x < (0 - this.w))
		{
			this.x = Game.WINDOW_WIDTH;
		}
	}	


//-------------------------------------------------------------------
// This method is called when the fruit collides with a wall. It changes
// the direction of the fruit.
//-------------------------------------------------------------------
	public void fixCollisionWithWall(Sprite wallSprite)
	{
		direction++;
		if (direction > 3)
			direction = 0;

	}


	public boolean userClickedFruit(int x, int y)
	{	
		
		if ((x >= this.x) && (x <= (this.x + this.w)) && (y >= this.y) && (y <= (this.y + this.h)))
		{
			return true;
		}
		return false;		
	}


//*******************************************************************
//! Methods for Sprite Characteristics
//*******************************************************************

//-------------------------------------------------------------------
// Method that is used to tell if a sprit is a fruit.
//-------------------------------------------------------------------
	@Override
	public boolean isFruit()
	{
		return true;
	}


//-------------------------------------------------------------------
// Method that is used to tell if a sprite is moving.
//-------------------------------------------------------------------
	@Override
	public boolean isMoving()
	{
		return true;
	}


//-------------------------------------------------------------------
// Prints the fruit's x, y, w, and h values to the console when called.
//-------------------------------------------------------------------
	@Override
	public String toString()
	{
		return "Fruit (x,y) = (" + this.x + ", " + this.y + "), w = " + this.w + ", h = " + this.h;
	}


//-------------------------------------------------------------------
// This method is called when the fruit is eaten. It sets the boolean
// value isValid to false and prints "nom nom..." to the console.
//-------------------------------------------------------------------	
	public void isEaten()
	{
		isValid = false;
		View.updateScore(500);
		System.out.println("Fruity! ");
	}


}



