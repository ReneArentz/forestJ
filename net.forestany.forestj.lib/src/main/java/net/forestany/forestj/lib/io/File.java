package net.forestany.forestj.lib.io;

/**
 * 
 * File class for creating, manipulating or deleting files in various ways.
 * Multiple static methods to check file or directory existence, move or copy files/directories, modify file permissions.
 * Static methods for creating random file content or replacing whole file content.
 *
 */
public class File {

	/* Constants */
	
	/**
	 * Charset constant
	 */
	public static final String CHARSET = "UTF-8";
	/**
	 * Directory separator 
	 */
	public static final String DIR = java.io.File.separator;
	/**
	 * Line separator
	 */
	public static final String NEWLINE = System.lineSeparator();
	
	/* Fields */
	
	private String s_fullFilename;
	private String s_filename;
	private long l_fileLength;
	private boolean b_ready;
	private java.util.List<String> a_fileContent;
	private java.nio.charset.Charset o_charset;
	private java.nio.file.Path o_path;
	private String s_lineBreak;
	
	/* Properties */
	
	/**
	 * Get integer value of amount of file lines
	 * 
	 * @return int
	 */
	public int getFileLines() {
		return this.a_fileContent.size();
	}
	
	/**
	 * Get string content of file with line breaks
	 * 
	 * @return String
	 */
	public String getFileContent() {
		if (!this.b_ready) {
			return "";
		}
		
		/* nothing to read in file */
		if ( (this.l_fileLength == 0) || (!java.nio.file.Files.exists(this.o_path)) ) {
			return "";
		}
		
		StringBuilder o_stringBuilder = new StringBuilder();
		
		for (int i = 0; i < this.a_fileContent.size(); i++) {
			o_stringBuilder.append(this.a_fileContent.get(i) + this.s_lineBreak);
		}
		
		return o_stringBuilder.toString();
	}
	
	/**
	 * Get content of file as dynamic string list
	 * 
	 * @return List of String
	 */
	public java.util.List<String> getFileContentAsList() {
		java.util.List<String> a_content = new java.util.ArrayList<String>();
		
		if (!this.b_ready) {
			return null;
		}
		
		/* nothing to read in file */
		if ( (this.l_fileLength == 0) || (!java.nio.file.Files.exists(this.o_path)) ) {
			return null;
		}
		
		for (int i = 0; i < this.a_fileContent.size(); i++) {
			a_content.add(this.a_fileContent.get(i));
		}
		
		return a_content;
	}
	
	/**
	 * Last modified date timestamp
	 * 
	 * @return java.time.LocalDateTime
	 */
	public java.time.LocalDateTime getLastModified() {
		return File.lastModified(this.s_fullFilename);
	}
	
