package de.forestj.lib.io;

/**
 * 
 * YAML class to encode and decode java objects to yaml files with help of a yaml schema file/data.
 * access to object fields can be directly on public fields or with public property methods (getXX setXX) on private fields.
 * NOTE: mostly only primitive types supported for encoding and decoding, only supporting ISO-8601 UTC timestamps within yaml files.
 *
 */
public class YAML {
	
	/* Fields */
	
	private YAMLElement o_root;
	private YAMLElement o_currentElement;
	private YAMLElement o_schema;
	private String s_lineBreak;
	private boolean b_usePropertyMethods;
	private YAMLElement o_definitions;
	private java.util.List<String> a_references;
	private YAMLElement o_properties;
	private int i_level;
	
	private int i_amountWhiteSpacesindentation;
	private boolean b_firstLevelCollectionElementsOnly;
	private String s_stringQuote; 
	
	/* Properties */
	
	public YAMLElement getRoot() {
		return this.o_root;
	}
	
	public void setRoot(YAMLElement p_o_value) {
		this.o_root = p_o_value;
	}
	
	public String getLineBreak() {
		return this.s_lineBreak;
	}
	
	/**
	 * Determine line break characters for reading and writing yaml files
	 */
	public void setLineBreak(String p_s_lineBreak) throws IllegalArgumentException {
		if (p_s_lineBreak.length() < 1) {
			throw new IllegalArgumentException("Line break must have at least a length of 1, but length is '" + p_s_lineBreak.length() + "'");
		}
		
		this.s_lineBreak = p_s_lineBreak;
												de.forestj.lib.Global.ilogConfig("updated line break to [" + de.forestj.lib.Helper.bytesToHexString(this.s_lineBreak.getBytes(), true) + "]");
	}
	
	public boolean getUsePropertyMethods() {
		return this.b_usePropertyMethods;
	}
	
	/**
	 * Determine if get and set property methods shall be used handling Objects
	 * convention: get'exactFieldName' and set'exactFieldName' must be used
	 */
	public void setUsePropertyMethods(boolean p_b_value) {
		this.b_usePropertyMethods = p_b_value;
		
												de.forestj.lib.Global.ilogConfig("updates use property methods to '" + this.b_usePropertyMethods + "'");
	}
		
	
	public int getAmountWhiteSpacesindentation() {
		return this.i_amountWhiteSpacesindentation;
	}
	
	public void setAmountWhiteSpacesindentation(int p_i_amountWhiteSpacesindentation) throws IllegalArgumentException {
		if (p_i_amountWhiteSpacesindentation < 2) {
			throw new IllegalArgumentException("Amount of white spaces for indentation must be at least '2', but was set to '" + p_i_amountWhiteSpacesindentation + "'");
		}
		
		this.i_amountWhiteSpacesindentation = p_i_amountWhiteSpacesindentation;
		
												de.forestj.lib.Global.ilogConfig("updates amount of white spaces indentation to '" + this.i_amountWhiteSpacesindentation + "'");
	}
	
	public String getStringQuote() {
		return this.s_stringQuote;
	}
	
	public void setStringQuote(String p_s_stringQuote) throws IllegalArgumentException {
		if (p_s_stringQuote.length() != 1) {
			throw new IllegalArgumentException("String quote must be '1' character, but has a length of '" + p_s_stringQuote.length() + "'");
		}
		
		this.s_stringQuote = p_s_stringQuote;
		
												de.forestj.lib.Global.ilogConfig("updates string quote to '" + this.s_stringQuote + "'");
	}
	
	/* Methods */
	
	/**
	 * Empty YAML constructor
	 */
	public YAML() {
		this.setAmountWhiteSpacesindentation(4);
		this.setStringQuote("\"");
		this.setLineBreak(de.forestj.lib.io.File.NEWLINE);
		this.setUsePropertyMethods(false);
		this.i_level = 0;
		
		this.a_references = new java.util.ArrayList<String>();
		
		this.setRoot(new YAMLElement("Root"));
		this.o_schema = this.o_root;
	}
	
	/**
	 * YAML constructor, giving file lines of schema as dynamic list and further instructions for encoding and decoding yaml data
	 * 
	 * @param p_a_yamlSchemaFileLines				file lines of schema as dynamic list
	 * @throws IllegalArgumentException				value/structure within yaml schema invalid
	 * @throws NullPointerException					yaml schema, root node is null
	 */
	public YAML(java.util.List<String> p_a_yamlSchemaFileLines) throws IllegalArgumentException, NullPointerException {
		this(p_a_yamlSchemaFileLines, 4);
	}
	
	/**
	 * YAML constructor, giving file lines of schema as dynamic list and further instructions for encoding and decoding yaml data.
	 * quote string values with '"' character.
	 * 
	 * @param p_a_yamlSchemaFileLines				file lines of schema as dynamic list
	 * @param p_i_amountWhiteSpacesindentation		define amount of white spaces which are used as indentation for yaml file, read and write
	 * @throws IllegalArgumentException				value/structure within yaml schema invalid
	 * @throws NullPointerException					yaml schema, root node is null
	 */
	public YAML(java.util.List<String> p_a_yamlSchemaFileLines, int p_i_amountWhiteSpacesindentation) throws IllegalArgumentException, NullPointerException {
		this(p_a_yamlSchemaFileLines, p_i_amountWhiteSpacesindentation, "\"");
	}
	
	/**
	 * YAML constructor, giving file lines of schema as dynamic list and further instructions for encoding and decoding yaml data
	 * 
	 * @param p_a_yamlSchemaFileLines				file lines of schema as dynamic list
	 * @param p_i_amountWhiteSpacesindentation		define amount of white spaces which are used as indentation for yaml file, read and write
	 * @param p_s_stringQuote						define character for quoting string values within yaml content
	 * @throws IllegalArgumentException				value/structure within yaml schema invalid
	 * @throws NullPointerException					yaml schema, root node is null
	 */
	public YAML(java.util.List<String> p_a_yamlSchemaFileLines, int p_i_amountWhiteSpacesindentation, String p_s_stringQuote) throws IllegalArgumentException, NullPointerException {
		this.setAmountWhiteSpacesindentation(p_i_amountWhiteSpacesindentation);
		this.setStringQuote(p_s_stringQuote);
		this.setLineBreak(de.forestj.lib.io.File.NEWLINE);
		this.setUsePropertyMethods(false);
		this.i_level = 0;
		
		this.a_references = new java.util.ArrayList<String>();
		
		java.util.List<String> a_fileLines = this.validateYAML(p_a_yamlSchemaFileLines);

												de.forestj.lib.Global.ilogConfig("yaml schema file lines validated");
		
	    /* check if root is null */
	    if ( (a_fileLines.size() == 0) || ((a_fileLines.size() == 1) && (a_fileLines.get(0).contentEquals("null"))) ) {
			throw new NullPointerException("Schema file is null");
		}

	    /* reset level */
	    this.i_level = 0;

	    /* create new root if is null */
		if (this.o_root == null) {
			this.o_root = new YAMLElement("Root");
		}
		
	    /* parse yaml schema */
	    this.parseYAML(1, a_fileLines.size(), a_fileLines, 0, this.o_root, true);

	    										de.forestj.lib.Global.ilogConfig("yaml schema parsed");
	    
	    /* set schema element with constructor input, root is unparsed schema */
	    this.setSchema(true);
	}
	
	/**
	 * YAML constructor, giving a schema yaml element object and further instructions for encoding and decoding yaml data.
	 * amount of white spaces which are used as indentation for yaml file is '4'.
	 * quote string values with '"' character.
	 * 
	 * @param p_o_schemaRoot						yaml element object as root schema node
	 * @throws IllegalArgumentException				invalid parameters for constructor
	 */
	public YAML(YAMLElement p_o_schemaRoot) throws IllegalArgumentException {
		this(p_o_schemaRoot, 4);
	}
	
	/**
	 * YAML constructor, giving a schema yaml element object and further instructions for encoding and decoding yaml data.
	 * amount of white spaces which are used as indentation for yaml file is '4'.
	 * 
	 * @param p_o_schemaRoot						yaml element object as root schema node
	 * @param p_i_amountWhiteSpacesindentation		define amount of white spaces which are used as indentation for yaml file, read and write
	 * @throws IllegalArgumentException				invalid parameters for constructor
	 */
	public YAML(YAMLElement p_o_schemaRoot, int p_i_amountWhiteSpacesindentation) throws IllegalArgumentException {
		this(p_o_schemaRoot, p_i_amountWhiteSpacesindentation, "\"");
	}
	
	/**
	 * YAML constructor, giving a schema yaml element object and further instructions for encoding and decoding yaml data
	 * 
	 * @param p_o_schemaRoot						yaml element object as root schema node
	 * @param p_i_amountWhiteSpacesindentation		define amount of white spaces which are used as indentation for yaml file, read and write
	 * @param p_s_stringQuote						define character for quoting string values within yaml content
	 * @throws IllegalArgumentException				invalid parameters for constructor
	 */
	public YAML(YAMLElement p_o_schemaRoot, int p_i_amountWhiteSpacesindentation, String p_s_stringQuote) throws IllegalArgumentException {
		if (p_o_schemaRoot == null) {
			throw new IllegalArgumentException("yaml element parameter for schema is null");
		}
		
		this.setAmountWhiteSpacesindentation(p_i_amountWhiteSpacesindentation);
		this.setStringQuote(p_s_stringQuote);
		this.setLineBreak(de.forestj.lib.io.File.NEWLINE);
		this.setUsePropertyMethods(false);
		this.i_level = 0;
		
		this.a_references = new java.util.ArrayList<String>();
		
		this.setRoot(p_o_schemaRoot);
		
		/* set schema element with constructor input, root is already parsed schema */
	    this.setSchema(false);
	}
	
	/**
	 * YAML constructor, giving a schema file and further instructions for encoding and decoding yaml data.
	 * amount of white spaces which are used as indentation for yaml file is '4'.
	 * quote string values with '"' character.
	 * 
	 * @param p_s_file								full-path to yaml schema file
	 * @throws IllegalArgumentException				value/structure within yaml schema file invalid
	 * @throws NullPointerException					yaml schema, root node is null
	 * @throws java.io.IOException					cannot access or open yaml file and it's content
	 */
	public YAML(String p_s_file) throws IllegalArgumentException, NullPointerException, java.io.IOException {
		this(p_s_file, 4);
	}
	
	/**
	 * YAML constructor, giving a schema file and further instructions for encoding and decoding yaml data.
	 * quote string values with '"' character.
	 * 
	 * @param p_s_file								full-path to yaml schema file
	 * @param p_i_amountWhiteSpacesindentation		define amount of white spaces which are used as indentation for yaml file, read and write
	 * @throws IllegalArgumentException				value/structure within yaml schema file invalid
	 * @throws NullPointerException					yaml schema, root node is null
	 * @throws java.io.IOException					cannot access or open yaml file and it's content
	 */
	public YAML(String p_s_file, int p_i_amountWhiteSpacesindentation) throws IllegalArgumentException, NullPointerException, java.io.IOException {
		this(p_s_file, p_i_amountWhiteSpacesindentation, "\"");
	}
	
	/**
	 * YAML constructor, giving a schema file and further instructions for encoding and decoding yaml data
	 * 
	 * @param p_s_file								full-path to yaml schema file
	 * @param p_i_amountWhiteSpacesindentation		define amount of white spaces which are used as indentation for yaml file, read and write
	 * @param p_s_stringQuote						define character for quoting string values within yaml content
	 * @throws IllegalArgumentException				value/structure within yaml schema file invalid
	 * @throws NullPointerException					yaml schema, root node is null
	 * @throws java.io.IOException					cannot access or open yaml file and it's content
	 */
	public YAML(String p_s_file, int p_i_amountWhiteSpacesindentation, String p_s_stringQuote) throws IllegalArgumentException, NullPointerException, java.io.IOException {
		this.setAmountWhiteSpacesindentation(p_i_amountWhiteSpacesindentation);
		this.setStringQuote(p_s_stringQuote);
		this.setLineBreak(de.forestj.lib.io.File.NEWLINE);
		this.setUsePropertyMethods(false);
		
		this.a_references = new java.util.ArrayList<String>();
		
		java.util.List<String> a_fileLines = this.validateYAML(p_s_file);

												de.forestj.lib.Global.ilogConfig("yaml schema file validated");
		
	    /* check if root is null */
	    if ( (a_fileLines.size() == 0) || ((a_fileLines.size() == 1) && (a_fileLines.get(0).contentEquals("null"))) ) {
			throw new NullPointerException("Schema file is null");
		}

	    /* reset level */
	    this.i_level = 0;

	    /* create new root if is null */
		if (this.o_root == null) {
			this.o_root = new YAMLElement("Root");
		}
		
	    /* parse yaml schema */
	    this.parseYAML(1, a_fileLines.size(), a_fileLines, 0, this.o_root, true);

	    										de.forestj.lib.Global.ilogConfig("yaml schema parsed");
	    
	    /* set schema element with constructor input, root is unparsed schema */
	    this.setSchema(true);
	}
	
	/**
	 * Method to set schema elements, afterwards each yaml constructor has read their input
	 */
	private void setSchema(boolean p_b_rootIsUnparsedSchema) {
	    /* check if root is null */
		if (this.o_root == null) {
			throw new NullPointerException("Root node is null");
		}
		
		/* check if root has any children */
		if (this.o_root.getChildren().size() == 0) {
			throw new NullPointerException("Root node has no children[size=" + this.o_root.getChildren().size() + "]");
		}
		
		if (p_b_rootIsUnparsedSchema) {
													if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("Unparsed YAML-Schema: " + this.s_lineBreak + this.o_root);
			
			/* reset level */
			this.i_level = 0;
			
			/* set current element with root if it is empty */
			if (this.o_currentElement == null) {
				this.o_currentElement = this.o_root;
			}
			
			this.parseYAMLSchemaElements(this.o_root);
			
													de.forestj.lib.Global.ilogConfig("yaml schema elements parsed");
			
			/* reset children of root */
			this.o_root.getChildren().clear();
			
			if (this.o_properties != null) {
				/* properties cannot have one child and 'Reference' */
				if ( (this.o_properties.getChildren().size() > 0) && (this.o_properties.getReference() != null) ) {
					throw new IllegalArgumentException("Properties after parsing yaml schema cannot have one child and 'Reference'");
				}
				
				/* check if properties has one child */
				if (this.o_properties.getChildren().size() > 0) {
					/* add all 'properties' children to root */
					for (YAMLElement o_yamlElement : this.o_properties.getChildren()) {
						this.o_root.getChildren().add(o_yamlElement);
					}
					
															de.forestj.lib.Global.ilogConfig("added all 'properties' from yaml schema to root");
				} else if (this.o_properties.getReference() != null) { /* we have 'Reference' in properties */
					/* set properties 'Reference' as root 'Reference' */
					this.o_root.setReference(this.o_properties.getReference());
					
															de.forestj.lib.Global.ilogConfig("set 'reference' from yaml schema as root 'reference'");
				}
			}
		}
		
												if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("Parsed YAML-Schema: " + this.s_lineBreak + this.o_root);
		
		this.o_schema = this.o_root;
		
												de.forestj.lib.Global.ilogConfig("set yaml root element as schema element");
	}
	
	/**
	 * Returns root element of yaml schema as string output and all of its children
	 */
	@Override
	public String toString() {
		String s_foo = "";
		
		s_foo += this.o_root;
				
		return s_foo;
	}
	
	/**
	 * Generate indentation string for yaml specification
	 * 
	 * @return		indentation string
	 */
	private String printIndentation() {
		String s_foo = "";
		
		for (int i = 0; i < this.i_level; i++) {
			if ( (i == 0) && (this.b_firstLevelCollectionElementsOnly) ) {
				s_foo += "  ";
			} else {
				for (int j = 0; j < this.i_amountWhiteSpacesindentation; j++) {
					s_foo += " ";
				}
			}
		}
		
		return s_foo;
	}
	
	/* validate YAML */
	
	/**
	 * Validate yaml file if values and structure are correct, otherwise throw IllegalArgumentException
	 * 
	 * @param p_s_yamlFile						full-path to yaml file
	 * @return									validated yaml lines
	 * @throws java.io.IOException				cannot access or open yaml file and it's content
	 * @throws IllegalArgumentException			value or structure within yaml file lines invalid
	 */
	public java.util.List<String> validateYAML(String p_s_yamlFile) throws java.io.IOException, IllegalArgumentException {
		/* check if file exists */
		if (!de.forestj.lib.io.File.exists(p_s_yamlFile)) {
			throw new IllegalArgumentException("File[" + p_s_yamlFile + "] does not exist.");
		}
		
		/* open yaml file */
		de.forestj.lib.io.File o_file = new de.forestj.lib.io.File(p_s_yamlFile, false);
		
		/* load file content into string */
		String s_fileContent = o_file.getFileContent();
		
												de.forestj.lib.Global.ilogConfig("yaml file accessed and content read");
		
		java.util.List<String> a_fileLines = new java.util.ArrayList<String>();

		/* load file content lines into array */
		for (String s_line : s_fileContent.split(this.s_lineBreak)) {
			a_fileLines.add(s_line);
		}
		
												de.forestj.lib.Global.ilogConfig("yaml file content transferred to array");
		
		return this.validateYAML(a_fileLines);
	}
	
	/**
	 * Validate yaml file lines if values and structure are correct, otherwise throw IllegalArgumentException
	 * 
	 * @param p_a_yamlLines						yaml file lines
	 * @return									validated yaml lines
	 * @throws IllegalArgumentException			value or structure within yaml file lines invalid
	 */
	public java.util.List<String> validateYAML(java.util.List<String> p_a_yamlLines) throws IllegalArgumentException {
		java.util.List<String> a_yamlLines = new java.util.ArrayList<String>();
		
		if (p_a_yamlLines == null || p_a_yamlLines.size() < 1) {
			throw new NullPointerException("YAML lines content is null");
		}
		
		int i_line = 1;
		
		/* load file content lines into array */
		for (String s_line : p_a_yamlLines) {
			/* check if line contains any tabulator-signs */
			if (s_line.contains("\t")) {
				throw new IllegalStateException("Line (" + i_line + ") contains 'tab'-signs which are not allowed within yaml-documents");
			}
			
			/* remove comments */
			if (s_line.contains("#")) {
				boolean b_commentFound = false;
				boolean b_inQuotation = false;
				boolean b_escapeCharacterBefore = false;
				
				/* check for unquoted comment character in line */
				for (int i = 0; i < s_line.length(); i++) {
					if ( (s_line.charAt(i) == '"') && (!b_escapeCharacterBefore) ) {
						if (b_inQuotation) {
							b_inQuotation = false;
						} else {
							b_inQuotation = true;
						}
					} else if ( (b_inQuotation) && (s_line.charAt(i) == '\\') ) {
						b_escapeCharacterBefore = true;
					} else if ( (s_line.charAt(i) == '#') && (!b_inQuotation) ) {
						b_commentFound = true;
						break;
					}
					
					if ( (b_inQuotation) && (s_line.charAt(i) != '\\') ) {
						b_escapeCharacterBefore = false;
					}
				}
				
				if (b_commentFound) {
					s_line = s_line.substring(0, s_line.indexOf('#'));
				}
			}
			
			boolean b_onlyWhiteSpaces = true;
			
			/* check if line only contains white spaces */
			for (int i = 0; i < s_line.length(); i++) {
				if (s_line.charAt(i) != ' ') {
					b_onlyWhiteSpaces = false;
					break;
				}
			}
			
			/* empty line if it contains only of white spaces */
			if (b_onlyWhiteSpaces) {
				s_line = "";
			}
			
			/* add file line to array */
			a_yamlLines.add(s_line);
			
			i_line++;
		}
		
		/* check if yaml document starts with '---' */
		if (!a_yamlLines.get(0).contentEquals("---")) {
			throw new IllegalArgumentException("YAML document must start with '---'");
		}
		
		/* set level for PrintIndentation to zero */
		this.i_level = 0;
		
		/* validate yaml file recursively */
		validateYAMLRecursive(1, a_yamlLines.size(), a_yamlLines, 0, false);
		
		return a_yamlLines;
	}
	
	/**
	 * Validate yaml file lines if values and structure are correct, otherwise throw IllegalArgumentException
	 * 
	 * @param p_i_min							pointer where to start line iteration
	 * @param p_i_max							pointer where to end line iteration
	 * @param p_a_lines							yaml file lines
	 * @param p_i_whiteSpaceindentation			amount of white space indentation in yaml file
	 * @param p_b_textBlock						true - current lines are a textblock within yaml data, false - normal yaml data
	 * @throws IllegalArgumentException			value or structure within yaml file lines invalid
	 */
	private void validateYAMLRecursive(int p_i_min, int p_i_max, java.util.List<String> p_a_lines, int p_i_whiteSpaceindentation, boolean p_b_textBlock) throws IllegalArgumentException {
		/* temp variables for key and value */
		String s_key = "null";
		String s_value = "null";
		
		/* iterate each line */
		for (int i_min = p_i_min; i_min < p_i_max; i_min++) {
			/* read line into string */
			String s_line = p_a_lines.get(i_min);
			
													de.forestj.lib.Global.ilogFiner(s_line);
			
			/* check if line has any content */
			if ( (s_line.length() == 0) || (s_line.contentEquals("...")) ) {
														de.forestj.lib.Global.ilogFiner("(" + (i_min + 1) + ")|NULL|");
				
				/* skip lines with no content */
				continue;
			}

			if (p_i_whiteSpaceindentation >= s_line.length()) {
				throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Invalid indentation, yaml syntax error");
			}
			
			/* check if line starts with white space on expected position */
			if (s_line.charAt(p_i_whiteSpaceindentation) == ' ') {
														de.forestj.lib.Global.ilogFiner(s_line);
				
				/* check if we already have a value from previous line */
				if (!s_value.contentEquals("null")) {
					/* new indentation with previous value only allowed if value was reference or text block */
					if ( (s_value.charAt(0) != '&') && (!s_value.contentEquals("|")) && (!s_value.contentEquals(">")) ) {
						throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): New indentation found, but parent element has already a value");
					}
				}
				
				int i = 0;
				
				/* count white spaces */
				while (s_line.charAt(i) == ' ') {
					i++;
				}
				
				int i_max = -1;
				
				/* search for end of indentation block */
				for (i_max = i_min + 1; i_max < p_i_max; i_max++) {
					/* read line */
					String s_lineTemp = p_a_lines.get(i_max);
					
					/* check if line has any content */
					if ( (s_lineTemp.length() == 0) || (s_lineTemp.contentEquals("...")) ) {
						/* skip lines with no content */
						continue;
					}
					
					int j = 0;
					
					/* count white spaces */
					while (s_lineTemp.charAt(j) == ' ') {
						j++;
					}
					
					/* if counted white spaces matches expected position of indentation or there is a new collection element with '-'-sign as prefix */
					if ( (j == p_i_whiteSpaceindentation) || ((p_i_whiteSpaceindentation >= 2) && (s_lineTemp.charAt(p_i_whiteSpaceindentation - 2) == '-') && (s_lineTemp.charAt(p_i_whiteSpaceindentation - 1) == ' ')) ) {
						/* break - end of indentation block found */
						break;
					}
				}
				
				/* increase level for PrintIndentation */
				this.i_level++;
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "block goes from (" + (i_min + 1) + ") to (" + (i_max) + ") with indentation '" + i + "'");
				
				/* start new recursion with new indentation block */
				this.validateYAMLRecursive(i_min, i_max, p_a_lines, i, ( (s_value.contentEquals("|")) || (s_value.contentEquals(">")) ));
				
				/* decrease level for PrintIndentation */
				this.i_level--;
				
				/* overwrite line pointer and continue for loop after indentation block */
				i_min = i_max - 1;
				continue;
			}

