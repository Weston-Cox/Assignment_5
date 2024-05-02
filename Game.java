//**********************************************************************
// Name: Weston Cox
// Date: 3/22/2024
// Description: This is the main application class. Additionally, it is 
//     where the Main Method is located. It builds the JFrame with it's 
//     initial size. Then it runs the game at 25 fps. 
//**********************************************************************


import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{

	private Model model;
	private Controller controller;
	private View view;
	public static final int WINDOW_HEIGHT = 800;
	public static final int WINDOW_WIDTH = 800; // Do not change
	public static final int GAME_HEIGHT_TOP = 0;
	public static final int GAME_HEIGHT_BOTTOM = 1600; // Do not change
	public static final int GAME_WIDTH = 800; // Do not change


	public Game()
	{
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		this.setTitle("A5 - Intermediate, Hungry Pacman");	// Title of the window
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		view.addMouseListener(controller);
		this.addKeyListener(controller);
	}


//---------------------------------------------------------------------------------
// This method runs the game at 25FPS. It updates the controller, model, and view.
//---------------------------------------------------------------------------------
	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // This will indirectly call View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Try-Catch used to go to sleep for 40ms. Screen updates at 25FPS.
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}

//----------------------------------------------------------
//! Main Method
//----------------------------------------------------------
	public static void main(String[] args)
	{
		Game g = new Game();
		g.run();
	}
}
