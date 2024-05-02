//***************************************************************************************
// Name: Weston Cox
// Date: 3/22/2024
// Description: This Wall class contains the x, y, w, and h coordinates for a wall object.
// 		It also has getters and setters to access each respective coordinate.
//	    It also has a method to check if the user clicked on a wall.
//**************************************************************************************

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Wall extends Sprite 
{
	private static BufferedImage wall_image = null;

//*******************************************************************
//! Constructors
//*******************************************************************

	public Wall() //Wall constructor with no parameters. 
	{
		this.x = 0;
		this.y = 0;
		this.w = 0;
		this.h = 0;
		if (wall_image == null)
			wall_image = View.loadImage("images/wall.png");
	}

	public Wall(int x, int y, int w, int h) //Wall constructor with x, y, w, and h parameters. 
	{
	
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		if (wall_image == null)
			wall_image = View.loadImage("images/wall.png");
	
	}


//*******************************************************************
//! Methods
//*******************************************************************


//-------------------------------------------------------------------
// This method updates the wall. Not doing much now...
//-------------------------------------------------------------------
	public boolean update()
	{

		return true;
	}


//-------------------------------------------------------------------
// This method draws the wall.
//-------------------------------------------------------------------
	public void draw(Graphics g, int scrollPosY)
	{
		g.drawImage(wall_image, this.x, this.y - scrollPosY, this.w, this.h, null);
	}


//-------------------------------------------------------------------
// This method checks if the user clicked within the boundary of a wall.
//-------------------------------------------------------------------
		public boolean userClickedWall(int x, int y)
	{	
		
		if ((x >= this.x) && (x <= (this.x + this.w)) && (y >= this.y) && (y <= (this.y + this.h)))
		{
			return true;
		}
		return false;		
	}


//---------------------------------------------------------------------------
// Json marshal method for the Wall class
//---------------------------------------------------------------------------
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


//---------------------------------------------------------------------------
// Wall constructor for the Json unmarshal method
//---------------------------------------------------------------------------
	Wall(Json ob)
	{	
		this.x = (int)ob.getLong("x");
		this.y = (int)ob.getLong("y");
		this.w = (int)ob.getLong("w");
		this.h = (int)ob.getLong("h");

	}


//*******************************************************************
//! Getters and Setters (Redundant, as they are in the Superclass Sprite.java)
//*******************************************************************

	// public int getX() 
	// {
	// 	return this.x;       
	// }

	// public int getY() 
	// {
	// 	return this.y;       
	// }

	// public int getW() 
	// {
	// 	return this.w;       
	// }

	// public int getH() 
	// {
	// 	return this.h;       
	// }

	// //*******************************************************************

	// public void setX(int x) 
	// {
	// 	this.x = x;       
	// }

	// public void setY(int y) 
	// {
	// 	this.y = y;       
	// }

	// public void setW(int w) 
	// {
	// 	this.w = w;       
	// }

	// public void setH(int h) 
	// {
	// 	this.h = h;       
	// }


//*******************************************************************
//! Methods for Sprite Characteristics
//*******************************************************************

//---------------------------------------------------------------------------
// Overrides the toString method to print the wall's coordinates.
//---------------------------------------------------------------------------
	@Override 
	public String toString()
	{
		return "Wall (x,y) = (" + this.x + ", " + this.y + "), w = " + this.w + ", h = " + this.h;
	}

//---------------------------------------------------------------------------
// Method that returns true if the sprite is a wall
//---------------------------------------------------------------------------
	@Override
	public boolean isWall()
	{
		return true;
	}

//---------------------------------------------------------------------------
// Method that returns false because a wall will never move. Avoids needless 
// collision detection.
//---------------------------------------------------------------------------
	@Override
	public boolean isMoving()
	{
		return false;
	}

}




