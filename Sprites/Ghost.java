//*****************************************************************************
// Name: Weston Cox
// Date: 3/22/2024
// Description: The Ghost.java class extends Sprite and contains all the information
//    and methods for the Ghost object. The Ghost object doesn't move, but it does
// 	  have a death animation sequence. 
//*****************************************************************************


import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Ghost extends Sprite
{

	private static BufferedImage[] ghostImages = new BufferedImage[9 + 1];

	private final int GHOST_WIDTH = 45;
	private final int GHOST_HEIGHT = 45;
	private int currentImage = 1;
	private int deathAnimationCounter = 9;
	private boolean valid = true;
	private boolean isAlive = true;


//*******************************************************************
//! Constructors
//*******************************************************************

	public Ghost()
	{
		this.x = 200;
		this.y = 300;
		this.w = GHOST_WIDTH;
		this.h = GHOST_HEIGHT;
		if (ghostImages[1] == null)
			ghostImages[1] = View.loadImage("images/blinky1.png");

		for (int i = 2; i <= 9; i++) 
		{
			if (ghostImages[i] == null)
			{
				ghostImages[i] = View.loadImage("images/ghost" + (i - 1) + ".png");
			}
		}
	}


	public Ghost(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.w = GHOST_WIDTH;
		this.h = GHOST_HEIGHT;
		if (ghostImages[1] == null)
			ghostImages[1] = View.loadImage("images/blinky1.png");

		for (int i = 2; i <= 9; i++) 
		{
			if (ghostImages[i] == null)
			{
				ghostImages[i] = View.loadImage("images/ghost" + (i - 1) + ".png");
			}
		}
	}


//*******************************************************************
//! Methods
//*******************************************************************


//-------------------------------------------------------------------
// This method updates the ghost. It checks if the ghost is alive
// and if it is not, it increments the death animation counter.
// Afterwards, it returns a boolean value that shows whether the ghost
// is valid or not. Called in Model.java
//-------------------------------------------------------------------
	public boolean update()
	{
		
		if (!isAlive)
		{
			addPointsWhenKilled();
			deathAnimationCounter++;
			if (deathAnimationCounter % 10 == 0)
				ghostDeathSequence();
		}
		return valid;
	}


//-------------------------------------------------------------------
// This method draws the ghost. Called in View.java
//-------------------------------------------------------------------
	public void draw(Graphics g, int scrollPosY)
	{
		g.drawImage(ghostImages[currentImage], this.x , this.y - scrollPosY, this.w, this.h, null);
	}


//-------------------------------------------------------------------
// This method marshals the ghost.
//-------------------------------------------------------------------
	@Override
	public Json marshal()
	{
		Json ob = Json.newObject();
		ob.add("x", this.x);
		ob.add("y", this.y);
		ob.add("w", this.w);
		ob.add("h", this.h);
		return ob;
	}	


//-------------------------------------------------------------------
// This method unmarshals the ghost.
//-------------------------------------------------------------------
	Ghost(Json ob)
	{
		this.x = (int)ob.getLong("x");
		this.y = (int)ob.getLong("y");
		this.w = (int)ob.getLong("w");
		this.h = (int)ob.getLong("h");
	}


//-------------------------------------------------------------------
// This method checks if the user clicked within the boundary of a ghost.
//-------------------------------------------------------------------
	public boolean userClickedGhost(int x, int y)
	{	
		
		if ((x >= this.x) && (x <= (this.x + this.w)) && (y >= this.y) && (y <= (this.y + this.h)))
		{
			return true;
		}
		return false;		
	}


//-------------------------------------------------------------------
// This method is the animation sequence for the ghost's death.
//-------------------------------------------------------------------
	public void ghostDeathSequence()
	{
		currentImage++;
		if (currentImage > 9)
		{
			valid = false;
		}
	}


//*******************************************************************
//! Getters and Setters 
//*******************************************************************


//-------------------------------------------------------------------
// This method adds points to the score if the ghost is killed. Private
//-------------------------------------------------------------------
	private void addPointsWhenKilled()
	{
		if (!isAlive && deathAnimationCounter == 9)
		{
			View.updateScore(1000); // Static call to add score to the JLabel
		}
	}


//-------------------------------------------------------------------
// This is the setter for the ghost being alive or dead. It is called in Pacman.java
//-------------------------------------------------------------------
	public void ghostDeath()
	{
		isAlive = false;
	}


//*******************************************************************
//! Methods for Sprite Characteristics
//*******************************************************************


//-------------------------------------------------------------------
// Method that is used to tell if a sprite is a ghost. Called in Model.java
//-------------------------------------------------------------------
	@Override
	public boolean isGhost()
	{
		return true;
	}


//-------------------------------------------------------------------
// Overridden Object.toString method. Prints the ghost's x, y, w, and h values to the console.
//-------------------------------------------------------------------
	@Override
	public String toString()
	{
		return "Ghost (x,y) = (" + this.x + ", " + this.y + "), w = " + this.w + ", h = " + this.h;
	}


}
