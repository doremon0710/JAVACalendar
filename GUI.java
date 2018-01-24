import java.io.IOException;
import java.text.ParseException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

enum MONTHS
{
	January, February, March, April, May, June, July, August, September, October, November, December;
}

enum DAYS
{
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
}

public class GUI {
	private final int FRAME_WIDTH = 1000;
	private final int FRAME_HEIGHT = 600;
	private final int OFFSET = 20;
	
	public GUI() throws ClassNotFoundException, IOException, ParseException 
	{
		JFrame calendarFrame = new JFrame("Calendar Application");
		Calendar modelCalendar = new Calendar();
		modelCalendar.loadEvents();
		MonthView monthView = new MonthView(calendarFrame, FRAME_WIDTH, FRAME_HEIGHT, OFFSET, modelCalendar);
		DayView dayView = new DayView(FRAME_WIDTH, FRAME_HEIGHT, modelCalendar);
		
		modelCalendar.attachView(monthView);
		modelCalendar.attachView(dayView);
				
		calendarFrame.setLayout(new BoxLayout(calendarFrame.getContentPane(),BoxLayout.X_AXIS));
		calendarFrame.add(dayView);
		calendarFrame.add(monthView);
		
		calendarFrame.pack();
		calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		calendarFrame.setVisible(true);
	}
}