	/**
	 * Overwrite file content with dynamic string list parameter
	 * 
	 * @param p_a_content				dynamic string list
	 * @throws java.io.IOException		issue with file write access
	 */
	public void setFileContentFromList(java.util.List<String> p_a_content) throws java.io.IOException {
		if ( (p_a_content != null) && (p_a_content.size() > 0) ) {
			StringBuilder o_stringBuilder = new StringBuilder();
			
			for (String s_line : p_a_content) {
				o_stringBuilder.append(s_line + this.s_lineBreak);
			}
			
			/* write file content */
			try {
				java.nio.file.Files.write(java.nio.file.Paths.get(this.s_fullFilename), o_stringBuilder.toString().getBytes(this.o_charset), java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
			} catch (java.io.IOException o_exc) {
				throw new java.io.IOException("File[" + this.s_fullFilename + "] write access not possible: " + o_exc.getMessage());
			}
		}
	}
	
	/**
	 * Get file name as string
	 * 
	 * @return String
	 */
	public String getFileName() {
		return this.s_filename;
	}
	
	/* Methods */
	
	/**
	 * Constructor to create file instance, opening an existing file.
	 * default charset: UTF-8.
	 * 
	 * @param p_s_fullfilename					full path + filename to the file
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				file cannot be created or cannot be read
	 * @throws IllegalArgumentException			invalid line break parameter
	 */
	public File(String p_s_fullfilename) throws java.io.FileNotFoundException, java.io.IOException, IllegalArgumentException {
		this(p_s_fullfilename, java.nio.charset.Charset.forName(File.CHARSET), false, System.lineSeparator());
	}
	
	/**
	 * Constructor to create file instance, opening an existing file.
	 * default charset: UTF-8.
	 * 
	 * @param p_s_fullfilename					full path + filename to the file
	 * @param p_s_lineBreak						use alternative line break '\n', '\r\n', or '\r'
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				file cannot be created or cannot be read
	 * @throws IllegalArgumentException			invalid line break parameter
	 */
	public File(String p_s_fullfilename, String p_s_lineBreak) throws java.io.FileNotFoundException, java.io.IOException, IllegalArgumentException {
		this(p_s_fullfilename, java.nio.charset.Charset.forName(File.CHARSET), false, p_s_lineBreak);
	}
	
	/**
	 * Constructor to create file instance.
	 * default charset: UTF-8.
	 * 
	 * @param p_s_fullfilename					full path + filename to the file
	 * @param p_b_new							flag to control if file should be created new or is already exisiting
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				file cannot be created or cannot be read
	 * @throws IllegalArgumentException			invalid line break parameter
	 */
	public File(String p_s_fullfilename, boolean p_b_new) throws java.io.FileNotFoundException, java.io.IOException, IllegalArgumentException {
		this(p_s_fullfilename, java.nio.charset.Charset.forName(File.CHARSET), p_b_new, System.lineSeparator());
	}
	
	/**
	 * Constructor to create file instance.
	 * default charset: UTF-8.
	 * 
	 * @param p_s_fullfilename					full path + filename to the file
	 * @param p_b_new							flag to control if file should be created new or is already exisiting
	 * @param p_s_lineBreak						use alternative line break '\n', '\r\n', or '\r'
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				file cannot be created or cannot be read
	 * @throws IllegalArgumentException			invalid line break parameter
	 */
	public File(String p_s_fullfilename, boolean p_b_new, String p_s_lineBreak) throws java.io.FileNotFoundException, java.io.IOException, IllegalArgumentException {
		this(p_s_fullfilename, java.nio.charset.Charset.forName(File.CHARSET), p_b_new, p_s_lineBreak);
	}
	
	/**
	 * Constructor to create file instance
	 * 
	 * @param p_s_fullfilename					full path + filename to the file
	 * @param p_b_new							flag to control if file should be created new or is already exisiting
	 * @param p_o_charset						which charset will be used accessing/modifying the file content
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				file cannot be created or cannot be read
	 * @throws IllegalArgumentException			invalid line break parameter
	 */
	public File(String p_s_fullfilename, boolean p_b_new, java.nio.charset.Charset p_o_charset) throws java.io.FileNotFoundException, java.io.IOException, IllegalArgumentException {
		this(p_s_fullfilename, p_o_charset, p_b_new, System.lineSeparator());
	}
	
	/**
	 * Constructor to create file instance
	 * 
	 * @param p_s_fullfilename					full path + filename to the file
	 * @param p_b_new							flag to control if file should be created new or is already exisiting
	 * @param p_o_charset						which charset will be used accessing/modifying the file content
	 * @param p_s_lineBreak						use alternative line break '\n', '\r\n', or '\r'
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				file cannot be created or cannot be read
	 * @throws IllegalArgumentException			invalid line break parameter
	 */
	public File(String p_s_fullfilename, boolean p_b_new, java.nio.charset.Charset p_o_charset, String p_s_lineBreak) throws java.io.FileNotFoundException, java.io.IOException, IllegalArgumentException {
		this(p_s_fullfilename, p_o_charset, p_b_new, p_s_lineBreak);
	}
	
	/**
	 * Constructor to create file instance, opening an existing file
	 * 
	 * @param p_s_fullfilename					full path + filename to the file
	 * @param p_o_charset						which charset will be used accessing/modifying the file content
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				file cannot be created or cannot be read
	 * @throws IllegalArgumentException			invalid line break parameter
	 */
	public File(String p_s_fullfilename, java.nio.charset.Charset p_o_charset) throws java.io.FileNotFoundException, java.io.IOException, IllegalArgumentException {
		this(p_s_fullfilename, p_o_charset, false, System.lineSeparator());
	}
	
	/**
	 * Constructor to create file instance, opening an existing file
	 * 
	 * @param p_s_fullfilename					full path + filename to the file
	 * @param p_o_charset						which charset will be used accessing/modifying the file content
	 * @param p_s_lineBreak						use alternative line break '\n', '\r\n', or '\r'
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				file cannot be created or cannot be read
	 * @throws IllegalArgumentException			invalid line break parameter
	 */
	public File(String p_s_fullfilename, java.nio.charset.Charset p_o_charset, String p_s_lineBreak) throws java.io.FileNotFoundException, java.io.IOException, IllegalArgumentException {
		this(p_s_fullfilename, p_o_charset, false, p_s_lineBreak);
	}
	
	/**
	 * Constructor to create file instance
	 * 
	 * @param p_s_fullfilename					full path + filename to the file
	 * @param p_o_charset						which charset will be used accessing/modifying the file content
	 * @param p_b_new							flag to control if file should be created new or is already exisiting
	 * @param p_s_lineBreak						use alternative line break '\n', '\r\n', or '\r'
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				file cannot be created or cannot be read
	 * @throws IllegalArgumentException			invalid line break parameter
	 */
	public File(String p_s_fullfilename, java.nio.charset.Charset p_o_charset, boolean p_b_new, String p_s_lineBreak) throws java.io.FileNotFoundException, java.io.IOException, IllegalArgumentException {
		if ( (!p_s_lineBreak.contentEquals("\n")) && (!p_s_lineBreak.contentEquals("\r\n")) && (!p_s_lineBreak.contentEquals("\r")) ) {
			throw new IllegalArgumentException("Line break parameter must be '\n', '\r\n' or '\r'");
		}
		
		this.s_fullFilename = p_s_fullfilename;
		this.s_filename = "";
		this.l_fileLength = 0;
		this.b_ready = false;
		this.a_fileContent = new java.util.ArrayList<String>();
		this.o_charset = p_o_charset;
		this.s_lineBreak = p_s_lineBreak;
		
		/* separate filepath from filename */
		this.s_fullFilename = this.s_fullFilename.replace("\\", File.DIR);
		this.s_fullFilename = this.s_fullFilename.replace("/", File.DIR);
		
		String[] a_fullFilename = null;
		
		if (File.DIR.contentEquals("\\")) {
			a_fullFilename = this.s_fullFilename.split(File.DIR + File.DIR);
		} else {
			a_fullFilename = this.s_fullFilename.split(File.DIR);
		}
		
		this.s_filename = a_fullFilename[a_fullFilename.length - 1];
		
		/* create java.nio path object */
		this.o_path = java.nio.file.Paths.get(this.s_fullFilename);
		
		if (p_b_new) {
			try {
				/* check if file exists */
				if (java.nio.file.Files.exists(this.o_path)) {
					throw new java.io.IOException("File[" + this.s_fullFilename + "] does already exist");
				}
				
				/* check if file can be created */
				this.o_path = java.nio.file.Files.createFile(o_path);
				
				/* check if file creation was successful */
				if (!java.nio.file.Files.exists(this.o_path)) {
					throw new java.io.FileNotFoundException("File[" + this.s_fullFilename + "] cannot be created");
				}
				
				/* calculate filelength */
				this.l_fileLength = java.nio.file.Files.size(this.o_path);
			} catch (java.io.IOException o_exc) {
				throw new java.io.IOException("File[" + this.s_fullFilename + "] cannot be created: " + o_exc.getMessage());
			}
			
			/* set ready flag that you can use all other methods on the file */
			this.b_ready = true;
		} else {
			/* check if file exists */
			if (!java.nio.file.Files.exists(this.o_path)) {
				throw new java.io.FileNotFoundException("File[" + this.s_fullFilename + "] does not exist");
			}
			
			/* check if file can be read */
			try {
				/* calculate filelength */
				this.l_fileLength = java.nio.file.Files.size(this.o_path);
				
				/* if filelength > 0, read content of the file */
				if (this.l_fileLength > 0) {
					this.a_fileContent = java.nio.file.Files.readAllLines(this.o_path, this.o_charset);
				}
			} catch (java.nio.charset.MalformedInputException o_exc) {
				throw new java.io.IOException("Cannot read file[" + this.s_fullFilename + "] with charset [" + this.o_charset + "]: " + o_exc.getMessage());
			} catch (java.io.IOException o_exc) {
				throw new java.io.IOException("File[" + this.s_fullFilename + "] read access not possible: " + o_exc.getMessage());
			}
			
			/* if last element is empty, delete it */
			if ( (this.l_fileLength > 0) && (net.forestany.forestj.lib.Helper.isStringEmpty(this.a_fileContent.get(this.a_fileContent.size() - 1))) ) {
				this.a_fileContent.remove(this.a_fileContent.size() - 1);
			}
			
			/* set ready flag that you can use all other methods on the file */
			this.b_ready = true;
		}
	}
		
	/**
	 * Read one line of open file instance
	 * 
	 * @param p_i_line					line number
	 * @return							line as String
	 * @throws IllegalStateException	if file is not open or readable
	 * @throws IllegalArgumentException invalid line number parameter
	 * @see								String
	 */
	public String readLine(int p_i_line) throws IllegalStateException, IllegalArgumentException {
		if (!this.b_ready) {
			throw new IllegalStateException("File[" + this.s_filename + "] cannot be used, not ready for reading");
		}
		
		/* nothing to read in file */
		if ( (this.l_fileLength == 0) || (!java.nio.file.Files.exists(this.o_path)) ) {
			return "";
		}
		
		/* line number must be positive */
		if (!(p_i_line - 1 >= 0)) {
			throw new IllegalArgumentException("Line number[" + p_i_line + "] must be greater than 0");
		}
		
		/* if line does not exist */
		if (p_i_line - 1 >= this.a_fileContent.size()) {
			throw new IllegalArgumentException("Invalid line[" + p_i_line + "]; line does not exist");
		}
		
		/* return line in file content */
		return this.a_fileContent.get(p_i_line - 1);
	}
	
	/**
	 * Insert new line of open file instance at the end of file
	 * 
	 * @param p_s_value					String line which will be appended
	 * @throws IllegalStateException	if file is not open or writable
	 * @throws java.io.IOException		error with write access
	 * @see								String
	 */
	public void appendLine(String p_s_value) throws IllegalStateException, java.io.IOException {
		if (!this.b_ready) {
			throw new IllegalStateException("File[" + this.s_filename + "] cannot be used, not ready for writing");
		}
		
		/* insert new line at end of file */
		this.a_fileContent.add(p_s_value);
		
		p_s_value += this.s_lineBreak;
		
		/* write file content */
		try {
			java.nio.file.Files.write(this.o_path, p_s_value.getBytes(this.o_charset), java.nio.file.StandardOpenOption.APPEND);
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("File[" + this.s_filename + "] write access not possible: " + o_exc.getMessage());
		}	
	}
	
	/**
	 * Insert new line at line position of open file instance
	 * 
	 * @param p_s_value							String line which will be written at line position
	 * @param p_i_line							line number where the new line will be written
	 * @throws IllegalStateException			if file is not open or writable
	 * @throws IllegalArgumentException			invalid line number parameter
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				error with read or/and write access to file instance
	 * @see										String
	 */
	public void writeLine(String p_s_value, int p_i_line) throws IllegalStateException, IllegalArgumentException, java.io.FileNotFoundException, java.io.IOException {
		if (!this.b_ready) {
			throw new IllegalStateException("File[" + this.s_filename + "] cannot be used, not ready for writing");
		}
		
		if ((p_i_line - 1) == 0) {
			this.a_fileContent.add((p_i_line - 1), p_s_value);
		} else {
			/* line number must be positive */
			if (!(p_i_line - 1 >= 0)) {
				throw new IllegalArgumentException("Line number[" + p_i_line + "] must be greater 0");
			}
			
			/* if line does not exist */
			if (p_i_line > this.a_fileContent.size()) {
				throw new IllegalArgumentException("Line in file[" + this.s_filename + "] does not exist");
			} else {
				/* insert new line at line position */
				this.a_fileContent.add((p_i_line - 1), p_s_value);
			}
		}
		
		/* update file content */
		this.setFileContent();
	}
	
	/**
	 * Replace line at line position of open file instance
	 * 
	 * @param p_s_value							String line which will replace line at line position
	 * @param p_i_line							line number where line will be replaced
	 * @throws IllegalStateException			if file is not open or writable
	 * @throws IllegalArgumentException			invalid line number parameter
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				error with read or/and write access to file instance
	 * @see										String
	 */
	public void replaceLine(String p_s_value, int p_i_line) throws IllegalStateException, IllegalArgumentException, java.io.FileNotFoundException, java.io.IOException {
		if (!this.b_ready) {
			throw new IllegalStateException("File[" + this.s_filename + "] cannot be used, not ready for writing");
		}
		
		/* line number must be positive */
		if (!(p_i_line - 1 >= 0)) {
			throw new IllegalArgumentException("Line number[" + p_i_line + "] must be greater 0");
		}
		
		/* if line does not exist */
		if (p_i_line > this.a_fileContent.size()) {
			throw new IllegalArgumentException("Invalid line[" + p_i_line + "]");
		}
		
		/* replace line */
		this.a_fileContent.set((p_i_line - 1), p_s_value);
		
		/* update file content */
		this.setFileContent();
	}
	
	/**
	 * Delete line at line position of open file instance
	 * 
	 * @param p_i_line							line number where the line will be deleted
	 * @throws IllegalStateException			if file is not open or writable
	 * @throws IllegalArgumentException			invalid line number parameter
	 * @throws java.io.IOException				error with read or/and write access to file instance
	 * @see										String
	 */
	public void deleteLine(int p_i_line) throws IllegalStateException, IllegalArgumentException, java.io.IOException {
		if (!this.b_ready) {
			throw new IllegalStateException("File[" + this.s_filename + "] cannot be used, not ready for writing");
		}
		
		/* line number must be positive */
		if (!(p_i_line - 1 >= 0)) {
			throw new IllegalArgumentException("Line number[" + p_i_line + "] must be greater 0");
		}
		
		/* if line does not exist */
		if (p_i_line > this.a_fileContent.size()) {
			throw new IllegalArgumentException("Invalid line[" + p_i_line + "]");
		}
		
		/* delete line */
		this.a_fileContent.remove((p_i_line - 1));
		
		/* update file content */
		if (this.a_fileContent.size() <= 0) {
			this.truncateContent();
		} else {
			this.setFileContent();
		}
	}
	
	/**
	 * Replace whole file content with string parameter of open file instance
	 * 
	 * @param p_s_value					string which will replace file content, can contain line separators
	 * @throws IllegalStateException	if file is not open or writable
	 * @throws java.io.IOException		error with read or/and write access to file instance
	 * @see								String
	 */
	public void replaceContent(String p_s_value) throws IllegalStateException, java.io.IOException {
		if (!this.b_ready) {
			throw new IllegalStateException("File[" + this.s_filename + "] cannot be used, not ready for writing");
		}
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			/* generate file content array with the new file content string and CRLF as separate sign */
			this.a_fileContent.clear();
			
			for(String s_line : p_s_value.split(this.s_lineBreak)) {
				this.a_fileContent.add(s_line);
			}
			
			/* update file content */
			this.setFileContent();
		} else {
			this.truncateContent();
		}
	}

