

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @author Mike
 *
 */
public class QuickClickGfx implements java.awt.event.WindowListener
{
	private QuickClick quickClickGame;
	private JButton square = new JButton("Square");
	private JButton triangle = new JButton("Triangle");
	private JButton septagon = new JButton("Septagon");
	private JButton pentagon = new JButton("Pentagon");
	private JButton octagon = new JButton("Octagon");
	private JButton hexagon = new JButton("Hexagon");
	private JButton nonagon = new JButton("Nonagon");
	private JButton decagon = new JButton("Decagon");
	private DashBoard db;
	private Integer difficulty = 0;
	
	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	private JFrame gameFrame;
	private int redShape;
	private GameShape tri = new GameShape(), sqr = new GameShape(), pent = new GameShape(),
		hex = new GameShape(), sept = new GameShape(), oct= new GameShape(), non = new GameShape(), dec = new GameShape();
	private Color red = new Color(255, 0, 0);
	private Color green = new Color(0, 255, 0);
	
	public int getRedShape()
	{
		return redShape;
	}

	public void setRedShape(int redShape)
	{
		this.redShape = redShape;
	}

	private JPanel panel;
	private Random rand = new Random();
	public QuickClickGfx(DashBoard db) throws Exception
	{
		this.db = db;
	    gameFrame = new JFrame("Quick Click");
	    gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    gameFrame.addWindowListener(this);
	    gameFrame.setSize(1024, 768);
	    
	    panel = (JPanel) gameFrame.getContentPane();
	    panel.setBackground(Color.WHITE);
	    panel.setSize(1024, 768);
	    panel.setLayout(null);
	    
	    sqr.setSize(200,200);
	    sqr.setLocation(450,250);
	    
	    tri.setSize(200,200);
	    tri.setLocation(450, 425);
	    
	    pent.setSize(200,200);
	    pent.setLocation(50, 425);
	    
	    hex.setSize(200,200);
	    hex.setLocation(50,250);
	    
	    sept.setSize(200,200);
	    sept.setLocation(250,150);
	    
	    oct.setSize(200,200);
	    oct.setLocation(250, 500);
	   
	    non.setSize(200, 200);
	    non.setLocation(250, 325);
	   
	    
	    gameFrame.addWindowListener(this);
		gameFrame.setResizable(false);
		gameFrame.setIconImage(new ImageIcon("icon.jpg").getImage());
		gameFrame.setVisible(true);
		addShapes();
		update();
		quickClickGame = new QuickClick(this);
	}
	

	public void addShapes()
	{
		panel.remove(tri);  panel.add(tri);
		panel.remove(sqr);  panel.add(sqr);
		panel.remove(pent); panel.add(pent);
		panel.remove(hex);  panel.add(hex);
		panel.remove(sept); if (difficulty >= 1) panel.add(sept);
		panel.remove(oct);  if (difficulty >= 2) panel.add(oct);
		panel.remove(non);  if (difficulty >= 3) panel.add(non);
		
	}
	public void update()
	{
		redShape = rand.nextInt(4 + difficulty);
		//System.out.println(redShape);
			    

		tri.setEdges(3);
	    if (redShape == 0)	    	
	    	tri.setColor(red);
	    else tri.setColor(green);
	    tri.repaint();
	
	    sqr.setEdges(4);
	    if (redShape == 1)
	    	sqr.setColor(red);
	    else sqr.setColor(green);
	    
	    pent.setEdges(5);
		if (redShape == 2)
			pent.setColor(red);
		else pent.setColor(green);
	   
		hex.setEdges(6);
	    if (redShape == 3)
	    	hex.setColor(red);
	    else hex.setColor(green);
	   
	    sept.setEdges(7);
	    if (redShape == 4)
	    	sept.setColor(red);
	    else sept.setColor(green);
	    
	    oct.setEdges(8);
	    if (redShape == 5)
	    	oct.setColor(red);
	    else oct.setColor(green);

	    non.setEdges(9);
	    if (redShape == 6)
	    	non.setColor(red);
	    else non.setColor(green);

	    panel.repaint();
	    
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
	//unused stubs
	
	int windowEventHandler = 0;
	public void windowActivated(WindowEvent e) {}
	public void windowClosing(WindowEvent e) 
	{
		if (windowEventHandler == 0)
		{
			windowClose(e);
		}
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	
	//For some weird reason windowClosing executes twice, so I added this method.
	//Refer to the if statement in windowClosing(WindowEvent e).
	private void windowClose(WindowEvent e)
	{
		db.setQuickclickIsRunning(false);
		try 
		{
			
			//the constructor in this filewrite will make the output stream
			//append any writes to the end of the file, so we can keep building the file up,
			//just like the parkinsons_UPDRM.data from CMSC509
			FileWriter fstream = new FileWriter("results.data", true);
			PrintWriter out = new PrintWriter(fstream);
		
			
			//sub#, gender, age, round, stdev, median, mean, time, mistakes, difficulty
			ArrayList<QuickClick.RoundData> res = quickClickGame.getFileOutput();
			for (int i = 0; i < res.size(); i ++)
			{
				out.println(db.getSubjectNumber() + ", " + db.getGender() + ", " + db.getSubjectAge() + ", " + res.get(i));
			}
			out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		windowEventHandler++;
	}

	@Override
	public void windowClosed(WindowEvent e) {}
	
	
}



