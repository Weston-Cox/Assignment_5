//************************************************************************
// Name: Weston Cox
// Date: 3/22/2024
// Description: This is the controller class for the assignment.
//     it is where the magic happens per se. It contains the methods
//     that are called when the user interacts with the program. Upon receiving
//	   input, it lets Model and View know about it.
//**************************************************************************



import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


public class Controller implements ActionListener, MouseListener, KeyListener
{	
	private View view;
	private Model model;
	
	private boolean editMode; // Helper boolean for edit mode
	private boolean add_remove_walls; // Helper boolean for adding/removing walls	
	private boolean add_remove_fruits; // Helper boolean for adding/removing fruits
	private boolean add_remove_ghosts; // Helper boolean for adding/removing ghosts
	private boolean add_remove_pellets; // Helper boolean for adding/removing pellets

	private boolean keyUp, keyDown, keyLeft, keyRight; // Sets the direction of pacman
	private boolean keyQ; // Quits the program
	private boolean keyE; // Changes in and out of Edit Mode
	private boolean keyC; // Clears the walls on screen
	private boolean keyA; // Changes whether the user is in add or remove mode
	private boolean keyS; // Saves the walls to the map.json file
	private boolean keyL; // Loads the walls from the map.json file
	private boolean keyP; // Prints the coordinates of pacman to the console or adds pellets to the map.
	private boolean keyF; // Adds fruit to the map wherever the mouse clicks.
	private boolean keyG; // Adds ghosts to the map wherever the mouse clicks.
	private boolean keyEsc; // Quits the program


	public Controller(Model m)
	{
		this.model = m; // Sets the model to the model passed in from Game.java
		editMode = false;
		add_remove_walls = true;
		add_remove_fruits = false;
		add_remove_ghosts = false;
		add_remove_pellets = false;
		keyL = true; // Automatically loads the map.json file when the program starts.
	}

	void setView(View v)
	{
		this.view = v; // Sets the view to the view passed in from Game.java
		view.followCamera();
	}

	public void actionPerformed(ActionEvent e) { } //Required method for ActionListener

	public void mousePressed(MouseEvent e)
	{
		model.startWallMousePressed(e.getX(), e.getY() + view.getScrollPos()); // Sends the x and y coordinates to the model when the mouse is pressed.
	}

	public void mouseReleased(MouseEvent e) 
	{
    	model.endWallMouseReleased(e.getX(), e.getY() + view.getScrollPos()); // sets the coordinates for the wall after the mouse is released
		
		//! Walls
		if (editMode && add_remove_walls && !add_remove_ghosts && !add_remove_fruits && !add_remove_pellets) 	//Checks to see if editMode and add_remove_walls are true before adding the wall to the array. 
			model.addWallToArray();
		else if(editMode && !add_remove_walls && !add_remove_ghosts && !add_remove_fruits && !add_remove_pellets)	//Checks to see if editMode is true and add_remove_walls is false before removing the wall from the array.
			model.removeWallFromArray(e.getX(), e.getY() + view.getScrollPos()); //Removes the wall from the array if the user clicks on it.

		//! Fruits
		if (editMode && add_remove_fruits)  
		{
			model.addFruitToArray(e.getX(), e.getY() + view.getScrollPos());
		} 
		else if (editMode && !add_remove_fruits) 
		{
			model.removeFruitFromArray(e.getX(), e.getY() + view.getScrollPos());
		}

		//! Ghosts
		if (editMode && add_remove_ghosts) 
		{
			model.addGhostToArray(e.getX(), e.getY() + view.getScrollPos());
		} 
		else if (editMode && !add_remove_ghosts) {
			model.removeGhostFromArray(e.getX(), e.getY() + view.getScrollPos());
		}

		//! Pellets
		if (editMode && add_remove_pellets)
		{
			model.addPelletToArray(e.getX(), e.getY() + view.getScrollPos());
		}
		else if (editMode && !add_remove_pellets)
		{
			model.removePelletFromArray(e.getX(), e.getY() + view.getScrollPos());
		}


	}
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseClicked(MouseEvent e) 
	{
		if (!editMode) // If the user is not in edit mode, then any clicks on wall from the user will print the coordinates of the wall.
			model.printWallCoords(e.getX(), e.getY() + view.getScrollPos());
	}

	public void keyPressed(KeyEvent e) // "Upon key pressed (self-explanatory)"
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_RIGHT: keyRight = true; break;
		}
	}

	public void keyReleased(KeyEvent e) // "Upon key released (self-explanatory)"
	{
		switch(e.getKeyCode())
		{	
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_Q: keyQ = true; break;
			case KeyEvent.VK_E: keyE = true; break;
			case KeyEvent.VK_C: keyC = true; break;
			case KeyEvent.VK_A: keyA = true; break;
			case KeyEvent.VK_S: keyS = true; break;
			case KeyEvent.VK_L: keyL = true; break;
			case KeyEvent.VK_P: keyP = true; break;
			case KeyEvent.VK_F: keyF = true; break;
			case KeyEvent.VK_G: keyG = true; break;
			case KeyEvent.VK_ESCAPE: keyEsc = true; break;	
		
		}
	}

	public void keyTyped(KeyEvent e) { }


