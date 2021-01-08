
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ColorModel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;


public class QuickClick
{
	public ArrayList<RoundData> getFileOutput() {
		return fileOutput;
	}
	public void setFileOutput(ArrayList<RoundData> fileOutput) {
		this.fileOutput = fileOutput;
	}

	private ArrayList<RoundData> fileOutput = new ArrayList<RoundData>();
	private QuickClickGfx quickClickGfx;
	private JButton square;
	private JButton triangle;
	private JButton octagon;
	private JButton pentagon;
	private JButton septagon;
	private JButton hexagon;
	private JButton nonagon;
	private JButton decagon;
	private JButton playAgain;
	private JButton decDiff;
	private JButton incDiff;
	
	private JFrame gameFrame;
	private Container panel;

	private DecimalFormat df = new DecimalFormat("###.###");

	
	private JLabel ready;
	private JLabel stats = new JLabel(); 
	private JLabel encouragement = new JLabel();
	private JLabel res;
	
	private ArrayList<Double> finalResults = new ArrayList<Double>();
	private JTextArea results = new JTextArea();
		
	private Random rand;   
	
	private int mistakes = 0;
	private int round = 1;
	
	private int gameRounds = 101;
	
	private double lastRoundTime = 0;
	private double beginTime = System.currentTimeMillis();
	private double startTime;
	private double endTime;
	private double totalTime;
	private int recentMistakes;
	private int difficulty;
	
