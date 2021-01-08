import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.IIOImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class DashBoard implements Runnable
{
	private JFrame dash;
	private JPanel panel;
	private ImageIcon logo;
	private ImageIcon countingButton;
	private ImageIcon quickClickButton;
	private JButton quickclick;
	private JButton counting;
	private JPanel content;
	private JLabel background;
	private JTextField subjectNum;
	private JTextField age;
	private JCheckBox genderMale;
	private JCheckBox genderFemale;
	private JLabel ageLabel;
	private JLabel subjectNumLabel;
	private CountingActionListener cal;
	private QuickClickActionListener qcal;
	public final DashBoard db = this;
	private int subjectAge;
	public int getSubjectAge() {
		return subjectAge;
	}

	public void setSubjectAge(int subjectAge) {
		this.subjectAge = subjectAge;
	}

	public int getSubjectNumber() {
		return subjectNumber;
	}

	public void setSubjectNumber(int subjectNumber) {
		this.subjectNumber = subjectNumber;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	private int subjectNumber;
	private int gender;
	private Boolean countingIsRunning; 
	private Boolean quickclickIsRunning;
	
	public void run()
	{
		dash = new JFrame("Najarian Laboratories: Brain Games");
		panel = new JPanel(new BorderLayout());
		logo = new ImageIcon("Dashboard.jpg");
		countingButton = new ImageIcon("countingbutton.jpg");
		quickClickButton = new ImageIcon("quickclickbutton.jpg");
		quickclick = new JButton(quickClickButton);
		counting = new JButton(countingButton);
		content = new JPanel();
		background = new JLabel(logo);
		subjectNum = new JTextField();
		age = new JTextField();
		genderMale = new JCheckBox("Male");
		genderFemale = new JCheckBox("Female");
		ageLabel = new JLabel("Age:");
		subjectNumLabel = new JLabel("Subject #:");
		cal = new CountingActionListener();
		qcal = new QuickClickActionListener();
		countingIsRunning = new Boolean(false);
		quickclickIsRunning = new Boolean(false);
		dash.getContentPane().setBackground(Color.WHITE);
		dash.setBackground(Color.WHITE);
		quickclick.setBorder(BorderFactory.createEmptyBorder());
		quickclick.setBackground(Color.WHITE);
		quickclick.setPreferredSize(new Dimension(300,120));
		quickclick.addActionListener(new QuickClickActionListener());
		counting.setBorderPainted(false);
		counting.setBackground(Color.WHITE);
		counting.setPreferredSize(new Dimension(300,120));
		counting.addActionListener(new CountingActionListener());
		dash.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dash.setSize(1024, 768);
		background.setPreferredSize(new Dimension(1024, 600));
		background.setBackground(Color.WHITE);
		dash.add(background, BorderLayout.NORTH);
		dash.add(panel, BorderLayout.SOUTH);
		panel.add(content, BorderLayout.CENTER);
		panel.add(quickclick, BorderLayout.WEST);
		panel.add(counting, BorderLayout.EAST);
		subjectNum.setPreferredSize(new Dimension(50,25));
		subjectNum.setLocation(700, 710);
		age.setPreferredSize(new Dimension(50,25));
		content.add(ageLabel, BorderLayout.AFTER_LAST_LINE);
		content.add(age, BorderLayout.AFTER_LAST_LINE);
		content.add(subjectNumLabel, BorderLayout.AFTER_LAST_LINE);
		content.add(subjectNum, BorderLayout.AFTER_LAST_LINE);
		content.add(genderMale, BorderLayout.AFTER_LAST_LINE);
		content.add(genderFemale, BorderLayout.AFTER_LAST_LINE);
		dash.setResizable(false);
		dash.setVisible(true);
	}
	
	abstract class DashboardActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent ae) 
		{
			boolean errorsExist = false;
			
			try
			{
				subjectAge = Integer.parseInt(age.getText());
				if (subjectAge <= 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e)
			{
				errorsExist = true;
				JOptionPane.showMessageDialog(dash, "Enter your age as a non-negative integer please.");
			}
			try
			{
				subjectNumber = Integer.parseInt(subjectNum.getText());
				if (subjectNumber < 0)
					throw new NumberFormatException();
			} catch (NumberFormatException e)
			{
				errorsExist = true;
				JOptionPane.showMessageDialog(dash, "Enter your subject # as a non-negative integer please.");
			}
			if ((genderMale.isSelected() && genderFemale.isSelected()) || (!genderMale.isSelected() && !genderFemale.isSelected()))
			{
				errorsExist = true;
				JOptionPane.showMessageDialog(dash, "Please select either Male or Female");
			}
			else
			{
				if (genderMale.isSelected())
				{
					gender = 0;
				}
				if (genderFemale.isSelected())
				{
					gender = 1;
				}
			}
			if (!errorsExist)
				startGame();
			
		}
		public void startGame()
		{
			
		}
	}
	
	class CountingActionListener extends DashboardActionListener
	{
		//The scariest code I have ever written.
		@Override
		public void startGame()
		{
			if (!countingIsRunning)
			{
				//Collect information from the dashboard and start the game.
				countingIsRunning = true;
				
				 final SwingWorker worker = new SwingWorker() {
			            public Object construct() {
			            
							try {
								return new CountingGfx(db);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return counting;
			            }
			        };
			        worker.start();
			}
			else JOptionPane.showMessageDialog(dash, "An instance of Counting is already running!");
		}
		
	}
	
	class QuickClickActionListener extends DashboardActionListener
	{
		@Override
		public void startGame()
		{
			if (!quickclickIsRunning)
			{
				quickclickIsRunning = true;
				QuickClickGfx quickClickGfx;
				try {
					quickClickGfx = new QuickClickGfx(db);
				} catch (Exception e) {}
			}
			else JOptionPane.showMessageDialog(dash, "An instance of Quick Click is already running!");
		}
		
	}
	
	//getters and setters
	public boolean isCountingIsRunning()
	{
		return countingIsRunning;
	}

	public void setCountingIsRunning(boolean countingIsRunning)
	{
		this.countingIsRunning = countingIsRunning;
	}

	public boolean isQuickclickIsRunning()
	{
		return quickclickIsRunning;
	}

	public void setQuickclickIsRunning(boolean quickclickIsRunning)
	{
		this.quickclickIsRunning = quickclickIsRunning;
	}
	
	// main method
	public static void main(String[] args)
	{
		try {
			SwingUtilities.invokeAndWait(new DashBoard());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
