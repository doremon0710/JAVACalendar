/**Minh Ngo 
 * Cs151 Assignment 4 
 * This class is to represent Calendar using Gregorian calendar 
 * This is also the model 
 */
/**
 * Version 1.1 
 * @author minhngo
 *
 */


import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
//-------------------------------------
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Calendar {
	private GregorianCalendar calendar;
	private ArrayList<ChangeListener> listeners; //ArrayList of listeners 
	private ArrayList<CalendarEvent> eventList; //data structure holding CalendarEvent 
	private int currDate;
	private int currMonth;
	private int currYear;
	private String archiveFile;// for the file that we may need to save


	/**constructor 
	 * set up instant variables
	 * initialize current month and date  
	 */
	public Calendar() {
		calendar = new GregorianCalendar();
		listeners = new ArrayList<ChangeListener>();
		eventList = new ArrayList<CalendarEvent>();
		currDate = calendar.get(java.util.Calendar.DAY_OF_MONTH);
		currMonth = calendar.get(java.util.Calendar.MONTH);
		currYear = calendar.get(java.util.Calendar.YEAR);
		archiveFile = "events.txt";
	}
	/**Attach the view/listener to the model  
	 * @param aListener add to the ArrayList of listeners 
	 */
	public void attachView(ChangeListener aListener) {
		listeners.add(aListener);
	}
	
	/**set the date with the given date 
	 * mutator 
	 * has to notify listeners 
	 * @param aDate
	 */
	public void setDate(String aDate) {
		if(aDate != null) 
		{
			calendar.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(aDate));
			for (ChangeListener l : listeners) //notify 
		{
				l.stateChanged(new ChangeEvent(this));
		}
		}
	}
	/**Update the time and date based on the passed in value 
	 * performed by a button in the GUI 
	 * @param dayChange
	 * @param monthChange
	 */
	public void buttonUpdate(int dayChange, int monthChange) {
		//calculate the newly "created" month and date change 
		calendar.add(java.util.Calendar.DAY_OF_MONTH, dayChange);
		calendar.add(java.util.Calendar.MONTH, monthChange);
		//notify 
		for (ChangeListener l : listeners) 
		{
			l.stateChanged(new ChangeEvent(this));
		}
	}
	/**change the value of month and date based on mouse clicking 
	 * basically the same as above 
	 * @param dayChange
	 * @param monthChange
	 */
	public void clickUpdate(int dayChange, int monthChange) {
		calendar.set(java.util.Calendar.DAY_OF_MONTH, dayChange);
		calendar.set(java.util.Calendar.DAY_OF_MONTH, monthChange);
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**check whether or not we can find that event 
	 * @param date
	 * @return if found --> return true
	 */
	public boolean hasEvent(String date) {
		for(int i = 0; i < eventList.size(); i++) {
			if(eventList.get(i).getDateStr().equalsIgnoreCase(date)) {
				return true;
			}
		}
		return false;
	}
	
	public int firstDayOfWeek() {
		int original = calendar.get(java.util.Calendar.DAY_OF_MONTH);
		calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
		int firstDayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);
		calendar.set(java.util.Calendar.DAY_OF_MONTH, original);
		return firstDayOfWeek;
	}
	
	public int lastDayOfMonth() 
	{
		return calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
	}
	
	public int currentDate() 
	{
		return this.currDate;
	}
	
	public int currentMonth() 
	{
		return this.currMonth;
	}
	
	public int currentYear() 
	{
		return this.currYear;
	}
	
	public int actualDayOfWeek() 
	{
		return calendar.get(java.util.Calendar.DAY_OF_WEEK);
	}
	public int actualDate() 
	{
		return calendar.get(java.util.Calendar.DAY_OF_MONTH);
	}
	
	public int actualMonth() 
	{
		return calendar.get(java.util.Calendar.MONTH);
	}
	
	public int actualYear() 
	{
		return calendar.get(java.util.Calendar.YEAR);
	}
	
	public String getEventList() {
		StringBuffer objStr = new StringBuffer();
		StringBuffer dateStr = new StringBuffer();
		dateStr.append((actualMonth() + 1) + "/");
		dateStr.append(actualDate() + "/");
		dateStr.append(actualYear());
		String finalDateStr = dateStr.toString();
		for(int i = 0; i < eventList.size(); i++) {
			if(eventList.get(i).getDateStr().equalsIgnoreCase(finalDateStr))
				objStr.append(eventList.get(i).toString() + '\n');
		}
		
		return objStr.toString();
	}
	/**Using serialization to save events to events.txt
	 * @throws IOException
	 */
	public void saveEvents() throws IOException {
		ObjectOutputStream writeSer = new ObjectOutputStream(new FileOutputStream(archiveFile));
		writeSer.writeInt(eventList.size());// write how many events we might have 
		for(int i = 0; i < eventList.size(); i++)
			writeSer.writeObject(eventList.get(i));// write each of the event objects to the events.txt 
		writeSer.close();
	}
	/**Load the event back using serialization
	 * Load from the events.txt 
	 * @throws IOException
	 * @throws ParseException
	 * @throws ClassNotFoundException 
	 */
	public void loadEvents() throws IOException, ParseException, ClassNotFoundException {
		File input = new File(archiveFile);
		if(input.exists()) //if the file exists 
		{
			ObjectInputStream readSer = new ObjectInputStream(new FileInputStream(input));
			int size = readSer.readInt();
			while(size > 0) // if events exist --> size >0 
			{
				eventList.add((CalendarEvent)readSer.readObject());//read them back 
				size--;
			}
			readSer.close();
		}
	}
	/**Mutator method to modify the calendar--> add event into the calendar 
	 * 
	 * @param title name of the event 
	 * @param date date of the event 
	 * @param starting starting time 
	 * @param ending ending time 
	 * @return
	 * @throws ParseException
	 */
	public String addEvent(String title, String date, String starting, String ending) throws ParseException {
		StringBuffer timeConvert = new StringBuffer();
		if(starting.charAt(5) == 'p') {
			for(int i = 0; i < 7; i++)
				timeConvert.append(starting.charAt(i));
			timeConvert.setCharAt(0, (char)(starting.charAt(0) + 1));
			timeConvert.setCharAt(1, (char)(starting.charAt(1) + 2));
			starting = timeConvert.toString();
		}
		timeConvert = new StringBuffer();
		if(ending.charAt(5) == 'p') {
			for(int i = 0; i < 7; i++)
				timeConvert.append(ending.charAt(i));
			timeConvert.setCharAt(0, (char)(ending.charAt(0) + 1));
			timeConvert.setCharAt(1, (char)(ending.charAt(1) + 2));
			ending = timeConvert.toString();
		}
		if(starting.compareToIgnoreCase(ending) > 0)
			return "Starting time: " + starting + " > Ending time: " + ending;
		for(int i = 0; i < eventList.size(); i++) {
			if(eventList.get(i).getDateStr().compareToIgnoreCase(date) == 0)
				if(eventList.get(i).getEnding().compareToIgnoreCase(starting) > 0 && eventList.get(i).getStarting().compareToIgnoreCase(ending) < 0)
					return "Time conflict " + eventList.get(i).toString();
		}
		this.eventList.add(new CalendarEvent(title, date, starting, ending));
		eventList.sort(new Comparator<CalendarEvent>() {
			public int compare(CalendarEvent a, CalendarEvent b) {
				if(a.getDate().compareTo(b.getDate()) == 0) {
					return a.getStarting().compareToIgnoreCase(b.getStarting());
				}
				return a.getDate().compareTo(b.getDate());
			}
		});
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
		return null;
	}
	/**delete selected event 
	 * @param aDate
	 * @param starting
	 */
	public void deleteSelectEve(String aDate, String starting) {
		for(int i = 0; i < eventList.size(); i++) {
			if(eventList.get(i).getDateStr().equalsIgnoreCase(aDate) && eventList.get(i).getStarting().equalsIgnoreCase(starting)) {
				eventList.remove(i);
				for (ChangeListener l : listeners) {
					l.stateChanged(new ChangeEvent(this));
				}
			}
		}
	}
	/**delete specific event in the Array list 
	 * 
	 * @param date
	 */
	public void deleteEvent(String date)
	{
		for(int i = 0; i <eventList.size(); i++) 
		{
			if(eventList.get(i).getDateStr().equalsIgnoreCase(date)) 
			{
				eventList.remove(i);
				}
		}
	}
}