			String s_originalLine = s_line;
			
			/* cut white space indentation of line */
			if (p_i_whiteSpaceindentation > 0) {
				s_line = s_line.substring(p_i_whiteSpaceindentation);
			}
			
			int i_amountColon = 0;
			int i_lineLevel = 0;
			int i_lineLevelSequences = 0;
			boolean b_inQuotation = false;
			boolean b_escapeCharacterBefore = false;
			
			/* check for multiple colon characters in line */
			for (int i = 0; i < s_line.length(); i++) {
				if (s_line.charAt(i) == '{') {
					i_lineLevel++;
				} else if (s_line.charAt(i) == '}') {
					i_lineLevel--;
				} else if (s_line.charAt(i) == '[') {
					i_lineLevelSequences++;
				} else if (s_line.charAt(i) == ']') {
					i_lineLevelSequences--;
				} else if ( (s_line.charAt(i) == '"') && (!b_escapeCharacterBefore) ) {
					if (b_inQuotation) {
						b_inQuotation = false;
					} else {
						b_inQuotation = true;
					}
				} else if ( (b_inQuotation) && (s_line.charAt(i) == '\\') ) {
					b_escapeCharacterBefore = true;
				} else if ( (s_line.charAt(i) == ':') && (i_lineLevel == 0) && (!b_inQuotation) ) {
					i_amountColon++;
				}
				
				if ( (b_inQuotation) && (s_line.charAt(i) != '\\') ) {
					b_escapeCharacterBefore = false;
				}
			}
			
			if ( (i_lineLevel != 0) && (!p_b_textBlock) ) {
				throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Invalid nesting with curly brackets '{', '}'");
			}
			
			if ( (i_lineLevelSequences != 0) && (!p_b_textBlock) ) {
				throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Invalid collection array with squared brackets '[', ']'");
			}
			
