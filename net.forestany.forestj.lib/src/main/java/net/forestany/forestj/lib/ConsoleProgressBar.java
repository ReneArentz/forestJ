package net.forestany.forestj.lib;

/**
 * 
 * Creates a progress bar which can be used in consoles.
 * Possibility of giving a starting text, done text and marquee text while progress is processing.
 *
 */
public class ConsoleProgressBar {
	
	/* Fields */
	
	private boolean b_initialised;
	private long l_animationInterval;
	private int i_blockLength;
	private int i_marqueeLength;
	private int i_marqueeInterval;
	private java.util.Timer o_timer;
	private ProgressBarTimerHandler o_progressBarTimerHandler;
	
	/* Properties */
	
	/* Methods */

	/** 
	 * Create console progress bar instance.
	 * animation interval: 125 - block length: 32 - marquee length: 32 - marquee interval: 4
	 * 
	 * @see		ConsoleProgressBar
	 */
	public ConsoleProgressBar() {
		this(32);
	}
	
	/** 
	 * Create console progress bar instance.
	 * animation interval: 125 - marquee interval: 4
	 * 
	 * @param	p_i_blockLength				length of the console progress bar animation and length of marquee text
	 * @see		ConsoleProgressBar
	 */
	public ConsoleProgressBar(int p_i_blockLength) {
		this(125, p_i_blockLength, p_i_blockLength);
	}
	
	/** 
	 * Create console progress bar instance.
	 * marquee interval: 4
	 * 
	 * @param	p_l_animationInterval		time interval in milliseconds for animation task execution
	 * @param	p_i_blockLength				length of the console progress bar animation and length of marquee text
	 * @see		ConsoleProgressBar
	 */
	public ConsoleProgressBar(long p_l_animationInterval, int p_i_blockLength) {
		this(p_l_animationInterval, p_i_blockLength, p_i_blockLength);
	}
	
	/** 
	 * Create console progress bar instance.
	 * marquee interval: 4
	 * 
	 * @param	p_l_animationInterval		time interval in milliseconds for animation task execution
	 * @param	p_i_blockLength				length of the console progress bar animation
	 * @param	p_i_marqueeLength			length of marquee text
	 * @see		ConsoleProgressBar
	 */
	public ConsoleProgressBar(long p_l_animationInterval, int p_i_blockLength, int p_i_marqueeLength) {
		this(p_l_animationInterval, p_i_blockLength, p_i_marqueeLength, 4);
	}
	
	/** 
	 * Create console progress bar instance.
	 * animation interval: 125 - block length: 32
	 * 
	 * @param	p_i_marqueeLength			length of marquee text
	 * @param	p_i_marqueeInterval			sub time interval of animation interval for marquee movement speed, e.g. 4 means every 4*animationInterval
	 * @see		ConsoleProgressBar
	 */
	public ConsoleProgressBar(int p_i_marqueeLength, int p_i_marqueeInterval) {
		this(125, 32, p_i_marqueeLength, p_i_marqueeInterval);
	}
	
	/** 
	 * Create console progress bar instance.
	 * 
	 * @param	p_l_animationInterval		time interval in milliseconds for animation task execution
	 * @param	p_i_blockLength				length of the console progress bar animation
	 * @param	p_i_marqueeLength			length of marquee text
	 * @param	p_i_marqueeInterval			sub time interval of animation interval for marquee movement speed, e.g. 4 means every 4*animationInterval
	 * @see		ConsoleProgressBar
	 */
	public ConsoleProgressBar(long p_l_animationInterval, int p_i_blockLength, int p_i_marqueeLength, int p_i_marqueeInterval) {
		this.b_initialised = false;
		this.l_animationInterval = p_l_animationInterval;
		this.i_blockLength = p_i_blockLength;
		this.i_marqueeLength = p_i_marqueeLength;
		this.i_marqueeInterval = p_i_marqueeInterval;
		
												net.forestany.forestj.lib.Global.ilogConfig("animationInterval: '" + this.l_animationInterval + "'");
												net.forestany.forestj.lib.Global.ilogConfig("blockLength: '" + this.i_blockLength + "'");
												net.forestany.forestj.lib.Global.ilogConfig("marqueeLength: '" + this.i_marqueeLength + "'");
												net.forestany.forestj.lib.Global.ilogConfig("marqueeInterval: '" + this.i_marqueeInterval + "'");
	}
	
