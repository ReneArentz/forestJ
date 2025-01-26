package de.forestj.lib;

/**
 * 
 * Timer class with timer task object to execute repeating code within a time interval.
 *
 */
public class Timer {
	
	/* Fields */
	
	private java.util.Timer o_timer;
	private TimerTask o_timerTask;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * Create a timer instance, only with a timer task object
	 * 
	 * @param p_o_timerTask		timer task which holds the real content which will be executed after the end of an interval
	 */
	public Timer(TimerTask p_o_timerTask) {
		this.o_timerTask = p_o_timerTask;
	}
	
	/**
	 * Start timer instance with timer task object and defined interval and optional start time value
	 */
	public void startTimer() {
		/* create new timer object, start delay standard 0  */
		this.o_timer = new java.util.Timer();
	    long l_startDelay = 0;
	    		
	    if (this.o_timerTask.getStartTime() != null) {
	    	/* start delay calculation, so we subtract current time from set start time */
	    											de.forestj.lib.Global.ilogConfig("Start delay calculation with start time '" + this.o_timerTask.getStartTime() + "'");

			/* get current time */
	    	java.time.LocalTime o_localTime = java.time.LocalTime.now();
	    	
	    	/* calculate start delay of timer */
	    	l_startDelay = this.o_timerTask.getStartTime().toNanoOfDay() / 1_000_000 - o_localTime.toNanoOfDay() / 1_000_000;
	    	
	    											de.forestj.lib.Global.ilogConfig("Calculated start delay: '" + l_startDelay + " ms'");	    	
			/* if calculated start delay is negative, it will be set for next day */ 
			if (l_startDelay < 0) {
				l_startDelay += 24 * 60 * 60 * 1_000;
														de.forestj.lib.Global.ilogConfig("start delay is negative, it will be set for next day: '" + java.time.LocalDateTime.now().plusSeconds(l_startDelay / 1_000) + "'");
			}
	    }
	    
	    /* start timer with timer task object, optional start delay and interval duration */
	    this.o_timer.scheduleAtFixedRate(this.o_timerTask, l_startDelay, this.o_timerTask.getInterval().toDuration());
	    										de.forestj.lib.Global.ilogFinest("timer started");
	}
	
	/**
	 * Stops timer instance
	 */
	public void stopTimer() {
		this.o_timer.cancel();
												de.forestj.lib.Global.ilogFinest("timer stopped");
	}
}
