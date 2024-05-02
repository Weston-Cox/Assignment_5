//*****************************************************************************
// Name: Weston Cox
// Date: 3/22/2024
// Description: The Sprite.java class is the superclass for all the sprites in game.
//			It is the objective of assignment 5, and is an example of Polymorphism.
//*****************************************************************************

import java.awt.Graphics;

public abstract class Sprite 
{
	protected int x, y, w, h;	


//*******************************************************************
//! Constructors
//*******************************************************************

	public Sprite()
	{
		this.x = 0;
		this.y = 0;
		this.w = 0;
		this.h = 0;
	}

	public Sprite(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}


//*******************************************************************
//! Abstract Methods
//*******************************************************************


	public abstract void draw(Graphics g, int scrollPosY);
	public abstract boolean update();


//*******************************************************************
//! Methods
//*******************************************************************


//-------------------------------------------------------------------
// Marshals the sprite object into a Json object.
//-------------------------------------------------------------------
	protected Json marshal()
	{
		Json ob = Json.newObject();
		ob.add("x", this.x);
		ob.add("y", this.y);
		ob.add("w", this.w);
		ob.add("h", this.h);
		return ob;
	}


//-------------------------------------------------------------------
// Is called by one sprite to see if it collides with another sprite.
//-------------------------------------------------------------------
	public boolean doesCollide(Sprite otherSprite)
	{
		// Check collision between sprites.
		if ( ( x + w ) < otherSprite.x ) // If the right side of the sprite is less than the left side of the other sprite: No Collision
			return false;
		if ( x > ( otherSprite.x + otherSprite.w ) ) // If the left side of the sprite is greater than the right side of the other sprite: No Collision
			return false;
		if ( ( y + h ) < otherSprite.y ) // If the bottom of the sprite is less than the top of the other sprite: No Collision
			return false;
		if ( y > ( otherSprite.y + otherSprite.h ) ) // If the top of the sprite is greater than the bottom of the other sprite: No Collision
			return false;
		return true; // If none of the above conditions are met, then there is a collision.
	}


//*******************************************************************
//! Getters and Setters 
//*******************************************************************


	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void setW(int w)
	{
		this.w = w;
	}

	public void setH(int h)
	{
		this.h = h;
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public int getW()
	{
		return this.w;
	}

	public int getH()
	{
		return this.h;
	}


//*******************************************************************
//! Methods for Sprite Characteristics
//*******************************************************************


//-------------------------------------------------------------------
// Protected method that is called by the subclasses to tell whether
// a sprite is a pacman object.
//-------------------------------------------------------------------
	protected boolean isPacman()
	{
		return false;
	}


//-------------------------------------------------------------------
// Protected method that is called by the subclasses to tell whether
// a sprite is a wall object.
//-------------------------------------------------------------------
	protected boolean isWall()
	{
		return false;
	}


//-------------------------------------------------------------------
// Protected method that is called by the subclasses to tell whether
// a sprite is a ghost object.
//-------------------------------------------------------------------
	protected boolean isFruit()
	{
		return false;
	}


//-------------------------------------------------------------------
// Protected method that is called by the subclasses to tell whether
// a sprite is a ghost object.
//-------------------------------------------------------------------
	protected boolean isGhost()
	{
		return false;
	}


//-------------------------------------------------------------------
// Protected method that is called by the subclasses to tell whether
// a sprite is a pellet object.
//-------------------------------------------------------------------
	protected boolean isPellet()
	{
		return false;
	}


//-------------------------------------------------------------------
// Protected method that is called by the subclasses to tell whether
// a sprite is moving.
//-------------------------------------------------------------------
	protected boolean isMoving()
	{
		return false;
	}

}
