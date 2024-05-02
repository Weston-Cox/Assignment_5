//*****************************************************************************
// Name: Weston Cox
// Date: 3/22/2024
// Description: The Pellet.java class extends Sprite and contains all the information
//    and methods for the Pellet object. The Pellet object doesn't move, but it 
//    can be eaten by the Pacman object.
//*****************************************************************************


import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Pellet extends Sprite{

    private static BufferedImage pellet_image = null;

    private boolean isValid = true;

    private final int PELLET_WIDTH = 10;
    private final int PELLET_HEIGHT = 10;


//*******************************************************************
//! Constructors
//*******************************************************************

    public Pellet()
    {
        this.x = 100;
        this.y = 300;
        this.w = PELLET_WIDTH;
        this.h = PELLET_HEIGHT;

        if (pellet_image == null)
            pellet_image = View.loadImage("images/pellet.png");
    }

    public Pellet(int x, int y) 
    {
        this.x = x;
        this.y = y;
        this.w = PELLET_WIDTH;
        this.h = PELLET_HEIGHT;

        if (pellet_image == null)
            pellet_image = View.loadImage("images/pellet.png");

    }


//*******************************************************************
//! Methods
//*******************************************************************


//-------------------------------------------------------------------
// This method updates the pellet. Called in Model.java to check whether
// the pellet is still valid.
//-------------------------------------------------------------------
    public boolean update()
    {
        return isValid;
    }


//-------------------------------------------------------------------
// This method draws the pellet. Called in View.java.
//-------------------------------------------------------------------
    public void draw(Graphics g, int scrollPosY)
    {
        g.drawImage(pellet_image, this.x, this.y - scrollPosY, this.w, this.h, null);
    }


//-------------------------------------------------------------------
// This method marshals the pellet object into the map.json file.
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
// This method unmarshals the pellet object from the map.json file.
//-------------------------------------------------------------------
	Pellet(Json ob)
	{
		this.x = (int)ob.getLong("x");
		this.y = (int)ob.getLong("y");
		this.w = (int)ob.getLong("w");
		this.h = (int)ob.getLong("h");
	}


//-------------------------------------------------------------------
// This method checks if the user clicked within the boundary of a pellet.
//-------------------------------------------------------------------
    public boolean userClickedPellet(int x, int y)
    {
        if (x >= this.x && x <= this.x + this.w && y >= this.y && y <= this.y + this.h)
        {
            return true;
        }
        return false;
    }


//*******************************************************************
//! Methods for Sprite Characteristics
//*******************************************************************


//-------------------------------------------------------------------
// Method that is called to tell whether a sprite is a pellet object.
// Called in Model.java.
//-------------------------------------------------------------------
    @Override
    protected boolean isPellet() {
        return true;
    }


//-------------------------------------------------------------------
// Method that is called to tell whether a sprite is moving.
// Called in Model.java.
//-------------------------------------------------------------------
    @Override
    protected boolean isMoving() {
        return false;
    }


//-------------------------------------------------------------------
//
//-------------------------------------------------------------------
    @Override
    public String toString()
    {
        return ("Pellet (x,y) = (" + this.x + ", " + this.y + "), w = " + this.w + ", h = " + this.h);
    }
//-------------------------------------------------------------------
// Method that is called to indicate that the pellet has been eaten by
// the Pacman object. Invalidates the pellet object, which is then removed.
// Called in Model.java.
//-------------------------------------------------------------------
    public void isEaten()
    {
        isValid = false;
        View.updateScore(100);
        System.out.println("Nom nom...");
    }
}