	/**
	 * Replace whole file content with byte array parameter of open file instance
	 * 
	 * @param p_a_value					byte array which will replace file content
	 * @throws IllegalArgumentException	byte array parameter is null or empty
	 * @throws IllegalStateException	if file is not open or writable
	 * @throws java.io.IOException		error with read or/and write access to file instance
	 * @see								String
	 */
	public void replaceContent(byte[] p_a_value) throws IllegalArgumentException, IllegalStateException, java.io.IOException {
		if (!this.b_ready) {
			throw new IllegalStateException("File[" + this.s_filename + "] cannot be used, not ready for writing");
		}
		
		/* check if file exists */
		if (!java.nio.file.Files.exists(this.o_path)) {
			throw new java.io.FileNotFoundException("File[" + this.s_filename + "] does not exist");
		}
		
		/* check byte array parameter */
		if ((p_a_value == null) || (p_a_value.length < 1) ) {
			throw new IllegalArgumentException("Byte array parameter is null or empty");
		}
		
		/* write file content */
		try {
			java.nio.file.Files.write(this.o_path, p_a_value, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("File[" + this.s_filename + "] write access not possible: " + o_exc.getMessage());
		}	
		
		/* check if file can be read */
		try {
			/* calculate filelength */
			this.l_fileLength = java.nio.file.Files.size(this.o_path);
			
			/* if filelength > 0, read content of the file */
			if (this.l_fileLength > 0) {
				this.a_fileContent = java.nio.file.Files.readAllLines(this.o_path, this.o_charset);
			}
		} catch (java.nio.charset.MalformedInputException o_exc) {
			throw new java.io.IOException("Cannot read file[" + this.s_fullFilename + "] with charset [" + this.o_charset + "]: " + o_exc.getMessage());
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("File[" + this.s_fullFilename + "] read access not possible: " + o_exc.getMessage());
		}
		
		/* if last element is empty, delete it */
		if ( (this.l_fileLength > 0) && (net.forestany.forestj.lib.Helper.isStringEmpty(this.a_fileContent.get(this.a_fileContent.size() - 1))) ) {
			this.a_fileContent.remove(this.a_fileContent.size() - 1);
		}
	}
	
	/**
	 * Truncate file
	 * 
	 * @throws IllegalStateException	if file is not open or writable
	 * @throws java.io.IOException		error with read or/and write access to file instance
	 */
	public void truncateContent() throws IllegalStateException, java.io.IOException {
		if (!this.b_ready) {
			throw new IllegalStateException("File[" + this.s_filename + "] cannot be used, not ready for writing");
		}
		
		/* truncate file content */
		try {
			java.nio.channels.FileChannel.open(this.o_path, java.nio.file.StandardOpenOption.WRITE, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING).close();
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("File[" + this.s_filename + "] cannot be truncated: " + o_exc.getMessage());
		}	
		
		this.a_fileContent.clear();
		this.l_fileLength = 0;
	}
	
	/**
	 * Method to store lines from memory into file
	 * 
	 * @throws IllegalStateException			if file is not open or writable
	 * @throws java.io.FileNotFoundException	file does not exist
	 * @throws java.io.IOException				error with read or/and write access to file instance
	 */
	private void setFileContent() throws IllegalStateException, java.io.FileNotFoundException, java.io.IOException {
		if (!this.b_ready) {
			throw new IllegalStateException("File[" + this.s_filename + "] cannot be used, not ready for writing");
		}
		
		/* check if file exists */
		if (!java.nio.file.Files.exists(this.o_path)) {
			throw new java.io.FileNotFoundException("File[" + this.s_filename + "] does not exist");
		}
		
		StringBuilder o_stringBuilder = new StringBuilder();
		
		/* check if file can be read */
		try {
			/* calculate filelength */
			this.l_fileLength = java.nio.file.Files.size(this.o_path);
		
			/* if filelength > 0, read content of the file */
			if (this.l_fileLength > 0) {
				java.nio.file.Files.readAllLines(this.o_path, this.o_charset);
			}
			
			for (int i = 0; i < this.a_fileContent.size(); i++) {
				o_stringBuilder.append(this.a_fileContent.get(i) + this.s_lineBreak);
			}
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("File[" + this.s_filename + "] read access not possible: " + o_exc.getMessage());
		}
		
		/* write file content */
		try {
			java.nio.file.Files.write(this.o_path, o_stringBuilder.toString().getBytes(this.o_charset), java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("File[" + this.s_filename + "] write access not possible: " + o_exc.getMessage());
		}	
		
		/* calculate filelength */
		this.l_fileLength = java.nio.file.Files.size(this.o_path);
	}
	
	/**
	 * Rename file with new filename
	 * 
	 * @param p_s_newName					new filename
	 * @throws IllegalStateException		if file is not open or writable
	 * @throws IllegalArgumentException		invalid new file name
	 * @throws java.io.IOException			error with renaming file
	 * @see									String
	 */
	public void renameFile(String p_s_newName) throws IllegalStateException, IllegalArgumentException, java.io.IOException {
		if (!this.b_ready) {
			throw new IllegalStateException("File[" + this.s_filename + "] cannot be used, not ready for writing");
		}
		
		/* handle directory separator */
		p_s_newName = p_s_newName.replace("\\", File.DIR);
		p_s_newName = p_s_newName.replace("/", File.DIR);
		
		/* rename file */
		try {
			java.nio.file.Path s_directory = this.o_path.getParent();        
			java.nio.file.Path s_newFilename = this.o_path.getFileSystem().getPath(p_s_newName);
			java.nio.file.Path s_target = (s_directory == null) ? s_newFilename : s_directory.resolve(s_newFilename);        
			this.o_path = java.nio.file.Files.move(this.o_path, s_target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("File[" + this.s_filename + "] cannot be renamed: " + o_exc.getMessage());
		}	
	}
	
	/**
	 * Create hash string of current file instance
	 * 
	 * @param p_s_algorithm									hash-algorithm: 'SHA-256', 'SHA-384', 'SHA-512'
	 * @return												hash string
	 * @throws IllegalArgumentException						if hash-algorithm is not 'SHA-256', 'SHA-384' or 'SHA-512'
	 * @throws java.security.NoSuchAlgorithmException 		wrong algorithm to hash byte array 
	 */
	public String hash(String p_s_algorithm) throws IllegalArgumentException, java.security.NoSuchAlgorithmException {
		return net.forestany.forestj.lib.Helper.hashByteArray(p_s_algorithm, this.getFileContent().getBytes());
	}
	
	
	/**
	 * Get directory of current java program
	 * 
	 * @return		directory path as String
	 * @see			String
	 */
 	public static String getCurrentDirectory() {
		return java.nio.file.Paths.get("").toAbsolutePath().toString();
	}
	
	/**
	 * Check if a file exists
	 * 
	 * @param p_s_file	full path to file
	 * @return			true - file does exist, false - file does not exist
	 */
	public static boolean exists(String p_s_file) {
		return java.nio.file.Files.exists(java.nio.file.Paths.get(p_s_file));
	}
	
	/**
	 * Check if a directory exists, if directory path it must end with directory separator
	 * 
	 * @param p_s_path	full path to directory
	 * @return			true - directory does exist, false - directory does not exist
	 */
	public static boolean folderExists(String p_s_path) {
		p_s_path = p_s_path.substring(0, p_s_path.lastIndexOf(File.DIR));
		return File.isDirectory(p_s_path);
	}
	
	/**
	 * Check if a file has an extension
	 * 
	 * @param p_s_file	full path to file
	 * @return			true - file has an extension, false - file has not an extension
	 */
	public static boolean hasFileExtension(String p_s_file) {
		p_s_file = p_s_file.substring(p_s_file.lastIndexOf(File.DIR) + 1);
		return p_s_file.contains(".");
	}
	
	/**
	 * Check if full path is a file
	 * 
	 * @param p_s_path		full path
	 * @return				true - full path is a file, false - full path is not a file
	 */
	public static boolean isFile(String p_s_path) {
		return java.nio.file.Paths.get(p_s_path).toFile().isFile();
	}
	
	/**
	 * Check if full path is a directory
	 * 
	 * @param p_s_path		full path
	 * @return				true - full path is a directory, false - full path is not a directory
	 */
	public static boolean isDirectory(String p_s_path) {
		return java.nio.file.Paths.get(p_s_path).toFile().isDirectory();
	}
	
	/**
	 * Check if current path is a sub directory of other path parameter
	 * 
	 * @param p_s_currentPath	current path
	 * @param p_s_otherPath		other path
	 * @return					true - path parameter is a sub directory of other path , false - path parameter is not a sub directory of other path
	 */
	public static boolean isSubDirectory(String p_s_currentPath, String p_s_otherPath) {
		return java.nio.file.Paths.get(p_s_currentPath).toAbsolutePath().startsWith( java.nio.file.Paths.get(p_s_otherPath).toAbsolutePath() );
	}
	
	/**
	 * Get all bytes from a file
	 * 
	 * @param p_s_file					full path
	 * @return							byte array or null if path is invalid file path
	 * @throws java.io.IOException 		could not read all bytes from file
	 */
	public static byte[] readAllBytes(String p_s_file) throws java.io.IOException {
		java.io.File o_foo = java.nio.file.Paths.get(p_s_file).toFile();
		
		if (!o_foo.isFile()) {
			return null;
		} else {
			return java.nio.file.Files.readAllBytes( java.nio.file.Paths.get(p_s_file) );
		}
	}
	
	/**
	 * Get all bytes from a file using a decoding charset
	 * 
	 * @param p_s_file					full path
	 * @param p_o_charset				the charset to use for decoding
	 * @return							byte array or null if path is invalid file path
	 * @throws java.io.IOException 		could not read all bytes from file
	 */
	public static byte[] readAllBytes(String p_s_file, java.nio.charset.Charset p_o_charset) throws java.io.IOException {
		java.io.File o_foo = java.nio.file.Paths.get(p_s_file).toFile();
		
		if (!o_foo.isFile()) {
			return null;
		} else {
			return java.nio.file.Files.readString( java.nio.file.Paths.get(p_s_file), p_o_charset ).getBytes();
		}
	}
	
	/**
	 * Get file length of full path file as long value
	 * 
	 * @param p_s_file	full path to file
	 * @return			file length of file
	 * @see				Long
	 */
	public static long fileLength(String p_s_file) {
		try {
			return java.nio.file.Files.size(java.nio.file.Paths.get(p_s_file));
		} catch (java.io.IOException o_exc) {
			return 0;
		}
	}
	
	/**
	 * Get last modified value of a file
	 * 
	 * @param p_s_path		full path
	 * @return				java.time.LocalDateTime or null if path is invalid file path
	 */
	public static java.time.LocalDateTime lastModified(String p_s_path) {
		java.io.File o_foo = java.nio.file.Paths.get(p_s_path).toFile();
		
		if (!o_foo.isFile()) {
			return null;
		} else {
			return java.time.LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli( o_foo.lastModified() ), java.util.TimeZone.getDefault().toZoneId());
		}
	}
	
	/**
	 * Creates a hash string from file content
	 * 
	 * @param p_s_file										full path to file
	 * @param p_s_algorithm									hash-algorithm: 'SHA-256', 'SHA-384', 'SHA-512'
	 * @return												hash string or null if file does not exist or is not a file
	 * @throws IllegalArgumentException						if hash-algorithm is not 'SHA-256', 'SHA-384' or 'SHA-512'
	 * @throws java.security.NoSuchAlgorithmException 		wrong algorithm to hash byte array 
	 * @throws java.io.IOException 							could not read all bytes from file
	 */
	public static String hashFile(String p_s_file, String p_s_algorithm) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, java.io.IOException {
		java.nio.file.Path o_path = java.nio.file.Paths.get(p_s_file);
		
		if ( (java.nio.file.Files.exists(o_path)) && (o_path.toFile().isFile()) ) {
			return net.forestany.forestj.lib.Helper.hashByteArray(p_s_algorithm, java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(p_s_file)));
		} else {
			return null;
		}
	}
	
	/**
	 * Creates a hash string from directory file contents
	 * 
	 * @param p_s_path											full path to directory
	 * @param p_s_algortihm										hash-algorithm: 'SHA-256', 'SHA-384', 'SHA-512'
	 * @return													hash string of directory file contents
	 * @throws IllegalArgumentException 						if path leads not to a directory or hash-algorithm is not 'SHA-256', 'SHA-384' or 'SHA-512'
	 * @throws java.security.NoSuchAlgorithmException 			wrong algorithm to hash byte array 
	 * @throws java.io.IOException								could not read all bytes from a file
	 */
	public static String hashDirectory(String p_s_path, String p_s_algortihm) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, java.io.IOException {
		return File.hashDirectory(p_s_path, p_s_algortihm, false);
	}
	
	/**
	 * Creates a hash string from directory and its sub directories(optional) file contents
	 * 
	 * @param p_s_path											full path to directory
	 * @param p_s_algorithm										hash-algorithm: 'SHA-256', 'SHA-384', 'SHA-512'
	 * @param p_b_recursive										include sub directories
	 * @return													hash string of directory and its sub directories(optional) file contents
	 * @throws IllegalArgumentException 						if path leads not to a directory or hash-algorithm is not 'SHA-256', 'SHA-384' or 'SHA-512'
	 * @throws java.security.NoSuchAlgorithmException 			wrong algorithm to hash byte array 
	 * @throws java.io.IOException								could not read all bytes from a file
	 */
	public static String hashDirectory(String p_s_path, String p_s_algorithm, boolean p_b_recursive) throws IllegalArgumentException, java.security.NoSuchAlgorithmException, java.io.IOException {
		/* check if full path leads to a directory */
		if (!File.isDirectory(p_s_path)) {
			throw new IllegalArgumentException("Path '" + p_s_path + "' is not a directory");
		}
		
		/* check for a valid algorithm parameter, before we read all file contents */
		if (!java.util.Arrays.asList("SHA-256", "SHA-384", "SHA-512").contains(p_s_algorithm)) {
			throw new IllegalArgumentException("Invalid algorithm '" + p_s_algorithm + "', please use a valid algorithm['" + String.join("', '", java.util.Arrays.asList("SHA-256", "SHA-384", "SHA-512")) + "']");
		}
		
		/* get list of all file elements, sub directories are optional with p_b_recursive */
		java.util.List<ListingElement> a_files = File.listDirectory(p_s_path, p_b_recursive);
		java.util.List<String> a_filePaths = new java.util.ArrayList<String>();
		
		int i_size = 0;
		
		/* iterate each file element */
		for (ListingElement o_file : a_files) {
			/* skip directory elements */
			if (o_file.getIsDirectory()) {
														net.forestany.forestj.lib.Global.ilogFinest("skip directory '" + o_file.getFullName() + "'");
				continue;
			}
			
			/* sum up all file sizes and add file element full paths */
			i_size += o_file.getSize();
			a_filePaths.add(o_file.getFullName());
													net.forestany.forestj.lib.Global.ilogFinest("added file '" + o_file.getFullName() + "'");
		}
		
		/* overall byte array for all file contents */
		byte[] by_array = new byte[i_size];
		int i = 0;
		
		/* sort file element full paths, so it is always the correct order when hashing byte array */
		//TODO net.forestany.forestj.lib.Sorts.quickSort(a_filePaths);
		
		/* iterate each file element full path */
		for (String s_file : a_filePaths) {
			/* read all bytes of file element */
			byte[] a_temp = java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(s_file));
			
			/* add bytes to overall byte array */
			for (byte by_value : a_temp) {
				by_array[i++] = by_value;
			}
		}
		
		/* returned hashed overall byte array */
		return net.forestany.forestj.lib.Helper.hashByteArray(p_s_algorithm, by_array);
	}
	
	
	/**
	 * Get list of windows file permissions as several acl entries
	 * 
	 * @param p_s_path								full path to file/directory
	 * @return										list of windows file permissions as several acl entries or null if they cannot be retrieved
	 * @throws java.nio.file.InvalidPathException	path does not exist
	 * @see java.util.List
	 * @see java.nio.file.attribute.AclEntry
	 */
 	public static java.util.List<java.nio.file.attribute.AclEntry> getWindowsFilePermission(String p_s_path) throws java.nio.file.InvalidPathException {
		try {
			java.nio.file.attribute.AclFileAttributeView a_aclAttributes = java.nio.file.Files.getFileAttributeView(java.nio.file.Paths.get(p_s_path), java.nio.file.attribute.AclFileAttributeView.class);
			return a_aclAttributes.getAcl();
		} catch (java.io.IOException o_exc) {
			return null;
		}
	}
	
