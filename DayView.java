import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DayView extends JPanel implements ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int frameWidth;
	private int frameHeight;
	private Calendar modelCalendar;
	private JTextField dateField;
	private JTextArea eventListArea;
	private DAYS [] day;
	private MONTHS [] month;
	
	public DayView(int frameWidth, int frameHeight, Calendar modelCalendar) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		this.modelCalendar = modelCalendar;
		this.day = DAYS.values();
		this.month = MONTHS.values();
		dateField = new JTextField();
		eventListArea = new JTextArea();
		drawPanel();
	}
	
	public void drawPanel() {
		JPanel viewPanel = new JPanel();
		viewPanel.setPreferredSize(new Dimension(frameWidth / 2, frameHeight));
		viewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		viewPanel.setLayout(new BorderLayout());
		dateField.setEditable(false);
		dateField.setHorizontalAlignment(JTextField.CENTER);
		eventListArea.setEditable(false);
		JScrollPane eventListScroll = new JScrollPane(eventListArea);
		viewPanel.add(dateField, BorderLayout.NORTH);
		viewPanel.add(eventListScroll, BorderLayout.CENTER);
		populate();
		
		this.add(viewPanel);
	}

	private void populate() {
		dateField.setText(day[modelCalendar.actualDayOfWeek() - 1].toString() + ", " + month[modelCalendar.actualMonth()].toString() + ' ' + modelCalendar.actualDate() + ", " + modelCalendar.actualYear());
		eventListArea.setText(modelCalendar.getEventList());
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		populate();
	}
}