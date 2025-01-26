package de.forestj.lib;

/**
 * 
 * Abstract timer task class inheriting from java.util.TimerTask.
 * Create an instance which holds the developer specified run method, interval and start time.
 * @see java.util.TimerTask
 * 
 */
public abstract class TimerTask extends java.util.TimerTask {
	
	/* Fields */
	
	private java.util.List<java.time.DayOfWeek> a_excludeWeekdays;
	private java.time.LocalTime o_startTime;
	private de.forestj.lib.DateInterval o_interval;
	private boolean b_showException;
	
	/* Properties */
	
	public java.time.LocalTime getStartTime() {
		return this.o_startTime;
	}
	
	public de.forestj.lib.DateInterval getInterval() {
		return this.o_interval;
	}
	
	public boolean getShowException() {
		return this.b_showException;
	}
	
	public void setShowException(boolean p_b_value) {
		this.b_showException = p_b_value;
	}
	
	/* Methods */
	
	/**
	 * Create a timer task instance with interval value
	 * 
	 * @param p_o_interval		interval when at the end the timer task will be always executed and interval will start new
	 */
	public TimerTask(de.forestj.lib.DateInterval p_o_interval) {
		this(p_o_interval, null);
	}
	
	/**
	 * Create a timer task instance with interval value and a start time
	 * 
	 * @param p_o_interval		interval when at the end the timer task will be always executed and interval will start new
	 * @param p_o_startTime		start time when the timer task will execute for the first time
	 * @see de.forestj.lib.DateInterval
	 * @see java.time.LocalTime
	 */
	public TimerTask(de.forestj.lib.DateInterval p_o_interval, java.time.LocalTime p_o_startTime) {
		this.a_excludeWeekdays = new java.util.ArrayList<java.time.DayOfWeek>();
		this.o_interval = p_o_interval;
		this.o_startTime = p_o_startTime;
		this.b_showException = false;
		
												de.forestj.lib.Global.ilogConfig("Created TimerTask with interval '" + p_o_interval + "' and start time '" + p_o_startTime + "'");
	}
	
	/**
	 * Delete all exclude weekdays settings
	 */
	public void clearExcludeWeekdays() {
		this.a_excludeWeekdays.clear();
												de.forestj.lib.Global.ilogConfig("Exclude weekday settings cleared");
	}
	
	/**
	 * Define weekdays where timer task will not be executed, e.g. Saturday and Sunday
	 * 
	 * @param p_o_dayOfWeek					weekday number 1..7
	 * 										1 - java.time.DayOfWeek.MONDAY
	 * 										2 - java.time.DayOfWeek.TUESDAY
	 * 										3 - java.time.DayOfWeek.WEDNESDAY
	 * 										4 - java.time.DayOfWeek.THURSDAY
	 * 										5 - java.time.DayOfWeek.FRIDAY
	 * 										6 - java.time.DayOfWeek.SATURDAY
	 * 										7 - java.time.DayOfWeek.SUNDAY 										
	 */
	public void excludeWeekday(java.time.DayOfWeek p_o_dayOfWeek) {
		if (!this.a_excludeWeekdays.contains(p_o_dayOfWeek)) {
			this.a_excludeWeekdays.add(p_o_dayOfWeek);
													de.forestj.lib.Global.ilogConfig("Exclude weekday '" + p_o_dayOfWeek + "'");
		}
	}
	
	/**
	 *  new instance of timer task must define a method runTimerTask()
	 */
	abstract public void runTimerTask() throws Exception;
	 
	/**
	 * timer task main run method which will always be executed at the end of an interval
	 */
	@Override
	public final void run() {
		/* get current weekday */
		java.time.DayOfWeek o_dayOfWeek = java.time.DayOfWeek.from(java.time.LocalDate.now()); 
		
		/* only execute runTimerTask if current weekday is not excluded */
		if (!this.a_excludeWeekdays.contains(o_dayOfWeek)) {
			try {
				/* execute runTimerTask */
				this.runTimerTask();
			} catch (Exception o_exc) {
				/* show exception stack trace if flag is set */
				if (this.b_showException) {
					de.forestj.lib.Global.logException(o_exc);
				}
			}
		} else {
													de.forestj.lib.Global.ilogFinest("Weekday '" + o_dayOfWeek + "' is excluded");
		}
	}
}