	/** 
	 * Initialize and start rendering progress bar in console, can be reused after closing instance
	 * 
	 * @throws	IllegalStateException	progress bar is already initialised
	 * @see		java.util.Timer
	 */
	public void init() throws IllegalStateException {
		this.init(null, null, null);
	}
	
	/** 
	 * Initialize and start rendering progress bar in console, can be reused after closing instance
	 * 
	 * @param	p_s_startText			text shown if progress bar is starting
	 * @param	p_s_doneText			text shown if progress bar is done
	 * @throws	IllegalStateException	progress bar is already initialised
	 * @see		java.util.Timer
	 */
	public void init(String p_s_startText, String p_s_doneText) throws IllegalStateException {
		this.init(p_s_startText, p_s_doneText, null);
	}
	
	/** 
	 * Initialize and start rendering progress bar in console, can be reused after closing instance
	 * 
	 * @param	p_s_startText			text shown if progress bar is starting
	 * @param	p_s_doneText			text shown if progress bar is done
	 * @param	p_s_marqueeText			text within marquee area
	 * @throws	IllegalStateException	progress bar is already initialised
	 * @see		java.util.Timer
	 */
	public void init(String p_s_startText, String p_s_doneText, String p_s_marqueeText) throws IllegalStateException {
		if (this.b_initialised) {
			throw new IllegalStateException("ProgressBar is already initialised.");
		} else {
			this.b_initialised = true;
		}
		
		/* create timer instance */
		this.o_timer = new java.util.Timer(true);
		/* create new instance of private sub class */
		this.o_progressBarTimerHandler = new ProgressBarTimerHandler(this.i_blockLength, this.i_marqueeLength, this.i_marqueeInterval);
		this.o_progressBarTimerHandler.setStartText(p_s_startText);
		this.o_progressBarTimerHandler.setDoneText(p_s_doneText);
		this.o_progressBarTimerHandler.setMarqueeText(p_s_marqueeText);
		
												net.forestany.forestj.lib.Global.ilogConfig("startText: '" + p_s_startText + "'");
												net.forestany.forestj.lib.Global.ilogConfig("doneText: '" + p_s_doneText + "'");
												net.forestany.forestj.lib.Global.ilogConfig("marqueeText: '" + p_s_marqueeText + "'");
												
		/* starting timer with task and animation interval */
		this.o_timer.schedule(this.o_progressBarTimerHandler, 0, this.l_animationInterval);
		
												net.forestany.forestj.lib.Global.ilogFinest("ConsoleProgressBar timer started");
												net.forestany.forestj.lib.Global.ilogFinest("ConsoleProgressBar initialised");
	}
	
	/** 
	 * replace start text of console progress bar task while running
	 * 
	 * @param	p_s_value			text which will replace start text
	 */
	public void setStartText(String p_s_value) {
		if (this.o_progressBarTimerHandler != null) {
			this.o_progressBarTimerHandler.setStartText(p_s_value);
		}
	}
	
	/** 
	 * replace start text of console progress bar task while running
	 * 
	 * @param	p_s_value			text which will replace done text
	 */
	public void setDoneText(String p_s_value) {
		if (this.o_progressBarTimerHandler != null) {
			this.o_progressBarTimerHandler.setDoneText(p_s_value);
		}
	}
	
	/** 
	 * replace start text of console progress bar task while running
	 * 
	 * @param	p_s_value			text which will replace marquee text
	 */
	public void setMarqueeText(String p_s_value) {
		if (this.o_progressBarTimerHandler != null) {
			this.o_progressBarTimerHandler.setMarqueeText(p_s_value);
		}
	}
	
	/** 
	 * overwrite console progress bar progress by reporting a new value
	 * 
	 * @param	p_d_value			overwrite progress value
	 */
	public void report(double p_d_value) {
		if (this.o_progressBarTimerHandler != null) {
			this.o_progressBarTimerHandler.setProgress(Math.max(0, Math.min(1, p_d_value)));
													if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("Reported progress: '"  + Math.max(0, Math.min(1, p_d_value)) + "'");
		}
	}
	
	/** 
	 * Closed console progress bar, but can be reused by executing Init again
	 */
	public void close() {
		this.b_initialised = false;
		
		/* stop task and truncate shown text */
		if (this.o_progressBarTimerHandler != null) {
			this.o_progressBarTimerHandler.close();
		}
		
		/* purge timer instance */
		if (this.o_timer != null) {
			this.o_timer.cancel();
			this.o_timer.purge();
													net.forestany.forestj.lib.Global.ilogFinest("ConsoleProgressBar timer stopped");
		}
		
												net.forestany.forestj.lib.Global.ilogFinest("ConsoleProgressBar closed");
	}
	
