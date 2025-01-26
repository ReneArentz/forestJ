package de.forestj.lib.io;

/**
 * 
 * Abstract FileSystemWatcher class to get events about file create, change, delete and access events of a directory.
 * optional to get events recursive for all sub directories and optional file extension filter.
 * 
 * Inherit from this class and define the event methods to tell your program what to do if an event is fired.
 *
 */
public abstract class FileSystemWatcher extends de.forestj.lib.TimerTask {
	
	/* Fields */
	
	private String s_directory;
	private java.util.List<ListingElement> a_files;
	private de.forestj.lib.Timer o_timerObject;
	private boolean b_recursive;
	private boolean b_create;
	private boolean b_change;
	private boolean b_delete;
	private boolean b_access;
	private String s_fileExtensionFilter;
	
	/* Properties */
	
	public void setRecursive(boolean p_b_value) {
		this.b_recursive = p_b_value;
												de.forestj.lib.Global.ilogConfig("updated recursive to '" + p_b_value + "'");
	}
	
	public void setCreate(boolean p_b_value) {
		this.b_create = p_b_value;
												de.forestj.lib.Global.ilogConfig("updated create to '" + p_b_value + "'");
	}
	
	public void setChange(boolean p_b_value) {
		this.b_change = p_b_value;
												de.forestj.lib.Global.ilogConfig("updated change to '" + p_b_value + "'");
	}
	
	public void setDelete(boolean p_b_value) {
		this.b_delete = p_b_value;
												de.forestj.lib.Global.ilogConfig("updated delete to '" + p_b_value + "'");
	}
	
	public void setAccess(boolean p_b_value) {
		this.b_access = p_b_value;
												de.forestj.lib.Global.ilogConfig("updated access to '" + p_b_value + "'");
	}
	
	public void setFileExtensionFilter(String p_s_value) {
		this.s_fileExtensionFilter = p_s_value;
												de.forestj.lib.Global.ilogConfig("updated file extension filter to '" + p_s_value + "'");
	}
	
	/* Methods */
	
	/**
	 * Create a file system watcher instance with interval value 
	 * 
	 * @param p_s_directory					full path to directory which will be watched
	 * @param p_o_interval					interval when at the end the timer task will be always executed and interval will start new
	 * @throws IllegalArgumentException		directory parameter is not a valid path
	 * @see de.forestj.lib.DateInterval
	 */
	public FileSystemWatcher(String p_s_directory, de.forestj.lib.DateInterval p_o_interval) throws IllegalArgumentException {
		this(p_s_directory, p_o_interval, null);
	}
	
	/**
	 * Create a file system watcher instance with interval value and a start time
	 * 
	 * @param p_s_directory					full path to directory which will be watched
	 * @param p_o_interval					interval when at the end the timer task will be always executed and interval will start new
	 * @param p_o_startTime					start time when the timer task will execute for the first time
	 * @throws IllegalArgumentException		directory parameter is not a valid path
	 * @see de.forestj.lib.DateInterval
	 * @see java.time.LocalTime
	 */
	public FileSystemWatcher(String p_s_directory, de.forestj.lib.DateInterval p_o_interval, java.time.LocalTime p_o_startTime) throws IllegalArgumentException {
		super(p_o_interval, p_o_startTime);
		
		/* check if directory parameter is a real directory */
		if (!File.isDirectory(p_s_directory)) {
			throw new IllegalArgumentException("File path[" + p_s_directory + "] is not a valid directory");
		}
		
		/* init class fields */
		this.s_directory = p_s_directory;
		this.a_files = new java.util.ArrayList<ListingElement>();
		this.o_timerObject = null;
		this.b_recursive = false;
		this.b_create = false;
		this.b_change = false;
		this.b_delete = false;
		this.b_access = false;
		this.s_fileExtensionFilter = "";
	}
	
	/**
	 * get dynamic list of file extension filter elements
	 * @return	string list
	 * @see java.util.List
	 * @see String
	 */
	private java.util.List<String> getFileExtensionFilter() {
		/* list of file extension or other filter restrictions we want to use */
		java.util.List<String> a_filter = new java.util.ArrayList<String>();
		
		/* check if file extension filter is not empty */
		if (!de.forestj.lib.Helper.isStringEmpty(this.s_fileExtensionFilter)) {
			/* if value contains delimiter, we need to split up the file extension filter */
			if (this.s_fileExtensionFilter.contains("|")) {
				for (String s_fileExtensionFilter : this.s_fileExtensionFilter.split("\\|")) {
					/* add file extension filter to list */
					a_filter.add(s_fileExtensionFilter);
				}
			} else {
				/* add file extension filter to list */
				a_filter.add(s_fileExtensionFilter);
			}
		}
		
		/* return list of filter restrictions */
		return a_filter;
	}
	