//-------------------------------------------------------------------
// This method is called to update the model. It checks to see if 
// specific keys have been pressed and then performs the appropriate
// action.
//-------------------------------------------------------------------
	public void update() 
	{
		model.setPacsPrevLocation(); // Sets the previous location of pacman
		
		if(keyQ || keyEsc) // Quits the program if the user presses Q or Esc
			System.exit(0);

		//----------------------------------------------------------

		if(keyUp) // Moves pacman up when the up key is pressed
		{
			model.setPacmanDirection(1);
			view.followCamera();
		}

		//----------------------------------------------------------

		if(keyDown) // Moves pacman down when the down key is pressed
		{
			model.setPacmanDirection(3);
			view.followCamera();
		} 

		//----------------------------------------------------------

		if (keyLeft) // Moves pacman left when the left key is pressed
		{
			model.setPacmanDirection(0);
		} 

		//----------------------------------------------------------

		if (keyRight) // Moves pacman right when the right key is pressed
		{
			model.setPacmanDirection(2);
		}

		if(keyE) // Edit Mode
		{
			editMode = !editMode;	
			view.setEditMode(editMode);
			keyE = !keyE; // Necessary so the keyE isn't stuck to true

			if (!editMode)
			{
				System.out.println("Normal Mode");
				View.setEditModeString("Normal Mode");
			}
			else
			{
				System.out.println("Edit Mode: Add Wall");	
				View.setEditModeString("Edit Mode: Add Wall");
			}
		
		} 

		//----------------------------------------------------------

		if(keyC && editMode) // Clears all the sprites except for Pacman.
		{
			model.clearSprites();
			keyC = !keyC;
		}
		else if(keyC && !editMode)
		{
			System.out.println("You must be in edit mode to clear the walls.");
			keyC = !keyC;
		}

		//----------------------------------------------------------
	
		if(editMode && keyA ) // Switch for adding and removing walls
		{
			setBooleansToFalse('w');
			add_remove_walls = !add_remove_walls;
			keyA = !keyA; 

			if (!add_remove_walls)
			{
				System.out.println("Edit: Remove Mode");
				View.setEditModeString("Edit Mode: Remove Mode");
			}

			else 
			{
				System.out.println("Edit: Add Wall Mode");
				View.setEditModeString("Edit Mode: Add Wall Mode");
			}

		}
		else if(keyA && !editMode)
		{
			System.out.println("You must be in edit mode to add or remove walls.");
			keyA = !keyA;
		}
		
		//----------------------------------------------------------

		if(editMode && keyF) // Switch for adding and removing fruits
		{	
			setBooleansToFalse('f');
			add_remove_fruits = !add_remove_fruits;
			keyF = !keyF;

			if (!add_remove_fruits)
			{
				System.out.println("Edit: Remove Mode");
				View.setEditModeString("Edit Mode: Remove Mode");
			}

			else
			{
				System.out.println("Edit: Add Fruit Mode");
				View.setEditModeString("Edit Mode: Add Fruit");
			}

		} 
		else if (!editMode && keyF)
		{
			System.out.println("You must be in edit mode to add fruit.");
			keyF = !keyF;
		}

		//----------------------------------------------------------

		if (editMode && keyG) // Switch for adding and removing ghosts
		{
			setBooleansToFalse('g');
			add_remove_ghosts = !add_remove_ghosts;
			keyG = !keyG;

			if (!add_remove_ghosts)
			{
				System.out.println("Edit: Remove Mode");
				View.setEditModeString("Edit Mode: Remove Mode");
			}
			else
			{
				System.out.println("Edit: Add Ghost Mode");
				View.setEditModeString("Edit Mode: Add Ghost");
			}
		}
		else if (!editMode && keyG)
		{
			System.out.println("You must be in edit mode to add ghosts.");
			keyG = !keyG;
		}

		//----------------------------------------------------------

		if(!editMode && keyP)  // Prints the coordinates of pacman to the console if not in edit mode
		{
			model.printPacmanPosition();
			keyP = !keyP;
		}
		else if (editMode && keyP) // Switch for adding and removing pellets
		{
			setBooleansToFalse('p');
			add_remove_pellets = !add_remove_pellets;
			keyP = !keyP;

			if (!add_remove_pellets)
			{
				System.out.println("Edit: Remove Mode");
				View.setEditModeString("Edit Mode: Remove Mode");
			}
			else
			{
				System.out.println("Edit: Add Pellet Mode");
				View.setEditModeString("Edit Mode: Add Pellet");
			}
		}

		//----------------------------------------------------------

		if(keyS) // Saves the map to the map.json file
		{
			model.marshal().save("map.json");
			System.out.println("Saving map.");
			keyS = !keyS;
		}

		//----------------------------------------------------------

		if(keyL) // Loads the map from the map.json file
		{
			
			// ob.load("map.json");
			model.unmarshal(Json.load("map.json"));
			System.out.println("Loading map.");
			View.updateScore(0);
			keyL = !keyL;
		}

		//----------------------------------------------------------



	}


//-------------------------------------------------------------------
// This method sets all the booleans to false except for the boolean
// specified by the char parameter. It is called when the user switches
// between add and remove modes.
//-------------------------------------------------------------------
	private void setBooleansToFalse(char c)
	{

		if (c == 'w') // If the char is 'w', then set all the booleans to false except for add_remove_walls
		{
			add_remove_fruits = false;
			add_remove_ghosts = false;
			add_remove_pellets = false;
		} 
		else if (c == 'f') // If the char is 'f', then set all the booleans to false except for add_remove_fruits
		{
			add_remove_walls = false;
			add_remove_ghosts = false;
			add_remove_pellets = false;
		}
		else if (c == 'g') // If the char is 'g', then set all the booleans to false except for add_remove_ghosts
		{
			add_remove_walls = false;
			add_remove_fruits = false;
			add_remove_pellets = false;
		}
		else if (c == 'p') // If the char is 'p', then set all the booleans to false except for add_remove_pellets
		{
			add_remove_walls = false;
			add_remove_fruits = false;
			add_remove_ghosts = false;
		}
		
	}

}