 	/**
 	 * Set windows file permission as acl entry to file
 	 * 
 	 * @param p_s_path								full path to file/directory
 	 * @param p_a_aclPermissions					acl entry permission
 	 * 												AclEntryPermission.ADD_FILE
	 * 												AclEntryPermission.ADD_SUBDIRECTORY
	 * 												AclEntryPermission.APPEND_DATA
	 * 												AclEntryPermission.DELETE
	 * 												AclEntryPermission.DELETE_CHILD
	 * 												AclEntryPermission.EXECUTE
	 * 												AclEntryPermission.LIST_DIRECTORY
	 * 												AclEntryPermission.READ_ACL
	 * 												AclEntryPermission.READ_ATTRIBUTES
	 * 												AclEntryPermission.READ_DATA
	 * 												AclEntryPermission.READ_NAMED_ATTRS
	 *												AclEntryPermission.WRITE_ACL
	 * 												AclEntryPermission.WRITE_ATTRIBUTES
	 * 												AclEntryPermission.WRITE_DATA
	 * 												AclEntryPermission.WRITE_NAMED_ATTRS
	 * 												AclEntryPermission.WRITE_OWNER 
 	 * @return										true - permission could be set, false - error while setting permission
 	 * @throws java.nio.file.InvalidPathException	path does not exist
 	 * @see java.util.Set
	 * @see java.nio.file.attribute.AclEntryPermission
 	 */
	public static boolean setWindowsFilePermission(String p_s_path, java.util.Set<java.nio.file.attribute.AclEntryPermission> p_a_aclPermissions) throws java.nio.file.InvalidPathException {
		try {
			java.nio.file.attribute.AclFileAttributeView a_aclAttributes = java.nio.file.Files.getFileAttributeView(java.nio.file.Paths.get(p_s_path), java.nio.file.attribute.AclFileAttributeView.class);
			java.nio.file.attribute.AclEntry.Builder o_builder = java.nio.file.attribute.AclEntry.newBuilder();       
		    o_builder.setPermissions(p_a_aclPermissions);
		    o_builder.setType(java.nio.file.attribute.AclEntryType.ALLOW);
		    a_aclAttributes.setAcl(java.util.Collections.singletonList(o_builder.build()));
		} catch (java.io.IOException o_exc) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get posix file permissions of a file, do not follow symbolic links
	 * 
	 * @param p_s_path			full path to file/directory
	 * @return					Set of posix file permissions
	 * @see java.util.Set
	 * @see java.nio.file.attribute.PosixFilePermission
	 */
	public static java.util.Set<java.nio.file.attribute.PosixFilePermission> getPosixFilePermission(String p_s_path) {
		java.util.Set<java.nio.file.attribute.PosixFilePermission> a_posixFilePermissions = null;
		
		try {
			a_posixFilePermissions = java.nio.file.Files.getPosixFilePermissions(java.nio.file.Paths.get(p_s_path), java.nio.file.LinkOption.NOFOLLOW_LINKS);
		} catch (java.io.IOException o_exc) {
			/* nothing to do */
		}
		
		return a_posixFilePermissions;
	}
	
	/**
 	 * Set posix file permission(s) to file, unix/linux
 	 * 
 	 * @param p_s_path								full path to file/directory
 	 * @param p_a_posixPermissions					set of posix file permissions
 	 * PosixFilePermission.OWNER_READ
	 * PosixFilePermission.OWNER_WRITE
	 * PosixFilePermission.OWNER_EXECUTE
	 * PosixFilePermission.GROUP_READ
	 * PosixFilePermission.GROUP_WRITE
	 * PosixFilePermission.GROUP_EXECUTE
	 * PosixFilePermission.OTHERS_READ
	 * PosixFilePermission.OTHERS_WRITE
	 * PosixFilePermission.OTHERS_EXECUTE
 	 * @return										true - permission could be set, false - error while setting permission
 	 * @throws java.nio.file.InvalidPathException	path does not exist
 	 */
	public static boolean setPosixFilePermission(String p_s_path, java.util.Set<java.nio.file.attribute.PosixFilePermission> p_a_posixPermissions) throws java.nio.file.InvalidPathException {
		try {
			java.nio.file.Files.setPosixFilePermissions(java.nio.file.Paths.get(p_s_path), p_a_posixPermissions);
		} catch (java.io.IOException o_exc) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get owner of a file/directory
	 * 
	 * @param p_s_path								full path to file/directory
	 * @return										owner as string value
	 * @throws java.nio.file.InvalidPathException	path does not exist
	 */
	public static String getOwner(String p_s_path) throws java.nio.file.InvalidPathException {
		String s_owner = null;
		
		try {
			s_owner = java.nio.file.Files.getOwner(java.nio.file.Paths.get(p_s_path), java.nio.file.LinkOption.NOFOLLOW_LINKS).getName();
		} catch (java.io.IOException o_exc) {
			/* nothing to do */
		}
		
		return s_owner;
	}
	
	/**
	 * Set owner of a file/directory
	 * 
	 * @param p_s_path								full path to file/directory
	 * @param p_s_newOwner							new owner as string value
	 * @return										true - owner changed, false - owner could not be changed
	 * @throws java.nio.file.InvalidPathException	path does not exist
	 */
	public static boolean setOwner(String p_s_path, String p_s_newOwner) throws java.nio.file.InvalidPathException {
		try {
			java.nio.file.Path o_path = java.nio.file.Paths.get(p_s_path);
			java.nio.file.Files.setOwner(o_path, o_path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName(p_s_newOwner));
			
		} catch (java.io.IOException o_exc) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Get posix group information
	 * 
	 * @param p_s_path								full path to file/directory
	 * @return										posix group as string value
	 * @throws java.nio.file.InvalidPathException	path does not exist
	 */
	public static String getPosixGroup(String p_s_path) throws java.nio.file.InvalidPathException {
		String s_group = null;
		
		try {
			s_group = java.nio.file.Files.readAttributes(java.nio.file.Paths.get(p_s_path), java.nio.file.attribute.PosixFileAttributes.class, java.nio.file.LinkOption.NOFOLLOW_LINKS).group().getName();
		} catch (java.io.IOException o_exc) {
			/* nothing to do */
		}
		
		return s_group;
	}
	
	/**
	 * Set posix group to an element
	 * 
	 * @param p_s_path								full path to file/directory
	 * @param p_s_newGroup							new posix group as string value
	 * @return										true - owner changed, false - owner could not be changed
	 * @throws java.nio.file.InvalidPathException	path does not exist
	 */
	public static boolean setPosixGroup(String p_s_path, String p_s_newGroup) throws java.nio.file.InvalidPathException {
		try {
			java.nio.file.Path o_path = java.nio.file.Paths.get(p_s_path);
			java.nio.file.Files.getFileAttributeView(o_path, java.nio.file.attribute.PosixFileAttributeView.class, java.nio.file.LinkOption.NOFOLLOW_LINKS).setGroup(o_path.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByGroupName(p_s_newGroup));
		} catch (java.io.IOException o_exc) {
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Copy file from source location to destination location incl. filename
	 * 
	 * @param p_s_source							full path of source file
	 * @param p_s_destination						full path of destination file
	 * @throws java.nio.file.InvalidPathException	file does not exist
	 * @throws java.io.IOException					copy process could not be completed successfully
	 */
	public static void copyFile(String p_s_source, String p_s_destination) throws java.nio.file.InvalidPathException, java.io.IOException {
		/* create java.nio path objects */
		java.nio.file.Path o_source = java.nio.file.Paths.get(p_s_source);
		java.nio.file.Path o_destination = java.nio.file.Paths.get(p_s_destination);
		
		/* check if source file exists */
		if (!java.nio.file.Files.exists(o_source)) {
			throw new IllegalArgumentException("File[" + p_s_source + "] does not exist");
		}
		
		/* copy file */
		try {
			java.nio.file.Files.copy(o_source, o_destination, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("File[" + p_s_source + "] cannot be copied: " + o_exc.getMessage());
		}	
	}
	
	/**
	 * Move file from source location to destination location incl. filename
	 * 
	 * @param p_s_source							full path of source file
	 * @param p_s_destination						full path of destination file
	 * @throws java.nio.file.InvalidPathException	file does not exist
	 * @throws java.io.IOException					move process could not be completed successfully
	 */
	public static void moveFile(String p_s_source, String p_s_destination) throws java.nio.file.InvalidPathException, java.io.IOException {
		/* create java.nio path objects */
		java.nio.file.Path o_source = java.nio.file.Paths.get(p_s_source);
		java.nio.file.Path o_destination = java.nio.file.Paths.get(p_s_destination);
		
		/* check if source file exists */
		if (!java.nio.file.Files.exists(o_source)) {
			throw new IllegalArgumentException("File[" + p_s_source + "] does not exist");
		}
		
		/* move file */
		try {
			java.nio.file.Files.move(o_source, o_destination, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("File[" + p_s_source + "] cannot be moved: " + o_exc.getMessage());
		}	
	}
	
	/**
	 * Delete file
	 * 
	 * @param p_s_source							full path of source file
	 * @throws java.nio.file.InvalidPathException	file does not exist
	 * @throws java.io.IOException					delete process could not be completed successfully
	 */
	public static void deleteFile(String p_s_source) throws java.nio.file.InvalidPathException, java.io.IOException {
		/* create java.nio path objects */
		java.nio.file.Path o_source = java.nio.file.Paths.get(p_s_source);
		
		/* check if source file exists */
		if (!java.nio.file.Files.exists(o_source)) {
			throw new IllegalArgumentException("File[" + p_s_source + "] does not exist");
		}
		
		/* delete file */
		try {
			if (!(java.nio.file.Files.deleteIfExists(o_source))) {
				throw new java.io.IOException("File[" + p_s_source + "] could not be deleted");
			}
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("File[" + p_s_source + "] could not be deleted: " + o_exc.getMessage());
		}	
	}
	
	
	/**
	 * List directory elements
	 * 
	 * @param p_s_path								full path to file/directory
	 * @return										list of ListingElement objects
	 * @throws java.nio.file.InvalidPathException	path does not exist
	 * @throws java.io.IOException					directory could not be listed or issue with reading file/directory attributes
	 * @see net.forestany.forestj.lib.io.ListingElement
	 */
	public static java.util.List<ListingElement> listDirectory(String p_s_path) throws java.nio.file.InvalidPathException, java.io.IOException {
		return File.listDirectory(p_s_path, false);
	}
	
	/**
	 * List directory elements
	 * 
	 * @param p_s_path								full path to file/directory
	 * @param p_b_recursive							include all sub directories
	 * @return										list of ListingElement objects
	 * @throws java.nio.file.InvalidPathException	path does not exist
	 * @throws java.io.IOException					directory could not be listed or issue with reading file/directory attributes
	 * @see net.forestany.forestj.lib.io.ListingElement
	 */
	public static java.util.List<ListingElement> listDirectory(String p_s_path, boolean p_b_recursive) throws java.nio.file.InvalidPathException, java.io.IOException {
		java.util.List<ListingElement> a_listing = new java.util.ArrayList<ListingElement>();
		
		/* create java.nio path objects */
		java.nio.file.Path o_path = java.nio.file.Paths.get(p_s_path);
		
		/* check if parameter is not a directory */
		if (!java.nio.file.Files.isDirectory(o_path)) {
			/* check if file exists */
			if (!java.nio.file.Files.exists(o_path)) {
				throw new IllegalArgumentException("File[" + p_s_path + "] does not exist");
			}
			
			/* retrieve file attributes */
			java.nio.file.attribute.BasicFileAttributes o_basicFileAttributes = java.nio.file.Files.readAttributes(o_path, java.nio.file.attribute.BasicFileAttributes.class);
	        			
			/* add element as a new listing object to our listing */
			a_listing.add(
	    		new ListingElement(
	    			o_path.toFile().getName().toString(),
	    			o_path.toFile().getAbsoluteFile().toString(),
	    			o_path.toFile().isDirectory(),
	    			o_basicFileAttributes.size(),
	    			java.time.LocalDateTime.ofInstant(o_basicFileAttributes.creationTime().toInstant(), java.time.ZoneId.systemDefault()),
	    			java.time.LocalDateTime.ofInstant(o_basicFileAttributes.lastAccessTime().toInstant(), java.time.ZoneId.systemDefault()),
	    			java.time.LocalDateTime.ofInstant(o_basicFileAttributes.lastModifiedTime().toInstant(), java.time.ZoneId.systemDefault())
	    		)
		    );
		} else {
			/* check if directory exists */
			if (!java.nio.file.Files.exists(o_path)) {
				throw new IllegalArgumentException("Directory[" + p_s_path + "] does not exist");
			}
			
			/* list directory */
			try (java.util.stream.Stream<java.nio.file.Path> o_stream = java.nio.file.Files.walk(o_path, 1)) {
			    /* iterate all elements in directory */
				o_stream.forEach(o_fileWalk -> {
					/* not iterate a file walk element with the same file name as parameter, preventing endless loop in recursion */
			    	if (!o_path.getFileName().equals(o_fileWalk.getFileName())) {
			    		/* retrieve file attributes */
						try {
							java.nio.file.attribute.BasicFileAttributes o_basicFileAttributes = java.nio.file.Files.readAttributes(o_fileWalk, java.nio.file.attribute.BasicFileAttributes.class);
							
							/* add every element as a new listing object to our listing with file attributes */
							a_listing.add(
					    		new ListingElement(
					    			o_fileWalk.toFile().getName().toString(),
					    			o_fileWalk.toFile().getAbsoluteFile().toString(),
					    			o_fileWalk.toFile().isDirectory(),
					    			o_basicFileAttributes.size(),
					    			java.time.LocalDateTime.ofInstant(o_basicFileAttributes.creationTime().toInstant(), java.time.ZoneId.systemDefault()),
					    			java.time.LocalDateTime.ofInstant(o_basicFileAttributes.lastAccessTime().toInstant(), java.time.ZoneId.systemDefault()),
					    			java.time.LocalDateTime.ofInstant(o_basicFileAttributes.lastModifiedTime().toInstant(), java.time.ZoneId.systemDefault())
					    		)
						    );
						} catch (java.io.IOException o_exc) {
							/* add every element as a new listing object to our listing without any file attributes */
					    	a_listing.add(
					    		new ListingElement(
					    			o_fileWalk.toFile().getName().toString(),
					    			o_fileWalk.toFile().getAbsoluteFile().toString(),
					    			o_fileWalk.toFile().isDirectory()
					    		)
					    	);
						}
						
				    	/* if element is a directory and we want to scan the directory recursive, continue listing with recursion */
				    	if ( (o_fileWalk.toFile().isDirectory()) && (p_b_recursive) ) {
				    		try {
				    			/* iterate all elements in recursion to our listing */
								for (ListingElement o_listingObject : File.listDirectory(o_fileWalk.toFile().getAbsoluteFile().toString(), p_b_recursive)) {
									a_listing.add(o_listingObject);
								}
							} catch (Exception o_exc) {
								/* nothing to do */
							}
				    	}
			    	}
			    });
			} catch (java.io.IOException o_exc) {
				throw new java.io.IOException("Directory[" + p_s_path + "] could not be listed: " + o_exc.getMessage());
			}
		}
		
		return a_listing;
	}
	
	/**
	 * Create a directory
	 * 
	 * @param p_s_source					full path to target directory
	 * @throws IllegalArgumentException		invalid directory path or directory already exists
	 * @throws java.io.IOException			directory could not be created
	 */
	public static void createDirectory(String p_s_source) throws IllegalArgumentException, java.io.IOException {
		File.createDirectory(p_s_source, false);
	}
	
	/**
	 * Create a directory
	 * 
	 * @param p_s_source					full path to target directory
	 * @param p_b_autoCreate				true - create path to target directory if directories to this target are missing
	 * @throws IllegalArgumentException		invalid directory path or directory already exists
	 * @throws java.io.IOException			directory could not be created
	 */
	public static void createDirectory(String p_s_source, boolean p_b_autoCreate) throws IllegalArgumentException, java.io.IOException {
		/* create java.nio path objects */
		java.nio.file.Path o_source = java.nio.file.Paths.get(p_s_source);
		
		/* check if directory exists */
		if (java.nio.file.Files.exists(o_source)) {
			throw new IllegalArgumentException("Directory[" + p_s_source + "] does already exist");
		}
		
		/* create path to target directory if directories to this target are missing */
		if (p_b_autoCreate) {
			/* start with first directory */
			String s_foo = o_source.toAbsolutePath().toString().substring(0, o_source.toAbsolutePath().toString().indexOf(File.DIR)) + File.DIR;
			
			/* iterate each directory until target directory has been reached */
			for (int i = 0; i < (o_source.getNameCount() - 1); i++) {
				/* add next directory to target path */
				s_foo += o_source.subpath(i, (i + 1)) + File.DIR;
				java.nio.file.Path o_foo = java.nio.file.Paths.get(s_foo);
				
				/* if directory path does not exist */
				if (!java.nio.file.Files.exists(o_foo)) {
					try {
						/* create directory */
						java.nio.file.Files.createDirectory(o_foo);
																net.forestany.forestj.lib.Global.ilogFinest("auto create directory '" + o_foo + "'");
					} catch (java.io.IOException o_exc) {
						throw new java.io.IOException("Directory[" + o_foo + "] could not be created: " + o_exc.getMessage());
					}	
				}
			}
		}
		
		try {
			/* create directory */
			java.nio.file.Files.createDirectory(o_source);
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("Directory[" + p_s_source + "] could not be created: " + o_exc.getMessage());
		}	
	}
	
	/**
	 * Copy directory to a destination directory path, incl. sub directories with file contents
	 * 
	 * @param p_s_source					full path to source directory
	 * @param p_s_destination				full path to destination directory
	 * @throws IllegalArgumentException		directory does not exist
	 * @throws java.io.IOException			file or directory could not be copied or destination directory could not be created
	 */
	public static void copyDirectory(String p_s_source, String p_s_destination) throws IllegalArgumentException, java.io.IOException {
		/* create java.nio path objects */
		java.nio.file.Path o_source = java.nio.file.Paths.get(p_s_source);
		
		/* check if directory exists */
		if (!java.nio.file.Files.exists(o_source)) {
			throw new IllegalArgumentException("Directory" + p_s_source + "] does not exist");
		}
		
		/* create destination directory */
		File.createDirectory(p_s_destination);
		
		/* copy content of directory recursive */
		java.nio.file.Files.walk(o_source).forEach(object -> {
			try {
				/* exclude source directory */
				if (object != o_source) {
					/* current element is not a directory and parent directory equals source directory parameter */
					if ( (!object.toFile().isDirectory()) && (object.getParent().compareTo(o_source) == 0) ) {
						/* copy file */
						File.copyFile(object.toFile().getAbsolutePath(), p_s_destination + File.DIR + object.toFile().getName());
																net.forestany.forestj.lib.Global.ilogFinest("copied file '" + object.toFile().getAbsolutePath() + "'");
					} else if (object.getParent().compareTo(o_source) == 0) { /* parent directory equals source directory parameter */
						/* copy directory recursively */
						File.copyDirectory(object.toFile().getAbsolutePath(), p_s_destination + File.DIR + object.toFile().getName());
																net.forestany.forestj.lib.Global.ilogFinest("copied directory '" + object.toFile().getAbsolutePath() + "'");
					}
				}
			} catch (java.io.IOException o_exc) {
				net.forestany.forestj.lib.Global.logException("CopyDirectory exception: ", o_exc);
			}
		});
	}
	
	/**
	 * Move directory to a destination directory path, incl. sub directories with file contents
	 * 
	 * @param p_s_source					full path to source directory
	 * @param p_s_destination				full path to destination directory
	 * @throws IllegalArgumentException		source or destination directory does not exist
	 * @throws java.io.IOException			file or directory could not be moved or source directory could not be deleted
	 */
	public static void moveDirectory(String p_s_source, String p_s_destination) throws IllegalArgumentException, java.io.IOException {
		/* create java.nio path objects */
		java.nio.file.Path o_source = java.nio.file.Paths.get(p_s_source);
		java.nio.file.Path o_destination = java.nio.file.Paths.get(p_s_destination);
		
		/* check if source directory exists */
		if (!java.nio.file.Files.exists(o_source)) {
			throw new IllegalArgumentException("Source directory[" + p_s_source + "] does not exist");
		}
		
		/* check if destination directory exists */
		if (java.nio.file.Files.exists(o_destination)) {
			throw new IllegalArgumentException("Destination directory[" + p_s_destination + "] does already exist");
		}
		
		/* move directory */
		try {
			File.copyDirectory(p_s_source, p_s_destination);
													net.forestany.forestj.lib.Global.ilogFinest("copied directory '" + p_s_source + "'");
			File.deleteDirectory(p_s_source);
													net.forestany.forestj.lib.Global.ilogFinest("deleted directory '" + p_s_source + "'");
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("Directory[" + p_s_source + "] cannot be moved: " + o_exc.getMessage());
		}	
	}
	
	/**
	 * Rename a directory
	 * 
	 * @param p_s_source					full path to source directory
	 * @param p_s_destination				full path to destination directory
	 * @throws IllegalArgumentException		source or destination directory does not exist
	 * @throws java.io.IOException			directory could not be renamed
	 */
	public static void renameDirectory(String p_s_source, String p_s_destination) throws IllegalArgumentException, java.io.IOException {
		/* create java.nio path objects */
		java.nio.file.Path o_source = java.nio.file.Paths.get(p_s_source);
		java.nio.file.Path o_destination = java.nio.file.Paths.get(p_s_destination);
		
		/* check if source directory exists */
		if (!java.nio.file.Files.exists(o_source)) {
			throw new IllegalArgumentException("Source directory[" + p_s_source + "] does not exist");
		}
		
		/* check if destination directory exists */
		if (java.nio.file.Files.exists(o_destination)) {
			throw new IllegalArgumentException("Destination directory[" + p_s_destination + "] does already exist");
		}
		
		/* rename directory */
		try {
			java.nio.file.Files.move(o_source, o_source.resolveSibling(o_destination.toFile().getName()));
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("Directory[" + p_s_source + "] cannot be renamed: " + o_exc.getMessage());
		}	
	}
	
	/**
	 * Delete a directory, incl. all files and sub directories
	 * 
	 * @param p_s_source				full path to directory
	 * @throws IllegalArgumentException	directory does not exist
	 * @throws java.io.IOException		directory could not be deleted
	 */
	public static void deleteDirectory(String p_s_source) throws IllegalArgumentException, java.io.IOException {
		/* create java.nio path objects */
		java.nio.file.Path o_source = java.nio.file.Paths.get(p_s_source);
		
		/* check if directory exists */
		if (!java.nio.file.Files.exists(o_source)) {
			throw new IllegalArgumentException("Directory" + p_s_source + "] does not exist");
		}
		
		/* delete content of directory recursive */
		java.nio.file.Files.walk(o_source).forEach(object -> {
			try {
				if (object != o_source) {
					if (!object.toFile().isDirectory()) {
						File.deleteFile(object.toFile().getAbsolutePath());
																net.forestany.forestj.lib.Global.ilogFinest("deleted file '" + object.toFile().getAbsolutePath() + "'");
					} else {
						File.deleteDirectory(object.toFile().getAbsolutePath());
																net.forestany.forestj.lib.Global.ilogFinest("deleted directory '" + object.toFile().getAbsolutePath() + "'");
					}
				}
			} catch (java.io.IOException o_exc) {
				net.forestany.forestj.lib.Global.logException("DeleteDirectory issue: ", o_exc);
			}
		});
		
		/* delete directory */
		try {
			if (!(java.nio.file.Files.deleteIfExists(o_source))) {
				throw new java.io.IOException("Directory does not exist");
			}
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("Directory[" + p_s_source + "] could not be deleted: " + o_exc.getMessage());
		}	
	}
	
	
	/**
	 * Create hex file folder structure.
	 * 2-character-length [00-FF].
	 * with leading zero.
	 * 
	 * @param p_s_source					parent directory full path of the new file folder structure
	 * @throws IllegalArgumentException		directory does not exist 
	 * @throws java.io.IOException 			a directory could not be created
	 */
	public static void createHexFileFolderStructure(String p_s_source) throws IllegalArgumentException, java.io.IOException {
		/* create java.nio path objects */
		java.nio.file.Path o_source = java.nio.file.Paths.get(p_s_source);
		
		/* check if directory exists */
		if (java.nio.file.Files.exists(o_source)) {
			throw new IllegalArgumentException("Directory" + p_s_source + "] does already exist");
		}
		
		File.createDirectory(p_s_source);
												net.forestany.forestj.lib.Global.ilogMass("created root directory '" + p_s_source + "'");
		
		/* create 256 directories */
		for (int i = 0; i < 256; i++) {
			/* hex folder name with capital letters */
			String s_hex = Integer.toHexString(i).toUpperCase();
			
			/* with leading zero */
			if (s_hex.length() == 1) {
				s_hex = "0" + s_hex;
			}
			
			/* create new hex directory */
			File.createDirectory(p_s_source + File.DIR + s_hex);
													net.forestany.forestj.lib.Global.ilogMass("created sub directory '" + s_hex + "'");
		}
	}
	
	
	/**
	 * Generate random file content, default CHARSET constant from File class and default line length of 256.
	 * automatic length of 1 KB.
	 * 
	 * @return					StringBuilder instance
	 * @see						String
	 * @see						StringBuilder
	 */
	public static StringBuilder generateRandomFileContent_1KB() {
		return generateRandomFileContent(java.nio.charset.Charset.forName(File.CHARSET), 256, 1024L);
	}
	
	/**
	 * Generate random file content, default CHARSET constant from File class and default line length of 256.
	 * automatic length of 1 MB.
	 * 
	 * @return					StringBuilder instance
	 * @see						String
	 * @see						StringBuilder
	 */
	public static StringBuilder generateRandomFileContent_1MB() {
		return generateRandomFileContent(java.nio.charset.Charset.forName(File.CHARSET), 256, 1024L * 1024L);
	}
	
	/**
	 * Generate random file content, default CHARSET constant from File class and default line length of 256.
	 * automatic length of 10 MB.
	 * 
	 * @return					StringBuilder instance
	 * @see						String
	 * @see						StringBuilder
	 */
	public static StringBuilder generateRandomFileContent_10MB() {
		return generateRandomFileContent(java.nio.charset.Charset.forName(File.CHARSET), 256, 10L * 1024L * 1024L);
	}
	
	/** 
	 * Generate random file content, default CHARSET constant from File class and default line length of 256.
	 * automatic length of 50 MB.
	 * 
	 * @return					StringBuilder instance
	 * @see						String
	 * @see						StringBuilder
	 */
	public static StringBuilder generateRandomFileContent_50MB() {
		return generateRandomFileContent(java.nio.charset.Charset.forName(File.CHARSET), 256, 50L * 1024L * 1024L);
	}
	
	/**
	 * Generate random file content, default CHARSET constant from File class and default line length of 256.
	 * automatic length of 100 MB.
	 * 
	 * @return					StringBuilder instance
	 * @see						String
	 * @see						StringBuilder
	 */
	public static StringBuilder generateRandomFileContent_100MB() {
		return generateRandomFileContent(java.nio.charset.Charset.forName(File.CHARSET), 256, 100L * 1024L * 1024L);
	}
	
	/**
	 * Generate random file content, default CHARSET constant from File class and default line length of 256.
	 * automatic length of 250 MB.
	 * 
	 * @return					StringBuilder instance
	 * @see						String
	 * @see						StringBuilder
	 */
	public static StringBuilder generateRandomFileContent_250MB() {
		return generateRandomFileContent(java.nio.charset.Charset.forName(File.CHARSET), 256, 250L * 1024L * 1024L);
	}
	
	/**
	 * Generate random file content, default CHARSET constant from File class and default line length of 256.
	 * automatic length of 500 MB.
	 * 
	 * @return					StringBuilder instance
	 * @see						String
	 * @see						StringBuilder
	 */
	public static StringBuilder generateRandomFileContent_500MB() {
		return generateRandomFileContent(java.nio.charset.Charset.forName(File.CHARSET), 256, 500L * 1024L * 1024L);
	}
	
	/**
	 * Generate random file content, default CHARSET constant from File class and default line length of 256.
	 * automatic length of 1 GB.
	 * 
	 * @return					StringBuilder instance
	 * @see						String
	 * @see						StringBuilder
	 */
	public static StringBuilder generateRandomFileContent_1GB() {
		return generateRandomFileContent(java.nio.charset.Charset.forName(File.CHARSET), 256, 1024L * 1024L * 1024L);
	}
	
	/**
	 * Generate random file content, default CHARSET constant from File class and default line length of 256
	 * 
	 * @param p_l_length		length of random string value
	 * @return					StringBuilder instance
	 * @see						String
	 * @see						StringBuilder
	 */
	public static StringBuilder generateRandomFileContent(long p_l_length) {
		return generateRandomFileContent(java.nio.charset.Charset.forName(File.CHARSET), 256, p_l_length);
	}
	
	/**
	 * Generate random file content, default CHARSET constant from File class
	 * 
	 * @param p_i_lineLength	line length within random file content
	 * @param p_l_length		length of random string value
	 * @return					StringBuilder instance
	 * @see						String
	 * @see						StringBuilder
	 */
	public static StringBuilder generateRandomFileContent(int p_i_lineLength, long p_l_length) {
		return generateRandomFileContent(java.nio.charset.Charset.forName(File.CHARSET), p_i_lineLength, p_l_length);
	}
	
	/**
	 * Generate random file content
	 * 
	 * @param p_o_charsetWrite	the charset will be used when creating random content characters
	 * @param p_i_lineLength	line length within random file content
	 * @param p_l_length		length of random string value
	 * @return					StringBuilder instance
	 * @see						String
	 * @see						StringBuilder
	 * @see						java.nio.charset.Charset
	 */
	public static StringBuilder generateRandomFileContent(java.nio.charset.Charset p_o_charsetWrite, int p_i_lineLength, long p_l_length) {
		/* check charset write parameter */
		if (p_o_charsetWrite == null) {
			throw new IllegalArgumentException("Charset write parameter is empty");
		}
		
		/* check line length parameter */
		if (p_i_lineLength < 1) {
			throw new IllegalArgumentException("Line length parameter must be positive");
		}
		
		/* check length parameter */
		if (p_l_length < 1) {
			throw new IllegalArgumentException("Length parameter must be positive");
		}
		
		/* check length parameter not overflow */
		if (p_l_length > Long.MAX_VALUE) {
			throw new IllegalArgumentException("Max. value of length parameter is " + Long.MAX_VALUE);
		}
		
		StringBuilder o_stringBuilder = new StringBuilder();
		int i_lineLength = 0;
		
		/* create each byte for random content */
		for (long i = 0; i < p_l_length; i++) {
			/* if we reached end of line */
			if ( (i_lineLength != 0) && ((i_lineLength % p_i_lineLength) == 0) ) {
				/* append line separator */
				o_stringBuilder.append(new String(System.lineSeparator().getBytes(), p_o_charsetWrite));
				/* increase counter with length of line separator */
				i += System.lineSeparator().length() - 1;
				
				/* reset line length counter */
				i_lineLength = 0;
			} else {
				/* generate random byte value */
				byte b_byte = ( (Integer) ( net.forestany.forestj.lib.Helper.randomIntegerRange(32, 126) ) ).byteValue();
				
				/* add byte value to random content, using charset parameter */
				o_stringBuilder.append(new String(new byte[] { b_byte }, p_o_charsetWrite));
				
				/* increment line length counter */
				i_lineLength++;
			}
		}
		
		/* return random content */
		return o_stringBuilder;
	}
	
	/**
	 * Replace file content with content of string builder, default CHARSET constant from File class
	 * 
	 * @param p_s_filePath						full file path to change its content
	 * @param p_o_stringBuilder					new content gathered in string builder instance
	 * @throws IllegalArgumentException			invalid file path, empty string builder or empty charset parameter
	 * @throws java.io.FileNotFoundException	invalid file path
	 * @throws java.io.IOException				write access to file not possible
	 */
	public static void replaceFileContent(String p_s_filePath, StringBuilder p_o_stringBuilder) throws IllegalArgumentException, java.io.FileNotFoundException, java.io.IOException {
		replaceFileContent(p_s_filePath, p_o_stringBuilder, java.nio.charset.Charset.forName(File.CHARSET));
	}
	
	/**
	 * Replace file content with content of string builder
	 * 
	 * @param p_s_filePath						full file path to change its content
	 * @param p_o_stringBuilder					new content gathered in string builder instance
	 * @param p_o_charset						charset will be used when writing new content to file
	 * @throws IllegalArgumentException			invalid file path, empty string builder or empty charset parameter
	 * @throws java.io.FileNotFoundException	invalid file path
	 * @throws java.io.IOException				write access to file not possible
	 */
	public static void replaceFileContent(String p_s_filePath, StringBuilder p_o_stringBuilder, java.nio.charset.Charset p_o_charset) throws IllegalArgumentException, java.io.FileNotFoundException, java.io.IOException {
		/* check file path parameter */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_filePath)) {
			throw new IllegalArgumentException("File path parameter is empty");
		}
		
		/* check string builder parameter */
		if (p_o_stringBuilder == null) {
			throw new IllegalArgumentException("String builder parameter is empty");
		}
		
		/* check charset parameter */
		if (p_o_charset == null) {
			throw new IllegalArgumentException("Charset parameter is empty");
		}
		
		/* create java.nio path object */
		java.nio.file.Path o_tempPath = java.nio.file.Paths.get(p_s_filePath);
		
		/* check if file exists */
		if (!java.nio.file.Files.exists(o_tempPath)) {
			throw new java.io.FileNotFoundException("File[" + p_s_filePath + "] does not exist");
		}
		
		/* write file content */
		try {
			java.nio.file.Files.write(o_tempPath, p_o_stringBuilder.toString().getBytes(p_o_charset), java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
		} catch (java.io.IOException o_exc) {
			throw new java.io.IOException("File[" + p_s_filePath + "] write access not possible: " + o_exc.getMessage());
		}
	}
}