	/**
	 * Start file system watcher with timer object as basis.
	 * multiple starts are not possible.
	 * 
	 * @throws IOException 					directory could not be listed or issue with reading file/directory attributes
	 * @throws InvalidPathException 		path does not exist
	 */
	public void start() throws java.nio.file.InvalidPathException, java.io.IOException {
		/* only start if timer object is not set, preventing double start */
		if (this.o_timerObject == null) {
			/* list of file extension or other filter restrictions we want to use */
			java.util.List<String> a_filter = this.getFileExtensionFilter();
			
			/* iterate all files in observing directory */
			for (ListingElement o_listingElement : File.listDirectory(this.s_directory, this.b_recursive)) {
				/* if we have any filter values */
				if (a_filter.size() > 0) {
					/* if one filter value allows all files with any extension, we do not need any restriction */
					if (!a_filter.contains("*.*")) {
						boolean b_match = false;
						
						/* iterate each filter restriction */
						for (String s_filter : a_filter) {
							/* if filter starts with wildcard, we are controlling just the end of file names */
							if (s_filter.startsWith("*")) {
								/* remove wildcard */
								s_filter = s_filter.substring(1);
								
								/* check if filename ends with filter restriction */
								if (o_listingElement.getName().endsWith(s_filter)) {
									b_match = true;
								}
							} else { /* otherwise file name must match completely */
								if (o_listingElement.getName().contentEquals(s_filter)) {
									b_match = true;
								}
							}
						}
						
						/* if we have no match with our restriction, we can skip this file */
						if (!b_match) {
							continue;
						}
					}
				}
				
				/* add file to file list */
				this.a_files.add(o_listingElement);
														de.forestj.lib.Global.ilogFinest("Added file on start of file system watcher to have it's initial state [" + o_listingElement.getFullName() + "]");
			}
			
			/* create timer object and start timer */
			this.o_timerObject = new de.forestj.lib.Timer(this);
			this.o_timerObject.startTimer();
													de.forestj.lib.Global.ilogFiner("File system watcher has been started");
		} else {
													de.forestj.lib.Global.ilog("File system watcher already has been started - no action has been done");
		}
	}
	
	/**
	 * Stops file system watcher and sets object null, so it can be start again
	 * multiple stops are not possible
	 */
	public void stop() {
		/* only stop if timer object is set, preventing double stop */
		if (this.o_timerObject != null) {
			this.o_timerObject.stopTimer();
			this.o_timerObject = null;
													de.forestj.lib.Global.ilogFiner("File system watcher has been stopped");
		} else {
													de.forestj.lib.Global.ilog("File system watcher already has been stopped or never started - no action has been done");
		}
	}
	
