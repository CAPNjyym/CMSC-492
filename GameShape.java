import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Polygon;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class GameShape extends JComponent
{
	private int edges;
	private Color color;
	private int x;
	private int y;
	
	public GameShape(int edges, Color color, int xLoc, int yLoc)
	{
		this.edges = edges;
		this.color = color;
		this.x = xLoc;
		this.y = yLoc;
	}
	public GameShape(GameShape g)
	{
		this.edges = g.edges; //g.getEdges();
		this.color = g.color; //g.getColor();
		this.x = g.x;
		this.y = g.y;
	}
	public GameShape()
	{
		x=55;
		y=55;
	}
	public void paint(Graphics g) 
	{
		Polygon border = new Polygon();
		Polygon p = new Polygon();
		for (int i = 0; i < edges; i++)
		{
			border.addPoint((int) (x + 55 * Math.cos(i*2*Math.PI/edges)),(int) (y + 55 * Math.sin(i*2*Math.PI/edges)));
			p.addPoint((int) (x + 50 * Math.cos(i*2*Math.PI/edges)),(int) (y + 50 * Math.sin(i*2*Math.PI/edges)));
		}
		g.setColor(Color.BLACK);
		g.fillPolygon(border);
		
		g.setColor(color);
		g.fillPolygon(p);
		
		super.paint(g);
	}
	public int getEdges()
	{
		return edges;
	}
	public Color getColor()
	{
		return color;
	}
	public void setEdges(int edges)
	{
		this.edges = edges;
	}
	public void setColor(Color color)
	{
		this.color = color;
	}
}