	public QuickClick(QuickClickGfx g)
	{
		this.quickClickGfx = g;
		this.panel = quickClickGfx.getPanel();
		ready = new JLabel();
		ready.setSize(200,60);
		ready.setLocation(20, 70);
		ready.setVisible(true);
		ready.setFont(new Font("Comic Sans", Font.BOLD, 30));
		panel.add(ready);
		
		square = quickClickGfx.getSquare();
		triangle = quickClickGfx.getTriangle();
		octagon = quickClickGfx.getOctagon();
		pentagon = quickClickGfx.getPentagon();
		septagon = quickClickGfx.getSeptagon();
		hexagon = quickClickGfx.getHexagon();
		gameFrame = quickClickGfx.getGameFrame();
		quickClickGfx.setGameFrame(gameFrame);
		
		JLabel intro = new JLabel("There are buttons which are named by some shapes. \n" +
				"For each round you will click on the button that corresponds to a red colored \n" +
				"shape. The game will last 20 rounds.");
		intro.setBackground(Color.BLUE);
		intro.setForeground(Color.BLUE);
		intro.setBorder(BorderFactory.createRaisedBevelBorder());
		intro.setFont(new Font("Lucida Sans", Font.ITALIC, 12));
		intro.setSize(1000,30);
		intro.setLocation(10,10);
		panel.add(intro);
		
		encouragement.setSize(700,80);
		encouragement.setLocation(200,60);
		encouragement.setFont(new Font("Comic Sans", Font.BOLD, 30));
		panel.add(encouragement);
		
		/*decDiff = new JButton("Play again, less difficult");
		decDiff.setSize(250,50);
		decDiff.setLocation(750,75);
		decDiff.setFont(new Font("Comic Sans", Font.BOLD, 16));
		decDiff.setForeground(Color.BLUE);
		decDiff.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) 
			{
				panel.remove(decDiff);
				panel.remove(incDiff);
				panel.remove(playAgain);
				quickClickGfx.setDifficulty(quickClickGfx.getDifficulty() - 1);
				difficulty = quickClickGfx.getDifficulty();
				gameRounds = gameRounds + 20;
				addButtons();
				quickClickGfx.addShapes();
				gameRound();
				
			}
			
		});*/
		
		/*incDiff = new JButton("Play again, more difficult");
		incDiff.setSize(250,50);
		incDiff.setLocation(750,175);
		incDiff.setFont(new Font("Comic Sans", Font.BOLD, 16));
		incDiff.setForeground(Color.RED);
		incDiff.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) 
			{
				panel.remove(incDiff);
				panel.remove(playAgain);
				panel.remove(decDiff);
				quickClickGfx.setDifficulty(quickClickGfx.getDifficulty() + 1);
				difficulty = quickClickGfx.getDifficulty();
				gameRounds = gameRounds + 20;
				addButtons();
				quickClickGfx.addShapes();
				gameRound();
				
			}
			
		});*/
		
		/*playAgain = new JButton("Play again, same difficulty");
		playAgain.setFont(new Font("Comic Sans", Font.BOLD, 16));
		playAgain.setSize(250,50);
		playAgain.setLocation(750,125);
		playAgain.setForeground(Color.GREEN);
		playAgain.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) 
			{
				panel.remove(playAgain);
				panel.remove(decDiff);
				panel.remove(incDiff);
				gameRounds = gameRounds + 20;
				gameRound();
				
			}
			
		});*/
		
		
		stats.setSize(600, 75);
		stats.setFont(new Font("Lucida Sans", Font.ITALIC, 20));
		stats.setLocation(25, 630);
		panel.add(stats);
		
		res = new JLabel("Results:");
		res.setBackground(Color.BLUE);
		res.setForeground(Color.BLUE);
		res.setFont(new Font("Lucida Sans", Font.BOLD, 15));
		res.setSize(100,50);
		res.setLocation(850,250);
		panel.add(res);
	
		//The next 80ish lines of code are all the buttons and their actionlisteners.
		
		square = new JButton("Square");
		square.setSize(100,50);
		square.setLocation(650, 500);
		square.addActionListener(new QuickClickButtonActionListener(1));
		
	    triangle = new JButton("Triangle");
		triangle.setSize(100,50);
		triangle.setLocation(650, 450);
		triangle.addActionListener(new QuickClickButtonActionListener(0));
		
		pentagon = new JButton("Pentagon");
		pentagon.setSize(100,50);
		pentagon.setLocation(650, 550);
		pentagon.addActionListener(new QuickClickButtonActionListener(2));
				
		hexagon = new JButton("Hexagon");
		hexagon.setSize(100,50);
		hexagon.setLocation(650, 600);
		hexagon.addActionListener(new QuickClickButtonActionListener(3));
		
		octagon = new JButton("octagon");
		octagon.setSize(100,50);
		octagon.setLocation(650, 350);
		octagon.addActionListener(new QuickClickButtonActionListener(5));
		
		
		septagon = new JButton("septagon");
		septagon.setSize(100,50);
		septagon.setLocation(650, 400);
		septagon.addActionListener(new QuickClickButtonActionListener(4));

		
		nonagon = new JButton("nonagon");
		nonagon.setSize(100,50);
		nonagon.setLocation(650, 300);
		nonagon.addActionListener(new QuickClickButtonActionListener(6));

		
		//Sets up a scrollpane and adds a JTextArea as the scrollpane's viewport
		JScrollPane scroll = new JScrollPane();
		scroll.setSize(175, 400);
		scroll.setLocation(800, 300);
		
		results.setSize(175, 400);
		results.setLocation(800, 300);
		results.setLineWrap(true);
		results.setBackground(Color.LIGHT_GRAY);
		results.setEditable(false);
		results.setBorder(BorderFactory.createLineBorder(Color.black));
		scroll.setViewportView(results);
		panel.add(scroll);
		ready.setText("GO!");
		addButtons();
		gameRound();

	}
	public void gameRound()
	{
		septagon.setForeground(Color.BLACK);
		square.setForeground(Color.BLACK);
		pentagon.setForeground(Color.BLACK);
		hexagon.setForeground(Color.BLACK);
		triangle.setForeground(Color.BLACK);
		octagon.setForeground(Color.BLACK);
		nonagon.setForeground(Color.BLACK);
		rand = new Random();
		if (quickClickGfx.getDifficulty() >= 3)
		{
		    
			int r = rand.nextInt(7);
			if (r == 0) septagon.setForeground(Color.RED);
			else if (r == 1) square.setForeground(Color.RED);
			else if (r == 2) pentagon.setForeground(Color.RED);
			else if (r == 3) hexagon.setForeground(Color.RED);
			else if (r == 4) triangle.setForeground(Color.RED);
			else if (r == 5) nonagon.setForeground(Color.RED);
			else octagon.setForeground(Color.RED);
		}

		startTime = System.currentTimeMillis();
		if (round > 0)
		{
			ready.setText("Round " + (round));
			ready.repaint();
			stats.setText("       Std Dev: " + df.format(stdev(finalResults)) + " " + "\nMean: " + df.format(mean(finalResults)) + " " + "\nMedian: " + df.format(median(finalResults)) + " \nMistakes: " + mistakes);
		}
		
		
		/*if ((round % 20) == 0 && round != 0)
		{
			if (quickClickGfx.getDifficulty()> 0) panel.add(decDiff);
			if (quickClickGfx.getDifficulty()< 3) panel.add(incDiff);
			panel.add(playAgain);
		}*/
		quickClickGfx.update();
	}
	
