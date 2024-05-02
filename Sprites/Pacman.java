//***********************************************************************
// Name: Weston Cox
// Date: 3/22/2024
// Description: This is where all the information about Pacman is stored.
// 				Pacman is animated with 12 images, and she is able to 
//				interact with other sprites in the game.
//
//***********************************************************************

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Pacman extends Sprite
{
	private int index;
	private int prevX, prevY;
	private boolean movePac = false;
	private double SPEED = 7.0;
	private static final int MAX_IMAGES_PER_DIRECTION = 3;
	private int currentImage = 1;
	private int direction = 0;
	// private int diagonalDirection = -1;

	private static BufferedImage[] pacmanImages = new BufferedImage[MAX_IMAGES_PER_DIRECTION * 4 + 1]; // Number of images per direction multiplied by number of directions, and then one added to burn the 0 index

//*******************************************************************
//! Constructors
//*******************************************************************

	public Pacman()
	{
		this.x = Game.WINDOW_WIDTH / 2 - 25;
		this.y = Game.WINDOW_HEIGHT / 2;
		this.w = 35;
		this.h = 35;
		this.prevX = this.x;
		this.prevY = this.y;
		this.direction = 0;
		this.index = (MAX_IMAGES_PER_DIRECTION * this.direction) + this.currentImage;
		
		for (int i = 1; i <= 12; i++)
		{	
			if (pacmanImages[i] == null)
			{
				String filename = "images/pacman" + i + ".png";
				pacmanImages[i] = View.loadImage(filename);
			}
		}
	}

	public Pacman(int x, int y, int w, int h, int bucket)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.prevX = this.x;
		this.prevY = this.y;
		this.direction = bucket;
		this.index = (MAX_IMAGES_PER_DIRECTION * this.direction) + this.currentImage;

		for (int i = 1; i <= 12; i++)
		{	
			if (pacmanImages[i] == null)
			{
				String filename = "images/pacman" + i + ".png";
				pacmanImages[i] = View.loadImage(filename);
			}
		}
	}

//*******************************************************************
//! Methods
//*******************************************************************


	//-------------------------------------------------------------------
	// Update method
	//-------------------------------------------------------------------
	public boolean update()
	{
		movePac();
		
		return true;
	}


//-------------------------------------------------------------------
// This method adjusts pacman's position to the edge of the wall
// if the pacman has collided with the wall.
//-------------------------------------------------------------------
	public void fixCollisionWithWall(Sprite wallSprite)
	{
		if ((this.prevX < x) && (direction == 2))		
		{
			this.x = (wallSprite.x - (this.w + 2)); // The +2 adds a buffer between Pacman and the wall
			// System.out.println("L2R X Collision"); //! Debuggin'
			return;
		}	
			
		if ((this.prevX > x) && (direction == 0))		
		{
			this.x = (wallSprite.x + (wallSprite.w + 2)); // The +2 adds a buffer between Pacman and the wall
			// System.out.println("R2L X Collision"); //! Debuggin'
			return;
		}

		if((this.prevY < this.y) && (direction == 3) )		
		{
			this.y = (wallSprite.y - (this.h + 2)); // The +2 adds a buffer between Pacman and the wall
			// System.out.println("T2B Y Collision"); //! Debuggin'
			return;
		}	
			
		if((this.prevY > y) && (direction == 1))	
		{
			this.y = (wallSprite.y + (wallSprite.h + 2)); // The +2 adds a buffer between Pacman and the wall
			// System.out.println("B2T Y Collision");  //! Debuggin'
			return;
		}	

		
	}


//-------------------------------------------------------------------
// If pacman collides with a fruit, this method tells the fruit it is eaten
// and to set itself to an invalid sprite. The invalid sprite is then deleted
// in Model's update method.
//-------------------------------------------------------------------
	public void eatFruit(Sprite fruitSprite)
	{
		((Fruit)fruitSprite).isEaten();
	}


//-------------------------------------------------------------------
// If pacman collides with a ghost, this method tells the ghost it is killed
// and to set itself to an invalid sprite. The invalid sprite is then deleted
// in Model's update method.
//-------------------------------------------------------------------
	public void killGhost(Sprite ghostSprite)
	{
		((Ghost)ghostSprite).ghostDeath();
	}

//-------------------------------------------------------------------
// If pacman collides with a pellet, this method tells the pellet it is eaten
// and to set itself to an invalid sprite. The invalid sprite is then deleted
// in Model's update method.
//-------------------------------------------------------------------
	public void eatPellet(Sprite pelletSprite)
	{
		((Pellet)pelletSprite).isEaten();
	}