	/* Internal Classes */
	
	private class ProgressBarTimerHandler extends java.util.TimerTask {
		/* Fields */
		
		private int i_blockLength = 32;
		private int i_marqueeLength = 32;
		private double d_progress = 0;

		private final java.util.concurrent.locks.ReentrantReadWriteLock Lock = new java.util.concurrent.locks.ReentrantReadWriteLock();
		
		private String s_animationCharacters = "|/-\\";
		private int i_animationIndex = 0;
		private int i_marqueeIndex = -1;
		private boolean b_scrollForward = true;
		private int i_scrollCounter = 0;
		private int i_marqueeInterval = 4;

		private String s_startText = "";
		private String s_doneText = "";
		private String s_marqueeText = "";
		private String s_text = "";
		private boolean b_disposed = false;
		
		/* Properties */

		/**
		 * thread safe method to read progress value
		 * 
		 */
		public double getProgress() {
			double d_foo = 0.0d;
			this.Lock.readLock().lock();
			d_foo = this.d_progress;
			this.Lock.readLock().unlock();
			return d_foo;
		}
		
		/**
		 * thread safe method to set progress value
		 * 
		 * @param	p_d_value		value to overwrite progress
		 */
		public void setProgress(double p_d_value) {
			this.Lock.writeLock().lock();
			this.d_progress = p_d_value;
			this.Lock.writeLock().unlock();
		}
		
		/**
		 * thread safe method to set start text value
		 * 
		 * @param	p_s_value		value to overwrite start text
		 */
		public void setStartText(String p_s_value) {
			if (!Helper.isStringEmpty(p_s_value)) {
				this.Lock.writeLock().lock();
				this.s_startText = p_s_value;
				this.Lock.writeLock().unlock();
			}
		}
		
		/**
		 * thread safe method to set done text value
		 * 
		 * @param	p_s_value		value to overwrite done text
		 */
		public void setDoneText(String p_s_value) {
			if (!Helper.isStringEmpty(p_s_value)) {
				this.Lock.writeLock().lock();
				this.s_doneText = p_s_value;
				this.Lock.writeLock().unlock();
			}
		}
		
		/**
		 * thread safe method to set marquee text value
		 * 
		 * @param	p_s_value		value to overwrite marquee text
		 */
		public void setMarqueeText(String p_s_value) {
			if (!Helper.isStringEmpty(p_s_value)) {
				this.Lock.writeLock().lock();
				this.s_marqueeText = p_s_value;
				this.Lock.writeLock().unlock();
			}
		}
		
		/* Methods */
		
		/** 
		 * Create progress bar timer task instance
		 * 
		 * @param	p_i_blockLength				length of the console progress bar animation
		 * @param	p_i_marqueeLength			length of marquee text
		 * @param	p_i_marqueeInterval			sub time interval of animation interval for marquee movement speed, e.g. 4 means every 4*animationInterval
		 * @see		ConsoleProgressBar
		 */
		public ProgressBarTimerHandler(int p_i_blockLength, int p_i_marqueeLength, int p_i_marqueeInterval) {
			this.i_blockLength = p_i_blockLength;
			this.i_marqueeLength = p_i_marqueeLength;
			this.i_marqueeInterval = p_i_marqueeInterval;
		}
		
