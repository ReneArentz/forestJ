package net.forestany.forestj.lib.io;

/**
 * 
 * Class to handle a fixed length record file - automatically detecting records, group headers or footers as a stacks of data
 *
 */
public class FixedLengthRecordFile {
	
	/* Fields */
	
	private String s_lineBreak;
	
	private FLRType o_groupHeader = null;
	private java.util.List<FLRType> a_flrTypes = new java.util.ArrayList<FLRType>();
	private FLRType o_groupFooter = null;
	private java.util.LinkedHashMap<Integer, FixedLengthRecordStack> a_stacks = new java.util.LinkedHashMap<Integer, FixedLengthRecordStack>();
	
	/* Properties */
	
	/**
	 * get line break characters
	 * 
	 * @return String
	 */
	public String getLineBreak() {
		return this.s_lineBreak;
	}
	
	/**
	 * Determine line break characters for reading and writing fixed length record files
	 * 
	 * @param p_s_lineBreak		line break characters
	 */
	public void setLineBreak(String p_s_lineBreak) {
		if (p_s_lineBreak.length() < 1) {
			throw new IllegalArgumentException("Line break must have at least a length of 1, but length is '" + p_s_lineBreak.length() + "'");
		}
		
		this.s_lineBreak = p_s_lineBreak;
												net.forestany.forestj.lib.Global.ilogConfig("updated line break to [" + net.forestany.forestj.lib.Helper.bytesToHexString(this.s_lineBreak.getBytes(), true) + "]");
	}
	
	/**
	 * Set group header with regex recognition or overall line length value
	 * 
	 * @param p_o_flrType						flr type as group header object
	 * @throws IllegalArgumentException			regex for recognizing group header is invalid
	 */
	public void setGroupHeader(FLRType p_o_flrType) throws IllegalArgumentException {
		this.o_groupHeader = p_o_flrType;
	}
	
	/**
	 * Set group footer with regex recognition or overall line length value
	 * 
	 * @param p_o_flrType						flr type as group footer object
	 * @throws IllegalArgumentException			regex for recognizing group footer is invalid
	 */
	public void setGroupFooter(FLRType p_o_flrType) throws IllegalArgumentException {
		this.o_groupFooter = p_o_flrType;
	}
	
	/**
	 * get flr file stacks
	 * 
	 * @return get linked hash map for stacks within fixed length record file
	 */
	public java.util.LinkedHashMap<Integer, FixedLengthRecordStack> getStacks() {
		return this.a_stacks;
	}
	
	/* Methods */
	
	/**
	 * Fixed length record file constructor
	 * 
	 * @param p_o_flr								fixed length record object for configuration
	 * @param p_s_regexFLR							regex recognition
	 * @throws IllegalArgumentException				parameter is null or invalid
	 */
	public FixedLengthRecordFile(FixedLengthRecord<?> p_o_flr, String p_s_regexFLR) throws IllegalArgumentException {
		this(p_o_flr, p_s_regexFLR, null, null, null, null);
	}
	
	/**
	 * Fixed length record file constructor
	 * 
	 * @param p_o_flr								fixed length record object for configuration
	 * @param p_s_regexFLR							regex recognition
	 * @param p_i_knownLengthFLR					overall line length of flr
	 * @throws IllegalArgumentException				parameter is null or invalid
	 */
	public FixedLengthRecordFile(FixedLengthRecord<?> p_o_flr, String p_s_regexFLR, int p_i_knownLengthFLR) throws IllegalArgumentException {
		this(p_o_flr, p_s_regexFLR, p_i_knownLengthFLR, null, null, -1, null, null, -1);
	}
	
	/**
	 * Fixed length record file constructor
	 * 
	 * @param p_o_flr								fixed length record object for configuration
	 * @param p_s_regexFLR							regex recognition
	 * @param p_o_groupHeader						group header object
	 * @param p_s_regexGroupHeader					regex recognition
	 * @throws IllegalArgumentException				parameter is null or invalid
	 */
	public FixedLengthRecordFile(FixedLengthRecord<?> p_o_flr, String p_s_regexFLR, FixedLengthRecord<?> p_o_groupHeader, String p_s_regexGroupHeader) throws IllegalArgumentException {
		this(p_o_flr, p_s_regexFLR, p_o_groupHeader, p_s_regexGroupHeader, null, null);
	}
	
	/**
	 * Fixed length record file constructor
	 * 
	 * @param p_o_flr								fixed length record object for configuration
	 * @param p_s_regexFLR							regex recognition
	 * @param p_i_knownLengthFLR					overall line length of flr
	 * @param p_o_groupHeader						group header object
	 * @param p_s_regexGroupHeader					regex recognition
	 * @param p_i_knownLengthGroupHeader			overall line length of group header
	 * @throws IllegalArgumentException				parameter is null or invalid
	 */
	public FixedLengthRecordFile(FixedLengthRecord<?> p_o_flr, String p_s_regexFLR, int p_i_knownLengthFLR, FixedLengthRecord<?> p_o_groupHeader, String p_s_regexGroupHeader, int p_i_knownLengthGroupHeader) throws IllegalArgumentException {
		this(p_o_flr, p_s_regexFLR, p_i_knownLengthFLR, p_o_groupHeader, p_s_regexGroupHeader, p_i_knownLengthGroupHeader, null, null, -1);
	}
	
