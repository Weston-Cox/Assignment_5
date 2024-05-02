//*****************************************************************************
// Name: Weston Cox
// Date: 3/22/2024
// Description: This Model class contains the ArrayList of sprites and their coordinates
// 		It has methods that allow the user to access details about the ArrayList.
// 		It also has methods that allow the user to add, remove, and clear sprites from the ArrayList.
// 		All of the sprites are stored in the same ArrayList.
//*****************************************************************************

import java.util.ArrayList;
import java.util.Iterator;

public class Model
{

	private ArrayList<Wall> walls;
	public ArrayList<Sprite> sprites;
	private ArrayList<Sprite> tempSpriteList;
	private int wallStartX, wallStartY, wallEndX, wallEndY;
	private Sprite initializingSprite;
	private Sprite pacmanSprite;




//*******************************************************************
//! Constructors
//*******************************************************************

	public Model() 
	{
		sprites = new ArrayList<Sprite>(); // ArrayList of sprites
		tempSpriteList = new ArrayList<Sprite>();
		pacmanSprite = new Pacman();
		initializingSprite = new Wall();
		initializingSprite = new Fruit();
		initializingSprite = new Ghost();
		initializingSprite = new Pellet();

		sprites.add(pacmanSprite); // Adds pacman to the ArrayList of sprites
	}


//*******************************************************************
//! Methods
//*******************************************************************


//--------------------------------------------------------------------------
// This is Model's update method
//--------------------------------------------------------------------------
	public void update()
	{	
		sprites.addAll(tempSpriteList);
		tempSpriteList.clear();

		for (int i = 0; i < sprites.size(); i++)
		{
			Sprite sprite1 = sprites.get(i); // Gets the sprite at index i

			if (!sprite1.update())	// Removes the sprite if something happens to the sprite that sets it to false.
			{						// For example, pacman eats a dot, so the dot is removed from the arraylist.
				sprites.remove(i);
				continue;
			}

			Iterator<Sprite> iter = sprites.iterator();

			if (!sprite1.isMoving())
				continue;

			while(iter.hasNext()) // Iterates through the ArrayList of sprites
			{
				Sprite sprite2 = iter.next();

				// The two sprites can't be the same, they must collide, and the first sprite must be moving
				// The first sprite moving means that there aren't collisions between walls that don't move.
				// This way, no unnecessary calculations are made.
				if (sprite1 != sprite2 && sprite1.doesCollide(sprite2))
				{
					// Collision between pacman and a wall
					if (sprite1.isPacman() && sprite2.isWall())
					{
						((Pacman)sprite1).fixCollisionWithWall(sprite2);
					}

					// Collision between fruit and a wall
					if (sprite1.isFruit() && sprite2.isWall())
					{
						((Fruit)sprite1).fixCollisionWithWall(sprite2);
					}

					// Collision between pacman and a fruit
					if (sprite1.isPacman() && sprite2.isFruit())
					{
						((Pacman)sprite1).eatFruit(sprite2);
					}
					// Collision between pacman and a ghost (For now. Ghosts will inevitably hurt pacman)
					if (sprite1.isPacman() && sprite2.isGhost())
					{
						((Pacman)sprite1).killGhost(sprite2);
					}

					// Collision between pacman and a pellet
					if (sprite1.isPacman() && sprite2.isPellet())
					{
						((Pacman)sprite1).eatPellet(sprite2);
					}
				}
			}
		}
	}


//---------------------------------------------------------------------------
// Json marshal method for the Model class. Stores walls within the ArrayList
// into the .json file.
//---------------------------------------------------------------------------
	Json marshal()
	{
		Json ob = Json.newObject();

		Json tmpListWalls = Json.newList();
		ob.add("walls", tmpListWalls);

		Json tmpListGhosts = Json.newList();
		ob.add("ghosts", tmpListGhosts);

		Json tmpListFruits = Json.newList();
		ob.add("fruits", tmpListFruits);

		Json tmpListPellets = Json.newList();
		ob.add("pellets", tmpListPellets);

		for(int i = 0; i < sprites.size(); i++)
		{
			if (sprites.get(i).isWall())
				tmpListWalls.add(sprites.get(i).marshal()); // Add the wall to the list of walls in the .json file
			if (sprites.get(i).isGhost())
				tmpListGhosts.add(sprites.get(i).marshal()); // Add the ghost to the list of ghosts in the .json file
			if (sprites.get(i).isFruit())
				tmpListFruits.add(sprites.get(i).marshal()); // Add the fruit to the list of fruits in the .json file
			if (sprites.get(i).isPellet())
				tmpListPellets.add(sprites.get(i).marshal()); // Add the pellet to the list of pellets in the .json file
		}

		return ob;
	}


//---------------------------------------------------------------------------
// Json unmarshal method for the Model class. Loads stored walls into the ArrayList
// from the .json file.
//---------------------------------------------------------------------------
	public void unmarshal (Json ob)
	{	
		sprites.clear(); // Clears the ArrayList of sprites to correctly load from the map.json.
		sprites.add(pacmanSprite); // Adds pacman to the ArrayList of sprites because she should be the same.
		
		Json tmpList; // Temporary list to store all the sprites in the .json file.

		try 
		{
			tmpList = ob.get("walls"); // Gets the list of walls from the .json file

			for (int i = 0; i < tmpList.size(); i++) // Iterates through the list of walls
				sprites.add(new Wall(tmpList.get(i))); // Adds each wall to the ArrayList of sprites

		} catch (RuntimeException e)
		{
			System.out.println("There are no walls saved in the map.json.");
		}
		
		try 
		{
			tmpList = ob.get("ghosts"); // Gets the list of ghosts from the .json file

			for (int i = 0; i < tmpList.size(); i++) // Iterates through the list of ghosts
				sprites.add(new Ghost(tmpList.get(i))); // Adds each ghost to the ArrayList of sprites

		} catch (RuntimeException e )
		{
			System.out.println("There are no ghosts saved in the map.json.");
		}
		
		try
		{
			tmpList = ob.get("fruits"); // Gets the list of fruits from the .json file

			for (int i = 0; i < tmpList.size(); i++) // Iterates through the list of fruits
				sprites.add(new Fruit(tmpList.get(i))); // Adds each fruit to the ArrayList of sprites
		} catch(RuntimeException e) 
		{
			System.out.println("There are no fruits saved in the map.json.");
		}
			
		try
		{
			tmpList = ob.get("pellets"); // Gets the list of pellets from the .json file

			for (int i = 0; i < tmpList.size(); i++) // Iterates through the list of pellets
				sprites.add(new Pellet(tmpList.get(i))); // Adds each pellet to the ArrayList of sprites
		} catch(RuntimeException e) 
		{
			System.out.println("There are no pellets saved in the map.json.");
		}

	}


//---------------------------------------------------------------------------
// This method assigns the initial x and y coords for the wall. It is 
// called in Controller.java
// --------------------------------------------------------------------------
	public void startWallMousePressed(int x, int y)
	{
		this.wallStartX = x;
		this.wallStartY = y;
	}


//---------------------------------------------------------------------------
// This method assigns the final x and y coords (height and width) for the 
// wall. It is called in Controller.java. A bunch of math was required to
// find the minimum and maximum x and y values so that the wall would be 
// drawn correctly.
//---------------------------------------------------------------------------
	public void endWallMouseReleased(int x, int y)
	{	
		// int swap = 0;
		int startX = Math.min(this.wallStartX, x); //Finds the minimum x value between the start and end x coords.
		int startY = Math.min(this.wallStartY, y); //Finds the minimum y value between the start and end y coords.
		int endX = Math.max(this.wallStartX, x);   //Finds the maximum x value between the start and end x coords.
		int endY = Math.max(this.wallStartY, y);   //Finds the maximum y value between the start and end y coords.

		this.wallStartX = startX;	//Assigns the minimum x value to wallStartX
		this.wallStartY = startY;   //Assigns the minimum y value to wallStartY
		this.wallEndX = endX;       //Assigns the maximum x value to wallEndX
		this.wallEndY = endY;       //Assigns the maximum y value to wallEndY
		
		this.wallEndX = Math.abs(this.wallEndX - this.wallStartX); // Sets wallEndX to the width
		this.wallEndY = Math.abs(this.wallEndY - this.wallStartY); // Sets wallEndY to the height
	}


//---------------------------------------------------------------------------
// This method adds the wall to the ArrayList of walls using the assigned 
// initial and final coordinates of x and y that were given in previous 
// methods
//---------------------------------------------------------------------------
	public void addWallToArray()
	{		
		initializingSprite = new Wall(); // Sets the initializingSprite to a new wall object.

		initializingSprite.setX(wallStartX); // Sets the x coord of the wall to the initial x coord
		initializingSprite.setY(wallStartY); // Sets the y coord of the wall to the initial y coord
		initializingSprite.setW(wallEndX);  // Sets the width of the wall to the calculated width
		initializingSprite.setH(wallEndY); // Sets the height of the wall to the calculated height

		tempSpriteList.add(initializingSprite); // Adds the wall to the ArrayList of sprites

		System.out.println("Adding wall."); //!Test
	}

//---------------------------------------------------------------------------
// Adds a fruit to the array list of sprites
//---------------------------------------------------------------------------
	public void addFruitToArray(int x, int y)
	{
		Sprite fruit = new Fruit(x, y);
		tempSpriteList.add(fruit);
		// System.out.println("Adding fruit."); //!Test
	}


//---------------------------------------------------------------------------
// Adds a ghost to the array list of sprites
//---------------------------------------------------------------------------
	public void addGhostToArray(int x, int y)
	{
		Sprite ghost = new Ghost(x, y);
		tempSpriteList.add(ghost);
		// System.out.println("Adding ghost."); //!Test
	}


//---------------------------------------------------------------------------
// Adds a pellet to the array list of sprites
//---------------------------------------------------------------------------
	public void addPelletToArray(int x, int y)
	{
		Sprite pellet = new Pellet(x, y);
		tempSpriteList.add(pellet);
		// System.out.println("Adding pellet."); //!Test
	}


//---------------------------------------------------------------------------
// This method removes a wall from the ArrayList
//---------------------------------------------------------------------------
	public void removeWallFromArray(int x, int y)
	{	
		for (int i = sprites.size() - 1; i >= 0; i--)
		{
			Sprite tempSprite = sprites.get(i);
			if (tempSprite.isWall() && ((Wall)tempSprite).userClickedWall(x,y))
			{
				sprites.remove(i);
				System.out.println("Removing wall from the sprites array."); //!Test
				break;
			}
		}
		
	}


//---------------------------------------------------------------------------
// This method removes a fruit from the ArrayList
//---------------------------------------------------------------------------
	public void removeFruitFromArray(int x, int y)
	{
		for (int i = sprites.size() - 1; i >= 0; i--)
		{
			Sprite tempSprite = sprites.get(i);
			if (tempSprite.isFruit() && ((Fruit)tempSprite).userClickedFruit(x,y))
			{
				sprites.remove(i);
				System.out.println("Removing fruit from the sprites array."); //!Test
				break;
			}
		}
	}


//---------------------------------------------------------------------------
// This method removes a ghost from the ArrayList
//---------------------------------------------------------------------------
	public void removeGhostFromArray(int x, int y)
	{
		for (int i = sprites.size() - 1; i >= 0; i--)
		{
			Sprite tempSprite = sprites.get(i);
			if (tempSprite.isGhost() && ((Ghost)tempSprite).userClickedGhost(x,y))
			{
				sprites.remove(i);
				System.out.println("Removing ghost from the sprites array."); //!Test
				break;
			}
		}
	}


//---------------------------------------------------------------------------
// This method removes a pellet from the ArrayList
//---------------------------------------------------------------------------
	public void removePelletFromArray(int x, int y)
	{
		for (int i = sprites.size() - 1; i >= 0; i--)
		{
			Sprite tempSprite = sprites.get(i);
			if (tempSprite.isPellet() && ((Pellet)tempSprite).userClickedPellet(x,y))
			{
				sprites.remove(i);
				System.out.println("Removing pellet from the sprites array."); //!Test
				break;
			}
		}
	}

//---------------------------------------------------------------------------
// This method clears all the walls in the ArrayList.
//---------------------------------------------------------------------------
	public void clearSprites()
	{
		sprites.clear();
		sprites.add(pacmanSprite);
		System.out.println("Clearing sprites."); //!Test
	}


//---------------------------------------------------------------------------
// Prints the coordinates of a singular wall that has been clicked.
//---------------------------------------------------------------------------
	public void printWallCoords(int x, int y)
	{
		for (int i = sprites.size() - 1; i >= 0; i--)
		{
			if (sprites.get(i).isWall())
			{
				Sprite tempWall = sprites.get(i);
				if(((Wall)tempWall).userClickedWall(x,y))
				{
					System.out.println(((Wall)tempWall).toString());
					break;
				}
			}
		}
	}


//*******************************************************************
//! Getters and Setters
//*******************************************************************


//--------------------------------------------------------------------------
// This method returns the y coordinate of the pacman object.
//--------------------------------------------------------------------------
	public int getPacmanY()
	{
		return pacmanSprite.getY();
	}


//--------------------------------------------------------------------------
// Sets the direction of the pacman object and calls other methods dealing with pacman.
//--------------------------------------------------------------------------
	public void setPacmanDirection(int direction)
	{
		((Pacman)pacmanSprite).setDirection(direction);
		((Pacman)pacmanSprite).keyHasBeenPressed();
	}


//--------------------------------------------------------------------------
// This method returns the bucket of the pacman object
//--------------------------------------------------------------------------
	public int getPacmanDirection()
	{
		return ((Pacman)pacmanSprite).getDirection();
	}


//--------------------------------------------------------------------------
// When called, this method tells the Pacman object to set it's previous location
//--------------------------------------------------------------------------
	public void setPacsPrevLocation()
	{
		((Pacman)pacmanSprite).setYourPreviousCoordinates();
	}


//--------------------------------------------------------------------------
// This method returns the overridden .toString() method that prints position
//--------------------------------------------------------------------------
	public void printPacmanPosition()
	{
		System.out.println(((Pacman)pacmanSprite).toString());
	}


//--------------------------------------------------------------------------
// This method returns the number of walls in the ArrayList walls
//--------------------------------------------------------------------------
	public int numOfWalls()
	{
		return walls.size();
	}


}