			if (i_amountColon > 1) {
				throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): A line can only contain one colon ':' character");
			}
			
			s_key = "null";
			s_value = "null";
			boolean b_newElementForCollection = false;
			
			/* if there is no colon or text block mode we only have a value in line */
			if ( (i_amountColon == 0) || (p_b_textBlock) ) {
				/* assume whole line as value */
				s_value = s_line;
				
				/* if line starts with a colon we have a new collection element */
				if (s_line.startsWith("- ")) {
					/* cut colon with white space out of line */
					s_value = s_value.substring(2);
					
					/* set new collection element flag */
					b_newElementForCollection = true;
				} else {
					if (!( ( (s_value.startsWith("{")) && (s_value.endsWith("}")) ) || (p_b_textBlock) )) {
						throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): A line which is not a key value pair, not a collection element, no inline-object and not part of a text block is not allowed");
					}
				}
			} else if (i_amountColon == 1) {
				/* split key value before colon and remove all white spaces between key value and colon */
				s_key = s_line.substring(0, s_line.indexOf(':')).replaceAll("\\s+$", "");
				
				/* if line starts with a colon we have a new collection element */
				if (s_key.startsWith("- ")) {
					/* cut colon with white space out of line */
					s_key = s_key.substring(2);
					
					/* increase indentation deep */
					p_i_whiteSpaceindentation = p_i_whiteSpaceindentation + 2;
					
					/* set new collection element flag */
					b_newElementForCollection = true;
				} else if ( (p_i_whiteSpaceindentation >= 2) && (s_originalLine.charAt(p_i_whiteSpaceindentation - 2) == '-') && (s_originalLine.charAt(p_i_whiteSpaceindentation - 1) == ' ') ) {
					/* if there is a new collection element with '-'-sign as prefix, set new collection element flag */
					b_newElementForCollection = true;
				}
				
				/* split value after colon */
				s_value = s_line.substring(s_line.indexOf(':') + 1);
			}
			
			/* if we are not in text block mode, trim key and value */
			if (!p_b_textBlock) {
				s_key = s_key.trim();
				s_value = s_value.trim();
			}
			
			if (!java.util.regex.Pattern.matches("[a-zA-Z0-9-_]*", s_key)) {
				throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Invalid key '" + s_key + "', invalid characters. Following characters are allowed: [a-z], [A-Z], [0-9], [-] and [_]");
			}
			
			/* if value is not 'NULL' and 'length > 0' */
			if ( (!s_value.contentEquals("null")) && (!s_value.contentEquals(this.s_stringQuote + this.s_stringQuote)) && (s_value.length() > 0) ) {
				java.util.List<Character> a_reservedCharacters = java.util.Arrays.asList(':', '}', ']', ',', '#', '?', '-', '<', '=', '!', '%', '@', '\\', '\'', this.s_stringQuote.toCharArray()[0]);
				
				/* remove surrounding string quote characters */
				if ( (s_value.startsWith(this.s_stringQuote)) && (s_value.endsWith(this.s_stringQuote)) ) {
					s_value = s_value.substring(1, s_value.length() - 1);
				} else if (s_value.contentEquals("[]")) { /* check for empty collection value */
					s_value = "null";
				} else if (a_reservedCharacters.contains(s_value.charAt(0))) { /* check for invalid characters on value's first position */
					if (!( (de.forestj.lib.Helper.isInteger(s_value)) || (de.forestj.lib.Helper.isDouble(s_value)) )) { /* leading '-' is valid if value is integer or double */
						throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Value starts with invalid character '" + s_value.charAt(0) + "'");
					}
				}
				
				if (s_value.charAt(0) == '[') {
					/* must end with ']' */
					if (!s_value.endsWith("]")) {
						throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Inline collection starts with character '" + s_value.charAt(0) + "' must end with character ']', but ends with '" + s_value.charAt(s_value.length() - 1) + "'");
					}
				} else if (s_value.charAt(0) == '{') {
					/* must end with '}' */
					if (!s_value.endsWith("}")) {
						throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Inline object starts with character '" + s_value.charAt(0) + "' must end with character '}', but ends with '" + s_value.charAt(s_value.length() - 1) + "'");
					}
				} else if ( (s_value.charAt(0) == '&') || (s_value.contentEquals("|")) || (s_value.contentEquals(">")) ) {
					/* next line must be higher level, not same level, not lower level */
					String s_lineTemp = "";
					int i_tempMin = i_min;
					
					do {
						if (++i_tempMin >= p_i_max) {
							if (s_value.charAt(0) == '&') {
								throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Reached end of block without any subordinate value of reference start");
							} else if ( (s_value.contentEquals("|")) || (s_value.contentEquals(">")) ) {
								throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Reached end of block without any subordinate value of textblock start");
							}
						}
						
						/* read next line into string */
						s_lineTemp = p_a_lines.get(i_tempMin);
						
						/* check if line has any content */
					} while ( (s_lineTemp.length() == 0) || (s_lineTemp.contentEquals("...")) );
					
					int i = 0;
					
					/* count white spaces */
					while (s_lineTemp.charAt(i) == ' ') {
						i++;
					}
					
					if (i <= p_i_whiteSpaceindentation) {
						if (s_value.charAt(0) == '&') {
							throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Next line after reference start is not subordinate");
						} else if ( (s_value.contentEquals("|")) || (s_value.contentEquals(">")) ) {
							throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Next line after textblock start is not subordinate");
						}
					}
				}
			} else {
				/* set value 'NULL' if 'length = 0' */
				s_value = "null";
			}
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "(" + (i_min + 1) + ")|key=" + s_key + "|value=" + s_value + "|" + b_newElementForCollection + "|" + p_b_textBlock + "|");
		}
	}

	/* parsing YAML schema */
	
	/**
	 * Analyze yaml element value and get a unique value type from it
	 * 
	 * @param p_s_yamlValue					yaml element value as string
	 * @return								unique yaml value type
	 * @throws IllegalArgumentException		invalid value or yaml value type could not be determined
	 */
	public YAMLValueType getYAMLValueType(String p_s_yamlValue) throws IllegalArgumentException {
		YAMLValueType e_yamlValueType = YAMLValueType.String;
		
		/* get yaml value type */
		if (de.forestj.lib.Helper.isStringEmpty(p_s_yamlValue)) {
			e_yamlValueType = YAMLValueType.Null;
		} else if (de.forestj.lib.Helper.isDateTime(p_s_yamlValue)) {
			/* recognize date format ISO-8601 as string, not as number or integer */
			e_yamlValueType = YAMLValueType.String;
		} else if ( (p_s_yamlValue.charAt(0) == '+') || (p_s_yamlValue.charAt(0) == '-') || (Character.isDigit(p_s_yamlValue.charAt(0))) ) { /* yaml value starts with digit, '+' or '-' character, so it is of type number or integer */
			boolean b_decimalDot = false;
			int i_amountPlusSign = 0;
			int i_amountMinusSign = 0;
			
			/* iterate value in detail */
			for (int i = 0; i < p_s_yamlValue.length(); i++) {
				/* check if we found a decimal point */
				if (p_s_yamlValue.charAt(i) == '.') {
					if (b_decimalDot) {
						/* if we already found a decimal point - number format is invalid */
						throw new IllegalArgumentException("Invalid number format, found second decimal point in value [" + p_s_yamlValue + "]");
					} else if ( ( (i == 1) && (!Character.isDigit(p_s_yamlValue.charAt(0))) ) || (i == p_s_yamlValue.length() - 1) ) {
						/* if decimal point has not a single previous digit or is at the end of the value - number format is invalid */
						throw new IllegalArgumentException("Invalid number format, decimal point at wrong position in value [" + p_s_yamlValue + "]");
					} else {
						/* set flag, that decimal point has been found */
						b_decimalDot = true;
					}
				} else if (p_s_yamlValue.charAt(i) == '+') {
					i_amountPlusSign++;
					
					if (i_amountPlusSign > 1) { /* exit number check, it seems to be a normal string starting with '++...' */
						break;
					}
				} else if (p_s_yamlValue.charAt(i) == '-') {
					i_amountMinusSign++;
					
					if (i_amountMinusSign > 1) { /* exit number check, it seems to be a normal string starting with '--...' */
						break;
					}
				} else if ( (i != 0) && (!Character.isDigit(p_s_yamlValue.charAt(i))) ) { /* check if we found a character which is not a digit */
					/* we do not need an exception, just setting both sign counters to 1 and exit number check */
					i_amountPlusSign = 1;
					i_amountMinusSign = 1;
					break;
				}
			}
			
			/* only accept one plus-sign or one minus-sign, otherwise it is a normal string */
			if ( ( (i_amountPlusSign == 0) && (i_amountMinusSign == 0) ) || (i_amountPlusSign == 1) ^ (i_amountMinusSign == 1) ) {
				if (b_decimalDot) {
					/* decimal point found, value is of type number */
					e_yamlValueType = YAMLValueType.Number;
				} else {
					/* value is of type integer */
					e_yamlValueType = YAMLValueType.Integer;
				}
			}
		} else if (p_s_yamlValue.contentEquals("true") || p_s_yamlValue.contentEquals("false")) { /* value equals 'true' or 'false', so it is of type boolean */
			e_yamlValueType = YAMLValueType.Boolean;
		} else if (p_s_yamlValue.contentEquals("null")) { /* value equals 'null', so it is of type object */
			e_yamlValueType = YAMLValueType.Object;
		} else if ( (p_s_yamlValue.startsWith("[")) && (p_s_yamlValue.endsWith("]")) ) { /* yaml value starts with '[' character and ends with ']' character, so it is of type array */
			e_yamlValueType = YAMLValueType.Array;
		}
		
		return e_yamlValueType;
	}
	
	/**
	 * Parse yaml file lines to yaml object structure, based on YAMLElement and YAMLRestriction
	 * 
	 * @param p_i_min							pointer where to start line iteration
	 * @param p_i_max							pointer where to end line iteration
	 * @param p_a_lines							yaml file lines
	 * @param p_i_whiteSpaceindentation			amount of white space indentation in yaml file
	 * @param p_o_yamlElement					yaml element object where yaml information will be parsed
	 * @return									parsed yaml element object
	 * @throws IllegalArgumentException			value or structure within yaml file lines invalid
	 * @throws NullPointerException				value within yaml file lines missing or min. amount not available
	 */
	public YAMLElement parseYAML(int p_i_min, int p_i_max, java.util.List<String> p_a_lines, int p_i_whiteSpaceindentation, YAMLElement p_o_yamlElement) throws IllegalArgumentException, NullPointerException {
		return this.parseYAML(p_i_min, p_i_max, p_a_lines, p_i_whiteSpaceindentation, p_o_yamlElement, false);
	}
	
	/**
	 * Parse yaml file lines to yaml object structure, based on YAMLElement and YAMLRestriction
	 * 
	 * @param p_i_min							pointer where to start line iteration
	 * @param p_i_max							pointer where to end line iteration
	 * @param p_a_lines							yaml file lines
	 * @param p_i_whiteSpaceindentation			amount of white space indentation in yaml file
	 * @param p_o_yamlElement					yaml element object where yaml information will be parsed
	 * @param p_b_parseSchema					true - parsing yaml file lines from a yaml schema, false - parsing yaml file lines from a data file
	 * @return									parsed yaml element object
	 * @throws IllegalArgumentException			value or structure within yaml file lines invalid
	 * @throws NullPointerException				value within yaml file lines missing or min. amount not available
	 */
	public YAMLElement parseYAML(int p_i_min, int p_i_max, java.util.List<String> p_a_lines, int p_i_whiteSpaceindentation, YAMLElement p_o_yamlElement, boolean p_b_parseSchema) throws IllegalArgumentException, NullPointerException {
		/* temp variables for key and value */
		String s_key = "null";
		String s_value = "null";
		String s_reference = null;
		boolean b_collectionLevelFlag = false;
		YAMLElement o_yamlOldCurrentLevelElement = null;
		
		/* store current level yaml element in variable */
		YAMLElement o_yamlCurrentLevelElement = p_o_yamlElement;
		
		/* iterate each line */
		for (int i_min = p_i_min; i_min < p_i_max; i_min++) {
			/* read line into string */
			String s_line = p_a_lines.get(i_min);
			
													de.forestj.lib.Global.ilogFiner(s_line);
			
			/* check if line has any content */
			if ( (s_line.length() == 0) || (s_line.contentEquals("...")) ) {
														de.forestj.lib.Global.ilogFiner("(" + (i_min + 1) + ")|NULL|");
				
				/* skip lines with no content */
				continue;
			}

			if (p_i_whiteSpaceindentation >= s_line.length()) {
				throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Invalid indentation, yaml syntax error");
			}
			
			/* check if line starts with white space on expected position */
			if (s_line.charAt(p_i_whiteSpaceindentation) == ' ') {
														de.forestj.lib.Global.ilogFiner(s_line);
				
				/* check if we already have a value from previous line */
				if (!s_value.contentEquals("null")) {
					/* new indentation with previous value only allowed if value was reference or text block */
					if ( (s_value.charAt(0) != '&') && (!s_value.contentEquals("|")) && (!s_value.contentEquals(">")) ) {
						throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): New indentation found, but parent element has already a value");
					}
				}
				
				int i = 0;
				
				/* count white spaces */
				while (s_line.charAt(i) == ' ') {
					i++;
				}
				
				int i_max = -1;
				
				/* search for end of indentation block */
				for (i_max = i_min + 1; i_max < p_i_max; i_max++) {
					/* read line */
					String s_lineTemp = p_a_lines.get(i_max);
					
					/* check if line has any content */
					if ( (s_lineTemp.length() == 0) || (s_lineTemp.contentEquals("...")) ) {
						/* skip lines with no content */
						continue;
					}
					
					int j = 0;
					
					/* count white spaces */
					while (s_lineTemp.charAt(j) == ' ') {
						j++;
					}
					
					/* if counted white spaces matches expected position of indentation or there is a new collection element with '-'-sign as prefix */
					if ( (j == p_i_whiteSpaceindentation) || ((p_i_whiteSpaceindentation >= 2) && (s_lineTemp.charAt(p_i_whiteSpaceindentation - 2) == '-') && (s_lineTemp.charAt(p_i_whiteSpaceindentation - 1) == ' ')) ) {
						/* break - end of indentation block found */
						break;
					}
				}
				
				/* increase level for PrintIndentation */
				this.i_level++;
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "block goes from (" + (i_min + 1) + ") to (" + (i_max) + ") with indentation '" + i + "'");
				
				/* start new recursion with new indentation block */
				YAMLElement o_returnRecursion = this.parseYAML(i_min, i_max, p_a_lines, i, p_o_yamlElement, p_b_parseSchema);
				
				if (s_reference != null) {
															de.forestj.lib.Global.ilogFiner(this.printIndentation() + "store reference: " + s_reference);
					o_returnRecursion.setName(s_reference);
					this.a_references.add(o_returnRecursion.getName());
					s_reference = null;
				}
				
				/* decrease level for PrintIndentation */
				this.i_level--;
				
				/* overwrite line pointer and continue for loop after indentation block */
				i_min = i_max - 1;
				continue;
			}

			String s_originalLine = s_line;
			
			/* cut white space indentation of line */
			if (p_i_whiteSpaceindentation > 0) {
				s_line = s_line.substring(p_i_whiteSpaceindentation);
			}
			
			int i_amountColon = 0;
			int i_lineLevel = 0;
			int i_lineLevelSequences = 0;
			boolean b_inQuotation = false;
			boolean b_escapeCharacterBefore = false;
			
			/* check for multiple colon characters in line */
			for (int i = 0; i < s_line.length(); i++) {
				if (s_line.charAt(i) == '{') {
					i_lineLevel++;
				} else if (s_line.charAt(i) == '}') {
					i_lineLevel--;
				} else if (s_line.charAt(i) == '[') {
					i_lineLevelSequences++;
				} else if (s_line.charAt(i) == ']') {
					i_lineLevelSequences--;
				} else if ( (s_line.charAt(i) == '"') && (!b_escapeCharacterBefore) ) {
					if (b_inQuotation) {
						b_inQuotation = false;
					} else {
						b_inQuotation = true;
					}
				} else if ( (b_inQuotation) && (s_line.charAt(i) == '\\') ) {
					b_escapeCharacterBefore = true;
				} else if ( (s_line.charAt(i) == ':') && (i_lineLevel == 0) && (!b_inQuotation) ) {
					i_amountColon++;
				}
				
				if ( (b_inQuotation) && (s_line.charAt(i) != '\\') ) {
					b_escapeCharacterBefore = false;
				}
			}
			
			if (i_lineLevel != 0) {
				throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Invalid nesting with curly brackets '{', '}'");
			}
			
			if (i_lineLevelSequences != 0) {
				throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Invalid collection array with squared brackets '[', ']'");
			}
			
			if (i_amountColon > 1) {
				throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): A line can only contain one colon ':' character");
			}
			
			s_key = "null";
			s_value = "null";
			boolean b_newElementForCollection = false;
			
			/* if there is no colon we only have a value in line */
			if ( (i_amountColon == 0) ) {
				/* assume whole line as value */
				s_value = s_line;
				
				/* if line starts with a colon we have a new collection element */
				if (s_line.startsWith("- ")) {
					/* cut colon with white space out of line */
					s_value = s_value.substring(2);
					
					/* set new collection element flag */
					b_newElementForCollection = true;
				} else {
					if (!( ( (s_value.startsWith("{")) && (s_value.endsWith("}")) ) )) {
						throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): A line which is not a key value pair, not a collection element, no inline-object and not part of a text block is not allowed");
					}
				}
			} else if (i_amountColon == 1) {
				/* split key value before colon and remove all white spaces between key value and colon */
				s_key = s_line.substring(0, s_line.indexOf(':')).replaceAll("\\s+$", "");
				
				/* if line starts with a colon we have a new collection element */
				if (s_key.startsWith("- ")) {
					/* cut colon with white space out of line */
					s_key = s_key.substring(2);
					
					/* increase indentation deep */
					p_i_whiteSpaceindentation = p_i_whiteSpaceindentation + 2;
					
					/* set new collection element flag */
					b_newElementForCollection = true;
				} else if ( (p_i_whiteSpaceindentation >= 2) && (s_originalLine.charAt(p_i_whiteSpaceindentation - 2) == '-') && (s_originalLine.charAt(p_i_whiteSpaceindentation - 1) == ' ') ) {
					/* if there is a new collection element with '-'-sign as prefix, set new collection element flag */
					b_newElementForCollection = true;
				}
				
				/* split value after colon */
				s_value = s_line.substring(s_line.indexOf(':') + 1);
			}
			
			/* trim key and value */
			s_key = s_key.trim();
			s_value = s_value.trim();
			
			if (!java.util.regex.Pattern.matches("[a-zA-Z0-9-_]*", s_key)) {
				throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Invalid key '" + s_key + "', invalid characters. Following characters are allowed: [a-z], [A-Z], [0-9], [-] and [_]");
			}
			
			/* if value is not 'NULL' and 'length > 0' */
			if ( (!s_value.contentEquals("null")) && (!s_value.contentEquals(this.s_stringQuote + this.s_stringQuote)) && (s_value.length() > 0) ) {
				java.util.List<Character> a_reservedCharacters = java.util.Arrays.asList(':', '}', ']', ',', '#', '?', '-', '<', '>', '|', '=', '!', '%', '@', '\\', '\'', this.s_stringQuote.toCharArray()[0]);
				
				/* remove surrounding string quote characters */
				if ( (s_value.startsWith(this.s_stringQuote)) && (s_value.endsWith(this.s_stringQuote)) ) {
					if ( (!p_b_parseSchema) && (!s_value.contentEquals("\"null\"")) ) {
						s_value = s_value.substring(1, s_value.length() - 1);
					}
				} else if (s_value.contentEquals("[]")) { /* check for empty collection value */
					s_value = "null";
				} else if (a_reservedCharacters.contains(s_value.charAt(0))) { /* check for invalid characters on value's first position */
					if (!( (de.forestj.lib.Helper.isInteger(s_value)) || (de.forestj.lib.Helper.isDouble(s_value)) )) { /* leading '-' is valid if value is integer or double */
						throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Value starts with invalid character '" + s_value.charAt(0) + "'");
					}
				}
				
				if (s_value.charAt(0) == '[') {
					/* must end with ']' */
					if (!s_value.endsWith("]")) {
						throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Inline collection starts with character '" + s_value.charAt(0) + "' must end with character ']', but ends with '" + s_value.charAt(s_value.length() - 1) + "'");
					}
				} else if (s_value.charAt(0) == '{') {
					/* must end with '}' */
					if (!s_value.endsWith("}")) {
						throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Inline object starts with character '" + s_value.charAt(0) + "' must end with character '}', but ends with '" + s_value.charAt(s_value.length() - 1) + "'");
					}
				} else if ( (s_value.charAt(0) == '&') || (s_value.contentEquals("|")) || (s_value.contentEquals(">")) ) {
					/* next line must be higher level, not same level, not lower level */
					String s_lineTemp = "";
					int i_tempMin = i_min;
					
					do {
						if (++i_tempMin >= p_i_max) {
							if (s_value.charAt(0) == '&') {
								throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Reached end of block without any subordinate value of reference start");
							} else if ( (s_value.contentEquals("|")) || (s_value.contentEquals(">")) ) {
								throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Reached end of block without any subordinate value of textblock start");
							}
						}
						
						/* read next line into string */
						s_lineTemp = p_a_lines.get(i_tempMin);
						
						/* check if line has any content */
					} while ( (s_lineTemp.length() == 0) || (s_lineTemp.contentEquals("...")) );
					
					int i = 0;
					
					/* count white spaces */
					while (s_lineTemp.charAt(i) == ' ') {
						i++;
					}
					
					if (i <= p_i_whiteSpaceindentation) {
						if (s_value.charAt(0) == '&') {
							throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Next line after reference start is not subordinate");
						} else if ( (s_value.contentEquals("|")) || (s_value.contentEquals(">")) ) {
							throw new IllegalArgumentException("Error in line (" + (i_min + 1) + "): Next line after textblock start is not subordinate");
						}
					}
					
					if (s_value.charAt(0) == '&') {
						s_reference = s_value.substring(1);
					}
				} else if (s_value.charAt(0) == '*') {
					/* check if reference is stored */
					String s_referenceValue = s_value.substring(1);
					String s_referenceName = null;
					
					for (String s_yamlReference : this.a_references) {
						if (s_yamlReference.contentEquals(s_referenceValue)) {
							s_referenceName = s_yamlReference;
						}
					}
					
					if (s_referenceName == null) {
						throw new NullPointerException("Error in line (" + (i_min + 1) + "): Reference '" + s_value + "' not found in yaml-schema-document");
					}
				}
			} else {
				if (s_value.contentEquals(this.s_stringQuote + this.s_stringQuote)) {
					/* set value empty string if 'value = ""' */
					s_value = "";
				} else {
					/* set value 'NULL' if 'length = 0' */
					s_value = "null";
				}
			}
			
			if ( (b_newElementForCollection) && (!p_b_parseSchema) ) {
				if ( (!b_collectionLevelFlag) || (!s_key.contentEquals("null")) ) {
					if (!s_key.contentEquals("null")) {
						if (o_yamlOldCurrentLevelElement != null) {
							o_yamlCurrentLevelElement = o_yamlOldCurrentLevelElement;
							o_yamlOldCurrentLevelElement = null;
							
							this.i_level--;
							
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "array object before is finished after new element for collection appeared and array level flag is still 'true'");
						}
					}
						
					b_collectionLevelFlag = true;
					
															de.forestj.lib.Global.ilogFiner(this.printIndentation() + "new array object");
					
					o_yamlOldCurrentLevelElement = o_yamlCurrentLevelElement;
					
					YAMLElement o_yamlArrayElement = new YAMLElement("__ArrayObject__", this.i_level);
					
					/* add new element to current element children and set as new current element */
					o_yamlCurrentLevelElement.getChildren().add(o_yamlArrayElement);
					o_yamlCurrentLevelElement = o_yamlArrayElement;
					
					this.i_level++;
				}
			}
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "(" + (i_min + 1) + ")|key=" + s_key + "|value=" + s_value + "|" + b_newElementForCollection + "|");
			
			YAMLElement o_yamlElement = new YAMLElement(s_key, this.i_level);
			
			if (!s_value.contentEquals("null")) {
				o_yamlElement.setValue(s_value);
			}
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + o_yamlCurrentLevelElement.getName() + ".getChildren().add(" + o_yamlElement.getName() + ");");
			o_yamlCurrentLevelElement.getChildren().add(o_yamlElement);
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "p_o_yamlElement = " + o_yamlElement.getName() + ";");
			p_o_yamlElement = o_yamlElement;
		}
		
		if ( (b_collectionLevelFlag) && (!p_b_parseSchema) ) {
			b_collectionLevelFlag = false;
			
			o_yamlCurrentLevelElement = o_yamlOldCurrentLevelElement;
			o_yamlOldCurrentLevelElement = null;
			
			this.i_level--;
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "array object before is finished after iteration");
		}
		
		if (p_o_yamlElement.getName().contentEquals(o_yamlCurrentLevelElement.getName())) {
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "return " + p_o_yamlElement.getName());
			return p_o_yamlElement;
		} else {
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "return " + o_yamlCurrentLevelElement.getName());
			return o_yamlCurrentLevelElement;
		}
	}

	/**
	 * Parse yaml schema elements with all children elements
	 * 
	 * @param p_o_yamlSchemaElement				yaml schema element object which will be parsed
	 * @throws IllegalArgumentException			value or type within yaml schema invalid
	 * @throws NullPointerException				value within yaml schema missing or min. amount not available
	 */
	private void parseYAMLSchemaElements(YAMLElement p_o_yamlSchemaElement) throws IllegalArgumentException, NullPointerException {
		if (p_o_yamlSchemaElement.getChildren().size() > 0) {
			boolean b_array = false;
			boolean b_object = false;
			boolean b_properties = false;
			boolean b_items = false;
			
			/* 
			 * check if we have "type": "array" and "items" and no "properties"
			 * or if we have "type": "object" and "properties" and no "items"
			 */
			for (YAMLElement o_yamlChild : p_o_yamlSchemaElement.getChildren()) {
				if (o_yamlChild.getName().toLowerCase().contentEquals("type")) {
					String s_type = o_yamlChild.getValue();
					
					/* remove surrounded double quotes from value */
					if ( (s_type.startsWith(this.s_stringQuote)) && (s_type.endsWith(this.s_stringQuote)) ) {
						s_type = s_type.substring(1, s_type.length() - 1);
					}
					
					if (s_type.contentEquals("array")) {
						b_array = true;
					} else if (s_type.contentEquals("object")) {
						b_object = true;
					}
				} else if (o_yamlChild.getName().toLowerCase().contentEquals("properties")) {
					b_properties = true;
				} else if (o_yamlChild.getName().toLowerCase().contentEquals("items")) {
					b_items = true;
				}
			}
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "element: '" + p_o_yamlSchemaElement.getName() + "' - array=" + b_array + "|object=" + b_object + "|properties=" + b_properties + "|items=" + b_items + "|");
			
			/* control result of check */
			if ( (!b_array) && (!b_object) ) {
				if ( (this.i_level == 0) && (!p_o_yamlSchemaElement.getName().toLowerCase().contentEquals("definitions")) && (!p_o_yamlSchemaElement.getName().toLowerCase().contentEquals("properties")) ) {
					throw new IllegalArgumentException("YAML definition of element[definitions] or [properties] necessary on first level for [" + p_o_yamlSchemaElement.getName() + "]");
				}
			} else if ( (b_array) && (b_properties) ) {
				throw new IllegalArgumentException("YAML definition with type[array] cannot have [properties] at the same time");
			} else if ( (b_array) && (!b_items) ) {
				throw new IllegalArgumentException("YAML definition with type[array] must have [items] definition as well");
			} else if ( (b_object) && (b_items) ) {
				throw new IllegalArgumentException("YAML definition with type[object] cannot have [items] at the same time");
			} else if ( (b_object) && (!b_properties) ) {
				throw new IllegalArgumentException("YAML definition with type[object] must have [properties] definition as well");
			}
			
			for (YAMLElement o_yamlChild : p_o_yamlSchemaElement.getChildren()) {
				YAMLValueType e_yamlValueType;
				
				/* determine yaml value type */
				if (de.forestj.lib.Helper.isStringEmpty(o_yamlChild.getValue())) {
					e_yamlValueType = YAMLValueType.Object;
				} else {
					e_yamlValueType = this.getYAMLValueType(o_yamlChild.getValue());
					
					/* remove surrounded quote characters from value */
					if ( (o_yamlChild.getValue().startsWith(this.s_stringQuote)) && (o_yamlChild.getValue().endsWith(this.s_stringQuote)) ) {
						o_yamlChild.setValue(o_yamlChild.getValue().substring(1, o_yamlChild.getValue().length() - 1));
					}
					
					/* if value type is string and value starts with '&' character */
					if ( (e_yamlValueType == YAMLValueType.String) && (o_yamlChild.getValue().charAt(0) == '&') ) {
						/* look for stored reference */
						String s_referenceValue = o_yamlChild.getValue().substring(1);
						String s_referenceName = null;
						
						/* iterate reference store */
						for (String s_yamlReference : this.a_references) {
							if (s_yamlReference.contentEquals(s_referenceValue)) {
								s_referenceName = s_yamlReference;
								break;
							}
						}
						
						/* if we do not find reference, throw Exception */
						if (s_referenceName == null) {
							throw new NullPointerException("Invalid YAML reference[" + o_yamlChild.getValue() + "] for property[" + o_yamlChild.getName() + "], reference not found");
						} else { /* reference found */
							/* current yaml child is object */
							e_yamlValueType = YAMLValueType.Object;
							/* truncate value */
							o_yamlChild.setValue(null);
						}
					}
				}
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + o_yamlChild.getName() + "(" + e_yamlValueType + ") of " + p_o_yamlSchemaElement.getName());
				
				/* special properties at level 0 of yaml schema only */
				if (o_yamlChild.getLevel() == 0) {
					if ( (o_yamlChild.getName().toLowerCase().contentEquals("definitions")) || (o_yamlChild.getName().toLowerCase().contentEquals("properties")) ) {
						/* if yaml value type is of type object */
						if (e_yamlValueType == YAMLValueType.Object) {
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "save current element(" + this.o_currentElement.getName() + ") in temporary variable");
							
							/* save current element in temporary variable */
							YAMLElement o_oldCurrentElement = this.o_currentElement;

																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "start recursion for: " + o_yamlChild.getName() + " = (" + e_yamlValueType + ") for " + this.o_currentElement.getName());
							
							if (o_yamlChild.getName().toLowerCase().contentEquals("definitions")) {
								/* create new yaml element for 'definitions' */
								this.o_currentElement = new YAMLElement(o_yamlChild.getName(), 0);
								
								/* parse yaml value recursively */
								this.parseYAMLSchemaElements(o_yamlChild);
								
								/* process current element as return value */
								this.o_definitions = this.o_currentElement;
							} else if (o_yamlChild.getName().toLowerCase().contentEquals("properties")) {
								/* create new yaml element for 'properties' */
								this.o_currentElement = new YAMLElement(o_yamlChild.getName(), 0);
								
								/* parse yaml child recursively */
								this.parseYAMLSchemaElements(o_yamlChild);
								
								/* process current element as return value */
								this.o_properties = this.o_currentElement;
							}
							
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "end recursion for " + this.o_currentElement.getName() + ": " + o_yamlChild.getName() + " = (" + e_yamlValueType + ")");
							
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "reset current element(" + this.o_currentElement.getName() + ") from temporary variable(" + o_oldCurrentElement.getName() + ")");
							
							/* reset current element from temporary variable */
							this.o_currentElement = o_oldCurrentElement;
							
							/* we can skip the rest of loop processing */
							continue;
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] of type object");
						}
					}
				}
				
				/* default parsing of properties for yaml schema */
				
				/* if current element is another object we need another recursion */
				if (e_yamlValueType == YAMLValueType.Object) {
					boolean b_skip = false;
					
					/* check if we have a required colletion which is not stored in one line */
					if (o_yamlChild.getName().toLowerCase().contentEquals("required")) {
						if (o_yamlChild.getType() == null) {
							if (o_yamlChild.getChildren().size() > 0) {
								for (YAMLElement o_yamlRequiredValue : o_yamlChild.getChildren()) {
									boolean b_nameIsNull = ( (o_yamlRequiredValue.getName() == null) || (o_yamlRequiredValue.getName() == "null") );
									boolean b_typeIsNull = ( (o_yamlRequiredValue.getType() == null) || (o_yamlRequiredValue.getType() == "null") );
									
									if ( (!b_nameIsNull) || (!b_typeIsNull) ) {
										throw new NullPointerException("Invalid YAML schema-element[" + o_yamlChild.getName() + "], child required value must not have name[" + o_yamlRequiredValue.getName() + "] and type[" + o_yamlRequiredValue.getType() + "] definition");
									} else if (o_yamlRequiredValue.getValue() == null) {
										throw new IllegalArgumentException("Invalid YAML schema-element[" + o_yamlChild.getName() + "], child required value has no value");
									} else {
										String s_requiredValue = o_yamlRequiredValue.getValue();
										
										/* trim required value */
										s_requiredValue = s_requiredValue.trim();
										
										boolean b_requiredFound = false;
										java.util.List<YAMLElement> a_children = null;
										
										/* check if we are at 'root' level */
										if ( (this.o_currentElement.getName().contentEquals("Root")) && (this.o_currentElement.getLevel() == 0) ) {
											/* look for 'properties' child */
											for (YAMLElement o_yamlCurrentElementChild : this.o_currentElement.getChildren()) {
												if (o_yamlCurrentElementChild.getName().toLowerCase().contentEquals("properties")) {
													/* set 'properties' children as array to search for 'required' element */
													a_children = o_yamlCurrentElementChild.getChildren();
												}
											}
										} else {
											/* set children of current element as array to search for 'required' element */
											a_children = this.o_currentElement.getChildren();
										}
										
										if (a_children == null) {
											throw new NullPointerException("Cannot handle required property[" + s_requiredValue + "] in array[" + o_yamlChild.getName() + "] because current element[" + this.o_currentElement.getName() + "] has no children or no properties");
										}
										
										/* iterate all children of current element to find required 'property' */
										for (YAMLElement o_yamlCurrentElementChild : a_children) {
											/* compare by property name */
											if (o_yamlCurrentElementChild.getName().toLowerCase().contentEquals(s_requiredValue.toLowerCase())) {
												b_requiredFound = true;
												o_yamlCurrentElementChild.setRequired(true);
												break;
											}
										}
										
										if (!b_requiredFound) {
											throw new IllegalArgumentException("Required property[" + s_requiredValue + "] in array[" + o_yamlChild.getName() + "] does not exist within 'properties'");
										}
									}
								}
								
								b_skip = true;
							} else {
								throw new IllegalArgumentException("Invalid YAML schema-element[" + o_yamlChild.getName() + "] has no children");
							}
						} else {
							throw new IllegalArgumentException("Invalid YAML schema-element[" + o_yamlChild.getName() + "]. Must have type null, but type is '" + o_yamlChild.getType() + "'");
						}
					}
					
					/* skip because of 'items' object with one reference */
					if (!b_skip) { /* new object */
																de.forestj.lib.Global.ilogFiner(this.printIndentation() + o_yamlChild.getName() + " = (" + e_yamlValueType + ")");
						
						if (o_yamlChild.getName().toLowerCase().contentEquals("properties")) {
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "start recursion for: " + o_yamlChild.getName() + " = (" + e_yamlValueType + ") for " + this.o_currentElement.getName());
							
							/* parse yaml child recursively */
							this.parseYAMLSchemaElements(o_yamlChild);
							
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "end recursion for: " + o_yamlChild.getName() + " = (" + e_yamlValueType + ")");
						} else {
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "save current element(" + this.o_currentElement.getName() + ") in temporary variable");
							
							/* save current element in temporary variable */
							YAMLElement o_oldCurrentElement = this.o_currentElement;
							
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "start recursion for: " + o_yamlChild.getName() + " = (" + e_yamlValueType + ") for " + this.o_currentElement.getName());
							
							/* create new yaml element */
							YAMLElement o_newYAMLElement = new YAMLElement(o_yamlChild.getName(), this.i_level);
							
							/* if we have Root node as current element on level 0 with type 'array' and new child 'items', we must not add a new child, because of concurrent modificication of the for loop */
							boolean b_handleRootItems = false;
							
							if ( (this.o_currentElement.getName().contentEquals("Root")) && (this.o_currentElement.getLevel() == 0) && (this.o_currentElement.getType().toLowerCase().contentEquals("array")) && (o_newYAMLElement.getName().toLowerCase().contentEquals("items")) ) {
								b_handleRootItems = true;
							} else {
								/* add new yaml element to current elements children */
								this.o_currentElement.getChildren().add(o_newYAMLElement);
							}
							
							/* set new yaml element as current element for recursive processing */
							this.o_currentElement = o_newYAMLElement;
							
							/* increase level for PrintIndentation */
							this.i_level++;
							
							/* parse yaml child recursively */
							this.parseYAMLSchemaElements(o_yamlChild);
							
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "reset current element(" + this.o_currentElement.getName() + ") from temporary variable(" + o_oldCurrentElement.getName() + ")");
								
							/* reset current element from temporary variable */
							this.o_currentElement = o_oldCurrentElement;
							
							/* check if we must handle root items */
							if (b_handleRootItems) {
								/* new element 'items', we set the child as reference of Root */
								if (o_newYAMLElement.getChildren().size() == 1) {
																			de.forestj.lib.Global.ilogFiner(this.printIndentation() + "setReference for (" + this.o_currentElement.getName() + ") with object(" + o_newYAMLElement.getChildren().get(0).getName() + ")");
									
									this.o_currentElement.setReference(o_newYAMLElement.getChildren().get(0));
								} else {
									throw new IllegalArgumentException("Root items must have only one child, but there are (" + o_newYAMLElement.getChildren().size() + ") children");
								}
							}
							
							/* decrease level for PrintIndentation */
							this.i_level--;
							
							/* between update of schema definitions, for the case that a definition is depending on another definition before */
							if (this.o_currentElement.getName().toLowerCase().contentEquals("definitions")) {
								this.o_definitions = this.o_currentElement;
							}
							
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "end recursion for: " + o_yamlChild.getName() + " = (" + e_yamlValueType + ")");
						}
					}
				} else {
															de.forestj.lib.Global.ilogFiner(this.printIndentation() + o_yamlChild.getName() + " = " + o_yamlChild.getValue() + " (" + e_yamlValueType + ") for " + this.o_currentElement.getName());
					
					if (o_yamlChild.getValue().charAt(0) == '*') {
						if (e_yamlValueType == YAMLValueType.String) {
							String s_reference = o_yamlChild.getValue().substring(1);
							boolean b_found = false;
							
							for (YAMLElement o_yamlDefinition : this.o_definitions.getChildren()) {
								if (o_yamlDefinition.getName().contentEquals(s_reference)) {
									if (o_yamlChild.getName().toLowerCase().contentEquals("items")) {
										/* add reference to current element */
										this.o_currentElement.setReference(o_yamlDefinition);
									} else {
										/* save current element in temporary variable */
										YAMLElement o_oldCurrentElement = this.o_currentElement;
										
										/* create new yaml element */
										YAMLElement o_newYAMLElement = new YAMLElement(o_yamlChild.getName(), this.i_level);
										
										/* add new yaml element to current elements children */
										this.o_currentElement.getChildren().add(o_newYAMLElement);
										
										/* set new yaml element as current element for recursive processing */
										this.o_currentElement = o_newYAMLElement;
										
										/* add reference to current element */
										this.o_currentElement.setReference(o_yamlDefinition);
										
										/* reset current element from temporary variable */
										this.o_currentElement = o_oldCurrentElement;
									}
									
									b_found = true;
									break;
								}
							}
							
							if (!b_found) {
								throw new IllegalArgumentException("YAML definition[" + s_reference + "] not found under /definitions in yaml schema");
							}
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("type")) {
						if (e_yamlValueType == YAMLValueType.String) {
							java.util.List<String> a_validTypes = java.util.Arrays.asList("string", "number", "integer", "boolean", "array", "object", "null");
							
							/* store type value in temp. variable */
							String s_foo = o_yamlChild.getValue();
							
							/* check if type value ends with '[]' */
							if (s_foo.endsWith("[]")) {
								/* delete '[]' from type value */
								s_foo = s_foo.substring(0, s_foo.length() - 2);
								
								/* set primitive array flag */
								this.o_currentElement.setPrimitiveArray(true);
							}
							
							/* check if we have a valid type value */
							if (!a_validTypes.contains(s_foo)) {
								throw new IllegalArgumentException("Invalid value[" + o_yamlChild.getValue() + "] for property[type], allowed values are " + a_validTypes);
							}
							
							/* store type value */
							this.o_currentElement.setType(s_foo);
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("description")) {
						if (e_yamlValueType == YAMLValueType.String) {
							this.o_currentElement.setDescription(o_yamlChild.getValue());
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("default")) {
						if (e_yamlValueType == YAMLValueType.String) {
							this.o_currentElement.setDefault(o_yamlChild.getValue());
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("mapping")) {
						if (e_yamlValueType == YAMLValueType.String) {
							if (o_yamlChild.getValue().contains(":")) { /* set mapping and mappingClass */
								this.o_currentElement.setMapping(o_yamlChild.getValue().substring(0, o_yamlChild.getValue().indexOf(":")));
								this.o_currentElement.setMappingClass(o_yamlChild.getValue().substring(o_yamlChild.getValue().indexOf(":") + 1, o_yamlChild.getValue().length()));
							} else { /* set only mappingClass */
								this.o_currentElement.setMappingClass(o_yamlChild.getValue());
							}
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("maxitems")) {
						if (e_yamlValueType == YAMLValueType.Integer) {
							if (!this.o_currentElement.getType().toLowerCase().contentEquals("array")) {
								throw new IllegalArgumentException("Invalid YAML restriction[" + o_yamlChild.getName() + "] for [" + this.o_currentElement.getName() + "] with type[" + o_yamlChild.getType() + "], type must be array");
							}
							
							this.o_currentElement.getRestrictions().add( new YAMLRestriction(o_yamlChild.getName(), this.i_level, "", Integer.parseInt(o_yamlChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("minitems")) {
						if (e_yamlValueType == YAMLValueType.Integer) {
							if (!this.o_currentElement.getType().toLowerCase().contentEquals("array")) {
								throw new IllegalArgumentException(this.o_currentElement.getName()+" - Invalid YAML restriction[" + o_yamlChild.getName() + "] for [" + this.o_currentElement.getName() + "] with type[" + o_yamlChild.getType() + "], type must be array");
							}
							
							this.o_currentElement.getRestrictions().add( new YAMLRestriction(o_yamlChild.getName(), this.i_level, "", Integer.parseInt(o_yamlChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("minimum")) {
						if (e_yamlValueType == YAMLValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new YAMLRestriction(o_yamlChild.getName(), this.i_level, "", Integer.parseInt(o_yamlChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("exclusiveminimum")) {
						if (e_yamlValueType == YAMLValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new YAMLRestriction(o_yamlChild.getName(), this.i_level, "", Integer.parseInt(o_yamlChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("maximum")) {
						if (e_yamlValueType == YAMLValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new YAMLRestriction(o_yamlChild.getName(), this.i_level, "", Integer.parseInt(o_yamlChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("exclusivemaximum")) {
						if (e_yamlValueType == YAMLValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new YAMLRestriction(o_yamlChild.getName(), this.i_level, "", Integer.parseInt(o_yamlChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("maxlength")) {
						if (e_yamlValueType == YAMLValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new YAMLRestriction(o_yamlChild.getName(), this.i_level, "", Integer.parseInt(o_yamlChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("minlength")) {
						if (e_yamlValueType == YAMLValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new YAMLRestriction(o_yamlChild.getName(), this.i_level, "", Integer.parseInt(o_yamlChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("pattern")) {
						if (e_yamlValueType == YAMLValueType.String) {
							this.o_currentElement.getRestrictions().add( new YAMLRestriction(o_yamlChild.getName(), this.i_level, o_yamlChild.getValue()) );
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					} else if (o_yamlChild.getName().toLowerCase().contentEquals("required")) {
						if (e_yamlValueType == YAMLValueType.Array) {
							/* check if current element has any children */
							if (this.o_currentElement.getChildren().size() < 1) {
								throw new NullPointerException("Current element[" + this.o_currentElement.getName() + "] must have at least one child for assigning 'required' property");
							}
							
							/* get yaml array */
							String s_array = o_yamlChild.getValue();
							
							/* check if array is surrounded with '[' and ']' characters */
							if ( (!s_array.startsWith("[")) || (!s_array.endsWith("]")) ) {
								throw new IllegalArgumentException("Invalid format for YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "], must start with '[' and end with ']'");
							}
							
							/* remove surrounding '[' and ']' characters */
							s_array = s_array.substring(1, s_array.length() - 1);
							
							/* split array in its values */
							String[] a_arrayValues = s_array.split(",");
							
							/* iterate each array value */
							for (String s_arrayValue : a_arrayValues) {
								/* check if array value is surrounded with quote characters */
								if ( (s_arrayValue.startsWith(this.s_stringQuote)) && (s_arrayValue.endsWith(this.s_stringQuote)) ) {
									/* remove surrounding quote characters */
									s_arrayValue = s_arrayValue.substring(1, s_arrayValue.length() - 1);
								}
								
								/* trim required value */
								s_arrayValue = s_arrayValue.trim();
								
								boolean b_requiredFound = false;
								java.util.List<YAMLElement> a_children = null;
								
								/* check if we are at 'root' level */
								if ( (this.o_currentElement.getName().contentEquals("Root")) && (this.o_currentElement.getLevel() == 0) ) {
									/* look for 'properties' child */
									for (YAMLElement o_yamlCurrentElementChild : this.o_currentElement.getChildren()) {
										if (o_yamlCurrentElementChild.getName().toLowerCase().contentEquals("properties")) {
											/* set 'properties' children as array to search for 'required' element */
											a_children = o_yamlCurrentElementChild.getChildren();
										}
									}
								} else {
									/* set children of current element as array to search for 'required' element */
									a_children = this.o_currentElement.getChildren();
								}
								
								if (a_children == null) {
									throw new NullPointerException("Cannot handle required property[" + s_arrayValue + "] in array[" + o_yamlChild.getName() + "] because current element[" + this.o_currentElement.getName() + "] has no children or no properties");
								}
								
								/* iterate all children of current element to find required 'property' */
								for (YAMLElement o_yamlCurrentElementChild : a_children) {
									/* compare by property name */
									if (o_yamlCurrentElementChild.getName().toLowerCase().contentEquals(s_arrayValue.toLowerCase())) {
										b_requiredFound = true;
										o_yamlCurrentElementChild.setRequired(true);
										break;
									}
								}
								
								if (!b_requiredFound) {
									throw new IllegalArgumentException("Required property[" + s_arrayValue + "] in array[" + o_yamlChild.getName() + "] does not exist within 'properties'");
								}
							}
						} else {
							throw new IllegalArgumentException("Invalid YAML type[" + e_yamlValueType + "] for property[" + o_yamlChild.getName() + "] with value[" + o_yamlChild.getValue() + "]");
						}
					}
				}
			}
		}
	}
	
	/* encoding data to YAML with YAML schema */
	
	/**
	 * Encode java object to a yaml file, keep existing yaml file
	 * 
	 * @param p_o_object					source java object to encode yaml information		
	 * @param p_s_yamlFile					destination yaml file to save encoded yaml information
	 * @return								file object with encoded yaml content
	 * @throws java.io.IOException			cannot create or access destination yaml file
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding yaml correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public File yamlEncode(Object p_o_object, String p_s_yamlFile) throws java.io.IOException, NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		return this.yamlEncode(p_o_object, p_s_yamlFile, false);
	}
	
	/**
	 * Encode java object to a yaml file
	 * 
	 * @param p_o_object					source java object to encode yaml information		
	 * @param p_s_yamlFile					destination yaml file to save encoded yaml information
	 * @param p_b_overwrite					true - overwrite existing yaml file, false - keep existing yaml file
	 * @return								file object with encoded yaml content
	 * @throws java.io.IOException			cannot create or access destination yaml file
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding yaml correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public File yamlEncode(Object p_o_object, String p_s_yamlFile, boolean p_b_overwrite) throws java.io.IOException, NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		/* encode data to yaml content string */
		String s_yaml = this.yamlEncode(p_o_object);
		
												if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("Encoded YAML:" + this.s_lineBreak + s_yaml);
		
		/* if file does not exist we must create an new file */
		if (!File.exists(p_s_yamlFile)) {
			if (p_b_overwrite) {
				p_b_overwrite = false;
			}
		}
		
		/* open (new) file */
		de.forestj.lib.io.File o_file = new de.forestj.lib.io.File(p_s_yamlFile, !p_b_overwrite);
		
		/* save yaml encoded data into file */
		o_file.replaceContent(s_yaml);
		
		/* return file object */
		return o_file;
	}
	
	/**
	 * Encode java object to a yaml content string
	 * 
	 * @param p_o_object					source java object to encode yaml information				
	 * @return								encoded yaml information from java object as string
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding yaml correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public String yamlEncode(Object p_o_object) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		/* check schema field */
		if (this.o_schema == null) {
			throw new NullPointerException("Cannot encode data. Schema is null.");
		}
		
		/* set level for PrintIndentation to zero */
		this.i_level = 0;
		
		/* init flag for indentation mode if first level has only collection elements */
		this.b_firstLevelCollectionElementsOnly = false;
		
		/* encode data to yaml recursive */
		String s_yaml = "---" + this.s_lineBreak + this.yamlEncodeRecursive(this.o_schema, p_o_object, false) + "...";
		
												if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("Encoded YAML:" + this.s_lineBreak + s_yaml);
		
		/* return yaml content string */
		return s_yaml;
	}
	
	/**
	 * Recursive method to encode java object and it's fields to a yaml string
	 * 
	 * @param p_o_yamlSchemaElement			current yaml schema element with additional information for encoding
	 * @param p_o_object					source java object to encode yaml information
	 * @param p_b_parentIsCollection		hint that the parent yaml element is an array collection
	 * @return								encoded yaml information from java object as string
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding yaml correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	private String yamlEncodeRecursive(YAMLElement p_o_yamlSchemaElement, Object p_o_object, boolean p_b_parentIsCollection) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		String s_yaml = "";
		String s_yamlParentName = "";
		
		/* if type and mapping class are not set, we need at least a reference to continue */
		if ( (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getType())) && (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMappingClass())) ) {
			if (p_o_yamlSchemaElement.getReference() == null) {
				throw new NullPointerException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no type, no mapping class and no reference");
			} else {
				/* save name of current schema-element */
				s_yamlParentName = p_o_yamlSchemaElement.getName();
				
				/* set reference as current schema-element */
				p_o_yamlSchemaElement = p_o_yamlSchemaElement.getReference();
			}
		}
		
		/* check if type is set */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getType())) {
			throw new NullPointerException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no type");
		}
		
		/* check if mapping class is set if schema-element is not 'items' */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMappingClass())) {
			if (!p_o_yamlSchemaElement.getName().toLowerCase().contentEquals("items")) {
				throw new NullPointerException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no mapping class");
			}
		}

		if (p_o_yamlSchemaElement.getType().toLowerCase().contentEquals("object")) {
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "cast schema-object(" + p_o_yamlSchemaElement.getName() + ") with schema-mapping(" + p_o_yamlSchemaElement.printMapping() + ") and p_o_object(" + p_o_object.getClass().getTypeName() + "), castonly=" + (p_o_yamlSchemaElement.getMappingClass().contentEquals(p_o_object.getClass().getTypeName())));
			
			Object o_object = null;
			
			if (p_o_object != null) {
				/* cast object of p_o_object */
				o_object = this.castObject(p_o_yamlSchemaElement, p_o_object, p_o_yamlSchemaElement.getMappingClass().contentEquals(p_o_object.getClass().getTypeName()));
			}
			
			/* check if casted object is null */
			if (o_object == null) {
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "casted schema-object(" + p_o_yamlSchemaElement.getName() + ") with schema-mapping(" + p_o_yamlSchemaElement.printMapping() + ") is null");
				
				/* add object null value to yaml output */
				if ( (this.i_level == 0) || (p_b_parentIsCollection) ) {
					s_yaml += "null" + this.s_lineBreak;
				} else {
					/* check if a parent name for this reference is set */
					if (!de.forestj.lib.Helper.isStringEmpty(s_yamlParentName)) {
						s_yaml += this.printIndentation() + s_yamlParentName + ": null" + this.s_lineBreak;
					} else {
						s_yaml += this.printIndentation() + p_o_yamlSchemaElement.getName() + ": null" + this.s_lineBreak;
					}
				}
			} else {
				/* if parent is a collection */
				if (p_b_parentIsCollection) {
					/* increase level for PrintIndentation */
					this.i_level++;
					
					/* save name of current schema-element */
					s_yamlParentName = p_o_yamlSchemaElement.getName();
				}
				
				/* help variable to skip children iteration */
				boolean b_childrenIteration = true;
				
				/* check conditions for handling object */
				if (p_o_yamlSchemaElement.getReference() != null) {
					/* check if reference has any children */
					if (p_o_yamlSchemaElement.getReference().getChildren().size() < 1) {
						/* check if reference has another reference */
						if (p_o_yamlSchemaElement.getReference().getReference() == null) {
							throw new NullPointerException("Reference[" + p_o_yamlSchemaElement.getReference().getName() + "] of schema-element[" + p_o_yamlSchemaElement.getName() + "] has no children and no other reference");
						} else {
							b_childrenIteration = false;
	
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-object(" + p_o_yamlSchemaElement.getName() + ") with its reference(" + p_o_yamlSchemaElement.getReference().getName() + ") and schema-mapping(" + p_o_yamlSchemaElement.printMapping() + ") which has another reference with recursion");
							
							/* handle reference with recursion */
							s_yaml += this.yamlEncodeRecursive(p_o_yamlSchemaElement.getReference(), o_object, false);
						}
					} else {
																de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-object(" + p_o_yamlSchemaElement.getName() + ") with its reference(" + p_o_yamlSchemaElement.getReference().getName() + ") which has children");
						
						/* set reference as current yaml element */
						p_o_yamlSchemaElement = p_o_yamlSchemaElement.getReference();
					}
				}
				
				/* execute children iteration */
				if (b_childrenIteration) {
					/* check if object has any children */
					if (p_o_yamlSchemaElement.getChildren().size() < 1) {
						throw new NullPointerException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no children");
					}
					
					/* bool flag for handle indentation and new colon for collection object */
					boolean b_once = false;
					
					/* temp variable for indentation string */
					String s_indentation = this.printIndentation();
					
					/* if parent is a collection */
					if (p_b_parentIsCollection) {
						/* add colon to YAML */
						s_yaml += s_indentation.substring(0, (s_indentation.length() - 2)) + "- ";
						
						/* set flag */
						b_once = true;
					} else if (this.i_level > 0) { /* if level is greater than zero */
						/* print object name */
						if (!de.forestj.lib.Helper.isStringEmpty(s_yamlParentName)) {
							s_yaml += this.printIndentation() + s_yamlParentName + ":" + this.s_lineBreak;
						} else {
							s_yaml += this.printIndentation() + p_o_yamlSchemaElement.getName() + ":" + this.s_lineBreak;
						}
						
						/* increase level for PrintIndentation */
						this.i_level++;
					}
					
					/* iterate all children of current yaml element */
					for (YAMLElement o_yamlElement : p_o_yamlSchemaElement.getChildren()) {
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "encode schema-object's(" + p_o_yamlSchemaElement.getName() + ") child(" + o_yamlElement.getName() + ") with schema-mapping(" + p_o_yamlSchemaElement.printMapping() + ") with recursion");
						
						/* handle child with recursion */
						String s_foo = this.yamlEncodeRecursive(o_yamlElement, o_object, false);
						
						/* if parent is a collection */
						if (b_once) {
							/* add recursive result, ignoring one indentation because of collection object */
							s_yaml += s_foo.substring(s_indentation.length());

							/* reset flag */
							b_once = false;
						} else {
							/* add recursive result */
							s_yaml += s_foo;
						}
					}
					
					/* if parent is not a collection and level is greater than 0 */
					if ( (!p_b_parentIsCollection) && (this.i_level > 0) ) {
						/*decrease level for PrintIndentation */
						this.i_level--;
					}
				}
				
				/* decrease level for PrintIndentation if parent is a collection */
				if (p_b_parentIsCollection) {
					this.i_level--;
				}
			}
		} else if (p_o_yamlSchemaElement.getType().toLowerCase().contentEquals("array")) {
			if (!de.forestj.lib.Helper.isStringEmpty(s_yamlParentName)) {
				s_yaml += this.printIndentation() + s_yamlParentName + ": " + this.s_lineBreak;
			} else if (
				(this.i_level > 0) ||
				( (p_o_yamlSchemaElement.getReference() != null) && (p_o_yamlSchemaElement.getReference().getType().toLowerCase().contentEquals("object")) && (p_o_yamlSchemaElement.getLevel() == p_o_yamlSchemaElement.getReference().getLevel()) && (!p_o_yamlSchemaElement.getName().contentEquals("Root")) ) ||
				( (p_o_yamlSchemaElement.getReference() == null) && (p_o_yamlSchemaElement.getChildren().size() == 1) && (p_o_yamlSchemaElement.getChildren().get(0).getName().toLowerCase().contentEquals("items")) && (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getChildren().get(0).getType())) && (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getChildren().get(0).getMappingClass())) )
			) {
				s_yaml += this.printIndentation() + p_o_yamlSchemaElement.getName() + ": " + this.s_lineBreak;
			} else if (this.i_level <= 1) {
				this.b_firstLevelCollectionElementsOnly = true;
			} 
			
			/* check conditions for handling array */
			if (p_o_yamlSchemaElement.getReference() != null) {
				if (p_o_yamlSchemaElement.getReference().getChildren().size() < 1) {
					throw new IllegalArgumentException("Reference[" + p_o_yamlSchemaElement.getReference().getName() + "] of schema-element[" + p_o_yamlSchemaElement.getName() + "] with schema-type[" + p_o_yamlSchemaElement.getType() + "] must have at least one child");
				}
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-array(" + p_o_yamlSchemaElement.getName() + ") with its reference(" + p_o_yamlSchemaElement.getReference().getName() + ") which has children");
			} else {
				if (p_o_yamlSchemaElement.getChildren().size() != 1) {
					throw new IllegalArgumentException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] with schema-type[" + p_o_yamlSchemaElement.getType() + "] must have just one child");
				}
				
				if (!p_o_yamlSchemaElement.getChildren().get(0).getName().toLowerCase().contentEquals("items")) {
					throw new IllegalArgumentException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] with schema-type[" + p_o_yamlSchemaElement.getName() + "] must have one child with name[items]");
				}
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-array(" + p_o_yamlSchemaElement.getName() + ") with child(items) which has children");
			}
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-array(" + p_o_yamlSchemaElement.getName() + ") - and cast object with p_o_object(" + p_o_object.getClass().getTypeName() + ") and schema-mapping(" + p_o_yamlSchemaElement.printMapping() + "), castonly if java.util.List=" + (p_o_object instanceof java.util.List));
			
			/* get array data from object */
			Object o_object = this.castObject(p_o_yamlSchemaElement, p_o_object, (p_o_object instanceof java.util.List));
			
			/* check if we have a primitive array and current value is just empty/null */
			if ( (o_object == null) && (p_o_yamlSchemaElement.getChildren().get(0).getPrimitiveArray()) ) {
				s_yaml = s_yaml.substring(0, (s_yaml.length() - this.s_lineBreak.length())) + "[]" + this.s_lineBreak;
			} else {
				/* cast current object as list with unknown generic type */
				java.util.List<?> a_objects = (java.util.List<?>)o_object;
	
				/* flag if we must handle generic list as primitive array */
				boolean b_primitiveArray = p_o_yamlSchemaElement.getChildren().size() > 0 && p_o_yamlSchemaElement.getChildren().get(0).getPrimitiveArray();
				
				/* check minItems and maxItems restrictions */
				if (p_o_yamlSchemaElement.getRestrictions().size() > 0) {
					for (YAMLRestriction o_yamlRestriction : p_o_yamlSchemaElement.getRestrictions()) {
						if (o_yamlRestriction.getName().toLowerCase().contentEquals("minitems")) {
							/* check minItems restriction */
							if (a_objects.size() < o_yamlRestriction.getIntValue()) {
								throw new IllegalArgumentException("Restriction error: not enough [" + p_o_yamlSchemaElement.getName() + "] yaml items(" + a_objects.size() + "), minimum = " + o_yamlRestriction.getIntValue());
							}
						}
						
						if (o_yamlRestriction.getName().toLowerCase().contentEquals("maxitems")) {
							/* check maxItems restriction */
							if (a_objects.size() > o_yamlRestriction.getIntValue()) {
								throw new IllegalArgumentException("Restriction error: too many [" + p_o_yamlSchemaElement.getName() + "] yaml items(" + a_objects.size() + "), maximum = " + o_yamlRestriction.getIntValue());
							}
						}
					}
				}
				
				if (p_o_yamlSchemaElement.getReference() != null) {
					/* set reference as current yaml element */
					p_o_yamlSchemaElement = p_o_yamlSchemaElement.getReference();
				} else {
					/* set current yaml element to 'items' child */
					p_o_yamlSchemaElement = p_o_yamlSchemaElement.getChildren().get(0);
					
					/* if 'items' child has a child as well, we take this child as current yaml element */
					if (p_o_yamlSchemaElement.getChildren().size() == 1) {
						p_o_yamlSchemaElement = p_o_yamlSchemaElement.getChildren().get(0);
					}
				}
				
				/* iterate objects in list and encode data to yaml recursively */
				for (int i = 0; i < a_objects.size(); i++) {
					/* check if array object value is null */
					if (a_objects.get(i) == null) {
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "encode schema-array(" + p_o_yamlSchemaElement.getName() + ") with array element(null) and schema-mapping(" + p_o_yamlSchemaElement.printMapping() + ")");
						
						/* increase level for PrintIndentation */
						this.i_level++;
						
						/* temp variable for indentation string */
						String s_indentation = this.printIndentation();
						
						/* add colon with null value to YAML */
						s_yaml += s_indentation.substring(0, (s_indentation.length() - 2)) + "- null" + this.s_lineBreak;
						
						/* decrease level for PrintIndentation */
						this.i_level--;
					} else if (b_primitiveArray) {
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "encode schema-array(" + p_o_yamlSchemaElement.getName() + ") with primitive array element(#" + (i + 1) + ") and schema-mapping(" + p_o_yamlSchemaElement.printMapping() + ")");
						
						/* increase level for PrintIndentation */
						this.i_level++;
						
						/* temp variable for indentation string */
						String s_indentation = this.printIndentation();
						
						/* add colon with primitive array element value to YAML */
						s_yaml += s_indentation.substring(0, (s_indentation.length() - 2)) + "- " + a_objects.get(i) + this.s_lineBreak;
						
						/* decrease level for PrintIndentation */
						this.i_level--;
					} else {
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "encode schema-array(" + p_o_yamlSchemaElement.getName() + ") with array element(" + a_objects.get(i).getClass().getTypeName() + ") and schema-mapping(" + p_o_yamlSchemaElement.printMapping() + ") with recursion");
						
						/* handle object with recursion */
						s_yaml += this.yamlEncodeRecursive(p_o_yamlSchemaElement, a_objects.get(i), true);
					}
				}
				
				if (a_objects.size() < 1) {
					s_yaml = s_yaml.substring(0, (s_yaml.length() - this.s_lineBreak.length())) + "[]" + this.s_lineBreak;
				}
			}
		} else {
			/* set object variable with current object */
			Object o_object = p_o_object;
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-property(" + p_o_yamlSchemaElement.getName() + ") with p_o_object(" + p_o_object.getClass().getTypeName() + ")");
			
			/* get object property if we have not an array with items */
			if (!p_o_yamlSchemaElement.getName().toLowerCase().contentEquals("items")) {
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-property(" + p_o_yamlSchemaElement.getName() + "), cast object with p_o_object(" + p_o_object.getClass().getTypeName() + ")");
				
				/* get object property of current yaml element */
				o_object = this.castObject(p_o_yamlSchemaElement, p_o_object);
			}
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-property(" + p_o_yamlSchemaElement.getName() + "), cast string from object with p_o_object(" + p_o_object.getClass().getTypeName() + ") and schema-type(" + p_o_yamlSchemaElement.getType() + ")");
			
			/* get string value of object for yaml-element */
			String s_foo = this.castStringFromObject(o_object, p_o_yamlSchemaElement.getType());
			
			/* check if yaml-element is required */
			if (p_o_yamlSchemaElement.getRequired()) {
				/* check if value is empty */
				if ( (s_foo.contentEquals("")) || (s_foo.contentEquals("null")) || (s_foo.contentEquals(this.s_stringQuote + this.s_stringQuote)) ) {
					throw new NullPointerException("'" + p_o_yamlSchemaElement.getName() + "' is required, but value[" + s_foo + "] is empty");
				}
			}
			
			/* check if yaml-element has any restrictions */
			if (p_o_yamlSchemaElement.getRestrictions().size() > 0) {
				for (YAMLRestriction o_yamlRestriction : p_o_yamlSchemaElement.getRestrictions()) {
					/* execute restriction check */
					this.checkRestriction(s_foo, o_yamlRestriction, p_o_yamlSchemaElement);
				}
			}
			
			/* add yaml-element with value */
			if (p_o_yamlSchemaElement.getName().toLowerCase().contentEquals("items")) {
				/* increase level for PrintIndentation */
				this.i_level++;
				
				/* temp variable for indentation string */
				String s_indentation = this.printIndentation();
				
				/* array with items does not need captions */
				s_yaml += ((p_b_parentIsCollection) ? s_indentation.substring(0, (s_indentation.length() - 2)) + "- " : s_indentation + "") + s_foo + this.s_lineBreak;
				
				/* decrease level for PrintIndentation */
				this.i_level--;
			} else {
				s_yaml += this.printIndentation() + p_o_yamlSchemaElement.getName() + ": " + s_foo + this.s_lineBreak;
			}
		}

		return s_yaml;
	}
	
	/**
	 * Cast field of an object
	 * 
	 * @param p_o_yamlElement				yaml element object	with mapping class information	
	 * @param p_o_object					object to access fields via direct public access or public access to property methods (getXX and setXX)
	 * @return								casted field value of object as object
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	private Object castObject(YAMLElement p_o_yamlElement, Object p_o_object) throws NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		return this.castObject(p_o_yamlElement, p_o_object, false);
	}
	
	/**
	 * Cast field of an object, optional cast only
	 * 
	 * @param p_o_yamlElement				yaml element object	with mapping class information	
	 * @param p_o_object					object to access fields via direct public access or public access to property methods (getXX and setXX)
	 * @param b_castOnly					true - just cast object with yaml element mapping class information, false - get object field value
	 * @return								casted field value of object as object
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	private Object castObject(YAMLElement p_o_yamlElement, Object p_o_object, boolean b_castOnly) throws NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		/* variable for object data */
		Object o_object = null;
		
		/* check if we only have to cast the current object */
		if (b_castOnly) {
			/* cast current object by string mapping value */
			Class<?> o_class = Class.forName(p_o_yamlElement.getMappingClass());
			o_object = o_class.cast(p_o_object); 
		} else {
			/* retrieve field information out of schema element */
			String s_field = p_o_yamlElement.getMappingClass();
			
			/* if there is additional mapping information, use this for field property access */
			if (!de.forestj.lib.Helper.isStringEmpty(p_o_yamlElement.getMapping())) {
				s_field = p_o_yamlElement.getMapping();
			}
			
			/* check if we use property methods with invoke to get object data values */
			if (this.b_usePropertyMethods) {
				java.lang.reflect.Method o_method = null;
				
				/* store get-property-method of java type object */
				try {
					o_method = p_o_object.getClass().getDeclaredMethod("get" + s_field);
				} catch (NoSuchMethodException | SecurityException o_exc) {
					throw new NoSuchMethodException("Class instance method[" + "get" + s_field + "] does not exist for encoding data yaml-schema-element: " + p_o_yamlElement.getName() + "(" + p_o_object.getClass().getTypeName() + ")");
				}
				
				/* invoke get-property-method to get object data of current element */
				o_object = o_method.invoke(p_o_object);
			} else {
				/* call field directly to get object data values */
				try {
					o_object = p_o_object.getClass().getDeclaredField(s_field).get(p_o_object);
				} catch (Exception o_exc) {
					throw new NoSuchFieldException("Class instance property[" + s_field + "] returns no data or not accessible for encoding data yaml-schema-element: " + p_o_yamlElement.getName() + "(" + p_o_object.getClass().getTypeName() + ")");
				}
			}
			
			/* check if we have an array schema element with primitive array type */
			if ( (o_object != null) && (p_o_yamlElement.getChildren().size() > 0) && (p_o_yamlElement.getChildren().get(0).getPrimitiveArray()) ) {
				/* handle usual arrays */
				java.util.List<String> a_primtiveArray = new java.util.ArrayList<String>();
				
				/* get array type */
				String s_arrayType = o_object.getClass().getTypeName().substring(0, o_object.getClass().getTypeName().length() - 2);
				
				if ( (s_arrayType.contentEquals("boolean")) || (s_arrayType.contentEquals("java.lang.Boolean")) ) {
					/* cast current field of parameter object as array */
					boolean[] a_objects = (boolean[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if ( (s_arrayType.contentEquals("byte")) || (s_arrayType.contentEquals("java.lang.Byte")) ) {
					/* cast current field of parameter object as array */
					byte[] a_objects = (byte[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if ( (s_arrayType.contentEquals("char")) || (s_arrayType.contentEquals("java.lang.Character")) ) {
					/* cast current field of parameter object as array */
					char[] a_objects = (char[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if ( (s_arrayType.contentEquals("float")) || (s_arrayType.contentEquals("java.lang.Float")) ) {
					/* cast current field of parameter object as array */
					float[] a_objects = (float[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if ( (s_arrayType.contentEquals("double")) || (s_arrayType.contentEquals("java.lang.Double")) ) {
					/* cast current field of parameter object as array */
					double[] a_objects = (double[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if ( (s_arrayType.contentEquals("short")) || (s_arrayType.contentEquals("java.lang.Short")) ) {
					/* cast current field of parameter object as array */
					short[] a_objects = (short[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if ( (s_arrayType.contentEquals("int")) || (s_arrayType.contentEquals("java.lang.Integer")) ) {
					/* cast current field of parameter object as array */
					int[] a_objects = (int[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if ( (s_arrayType.contentEquals("long")) || (s_arrayType.contentEquals("java.lang.Long")) ) {
					/* cast current field of parameter object as array */
					long[] a_objects = (long[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if ( (s_arrayType.contentEquals("string")) || (s_arrayType.contentEquals("java.lang.String")) ) {
					/* cast current field of parameter object as array */
					String[] a_objects = (String[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if (s_arrayType.contentEquals("java.util.Date")) {
					/* cast current field of parameter object as array */
					java.util.Date[] a_objects = (java.util.Date[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if (s_arrayType.contentEquals("java.time.LocalTime")) {
					/* cast current field of parameter object as array */
					java.time.LocalTime[] a_objects = (java.time.LocalTime[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if (s_arrayType.contentEquals("java.time.LocalDate")) {
					/* cast current field of parameter object as array */
					java.time.LocalDate[] a_objects = (java.time.LocalDate[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if (s_arrayType.contentEquals("java.time.LocalDateTime")) {
					/* cast current field of parameter object as array */
					java.time.LocalDateTime[] a_objects = (java.time.LocalDateTime[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode array object element and add string value to generic string list */
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
						}
					}
				} else if (s_arrayType.contentEquals("java.math.BigDecimal")) {
					/* cast current field of parameter object as array */
					java.math.BigDecimal[] a_objects = (java.math.BigDecimal[])o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in list and encode data to generic string list */
						for (int i = 0; i < a_objects.length; i++) {
							if (a_objects[i] == null) {
								/* it is allowed to have null value for type number, especially java.math.BigDecimal */
								a_primtiveArray.add( "null" );
							} else {
								/* encode array object element and add string value to generic string list */
								a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_yamlElement.getChildren().get(0).getType()) );
							}
						}
					}
				}
				
				return a_primtiveArray;
			}
		}
		
		return o_object;
	}
	
	/**
	 * Method to cast an object value to a string value for encoding yaml data
	 * 
	 * @param p_o_object					object value which will be casted to string
	 * @param p_s_type						type as string to distinguish
	 * @return								casted object value as string
	 * @throws IllegalArgumentException		invalid or empty type value
	 */
	private String castStringFromObject(Object p_o_object, String p_s_type) throws IllegalArgumentException {
		String s_foo = "";
		
		if (p_o_object != null) {
			p_s_type = p_s_type.toLowerCase();
			
			try {
				if (p_s_type.contentEquals("string")) {
					if (p_o_object instanceof java.util.Date) {
						s_foo = de.forestj.lib.Helper.utilDateToISO8601UTC(java.util.Date.class.cast(p_o_object));
					} else if (p_o_object instanceof java.time.LocalDateTime) {
						s_foo = de.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.class.cast(p_o_object));
					} else if (p_o_object instanceof java.time.LocalDate) {
						s_foo = de.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.of(java.time.LocalDate.class.cast(p_o_object), java.time.LocalTime.of(0, 0)));
					} else if (p_o_object instanceof java.time.LocalTime) {
						s_foo = de.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.of(java.time.LocalDate.of(1900, 1, 1), java.time.LocalTime.class.cast(p_o_object)));
					} else {
						s_foo = String.class.cast(p_o_object).toString();
					}
					
					String[] a_reservedCharacters = new String[] {":", "{", "}", "[", "]", ",", "&", "*", "#", "?", "|", "-", "<", ">", "=", "!", "%", "@", "\\", "'", this.s_stringQuote};
					
					boolean b_needToQuote = false;
					
					for (String s_reserverdCharacter : a_reservedCharacters) {
						if (s_foo.contains(s_reserverdCharacter)) {
							b_needToQuote = true;
						}
					}
					
					if (s_foo.contains(this.s_stringQuote)) {
						s_foo = s_foo.replaceAll(this.s_stringQuote, "\\\\" + this.s_stringQuote);
					}
					
					if ( (b_needToQuote) || (s_foo.contentEquals("null")) || (s_foo.length() == 0) ) {
						s_foo = this.s_stringQuote + s_foo + this.s_stringQuote;
					}
				} else if (p_s_type.contentEquals("number")) {
					if (p_o_object instanceof java.lang.Float) {
						s_foo = Float.class.cast(p_o_object).toString();
					} else if (p_o_object instanceof java.lang.Double) {
						s_foo = Double.class.cast(p_o_object).toString();
					} else {
						s_foo = java.math.BigDecimal.class.cast(p_o_object).toString();
					}
					
					if ( (s_foo.contentEquals("0")) || (s_foo.contentEquals("0.0")) ) {
						s_foo = "0.0";
					}
				} else if (p_s_type.contentEquals("integer")) {
					if (p_o_object instanceof java.lang.Short) {
						s_foo = Short.class.cast(p_o_object).toString();
					} else if (p_o_object instanceof java.lang.Long) {
						s_foo = Long.class.cast(p_o_object).toString();
					} else {
						s_foo = Integer.class.cast(p_o_object).toString();
					}
				} else if (p_s_type.contentEquals("boolean")) {
					s_foo = Boolean.class.cast(p_o_object).toString();
				} else if (p_s_type.contentEquals("null")) {
					s_foo = "null";
				} else {
					throw new IllegalArgumentException("Invalid type[" + p_s_type + "] for " + p_o_object.getClass().getSimpleName());
				}
			} catch (ClassCastException o_exc) {
				throw new IllegalArgumentException("Cannot cast value[" + p_o_object.toString() + "] to type[" + p_s_type + "]: " + o_exc.getMessage());
			}
		} else {
			if ( (p_s_type.contentEquals("number")) || (p_s_type.contentEquals("integer")) || (p_s_type.contentEquals("boolean")) ) {
				throw new IllegalArgumentException("Invalid value[null] for type[" + p_s_type + "]");
			}
			
			s_foo = "null";
		}
		
		return s_foo;
	}

	/**
	 * Check if yaml element restriction is valid with current value
	 * 
	 * @param p_s_value						string value for yaml element restriction, can be casted to integer as well
	 * @param p_o_yamlRestriction			yaml restriction object which holds all restriction information
	 * @param p_o_yamlElement				yaml element object
	 * @throws IllegalArgumentException		unknown restriction name, restriction error or invalid type from yaml element object
	 */
	private void checkRestriction(String p_s_value, YAMLRestriction p_o_yamlRestriction, YAMLElement p_o_yamlElement) throws IllegalArgumentException {
		String p_s_type = p_o_yamlElement.getType().toLowerCase();
		
		/* remove surrounding string quote characters */
		if ( (p_s_value.startsWith(this.s_stringQuote)) && (p_s_value.endsWith(this.s_stringQuote)) ) {
			p_s_value = p_s_value.substring(1, p_s_value.length() - 1);
		}
		
		if (p_o_yamlRestriction.getName().toLowerCase().contentEquals("minimum")) {
			if (p_s_type.contentEquals("number")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_yamlRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Restriction error: value[" + o_value + "] does not match " + p_o_yamlRestriction.getName() + " restriction[" + p_o_yamlRestriction.getStrValue() + "]");
				}
			} else if (p_s_type.contentEquals("integer")) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_yamlRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Restriction error: value[" + i_value + "] does not match " + p_o_yamlRestriction.getName() + " restriction[" + p_o_yamlRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_yamlElement.getName() + "' using restriction[" + p_o_yamlRestriction.getName() + "]");
			}
		} else if (p_o_yamlRestriction.getName().toLowerCase().contentEquals("exclusiveminimum")) {
			if (p_s_type.contentEquals("number")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_yamlRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if ( (i_compare == -1) || (i_compare == 0) ) {
					throw new IllegalArgumentException("Restriction error: value[" + o_value + "] does not match " + p_o_yamlRestriction.getName() + " restriction[" + p_o_yamlRestriction.getStrValue() + "]");
				}
			} else if (p_s_type.contentEquals("integer")) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_yamlRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if ( (i_compare == -1) || (i_compare == 0) ) {
					throw new IllegalArgumentException("Restriction error: value[" + i_value + "] does not match " + p_o_yamlRestriction.getName() + " restriction[" + p_o_yamlRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_yamlElement.getName() + "' using restriction[" + p_o_yamlRestriction.getName() + "]");
			}
		} else if (p_o_yamlRestriction.getName().toLowerCase().contentEquals("maximum")) {
			if (p_s_type.contentEquals("number")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_yamlRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Restriction error: value[" + o_value + "] does not match " + p_o_yamlRestriction.getName() + " restriction[" + p_o_yamlRestriction.getStrValue() + "]");
				}
			} else if (p_s_type.contentEquals("integer")) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_yamlRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Restriction error: value[" + i_value + "] does not match " + p_o_yamlRestriction.getName() + " restriction[" + p_o_yamlRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_yamlElement.getName() + "' using restriction[" + p_o_yamlRestriction.getName() + "]");
			}
		} else if (p_o_yamlRestriction.getName().toLowerCase().contentEquals("exclusivemaximum")) {
			if (p_s_type.contentEquals("number")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_yamlRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if ( (i_compare == 1) || (i_compare == 0) ) {
					throw new IllegalArgumentException("Restriction error: value[" + o_value + "] does not match " + p_o_yamlRestriction.getName() + " restriction[" + p_o_yamlRestriction.getStrValue() + "]");
				}
			} else if (p_s_type.contentEquals("integer")) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_yamlRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if ( (i_compare == 1) || (i_compare == 0) ) {
					throw new IllegalArgumentException("Restriction error: value[" + i_value + "] does not match " + p_o_yamlRestriction.getName() + " restriction[" + p_o_yamlRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_yamlElement.getName() + "' using restriction[" + p_o_yamlRestriction.getName() + "]");
			}
		} else if (p_o_yamlRestriction.getName().toLowerCase().contentEquals("minlength")) {
			if (p_s_type.contentEquals("string")) {
				if (p_s_value.length() < p_o_yamlRestriction.getIntValue()) {
					throw new IllegalArgumentException("Restriction error: value[" + p_s_value + "] does not match " + p_o_yamlRestriction.getName() + " restriction[" + p_o_yamlRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_yamlElement.getName() + "' using restriction[" + p_o_yamlRestriction.getName() + "]");
			}
		} else if (p_o_yamlRestriction.getName().toLowerCase().contentEquals("maxlength")) {
			if (p_s_type.contentEquals("string")) {
				if (p_s_value.length() > p_o_yamlRestriction.getIntValue()) {
					throw new IllegalArgumentException("Restriction error: value[" + p_s_value + "] does not match " + p_o_yamlRestriction.getName() + " restriction[" + p_o_yamlRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_yamlElement.getName() + "' using restriction[" + p_o_yamlRestriction.getName() + "]");
			}
		} else if (p_o_yamlRestriction.getName().toLowerCase().contentEquals("pattern")) {
			if ( (p_s_type.contentEquals("string")) || (p_s_type.contentEquals("boolean")) || (p_s_type.contentEquals("number")) || (p_s_type.contentEquals("integer")) )  {
				if (!de.forestj.lib.Helper.matchesRegex(p_s_value, p_o_yamlRestriction.getStrValue())) {
					throw new IllegalArgumentException("Restriction error: value[" + p_s_value + "] does not match " + p_o_yamlRestriction.getName() + " restriction[" + p_o_yamlRestriction.getStrValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_yamlElement.getName() + "' using restriction[" + p_o_yamlRestriction.getName() + "]");
			}
		} else {
			throw new IllegalArgumentException("Unknown Restriction: " + p_o_yamlRestriction.getName());
		}
	}
	
	/* validating yaml data with YAML schema */
	
	/**
	 * Validate yaml file
	 * 
	 * @param p_s_yamlFile					full-path to yaml file
	 * @return								true - content of yaml file is valid, false - content of yaml file is invalid
	 * @throws IllegalArgumentException		yaml file does not exist
	 * @throws java.io.IOException			cannot read yaml file content
	 * @throws NullPointerException			empty schema, empty yaml file or root node after parsing yaml content
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	public boolean validateAgainstSchema(String p_s_yamlFile) throws IllegalArgumentException, java.io.IOException, NullPointerException, java.text.ParseException, java.time.DateTimeException {
		/* check if file exists */
		if (!de.forestj.lib.io.File.exists(p_s_yamlFile)) {
			throw new IllegalArgumentException("File[" + p_s_yamlFile + "] does not exist.");
		}
		
		/* open yaml file */
		de.forestj.lib.io.File o_file = new de.forestj.lib.io.File(p_s_yamlFile, false);
		
		/* load file content into string */
		String s_fileContent = o_file.getFileContent();
		
		java.util.List<String> a_fileLines = new java.util.ArrayList<String>();

		/* load file content lines into array */
		for (String s_line : s_fileContent.split(this.s_lineBreak)) {
			a_fileLines.add(s_line);
		}
		
												de.forestj.lib.Global.ilogFinest("read all lines from yaml file '" + p_s_yamlFile + "'");
		
		return this.validateAgainstSchema(a_fileLines);
	}
	
	/**
	 * Validate yaml content
	 * 
	 * @param p_a_yamlLines					yaml lines
	 * @return								true - yaml content is valid, false - yaml content is invalid
	 * @throws NullPointerException			empty schema, empty yaml file or root node after parsing yaml content
	 * @throws IllegalArgumentException		condition failed for decoding yaml correctly
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	public boolean validateAgainstSchema(java.util.List<String> p_a_yamlLines) throws NullPointerException, IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		/* check schema field */
		if (this.o_schema == null) {
			throw new NullPointerException("Cannot decode data. Schema is null.");
		}
		
		java.util.List<String> a_fileLines = this.validateYAML(p_a_yamlLines);

												de.forestj.lib.Global.ilogFinest("validated yaml content lines");
		
	    /* check if root is null */
	    if ( (a_fileLines.size() == 0) || ((a_fileLines.size() == 1) && (a_fileLines.get(0).contentEquals("null"))) ) {
			throw new NullPointerException("YAML file is null");
		}

	    /* reset level */
	    this.i_level = 0;

	    /* create new root */
		this.o_root = new YAMLElement("Root");
		
		/* init flag for indentation mode if first level has only collection elements */
		this.b_firstLevelCollectionElementsOnly = false;
		
	    /* parse yaml content lines */
	    this.parseYAML(1, a_fileLines.size(), a_fileLines, 0, this.o_root);

	    										de.forestj.lib.Global.ilogFinest("parsed yaml content lines");
	    
	    /* check if root is null */
	    if (this.o_root == null) {
			throw new NullPointerException("Root node is null");
		}
		
	    /* check if root has any children */
		if (this.o_root.getChildren().size() == 0) {
			throw new NullPointerException("Root node has no children[size=" + this.o_root.getChildren().size() + "]");
		}
	    
	    this.o_currentElement = null;
	    
											    if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("YAML-Schema:" + de.forestj.lib.io.File.NEWLINE + this.o_schema);
											    if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("YAML-Root-Element:" + de.forestj.lib.io.File.NEWLINE + this.o_root);
	    
	    /* validate yaml content recursively */
	    return this.validateAgainstSchemaRecursive(this.o_root, this.o_schema);
	}
	
	/**
	 * Recursive method to validate yaml content string
	 * 
	 * @param p_o_yamlDataElement			current yaml data element
	 * @param p_o_yamlSchemaElement			current yaml schema element with additional information for decoding
	 * @return								true - yaml content is valid, false - yaml content is invalid
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding yaml correctly
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private boolean validateAgainstSchemaRecursive(YAMLElement p_o_yamlDataElement, YAMLElement p_o_yamlSchemaElement) throws NullPointerException, IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		boolean b_return = true;
		
		/* if type and mapping class are not set, we need at least a reference to continue */
		if ( (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getType())) && (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMappingClass())) ) {
			if (p_o_yamlSchemaElement.getReference() == null) {
				throw new NullPointerException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no type, no mapping class and no reference");
			} else {
				/* set reference as current schema-element */
				p_o_yamlSchemaElement = p_o_yamlSchemaElement.getReference();
			}
		}
		
		/* check if type is set */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getType())) {
			throw new NullPointerException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no type");
		}
		
		/* check if mapping class is set if schema-element is not 'items' */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMappingClass())) {
			if (!p_o_yamlSchemaElement.getName().toLowerCase().contentEquals("items")) {
				throw new IllegalArgumentException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no mapping class");
			}
		}
		
		if (p_o_yamlSchemaElement.getType().toLowerCase().contentEquals("object")) {
			/* check if we have any data for new object */
			if (p_o_yamlDataElement.getChildren().size() > 0) {
				String s_objectType = p_o_yamlSchemaElement.getMappingClass();
				
				/* if object has reference, we create new object instance by mapping of reference */
				if ( (p_o_yamlSchemaElement.getReference() != null) && (p_o_yamlSchemaElement.getReference().getType().toLowerCase().contentEquals("object")) ) {
					s_objectType = p_o_yamlSchemaElement.getReference().getMappingClass();
				}
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": create new schema-object instance with mapping[" + p_o_yamlSchemaElement.printMapping() + "] and type[" + s_objectType + "]");
				
				/* increase level for PrintTabs */
				this.i_level++;
				
				/* help variable to skip children iteration */
				boolean b_childrenIteration = true;
				
				/* check conditions for handling object */
				if (p_o_yamlSchemaElement.getReference() != null) {
					/* check if reference has any children */
					if (p_o_yamlSchemaElement.getReference().getChildren().size() < 1) {
						/* check if reference has another reference */
						if (p_o_yamlSchemaElement.getReference().getReference() == null) {
							throw new NullPointerException("Reference[" + p_o_yamlSchemaElement.getReference().getName() + "] of schema-element[" + p_o_yamlSchemaElement.getName() + "] has no children and no other reference");
						} else {
							b_childrenIteration = false;
							
							/* check if current element in schema has data element by name, otherwise skip this element */
							if (p_o_yamlSchemaElement.getName().contentEquals(p_o_yamlDataElement.getName())) {
																		de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": get schema-object[" + p_o_yamlSchemaElement.getMappingClass() + "] with reference[" + p_o_yamlSchemaElement.getReference().getName() + "]");
	
								/* only create new object if we have one object data */
								if (p_o_yamlDataElement.getChildren().size() != 1) {
									throw new IllegalArgumentException("We have (" + p_o_yamlDataElement.getChildren().size() + ") no data children or more than one for schema-element[" + p_o_yamlSchemaElement.getName() + "]");
								}
								
								/* handle reference with recursion */
								b_return = this.validateAgainstSchemaRecursive(p_o_yamlDataElement.getChildren().get(0), p_o_yamlSchemaElement.getReference());
							}
						}
					} else {
																de.forestj.lib.Global.ilogFiner(this.printIndentation() + "update current schema-element[" + p_o_yamlSchemaElement.getName() + "](" + p_o_yamlSchemaElement.printMapping() + ") with reference[" + p_o_yamlSchemaElement.getReference().getName() + "](" + p_o_yamlSchemaElement.getReference().printMapping() + ")");
						
						/* set reference as current yaml element */
						p_o_yamlSchemaElement = p_o_yamlSchemaElement.getReference();
					}
				}
				
				/* execute children iteration */
				if (b_childrenIteration) {
															de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": get schema-object with type[" + p_o_yamlSchemaElement.getMappingClass() + "] and children[" + p_o_yamlSchemaElement.getChildren().size() + "]");
					
					/* only create new object if we have one child definition for object in yaml schema */
					if (p_o_yamlSchemaElement.getChildren().size() < 1) {
						throw new IllegalArgumentException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no children");	
					}
					
					/* check if new object is required if there is no data */
					if ( (p_o_yamlDataElement.getChildren().size() < 1) && (p_o_yamlSchemaElement.getRequired()) ) {
						throw new IllegalArgumentException("We have no data children for schema-element[" + p_o_yamlSchemaElement.getName() + "] which is required");
					}
					
					/* only iterate if we have object data */
					if (p_o_yamlDataElement.getChildren().size() > 0) {
						/* increase level for PrintTabs */
						this.i_level++;
						
						/* data pointer */
						int j = 0;
						
						for (int i = 0; i < p_o_yamlSchemaElement.getChildren().size(); i++) {
																	de.forestj.lib.Global.ilogFinest(this.printIndentation() + "compare schema-child-name(" + p_o_yamlSchemaElement.getChildren().get(i).getName() + ") with data-child-name(" + p_o_yamlDataElement.getChildren().get(j).getName() + ")");
																	
							/* check if current element in schema has data element by name, otherwise skip this element */
							if (!p_o_yamlSchemaElement.getChildren().get(i).getName().contentEquals(p_o_yamlDataElement.getChildren().get(j).getName())) {
								/* but check if current schema element is required, so we must throw an exception */
								if (p_o_yamlSchemaElement.getChildren().get(i).getRequired()) {
									throw new IllegalArgumentException("We have no data for schema-element[" + p_o_yamlSchemaElement.getChildren().get(i).getName() + "] which is required");
								} else {
									continue;
								}
							}
							
							/* handle new object with recursion */
							b_return = this.validateAgainstSchemaRecursive(p_o_yamlDataElement.getChildren().get(j), p_o_yamlSchemaElement.getChildren().get(i));
							
							/* increase data pointer */
							j++;
						}
						
						/* decrease level for PrintTabs */
						this.i_level--;
					}
				}
				
				/* decrease level for PrintTabs */
				this.i_level--;
			}
		} else if (p_o_yamlSchemaElement.getType().toLowerCase().contentEquals("array")) {
			/* check conditions for handling array */
			if (p_o_yamlSchemaElement.getReference() != null) {
				if (p_o_yamlSchemaElement.getReference().getChildren().size() < 1) {
					throw new IllegalArgumentException("Reference[" + p_o_yamlSchemaElement.getReference().getName() + "] of schema-array[" + p_o_yamlSchemaElement.getName() + "] with mapping[" + p_o_yamlSchemaElement.getMappingClass() + "] must have at least one child");
				}
			} else {
				if (p_o_yamlSchemaElement.getChildren().size() != 1) {
					throw new IllegalArgumentException("Schema-array[" + p_o_yamlSchemaElement.getName() + "] with mapping[" + p_o_yamlSchemaElement.getMappingClass() + "] must have just one child");
				}
				
				if (!p_o_yamlSchemaElement.getChildren().get(0).getName().toLowerCase().contentEquals("items")) {
					throw new IllegalArgumentException("Schema-array[" + p_o_yamlSchemaElement.getName() + "] with mapping[" + p_o_yamlSchemaElement.getMappingClass() + "] must have one child with name[items]");
				}
			}
			
			/* help variables to handle array */
			boolean b_required = false;
			String s_requiredProperty = "";
			java.util.List<YAMLRestriction> a_restrictions = new java.util.ArrayList<YAMLRestriction>();
			String s_amountProperty = "";
			
			/* check if yaml-element is required */
			if (p_o_yamlSchemaElement.getRequired()) {
				b_required = true;
				s_requiredProperty = p_o_yamlSchemaElement.getName();
			}
			
			/* check minItems and maxItems restrictions and save them for items check afterwards */
			if (p_o_yamlSchemaElement.getRestrictions().size() > 0) {
				for (YAMLRestriction o_yamlRestriction : p_o_yamlSchemaElement.getRestrictions()) {
					if ( (o_yamlRestriction.getName().toLowerCase().contentEquals("minitems")) || (o_yamlRestriction.getName().toLowerCase().contentEquals("maxitems"))) {
						a_restrictions.add(o_yamlRestriction);
						s_amountProperty = p_o_yamlSchemaElement.getName();
					}
				}
			}
			
			if (p_o_yamlSchemaElement.getReference() != null) {
				/* set reference as current yaml element */
				p_o_yamlSchemaElement = p_o_yamlSchemaElement.getReference();
			} else {
				/* set current yaml element to 'items' child */
				p_o_yamlSchemaElement = p_o_yamlSchemaElement.getChildren().get(0);
				
				/* if 'items' child has a child as well, we take this child as current yaml element */
				if (p_o_yamlSchemaElement.getChildren().size() == 1) {
					p_o_yamlSchemaElement = p_o_yamlSchemaElement.getChildren().get(0);
				}
				
				/* important part for parsing collection which are not inline collection values in yaml document */
				if ( (!p_o_yamlDataElement.getName().contentEquals("__ArrayObject__")) && (p_o_yamlSchemaElement.getName().toLowerCase().contentEquals("items")) ) {
					if ( (p_o_yamlDataElement.getChildren().size() > 0) && (p_o_yamlDataElement.getChildren().get(0).getName().contentEquals("__ArrayObject__")) ) {
						p_o_yamlDataElement = p_o_yamlDataElement.getChildren().get(0);
					}
				}
			}
			
			if (de.forestj.lib.Helper.isStringEmpty(p_o_yamlDataElement.getValue())) { /* we have multiple minor objects for current array */
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": get schema-array with mapping[" + p_o_yamlSchemaElement.printMapping() + "]");
				
				if (p_o_yamlDataElement.getChildren().size() > 0) { /* if we have objects to the new array */
					/* check minItems and maxItems restrictions */
					if (a_restrictions.size() > 0) {
						for (YAMLRestriction o_yamlRestriction : a_restrictions) {
							if (o_yamlRestriction.getName().toLowerCase().contentEquals("minitems")) {
								/* check minItems restriction */
								if (p_o_yamlDataElement.getChildren().size() < o_yamlRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: not enough [" + p_o_yamlSchemaElement.getName() + " of " + s_amountProperty + "] yaml items(" + p_o_yamlDataElement.getChildren().size() + "), minimum = " + o_yamlRestriction.getIntValue());
								}
							}
							
							if (o_yamlRestriction.getName().toLowerCase().contentEquals("maxitems")) {
								/* check maxItems restriction */
								if (p_o_yamlDataElement.getChildren().size() > o_yamlRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: too many [" + p_o_yamlSchemaElement.getName() + " of " + s_amountProperty + "] yaml items(" + p_o_yamlDataElement.getChildren().size() + "), maximum = " + o_yamlRestriction.getIntValue());
								}
							}
						}
					}
					
					/* iterate objects in list and encode data to yaml recursively */
					for (int i = 0; i < p_o_yamlDataElement.getChildren().size(); i++) {
						/* increase level for PrintTabs */
						this.i_level++;
						
						/* handle array object with recursion */
						b_return = this.validateAgainstSchemaRecursive(p_o_yamlDataElement.getChildren().get(i), p_o_yamlSchemaElement);
						
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + " return value of " + p_o_yamlDataElement.getName() + ": " + b_return);
						
						/* decrease level for PrintTabs */
						this.i_level--;
					}
				}
			} else { /* array objects must be retrieved out of value property */
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": handle array value = " + p_o_yamlDataElement.getValue() + " - mapping[" + p_o_yamlSchemaElement.printMapping() + "]");
				
				/* set array with values if we have any values */
				if ( (!de.forestj.lib.Helper.isStringEmpty(p_o_yamlDataElement.getValue())) && (!p_o_yamlDataElement.getValue().contentEquals("[]")) ) {
					/* remove opening and closing brackets */
					if ( (p_o_yamlDataElement.getValue().startsWith("[")) && (p_o_yamlDataElement.getValue().endsWith("]")) ) {
						p_o_yamlDataElement.setValue(p_o_yamlDataElement.getValue().substring(1, p_o_yamlDataElement.getValue().length() - 1));
					}
					
					/* split array into values, divided by ',' */
					String[] a_values = p_o_yamlDataElement.getValue().split(",");
					
					/* check minItems and maxItems restrictions */
					if (a_restrictions.size() > 0) {
						for (YAMLRestriction o_yamlRestriction : a_restrictions) {
							if (o_yamlRestriction.getName().toLowerCase().contentEquals("minitems")) {
								/* check minItems restriction */
								if (a_values.length < o_yamlRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: not enough [" + p_o_yamlSchemaElement.getName() + " of " + s_amountProperty + "] yaml items(" + a_values.length + "), minimum = " + o_yamlRestriction.getIntValue());
								}
							}
							
							if (o_yamlRestriction.getName().toLowerCase().contentEquals("maxitems")) {
								/* check maxItems restriction */
								if (a_values.length > o_yamlRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: too many [" + p_o_yamlSchemaElement.getName() + " of " + s_amountProperty + "] yaml items(" + a_values.length + "), maximum = " + o_yamlRestriction.getIntValue());
								}
							}
						}
					}
					
					/* iterate all array values */
					for (String s_value : a_values) {
						YAMLValueType e_yamlValueType = this.getYAMLValueType(s_value);
						
						/* check if YAML value types are matching between schema and data */
						if (e_yamlValueType != stringToYAMLValueType(p_o_yamlSchemaElement.getType())) {
							throw new IllegalArgumentException("YAML schema type[" + stringToYAMLValueType(p_o_yamlSchemaElement.getType()) + "] does not match with data value type[" + e_yamlValueType + "] with value[" + s_value + "]");
						}
						
						/* check if yaml-element has any restrictions */
						if (p_o_yamlSchemaElement.getRestrictions().size() > 0) {
							for (YAMLRestriction o_yamlRestriction : p_o_yamlSchemaElement.getRestrictions()) {
								/* execute restriction check */
								this.checkRestriction(s_value, o_yamlRestriction, p_o_yamlSchemaElement);
							}
						}
						
						/* cast array string value into object */
						Object o_returnObject = this.castObjectFromString(s_value, p_o_yamlSchemaElement.getType());
						
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "add value[" + ( (o_returnObject == null) ? "null" : o_returnObject ) + "] to list of " + p_o_yamlDataElement.getName() + ", type[" + ( (o_returnObject == null) ? "null" : o_returnObject.getClass().getTypeName() ) + "]");
					}
				} else if (b_required) { /* if yaml-element with type array is required, throw exception */
					throw new IllegalArgumentException("'" + p_o_yamlSchemaElement.getName() + "' of '" + s_requiredProperty + "' is required, but array has no values");
				}
			}
		} else {
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": handle value = " + p_o_yamlDataElement.getValue() + " - mapping[" + p_o_yamlSchemaElement.printMapping() + "]");
			
			YAMLValueType e_yamlValueType = this.getYAMLValueType(p_o_yamlDataElement.getValue());
			
			/* check if YAML value types are matching between schema and data, additional condition = YAML value type is not 'null' */
			if ( (e_yamlValueType != stringToYAMLValueType(p_o_yamlSchemaElement.getType())) && (e_yamlValueType != YAMLValueType.Null) ) {
				/* it is acceptable if source type is 'integer' and destination type is 'number', valid cast available */
				if (!( (e_yamlValueType == YAMLValueType.Integer) && (stringToYAMLValueType(p_o_yamlSchemaElement.getType()) == YAMLValueType.Number) ) ) {
					throw new IllegalArgumentException("YAML schema type[" + e_yamlValueType + "] does not match with data value type[" + stringToYAMLValueType(p_o_yamlSchemaElement.getType()) + "] with value[" + p_o_yamlDataElement.getValue() + "]");
				}
			}
			
			/* check if yaml-element is required */
			if (p_o_yamlSchemaElement.getRequired()) {
				/* check if value is empty */
				if ( (p_o_yamlDataElement.getValue().contentEquals("")) || (p_o_yamlDataElement.getValue().contentEquals("null")) || (p_o_yamlDataElement.getValue().contentEquals("\"\"")) ) {
					throw new NullPointerException("'" + p_o_yamlSchemaElement.getName() + "' is required, but value[" + p_o_yamlDataElement.getValue() + "] is empty");
				}
			}
			
			/* check if yaml-element has any restrictions */
			if (p_o_yamlSchemaElement.getRestrictions().size() > 0) {
				for (YAMLRestriction o_yamlRestriction : p_o_yamlSchemaElement.getRestrictions()) {
					/* execute restriction check */
					this.checkRestriction(p_o_yamlDataElement.getValue(), o_yamlRestriction, p_o_yamlSchemaElement);
				}
			}
			
			if ( (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMapping())) && (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMappingClass())) ) {
				@SuppressWarnings("unused")
				Object o_foo = this.castObjectFromString(p_o_yamlDataElement.getValue(), p_o_yamlSchemaElement.getType());
			} else {
				String s_objectValue = p_o_yamlDataElement.getValue();
				
				/* remove surrounded double quotes from value */
				if ( (s_objectValue != null) && (s_objectValue.startsWith(this.s_stringQuote)) && (s_objectValue.endsWith(this.s_stringQuote)) ) {
					s_objectValue = s_objectValue.substring(1, s_objectValue.length() - 1);
				}
				
				try {
					@SuppressWarnings("unused")
					Object o_foo = this.castObjectFromString(s_objectValue, p_o_yamlSchemaElement.getType());
				} catch (Exception o_excOne) {
					try {
						@SuppressWarnings("unused")
						Object o_foo = this.castObjectFromString(s_objectValue, p_o_yamlSchemaElement.getMapping());
					} catch (Exception o_excTwo) {
						@SuppressWarnings("unused")
						Object o_foo = this.castObjectFromString(s_objectValue, p_o_yamlSchemaElement.getMappingClass());
					}
				}
			}
		}
		
												de.forestj.lib.Global.ilogFine(this.printIndentation() + "return " + b_return);
		return b_return;
	}
	
	/* decoding YAML to data with YAML schema */
	
	/**
	 * Decode yaml file to an java object
	 * 
	 * @param p_s_yamlFile					full-path to yaml file
	 * @return								yaml decoded java object
	 * @throws IllegalArgumentException		yaml file does not exist
	 * @throws java.io.IOException			cannot read yaml file content
	 * @throws NullPointerException			empty schema, empty yaml file or root node after parsing yaml content
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InstantiationException		could not create new object instance returning at the end of the method
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public Object yamlDecode(String p_s_yamlFile) throws IllegalArgumentException, java.io.IOException, NullPointerException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException, InstantiationException, ClassNotFoundException {
		/* check if file exists */
		if (!de.forestj.lib.io.File.exists(p_s_yamlFile)) {
			throw new IllegalArgumentException("File[" + p_s_yamlFile + "] does not exist.");
		}
		
		/* open yaml file */
		de.forestj.lib.io.File o_file = new de.forestj.lib.io.File(p_s_yamlFile, false);
		
		/* load file content into string */
		String s_fileContent = o_file.getFileContent();
		
		java.util.List<String> a_fileLines = new java.util.ArrayList<String>();

		/* load file content lines into array */
		for (String s_line : s_fileContent.split(this.s_lineBreak)) {
			a_fileLines.add(s_line);
		}
		
												de.forestj.lib.Global.ilogFinest("read all lines from yaml file '" + p_s_yamlFile + "'");
		
		return this.yamlDecode(a_fileLines);
	}
	
	/**
	 * Decode yaml content to an java object
	 * 
	 * @param p_a_yamlLines					yaml lines
	 * @return								yaml decoded java object
	 * @throws NullPointerException			empty schema, empty yaml file or root node after parsing yaml content
	 * @throws IllegalArgumentException		condition failed for decoding yaml correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InstantiationException		could not create new object instance returning at the end of the method
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public Object yamlDecode(java.util.List<String> p_a_yamlLines) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException, InstantiationException, ClassNotFoundException {
		Object o_foo = null;
		
		/* check schema field */
		if (this.o_schema == null) {
			throw new NullPointerException("Cannot decode data. Schema is null.");
		}
		
		java.util.List<String> a_fileLines = this.validateYAML(p_a_yamlLines);

												de.forestj.lib.Global.ilogFinest("validated yaml content lines");
		
	    /* check if root is null */
	    if ( (a_fileLines.size() == 0) || ((a_fileLines.size() == 1) && (a_fileLines.get(0).contentEquals("null"))) ) {
			throw new NullPointerException("YAML file is null");
		}

	    /* reset level */
	    this.i_level = 0;

	    /* create new root */
		this.o_root = new YAMLElement("Root");
		
		/* init flag for indentation mode if first level has only collection elements */
		this.b_firstLevelCollectionElementsOnly = false;
		
	    /* parse yaml content lines */
	    this.parseYAML(1, a_fileLines.size(), a_fileLines, 0, this.o_root);

	    										de.forestj.lib.Global.ilogFinest("parsed yaml content lines");
	    
	    /* check if root is null */
	    if (this.o_root == null) {
			throw new NullPointerException("Root node is null");
		}
		
	    /* check if root has any children */
		if (this.o_root.getChildren().size() == 0) {
			throw new NullPointerException("Root node has no children[size=" + this.o_root.getChildren().size() + "]");
		}
	    
	    this.o_currentElement = null;
	    
											    if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("YAML-Schema:" + de.forestj.lib.io.File.NEWLINE + this.o_schema);
											    if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("YAML-Root-Element:" + de.forestj.lib.io.File.NEWLINE + this.o_root);
	    
	    /* decode yaml recursively */
	    o_foo = this.yamlDecodeRecursive(this.o_root, this.o_schema, o_foo);
		
		return o_foo;
	}
	
	/**
	 * Recursive method to decode yaml string to a java object and it's fields
	 * 
	 * @param p_o_yamlDataElement			current yaml data element
	 * @param p_o_yamlSchemaElement			current yaml schema element with additional information for decoding
	 * @param p_o_object					destination java object to decode yaml information
	 * @return								decoded yaml information as java object
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding yaml correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InstantiationException		could not create new object instance returning at the end of the method
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	private Object yamlDecodeRecursive(YAMLElement p_o_yamlDataElement, YAMLElement p_o_yamlSchemaElement, Object p_o_object) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException, InstantiationException, ClassNotFoundException {
		/* if type and mapping class are not set, we need at least a reference to continue */
		if ( (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getType())) && (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMappingClass())) ) {
			if (p_o_yamlSchemaElement.getReference() == null) {
				throw new NullPointerException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no type, no mapping class and no reference");
			} else {
				/* set reference as current schema-element */
				p_o_yamlSchemaElement = p_o_yamlSchemaElement.getReference();
			}
		}
		
		/* check if type is set */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getType())) {
			throw new NullPointerException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no type");
		}
		
		/* check if mapping class is set if schema-element is not 'items' */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMappingClass())) {
			if (!p_o_yamlSchemaElement.getName().toLowerCase().contentEquals("items")) {
				throw new IllegalArgumentException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no mapping class");
			}
		}
		
		if (p_o_yamlSchemaElement.getType().toLowerCase().contentEquals("object")) {
			/* check if we have any data for new object */
			if (p_o_yamlDataElement.getChildren().size() < 1) {
				/* set current object as null */
				p_o_object = null;
			} else {
				String s_objectType = p_o_yamlSchemaElement.getMappingClass();
				
				/* if object has reference, we create new object instance by mapping of reference */
				if ( (p_o_yamlSchemaElement.getReference() != null) && (p_o_yamlSchemaElement.getReference().getType().toLowerCase().contentEquals("object")) ) {
					s_objectType = p_o_yamlSchemaElement.getReference().getMappingClass();
				}
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": create new schema-object instance with mapping[" + p_o_yamlSchemaElement.printMapping() + "] and type[" + s_objectType + "]");
				
				/* object variable which will be returned at the end of this function */
				Object o_object = null;
														
				/* check if class type of object is a inner class */
				if (s_objectType.contains("$")) {
					/* get target class */
					Class<?> o_targetClass = Class.forName(s_objectType);
					
					/* get instance of parent class */
					Object o_parentClass = Class.forName(s_objectType.split("\\$")[0]).getDeclaredConstructor().newInstance();
					boolean b_found = false;
					
					/* look for declared inner classes in parent class */
					for (Class<?> o_subClass : o_parentClass.getClass().getDeclaredClasses()) {
						/* inner class must match with Class<?> type parameter of dynamic generic list */
						if (o_subClass == o_targetClass) {
							b_found = true;
							/* create new object instance of inner class, with help of parent class instance */
							o_object = o_targetClass.getDeclaredConstructor(o_parentClass.getClass()).newInstance(o_parentClass);
							break;
						}
					}
					
					/* throw exception if inner class could not be found */
					if (!b_found) {
						throw new ClassNotFoundException("Could not found inner class in scope '" + o_targetClass.getTypeName() + "'");
					}
				} else {
					/* create new object instance which will be returned at the end of this function */
					o_object = Class.forName(s_objectType).getDeclaredConstructor().newInstance();
				}
				
				/* increase level for PrintTabs */
				this.i_level++;
				
				/* help variable to skip children iteration */
				boolean b_childrenIteration = true;
				
				/* help variable for object mapping within objects */
				String s_objectMapping = p_o_yamlSchemaElement.getMappingClass();
				
				/* if there is additional mapping information, use this for object mapping access */
				if (!de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMapping())) {
					s_objectMapping = p_o_yamlSchemaElement.getMapping();
				}
				
				/* check conditions for handling object */
				if (p_o_yamlSchemaElement.getReference() != null) {
					/* check if reference has any children */
					if (p_o_yamlSchemaElement.getReference().getChildren().size() < 1) {
						/* check if reference has another reference */
						if (p_o_yamlSchemaElement.getReference().getReference() == null) {
							throw new NullPointerException("Reference[" + p_o_yamlSchemaElement.getReference().getName() + "] of schema-element[" + p_o_yamlSchemaElement.getName() + "] has no children and no other reference");
						} else {
							b_childrenIteration = false;
							
							/* check if current element in schema has data element by name, otherwise skip this element */
							if (p_o_yamlSchemaElement.getName().contentEquals(p_o_yamlDataElement.getName())) {
																		de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": get schema-object[" + o_object.getClass().getTypeName() + "] with reference[" + p_o_yamlSchemaElement.getReference().getName() + "]");
	
								/* only create new object if we have one object data */
								if (p_o_yamlDataElement.getChildren().size() != 1) {
									throw new IllegalArgumentException("We have (" + p_o_yamlDataElement.getChildren().size() + ") no data children or more than one for schema-element[" + p_o_yamlSchemaElement.getName() + "]");
								}
								
								/* handle reference with recursion */
								Object o_returnObject = this.yamlDecodeRecursive(p_o_yamlDataElement.getChildren().get(0), p_o_yamlSchemaElement.getReference(), o_object);
								
								/* check if we got a return object of recursion */
								if (o_returnObject == null) {
									/* it is valid to return a null object, anyway keep this exception if you want to uncomment it in the future */
									/* throw new NullPointerException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] returns no object after recursion with data[" + p_o_yamlDataElement.getName() + "]"); */
								}
							}
						}
					} else {
																de.forestj.lib.Global.ilogFiner(this.printIndentation() + "update current schema-element[" + p_o_yamlSchemaElement.getName() + "](" + p_o_yamlSchemaElement.printMapping() + ") with reference[" + p_o_yamlSchemaElement.getReference().getName() + "](" + p_o_yamlSchemaElement.getReference().printMapping() + ")");
						
						/* set reference as current yaml element */
						p_o_yamlSchemaElement = p_o_yamlSchemaElement.getReference();
					}
				}
				
				/* execute children iteration */
				if (b_childrenIteration) {
															de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": get schema-object with type[" + o_object.getClass().getTypeName() + "] and children[" + p_o_yamlSchemaElement.getChildren().size() + "]");
					
					/* only create new object if we have one child definition for object in yaml schema */
					if (p_o_yamlSchemaElement.getChildren().size() < 1) {
						throw new IllegalArgumentException("Schema-element[" + p_o_yamlSchemaElement.getName() + "] has no children");	
					}
					
					/* check if new object is required if there is no data */
					if ( (p_o_yamlDataElement.getChildren().size() < 1) && (p_o_yamlSchemaElement.getRequired()) ) {
						throw new IllegalArgumentException("We have no data children for schema-element[" + p_o_yamlSchemaElement.getName() + "] which is required");
					}
					
					/* only iterate if we have object data */
					if (p_o_yamlDataElement.getChildren().size() > 0) {
						/* increase level for PrintTabs */
						this.i_level++;
						
						/* data pointer */
						int j = 0;
						
						for (int i = 0; i < p_o_yamlSchemaElement.getChildren().size(); i++) {
																	de.forestj.lib.Global.ilogFinest(this.printIndentation() + "compare schema-child-name(" + p_o_yamlSchemaElement.getChildren().get(i).getName() + ") with data-child-name(" + p_o_yamlDataElement.getChildren().get(j).getName() + ")");
							
							/* check if current element in schema has data element by name, otherwise skip this element */
							if (!p_o_yamlSchemaElement.getChildren().get(i).getName().contentEquals(p_o_yamlDataElement.getChildren().get(j).getName())) {
								/* but check if current schema element is required, so we must throw an exception */
								if (p_o_yamlSchemaElement.getChildren().get(i).getRequired()) {
									throw new IllegalArgumentException("We have no data for schema-element[" + p_o_yamlSchemaElement.getChildren().get(i).getName() + "] which is required");
								} else {
									continue;
								}
							}
							
							/* handle new object with recursion */
							Object o_returnObject = this.yamlDecodeRecursive(p_o_yamlDataElement.getChildren().get(j), p_o_yamlSchemaElement.getChildren().get(i), o_object);
							
							/* increase data pointer */
							j++;
							
							/* check if we got a return object of recursion */
							if (o_returnObject == null) {
								/* it is valid to return a null object, anyway keep this exception if you want to uncomment it in the future */
								/* throw new NullPointerException("Schema-element[" + p_o_yamlSchemaElement.getChildren().get(i).getName() + "] returns no object after recursion with data[" + p_o_yamlDataElement.getChildren().get(i).getName() + "]"); */
							}
						}
						
						/* decrease level for PrintTabs */
						this.i_level--;
					}
					
					if ( (p_o_object != null) && (!(p_o_object instanceof java.util.List)) ) {
																de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": set schema-object[" + o_object.getClass().getTypeName() + "] to current object[" + p_o_object.getClass().getTypeName() + "] with mapping[" + s_objectMapping + "]");
						
						this.setProperty(s_objectMapping, p_o_object, o_object);
					}
				}
				
				/* decrease level for PrintTabs */
				this.i_level--;
				
				/* set object instance as current object */
				p_o_object = o_object;
			}
		} else if (p_o_yamlSchemaElement.getType().toLowerCase().contentEquals("array")) {
			/* check conditions for handling array */
			if (p_o_yamlSchemaElement.getReference() != null) {
				if (p_o_yamlSchemaElement.getReference().getChildren().size() < 1) {
					throw new IllegalArgumentException("Reference[" + p_o_yamlSchemaElement.getReference().getName() + "] of schema-array[" + p_o_yamlSchemaElement.getName() + "] with p_o_object[" + p_o_object.getClass().getTypeName() + "] must have at least one child");
				}
			} else {
				if (p_o_yamlSchemaElement.getChildren().size() != 1) {
					throw new IllegalArgumentException("Schema-array[" + p_o_yamlSchemaElement.getName() + "] with p_o_object[" + p_o_object.getClass().getTypeName() + "] must have just one child");
				}
				
				if (!p_o_yamlSchemaElement.getChildren().get(0).getName().toLowerCase().contentEquals("items")) {
					throw new IllegalArgumentException("Schema-array[" + p_o_yamlSchemaElement.getName() + "] with p_o_object[" + p_o_object.getClass().getTypeName() + "] must have one child with name[items]");
				}
			}
			
			/* help variables to handle array */
			Object o_objectList = null;
			boolean b_required = false;
			String s_requiredProperty = "";
			java.util.List<YAMLRestriction> a_restrictions = new java.util.ArrayList<YAMLRestriction>();
			String s_amountProperty = "";
			boolean b_primitiveArray = false;
			String s_primitiveArrayMapping = "";
			
			/* check if current array element is a primitive array */
			if ( (p_o_yamlSchemaElement.getChildren().size() > 0) && (p_o_yamlSchemaElement.getChildren().get(0).getPrimitiveArray()) ) {
				/* create list object for primitive array */
				o_objectList = (Object)(new java.util.ArrayList<Object>());
				/* set flag for primitive array */
				b_primitiveArray = true;
				
				/* check if we have a mapping value for primitive array */
				if ( (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMapping())) && (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMappingClass())) ) {
					throw new IllegalArgumentException("Schema-primitive-array[" + p_o_yamlSchemaElement.getName() + "] with p_o_object[" + p_o_object.getClass().getTypeName() + "] has no mapping value");
				} else {
					/* store mapping value for later use */
					if (!(de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMapping()))) {
						s_primitiveArrayMapping = p_o_yamlSchemaElement.getMapping();
					} else if (!(de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMappingClass()))) {
						s_primitiveArrayMapping = p_o_yamlSchemaElement.getMappingClass();
					}
				}
			} else {
				/* create or retrieve object list data */
				if (p_o_object == null) { /* create a new object instance of yaml array element */
															de.forestj.lib.Global.ilogFiner(this.printIndentation() + "p_o_object == null, create new array list");
					
					/* create list object */
					o_objectList = (Object)(new java.util.ArrayList<Object>());
				} else { /* we have to retrieve the list object */
															de.forestj.lib.Global.ilogFiner(this.printIndentation() + "p_o_object(" + p_o_object.getClass().getTypeName() + ") != null, get list property " + p_o_yamlSchemaElement.getName());
					
					/* get list property */
					o_objectList = getListProperty(p_o_yamlSchemaElement, p_o_object);
	    		}
			}
			
			/* check if yaml-element is required */
			if (p_o_yamlSchemaElement.getRequired()) {
				b_required = true;
				s_requiredProperty = p_o_yamlSchemaElement.getName();
			}
			
			/* check minItems and maxItems restrictions and save them for items check afterwards */
			if (p_o_yamlSchemaElement.getRestrictions().size() > 0) {
				for (YAMLRestriction o_yamlRestriction : p_o_yamlSchemaElement.getRestrictions()) {
					if ( (o_yamlRestriction.getName().toLowerCase().contentEquals("minitems")) || (o_yamlRestriction.getName().toLowerCase().contentEquals("maxitems"))) {
						a_restrictions.add(o_yamlRestriction);
						s_amountProperty = p_o_yamlSchemaElement.getName();
					}
				}
			}
			
			if (p_o_yamlSchemaElement.getReference() != null) {
				/* set reference as current yaml element */
				p_o_yamlSchemaElement = p_o_yamlSchemaElement.getReference();
			} else {
				/* set current yaml element to 'items' child */
				p_o_yamlSchemaElement = p_o_yamlSchemaElement.getChildren().get(0);
				
				/* if 'items' child has a child as well, we take this child as current yaml element */
				if (p_o_yamlSchemaElement.getChildren().size() == 1) {
					p_o_yamlSchemaElement = p_o_yamlSchemaElement.getChildren().get(0);
				}
				
				/* important part for parsing collection which are not inline collection values in yaml document */
				if ( (!p_o_yamlDataElement.getName().contentEquals("__ArrayObject__")) && (p_o_yamlSchemaElement.getName().toLowerCase().contentEquals("items")) ) {
					if ( (p_o_yamlDataElement.getChildren().size() > 0) && (p_o_yamlDataElement.getChildren().get(0).getName().contentEquals("__ArrayObject__")) ) {
						p_o_yamlDataElement = p_o_yamlDataElement.getChildren().get(0);
					}
				}
			}
			
			if (de.forestj.lib.Helper.isStringEmpty(p_o_yamlDataElement.getValue())) { /* we have multiple minor objects for current array */
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": get schema-array with mapping[" + p_o_yamlSchemaElement.printMapping() + "]");
				
				if (p_o_yamlDataElement.getChildren().size() > 0) { /* if we have objects to the new array */
					/* check minItems and maxItems restrictions */
					if (a_restrictions.size() > 0) {
						for (YAMLRestriction o_yamlRestriction : a_restrictions) {
							if (o_yamlRestriction.getName().toLowerCase().contentEquals("minitems")) {
								/* check minItems restriction */
								if (p_o_yamlDataElement.getChildren().size() < o_yamlRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: not enough [" + p_o_yamlSchemaElement.getName() + " of " + s_amountProperty + "] yaml items(" + p_o_yamlDataElement.getChildren().size() + "), minimum = " + o_yamlRestriction.getIntValue());
								}
							}
							
							if (o_yamlRestriction.getName().toLowerCase().contentEquals("maxitems")) {
								/* check maxItems restriction */
								if (p_o_yamlDataElement.getChildren().size() > o_yamlRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: too many [" + p_o_yamlSchemaElement.getName() + " of " + s_amountProperty + "] yaml items(" + p_o_yamlDataElement.getChildren().size() + "), maximum = " + o_yamlRestriction.getIntValue());
								}
							}
						}
					}
					
					/* iterate objects in list and encode data to yaml recursively */
					for (int i = 0; i < p_o_yamlDataElement.getChildren().size(); i++) {
						/* increase level for PrintTabs */
						this.i_level++;
						
						/* handle array object with recursion */
						Object o_returnObject = this.yamlDecodeRecursive(p_o_yamlDataElement.getChildren().get(i), p_o_yamlSchemaElement, o_objectList);
						
						/* cast current object to list */
						@SuppressWarnings("unchecked")
						java.util.List<Object> o_temp = (java.util.List<Object>)o_objectList;
						
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "add return value to list of " + p_o_yamlDataElement.getName() + ", type[" + ((o_returnObject == null) ? "object is null" : o_returnObject.getClass().getTypeName()) + "]");
						
						/* add return object of recursion to list */
						o_temp.add(o_returnObject);
						
						/* decrease level for PrintTabs */
						this.i_level--;
					}
				}
			} else { /* array objects must be retrieved out of value property */
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": handle array value = " + p_o_yamlDataElement.getValue() + " - mapping[" + p_o_yamlSchemaElement.printMapping() + "]");
				
				/* cast current object to list */
				@SuppressWarnings("unchecked")
				java.util.List<Object> o_temp = (java.util.List<Object>)o_objectList;
				
				/* set array with values if we have any values */
				if ( (!de.forestj.lib.Helper.isStringEmpty(p_o_yamlDataElement.getValue())) && (!p_o_yamlDataElement.getValue().contentEquals("[]")) ) {
					/* remove opening and closing brackets */
					if ( (p_o_yamlDataElement.getValue().startsWith("[")) && (p_o_yamlDataElement.getValue().endsWith("]")) ) {
						p_o_yamlDataElement.setValue(p_o_yamlDataElement.getValue().substring(1, p_o_yamlDataElement.getValue().length() - 1));
					}
					
					/* split array into values, divided by ',' */
					String[] a_values = p_o_yamlDataElement.getValue().split(",");
					
					/* check minItems and maxItems restrictions */
					if (a_restrictions.size() > 0) {
						for (YAMLRestriction o_yamlRestriction : a_restrictions) {
							if (o_yamlRestriction.getName().toLowerCase().contentEquals("minitems")) {
								/* check minItems restriction */
								if (a_values.length < o_yamlRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: not enough [" + p_o_yamlSchemaElement.getName() + " of " + s_amountProperty + "] yaml items(" + a_values.length + "), minimum = " + o_yamlRestriction.getIntValue());
								}
							}
							
							if (o_yamlRestriction.getName().toLowerCase().contentEquals("maxitems")) {
								/* check maxItems restriction */
								if (a_values.length > o_yamlRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: too many [" + p_o_yamlSchemaElement.getName() + " of " + s_amountProperty + "] yaml items(" + a_values.length + "), maximum = " + o_yamlRestriction.getIntValue());
								}
							}
						}
					}
					
					/* iterate all array values */
					for (String s_value : a_values) {
						/* trim any whitespace characters from string value */
						s_value = s_value.trim();
						
						/* get yaml value type of string value */
						YAMLValueType e_yamlValueType = this.getYAMLValueType(s_value);
						
						/* check if YAML value types are matching between schema and data, if it is not 'null' */
						if ( (e_yamlValueType != stringToYAMLValueType(p_o_yamlSchemaElement.getType())) && (e_yamlValueType != YAMLValueType.Null) && ( (s_value != null) && (!s_value.contentEquals("null")) ) ) {
							throw new IllegalArgumentException("YAML schema type[" + stringToYAMLValueType(p_o_yamlSchemaElement.getType()) + "] does not match with data value type[" + e_yamlValueType + "] with value[" + s_value + "]");
						}
						
						/* check if yaml-element has any restrictions */
						if (p_o_yamlSchemaElement.getRestrictions().size() > 0) {
							for (YAMLRestriction o_yamlRestriction : p_o_yamlSchemaElement.getRestrictions()) {
								/* execute restriction check */
								this.checkRestriction(s_value, o_yamlRestriction, p_o_yamlSchemaElement);
							}
						}
						
						/* cast array string value into object */
						Object o_returnObject = this.castObjectFromString(s_value, p_o_yamlSchemaElement.getType());
						
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "add value[" + ( (o_returnObject == null) ? "null" : o_returnObject ) + "] to list of " + p_o_yamlDataElement.getName() + ", type[" + ( (o_returnObject == null) ? "null" : o_returnObject.getClass().getTypeName() ) + "]");
						
						/* add return object of recursion to list */
						o_temp.add(o_returnObject);
					}
				} else if (b_required) { /* if yaml-element with type array is required, throw exception */
					throw new IllegalArgumentException("'" + p_o_yamlSchemaElement.getName() + "' of '" + s_requiredProperty + "' is required, but array has no values");
				}
			}
			
			/* if we have a primitive array, we take our object list and convert it to a primitive array */
			if (b_primitiveArray) {
				/* we must have an object to set primitive array property */
				if (p_o_object == null) {
					throw new NullPointerException("YAML schema element[" + p_o_yamlSchemaElement.getName() + "] has not initiated object for primitive array[" + s_primitiveArrayMapping + "]");
				}
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "set primitive array for [" + s_primitiveArrayMapping + "] of object [" + p_o_object.getClass().getTypeName() + "]");
				
				/* set primitive array property of object */
				this.setPrimitiveArrayProperty(s_primitiveArrayMapping, p_o_object, o_objectList);
			}
			
			/* set array instance as current object, if it is still null */
			if (p_o_object == null) {
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "p_o_object == null, set p_o_object = o_objectList");
				p_o_object = o_objectList;
			}
		} else {
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_yamlSchemaElement.getName() + ": handle value = " + p_o_yamlDataElement.getValue() + " - mapping[" + p_o_yamlSchemaElement.printMapping() + "]");
			
			YAMLValueType e_yamlValueType = this.getYAMLValueType(p_o_yamlDataElement.getValue());
			
			/* check if YAML value types are matching between schema and data, additional condition = YAML value type is not 'null' */
			if ( (e_yamlValueType != stringToYAMLValueType(p_o_yamlSchemaElement.getType())) && (e_yamlValueType != YAMLValueType.Null) ) {
				/* it is acceptable if source type is 'integer' and destination type is 'number', valid cast available */
				if (!( (e_yamlValueType == YAMLValueType.Integer) && (stringToYAMLValueType(p_o_yamlSchemaElement.getType()) == YAMLValueType.Number) ) ) {
					throw new IllegalArgumentException("YAML schema type[" + e_yamlValueType + "] does not match with data value type[" + stringToYAMLValueType(p_o_yamlSchemaElement.getType()) + "] with value[" + p_o_yamlDataElement.getValue() + "]");
				}
			}
			
			/* check if yaml-element is required */
			if (p_o_yamlSchemaElement.getRequired()) {
				/* check if value is empty */
				if ( (p_o_yamlDataElement.getValue().contentEquals("")) || (p_o_yamlDataElement.getValue().contentEquals("null")) || (p_o_yamlDataElement.getValue().contentEquals("\"\"")) ) {
					throw new NullPointerException("'" + p_o_yamlSchemaElement.getName() + "' is required, but value[" + p_o_yamlDataElement.getValue() + "] is empty");
				}
			}
			
			/* check if yaml-element has any restrictions */
			if (p_o_yamlSchemaElement.getRestrictions().size() > 0) {
				for (YAMLRestriction o_yamlRestriction : p_o_yamlSchemaElement.getRestrictions()) {
					/* execute restriction check */
					this.checkRestriction(p_o_yamlDataElement.getValue(), o_yamlRestriction, p_o_yamlSchemaElement);
				}
			}
			
			if ( (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMapping())) && (de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMappingClass())) ) {
				p_o_object = this.castObjectFromString(p_o_yamlDataElement.getValue(), p_o_yamlSchemaElement.getType());
			} else {
				this.setProperty(p_o_yamlSchemaElement, p_o_object, p_o_yamlDataElement.getValue());
			}
		}
		
												de.forestj.lib.Global.ilogFine(this.printIndentation() + "return " + ((p_o_object == null) ? "null" : p_o_object.getClass().getTypeName()));
		return p_o_object;
	}
	
	/**
	 * Convert a string value of yaml type to yaml type enumeration value
	 * 
	 * @param p_s_yamlValueType			yaml type string from yaml element
	 * @return							yaml type enumeration value
	 * @throws IllegalArgumentException	invalid string type parameter
	 */
	private YAMLValueType stringToYAMLValueType(String p_s_yamlValueType) throws IllegalArgumentException {
		p_s_yamlValueType = p_s_yamlValueType.toLowerCase();
		
		if (p_s_yamlValueType.contentEquals("string")) {
			return YAMLValueType.String;
		} else if (p_s_yamlValueType.contentEquals("number")) {
			return YAMLValueType.Number;
		} else if (p_s_yamlValueType.contentEquals("integer")) {
			return YAMLValueType.Integer;
		} else if (p_s_yamlValueType.contentEquals("boolean")) {
			return YAMLValueType.Boolean;
		} else if (p_s_yamlValueType.contentEquals("array")) {
			return YAMLValueType.Array;
		} else if (p_s_yamlValueType.contentEquals("object")) {
			return YAMLValueType.Object;
		} else if (p_s_yamlValueType.contentEquals("null")) {
			return YAMLValueType.Null;
		} else {
			throw new IllegalArgumentException("Invalid YAML value type with value [" + p_s_yamlValueType + "]");
		}
	}
	
	/**
	 * Get a java.util.List object from object's property, access directly via public field or public methods
	 * 
	 * @param p_o_yamlSchemaElement			yaml schema element with mapping information	
	 * @param p_o_object					object where to retrieve java.util.List object
	 * @return								java.util.List object
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws NullPointerException			list object is not initialized
	*/
	private Object getListProperty(YAMLElement p_o_yamlSchemaElement, Object p_o_object) throws NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, NullPointerException {
		/* retrieve field information out of schema element */
		String s_field = p_o_yamlSchemaElement.getMappingClass();
		
		/* if there is additional mapping information, use this for field property access */
		if (!de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMapping())) {
			s_field = p_o_yamlSchemaElement.getMapping();
		}
		
		/* check if we use property methods with invoke to set object data values */
		if (this.b_usePropertyMethods) {
			java.lang.reflect.Method o_method = null;
			boolean b_methodFound = false;
			
			/* look for get-property-method for list object */
			for (java.lang.reflect.Method o_methodSearch : p_o_object.getClass().getDeclaredMethods()) {
				if (o_methodSearch.getName().contentEquals("get" + s_field)) {
					o_method = o_methodSearch;
					b_methodFound = true;
				}
			}
			
			/* if we have found property-method to get list object */
			if (b_methodFound) {
				/* invoke get-property-method */
				p_o_object = o_method.invoke(p_o_object);
			} else {
				throw new NoSuchMethodException("Method[" + "get" + s_field + "] does not exist for object: " + p_o_object.getClass().getTypeName());
			}
		} else {
			/* call field directly to set object data values */
			try {
				p_o_object = p_o_object.getClass().getDeclaredField(s_field).get(p_o_object);
			} catch (Exception o_exc) {
				throw new NoSuchFieldException("Property[" + s_field + "] is not accessible for object: " + p_o_object.getClass().getTypeName());
			}
		}
		
		/* check if list object is not null */
		if (p_o_object == null) {
			throw new NullPointerException("List object from method[" + "get" + s_field + "] not initialised for object");
		}
		
		/* check if object is of type List */
		if (!(p_o_object instanceof java.util.List)) {
			throw new NoSuchFieldException("Object from method[" + "get" + s_field + "] is not a list object for object: " + p_o_object.getClass().getTypeName());
		}
		
		return p_o_object;
	}
	
	/**
	 * Method to set property field of an object with value of yaml element from file line and mapping value of yaml schema element
	 * 
	 * @param p_o_yamlSchemaElement			mapping class and type hint to cast value to object's field from yaml schema
	 * @param p_o_object					object parameter where yaml data will be decoded and cast into object fields
	 * @param p_s_objectValue				string value of yaml element from file line
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private void setProperty(YAMLElement p_o_yamlSchemaElement, Object p_o_object, String p_s_objectValue) throws NoSuchMethodException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException {
		/* remove surrounded double quotes from value */
		if ( (p_s_objectValue != null) && (p_s_objectValue.startsWith(this.s_stringQuote)) && (p_s_objectValue.endsWith(this.s_stringQuote)) ) {
			p_s_objectValue = p_s_objectValue.substring(1, p_s_objectValue.length() - 1);
		}
		
		/* retrieve field information out of schema element */
		String s_field = p_o_yamlSchemaElement.getMappingClass();
		
		/* if there is additional mapping information, use this for field property access */
		if (!de.forestj.lib.Helper.isStringEmpty(p_o_yamlSchemaElement.getMapping())) {
			s_field = p_o_yamlSchemaElement.getMapping();
		}
		
		/* check if we use property methods with invoke to set object data values */
		if (this.b_usePropertyMethods) {
			java.lang.reflect.Method o_method = null;
			boolean b_methodFound = false;
			
			/* look for set-property-method of current parameter object value */
			for (java.lang.reflect.Method o_methodSearch : p_o_object.getClass().getDeclaredMethods()) {
				if (o_methodSearch.getName().contentEquals("set" + s_field)) {
					o_method = o_methodSearch;
					b_methodFound = true;
				}
			}
			
			if (!b_methodFound) {
				throw new NoSuchMethodException("Method[" + "set" + s_field + "] does not exist for object: " + p_o_object.getClass().getTypeName());
			}
			
			try {
				/* invoke set-property-method to set object data field of current element and cast string to object value with yaml schema type */
				o_method.invoke(p_o_object, this.castObjectFromString(p_s_objectValue, p_o_yamlSchemaElement.getType()));
			} catch (IllegalArgumentException o_exc) {
														de.forestj.lib.Global.ilogFinest(this.printIndentation() + o_exc.getMessage() + " - with type[" + p_o_yamlSchemaElement.getType() + "] - try another cast with type[" + p_o_object.getClass().getDeclaredField(s_field).getType().getTypeName() + "]");
				/* invoke set-property-method to set object data field of current element and cast string to object value */
				o_method.invoke(p_o_object, this.castObjectFromString(p_s_objectValue, p_o_object.getClass().getDeclaredField(s_field).getType().getTypeName()));
			}	
		} else {
			/* call field directly to set object data values */
			try {
				p_o_object.getClass().getDeclaredField(s_field).set(p_o_object, this.castObjectFromString(p_s_objectValue, p_o_yamlSchemaElement.getType()));
			} catch (IllegalArgumentException o_exc) {
														de.forestj.lib.Global.ilogFinest(this.printIndentation() + o_exc.getMessage() + " - with type[" + p_o_yamlSchemaElement.getType() + "]  - try another cast with type[" + p_o_object.getClass().getDeclaredField(s_field).getType().getTypeName() + "]");
				p_o_object.getClass().getDeclaredField(s_field).set(p_o_object, this.castObjectFromString(p_s_objectValue, p_o_object.getClass().getDeclaredField(s_field).getType().getTypeName()));
			} catch (Exception o_exc) {
				throw new NoSuchFieldException("Property[" + s_field + "] is not accessible for object: " + p_o_object.getClass().getTypeName());
			}
		}
	}
	
	/**
	 * Method to set property field of an object with simple object value, so no cast will be done
	 * 
	 * @param p_s_mapping					mapping class and type hint to cast value to object's field
	 * @param p_o_object					object parameter where yaml data will be decoded and cast into object fields
	 * @param p_o_objectValue				object value of yaml element from file line
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 */
	private void setProperty(String p_s_mapping, Object p_o_object, Object p_o_objectValue) throws NoSuchMethodException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, IllegalAccessException {
		/* check if we use property methods with invoke to set object data values */
		if (this.b_usePropertyMethods) {
			java.lang.reflect.Method o_method = null;
			boolean b_methodFound = false;
			
			/* look for set-property-method of current parameter object value */
			for (java.lang.reflect.Method o_methodSearch : p_o_object.getClass().getDeclaredMethods()) {
				if (o_methodSearch.getName().contentEquals("set" + p_s_mapping)) {
					o_method = o_methodSearch;
					b_methodFound = true;
				}
			}
			
			if (!b_methodFound) {
				throw new NoSuchMethodException("Method[" + "set" + p_s_mapping + "] does not exist for object: " + p_o_object.getClass().getTypeName());
			}
			
			/* invoke set-property-method to set object data field of current element and cast string to object value */
			o_method.invoke(p_o_object, p_o_objectValue);
		} else {
			/* call field directly to set object data values */
			try {
				p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, p_o_objectValue);
			} catch (Exception o_exc) {
				throw new NoSuchFieldException("Property[" + p_s_mapping + "] is not accessible for object: " + p_o_object.getClass().getTypeName());
			}
		}
	}
	
	/**
	 * Method to set primitive array property field of an object with simple object value, so no cast will be done
	 * 
	 * @param p_s_mapping					mapping class and type hint to cast value to object's primitive array field
	 * @param p_o_object					object parameter where yaml data will be decoded and cast into object fields
	 * @param p_o_objectValue				object value of yaml element from file line
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws IllegalArgumentException		invalid type parameter
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private void setPrimitiveArrayProperty(String p_s_mapping, Object p_o_object, Object p_o_objectValue) throws NoSuchMethodException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, IllegalAccessException, IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		/* cast object value parameter to object list */
		@SuppressWarnings("unchecked")
		java.util.List<Object> o_foo = (java.util.List<Object>)p_o_objectValue;
		
		/* check if we use property methods with invoke to set object data values */
		if (this.b_usePropertyMethods) {
			java.lang.reflect.Method o_method = null;
			boolean b_methodFound = false;
			
			/* look for set-property-method of current parameter object value */
			for (java.lang.reflect.Method o_methodSearch : p_o_object.getClass().getDeclaredMethods()) {
				if (o_methodSearch.getName().contentEquals("set" + p_s_mapping)) {
					o_method = o_methodSearch;
					b_methodFound = true;
				}
			}
			
			if (!b_methodFound) {
				throw new NoSuchMethodException("Method[" + "set" + p_s_mapping + "] does not exist for object: " + p_o_object.getClass().getTypeName());
			}
			
			/* get primitive array type */
			String s_primitiveArrayType = o_method.getParameterTypes()[0].getTypeName();
			
			/* remove '[]' from type value */
			if (s_primitiveArrayType.endsWith("[]")) {
				s_primitiveArrayType = s_primitiveArrayType.substring(0, s_primitiveArrayType.length() - 2);
			}
			
			if (o_foo.size() < 1) {
				/* set primitive array to null if we have no elements */
				o_method.invoke(p_o_object, new Object[] {null});
			} else {
				/* cast object list to primitive array, invoke set-property-method to set object array field of current element */
				if ( (s_primitiveArrayType.contentEquals("boolean")) || (s_primitiveArrayType.contentEquals("java.lang.Boolean")) ) {
					boolean[] o_bar = new boolean[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (boolean)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("byte")) || (s_primitiveArrayType.contentEquals("java.lang.Byte")) ) {
					byte[] o_bar = new byte[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (byte)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("char")) || (s_primitiveArrayType.contentEquals("java.lang.Character")) ) {
					char[] o_bar = new char[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (char)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("float")) || (s_primitiveArrayType.contentEquals("java.lang.Float")) ) {
					float[] o_bar = new float[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (float)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("double")) || (s_primitiveArrayType.contentEquals("java.lang.Double")) ) {
					double[] o_bar = new double[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (double)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("short")) || (s_primitiveArrayType.contentEquals("java.lang.Short")) ) {
					short[] o_bar = new short[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (short)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("int")) || (s_primitiveArrayType.contentEquals("java.lang.Integer")) ) {
					int[] o_bar = new int[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (int)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("long")) || (s_primitiveArrayType.contentEquals("java.lang.Long")) ) {
					long[] o_bar = new long[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (long)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if (s_primitiveArrayType.contentEquals("java.util.Date")) {
					java.util.Date[] o_bar = new java.util.Date[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (java.util.Date)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if (s_primitiveArrayType.contentEquals("java.time.LocalTime")) {
					java.time.LocalTime[] o_bar = new java.time.LocalTime[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (java.time.LocalTime)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if (s_primitiveArrayType.contentEquals("java.time.LocalDate")) {
					java.time.LocalDate[] o_bar = new java.time.LocalDate[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (java.time.LocalDate)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if (s_primitiveArrayType.contentEquals("java.time.LocalDateTime")) {
					java.time.LocalDateTime[] o_bar = new java.time.LocalDateTime[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (java.time.LocalDateTime)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if (s_primitiveArrayType.contentEquals("java.math.BigDecimal")) {
					java.math.BigDecimal[] o_bar = new java.math.BigDecimal[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (java.math.BigDecimal)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else {
					throw new IllegalArgumentException("Invalid type [" + s_primitiveArrayType + "] for property[" + p_s_mapping + "] and object [" + p_o_object.getClass().getTypeName() + "]");
				}
			}
		} else {
			/* call array field directly to set object array values */
			try {
				/* get primitive array type */
				String s_primitiveArrayType = p_o_object.getClass().getDeclaredField(p_s_mapping).getType().getTypeName();
				
				/* remove '[]' from type value */
				if (s_primitiveArrayType.endsWith("[]")) {
					s_primitiveArrayType = s_primitiveArrayType.substring(0, s_primitiveArrayType.length() - 2);
				}
				
				if (o_foo.size() < 1) {
					/* set primitive array to null if we have no elements */
					p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, null);
				} else {
					/* cast object list to primitive array, set object array field of current element */
					if ( (s_primitiveArrayType.contentEquals("boolean")) || (s_primitiveArrayType.contentEquals("java.lang.Boolean")) ) {
						boolean[] o_bar = new boolean[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (boolean)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("byte")) || (s_primitiveArrayType.contentEquals("java.lang.Byte")) ) {
						byte[] o_bar = new byte[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (byte)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("char")) || (s_primitiveArrayType.contentEquals("java.lang.Character")) ) {
						char[] o_bar = new char[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (char)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("float")) || (s_primitiveArrayType.contentEquals("java.lang.Float")) ) {
						float[] o_bar = new float[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (float)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("double")) || (s_primitiveArrayType.contentEquals("java.lang.Double")) ) {
						double[] o_bar = new double[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (double)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("short")) || (s_primitiveArrayType.contentEquals("java.lang.Short")) ) {
						short[] o_bar = new short[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (short)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("int")) || (s_primitiveArrayType.contentEquals("java.lang.Integer")) ) {
						int[] o_bar = new int[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (int)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("long")) || (s_primitiveArrayType.contentEquals("java.lang.Long")) ) {
						long[] o_bar = new long[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (long)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if (s_primitiveArrayType.contentEquals("java.util.Date")) {
						java.util.Date[] o_bar = new java.util.Date[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (java.util.Date)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if (s_primitiveArrayType.contentEquals("java.time.LocalTime")) {
						java.time.LocalTime[] o_bar = new java.time.LocalTime[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (java.time.LocalTime)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if (s_primitiveArrayType.contentEquals("java.time.LocalDate")) {
						java.time.LocalDate[] o_bar = new java.time.LocalDate[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (java.time.LocalDate)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if (s_primitiveArrayType.contentEquals("java.time.LocalDateTime")) {
						java.time.LocalDateTime[] o_bar = new java.time.LocalDateTime[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (java.time.LocalDateTime)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if (s_primitiveArrayType.contentEquals("java.math.BigDecimal")) {
						java.math.BigDecimal[] o_bar = new java.math.BigDecimal[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (java.math.BigDecimal)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else {
						throw new IllegalArgumentException("Invalid type [" + s_primitiveArrayType + "] for field[" + p_s_mapping + "] and object [" + p_o_object.getClass().getTypeName() + "]");
					}
				}
			} catch (NoSuchFieldException o_exc) {
				throw new NoSuchFieldException("Property[" + p_s_mapping + "] is not accessible for object: " + p_o_object.getClass().getTypeName() + " - " + o_exc.getMessage());
			}
		}
	}

	/**
	 * Convert a string value from a yaml element to an object to decode it into an object
	 * 
	 * @param p_s_value						string value of yaml element from file
	 * @param p_s_type						type of destination object field
	 * @return								casted object value from string
	 * @throws IllegalArgumentException		invalid type parameter
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private Object castObjectFromString(String p_s_value, String p_s_type) throws IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		Object o_foo = null;
		
		/* return null if value is null */
		if (p_s_value == null) {
			return o_foo;
		}
		
		/* check if value is not empty */
		if (!p_s_value.contentEquals("")) {
			p_s_type = p_s_type.toLowerCase();
			
			/* cast string value into object */
			if (p_s_type.contentEquals("string")) {
				/* recognize date format ISO-8601 */
				if (de.forestj.lib.Helper.isDateTime(p_s_value)) {
					o_foo = de.forestj.lib.Helper.fromISO8601UTCToUtilDate(p_s_value);
				} else {
					if (p_s_value.contains("\\" + this.s_stringQuote)) {
						p_s_value = p_s_value.replaceAll("\\\\" + this.s_stringQuote, this.s_stringQuote);
					}
					
					o_foo = p_s_value;
				}
			} else if (p_s_type.contentEquals("java.util.date")) {
				if (de.forestj.lib.Helper.isDateTime(p_s_value)) {
					o_foo = de.forestj.lib.Helper.fromISO8601UTCToUtilDate(p_s_value);
				} else {
					throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'java.util.date'");
				}
			} else if (p_s_type.contentEquals("java.time.localdatetime")) {
				if (de.forestj.lib.Helper.isDateTime(p_s_value)) {
					o_foo = de.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				} else {
					throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'java.time.localdatetime'");
				}
			} else if (p_s_type.contentEquals("java.time.localdate")) {
				if (de.forestj.lib.Helper.isDateTime(p_s_value)) {
					o_foo = de.forestj.lib.Helper.fromISO8601UTC(p_s_value).toLocalDate();
				} else {
					throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'java.time.localdate'");
				}
			} else if (p_s_type.contentEquals("java.time.localtime")) {
				if (de.forestj.lib.Helper.isDateTime(p_s_value)) {
					o_foo = de.forestj.lib.Helper.fromISO8601UTC(p_s_value).toLocalTime();
				} else {
					throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'java.time.localtime'");
				}
			} else if ( (p_s_type.contentEquals("number")) || (p_s_type.contentEquals("java.math.bigdecimal")) ) {
				if (!p_s_value.contentEquals("null")) {
					java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat();
					o_decimalFormat.setParseBigDecimal(true);
					o_foo = o_decimalFormat.parseObject(p_s_value);
					o_foo = new java.math.BigDecimal(p_s_value);
				}
			} else if ( (p_s_type.contentEquals("float")) || (p_s_type.contentEquals("java.lang.float")) ) {
				if (!p_s_value.contentEquals("null")) {
					o_foo = Float.parseFloat(p_s_value);
				}
			} else if ( (p_s_type.contentEquals("double")) || (p_s_type.contentEquals("java.lang.double")) ) {
				if (!p_s_value.contentEquals("null")) {
					o_foo = Double.parseDouble(p_s_value);
				}
			} else if ( (p_s_type.contentEquals("short")) || (p_s_type.contentEquals("java.lang.short")) ) {
				if (!p_s_value.contentEquals("null")) {
					o_foo = Short.parseShort(p_s_value);
				}
			} else if ( (p_s_type.contentEquals("long")) || (p_s_type.contentEquals("java.lang.long")) ) {
				if (!p_s_value.contentEquals("null")) {
					o_foo = Long.parseLong(p_s_value);
				}
			} else if ( (p_s_type.contentEquals("integer")) || (p_s_type.contentEquals("java.lang.integer")) ) {
				if (!p_s_value.contentEquals("null")) {
					o_foo = Integer.parseInt(p_s_value);
				}
			} else if ( (p_s_type.contentEquals("boolean")) || (p_s_type.contentEquals("java.lang.boolean")) ) {
				if (!p_s_value.contentEquals("null")) {
					o_foo = Boolean.valueOf(p_s_value);
				}
			} else if (p_s_type.contentEquals("null")) {
				o_foo = null;
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] for " + p_s_value);
			}
		} else {
			o_foo = "";
		}
		
		return o_foo;
	}
	
	/* Internal Classes */
	
	/**
	 * Enumeration of valid yaml types
	 */
	public enum YAMLValueType {
		String, Number, Integer, Boolean, Array, Object, Null
	}
	
	/**
	 * Encapsulation of a schema's yaml element
	 */
	public class YAMLElement {

		/* Fields */
		
		private String s_name;
		private int i_Level;
		private String s_type;
		private String s_description;
		private String s_default;
		private String s_value;
		private String s_mapping;
		private String s_mappingClass;
		private boolean b_required;
		private boolean b_primitiveArray;
		private YAMLElement o_reference;
		private java.util.List<YAMLElement> a_children = new java.util.ArrayList<YAMLElement>();
		private java.util.List<YAMLRestriction> a_restrictions = new java.util.ArrayList<YAMLRestriction>();
		
		/* Properties */
		
		public String getName() {
			return this.s_name;
		}
		
		public void setName(String p_s_value) {
			this.s_name = p_s_value;
		}
		
		public int getLevel() {
			return this.i_Level;
		}
		
		public void setLevel(int p_i_value) {
			this.i_Level = p_i_value;
		}
		
		public String getType() {
			return this.s_type;
		}
		
		public void setType(String p_s_value) {
			this.s_type = p_s_value;
		}
			
		public String getDescription() {
			return this.s_description;
		}
		
		public void setDescription(String p_s_value) {
			this.s_description = p_s_value;
		}
		
		public String getDefault() {
			return this.s_default;
		}
		
		public void setDefault(String p_s_value) {
			this.s_default = p_s_value;
		}
		
		public String getValue() {
			return this.s_value;
		}
		
		public void setValue(String p_s_value) {
			this.s_value = p_s_value;
		}
		
		public String getMapping() {
			return this.s_mapping;
		}
		
		public void setMapping(String p_s_value) {
			this.s_mapping = p_s_value;
		}
		
		public String getMappingClass() {
			return this.s_mappingClass;
		}
		
		public void setMappingClass(String p_s_value) {
			this.s_mappingClass = p_s_value;
		}
		
		public boolean getRequired() {
			return this.b_required;
		}
		
		public void setRequired(boolean p_b_value) {
			this.b_required = p_b_value;
		}
		
		public boolean getPrimitiveArray() {
			return this.b_primitiveArray;
		}
		
		public void setPrimitiveArray(boolean p_b_value) {
			this.b_primitiveArray = p_b_value;
		}
				
		public YAMLElement getReference() {
			return this.o_reference;
		}
		
		public void setReference(YAMLElement p_o_value) {
			this.o_reference = p_o_value;
		}
		
		public java.util.List<YAMLElement> getChildren() {
			return this.a_children;
		}
		
		public java.util.List<YAMLRestriction> getRestrictions() {
			return this.a_restrictions;
		}
		
		/* Methods */
		
		/**
		 * YAMLElement constructor with empty string as name and level 0
		 */
		public YAMLElement() {
			this("", 0);
		}
		
		/**
		 * YAMLElement constructor with parameter name and level 0
		 * 
		 * @param p_s_name		name of yaml element in schema
		 */
		public YAMLElement(String p_s_name) {
			this(p_s_name, 0);
		}
		
		/**
		 * YAMLElement constructor
		 * 
		 * @param p_s_name		name of yaml element in schema
		 * @param p_i_level		level of yaml element
		 */
		public YAMLElement(String p_s_name, int p_i_level) {
			this.setName(p_s_name);
			this.setLevel(p_i_level);
		}
		
		/**
		 * Print i tabs as indentation, i is the level of the yaml element
		 * 
		 * @return	indentation string
		 */
		private String printTabs() {
			String s_foo = "";
			
			for (int i = 0; i < this.i_Level; i++) {
				s_foo += "\t";
			}
			
			return s_foo;
		}
		
		/**
		 * Returns each field of yaml element with name and value, separated by a pipe '|'
		 */
		@Override
		public String toString() {
			String s_foo = this.printTabs() + "YAMLElement: ";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				try {
					if (o_field.get(this) instanceof java.util.List<?>) {
						java.util.List<?> a_objects = (java.util.List<?>)o_field.get(this);
						
						if (a_objects.size() > 0) {
							s_foo += de.forestj.lib.io.File.NEWLINE;
							
							for (Object o_object : a_objects) {
								s_foo += o_object.toString() + de.forestj.lib.io.File.NEWLINE;
							}
						} else {
							s_foo += o_field.getName() + " = null|";
						}
					} else {
						s_foo += o_field.getName() + " = " + o_field.get(this).toString() + "|";
					}
				} catch (Exception o_exc) {
					if (o_exc instanceof java.lang.NullPointerException) {
						s_foo += o_field.getName() + " = null|";
					} else {
						s_foo += o_field.getName() + " = ERR:AccessViolation|";
					}
				}
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
					
			return s_foo;
		}
		
		/**
		 * Returns mapping string of yaml element, mapping class or a combination of mapping and mapping class separated by ':'
		 */
		public String printMapping() {
			String s_foo = "";
			
			if ( (de.forestj.lib.Helper.isStringEmpty(this.getMapping())) && (!de.forestj.lib.Helper.isStringEmpty(this.getMappingClass())) ) {
				s_foo = this.getMappingClass();
			} else if ( (!de.forestj.lib.Helper.isStringEmpty(this.getMapping())) && (!de.forestj.lib.Helper.isStringEmpty(this.getMappingClass())) ) {
				s_foo = this.getMapping() + ":" + this.getMappingClass();
			}
			
			return s_foo;
		}
	}
	
	/**
	 * Encapsulation of a schema's yaml element restrictions
	 */
	public class YAMLRestriction {
		
		/* Fields */
		
		private String s_name;
		private int i_level;
		private String s_strValue;
		private int i_intValue;
		
		/* Properties */
		
		public String getName() {
			return this.s_name;
		}
		
		public void setName(String p_s_value) {
			this.s_name = p_s_value;
		}
		
		public int getLevel() {
			return this.i_level;
		}
		
		public void setLevel(int p_i_value) {
			this.i_level = p_i_value;
		}
		
		public String getStrValue() {
			return this.s_strValue;
		}
		
		public void setStrValue(String p_s_value) {
			this.s_strValue = p_s_value;
		}
		
		public int getIntValue() {
			return this.i_intValue;
		}
		
		public void setIntValue(int p_i_value) {
			this.i_intValue = p_i_value;
		}
		
		/* Methods */
		
		/**
		 * YAMLRestriction constructor, no name value [= null], level 0, no string value [= null] and no integer value [= 0]
		 */
		public YAMLRestriction() {
			this("", 0, "", 0);
		}
		
		/**
		 * YAMLRestriction constructor, level 0, no string value [= null] and no integer value [= 0]
		 * 
		 * @param p_s_name			name of yaml element restriction
		 */
		public YAMLRestriction(String p_s_name) {
			this(p_s_name, 0, "", 0);
		}
		
		/**
		 * YAMLRestriction constructor, no string value [= null] and no integer value [= 0]
		 * 
		 * @param p_s_name			name of yaml element restriction
		 * @param p_i_level			level of yaml element restriction
		 */
		public YAMLRestriction(String p_s_name, int p_i_level) {
			this(p_s_name, p_i_level, "", 0);
		}
		
		/**
		 * YAMLRestriction constructor, no integer value [= 0]
		 * 
		 * @param p_s_name			name of yaml element restriction
		 * @param p_i_level			level of yaml element restriction
		 * @param p_s_strValue		string value of restriction
		 */
		public YAMLRestriction(String p_s_name, int p_i_level, String p_s_strValue) {
			this(p_s_name, p_i_level, p_s_strValue, 0);
		}
		
		/**
		 * YAMLRestriction constructor
		 * 
		 * @param p_s_name			name of yaml element restriction
		 * @param p_i_level			level of yaml element restriction
		 * @param p_s_strValue		string value of restriction
		 * @param p_i_intValue		integer value of restriction
		 */
		public YAMLRestriction(String p_s_name, int p_i_level, String p_s_strValue, int p_i_intValue) {
			this.setName(p_s_name);
			this.setLevel(p_i_level);
			this.setStrValue(p_s_strValue);
			this.setIntValue(p_i_intValue);
		}

		/**
		 * Print i tabs as indentation, i is the level of the yaml element
		 * 
		 * @return	indentation string
		 */
		private String printTabs() {
			String s_foo = "";
			
			for (int i = 0; i < this.i_level; i++) {
				s_foo += "\t";
			}
			
			return s_foo;
		}
		
		/**
		 * Returns each field of yaml element restriction with name and value, separated by a pipe '|'
		 */
		@Override
		public String toString() {
			String s_foo = de.forestj.lib.io.File.NEWLINE + this.printTabs() + "\t" + "YAMLRestriction: ";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				if (o_field.getName().startsWith("this$")) {
					continue;
				}
				
				String s_value = "";
				
				try {
					s_value = o_field.get(this).toString();
				} catch (Exception o_exc) {
					s_value = "ERR:AccessViolation";
				}
				
				s_foo += o_field.getName() + " = " + s_value + "|";
			}
			
			return s_foo;
		}
	}
}
