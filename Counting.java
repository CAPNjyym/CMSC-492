import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.ColorModel;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.ArrayList;

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
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class Counting implements KeyListener
{
	// Required
	private CountingGfx CountingGfx;
	private Container panel;
	
	// Game Specific Swing Components
	private JFrame gameFrame;
	private JLabel instructions, instructions2, message, diffDisplay;
	private JTextField input;
	private JButton submit, quit, replay, increase, decrease;
	private JScrollPane scroll;
	private JTextArea results;
	private Random RNG = new Random();
	private DecimalFormat D = new DecimalFormat("#.000");
	
	// Game Specific primitive variables
	private int round, difficulty, total, count, answer, totalMessages;
	private boolean quitting, answered;
	private String messageList[];
	
	// Used for difficulty adjustment
	private int changeTime; // rounds since last difficulty adjustment
	private ArrayList<Data> data;
	private String info;
	
	public Counting(CountingGfx g, String i)
	{
		this.CountingGfx = g;
		gameFrame = CountingGfx.getGameFrame();
		CountingGfx.setGameFrame(gameFrame);
		this.panel = CountingGfx.getPanel();
		info = i;
		
		instructions = new JLabel("A message will appear telling you what to count.  " +
			"Objects will scroll across the screen.  Count the specified objects and remember the number.");
		instructions.setFont(new Font("Lucida Sans", Font.ITALIC, 14));
		instructions.setForeground(Color.BLUE);
		instructions.setBackground(Color.BLUE);
		instructions.setBorder(BorderFactory.createRaisedBevelBorder());
		instructions.setHorizontalAlignment(SwingConstants.CENTER);
		instructions.setVerticalAlignment(SwingConstants.TOP);
		instructions.setSize(1000, 40);
		instructions.setLocation(10, 10);
		instructions2 = new JLabel("After all objects have passed, " +
			"enter the number into the textfield and click \"Submit\".  " +
			"Click \"Check Results\" at any time to display your results.");
		instructions2.setFont(new Font("Lucida Sans", Font.ITALIC, 14));
		instructions2.setForeground(Color.BLUE);
		instructions2.setBackground(Color.BLUE);
		instructions2.setHorizontalAlignment(SwingConstants.CENTER);
		instructions2.setSize(1000, 20);
		instructions2.setLocation(10, 28);
		panel.add(instructions);
		panel.add(instructions2);
		
		message = new JLabel();
		message.setFont(new Font("Lucida Sans", Font.BOLD, 24));
		message.setForeground(Color.BLACK);
		message.setBackground(Color.BLUE);
		message.setBorder(BorderFactory.createRaisedBevelBorder());
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setSize(400, 38);
		message.setLocation(10, 690);
		panel.add(message);
		
		input = new JTextField();
		input.setFont(new Font("Lucida Sans", Font.PLAIN, 16));
		input.setSize(200, 39);
		input.setLocation(410, 690);
		input.addKeyListener(this);
		panel.add(input);
		
		submit = new JButton("Submit");
		submit.setSize(200, 38);
		submit.setLocation(608, 690);
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				try
				{
					answer = Integer.parseInt(input.getText());
					if (answer < 0)
						throw new Exception();
					
					answered = true;
				}
				catch (Exception e)
				{
					input.setText("Invalid Answer");
					input.grabFocus();
				}
			}	
		});
		panel.add(submit);
		
		quit = new JButton("Check Results");
		quit.setSize(200, 38);
		quit.setLocation(807, 690);
		quit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				quitGame();
			}	
		});
		panel.add(quit);
		
		results = new JTextArea();
		results.setBackground(Color.LIGHT_GRAY);
		results.setBorder(BorderFactory.createLineBorder(Color.black));
		results.setLineWrap(true);
		results.setEditable(true);
		results.setSize(400, 600);
		results.setLocation(312, 84);
		
		replay = new JButton("Replay");
		replay.setSize(200, 38);
		replay.setLocation(10, 690);
		replay.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				quitting = false;
				initGame();
			}	
		});
		panel.add(replay);
		
		diffDisplay = new JLabel();
		diffDisplay.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
		diffDisplay.setForeground(Color.BLACK);
		diffDisplay.setBackground(Color.BLUE);
		diffDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		diffDisplay.setSize(200, 38);
		diffDisplay.setLocation(10, 540);
		panel.add(diffDisplay);
		
		increase = new JButton("Increase Difficulty");
		increase.setFont(new Font("Arial", Font.BOLD, 18));
		increase.setForeground(Color.RED);
		increase.setSize(200, 38);
		increase.setLocation(10, 490);
		increase.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				difficulty++;
				changeTime = 0;
				
				if (difficulty > 1)
					decrease.setVisible(true);
				if (difficulty >= 4)
				{
					increase.setVisible(false);
					difficulty = 4;
				}
				
				diffDisplay.setText("Difficulty Level: " + difficulty);
			}	
		});
		panel.add(increase);
		
		decrease = new JButton("Decrease Difficulty");
		decrease.setFont(new Font("Arial", Font.BOLD, 18));
		decrease.setForeground(Color.BLUE);
		decrease.setSize(200, 38);
		decrease.setLocation(10, 590);
		decrease.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				difficulty--;
				changeTime = 0;
				
				if (difficulty <= 1)
				{
					decrease.setVisible(false);
					difficulty = 1;
				}
				if (difficulty < 4)
					increase.setVisible(true);
				
				diffDisplay.setText("Difficulty Level: " + difficulty);
			}	
		});
		panel.add(decrease);
		
		scroll = new JScrollPane();
		scroll.setSize(400, 600);
		scroll.setLocation(312, 84);
		
		scroll.setViewportView(results);
		panel.add(scroll);
		
		initializeVariables();
		CountingGfx.initCounting();
		initGame();
		runGame();
	}
	
	private void initializeVariables()
	{
		round = 0;
		difficulty = 2;
		answer = -1;
		
		quitting = answered = false;
		messageList = messages();
		totalMessages = messageList.length;
		
		changeTime = 0;
		data = new ArrayList<Data>();
		
		diffDisplay.setText("Difficulty Level: " + difficulty);
	}
	
	private void initGame()
	{
		instructions.setText("A message will appear telling you what to count.  " +
			"Objects will scroll across the screen.  Count the specified objects and remember the number.");
		instructions.setFont(new Font("Lucida Sans", Font.ITALIC, 14));
		instructions.setVerticalAlignment(SwingConstants.TOP);
		
		instructions2.setVisible(true);
		message.setVisible(true);
		input.setVisible(false);
		submit.setVisible(false);
		quit.setVisible(true);
		scroll.setVisible(false);
		replay.setVisible(false);
		diffDisplay.setVisible(false);
		increase.setVisible(false);
		decrease.setVisible(false);
	}
	
	private void runGame()
	{
		int messageNum;
		long answerTime;
		
		try
		{
			PrintWriter out;
			
			while (true)
			{
				out = new PrintWriter(new FileWriter("counting.data", true));
				
				while (!quitting)
				{
					// sets message of what to count
					total = (totalMessages * difficulty / 4) + 2;
					if (total > totalMessages)
						total = totalMessages;
					messageNum = RNG.nextInt(total);
					message.setText(messageList[messageNum]);
					total = getNumOfShapes();
					count = CountingGfx.generateShapes(total, messageNum, difficulty);
					
					// scrolls shapes across the screen
					while (!CountingGfx.scrollShapes())
					{
						pause(7);
						if (quitting)
							break;
					}
					
					input.setText("");
					input.setVisible(true);
					submit.setVisible(true);
					input.grabFocus(); // set focus to input
					answerTime = System.currentTimeMillis();
					
					// waits for answer before starting next round
					while (!answered)
					{
						if (quitting)
							break;
					}
					
					answerTime = System.currentTimeMillis() - answerTime;
					input.setVisible(false);
					submit.setVisible(false);
					answered = false;
					CountingGfx.EndRound();
					
					if (!quitting)
					{
						data.add(new Data(difficulty, answer, count, total, answerTime / 1000.0));
						out.println(info + data.get(round));

						round++;
						adjustDifficulty();
					}
					// accuracy: penalty per wrong
					// time: no penalty for answer within 5 seconds
					//		penalty beyond 5 seconds
				}
				
				out.close();
			}
		}
		
		catch (IOException IOE)
			{IOE.printStackTrace();}
		
		// difficulty differences
		// 0: all same objects move left to right at slow set speed and angle (0)
		// 1: all objects move left to right at medium set speed and angle (0)
		// 2: all objects move left to right at different speeds and set angle (0)
		// 3: all objects move left to right at different speeds and differing angles (-30 to 30?)
		// 4: all objects move from anywhere to anywhere else at any speed and corresponding angle
		// 5: all objects move from anywhere to anywhere with changing speed and correspronding angle
		// 6?: all objects move from anywhere to anywhere with changing speed and changing angles
	}
	
	private void quitGame()
	{
		/* UNCOMMENT AFTER EXIT BUTTON IS FIXED
		if (quitting)
			System.exit(0);
		*/
		quit.setVisible(false);
		quitting = true;
		
		printResults();
		
		instructions.setText("RESULTS");
		instructions.setVerticalAlignment(SwingConstants.CENTER);
		instructions.setFont(new Font("Lucida Sans", Font.BOLD, 36));
		instructions2.setVisible(false);
		message.setVisible(false);
		//quit.setText("Exit");
		scroll.setVisible(true);
		replay.setVisible(true);
		
		diffDisplay.setText("Difficulty Level: " + difficulty);
		diffDisplay.setVisible(true);
		if (difficulty > 1)
			decrease.setVisible(true);
		if (difficulty < 4)
			increase.setVisible(true);
		
		CountingGfx.EndRound();
	}
	
	
	private int getNumOfShapes()
	{
		int flux = 0, cont = 0;
		
		switch (difficulty)
		{
			case 1:
				flux = 3;
				cont = 6;
				break;
			case 2:
				flux = 6;
				cont = 12;
				break;
			case 3:
				flux = 8;
				cont = 15;
				break;
			case 4:
				flux = 10;
				cont = 20;
				break;
			default:
				return 0;
		}
		
		return RNG.nextInt(flux) + cont;
	}
	
	private void adjustDifficulty()
	{
		int newDif = difficulty;
		Data roundData;
		changeTime++;
		
		if (changeTime >= 4)
		{
			// figure out if difficulty needs to change
			int i, totalMistakes = 0;
			double totalTime = 0;
			
			for (i=round-1; i>round-5; i--)
			{
				roundData = data.get(i);
				
				totalMistakes += roundData.mistakes(); // total mistakes for last 4 rounds
				totalTime += roundData.time(); // total time for last 4 rounds
			}
			
			System.out.println(totalMistakes);
			System.out.println(totalTime);
			
			// adjust difficulty based on mistakes and time
			if (totalMistakes > 6) // > 1.5 mistakes per round
			{
				newDif--;
			}
			else if (totalMistakes > 3) // 1 to 1.5 mistakes per round
			{
				if (totalTime > 20.0) // > 5 seconds per round
					newDif--;
			}
			else if (totalMistakes > 1) // .5 to 1 mistakes per round
			{
				if (totalTime > 40.0) // > 10 seconds per round
					newDif--;
			}
			else // if (totalMistakes <= 1) // 0 - .5 mistakes per round
			{
				if (totalTime < 20.0) // < 5 seconds per round
					newDif++;
			}
			
			if (newDif > 4)
				newDif = 4;
			else if (newDif < 1)
				newDif = 1;
			
			if (difficulty != newDif)
			{
				System.out.println("Difficulty changed from " + difficulty + " to " + newDif);
				changeTime = 0;
				difficulty = newDif;
			}
		}
	}
	
	
	private void printResults()
	{
		int i, mistakes;
		double time, avgMis = 0.0, avgTime = 0.0;
		Data roundData;
		
		results.setText("");
		
		for(i=0; i<round; i++)
		{
			roundData = data.get(i);
			mistakes = roundData.mistakes();
			time = roundData.time();
			avgMis += mistakes;
			avgTime += time;
			
			results.append("Round " + (i+1) + ": " + mistakes + " mistakes\t" + "@ " + time + " seconds\n");
		}
		
		avgMis /= round;
		avgTime /= round;
		
		results.append("\nAverage : " + D.format(avgMis) + " per round" +
				"\nAverage Response Time: " + D.format(avgTime) + " seconds\n\n");
	}
	
	public void outputToFile(String info)
	{
		try
		{
			PrintWriter out = new PrintWriter(new FileWriter("counting.data", true));
			int i;
			
			for (i=0; i<round; i++)
				out.println(info + ", " + data.get(i));
			
			out.close();
		}
		catch (IOException IOE)
			{IOE.printStackTrace();}
	}
	
	
	private void pause(long millis)
	{
		try {Thread.sleep(millis);}
	    catch(InterruptedException e){}
	}
	
	
	private String[] messages()
	{
		String[] messages = {"Count ALL the Shapes!",
									"Count All Triangles",
									"Count All Squares",
									"Count All Red Shapes",
									"Count All Green Shapes",
									// difficulty 2+ messages
									"Count All Pentagons",
									"Count All Hexagons",
									"Count All Blue Shapes",
									// difficulty 3+ messages
									"Count All Septagons",
									"Count All Octagons",
									"Count All Yellow Shapes",
									// difficulty 4+ messages
									"Count All Nonagons",
									"Count All Decagons"};
		return messages;
	}
	
	
	public void keyPressed(KeyEvent KE)
	{
		if (KE.getKeyCode() == KeyEvent.VK_ENTER)
		{
			try
			{
				answer = Integer.parseInt(input.getText());
				if (answer < 0)
					throw new Exception();
				
				answered = true;
			}
			catch (Exception e)
				{input.setText("Invalid Answer");}
		}
	}
	
	
	public void keyReleased(KeyEvent KE){}
	public void keyTyped(KeyEvent KE){}
	
	private class Data
	{
		private int difficulty, answer, correct, total;
		private double time;
		
		private Data(int d, int a, int c, int t, double t2)
		{
			difficulty = d;
			answer = a;
			correct = c;
			total = t;
			time = t2;
		}
		
		private int mistakes()
		{
			return Math.abs(correct - answer);
		}
		
		private double time()
		{
			return time;
		}
		
		public String toString()
		{
			return difficulty + ", " + answer + ", " + correct + ", " + total + ", " + D.format(time);
		}
	}
}
