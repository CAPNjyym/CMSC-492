import java.awt.BorderLayout;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CountingGfx extends Thread implements java.awt.event.WindowListener
{
	private DashBoard db;
	private Counting game;
	private JFrame gameFrame;
	
	private JButton square = new JButton("Square");
	private JButton triangle = new JButton("Triangle");
	private JButton septagon = new JButton("Septagon");
	private JButton pentagon = new JButton("Pentagon");
	private JButton octagon = new JButton("Octagon");
	private JButton hexagon = new JButton("Hexagon");
	private JButton nonagon = new JButton("Nonagon");
	private JButton decagon = new JButton("Decagon");
	private int redShape;
	private GameShape tri, sqr, pent, hex, sept, oct, non, dec,
		shapes[] = new GameShape[8]; // array of all available shapes, listed in order of size/points
	private Color red = Color.RED, green = Color.GREEN, blue = Color.BLUE, yellow = Color.YELLOW;

	// all available colors, listed in shown order
	private Color[] colors = {red, green, blue, yellow};
	
	private JPanel panel;
	private Random rand = new Random();
	
	// for use by Counting
	private GameShape herd[];
	private double herd_pos_x[], herd_pos_y[], herd_speeds[];
	private long begin, herd_delays[];
	
	public CountingGfx(DashBoard db)
	{
		this.db = db;
		
		gameFrame = new JFrame("Counting");
		gameFrame.setSize(1024, 768);
		
		panel = (JPanel) gameFrame.getContentPane();
		panel.setBackground(Color.WHITE);
		panel.setSize(1024, 768);
		panel.setLayout(null);
		
		shapes[0] = tri = new GameShape(3, green, 100, 100);
		shapes[1] = sqr = new GameShape(4, green, 100, 100);
		shapes[2] = pent = new GameShape(5, green, 100, 100);
		shapes[3] = hex = new GameShape(6, green, 100, 100);
		shapes[4] = sept = new GameShape(7, green, 100, 100);
		shapes[5] = oct = new GameShape(8, green, 100, 100);
		shapes[6] = non = new GameShape(9, green, 100, 100);
		shapes[7] = dec = new GameShape(10, green, 100, 100);
		
		for (int i=0; i<shapes.length; i++)
		{
			shapes[i].setSize(200, 200);
			panel.add(shapes[i]);
		}
		
	    tri.setLocation(250, 500);
	    sqr.setLocation(450, 250);
	    pent.setLocation(250, 150);
	    hex.setLocation(50, 250);
	    sept.setLocation(50, 425);
	    oct.setLocation(450, 425);
	    non.setLocation(250, 325);
		
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.addWindowListener(this);
		gameFrame.setResizable(false);
		gameFrame.setIconImage(new ImageIcon("icon.jpg").getImage());
		gameFrame.setVisible(true);
		
		game = new Counting(this, db.getSubjectNumber() + ", " + db.getGender() + ", " + db.getSubjectAge() + ", ");
	}
	
	public void initCounting()
	{
		//game.outputToFile("1");
		for (int i=0; i<shapes.length; i++)
			shapes[i].setVisible(false);
	}
	
	// returns number of shapes that is answer
	public int generateShapes(int total, int key, int difficulty)
	{
		int i, count = 0,
			shapeVal, colorVal, maxShapeVal, maxColorVal;
		double minSpeed, varSpeed, minDelay, varDelay,
			difficulty_vars[];
		
		herd = new GameShape[total];
		herd_speeds = new double[total];
		herd_delays = new long[total];
		herd_pos_x = new double[total];
		herd_pos_y = new double[total];
		
		difficulty_vars = getDifficultyVars(difficulty);
		maxShapeVal = (int) difficulty_vars[0];
		maxColorVal = (int) difficulty_vars[1];
		minSpeed = difficulty_vars[2];
		varSpeed = difficulty_vars[3];
		minDelay = difficulty_vars[4];
		varDelay = difficulty_vars[5];
		
		for(i=0; i<total; i++)
		{
			shapeVal = rand.nextInt(maxShapeVal);
			colorVal = rand.nextInt(maxColorVal);
			// System.out.print(" " + shapeVal + "/" + colorVal);
			if (numFitsKey(shapeVal, colorVal, key))
			{
				count++;
				// System.out.print("!");
			}
			
			herd[i] = new GameShape(shapes[shapeVal]);
			herd[i].setColor(colors[colorVal]);
			herd_speeds[i] = varSpeed * rand.nextDouble() + minSpeed;
			herd_delays[i] = rand.nextInt((int) (1000 * varDelay)) + (int) (1000 * minDelay);
			herd_pos_x[i] = -160.0;
			herd_pos_y[i] = rand.nextInt(490) + 50;
			
			// initialize location, speed, etc
			herd[i].setSize(200, 200);
			herd[i].setLocation((int)herd_pos_x[i], (int)herd_pos_y[i]);
			
			panel.add(herd[i]);
		}
		begin = System.currentTimeMillis();
		// System.out.println();
		return count;
	}
	
	private double[] getDifficultyVars(int difficulty)
	{
		double[] vars = new double[6];
		
		// Maximum number of different shapes to use
		vars[0] = 2 * difficulty;
		
		// Maximum number of different colors to use
		vars[1] = difficulty + 1;
		if (vars[1] > 4)
			vars[1] = 4;
		
		// Minimum speed
		vars[2] = difficulty / 2.0;
		if (difficulty == 1)
			vars[2] = 1.0;
		
		// Variance in speed
		vars[3] = difficulty / 2.0 + 0.5;
		
		// Minimum delay
		vars[4] = 5 - difficulty;
		
		// Variance in delay
		vars[5] = 3 + difficulty;
		
		return vars;
	}
	
	// determines if count should go up based on shape/color and key
	private boolean numFitsKey(int shape, int color, int key)
	{
		switch (key)
		{
			case 0: // Count ALL the Shapes!
				return true;
			case 1: // Count All Triangles
				return (shape == 0);
			case 2: // Count All Squares
				return (shape == 1);
			case 3: // Count All Red Shapes
				return (color == 0);
			case 4: // Count All Green Shapes
				return (color == 1);
			case 5: // Count All Pentagons
				return (shape == 2);
			case 6: // Count All Hexagons
				return (shape == 3);
			case 7: // Count All Blue Shapes
				return (color == 2);
			case 8: // Count All Septagons
				return (shape == 4);
			case 9: // Count All Octagons
				return (shape == 5);
			case 10: // Count All Yellow Shapes
				return (color == 3);
			case 11: // Count All Nonagons
				return (shape == 6);
			case 12: // Count All Decagons
				return (shape == 7);
			default:
				return false;
		}
	}
	
	// moves all shapes across the screen
	public boolean scrollShapes()
	{
		boolean allRight = true;
		int i, total = herd.length;
		long now = System.currentTimeMillis();
		
		for (i=0; i<total; i++)
		{
			if (herd_pos_x[i] < 1025)
			{
				allRight = false;
				if (now - begin > herd_delays[i])
				{
					herd_pos_x[i] = herd_pos_x[i] + herd_speeds[i];
					herd[i].setLocation((int) (herd_pos_x[i]), (int) herd_pos_y[i]);
				}
			}
		}
		//panel.repaint();
		
		return allRight;
	}
	
	// removes shapes when they are finished
	public void EndRound()
	{
		int i, total = herd.length;
		
		for (i=0; i<total; i++)
		{
			herd[i].setVisible(false);
			herd[i].removeAll();
			panel.remove(herd[i]);
		}
	}
	
	public int getRedShape()
	{
		return redShape;
	}
	
	public void setRedShape(int redShape)
	{
		this.redShape = redShape;
	}
	
	public JButton getSquare()
	{
		return square;
	}
	
	public JButton getTriangle()
	{
		return triangle;
	}
	
	public JButton getSeptagon()
	{
		return septagon;
	}
	
	public JButton getPentagon()
	{
		return pentagon;
	}
	
	public JButton getOctagon()
	{
		return octagon;
	}
	
	public JButton getHexagon()
	{
		return hexagon;
	}
	
	public JButton getNonagon()
	{
		return nonagon;
	}
	
	public JButton getDecagon()
	{
		return decagon;
	}
	
	public JFrame getGameFrame()
	{
		return gameFrame;
	}
	
	public GameShape getTri()
	{
		return tri;
	}
	
	public GameShape getSqr()
	{
		return sqr;
	}
	
	public GameShape getPent()
	{
		return pent;
	}
	
	public GameShape getHex()
	{
		return hex;
	}
	
	public GameShape getSept()
	{
		return sept;
	}
	
	public GameShape getOct()
	{
		return oct;
	}
	
	public GameShape getNon()
	{
		return non;
	}
	
	public GameShape getDec()
	{
		return dec;
	}
	
	public Color getRed()
	{
		return red;
	}
	
	public Color getGreen()
	{
		return green;
	}
	
	public Color getBlue()
	{
		return blue;
	}
	
	public Color getYellow()
	{
		return yellow;
	}
	
	public Container getPanel()
	{
		return panel;
	}
	
	public Random getRand()
	{
		return rand;
	}
	
	public void setSquare(JButton square)
	{
		this.square = square;
	}
	
	public void setTriangle(JButton triangle)
	{
		this.triangle = triangle;
	}
	
	public void setSeptagon(JButton septagon)
	{
		this.septagon = septagon;
	}
	
	public void setPentagon(JButton pentagon)
	{
		this.pentagon = pentagon;
	}
	
	public void setOctagon(JButton octagon)
	{
		this.octagon = octagon;
	}
	
	public void setHexagon(JButton hexagon)
	{
		this.hexagon = hexagon;
	}
	
	public void setNonagon(JButton nonagon)
	{
		this.nonagon = nonagon;
	}
	
	public void setDecagon(JButton decagon)
	{
		this.decagon = decagon;
	}
	
	public void setGameFrame(JFrame gameFrame)
	{
		this.gameFrame = gameFrame;
	}
	
	public void setTri(GameShape tri)
	{
		this.tri = tri;
	}
	
	public void setSqr(GameShape sqr)
	{
		this.sqr = sqr;
	}
	
	public void setPent(GameShape pent)
	{
		this.pent = pent;
	}
	
	public void setHex(GameShape hex)
	{
		this.hex = hex;
	}
	
	public void setSept(GameShape sept)
	{
		this.sept = sept;
	}
	
	public void setOct(GameShape oct)
	{
		this.oct = oct;
	}
	
	public void setNon(GameShape non)
	{
		this.non = non;
	}
	
	public void setDec(GameShape dec)
	{
		this.dec = dec;
	}
	
	public void setRed(Color red)
	{
		this.red = red;
	}
	
	public void setGreen(Color green)
	{
		this.green = green;
	}
	
	public void setPanel(Container panel)
	{
		this.panel = (JPanel) panel;
	}
	
	public void setRand(Random rand)
	{
		this.rand = rand;
	}
	
	public void windowClosed(WindowEvent e)
	{
		//game.outputToFile(db.getSubjectNumber() + ", " + db.getGender() + ", " + db.getSubjectAge() + ", ");
		db.setCountingIsRunning(false);
	}
	
	public void windowActivated(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}