	private double mean(List<Double> res)
	{
		double mean = 0.0;
		for (int i = 0; i < res.size(); i++)
		{
			mean = mean + res.get(i);
		}
		mean = mean/res.size();
		return mean;
		
	}
	private double median(ArrayList<Double> res)
	{
		if (res.size() == 0) return 0;
			double median = 0.0;
			ArrayList<Double> med = new ArrayList<Double>();
			for (int i = 0; i < res.size(); i++)
			{
				med.add(res.get(i));
			}
			Collections.sort(med);
			median = med.get(med.size()/2);
			return median;
		
	}
	private double stdev(ArrayList<Double> res)
	{
		double mean = mean(res);
		double stdev = 0.0;
		for (int i = 0; i < res.size(); i++)
		{
			stdev = stdev + (Math.pow(res.get(i) - mean, 2));
		}
		stdev = stdev/res.size();
		stdev = Math.sqrt(stdev);
		return stdev;
	}
	
	public void addButtons()
	{
		panel.remove(triangle); panel.add(triangle);
		panel.remove(square);   panel.add(square);
		panel.remove(pentagon); panel.add(pentagon);
		panel.remove(hexagon);  panel.add(hexagon);
		
		panel.remove(septagon); if (quickClickGfx.getDifficulty() >= 1) panel.add(septagon);
		panel.remove(octagon);  if (quickClickGfx.getDifficulty() >= 2) panel.add(octagon);
		panel.remove(nonagon);  if (quickClickGfx.getDifficulty() >= 3) panel.add(nonagon);
		
	}
	
	class QuickClickButtonActionListener implements ActionListener
	{
		
		private int redShape;
		public QuickClickButtonActionListener(int shape)
		{
			this.redShape = shape;
		}
		public void actionPerformed(ActionEvent ae)
		{
			if (round < gameRounds)
			{
				encouragement.setForeground(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
				double roundTime = 0.0;
				endTime=System.currentTimeMillis();
				roundTime = (endTime-startTime);
				finalResults.add(roundTime/1000);
				if (quickClickGfx.getRedShape() == redShape)
				{
					if(roundTime/1000 < 1) encouragement.setText(roundTime/1000 + " seconds: Excellent, keep it up!");
					else if (roundTime/1000 > 1 && roundTime/1000 < 5) encouragement.setText(roundTime/1000 + " seconds: Not bad, keep it up!");
					else encouragement.setText(roundTime/1000 + " seconds: That was pretty slow! Wake up!");
					
					
					fileOutput.add(new RoundData(round, stdev(finalResults), median(finalResults), mean(finalResults), roundTime/1000, mistakes, difficulty));
					results.append("\nRound " + round + ":  " + roundTime/1000);
					gameRound();
				}
				else
				{
					encouragement.setText("You picked the wrong shape!!");
					results.append("\nRound " + round + ":  " + roundTime/1000);
					mistakes++;
					recentMistakes++;
					fileOutput.add(new RoundData(round, stdev(finalResults), median(finalResults), mean(finalResults), roundTime/1000, mistakes, difficulty));
					gameRound();
				}
				if (round % 5 == 0 && round != 0)
				{
					System.out.println("round check");
					List<Double> test = finalResults.subList(finalResults.size()-5, finalResults.size());
					for (int i = 0; i< test.size(); i++)
					{
						System.out.print(test.get(i) + " ");
					}
					System.out.println(mean(test));
					if ((recentMistakes < 2 && mean((List<Double>)finalResults.subList(finalResults.size()-5, finalResults.size())) < 2))
					{
						System.out.println("difficulty changed");
						if(quickClickGfx.getDifficulty()<3)
						{
							quickClickGfx.setDifficulty(quickClickGfx.getDifficulty()+1);
						}
					}
					else
					{
						if (quickClickGfx.getDifficulty()>0)
						{
							quickClickGfx.setDifficulty(quickClickGfx.getDifficulty()-1);
						
						}
					}
					addButtons();
					quickClickGfx.addShapes();
					recentMistakes = 0;
				}
				round++;
				
				//Difficulty handling!
				
			}
		}
	}
	
	class RoundData
	{
		int round;
		double stdev;
		double median;
		double mean;
		double time;
		int mistakes;
		int difficulty;
		
		
		public RoundData(int x, double st, double med, double ave, double time, int mistakes, int diff)
		{
			round = x;
			stdev = st;
			median = med;
			mean = ave;
			this.time = time;
			this.mistakes = mistakes;
			this.difficulty = diff;
		}
		public String toString()
		{
			String x = "";			
			x = round + ", " + stdev + ", " + median + ", " + mean + ", " + time + ", " + mistakes + ", " + difficulty;
			return x;
			
		}
		
	}
}