	/**
	 * Fixed length record file constructor
	 * 
	 * @param p_o_flr								fixed length record object for configuration
	 * @param p_s_regexFLR							regex recognition
	 * @param p_o_groupHeader						group header object
	 * @param p_s_regexGroupHeader					regex recognition
	 * @param p_o_groupFooter						group footer object
	 * @param p_s_regexGroupFooter					regex recognition
	 * @throws IllegalArgumentException				parameter is null or invalid
	 */
	public FixedLengthRecordFile(FixedLengthRecord<?> p_o_flr, String p_s_regexFLR, FixedLengthRecord<?> p_o_groupHeader, String p_s_regexGroupHeader, FixedLengthRecord<?> p_o_groupFooter, String p_s_regexGroupFooter) throws IllegalArgumentException {
		this(p_o_flr, p_s_regexFLR, -1, p_o_groupHeader, p_s_regexGroupHeader, -1, p_o_groupFooter, p_s_regexGroupFooter, -1);
	}
	
	/**
	 * Fixed length record file constructor
	 * 
	 * @param p_o_flr								fixed length record object for configuration
	 * @param p_s_regexFLR							regex recognition
	 * @param p_i_knownLengthFLR					overall line length of flr
	 * @param p_o_groupHeader						group header object
	 * @param p_s_regexGroupHeader					regex recognition
	 * @param p_i_knownLengthGroupHeader			overall line length of group header
	 * @param p_o_groupFooter						group footer object
	 * @param p_s_regexGroupFooter					regex recognition
	 * @param p_i_knownLengthGroupFooter			overall line length of group footer
	 * @throws IllegalArgumentException				parameter is null or invalid
	 */
	public FixedLengthRecordFile(FixedLengthRecord<?> p_o_flr, String p_s_regexFLR, int p_i_knownLengthFLR, FixedLengthRecord<?> p_o_groupHeader, String p_s_regexGroupHeader, int p_i_knownLengthGroupHeader, FixedLengthRecord<?> p_o_groupFooter, String p_s_regexGroupFooter, int p_i_knownLengthGroupFooter) throws IllegalArgumentException {
		this.setLineBreak(net.forestany.forestj.lib.io.File.NEWLINE);
		
		if ( (p_o_groupHeader != null) && ((net.forestany.forestj.lib.Helper.isStringEmpty(p_s_regexGroupHeader)) ^ (p_i_knownLengthGroupHeader < 0)) ) {
			this.setGroupHeader(new FLRType(p_o_groupHeader, p_s_regexGroupHeader, p_i_knownLengthGroupHeader));
		}
		
		if ( (p_o_groupFooter != null) && ((net.forestany.forestj.lib.Helper.isStringEmpty(p_s_regexGroupFooter)) ^ (p_i_knownLengthGroupFooter < 0)) ) {
			this.setGroupFooter(new FLRType(p_o_groupFooter, p_s_regexGroupFooter, p_i_knownLengthGroupFooter));
		}
		
		if (p_o_flr == null) {
			throw new IllegalArgumentException("Parameter for fixed length record object is null");
		}
		
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_regexFLR)) && (p_i_knownLengthFLR < 0) ) {
			throw new IllegalArgumentException("Parameter for recognizing fixed length record and parameter for known length of flr is null or empty");
		}
		
		this.addFLRType(new FLRType(p_o_flr, p_s_regexFLR, p_i_knownLengthFLR));
	}
	
	/**
	 * Add flr type to configuration, which are accepted for flr file
	 * 
	 * @param p_o_flrType					fixed length record type object for the list of flr types
	 * @throws IllegalArgumentException		regex for recognizing is invalid or flr type already exists in configuration
	 */
	public void addFLRType(FLRType p_o_flrType) throws IllegalArgumentException {
		/* iterate all existing flr types */
		for (FLRType o_flrType : this.a_flrTypes) {
			/* check if we already have a flr type with matching regex and known length parameter */
			if ( ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_o_flrType.s_regexFLR)) && (o_flrType.s_regexFLR.contentEquals(p_o_flrType.s_regexFLR)) ) && (o_flrType.i_knownLengthFLR == p_o_flrType.i_knownLengthFLR) ) {
				throw new IllegalArgumentException("Fixed length record type with regex '" + p_o_flrType.s_regexFLR + "' and known length '" + p_o_flrType.i_knownLengthFLR + "' already exists in configuration");
			}
		}
		
		/* clear unique temp map */
		p_o_flrType.o_flrObject.clearUniqueTemp();
		
		this.a_flrTypes.add(p_o_flrType);
	}
	
	/**
	 * create new stack
	 * 
	 * @return get new fixed length record stack object
	 */
	public FixedLengthRecordStack createNewStack() {
		return new FixedLengthRecordStack();
	}
	
	/**
	 * Add stack to configuration
	 * 
	 * @param p_i_key			number of stack
	 * @param p_o_stack			flr stack object
	 */
	public void addStack(int p_i_key, FixedLengthRecordStack p_o_stack) {
		this.a_stacks.put(p_i_key, p_o_stack);
	}
	
	/**
	 * method to read a fixed length record file, using net.forestany.forestj.lib.io.File standard charset
	 * 
	 * @param p_s_file												full-path to flr file
	 * @throws IllegalArgumentException								value/structure within flr file invalid
	 * @throws java.io.IOException									cannot access or open flr file and it's content
	 * @throws ClassCastException									we could not parse a value to predicted field object type
	 * @throws java.lang.reflect.InvocationTargetException			if the underlying constructor throws an exception
	 * @throws NoSuchFieldException									field does not exist
	 * @throws InstantiationException								if the class that declares the underlying constructor represents an abstract class
	 * @throws IllegalAccessException								cannot access field, must be public
	 * @throws NoSuchMethodException								could not retrieve declared constructor
	 * @throws ClassNotFoundException								class cannot be located
	 * @throws IllegalStateException								field value for unique field already exists
	 */
	public void readFile(String p_s_file) throws IllegalArgumentException, ClassCastException, java.lang.reflect.InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException, NoSuchFieldException, java.io.IOException {
		this.readFile(p_s_file, java.nio.charset.Charset.forName(net.forestany.forestj.lib.io.File.CHARSET), false);
	}
	
	/**
	 * method to read a fixed length record file
	 * 
	 * @param p_s_file												full-path to flr file
	 * @param p_o_charset											which charset will be used accessing/modifying the file content
	 * @throws IllegalArgumentException								value/structure within flr file invalid
	 * @throws java.io.IOException									cannot access or open flr file and it's content
	 * @throws ClassCastException									we could not parse a value to predicted field object type
	 * @throws java.lang.reflect.InvocationTargetException			if the underlying constructor throws an exception
	 * @throws NoSuchFieldException									field does not exist
	 * @throws InstantiationException								if the class that declares the underlying constructor represents an abstract class
	 * @throws IllegalAccessException								cannot access field, must be public
	 * @throws NoSuchMethodException								could not retrieve declared constructor
	 * @throws ClassNotFoundException								class cannot be located
	 * @throws IllegalStateException								field value for unique field already exists
	 */
	public void readFile(String p_s_file, java.nio.charset.Charset p_o_charset) throws IllegalArgumentException, ClassCastException, java.lang.reflect.InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException, NoSuchFieldException, java.io.IOException {
		this.readFile(p_s_file, p_o_charset, false);
	}
	
	/**
	 * method to read a fixed length record file
	 * 
	 * @param p_s_file												full-path to flr file
	 * @param p_o_charset											which charset will be used accessing/modifying the file content
	 * @param p_b_ignroeUniqueConstraint							true - ignore unique constraint of unique fields and it's values, false - exception will be thrown if unique constraint has been violated
	 * @throws IllegalArgumentException								value/structure within flr file invalid
	 * @throws java.io.IOException									cannot access or open flr file and it's content
	 * @throws ClassCastException									we could not parse a value to predicted field object type
	 * @throws java.lang.reflect.InvocationTargetException			if the underlying constructor throws an exception
	 * @throws NoSuchFieldException									field does not exist
	 * @throws InstantiationException								if the class that declares the underlying constructor represents an abstract class
	 * @throws IllegalAccessException								cannot access field, must be public
	 * @throws NoSuchMethodException								could not retrieve declared constructor
	 * @throws ClassNotFoundException								class cannot be located
	 * @throws IllegalStateException								field value for unique field already exists
	 */
	public void readFile(String p_s_file, java.nio.charset.Charset p_o_charset, boolean p_b_ignroeUniqueConstraint) throws IllegalArgumentException, ClassCastException, java.lang.reflect.InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException, NoSuchFieldException, java.io.IOException {
		/* check if file exists */
		if (!File.exists(p_s_file)) {
			throw new IllegalArgumentException("File[" + p_s_file + "] does not exist.");
		}
		
		/* open flr file */
		File o_file = new File(p_s_file, false, p_o_charset, this.s_lineBreak);
		
		/* read all flr file lines */
		java.util.List<String> a_flrLines = o_file.getFileContentAsList();
		
		/* help variables */
		int i_stackNumber = 0;
		int i_flrNumber = 0;
		this.a_stacks.put(i_stackNumber, new FixedLengthRecordStack());
		ReadState e_readState = ReadState.FLR;
		
		/* iterate each line in flr file */
		for (String s_line : a_flrLines) {
			FLRType o_foundFLRType = null;
			
			/* check if we have a line for group header */
			if ( (this.o_groupHeader != null) && 
					(
						(!net.forestany.forestj.lib.Helper.isStringEmpty(this.o_groupHeader.s_regexFLR)) && (java.util.regex.Pattern.matches(this.o_groupHeader.s_regexFLR, s_line)) ||
						(this.o_groupHeader.i_knownLengthFLR >= 0) && (s_line.length() == this.o_groupHeader.i_knownLengthFLR)
					)
				) {
				e_readState = ReadState.GROUPHEADER;
			} else if ( (this.o_groupFooter != null) && 
				(
						(!net.forestany.forestj.lib.Helper.isStringEmpty(this.o_groupFooter.s_regexFLR)) && (java.util.regex.Pattern.matches(this.o_groupFooter.s_regexFLR, s_line)) ||
						(this.o_groupFooter.i_knownLengthFLR >= 0) && (s_line.length() == this.o_groupFooter.i_knownLengthFLR)
					)
				) { /* check if we have a line for group footer */
				e_readState = ReadState.GROUPFOOTER;
			} else { /* check for other flr types */
				boolean b_foundFLR = false;
				
				/* iterate all existing flr types */
				for (FLRType o_flrType : this.a_flrTypes) {
					/* regex recognition is not successful -> skip */
					if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(o_flrType.s_regexFLR)) && (!java.util.regex.Pattern.matches(o_flrType.s_regexFLR, s_line)) ) {
						continue;
					}
					
					/* overall line length is not successful -> skip */
					if ( (o_flrType.i_knownLengthFLR >= 0) && (s_line.length() != o_flrType.i_knownLengthFLR) ) {
						continue;
					}

					/* memorize found flr type */
					o_foundFLRType = o_flrType;
					b_foundFLR = true;
				}
				
				/* have we found a flr type */
				if (!b_foundFLR) {
					throw new IllegalArgumentException("could not parse fixed length record #" + (i_flrNumber + 1) + " within stack #" + (i_stackNumber + 1) + ": line does not match any regex or known length values(" + s_line.length() + ")");
				}
				
				e_readState = ReadState.FLR;
			}
			
			if (e_readState == ReadState.GROUPHEADER) { /* read group header */
				/* if we have no group footer configured and this is not the first group header(flr number != 0), then we can increase our stack number */
				if ( (this.o_groupFooter == null) && (i_flrNumber != 0) ) {
					/* increase stack number */
					i_stackNumber++;
					/* create a new stack */
					this.a_stacks.put(i_stackNumber, new FixedLengthRecordStack());
					/* reset record number to zero */
					i_flrNumber = 0;
					
					/* clear all unique temp lists of flr types */
					for (FLRType o_flrType : this.a_flrTypes) {
						o_flrType.o_flrObject.clearUniqueTemp();
					}
				}
				
				/* read all fields from group header */
				FixedLengthRecord<?> o_temp = readFileLineRecursive(s_line, e_readState, this.o_groupHeader.o_flrObject, i_stackNumber, i_flrNumber, p_b_ignroeUniqueConstraint);
				
				/* add group header to current stack */
				this.a_stacks.get(i_stackNumber).setGroupHeader(o_temp);
			} else if (e_readState == ReadState.FLR) { /* read fixed length records */
				/* read all fields from fixed length record */
				FixedLengthRecord<?> o_temp = readFileLineRecursive(s_line, e_readState, o_foundFLRType.o_flrObject, i_stackNumber, i_flrNumber, p_b_ignroeUniqueConstraint);
				
				/* add fixed length record to current stack */
				this.a_stacks.get(i_stackNumber).addFixedLengthRecord(i_flrNumber, o_temp);
			} else if (e_readState == ReadState.GROUPFOOTER) { /* read group footer */
				/* read all fields from group footer */
				FixedLengthRecord<?> o_temp = readFileLineRecursive(s_line, e_readState, this.o_groupFooter.o_flrObject, i_stackNumber, i_flrNumber, p_b_ignroeUniqueConstraint);
				
				/* add group footer to current stack */
				this.a_stacks.get(i_stackNumber).setGroupFooter(o_temp);
				
				/* increase stack number, because we read group footer - thus we closed a stack */
				i_stackNumber++;
				/* create a new stack */
				this.a_stacks.put(i_stackNumber, new FixedLengthRecordStack());
				/* reset record number to zero */
				i_flrNumber = 0;
				
				/* clear all unique temp lists of flr types */
				for (FLRType o_flrType : this.a_flrTypes) {
					o_flrType.o_flrObject.clearUniqueTemp();
				}
			}
			
			/* increase fixed length record number */
			i_flrNumber++;
		}
		
		/* check if last stack is completly empty, because if the last line is a group footer, we might have create a new empty stack */
		if ( (this.a_stacks.get(i_stackNumber).getGroupHeader() == null) && (this.a_stacks.get(i_stackNumber).getGroupFooter() == null) && (this.a_stacks.get(i_stackNumber).getFixedLengthRecords().size() < 1) ) {
			/* remove last stack */
			this.a_stacks.remove(i_stackNumber);
		}
	}
	
	/**
	 * handling a file line and read it as group header, footer or normal flr line
	 * 
	 * @param p_s_line												flr line
	 * @param p_e_readState											which type of line we want to read
	 * @param p_o_flrType											flr object
	 * @param p_i_stackNumber										which flr file stack we are processing
	 * @param p_i_flrNumber											which record within a stack we are processing
	 * @param p_b_ignroeUniqueConstraint							true - ignore unique constraint of unique fields and it's values, false - exception will be thrown if unique constraint has been violated
	 * @return														fixed length record object which can be added to a stack
	 * @throws IllegalArgumentException								line length does not match known overall length of fixed length record
	 * @throws ClassCastException									we could not parse a value to predicted field object type
	 * @throws java.lang.reflect.InvocationTargetException			if the underlying constructor throws an exception
	 * @throws NoSuchFieldException									field does not exist
	 * @throws java.lang.InstantiationException						if the class that declares the underlying constructor represents an abstract class
	 * @throws IllegalAccessException								cannot access field, must be public
	 * @throws NoSuchMethodException								could not retrieve declared constructor
	 * @throws ClassNotFoundException								class cannot be located
	 * @throws IllegalStateException								field value for unique field already exists
	 */
	private static FixedLengthRecord<?> readFileLineRecursive(String p_s_line, ReadState p_e_readState, FixedLengthRecord<?> p_o_flrType, int p_i_stackNumber, int p_i_flrNumber, boolean p_b_ignroeUniqueConstraint) throws IllegalArgumentException, ClassCastException, java.lang.reflect.InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, ClassNotFoundException, NoSuchFieldException, IllegalStateException {
		FixedLengthRecord<?> o_temp = null;
		
		/* read group header */
		if (p_e_readState == ReadState.GROUPHEADER) {
			try {
				/* read all field values from string line */
				o_temp = (FixedLengthRecord<?>)p_o_flrType.readFieldsFromString(p_s_line);
			} catch (IllegalStateException o_exc) {
				if (!p_b_ignroeUniqueConstraint) {
					throw new IllegalStateException("could not parse group header of stack #" + (p_i_stackNumber + 1) + ": " + o_exc);
				} else {
					net.forestany.forestj.lib.Global.ilog("could not parse group header of stack #" + (p_i_stackNumber + 1) + ": " + o_exc);
				}
			}
		} else if (p_e_readState == ReadState.FLR) { /* read fixed length records */
			try {
				/* read all field values from string line */
				o_temp = (FixedLengthRecord<?>)p_o_flrType.readFieldsFromString(p_s_line);
			} catch (IllegalStateException o_exc) {
				if (!p_b_ignroeUniqueConstraint) {
					throw new IllegalStateException("could not parse fixed length record #" + (p_i_flrNumber + 1) + " within stack #" + (p_i_stackNumber + 1) + ": " + o_exc);
				} else {
					net.forestany.forestj.lib.Global.ilog("could not parse fixed length record #" + (p_i_flrNumber + 1) + " within stack #" + (p_i_stackNumber + 1) + ": " + o_exc);
				}
			}
		} else if (p_e_readState == ReadState.GROUPFOOTER) { /* read group footer */
			try {
				/* read all field values from string line */
				o_temp = (FixedLengthRecord<?>)p_o_flrType.readFieldsFromString(p_s_line);
			} catch (IllegalStateException o_exc) {
				if (!p_b_ignroeUniqueConstraint) {
					throw new IllegalStateException("could not parse group footer of stack #" + (p_i_stackNumber + 1) + ": " + o_exc);
				} else {
					net.forestany.forestj.lib.Global.ilog("could not parse group footer of stack #" + (p_i_stackNumber + 1) + ": " + o_exc);
				}
			}
		}
		
		return o_temp;
	}
	
	/**
	 * method to write a fixed length record file, using net.forestany.forestj.lib.io.File standard charset
	 * 
	 * @param p_s_file								full-path to flr file
	 * @throws IllegalArgumentException				flr file already exists
	 * @throws java.io.IOException					cannot access or create flr file and it's content
	 * @throws NoSuchFieldException					field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 * @throws IllegalStateException				unique constraint violation
	 * @throws NoSuchMethodException							could not retrieve declared constructor
	 * @throws java.lang.InstantiationException					if the class that declares the underlying constructor represents an abstract class
	 * @throws ClassNotFoundException							class cannot be located
	 * @throws java.lang.reflect.InvocationTargetException		if the underlying constructor throws an exception
	 */
	public void writeFile(String p_s_file) throws IllegalArgumentException, java.io.IOException, NoSuchFieldException, IllegalAccessException, IllegalStateException, NoSuchMethodException, java.lang.InstantiationException, ClassNotFoundException, java.lang.reflect.InvocationTargetException {
		this.writeFile(p_s_file, java.nio.charset.Charset.forName(net.forestany.forestj.lib.io.File.CHARSET));
	}
	
	/**
	 * method to write a fixed length record file
	 * 
	 * @param p_s_file											full-path to flr file
	 * @param p_o_charset										which charset will be used accessing/modifying the file content
	 * @throws IllegalArgumentException							flr file already exists
	 * @throws java.io.IOException								cannot access or create flr file and it's content
	 * @throws NoSuchFieldException								field does not exist
	 * @throws IllegalAccessException							cannot access field, must be public
	 * @throws IllegalStateException							unique constraint violation
	 * @throws NoSuchMethodException							could not retrieve declared constructor
	 * @throws java.lang.InstantiationException					if the class that declares the underlying constructor represents an abstract class
	 * @throws ClassNotFoundException							class cannot be located
	 * @throws java.lang.reflect.InvocationTargetException		if the underlying constructor throws an exception
	 */
	public void writeFile(String p_s_file, java.nio.charset.Charset p_o_charset) throws IllegalArgumentException, java.io.IOException, NoSuchFieldException, IllegalAccessException, IllegalStateException, NoSuchMethodException, java.lang.InstantiationException, ClassNotFoundException, java.lang.reflect.InvocationTargetException {
		/* we must check for unique constraint violations */
		int i_stackNumber = 1;
		
		/* iterate each flr stack */
		for (FixedLengthRecordStack o_flrStack : this.a_stacks.values()) {
			/* check if group header is available */
			if (o_flrStack.getGroupHeader() != null) {
				/* check for unique constraints with current group header */
				this.writeCheckUniqueConstraints(o_flrStack.getGroupHeader(), i_stackNumber, true);
			}
			
			/* check if group footer is available */
			if (o_flrStack.getGroupFooter() != null) {
				/* check for unique constraints with current group footer */
				this.writeCheckUniqueConstraints(o_flrStack.getGroupFooter(), i_stackNumber, false);
			}
			
			i_stackNumber++;
		}
		
		/* check if file exists */
		if (File.exists(p_s_file)) {
			throw new IllegalArgumentException("File[" + p_s_file + "] does exist.");
		}
		
		/* create flr file */
		File o_file = new File(p_s_file, true, this.s_lineBreak);
		
		/* list of lines */
		java.util.List<String> a_lines = new java.util.ArrayList<String>();
		
		/* iterate each stack in current flr file object */
		for (java.util.Map.Entry<Integer, net.forestany.forestj.lib.io.FixedLengthRecordFile.FixedLengthRecordStack> o_stack : this.a_stacks.entrySet()) {
			/* check if group header is available */
			if (o_stack.getValue().getGroupHeader() != null) {
				/* add group header as line */
				a_lines.add(o_stack.getValue().getGroupHeader().writeFieldsToString());
			}
			
			/* iterate each fixed length record of current stack */
			for (java.util.Map.Entry<Integer, net.forestany.forestj.lib.io.FixedLengthRecord<?>> o_foo : o_stack.getValue().getFixedLengthRecords().entrySet()) {
				/* check if flr is available */
				if (o_foo != null) {
					/* add flr as line */
					a_lines.add(o_foo.getValue().writeFieldsToString());
				}
			}
			
			/* check if group footer is available */
			if (o_stack.getValue().getGroupFooter() != null) {
				/* add group footer as line */
				a_lines.add(o_stack.getValue().getGroupFooter().writeFieldsToString());
			}
		}
		
		/* write lines to flr file */
		o_file.setFileContentFromList(a_lines);
	}
	
	/**
	 * We must check that we do not violate a unique constraint with group headers or group footers
	 * 
	 * @param p_o_flr								fixed length record of group header or group footer
	 * @param p_i_stackNumber						origin stack number of header or footer
	 * @param p_b_checkHeader						true - compare group headers, false - compare group footers
	 * @throws NoSuchFieldException					field does not exist
	 * @throws IllegalAccessException				cannot access field, must be public
	 * @throws IllegalStateException				unique constraint violation
	 * @throws IllegalArgumentException				parameter stack number must be at least '1'
	 */
	private void writeCheckUniqueConstraints(FixedLengthRecord<?> p_o_flr, int p_i_stackNumber, boolean p_b_checkHeader) throws NoSuchFieldException, IllegalAccessException, IllegalStateException, IllegalArgumentException {
		/* check parameter stack number */
		if (p_i_stackNumber < 1) {
			throw new IllegalArgumentException("Parameter stack number must be at least '1', not lower");
		}
		
		/* iterate each unique key */
		for (String s_unique : p_o_flr.Unique) {
			/* it is possible that a unique constraint exists of multiple columns, separated by semicolon */
			if (s_unique.contains(";")) {
				String[] a_uniques = s_unique.split(";");
				
				int i_stackNumber = 1;
				
				/* iterate each flr stack */
				for (FixedLengthRecordStack o_flrStack : this.a_stacks.values()) {
					/* skip own stack */
					if (i_stackNumber == p_i_stackNumber) {
						continue;
					}
					
					/* get group header or group footer */
					FixedLengthRecord<?> o_flr = (p_b_checkHeader) ? o_flrStack.getGroupHeader() : o_flrStack.getGroupFooter();
					
					/* check if both records are the same type */
					if (p_o_flr.getClass().equals(o_flr.getClass())) {
						boolean b_allAreEqual = true;
						
						/* iterate each unique key field */
						for (int i = 0; i < a_uniques.length; i++) {
							/* get unique field values */
							Object o_one = p_o_flr.getFieldValue(a_uniques[i]);
							Object o_two = o_flr.getFieldValue(a_uniques[i]);
							
							/* compare both unique field values */
							if (o_flr.AllowEmptyUniqueFields) {
                                /* field value is null or an empty string */
                                if (((o_one == null) || (o_one.toString().trim().length() < 1)) && ((o_two == null) || (o_two.toString().trim().length() < 1))) {
                                    b_allAreEqual = false;
                                }

                                try {
                                    /* field value can be parsed to int and is equal to zero */
                                    if ((Integer.parseInt(o_one.toString()) == 0) && (Integer.parseInt(o_two.toString()) == 0)) {
                                        b_allAreEqual = false;
                                    }
                                } catch (Exception o_exc) {
                                    /* nothing to do */
                                }

                                if (b_allAreEqual) {
                                    b_allAreEqual = ((o_one != null) && (o_one.equals(o_two)));
                                }
                            } else if (!( ((o_one == null) && (o_two == null)) || ( (o_one != null) && ( o_one.equals(o_two) ) ) )) {
								b_allAreEqual = false;
							}
						}
						
						if (b_allAreEqual) {
							/* unique fields are equal */
							throw new IllegalStateException("Unique constraint issue: values for fields '" + s_unique + "' in stack #" + p_i_stackNumber + " already exists within another stack (#" + i_stackNumber + ")");
						}
					}
					
					i_stackNumber++;
				}
			} else {
				int i_stackNumber = 1;
				
				/* iterate each flr stack */
				for (FixedLengthRecordStack o_flrStack : this.a_stacks.values()) {
					/* skip own stack */
					if (i_stackNumber == p_i_stackNumber) {
						continue;
					}
					
					/* get group header or group footer */
					FixedLengthRecord<?> o_flr = (p_b_checkHeader) ? o_flrStack.getGroupHeader() : o_flrStack.getGroupFooter();
					
					/* check if both records are the same type */
					if (p_o_flr.getClass().equals(o_flr.getClass())) {
						/* get unique field values */
						Object o_one = p_o_flr.getFieldValue(s_unique);
						Object o_two = o_flr.getFieldValue(s_unique);
						
						/* compare both unique field values */
						if (o_flr.AllowEmptyUniqueFields) {
                            boolean b_allAreEqual = true;

                            /* field value is null or an empty string */
                            if (((o_one == null) || (o_one.toString().trim().length() < 1)) && ((o_two == null) || (o_two.toString().trim().length() < 1))) {
                                b_allAreEqual = false;
                            }

                            try {
                                /* field value can be parsed to int and is equal to zero */
                            	if ((Integer.parseInt(o_one.toString()) == 0) && (Integer.parseInt(o_two.toString()) == 0)) {
                            	    b_allAreEqual = false;
                                }
                            } catch (Exception o_exc) {
                                /* nothing to do */
                            }

                            if ((b_allAreEqual) && ((o_one != null) && (o_one.equals(o_two)))) {
                                /* unique field values are equal */
                                throw new IllegalStateException("Unique constraint issue: value for field '" + s_unique + "' in stack #" + p_i_stackNumber + " already exists within another record (#" + i_stackNumber + ")");
                            }
                        } else if ( ((o_one == null) && (o_two == null)) || ( (o_one != null) && ( o_one.equals(o_two) ) ) ) {
							/* unique field values are equal */
							throw new IllegalStateException("Unique constraint issue: value for field '" + s_unique + "' in stack #" + p_i_stackNumber + " already exists within another record (#" + i_stackNumber + ")");
						}
					}
					
					i_stackNumber++;
				}
			}
		}
	}
	
	/* Internal Classes */
	
	/**
	 * Enumeration for read state of a fixed length record file
	 */
	private enum ReadState {
		GROUPHEADER, FLR, GROUPFOOTER
	}
	
	/**
	 * Other class to encapsulate a fixed length record object with regex recognition property and/or overall line length property
	 */
	public class FLRType {
		
		/* Fields */
		
		/**
		 * Fixed length record instance
		 */
		public FixedLengthRecord<?> o_flrObject = null;
		/**
		 * Regex recognition string
		 */
		public String s_regexFLR = null;
		/**
		 * Overall known line length of flr
		 */
		public int i_knownLengthFLR = -1;
		
		/* Properties */
		
		/* Methods */
		
		/**
		 * Fixed length record type constructor
		 * 
		 * @param p_o_flrObject							flr object
		 * @param p_s_regexFLR							regex recognition
		 * @throws IllegalArgumentException				parameter is null, at least a regex recognition or an overall line length parameter, regex for recognizing flr is invalid
		 */
		public FLRType(FixedLengthRecord<?> p_o_flrObject, String p_s_regexFLR) throws IllegalArgumentException {
			this(p_o_flrObject, p_s_regexFLR, -1);
		}
		
		/**
		 * Fixed length record type constructor
		 * 
		 * @param p_o_flrObject							flr object
		 * @param p_s_regexFLR							regex recognition
		 * @param p_i_knownLengthFLR					overall line length of flr
		 * @throws IllegalArgumentException				parameter is null, at least a regex recognition or an overall line length parameter, regex for recognizing flr is invalid
		 */
		public FLRType(FixedLengthRecord<?> p_o_flrObject, String p_s_regexFLR, int p_i_knownLengthFLR) throws IllegalArgumentException {
			/* flr object must not be null */
			if (p_o_flrObject == null) {
				throw new IllegalArgumentException("Parameter for fixed length record object is null");
			}
			
			/* we must have at least a regex recognition or an overall line length parameter */
			if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_regexFLR)) && (p_i_knownLengthFLR < 0) ) {
				throw new IllegalArgumentException("Parameter for recognizing fixed length record and parameter for known length of flr is null or empty");
			}
			
			/* check if we use regex recognition */
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_regexFLR)) {
				try {
					java.util.regex.Pattern.compile(p_s_regexFLR);
				} catch (java.util.regex.PatternSyntaxException o_exc) {
					throw new IllegalArgumentException("Regex for recognizing group footer is invalid: " + o_exc);
				}
			} else {
				p_s_regexFLR = null;
			}
			
			this.o_flrObject = p_o_flrObject;
			this.s_regexFLR = p_s_regexFLR;
			this.i_knownLengthFLR = p_i_knownLengthFLR;
			
			/* clear unique temp map */
			this.o_flrObject.clearUniqueTemp();
		}
	}
	
	/**
	 * Internal class to encapsulate a stack fixed length record objects, group header and group footer
	 */
	public class FixedLengthRecordStack {
		
		/* Fields */
		
		private FixedLengthRecord<?> o_groupHeader = null;
		private java.util.LinkedHashMap<Integer, FixedLengthRecord<?>> o_flr = new java.util.LinkedHashMap<Integer, FixedLengthRecord<?>>();
		private FixedLengthRecord<?> o_groupFooter = null;
		
		/* Properties */
		
		/**
		 * get group header instance
		 * 
		 * @return FixedLengthRecord&lt;?&gt;
		 */
		public FixedLengthRecord<?> getGroupHeader() {
			return this.o_groupHeader;
		}
		
		/**
		 * set group header instance
		 * 
		 * @param p_o_value		Fixed length record instance which describes a group header
		 */
		public void setGroupHeader(FixedLengthRecord<?> p_o_value) {
			this.o_groupHeader = p_o_value;
		}
		
		/**
		 * get group footer instance
		 * 
		 * @return FixedLengthRecord&lt;?&gt;
		 */
		public FixedLengthRecord<?> getGroupFooter() {
			return this.o_groupFooter;
		}
		
		/**
		 * set group footer instance
		 * 
		 * @param p_o_value		Fixed length record instance which describes a group footer
		 */
		public void setGroupFooter(FixedLengthRecord<?> p_o_value) {
			this.o_groupFooter = p_o_value;
		}
		
		/**
		 * get fixed length records from current stack
		 * 
		 * @return java.util.LinkedHashMap&lt;Integer, FixedLengthRecord&lt;?&gt;&gt;
		 */
		public java.util.LinkedHashMap<Integer, FixedLengthRecord<?>> getFixedLengthRecords() {
			return this.o_flr;
		}
		
		/* Methods */
		
		/**
		 * Fixed length record stack constructor
		 */
		public FixedLengthRecordStack() {
			
		}
		
		/**
		 * Add fixed length record object to internal list of flr stack object
		 * 
		 * @param p_i_key						key number of fixed length record
		 * @param p_o_flr						fixed length record object
		 * @throws NoSuchFieldException			field does not exist
		 * @throws IllegalAccessException		cannot access field, must be public
		 * @throws IllegalStateException		unique constraint violation
		 * @throws IllegalArgumentException		parameter flr key number must be at least '0'
		 */
		public void addFixedLengthRecord(int p_i_key, FixedLengthRecord<?> p_o_flr) throws NoSuchFieldException, IllegalAccessException, IllegalStateException {
			/* check parameter flr key number */
			if (p_i_key < 0) {
				throw new IllegalArgumentException("Parameter flr key number must be at least '0', positive number");
			}
			
			/* we must check that we do not violate a unique constraint within flr list */
			
			/* iterate each unique key */
			for (String s_unique : p_o_flr.Unique) {
				/* it is possible that a unique constraint exists of multiple columns, separated by semicolon */
				if (s_unique.contains(";")) {
					String[] a_uniques = s_unique.split(";");
					
					int i_recordNumber = 1;
					
					/* iterate each flr in current stack */
					for (FixedLengthRecord<?> o_flr : this.o_flr.values()) {
						/* check if both records are the same type */
						if (p_o_flr.getClass().equals(o_flr.getClass())) {
							boolean b_allAreEqual = true;
							
							/* iterate each unique key field */
							for (int i = 0; i < a_uniques.length; i++) {
								/* get unique field values */
								Object o_one = p_o_flr.getFieldValue(a_uniques[i]);
								Object o_two = o_flr.getFieldValue(a_uniques[i]);
								
								/* compare both unique field values */
								if (o_flr.AllowEmptyUniqueFields) {
                                    /* field value is null or an empty string */
                                    if (((o_one == null) || (o_one.toString().trim().length() < 1)) && ((o_two == null) || (o_two.toString().trim().length() < 1))) {
                                        b_allAreEqual = false;
                                    }

                                    try {
                                        /* field value can be parsed to int and is equal to zero */
                                    	if ((Integer.parseInt(o_one.toString()) == 0) && (Integer.parseInt(o_two.toString()) == 0)) {
                                    	    b_allAreEqual = false;
                                        }
                                    } catch (Exception o_exc) {
                                        /* nothing to do */
                                    }

                                    if (b_allAreEqual) {
                                        b_allAreEqual = ((o_one != null) && (o_one.equals(o_two)));
                                    }
                                } else if (!( ((o_one == null) && (o_two == null)) || ( (o_one != null) && ( o_one.equals(o_two) ) ) )) {
									b_allAreEqual = false;
								}
							}
							
							if (b_allAreEqual) {
								/* unique fields are equal */
								throw new IllegalStateException("Unique constraint issue: values for fields '" + s_unique + "' in record #" + (p_i_key + 1) + " already exists within another record (#" + i_recordNumber + ")");
							}
						}
						
						i_recordNumber++;
					}
				} else {
					int i_recordNumber = 1;
					
					/* iterate each flr in current stack */
					for (FixedLengthRecord<?> o_flr : this.o_flr.values()) {
						/* check if both records are the same type */
						if (p_o_flr.getClass().equals(o_flr.getClass())) {
							/* get unique field values */
							Object o_one = p_o_flr.getFieldValue(s_unique);
							Object o_two = o_flr.getFieldValue(s_unique);
							
							/* compare both unique field values */
							if (o_flr.AllowEmptyUniqueFields)
                            {
                                boolean b_allAreEqual = true;

                                /* field value is null or an empty string */
                                if (((o_one == null) || (o_one.toString().trim().length() < 1)) && ((o_two == null) || (o_two.toString().trim().length() < 1))) {
                                    b_allAreEqual = false;
                                }

                                try {
                                    /* field value can be parsed to int and is equal to zero */
                                	if ((Integer.parseInt(o_one.toString()) == 0) && (Integer.parseInt(o_two.toString()) == 0)) {
                                        b_allAreEqual = false;
                                    }
                                } catch (Exception o_exc) {
                                    /* nothing to do */
                                }

                                if ((b_allAreEqual) && ((o_one != null) && (o_one.equals(o_two)))) {
                                    /* unique field values are equal */
                                    throw new IllegalStateException("Unique constraint issue: value for field '" + s_unique + "' in record #" + (p_i_key + 1) + " already exists within another record (#" + i_recordNumber + ")");
                                }
                            } else if ( ((o_one == null) && (o_two == null)) || ( (o_one != null) && ( o_one.equals(o_two) ) ) ) {
								/* unique field values are equal */
								throw new IllegalStateException("Unique constraint issue: value for field '" + s_unique + "' in record #" + (p_i_key + 1) + " already exists within another record (#" + i_recordNumber + ")");
							}
						}
						
						i_recordNumber++;
					}
				}
			}
			
			this.o_flr.put(p_i_key, p_o_flr);
		}
	}
}
