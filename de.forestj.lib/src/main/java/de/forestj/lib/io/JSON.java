package de.forestj.lib.io;

/**
 * 
 * JSON class to encode and decode java objects to json files with help of a json schema file/data.
 * access to object fields can be directly on public fields or with public property methods (getXX setXX) on private fields.
 * NOTE: mostly only primitive types supported for encoding and decoding, only supporting ISO-8601 UTC timestamps within json files.
 *
 */
public class JSON {

	/* Fields */
	
	private JSONElement o_root;
	private JSONElement o_currentElement;
	private JSONElement o_schema;
	private String s_lineBreak;
	private boolean b_usePropertyMethods;
	private JSONElement a_definitions;
	private JSONElement a_properties;
	private int i_level;
	
	private String s_id;
	private String s_schemaValue;
	
	/* Properties */
	
	public JSONElement getRoot() {
		return this.o_root;
	}
	
	public void setRoot(JSONElement p_o_value) {
		this.o_root = p_o_value;
	}
	
	public String getLineBreak() {
		return this.s_lineBreak;
	}
	
	/**
	 * Determine line break characters for reading and writing json files
	 */
	public void setLineBreak(String p_s_lineBreak) {
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
		
	/* Methods */
	
	/**
	 * Empty JSON constructor
	 */
	public JSON() {
		this.setLineBreak(de.forestj.lib.io.File.NEWLINE);
		this.setUsePropertyMethods(false);
		this.i_level = 0;
		
		this.setRoot(new JSONElement("Root"));
		this.o_schema = this.o_root;
	}
	
	/**
	 * JSON constructor, giving file lines of schema as dynamic list for encoding and decoding json data
	 * 
	 * @param p_a_jsonSchemaLines					file lines of schema as dynamic list
	 * @throws IllegalArgumentException				value/structure within json schema invalid
	 * @throws NullPointerException					json schema, root node is null
	 */
	public JSON(java.util.List<String> p_a_jsonSchemaLines) throws IllegalArgumentException, NullPointerException {
		this.setLineBreak(de.forestj.lib.io.File.NEWLINE);
		this.setUsePropertyMethods(false);
		this.i_level = 0;
		
		StringBuilder o_stringBuilder = new StringBuilder();
		
		/* read all json schema file lines to one string builder */
		for (String s_line : p_a_jsonSchemaLines) {
			o_stringBuilder.append(s_line);
		}
		
		/* read all json-schema file lines and delete all line-wraps and tabs */
		String s_json = o_stringBuilder.toString().replaceAll("[\\r\\n\\t]", "");
		
		/* remove all white spaces, but not between double quotes */
		s_json = this.removeWhiteSpaces(s_json);
		
		/* check if json-schema starts with curly brackets */
	    if ( (!s_json.startsWith("{")) || (!s_json.endsWith("}")) ) {
    		throw new IllegalArgumentException("JSON-schema must start with curly bracket '{' and end with curly bracket '}'.");
    	}
	    
	    /* validate json schema */
	    this.validateJSON(s_json);
	    
	    										de.forestj.lib.Global.ilogConfig("json schema file lines validated");
	    
	    /* parse json */
	    this.parseJSON(s_json);
	    
	    										de.forestj.lib.Global.ilogConfig("json schema parsed");
	    
	    /* set schema element with constructor input, root is unparsed schema */
	    this.setSchema(true);
	}
	
	/**
	 * JSON constructor, giving a schema json element object as schema for encoding and decoding json data
	 * 
	 * @param p_o_schemaRoot						json element object as root schema node
	 * @throws IllegalArgumentException				invalid parameters for constructor
	 */
	public JSON(JSONElement p_o_schemaRoot) throws IllegalArgumentException {
		if (p_o_schemaRoot == null) {
			throw new IllegalArgumentException("json element parameter for schema is null");
		}
		
		this.setLineBreak(de.forestj.lib.io.File.NEWLINE);
		this.setUsePropertyMethods(false);
		this.i_level = 0;
		this.setRoot(p_o_schemaRoot);

		/* set schema element with constructor input, root is already parsed schema */
	    this.setSchema(false);
	}
	
	/**
	 * JSON constructor, giving a schema file as orientation for encoding and decoding json data
	 * 
	 * @param p_s_file								full-path to json schema file
	 * @throws IllegalArgumentException				value/structure within json schema file invalid
	 * @throws NullPointerException					json schema, root node is null
	 * @throws java.io.IOException					cannot access or open json file and it's content
	 */
	public JSON(String p_s_file) throws IllegalArgumentException, NullPointerException, java.io.IOException {
		this.setLineBreak(de.forestj.lib.io.File.NEWLINE);
		this.setUsePropertyMethods(false);
		this.i_level = 0;
		
		/* check if file exists */
		if (!File.exists(p_s_file)) {
			throw new IllegalArgumentException("File[" + p_s_file + "] does not exist.");
		}
		
		/* open json-schema file */
		File o_file = new File(p_s_file, false);
		
		/* read all json-schema file lines and delete all line-wraps and tabs */
		String s_json = o_file.getFileContent().replaceAll("[\\r\\n\\t]", "");
		
		/* remove all white spaces, but not between double quotes */
		s_json = this.removeWhiteSpaces(s_json);
		
		/* check if json-schema starts with curly brackets */
	    if ( (!s_json.startsWith("{")) || (!s_json.endsWith("}")) ) {
    		throw new IllegalArgumentException("JSON-schema must start with curly bracket '{' and end with curly bracket '}'.");
    	}
	    
	    /* validate json schema */
	    this.validateJSON(s_json);
	    
	    										de.forestj.lib.Global.ilogConfig("json schema file validated");
	    
	    /* parse json */
	    this.parseJSON(s_json);
	    
	    										de.forestj.lib.Global.ilogConfig("json schema parsed");
	    
	    /* set schema element with constructor input, root is unparsed schema */
	    this.setSchema(true);
	}
	
	/**
	 * Method to set schema elements, afterwards each json constructor has read their input
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
	    											if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("Unparsed JSON-Schema: " + this.s_lineBreak + this.o_root);
		
	    	/* reset level */
		    this.i_level = 0;
		    
		    /* parse json schema */
		    this.parseJSONSchema(this.o_root);
		    
		    										de.forestj.lib.Global.ilogConfig("json schema elements parsed");
		    
		    /* reset children of root */
		    this.o_root.getChildren().clear();
		    
		    if (this.a_properties != null) {
		    	/* properties cannot have one child and 'Reference' */
		    	if ( (this.a_properties.getChildren().size() > 0) && (this.a_properties.getReference() != null) ) {
		    		throw new IllegalArgumentException("Properties after parsing json schema cannot have one child and 'Reference'");
		    	}
		    	
		    	/* check if properties has one child */
		    	if (this.a_properties.getChildren().size() > 0) {
	    			/* add all 'properties' children to root */
	    			for (JSONElement o_jsonElement : this.a_properties.getChildren()) {
	    				this.o_root.getChildren().add(o_jsonElement);
	    			}
		    	} else if (this.a_properties.getReference() != null) { /* we have 'Reference' in properties */
		    		/* set properties 'Reference' as root 'Reference' */
		    		this.o_root.setReference(this.a_properties.getReference());
		    	}
		    }
	    }
	    
	    										if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("Parsed JSON-Schema: " + this.s_lineBreak + this.o_root);
	    
	    this.o_schema = this.o_root;
	    
