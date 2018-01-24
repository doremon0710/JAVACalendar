import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**Monthview is a JPanel GUI 
 * 
 * @author minhngo
 *
 */
public class MonthView extends JPanel implements ChangeListener {
	/** Class MonthView to draw the GUI part of the month calendar 
	 * 
	 */
	
	
	public MonthView(JFrame closeFrame, int frameWidth, int frameHeight, int offSet, Calendar modelCalendar) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.offSet = offSet;
		this.modelCalendar = modelCalendar;
		this.presentDate = modelCalendar.actualDate();
		this.presentMonth = modelCalendar.actualMonth();
		this.presentYear = modelCalendar.actualYear();
		this.currDatePos = modelCalendar.firstDayOfWeek() + presentDate + 5;
		this.closeFrame = closeFrame;
		this.dateStr = new JTextField();
		this.calBut = new JButton[49];
		this.month = MONTHS.values();
		draw();
	}
	
	/**
	 * the draw method that handle all of the drawing 
	 */
	private void draw() {
		String [] weekDay = {"Su", "M", "T", "W", "Th", "F", "Sa"};
		
		JPanel calendarPanel = new JPanel();
		calendarPanel.setPreferredSize(new Dimension(frameWidth / 2, frameHeight));
		calendarPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray,3));
		this.add(calendarPanel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension((int)calendarPanel.getPreferredSize().getWidth() - offSet, 
				(int)calendarPanel.getPreferredSize().getHeight() / 4));
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));
		calendarPanel.add(buttonPanel);
		
		JPanel calendarPaneContent = new JPanel();
		calendarPaneContent.setPreferredSize(new Dimension((int)calendarPanel.getPreferredSize().getWidth() * 5 / 6, (int)calendarPanel.getPreferredSize().getHeight() * 3 / 4 - offSet));
		calendarPaneContent.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		calendarPanel.add(calendarPaneContent);
		
		JPanel upDownPanel = new JPanel();
		upDownPanel.setPreferredSize(new Dimension((int)calendarPanel.getPreferredSize().getWidth() / 6 - offSet, (int)calendarPanel.getPreferredSize().getHeight() * 3 / 4 - offSet));
		upDownPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		calendarPanel.add(upDownPanel);
		// Create add/ "create" button to add event
		// attach listener to it 
		JButton createBut = new JButton("Create");
		createBut.setForeground(Color.white);
		createBut.setBackground(Color.RED);
		createBut.setOpaque(true);
		createBut.setBorderPainted(false);
		createBut.setPreferredSize(new Dimension((int)buttonPanel.getPreferredSize().getWidth() / 4 - offSet, (int)buttonPanel.getPreferredSize().getHeight() / 2));
		createBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame createFrame = new JFrame("Create an event");
				createFrame.setSize(new Dimension(750, 150));
				createFrame.setLayout(new FlowLayout());
				JTextField eventTitle = new JTextField();
				eventTitle.setPreferredSize(new Dimension(700, 50));
				createFrame.add(eventTitle);
				JTextField dateField = new JTextField();
				dateField.setPreferredSize(new Dimension(100, 50));
				dateField.setEditable(false);
				dateField.setHorizontalAlignment(JTextField.CENTER);
				dateField.setText((modelCalendar.actualMonth() + 1) + "/" + modelCalendar.actualDate() + "/" + modelCalendar.actualYear());
				createFrame.add(dateField);
				JTextField startField = new JTextField();
				startField.setPreferredSize(new Dimension(150, 50));
				startField.setHorizontalAlignment(JTextField.CENTER);
				startField.setText("--:-- am/pm");
				createFrame.add(startField);
				JTextField toField = new JTextField();
				toField.setPreferredSize(new Dimension(40, 40));
				toField.setHorizontalAlignment(JTextField.CENTER);
				toField.setBorder(BorderFactory.createEmptyBorder());
				toField.setEditable(false);
				toField.setText("to");
				createFrame.add(toField);
				JTextField endField = new JTextField();
				endField.setPreferredSize(new Dimension(150, 50));
				endField.setHorizontalAlignment(JTextField.CENTER);
				endField.setText("--:-- am/pm");
				createFrame.add(endField);
				JButton saveButton = new JButton("Save");
				saveButton.setForeground(Color.white);
				saveButton.setPreferredSize(new Dimension(150, 40));
				saveButton.setBackground(Color.RED);
				saveButton.setOpaque(true);
				saveButton.setBorderPainted(false);
				JFrame errorFrame = new JFrame("Time conflict");
				JTextField errorField = new JTextField();
				errorField.setPreferredSize(new Dimension(500, 50));
				errorField.setEditable(false);
				errorFrame.add(errorField);
				errorFrame.pack();
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							String error;
							if((error = modelCalendar.addEvent(eventTitle.getText(), dateField.getText(), startField.getText(), endField.getText())) != null) {
								errorField.setText(error);
								errorFrame.setVisible(true);
							}
							else {
								createFrame.dispose();
								errorFrame.dispose();
							}
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				});
				createFrame.add(saveButton);
				createFrame.setVisible(true);
			}	
		});
		buttonPanel.add(createBut);
		
		JButton delBut = new JButton("Delete");
		delBut.setPreferredSize(new Dimension((int)buttonPanel.getPreferredSize().getWidth() / 4 - offSet, (int)buttonPanel.getPreferredSize().getHeight() / 2));
		delBut.setForeground(Color.white);
		delBut.setBackground(Color.BLACK);
		delBut.setOpaque(true);
		delBut.setBorderPainted(false);
		buttonPanel.add(delBut);
		/**
		 * This part is to create a dialog panel for the input --> deleteButton
		 * so that we can get the input and remove it with enough information 
		 */
		delBut.addActionListener(new ActionListener()
				{
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame delFrame = new JFrame("Delete an event");
				delFrame.setSize(new Dimension(750, 150));
				delFrame.setLayout(new FlowLayout());
				JTextField eventTitle = new JTextField();
				eventTitle.setPreferredSize(new Dimension(700, 50));
				delFrame.add(eventTitle);
				JTextField dateField = new JTextField();
				dateField.setPreferredSize(new Dimension(100, 50));
				dateField.setEditable(false);
				dateField.setHorizontalAlignment(JTextField.CENTER);
				dateField.setText((modelCalendar.actualMonth() + 1) + "/" + modelCalendar.actualDate() + "/" + modelCalendar.actualYear());
				delFrame.add(dateField);
				JTextField startField = new JTextField();
				startField.setPreferredSize(new Dimension(150, 50));
				startField.setHorizontalAlignment(JTextField.CENTER);
				startField.setText("--:-- am/pm");
				delFrame.add(startField);
				JTextField toField = new JTextField();
				toField.setPreferredSize(new Dimension(40, 40));
				toField.setHorizontalAlignment(JTextField.CENTER);
				toField.setBorder(BorderFactory.createEmptyBorder());
				toField.setEditable(false);
				toField.setText("to");
				delFrame.add(toField);
				JTextField endField = new JTextField();
				endField.setPreferredSize(new Dimension(150, 50));
				endField.setHorizontalAlignment(JTextField.CENTER);
				endField.setText("--:-- am/pm");
				delFrame.add(endField);
				JButton dButton = new JButton("Delete");
				dButton.setForeground(Color.white);
				dButton.setPreferredSize(new Dimension(150, 40));
				dButton.setBackground(Color.BLACK);
			    dButton.setOpaque(true);
				dButton.setBorderPainted(false);
			
				
				dButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						modelCalendar.deleteSelectEve(dateField.getText(), startField.getText());
						delFrame.dispose();
								
					}
				});
				delFrame.add(dButton);
				delFrame.setVisible(true);
			}	
					
				});
		
		
		JButton quitBut = new JButton("Quit");
		quitBut.setPreferredSize(new Dimension((int)buttonPanel.getPreferredSize().getWidth() / 4 - offSet, (int)buttonPanel.getPreferredSize().getHeight() / 2));
		quitBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					modelCalendar.saveEvents();
					closeFrame.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		});
		quitBut.setPreferredSize(new Dimension(150,75));
		quitBut.setBackground(Color.GREEN);
		quitBut.setForeground(Color.white);
	    quitBut.setOpaque(true);
		quitBut.setBorderPainted(false);
	
		
		
		buttonPanel.add(quitBut);
		JButton prevBut = new JButton("<Previous");
		prevBut.setPreferredSize(new Dimension((int)buttonPanel.getPreferredSize().getWidth() / 2 - offSet, (int)buttonPanel.getPreferredSize().getHeight() / 2 - offSet));
		prevBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(modelCalendar.hasEvent((modelCalendar.actualMonth() + 1) + "/" + modelCalendar.actualDate() + "/" + modelCalendar.actualYear()))
					calBut[modelCalendar.firstDayOfWeek() + 5 + presentDate].setBackground(Color.CYAN);
				else
					calBut[modelCalendar.firstDayOfWeek() + 5 + presentDate].setBackground(null);
				modelCalendar.buttonUpdate(-1, 0);
			}	
		});
		buttonPanel.add(prevBut);
		
		JButton nextBut = new JButton("Next>");
		nextBut.setPreferredSize(new Dimension((int)buttonPanel.getPreferredSize().getWidth() / 2 - offSet, (int)buttonPanel.getPreferredSize().getHeight() / 2 - offSet));
		nextBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(modelCalendar.hasEvent((modelCalendar.actualMonth() + 1) + "/" + modelCalendar.actualDate() + "/" + modelCalendar.actualYear()))
					calBut[modelCalendar.firstDayOfWeek() + 5 + presentDate].setBackground(Color.CYAN);
				else
					calBut[modelCalendar.firstDayOfWeek() + 5 + presentDate].setBackground(null);
				modelCalendar.buttonUpdate(1, 0);
			}	
		});
		buttonPanel.add(nextBut);
		// upButton to move up the calendar monthview 
		JButton upBut = new JButton("Up");
		upBut.setPreferredSize(new Dimension((int)upDownPanel.getPreferredSize().getWidth() - offSet / 2, (int)upDownPanel.getPreferredSize().getHeight() / 2 - offSet));
		upBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calBut[modelCalendar.firstDayOfWeek() + 5 + presentDate].setBackground(null);
				modelCalendar.buttonUpdate(0, -1);
			}	
		});
		upDownPanel.add(upBut);
		
		JButton downBut = new JButton("Dn");
		downBut.setPreferredSize(new Dimension((int)upDownPanel.getPreferredSize().getWidth() - offSet / 2, (int)upDownPanel.getPreferredSize().getHeight() / 2 - offSet));
		downBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calBut[modelCalendar.firstDayOfWeek() + 5 + presentDate].setBackground(null);
				modelCalendar.buttonUpdate(0, 1);
			}	
		});
		upDownPanel.add(downBut);
		
		dateStr.setPreferredSize(new Dimension((int)calendarPaneContent.getPreferredSize().getWidth() - offSet / 3, (int)calendarPaneContent.getPreferredSize().getHeight() / 8 - offSet / 3));
		dateStr.setEditable(false);
		dateStr.setHorizontalAlignment(JTextField.CENTER);
		calendarPaneContent.add(dateStr);
		
		final int CAL_BUT_WIDTH = (int)calendarPaneContent.getPreferredSize().getWidth() / 7 - offSet / 3;
		final int CAL_BUT_HEIGHT = (int)calendarPaneContent.getPreferredSize().getHeight() / 8 - offSet / 3;

		ActionListener onClick = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(modelCalendar.hasEvent((modelCalendar.actualMonth() + 1) + "/" + modelCalendar.actualDate() + "/" + modelCalendar.actualYear()))
					calBut[modelCalendar.firstDayOfWeek() + 5 + presentDate].setBackground(Color.CYAN);
				else
					calBut[modelCalendar.firstDayOfWeek() + 5 + presentDate].setBackground(null);
				JButton clickedButton = (JButton)(e.getSource());
				modelCalendar.setDate(clickedButton.getText());
			}
		};
		
		for(int i = 0; i < calBut.length; i++) {
			if (i < 7)
				calBut[i] = new JButton(weekDay[i]);
			else {
				calBut[i] = new JButton();
				calBut[i].addActionListener(onClick);
			}
			calBut[i].setPreferredSize(new Dimension(CAL_BUT_WIDTH, CAL_BUT_HEIGHT));
			calendarPaneContent.add(calBut[i]);
		}
		
		populateCal();
		calBut[currDatePos].setBorder(BorderFactory.createTitledBorder("C"));
		calBut[currDatePos].setBackground(Color.ORANGE);
	}
	
	private void populateCal() {
		int firstDayOfWeek = modelCalendar.firstDayOfWeek() + 6;
		int lastDayOfMonth = modelCalendar.lastDayOfMonth();
		
		dateStr.setText(month[modelCalendar.actualMonth()].toString() + ' ' + modelCalendar.actualYear());
		for(int i = 7; i < firstDayOfWeek; i++)
			calBut[i].setText(null);
		for(int i = firstDayOfWeek + lastDayOfMonth; i < calBut.length; i++)
			calBut[i].setText(null);
		for(int i = 0; i < lastDayOfMonth; i++) {
			if(presentMonth == modelCalendar.currentMonth() && presentYear == modelCalendar.currentYear())
				calBut[currDatePos].setBorder(BorderFactory.createTitledBorder("C"));
			if(modelCalendar.hasEvent((modelCalendar.actualMonth() + 1) + "/" + Integer.toString(i + 1) + "/" + modelCalendar.actualYear()))
				calBut[modelCalendar.firstDayOfWeek() + 5 + i + 1].setBackground(Color.CYAN);
			else
				calBut[modelCalendar.firstDayOfWeek() + 5 + i + 1].setBackground(null);
			calBut[firstDayOfWeek].setText(Integer.toString(i + 1));
			firstDayOfWeek++;
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(presentMonth != modelCalendar.actualMonth()) {
			presentMonth = modelCalendar.actualMonth();
			presentYear = modelCalendar.actualYear();
			calBut[currDatePos].setBorder(UIManager.getBorder("Button.border"));
			populateCal();
		}
		presentDate = modelCalendar.actualDate();
		calBut[modelCalendar.firstDayOfWeek() + 5 + presentDate].setBackground(Color.ORANGE);
	}
	private static final long serialVersionUID = 1L;
	// variables for frameWidth, Height,offSet and calendar stuffs 
	private int frameWidth;
	private int frameHeight;
	private int offSet;
	private int presentDate;
	private int presentMonth;
	private int presentYear;
	private int currDatePos;
	// reference to the model is here 
	private Calendar modelCalendar;
	private JFrame closeFrame;
	private JButton [] calBut; //array of JButton for calendar/date 
	private JTextField dateStr;
	private MONTHS [] month;
}