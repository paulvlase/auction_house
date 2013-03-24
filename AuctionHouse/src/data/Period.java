package data;

import java.util.Calendar;
import java.util.Date;

public class Period {
	private Long				start;
	private Long				stop;

	private Integer				days;
	private Integer				hours;
	private Integer				minutes;
	private Integer				seconds;

	private static final int	SECOND_MILISECONDS	= 1000;
	private static final int	MINUTE_MILISECONDS	= 60 * SECOND_MILISECONDS;
	private static final int	HOUR_MILISECONDS	= 60 * MINUTE_MILISECONDS;
	private static final int	DAY_MILISECONDS		= 24 * HOUR_MILISECONDS;

	public Period(Date start, Date stop) {
		this.start = start.getTime();
		this.stop = stop.getTime();
		computeDifference();
	}

	public Period(Long start, Long stop) {
		this.start = start;
		this.stop = stop;
		computeDifference();
	}

	private void computeDifference() {
		Long diff = stop - start;

		days = (int) (diff / DAY_MILISECONDS);
		diff %= DAY_MILISECONDS;

		hours = (int) (diff / HOUR_MILISECONDS);
		diff %= HOUR_MILISECONDS;

		minutes = (int) (diff / MINUTE_MILISECONDS);
		diff %= MINUTE_MILISECONDS;

		seconds = (int) (diff / SECOND_MILISECONDS);
	}

	public Date getStart() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(start);
		return cal.getTime();
	}

	public void setStart(Date start) {
		this.start = start.getTime();
		computeDifference();
	}

	public Date getStop() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(stop);
		return cal.getTime();
	}

	public void setStop(Date stop) {
		this.stop = stop.getTime();
		computeDifference();
	}

	@Override
	public String toString() {
		return days + "d " + hours + "h:" + minutes + "m:" + seconds + "s";
	}
}
