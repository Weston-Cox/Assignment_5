//*******************************************************************
// Name: Weston Cox
// Date: 3/22/2024
// Description: This is the View class that, as the name suggests,
//     constructs and alters the view of the interface. It contains
//     the paintComponent method that tells the sprites to draw themselves
//	   and it draws the background. It also contains the setScrollPos method that
//     sets the camera position relative to where pacman is located.
//*******************************************************************



import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;

public class View extends JPanel
{
	private Model model;
	private boolean editMode = false;
	private int scrollPosY = 0;
	private static int scoreNum = 0;
	private static String editModeString = "Normal Mode";
	private static JLabel score;
	private static JLabel mode;
	
	private final int CENTER_POSITION = Game.WINDOW_HEIGHT / 2;
	private final int BOTTOM_BOUNDARY = Game.GAME_HEIGHT_BOTTOM - 365;
	private final int TOP_BOUNDARY = Game.GAME_HEIGHT_TOP + 400;


//*******************************************************************
//! Constructors
//*******************************************************************

	public View(Controller c, Model m)
	{
		this.setLayout(null);

		score = new JLabel("Score: "+ scoreNum);
		score.setForeground(Color.YELLOW);
		score.setFont(new Font("Arial", Font.BOLD, 16));
		score.setBounds((Game.WINDOW_WIDTH / 2) - 25, 20, 100, 30); // (x, y, width, height

		mode = new JLabel(editModeString);
		mode.setForeground(Color.YELLOW);
		mode.setFont(new Font("Arial", Font.BOLD, 20));
		mode.setBounds(Game.WINDOW_WIDTH - 300, Game.WINDOW_HEIGHT - 100, 250, 25);

		this.add(score);
		this.add(mode);
		this.revalidate();
		this.repaint();

		this.model = m;
		c.setView(this);

	}


//*******************************************************************
//! Methods
//*******************************************************************


//-------------------------------------------------------------------
// Put the loadImage method here. Instead of loading the images in the 
// view. The images should be loaded withing their respective classes.
//-------------------------------------------------------------------
	public static BufferedImage loadImage(String filename)
	{
		BufferedImage img = null;
		try
		{
			img = ImageIO.read(new File(filename));
			System.out.println(filename + " loaded successfully.");
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return img;

	}
	

//-------------------------------------------------------------------
// This is the view camera that follows pacman vertically. The extra
// conditions allow the camera to stop scrolling when it reaches the
// top or bottom of the screen. I think it looks better this way.
//-------------------------------------------------------------------
	public void followCamera()
	{

		// If Pacman is above the center of the screen, scroll up
		if (model.getPacmanY() < CENTER_POSITION && model.getPacmanY() > TOP_BOUNDARY)
		{
			this.scrollPosY = model.getPacmanY() - CENTER_POSITION;
		}
		// If Pacman is below the center of the screen, scroll down
		else if (model.getPacmanY() > CENTER_POSITION && model.getPacmanY() < BOTTOM_BOUNDARY)
		{
			this.scrollPosY = model.getPacmanY() - CENTER_POSITION;
		}
		
	}


//--------------------------------------------------------------------
// This is what draws the world.
//-------------------------------------------------------------------- 	
	public void paintComponent(Graphics g)
	{			
		if (editMode) {

			g.setColor(new Color(0, 0, 136)); //Sets pane color to blue if in edit mode
			
		} else {

			g.setColor(new Color(0, 0, 0)); //Sets pane color to black if not in edit mode
		}

		g.fillRect(0, 0, this.getWidth(), this.getHeight()); // Fills the rectangle with black.
		
		for (int i = 0; i < model.sprites.size(); i++) // Draws the sprites
		{
			model.sprites.get(i).draw(g, scrollPosY);
		}

	}


//*******************************************************************
//! Getters and Setters
//*******************************************************************


//-------------------------------------------------------------------
// This method updates the score JLabel.
//-------------------------------------------------------------------
	public static void updateScore(int newScore)
	{
		scoreNum += newScore;
		if (scoreNum < 0)
		{
			scoreNum = 0;
		}
		else
			score.setText("Score: " + scoreNum);

	}


//-------------------------------------------------------------------
// This method updates the mode JLabel.
//-------------------------------------------------------------------
	public static void setEditModeString(String newMode)
	{
		editModeString = newMode;
		mode.setText(editModeString);
	}
//-------------------------------------------------------------------
// This method changes the editMode. It is called within Controller.java
//-------------------------------------------------------------------
	public void setEditMode(boolean edit)
	{
		this.editMode = edit;					//! Move this to the model. Unnecessary to have here.
	}


//-------------------------------------------------------------------
// Getter for the Camera position
//-------------------------------------------------------------------
	public int getScrollPos()
	{
		return scrollPosY;
	}

//-------------------------------------------------------------------
// Setter for the Camera position
//-------------------------------------------------------------------
	public void setScrollPos(int scrollPos)
	{
		this.scrollPosY = scrollPos;
	}


}
