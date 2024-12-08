package de.forestj.lib;

/**
 * 
 * Runnable thread class to log memory info of java virtual machine.
 *
 */
public class JVMMemoryInfo implements Runnable {
	
	/* Fields */
	
	private java.util.logging.Logger o_logger;
	private long l_intervalMilliseconds;
	private java.util.logging.Level e_level;
	private boolean b_memoryChanged;
	private boolean b_stop;
	private Runtime o_runtime;
	private long l_prevTotal;
	private long l_prevFree;
	private long l_prevUsed;
	private long l_maxTotal;
	private long l_maxFree;
	private long l_maxUsed;
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * Creates a runnable thread instance to log memory info of java virtual machine 
	 * 
	 * @param p_o_logger					logger object where the memory information will be logged
	 * @param p_l_intervalMilliseconds		interval how often the memory information should be added to log
	 * @param p_e_level						on which log level the memory information will be added to log
	 * @throws IllegalArgumentException		will tell invalid value for interval milliseconds
	 */
	public JVMMemoryInfo(java.util.logging.Logger p_o_logger, long p_l_intervalMilliseconds, java.util.logging.Level p_e_level) throws IllegalArgumentException {
		this(p_o_logger, p_l_intervalMilliseconds, p_e_level, false);
	}
	
	/**
	 * Creates a runnable thread instance to log memory info of java virtual machine 
	 * 
	 * @param p_o_logger					logger object where the memory information will be logged
	 * @param p_l_intervalMilliseconds		interval how often the memory information should be added to log
	 * @param p_e_level						on which log level the memory information will be added to log
	 * @param p_b_memoryChanged				only add memory information to log if total or free memory has changed between current and previous iteration
	 * @throws IllegalArgumentException		will tell invalid value for interval milliseconds
	 */
	public JVMMemoryInfo(java.util.logging.Logger p_o_logger, long p_l_intervalMilliseconds, java.util.logging.Level p_e_level, boolean p_b_memoryChanged) throws IllegalArgumentException {
		/* check parameter value */
		if (p_l_intervalMilliseconds < 1) {
			throw new IllegalArgumentException("Interval must be at least '1' millisecond, but was set to '" + p_l_intervalMilliseconds + "' millisecond(s)");
		}
		
		/* assume parameters to values and set defaults */
		this.o_logger = p_o_logger;
		this.l_intervalMilliseconds = p_l_intervalMilliseconds;
		this.b_memoryChanged = p_b_memoryChanged;
		this.e_level = p_e_level;
		this.b_stop = false;
		this.o_runtime = Runtime.getRuntime();
		this.l_prevTotal = 0;
		this.l_prevFree = 0;
		this.l_prevUsed = 0;
		this.l_maxTotal = 0;
		this.l_maxFree = 0;
		this.l_maxUsed = 0;
	}
	
	/**
	 * stops thread run-method by breaking endless loop with stop-flag
	 */
	public void stop() {
		this.b_stop = true;
	}
	
	/**
	 * thread run-method
	 */
	@Override
	public void run() {
		try {
			/* endless loop until stop-flag is set */
			while (!this.b_stop) {
				/* sleep for defined interval in milliseconds */
				Thread.sleep(this.l_intervalMilliseconds);
				
				/* get memory info from runtime instance */
				long l_total = this.o_runtime.totalMemory();
		        long l_free = this.o_runtime.freeMemory();
		        long l_used = l_total - l_free;
		        
		        /* update max memory info if we reached a new peak */
		        if (l_total > this.l_maxTotal) {
		        	this.l_maxTotal = l_total;
		        }
		        
		        /* update free memory info if we reached a new peak */
		        if (l_free > this.l_maxFree) {
		        	this.l_maxFree = l_free;
		        }
		        
		        /* update used memory info if we reached a new peak */
		        if (l_used > this.l_maxUsed) {
		        	this.l_maxUsed = l_used;
		        }
		        
		        /* if total and free memory info have not changed, we will jump to start of endless loop */
		        if ( (l_total == this.l_prevTotal) && (l_free == this.l_prevFree) ) {
		        	if (this.b_memoryChanged) {
		        		continue;
		        	}
		        }
		        
		        /* update previous used memory info */
		        this.l_prevUsed = (this.l_prevTotal - this.l_prevFree);
	            
		        /* add jvm memory information to log */
	            this.o_logger.log(this.e_level,
					"JVM-Memory-Info - Total: " + Helper.formatBytes(l_total) +
					", Used: " + Helper.formatBytes(l_used) +
					", dUsed: " + Helper.formatBytes(l_used - this.l_prevUsed) +
					", Free: " + Helper.formatBytes(l_free) +
					", dFree: " + Helper.formatBytes(l_free - this.l_prevFree)
	            );
		        
	            /* update previous total memory info */
		        this.l_prevTotal = l_total;
		        /* update previous free memory info */
		        this.l_prevFree = l_free;
			}
		} catch (Exception o_exc) {
			/* log exception */
			de.forestj.lib.Global.logException(o_exc);
		} finally {
			/* add jvm memory maximum information to log as end result */
			this.o_logger.log(this.e_level,
				"JVM-Memory-Info - max. Total: " + Helper.formatBytes(this.l_maxTotal) +
				", max. Used: " + Helper.formatBytes(this.l_maxUsed) +
				", max. Free: " + Helper.formatBytes(this.l_maxFree)
			);
		}
	}
}