		/** 
		 * Progress bar timer task run method which will be executed each interval
		 */
		@Override
		public final void run() {
			/* timer task is already disposed */
			if (this.b_disposed) return;
			
			String s_foo = "";
			
			try {
				if (!Helper.isStringEmpty(this.s_startText)) {
					s_foo = this.s_startText + " ";
				}
				
				/* implement marquee text if it has a value */
				if (!Helper.isStringEmpty(this.s_marqueeText)) {
					if (this.s_marqueeText.length() == this.i_marqueeLength) {
						/* marquee text length and marquee block length miraculously are the same */
						s_foo += "<" + this.s_marqueeText + "> ";
					} else {
						/* implement marquee interval with modulo calculation and an endless counter, or index < 0 */
						if ( (this.i_scrollCounter % this.i_marqueeInterval == 0) || (this.i_marqueeIndex < 0) ) {
							if (this.b_scrollForward) {
								/* increment index if we are scrolling forward */
								this.i_marqueeIndex++;
							} else {
								/* decrement index if we are not scrolling forward */
								this.i_marqueeIndex--;
							}
						}
						
						/* calculate start index */
						int i_start = this.i_marqueeIndex % this.s_marqueeText.length();
						/* calculate end index */
						int i_end = this.i_marqueeIndex + this.i_marqueeLength;
						
						if (this.i_marqueeIndex == 0) {
							/* scrolling forward if we start or returned to index 0 */
							this.b_scrollForward = true;
						} else if (i_end >= this.s_marqueeText.length()) {
							/* scrolling backward if end index greater equal complete marquee text length */
							this.b_scrollForward = false;
						} else {
							/* keep scroll state unchanged */
						}
						
						/* handle state if marquee text length is lower than specified marquee length for progress bar */
						if (this.s_marqueeText.length() < this.i_marqueeLength) {
							/* overwrite index values, so the whole marquee text will be shown */
							i_start = 0;
							i_end = this.s_marqueeText.length();
						}
						
						/* render marquee substring */
						s_foo += "<" + this.s_marqueeText.substring(i_start, i_end) + "> ";
						
						/* increment endless counter */
						this.i_scrollCounter++;
					}
				}
				
				/* calculate how many blocks we can render for progress */
				int i_handlerBlockCount = (int)(this.getProgress() * i_blockLength);
				
				/* render the actual progress bar in console, '#' sign for completed progress, '-' sign for uncompleted progress */
				s_foo += "[" + this.createCharacterChain('#', i_handlerBlockCount) + this.createCharacterChain('-', i_blockLength - i_handlerBlockCount) + "]";
				/* render percentage value of progress bar in console + animation character */
				s_foo += " " + String.format("%.2f", (this.getProgress() * 100.0d)) + "% " + s_animationCharacters.charAt( i_animationIndex++ % s_animationCharacters.length() );
			} catch (Exception o_exc) {
				s_foo += o_exc.getMessage(); 
			}
			
			try {
				/* update rendering of progress bar */
				this.update(s_foo);
			} catch (Exception o_exc) {
				/* log exception of rendering progress bar */
				Global.logException("Exception in rendering console progress bar with Update(String): ", o_exc);
			}
        }
		
		/** 
		 * Method for rendering progress bar
		 * 
		 * @param	p_s_text	complete console progress bar text, with animation, marquee, percentage and text
		 */
		private void update(String p_s_text) {
			/* calculate prefix length, where no characters must be changed */
			int i_prefixLength = 0;

			while (
					(i_prefixLength < Math.min( this.s_text.length(), p_s_text.length() )) /* prefix length is lower than minimum of local text field or text parameter value */
					 && (p_s_text.charAt(i_prefixLength) == this.s_text.charAt(i_prefixLength)) /* local text field and text parameter value have the same character on the same position */
			) {
				i_prefixLength++;
			}

			/* backspace to the first differing character */
			StringBuilder o_stringBuilder = new StringBuilder();
			o_stringBuilder.append( this.createCharacterChain('\b', this.s_text.length() - i_prefixLength) );

			/* add new suffix */
			o_stringBuilder.append(p_s_text.substring(i_prefixLength));

			/* get amount of overlapping characters */
			int i_overlapCount = this.s_text.length() - p_s_text.length();

			/* if new text is shorter, delete overlapping characters */
			if (i_overlapCount > 0) {
				o_stringBuilder.append( this.createCharacterChain(' ', i_overlapCount) );
				o_stringBuilder.append( this.createCharacterChain('\b', i_overlapCount) );
			}

			/* render progress bar */
			System.out.print(o_stringBuilder);

			/* store new text in local field */
			this.s_text = p_s_text;
		}
		
		/** 
		 * Closing progress bar timer task and render done text value
		 */
		public void close() {
			/* disposed flag, to stop run method from executing */
			b_disposed = true;
			/* delete console progress bar text */
			/* so you can enter any status text after progress has been finished or canceled */
			this.update("");
			
			/* render done text value */
			System.out.print(this.s_doneText + net.forestany.forestj.lib.io.File.NEWLINE);
		}
		
		/**
		 * Creates a string chain of one character only
		 * 
		 * @param p_c_char		character used for string chain
		 * @param p_i_amount	length of string chain
		 * @return	String
		 */
		private String createCharacterChain(char p_c_char, int p_i_amount) {
			StringBuilder o_stringBuilder = new StringBuilder();
			
			for (int i = 0; i < p_i_amount; i++) {
				o_stringBuilder.append(p_c_char);
			}
			
			return o_stringBuilder.toString();
		}
    }
}
