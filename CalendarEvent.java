/** Minh Ngo
 * CS151 last assignment 
 * CalendarEvent class --> represent event in a calendar
 * 
 */

import java.text.SimpleDateFormat;
import java.util.Date;
//------------------------------
import java.text.ParseException;
import java.io.Serializable;

public class CalendarEvent implements Serializable {
	/**Represent the event that we add to the calendar 
	 * title: name of the event
	 * date: date of the event
	 * starting, ending: event time frame 
	 */
	private static final long serialVersionUID = 1L;
	String title;
	Date date;
	String starting;
	String ending;
	//---------------------------------------------------------------------------------------------	
	/**Constructor
	 * @param title name of the event 
	 * @param date date of the event 
	 * @param s starting time 
	 * @param e ending time 
	 * @throws ParseException
	 */
	public CalendarEvent(String title, String date, String s, String e) throws ParseException {
		this.title = title;
		SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
		this.date = format.parse(date);
		this.starting = s;
		this.ending = e;
	}
	/**getter 
	 * 
	 * @return title of the event 
	 */
	public String getTitle() {
		return title;
	}
	/**setter 
	 * 
	 * @param title set the title/ name of the event 
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**getter 
	 * 
	 * @return date of the event 
	 */

	public Date getDate() {
		return date;
	}
	
	/** getter 
	 * 
	 * @return the string of date/ formatted in the desired format 
	 */
	
	public String getDateStr() {
		SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
		return format.format(date);
	}
	/**setter 
	 * 
	 * @param date set date 
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public String getStarting() {
		return starting;
	}

	public void setStarting(String starting) {
		this.starting = starting;
	}
	
	/**getter 
	 * 
	 * @return the ending time of the event 
	 */
	public String getEnding() {
		return ending;
	}
	
	/**setter 
	 * 
	 * @param ending end time of the event 
	 */

	public void setEnding(String ending) {
		this.ending = ending;
	}
	
	/**default constructor 
	 * 
	 */
	public CalendarEvent() {
		title = null;
		date = null;
		starting = null;
		ending = null;
	}
	
	/**Print out the content of the event using desired format 
	 * Use stringBuffer for it 
	 */
	
	public String toString() {
		StringBuffer objStr = new StringBuffer();
		StringBuffer startingConvert = new StringBuffer();
		StringBuffer endingConvert = new StringBuffer();
		for(int i = 0; i < 7; i++) {
			startingConvert.append(starting.charAt(i));
			endingConvert.append(ending.charAt(i));
		}
		if(starting.charAt(5) == 'p') {
			startingConvert.setCharAt(0, (char)(starting.charAt(0) - 1));
			startingConvert.setCharAt(1, (char)(starting.charAt(1) - 2));
		}
		if(ending.charAt(5) == 'p') {
			endingConvert.setCharAt(0, (char)(ending.charAt(0) - 1));
			endingConvert.setCharAt(1, (char)(ending.charAt(1) - 2));
		}
		objStr.append(startingConvert.toString() + " - " + endingConvert.toString() + "  " + title);
		
		return objStr.toString();
	}
}