	   											de.forestj.lib.Global.ilogConfig("set json root element as schema element");
	}
	
	/**
	 * Remove all white spaces from json content, but not within values escaped by double quotes
	 * 
	 * @param p_s_json		json content as string
	 * @return				modified json content as string value
	 */
	private String removeWhiteSpaces(String p_s_json) {
		String s_json = "";
		
		/* state variable if we are currently in a double quoted value or not */
		boolean b_doubleQuoteActive = false;
		
		/* iterate json for each character */
		for (int i = 0; i < p_s_json.length(); i++) {
			/* if we found a not unescaped double quote */
			if ( (i != 0) && (p_s_json.charAt(i) == '"') && (p_s_json.charAt(i - 1) != '\\') ) {
				/* if we are at the end of a double quoted value */
				if (b_doubleQuoteActive) {
					/* unset state */
					b_doubleQuoteActive = false;
				} else {
					/* we have a new double quoted value incoming, set state */
					b_doubleQuoteActive = true;
				}
			}
			
			if ( (p_s_json.charAt(i) == ' ') && (!b_doubleQuoteActive) ) {
				continue;
			}
			
			s_json += p_s_json.charAt(i);
		}
		
		return s_json;
	}
	
	/**
	 * Returns root element of json schema as string output and all of its children
	 */
	@Override
	public String toString() {
		String s_foo = "";
		
		s_foo += "$id = " + this.s_id;
		s_foo += "\n";
		s_foo += "$schema = " + this.s_schemaValue;
		s_foo += "\n";
		s_foo += this.o_root;
				
		return s_foo;
	}
	
	/**
	 * Generate indentation string for json specification
	 * 
	 * @return		indentation string
	 */
	private String printIndentation() {
		String s_foo = "";
		
		for (int i = 0; i < this.i_level; i++) {
			s_foo += "\t";
		}
		
		return s_foo;
	}
	
	/* parsing JSON schema */
	
	/**
	 * Validate json file if values and structure are correct, otherwise throw IllegalArgumentException
	 * 
	 * @param p_s_json							json content as string
	 * @throws IllegalArgumentException			value or structure within json file lines invalid
	 */
	public void validateJSON(String p_s_json) throws IllegalArgumentException {
		/* remove all white spaces, even within double quoted values for validation */
		p_s_json = p_s_json.replaceAll("\\s", "");
		/* state variable if we are currently in a double quoted value or not */
		boolean b_doubleQuoteActive = false;
		/* array of characters which are expected after double quoted value in json */
		java.util.List<Character> a_allowedCharactersAfterDoubleQuote = java.util.Arrays.asList(':', ',', '}', ']');
		
		/* iterate json for each character */
		for (int i = 0; i < p_s_json.length(); i++) {
			/* if we found a not unescaped double quote */
			if ( (p_s_json.charAt(i) == '"') && (p_s_json.charAt(i - 1) != '\\') ) {
				/* if we are at the end of a double quoted value */
				if (b_doubleQuoteActive) {
					/* unset state */
					b_doubleQuoteActive = false;
					
					/* if we are not at the last character of the json document, we expect an allowed character after double quoted value ends */
					if ( (i != p_s_json.length() - 1) && (!a_allowedCharactersAfterDoubleQuote.contains(p_s_json.charAt(i + 1))) ) {
						/* sequence variable for exception message */
						String s_sequence = "";
						
						if (i <= p_s_json.length() - 4) {
							if (i == 0) {
								/* sequence if we are at the beginning of the json document */
								s_sequence = "" + p_s_json.charAt(i) + p_s_json.charAt(i + 1) + p_s_json.charAt(i + 2) + p_s_json.charAt(i + 3) + p_s_json.charAt(i + 4);
							} else {
								/* standard sequence */
								s_sequence = "" + p_s_json.charAt(i - 1) + p_s_json.charAt(i) + p_s_json.charAt(i + 1) + p_s_json.charAt(i + 2) + p_s_json.charAt(i + 3);
							}
						} else {
							/* add previous and current characters to the sequence */
							s_sequence = "" + p_s_json.charAt(i - 1) + p_s_json.charAt(i);
							
							/* iterate remaining characters and add them to the sequence */
							for (int j = i + 1; j < p_s_json.length(); j++) {
								s_sequence += p_s_json.charAt(j);
							}
						}
						
						throw new IllegalArgumentException("Expected ':', ',', '}' or ']' character, but found '" + p_s_json.charAt(i + 1) + "' in sequence '" + s_sequence + "', please check for unescaped double quotes");
					} else if (i != p_s_json.length() - 2) { /* this check requires that we are not at the last and second last character of the json document */
						/* sequence variable for exception message */
						String s_sequence = "";
						
						if (i <= p_s_json.length() - 5) {
							/* standard sequence */
							s_sequence = "" + p_s_json.charAt(i) + p_s_json.charAt(i + 1) + p_s_json.charAt(i + 2) + p_s_json.charAt(i + 3) + p_s_json.charAt(i + 4);
						} else {
							/* add current character to the sequence */
							s_sequence = "" + p_s_json.charAt(i);
							
							/* iterate remaining characters and add them to the sequence */
							for (int j = i + 1; j < p_s_json.length(); j++) {
								s_sequence += p_s_json.charAt(j);
							}
						}
						
						/* if the next character after the end of a double quoted value is ':' character */
						if (p_s_json.charAt(i + 1) == ':') {
							if (
								/* allowed characters/digits after the end of a double quoted value and the ':' character */
								(p_s_json.charAt(i + 2) != '"') && 
								(p_s_json.charAt(i + 2) != '+') && 
								(p_s_json.charAt(i + 2) != '-') && 
								(p_s_json.charAt(i + 2) != 't') && 
								(p_s_json.charAt(i + 2) != 'f') && 
								(p_s_json.charAt(i + 2) != 'n') &&
								(p_s_json.charAt(i + 2) != '{') && 
								(p_s_json.charAt(i + 2) != '[') &&
								(!Character.isDigit(p_s_json.charAt(i + 2)))
							) {
								throw new IllegalArgumentException("Expected '\"', '+', '-', 't', 'f', 'n', '{', '[' or a digit character, but found '" + p_s_json.charAt(i + 2) + "' in sequence '" + s_sequence + "', after ':'");
							}
						} else if (p_s_json.charAt(i + 1) == ',') { /* if the next character after the end of a double quoted value is ',' character */
							/* '"' character only allowed after the end of a double quoted value and the ',' character */
							if (p_s_json.charAt(i + 2) != '"') {
								throw new IllegalArgumentException("Expected '\"' character, but found '" + p_s_json.charAt(i + 2) + "' in sequence '" + s_sequence + "', after ','");
							}
						} else if ( (p_s_json.charAt(i + 1) == '}') || (p_s_json.charAt(i + 1) == ']') ) { /* if the next character after the end of a double quoted value is '}' or ']' character */
							/* '}' or ',' character only allowed after the end of a double quoted value and the '}' or ']' character */
							if ( (p_s_json.charAt(i + 2) != '}') && (p_s_json.charAt(i + 2) != ',') && (p_s_json.charAt(i + 2) != ']') ) {
								throw new IllegalArgumentException("Expected '}', ']' or ',' character, but found '" + p_s_json.charAt(i + 2) + "' in sequence '" + s_sequence + "', after '}' or ']'");
							}
						}
					}
				} else {
					/* we have a new double quoted value incoming, set state */
					b_doubleQuoteActive = true;
				}
			} else {
				/* if we are not at the last character of the json document and not in a double quoted value */
				if ( (i != p_s_json.length() - 1) && (!b_doubleQuoteActive) ) {
					/* sequence variable for exception message */
					String s_sequence = "";
					
					/* if we are at the end of the json document, we cannot add all sequence characters to exception message */
					if (i <= p_s_json.length() - 3) {
						if (i > 2) {
							/* standard sequence */
							s_sequence = "" + p_s_json.charAt(i - 2) + p_s_json.charAt(i - 1) + p_s_json.charAt(i) + p_s_json.charAt(i + 1) + p_s_json.charAt(i + 2);
						} else {
							/* get first characters at the beginning of the json document */
							for (int j = 0; j < i; j++) {
								s_sequence += p_s_json.charAt(j);
							}
							
							/* add rest of characters to the sequence */
							s_sequence += p_s_json.charAt(i) + p_s_json.charAt(i + 1) + p_s_json.charAt(i + 2);
						}
					} else {
						/* add previous and current characters to the sequence */
						s_sequence = "" + p_s_json.charAt(i - 2) + p_s_json.charAt(i - 1) + p_s_json.charAt(i);
						
						/* iterate remaining characters and add them to the sequence */
						for (int j = i + 1; j < p_s_json.length(); j++) {
							s_sequence += p_s_json.charAt(j);
						}
					}
					
					if ( (p_s_json.charAt(i) == ',') && (p_s_json.charAt(i + 1) == '}') ) {
						/* character sequence ,} is not allowed */
						throw new IllegalArgumentException("Sequence of ',}' is not allowed outside of double quoted values in sequence '" + s_sequence + "'");
					} else if ( (p_s_json.charAt(i) == '}') && (p_s_json.charAt(i + 1) == '"') ) {
						/* character sequence }" is not allowed */
						throw new IllegalArgumentException("Sequence of '}\"' is not allowed outside of double quoted values in sequence '" + s_sequence + "'");
					} else if ( (i != p_s_json.length() - 2) && (p_s_json.charAt(i) == ',') && (p_s_json.charAt(i + 1) == '"') && (p_s_json.charAt(i + 2) == '"') ) {
						/* character sequence ,"" is not allowed */
						throw new IllegalArgumentException("Sequence of ',\"\"' is not allowed outside of double quoted values in sequence '" + s_sequence + "'");
					} 
				}
			}
		}
	}
	
	/**
	 * Analyze json element value and get a unique value type from it
	 * 
	 * @param p_s_jsonValue					json element value as string
	 * @return								unique json value type
	 * @throws IllegalArgumentException		invalid value or json value type could not be determined
	 */
	private JSONValueType getJSONValueType(String p_s_jsonValue) throws IllegalArgumentException {
		JSONValueType e_jsonValueType = null;
		
		/* get json value type */
		if (p_s_jsonValue.charAt(0) == '"') { /* json value starts with '"' character, so it is of type string */
			e_jsonValueType = JSONValueType.String;
		} else if ( (p_s_jsonValue.charAt(0) == '+') || (p_s_jsonValue.charAt(0) == '-') || (Character.isDigit(p_s_jsonValue.charAt(0))) ) { /* json value starts with digit, '+' or '-' character, so it is of type number or integer */
			boolean b_decimalDot = false;
			int i_amountPlusSign = 0;
			int i_amountMinusSign = 0;
			
			/* iterate value in detail */
			for (int i = 0; i < p_s_jsonValue.length(); i++) {
				/* check if we found a decimal point */
				if (p_s_jsonValue.charAt(i) == '.') {
					if (b_decimalDot) {
						/* if we already found a decimal point - number format is invalid */
						throw new IllegalArgumentException("Invalid number format, found second decimal point in value [" + p_s_jsonValue + "]");
					} else if ( ( (i == 1) && (!Character.isDigit(p_s_jsonValue.charAt(0))) ) || (i == p_s_jsonValue.length() - 1) ) {
						/* if decimal point has not a single previous digit or is at the end of the value - number format is invalid */
						throw new IllegalArgumentException("Invalid number format, decimal point at wrong position in value [" + p_s_jsonValue + "]");
					} else {
						/* set flag, that decimal point has been found */
						b_decimalDot = true;
					}
				} else if (p_s_jsonValue.charAt(i) == '+') {
					i_amountPlusSign++;
					
					if (i_amountPlusSign > 1) { /* exit number check, it seems to be a normal string starting with '++...' */
						break;
					}
				} else if (p_s_jsonValue.charAt(i) == '-') {
					i_amountMinusSign++;
					
					if (i_amountMinusSign > 1) { /* exit number check, it seems to be a normal string starting with '--...' */
						break;
					}
				} else if ( (i != 0) && (!Character.isDigit(p_s_jsonValue.charAt(i))) ) { /* check if we found a character which is not a digit */
					/* we do not need an exception, just setting both sign counters to 1 and exit number check */
					i_amountPlusSign = 1;
					i_amountMinusSign = 1;
					break;
				}
			}
			
			/* only accept one plus-sign or one minus-sign */
			if ( ( (i_amountPlusSign == 0) && (i_amountMinusSign == 0) ) || (i_amountPlusSign == 1) ^ (i_amountMinusSign == 1) ) {
				if (b_decimalDot) {
					/* decimal point found, value is of type number */
					e_jsonValueType = JSONValueType.Number;
				} else {
					/* value is of type integer */
					e_jsonValueType = JSONValueType.Integer;
				}
			} else {
				throw new IllegalArgumentException("Invalid integer or number, amount plus signs[" + i_amountPlusSign + "] and amount minus signs[" + i_amountMinusSign + "] found in value [" + p_s_jsonValue + "]");
			}
		} else if (p_s_jsonValue.contentEquals("true") || p_s_jsonValue.contentEquals("false")) { /* value equals 'true' or 'false', so it is of type boolean */
			e_jsonValueType = JSONValueType.Boolean;
		} else if (p_s_jsonValue.contentEquals("null")) { /* value equals 'null', so it is of type null */
			e_jsonValueType = JSONValueType.Null;
		} else if (p_s_jsonValue.charAt(0) == '[') { /* json value starts with '[' character, so it is of type array */
			e_jsonValueType = JSONValueType.Array;
		} else if (p_s_jsonValue.charAt(0) == '{') { /* json value starts with '{' character, so it is of type object */
			e_jsonValueType = JSONValueType.Object;
		}
		
		/* check if we have a valid json value type */
    	if (e_jsonValueType == null) {
    		throw new NullPointerException("Invalid JSON value type with value [" + p_s_jsonValue + "]");
    	}
		
		return e_jsonValueType;
	}
	
	/**
	 * Parse json content to json object structure, based on JSONElement and JSONRestriction
	 * 
	 * @param p_s_json							json content as string
	 * @throws IllegalArgumentException			value or structure within json file lines invalid
	 * @throws NullPointerException				value within json content missing or min. amount not available
	 */
	private void parseJSON(String p_s_json) throws IllegalArgumentException, NullPointerException {
		/* create new root if is null */
		if (this.o_root == null) {
			this.o_root = new JSONElement("Root");
		}
		
		/* start position for parsing json schema */
		int i = 0;
		
		/* check if json starts with '{' or '[' character */
		if ( (p_s_json.charAt(i) != '{') && (p_s_json.charAt(i) != '[') ) {
			throw new IllegalArgumentException("Expected '{' or '[' character, but found '" + p_s_json.charAt(i) + "' in value [" + p_s_json.substring(0, 10) + "...]");
		}
		
		/* increment position */
		i++;
		
		/* increase position as long as we do not find closing '}' character */
		while (p_s_json.charAt(i) != '}') {
			/* variable for current json line in json schema document, mostly separated by ',' character */
			String s_jsonLine = "";
			/* variable for interlacing objects/arrays within current part of json schema */
			int i_level = 0;
			/* state variable if we are currently in a double quoted value or not */
			boolean b_doubleQuoteActive = false;
			
			/* increase position as long as we do not find ',' character, in a double quoted value or interlacing/array */ 
			while ( (p_s_json.charAt(i) != ',') || (b_doubleQuoteActive) || (i_level != 0) ) {
				/* if we found a not unescaped double quote */
				if ( (p_s_json.charAt(i) == '"') && (p_s_json.charAt(i - 1) != '\\') ) {
					/* if we are at the end of a double quoted value */
					if (b_doubleQuoteActive) {
						/* unset state */
						b_doubleQuoteActive = false;
					} else {
						/* set state */
						b_doubleQuoteActive = true;
					}
				}
				
				/* if we are not in a double quoted value */
				if (!b_doubleQuoteActive) {
					/* detect a new interlacing or array */
					if ( (p_s_json.charAt(i) == '{') || (p_s_json.charAt(i) == '[') ) {
						/* increase level value */
						i_level++;
					}
					
					/* detect close of an interlacing or array */
					if ( (p_s_json.charAt(i) == '}') || (p_s_json.charAt(i) == ']') ) {
						/* if current level is zero and interlacing is closing, we end 2nd level while loop */
						if ( (i_level == 0) && ((p_s_json.charAt(i) == '}') || (p_s_json.charAt(i) == ']')) ) {
							break;
						} else {
							/* decrease level */
							i_level--;
						}
					}
				}
				
				/* add character to current json line */
				s_jsonLine += p_s_json.charAt(i);
				
				/* increment position */
				i++;
			}
			
													de.forestj.lib.Global.ilogFiner(printIndentation() + s_jsonLine);
			
			/* set current element with root if it is empty */
			if (this.o_currentElement == null) {
				this.o_currentElement = this.o_root;
			}
			
			/* we have an array object, so we directly start another recursion */
			if ( (s_jsonLine.startsWith("{")) && (s_jsonLine.endsWith("}")) ) {
														de.forestj.lib.Global.ilogFiner(printIndentation() + "new ArrayObject()");
														
				/* save current element in temporary variable */
				JSONElement o_oldCurrentElement = this.o_currentElement;
				
				/* with value type as object, we have a new current element */
				JSONElement o_jsonElement = new JSONElement("__ArrayObject__", this.i_level);
				
				/* add new element to current element children and set as new current element */
				this.o_currentElement.getChildren().add(o_jsonElement);
				this.o_currentElement = o_jsonElement;
				
				/* increase level for PrintIndentation */
				this.i_level++;
				
				/* parse json value recursively */
				this.parseJSON(s_jsonLine);
				
				/* decrease level for PrintIndentation */
				this.i_level--;
				
				/* reset current element with temporary variable */
				this.o_currentElement = o_oldCurrentElement;
			} else {
				/* the parsed json line must start with '"' or '{' character */
				if (!s_jsonLine.startsWith("\"")) {
					throw new IllegalArgumentException("Invalid format, line does not start with '\"'");
				}
				
				/* get property name of current json line */
				String s_jsonProperty = s_jsonLine.substring(0, s_jsonLine.indexOf(":"));
				
				/* property name must start and end with '"' character */
				if ( (!s_jsonProperty.startsWith("\"")) && (!s_jsonProperty.endsWith("\"")) ) {
					throw new IllegalArgumentException("Invalid format for JSON property '" + s_jsonProperty + "'");
				}
				
				/* delete surrounded '"' characters from property name */
				s_jsonProperty = s_jsonProperty.substring(1, s_jsonProperty.length() - 1);
				
				/* get value of current json line */
				String s_jsonValue = s_jsonLine.substring(s_jsonLine.indexOf(":") + 1);
				
				/* determine json value type */
				JSONValueType e_jsonValueType = this.getJSONValueType(s_jsonValue);
				
				/* flag for handling array as object */
				boolean b_handleArrayAsObject = false;
				
				/* if we have an array, we must determine if it is an array of objects */
				if (e_jsonValueType == JSONValueType.Array) {
					if (s_jsonValue.charAt(1) == '{') {
						b_handleArrayAsObject = true;
					}
				}
				
				/* if json value type is of type object, or array of objects */
				if ( (e_jsonValueType == JSONValueType.Object) || (b_handleArrayAsObject) ) {
															de.forestj.lib.Global.ilogFiner(printIndentation() + s_jsonProperty + " = (" + e_jsonValueType + ")");
					/* save current element in temporary variable */
					JSONElement o_oldCurrentElement = this.o_currentElement;
					
					/* with value type as object, we have a new current element */
					JSONElement o_jsonElement = new JSONElement(s_jsonProperty, this.i_level);
					
					/* add new element to current element children and set as new current element */
					this.o_currentElement.getChildren().add(o_jsonElement);
					this.o_currentElement = o_jsonElement;
					
					/* increase level for PrintIndentation */
					this.i_level++;
					
					/* parse json value recursively */
					this.parseJSON(s_jsonValue);
					
					/* decrease level for PrintIndentation */
					this.i_level--;
					
					/* reset current element with temporary variable */
					this.o_currentElement = o_oldCurrentElement;
				} else {
															de.forestj.lib.Global.ilogFiner(printIndentation() + s_jsonProperty + " = " + s_jsonValue + " (" + e_jsonValueType + ")");
					/* add json property as a new child to current element */
					JSONElement o_jsonElement = new JSONElement(s_jsonProperty, this.i_level);
					o_jsonElement.setValue(s_jsonValue);
					this.o_currentElement.getChildren().add(o_jsonElement);
				}
			}
			
			/* if we detect close of json object with '}' or ']' character */
			if ( (p_s_json.charAt(i) == '}') || (p_s_json.charAt(i) == ']') ) {
				/* end 1st level while loop */
				break;
			} else {
				/* increment position */
				i++;
			}
		}
	}
	
	/**
	 * Parse json schema elements with all children elements
	 * 
	 * @param p_o_jsonSchemaElement				json schema element object which will be parsed
	 * @throws IllegalArgumentException			value or type within json schema invalid
	 * @throws NullPointerException				value within json schema missing or min. amount not available
	 */
	private void parseJSONSchema(JSONElement p_o_jsonElement) throws IllegalArgumentException, NullPointerException {
		if (p_o_jsonElement.getChildren().size() > 0) {
			boolean b_array = false;
			boolean b_object = false;
			boolean b_properties = false;
			boolean b_items = false;
			
			/* 
			 * check if we have "type": "array" and "items" and no "properties"
			 * or if we have "type": "object" and "properties" and no "items"
			 */
			for (JSONElement o_jsonChild : p_o_jsonElement.getChildren()) {
				if (o_jsonChild.getName().toLowerCase().contentEquals("type")) {
					String s_type = o_jsonChild.getValue();
					
					/* remove surrounded double quotes from value */
					if ( (s_type.startsWith("\"")) && (s_type.endsWith("\"")) ) {
						s_type = s_type.substring(1, s_type.length() - 1);
					}
					
					if (s_type.contentEquals("array")) {
						b_array = true;
					} else if (s_type.contentEquals("object")) {
						b_object = true;
					}
				} else if (o_jsonChild.getName().toLowerCase().contentEquals("properties")) {
					b_properties = true;
				} else if (o_jsonChild.getName().toLowerCase().contentEquals("items")) {
					b_items = true;
				}
			}
			
			/* control result of check */
			if ( (!b_array) && (!b_object) ) {
				if ( (this.i_level == 0) && (!p_o_jsonElement.getName().toLowerCase().contentEquals("definitions")) && (!p_o_jsonElement.getName().toLowerCase().contentEquals("properties")) ) {
					throw new IllegalArgumentException("JSON definition of element[definitions] or [properties] necessary on first level for [" + p_o_jsonElement.getName() + "]");
				}
			} else if ( (b_array) && (b_properties) ) {
				throw new IllegalArgumentException("JSON definition with type[array] cannot have [properties] at the same time");
			} else if ( (b_array) && (!b_items) ) {
				throw new IllegalArgumentException("JSON definition with type[array] must have [items] definition as well");
			} else if ( (b_object) && (b_items) ) {
				throw new IllegalArgumentException("JSON definition with type[object] cannot have [items] at the same time");
			} else if ( (b_object) && (!b_properties) ) {
				throw new IllegalArgumentException("JSON definition with type[object] must have [properties] definition as well");
			}
			
			for (JSONElement o_jsonChild : p_o_jsonElement.getChildren()) {
				JSONValueType e_jsonValueType;
				
				/* determine json value type */
				if (de.forestj.lib.Helper.isStringEmpty(o_jsonChild.getValue())) {
					e_jsonValueType = JSONValueType.Object;
				} else {
					e_jsonValueType = this.getJSONValueType(o_jsonChild.getValue());
					
					/* remove surrounded double quotes from value */
					if ( (o_jsonChild.getValue().startsWith("\"")) && (o_jsonChild.getValue().endsWith("\"")) ) {
						o_jsonChild.setValue(o_jsonChild.getValue().substring(1, o_jsonChild.getValue().length() - 1));
					}
				}
				
														de.forestj.lib.Global.ilogFiner(printIndentation() + o_jsonChild.getName() + "(" + e_jsonValueType + ") of " + p_o_jsonElement.getName());
				
				/* special properties at level 0 of json schema only */
				if (o_jsonChild.getLevel() == 0) {
					if (o_jsonChild.getName().toLowerCase().contentEquals("$id")) {
						if (e_jsonValueType == JSONValueType.String) {
							this.s_id = o_jsonChild.getValue();
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("$schema")) {
						if (e_jsonValueType == JSONValueType.String) {
							this.s_schemaValue = o_jsonChild.getValue();
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if ( (o_jsonChild.getName().toLowerCase().contentEquals("definitions")) || (o_jsonChild.getName().toLowerCase().contentEquals("properties")) ) {
						/* if json value type is of type object */
						if (e_jsonValueType == JSONValueType.Object) {
																	de.forestj.lib.Global.ilogFiner(printIndentation() + "save current element(" + this.o_currentElement.getName() + ") in temporary variable");
							
							/* save current element in temporary variable */
							JSONElement o_oldCurrentElement = this.o_currentElement;

																	de.forestj.lib.Global.ilogFiner(printIndentation() + "start recursion for: " + o_jsonChild.getName() + " = (" + e_jsonValueType + ") for " + this.o_currentElement.getName());
							
							if (o_jsonChild.getName().toLowerCase().contentEquals("definitions")) {
								/* create new json element for 'definitions' */
								this.o_currentElement = new JSONElement(o_jsonChild.getName(), 0);
								
								/* parse json value recursively */
								this.parseJSONSchema(o_jsonChild);
								
								/* process current element as return value */
								this.a_definitions = this.o_currentElement;
							} else if (o_jsonChild.getName().toLowerCase().contentEquals("properties")) {
								/* create new json element for 'properties' */
								this.o_currentElement = new JSONElement(o_jsonChild.getName(), 0);
								
								/* parse json child recursively */
								this.parseJSONSchema(o_jsonChild);
								
								/* process current element as return value */
								this.a_properties = this.o_currentElement;
							}
							
																	de.forestj.lib.Global.ilogFiner(printIndentation() + "end recursion for " + this.o_currentElement.getName() + ": " + o_jsonChild.getName() + " = (" + e_jsonValueType + ")");
							
																	de.forestj.lib.Global.ilogFiner(printIndentation() + "reset current element(" + this.o_currentElement.getName() + ") from temporary variable(" + o_oldCurrentElement.getName() + ")");
							
							/* reset current element from temporary variable */
							this.o_currentElement = o_oldCurrentElement;
							
							/* we can skip the rest of loop processing */
							continue;
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] of type object");
						}
					}
				}
				
				/* default parsing of properties for json schema */
				
				/* if current element is another object we need another recursion */
				if (e_jsonValueType == JSONValueType.Object) {
					boolean b_skip = false;
					
					/* check if new object has property name 'items' */
					if (o_jsonChild.getName().toLowerCase().contentEquals("items")) {
						if (!this.o_currentElement.getType().toLowerCase().contentEquals("array")) {
							throw new IllegalArgumentException("JSON object[" + this.o_currentElement.getName() + "] with property[items] must be of type 'array' != '" + this.o_currentElement.getType() + "'");
						}
						
						if (o_jsonChild.getChildren().size() == 1) {
							JSONElement o_itemChild = o_jsonChild.getChildren().get(0);
							
							if (!de.forestj.lib.Helper.isStringEmpty(o_itemChild.getValue())) {
								/* remove surrounded double quotes from value */
								String s_itemValue = o_itemChild.getValue().substring(1, o_itemChild.getValue().length() - 1);
								
								if ( (o_itemChild.getName().toLowerCase().contentEquals("$ref")) && (s_itemValue.startsWith("#")) ) {
									String s_referenceName = s_itemValue.replace("#/definitions/", "");
									
									for (JSONElement o_jsonDefinition : this.a_definitions.getChildren()) {
										if (o_jsonDefinition.getName().contentEquals(s_referenceName)) {
																					de.forestj.lib.Global.ilogFiner(this.printIndentation() + "setReference for (" + this.o_currentElement.getName() + ") with reference=" + s_referenceName);
											
											this.o_currentElement.setReference(o_jsonDefinition);
											b_skip = true;
										}
									}
									
									if (!b_skip) {
										throw new IllegalArgumentException("JSON schema definition[" + s_referenceName + "] not found under #/definitions/");
									}
								}
							}
						}
					}
					
					/* skip because of 'items' object with one reference */
					if (!b_skip) { /* new object */
																de.forestj.lib.Global.ilogFiner(printIndentation() + o_jsonChild.getName() + " = (" + e_jsonValueType + ")");
						
						if (o_jsonChild.getName().toLowerCase().contentEquals("properties")) {
																	de.forestj.lib.Global.ilogFiner(printIndentation() + "start recursion for: " + o_jsonChild.getName() + " = (" + e_jsonValueType + ") for " + this.o_currentElement.getName());
							
							/* parse json child recursively */
							this.parseJSONSchema(o_jsonChild);
							
																	de.forestj.lib.Global.ilogFiner(printIndentation() + "end recursion for: " + o_jsonChild.getName() + " = (" + e_jsonValueType + ")");
						} else {
																	de.forestj.lib.Global.ilogFiner(printIndentation() + "save current element(" + this.o_currentElement.getName() + ") in temporary variable");
							
							/* save current element in temporary variable */
							JSONElement o_oldCurrentElement = this.o_currentElement;
							
							if (!java.util.regex.Pattern.matches("[a-zA-Z0-9-_]*", o_jsonChild.getName())) {
								throw new IllegalArgumentException("Invalid schema element name '" + o_jsonChild.getName() + "', invalid characters. Following characters are allowed: [a-z], [A-Z], [0-9], [-] and [_]");
							}
							
																	de.forestj.lib.Global.ilogFiner(printIndentation() + "start recursion for: " + o_jsonChild.getName() + " = (" + e_jsonValueType + ") for " + this.o_currentElement.getName());
							
							/* create new json element */
							JSONElement o_newJSONElement = new JSONElement(o_jsonChild.getName(), this.i_level);
							
							/* if we have Root node as current element on level 0 with type 'array' and new child 'items', we must not add a new child, because of concurrent modification of the for loop */
							boolean b_handleRootItems = false;
							
							if ( (this.o_currentElement.getName().contentEquals("Root")) && (this.o_currentElement.getLevel() == 0) && (this.o_currentElement.getType().toLowerCase().contentEquals("array")) && (o_newJSONElement.getName().toLowerCase().contentEquals("items")) ) {
								b_handleRootItems = true;
							} else {
								/* add new json element to current elements children */
								this.o_currentElement.getChildren().add(o_newJSONElement);
							}
							
							/* set new json element as current element for recursive processing */
							this.o_currentElement = o_newJSONElement;
							
							/* increase level for PrintIndentation */
							this.i_level++;
							
							/* parse json child recursively */
							this.parseJSONSchema(o_jsonChild);
							
																	de.forestj.lib.Global.ilogFiner(printIndentation() + "reset current element(" + this.o_currentElement.getName() + ") from temporary variable(" + o_oldCurrentElement.getName() + ")");
								
							/* reset current element from temporary variable */
							this.o_currentElement = o_oldCurrentElement;
							
							/* check if we must handle root items */
							if (b_handleRootItems) {
								/* new element 'items', we set the child as reference of Root */
								if (o_newJSONElement.getChildren().size() == 1) {
																			de.forestj.lib.Global.ilogFiner(this.printIndentation() + "setReference for (" + this.o_currentElement.getName() + ") with object(" + o_newJSONElement.getChildren().get(0).getName() + ")");
									
									this.o_currentElement.setReference(o_newJSONElement.getChildren().get(0));
								} else {
									throw new IllegalArgumentException("Root items must have only one child, but there are (" + o_newJSONElement.getChildren().size() + ") children");
								}
							}
							
							/* decrease level for PrintIndentation */
							this.i_level--;
							
							/* between update of schema definitions, for the case that a definition is depending on another definition before */
							if (this.o_currentElement.getName().toLowerCase().contentEquals("definitions")) {
								this.a_definitions = this.o_currentElement;
							}
							
																	de.forestj.lib.Global.ilogFiner(printIndentation() + "end recursion for: " + o_jsonChild.getName() + " = (" + e_jsonValueType + ")");
						}
					}
				} else {
															de.forestj.lib.Global.ilogFiner(printIndentation() + o_jsonChild.getName() + " = " + o_jsonChild.getValue() + " (" + e_jsonValueType + ") for " + this.o_currentElement.getName());
					
					if (o_jsonChild.getName().toLowerCase().contentEquals("$ref")) {
						if (e_jsonValueType == JSONValueType.String) {
							boolean b_found = false;
							String s_referenceName = o_jsonChild.getValue().replace("#/definitions/", "");
							
							for (JSONElement o_jsonDefinition : this.a_definitions.getChildren()) {
								if (o_jsonDefinition.getName().contentEquals(s_referenceName)) {
									this.o_currentElement.setReference(o_jsonDefinition);
									b_found = true;
								}
							}
							
							if (!b_found) {
								throw new IllegalArgumentException("JSON definition[" + s_referenceName + "] not found under #/definitions/ in json schema");
							}
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("type")) {
						if (e_jsonValueType == JSONValueType.String) {
							java.util.List<String> a_validTypes = java.util.Arrays.asList("string", "number", "integer", "boolean", "array", "object", "null");
							
							/* store type value in temp. variable */
							String s_foo = o_jsonChild.getValue();
							
							/* check if type value ends with '[]' */
							if (s_foo.endsWith("[]")) {
								/* delete '[]' from type value */
								s_foo = s_foo.substring(0, s_foo.length() - 2);
								
								/* set primitive array flag */
								this.o_currentElement.setPrimitiveArray(true);
							}
							
							/* check if we have a valid type value */
							if (!a_validTypes.contains(s_foo)) {
								throw new IllegalArgumentException("Invalid value[" + o_jsonChild.getValue() + "] for property[type], allowed values are " + a_validTypes);
							}
							
							/* store type value */
							this.o_currentElement.setType(s_foo);
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("description")) {
						if (e_jsonValueType == JSONValueType.String) {
							this.o_currentElement.setDescription(o_jsonChild.getValue());
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("default")) {
						if (e_jsonValueType == JSONValueType.String) {
							this.o_currentElement.setDefault(o_jsonChild.getValue());
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("mapping")) {
						if (e_jsonValueType == JSONValueType.String) {
							if (o_jsonChild.getValue().contains(":")) { /* set mapping and mappingClass */
								this.o_currentElement.setMapping(o_jsonChild.getValue().substring(0, o_jsonChild.getValue().indexOf(":")));
								this.o_currentElement.setMappingClass(o_jsonChild.getValue().substring(o_jsonChild.getValue().indexOf(":") + 1, o_jsonChild.getValue().length()));
							} else { /* set only mappingClass */
								this.o_currentElement.setMappingClass(o_jsonChild.getValue());
							}
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("maxitems")) {
						if (e_jsonValueType == JSONValueType.Integer) {
							if (!this.o_currentElement.getType().toLowerCase().contentEquals("array")) {
								throw new IllegalArgumentException("Invalid JSON restriction[" + o_jsonChild.getName() + "] for [" + this.o_currentElement.getName() + "] with type[" + o_jsonChild.getType() + "], type must be array");
							}
							
							this.o_currentElement.getRestrictions().add( new JSONRestriction(o_jsonChild.getName(), this.i_level, "", Integer.parseInt(o_jsonChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("minitems")) {
						if (e_jsonValueType == JSONValueType.Integer) {
							if (!this.o_currentElement.getType().toLowerCase().contentEquals("array")) {
								throw new IllegalArgumentException(this.o_currentElement.getName()+" - Invalid JSON restriction[" + o_jsonChild.getName() + "] for [" + this.o_currentElement.getName() + "] with type[" + o_jsonChild.getType() + "], type must be array");
							}
							
							this.o_currentElement.getRestrictions().add( new JSONRestriction(o_jsonChild.getName(), this.i_level, "", Integer.parseInt(o_jsonChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("minimum")) {
						if (e_jsonValueType == JSONValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new JSONRestriction(o_jsonChild.getName(), this.i_level, "", Integer.parseInt(o_jsonChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("exclusiveminimum")) {
						if (e_jsonValueType == JSONValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new JSONRestriction(o_jsonChild.getName(), this.i_level, "", Integer.parseInt(o_jsonChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("maximum")) {
						if (e_jsonValueType == JSONValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new JSONRestriction(o_jsonChild.getName(), this.i_level, "", Integer.parseInt(o_jsonChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("exclusivemaximum")) {
						if (e_jsonValueType == JSONValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new JSONRestriction(o_jsonChild.getName(), this.i_level, "", Integer.parseInt(o_jsonChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("maxlength")) {
						if (e_jsonValueType == JSONValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new JSONRestriction(o_jsonChild.getName(), this.i_level, "", Integer.parseInt(o_jsonChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("minlength")) {
						if (e_jsonValueType == JSONValueType.Integer) {
							this.o_currentElement.getRestrictions().add( new JSONRestriction(o_jsonChild.getName(), this.i_level, "", Integer.parseInt(o_jsonChild.getValue())) );
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("pattern")) {
						if (e_jsonValueType == JSONValueType.String) {
							this.o_currentElement.getRestrictions().add( new JSONRestriction(o_jsonChild.getName(), this.i_level, o_jsonChild.getValue()) );
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					} else if (o_jsonChild.getName().toLowerCase().contentEquals("required")) {
						if (e_jsonValueType == JSONValueType.Array) {
							/* check if current element has any children */
							if (this.o_currentElement.getChildren().size() < 1) {
								throw new IllegalArgumentException("Current element[" + this.o_currentElement.getName() + "] must have at least one child for assigning 'required' property");
							}
							
							/* get json array */
							String s_array = o_jsonChild.getValue();
							
							/* check if array is surrounded with '[' and ']' characters */
							if ( (!s_array.startsWith("[")) || (!s_array.endsWith("]")) ) {
								throw new IllegalArgumentException("Invalid format for JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "], must start with '[' and end with ']'");
							}
							
							/* remove surrounding '[' and ']' characters */
							s_array = s_array.substring(1, s_array.length() - 1);
							
							/* split array in its values */
							String[] a_arrayValues = s_array.split(",");
							
							/* iterate each array value */
							for (String s_arrayValue : a_arrayValues) {
								/* check if array value is surrounded with '"' and '"' characters */
								if ( (!s_arrayValue.startsWith("\"")) || (!s_arrayValue.endsWith("\"")) ) {
									throw new IllegalArgumentException("Invalid format for array value[" + s_arrayValue + "] for property[" + o_jsonChild.getName() + "], must start with '\"' and end with '\"'");
								}
								
								/* remove surrounding '"' and '"' characters */
								s_arrayValue = s_arrayValue.substring(1, s_arrayValue.length() - 1);
								
								boolean b_requiredFound = false;
								java.util.List<JSONElement> a_children = null;
								
								/* check if we are at 'root' level */
								if ( (this.o_currentElement.getName().contentEquals("Root")) && (this.o_currentElement.getLevel() == 0) ) {
									/* look for 'properties' child */
									for (JSONElement o_jsonCurrentElementChild : this.o_currentElement.getChildren()) {
										if (o_jsonCurrentElementChild.getName().toLowerCase().contentEquals("properties")) {
											/* set 'properties' children as array to search for 'required' element */
											a_children = o_jsonCurrentElementChild.getChildren();
										}
									}
								} else {
									/* set children of current element as array to search for 'required' element */
									a_children = this.o_currentElement.getChildren();
								}
								
								if (a_children == null) {
									throw new NullPointerException("Cannot handle required property[" + s_arrayValue + "] in array[" + o_jsonChild.getName() + "] because current element[" + this.o_currentElement.getName() + "] has no children or no properties");
								}
								
								/* iterate all children of current element to find required 'property' */
								for (JSONElement o_jsonCurrentElementChild : a_children) {
									/* compare by property name */
									if (o_jsonCurrentElementChild.getName().toLowerCase().contentEquals(s_arrayValue.toLowerCase())) {
										b_requiredFound = true;
										o_jsonCurrentElementChild.setRequired(true);
										break;
									}
								}
								
								if (!b_requiredFound) {
									throw new IllegalArgumentException("Required property[" + s_arrayValue + "] in array[" + o_jsonChild.getName() + "] does not exist within 'properties'");
								}
							}
						} else {
							throw new IllegalArgumentException("Invalid JSON type[" + e_jsonValueType + "] for property[" + o_jsonChild.getName() + "] with value[" + o_jsonChild.getValue() + "]");
						}
					}
				}
			}
		}
	}

	/* encoding data to JSON with JSON schema */
	
	/**
	 * Encode java object to a json file, keep existing json file
	 * 
	 * @param p_o_object					source java object to encode json information		
	 * @param p_s_jsonFile					destination json file to save encoded json information
	 * @return								file object with encoded json content
	 * @throws java.io.IOException			cannot create or access destination json file
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding json correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public File jsonEncode(Object p_o_object, String p_s_jsonFile) throws java.io.IOException, NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		return this.jsonEncode(p_o_object, p_s_jsonFile, false, true);
	}
	
	/**
	 * Encode java object to a json file
	 * 
	 * @param p_o_object					source java object to encode json information		
	 * @param p_s_jsonFile					destination json file to save encoded json information
	 * @param p_b_overwrite					true - overwrite existing json file, false - keep existing json file
	 * @return								file object with encoded json content
	 * @throws java.io.IOException			cannot create or access destination json file
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding json correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public File jsonEncode(Object p_o_object, String p_s_jsonFile, boolean p_b_overwrite) throws java.io.IOException, NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		return this.jsonEncode(p_o_object, p_s_jsonFile, p_b_overwrite, true);
	}
	
	/**
	 * Encode java object to a json file
	 * 
	 * @param p_o_object					source java object to encode json information		
	 * @param p_s_jsonFile					destination json file to save encoded json information
	 * @param p_b_overwrite					true - overwrite existing json file, false - keep existing json file
	 * @param p_b_prettyPrint				true - keep json file structure over multiple lines, false - delete all line breaks and white spaces not escaped by double quotes
	 * @return								encoded json information from java object as string
	 * @throws java.io.IOException			cannot create or access destination json file
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding json correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public File jsonEncode(Object p_o_object, String p_s_jsonFile, boolean p_b_overwrite, boolean p_b_prettyPrint) throws java.io.IOException, NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		/* check schema field */
		if (this.o_schema == null) {
			throw new NullPointerException("Cannot encode data. Schema is null.");
		}
		
		/* set level for PrintIndentation to zero */
		this.i_level = 0;
		
		/* encode data to json recursive */
		String s_json = this.jsonEncodeRecursive(this.o_schema, p_o_object, false);
		
												if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("Encoded JSON:" + this.s_lineBreak + s_json);
		
		if (!p_b_prettyPrint) {
			/* read all json file lines and delete all line-wraps and tabs */
			s_json = s_json.replaceAll("[\\r\\n\\t]", "");
			
			/* remove all white spaces, but not between double quotes */
			s_json = this.removeWhiteSpaces(s_json);
		}
		
		/* if file does not exist we must create an new file */
		if (!File.exists(p_s_jsonFile)) {
			if (p_b_overwrite) {
				p_b_overwrite = false;
			}
		}
		
		/* open (new) file */
		File o_file = new File(p_s_jsonFile, !p_b_overwrite);
		
		/* save json encoded data into file */
		o_file.replaceContent(s_json);
		
		/* return file object */
		return o_file;
	}
	
	/**
	 * Encode java object to a json content string
	 * 
	 * @param p_o_object					source java object to encode json information
	 * @return								encoded json information from java object as string
	 * @throws java.io.IOException			cannot create or access destination json file
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding json correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public String jsonEncode(Object p_o_object) throws java.io.IOException, NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		return this.jsonEncode(p_o_object, true);
	}
	
	/**
	 * Encode java object to a json content string
	 * 
	 * @param p_o_object					source java object to encode json information
	 * @param p_b_prettyPrint				true - keep json file structure over multiple lines, false - delete all line breaks and white spaces not escaped by double quotes		
	 * @return								encoded json information from java object as string
	 * @throws java.io.IOException			cannot create or access destination json file
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding json correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public String jsonEncode(Object p_o_object, boolean p_b_prettyPrint) throws java.io.IOException, NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		/* check schema field */
		if (this.o_schema == null) {
			throw new NullPointerException("Cannot encode data. Schema is null.");
		}
		
		/* set level for PrintIndentation to zero */
		this.i_level = 0;
		
		/* encode data to json recursive */
		String s_json = this.jsonEncodeRecursive(this.o_schema, p_o_object, false);
		
												if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("Encoded JSON:" + this.s_lineBreak + s_json);
		
		if (!p_b_prettyPrint) {
			/* read all json file lines and delete all line-wraps and tabs */
			s_json = s_json.replaceAll("[\\r\\n\\t]", "");
			
			/* remove all white spaces, but not between double quotes */
			s_json = this.removeWhiteSpaces(s_json);
		}
		
		/* return json content string */
		return s_json;
	}
	
	/**
	 * Recursive method to encode java object and it's fields to a json string
	 * 
	 * @param p_o_jsonSchemaElement			current json schema element with additional information for encoding
	 * @param p_o_object					source java object to encode json information
	 * @param p_b_parentIsCollection		hint that the parent json element is an array collection
	 * @return								encoded json information from java object as string
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding json correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	private String jsonEncodeRecursive(JSONElement p_o_jsonSchemaElement, Object p_o_object, boolean p_b_parentIsArray) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		String s_json = "";
		String s_jsonReferenceParentName = "";
		
		/* if type and mapping class are not set, we need at least a reference to continue */
		if ( (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getType())) && (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMappingClass())) ) {
			if (p_o_jsonSchemaElement.getReference() == null) {
				throw new NullPointerException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no type, no mapping class and no reference");
			} else {
				/* save name of current schema-element */
				s_jsonReferenceParentName = p_o_jsonSchemaElement.getName();
				
				/* set reference as current schema-element */
				p_o_jsonSchemaElement = p_o_jsonSchemaElement.getReference();
			}
		}
		
		/* check if type is set */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getType())) {
			throw new NullPointerException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no type");
		}
		
		/* check if mapping class is set if schema-element is not 'items' */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMappingClass())) {
			if (!p_o_jsonSchemaElement.getName().toLowerCase().contentEquals("items")) {
				throw new NullPointerException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no mapping class");
			}
		}

		if (p_o_jsonSchemaElement.getType().toLowerCase().contentEquals("object")) {
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "cast schema-object(" + p_o_jsonSchemaElement.getName() + ")[parentName=" + s_jsonReferenceParentName + "] with schema-mapping(" + p_o_jsonSchemaElement.printMapping() + ") and p_o_object(" + p_o_object.getClass().getTypeName() + "), castonly=" + (p_o_jsonSchemaElement.getMappingClass().contentEquals(p_o_object.getClass().getTypeName())));
			
			/* cast object of p_o_object */
			Object o_object = this.castObject(p_o_jsonSchemaElement, p_o_object, p_o_jsonSchemaElement.getMappingClass().contentEquals(p_o_object.getClass().getTypeName()));
			
			/* check if casted object is null */
			if (o_object == null) {
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "casted schema-object(" + p_o_jsonSchemaElement.getName() + ")[parentName=" + s_jsonReferenceParentName + "] with schema-mapping(" + p_o_jsonSchemaElement.printMapping() + ") is null");
				
				/* add object null value to json output */
				if ( (this.i_level == 0) || (p_b_parentIsArray) ) {
					s_json += this.printIndentation() + "null" + this.s_lineBreak;
				} else {
					/* check if a parent name for this reference is set */
					if (!de.forestj.lib.Helper.isStringEmpty(s_jsonReferenceParentName)) {
						s_json += this.printIndentation() + "\"" + s_jsonReferenceParentName + "\": null," + this.s_lineBreak;
					} else {
						s_json += this.printIndentation() + "\"" + p_o_jsonSchemaElement.getName() + "\": null," + this.s_lineBreak;
					}
				}
			} else {
				/* add object start curved bracket to json output */
				if ( (this.i_level == 0) || (p_b_parentIsArray) ) {
					s_json += this.printIndentation() + "{" + this.s_lineBreak;
				} else {
					/* check if a parent name for this reference is set */
					if (!de.forestj.lib.Helper.isStringEmpty(s_jsonReferenceParentName)) {
						s_json += this.printIndentation() + "\"" + s_jsonReferenceParentName + "\": {" + this.s_lineBreak;
					} else {
						s_json += this.printIndentation() + "\"" + p_o_jsonSchemaElement.getName() + "\": {" + this.s_lineBreak;
					}
				}
				
				/* increase level for PrintIndentation */
				this.i_level++;
				
				/* help variable to skip children iteration */
				boolean b_childrenIteration = true;
				
				/* check conditions for handling object */
				if (p_o_jsonSchemaElement.getReference() != null) {
					/* check if reference has any children */
					if (p_o_jsonSchemaElement.getReference().getChildren().size() < 1) {
						/* check if reference has another reference */
						if (p_o_jsonSchemaElement.getReference().getReference() == null) {
							throw new NullPointerException("Reference[" + p_o_jsonSchemaElement.getReference().getName() + "] of schema-element[" + p_o_jsonSchemaElement.getName() + "] has no children and no other reference");
						} else {
							b_childrenIteration = false;
	
																	de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-object(" + p_o_jsonSchemaElement.getName() + ") with its reference(" + p_o_jsonSchemaElement.getReference().getName() + ") and schema-mapping(" + p_o_jsonSchemaElement.printMapping() + ") which has another reference with recursion");
							
							/* handle reference with recursion */
							s_json += this.jsonEncodeRecursive(p_o_jsonSchemaElement.getReference(), o_object, false);
						}
					} else {
																de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-object(" + p_o_jsonSchemaElement.getName() + ") with its reference(" + p_o_jsonSchemaElement.getReference().getName() + ") which has children");
						
						/* set reference as current json element */
						p_o_jsonSchemaElement = p_o_jsonSchemaElement.getReference();
					}
				}
				
				/* execute children iteration */
				if (b_childrenIteration) {
					/* check if object has any children */
					if (p_o_jsonSchemaElement.getChildren().size() < 1) {
						throw new IllegalArgumentException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no children");
					}
					
					/* iterate all children of current json element */
					for (JSONElement o_jsonElement : p_o_jsonSchemaElement.getChildren()) {
															de.forestj.lib.Global.ilogFinest(this.printIndentation() + "encode schema-object's(" + p_o_jsonSchemaElement.getName() + ") child(" + o_jsonElement.getName() + ") with schema-mapping(" + p_o_jsonSchemaElement.printMapping() + ") with recursion");
						
						/* handle child with recursion */
						s_json += this.jsonEncodeRecursive(o_jsonElement, o_object, false);
					}
				}
				
				/* decrease level for PrintIndentation */
				this.i_level--;
				
				/* change last ',\r\n' to '\r\n' */
				if (s_json.endsWith("," + this.s_lineBreak)) {
					s_json = s_json.substring(0, s_json.length() - 1 - this.s_lineBreak.length()) + this.s_lineBreak;
				}
				
				/* add object end curved bracket to json output */
				s_json += this.printIndentation() + "}," + this.s_lineBreak;
			}
		} else if (p_o_jsonSchemaElement.getType().toLowerCase().contentEquals("array")) {
			/* add property to json with starting array with opening bracket */
			if ( (this.i_level == 0) || (p_b_parentIsArray) ) {
				s_json += this.printIndentation() + "[" + this.s_lineBreak;
			} else {
				/* check if a parent name for this reference is set */
				if (!de.forestj.lib.Helper.isStringEmpty(s_jsonReferenceParentName)) {
					s_json += this.printIndentation() + "\"" + s_jsonReferenceParentName + "\": [" + this.s_lineBreak;
				} else {
					s_json += this.printIndentation() + "\"" + p_o_jsonSchemaElement.getName() + "\": [" + this.s_lineBreak;
				}
			}
			
			/* check conditions for handling array */
			if (p_o_jsonSchemaElement.getReference() != null) {
				if (p_o_jsonSchemaElement.getReference().getChildren().size() < 1) {
					throw new IllegalArgumentException("Reference[" + p_o_jsonSchemaElement.getReference().getName() + "] of schema-element[" + p_o_jsonSchemaElement.getName() + "] with schema-type[" + p_o_jsonSchemaElement.getType() + "] must have at least one child");
				}
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-array(" + p_o_jsonSchemaElement.getName() + ")[parentName=" + s_jsonReferenceParentName + "] with its reference(" + p_o_jsonSchemaElement.getReference().getName() + ") which has children");
			} else {
				if (p_o_jsonSchemaElement.getChildren().size() != 1) {
					throw new IllegalArgumentException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] with schema-type[" + p_o_jsonSchemaElement.getType() + "] must have just one child");
				}
				
				if (!p_o_jsonSchemaElement.getChildren().get(0).getName().toLowerCase().contentEquals("items")) {
					throw new IllegalArgumentException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] with schema-type[" + p_o_jsonSchemaElement.getName() + "] must have one child with name[items]");
				}
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-array(" + p_o_jsonSchemaElement.getName() + ")[parentName=" + s_jsonReferenceParentName + "] with child(items) which has children");
			}
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-array(" + p_o_jsonSchemaElement.getName() + ") - and cast object with p_o_object(" + p_o_object.getClass().getTypeName() + ") and schema-mapping(" + p_o_jsonSchemaElement.printMapping() + "), castonly if java.util.List=" + (p_o_object instanceof java.util.List));
			
			/* get array data from object */
			Object o_object = this.castObject(p_o_jsonSchemaElement, p_o_object, (p_o_object instanceof java.util.List));
			
			/* check if we have a primitive array and current value is just empty/null */
			if ( (o_object == null) && (p_o_jsonSchemaElement.getChildren().get(0).getPrimitiveArray()) ) {
				s_json += this.s_lineBreak;
			} else {
				/* cast current object as list with unknown generic type */
				java.util.List<?> a_objects = (java.util.List<?>)o_object;
	
				/* flag if we must handle generic list as primitive array */
				boolean b_primitiveArray = p_o_jsonSchemaElement.getChildren().size() > 0 && p_o_jsonSchemaElement.getChildren().get(0).getPrimitiveArray();
				
				/* check minItems and maxItems restrictions */
				if (p_o_jsonSchemaElement.getRestrictions().size() > 0) {
					for (JSONRestriction o_jsonRestriction : p_o_jsonSchemaElement.getRestrictions()) {
						if (o_jsonRestriction.getName().toLowerCase().contentEquals("minitems")) {
							/* check minItems restriction */
							if (a_objects.size() < o_jsonRestriction.getIntValue()) {
								throw new IllegalArgumentException("Restriction error: not enough [" + p_o_jsonSchemaElement.getName() + "] json items(" + a_objects.size() + "), minimum = " + o_jsonRestriction.getIntValue());
							}
						}
						
						if (o_jsonRestriction.getName().toLowerCase().contentEquals("maxitems")) {
							/* check maxItems restriction */
							if (a_objects.size() > o_jsonRestriction.getIntValue()) {
								throw new IllegalArgumentException("Restriction error: too many [" + p_o_jsonSchemaElement.getName() + "] json items(" + a_objects.size() + "), maximum = " + o_jsonRestriction.getIntValue());
							}
						}
					}
				}
				
				if (p_o_jsonSchemaElement.getReference() != null) {
					/* set reference as current json element */
					p_o_jsonSchemaElement = p_o_jsonSchemaElement.getReference();
				} else {
					/* set current json element to 'items' child */
					p_o_jsonSchemaElement = p_o_jsonSchemaElement.getChildren().get(0);
					
					/* if 'items' child has a child as well, we take this child as current json element */
					if (p_o_jsonSchemaElement.getChildren().size() == 1) {
						p_o_jsonSchemaElement = p_o_jsonSchemaElement.getChildren().get(0);
					}
				}
				
				/* iterate objects in list and encode data to json recursively */
				for (int i = 0; i < a_objects.size(); i++) {
					/* increase level for PrintIndentation */
					this.i_level++;
					
					/* check if array object value is null */
					if (a_objects.get(i) == null) {
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "encode schema-array(" + p_o_jsonSchemaElement.getName() + ") with array element(null) and schema-mapping(" + p_o_jsonSchemaElement.printMapping() + ")");
						
						s_json += this.printIndentation() + "null," + this.s_lineBreak;
					} else if (b_primitiveArray) {
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "encode schema-array(" + p_o_jsonSchemaElement.getName() + ") with primitive array element(#" + (i + 1) + ") and schema-mapping(" + p_o_jsonSchemaElement.printMapping() + ")");
						
						/* add primitive array element value to JSON */
						s_json += this.printIndentation() + a_objects.get(i) + "," + this.s_lineBreak;
					} else {
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "encode schema-array(" + p_o_jsonSchemaElement.getName() + ") with array element(" + a_objects.get(i).getClass().getTypeName() + ") and schema-mapping(" + p_o_jsonSchemaElement.printMapping() + ") with recursion");
						
						/* handle object with recursion */
						s_json += this.jsonEncodeRecursive(p_o_jsonSchemaElement, a_objects.get(i), true);
					}
					
					/* decrease level for PrintIndentation */
					this.i_level--;
				}
			}
			
			/* change last "},\r\n" to "}\r\n" */
			if (s_json.endsWith("}," + this.s_lineBreak)) {
				s_json = s_json.substring(0, s_json.length() - 1 - this.s_lineBreak.length()) + this.s_lineBreak;
			}
			
			/* change last ',\r\n' to '\r\n' */
			if (s_json.endsWith("," + this.s_lineBreak)) {
				s_json = s_json.substring(0, s_json.length() - 1 - this.s_lineBreak.length()) + this.s_lineBreak;
			}
			
			/* end array with closing bracket */
			s_json += this.printIndentation() + "]," + this.s_lineBreak;
			
			/* recognize empty array result and replace it into two brackets */
			s_json = s_json.replaceAll("\\[" + this.s_lineBreak + "(\\t)*\\]", "\\[\\]");
		} else {
			/* set object variable with current object */
			Object o_object = p_o_object;
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-property(" + p_o_jsonSchemaElement.getName() + ") with p_o_object(" + p_o_object.getClass().getTypeName() + ")");
			
			/* get object property if we have not an array with items */
			if (!p_o_jsonSchemaElement.getName().toLowerCase().contentEquals("items")) {
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-property(" + p_o_jsonSchemaElement.getName() + "), cast object with p_o_object(" + p_o_object.getClass().getTypeName() + ")");
				
				/* get object property of current json element */
				o_object = this.castObject(p_o_jsonSchemaElement, p_o_object);
			}
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + "encode schema-property(" + p_o_jsonSchemaElement.getName() + "), cast string from object with p_o_object(" + p_o_object.getClass().getTypeName() + ") and schema-type(" + p_o_jsonSchemaElement.getType() + ")");
			
			/* get string value of object for json-element */
			String s_foo = this.castStringFromObject(o_object, p_o_jsonSchemaElement.getType());
			
			/* check if json-element is required */
			if (p_o_jsonSchemaElement.getRequired()) {
				/* check if value is empty */
				if ( (s_foo.contentEquals("")) || (s_foo.contentEquals("null")) || (s_foo.contentEquals("\"\"")) ) {
					throw new IllegalArgumentException("'" + p_o_jsonSchemaElement.getName() + "' is required, but value[" + s_foo + "] is empty");
				}
			}
			
			/* check if json-element has any restrictions */
			if (p_o_jsonSchemaElement.getRestrictions().size() > 0) {
				for (JSONRestriction o_jsonRestriction : p_o_jsonSchemaElement.getRestrictions()) {
					/* execute restriction check */
					this.checkRestriction(s_foo, o_jsonRestriction, p_o_jsonSchemaElement);
				}
			}
			
			/* add json-element with value */
			if (p_o_jsonSchemaElement.getName().toLowerCase().contentEquals("items")) {
				/* array with items does not need captions */
				s_json += this.printIndentation() + s_foo + "," + this.s_lineBreak;
			} else {
				s_json += this.printIndentation() + "\"" + p_o_jsonSchemaElement.getName() + "\": " + s_foo + "," + this.s_lineBreak;
			}
		}

		if (this.i_level == 0) {
			if (s_json.endsWith("," + this.s_lineBreak)) {
				s_json = s_json.substring(0, s_json.length() - 1 - this.s_lineBreak.length()) + this.s_lineBreak;
			}
		}
		
		return s_json;
	}
	
	/**
	 * Cast field of an object
	 * 
	 * @param p_o_jsonElement				json element object	with mapping class information	
	 * @param p_o_object					object to access fields via direct public access or public access to property methods (getXX and setXX)
	 * @return								casted field value of object as object
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	private Object castObject(JSONElement p_o_jsonElement, Object p_o_object) throws NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		return this.castObject(p_o_jsonElement, p_o_object, false);
	}
	
	/**
	 * Cast field of an object, optional cast only
	 * 
	 * @param p_o_jsonElement				json element object	with mapping class information	
	 * @param p_o_object					object to access fields via direct public access or public access to property methods (getXX and setXX)
	 * @param b_castOnly					true - just cast object with json element mapping class information, false - get object field value
	 * @return								casted field value of object as object
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	private Object castObject(JSONElement p_o_jsonElement, Object p_o_object, boolean b_castOnly) throws NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException {
		/* variable for object data */
		Object o_object = null;
		
		/* check if we only have to cast the current object */
		if (b_castOnly) {
			/* cast current object by string mapping value */
			Class<?> o_class = Class.forName(p_o_jsonElement.getMappingClass());
			o_object = o_class.cast(p_o_object); 
		} else {
			/* retrieve field information out of schema element */
			String s_field = p_o_jsonElement.getMappingClass();
			
			/* if there is additional mapping information, use this for field property access */
			if (!de.forestj.lib.Helper.isStringEmpty(p_o_jsonElement.getMapping())) {
				s_field = p_o_jsonElement.getMapping();
			}
			
			/* check if we use property methods with invoke to get object data values */
			if (this.b_usePropertyMethods) {
				java.lang.reflect.Method o_method = null;
				
				/* store get-property-method of java type object */
				try {
					o_method = p_o_object.getClass().getDeclaredMethod("get" + s_field);
				} catch (NoSuchMethodException | SecurityException o_exc) {
					throw new NoSuchMethodException("Class instance method[" + "get" + s_field + "] does not exist for encoding data json-schema-element: " + p_o_jsonElement.getName() + "(" + p_o_object.getClass().getTypeName() + ")");
				}
				
				/* invoke get-property-method to get object data of current element */
				o_object = o_method.invoke(p_o_object);
			} else {
				/* call field directly to get object data values */
				try {
					o_object = p_o_object.getClass().getDeclaredField(s_field).get(p_o_object);
				} catch (Exception o_exc) {
					throw new NoSuchFieldException("Class instance property[" + s_field + "] returns no data or not accessible for encoding data json-schema-element: " + p_o_jsonElement.getName());
				}
			}
			
			/* check if we have an array schema element with primitive array type */
			if ( (o_object != null) && (p_o_jsonElement.getChildren().size() > 0) && (p_o_jsonElement.getChildren().get(0).getPrimitiveArray()) ) {
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
							a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
								a_primtiveArray.add( this.castStringFromObject(a_objects[i], p_o_jsonElement.getChildren().get(0).getType()) );
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
	 * Method to cast an object value to a string value for encoding json data
	 * 
	 * @param p_o_object					object value which will be casted to string
	 * @param p_s_type						type as string to distinguish
	 * @return								casted object value as string
	 * @throws IllegalArgumentException		invalid or empty type value
	 */
	private String castStringFromObject(Object p_o_object, String p_s_type) throws IllegalArgumentException {
		String s_foo = "";
		p_s_type = p_s_type.toLowerCase();
		
		if (p_o_object != null) {
			if (p_s_type.contentEquals("string")) {
				if (p_o_object instanceof java.util.Date) {
					s_foo = "\"" + de.forestj.lib.Helper.utilDateToISO8601UTC(java.util.Date.class.cast(p_o_object)) + "\"";
				} else if (p_o_object instanceof java.time.LocalDateTime) {
					s_foo = "\"" + de.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.class.cast(p_o_object)) + "\"";
				} else if (p_o_object instanceof java.time.LocalDate) {
					s_foo = "\"" + de.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.of(java.time.LocalDate.class.cast(p_o_object), java.time.LocalTime.of(0, 0))) + "\"";
				} else if (p_o_object instanceof java.time.LocalTime) {
					s_foo = "\"" + de.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.of(java.time.LocalDate.of(1900, 1, 1), java.time.LocalTime.class.cast(p_o_object))) + "\"";
				} else {
					s_foo = "\"" + String.class.cast(p_o_object).toString().replaceAll("\"", "\\\\\"") + "\"";
				}
			} else if (p_s_type.contentEquals("number")) {
				if (p_o_object instanceof java.lang.Float) {
					java.lang.Float o_float = java.lang.Float.class.cast(p_o_object);
					s_foo = o_float.toString();
				} else if (p_o_object instanceof java.lang.Double) {
					java.lang.Double o_double = java.lang.Double.class.cast(p_o_object);
					s_foo = o_double.toString();
				} else {
					java.math.BigDecimal o_bigDecimal = java.math.BigDecimal.class.cast(p_o_object);
					s_foo = o_bigDecimal.toString();
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
				
				if (s_foo.contentEquals("0")) {
					s_foo = "0";
				}
			} else if (p_s_type.contentEquals("boolean")) {
				s_foo = Boolean.class.cast(p_o_object).toString();
			} else if (p_s_type.contentEquals("null")) {
				s_foo = "null";
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] for " + p_o_object.getClass().getSimpleName());
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
	 * Check if json element restriction is valid with current value
	 * 
	 * @param p_s_value						string value for json element restriction, can be casted to integer as well
	 * @param p_o_jsonRestriction			json restriction object which holds all restriction information
	 * @param p_o_jsonElement				json element object
	 * @throws IllegalArgumentException		unknown restriction name, restriction error or invalid type from json element object
	 */
	private void checkRestriction(String p_s_value, JSONRestriction p_o_jsonRestriction, JSONElement p_o_jsonElement) throws IllegalArgumentException {
		String p_s_type = p_o_jsonElement.getType().toLowerCase();
		
		/* remove surrounding '"' and '"' characters */
		if ( (p_s_value.startsWith("\"")) && (p_s_value.endsWith("\"")) ) {
			p_s_value = p_s_value.substring(1, p_s_value.length() - 1);
		}
		
		if (p_o_jsonRestriction.getName().toLowerCase().contentEquals("minimum")) {
			if (p_s_type.contentEquals("number")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_jsonRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Restriction error: value[" + o_value + "] does not match " + p_o_jsonRestriction.getName() + " restriction[" + p_o_jsonRestriction.getStrValue() + "]");
				}
			} else if (p_s_type.contentEquals("integer")) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_jsonRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Restriction error: value[" + i_value + "] does not match " + p_o_jsonRestriction.getName() + " restriction[" + p_o_jsonRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_jsonElement.getName() + "' using restriction[" + p_o_jsonRestriction.getName() + "]");
			}
		} else if (p_o_jsonRestriction.getName().toLowerCase().contentEquals("exclusiveminimum")) {
			if (p_s_type.contentEquals("number")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_jsonRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if ( (i_compare == -1) || (i_compare == 0) ) {
					throw new IllegalArgumentException("Restriction error: value[" + o_value + "] does not match " + p_o_jsonRestriction.getName() + " restriction[" + p_o_jsonRestriction.getStrValue() + "]");
				}
			} else if (p_s_type.contentEquals("integer")) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_jsonRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if ( (i_compare == -1) || (i_compare == 0) ) {
					throw new IllegalArgumentException("Restriction error: value[" + i_value + "] does not match " + p_o_jsonRestriction.getName() + " restriction[" + p_o_jsonRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_jsonElement.getName() + "' using restriction[" + p_o_jsonRestriction.getName() + "]");
			}
		} else if (p_o_jsonRestriction.getName().toLowerCase().contentEquals("maximum")) {
			if (p_s_type.contentEquals("number")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_jsonRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Restriction error: value[" + o_value + "] does not match " + p_o_jsonRestriction.getName() + " restriction[" + p_o_jsonRestriction.getStrValue() + "]");
				}
			} else if (p_s_type.contentEquals("integer")) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_jsonRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Restriction error: value[" + i_value + "] does not match " + p_o_jsonRestriction.getName() + " restriction[" + p_o_jsonRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_jsonElement.getName() + "' using restriction[" + p_o_jsonRestriction.getName() + "]");
			}
		} else if (p_o_jsonRestriction.getName().toLowerCase().contentEquals("exclusivemaximum")) {
			if (p_s_type.contentEquals("number")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_jsonRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if ( (i_compare == 1) || (i_compare == 0) ) {
					throw new IllegalArgumentException("Restriction error: value[" + o_value + "] does not match " + p_o_jsonRestriction.getName() + " restriction[" + p_o_jsonRestriction.getStrValue() + "]");
				}
			} else if (p_s_type.contentEquals("integer")) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_jsonRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if ( (i_compare == 1) || (i_compare == 0) ) {
					throw new IllegalArgumentException("Restriction error: value[" + i_value + "] does not match " + p_o_jsonRestriction.getName() + " restriction[" + p_o_jsonRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_jsonElement.getName() + "' using restriction[" + p_o_jsonRestriction.getName() + "]");
			}
		} else if (p_o_jsonRestriction.getName().toLowerCase().contentEquals("minlength")) {
			if (p_s_type.contentEquals("string")) {
				if (p_s_value.length() < p_o_jsonRestriction.getIntValue()) {
					throw new IllegalArgumentException("Restriction error: value[" + p_s_value + "] does not match " + p_o_jsonRestriction.getName() + " restriction[" + p_o_jsonRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_jsonElement.getName() + "' using restriction[" + p_o_jsonRestriction.getName() + "]");
			}
		} else if (p_o_jsonRestriction.getName().toLowerCase().contentEquals("maxlength")) {
			if (p_s_type.contentEquals("string")) {
				if (p_s_value.length() > p_o_jsonRestriction.getIntValue()) {
					throw new IllegalArgumentException("Restriction error: value[" + p_s_value + "] does not match " + p_o_jsonRestriction.getName() + " restriction[" + p_o_jsonRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_jsonElement.getName() + "' using restriction[" + p_o_jsonRestriction.getName() + "]");
			}
		} else if (p_o_jsonRestriction.getName().toLowerCase().contentEquals("pattern")) {
			if ( (p_s_type.contentEquals("string")) || (p_s_type.contentEquals("boolean")) || (p_s_type.contentEquals("number")) || (p_s_type.contentEquals("integer")) )  {
				if (!de.forestj.lib.Helper.matchesRegex(p_s_value, p_o_jsonRestriction.getStrValue())) {
					throw new IllegalArgumentException("Restriction error: value[" + p_s_value + "] does not match " + p_o_jsonRestriction.getName() + " restriction[" + p_o_jsonRestriction.getStrValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] of '" + p_o_jsonElement.getName() + "' using restriction[" + p_o_jsonRestriction.getName() + "]");
			}
		} else {
			throw new IllegalArgumentException("Unknown Restriction: " + p_o_jsonRestriction.getName());
		}
	}

	/* validating json data with JSON schema */
	
	/**
	 * Validate json file
	 * 
	 * @param p_s_jsonFile					full-path to json file
	 * @return								true - content of json file is valid, false - content of json file is invalid
	 * @throws IllegalArgumentException		json file does not exist
	 * @throws java.io.IOException			cannot read json file content
	 * @throws NullPointerException			empty schema, empty json file or root node after parsing json content
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	public boolean validateAgainstSchema(String p_s_jsonFile) throws NullPointerException, IllegalArgumentException, java.io.IOException, java.text.ParseException, java.time.DateTimeException {
		/* check schema field */
		if (this.o_schema == null) {
			throw new NullPointerException("Cannot decode data. Schema is null.");
		}
		
		/* check if file exists */
		if (!File.exists(p_s_jsonFile)) {
			throw new IllegalArgumentException("JSON file[" + p_s_jsonFile + "] does not exist.");
		}
		
		/* open json file */
		File o_file = new File(p_s_jsonFile, false);
		
												de.forestj.lib.Global.ilogFiner("read all lines from json file '" + p_s_jsonFile + "'");
		
		/* decode json file lines */										
		return validateAgainstSchema(o_file.getFileContentAsList());
	}
	
	/**
	 * Validate json content
	 * 
	 * @param p_a_jsonLines					json lines
	 * @return								true - json content is valid, false - json content is invalid
	 * @throws NullPointerException			empty schema, empty json file or root node after parsing json content
	 * @throws IllegalArgumentException		condition failed for decoding json correctly
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	public boolean validateAgainstSchema(java.util.List<String> p_a_jsonLines) throws NullPointerException, IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		/* check schema field */
		if (this.o_schema == null) {
			throw new NullPointerException("Cannot decode data. Schema is null.");
		}
		
												de.forestj.lib.Global.ilogFiner("read all lines: '" + p_a_jsonLines.size() + "'");
		
		StringBuilder o_stringBuilder = new StringBuilder();
		
		/* read all json schema file lines to one string builder */
		for (String s_line : p_a_jsonLines) {
			o_stringBuilder.append(s_line);
		}
		
		/* read all json-schema file lines and delete all line-wraps and tabs */
		String s_json = o_stringBuilder.toString().replaceAll("[\\r\\n\\t]", "");
		
		/* remove all white spaces, but not between double quotes */
		s_json = this.removeWhiteSpaces(s_json);
		
		/* check if json-schema starts with (curly) brackets */
	    if (( ( (!s_json.startsWith("{")) || (!s_json.endsWith("}")) ) && ( (!s_json.startsWith("[")) || (!s_json.endsWith("]")) ) )) {
    		throw new IllegalArgumentException("JSON-file must start and end with curly bracket '{', '}' or must start and end with bracket '[', ']'");
    	}
	    
	    /* validate json schema */
	    this.validateJSON(s_json);
	    
	   											de.forestj.lib.Global.ilogFinest("validated json content lines");
	    
	    this.o_root = null;
	    this.o_currentElement = null;
	    
	    /* parse json */
	    this.parseJSON(s_json);
	    
											    de.forestj.lib.Global.ilogFinest("parsed json content lines");
											    
												if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("JSON-Schema:" + de.forestj.lib.io.File.NEWLINE + this.o_schema);
											    if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("JSON-Root-Element:" + de.forestj.lib.io.File.NEWLINE + this.o_root);
	    
	    /* validate json recursively */
	    return this.validateAgainstSchemaRecursive(this.o_root, this.o_schema);
	}
	
	/**
	 * Recursive method to validate yaml content string
	 * 
	 * @param p_o_jsonDataElement			current json data element
	 * @param p_o_jsonSchemaElement			current json schema element with additional information for decoding
	 * @return								true - json content is valid, false - json content is invalid
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding json correctly
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private boolean validateAgainstSchemaRecursive(JSONElement p_o_jsonDataElement, JSONElement p_o_jsonSchemaElement) throws NullPointerException, IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		boolean b_return = true;
		
		/* if type and mapping class are not set, we need at least a reference to continue */
		if ( (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getType())) && (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMappingClass())) ) {
			if (p_o_jsonSchemaElement.getReference() == null) {
				throw new NullPointerException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no type, no mapping class and no reference");
			} else {
				/* set reference as current schema-element */
				p_o_jsonSchemaElement = p_o_jsonSchemaElement.getReference();
			}
		}
		
		/* check if type is set */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getType())) {
			throw new IllegalArgumentException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no type");
		}
		
		/* check if mapping class is set if schema-element is not 'items' */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMappingClass())) {
			if (!p_o_jsonSchemaElement.getName().toLowerCase().contentEquals("items")) {
				throw new IllegalArgumentException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no mapping class");
			}
		}
		
		if (p_o_jsonSchemaElement.getType().toLowerCase().contentEquals("object")) {
			/* check if we have any data for new object */
			if (p_o_jsonDataElement.getChildren().size() > 0) {
				String s_objectType = p_o_jsonSchemaElement.getMappingClass();
				
				/* if object has reference, we create new object instance by mapping of reference */
				if ( (p_o_jsonSchemaElement.getReference() != null) && (p_o_jsonSchemaElement.getReference().getType().toLowerCase().contentEquals("object")) ) {
					s_objectType = p_o_jsonSchemaElement.getReference().getMappingClass();
				}
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": create new schema-object instance with mapping[" + p_o_jsonSchemaElement.printMapping() + "] and type[" + s_objectType + "]");
				
				/* increase level for PrintIndentation */
				this.i_level++;
				
				/* help variable to skip children iteration */
				boolean b_childrenIteration = true;
				
				/* check conditions for handling object */
				if (p_o_jsonSchemaElement.getReference() != null) {
					/* check if reference has any children */
					if (p_o_jsonSchemaElement.getReference().getChildren().size() < 1) {
						/* check if reference has another reference */
						if (p_o_jsonSchemaElement.getReference().getReference() == null) {
							throw new NullPointerException("Reference[" + p_o_jsonSchemaElement.getReference().getName() + "] of schema-element[" + p_o_jsonSchemaElement.getName() + "] has no children and no other reference");
						} else {
							b_childrenIteration = false;
							
							/* check if current element in schema has data element by name, otherwise skip this element */
							if (p_o_jsonSchemaElement.getName().contentEquals(p_o_jsonDataElement.getName())) {
																		de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": get schema-object[" + p_o_jsonSchemaElement.getMappingClass() + "] with reference[" + p_o_jsonSchemaElement.getReference().getName() + "]");
	
								/* only create new object if we have one object data */
								if (p_o_jsonDataElement.getChildren().size() != 1) {
									throw new IllegalArgumentException("We have (" + p_o_jsonDataElement.getChildren().size() + ") no data children or more than one for schema-element[" + p_o_jsonSchemaElement.getName() + "]");
								}
								
								/* handle reference with recursion */
								b_return = this.validateAgainstSchemaRecursive(p_o_jsonDataElement.getChildren().get(0), p_o_jsonSchemaElement.getReference());
							}
						}
					} else {
																de.forestj.lib.Global.ilogFiner(this.printIndentation() + "update current schema-element[" + p_o_jsonSchemaElement.getName() + "](" + p_o_jsonSchemaElement.printMapping() + ") with reference[" + p_o_jsonSchemaElement.getReference().getName() + "](" + p_o_jsonSchemaElement.getReference().printMapping() + ")");
						
						/* set reference as current json element */
						p_o_jsonSchemaElement = p_o_jsonSchemaElement.getReference();
					}
				}
				
				/* execute children iteration */
				if (b_childrenIteration) {
															de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": get schema-object with type[" + p_o_jsonSchemaElement.getMappingClass() + "] and children[" + p_o_jsonSchemaElement.getChildren().size() + "]");
					
					/* only create new object if we have one child definition for object in json schema */
					if (p_o_jsonSchemaElement.getChildren().size() < 1) {
						throw new IllegalArgumentException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no children");	
					}
					
					/* check if new object is required if there is no data */
					if ( (p_o_jsonDataElement.getChildren().size() < 1) && (p_o_jsonSchemaElement.getRequired()) ) {
						throw new IllegalArgumentException("We have no data children for schema-element[" + p_o_jsonSchemaElement.getName() + "] which is required");
					}
					
					/* only iterate if we have object data */
					if (p_o_jsonDataElement.getChildren().size() > 0) {
						/* increase level for PrintIndentation */
						this.i_level++;
						
						/* data pointer */
						int j = 0;
						
						for (int i = 0; i < p_o_jsonSchemaElement.getChildren().size(); i++) {
																	de.forestj.lib.Global.ilogFinest(this.printIndentation() + "compare schema-child-name(" + p_o_jsonSchemaElement.getChildren().get(i).getName() + ") with data-child-name(" + p_o_jsonDataElement.getChildren().get(j).getName() + ")");
							
							/* check if current element in schema has data element by name, otherwise skip this element */
							if (!p_o_jsonSchemaElement.getChildren().get(i).getName().contentEquals(p_o_jsonDataElement.getChildren().get(j).getName())) {
								/* but check if current schema element is required, so we must throw an exception */
								if (p_o_jsonSchemaElement.getChildren().get(i).getRequired()) {
									throw new IllegalArgumentException("We have no data for schema-element[" + p_o_jsonSchemaElement.getChildren().get(i).getName() + "] which is required");
								} else {
									continue;
								}
							}
							
							/* handle new object with recursion */
							b_return = this.validateAgainstSchemaRecursive(p_o_jsonDataElement.getChildren().get(j), p_o_jsonSchemaElement.getChildren().get(i));
							
							/* increase data pointer */
							j++;
						}
						
						/* decrease level for PrintIndentation */
						this.i_level--;
					}
				}
				
				/* decrease level for PrintIndentation */
				this.i_level--;
			}
		} else if (p_o_jsonSchemaElement.getType().toLowerCase().contentEquals("array")) {
			/* check conditions for handling array */
			if (p_o_jsonSchemaElement.getReference() != null) {
				if (p_o_jsonSchemaElement.getReference().getChildren().size() < 1) {
					throw new IllegalArgumentException("Reference[" + p_o_jsonSchemaElement.getReference().getName() + "] of schema-array[" + p_o_jsonSchemaElement.getName() + "] with mapping[" + p_o_jsonSchemaElement.getMappingClass() + "] must have at least one child");
				}
			} else {
				if (p_o_jsonSchemaElement.getChildren().size() != 1) {
					throw new IllegalArgumentException("Schema-array[" + p_o_jsonSchemaElement.getName() + "] with mapping[" + p_o_jsonSchemaElement.getMappingClass() + "] must have just one child");
				}
				
				if (!p_o_jsonSchemaElement.getChildren().get(0).getName().toLowerCase().contentEquals("items")) {
					throw new IllegalArgumentException("Schema-array[" + p_o_jsonSchemaElement.getName() + "] with mapping[" + p_o_jsonSchemaElement.getMappingClass() + "] must have one child with name[items]");
				}
			}
			
			/* help variables to handle array */
			boolean b_required = false;
			String s_requiredProperty = "";
			java.util.List<JSONRestriction> a_restrictions = new java.util.ArrayList<JSONRestriction>();
			String s_amountProperty = "";
			
			/* check if json-element is required */
			if (p_o_jsonSchemaElement.getRequired()) {
				b_required = true;
				s_requiredProperty = p_o_jsonSchemaElement.getName();
			}
			
			/* check minItems and maxItems restrictions and save them for items check afterwards */
			if (p_o_jsonSchemaElement.getRestrictions().size() > 0) {
				for (JSONRestriction o_jsonRestriction : p_o_jsonSchemaElement.getRestrictions()) {
					if ( (o_jsonRestriction.getName().toLowerCase().contentEquals("minitems")) || (o_jsonRestriction.getName().toLowerCase().contentEquals("maxitems"))) {
						a_restrictions.add(o_jsonRestriction);
						s_amountProperty = p_o_jsonSchemaElement.getName();
					}
				}
			}
			
			if (p_o_jsonSchemaElement.getReference() != null) {
				/* set reference as current json element */
				p_o_jsonSchemaElement = p_o_jsonSchemaElement.getReference();
			} else {
				/* set current json element to 'items' child */
				p_o_jsonSchemaElement = p_o_jsonSchemaElement.getChildren().get(0);
				
				/* if 'items' child has a child as well, we take this child as current json element */
				if (p_o_jsonSchemaElement.getChildren().size() == 1) {
					p_o_jsonSchemaElement = p_o_jsonSchemaElement.getChildren().get(0);
				}
			}
			
			if (de.forestj.lib.Helper.isStringEmpty(p_o_jsonDataElement.getValue())) { /* we have multiple minor objects for current array */
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": get schema-array with mapping[" + p_o_jsonSchemaElement.printMapping() + "]");
				
				if (p_o_jsonDataElement.getChildren().size() > 0) { /* if we have objects to the new array */
					/* check minItems and maxItems restrictions */
					if (a_restrictions.size() > 0) {
						for (JSONRestriction o_jsonRestriction : a_restrictions) {
							if (o_jsonRestriction.getName().toLowerCase().contentEquals("minitems")) {
								/* check minItems restriction */
								if (p_o_jsonDataElement.getChildren().size() < o_jsonRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: not enough [" + p_o_jsonSchemaElement.getName() + " of " + s_amountProperty + "] json items(" + p_o_jsonDataElement.getChildren().size() + "), minimum = " + o_jsonRestriction.getIntValue());
								}
							}
							
							if (o_jsonRestriction.getName().toLowerCase().contentEquals("maxitems")) {
								/* check maxItems restriction */
								if (p_o_jsonDataElement.getChildren().size() > o_jsonRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: too many [" + p_o_jsonSchemaElement.getName() + " of " + s_amountProperty + "] json items(" + p_o_jsonDataElement.getChildren().size() + "), maximum = " + o_jsonRestriction.getIntValue());
								}
							}
						}
					}
					
					/* iterate objects in list and encode data to json recursively */
					for (int i = 0; i < p_o_jsonDataElement.getChildren().size(); i++) {
						/* increase level for PrintIndentation */
						this.i_level++;
						
						/* handle array object with recursion */
						b_return = this.validateAgainstSchemaRecursive(p_o_jsonDataElement.getChildren().get(i), p_o_jsonSchemaElement);
						
						/* decrease level for PrintIndentation */
						this.i_level--;
					}
				}
			} else { /* array objects must be retrieved out of value property */
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": handle array value = " + p_o_jsonDataElement.getValue() + " - mapping[" + p_o_jsonSchemaElement.printMapping() + "]");
				
				/* set array with values if we have any values */
				if ( (!de.forestj.lib.Helper.isStringEmpty(p_o_jsonDataElement.getValue())) && (!p_o_jsonDataElement.getValue().contentEquals("[]")) ) {
					/* remove opening and closing brackets */
					if ( (p_o_jsonDataElement.getValue().startsWith("[")) && (p_o_jsonDataElement.getValue().endsWith("]")) ) {
						p_o_jsonDataElement.setValue(p_o_jsonDataElement.getValue().substring(1, p_o_jsonDataElement.getValue().length() - 1));
					}
					
					/* split array into values, divided by ',' */
					String[] a_values = p_o_jsonDataElement.getValue().split(",");
					
					/* check minItems and maxItems restrictions */
					if (a_restrictions.size() > 0) {
						for (JSONRestriction o_jsonRestriction : a_restrictions) {
							if (o_jsonRestriction.getName().toLowerCase().contentEquals("minitems")) {
								/* check minItems restriction */
								if (a_values.length < o_jsonRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: not enough [" + p_o_jsonSchemaElement.getName() + " of " + s_amountProperty + "] json items(" + a_values.length + "), minimum = " + o_jsonRestriction.getIntValue());
								}
							}
							
							if (o_jsonRestriction.getName().toLowerCase().contentEquals("maxitems")) {
								/* check maxItems restriction */
								if (a_values.length > o_jsonRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: too many [" + p_o_jsonSchemaElement.getName() + " of " + s_amountProperty + "] json items(" + a_values.length + "), maximum = " + o_jsonRestriction.getIntValue());
								}
							}
						}
					}
					
					/* iterate all array values */
					for (String s_value : a_values) {
						JSONValueType e_jsonValueType = this.getJSONValueType(s_value);
						
						/* check if JSON value types are matching between schema and data */
						if ( (e_jsonValueType != stringToJSONValueType(p_o_jsonSchemaElement.getType())) && (e_jsonValueType != JSONValueType.Null) ) {
							throw new IllegalArgumentException("JSON schema type[" + stringToJSONValueType(p_o_jsonSchemaElement.getType()) + "] does not match with data value type[" + e_jsonValueType + "] with value[" + s_value + "]");
						}
						
						/* check if json-element has any restrictions */
						if (p_o_jsonSchemaElement.getRestrictions().size() > 0) {
							for (JSONRestriction o_jsonRestriction : p_o_jsonSchemaElement.getRestrictions()) {
								/* execute restriction check */
								this.checkRestriction(s_value, o_jsonRestriction, p_o_jsonSchemaElement);
							}
						}
						
						/* cast array string value into object */
						Object o_returnObject = this.castObjectFromString(s_value, p_o_jsonSchemaElement.getType());
						
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "add value[" + ( (o_returnObject == null) ? "null" : o_returnObject ) + "] to list of " + p_o_jsonDataElement.getName() + ", type[" + ( (o_returnObject == null) ? "null" : o_returnObject.getClass().getTypeName() ) + "]");
					}
				} else if (b_required) { /* if json-element with type array is required, throw exception */
					throw new NullPointerException("'" + p_o_jsonSchemaElement.getName() + "' of '" + s_requiredProperty + "' is required, but array has no values");
				}
			}
		} else {
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": handle value = " + p_o_jsonDataElement.getValue() + " - mapping[" + p_o_jsonSchemaElement.printMapping() + "]");
			
			JSONValueType e_jsonValueType = this.getJSONValueType(p_o_jsonDataElement.getValue());
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": json value type = " + e_jsonValueType);
													
			/* check if JSON value types are matching between schema and data, additional condition = JSON value type is not 'null' */
			if ( (e_jsonValueType != stringToJSONValueType(p_o_jsonSchemaElement.getType())) && (e_jsonValueType != JSONValueType.Null) ) {
				/* it is acceptable if source type is 'integer' and destination type is 'number', valid cast available */
				if (!( (e_jsonValueType == JSONValueType.Integer) && (stringToJSONValueType(p_o_jsonSchemaElement.getType()) == JSONValueType.Number) ) ) {
					throw new IllegalArgumentException("JSON schema type[" + e_jsonValueType + "] does not match with data value type[" + stringToJSONValueType(p_o_jsonSchemaElement.getType()) + "] with value[" + p_o_jsonDataElement.getValue() + "]");
				}
			}
			
			/* check if json-element is required */
			if (p_o_jsonSchemaElement.getRequired()) {
				/* check if value is empty */
				if ( (p_o_jsonDataElement.getValue().contentEquals("")) || (p_o_jsonDataElement.getValue().contentEquals("null")) || (p_o_jsonDataElement.getValue().contentEquals("\"\"")) ) {
					throw new IllegalArgumentException("'" + p_o_jsonSchemaElement.getName() + "' is required, but value[" + p_o_jsonDataElement.getValue() + "] is empty");
				}
			}
			
			/* check if json-element has any restrictions */
			if (p_o_jsonSchemaElement.getRestrictions().size() > 0) {
				for (JSONRestriction o_jsonRestriction : p_o_jsonSchemaElement.getRestrictions()) {
					/* execute restriction check */
					this.checkRestriction(p_o_jsonDataElement.getValue(), o_jsonRestriction, p_o_jsonSchemaElement);
				}
			}
			
			if (!p_o_jsonDataElement.getValue().contentEquals("null")) {
				if ( (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMapping())) && (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMappingClass())) ) {
					@SuppressWarnings("unused")
					Object o_foo = this.castObjectFromString(p_o_jsonDataElement.getValue(), p_o_jsonSchemaElement.getType());
				} else {
					String s_objectValue = p_o_jsonDataElement.getValue();
					
					/* remove surrounded double quotes from value */
					if ( (s_objectValue.startsWith("\"")) && (s_objectValue.endsWith("\"")) ) {
						s_objectValue = s_objectValue.substring(1, s_objectValue.length() - 1);
					}
					
					try {
						@SuppressWarnings("unused")
						Object o_foo = this.castObjectFromString(s_objectValue, p_o_jsonSchemaElement.getType());
					} catch (Exception o_excOne) {
						try {
							@SuppressWarnings("unused")
							Object o_foo = this.castObjectFromString(s_objectValue, p_o_jsonSchemaElement.getMapping());
						} catch (Exception o_excTwo) {
							@SuppressWarnings("unused")
							Object o_foo = this.castObjectFromString(s_objectValue, p_o_jsonSchemaElement.getMappingClass());
						}
					}
				}
			}
		}
		
												de.forestj.lib.Global.ilogFiner(this.printIndentation() + "return " + b_return);
		return b_return;
	}
	
	/* decoding JSON to data with JSON schema */
	
	/**
	 * Decode json file to an java object
	 * 
	 * @param p_s_jsonFile					full-path to json file
	 * @return								json decoded java object
	 * @throws IllegalArgumentException		json file does not exist
	 * @throws java.io.IOException			cannot read json file content
	 * @throws NullPointerException			empty schema, empty json file or root node after parsing json content
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InstantiationException		could not create new object instance returning at the end of the method
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public Object jsonDecode(String p_s_jsonFile) throws NullPointerException, IllegalArgumentException, java.io.IOException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException, InstantiationException, ClassNotFoundException {
		/* check schema field */
		if (this.o_schema == null) {
			throw new NullPointerException("Cannot decode data. Schema is null.");
		}
		
		/* check if file exists */
		if (!File.exists(p_s_jsonFile)) {
			throw new IllegalArgumentException("JSON file[" + p_s_jsonFile + "] does not exist.");
		}
		
		/* open json file */
		File o_file = new File(p_s_jsonFile, false);
		
												de.forestj.lib.Global.ilogFiner("read all lines from json file '" + p_s_jsonFile + "'");
		
		/* decode json file lines */										
		return jsonDecode(o_file.getFileContentAsList());
	}
	
	/**
	 * Decode json content to an java object
	 * 
	 * @param p_a_jsonLines					json lines
	 * @return								json decoded java object
	 * @throws NullPointerException			empty schema, empty json file or root node after parsing json content
	 * @throws IllegalArgumentException		condition failed for decoding json correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InstantiationException		could not create new object instance returning at the end of the method
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	public Object jsonDecode(java.util.List<String> p_a_jsonLines) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException, InstantiationException, ClassNotFoundException {
		Object o_foo = null;
		
		/* check schema field */
		if (this.o_schema == null) {
			throw new NullPointerException("Cannot decode data. Schema is null.");
		}
		
												de.forestj.lib.Global.ilogFiner("read all lines: '" + p_a_jsonLines.size() + "'");
		
		StringBuilder o_stringBuilder = new StringBuilder();
		
		/* read all json schema file lines to one string builder */
		for (String s_line : p_a_jsonLines) {
			o_stringBuilder.append(s_line);
		}
		
		/* read all json-schema file lines and delete all line-wraps and tabs */
		String s_json = o_stringBuilder.toString().replaceAll("[\\r\\n\\t]", "");
		
		/* remove all white spaces, but not between double quotes */
		s_json = this.removeWhiteSpaces(s_json);
		
		/* check if json-schema starts with (curly) brackets */
	    if (( ( (!s_json.startsWith("{")) || (!s_json.endsWith("}")) ) && ( (!s_json.startsWith("[")) || (!s_json.endsWith("]")) ) )) {
    		throw new IllegalArgumentException("JSON-file must start and end with curly bracket '{', '}' or must start and end with bracket '[', ']'");
    	}
	    
	    /* validate json schema */
	    this.validateJSON(s_json);
	    
	   											de.forestj.lib.Global.ilogFinest("validated json content lines");
	    
	    this.o_root = null;
	    this.o_currentElement = null;
	    
	    /* parse json */
	    this.parseJSON(s_json);
	    
											    de.forestj.lib.Global.ilogFinest("parsed json content lines");
											    
												if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("JSON-Schema:" + de.forestj.lib.io.File.NEWLINE + this.o_schema);
											    if (de.forestj.lib.Global.isILevel(de.forestj.lib.Global.MASS)) de.forestj.lib.Global.ilogMass("JSON-Root-Element:" + de.forestj.lib.io.File.NEWLINE + this.o_root);
	    
	    /* decode json recursively */
	    o_foo = this.jsonDecodeRecursive(this.o_root, this.o_schema, o_foo);
		
	    										de.forestj.lib.Global.ilogFinest("decoded json content lines");
	    
		return o_foo;
	}
	
	/**
	 * Recursive method to decode json string to a java object and it's fields
	 * 
	 * @param p_o_jsonDataElement			current json data element
	 * @param p_o_jsonSchemaElement			current json schema element with additional information for decoding
	 * @param p_o_object					destination java object to decode json information
	 * @return								decoded json information as java object
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding json correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InstantiationException		could not create new object instance returning at the end of the method
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	private Object jsonDecodeRecursive(JSONElement p_o_jsonDataElement, JSONElement p_o_jsonSchemaElement, Object p_o_object) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException, InstantiationException, ClassNotFoundException {
		/* if type and mapping class are not set, we need at least a reference to continue */
		if ( (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getType())) && (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMappingClass())) ) {
			if (p_o_jsonSchemaElement.getReference() == null) {
				throw new NullPointerException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no type, no mapping class and no reference");
			} else {
				/* set reference as current schema-element */
				p_o_jsonSchemaElement = p_o_jsonSchemaElement.getReference();
			}
		}
		
		/* check if type is set */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getType())) {
			throw new IllegalArgumentException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no type");
		}
		
		/* check if mapping class is set if schema-element is not 'items' */
		if (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMappingClass())) {
			if (!p_o_jsonSchemaElement.getName().toLowerCase().contentEquals("items")) {
				throw new IllegalArgumentException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no mapping class");
			}
		}
		
		if (p_o_jsonSchemaElement.getType().toLowerCase().contentEquals("object")) {
			/* check if we have any data for new object */
			if (p_o_jsonDataElement.getChildren().size() < 1) {
				/* set current object as null */
				p_o_object = null;
			} else {
				String s_objectType = p_o_jsonSchemaElement.getMappingClass();
				
				/* if object has reference, we create new object instance by mapping of reference */
				if ( (p_o_jsonSchemaElement.getReference() != null) && (p_o_jsonSchemaElement.getReference().getType().toLowerCase().contentEquals("object")) ) {
					s_objectType = p_o_jsonSchemaElement.getReference().getMappingClass();
				}
				
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": create new schema-object instance with mapping[" + p_o_jsonSchemaElement.printMapping() + "] and type[" + s_objectType + "]");
				
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
				
				/* increase level for PrintIndentation */
				this.i_level++;
				
				/* help variable to skip children iteration */
				boolean b_childrenIteration = true;
				
				/* help variable for object mapping within objects */
				String s_objectMapping = p_o_jsonSchemaElement.getMappingClass();
				
				/* if there is additional mapping information, use this for object mapping access */
				if (!de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMapping())) {
					s_objectMapping = p_o_jsonSchemaElement.getMapping();
				}
				
				/* check conditions for handling object */
				if (p_o_jsonSchemaElement.getReference() != null) {
					/* check if reference has any children */
					if (p_o_jsonSchemaElement.getReference().getChildren().size() < 1) {
						/* check if reference has another reference */
						if (p_o_jsonSchemaElement.getReference().getReference() == null) {
							throw new NullPointerException("Reference[" + p_o_jsonSchemaElement.getReference().getName() + "] of schema-element[" + p_o_jsonSchemaElement.getName() + "] has no children and no other reference");
						} else {
							b_childrenIteration = false;
							
							/* check if current element in schema has data element by name, otherwise skip this element */
							if (p_o_jsonSchemaElement.getName().contentEquals(p_o_jsonDataElement.getName())) {
																		de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": get schema-object[" + o_object.getClass().getTypeName() + "] with reference[" + p_o_jsonSchemaElement.getReference().getName() + "]");
	
								/* only create new object if we have one object data */
								if (p_o_jsonDataElement.getChildren().size() != 1) {
									throw new IllegalArgumentException("We have (" + p_o_jsonDataElement.getChildren().size() + ") no data children or more than one for schema-element[" + p_o_jsonSchemaElement.getName() + "]");
								}
								
								/* handle reference with recursion */
								Object o_returnObject = this.jsonDecodeRecursive(p_o_jsonDataElement.getChildren().get(0), p_o_jsonSchemaElement.getReference(), o_object);
								
								/* check if we got a return object of recursion */
								if (o_returnObject == null) {
									/* it is valid to return a null object, anyway keep this exception if you want to uncomment it in the future */
									/* throw new Exception("Schema-element[" + p_o_jsonSchemaElement.getName() + "] returns no object after recursion with data[" + p_o_jsonDataElement.getName() + "]"); */
								}
							}
						}
					} else {
																de.forestj.lib.Global.ilogFiner(this.printIndentation() + "update current schema-element[" + p_o_jsonSchemaElement.getName() + "](" + p_o_jsonSchemaElement.printMapping() + ") with reference[" + p_o_jsonSchemaElement.getReference().getName() + "](" + p_o_jsonSchemaElement.getReference().printMapping() + ")");
						
						/* set reference as current json element */
						p_o_jsonSchemaElement = p_o_jsonSchemaElement.getReference();
					}
				}
				
				/* execute children iteration */
				if (b_childrenIteration) {
															de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": get schema-object with type[" + o_object.getClass().getTypeName() + "] and children[" + p_o_jsonSchemaElement.getChildren().size() + "]");
					
					/* only create new object if we have one child definition for object in json schema */
					if (p_o_jsonSchemaElement.getChildren().size() < 1) {
						throw new IllegalArgumentException("Schema-element[" + p_o_jsonSchemaElement.getName() + "] has no children");	
					}
					
					/* check if new object is required if there is no data */
					if ( (p_o_jsonDataElement.getChildren().size() < 1) && (p_o_jsonSchemaElement.getRequired()) ) {
						throw new IllegalArgumentException("We have no data children for schema-element[" + p_o_jsonSchemaElement.getName() + "] which is required");
					}
					
					/* only iterate if we have object data */
					if (p_o_jsonDataElement.getChildren().size() > 0) {
						/* increase level for PrintIndentation */
						this.i_level++;
						
						/* data pointer */
						int j = 0;
						
						for (int i = 0; i < p_o_jsonSchemaElement.getChildren().size(); i++) {
																	de.forestj.lib.Global.ilogFinest(this.printIndentation() + "compare schema-child-name(" + p_o_jsonSchemaElement.getChildren().get(i).getName() + ") with data-child-name(" + p_o_jsonDataElement.getChildren().get(j).getName() + ")");
							
							/* check if current element in schema has data element by name, otherwise skip this element */
							if (!p_o_jsonSchemaElement.getChildren().get(i).getName().contentEquals(p_o_jsonDataElement.getChildren().get(j).getName())) {
								/* but check if current schema element is required, so we must throw an exception */
								if (p_o_jsonSchemaElement.getChildren().get(i).getRequired()) {
									throw new IllegalArgumentException("We have no data for schema-element[" + p_o_jsonSchemaElement.getChildren().get(i).getName() + "] which is required");
								} else {
									continue;
								}
							}
							
							/* handle new object with recursion */
							Object o_returnObject = this.jsonDecodeRecursive(p_o_jsonDataElement.getChildren().get(j), p_o_jsonSchemaElement.getChildren().get(i), o_object);
							
							/* increase data pointer */
							j++;
							
							/* check if we got a return object of recursion */
							if (o_returnObject == null) {
								/* it is valid to return a null object, anyway keep this exception if you want to uncomment it in the future */
								/* throw new Exception("Schema-element[" + p_o_jsonSchemaElement.getChildren().get(i).getName() + "] returns no object after recursion with data[" + p_o_jsonDataElement.getChildren().get(i).getName() + "]"); */
							}
						}
						
						/* decrease level for PrintIndentation */
						this.i_level--;
					}
					
					if ( (!(p_o_object instanceof java.util.List)) && (p_o_object != null) ) {
																de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": set schema-object[" + o_object.getClass().getTypeName() + "] to current object[" + p_o_object.getClass().getTypeName() + "] with mapping[" + s_objectMapping + "]");
						
						this.setProperty(s_objectMapping, p_o_object, o_object);
					}
				}
				
				/* decrease level for PrintIndentation */
				this.i_level--;
				
				/* set object instance as current object */
				p_o_object = o_object;
			}
		} else if (p_o_jsonSchemaElement.getType().toLowerCase().contentEquals("array")) {
			/* check conditions for handling array */
			if (p_o_jsonSchemaElement.getReference() != null) {
				if (p_o_jsonSchemaElement.getReference().getChildren().size() < 1) {
					throw new IllegalArgumentException("Reference[" + p_o_jsonSchemaElement.getReference().getName() + "] of schema-array[" + p_o_jsonSchemaElement.getName() + "] with p_o_object[" + p_o_object.getClass().getTypeName() + "] must have at least one child");
				}
			} else {
				if (p_o_jsonSchemaElement.getChildren().size() != 1) {
					throw new IllegalArgumentException("Schema-array[" + p_o_jsonSchemaElement.getName() + "] with p_o_object[" + p_o_object.getClass().getTypeName() + "] must have just one child");
				}
				
				if (!p_o_jsonSchemaElement.getChildren().get(0).getName().toLowerCase().contentEquals("items")) {
					throw new IllegalArgumentException("Schema-array[" + p_o_jsonSchemaElement.getName() + "] with p_o_object[" + p_o_object.getClass().getTypeName() + "] must have one child with name[items]");
				}
			}
			
			/* help variables to handle array */
			Object o_objectList = null;
			boolean b_required = false;
			String s_requiredProperty = "";
			java.util.List<JSONRestriction> a_restrictions = new java.util.ArrayList<JSONRestriction>();
			String s_amountProperty = "";
			boolean b_primitiveArray = false;
			String s_primitiveArrayMapping = "";
			
			/* check if current array element is a primitive array */
			if ( (p_o_jsonSchemaElement.getChildren().size() > 0) && (p_o_jsonSchemaElement.getChildren().get(0).getPrimitiveArray()) ) {
				/* create list object for primitive array */
				o_objectList = (Object)(new java.util.ArrayList<Object>());
				/* set flag for primitive array */
				b_primitiveArray = true;
				
				/* check if we have a mapping value for primitive array */
				if ( (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMapping())) && (de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMappingClass())) ) {
					throw new IllegalArgumentException("Schema-primitive-array[" + p_o_jsonSchemaElement.getName() + "] with p_o_object[" + p_o_object.getClass().getTypeName() + "] has no mapping value");
				} else {
					/* store mapping value for later use */
					if (!(de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMapping()))) {
						s_primitiveArrayMapping = p_o_jsonSchemaElement.getMapping();
					} else if (!(de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMappingClass()))) {
						s_primitiveArrayMapping = p_o_jsonSchemaElement.getMappingClass();
					}
				}
			} else {
				/* create or retrieve object list data */
				if (p_o_object == null) { /* create a new object instance of yaml array element */
															de.forestj.lib.Global.ilogFiner(this.printIndentation() + "p_o_object == null, create new array list");
					
					/* create list object */
					o_objectList = (Object)(new java.util.ArrayList<Object>());
				} else { /* we have to retrieve the list object */
															de.forestj.lib.Global.ilogFiner(this.printIndentation() + "p_o_object(" + p_o_object.getClass().getTypeName() + ") != null, get list property " + p_o_jsonSchemaElement.getName());
					
					/* get list property */
					o_objectList = getListProperty(p_o_jsonSchemaElement, p_o_object);
	    		}
			}
			
			/* check if json-element is required */
			if (p_o_jsonSchemaElement.getRequired()) {
				b_required = true;
				s_requiredProperty = p_o_jsonSchemaElement.getName();
			}
			
			/* check minItems and maxItems restrictions and save them for items check afterwards */
			if (p_o_jsonSchemaElement.getRestrictions().size() > 0) {
				for (JSONRestriction o_jsonRestriction : p_o_jsonSchemaElement.getRestrictions()) {
					if ( (o_jsonRestriction.getName().toLowerCase().contentEquals("minitems")) || (o_jsonRestriction.getName().toLowerCase().contentEquals("maxitems"))) {
						a_restrictions.add(o_jsonRestriction);
						s_amountProperty = p_o_jsonSchemaElement.getName();
					}
				}
			}
			
			if (p_o_jsonSchemaElement.getReference() != null) {
				/* set reference as current json element */
				p_o_jsonSchemaElement = p_o_jsonSchemaElement.getReference();
			} else {
				/* set current json element to 'items' child */
				p_o_jsonSchemaElement = p_o_jsonSchemaElement.getChildren().get(0);
				
				/* if 'items' child has a child as well, we take this child as current json element */
				if (p_o_jsonSchemaElement.getChildren().size() == 1) {
					p_o_jsonSchemaElement = p_o_jsonSchemaElement.getChildren().get(0);
				}
			}
			
			if (de.forestj.lib.Helper.isStringEmpty(p_o_jsonDataElement.getValue())) { /* we have multiple minor objects for current array */
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": get schema-array with mapping[" + p_o_jsonSchemaElement.printMapping() + "]");
				
				if (p_o_jsonDataElement.getChildren().size() > 0) { /* if we have objects to the new array */
					/* check minItems and maxItems restrictions */
					if (a_restrictions.size() > 0) {
						for (JSONRestriction o_jsonRestriction : a_restrictions) {
							if (o_jsonRestriction.getName().toLowerCase().contentEquals("minitems")) {
								/* check minItems restriction */
								if (p_o_jsonDataElement.getChildren().size() < o_jsonRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: not enough [" + p_o_jsonSchemaElement.getName() + " of " + s_amountProperty + "] json items(" + p_o_jsonDataElement.getChildren().size() + "), minimum = " + o_jsonRestriction.getIntValue());
								}
							}
							
							if (o_jsonRestriction.getName().toLowerCase().contentEquals("maxitems")) {
								/* check maxItems restriction */
								if (p_o_jsonDataElement.getChildren().size() > o_jsonRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: too many [" + p_o_jsonSchemaElement.getName() + " of " + s_amountProperty + "] json items(" + p_o_jsonDataElement.getChildren().size() + "), maximum = " + o_jsonRestriction.getIntValue());
								}
							}
						}
					}
					
					/* iterate objects in list and encode data to json recursively */
					for (int i = 0; i < p_o_jsonDataElement.getChildren().size(); i++) {
						/* increase level for PrintIndentation */
						this.i_level++;
						
						/* handle array object with recursion */
						Object o_returnObject = this.jsonDecodeRecursive(p_o_jsonDataElement.getChildren().get(i), p_o_jsonSchemaElement, o_objectList);
						
						/* check if we got a return object of recursion */
						if (o_returnObject != null) {
							/* cast current object to list */
							@SuppressWarnings("unchecked")
							java.util.List<Object> o_temp = (java.util.List<Object>)o_objectList;
							
																	de.forestj.lib.Global.ilogFinest(this.printIndentation() + "add return value to list of " + p_o_jsonDataElement.getName() + ", type[" + o_returnObject.getClass().getTypeName() + "]");
							
							/* add return object of recursion to list */
							o_temp.add(o_returnObject);
						}
						
						/* decrease level for PrintIndentation */
						this.i_level--;
					}
				}
			} else { /* array objects must be retrieved out of value property */
														de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": handle array value = " + p_o_jsonDataElement.getValue() + " - mapping[" + p_o_jsonSchemaElement.printMapping() + "]");
				
				/* cast current object to list */
				@SuppressWarnings("unchecked")
				java.util.List<Object> o_temp = (java.util.List<Object>)o_objectList;
				
				/* set array with values if we have any values */
				if ( (!de.forestj.lib.Helper.isStringEmpty(p_o_jsonDataElement.getValue())) && (!p_o_jsonDataElement.getValue().contentEquals("[]")) ) {
					/* remove opening and closing brackets */
					if ( (p_o_jsonDataElement.getValue().startsWith("[")) && (p_o_jsonDataElement.getValue().endsWith("]")) ) {
						p_o_jsonDataElement.setValue(p_o_jsonDataElement.getValue().substring(1, p_o_jsonDataElement.getValue().length() - 1));
					}
					
					/* split array into values, divided by ',' */
					String[] a_values = p_o_jsonDataElement.getValue().split(",");
					
					/* check minItems and maxItems restrictions */
					if (a_restrictions.size() > 0) {
						for (JSONRestriction o_jsonRestriction : a_restrictions) {
							if (o_jsonRestriction.getName().toLowerCase().contentEquals("minitems")) {
								/* check minItems restriction */
								if (a_values.length < o_jsonRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: not enough [" + p_o_jsonSchemaElement.getName() + " of " + s_amountProperty + "] json items(" + a_values.length + "), minimum = " + o_jsonRestriction.getIntValue());
								}
							}
							
							if (o_jsonRestriction.getName().toLowerCase().contentEquals("maxitems")) {
								/* check maxItems restriction */
								if (a_values.length > o_jsonRestriction.getIntValue()) {
									throw new IllegalArgumentException("Restriction error: too many [" + p_o_jsonSchemaElement.getName() + " of " + s_amountProperty + "] json items(" + a_values.length + "), maximum = " + o_jsonRestriction.getIntValue());
								}
							}
						}
					}
					
					/* iterate all array values */
					for (String s_value : a_values) {
						/* trim any whitespace characters from string value */
						s_value = s_value.trim();
						
						/* get json value type of string value */
						JSONValueType e_jsonValueType = this.getJSONValueType(s_value);
						
						/* check if JSON value types are matching between schema and data, if it is not 'null' */
						if ( (e_jsonValueType != stringToJSONValueType(p_o_jsonSchemaElement.getType())) && (e_jsonValueType != JSONValueType.Null) &&  ( (s_value != null) && (!s_value.contentEquals("null")) ) ) {
							throw new IllegalArgumentException("JSON schema type[" + stringToJSONValueType(p_o_jsonSchemaElement.getType()) + "] does not match with data value type[" + e_jsonValueType + "] with value[" + s_value + "]");
						}
						
						/* check if json-element has any restrictions */
						if (p_o_jsonSchemaElement.getRestrictions().size() > 0) {
							for (JSONRestriction o_jsonRestriction : p_o_jsonSchemaElement.getRestrictions()) {
								/* execute restriction check */
								this.checkRestriction(s_value, o_jsonRestriction, p_o_jsonSchemaElement);
							}
						}
						
						/* cast array string value into object */
						Object o_returnObject = this.castObjectFromString(s_value, p_o_jsonSchemaElement.getType());
						
																de.forestj.lib.Global.ilogFinest(this.printIndentation() + "add value[" + ( (o_returnObject == null) ? "null" : o_returnObject ) + "] to list of " + p_o_jsonDataElement.getName() + ", type[" + ( (o_returnObject == null) ? "null" : o_returnObject.getClass().getTypeName() ) + "]");
						
						/* add return object of recursion to list */
						o_temp.add(o_returnObject);
					}
				} else if (b_required) { /* if json-element with type array is required, throw exception */
					throw new NullPointerException("'" + p_o_jsonSchemaElement.getName() + "' of '" + s_requiredProperty + "' is required, but array has no values");
				}
			}
			
			/* if we have a primitive array, we take our object list and convert it to a primitive array */
			if (b_primitiveArray) {
				/* we must have an object to set primitive array property */
				if (p_o_object == null) {
					throw new NullPointerException("JSON schema element[" + p_o_jsonSchemaElement.getName() + "] has not initiated object for primitive array[" + s_primitiveArrayMapping + "]");
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
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": handle value = " + p_o_jsonDataElement.getValue() + " - mapping[" + p_o_jsonSchemaElement.printMapping() + "]");
			
			JSONValueType e_jsonValueType = this.getJSONValueType(p_o_jsonDataElement.getValue());
			
													de.forestj.lib.Global.ilogFiner(this.printIndentation() + p_o_jsonSchemaElement.getName() + ": json value type = " + e_jsonValueType);
													
			/* check if JSON value types are matching between schema and data, additional condition = JSON value type is not 'null' */
			if ( (e_jsonValueType != stringToJSONValueType(p_o_jsonSchemaElement.getType())) && (e_jsonValueType != JSONValueType.Null) ) {
				/* it is acceptable if source type is 'integer' and destination type is 'number', valid cast available */
				if (!( (e_jsonValueType == JSONValueType.Integer) && (stringToJSONValueType(p_o_jsonSchemaElement.getType()) == JSONValueType.Number) ) ) {
					throw new IllegalArgumentException("JSON schema type[" + e_jsonValueType + "] does not match with data value type[" + stringToJSONValueType(p_o_jsonSchemaElement.getType()) + "] with value[" + p_o_jsonDataElement.getValue() + "]");
				}
			}
			
			/* check if json-element is required */
			if (p_o_jsonSchemaElement.getRequired()) {
				/* check if value is empty */
				if ( (p_o_jsonDataElement.getValue().contentEquals("")) || (p_o_jsonDataElement.getValue().contentEquals("null")) || (p_o_jsonDataElement.getValue().contentEquals("\"\"")) ) {
					throw new IllegalArgumentException("'" + p_o_jsonSchemaElement.getName() + "' is required, but value[" + p_o_jsonDataElement.getValue() + "] is empty");
				}
			}
			
			/* check if json-element has any restrictions */
			if (p_o_jsonSchemaElement.getRestrictions().size() > 0) {
				for (JSONRestriction o_jsonRestriction : p_o_jsonSchemaElement.getRestrictions()) {
					/* execute restriction check */
					this.checkRestriction(p_o_jsonDataElement.getValue(), o_jsonRestriction, p_o_jsonSchemaElement);
				}
			}
			
			this.setProperty(p_o_jsonSchemaElement, p_o_object, p_o_jsonDataElement.getValue());
		}
		
												de.forestj.lib.Global.ilogFiner(this.printIndentation() + "return " + ( (p_o_object == null) ? "null" : p_o_object.getClass().getTypeName() ) );
		return p_o_object;
	}
	
	/**
	 * Convert a string value of json type to json type enumeration value
	 * 
	 * @param p_s_jsonValueType			json type string from json element
	 * @return							json type enumeration value
	 * @throws IllegalArgumentException	invalid string type parameter
	 */
	private JSONValueType stringToJSONValueType(String p_s_jsonValueType) throws IllegalArgumentException {
		p_s_jsonValueType = p_s_jsonValueType.toLowerCase();
		
		if (p_s_jsonValueType.contentEquals("string")) {
			return JSONValueType.String;
		} else if (p_s_jsonValueType.contentEquals("number")) {
			return JSONValueType.Number;
		} else if (p_s_jsonValueType.contentEquals("integer")) {
			return JSONValueType.Integer;
		} else if (p_s_jsonValueType.contentEquals("boolean")) {
			return JSONValueType.Boolean;
		} else if (p_s_jsonValueType.contentEquals("array")) {
			return JSONValueType.Array;
		} else if (p_s_jsonValueType.contentEquals("object")) {
			return JSONValueType.Object;
		} else if (p_s_jsonValueType.contentEquals("null")) {
			return JSONValueType.Null;
		} else {
			throw new IllegalArgumentException("Invalid JSON value type with value [" + p_s_jsonValueType + "]");
		}
	}
	
	/**
	 * Get a java.util.List object from object's property, access directly via public field or public methods
	 * 
	 * @param p_o_jsonSchemaElement			json schema element with mapping information	
	 * @param p_o_object					object where to retrieve java.util.List object
	 * @return								java.util.List object
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws NullPointerException			list object is not initialized
	*/
	private Object getListProperty(JSONElement p_o_jsonSchemaElement, Object p_o_object) throws NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, NullPointerException {
		/* retrieve field information out of schema element */
		String s_field = p_o_jsonSchemaElement.getMappingClass();
		
		/* if there is additional mapping information, use this for field property access */
		if (!de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMapping())) {
			s_field = p_o_jsonSchemaElement.getMapping();
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
			throw new NullPointerException("List object from method[" + "get" + s_field + "] not initialised");
		}
		
		/* check if object is of type List */
		if (!(p_o_object instanceof java.util.List)) {
			throw new IllegalArgumentException("Object from method[" + "get" + s_field + "] is not a list object for object: " + p_o_object.getClass().getTypeName());
		}
		
		return p_o_object;
	}
	
	/**
	 * Method to set property field of an object with value of json element from file line and mapping value of json schema element
	 * 
	 * @param p_o_jsonSchemaElement			mapping class and type hint to cast value to object's field from json schema
	 * @param p_o_object					object parameter where json data will be decoded and cast into object fields
	 * @param p_o_objectValue				string value of json element from file line
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private void setProperty(JSONElement p_o_jsonSchemaElement, Object p_o_object, String p_s_objectValue) throws NoSuchMethodException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException {
		/* detect null value */
		if (p_s_objectValue.contentEquals("null")) {
			return;
		} else {
			/* remove surrounded double quotes from value */
			if ( (p_s_objectValue.startsWith("\"")) && (p_s_objectValue.endsWith("\"")) ) {
				p_s_objectValue = p_s_objectValue.substring(1, p_s_objectValue.length() - 1);
			}
		}
		
		/* retrieve field information out of schema element */
		String s_field = p_o_jsonSchemaElement.getMappingClass();
		
		/* if there is additional mapping information, use this for field property access */
		if (!de.forestj.lib.Helper.isStringEmpty(p_o_jsonSchemaElement.getMapping())) {
			s_field = p_o_jsonSchemaElement.getMapping();
		}
		
		/* check if we use property methods with invoke to set object data values */
		if (this.b_usePropertyMethods) {
			java.lang.reflect.Method o_method = null;
			boolean b_methodFound = false;
			
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
				o_method.invoke(p_o_object, this.castObjectFromString(p_s_objectValue, p_o_jsonSchemaElement.getType()));
			} catch (Exception o_exc) {
														de.forestj.lib.Global.ilogFinest(this.printIndentation() + o_exc.getMessage() + " - with type[" + p_o_jsonSchemaElement.getType() + "] - try another cast with type[" + p_o_object.getClass().getDeclaredField(s_field).getType().getTypeName() + "]");
				o_method.invoke(p_o_object, this.castObjectFromString(p_s_objectValue, p_o_object.getClass().getDeclaredField(s_field).getType().getTypeName()));
			}	
		} else {
			/* call field directly to set object data values */
			try {
				p_o_object.getClass().getDeclaredField(s_field).set(p_o_object, this.castObjectFromString(p_s_objectValue, p_o_jsonSchemaElement.getType()));
			} catch (IllegalArgumentException o_exc) {
														de.forestj.lib.Global.ilogFinest(this.printIndentation() + o_exc.getMessage() + " - with type[" + p_o_jsonSchemaElement.getType() + "]  - try another cast with type[" + p_o_object.getClass().getDeclaredField(s_field).getType().getTypeName() + "]");
				p_o_object.getClass().getDeclaredField(s_field).set(p_o_object, this.castObjectFromString(p_s_objectValue, p_o_object.getClass().getDeclaredField(s_field).getType().getTypeName()));
			} catch (Exception o_exc) {
				throw new NoSuchFieldException("Property[" + s_field + "] with type[" + p_o_object.getClass().getDeclaredField(s_field).getType().getTypeName() + "] is not accessible for object: " + p_o_object.getClass().getTypeName());
			}
		}
	}
	
	/**
	 * Method to set property field of an object with simple object value, so no cast will be done
	 * 
	 * @param p_s_mapping					mapping class and type hint to cast value to object's field
	 * @param p_o_object					object parameter where json data will be decoded and cast into object fields
	 * @param p_o_objectValue				object value of json element from file line
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
			
			for (java.lang.reflect.Method o_methodSearch : p_o_object.getClass().getDeclaredMethods()) {
				if (o_methodSearch.getName().contentEquals("set" + p_s_mapping)) {
					o_method = o_methodSearch;
					b_methodFound = true;
				}
			}
			
			if (!b_methodFound) {
				throw new NoSuchMethodException("Method[" + "set" + p_s_mapping + "] does not exist for object: " + p_o_object.getClass().getTypeName());
			}
			
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
	 * Convert a string value from a json element to an object to decode it into an object
	 * 
	 * @param p_s_value						string value of json element from file
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
					/* undo escape double quote with backslash */
					o_foo = p_s_value.replaceAll("\\\\\"", "\"");
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
	 * Enumeration of valid json types
	 */
	public enum JSONValueType {
		String, Number, Integer, Boolean, Array, Object, Null
	}
	
	/**
	 * Encapsulation of a schema's json element
	 */
	public class JSONElement {

		/* Fields */
		
		private String s_name;
		private int i_level;
		private String s_type;
		private String s_description;
		private String s_default;
		private String s_value;
		private String s_mapping;
		private String s_mappingClass;
		private boolean b_required;
		private boolean b_primitiveArray;
		private JSONElement o_reference;
		private java.util.List<JSONElement> a_children = new java.util.ArrayList<JSONElement>();
		private java.util.List<JSONRestriction> a_restrictions = new java.util.ArrayList<JSONRestriction>();
		
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
		
		public JSONElement getReference() {
			return this.o_reference;
		}
		
		public void setReference(JSONElement p_o_value) {
			this.o_reference = p_o_value;
		}
		
		public java.util.List<JSONElement> getChildren() {
			return this.a_children;
		}
		
		public java.util.List<JSONRestriction> getRestrictions() {
			return this.a_restrictions;
		}
		
		/* Methods */
		
		/**
		 * JSONElement constructor with empty string as name and level 0
		 */
		public JSONElement() {
			this("", 0);
		}
		
		/**
		 * JSONElement constructor with parameter name and level 0
		 * 
		 * @param p_s_name		name of json element in schema
		 */
		public JSONElement(String p_s_name) {
			this(p_s_name, 0);
		}
		
		/**
		 * JSONElement constructor
		 * 
		 * @param p_s_name		name of json element in schema
		 * @param p_i_level		level of json element
		 */
		public JSONElement(String p_s_name, int p_i_level) {
			this.setName(p_s_name);
			this.setLevel(p_i_level);
		}
		
		/**
		 * Print i tabs as indentation, i is the level of the json element
		 * 
		 * @return	indentation string
		 */
		private String printIndentation() {
			String s_foo = "";
			
			for (int i = 0; i < this.i_level; i++) {
				s_foo += "\t";
			}
			
			return s_foo;
		}
		
		/**
		 * Returns each field of json element with name and value, separated by a pipe '|'
		 */
		@Override
		public String toString() {
			String s_foo = this.printIndentation() + "JSONElement: ";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				try {
					if (o_field.getName().startsWith("this$")) {
						continue;
					}
					
					if (o_field.get(this) instanceof java.util.List<?>) {
						java.util.List<?> a_objects = (java.util.List<?>)o_field.get(this);
						
						if (a_objects.size() > 0) {
							s_foo += "\n";
							
							for (Object o_object : a_objects) {
								s_foo += o_object.toString() + "\n";
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
		 * Returns mapping string of json element, mapping class or a combination of mapping and mapping class separated by ':'
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
	 * Encapsulation of a schema's json element restrictions
	 */
	public class JSONRestriction {
		
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
		 * JSONRestriction constructor, no name value [= null], level 0, no string value [= null] and no integer value [= 0]
		 */
		public JSONRestriction() {
			this("", 0, "", 0);
		}
		
		/**
		 * JSONRestriction constructor, level 0, no string value [= null] and no integer value [= 0]
		 * 
		 * @param p_s_name			name of json element restriction
		 */
		public JSONRestriction(String p_s_name) {
			this(p_s_name, 0, "", 0);
		}
		
		/**
		 * JSONRestriction constructor, no string value [= null] and no integer value [= 0]
		 * 
		 * @param p_s_name			name of json element restriction
		 * @param p_i_level			level of json element restriction
		 */
		public JSONRestriction(String p_s_name, int p_i_level) {
			this(p_s_name, p_i_level, "", 0);
		}
		
		/**
		 * JSONRestriction constructor, no integer value [= 0]
		 * 
		 * @param p_s_name			name of json element restriction
		 * @param p_i_level			level of json element restriction
		 * @param p_s_strValue		string value of restriction
		 */
		public JSONRestriction(String p_s_name, int p_i_level, String p_s_strValue) {
			this(p_s_name, p_i_level, p_s_strValue, 0);
		}
		
		/**
		 * JSONRestriction constructor
		 * 
		 * @param p_s_name			name of json element restriction
		 * @param p_i_level			level of json element restriction
		 * @param p_s_strValue		string value of restriction
		 * @param p_i_intValue		integer value of restriction
		 */
		public JSONRestriction(String p_s_name, int p_i_level, String p_s_strValue, int p_i_intValue) {
			this.setName(p_s_name);
			this.setLevel(p_i_level);
			this.setStrValue(p_s_strValue);
			this.setIntValue(p_i_intValue);
		}

		/**
		 * Print i tabs as indentation, i is the level of the json element
		 * 
		 * @return	indentation string
		 */
		private String printIndentation() {
			String s_foo = "";
			
			for (int i = 0; i < this.i_level; i++) {
				s_foo += "\t";
			}
			
			return s_foo;
		}
		
		/**
		 * Returns each field of json element restriction with name and value, separated by a pipe '|'
		 */
		@Override
		public String toString() {
			String s_foo = "\n" + this.printIndentation() + "\t" + "JSONRestriction: ";
			
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