	/**
	 * File System Watcher run method which will recognize and fire events
	 * 
	 * @throws IOException 					directory could not be listed or issue with reading file/directory attributes
	 * @throws InvalidPathException 		path does not exist
	 */
	@Override
	public void runTimerTask() throws java.nio.file.InvalidPathException, java.io.IOException {
		/* temp list for scanning file of newest state */
		java.util.List<ListingElement> a_currentFiles = new java.util.ArrayList<ListingElement>();
		
		/* list of file extension or other filter values we want to use */
		java.util.List<String> a_filter = this.getFileExtensionFilter();
		
		/* iterate all files in observing directory */
		for (ListingElement o_listingElement : File.listDirectory(this.s_directory, this.b_recursive)) {
			/* if we have any filter values */
			if (a_filter.size() > 0) {
				/* if one filter value allows all files with any extension, we do not need any restriction */
				if (!a_filter.contains("*.*")) {
					boolean b_match = false;
					
					/* iterate each filter restriction */
					for (String s_filter : a_filter) {
						/* if filter starts with wildcard, we are controlling just the end of file names */
						if (s_filter.startsWith("*")) {
							/* remove wildcard */
							s_filter = s_filter.substring(1);
							
							/* check if filename ends with filter restriction */
							if (o_listingElement.getName().endsWith(s_filter)) {
								b_match = true;
							}
						} else { /* otherwise file name must match completely */
							if (o_listingElement.getName().contentEquals(s_filter)) {
								b_match = true;
							}
						}
					}
					
					/* if we have no match with our restriction, we can skip this file */
					if (!b_match) {
						continue;
					}
				}
			}
			
			/* add file to temporary file list */
			a_currentFiles.add(o_listingElement);
		}
		
		/* iterate all files from last timer iteration, to find changed and deleted files */
		for (ListingElement o_listingElement : this.a_files) {
			/* skip elements which are directories */
			if (o_listingElement.getIsDirectory()) {
				continue;
			}
			
			/* help variables for file state comparison */
			ListingElement o_compareListingElement = null;
			boolean b_found = false;
			
			/* iterate all files of newest state */
			for (ListingElement o_searchListingElement : a_currentFiles) {
				/* if full name matches, file still exists */
				if (o_listingElement.getFullName().contentEquals(o_searchListingElement.getFullName())) {
					o_compareListingElement = o_searchListingElement;
					b_found = true;
					break;
				}
			}
			
			if (b_found) { /* file still exists */
				/* check if we want to handle change events */
				if (this.b_change) {
					if (o_compareListingElement.getLastModifiedTime().compareTo(o_listingElement.getLastModifiedTime()) > 0) {
						/* fire change event */
						de.forestj.lib.Global.ilogFinest("fire change event '" + o_compareListingElement.getFullName() + "'");
						this.changeEvent(o_compareListingElement);
					}
				}
				
				/* check if we want to handle access events */
				if (this.b_access) {
					if (o_compareListingElement.getLastAccessTime().compareTo(o_listingElement.getLastAccessTime()) > 0) {
						/* fire access event */
						de.forestj.lib.Global.ilogFinest("fire access event '" + o_compareListingElement.getFullName() + "'");
						this.accessEvent(o_compareListingElement);
					}
				}
			} else { /* file was deleted */
				/* check if we want to handle delete events */
				if (this.b_delete) {
					/* fire delete event */
					de.forestj.lib.Global.ilogFinest("fire delete event '" + o_listingElement.getFullName() + "'");
					this.deleteEvent(o_listingElement);
				}
			}
		}
		
		/* iterate all files of newest state, to find new files */
		for (ListingElement o_listingElement : a_currentFiles) {
			/* skip elements which are directories */
			if (o_listingElement.getIsDirectory()) {
				continue;
			}
			
			/* help variable for file state comparison */
			boolean b_found = false;
			
			/* iterate all files from last timer iteration */
			for (ListingElement o_searchListingElement : this.a_files) {
				/* if full name matches, file exists before */
				if (o_listingElement.getFullName().contentEquals(o_searchListingElement.getFullName())) {
					b_found = true;
					break;
				}
			}
			
			/* check if file not exists before and if we want to handle create events */
			if ( (!b_found) && (this.b_create) ) {
				/* fire create event */
				de.forestj.lib.Global.ilogFinest("fire create event '" + o_listingElement.getFullName() + "'");
				this.createEvent(o_listingElement);
			}
		}
	
		/* update file list with temporary file list for next timer tick */
		this.a_files = a_currentFiles;
	}
	
	/**
	 * What should happen if a new file has been created
	 * 
	 * @param p_o_listingElement	file element of type ListingElement
	 * @see de.forestj.lib.io.ListingElement
	 */
	abstract public void createEvent(ListingElement p_o_listingElement);
	
	/**
	 * What should happen if a file has been changed
	 * 
	 * @param p_o_listingElement	file element of type ListingElement
	 * @see de.forestj.lib.io.ListingElement
	 */
	abstract public void changeEvent(ListingElement p_o_listingElement);
	
	/**
	 * What should happen if a file has been deleted
	 * 
	 * @param p_o_listingElement	file element of type ListingElement
	 * @see de.forestj.lib.io.ListingElement
	 */
	abstract public void deleteEvent(ListingElement p_o_listingElement);
	
	/**
	 * What should happen if a file has been accessed
	 * ### NOT WORKING ON EVERY OS, OS SETTINGS CAN PREVENT THIS EVENT ###
	 * 
	 * @param p_o_listingElement	file element of type ListingElement
	 * @see de.forestj.lib.io.ListingElement
	 */
	abstract public void accessEvent(ListingElement p_o_listingElement);
}
