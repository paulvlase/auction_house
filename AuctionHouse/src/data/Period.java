package data;

import java.util.Date;

public class Period {
	private Date	start;
	private Date	stop;

	private Integer	days;
	private Integer	hours;
	private Integer minutes;
	private Integer	seconds;
	
	private static final int SECOND_MILISECONDS = 1000;
	private static final int MINUTE_MILISECONDS = 60 * SECOND_MILISECONDS;
	private static final int HOUR_MILISECONDS = 60 * MINUTE_MILISECONDS;
	private static final int DAY_MILISECONDS = 24 * HOUR_MILISECONDS;

	public Period(Date start, Date stop) {
		this.start = start;
		this.stop = stop;
		computeDifference();
	}

	private void computeDifference() {
		Long diff = stop.getTime() - start.getTime();
		
		days = (int) (diff / DAY_MILISECONDS);
		diff %= DAY_MILISECONDS;
		
		hours = (int) (diff / HOUR_MILISECONDS);
		diff %= HOUR_MILISECONDS;
		
		minutes = (int) (diff / MINUTE_MILISECONDS);
		diff %= MINUTE_MILISECONDS;
		
		seconds = (int) (diff / SECOND_MILISECONDS);
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
		computeDifference();
	}

	public Date getStop() {
		return stop;
	}

	public void setStop(Date stop) {
		this.stop = stop;
		computeDifference();
	}

	@Override
	public String toString() {
		return days + "d " + hours + "h:" + minutes + "m:" + seconds + "s";
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("deprecation")
		Period period = new Period(new Date(2013, 3, 3, 3, 0), new Date(2013, 3, 4, 4, 0));
		System.out.println(period);
	}
}