//-------------------------------------------------------------------
// This method tells pacman that she is moving and should change her image
// and set her movePac boolean to true.
//-------------------------------------------------------------------
	public void keyHasBeenPressed()
	{	
		this.index = (MAX_IMAGES_PER_DIRECTION * this.direction) + this.currentImage;
		movePac = true;

	}

//-------------------------------------------------------------------
// This method reassigns the coordinates of pacman to move her in the direction
// of the arrow key pressed.
//-------------------------------------------------------------------
	public void movePac()
	{
		if (movePac && this.direction == 0)	//Pacman moves left and handles up and down movement
		{
			this.x -= this.SPEED;
		}
		else if (movePac && this.direction == 1)	//Pacman moves up and handles left and right movement
		{
			this.y -= this.SPEED;
		}
		else if (movePac && this.direction == 2)	//Pacman moves right and handles up and down movement
		{
			// this.prevX = this.pacmanX;
			this.x += this.SPEED;
		}
		else if (movePac && this.direction == 3)	//Pacman moves down and handles left and right movement
		{	
			this.y += this.SPEED;
		}
	}

//-------------------------------------------------------------------
// This method sets the previous coordinates of the pacman.
//-------------------------------------------------------------------
	public void setYourPreviousCoordinates()
	{
		prevX = x;
		prevY = y;
	}

//-------------------------------------------------------------------
// This method warps the pacman to the other side of the screen horzontally.
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
// This method warps the pacman to the other side of the screen vertically.
//-------------------------------------------------------------------
	// public void verticalWarp()
	// {
	// 	if (this.y > Game.GAME_HEIGHT_BOTTOM + this.h)
	// 	{
	// 		this.y = Game.GAME_HEIGHT_TOP - this.h;
	// 	}
	// 	else if (this.y < (Game.GAME_HEIGHT_TOP - this.h))
	// 	{
	// 		this.y = Game.GAME_HEIGHT_BOTTOM + this.h;
	// 	}
	// }

//-------------------------------------------------------------------
// This method updates the pacman's image index.
//-------------------------------------------------------------------
	private void updatePacmanImg()
	{
		this.currentImage++;
		if (currentImage > 3)
			currentImage = 1;

		horizontalWarp();
		// verticalWarp();

	}


//-------------------------------------------------------------------
//
//-------------------------------------------------------------------
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
// Draw method (Lazy Loading)
//-------------------------------------------------------------------
	public void draw(Graphics g, int scrollPosY)
	{
		g.drawImage(pacmanImages[index], x, y - scrollPosY, w, h, null);
		updatePacmanImg();
		movePac = false;

	}


//*******************************************************************
//! Getters and Setters (Some are redundant, as they are in the Superclass Sprite.java)
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

	public int getDirection() 
	{
		return this.direction;       
	}

	public int getImgIndex() 
	{
		return this.currentImage;       
	}

	public double getSPEED()
	{
		return this.SPEED;
	}

	public int getPrevX()
	{
		return this.prevX;
	}

	public int getPrevY()
	{
		return this.prevY;
	}

	//******************************************************************/

	// public void setX(int x) 
	// {
	// 	this.prevX = this.x;
	// 	this.x = x;
	// }

	// public void setY(int y) 
	// {
	// 	this.prevY = this.y;
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

	public void setDirection(int direction) 
	{
		this.direction = direction;
		movePac = true;
	}

	public void setImgIndex(int imgIndex) 
	{
		this.currentImage = imgIndex;
	}

	public void setSPEED(double speed)
	{
		this.SPEED = speed;
	}

	public void setPrevX(int prevX)
	{
		this.prevX = prevX;
	}

	public void setPrevY(int prevY)
	{
		this.prevY = prevY;
	}


//*******************************************************************
//! Methods for Sprite Characteristics
//*******************************************************************


//-------------------------------------------------------------------
// Method that overrides the Object.toString() method
//-------------------------------------------------------------------	
	@Override
	public String toString()
	{
		return "Pacman: (x,y) = (" + this.x +  " , " + this.y + ") w = " + this.w + " h = " + this.h + " .\n"; 
	}


//-------------------------------------------------------------------
// Method that returns true if the sprite is a pacman
//-------------------------------------------------------------------	
	@Override
	public boolean isPacman()
	{
		return true;
	}


//-------------------------------------------------------------------
// Method that returns true because the pacman will be moving.
//-------------------------------------------------------------------	
	@Override
	public boolean isMoving()
	{
		return true;
	}


}
