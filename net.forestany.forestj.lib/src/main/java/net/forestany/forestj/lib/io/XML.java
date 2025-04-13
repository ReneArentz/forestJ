package net.forestany.forestj.lib.io;

/**
 * 
 * XML class to encode and decode java objects to xml files with help of a xsd schema file/data.
 * access to object fields can be directly on public fields or with public property methods (getXX setXX) on private fields.
 * NOTE: mostly only primitive types supported for encoding and decoding.
 *
 */
public class XML {

	/* Fields */
	
	private XSDElement o_root;
	private XSDElement o_currentElement;
	private java.util.List<XSDElement> a_elementDefinitons = new java.util.ArrayList<XSDElement>();
	private java.util.List<XSDAttribute> a_attributeDefinitions = new java.util.ArrayList<XSDAttribute>();
	private java.util.List<java.util.Map.Entry<Integer, Integer>> a_temp = new java.util.ArrayList<java.util.Map.Entry<Integer, Integer>>();
	private java.util.List<XSDElement> a_dividedElements = new java.util.ArrayList<XSDElement>();
	private String s_lineBreak;
	private int i_level;
	private boolean b_usePropertyMethods;
	private boolean b_printEmptyString;
	private boolean b_useISO8601UTC;
	private boolean b_ignoreMapping;
	private String s_dateTimeFormat;
	private String s_dateFormat;
	private String s_timeFormat;
	private String s_targetNamespace;
	
	/* Properties */
	
	/**
	 * get root element
	 * 
	 * @return XMLElement
	 */
	public XSDElement getRoot() {
		return this.o_root;
	}
	
	/**
	 * set root element
	 * 
	 * @param p_o_value XMLElement instance
	 */
	public void setRoot(XSDElement p_o_value) {
		this.o_root = p_o_value;
	}
	
	/**
	 * get line break characters
	 * 
	 * @return String
	 */
	public String getLineBreak() {
		return this.s_lineBreak;
	}
	
	/**
	 * set line break characters
	 * 
	 * @param p_s_lineBreak					determine line break characters for reading and writing xml files
	 * @throws IllegalArgumentException		must have at least a length of '1'
	 */
	public void setLineBreak(String p_s_lineBreak) {
		if (p_s_lineBreak.length() < 1) {
			throw new IllegalArgumentException("Line break must have at least a length of 1, but length is '" + p_s_lineBreak.length() + "'");
		}
		
		this.s_lineBreak = p_s_lineBreak;
												net.forestany.forestj.lib.Global.ilogConfig("updated line break to [" + net.forestany.forestj.lib.Helper.bytesToHexString(this.s_lineBreak.getBytes(), true) + "]");
	}
	
	/**
	 * get use property methods flag
	 * 
	 * @return boolean
	 */
	public boolean getUsePropertyMethods() {
		return this.b_usePropertyMethods;
	}
	
	/**
	 * set use property methods flag
	 * convention: get'exactFieldName' and set'exactFieldName' must be used
	 * 
	 * @param p_b_value determine if get and set property methods shall be used handling objects
	 */
	public void setUsePropertyMethods(boolean p_b_value) {
		this.b_usePropertyMethods = p_b_value;
		
												net.forestany.forestj.lib.Global.ilogConfig("updates use property methods to '" + this.b_usePropertyMethods + "'");
	}
	
	/**
	 * get print empty string flag
	 * 
	 * @return boolean
	 */
	public boolean getPrintEmptyString() {
		return this.b_printEmptyString;
	}
	
	/**
	 * set print empty string flag
	 * 
	 * @param p_b_value if a string value is empty for a xml-element, set '&#x200B;' (zero-width space) as it's value
	 */
	public void setPrintEmptyString(boolean p_b_value) {
		this.b_printEmptyString = p_b_value;
		
												net.forestany.forestj.lib.Global.ilogConfig("updates print empty string to '" + this.b_printEmptyString + "'");
	}
	
	/**
	 * get use iso-8601 utc flag
	 * 
	 * @return boolean
	 */
	public boolean getUseISO8601UTC() {
		return this.b_useISO8601UTC;
	}
	
	/**
	 * set use iso-8601 utc flag
	 * 
	 * @param p_b_value determine if you want to use only ISO 8601 UTC timestamps within xml files
	 */
	public void setUseISO8601UTC(boolean p_b_value) {
		this.b_useISO8601UTC = p_b_value;
		
												net.forestany.forestj.lib.Global.ilogConfig("updates use ISO 8601 UTC to '" + this.b_useISO8601UTC + "'");
	}
	
	/**
	 * get ignore mapping flag
	 * 
	 * @return boolean
	 */
	public boolean getIgnoreMapping() {
		return this.b_ignoreMapping;
	}
	
	/**
	 * set ignore mapping flag
	 * 
	 * @param p_b_value determine if you want to ignore required mapping attribute values to xsd-tags
	 */
	public void setIgnoreMapping(boolean p_b_value) {
		this.b_ignoreMapping = p_b_value;
		
												net.forestany.forestj.lib.Global.ilogConfig("updates ignore mapping to '" + this.b_ignoreMapping + "'");
	}
	
	/**
	 * get date time format value
	 * 
	 * @return String
	 */
	public String getDateTimeFormat() {
		return this.s_dateTimeFormat;
	}
	
	/**
	 * set date time format value
	 * 
	 * @param p_s_value determine date time format if not using ISO 8601 UTC
	 */
	public void setDateTimeFormat(String p_s_value) {
		this.s_dateTimeFormat = p_s_value;
		
												net.forestany.forestj.lib.Global.ilogConfig("updates date time format to '" + this.s_dateTimeFormat + "'");
	}
	
	/**
	 * get date format value
	 * 
	 * @return String
	 */
	public String getDateFormat() {
		return this.s_dateFormat;
	}
	
	/**
	 * set date format value
	 * 
	 * @param p_s_value determine date format if not using ISO 8601 UTC
	 */
	public void setDateFormat(String p_s_value) {
		this.s_dateFormat = p_s_value;
		
												net.forestany.forestj.lib.Global.ilogConfig("updates date format to '" + this.s_dateFormat + "'");
	}
	
	/**
	 * get time format value
	 * 
	 * @return String
	 */
	public String getTimeFormat() {
		return this.s_timeFormat;
	}
	
	/**
	 * set time format value
	 * 
	 * @param p_s_value determine time format if not using ISO 8601 UTC
	 */
	public void setTimeFormat(String p_s_value) {
		this.s_timeFormat = p_s_value;
		
												net.forestany.forestj.lib.Global.ilogConfig("updates time format to '" + this.s_timeFormat + "'");
	}
	
	/**
	 * get target namespace, which will be filled by reading a xml-schema with this class
	 * 
	 * @return String
	 */
	public String getTargetNamespace() {
		return this.s_targetNamespace;
	}
	
	/* Methods */
	
	/**
	 * Empty XML constructor
	 */
	public XML() {
		this.setLineBreak(net.forestany.forestj.lib.io.File.NEWLINE);
		this.setUsePropertyMethods(false);
		this.setPrintEmptyString(true);
		this.setUseISO8601UTC(true);
		this.setIgnoreMapping(false);
		this.setDateTimeFormat("dd.MM.yyyy HH:mm:ss");
		this.setDateFormat("dd.MM.yyyy");
		this.setTimeFormat("HH:mm:ss");
		
		this.i_level = 0;
		this.s_targetNamespace = null;
	}
	
	/**
	 * XML constructor, giving a schema xsd element object as schema for encoding and decoding xml data
	 * 
	 * @param p_o_schemaRoot						xsd element object as root schema node
	 * @throws IllegalArgumentException				invalid parameters for constructor
	 */
	public XML(XSDElement p_o_schemaRoot) throws IllegalArgumentException {
		if (p_o_schemaRoot == null) {
			throw new IllegalArgumentException("xsd element parameter for schema is null");
		}
		
		this.setLineBreak(net.forestany.forestj.lib.io.File.NEWLINE);
		this.setUsePropertyMethods(false);
		this.setPrintEmptyString(true);
		this.setUseISO8601UTC(true);
		this.setIgnoreMapping(false);
		this.setDateTimeFormat("dd.MM.yyyy HH:mm:ss");
		this.setDateFormat("dd.MM.yyyy");
		this.setTimeFormat("HH:mm:ss");
		
		this.i_level = 0;
		this.s_targetNamespace = null;
		
		this.setRoot(p_o_schemaRoot);
	}
	
	/**
	 * XML constructor, giving file lines of a schema file as dynamic list for encoding and decoding xml data
	 * 
	 * @param p_a_xsdSchemaLines					file lines of schema as dynamic list
	 * @throws IllegalArgumentException				value/structure within xsd schema file invalid
	 * @throws NullPointerException					xsd schema, root node is null
	 */
	public XML(java.util.List<String> p_a_xsdSchemaLines) throws IllegalArgumentException, NullPointerException {
		/* read all xsd-schema file lines as xsd schema */
		this.setSchema(p_a_xsdSchemaLines);
	}
	
	/**
	 * XML constructor, giving a schema file as orientation for encoding and decoding xml data
	 * 
	 * @param p_s_file								full-path to xsd schema file
	 * @throws IllegalArgumentException				value/structure within xsd schema file invalid
	 * @throws NullPointerException					xsd schema, root node is null
	 * @throws java.io.IOException					cannot access or open xsd file and it's content
	 */
	public XML(String p_s_file) throws IllegalArgumentException, NullPointerException, java.io.IOException {
		/* check if file exists */
		if (!File.exists(p_s_file)) {
			throw new IllegalArgumentException("File[" + p_s_file + "] does not exist.");
		}
		
		/* open xsd-schema file */
		File o_file = new File(p_s_file, false);
		
		/* read all xsd-schema file lines as xsd schema */
		this.setSchema(o_file.getFileContentAsList());
	}
	
	/**
	 * Method to set schema elements, afterwards each xml constructor has read their input
	 */
	private void setSchema(java.util.List<String> p_a_xsdSchemaLines) throws IllegalArgumentException, NullPointerException {
		this.setLineBreak(net.forestany.forestj.lib.io.File.NEWLINE);
		this.setUsePropertyMethods(false);
		this.setPrintEmptyString(true);
		this.setUseISO8601UTC(true);
		this.setIgnoreMapping(false);
		this.setDateTimeFormat("dd.MM.yyyy HH:mm:ss");
		this.setDateFormat("dd.MM.yyyy");
		this.setTimeFormat("HH:mm:ss");
		
		this.i_level = 0;
		this.s_targetNamespace = null;
		
		StringBuilder o_stringBuilder = new StringBuilder();
		
		/* read all xsd schema file lines to one string builder */
		for (String s_line : p_a_xsdSchemaLines) {
			o_stringBuilder.append(s_line);
		}
		
		/* read all xsd-schema file lines and delete all line-wraps and tabs and values only containing white spaces */
		String s_xsd = o_stringBuilder.toString().replaceAll("[\\r\\n\\t]", "").replaceAll(">\\s*<", "><");
		
		/* clean up xsd-schema */
		s_xsd = s_xsd.replaceAll("<\\?(.*?)\\?>", "");
		s_xsd = s_xsd.replaceAll("<!--(.*?)-->", "");
		
		/* look for targetNamespace in schema-tag */
		if (s_xsd.startsWith("<xs:schema")) {
			String s_foo = s_xsd.substring(0, s_xsd.indexOf(">"));
			
			if (s_foo.contains("targetnamespace=\"")) {
				s_foo = s_foo.substring(s_foo.indexOf("targetnamespace=\"") + 17);
				s_foo = s_foo.substring(0, s_foo.indexOf("\""));
				
				this.s_targetNamespace = s_foo;
			} else if (s_foo.contains("targetNamespace=\"")) {
				s_foo = s_foo.substring(s_foo.indexOf("targetNamespace=\"") + 17);
				s_foo = s_foo.substring(0, s_foo.indexOf("\""));
				
				this.s_targetNamespace = s_foo;
			}
		}
		
		s_xsd = s_xsd.replaceAll("<xs:schema(.*?)>", "");
		s_xsd = s_xsd.replaceAll("</xs:schema>", "");
		s_xsd = s_xsd.replaceAll("<xs:annotation>(.*?)</xs:annotation>", "");
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("cleaned up xsd-schema: " + this.s_lineBreak + s_xsd);
		
		/* validate xsd */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("(<[^<>]*?<[^<>]*?>|<[^<>]*?>[^<>]*?>)");
		java.util.regex.Matcher o_matcher = o_regex.matcher(s_xsd);
		
		/* if regex-matcher has match, the xsd-schema is not valid */
	    while (o_matcher.find()) {
	        throw new IllegalArgumentException("Invalid xsd-schema. Please check xsd-schema at \"" + o_matcher.group(0) + "\".");
	    }
		
	    										net.forestany.forestj.lib.Global.ilogConfig("split xsd-schema into xsd-elements");
	    
	    java.util.List<String> a_xsdTags = new java.util.ArrayList<String>();
	    
	    /* add all xsd-schema-tags to a list for parsing */
	    o_regex = java.util.regex.Pattern.compile("<[^<>]*?>");
	    o_matcher = o_regex.matcher(s_xsd);
	    
	    while (o_matcher.find()) {
	        a_xsdTags.add(o_matcher.group(0));
	    }
	    
	    /* check if xsd-schema starts with xs:element */
	    if ( (!a_xsdTags.get(0).toLowerCase().startsWith("<xs:element")) && (!a_xsdTags.get(0).toLowerCase().startsWith("<xs:complextype")) ) {
    		throw new IllegalArgumentException("xsd-schema must start with <xs:element>-tag or with <xs:complexType>-tag.");
    	}
    	
	    /* difference between xsd-schema-tree and xsd-schema-divided-definitions */
    	if (!a_xsdTags.get(0).endsWith("/>")) {
    		parseXSDSchemaTree(a_xsdTags, 0, a_xsdTags.size() - 1, null);
    												net.forestany.forestj.lib.Global.ilogConfig("parsed xsd-schema as xsd-schema-tree");
    	} else {
    		parseXSDSchemaDivided(a_xsdTags, 0, a_xsdTags.size() - 1);
    												net.forestany.forestj.lib.Global.ilogConfig("parsed xsd-schema as xsd-schema-divided-definitions");
    	}
	    
    	/* check if root is null */
	    if (this.o_root == null) {
			throw new NullPointerException("Root node is null");
		}
		
	    /* check if root has any children */
		if (this.o_root.getChildren().size() == 0) {
			throw new NullPointerException("Root node has no children[size=" + this.o_root.getChildren().size() + "]");
		}
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("xsd-schema: " + this.s_lineBreak + this.o_root);
	}
	
	/**
	 * Returns root element of xsd schema as string output and all of its children
	 */
	@Override
	public String toString() {
		String s_foo = "";
		
		s_foo += this.o_root.toString();
		
		return s_foo;
	}
	
	/**
	 * Generate indentation string for xml specification
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
	
	/* parsing XSD schema */
	
	/**
	 * Analyze xsd element value and get a unique value type from it
	 * 
	 * @param p_a_xsdTags					xsd element value as string
	 * @param p_i_line						line number of xsd element
	 * @return								unique xsd value type
	 * @throws IllegalArgumentException		invalid value or xsd value type could not be determined
	 */
	private XSDType getXSDType(java.util.List<String> p_a_xsdTags, int p_i_line) throws IllegalArgumentException {
		XSDType e_xsdTagType = null;
    	
    	/* get xsd type */
		if (p_a_xsdTags.get(p_i_line).contains("<xs:element ")) {
    		e_xsdTagType = XSDType.Element;
    	} else if ( (p_a_xsdTags.get(p_i_line).contains("<xs:complexType")) || (p_a_xsdTags.get(p_i_line).contains("<xs:complextype")) ) {
    		e_xsdTagType = XSDType.ComplexType;
    	} else if (p_a_xsdTags.get(p_i_line).contains("<xs:sequence")) {
    		e_xsdTagType = XSDType.Sequence;
    	} else if (p_a_xsdTags.get(p_i_line).contains("xs:attribute ")) {
    		e_xsdTagType = XSDType.Attribute;
    	} else if (p_a_xsdTags.get(p_i_line).contains("<xs:choice")) {
    		e_xsdTagType = XSDType.Choice;
    	} else if ( (p_a_xsdTags.get(p_i_line).contains("<xs:simpleType")) || (p_a_xsdTags.get(p_i_line).contains("<xs:simpletype")) ) {
    		e_xsdTagType = XSDType.SimpleType;
    	} else if ( (p_a_xsdTags.get(p_i_line).contains("<xs:simpleContent")) || (p_a_xsdTags.get(p_i_line).contains("<xs:simplecontent")) ) {
    		e_xsdTagType = XSDType.SimpleContent;
    	} else if (p_a_xsdTags.get(p_i_line).contains("<xs:restriction")) {
    		e_xsdTagType = XSDType.Restriction;
    	} else if (p_a_xsdTags.get(p_i_line).contains("<xs:extension")) {
    		e_xsdTagType = XSDType.Extension;
    	} else if (
    		( p_a_xsdTags.get(p_i_line).contains("<xs:minExclusive") ) ||
    		( p_a_xsdTags.get(p_i_line).contains("<xs:maxExclusive") ) ||
    		( p_a_xsdTags.get(p_i_line).contains("<xs:minInclusive") ) ||
    		( p_a_xsdTags.get(p_i_line).contains("<xs:maxInclusive") ) ||
    		( p_a_xsdTags.get(p_i_line).contains("<xs:totalDigits") ) ||
    		( p_a_xsdTags.get(p_i_line).contains("<xs:fractionDigits") ) ||
    		( p_a_xsdTags.get(p_i_line).contains("<xs:length") ) ||
    		( p_a_xsdTags.get(p_i_line).contains("<xs:minLength") ) ||
    		( p_a_xsdTags.get(p_i_line).contains("<xs:maxLength") ) ||
    		( p_a_xsdTags.get(p_i_line).contains("<xs:enumeration") ) ||
    		( p_a_xsdTags.get(p_i_line).contains("<xs:whiteSpace") ) ||
    		( p_a_xsdTags.get(p_i_line).contains("<xs:pattern") )
    	) {
    		e_xsdTagType = XSDType.RestrictionItem;
    	} else if (p_a_xsdTags.get(p_i_line).contains("/xs:element>")) {
			e_xsdTagType = XSDType.Element;
    	} else if ( (p_a_xsdTags.get(p_i_line).contains("/xs:complexType>")) || (p_a_xsdTags.get(p_i_line).contains("/xs:complextype>")) ) {
    		e_xsdTagType = XSDType.ComplexType;
    	} else if (p_a_xsdTags.get(p_i_line).contains("/xs:sequence>")) {
    		e_xsdTagType = XSDType.Sequence;
    	} else if (p_a_xsdTags.get(p_i_line).contains("/xs:attribute>")) {
    		e_xsdTagType = XSDType.Attribute;
    	} else if (p_a_xsdTags.get(p_i_line).contains("/xs:choice>")) {
    		e_xsdTagType = XSDType.Choice;
    	} else if ( (p_a_xsdTags.get(p_i_line).contains("/xs:simpleType>")) || (p_a_xsdTags.get(p_i_line).contains("/xs:simpletype>")) ) {
    		e_xsdTagType = XSDType.SimpleType;
    	} else if ( (p_a_xsdTags.get(p_i_line).contains("/xs:simpleContent>")) || (p_a_xsdTags.get(p_i_line).contains("/xs:simplecontent>")) ) {
    		e_xsdTagType = XSDType.SimpleContent;
    	} else if (p_a_xsdTags.get(p_i_line).contains("/xs:restriction>")) {
    		e_xsdTagType = XSDType.Restriction;
    	} else if (p_a_xsdTags.get(p_i_line).contains("/xs:extension>")) {
    		e_xsdTagType = XSDType.Extension;
    	}
    	
    	/* check if we have a valid xsd type */
    	if (e_xsdTagType == null) {
    		throw new IllegalArgumentException("Invalid xsd-tag-type at(" + (p_i_line + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_line) + "\".");
    	}
    	
    	return e_xsdTagType;
	}
	
	/**
	 * Parse xsd content to xsd object structure as schema tree view, based on XSDElement, XSDAttribute and XSDRestriction
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_i_max						pointer where to end line iteration
	 * @param p_e_xsdParentTagType			xsd type of parent xsd element
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 * @throws NullPointerException			value within xsd content missing or min. amount not available
	 */
	private void parseXSDSchemaTree(java.util.List<String> p_a_xsdTags, int p_i_min, int p_i_max, XSDType p_e_xsdParentTagType) throws IllegalArgumentException, NullPointerException {
		XSDType e_xsdTagTypeBefore = null;
		int i_max = p_i_max;
	    boolean b_parsed = false;
	    boolean b_oneLinerBefore = false;
    	
	    /* iterate all elements */
	    for (int i_min = p_i_min; i_min <= i_max; i_min++) {
	    	/* get xsd type */
	    	XSDType e_xsdTagType = getXSDType(p_a_xsdTags, i_min);
	    	
	    	boolean b_simpleType = false;
			boolean b_simpleContent = false;
			
	    	/* we found a xs:element, xs:complexType or xs:simpleType with interlacing */
	    	if (!p_a_xsdTags.get(i_min).endsWith("/>")) {
	    		/* check if we have a simpleType or a simpleContent */
	    		if ( (e_xsdTagType == XSDType.Element) || (e_xsdTagType == XSDType.ComplexType) || (e_xsdTagType == XSDType.SimpleType) ) {
					/* conditions for simpleType */
	    			if ( (e_xsdTagType == XSDType.SimpleType) || (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleType) ) {
						b_simpleType = true;
					}
					
	    			/* conditions for simpleContent */
					if ( (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleContent) || ( (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.ComplexType) && (getXSDType(p_a_xsdTags, i_min + 2) == XSDType.SimpleContent) ) ) {
						b_simpleContent = true;
					}
				}
	    		
	    												net.forestany.forestj.lib.Global.ilogFiner(p_a_xsdTags.get(i_min) + " ... parsed=" + b_parsed + " ... current=" + e_xsdTagType + " ... before=" + e_xsdTagTypeBefore + " ... parent=" + p_e_xsdParentTagType + " ... oneLinerBefore=" + b_oneLinerBefore);
	    		
	    		/* if we have another xs:element with interlacing we may need another recursion, if it was already parsed and its not simpleType and not simpleContent */
	    		if ( (b_parsed) && ( (e_xsdTagType == XSDType.Element) || (( (e_xsdTagTypeBefore != XSDType.Element) || (b_oneLinerBefore) ) && (e_xsdTagType == XSDType.ComplexType)) ) && (!b_simpleType) && (!b_simpleContent) ) {
	    			int i_oldMax = i_max;
	    			int i_tempMin = i_min + 1;
	    			int i_level = 0;
	    			
	    			/* look for end of nested xs:element tag */
	    			while (
		    				( (e_xsdTagType == XSDType.Element) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:element>")) ) || 
		    				( (e_xsdTagType == XSDType.ComplexType) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:complextype>")) ) || 
		    				(i_level != 0)
		    			)
	    			{
	    				if (e_xsdTagType == XSDType.Element) {
	    					/* handle other interlacing in current nested xs:element tag */
		    				if ( (p_a_xsdTags.get(i_tempMin).toLowerCase().startsWith("<xs:element")) && (!p_a_xsdTags.get(i_tempMin).endsWith("/>")) ) {
		    					i_level++;
		    				} else if (p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:element>")) {
		    					i_level--;
		    				}
	    				} else if (e_xsdTagType == XSDType.ComplexType) {
	    					/* handle other interlacing in current nested xs:complexType tag */
		    				if ( (p_a_xsdTags.get(i_tempMin).toLowerCase().startsWith("<xs:complextype")) && (!p_a_xsdTags.get(i_tempMin).endsWith("/>")) ) {
		    					i_level++;
		    				} else if (p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:complextype>")) {
		    					i_level--;
		    				}
	    				}
	    				
	    				if (i_tempMin == i_max) {
	    					/* forbidden state - interlacing is not valid in xsd-schema */
	    					throw new IllegalArgumentException("Invalid nested xsd-tag xs:element at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    				}
	    				
	    				i_tempMin++;
		    		}
	    			
	    			/* save current element to reset it after recursion */
	    			XSDElement o_oldCurrentElement = this.o_currentElement; 
	    			
	    													net.forestany.forestj.lib.Global.ilogFiner("interlacing with parent=" + e_xsdTagType);
											    			net.forestany.forestj.lib.Global.ilogFiner(i_min + " ... " + i_tempMin);
											    			net.forestany.forestj.lib.Global.ilogFiner(p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_tempMin));
	    			
	    			/* parse interlacing recursive */
	    			parseXSDSchemaTree(p_a_xsdTags, i_min, i_tempMin, e_xsdTagType);
	    			
	    			/* reset current element */
    				this.o_currentElement = o_oldCurrentElement;
	    			
	    			i_min = i_tempMin;
	    			i_max = i_oldMax;
	    			
	    			continue;
	    		}
	    		
	    		/* overwrite value for end tag pointer */
	    		int i_nestedMax = -1;
	    		
	    		/* store attributes of complex element type until sequence end tag was found */
	    		if (e_xsdTagType == XSDType.Sequence) {
		    		while (!p_a_xsdTags.get(i_max).toLowerCase().contentEquals("</xs:sequence>")) {
		    			if (p_a_xsdTags.get(i_max).toLowerCase().startsWith("<xs:attribute")) {
												    				net.forestany.forestj.lib.Global.ilogFinest("\t\tAttribute: " + i_max + " to Current Element: " + this.o_currentElement.getName() + " - - - parent: " + p_e_xsdParentTagType);
												    				net.forestany.forestj.lib.Global.ilogFinest("\t\tAttribute: " + p_a_xsdTags.get(i_max) + " to Current Element: " + this.o_currentElement.getName() + " - - - parent: " + p_e_xsdParentTagType);
		    				/* parse xs:attribute */
		    				XSDAttribute o_xsdAttribute = null;
		    				
		    				/* check if we have an attribute with simpleType */
		    				if (getXSDType(p_a_xsdTags, i_max + 1) == XSDType.SimpleType) {
		    					o_xsdAttribute = this.parseXSDAttributeWithSimpleType(p_a_xsdTags, i_max);
		    				} else {
		    					o_xsdAttribute = this.parseXSDAttribute(p_a_xsdTags, i_max);
		    				}
			    			
			    			/* add xs:attribute to current element */
			    			this.o_currentElement.getAttributes().add(o_xsdAttribute);
		    			}
			    			
		    			i_max--;
		    		}
	    		} else if (e_xsdTagType == XSDType.Choice) { /* handle choice */
											    			net.forestany.forestj.lib.Global.ilogFiner("\tChoice: " + i_min + " to Current Element: " + this.o_currentElement.getName());
											    			net.forestany.forestj.lib.Global.ilogFiner("\tChoice: " + p_a_xsdTags.get(i_min) + " to Current Element: " + this.o_currentElement.getName());
					/* parse xs:choice */
		    		XSDElement o_xsdChoice = this.parseXSDChoice(p_a_xsdTags, i_min);
		    		
		    		/* set choice flag for current element */ 
		    		this.o_currentElement.setChoice(true);
		    		
		    		/* set minOccurs for current element */
		    		if (o_xsdChoice.getMinOccurs() != 1) {
		    			this.o_currentElement.setMinOccurs(o_xsdChoice.getMinOccurs());	
		    		}
		    		
		    		/* set maxOccurs for current element */
		    		if (o_xsdChoice.getMaxOccurs() != 1) {
		    			this.o_currentElement.setMaxOccurs(o_xsdChoice.getMaxOccurs());	
		    		}
	    		} else if (b_simpleType) { /* handle simpleType */
											    			net.forestany.forestj.lib.Global.ilogFiner("\tSimpleType: " + i_min + " to Current Element: " + this.o_currentElement.getName());
											    			net.forestany.forestj.lib.Global.ilogFiner("\tSimpleType: " + p_a_xsdTags.get(i_min) + p_a_xsdTags.get(i_min + 1) + " to Current Element: " + this.o_currentElement.getName());
    				
    				/* parse simpleType */
    				i_nestedMax = this.parseXSDSimpleType(p_a_xsdTags, i_min, i_max);
    				
    				/* set xml tag pointer to skip processed simpleType tag */
	    			i_min = i_nestedMax;
	    			
											    			net.forestany.forestj.lib.Global.ilogFiner("after");
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + i_min + " ... " + i_max);
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		} else if (b_simpleContent) {  /* handle simpleContent */
											    			net.forestany.forestj.lib.Global.ilogFiner("\tSimpleContent: " + i_min + " to Current Element: " + this.o_currentElement.getName());
											    			net.forestany.forestj.lib.Global.ilogFiner("\tSimpleContent: " + p_a_xsdTags.get(i_min) + p_a_xsdTags.get(i_min + 1) + p_a_xsdTags.get(i_min + 2) + " to Current Element: " + this.o_currentElement.getName());

    				/* parse simpleContent */
    				i_nestedMax = this.parseXSDSimpleContent(p_a_xsdTags, i_min, i_max);
    				    				
    				/* set xml tag pointer to skip processed simpleContent tag */
	    			i_min = i_nestedMax;
	    			
											    			net.forestany.forestj.lib.Global.ilogFiner("after");
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + i_min + " ... " + i_max);
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		}
	    		
	    		/* set end tag pointer */
	    		int i_endTagPointer = i_max;
	    		
	    		/* overwrite end tag pointer if we had a nested simpleType or simpleContent */
	    		if (i_nestedMax > 0) {
	    			i_endTagPointer = i_nestedMax;
	    		}
	    		
											    		net.forestany.forestj.lib.Global.ilogFiner("endTagPointer");
											    		net.forestany.forestj.lib.Global.ilogFiner("\t\t" + i_min + " ... " + i_endTagPointer);
											    		net.forestany.forestj.lib.Global.ilogFiner("\t\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_endTagPointer));
											    		
											    		net.forestany.forestj.lib.Global.ilogFiner("after");
											    		net.forestany.forestj.lib.Global.ilogFiner("\t" + i_min + " ... " + i_max);
											    		net.forestany.forestj.lib.Global.ilogFiner("\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		
	    		/* if we still have no closing tag, then our xsd schema is invalid */
	    		if (!p_a_xsdTags.get(i_endTagPointer).startsWith("</")) {
	    			throw new IllegalArgumentException("Invalid xsd-tag(" + p_a_xsdTags.get(i_endTagPointer) + ") is not closed in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		}
	    		
	    		/* get xsd end type */
		    	XSDType e_xsdEndTagType = getXSDType(p_a_xsdTags, i_endTagPointer);
	    		
	    		/* xsd type and xsd close type must match */
	    		if (e_xsdTagType != e_xsdEndTagType) {
	    			throw new IllegalArgumentException("Invalid xsd-tag-type(" + p_a_xsdTags.get(i_endTagPointer) + ") for closing in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		} else {
	    			/* if we had a no nested simpleType or simpleContent, decrease xml end tag pointer */
	    			if (i_nestedMax < 0) {
	    				i_max--;
	    			}
	    		}
	    		
	    		b_oneLinerBefore = false;
	    	} else {
	    		b_oneLinerBefore = true;
	    	}
	    	
	    											net.forestany.forestj.lib.Global.ilogFiner(p_a_xsdTags.get(i_min) + " ... parsed=" + b_parsed + " ... current=" + e_xsdTagType + " ... before=" + e_xsdTagTypeBefore + " ... parent=" + p_e_xsdParentTagType + " ... oneLinerBefore=" + b_oneLinerBefore);
	    	
	    	/* if tag is of type element, complex type or attribute if parent type is complex */
	    	if ( ( (e_xsdTagType == XSDType.Element) || (( (e_xsdTagTypeBefore != XSDType.Element) || (b_oneLinerBefore) ) && (e_xsdTagType == XSDType.ComplexType)) || ((p_e_xsdParentTagType == XSDType.ComplexType) && (e_xsdTagType == XSDType.Attribute)) ) && (!b_simpleType) && (!b_simpleContent) ) {
	    		if ( ( (e_xsdTagType == XSDType.Element) || (( (e_xsdTagTypeBefore != XSDType.Element) || (b_oneLinerBefore) ) && (e_xsdTagType == XSDType.ComplexType)) ) ) {
	    			if (!p_a_xsdTags.get(i_min).endsWith("/>")) {
											    				net.forestany.forestj.lib.Global.ilogFiner("\tnew Current Element: " + i_min);
											    				net.forestany.forestj.lib.Global.ilogFiner("\tnew Current Element: " + p_a_xsdTags.get(i_min));
	    				/* parse xs:element */
	    				XSDElement o_xsdElement = this.parseXSDElement(p_a_xsdTags, i_min, true);
	    				
	    				/* if current element is set, add xs:element as child to current element */
	    				if (this.o_currentElement != null) {
	    															net.forestany.forestj.lib.Global.ilogFiner("\t\tto Current Element: " + this.o_currentElement.getName());
	    					this.o_currentElement.getChildren().add(o_xsdElement);
	    				}
	    				
	    				/* set xs:element as current element */
	    				this.o_currentElement = o_xsdElement;
	    				
	    				/* set root if its is null */
	    				if (this.o_root == null) {
	    					this.o_root = this.o_currentElement;
	    				}
	    			} else {
											    				net.forestany.forestj.lib.Global.ilogFiner("\tElement: " + i_min + " to Current Element: " + this.o_currentElement.getName());
											    				net.forestany.forestj.lib.Global.ilogFiner("\tElement: " + p_a_xsdTags.get(i_min) + " to Current Element: " + this.o_currentElement.getName());
	    				/* parse xs:element */
	    				XSDElement o_xsdElement = this.parseXSDElement(p_a_xsdTags, i_min);
	    				
	    				/* library does not support multiple occurrences of the same xml element without a list definition */
    	    			if (o_xsdElement.getMaxOccurs() > 1) {
    	    				throw new IllegalArgumentException("Library does not support multiple occurrences of the same xml element without a list definition in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
    	    			}
	    				
	    				/* add xs:element to current element */
	    				this.o_currentElement.getChildren().add(o_xsdElement);
	    			}
	    		} else if (e_xsdTagType == XSDType.Attribute) {
											    			net.forestany.forestj.lib.Global.ilogFiner("\t\tAttribute: " + i_min + " to Current Element: " + this.o_currentElement.getName() + " - - - parent: " + p_e_xsdParentTagType);
											    			net.forestany.forestj.lib.Global.ilogFiner("\t\tAttribute: " + p_a_xsdTags.get(i_min) + " to Current Element: " + this.o_currentElement.getName() + " - - - parent: " + p_e_xsdParentTagType);
    				/* parse xs:attribute */
	    			XSDAttribute o_xsdAttribute = null;
    				
    				/* check if we have an attribute with simpleType */
    				if (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleType) {
    					o_xsdAttribute = this.parseXSDAttributeWithSimpleType(p_a_xsdTags, i_min);
    				} else {
    					o_xsdAttribute = this.parseXSDAttribute(p_a_xsdTags, i_min);
    				}
	    			
	    			/* add xs:attribute to current element */
	    			this.o_currentElement.getAttributes().add(o_xsdAttribute);
	    		}
	    	} else if ( (p_e_xsdParentTagType != XSDType.ComplexType) && (e_xsdTagType == XSDType.Attribute) ) {
	    		/* if parent type is not complex but we have an attribute tag, it is invalid */
	    		throw new IllegalArgumentException("Invalid xsd-attribute-tag in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    	}
	    	
	    	/* save parent xsd type */
	    											net.forestany.forestj.lib.Global.ilogFiner("\tset tag before: " + e_xsdTagType + " - - - old before: " + e_xsdTagTypeBefore);
    		e_xsdTagTypeBefore = e_xsdTagType;
	    	
	    	b_parsed = true;
	    }
	}
	
	/**
	 * Parse xsd content to xsd object structure as schema with divided definitions, based on XSDElement, XSDAttribute and XSDRestriction
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_i_max						pointer where to end line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 * @throws NullPointerException			value within xsd content missing or min. amount not available
	 */
	private void parseXSDSchemaDivided(java.util.List<String> p_a_xsdTags, int p_i_min, int p_i_max) throws IllegalArgumentException, NullPointerException {
		int i_max = p_i_max;
		
		/* get all element and attribute definitions of xsd schema */
    	p_i_min = parseXSDSchemaDividedDefinitions(p_a_xsdTags, p_i_min);
    	
    	/* iterate all elements */
	    for (int i_min = p_i_min; i_min <= i_max; i_min++) {
	    	/* get xsd type */
	    	XSDType e_xsdTagType = getXSDType(p_a_xsdTags, i_min);
	    	
	    	/* first xsd tag must be of type element or complex type and not close itself */
	    	if (!p_a_xsdTags.get(i_min).endsWith("/>")) {
	    		/* first xsd tag is type xs:element or xs:complexType */
	    		if ( (e_xsdTagType == XSDType.Element) || (e_xsdTagType == XSDType.ComplexType) ) {
	    			/* parse xs:element */
	    			XSDElement o_xsdElement = this.parseXSDElement(p_a_xsdTags, i_min, true);
	    				
	    		    /* add element definition to list */
	    		    this.a_elementDefinitons.add(o_xsdElement);
	    		    
	    		    /* set found element as current element */
    		    	this.o_currentElement = o_xsdElement;
	    		    
	    			int i_oldMax = i_max;
	    			int i_tempMin = i_min + 1;
	    			int i_level = 0;
	    			
	    			/* look for end of nested xs:element tag */
	    			while (
	    				( (e_xsdTagType == XSDType.Element) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:element>")) ) || 
	    				( (e_xsdTagType == XSDType.ComplexType) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:complextype>")) ) || 
	    				(i_level != 0)
	    			)
	    			{
	    				if (e_xsdTagType == XSDType.Element) {
	    					/* handle other interlacing in current nested xs:element tag */
		    				if ( (p_a_xsdTags.get(i_tempMin).toLowerCase().startsWith("<xs:element")) && (!p_a_xsdTags.get(i_tempMin).endsWith("/>")) ) {
		    					i_level++;
		    				} else if (p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:element>")) {
		    					i_level--;
		    				}
	    				} else if (e_xsdTagType == XSDType.ComplexType) {
	    					/* handle other interlacing in current nested xs:complexType tag */
		    				if ( (p_a_xsdTags.get(i_tempMin).toLowerCase().startsWith("<xs:complextype")) && (!p_a_xsdTags.get(i_tempMin).endsWith("/>")) ) {
		    					i_level++;
		    				} else if (p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:complextype>")) {
		    					i_level--;
		    				}
	    				}
	    				
	    				if (i_tempMin == i_max) {
	    					/* forbidden state - interlacing is not valid in xsd-schema */
	    					throw new IllegalArgumentException("Invalid nested xsd-tag xs:element at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    				}
	    				
	    				i_tempMin++;
		    		}
	    			
											    			net.forestany.forestj.lib.Global.ilogFiner("interlacing");
											    			net.forestany.forestj.lib.Global.ilogFiner(i_min + " ... " + i_tempMin);
											    			net.forestany.forestj.lib.Global.ilogFiner(p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_tempMin));
	    			
	    			/* parse found complex xs:element or xs:complexType tag in detail */
	    			parseXSDSchemaDividedElement(p_a_xsdTags, i_min, i_tempMin);
	    			
	    			i_min = i_tempMin;
	    			i_max = i_oldMax;
	    			continue;
	    		}
	    		
	    		/* decrease xml end tag counter until sequence end tag was found */
	    		if (e_xsdTagType == XSDType.Sequence) {
		    		while (!p_a_xsdTags.get(i_max).toLowerCase().contentEquals("</xs:sequence>")) {
	    				i_max--;
		    		}
	    		}
	    		
	    		/* if we still have no closing tag, then our xsd schema is invalid */
	    		if (!p_a_xsdTags.get(i_max).startsWith("</")) {
	    			throw new IllegalArgumentException("Invalid xsd-tag is not closed in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		}
	    		
	    		/* get xsd end type */
		    	XSDType e_xsdEndTagType = getXSDType(p_a_xsdTags, i_max);
	    		
	    		/* xsd type and xsd close type must match */
	    		if (e_xsdTagType != e_xsdEndTagType) {
	    			throw new IllegalArgumentException("Invalid xsd-tag-type for closing in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		} else {
	    			i_max--;
	    		}
	    	} else {
	    		/* other one line xsd-tags are not allowed in divided xsd-schema */
	    		throw new IllegalArgumentException("Invalid xsd-tag in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    	}
	    }
	    
	    this.o_root = this.o_currentElement;
	}
	
	/**
	 * Parse xsd definitions within schema, based on XSDElement, XSDAttribute and XSDRestriction
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 * @throws NullPointerException			value within xsd content missing or min. amount not available
	 */
	private int parseXSDSchemaDividedDefinitions(java.util.List<String> p_a_xsdTags, int p_i_min) throws IllegalArgumentException {
		int i_return_min = p_i_min;
		boolean b_definitionElementsClosed = false;
    	
    	/* iterate all elements */
	    for (int i_min = p_i_min; i_min <= p_a_xsdTags.size() - 1; i_min++) {
	    	/* get xsd type */
	    	XSDType e_xsdTagType = getXSDType(p_a_xsdTags, i_min);
	    	
	    	/* xsd tags must close themselves, otherwise we have no more definitions */
	    	if (p_a_xsdTags.get(i_min).endsWith("/>")) {
	    		if ( (e_xsdTagType == XSDType.Element) && (!b_definitionElementsClosed) ) {
	    													net.forestany.forestj.lib.Global.ilogFiner("add element reference of: " + p_a_xsdTags.get(i_min));
	    			/* parse xs:element */
	    			XSDElement o_xsdElement = this.parseXSDElement(p_a_xsdTags, i_min);
	    			
	    			/* library does not support multiple occurrences of the same xml element without a list definition */
	    			if (o_xsdElement.getMaxOccurs() > 1) {
	    				throw new IllegalArgumentException("Library does not support multiple occurrences of the same xml element without a list definition in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    			}
	    			
	    			/* check if xs:element definition already exists */
	    			if (this.xsdElementDefinitionExist(o_xsdElement.getName())) {
	    				throw new IllegalArgumentException("Invalid xsd-element-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    			}
	    			
	    			/* add element definition to list */
	    		    this.a_elementDefinitons.add(o_xsdElement);
		    	}
	    		
	    		if (e_xsdTagType == XSDType.Attribute) {
	    													net.forestany.forestj.lib.Global.ilogFiner("add attribute reference of: " + p_a_xsdTags.get(i_min));
	    			/* parse xs:attribute */
	    			XSDAttribute o_xsdAttribute = this.parseXSDAttribute(p_a_xsdTags, i_min);
	    			
	    			/* check if xs:attribute definition already exists */
	    			if (this.xsdAttributeDefinitionExist(o_xsdAttribute.getName())) {
	    				throw new IllegalArgumentException("Invalid xsd-attribute-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    			}
	    			
	    		    /* add attribute definition to list */
	    		    this.a_attributeDefinitions.add(o_xsdAttribute);
		    	} else {
		    		if (b_definitionElementsClosed) {
		    			throw new IllegalArgumentException("Invalid xsd-attribute-definition in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
		    		}
		    	}
	    		
	    		/* check if elements definitions are closed and attribute definitions are found */
	    		if ( (e_xsdTagType == XSDType.Attribute) && (!b_definitionElementsClosed) ) {
	    			b_definitionElementsClosed = true;
	    		}
	    	} else if ( (!p_a_xsdTags.get(i_min).endsWith("/>")) && (e_xsdTagType == XSDType.Element) && (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleType) ) { /* handle element definition with simpleType */
														net.forestany.forestj.lib.Global.ilogFiner("add element reference with simpleType of: " + p_a_xsdTags.get(i_min));
				/* parse xs:element with simpleType */
				XSDElement o_xsdElement = this.parseXSDElementWithSimpleType(p_a_xsdTags, i_min);
				
				/* library does not support multiple occurrences of the same xml element without a list definition */
    			if (o_xsdElement.getMaxOccurs() > 1) {
    				throw new IllegalArgumentException("Library does not support multiple occurrences of the same xml element without a list definition in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
    			}
    			
    			/* check if xs:element definition already exists */
    			if (this.xsdElementDefinitionExist(o_xsdElement.getName())) {
    				throw new IllegalArgumentException("Invalid xsd-element-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
    			}
    			
    			/* add element definition to list */
    		    this.a_elementDefinitons.add(o_xsdElement);
				
				/* end tag pointer for nested xs:element */
				int i_tempMax = i_min;
				
				/* find end of element interlacing */
				while (!p_a_xsdTags.get(i_tempMax).toLowerCase().startsWith("</xs:element>")) {
					if (i_tempMax == p_i_min + 100000) {
						/* forbidden state - interlacing is not valid in xsd-schema */
						throw new IllegalArgumentException("Invalid nested xsd-tag xs:restriction at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
					}
					
					i_tempMax++;
				}
				
				/* set new xsd tag pointer to skip xs:element interlacing */
				i_min = i_tempMax;
	    	} else if ( (!p_a_xsdTags.get(i_min).endsWith("/>")) && (e_xsdTagType == XSDType.Attribute) && (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleType) ) { /* handle attribute definition with simpleType */
	    												net.forestany.forestj.lib.Global.ilogFiner("add attribute reference with simpleType of: " + p_a_xsdTags.get(i_min));
    			/* parse xs:attribute with simpleType */
    			XSDAttribute o_xsdAttribute = this.parseXSDAttributeWithSimpleType(p_a_xsdTags, i_min);
    			
    			/* check if xs:attribute definition already exists */
    			if (this.xsdAttributeDefinitionExist(o_xsdAttribute.getName())) {
    				throw new IllegalArgumentException("Invalid xsd-attribute-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
    			}
    			
    		    /* add attribute definition to list */
    		    this.a_attributeDefinitions.add(o_xsdAttribute);
	    		
	    		/* end tag pointer for nested xs:attribute */
	    		int i_tempMax = i_min;
	    		
	    		/* find end of attribute interlacing */
	    		while (!p_a_xsdTags.get(i_tempMax).toLowerCase().startsWith("</xs:attribute>")) {
	    			if (i_tempMax == p_i_min + 100000) {
	    				/* forbidden state - interlacing is not valid in xsd-schema */
	    				throw new IllegalArgumentException("Invalid nested xsd-tag xs:restriction at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    			}
	    			
	    			i_tempMax++;
	    		}
	    		
	    		/* set new xsd tag pointer to skip xs:attribute interlacing */
	    		i_min = i_tempMax;
	    	} else {
	    		/* no more definitions available */
	    		i_return_min = i_min;
	    		break;
	    	}
	    }
	    
	    return i_return_min;
	}
	
	/**
	 * Parse xsd content after divided definitions has been parsed, based on XSDElement, XSDAttribute and XSDRestriction
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_i_max						pointer where to end line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 * @throws NullPointerException			value within xsd content missing or min. amount not available
	 */
	private void parseXSDSchemaDividedElement(java.util.List<String> p_a_xsdTags, int p_i_min, int p_i_max) throws IllegalArgumentException, NullPointerException {
		int i_max = p_i_max;
	    boolean b_simpleContent = false;
	    boolean b_simpleType = false;
	    
		/* iterate all elements */
    	for (int i_min = p_i_min; i_min <= i_max; i_min++) {
    		/* get xsd type */
    		XSDType e_xsdTagType = getXSDType(p_a_xsdTags, i_min);
    		
    		/* check for xs:simpleContent and xs:simpleType */
    		if ( ( (e_xsdTagType == XSDType.ComplexType) && (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleContent) ) || 
    			( (e_xsdTagType == XSDType.Element) && (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.ComplexType) && (getXSDType(p_a_xsdTags, i_min + 2) == XSDType.SimpleContent) ) ) {
    			b_simpleContent = true;
    		}
    		
    		if ( ( (e_xsdTagType == XSDType.Element) && (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleType) ) || (e_xsdTagType == XSDType.SimpleType) ) {
    			b_simpleType = true;
    		}
    		
    		if (!p_a_xsdTags.get(i_min).endsWith("/>")) {
	    		/* first xsd tag is type xs:element or xs:complexType */
	    		if ( ( (e_xsdTagType == XSDType.Element) && (!b_simpleContent) && (!b_simpleType) ) || ( (e_xsdTagType == XSDType.ComplexType) && (!b_simpleContent) ) ) {
	    			i_max--;
	    			continue;
	    		}
	    		
	    		/* overwrite value for end tag pointer */
	    		int i_nestedMax = -1;
	    		
											    		net.forestany.forestj.lib.Global.ilogFiner("before");
											    		net.forestany.forestj.lib.Global.ilogFiner("\t" + i_min + " ... " + i_max);
											    		net.forestany.forestj.lib.Global.ilogFiner("\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		
	    		/* save attributes in complex type until sequence end tag found */
	    		if (e_xsdTagType == XSDType.Sequence) {
	    			while (!p_a_xsdTags.get(i_max).toLowerCase().contentEquals("</xs:sequence>")) {
	    				if (p_a_xsdTags.get(i_max).toLowerCase().startsWith("<xs:attribute")) {
		    				java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("ref=\"([^\"]*)\"");
			    			java.util.regex.Matcher o_matcherRef = o_regex.matcher(p_a_xsdTags.get(i_max));
			    			
			    			o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
			    			java.util.regex.Matcher o_matcherName = o_regex.matcher(p_a_xsdTags.get(i_max));
			    			
			    			o_regex = java.util.regex.Pattern.compile("type=\"([^\"]*)\"");
			    			java.util.regex.Matcher o_matcherType = o_regex.matcher(p_a_xsdTags.get(i_max));
			    			
			    		    if (o_matcherRef.find()) { /* we have an attribute element with reference */
			    		        String s_referenceName = o_matcherRef.group(0).substring(5, o_matcherRef.group(0).length() - 1);
			    		    	
			    		        /* check if we have a duplicate */
			    		        if (this.getXSDAttribute(s_referenceName) != null) {
			    		        	throw new NullPointerException("Invalid xsd-tag xs:attribute (duplicate) at(" + (i_max + 1) + ".-element) \"" + p_a_xsdTags.get(i_max) + "\".");
			    		        }
			    		        
			    		    	if (this.xsdAttributeDefinitionExist(s_referenceName)) {
			    		    												net.forestany.forestj.lib.Global.ilogFinest("add and check attribute reference = " + s_referenceName);
			    		    												
			    		    		XSDAttribute o_xsdAttribute = this.getXSDAttributeDefinition(s_referenceName);
			    		    		
			    		    		/* read name attribute out of xs:attribute tag */
			    		    		if ((o_matcherName.find())) {
			    		    			String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
			    		    			
			    		    			/* overwrite name value from reference, because it is dominant from the usage point */
			    		    			o_xsdAttribute.setName(s_name);
			    		    		}
			    		    												
			    		    		/* add xs:attribute object to current element */
			    		    		this.o_currentElement.getAttributes().add(o_xsdAttribute);
			    		    	} else {
			    		    		throw new IllegalArgumentException("Invalid xsd-tag xs:attribute with unknown reference at(" + (i_max + 1) + ".-element) \"" + p_a_xsdTags.get(i_max) + "\".");
			    		    	}
			    		    } else if ((o_matcherName.find()) && (o_matcherType.find())) { /* we have an attribute element with name and type */
			    		    	/* read name and type attribute values of xs:attribute tag */
			    		    	String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
			    		    	String s_type = o_matcherType.group(0).substring(6, o_matcherType.group(0).length() - 1);
			    		    	
			    		    	/* check if we have a duplicate */
			    		        if (this.getXSDAttribute(s_type) != null) {
			    		        	throw new NullPointerException("Invalid xsd-tag xs:attribute (duplicate) at(" + (i_max + 1) + ".-element) \"" + p_a_xsdTags.get(i_max) + "\".");
			    		        }
			    		        
			    		    	if (this.xsdAttributeDefinitionExist(s_type)) {
			    		    												net.forestany.forestj.lib.Global.ilogFinest("add and check attribute type reference = " + s_type);
			    		    												
			    		    		XSDAttribute o_xsdAttribute = this.getXSDAttributeDefinition(s_type);
			    		    		
		    		    			/* overwrite name value from reference, because it is dominant from the usage point */
		    		    			o_xsdAttribute.setName(s_name);
			    		    												
			    		    		/* add xs:attribute object to current element */
			    		    		this.o_currentElement.getAttributes().add(o_xsdAttribute);
			    		    	} else {
			    		    		throw new IllegalArgumentException("Invalid xsd-tag xs:attribute with unknown type reference at(" + (i_max + 1) + ".-element) \"" + p_a_xsdTags.get(i_max) + "\".");
			    		    	}
			    		    } else {
			    		    	throw new NullPointerException("Invalid xsd-tag xs:attribute without a reference at(" + (i_max + 1) + ".-element) \"" + p_a_xsdTags.get(i_max) + "\".");
			    		    }
	    				}
		    			
		    			i_max--;
		    		}
	    		} else if (e_xsdTagType == XSDType.Choice) { /* identify choice tag for current element */
											    			net.forestany.forestj.lib.Global.ilogFiner("\tChoice: " + i_min + " to Current Element: " + this.o_currentElement.getName());
											    			net.forestany.forestj.lib.Global.ilogFiner("\tChoice: " + p_a_xsdTags.get(i_min) + " to Current Element: " + this.o_currentElement.getName());
		    		/* parse xs:choice */
		    		XSDElement o_xsdChoice = this.parseXSDChoice(p_a_xsdTags, i_min);
		    		
		    		/* set choice flag for current element */ 
		    		this.o_currentElement.setChoice(true);
		    		
		    		/* set minOccurs for current element */
		    		if (o_xsdChoice.getMinOccurs() != 1) {
		    			this.o_currentElement.setMinOccurs(o_xsdChoice.getMinOccurs());	
		    		}
		    		
		    		/* set maxOccurs for current element */
		    		if (o_xsdChoice.getMaxOccurs() != 1) {
		    			this.o_currentElement.setMaxOccurs(o_xsdChoice.getMaxOccurs());	
		    		}
	    		} else if (b_simpleType) { /* handle xs:simpleType */
	    			/* parse xs:simpleType */
	    			i_nestedMax = this.parseXSDSimpleType(p_a_xsdTags, i_min, i_max);
	    			
	    			/* set xml tag pointer to skip processed simpleType tag */
	    			i_min = i_nestedMax;
	    			
	    			b_simpleType = false;
											    			net.forestany.forestj.lib.Global.ilogFiner("after");
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + i_min + " ... " + i_max);
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		} else if (b_simpleContent) { /* handle xs:simpleContent */
	    			/* parse xs:simpleContent */
	    			i_nestedMax = this.parseXSDSimpleContent(p_a_xsdTags, i_min, i_max);
	    			
	    			/* set xml tag pointer to skip processed simpleContent tag */
	    			i_min = i_nestedMax;
	    			
	    			b_simpleContent = false;
											    			net.forestany.forestj.lib.Global.ilogFiner("after");
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + i_min + " ... " + i_max);
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		}
	    		
	    		/* set end tag pointer */
	    		int i_endTagPointer = i_max;
	    		
	    		/* overwrite end tag pointer if we had a nested simpleType or simpleContent */
	    		if (i_nestedMax > 0) {
	    			i_endTagPointer = i_nestedMax;
	    		}
	    		
											    		net.forestany.forestj.lib.Global.ilogFiner("endTagPointer");
											    		net.forestany.forestj.lib.Global.ilogFiner("\t\t" + i_min + " ... " + i_endTagPointer);
											    		net.forestany.forestj.lib.Global.ilogFiner("\t\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_endTagPointer));
											    		
											    		net.forestany.forestj.lib.Global.ilogFiner("after");
											    		net.forestany.forestj.lib.Global.ilogFiner("\t" + i_min + " ... " + i_max);
											    		net.forestany.forestj.lib.Global.ilogFiner("\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		
	    		/* if we still have no closing tag, then our xsd schema is invalid */
	    		if (!p_a_xsdTags.get(i_endTagPointer).startsWith("</")) {
	    			throw new IllegalArgumentException("Invalid xsd-tag(" + p_a_xsdTags.get(i_endTagPointer) + ") is not closed in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		}
	    		
	    		/* get xsd end type */
		    	XSDType e_xsdEndTagType = getXSDType(p_a_xsdTags, i_endTagPointer);
	    		
	    		/* xsd type and xsd close type must match */
	    		if (e_xsdTagType != e_xsdEndTagType) {
	    			throw new IllegalArgumentException("Invalid xsd-tag-type \"" + p_a_xsdTags.get(i_endTagPointer) + "\" for closing in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		} else {
	    			/* if we had a no nested simpleType or simpleContent, decrease xml end tag pointer */
	    			if (i_nestedMax < 0) {
	    				i_max--;
	    			}
	    		}
	    	} else {
											    		net.forestany.forestj.lib.Global.ilogFiner("\tElement: " + i_min + " ... " + i_max);
											    		net.forestany.forestj.lib.Global.ilogFiner("\tElement: " + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		
	    		if ( (e_xsdTagType == XSDType.Element) || (e_xsdTagType == XSDType.ComplexType) ) {
	    			java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("ref=\"([^\"]*)\"");
	    			java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    			
	    			o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
	    			java.util.regex.Matcher o_matcherName = o_regex.matcher(p_a_xsdTags.get(i_min));
	    			
	    			o_regex = java.util.regex.Pattern.compile("type=\"xs:([^\"]*)\"");
	    			java.util.regex.Matcher o_matcherPrimitiveType = o_regex.matcher(p_a_xsdTags.get(i_min));
	    			
	    			o_regex = java.util.regex.Pattern.compile("type=\"([^\"]*)\"");
	    			java.util.regex.Matcher o_matcherType = o_regex.matcher(p_a_xsdTags.get(i_min));

	    			boolean b_nameAttributeFound = o_matcherName.find();
	    			
	    			if (o_matcher.find()) { /* must have a reference attribute */
	    		        String s_referenceName = o_matcher.group(0).substring(5, o_matcher.group(0).length() - 1);
	    		        
	    		        /* check if we have a duplicate */
	    		        if (this.getXSDElement(s_referenceName) != null) {
	    		        	throw new IllegalArgumentException("Invalid xsd-tag xs:element (duplicate) at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		        }
	    		        
	    		    	if (this.xsdElementDefinitionExist(s_referenceName)) {
	    		    												net.forestany.forestj.lib.Global.ilogFiner("add and check element reference = " + s_referenceName);
	    		    		XSDElement o_xsdElement = this.getXSDElementDefinition(s_referenceName);
	    		    		
	    		    		/* read name attribute out of xs:element tag */
	    		    		if (o_matcherName.find()) {
			    				String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
			    				
			    				/* overwrite name value from reference, because it is dominant from the usage point */
			    				o_xsdElement.setName(s_name);
	    		    		}
	    		    		
	    		    		/* read minOccurs attribute out of xs:element tag */
	    	    			o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
	    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    	    			
	    	    		    if (o_matcher.find()) {
	    	    		        o_xsdElement.setMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	    		    }
	    	    		    
	    	    		    /* read maxOccurs attribute out of xs:element tag */
	    	    			o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
	    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    	    			
	    	    		    if (o_matcher.find()) {
	    	    		    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    	    		    		o_xsdElement.setMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	    		    	} else if (o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    	    		    		o_xsdElement.setMaxOccurs(-1);
	    	    		    	}
	    	    		    }
	    		    		
	    	    		    /* library does not support multiple occurrences of the same xml element without a list definition */
	    	    			if ( (!o_xsdElement.getChoice()) && (!this.o_currentElement.getMapping().contains("ArrayList(")) && (o_xsdElement.getMaxOccurs() > 1) ) {
	    	    				throw new IllegalArgumentException("Library does not support multiple occurrences of the same xml element without a list definition in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    	    			}
	    	    		    
	    	    		    /* add xs:element to current element */
	    		    		this.o_currentElement.getChildren().add(o_xsdElement);
	    		    	} else {
	    		    		throw new IllegalArgumentException("Invalid xsd-tag xs:element with unknown reference at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		    	}
	    			} else if ((b_nameAttributeFound) && (o_matcherPrimitiveType.find())) { /* must have a name and primitive type attribute which are equal */
	    				String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
	    				String s_type = o_matcherPrimitiveType.group(0).substring(9, o_matcherPrimitiveType.group(0).length() - 1);
	    				
	    				if (!s_name.contentEquals(s_type)) {
	    					throw new IllegalArgumentException("Invalid xsd-tag xs:element without reference at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\", and name and primitive type are not equal ['" + s_name + "' != '" + s_type + "']");
	    				}
	    				
	    				net.forestany.forestj.lib.Global.ilogFiner("create and read xsd-element as array element definition = " + s_type);
	    				XSDElement o_xsdElement = new XSDElement(s_name, s_type);
	    				
	    				/* set xsd-element as array */
	    				o_xsdElement.setIsArray(true);
	    				
	    				/* read minOccurs attribute out of xs:element tag */
    	    			o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
    	    			
    	    		    if (o_matcher.find()) {
    	    		        o_xsdElement.setMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
    	    		    }
    	    		    
    	    		    /* read maxOccurs attribute out of xs:element tag */
    	    			o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
    	    			
    	    		    if (o_matcher.find()) {
    	    		    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
    	    		    		o_xsdElement.setMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
    	    		    	} else if (o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
    	    		    		o_xsdElement.setMaxOccurs(-1);
    	    		    	}
    	    		    }
	    				
	    				/* add xs:element to current element */
    		    		this.o_currentElement.getChildren().add(o_xsdElement);
	    			} else if ((b_nameAttributeFound) && (o_matcherType.find())) { /* must have a name and type attribute, where type attribute value is used as reference */
	    				/* read name and type attribute value */
	    				String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
	    				String s_typeName = o_matcherType.group(0).substring(6, o_matcherType.group(0).length() - 1);
	    		        
	    		        /* check if we have a duplicate */
	    		        if (this.getXSDElement(s_typeName) != null) {
	    		        	throw new IllegalArgumentException("Invalid xsd-tag xs:element (duplicate) at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		        }
	    		        
	    		    	if (this.xsdElementDefinitionExist(s_typeName)) {
	    		    												net.forestany.forestj.lib.Global.ilogFiner("add and check element reference = " + s_typeName);
	    		    		XSDElement o_xsdElement = this.getXSDElementDefinition(s_typeName);
	    		    		
	    		    		/* overwrite name value from reference, because it is dominant from the usage point */
			    			o_xsdElement.setName(s_name);
	    		    		
	    		    		/* read minOccurs attribute out of xs:element tag */
	    	    			o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
	    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    	    			
	    	    		    if (o_matcher.find()) {
	    	    		        o_xsdElement.setMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	    		    }
	    	    		    
	    	    		    /* read maxOccurs attribute out of xs:element tag */
	    	    			o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
	    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    	    			
	    	    		    if (o_matcher.find()) {
	    	    		    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    	    		    		o_xsdElement.setMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	    		    	} else if (o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    	    		    		o_xsdElement.setMaxOccurs(-1);
	    	    		    	}
	    	    		    }
	    		    		
	    	    		    /* library does not support multiple occurrences of the same xml element without a list definition */
	    	    			if ( (!o_xsdElement.getChoice()) && (!this.o_currentElement.getMapping().contains("ArrayList(")) && (o_xsdElement.getMaxOccurs() > 1) ) {
	    	    				throw new IllegalArgumentException("Library does not support multiple occurrences of the same xml element without a list definition in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    	    			}
	    	    		    
	    	    		    /* add xs:element to current element */
	    		    		this.o_currentElement.getChildren().add(o_xsdElement);
	    		    	} else {
	    		    		throw new IllegalArgumentException("Invalid xsd-tag xs:element with unknown type as reference at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		    	}
	    			} else {
	    				throw new IllegalArgumentException("Invalid xsd-tag xs:element without a reference at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    			}
	    		} else {
	    			throw new IllegalArgumentException("Invalid xsd-tag in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		}
	    	}
	    }
	}
	
	/**
	 * Parse xsd element based on XSDElement
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 */
	private XSDElement parseXSDElement(java.util.List<String> p_a_xsdTags, int p_i_min) throws IllegalArgumentException {
		return parseXSDElement(p_a_xsdTags, p_i_min, false);
	}
	
	/**
	 * Parse xsd content after divided definitions has been parsed, based on XSDElement, XSDAttribute and XSDRestriction
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_b_ignoreType				true - ignore attribute value of xsd element, false - check for valid attribute type of xsd element
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 */
	private XSDElement parseXSDElement(java.util.List<String> p_a_xsdTags, int p_i_min, boolean p_b_ignoreType) throws IllegalArgumentException {
		XSDElement o_xsdElement = null;
		
		/* read name attribute out of xs:element tag */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	String s_name = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
	    	
	    	if (!java.util.regex.Pattern.matches("[a-zA-Z0-9-_]*", s_name)) {
				throw new IllegalArgumentException("Invalid schema element name '" + s_name + "', invalid characters. Following characters are allowed: [a-z], [A-Z], [0-9], [-] and [_]");
			}
	    	
	        o_xsdElement = new XSDElement(s_name);
	    } else {
	    	/* no name attribute found */
	    	throw new IllegalArgumentException("Invalid xsd-tag xs:element without a name at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    }
	    
	    if (!p_b_ignoreType) {
		    /* read type attribute out of xs:element tag */
			o_regex = java.util.regex.Pattern.compile("type=\"xs:([^\"]*)\"");
			o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
			
		    if (o_matcher.find()) {
		        o_xsdElement.setType(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
		    } else {
		    	if (this.b_ignoreMapping) {
		    		/* read type attribute out of xs:element tag as reference */
					o_regex = java.util.regex.Pattern.compile("type=\"([^\"]*)\"");
					o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
					
				    if (o_matcher.find()) {
				    	o_xsdElement.setType(o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1));
				    } else {
				    	/* no type attribute found */
			    		throw new IllegalArgumentException("Invalid xsd-tag xs:element without a type at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
				    }
		    	} else {
		    		/* no type attribute found */
		    		throw new IllegalArgumentException("Invalid xsd-tag xs:element without a type at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
		    	}
		    }
	    }
		
	    /* read mapping attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("mapping=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	        o_xsdElement.setMapping(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	    } else {
	    	if ( o_xsdElement.getName().contentEquals(o_xsdElement.getType()) ) {
	    		java.util.List<String> a_invalidNames = java.util.Arrays.asList("string", "duration", "hexbinary", "base64binary", "anyuri", "normalizedstring", "token", 
						"language", "name", "ncname", "nmtoken", "id", "idref", "entity", "integer", "int", "positiveinteger", "nonpositiveinteger", "negativeinteger", 
						"nonnegativeinteger", "byte", "unsignedint", "unsignedbyte", "boolean", "duration", "date", "time", "datetime", "decimal", "double", "float", "short", "long");
				
				if (!a_invalidNames.contains(o_xsdElement.getName())) {
					if (!this.b_ignoreMapping) { /* it is fine that there is no valid name with type if we want to ignore mapping */
						throw new IllegalArgumentException("Invalid name for xsd-element as array '" + o_xsdElement.getName() + "'");
					}
				} else {
					o_xsdElement.setIsArray(true);
				}
	    	} else {
	    		if (!this.b_ignoreMapping) { /* it is fine that there is no mapping attribute if we want to ignore mapping */
	    			/* no mapping attribute found */
	    			throw new IllegalArgumentException("Invalid xsd-tag xs:element without a mapping at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    		}
	    	}
	    }
	    
	    /* read minOccurs attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	        o_xsdElement.setMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    }
	    
	    /* read maxOccurs attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    		o_xsdElement.setMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	} else if (o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    		o_xsdElement.setMaxOccurs(-1);
	    	}
	    }
	    
	    return o_xsdElement;
	}
	
	/**
	 * Parse xsd element with a simple type within, based on XSDElement
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 */
	private XSDElement parseXSDElementWithSimpleType(java.util.List<String> p_a_xsdTags, int p_i_min) throws IllegalArgumentException {
		XSDElement o_xsdElement = null;
		
		/* read name attribute out of xs:element tag */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	String s_name = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
	    	
	    	if (!java.util.regex.Pattern.matches("[a-zA-Z0-9-_]*", s_name)) {
				throw new IllegalArgumentException("Invalid schema element name '" + s_name + "', invalid characters. Following characters are allowed: [a-z], [A-Z], [0-9], [-] and [_]");
			}
	    	
	        o_xsdElement = new XSDElement(s_name);
	        o_xsdElement.setSimpleType(true);
	    } else {
	    	/* no name attribute found */
	    	throw new IllegalArgumentException("Invalid xsd-tag xs:element without a name at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    }
	    
	    /* read mapping attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("mapping=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	        o_xsdElement.setMapping(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	    } else {
	    	if ( o_xsdElement.getName().contentEquals(o_xsdElement.getType()) ) {
	    		java.util.List<String> a_invalidNames = java.util.Arrays.asList("string", "duration", "hexbinary", "base64binary", "anyuri", "normalizedstring", "token", 
						"language", "name", "ncname", "nmtoken", "id", "idref", "entity", "integer", "int", "positiveinteger", "nonpositiveinteger", "negativeinteger", 
						"nonnegativeinteger", "byte", "unsignedint", "unsignedbyte", "boolean", "duration", "date", "time", "datetime", "decimal", "double", "float", "short", "long");
				
				if (!a_invalidNames.contains(o_xsdElement.getName())) {
					throw new IllegalArgumentException("Invalid name for xsd-element as array '" + o_xsdElement.getName() + "'");
				}
				
	    		o_xsdElement.setIsArray(true);
	    	} else {
	    		if (!this.b_ignoreMapping) { /* it is fine that there is no mapping attribute if we want to ignore mapping */
	    			/* no mapping attribute found */
	    			throw new IllegalArgumentException("Invalid xsd-tag xs:element without a mapping at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    		}
	    	}
	    }
	    
	    /* read minOccurs attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	        o_xsdElement.setMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    }
	    
	    /* read maxOccurs attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    		o_xsdElement.setMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	} else if (o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    		o_xsdElement.setMaxOccurs(-1);
	    	}
	    }
	    
	    /* check if we have a restriction tag */
	    if ( (getXSDType(p_a_xsdTags, p_i_min + 1) != XSDType.Restriction) && (getXSDType(p_a_xsdTags, p_i_min + 2) != XSDType.Restriction) ) {
	    	/* no restriction tag found for attribute with simpleType */
	    	throw new IllegalArgumentException("Invalid xsd-tag xs:attribute with xs:simpleType without a xs:restriction at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    }
	    
	    /* read base attribute out of xs:restriction tag */
		o_regex = java.util.regex.Pattern.compile("base=\"xs:([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min + 2));
		
	    if (o_matcher.find()) {
	    											net.forestany.forestj.lib.Global.ilogFiner("found restriction = \"" + p_a_xsdTags.get(p_i_min + 2) + "\"");
	    	o_xsdElement.setType(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	    	o_xsdElement.setRestriction(true);
	    } else {
	    	/* read base attribute out of xs:restriction tag */
			o_regex = java.util.regex.Pattern.compile("base=\"xs:([^\"]*)\"");
			o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min + 1));
			
		    if (o_matcher.find()) {
		    											net.forestany.forestj.lib.Global.ilogFiner("found restriction = \"" + p_a_xsdTags.get(p_i_min + 1) + "\"");
		    	o_xsdElement.setType(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
		    	o_xsdElement.setRestriction(true);
		    } else {
		    	/* no type attribute found */
		    	throw new IllegalArgumentException("Invalid xsd-tag xs:restriction without a valid base-type at(" + (p_i_min + 2) + ".-element) \"" + p_a_xsdTags.get(p_i_min + 1) + "\".");
		    }
	    }
	    
	    /* start tag pointer for restriction items within xs:simpleType */
	    int i_tempStart = p_i_min + 3;
	    
	    /* end tag pointer for nested xs:simpleType */
		int i_tempMax = p_i_min + 2;
		
		/* check next nested tag within xs:simpleType, if next tag is already a xs:restriction tag(not starting with xs:element), we must decrement temp start and temp max */
		if (getXSDType(p_a_xsdTags, p_i_min + 1) == XSDType.Restriction) {
			if (p_a_xsdTags.get(p_i_min + 1).endsWith("/>")) {
				/* just a restriction as one line, without any restriction items - can return xsd element here */
				return o_xsdElement;
			} else {
				i_tempStart--;
				i_tempMax--;
			}
		}
		
		try {
			/* find end of restriction interlacing */
			while (!p_a_xsdTags.get(i_tempMax).toLowerCase().startsWith("</xs:restriction>")) {
				i_tempMax++;
			}
		} catch (Exception o_exc) {
			/* forbidden state - interlacing is not valid in xsd-schema */
			throw new IllegalArgumentException("Invalid nested xsd-tag xs:restriction at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("iterate simpleType");
												net.forestany.forestj.lib.Global.ilogFiner("\t\t" + (i_tempStart) + " ... " + (i_tempMax - 1));
												net.forestany.forestj.lib.Global.ilogFiner("\t\t" + p_a_xsdTags.get((i_tempStart)) + " ... " + p_a_xsdTags.get((i_tempMax - 1)));
	    
		/* parse content of xs:simpleType */
		for (int i_tempMin = i_tempStart; i_tempMin <= (i_tempMax - 1); i_tempMin++) {
			/* get xsd type */
    		XSDType e_xsdSimpleTypeTagType = getXSDType(p_a_xsdTags, i_tempMin);
    		
			if (e_xsdSimpleTypeTagType == XSDType.RestrictionItem) {
														net.forestany.forestj.lib.Global.ilogFinest("found restriction item = \"" + p_a_xsdTags.get(i_tempMin) + "\"");
    			/* add restriction to xs:simpleType element */
				o_xsdElement.getRestrictions().add(this.parseXSDRestrictionItem(p_a_xsdTags, i_tempMin));
    		}
		}
	    
	    return o_xsdElement;
	}
	
	/**
	 * Parse xsd complex type with a simple content within, based on XSDElement
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 */
	private XSDElement parseXSDComplexTypeWithSimpleContent(java.util.List<String> p_a_xsdTags, int p_i_min) throws IllegalArgumentException {
		/* identify simpleContent tag for complexType element */
												net.forestany.forestj.lib.Global.ilogFiner("found simpleContent: " + p_a_xsdTags.get(p_i_min));
		
		XSDElement o_xsdElement = new XSDElement();
		boolean b_foundName = false;
		boolean b_foundMapping = false;
		
		/* read name attribute out of xs:element tag */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	String s_name = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
	    	
	    	if (!java.util.regex.Pattern.matches("[a-zA-Z0-9-_]*", s_name)) {
				throw new IllegalArgumentException("Invalid schema element name '" + s_name + "', invalid characters. Following characters are allowed: [a-z], [A-Z], [0-9], [-] and [_]");
			}
	    	
	        o_xsdElement.setName(s_name);
	        b_foundName = true;
	    }
	    
	    /* read mapping attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("mapping=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	        o_xsdElement.setMapping(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	        b_foundMapping = true;
	    }
	    
	    /* read minOccurs attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	        o_xsdElement.setMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    }
	    
	    /* read maxOccurs attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    		o_xsdElement.setMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	} else if (o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    		o_xsdElement.setMaxOccurs(-1);
	    	}
	    }
		
		/* start tag pointer for nested xs:simpleContent */
		int i_tempMin = p_i_min;
		
		/* find start of simpleContent complexType interlacing */
		if (!p_a_xsdTags.get(i_tempMin).toLowerCase().startsWith("<xs:complextype")) {
			/* forbidden state - interlacing is not valid in xsd-schema */
			throw new IllegalArgumentException("Invalid nested xsd-tag xs:simpleContent not starting with xs:complexType at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
		}
		
		/* end tag pointer for nested xs:simpleContent */
		int i_tempMax = p_i_min;
		
		/* find end of simpleType interlacing */
		while (!p_a_xsdTags.get(i_tempMax).toLowerCase().startsWith("</xs:complextype")) {
			if (i_tempMax == (p_a_xsdTags.size() - 1)) {
				/* forbidden state - interlacing is not valid in xsd-schema */
				throw new IllegalArgumentException("Invalid nested xsd-tag xs:simpleContent at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
			}
			
			i_tempMax++;
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("iterate simpleContent");
												net.forestany.forestj.lib.Global.ilogFiner("\t\t" + (i_tempMin + 1) + " ... " + (i_tempMax - 1));
												net.forestany.forestj.lib.Global.ilogFiner("\t\t" + p_a_xsdTags.get((i_tempMin + 1)) + " ... " + p_a_xsdTags.get((i_tempMax - 1)));
		
		/* parse content of xs:simpleContent */
		for (++i_tempMin; i_tempMin <= (i_tempMax - 1); i_tempMin++) {
			/* get xsd type */
    		XSDType e_xsdSimpleContentTagType = getXSDType(p_a_xsdTags, i_tempMin);
    		
    		if (e_xsdSimpleContentTagType == XSDType.Extension) {
    													net.forestany.forestj.lib.Global.ilogFinest("found extension = \"" + p_a_xsdTags.get(i_tempMin) + "\"");
    			
    			boolean b_base_processed = false;
    			
    			/* read base attribute out of xs:extension tag as primitive type */
    			o_regex = java.util.regex.Pattern.compile("base=\"xs:([^\"]*)\"");
    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
    			
    		    if (o_matcher.find()) {
    		    	/* check for name attribute of xs:element tag if we read a primitive type for simple content */
    		    	if (!b_foundName) {
    		    		/* no name attribute found */
    			    	throw new IllegalArgumentException("Invalid xsd-tag xs:element without a name at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
    		    	}
    		    	
    		    											net.forestany.forestj.lib.Global.ilogFinest("found primitive type: " + o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
    		    	o_xsdElement.setType(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
    		    	b_base_processed = true;
    		    } else {
	    		    /* read base attribute out of xs:extension tag as reference type */
	    			o_regex = java.util.regex.Pattern.compile("base=\"([^\"]*)\"");
	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
	    			
	    		    if (o_matcher.find()) {
	    		    											net.forestany.forestj.lib.Global.ilogFinest("found reference type: " + o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1));
	    		    	
	    		    	/* found reference value */
	    		    	String s_referenceName = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
	    		    	
	    		    	/* xsd object for reference */
	    		    	XSDElement o_xsdElementReference = null;
	    		    	
	    		    	if (this.xsdElementDefinitionExist(s_referenceName)) { /* check if we have a reference within our element definition list */
	    		    		o_xsdElementReference = this.getXSDElementDefinition(s_referenceName);
	    		    	} else if (this.schemaElementDefinitionExist(s_referenceName)) { /* check if we have a reference within our schema definition list */
	    		    		o_xsdElementReference = this.getSchemaElementDefinition(s_referenceName);
	    		    	}
	    		    	
	    		    	/* check if we found a reference */
	    		        if (o_xsdElementReference != null) {
	    		    												net.forestany.forestj.lib.Global.ilogFinest("add element reference = " + s_referenceName);
	    		    		
	    		    		/* set type value from reference name */
	    		    		o_xsdElement.setType(s_referenceName);
	    		    		
	    		    		/* overwrite name value */
	    		    		if (!b_foundName) {
	    		    			o_xsdElement.setName(o_xsdElementReference.getName());
	    		    		}
	    		    		
	    		    		/* overwrite mapping value */
	    		    		if (!b_foundMapping) {
	    		    			o_xsdElement.setMapping(o_xsdElementReference.getMapping());
	    		    		}
	    		    		
	    		    		/* library does not support multiple occurrences of the same xml element without a list definition */
	    	    			if ( (!o_xsdElement.getChoice()) && (!o_xsdElement.getMapping().contains("ArrayList(")) && (o_xsdElement.getMaxOccurs() > 1) ) {
	    	    				throw new IllegalArgumentException("Library does not support multiple occurrences of the same xml element without a list definition in xsd-schema at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    	    			}
	    	    		} else {
	    	    			/* we do not have reference in definition pool */
	    		    		return null;
	    		    	}
	    		    	
	    		    	b_base_processed = true;
	    		    }
    		    }
    		    
    		    if (!b_base_processed) {
    		    	throw new IllegalArgumentException("Invalid xsd-tag xs:extension without a valid base-type at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
    		    }
    		}
    		
    		if (!p_a_xsdTags.get(i_tempMin).endsWith("/>")) {
    			if ( (e_xsdSimpleContentTagType == XSDType.Attribute) && (getXSDType(p_a_xsdTags, i_tempMin + 1) == XSDType.SimpleType) ) {
    				/* parse xs:attribute with simpleType */
	    			XSDAttribute o_xsdAttribute = this.parseXSDAttributeWithSimpleType(p_a_xsdTags, i_tempMin);
	    			
	    			/* add attribute definition to list */
					this.a_attributeDefinitions.add(o_xsdAttribute);
					
					XSDAttribute o_xsdAttributeReplace = new XSDAttribute();
					o_xsdAttributeReplace.setReference(o_xsdAttribute.getName());
					
					/* add xs:attribute object replacement to current complexType element */
					o_xsdElement.getAttributes().add(o_xsdAttributeReplace);
    			}
    			
    			/* if we still have no closing tag, then our xsd schema is invalid */
	    		if (!p_a_xsdTags.get(i_tempMax).startsWith("</")) {
	    			throw new IllegalArgumentException("Invalid xsd-tag is not closed in xsd-schema at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		}
	    		
	    		/* get xsd end type */
		    	XSDType e_xsdSimpleContentEndTagType = getXSDType(p_a_xsdTags, (i_tempMax - 1));
		    	
	    		/* xsd type and xsd close type must match */
	    		if (e_xsdSimpleContentTagType != e_xsdSimpleContentEndTagType) {
	    			throw new IllegalArgumentException("Invalid xsd-tag-type \"" + p_a_xsdTags.get((i_tempMax - 1)) + "\" for closing in xsd-schema at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		} else {
	    			i_tempMax--;
	    		}
    		} else {
    			if (e_xsdSimpleContentTagType == XSDType.Attribute) {
    														net.forestany.forestj.lib.Global.ilogFinest("found attribute item = \"" + p_a_xsdTags.get(i_tempMin) + "\"");
	    			o_regex = java.util.regex.Pattern.compile("ref=\"([^\"]*)\"");
	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
	    			
	    			o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
	    			java.util.regex.Matcher o_matcherName = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
	    			
	    			o_regex = java.util.regex.Pattern.compile("type=\"([^\"]*)\"");
	    			java.util.regex.Matcher o_matcherType = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
	    			
	    		    if (o_matcher.find()) { /* we have an attribute element with reference */
	    		        String s_referenceName = o_matcher.group(0).substring(5, o_matcher.group(0).length() - 1);
	    		    	
	    		        /* check if we have a duplicate */
	    		        if (this.getXSDAttribute(s_referenceName, o_xsdElement) != null) {
	    		        	throw new IllegalArgumentException("Invalid xsd-tag xs:attribute (duplicate) at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		        }
	    		        
	    		    	if (this.xsdAttributeDefinitionExist(s_referenceName)) {
	    		    												net.forestany.forestj.lib.Global.ilogFinest("add and check attribute reference = " + s_referenceName);
	    		    												
							XSDAttribute o_xsdAttribute = this.getXSDAttributeDefinition(s_referenceName);
	    		    		
	    		    		/* read name attribute out of xs:attribute tag */
	    		    		if ((o_matcherName.find())) {
	    		    			String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
	    		    			
	    		    			/* overwrite name value from reference, because it is dominant from the usage point */
	    		    			o_xsdAttribute.setName(s_name);
	    		    		}
	    		    												
	    		    		/* add xs:attribute to xsd element */
	    		    		o_xsdElement.getAttributes().add(o_xsdAttribute);
	    		    	} else {
	    		    		/* we do not have reference in definition pool */
	    		    		return null;
	    		    	}
    		    	} else if ((o_matcherName.find()) && (o_matcherType.find())) { /* we have an attribute element with name and type */
	    		    	/* read name and type attribute values of xs:attribute tag */
	    		    	String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
	    		    	String s_type = o_matcherType.group(0).substring(6, o_matcherType.group(0).length() - 1);
	    		    	
	    		    	/* check if we have a duplicate */
	    		        if (this.getXSDAttribute(s_type, o_xsdElement) != null) {
	    		        	throw new NullPointerException("Invalid xsd-tag xs:attribute (duplicate) at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		        }
	    		        
	    		        /* xsd object for reference */
	    		    	XSDElement o_xsdElementReference = null;
	    		    	
	    		    	if (this.xsdElementDefinitionExist(s_type)) { /* check if we have a reference within our element definition list */
	    		    		o_xsdElementReference = this.getXSDElementDefinition(s_type);
	    		    	} else if (this.schemaElementDefinitionExist(s_type)) { /* check if we have a reference within our schema definition list */
	    		    		o_xsdElementReference = this.getSchemaElementDefinition(s_type);
	    		    	}
	    		    	
	    		    	/* check if we found an attribute reference */
	    		    	if (this.xsdAttributeDefinitionExist(s_type)) {
	    		    												net.forestany.forestj.lib.Global.ilogFinest("add and check attribute type reference = " + s_type);
	    		    												
	    		    		XSDAttribute o_xsdAttribute = this.getXSDAttributeDefinition(s_type);
	    		    		
    		    			/* overwrite name value from reference, because it is dominant from the usage point */
    		    			o_xsdAttribute.setName(s_name);
	    		    												
	    		    		/* add xs:attribute object to xsd element */
    		    			o_xsdElement.getAttributes().add(o_xsdAttribute);
	    		    	} else if (o_xsdElementReference != null) { /* check if we found another reference */
																	net.forestany.forestj.lib.Global.ilogFinest("add element reference = " + s_type);
																	
	    		    		XSDAttribute o_xsdAttribute = new XSDAttribute(s_name);
	    		    		
	    		    		/* read required attribute out of xs:attribute tag */
	    		    		o_regex = java.util.regex.Pattern.compile("use=\"required\"");
	    		    		o_matcher = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
	    		    		
	    		    	    if (o_matcher.find()) {
	    		    	    	o_xsdAttribute.setRequired(true);
	    		    	    }
	    		    	    
	    		    	    /* read default attribute out of xs:attribute tag */
	    		    		o_regex = java.util.regex.Pattern.compile("default=\"([^\"]*)\"");
	    		    		o_matcher = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
	    		    		
	    		    	    if (o_matcher.find()) {
	    		    	    	o_xsdAttribute.setDefault(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	    		    	    }
	    		    	    
	    		    	    /* read fixed attribute out of xs:attribute tag */
	    		    		o_regex = java.util.regex.Pattern.compile("fixed=\"([^\"]*)\"");
	    		    		o_matcher = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
	    		    		
	    		    	    if (o_matcher.find()) {
	    		    	    	o_xsdAttribute.setFixed(o_matcher.group(0).substring(7, o_matcher.group(0).length() - 1));
	    		    	    }
	    		    		
	    		    	    /* set type value from reference name */
    		    			o_xsdAttribute.setType(o_xsdElementReference.getName());
    		    			
    		    			/* assume restrictions from reference */
    		    			for (XSDRestriction o_restriction : o_xsdElementReference.getRestrictions()) {
    		    				o_xsdAttribute.getRestrictions().add(o_restriction);
    		    			}
	    		    												
	    		    		/* add xs:attribute object to xsd element */
    		    			o_xsdElement.getAttributes().add(o_xsdAttribute);
	    		    	} else {
	    		    		/* we do not have reference in definition pool */
	    		    		return null;
	    		    	}
		    		} else {
	    		    	/* parse xs:attribute */
		    			XSDAttribute o_xsdAttribute = this.parseXSDAttribute(p_a_xsdTags, i_tempMin);
		    			
		    			/* add attribute definition to list */
						this.a_attributeDefinitions.add(o_xsdAttribute);
						
						XSDAttribute o_xsdAttributeReplace = new XSDAttribute();
						o_xsdAttributeReplace.setReference(o_xsdAttribute.getName());
						
						/* add xs:attribute object replacement to current complexType element */
						o_xsdElement.getAttributes().add(o_xsdAttributeReplace);
	    		    }
	    		}
    		}
		}
		
		o_xsdElement.setSimpleContent(true);
		
		return o_xsdElement;
	}
		
	/**
	 * Parse xsd attribute based on XSDAttribute
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 */
	private XSDAttribute parseXSDAttribute(java.util.List<String> p_a_xsdTags, int p_i_min) throws IllegalArgumentException {
		XSDAttribute o_xsdAttribute = null;
		
		/* read name attribute out of xs:attribute tag */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
    		String s_name = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
	    	
	    	if (!java.util.regex.Pattern.matches("[a-zA-Z0-9-_]*", s_name)) {
				throw new IllegalArgumentException("Invalid schema attribute name '" + s_name + "', invalid characters. Following characters are allowed: [a-z], [A-Z], [0-9], [-] and [_]");
			}
	    	
	    	o_xsdAttribute = new XSDAttribute(s_name);
	    } else {
	    	/* no name attribute found */
	    	throw new IllegalArgumentException("Invalid xsd-tag xs:attribute without a name at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    }
	    
	    /* read type attribute out of xs:attribute tag */
		o_regex = java.util.regex.Pattern.compile("type=\"xs:([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	o_xsdAttribute.setType(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	    } else {
	    	/* no type attribute found */
	    	throw new IllegalArgumentException("Invalid xsd-tag xs:attribute without a type at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    }
	    
	    /* read mapping attribute out of xs:attribute tag */
		o_regex = java.util.regex.Pattern.compile("mapping=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	o_xsdAttribute.setMapping(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	    } else {
	    	if (!this.b_ignoreMapping) { /* it is fine that there is no mapping attribute if we want to ignore mapping */
	    		/* no mapping attribute found */
	    		throw new IllegalArgumentException("Invalid xsd-tag xs:attribute without a mapping at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    	}
		}
	    
	    /* read required attribute out of xs:attribute tag */
		o_regex = java.util.regex.Pattern.compile("use=\"required\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	o_xsdAttribute.setRequired(true);
	    }
	    
	    /* read default attribute out of xs:attribute tag */
		o_regex = java.util.regex.Pattern.compile("default=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	o_xsdAttribute.setDefault(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	    }
	    
	    /* read fixed attribute out of xs:attribute tag */
		o_regex = java.util.regex.Pattern.compile("fixed=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	o_xsdAttribute.setFixed(o_matcher.group(0).substring(7, o_matcher.group(0).length() - 1));
	    }
	    
	    return o_xsdAttribute;
	}
	
	/**
	 * Parse xsd attribute with a simple type within, based on XSDAttribute
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 */
	private XSDAttribute parseXSDAttributeWithSimpleType(java.util.List<String> p_a_xsdTags, int p_i_min) throws IllegalArgumentException {
		XSDAttribute o_xsdAttribute = null;
		
		/* read name attribute out of xs:attribute tag */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	String s_name = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
	    	
	    	if (!java.util.regex.Pattern.matches("[a-zA-Z0-9-_]*", s_name)) {
				throw new IllegalArgumentException("Invalid schema attribute name '" + s_name + "', invalid characters. Following characters are allowed: [a-z], [A-Z], [0-9], [-] and [_]");
			}
	    	
	    	o_xsdAttribute = new XSDAttribute(s_name);
	    	o_xsdAttribute.setSimpleType(true);
	    } else {
	    	/* no name attribute found */
	    	throw new IllegalArgumentException("Invalid xsd-tag xs:attribute without a name at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    }
	    
	    /* read mapping attribute out of xs:attribute tag */
		o_regex = java.util.regex.Pattern.compile("mapping=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	o_xsdAttribute.setMapping(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	    } else {
	    	if (!this.b_ignoreMapping) { /* it is fine that there is no mapping attribute if we want to ignore mapping */
	    		/* no mapping attribute found */
	    		throw new IllegalArgumentException("Invalid xsd-tag xs:attribute without a mapping at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    	}
	    }
	    
	    /* read required attribute out of xs:attribute tag */
		o_regex = java.util.regex.Pattern.compile("use=\"required\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	o_xsdAttribute.setRequired(true);
	    }
	    
	    /* read default attribute out of xs:attribute tag */
		o_regex = java.util.regex.Pattern.compile("default=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	o_xsdAttribute.setDefault(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	    }
	    
	    /* read fixed attribute out of xs:attribute tag */
		o_regex = java.util.regex.Pattern.compile("fixed=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	o_xsdAttribute.setFixed(o_matcher.group(0).substring(7, o_matcher.group(0).length() - 1));
	    }
	    
	    /* check if we have a restriction tag */
	    if (getXSDType(p_a_xsdTags, p_i_min + 2) != XSDType.Restriction) {
	    	/* no restriction tag found for attribute with simpleType */
	    	throw new IllegalArgumentException("Invalid xsd-tag xs:attribute with xs:simpleType without a xs:restriction at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    }
	    
	    										net.forestany.forestj.lib.Global.ilogFiner("found restriction = \"" + p_a_xsdTags.get(p_i_min + 2) + "\"");
		/* read base attribute out of xs:restriction tag */
		o_regex = java.util.regex.Pattern.compile("base=\"xs:([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min + 2));
		
	    if (o_matcher.find()) {
	    	o_xsdAttribute.setType(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	    	o_xsdAttribute.setRestriction(true);
	    } else {
	    	/* no type attribute found */
	    	throw new IllegalArgumentException("Invalid xsd-tag xs:restriction without a valid base-type at(" + (p_i_min + 3) + ".-element) \"" + p_a_xsdTags.get(p_i_min + 2) + "\".");
	    }
	    
	    /* end tag pointer for nested xs:simpleType */
		int i_tempMax = p_i_min + 2;
		
		try {
			/* find end of restriction interlacing */
			while (!p_a_xsdTags.get(i_tempMax).toLowerCase().startsWith("</xs:restriction>")) {
				if (p_a_xsdTags.get(i_tempMax).contentEquals("</xs:attribute>")) {
					/* reached end of our attribte */
					break;
				}
				
				i_tempMax++;
			}
		} catch (Exception o_exc) {
			/* forbidden state - interlacing is not valid in xsd-schema */
			throw new IllegalArgumentException("Invalid nested xsd-tag xs:restriction at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("iterate simpleType");
												net.forestany.forestj.lib.Global.ilogFiner("\t\t" + (p_i_min + 2) + " ... " + (i_tempMax - 1));
												net.forestany.forestj.lib.Global.ilogFiner("\t\t" + p_a_xsdTags.get((p_i_min + 2)) + " ... " + p_a_xsdTags.get((i_tempMax - 1)));
	    
		/* parse content of xs:simpleType */
		for (int i_tempMin = p_i_min + 2; i_tempMin <= (i_tempMax - 1); i_tempMin++) {
			/* get xsd type */
    		XSDType e_xsdSimpleTypeTagType = getXSDType(p_a_xsdTags, i_tempMin);
    		
			if (e_xsdSimpleTypeTagType == XSDType.RestrictionItem) {
														net.forestany.forestj.lib.Global.ilogFinest("found restriction item = \"" + p_a_xsdTags.get(i_tempMin) + "\"");
    			/* add restriction to xs:simpleType element */
				o_xsdAttribute.getRestrictions().add(this.parseXSDRestrictionItem(p_a_xsdTags, i_tempMin));
    		}
		}
		
	    return o_xsdAttribute;
	}
	
	/**
	 * Parse xsd choice based on XSDElement
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 */
	private XSDElement parseXSDChoice(java.util.List<String> p_a_xsdTags, int p_i_min) throws IllegalArgumentException {
		XSDElement o_xsdElement = new XSDElement();
		
		/* read minOccurs attribute out of xs:element tag */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	        o_xsdElement.setMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    }
	    
	    /* read maxOccurs attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    		o_xsdElement.setMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	}
	    }
	    
	    return o_xsdElement;
	}
	
	/**
	 * Parse xsd restriction based on XSDRestriction
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 */
	private XSDRestriction parseXSDRestrictionItem(java.util.List<String> p_a_xsdTags, int p_i_min) throws IllegalArgumentException {
		XSDRestriction o_xsdRestriction = null;
		
		/* read name attribute out of xs:attribute tag */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("xs:([^<>\\s]*)");
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	o_xsdRestriction = new XSDRestriction(o_matcher.group(0).substring(3));
	    } else {
	    	/* no restriction name found */
	    	throw new IllegalArgumentException("Invalid xsd-tag xs:restriction without a valid restriction name at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    }
	    
	    /* read type attribute out of xs:attribute tag */
		o_regex = java.util.regex.Pattern.compile("value=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	String s_value = o_matcher.group(0).substring(7, o_matcher.group(0).length() - 1);
	    	
	    	if (net.forestany.forestj.lib.Helper.isInteger(s_value)) {
	    		o_xsdRestriction.setIntValue(Integer.parseInt(s_value));
	    	} else {
	    		o_xsdRestriction.setStrValue(s_value);
	    	}
	    } else {
	    	/* no type attribute found */
	    	throw new IllegalArgumentException("Invalid xsd-tag xs:restriction without a value at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    }
	    
	    return o_xsdRestriction;
	}
	
	/**
	 * Parse xsd simple type based on XSDElement
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_i_max						pointer where to end line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 */
	private int parseXSDSimpleType(java.util.List<String> p_a_xsdTags, int p_i_min, int p_i_max) throws IllegalArgumentException {
		/* return xml tag pointer value */
		int i_nestedMax = -1;
		
		/* identify simpleType tag for current element */
												net.forestany.forestj.lib.Global.ilogFiner("found simpleType: " + p_a_xsdTags.get(p_i_min) + " to Current Element: " + this.o_currentElement.getName());
		XSDElement o_xsdElement = this.parseXSDElement(p_a_xsdTags, p_i_min, true);
		o_xsdElement.setRestriction(true);
		
		/* start tag pointer for nested xs:simpleType */
		int i_tempMin = p_i_min;
		
		boolean b_simpleTypeFirst = true;
		
		/* find start of simpleType interlacing */
		while (!p_a_xsdTags.get(i_tempMin).toLowerCase().startsWith("<xs:simpletype")) {
			if (i_tempMin == p_i_max) {
				/* forbidden state - interlacing is not valid in xsd-schema */
				throw new IllegalArgumentException("Invalid nested xsd-tag xs:simpleType at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
			}
			
			b_simpleTypeFirst = false;
			i_tempMin++;
		}
		
		/* end tag pointer for nested xs:simpleType */
		int i_tempMax = p_i_min;
		
		/* find end of simpleType interlacing */
		while (!p_a_xsdTags.get(i_tempMax).toLowerCase().startsWith("</xs:simpletype")) {
			if (i_tempMax == p_i_max) {
				/* forbidden state - interlacing is not valid in xsd-schema */
				throw new IllegalArgumentException("Invalid nested xsd-tag xs:simpleType at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
			}
			
			i_tempMax++;
		}
		
		/* set overwrite value for end tag pointer */
		i_nestedMax = i_tempMax;
		
		if (!b_simpleTypeFirst) {
			i_nestedMax++;
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("iterate simpleType");
												net.forestany.forestj.lib.Global.ilogFiner("\t\t" + (i_tempMin + 1) + " ... " + (i_tempMax - 1));
												net.forestany.forestj.lib.Global.ilogFiner("\t\t" + p_a_xsdTags.get((i_tempMin + 1)) + " ... " + p_a_xsdTags.get((i_tempMax - 1)));
		
		/* parse content of xs:simpleType */
		for (++i_tempMin; i_tempMin < (i_tempMax - 1); i_tempMin++) {
			/* get xsd type */
    		XSDType e_xsdSimpleTypeTagType = getXSDType(p_a_xsdTags, i_tempMin);
    		
    		if (e_xsdSimpleTypeTagType == XSDType.Restriction) {
    													net.forestany.forestj.lib.Global.ilogFiner("found restriction = \"" + p_a_xsdTags.get(i_tempMin) + "\"");
    			/* read base attribute out of xs:restriction tag */
    			java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("base=\"xs:([^\"]*)\"");
    			java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
    			
    		    if (o_matcher.find()) {
    		        o_xsdElement.setType(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
    		    } else {
    		    	/* no type attribute found */
    		    	throw new IllegalArgumentException("Invalid xsd-tag xs:restriction without a valid base-type at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
    		    }
    		}
    		
    		if (!p_a_xsdTags.get(i_tempMin).endsWith("/>")) {
    			/* if we still have no closing tag, then our xsd schema is invalid */
	    		if (!p_a_xsdTags.get(i_tempMax).startsWith("</")) {
	    			throw new IllegalArgumentException("Invalid xsd-tag is not closed in xsd-schema at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		}
	    		
	    		/* get xsd end type */
		    	XSDType e_xsdSimpleTypeEndTagType = getXSDType(p_a_xsdTags, (i_tempMax - 1));
		    	
	    		/* xsd type and xsd close type must match */
	    		if (e_xsdSimpleTypeTagType != e_xsdSimpleTypeEndTagType) {
	    			throw new IllegalArgumentException("Invalid xsd-tag-type \"" + p_a_xsdTags.get((i_tempMax - 1)) + "\" for closing in xsd-schema at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		}
    		} else {
    			if (e_xsdSimpleTypeTagType == XSDType.RestrictionItem) {
    														net.forestany.forestj.lib.Global.ilogFinest("found restriction item = \"" + p_a_xsdTags.get(i_tempMin) + "\"");
	    			/* add restriction to xs:simpleType element */
	    			o_xsdElement.getRestrictions().add(this.parseXSDRestrictionItem(p_a_xsdTags, i_tempMin));
	    		}
    		}
		}
		
		/* add xs:simpleType as xs:element to current element */
		this.o_currentElement.getChildren().add(o_xsdElement);
		
		/* return xml tag pointer to skip processed simpleType tag */
		return i_nestedMax;
	}
	
	/**
	 * Parse xsd simple content based on XSDElement
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_i_max						pointer where to end line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 */
	private int parseXSDSimpleContent(java.util.List<String> p_a_xsdTags, int p_i_min, int p_i_max) throws IllegalArgumentException {
		/* return xml tag pointer value */
		int i_nestedMax = -1;
		
		/* identify simpleContent tag for current element */
												net.forestany.forestj.lib.Global.ilogFiner("found simpleContent: " + p_a_xsdTags.get(p_i_min) + " to Current Element: " + this.o_currentElement.getName());
		
		XSDElement o_xsdElement = new XSDElement();
		boolean b_foundName = false;
		boolean b_foundMapping = false;
		
		/* read name attribute out of xs:element tag */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	String s_name = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
	    	
	    	if (!java.util.regex.Pattern.matches("[a-zA-Z0-9-_]*", s_name)) {
				throw new IllegalArgumentException("Invalid schema element name '" + s_name + "', invalid characters. Following characters are allowed: [a-z], [A-Z], [0-9], [-] and [_]");
			}
	    	
	        o_xsdElement.setName(s_name);
	        b_foundName = true;
	    }
	    
	    /* read mapping attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("mapping=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	        o_xsdElement.setMapping(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
	        b_foundMapping = true;
	    } else {
	    	if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(o_xsdElement.getName())) && ( o_xsdElement.getName().contentEquals(o_xsdElement.getType()) ) ) {
	    		java.util.List<String> a_invalidNames = java.util.Arrays.asList("string", "duration", "hexbinary", "base64binary", "anyuri", "normalizedstring", "token", 
						"language", "name", "ncname", "nmtoken", "id", "idref", "entity", "integer", "int", "positiveinteger", "nonpositiveinteger", "negativeinteger", 
						"nonnegativeinteger", "byte", "unsignedint", "unsignedbyte", "boolean", "duration", "date", "time", "datetime", "decimal", "double", "float", "short", "long");
				
				if (!a_invalidNames.contains(o_xsdElement.getName())) {
					throw new IllegalArgumentException("Invalid name for xsd-element as array '" + o_xsdElement.getName() + "'");
				}
				
	    		o_xsdElement.setIsArray(true);
	    	}
	    }
	    
	    /* read minOccurs attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	        o_xsdElement.setMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    }
	    
	    /* read maxOccurs attribute out of xs:element tag */
		o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
		o_matcher = o_regex.matcher(p_a_xsdTags.get(p_i_min));
		
	    if (o_matcher.find()) {
	    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    		o_xsdElement.setMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	} else if (o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    		o_xsdElement.setMaxOccurs(-1);
	    	}
	    }
		
		/* start tag pointer for nested xs:simpleContent */
		int i_tempMin = p_i_min;
		
		boolean b_complexTypeFirst = true;
		
		/* find start of simpleContent complexType interlacing */
		while (!p_a_xsdTags.get(i_tempMin).toLowerCase().startsWith("<xs:complextype")) {
			if (i_tempMin == p_i_max) {
				/* forbidden state - interlacing is not valid in xsd-schema */
				throw new IllegalArgumentException("Invalid nested xsd-tag xs:simpleType at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
			}
			
			b_complexTypeFirst = false;
			i_tempMin++;
		}
		
		/* end tag pointer for nested xs:simpleContent */
		int i_tempMax = p_i_min;
		
		/* find end of simpleType interlacing */
		while (!p_a_xsdTags.get(i_tempMax).toLowerCase().startsWith("</xs:complextype")) {
			if (i_tempMax == p_i_max) {
				/* forbidden state - interlacing is not valid in xsd-schema */
				throw new IllegalArgumentException("Invalid nested xsd-tag xs:simpleContent at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
			}
			
			i_tempMax++;
		}
		
		/* set overwrite value for end tag pointer */
		i_nestedMax = i_tempMax;
		
		if (!b_complexTypeFirst) {
			i_nestedMax++;
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("iterate simpleContent");
												net.forestany.forestj.lib.Global.ilogFiner("\t\t" + (i_tempMin + 1) + " ... " + (i_tempMax - 1));
												net.forestany.forestj.lib.Global.ilogFiner("\t\t" + p_a_xsdTags.get((i_tempMin + 1)) + " ... " + p_a_xsdTags.get((i_tempMax - 1)));
		
		/* parse content of xs:simpleContent */
		for (++i_tempMin; i_tempMin <= (i_tempMax - 1); i_tempMin++) {
			/* get xsd type */
    		XSDType e_xsdSimpleContentTagType = getXSDType(p_a_xsdTags, i_tempMin);
    		
    		if (e_xsdSimpleContentTagType == XSDType.Extension) {
    													net.forestany.forestj.lib.Global.ilogFinest("found extension = \"" + p_a_xsdTags.get(i_tempMin) + "\"");
    			
    			boolean b_base_processed = false;
    			
    			/* read base attribute out of xs:extension tag as primitive type */
    			o_regex = java.util.regex.Pattern.compile("base=\"xs:([^\"]*)\"");
    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
    			
    		    if (o_matcher.find()) {
    		    	/* check for name attribute of xs:element tag if we read a primitive type for simple content */
    		    	if (!b_foundName) {
    		    		/* no name attribute found */
    			    	throw new IllegalArgumentException("Invalid xsd-tag xs:element without a name at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
    		    	}
    		    	
    		    											net.forestany.forestj.lib.Global.ilogFinest("found primitive type: " + o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
    		    	o_xsdElement.setType(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
    		    	b_base_processed = true;
    		    } else {
	    		    /* read base attribute out of xs:extension tag as reference type */
	    			o_regex = java.util.regex.Pattern.compile("base=\"([^\"]*)\"");
	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
	    			
	    		    if (o_matcher.find()) {
	    		    											net.forestany.forestj.lib.Global.ilogFinest("found reference type: " + o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1));
	    		    	
	    		    	String s_referenceName = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
	    		        
	    		        /* check if we have a duplicate */
	    		        if (this.getXSDElement(s_referenceName) != null) {
	    		        	throw new IllegalArgumentException("Invalid xsd-tag xs:element (duplicate) at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    		        }
	    		        
	    		    	if (this.xsdElementDefinitionExist(s_referenceName)) {
	    		    												net.forestany.forestj.lib.Global.ilogFinest("add and check element reference = " + s_referenceName);
	    		    		
	    		    		XSDElement o_xsdElementReference = this.getXSDElementDefinition(s_referenceName);
	    		    		
	    		    		/* overwrite type value */
	    		    		o_xsdElement.setType(o_xsdElementReference.getType());
	    		    		
	    		    		/* overwrite name value */
	    		    		if (!b_foundName) {
	    		    			o_xsdElement.setName(o_xsdElementReference.getName());
	    		    		}
	    		    		
	    		    		/* overwrite mapping value */
	    		    		if (!b_foundMapping) {
	    		    			o_xsdElement.setMapping(o_xsdElementReference.getMapping());
	    		    		}
	    		    		
	    		    		/* library does not support multiple occurrences of the same xml element without a list definition */
	    	    			if ( (!o_xsdElement.getChoice()) && (!this.o_currentElement.getMapping().contains("ArrayList(")) && (o_xsdElement.getMaxOccurs() > 1) ) {
	    	    				throw new IllegalArgumentException("Library does not support multiple occurrences of the same xml element without a list definition in xsd-schema at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    	    			}
	    	    		} else {
	    		    		throw new IllegalArgumentException("Invalid xsd-tag xs:element with unknown reference at(" + (p_i_min + 1) + ".-element) \"" + p_a_xsdTags.get(p_i_min) + "\".");
	    		    	}
	    		    	
	    		    	b_base_processed = true;
	    		    }
    		    }
    		    
    		    if (!b_base_processed) {
    		    	throw new IllegalArgumentException("Invalid xsd-tag xs:extension without a valid base-type at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
    		    }
    		}
    		
    		if (!p_a_xsdTags.get(i_tempMin).endsWith("/>")) {
    			if ( (e_xsdSimpleContentTagType == XSDType.Attribute) && (getXSDType(p_a_xsdTags, i_tempMin + 1) == XSDType.SimpleType) ) {
    				/* parse xs:attribute with simpleType */
	    			XSDAttribute o_xsdAttribute = this.parseXSDAttributeWithSimpleType(p_a_xsdTags, i_tempMin);
	    			
	    			/* add xs:attribute to xsd element */
		    		o_xsdElement.getAttributes().add(o_xsdAttribute);
    			}
    			
    			/* if we still have no closing tag, then our xsd schema is invalid */
	    		if (!p_a_xsdTags.get(i_tempMax).startsWith("</")) {
	    			throw new IllegalArgumentException("Invalid xsd-tag is not closed in xsd-schema at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		}
	    		
	    		/* get xsd end type */
		    	XSDType e_xsdSimpleContentEndTagType = getXSDType(p_a_xsdTags, (i_tempMax - 1));
		    	
	    		/* xsd type and xsd close type must match */
	    		if (e_xsdSimpleContentTagType != e_xsdSimpleContentEndTagType) {
	    			throw new IllegalArgumentException("Invalid xsd-tag-type \"" + p_a_xsdTags.get((i_tempMax - 1)) + "\" for closing in xsd-schema at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		} else {
	    			i_tempMax--;
	    		}
    		} else {
    			if (e_xsdSimpleContentTagType == XSDType.Attribute) {
    														net.forestany.forestj.lib.Global.ilogFinest("found attribute item = \"" + p_a_xsdTags.get(i_tempMin) + "\"");
	    			o_regex = java.util.regex.Pattern.compile("ref=\"([^\"]*)\"");
	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
	    			
	    			o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
	    			java.util.regex.Matcher o_matcherName = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
	    			
	    			o_regex = java.util.regex.Pattern.compile("type=\"([^\"]*)\"");
	    			java.util.regex.Matcher o_matcherType = o_regex.matcher(p_a_xsdTags.get(i_tempMin));
	    			
	    		    if (o_matcher.find()) { /* we have an attribute element with reference */
	    		        String s_referenceName = o_matcher.group(0).substring(5, o_matcher.group(0).length() - 1);
	    		    	
	    		        /* check if we have a duplicate */
	    		        if (this.getXSDAttribute(s_referenceName) != null) {
	    		        	throw new IllegalArgumentException("Invalid xsd-tag xs:attribute (duplicate) at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		        }
	    		        
	    		    	if (this.xsdAttributeDefinitionExist(s_referenceName)) {
	    		    												net.forestany.forestj.lib.Global.ilogFinest("add and check attribute reference = " + s_referenceName);
	    		    												
							XSDAttribute o_xsdAttribute = this.getXSDAttributeDefinition(s_referenceName);
	    		    		
	    		    		/* read name attribute out of xs:attribute tag */
	    		    		if ((o_matcherName.find())) {
	    		    			String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
	    		    			
	    		    			/* overwrite name value from reference, because it is dominant from the usage point */
	    		    			o_xsdAttribute.setName(s_name);
	    		    		}
	    		    												
	    		    		/* add xs:attribute to xsd element */
	    		    		o_xsdElement.getAttributes().add(o_xsdAttribute);
	    		    	} else {
	    		    		throw new IllegalArgumentException("Invalid xsd-tag xs:attribute with unknown reference at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		    	}
    		    	} else if ((o_matcherName.find()) && (o_matcherType.find())) { /* we have an attribute element with name and type */
	    		    	/* read name and type attribute values of xs:attribute tag */
	    		    	String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
	    		    	String s_type = o_matcherType.group(0).substring(6, o_matcherType.group(0).length() - 1);
	    		    	
	    		    	/* check if we have a duplicate */
	    		        if (this.getXSDAttribute(s_type) != null) {
	    		        	throw new NullPointerException("Invalid xsd-tag xs:attribute (duplicate) at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		        }
	    		        
	    		    	if (this.xsdAttributeDefinitionExist(s_type)) {
	    		    												net.forestany.forestj.lib.Global.ilogFinest("add and check attribute type reference = " + s_type);
	    		    												
	    		    		XSDAttribute o_xsdAttribute = this.getXSDAttributeDefinition(s_type);
	    		    		
    		    			/* overwrite name value from reference, because it is dominant from the usage point */
    		    			o_xsdAttribute.setName(s_name);
	    		    												
	    		    		/* add xs:attribute object to xsd element */
    		    			o_xsdElement.getAttributes().add(o_xsdAttribute);
	    		    	} else {
	    		    		throw new IllegalArgumentException("Invalid xsd-tag xs:attribute with unknown type reference at(" + (i_tempMin + 1) + ".-element) \"" + p_a_xsdTags.get(i_tempMin) + "\".");
	    		    	}
		    		} else {
	    		    	/* parse xs:attribute */
		    			XSDAttribute o_xsdAttribute = this.parseXSDAttribute(p_a_xsdTags, i_tempMin);
		    			
		    			/* add xs:attribute to xsd element */
    		    		o_xsdElement.getAttributes().add(o_xsdAttribute);
	    		    }
	    		}
    		}
		}
		
		/* add xs:simpleContent as xs:element to current element */
		this.o_currentElement.getChildren().add(o_xsdElement);
		
		return i_nestedMax;
	}
	
	/**
	 * Check if a xsd element definition exists by reference name
	 * 
	 * @param p_s_referenceName		reference name of xsd element definition
	 * @return						true - definition exists, false - definition does not exist
	 */
	private boolean xsdElementDefinitionExist(String p_s_referenceName) {
		boolean b_found = false;
		
		for (XSDElement o_xsdElement : this.a_elementDefinitons) {
			if (o_xsdElement.getName().contentEquals(p_s_referenceName)) {
				b_found = true;
			}
		}
		
		return b_found;
	}
	
	/**
	 * Check if a xsd element definition exists by element object as clone
	 * 
	 * @param p_o_xsdElement		xsd element object
	 * @return						true - definition exists as clone, false - definition does not exist as clone, but with other values
	 */
	private boolean xsdElementDefinitionExistAsClone(XSDElement p_o_xsdElement) {
		boolean b_found = false;
		
		for (XSDElement o_xsdElement : this.a_elementDefinitons) {
			if ( (o_xsdElement.getName().contentEquals(p_o_xsdElement.getName())) && (o_xsdElement.isEqual(p_o_xsdElement)) ) {
				b_found = true;
			}
		}
		
		return b_found;
	}
		
	/**
	 * Get xsd element definition exists by reference name
	 * 
	 * @param p_s_referenceName		reference name of xsd element definition
	 * @return						xsd element object as xsd definition
	 */
	private XSDElement getXSDElementDefinition(String p_s_referenceName) {
		XSDElement o_xsdElement = null;
		
		for (XSDElement o_xsdElementObject : this.a_elementDefinitons) {
			if (o_xsdElementObject.getName().contentEquals(p_s_referenceName)) {
				o_xsdElement = o_xsdElementObject;
			}
		}
		
		return o_xsdElement;
	}
	
	/**
	 * Get xsd element exists by reference name
	 * 
	 * @param p_s_referenceName		reference name of xsd element
	 * @return						xsd element object
	 */
	private XSDElement getXSDElement(String p_s_referenceName) {
		return this.getXSDElement(p_s_referenceName, null);
	}
	
	/**
	 * Get xsd element exists by reference name
	 * 
	 * @param p_s_referenceName		reference name of xsd element
	 * @param p_o_xsdElement		look within xsd element object parameter
	 * @return						xsd element object
	 */
	private XSDElement getXSDElement(String p_s_referenceName, XSDElement p_o_xsdElement) {
		XSDElement o_xsdElement = null;
		
		if (p_o_xsdElement == null) {
			p_o_xsdElement = this.o_currentElement;
		}
		
		for (XSDElement o_xsdElementObject : p_o_xsdElement.getChildren()) {
			if (o_xsdElementObject.getName().contentEquals(p_s_referenceName)) {
				o_xsdElement = o_xsdElementObject.clone();
			}
		}
		
		return o_xsdElement;
	}
	
	/**
	 * Check if a xsd attribute definition exists by reference name
	 * 
	 * @param p_s_referenceName		reference name of xsd attribute definition
	 * @return						true - attribute exists, false - attribute does not exist
	 */
	private boolean xsdAttributeDefinitionExist(String p_s_referenceName) {
		boolean b_found = false;
		
		for (XSDAttribute o_xsdAttribute : this.a_attributeDefinitions) {
			if (o_xsdAttribute.getName().contentEquals(p_s_referenceName)) {
				b_found = true;
			}
		}
		
		return b_found;
	}
	
	/**
	 * Check if a xsd attribute definition exists by attribute object as clone
	 * 
	 * @param p_o_xsdAttribute		xsd attribute object
	 * @return						true - attribute exists as clone, false - attribute does not exist as clone, but with other values
	 */
	private boolean xsdAttributeDefinitionExistAsClone(XSDAttribute p_o_xsdAttribute) {
		boolean b_found = false;
		
		for (XSDAttribute o_xsdAttribute : this.a_attributeDefinitions) {
			if ( (o_xsdAttribute.getName().contentEquals(p_o_xsdAttribute.getName())) && (o_xsdAttribute.isEqual(p_o_xsdAttribute)) ) {
				b_found = true;
			}
		}
		
		return b_found;
	}
	
	/**
	 * Get xsd attribute definition exists by reference name
	 * 
	 * @param p_s_referenceName		reference name of xsd attribute definition
	 * @return						xsd attribute object as xsd attribute definition
	 */
	private XSDAttribute getXSDAttributeDefinition(String p_s_referenceName) {
		XSDAttribute o_xsdAttribute = null;
		
		for (XSDAttribute o_xsdAttributeObject : this.a_attributeDefinitions) {
			if (o_xsdAttributeObject.getName().contentEquals(p_s_referenceName)) {
				o_xsdAttribute = o_xsdAttributeObject;
			}
		}
		
		return o_xsdAttribute;
	}
	
	/**
	 * Get xsd attribute exists by reference name
	 * 
	 * @param p_s_referenceName		reference name of xsd attribute
	 * @param p_o_xsdElement		look within xsd element object parameter
	 * @return						xsd attribute object
	 */
	private XSDAttribute getXSDAttribute(String p_s_referenceName) {
		return this.getXSDAttribute(p_s_referenceName, null);
	}
	
	/**
	 * Get xsd attribute exists by reference name
	 * 
	 * @param p_s_referenceName		reference name of xsd attribute
	 * @return						xsd attribute object
	 */
	private XSDAttribute getXSDAttribute(String p_s_referenceName, XSDElement p_o_xsdElement) {
		XSDAttribute o_xsdAttribute = null;
		
		if (p_o_xsdElement == null) {
			p_o_xsdElement = this.o_currentElement;
		}
		
		for (XSDAttribute o_xsdAttributeObject : p_o_xsdElement.getAttributes()) {
			if (o_xsdAttributeObject.getName().contentEquals(p_s_referenceName)) {
				o_xsdAttribute = o_xsdAttributeObject.clone();
			}
		}
		
		return o_xsdAttribute;
	}
	
	/* simplify chaotic divided schema */
	
	/**
	 * (ALPHA - NOT TESTED - MIGHT NOT WORK) Simplifies a chaotic divided xsd schema file and sort it to ordered element, attributes and complex type tags
	 * 
	 * @param p_s_source							full-path to xsd schema source file
	 * @param p_s_destination						full-path to xsd schema destination file, will be overwritten if it exists
	 * @throws IllegalArgumentException				value/structure within xsd schema file invalid
	 * @throws NullPointerException					xsd schema not possible, node is null or a duplicate
	 * @throws java.io.IOException					cannot access or open xsd file and it's content
	 */
	public void simplifyChaoticDividedSchemaFile(String p_s_source, String p_s_destination) throws IllegalArgumentException, java.io.IOException, NullPointerException {
		/* initialize helper lists */
		this.a_elementDefinitons = new java.util.ArrayList<XSDElement>();
		this.a_attributeDefinitions = new java.util.ArrayList<XSDAttribute>();
		this.a_temp = new java.util.ArrayList<java.util.Map.Entry<Integer, Integer>>();
		this.a_dividedElements = new java.util.ArrayList<XSDElement>();
		
		this.i_level = 0;
		
		/* check if file exists */
		if (!File.exists(p_s_source)) {
			throw new IllegalArgumentException("File[" + p_s_source + "] does not exist.");
		}
		
		/* open xsd-schema source file */
		File o_file = new File(p_s_source, false);
		
		StringBuilder o_stringBuilder = new StringBuilder();
		
		/* read all xsd schema file lines to one string builder */
		for (String s_line : o_file.getFileContentAsList()) {
			o_stringBuilder.append(s_line);
		}
		
		/* read all xsd-schema file lines and delete all line-wraps and tabs and values only containing white spaces */
		String s_xsd = o_stringBuilder.toString().replaceAll("[\\r\\n\\t]", "").replaceAll(">\\s*<", "><");
		
		/* clean up xsd-schema */
		s_xsd = s_xsd.replaceAll("<\\?(.*?)\\?>", "");
		s_xsd = s_xsd.replaceAll("<!--(.*?)-->", "");
		
		/* look for targetNamespace in schema-tag */
		if (s_xsd.startsWith("<xs:schema")) {
			String s_foo = s_xsd.substring(0, s_xsd.indexOf(">"));
			
			if (s_foo.contains("targetnamespace=\"")) {
				s_foo = s_foo.substring(s_foo.indexOf("targetnamespace=\"") + 17);
				s_foo = s_foo.substring(0, s_foo.indexOf("\""));
				
				this.s_targetNamespace = s_foo;
			} else if (s_foo.contains("targetNamespace=\"")) {
				s_foo = s_foo.substring(s_foo.indexOf("targetNamespace=\"") + 17);
				s_foo = s_foo.substring(0, s_foo.indexOf("\""));
				
				this.s_targetNamespace = s_foo;
			}
		}
		
		/* remove schema and annotation tags */
		s_xsd = s_xsd.replaceAll("<xs:schema(.*?)>", "");
		s_xsd = s_xsd.replaceAll("</xs:schema>", "");
		s_xsd = s_xsd.replaceAll("<xs:annotation>(.*?)</xs:annotation>", "");
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("cleaned up xsd-schema: " + this.s_lineBreak + s_xsd);
		
		/* validate xsd */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("(<[^<>]*?<[^<>]*?>|<[^<>]*?>[^<>]*?>)");
		java.util.regex.Matcher o_matcher = o_regex.matcher(s_xsd);
		
		/* if regex-matcher has match, the xsd-schema is not valid */
	    while (o_matcher.find()) {
	        throw new IllegalArgumentException("Invalid xsd-schema. Please check xsd-schema at \"" + o_matcher.group(0) + "\".");
	    }
		
	    										net.forestany.forestj.lib.Global.ilogConfig("split xsd-schema into xsd-elements");
	    
	    java.util.List<String> a_xsdTags = new java.util.ArrayList<String>();
	    
	    /* add all xsd-schema-tags to a list for parsing */
	    o_regex = java.util.regex.Pattern.compile("<[^<>]*?>");
	    o_matcher = o_regex.matcher(s_xsd);
	    
	    while (o_matcher.find()) {
	        a_xsdTags.add(o_matcher.group(0));
	    }
	    
	    /* list for indexes of closing tags for deletion */
	    java.util.List<Integer> a_deleteTags = new java.util.ArrayList<Integer>();
	    
	    /* check for element definitions which have not any interlacing content, so these can be converted to one-liner */
	    for (int i_min = 0; i_min <= a_xsdTags.size() - 1; i_min++) {
	    	if (i_min > 0) {
	    		if ( (a_xsdTags.get(i_min).contentEquals("</xs:element>")) && (a_xsdTags.get(i_min - 1).startsWith("<xs:element")) ) {
	    			/* make element definition one liner */
	    			a_xsdTags.set(i_min - 1, a_xsdTags.get(i_min - 1).substring(0, a_xsdTags.get(i_min - 1).length() - 1) + "/>");
	    			/* remember element closing tag for deletion */
	    			a_deleteTags.add(i_min);
	    		}
	    	}
	    }
	    
	    int i_count = 0;
	    
	    /* iterate all deletion indexes */
	    for (int i_delete : a_deleteTags) {
	    	/* use overall count to delete the correct index in this for each loop */
	    	a_xsdTags.remove(i_delete - (i_count++));
	    }
	    
	    /* check if xsd-schema starts with xs:element */
	    if ( (!a_xsdTags.get(0).toLowerCase().startsWith("<xs:element")) && (!a_xsdTags.get(0).toLowerCase().startsWith("<xs:complextype")) ) {
    		throw new IllegalArgumentException("xsd-schema must start with <xs:element>-tag or with <xs:complexType>-tag.");
    	}
	    
	    /* simplify chaotic divided schema */
	    simplifyChaoticDividedSchema(a_xsdTags, 0, a_xsdTags.size() - 1);
												net.forestany.forestj.lib.Global.ilogConfig("simplified xsd-schema");
	    
		/* build new xsd schema document content */
		o_stringBuilder = this.printOutSimplifiedDividedSchema();
		
		/* check if destination file exists, if true -> delete it */
		if (File.exists(p_s_destination)) {
			File.deleteFile(p_s_destination);
		}
		
		/* create xsd-schema destination file */
		File o_fileDestination = new File(p_s_destination, true);
		
		/* write xsd schema document content to file */
		o_fileDestination.replaceContent(o_stringBuilder.toString());
	}
	
	/**
	 * Build new xsd schema document content
	 * @return StringBuilder instance with document content
	 */
	private StringBuilder printOutSimplifiedDividedSchema() {
		/* list of primitive xsd types */
		java.util.List<String> a_primitiveTypes = java.util.Arrays.asList("string", "duration", "hexbinary", "base64binary", "anyuri", "normalizedstring", "token", 
				"language", "name", "ncname", "nmtoken", "id", "idref", "entity", "integer", "int", "positiveinteger", "nonpositiveinteger", "negativeinteger", 
				"nonnegativeinteger", "byte", "unsignedint", "unsignedbyte", "boolean", "duration", "date", "time", "datetime", "decimal", "double", "float", "short", "long");
		
		StringBuilder o_stringBuilder = new StringBuilder();
		
		/* xml document tag */
		o_stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append(this.s_lineBreak);
		
		/* add schema tag */
		o_stringBuilder.append("<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" elementFormDefault=\"qualified\" attributeFormDefault=\"unqualified\">").append(this.s_lineBreak);
		o_stringBuilder.append(this.s_lineBreak);
		
		/* definition of simple elements */
		o_stringBuilder.append("<!-- definition of simple elements -->").append(this.s_lineBreak);
		
		for (XSDElement o_element : this.a_elementDefinitons) {
			if (o_element.getSimpleType()) { /* element with simple type */
				o_stringBuilder.append("<xs:simpleType name=\"" + o_element.getName() + "\"");
				
				if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_element.getMapping())) {
					o_stringBuilder.append(" mapping=\"" + o_element.getMapping() + "\"");
				}
				
				if (o_element.getMinOccurs() != 1) {
					o_stringBuilder.append(" minOccurs=\"" + o_element.getMinOccurs() + "\"");
				}
				
				if (o_element.getMaxOccurs() < 0) {
					o_stringBuilder.append(" maxOccurs=\"unbounded\"");
				} else if (o_element.getMaxOccurs() != 1) {
					o_stringBuilder.append(" maxOccurs=\"" + o_element.getMaxOccurs() + "\"");
				}
				
				o_stringBuilder.append(">").append(this.s_lineBreak);
					
					if (o_element.getRestrictions().size() > 0) {
						o_stringBuilder.append("\t" + "<xs:restriction base=\"xs:" + o_element.getType() + "\">").append(this.s_lineBreak);
						
						for (XSDRestriction o_restriction : o_element.getRestrictions()) {
							o_stringBuilder.append("\t\t" + "<xs:" + o_restriction.getName() + " value=\"" + ( (!net.forestany.forestj.lib.Helper.isStringEmpty(o_restriction.getStrValue())) ? o_restriction.getStrValue() : o_restriction.getIntValue() ) + "\"/>").append(this.s_lineBreak);
						}
						
						o_stringBuilder.append("\t" + "</xs:restriction>").append(this.s_lineBreak);
					} else {
						o_stringBuilder.append("\t" + "<xs:restriction base=\"xs:" + o_element.getType() + "\"/>").append(this.s_lineBreak);
					}
			
				o_stringBuilder.append("</xs:simpleType>").append(this.s_lineBreak);
			} else { /* element one liner */
				o_stringBuilder.append("<xs:element name=\"" + o_element.getName() + "\" type=\"xs:" + o_element.getType() + "\"");
				
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_element.getMapping())) {
						o_stringBuilder.append(" mapping=\"" + o_element.getMapping() + "\"");
					}
					
					if (o_element.getMinOccurs() != 1) {
						o_stringBuilder.append(" minOccurs=\"" + o_element.getMinOccurs() + "\"");
					}
					
					if (o_element.getMaxOccurs() < 0) {
						o_stringBuilder.append(" maxOccurs=\"unbounded\"");
					} else if (o_element.getMaxOccurs() != 1) {
						o_stringBuilder.append(" maxOccurs=\"" + o_element.getMaxOccurs() + "\"");
					}
				
				o_stringBuilder.append("/>").append(this.s_lineBreak);
			}
		}
		
		/* definition of attributes */
		o_stringBuilder.append(this.s_lineBreak);
		o_stringBuilder.append("<!-- definition of attributes -->").append(this.s_lineBreak);
		
		for (XSDAttribute o_attribute : this.a_attributeDefinitions) {
			/* attribute with simple type */
			if (o_attribute.getSimpleType()) {
				o_stringBuilder.append("<xs:attribute name=\"" + o_attribute.getName() + "\"");
				
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getMapping())) {
						o_stringBuilder.append(" mapping=\"" + o_attribute.getMapping() + "\"");
					}
					
					if (o_attribute.getRequired()) {
						o_stringBuilder.append(" use=\"required\"");
					}
					
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getDefault())) {
						o_stringBuilder.append(" default=\"" + o_attribute.getDefault() + "\"");
					}
					
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getFixed())) {
						o_stringBuilder.append(" fixed=\"" + o_attribute.getFixed() + "\"");
					}
					
					o_stringBuilder.append(">").append(this.s_lineBreak);
						o_stringBuilder.append("\t" + "<xs:simpleType>").append(this.s_lineBreak);
						
						if (o_attribute.getRestrictions().size() > 0) {
							o_stringBuilder.append("\t\t" + "<xs:restriction base=\"xs:" + o_attribute.getType() + "\">").append(this.s_lineBreak);
							
							for (XSDRestriction o_restriction : o_attribute.getRestrictions()) {
								o_stringBuilder.append("\t\t\t" + "<xs:" + o_restriction.getName() + " value=\"" + ( (!net.forestany.forestj.lib.Helper.isStringEmpty(o_restriction.getStrValue())) ? o_restriction.getStrValue() : o_restriction.getIntValue() ) + "\"/>").append(this.s_lineBreak);
							}
							
							o_stringBuilder.append("\t\t" + "</xs:restriction>").append(this.s_lineBreak);
						} else {
							o_stringBuilder.append("\t\t" + "<xs:restriction base=\"xs:" + o_attribute.getType() + "\"/>").append(this.s_lineBreak);
						}
						
						o_stringBuilder.append("\t" + "</xs:simpleType>").append(this.s_lineBreak);
				o_stringBuilder.append("</xs:attribute>").append(this.s_lineBreak);
			} else { /* attribute one liner */
				o_stringBuilder.append("<xs:attribute name=\"" + o_attribute.getName() + "\" type=\"xs:" + o_attribute.getType() + "\"");
				
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getMapping())) {
						o_stringBuilder.append(" mapping=\"" + o_attribute.getMapping() + "\"");
					}
					
					if (o_attribute.getRequired()) {
						o_stringBuilder.append(" use=\"required\"");
					}
					
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getDefault())) {
						o_stringBuilder.append(" default=\"" + o_attribute.getDefault() + "\"");
					}
					
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getFixed())) {
						o_stringBuilder.append(" fixed=\"" + o_attribute.getFixed() + "\"");
					}
				
				o_stringBuilder.append("/>").append(this.s_lineBreak);
			}
		}
		
		/* definition of complex types */
		o_stringBuilder.append(this.s_lineBreak);
		o_stringBuilder.append("<!-- definition of complex types -->").append(this.s_lineBreak);
		
		for (XSDElement o_complexType : this.a_dividedElements) {
			/* complex type with simple content */
			if (o_complexType.getSimpleContent()) {
				o_stringBuilder.append("<xs:complexType name=\"" + o_complexType.getName() + "\"");
				
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_complexType.getMapping())) {
						o_stringBuilder.append(" mapping=\"" + o_complexType.getMapping() + "\"");
					}
					
					if (o_complexType.getMinOccurs() != 1) {
						o_stringBuilder.append(" minOccurs=\"" + o_complexType.getMinOccurs() + "\"");
					}
					
					if (o_complexType.getMaxOccurs() < 0) {
						o_stringBuilder.append(" maxOccurs=\"unbounded\"");
					} else if (o_complexType.getMaxOccurs() != 1) {
						o_stringBuilder.append(" maxOccurs=\"" + o_complexType.getMaxOccurs() + "\"");
					}
					
					o_stringBuilder.append(">").append(this.s_lineBreak);
					
						o_stringBuilder.append("\t" + "<xs:simpleContent>").append(this.s_lineBreak);
						
							if (o_complexType.getAttributes().size() > 0) {
								o_stringBuilder.append("\t\t" + "<xs:extension base=\"" + o_complexType.getType() + "\">").append(this.s_lineBreak);
								
								/* attributes to simple content */
								for (XSDAttribute o_attribute : o_complexType.getAttributes()) {
									if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getReference())) {
										if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getName())) {
											o_stringBuilder.append("\t\t\t" + "<xs:attribute name=\"" + o_attribute.getName() + "\" type=\"" + o_attribute.getReference() + "\"/>").append(this.s_lineBreak);
										} else {
											o_stringBuilder.append("\t\t\t" + "<xs:attribute ref=\"" + o_attribute.getReference() + "\"/>").append(this.s_lineBreak);
										}
									} else {
										o_stringBuilder.append("\t\t\t" + "<xs:attribute name=\"" + o_attribute.getName() + "\" type=\"" + o_attribute.getType() + "\"");
										
											if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getMapping())) {
												o_stringBuilder.append(" mapping=\"" + o_attribute.getMapping() + "\"");
											}
											
											if (o_attribute.getRequired()) {
												o_stringBuilder.append(" use=\"required\"");
											}
											
											if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getDefault())) {
												o_stringBuilder.append(" default=\"" + o_attribute.getDefault() + "\"");
											}
											
											if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getFixed())) {
												o_stringBuilder.append(" fixed=\"" + o_attribute.getFixed() + "\"");
											}
											
										o_stringBuilder.append("/>").append(this.s_lineBreak);
									}
								}
								
								o_stringBuilder.append("\t\t" + "</xs:extension>").append(this.s_lineBreak);
							} else {
								o_stringBuilder.append("\t\t" + "<xs:extension base=\"" + o_complexType.getType() + "\"/>").append(this.s_lineBreak);
							}
						
						o_stringBuilder.append("\t" + "</xs:simpleContent>").append(this.s_lineBreak);
				
				o_stringBuilder.append("</xs:complexType>").append(this.s_lineBreak).append(this.s_lineBreak);
			} else { /* default complex type */
				o_stringBuilder.append("<xs:complexType name=\"" + o_complexType.getName() + "\"");
				
					if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_complexType.getMapping())) {
						o_stringBuilder.append(" mapping=\"" + o_complexType.getMapping() + "\"");
					}
					
					if (!o_complexType.getChoice()) {
						if (o_complexType.getMinOccurs() != 1) {
							o_stringBuilder.append(" minOccurs=\"" + o_complexType.getMinOccurs() + "\"");
						}
						
						if (o_complexType.getMaxOccurs() < 0) {
							o_stringBuilder.append(" maxOccurs=\"unbounded\"");
						} else if (o_complexType.getMaxOccurs() != 1) {
							o_stringBuilder.append(" maxOccurs=\"" + o_complexType.getMaxOccurs() + "\"");
						}
					}
					
					o_stringBuilder.append(">").append(this.s_lineBreak);
					
						o_stringBuilder.append("\t" + "<xs:sequence");
						
						if (o_complexType.getSequenceMinOccurs() != 1) {
							o_stringBuilder.append(" minOccurs=\"" + o_complexType.getSequenceMinOccurs() + "\"");
						}
						
						if (o_complexType.getSequenceMaxOccurs() < 0) {
							o_stringBuilder.append(" maxOccurs=\"unbounded\"");
						} else if (o_complexType.getSequenceMaxOccurs() != 1) {
							o_stringBuilder.append(" maxOccurs=\"" + o_complexType.getSequenceMaxOccurs() + "\"");
						}
						
						o_stringBuilder.append(">").append(this.s_lineBreak);
						
						String s_additionalIndent = "";
						
							if (o_complexType.getChoice()) {
								s_additionalIndent = "\t";
								
								o_stringBuilder.append("\t\t" + "<xs:choice");
								
								if (o_complexType.getMinOccurs() != 1) {
									o_stringBuilder.append(" minOccurs=\"" + o_complexType.getMinOccurs() + "\"");
								}
								
								if (o_complexType.getMaxOccurs() < 0) {
									o_stringBuilder.append(" maxOccurs=\"unbounded\"");
								} else if (o_complexType.getMaxOccurs() != 1) {
									o_stringBuilder.append(" maxOccurs=\"" + o_complexType.getMaxOccurs() + "\"");
								}
								
								o_stringBuilder.append(">").append(this.s_lineBreak);
							}
							
							/* elements in sequence of complex type */
							for (XSDElement o_element : o_complexType.getChildren()) {
								if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_element.getReference())) {
									if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_element.getName())) {
										o_stringBuilder.append(s_additionalIndent + "\t\t" + "<xs:element name=\"" + o_element.getName() + "\" type=\"" + o_element.getReference() + "\"");
									} else {
										o_stringBuilder.append(s_additionalIndent + "\t\t" + "<xs:element ref=\"" + o_element.getReference() + "\"");
									}
								} else {
									if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(o_element.getName())) && (!net.forestany.forestj.lib.Helper.isStringEmpty(o_element.getType())) && (!a_primitiveTypes.contains(o_element.getType().toLowerCase())) ) {
										o_stringBuilder.append(s_additionalIndent + "\t\t" + "<xs:element name=\"" + o_element.getName() + "\" type=\"" + o_element.getType() + "\"");
									} else {
										o_stringBuilder.append(s_additionalIndent + "\t\t" + "<xs:element ref=\"" + o_element.getName() + "\"");
									}
								}
								
								if (o_element.getMinOccurs() != 1) {
									o_stringBuilder.append(" minOccurs=\"" + o_element.getMinOccurs() + "\"");
								}
								
								if (o_element.getMaxOccurs() < 0) {
									o_stringBuilder.append(" maxOccurs=\"unbounded\"");
								} else if (o_element.getMaxOccurs() != 1) {
									o_stringBuilder.append(" maxOccurs=\"" + o_element.getMaxOccurs() + "\"");
								}
								
								o_stringBuilder.append("/>").append(this.s_lineBreak);
							}
							
							if (o_complexType.getChoice()) {
								o_stringBuilder.append("\t\t" + "</xs:choice>").append(this.s_lineBreak);
							}
						
						o_stringBuilder.append("\t" + "</xs:sequence>").append(this.s_lineBreak);
					
					/* attributes after sequence of complex type */
					for (XSDAttribute o_attribute : o_complexType.getAttributes()) {
						if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getReference())) {
							if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getName())) {
								o_stringBuilder.append("\t\t\t" + "<xs:attribute name=\"" + o_attribute.getName() + "\" type=\"" + o_attribute.getReference() + "\"/>").append(this.s_lineBreak);
							} else {
								o_stringBuilder.append("\t\t\t" + "<xs:attribute ref=\"" + o_attribute.getReference() + "\"/>").append(this.s_lineBreak);
							}
						} else {
							o_stringBuilder.append("\t" + "<xs:attribute name=\"" + o_attribute.getName() + "\" type=\"" + o_attribute.getType() + "\"");
							
								if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getMapping())) {
									o_stringBuilder.append(" mapping=\"" + o_attribute.getMapping() + "\"");
								}
								
								if (o_attribute.getRequired()) {
									o_stringBuilder.append(" use=\"required\"");
								}
								
								if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getDefault())) {
									o_stringBuilder.append(" default=\"" + o_attribute.getDefault() + "\"");
								}
								
								if (!net.forestany.forestj.lib.Helper.isStringEmpty(o_attribute.getFixed())) {
									o_stringBuilder.append(" fixed=\"" + o_attribute.getFixed() + "\"");
								}
								
							o_stringBuilder.append("/>").append(this.s_lineBreak);
						}
					}
				
				o_stringBuilder.append("</xs:complexType>").append(this.s_lineBreak).append(this.s_lineBreak);
			}
		}
		
		/* close schema tag */
		o_stringBuilder.append("</xs:schema>").append(this.s_lineBreak);
		
		return o_stringBuilder;
	}
	
	/**
	 * Main method to simplify a chatoic divided schema, looking for definitions and complex type elements
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_i_max						pointer where to end line iteration
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid, not all references could be resolved
	 * @throws NullPointerException			value within xsd content missing or min. amount not available
	 */
	private void simplifyChaoticDividedSchema(java.util.List<String> p_a_xsdTags, int p_i_min, int p_i_max) throws IllegalArgumentException, NullPointerException {
		int i_max = p_i_max;
		
		int i_complexTypes = 1;
		int i_definitions = 1;
		
    	/* iterate all elements */
	    for (int i_min = p_i_min; i_min <= i_max; i_min++) {
	    	/* get xsd type */
	    	XSDType e_xsdTagType = getXSDType(p_a_xsdTags, i_min);
	    	
	    	/* first xsd tag must be of type element, complex type or simple type and not close itself */
	    	if (!p_a_xsdTags.get(i_min).endsWith("/>")) {
	    		/* 
	    		 * check if we have a simpleType xsd tag or
	    		 * an xs:element tag with a simple type or
	    		 * an xs:attribute tag with a simple type or
	    		 * an xs:complexType tag with a simple content in it,
	    		 * this is like a one-liner element-definition or attribute-definition */
	    		if (
	    			(e_xsdTagType == XSDType.SimpleType) ||
	    			( (e_xsdTagType == XSDType.Element) && (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleType) ) ||
	    			( (e_xsdTagType == XSDType.Attribute) && (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleType) ) ||
	    			( (e_xsdTagType == XSDType.ComplexType) && (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleContent) )
	    		) {
	    			int i_tempMin = i_min + 1;
	    			
	    			if (e_xsdTagType == XSDType.SimpleType) { /* parse simple type */
	    														net.forestany.forestj.lib.Global.ilogFiner("add element reference with simpleType of: " + p_a_xsdTags.get(i_min));
						/* parse xs:element with simpleType */
						XSDElement o_xsdElement = this.parseXSDElementWithSimpleType(p_a_xsdTags, i_min);
						
						/* library does not support multiple occurrences of the same xml element without a list definition */
						if (o_xsdElement.getMaxOccurs() > 1) {
							throw new IllegalArgumentException("Library does not support multiple occurrences of the same xml element without a list definition in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
						}
						
						/* skip flag for adding object to list */
						boolean b_skip = false;
						
						/* check if xs:element definition already exists */
						if (this.xsdElementDefinitionExist(o_xsdElement.getName())) {
							if (!this.xsdElementDefinitionExistAsClone(o_xsdElement)) {
								throw new IllegalArgumentException("Invalid xsd-element-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
							} else {
								b_skip = true;
							}
						}
						
						if (!b_skip) {
							/* add element definition to list */
							this.a_elementDefinitons.add(o_xsdElement);
						}
		    			
		    			/* look for end of nested xs:simpleType tag */
		    			while ( (e_xsdTagType == XSDType.SimpleType) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:simpletype>")) ) {
		    				if (i_tempMin == i_max) {
		    					/* forbidden state - interlacing is not valid in xsd-schema */
		    					throw new IllegalArgumentException("Invalid nested xsd-tag xs:simpleType at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
		    				}
		    				
		    				i_tempMin++;
			    		}
		    		} else if (e_xsdTagType == XSDType.Element) { /* parse xs:element with simple type */
		    			/* parse xs:element with simple type */
		    			XSDElement o_xsdElement = this.parseXSDElementWithSimpleType(p_a_xsdTags, i_min);
		    			
		    			/* skip flag for adding object to list */
						boolean b_skip = false;
		    			
		    			/* check if xs:element definition already exists */
		    			if (this.xsdElementDefinitionExist(o_xsdElement.getName())) {
		    				if (!this.xsdElementDefinitionExistAsClone(o_xsdElement)) {
		    					throw new IllegalArgumentException("Invalid xsd-element-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
		    				} else {
								b_skip = true;
							}
		    			}
		    			
		    			if (!b_skip) {
		    				/* add element definition to list */
		    				this.a_elementDefinitons.add(o_xsdElement);
		    			}
	    				
		    			/* look for end of nested xs:element tag */
		    			while ( (e_xsdTagType == XSDType.Element) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:element>")) ) {
		    				if (i_tempMin == i_max) {
		    					/* forbidden state - interlacing is not valid in xsd-schema */
		    					throw new IllegalArgumentException("Invalid nested xsd-tag xs:element at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
		    				}
		    				
		    				i_tempMin++;
			    		}
		    		} else if (e_xsdTagType == XSDType.Attribute) { /* parse xs:attribute with simple type */
		    			/* parse xs:attribute with simple type */
		    			XSDAttribute o_xsdAttribute = this.parseXSDAttributeWithSimpleType(p_a_xsdTags, i_min);
		    			
		    			/* skip flag for adding object to list */
						boolean b_skip = false;
		    			
		    			/* check if xs:attribute definition already exists */
		    			if (this.xsdAttributeDefinitionExist(o_xsdAttribute.getName())) {
		    				if (!this.xsdAttributeDefinitionExistAsClone(o_xsdAttribute)) {
		    					throw new IllegalArgumentException("Invalid xsd-attribute-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
		    				} else {
								b_skip = true;
							}
		    			}
		    			
		    			if (!b_skip) {
		    				/* add element definition to list */
		    				this.a_attributeDefinitions.add(o_xsdAttribute);
		    			}
	    				
		    			/* look for end of nested xs:element tag */
		    			while ( (e_xsdTagType == XSDType.Attribute) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:attribute>")) ) {
		    				if (i_tempMin == i_max) {
		    					/* forbidden state - interlacing is not valid in xsd-schema */
		    					throw new IllegalArgumentException("Invalid nested xsd-tag xs:attribute at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
		    				}
		    				
		    				i_tempMin++;
			    		}
		    		} else if (e_xsdTagType == XSDType.ComplexType) { /* parse xs:complexType with simple content */
		    			/* parse xs:complexType with simple content */
		    			XSDElement o_xsdElement = this.parseXSDComplexTypeWithSimpleContent(p_a_xsdTags, i_min);
		    			
		    			/* look for end of nested xs:complexType tag */
		    			while ( (e_xsdTagType == XSDType.ComplexType) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:complextype>")) ) {
		    				if (i_tempMin == i_max) {
		    					/* forbidden state - interlacing is not valid in xsd-schema */
		    					throw new IllegalArgumentException("Invalid nested xsd-tag xs:complexType at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
		    				}
		    				
		    				i_tempMin++;
			    		}
		    			
		    			if (o_xsdElement != null) {
		    				/* skip flag for adding object to list */
							boolean b_skip = false;
		    				
		    				/* check if xs:element definition already exists */
			    			if (this.schemaElementDefinitionExist(o_xsdElement.getName())) {
			    				if (!this.schemaElementDefinitionExistAsClone(o_xsdElement)) {
			    					throw new IllegalArgumentException("Invalid xsd-element-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
			    				} else {
									b_skip = true;
								}
			    			}
		    				
			    			if (!b_skip) {
				    			/* add element definition to list */
								this.a_dividedElements.add(o_xsdElement);
			    			}
		    			} else {
		    				/* store xsd start tag index and end tag index for later iteration */
		    				this.a_temp.add(new java.util.AbstractMap.SimpleEntry<Integer, Integer>(i_min, i_tempMin));
		    			}
		    		}
	    			
	    			i_min = i_tempMin;
	    			i_definitions++;
	    			
	    			continue;
	    		} else if ( (e_xsdTagType == XSDType.Element) || (e_xsdTagType == XSDType.ComplexType) ) { /* xsd tag is type xs:element or xs:complexType */
	    			/* parse xs:element */
	    			XSDElement o_xsdElement = this.parseXSDElement(p_a_xsdTags, i_min, true);
	    				
	    		    int i_oldMax = i_max;
	    			int i_tempMin = i_min + 1;
	    			int i_level = 0;
	    			
	    			/* look for end of nested xs:element or xs:complexType tag */
	    			while (
	    				( (e_xsdTagType == XSDType.Element) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:element>")) ) || 
	    				( (e_xsdTagType == XSDType.ComplexType) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:complextype>")) ) || 
	    				(i_level != 0)
	    			)
	    			{
	    				if (e_xsdTagType == XSDType.Element) {
	    					/* handle other interlacing in current nested xs:element tag */
		    				if ( (p_a_xsdTags.get(i_tempMin).toLowerCase().startsWith("<xs:element")) && (!p_a_xsdTags.get(i_tempMin).endsWith("/>")) ) {
		    					i_level++;
		    				} else if (p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:element>")) {
		    					i_level--;
		    				}
	    				} else if (e_xsdTagType == XSDType.ComplexType) {
	    					/* handle other interlacing in current nested xs:complexType tag */
		    				if ( (p_a_xsdTags.get(i_tempMin).toLowerCase().startsWith("<xs:complextype")) && (!p_a_xsdTags.get(i_tempMin).endsWith("/>")) ) {
		    					i_level++;
		    				} else if (p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:complextype>")) {
		    					i_level--;
		    				}
	    				}
	    				
	    				if (i_level > 1) {
	    					/* forbidden state - interlacing is to deep.not valid in xsd-schema */
	    					throw new IllegalArgumentException("Invalid nested xsd-tag xs:element at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\". This interlacing goes to deep for a divided xsd-schema.");
	    				}
	    				
	    				if (i_tempMin == i_max) {
	    					/* forbidden state - interlacing is not valid in xsd-schema */
	    					throw new IllegalArgumentException("Invalid nested xsd-tag xs:element at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    				}
	    				
	    				i_tempMin++;
		    		}
	    			
											    			net.forestany.forestj.lib.Global.ilogFiner("interlacing");
											    			net.forestany.forestj.lib.Global.ilogFiner(i_min + " ... " + i_tempMin);
											    			net.forestany.forestj.lib.Global.ilogFiner(p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_tempMin));
	    			
	    			/* parse found complex xs:element or xs:complexType tag in detail */
	    			if (parseXSDSchemaDividedElement(p_a_xsdTags, i_min, i_tempMin, o_xsdElement)) {
	    				if ( (this.schemaElementDefinitionExist(o_xsdElement.getName())) && (!this.schemaElementDefinitionExistAsClone(o_xsdElement)) ) {
	    					throw new IllegalArgumentException("Invalid xsd-tag xs:element or xs:complexType already exists within schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    				} else {
	    					/* last check if element does not already exists in definition list */
	    					if (!this.schemaElementDefinitionExistAsClone(o_xsdElement)) {
	    						this.a_dividedElements.add(o_xsdElement);
	    					}
	    				}
	    			} else {
	    				/* store xsd start tag index and end tag index for later iteration */
	    				this.a_temp.add(new java.util.AbstractMap.SimpleEntry<Integer, Integer>(i_min, i_tempMin));
	    			}
	    			
	    			i_min = i_tempMin;
	    			i_max = i_oldMax;
	    			
	    			i_complexTypes++;
	    			
	    			continue;
	    		}
	    		
	    		/* decrease xml end tag counter until sequence end tag was found */
	    		if (e_xsdTagType == XSDType.Sequence) {
		    		while (!p_a_xsdTags.get(i_max).toLowerCase().contentEquals("</xs:sequence>")) {
	    				i_max--;
		    		}
	    		}
	    		
	    		/* if we still have no closing tag, then our xsd schema is invalid */
	    		if (!p_a_xsdTags.get(i_max).startsWith("</")) {
	    			throw new IllegalArgumentException("Invalid xsd-tag is not closed in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		}
	    		
	    		/* get xsd end type */
		    	XSDType e_xsdEndTagType = getXSDType(p_a_xsdTags, i_max);
	    		
	    		/* xsd type and xsd close type must match */
	    		if (e_xsdTagType != e_xsdEndTagType) {
	    			throw new IllegalArgumentException("Invalid xsd-tag-type for closing in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		} else {
	    			i_max--;
	    		}
	    		
	    		i_complexTypes++;
	    	} else {
	    		/* list of primitive xsd types */
    			java.util.List<String> a_primitiveTypes = java.util.Arrays.asList("string", "duration", "hexbinary", "base64binary", "anyuri", "normalizedstring", "token", 
						"language", "name", "ncname", "nmtoken", "id", "idref", "entity", "integer", "int", "positiveinteger", "nonpositiveinteger", "negativeinteger", 
						"nonnegativeinteger", "byte", "unsignedint", "unsignedbyte", "boolean", "duration", "date", "time", "datetime", "decimal", "double", "float", "short", "long");
    			
	    		if (e_xsdTagType == XSDType.Element) {
	    													net.forestany.forestj.lib.Global.ilogFiner("add element reference of: " + p_a_xsdTags.get(i_min));
	    			/* parse xs:element */
	    			XSDElement o_xsdElement = this.parseXSDElement(p_a_xsdTags, i_min);
	    			
	    			/* check if element defintion on level 0 has a primitive type */
	    			if (!a_primitiveTypes.contains(o_xsdElement.getType().toLowerCase())) {
	    				throw new IllegalArgumentException("Library does not support usage of non-primitive xsd types on element definitions on level '0' in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    			}
	    			
	    			/* library does not support multiple occurrences of the same xml element without a list definition */
	    			if (o_xsdElement.getMaxOccurs() > 1) {
	    				throw new IllegalArgumentException("Library does not support multiple occurrences of the same xml element without a list definition in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    			}
	    			
	    			/* skip flag for adding object to list */
					boolean b_skip = false;
					
	    			/* check if xs:element definition already exists */
	    			if (this.xsdElementDefinitionExist(o_xsdElement.getName())) {
	    				if (!this.xsdElementDefinitionExistAsClone(o_xsdElement)) {
	    					throw new IllegalArgumentException("Invalid xsd-element-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    				} else {
							b_skip = true;
						}	
	    			}
	    			
	    			if (!b_skip) {
		    			/* add element definition to list */
		    		    this.a_elementDefinitons.add(o_xsdElement);
	    			}
		    	} else if (e_xsdTagType == XSDType.Attribute) {
	    													net.forestany.forestj.lib.Global.ilogFiner("add attribute reference of: " + p_a_xsdTags.get(i_min));
	    			/* parse xs:attribute */
	    			XSDAttribute o_xsdAttribute = this.parseXSDAttribute(p_a_xsdTags, i_min);
	    			
	    			/* check if element defintion on level 0 has a primitive type */
	    			if (!a_primitiveTypes.contains(o_xsdAttribute.getType().toLowerCase())) {
	    				throw new IllegalArgumentException("Library does not support usage of non-primitive xsd types on element definitions on level '0' in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    			}
	    			
	    			/* skip flag for adding object to list */
					boolean b_skip = false;
	    			
	    			/* check if xs:attribute definition already exists */
	    			if (this.xsdAttributeDefinitionExist(o_xsdAttribute.getName())) {
	    				if (!this.xsdAttributeDefinitionExistAsClone(o_xsdAttribute)) {
	    					throw new IllegalArgumentException("Invalid xsd-attribute-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    				} else {
							b_skip = true;
						}
	    			}
	    			
	    			if (!b_skip) {
	    				/* add attribute definition to list */
	    				this.a_attributeDefinitions.add(o_xsdAttribute);
	    			}
		    	} else {
		    		/* could not parse xsd-definition */
		    		throw new IllegalArgumentException("Invalid xsd-definition in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
		    	}
		    	
	    		i_definitions++;
	    	}
	    }
	    
											    net.forestany.forestj.lib.Global.ilogFiner("found definitions: " + i_definitions);
											    net.forestany.forestj.lib.Global.ilogFiner("found complex types: " + i_complexTypes);
											    net.forestany.forestj.lib.Global.ilogFiner("");
											    net.forestany.forestj.lib.Global.ilogFiner("element definitions: " + this.a_elementDefinitons.size());
											    net.forestany.forestj.lib.Global.ilogFiner("attribute definitions: " + this.a_attributeDefinitions.size());
											    net.forestany.forestj.lib.Global.ilogFiner("divided elements: " + this.a_dividedElements.size());
											    net.forestany.forestj.lib.Global.ilogFiner("unresolved references: " + this.a_temp.size());
											    net.forestany.forestj.lib.Global.ilogFiner("");
	    
		/* max. iteration for unresolved references is 100 */
	    for (int foo = 0; foo < 10; foo++) {
	    	/* hash map for still unresolved references */
	    	java.util.List<java.util.Map.Entry<Integer, Integer>> a_anotherTemp = new java.util.ArrayList<java.util.Map.Entry<Integer, Integer>>();
	    	
	    	/* iterate each unresolved reference */
		    for (java.util.Map.Entry<Integer, Integer> o_entry : this.a_temp) {
		    	/* parse xs:element again */
				XSDElement o_xsdElement = this.parseXSDElement(p_a_xsdTags, o_entry.getKey(), true);
				
		    	/* parse found complex xs:element or xs:complexType tag in detail */
				if (parseXSDSchemaDividedElement(p_a_xsdTags, o_entry.getKey(), o_entry.getValue(), o_xsdElement)) {
					if ( (this.schemaElementDefinitionExist(o_xsdElement.getName())) && (!this.schemaElementDefinitionExistAsClone(o_xsdElement)) ) {
						throw new IllegalArgumentException("Invalid xsd-tag xs:element or xs:complexType already exists within schema at(" + (o_entry.getKey() + 1) + ".-element) \"" + p_a_xsdTags.get(o_entry.getKey()) + "\".");
					} else {
						/* last check if element does not already exists in definition list */
						if (!this.schemaElementDefinitionExistAsClone(o_xsdElement)) {
							this.a_dividedElements.add(o_xsdElement);
						}
					}
				} else {
					/* store xsd start tag index and end tag index for later iteration */
					a_anotherTemp.add(new java.util.AbstractMap.SimpleEntry<Integer, Integer>(o_entry.getKey(), o_entry.getValue()));
				}
		    }
		    
		    /* clear list for unresolved references */
		    this.a_temp.clear();
		    
		    /* add all still unresolved references */
		    for (java.util.Map.Entry<Integer, Integer> o_entry : a_anotherTemp) {
		    	a_temp.add(new java.util.AbstractMap.SimpleEntry<Integer, Integer>(o_entry.getKey(), o_entry.getValue()));
		    }
		    
		    /* clear hash map for still unresolved references */
		    a_anotherTemp.clear();
		    
		    										net.forestany.forestj.lib.Global.ilogFinest((foo + 1) + ". divided elements: " + this.a_dividedElements.size());
		    										net.forestany.forestj.lib.Global.ilogFinest((foo + 1) + ". unresolved references: " + this.a_temp.size());
		    
		    /* if we have no more unresolved references, we can break iteration */
		    if (this.a_temp.size() < 1) {
		    	break;
		    }
		}
	    
	    /* check if all references could be resolved */
	    if (this.a_temp.size() > 0) {
	    	throw new IllegalArgumentException("Not all references could be resolved, invalid xsd schema.");
		}
	
											    net.forestany.forestj.lib.Global.ilogFiner("found definitions: " + i_definitions);
											    net.forestany.forestj.lib.Global.ilogFiner("found complex types: " + i_complexTypes);
											    net.forestany.forestj.lib.Global.ilogFiner("");
											    net.forestany.forestj.lib.Global.ilogFiner("element definitions: " + this.a_elementDefinitons.size());
											    net.forestany.forestj.lib.Global.ilogFiner("attribute definitions: " + this.a_attributeDefinitions.size());
											    net.forestany.forestj.lib.Global.ilogFiner("divided elements: " + this.a_dividedElements.size());
											    net.forestany.forestj.lib.Global.ilogFiner("unresolved references: " + this.a_temp.size());
											    net.forestany.forestj.lib.Global.ilogFiner("");
	}
	
	/**
	 * Parse xsd content, based on XSDElement, XSDAttribute and XSDRestriction in relation to parend xsd element
	 * 
	 * @param p_a_xsdTags					list of xsd element tags
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_i_max						pointer where to end line iteration
	 * @param p_o_xsdParentElement			parent xsd element object
	 * @return								true - parent xsd element object can be used for xsd schema list, false - still some references within this xsd element are not resolved
	 * @throws IllegalArgumentException		value or structure within xsd file lines invalid
	 * @throws NullPointerException			value within xsd content missing or min. amount not available
	 */
	private boolean parseXSDSchemaDividedElement(java.util.List<String> p_a_xsdTags, int p_i_min, int p_i_max, XSDElement p_o_xsdParentElement) throws IllegalArgumentException, NullPointerException {
		int i_max = p_i_max;
	    boolean b_simpleContent = false;
	    boolean b_simpleType = false;
	    boolean b_handledFirstElementInterleaving = false;
	    
	    /* iterate all elements */
    	for (int i_min = p_i_min; i_min <= i_max; i_min++) {
    		/* get xsd type */
    		XSDType e_xsdTagType = getXSDType(p_a_xsdTags, i_min);
    		
    		/* check for xs:simpleContent and xs:simpleType */
    		if ( ( (e_xsdTagType == XSDType.ComplexType) && (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleContent) ) || 
    			( (e_xsdTagType == XSDType.Element) && (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.ComplexType) && (getXSDType(p_a_xsdTags, i_min + 2) == XSDType.SimpleContent) ) ) {
    			b_simpleContent = true;
    		}
    		
    		if ( ( (e_xsdTagType == XSDType.Element) && (getXSDType(p_a_xsdTags, i_min + 1) == XSDType.SimpleType) ) || (e_xsdTagType == XSDType.SimpleType) ) {
    			b_simpleType = true;
    		}
    		
    		if (!p_a_xsdTags.get(i_min).endsWith("/>")) {
	    		/* first xsd tag is type xs:element or xs:complexType */
	    		if ( (!b_handledFirstElementInterleaving) && ( (e_xsdTagType == XSDType.Element) && (!b_simpleContent) && (!b_simpleType) ) || ( (e_xsdTagType == XSDType.ComplexType) && (!b_simpleContent) ) ) {
	    			i_max--;
	    			continue;
	    		} else {
	    			b_handledFirstElementInterleaving = true;
	    		}
	    		
	    		/* overwrite value for end tag pointer */
	    		int i_nestedMax = -1;
	    		
											    		net.forestany.forestj.lib.Global.ilogFiner("before");
											    		net.forestany.forestj.lib.Global.ilogFiner("\t" + i_min + " ... " + i_max);
											    		net.forestany.forestj.lib.Global.ilogFiner("\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		
	    		/* save attributes in complex type until sequence end tag found */
	    		if (e_xsdTagType == XSDType.Sequence) {
	    			/* read minOccurs attribute out of xs:sequence tag */
	    			java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
	    			java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    			
	    		    if (o_matcher.find()) {
	    		    	p_o_xsdParentElement.setSequenceMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    		    }
	    		    
	    		    /* read maxOccurs attribute out of xs:sequence tag */
	    			o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    			
	    		    if (o_matcher.find()) {
	    		    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    		    		p_o_xsdParentElement.setSequenceMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    		    	} else {
	    		    		p_o_xsdParentElement.setSequenceMaxOccurs(-1);
	    		    	}
	    		    }
	    			
	    			while (!p_a_xsdTags.get(i_max).toLowerCase().contentEquals("</xs:sequence>")) {
	    				if (p_a_xsdTags.get(i_max).toLowerCase().startsWith("<xs:attribute")) {
		    				o_regex = java.util.regex.Pattern.compile("ref=\"([^\"]*)\"");
			    			java.util.regex.Matcher o_matcherRef = o_regex.matcher(p_a_xsdTags.get(i_max));
			    			
			    			o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
			    			java.util.regex.Matcher o_matcherName = o_regex.matcher(p_a_xsdTags.get(i_max));
			    			
			    			o_regex = java.util.regex.Pattern.compile("type=\"([^\"]*)\"");
			    			java.util.regex.Matcher o_matcherType = o_regex.matcher(p_a_xsdTags.get(i_max));
			    			
			    		    if (o_matcherRef.find()) { /* we have an attribute element with reference */
			    		        String s_referenceName = o_matcherRef.group(0).substring(5, o_matcherRef.group(0).length() - 1);
			    		    	
			    		        /* check if we have a duplicate */
			    		        if (this.getXSDAttribute(s_referenceName, p_o_xsdParentElement) != null) {
			    		        	throw new NullPointerException("Invalid xsd-tag xs:attribute (duplicate) at(" + (i_max + 1) + ".-element) \"" + p_a_xsdTags.get(i_max) + "\".");
			    		        }
			    		        
			    		    	if (this.xsdAttributeDefinitionExist(s_referenceName)) {
			    		    												net.forestany.forestj.lib.Global.ilogFinest("add and check attribute reference = " + s_referenceName);
			    		    												
			    		    		XSDAttribute o_xsdAttribute = this.getXSDAttributeDefinition(s_referenceName).clone();
			    		    		
			    		    		/* read name attribute out of xs:attribute tag */
			    		    		if ((o_matcherName.find())) {
			    		    			String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
			    		    			
			    		    			/* overwrite name value from reference, because it is dominant from the usage point */
			    		    			o_xsdAttribute.setName(s_name);
			    		    		}
			    		    												
			    		    		/* add xs:attribute object to parent element */
			    		    		p_o_xsdParentElement.getAttributes().add(o_xsdAttribute);
			    		    	} else {
			    		    		/* attribute definition does not exist */
			    		    		return false;
			    		    	}
			    		    } else if ((o_matcherName.find()) && (o_matcherType.find())) { /* we have an attribute element with name and type */
			    		    	/* read name and type attribute values of xs:attribute tag */
			    		    	String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
			    		    	String s_type = o_matcherType.group(0).substring(6, o_matcherType.group(0).length() - 1);
			    		    	
			    		    	/* check if we have a duplicate */
			    		        if (this.getXSDAttribute(s_type, p_o_xsdParentElement) != null) {
			    		        	throw new NullPointerException("Invalid xsd-tag xs:attribute (duplicate) at(" + (i_max + 1) + ".-element) \"" + p_a_xsdTags.get(i_max) + "\".");
			    		        }
  			    		        
			    		    	if (this.xsdAttributeDefinitionExist(s_type)) {
			    		    												net.forestany.forestj.lib.Global.ilogFinest("add and check attribute type reference = " + s_type);
			    		    												
			    		    		XSDAttribute o_xsdAttribute = this.getXSDAttributeDefinition(s_type).clone();
			    		    		
		    		    			/* overwrite name value from reference, because it is dominant from the usage point */
		    		    			o_xsdAttribute.setName(s_name);
			    		    												
			    		    		/* add xs:attribute object to parent element */
		    		    			p_o_xsdParentElement.getAttributes().add(o_xsdAttribute);
			    		    	} else if (this.xsdElementDefinitionExist(s_type)) {
																			net.forestany.forestj.lib.Global.ilogFinest("add and check element type reference = " + s_type);

									XSDElement o_xsdElementReference = this.getXSDElementDefinition(s_type).clone();
																		
									XSDAttribute o_xsdAttribute = new XSDAttribute(s_name);
									
									/* read required attribute out of xs:attribute tag */
									o_regex = java.util.regex.Pattern.compile("use=\"required\"");
									o_matcher = o_regex.matcher(p_a_xsdTags.get(i_max));
									
									if (o_matcher.find()) {
										o_xsdAttribute.setRequired(true);
									}
									
									/* read default attribute out of xs:attribute tag */
									o_regex = java.util.regex.Pattern.compile("default=\"([^\"]*)\"");
									o_matcher = o_regex.matcher(p_a_xsdTags.get(i_max));
									
									if (o_matcher.find()) {
										o_xsdAttribute.setDefault(o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1));
									}
									
									/* read fixed attribute out of xs:attribute tag */
									o_regex = java.util.regex.Pattern.compile("fixed=\"([^\"]*)\"");
									o_matcher = o_regex.matcher(p_a_xsdTags.get(i_max));
									
									if (o_matcher.find()) {
										o_xsdAttribute.setFixed(o_matcher.group(0).substring(7, o_matcher.group(0).length() - 1));
									}
									
									/* overwrite type value from reference, because it is dominant from the usage point */
									o_xsdAttribute.setType(o_xsdElementReference.getType());
									
									/* assume restrictions from reference */
									for (XSDRestriction o_restriction : o_xsdElementReference.getRestrictions()) {
										o_xsdAttribute.getRestrictions().add(o_restriction);
									}
																		
									/* add xs:attribute object to parent xsd element */
									p_o_xsdParentElement.getAttributes().add(o_xsdAttribute);
			    		    	} else {
			    		    		/* we do not have reference in definition pool */
			    		    		return false;
			    		    	}
			    		    } else {
			    		    	if (getXSDType(p_a_xsdTags, (i_max + 1)) == XSDType.SimpleType) {
			    		    		/* parse xs:attribute with simple type */
					    			XSDAttribute o_xsdAttribute = this.parseXSDAttributeWithSimpleType(p_a_xsdTags, i_max);
					    			
					    			/* skip flag for adding object to list */
									boolean b_skip = false;
					    			
					    			/* check if xs:attribute definition already exists */
					    			if (this.xsdAttributeDefinitionExist(o_xsdAttribute.getName())) {
					    				if (!this.xsdAttributeDefinitionExistAsClone(o_xsdAttribute)) {
					    					throw new IllegalArgumentException("Invalid xsd-attribute-definition (duplicate) in xsd-schema at(" + (i_max + 1) + ".-element) \"" + p_a_xsdTags.get(i_max) + "\".");
					    				} else {
											b_skip = true;
										}
					    			}
					    			
					    			if (!b_skip) {
					    				/* add attribute definition to list */
					    				this.a_attributeDefinitions.add(o_xsdAttribute);
					    			}
									
									XSDAttribute o_xsdAttributeReplace = new XSDAttribute();
									o_xsdAttributeReplace.setReference(o_xsdAttribute.getName());
									
									/* add xs:attribute object replacement to parent element */
		    		    			p_o_xsdParentElement.getAttributes().add(o_xsdAttributeReplace);
			    		    	} else {
				    		    	throw new NullPointerException("Invalid xsd-tag xs:attribute without a reference or name and type at(" + (i_max + 1) + ".-element) \"" + p_a_xsdTags.get(i_max) + "\".");
			    		    	}
			    		    }
	    				}
		    			
		    			i_max--;
		    		}
	    		} else if (e_xsdTagType == XSDType.Choice) { /* identify choice tag for parent element */
											    			net.forestany.forestj.lib.Global.ilogFiner("\tChoice: " + i_min + " to Parent Element: " + p_o_xsdParentElement.getName());
											    			net.forestany.forestj.lib.Global.ilogFiner("\tChoice: " + p_a_xsdTags.get(i_min) + " to Parent Element: " + p_o_xsdParentElement.getName());
		    		/* parse xs:choice */
		    		XSDElement o_xsdChoice = this.parseXSDChoice(p_a_xsdTags, i_min);
		    		
		    		/* set choice flag for parent element */ 
		    		p_o_xsdParentElement.setChoice(true);
		    		
		    		/* set minOccurs for parent element */
		    		if (o_xsdChoice.getMinOccurs() != 1) {
		    			p_o_xsdParentElement.setMinOccurs(o_xsdChoice.getMinOccurs());	
		    		}
		    		
		    		/* set maxOccurs for parent element */
		    		if (o_xsdChoice.getMaxOccurs() != 1) {
		    			p_o_xsdParentElement.setMaxOccurs(o_xsdChoice.getMaxOccurs());	
		    		}
	    		} else if (b_simpleType) { /* handle xs:simpleType */
	    			/* parse xs:simpleType */
	    			XSDElement o_xsdElement = this.parseXSDElementWithSimpleType(p_a_xsdTags, i_min);
	    			
	    			/* skip flag for adding object to list */
					boolean b_skip = false;
	    			
	    			/* check if xs:element definition already exists */
	    			if (this.xsdElementDefinitionExist(o_xsdElement.getName())) {
	    				if (!this.xsdElementDefinitionExistAsClone(o_xsdElement)) {
	    					throw new IllegalArgumentException("Invalid xsd-element-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    				} else {
							b_skip = true;
						}
	    			}
	    			
	    			if (!b_skip) {
	    				/* add element definition to list */
	    				this.a_elementDefinitons.add(o_xsdElement);
	    			}
	    			
					XSDElement o_xsdElementReplace = new XSDElement();
					o_xsdElementReplace.setReference(o_xsdElement.getName());
					
					/* add xs:attribute object replacement to parent element */
	    			p_o_xsdParentElement.getChildren().add(o_xsdElementReplace);
	    			
	    			boolean b_simpleTypeFirst = true;
	    			
	    			/* find start of simpleType interlacing */
	    			if (!p_a_xsdTags.get(i_min).toLowerCase().startsWith("<xs:simpletype")) {
	    				b_simpleTypeFirst = false;
	    			}
	    			
	    			/* end tag pointer for nested xs:simpleType */
	    			int i_tempMax = i_min;
	    			
	    			/* find end of simpleType interlacing */
	    			while (!p_a_xsdTags.get(i_tempMax).toLowerCase().startsWith("</xs:simpletype")) {
	    				if (i_tempMax == p_i_max) {
	    					/* forbidden state - interlacing is not valid in xsd-schema */
	    					throw new IllegalArgumentException("Invalid nested xsd-tag xs:simpleType at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    				}
	    				
	    				i_tempMax++;
	    			}
	    			
	    			/* set overwrite value for end tag pointer */
	    			i_nestedMax = i_tempMax;
	    			
	    			if (!b_simpleTypeFirst) {
	    				i_nestedMax++;
	    			}
	    			
	    			/* set xml tag pointer to skip processed simpleType tag */
	    			i_min = i_nestedMax;
	    			
	    			b_simpleType = false;
											    			net.forestany.forestj.lib.Global.ilogFiner("after");
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + i_min + " ... " + i_max);
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		} else if (b_simpleContent) { /* handle xs:simpleContent */
	    			/* parse xs:simpleContent */
	    			XSDElement o_xsdElement = this.parseXSDComplexTypeWithSimpleContent(p_a_xsdTags, i_min);
	    			
	    			boolean b_simpleContentFirst = true;
	    			
	    			/* find start of simpleContent interlacing */
	    			if (!p_a_xsdTags.get(i_min).toLowerCase().startsWith("<xs:simplecontent")) {
	    				b_simpleContentFirst = false;
	    			}
	    			
	    			/* end tag pointer for nested xs:simpleContent */
	    			int i_tempMax = i_min;
	    			
	    			/* find end of simpleContent interlacing */
	    			while (!p_a_xsdTags.get(i_tempMax).toLowerCase().startsWith("</xs:simplecontent")) {
	    				if (i_tempMax == p_i_max) {
	    					/* forbidden state - interlacing is not valid in xsd-schema */
	    					throw new IllegalArgumentException("Invalid nested xsd-tag xs:simpleContent at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    				}
	    				
	    				i_tempMax++;
	    			}
	    			
	    			/* set overwrite value for end tag pointer */
	    			i_nestedMax = i_tempMax;
	    			
	    			if (!b_simpleContentFirst) {
	    				i_nestedMax++;
	    			}
	    			
	    			if (o_xsdElement != null) {
	    				/* overwrite parent xsd element with xsd element simple content if simple content name and parent xsd element name are equal */
	    				if (p_o_xsdParentElement.getName().contentEquals(o_xsdElement.getName())) {
			    			p_o_xsdParentElement.setName(o_xsdElement.getName());
			    			p_o_xsdParentElement.setType(o_xsdElement.getType());
			    			p_o_xsdParentElement.setMapping(o_xsdElement.getMapping());
			    			p_o_xsdParentElement.setReference(o_xsdElement.getReference());
			    			p_o_xsdParentElement.setMinOccurs(o_xsdElement.getMinOccurs());
			    			p_o_xsdParentElement.setMaxOccurs(o_xsdElement.getMaxOccurs());
			    			p_o_xsdParentElement.setChoice(o_xsdElement.getChoice());
			    			p_o_xsdParentElement.setRestriction(o_xsdElement.getRestriction());
			    			p_o_xsdParentElement.setIsArray(o_xsdElement.getIsArray());
			    			p_o_xsdParentElement.setSequenceMinOccurs(o_xsdElement.getSequenceMinOccurs());
			    			p_o_xsdParentElement.setSequenceMaxOccurs(o_xsdElement.getSequenceMaxOccurs());
			    			p_o_xsdParentElement.setSimpleType(o_xsdElement.getSimpleType());
			    			p_o_xsdParentElement.setSimpleContent(o_xsdElement.getSimpleContent());
			    			
			    			for (XSDElement o_element : o_xsdElement.getChildren()) {
			    				p_o_xsdParentElement.getChildren().add(o_element.clone());
			    			}
			    			
			    			for (XSDAttribute o_attribute : o_xsdElement.getAttributes()) {
			    				p_o_xsdParentElement.getAttributes().add(o_attribute.clone());
			    			}
			    			
			    			for (XSDRestriction o_restriction : o_xsdElement.getRestrictions()) {
			    				p_o_xsdParentElement.getRestrictions().add(o_restriction.clone());
			    			}
	    				} else { /* add xsd element with simple content to schema list and add a reference to parent xsd element as child object */
	    					if ( (this.schemaElementDefinitionExist(o_xsdElement.getName())) && (!this.schemaElementDefinitionExistAsClone(o_xsdElement)) ) {
		    					throw new IllegalArgumentException("Invalid xsd-tag xs:element or xs:complexType already exists within schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
		    				} else {
		    					/* last check if element does not already exists in definition list */
		    					if (!this.schemaElementDefinitionExistAsClone(o_xsdElement)) {
		    						this.a_dividedElements.add(o_xsdElement);
		    					}
		    					
		    					/* create new xsd element as reference replacement */
		    					XSDElement o_xsdElementReplace = new XSDElement();
								o_xsdElementReplace.setReference(o_xsdElement.getName());
								
								/* add xs:element object replacement to parent element */
				    			p_o_xsdParentElement.getChildren().add(o_xsdElementReplace);
		    				}
	    				}
		    		} else {
		    			/* could not parse simple content, maybe because of unresolved references */
	    				return false;
	    			}
	    			
	    			/* set xml tag pointer to skip processed simpleContent tag */
	    			i_min = i_nestedMax;
	    			
	    			b_simpleContent = false;
											    			net.forestany.forestj.lib.Global.ilogFiner("after");
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + i_min + " ... " + i_max);
											    			net.forestany.forestj.lib.Global.ilogFiner("\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		} else if ( (e_xsdTagType == XSDType.Element) || (e_xsdTagType == XSDType.ComplexType) ) { /* handle interlacing xs:element or xs:complexType tag (no one-liner), because we support this level and replace it with a reference */
	    			/* parse xs:element */
	    			XSDElement o_xsdElement = this.parseXSDElement(p_a_xsdTags, i_min, true);
	    				
	    		    int i_tempMin = i_min + 1;
	    			int i_level = 0;
	    			
	    			/* look for end of nested xs:element or xs:complexType tag */
	    			while (
	    				( (e_xsdTagType == XSDType.Element) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:element>")) ) || 
	    				( (e_xsdTagType == XSDType.ComplexType) && (!p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:complextype>")) ) || 
	    				(i_level != 0)
	    			)
	    			{
	    				if (e_xsdTagType == XSDType.Element) {
	    					/* handle other interlacing in current nested xs:element tag */
		    				if ( (p_a_xsdTags.get(i_tempMin).toLowerCase().startsWith("<xs:element")) && (!p_a_xsdTags.get(i_tempMin).endsWith("/>")) ) {
		    					i_level++;
		    				} else if (p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:element>")) {
		    					i_level--;
		    				}
	    				} else if (e_xsdTagType == XSDType.ComplexType) {
	    					/* handle other interlacing in current nested xs:complexType tag */
		    				if ( (p_a_xsdTags.get(i_tempMin).toLowerCase().startsWith("<xs:complextype")) && (!p_a_xsdTags.get(i_tempMin).endsWith("/>")) ) {
		    					i_level++;
		    				} else if (p_a_xsdTags.get(i_tempMin).toLowerCase().equals("</xs:complextype>")) {
		    					i_level--;
		    				}
	    				}
	    				
	    				if (i_tempMin == i_max) {
	    					/* forbidden state - interlacing is not valid in xsd-schema */
	    					throw new IllegalArgumentException("Invalid nested xsd-tag xs:element at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    				}
	    				
	    				i_tempMin++;
		    		}
	    			
											    			net.forestany.forestj.lib.Global.ilogFiner("interlacing");
											    			net.forestany.forestj.lib.Global.ilogFiner(i_min + " ... " + i_tempMin);
											    			net.forestany.forestj.lib.Global.ilogFiner(p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_tempMin));
					
					/* parse found complex xs:element or xs:complexType tag in detail */
	    			if (parseXSDSchemaDividedElement(p_a_xsdTags, i_min, i_tempMin, o_xsdElement)) {
	    				if ( (this.schemaElementDefinitionExist(o_xsdElement.getName())) && (!this.schemaElementDefinitionExistAsClone(o_xsdElement)) ) {
	    					throw new IllegalArgumentException("Invalid xsd-tag xs:element or xs:complexType already exists within schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    				} else {
	    					/* last check if element does not already exists in definition list */
	    					if (!this.schemaElementDefinitionExistAsClone(o_xsdElement)) {
	    						this.a_dividedElements.add(o_xsdElement);
	    					}
	    					
	    					/* create new xsd element as reference replacement */
	    					XSDElement o_xsdElementReplace = new XSDElement();
							o_xsdElementReplace.setReference(o_xsdElement.getName());
							
							/* add xs:element object replacement to parent element */
			    			p_o_xsdParentElement.getChildren().add(o_xsdElementReplace);
	    				}
	    			} else {
	    				/* found element could not be parsed, maybe because of unresolved references */
    					return false;
	    			}
	    			
	    			i_min = i_tempMin;
	    			
	    			/* set overwrite value for end tag pointer */
	    			i_nestedMax = i_tempMin;
	    		}
	    		
	    		/* set end tag pointer */
	    		int i_endTagPointer = i_max;
	    		
	    		/* overwrite end tag pointer if we had a nested simpleType or simpleContent */
	    		if (i_nestedMax > 0) {
	    			i_endTagPointer = i_nestedMax;
	    		}
	    		
											    		net.forestany.forestj.lib.Global.ilogFiner("endTagPointer");
											    		net.forestany.forestj.lib.Global.ilogFiner("\t\t" + i_min + " ... " + i_endTagPointer);
											    		net.forestany.forestj.lib.Global.ilogFiner("\t\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_endTagPointer));
											    		
											    		net.forestany.forestj.lib.Global.ilogFiner("after");
											    		net.forestany.forestj.lib.Global.ilogFiner("\t" + i_min + " ... " + i_max);
											    		net.forestany.forestj.lib.Global.ilogFiner("\t" + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		
	    		/* if we still have no closing tag, then our xsd schema is invalid */
	    		if (!p_a_xsdTags.get(i_endTagPointer).startsWith("</")) {
	    			throw new IllegalArgumentException("Invalid xsd-tag(" + p_a_xsdTags.get(i_endTagPointer) + ") is not closed in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		}
	    		
	    		/* get xsd end type */
		    	XSDType e_xsdEndTagType = getXSDType(p_a_xsdTags, i_endTagPointer);
	    		
	    		/* xsd type and xsd close type must match */
	    		if (e_xsdTagType != e_xsdEndTagType) {
	    			throw new IllegalArgumentException("Invalid xsd-tag-type \"" + p_a_xsdTags.get(i_endTagPointer) + "\" for closing in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		} else {
	    			/* if we had a no nested simpleType or simpleContent, decrease xml end tag pointer */
	    			if (i_nestedMax < 0) {
	    				i_max--;
	    			}
	    		}
	    	} else {
											    		net.forestany.forestj.lib.Global.ilogFiner("\tElement: " + i_min + " ... " + i_max);
											    		net.forestany.forestj.lib.Global.ilogFiner("\tElement: " + p_a_xsdTags.get(i_min) + " ... " + p_a_xsdTags.get(i_max));
	    		
	    		if ( (e_xsdTagType == XSDType.Element) || (e_xsdTagType == XSDType.ComplexType) ) {
	    			java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("ref=\"([^\"]*)\"");
	    			java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    			
	    			o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
	    			java.util.regex.Matcher o_matcherName = o_regex.matcher(p_a_xsdTags.get(i_min));
	    			
	    			o_regex = java.util.regex.Pattern.compile("type=\"xs:([^\"]*)\"");
	    			java.util.regex.Matcher o_matcherPrimitiveType = o_regex.matcher(p_a_xsdTags.get(i_min));
	    			
	    			o_regex = java.util.regex.Pattern.compile("type=\"([^\"]*)\"");
	    			java.util.regex.Matcher o_matcherType = o_regex.matcher(p_a_xsdTags.get(i_min));

	    			boolean b_nameAttributeFound = o_matcherName.find();
	    			
	    			if (o_matcher.find()) { /* must have a reference attribute */
	    		        String s_referenceName = o_matcher.group(0).substring(5, o_matcher.group(0).length() - 1);
	    		        
	    		        /* check if we have a duplicate */
	    		        if (this.getXSDElement(s_referenceName, p_o_xsdParentElement) != null) {
	    		        	throw new IllegalArgumentException("Invalid xsd-tag xs:element (duplicate) at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		        }
	    		        
	    		        /* xsd object for reference */
	    		    	XSDElement o_xsdElementReference = null;
	    		    	
	    		    	if (this.xsdElementDefinitionExist(s_referenceName)) { /* check if we have a reference within our element definition list */
	    		    		o_xsdElementReference = this.getXSDElementDefinition(s_referenceName).clone();
	    		    	} else if (this.schemaElementDefinitionExist(s_referenceName)) { /* check if we have a reference within our schema definition list */
	    		    		o_xsdElementReference = this.getSchemaElementDefinition(s_referenceName);
	    		    	}
	    		    	
	    		    	/* check if we found a reference */
	    		        if (o_xsdElementReference != null) {
	    		    												net.forestany.forestj.lib.Global.ilogFinest("add element reference = " + s_referenceName);
	    		    		
	    		    		/* read name attribute out of xs:element tag */
	    		    		if (o_matcherName.find()) {
			    				String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
			    				
			    				/* overwrite name value from reference, because it is dominant from the usage point */
			    				o_xsdElementReference.setName(s_name);
	    		    		}
	    		    		
	    		    		/* read minOccurs attribute out of xs:element tag */
	    	    			o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
	    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    	    			
	    	    		    if (o_matcher.find()) {
	    	    		    	o_xsdElementReference.setMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	    		    }
	    	    		    
	    	    		    /* read maxOccurs attribute out of xs:element tag */
	    	    			o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
	    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    	    			
	    	    		    if (o_matcher.find()) {
	    	    		    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    	    		    		o_xsdElementReference.setMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	    		    	} else if (o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    	    		    		o_xsdElementReference.setMaxOccurs(-1);
	    	    		    	}
	    	    		    }
	    		    		
	    	    		    /* library does not support multiple occurrences of the same xml element without a list definition */
	    	    			if ( (!o_xsdElementReference.getChoice()) && (!p_o_xsdParentElement.getMapping().contains("ArrayList(")) && (o_xsdElementReference.getMaxOccurs() > 1) ) {
	    	    				throw new IllegalArgumentException("Library does not support multiple occurrences of the same xml element without a list definition in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    	    			}
	    	    		    
	    	    		    /* add xs:element to parent element */
	    	    			p_o_xsdParentElement.getChildren().add(o_xsdElementReference);
	    		    	} else {
	    		    		/* we do not have reference in definition pool */
	    		    		return false;
	    		    	}
	    			} else if ((b_nameAttributeFound) && (o_matcherPrimitiveType.find())) { /* must have a name and primitive type attribute which are equal */
	    				String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
	    				String s_type = o_matcherPrimitiveType.group(0).substring(9, o_matcherPrimitiveType.group(0).length() - 1);
	    				
	    				if (!s_name.contentEquals(s_type)) {
	    					XSDElement o_xsdElement = new XSDElement(s_name, s_type);
	    					
	    					/* read mapping attribute out of xs:element tag */
	    	    			o_regex = java.util.regex.Pattern.compile("mapping=\"([^\"]*)\"");
	    	    			java.util.regex.Matcher o_matcherMapping = o_regex.matcher(p_a_xsdTags.get(i_min));
	    	    			
	    	    		    if (o_matcherMapping.find()) {
	    	    		        o_xsdElement.setMapping(o_matcherMapping.group(0).substring(9, o_matcherMapping.group(0).length() - 1));
	    	    		    }
	    	    		    
	    	    		    /* skip flag for adding object to list */
							boolean b_skip = false;
	    	    		    
	    	    		    /* check if xs:element definition already exists */
	    	    			if (this.xsdElementDefinitionExist(o_xsdElement.getName())) {
	    	    				if (!this.xsdElementDefinitionExistAsClone(o_xsdElement)) {
	    	    					throw new IllegalArgumentException("Invalid xsd-element-definition (duplicate) in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    	    				} else {
									b_skip = true;
								}	
	    	    			}
	    	    		    
	    	    			if (!b_skip) {
	    	    				/* add element definition to list */
	    	    				this.a_elementDefinitons.add(o_xsdElement);
	    	    			}
							
							XSDElement o_xsdElementReplace = new XSDElement();
							o_xsdElementReplace.setReference(o_xsdElement.getName());
							
							/* add xs:attribute object replacement to parent element */
			    			p_o_xsdParentElement.getChildren().add(o_xsdElementReplace);
			    		} else {
		    														net.forestany.forestj.lib.Global.ilogFiner("create and read xsd-element as array element definition = " + s_type);
		    				XSDElement o_xsdElement = new XSDElement(s_name, s_type);
		    				
		    				/* set xsd-element as array */
		    				o_xsdElement.setIsArray(true);
		    				
		    				/* read minOccurs attribute out of xs:element tag */
	    	    			o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
	    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    	    			
	    	    		    if (o_matcher.find()) {
	    	    		        o_xsdElement.setMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	    		    }
	    	    		    
	    	    		    /* read maxOccurs attribute out of xs:element tag */
	    	    			o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
	    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    	    			
	    	    		    if (o_matcher.find()) {
	    	    		    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    	    		    		o_xsdElement.setMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	    		    	} else if (o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    	    		    		o_xsdElement.setMaxOccurs(-1);
	    	    		    	}
	    	    		    }
		    				
		    				/* add xs:element to parent element */
	    	    		    p_o_xsdParentElement.getChildren().add(o_xsdElement);
	    				}
	    			} else if ((b_nameAttributeFound) && (o_matcherType.find())) { /* must have a name and type attribute, where type attribute value is used as reference */
	    				/* read name and type attribute value */
	    				String s_name = o_matcherName.group(0).substring(6, o_matcherName.group(0).length() - 1);
	    				String s_typeName = o_matcherType.group(0).substring(6, o_matcherType.group(0).length() - 1);
	    		        
	    		        /* check if we have a duplicate */
	    		        if (this.getXSDElement(s_typeName, p_o_xsdParentElement) != null) {
	    		        	throw new IllegalArgumentException("Invalid xsd-tag xs:element (duplicate) at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		        }
	    		        
	    		        /* xsd object for reference */
	    		    	XSDElement o_xsdElementReference = null;
	    		    	
	    		    	if (this.xsdElementDefinitionExist(s_typeName)) { /* check if we have a reference within our element definition list */
	    		    		o_xsdElementReference = this.getXSDElementDefinition(s_typeName).clone();
	    		    	} else if (this.schemaElementDefinitionExist(s_typeName)) { /* check if we have a reference within our schema definition list */
	    		    		o_xsdElementReference = this.getSchemaElementDefinition(s_typeName);
	    		    	}
	    		    	
	    		    	/* check if we found a reference */
	    		        if (o_xsdElementReference != null) {
	    		    												net.forestany.forestj.lib.Global.ilogFinest("add element reference = " + s_typeName);
	    		    		
	    		    		/* overwrite name value from reference, because it is dominant from the usage point */
	    		    		o_xsdElementReference.setName(s_name);
	    		    		
	    		    		/* even overwrite type value, because all we wanted is to check if the reference exists */
	    		    		o_xsdElementReference.setType(s_typeName);
	    		    		
	    		    		/* read minOccurs attribute out of xs:element tag */
	    	    			o_regex = java.util.regex.Pattern.compile("minOccurs=\"([^\"]*)\"");
	    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    	    			
	    	    		    if (o_matcher.find()) {
	    	    		    	o_xsdElementReference.setMinOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	    		    }
	    	    		    
	    	    		    /* read maxOccurs attribute out of xs:element tag */
	    	    			o_regex = java.util.regex.Pattern.compile("maxOccurs=\"([^\"]*)\"");
	    	    			o_matcher = o_regex.matcher(p_a_xsdTags.get(i_min));
	    	    			
	    	    		    if (o_matcher.find()) {
	    	    		    	if (!o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    	    		    		o_xsdElementReference.setMaxOccurs(Integer.parseInt(o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1)));
	    	    		    	} else if (o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1).contentEquals("unbounded")) {
	    	    		    		o_xsdElementReference.setMaxOccurs(-1);
	    	    		    	}
	    	    		    }
	    		    		
	    	    		    /* library does not support multiple occurrences of the same xml element without a list definition */
	    	    			if ( (!o_xsdElementReference.getChoice()) && (!p_o_xsdParentElement.getMapping().contains("ArrayList(")) && (o_xsdElementReference.getMaxOccurs() > 1) ) {
	    	    				throw new IllegalArgumentException("Library does not support multiple occurrences of the same xml element without a list definition in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    	    			}
	    	    		    
	    	    		    /* add xs:element to parent element */
	    	    			p_o_xsdParentElement.getChildren().add(o_xsdElementReference);
	    		    	} else {
	    		    		/* we do not have reference in definition pool */
	    		    		return false;
	    		    	}
	    			} else {
	    				throw new IllegalArgumentException("Invalid xsd-tag xs:element without a reference at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    			}
	    		} else {
	    			throw new IllegalArgumentException("Invalid xsd-tag in xsd-schema at(" + (i_min + 1) + ".-element) \"" + p_a_xsdTags.get(i_min) + "\".");
	    		}
	    	}
	    }
    	
    	return true;
	}
	
	/**
	 * Check if a schema element definition exists by reference name
	 * 
	 * @param p_s_referenceName		reference name of schema element definition
	 * @return						true - definition exists, false - definition does not exist
	 */
	private boolean schemaElementDefinitionExist(String p_s_referenceName) {
		boolean b_found = false;
		
		for (XSDElement o_xsdElement : this.a_dividedElements) {
			if (o_xsdElement.getName().contentEquals(p_s_referenceName)) {
				b_found = true;
			}
		}
		
		return b_found;
	}
	
	/**
	 * Check if a schema definition exists by element object as clone
	 * 
	 * @param p_o_xsdElement		xsd element object
	 * @return						true - schema exists as clone, false - schema does not exist as clone, but with other values
	 */
	private boolean schemaElementDefinitionExistAsClone(XSDElement p_o_xsdElement) {
		boolean b_found = false;
		
		for (XSDElement o_xsdElement : this.a_dividedElements) {
			if ( (o_xsdElement.getName().contentEquals(p_o_xsdElement.getName())) && (o_xsdElement.isEqual(p_o_xsdElement)) ) {
				b_found = true;
			}
		}
		
		return b_found;
	}
		
	/**
	 * Get schema element definition exists by reference name
	 * 
	 * @param p_s_referenceName		reference name of schema element definition
	 * @return						xsd element object as schema definition
	 */
	private XSDElement getSchemaElementDefinition(String p_s_referenceName) {
		XSDElement o_xsdElement = null;
		
		for (XSDElement o_xsdElementObject : this.a_dividedElements) {
			if (o_xsdElementObject.getName().contentEquals(p_s_referenceName)) {
				o_xsdElement = o_xsdElementObject.clone();
			}
		}
		
		return o_xsdElement;
	}
	
	/* encoding data to XML with XSD schema */
	
	/**
	 * Encode java object to a xml content string
	 * 
	 * @param p_o_object										source java object to encode xml information
	 * @return													encoded xml information from java object as string
	 * @throws java.io.IOException								cannot create or access destination xml file
	 * @throws NullPointerException								value in schema or expected element is not available
	 * @throws IllegalArgumentException							condition failed for decoding xml correctly
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 * @throws ClassNotFoundException							could not retrieve class by string class name
	 * @throws java.text.ParseException							could not parse java.util.Date
	 */
	public String xmlEncode(Object p_o_object) throws java.io.IOException, NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException, java.text.ParseException {
		if (this.o_root == null) {
			throw new NullPointerException("Cannot encode data. Schema is null.");
		}
		
		if (p_o_object == null) {
			throw new NullPointerException("Cannot encode data. Object is null.");
		}
		
		/* set level for PrintIdentation to zero */
		this.i_level = 0;
		
		/* add xml header to file */
		String s_xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + this.s_lineBreak;
		
		XSDElement o_xsdElement = this.o_root;
		
		/* maybe input object type is not the expected root, but just a sub tree of a valid xsd schema, so we look for it in the element definitions */
		if (!p_o_object.getClass().getTypeName().contentEquals(o_xsdElement.getMapping())) {
			String s_typeName = p_o_object.getClass().getTypeName();
			boolean b_list = false;
			
			/* check if object which will be encoded is actually a java.util.List */
			if (p_o_object instanceof java.util.List) {
				@SuppressWarnings("unchecked")
				java.util.List<Object> o_temp = (java.util.List<Object>)p_o_object;
				
				/* thus we must find the real class of an list object */
				for (Object o_foo : o_temp) {
					if (o_foo != null) {
						s_typeName = o_foo.getClass().getTypeName();
						b_list = true;
						break;
					}
				}
			}
			
			for (XSDElement o_temp : this.a_elementDefinitons) {
				/* find xml element as element definition of xsd schema */
				if (
					(!b_list) && (s_typeName.contentEquals(o_temp.getMapping()))
				||
					(b_list) && ( (o_temp.getMapping().contains("(")) && (o_temp.getMapping().contains(")")) && (s_typeName.contentEquals( o_temp.getMapping().substring(o_temp.getMapping().indexOf("(") + 1, o_temp.getMapping().indexOf(")")) )) )
				) {
															net.forestany.forestj.lib.Global.ilogFiner("object type " + s_typeName + ", is not the correct root element of schema, but a valid sub tree, so take this sub tree as entry point for encoding");
					
					/* update schema element to encode xml tree recursively with that sub tree of xsd schema */
					o_xsdElement = o_temp;
					break;
				}
			}
		}
		
		/* encode data to xml recursive */
		s_xml += this.xmlEncodeRecursive(o_xsdElement, p_o_object);
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("Encoded XML:" + this.s_lineBreak + s_xml);
		
		/* return xml content string */
		return s_xml;
	}
	
	/**
	 * Encode java object to a xml file, keep existing xml file
	 * 
	 * @param p_o_object										source java object to encode xml information		
	 * @param p_s_xmlFile										destination xml file to save encoded xml information
	 * @throws java.io.IOException								cannot create or access destination xml file
	 * @throws NullPointerException								value in schema or expected element is not available
	 * @throws IllegalArgumentException							condition failed for decoding xml correctly
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 * @throws ClassNotFoundException							could not retrieve class by string class name
	 * @throws java.text.ParseException							could not parse java.util.Date
	 */
	public void xmlEncode(Object p_o_object, String p_s_xmlFile) throws java.io.IOException, NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException, java.text.ParseException {
		this.xmlEncode(p_o_object, p_s_xmlFile, false);
	}
	
	/**
	 * Encode java object to a xml file
	 * 
	 * @param p_o_object										source java object to encode xml information		
	 * @param p_s_xmlFile										destination xml file to save encoded xml information
	 * @param p_b_overwrite										true - overwrite existing xml file, false - keep existing xml file
	 * @return													file object with encoded xml content
	 * @throws java.io.IOException								cannot create or access destination xml file
	 * @throws NullPointerException								value in schema or expected element is not available
	 * @throws IllegalArgumentException							condition failed for decoding xml correctly
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 * @throws ClassNotFoundException							could not retrieve class by string class name
	 * @throws java.text.ParseException							could not parse java.util.Date
	 */
	public File xmlEncode(Object p_o_object, String p_s_xmlFile, boolean p_b_overwrite) throws java.io.IOException, NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException, java.text.ParseException {
		/* encode java object to xml string */
		String s_xml = this.xmlEncode(p_o_object);
		
		/* if file does not exist we must create an new file */
		if (!File.exists(p_s_xmlFile)) {
			if (p_b_overwrite) {
				p_b_overwrite = false;
			}
		}
		
		/* open (new) file */
		File o_file = new File(p_s_xmlFile, !p_b_overwrite);
		/* save xml encoded data into file */
		o_file.replaceContent(s_xml);
		
												net.forestany.forestj.lib.Global.ilogFiner("written encoded xml to file");
		
		/* return file object */
		return o_file;
	}
	
	/**
	 * Recursive method to encode java object and it's fields to a xml string
	 * 
	 * @param p_o_xmlSchemaElement			current xml schema element with additional information for encoding
	 * @param p_o_object					source java object to encode xml information
	 * @param p_b_parentIsCollection		hint that the parent xml element is an array collection
	 * @return								encoded xml information from java object as string
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding xml correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 * @throws ParseException				could not parse java.util.Date
	 */
	private String xmlEncodeRecursive(XSDElement p_o_xsdElement, Object p_o_object) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException, java.text.ParseException {
		String s_xml = "";
		
		/* start xml tag */
		s_xml += this.printIndentation() + "<" + p_o_xsdElement.getName();
		
		/* check if current xml element should have attributes */
		if (p_o_xsdElement.getAttributes().size() > 0) {
			/* iterate all xml attributes */
			for (XSDAttribute o_xsdAttribute : p_o_xsdElement.getAttributes()) {
				/* get value for xml attribute */
				String s_attributeValue = this.getAttributeValue(o_xsdAttribute, p_o_object);
				
				/* if attribute is required but value is empty, throw exception */
				if ( (o_xsdAttribute.getRequired()) && (net.forestany.forestj.lib.Helper.isStringEmpty(s_attributeValue)) ) {
					throw new NullPointerException("Missing attribute value for xs:attribute[" + p_o_object.getClass().getSimpleName() + "." + o_xsdAttribute.getName() + "]");
				}
				
				/* if attribute value is not empty, add it to xml element */
				if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_attributeValue)) {
					s_xml += " " + o_xsdAttribute.getName() + "=\"" + s_attributeValue + "\"";
				}
			}
		}
		
		/* end tag */
		s_xml += ">";
		s_xml += this.s_lineBreak;
		
		/* if we have xs:element definition with no type, exact one child element and mapping contains ":" and mapping does NOT end with "[]"
		 * we have to iterate a list of object and must print multiple xml elements
		 * otherwise we have usual xs:element definitions for current element
		 */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_o_xsdElement.getType())) && (p_o_xsdElement.getMapping().contains(":")) && (p_o_xsdElement.getChildren().size() == 1) && (!p_o_xsdElement.getMapping().endsWith("[]")) ) {
			/* cast current object as list with unknown generic type */
			java.util.List<?> a_objects = (java.util.List<?>)p_o_object;
			
			/* check minOccurs attribute and current list size */
			if ( (p_o_xsdElement.getChildren().get(0).getMinOccurs() > 0) && (a_objects.size() == 0) ) {
				throw new IllegalArgumentException("Not enough [" + p_o_xsdElement.getChildren().get(0).getName() + "] objects. The minimum number is " + p_o_xsdElement.getChildren().get(0).getMinOccurs());
			}
			
			/* check maxOccurs attribute and current list size */
			if ( (p_o_xsdElement.getChildren().get(0).getMaxOccurs() >= 0) && (a_objects.size() > p_o_xsdElement.getChildren().get(0).getMaxOccurs()) ) {
				throw new IllegalArgumentException("Too many [" + p_o_xsdElement.getChildren().get(0).getName() + "] objects. The maximum number is " + p_o_xsdElement.getChildren().get(0).getMaxOccurs());
			}
			
			/* increase level for PrintIdentation */
			this.i_level++;
			
			/* iterate objects in list and encode data to xml recursively */
			for (int i = 0; i < a_objects.size(); i++) {
				s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), a_objects.get(i));
			}
			
			/* decrease level for PrintIdentation */
			this.i_level--;
		} else {
			/* increase level for PrintIdentation */
			this.i_level++;
			
			/* create choice counter */
			int i_choiceCnt = 0;

			/* iterate children elements of current xsd element, but not if mapping is not null and ends with "[]" -> primitive array */
			if ( (net.forestany.forestj.lib.Helper.isStringEmpty( p_o_xsdElement.getMapping() )) || ( (!net.forestany.forestj.lib.Helper.isStringEmpty( p_o_xsdElement.getMapping() )) && (!p_o_xsdElement.getMapping().endsWith("[]")) ) ) {
				/* iterate all xs:elements of current element */
				for (XSDElement o_xsdElement : p_o_xsdElement.getChildren()) {
					/* if xs:element has no type we may have a special object definition or another list of objects */
					if (net.forestany.forestj.lib.Helper.isStringEmpty(o_xsdElement.getType())) {
						/* variable for object data */
						Object o_object = null;
						
						/* get java type */
						String s_javaType = o_xsdElement.getMapping();
						
						if (s_javaType.contentEquals("_skipLevel_")) {
							o_object = p_o_object;
						} else {
							/* remove enclosure of java type if it exists */
							if (o_xsdElement.getMapping().contains(":")) {
								s_javaType = s_javaType.substring(0, s_javaType.indexOf(":"));
							} else {
								/* remove package prefix */
								if (s_javaType.contains(".")) {
									s_javaType = s_javaType.substring(s_javaType.lastIndexOf(".") + 1, s_javaType.length());
								}
								
								/* remove internal class prefix */
								if (s_javaType.contains("$")) {
									s_javaType = s_javaType.substring(s_javaType.lastIndexOf("$") + 1, s_javaType.length());
								}
							}
							
							/* check if we use property methods with invoke to get object data values */
							if (this.b_usePropertyMethods) {
								java.lang.reflect.Method o_method = null;
								
								/* store get-property-method of java type object */
								try {
									o_method = p_o_object.getClass().getDeclaredMethod("get" + s_javaType);
								} catch (NoSuchMethodException | SecurityException o_exc) {
									throw new NoSuchMethodException("Method[" + "get" + s_javaType + "] of object[" + p_o_object.getClass().getTypeName() + "] does not exist for encoding data xs:element: " + o_xsdElement.getName() + "(" + p_o_object.getClass().getTypeName() + "); " + o_exc);
								}
								
								/* invoke get-property-method to get object data of current element */
								o_object = o_method.invoke(p_o_object);
							} else {
								/* call field directly to get object data values */
								try {
									o_object = p_o_object.getClass().getDeclaredField(s_javaType).get(p_o_object);
								} catch (Exception o_exc) {
									throw new NoSuchFieldException("Property[" + s_javaType + "] of object[" + p_o_object.getClass().getTypeName() + "] returns no data or not accessible for encoding data xs:element: '" + o_xsdElement.getName() + "', object type: '" + p_o_object.getClass().getTypeName() + "'; " + o_exc);
								}
							}
						}
						
						/* check if object is not null, but ignore if parent element is not a choice tag or we have not a primitive array in mapping */
						if ( (o_object == null) && (!p_o_xsdElement.getChoice()) && (!o_xsdElement.getMapping().endsWith("[]")) ) {
							throw new NullPointerException(s_javaType + " has no value in xs:element " + o_xsdElement.getName() + "(" + p_o_object.getClass().getTypeName() + ")");
						}
						
						/* do not continue recursion, when object is null and parent element is a choice tag */
						if (!( (o_object == null) && (p_o_xsdElement.getChoice()) )) {
							/* check choice counter */
							if ( (p_o_xsdElement.getMaxOccurs() >= 0) && (++i_choiceCnt > p_o_xsdElement.getMaxOccurs()) ) {
								throw new IllegalArgumentException(s_javaType + " has to many objects(" + i_choiceCnt + ") in xs:choice " + p_o_xsdElement.getName() + "(" + p_o_object.getClass().getTypeName() + "), maximum = " + p_o_xsdElement.getMaxOccurs());
							}
							
							/* encode object data to xml recursively */
							s_xml += this.xmlEncodeRecursive(o_xsdElement, o_object);
						}
					} else { /* otherwise we have xs:elements with primitive types */
						/* get value for xml element */
						String s_elementValue = this.getElementValue(o_xsdElement, p_o_object);
						
						/* check minOccurs attribute of xml element and if value is empty */
						if ( (o_xsdElement.getMinOccurs() > 0) && (net.forestany.forestj.lib.Helper.isStringEmpty(s_elementValue)) ) {
							if (p_o_xsdElement.getChoice()) {
								continue;
							} else {
								throw new IllegalArgumentException("Missing element value for xs:element[" + p_o_object.getClass().getTypeName() + "." + o_xsdElement.getName() + "]");
							}
						}
						
						/* check choice counter */
						if (p_o_xsdElement.getChoice()) {
							if ( (p_o_xsdElement.getMaxOccurs() >= 0) && (++i_choiceCnt > p_o_xsdElement.getMaxOccurs()) && (!net.forestany.forestj.lib.Helper.isStringEmpty(s_elementValue)) ) {
								throw new IllegalArgumentException(p_o_xsdElement.getName() + " has to many objects(" + i_choiceCnt + ") in xs:choice " + p_o_object.getClass().getTypeName() + "." + o_xsdElement.getName() + ", maximum = " + p_o_xsdElement.getMaxOccurs());
							}
						}
						
						/* start xml tag */
						s_xml += this.printIndentation() + "<" + o_xsdElement.getName();
						
						/* check if current xml element should have attributes */
						if (o_xsdElement.getAttributes().size() > 0) {
							/* iterate all xml attributes */
							for (XSDAttribute o_xsdAttribute : o_xsdElement.getAttributes()) {
								/* get value for xml attribute */
								String s_attributeValue = this.getAttributeValue(o_xsdAttribute, p_o_object);
								
								/* if attribute is required but value is empty, throw exception */
								if ( (o_xsdAttribute.getRequired()) && (net.forestany.forestj.lib.Helper.isStringEmpty(s_attributeValue)) ) {
									throw new IllegalArgumentException("Missing attribute value for xs:attribute[" + p_o_object.getClass().getTypeName() + "." + o_xsdAttribute.getName() + "]");
								}
								
								/* if attribute value is not empty, add it to xml element */
								if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_attributeValue)) {
									s_xml += " " + o_xsdAttribute.getName() + "=\"" + s_attributeValue + "\"";
								}
							}
						}
						
						if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_elementValue)) {
							/* end tag */
							s_xml += ">";
							/* write value in xml element tag */
							s_xml += s_elementValue;
							/* end xml tag */
							s_xml += "</" + o_xsdElement.getName() + ">";
						} else {
							/* close tag without a value */
							s_xml += "/>";
						}
						
						s_xml += this.s_lineBreak;
					}
				}
			}
			
			/* check choice counter for minimum objects */
			if (p_o_xsdElement.getChoice()) {
				if (i_choiceCnt < p_o_xsdElement.getMinOccurs()) {
					throw new IllegalArgumentException(p_o_xsdElement.getName() + " has to few objects(" + i_choiceCnt + ") in xs:choice, minimum = " + p_o_xsdElement.getMinOccurs());
				}
			}
			
			/* check if we have an array element */
			if (p_o_xsdElement.getIsArray()) {
				/* get value for xml element */
				String s_elementValue = this.getElementValue(p_o_xsdElement, p_o_object);
				
				/* check minOccurs attribute of xml element and if value is empty */
				if ( (p_o_xsdElement.getMinOccurs() > 0) && (net.forestany.forestj.lib.Helper.isStringEmpty(s_elementValue)) ) {
					throw new IllegalArgumentException("Missing element value for xs:element[" + p_o_object.getClass().getTypeName() + "." + p_o_xsdElement.getName() + "]");
				}
				
				/* remove auto line break */
				s_xml = s_xml.substring(0, s_xml.length() - this.s_lineBreak.length());
				
				/* only write not null element values */
				if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_elementValue)) {
					/* write value in xml element tag */
					s_xml += s_elementValue;
					/* close xml element tag */
					s_xml += "</" + p_o_xsdElement.getName() + ">";
				} else {
					/* write empty string or empty xml tag */
					if (this.b_printEmptyString) {
						/* write empty string value in xml element tag */
						s_xml += "&#x200B;";
						/* close xml element tag */
						s_xml += "</" + p_o_xsdElement.getName() + ">";
					} else {
						this.i_level--;
						s_xml = this.printIndentation() + "<" + p_o_xsdElement.getName() + "/>" ;
						this.i_level++;
					}
				}
			} else if ( (p_o_object != null) && (!net.forestany.forestj.lib.Helper.isStringEmpty(p_o_xsdElement.getMapping())) && (p_o_xsdElement.getMapping().endsWith("[]")) ) { /* handle primitive array elements */
				/* get primitive array type */
				String s_primitiveArrayType = p_o_xsdElement.getMapping();
				
				/* get second part of mapping value as primitive array type */
				if (s_primitiveArrayType.contains(":")) {
					s_primitiveArrayType = s_primitiveArrayType.split(":")[1];
				}
				
				/* remove '[]' from type value */
				if (s_primitiveArrayType.endsWith("[]")) {
					s_primitiveArrayType = s_primitiveArrayType.substring(0, s_primitiveArrayType.length() - 2);
				}
				
				/* handle primitive array in object parameter */
				if ( (s_primitiveArrayType.contentEquals("boolean")) || (s_primitiveArrayType.contentEquals("java.lang.Boolean")) ) {
					/* cast current field of parameter object as array */
					boolean[] a_objects = (boolean[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if ( (s_primitiveArrayType.contentEquals("byte")) || (s_primitiveArrayType.contentEquals("java.lang.Byte")) ) {
					/* cast current field of parameter object as array */
					byte[] a_objects = (byte[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if ( (s_primitiveArrayType.contentEquals("char")) || (s_primitiveArrayType.contentEquals("java.lang.Character")) ) {
					/* cast current field of parameter object as array */
					char[] a_objects = (char[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if ( (s_primitiveArrayType.contentEquals("float")) || (s_primitiveArrayType.contentEquals("java.lang.Float")) ) {
					/* cast current field of parameter object as array */
					float[] a_objects = (float[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if ( (s_primitiveArrayType.contentEquals("double")) || (s_primitiveArrayType.contentEquals("java.lang.Double")) ) {
					/* cast current field of parameter object as array */
					double[] a_objects = (double[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if ( (s_primitiveArrayType.contentEquals("short")) || (s_primitiveArrayType.contentEquals("java.lang.Short")) ) {
					/* cast current field of parameter object as array */
					short[] a_objects = (short[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if ( (s_primitiveArrayType.contentEquals("int")) || (s_primitiveArrayType.contentEquals("java.lang.Integer")) ) {
					/* cast current field of parameter object as array */
					int[] a_objects = (int[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if ( (s_primitiveArrayType.contentEquals("long")) || (s_primitiveArrayType.contentEquals("java.lang.Long")) ) {
					/* cast current field of parameter object as array */
					long[] a_objects = (long[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if ( (s_primitiveArrayType.contentEquals("string")) || (s_primitiveArrayType.contentEquals("java.lang.String")) ) {
					/* cast current field of parameter object as array */
					String[] a_objects = (String[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if (s_primitiveArrayType.contentEquals("java.util.Date")) {
					/* cast current field of parameter object as array */
					java.util.Date[] a_objects = (java.util.Date[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if (s_primitiveArrayType.contentEquals("java.time.LocalTime")) {
					/* cast current field of parameter object as array */
					java.time.LocalTime[] a_objects = (java.time.LocalTime[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if (s_primitiveArrayType.contentEquals("java.time.LocalDate")) {
					/* cast current field of parameter object as array */
					java.time.LocalDate[] a_objects = (java.time.LocalDate[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if (s_primitiveArrayType.contentEquals("java.time.LocalDateTime")) {
					/* cast current field of parameter object as array */
					java.time.LocalDateTime[] a_objects = (java.time.LocalDateTime[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				} else if (s_primitiveArrayType.contentEquals("java.math.BigDecimal")) {
					/* cast current field of parameter object as array */
					java.math.BigDecimal[] a_objects = (java.math.BigDecimal[])p_o_object;
					
					/* only execute if we have more than one array element */
					if (a_objects.length > 0) {
						/* iterate objects in primitive array and encode element */
						for (int i = 0; i < a_objects.length; i++) {
							/* encode primitive array object to xml recursively */
							s_xml += this.xmlEncodeRecursive(p_o_xsdElement.getChildren().get(0), (Object)a_objects[i]);
						}
					}
				}
			}
			
			/* decrease level for PrintIdentation */
			this.i_level--;
		}
		
		/* render closing tag if we are not rendering an array element */
		if (!p_o_xsdElement.getIsArray()) {
			/* check if current element has any value output */
			if (s_xml.endsWith("<" + p_o_xsdElement.getName() + ">" + this.s_lineBreak)) {
				/* remove tag opening */
				s_xml = s_xml.substring(0, ( s_xml.length() - ( p_o_xsdElement.getName().length() + 2 + this.s_lineBreak.length() ) ) );
				/* close tag without a value */
				s_xml += "<" + p_o_xsdElement.getName() + "/>";
			} else {
				/* end xml tag */
				s_xml += this.printIndentation() + "</" + p_o_xsdElement.getName() + ">";
			}
		}
		
		/* add line break */
		s_xml += this.s_lineBreak;
		
		return s_xml;
	}
	
	/**
	 * Get value of object to generate xml element with value
	 * 
	 * @param p_o_xsdElement				xsd element object with mapping class information	
	 * @param p_o_object					object to access fields via direct public access or public access to property methods (getXX and setXX)
	 * @return								casted field value of object as object
	 * @throws IllegalArgumentException		invalid value for xml element
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 * @throws ParseException				could not parse java.util.Date
	 */
	private String getElementValue(XSDElement p_o_xsdElement, Object p_o_object) throws IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException, java.text.ParseException {
		/* variable for object data */
		Object o_value = null;
		
		/* do not retrieve value from field or property method, because we already have array element as object in the parameter variable */
		if (!p_o_xsdElement.getIsArray()) {
			/* check if we use property methods with invoke to get object data values */
			if (this.b_usePropertyMethods) {
				java.lang.reflect.Method o_method = null;
				
				/* store get-property-method of java type object */
				try {
					o_method = p_o_object.getClass().getDeclaredMethod("get" + p_o_xsdElement.getMapping());
				} catch (NoSuchMethodException | SecurityException o_exc) {
					throw new NoSuchMethodException("Method[" + "get" + p_o_xsdElement.getMapping() + "] of object[" + p_o_object.getClass().getTypeName() + "] does not exist for encoding data xs:element: " + p_o_xsdElement.getName() + "(" + p_o_object.getClass().getTypeName() + "); " + o_exc);
				} catch (Exception o_exc) {
					throw new NoSuchMethodException("Method[" + "get" + p_o_xsdElement.getMapping() + "] of object[" + p_o_object.getClass().getTypeName() + "] returns no data for encoding data xs:element: " + p_o_xsdElement.getName() + "; " + o_exc);
				}
				
				/* invoke get-property-method to get object data of current element */
				o_value = o_method.invoke(p_o_object);
			} else {
				/* call field directly to get object data values */
				try {
					o_value = p_o_object.getClass().getDeclaredField(p_o_xsdElement.getMapping()).get(p_o_object);
				} catch (Exception o_exc) {
					throw new NoSuchFieldException("Property[" + p_o_xsdElement.getMapping() + "] of object[" + p_o_object.getClass().getTypeName() + "] returns no data or not accessible for encoding data xs:element: " + p_o_xsdElement.getName() + "; " + o_exc);
				}
			}
		} else {
			/* we already have array element as object in the parameter variable */
			o_value = p_o_object;
		}
		
		/* cast object data to string value, based on xsd attribute type */
		String s_foo = this.castStringFromObject(o_value, p_o_xsdElement.getType()); 
		
		/* check if xs:element has any restrictions */
		if (p_o_xsdElement.getRestriction()) {
			boolean b_enumerationFound = false;
			boolean b_enumerationReturnValue = false;
			
			for (XSDRestriction o_xsdRestriction : p_o_xsdElement.getRestrictions()) {
				if (o_xsdRestriction.getName().toLowerCase().contentEquals("enumeration")) {
					b_enumerationFound = true;
				}
				
				b_enumerationReturnValue = this.checkRestriction(s_foo, o_xsdRestriction, p_o_xsdElement.getType());
				
				if ( (b_enumerationFound) && (b_enumerationReturnValue) ) {
					break;
				}
			}
			
			if ( (b_enumerationFound) && (!b_enumerationReturnValue) ) {
				throw new IllegalArgumentException("Element[" + p_o_xsdElement.getName() + "] with value[" + s_foo + "] does not match enumaration restrictions defined in xsd-schema for this xs:element");
			}
		}
		
		/* return value */
		return s_foo;
	}
	
	/**
	 * Get value of object to generate xml attribute with value
	 * 
	 * @param p_o_xsdAttribute				xsd attribute object with mapping class information	
	 * @param p_o_object					object to access fields via direct public access or public access to property methods (getXX and setXX)
	 * @return								casted field value of object as object
	 * @throws IllegalArgumentException		invalid value for xml attribute
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 * @throws ParseException				could not parse java.util.Date
	 */
	private String getAttributeValue(XSDAttribute p_o_xsdAttribute, Object p_o_object) throws IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, ClassNotFoundException, java.text.ParseException {
		/* if attribute has fixed value, return fixed value */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_o_xsdAttribute.getFixed())) {
			return p_o_xsdAttribute.getFixed();
		}
		
		/* variable for object data */
		Object o_value = null;
		
		/* check if we use property methods with invoke to get object data values */
		if (this.b_usePropertyMethods) {
			java.lang.reflect.Method o_method = null;
			
			/* store get-property-method of java type object */
			try {
				o_method = p_o_object.getClass().getDeclaredMethod("get" + p_o_xsdAttribute.getMapping());
			} catch (NoSuchMethodException | SecurityException o_exc) {
				throw new NoSuchMethodException("Method[" + "get" + p_o_xsdAttribute.getMapping() + "] of object[" + p_o_object.getClass().getTypeName() + "] does not exist for encoding data xs:attribute: " + p_o_xsdAttribute.getName() + "(" + p_o_object.getClass().getTypeName() + "); " + o_exc);
			} catch (Exception o_exc) {
				throw new NoSuchMethodException("Method[" + "get" + p_o_xsdAttribute.getMapping() + "] of object[" + p_o_object.getClass().getTypeName() + "] returns no data for encoding data xs:attribute: " + p_o_xsdAttribute.getName() + " ; " + o_exc);
			}
			
			/* invoke get-property-method to get object data of current element */
			o_value = o_method.invoke(p_o_object);
		} else {
			/* call field directly to get object data values */
			try {
				o_value = p_o_object.getClass().getDeclaredField(p_o_xsdAttribute.getMapping()).get(p_o_object);
			} catch (Exception o_exc) {
				throw new NoSuchFieldException("Property[" + p_o_xsdAttribute.getMapping() + "] of object[" + p_o_object.getClass().getTypeName() + "] returns no data or not accessible for encoding data xs:attribute: " + p_o_xsdAttribute.getName() + "; " + o_exc);
			}
		}
		
		/* cast object data to string value, based on xsd attribute type */
		String s_foo = this.castStringFromObject(o_value, p_o_xsdAttribute.getType());
		
		/* check if xs:attribute has any restrictions, when value is not empty */
		if ( (p_o_xsdAttribute.getRestriction()) && (!net.forestany.forestj.lib.Helper.isStringEmpty(s_foo)) ) {
			boolean b_enumerationFound = false;
			boolean b_enumerationReturnValue = false;
			
			for (XSDRestriction o_xsdRestriction : p_o_xsdAttribute.getRestrictions()) {
				if (o_xsdRestriction.getName().toLowerCase().contentEquals("enumeration")) {
					b_enumerationFound = true;
				}
				
				b_enumerationReturnValue = this.checkRestriction(s_foo, o_xsdRestriction, p_o_xsdAttribute.getType());
				
				if ( (b_enumerationFound) && (b_enumerationReturnValue) ) {
					break;
				}
			}
			
			if ( (b_enumerationFound) && (!b_enumerationReturnValue) ) {
				throw new IllegalArgumentException("Attribute[" + p_o_xsdAttribute.getName() + "] with value[" + s_foo + "] does not match enumaration restrictions defined in xsd-schema for this xs:attribute");
			}
		}
		
		/* if object data is empty, but we have a default value, return default value */
		if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_o_xsdAttribute.getDefault())) && (net.forestany.forestj.lib.Helper.isStringEmpty(s_foo)) ) {
			s_foo = p_o_xsdAttribute.getDefault();
		}
		
		return s_foo;
	}
	
	/**
	 * Method to cast an object value to a string value for encoding xml data
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
			
			if (p_s_type.contentEquals("boolean")) {
				s_foo = Boolean.class.cast(p_o_object).toString();
			} else if ( (p_s_type.contentEquals("string")) || (p_s_type.contentEquals("duration")) ) {
				s_foo = String.class.cast(p_o_object).toString();
				
				if ( (s_foo.length() == 0) && (this.b_printEmptyString) ) {
					s_foo = "&#x200B;";
				}
			} else if ( (p_s_type.contentEquals("date")) || (p_s_type.contentEquals("time")) || (p_s_type.contentEquals("datetime")) ) {
				if (this.b_useISO8601UTC) {
					if (p_o_object instanceof java.util.Date) {
						s_foo = net.forestany.forestj.lib.Helper.utilDateToISO8601UTC(java.util.Date.class.cast(p_o_object));
					} else if (p_o_object instanceof java.time.LocalDateTime) {
						s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.class.cast(p_o_object));
					} else if (p_o_object instanceof java.time.LocalDate) {
						s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.of(java.time.LocalDate.class.cast(p_o_object), java.time.LocalTime.of(0, 0)));
					} else if (p_o_object instanceof java.time.LocalTime) {
						s_foo = net.forestany.forestj.lib.Helper.toISO8601UTC(java.time.LocalDateTime.of(java.time.LocalDate.of(1900, 1, 1), java.time.LocalTime.class.cast(p_o_object)));
					} else {
						throw new IllegalArgumentException("Illegal object type '" + p_o_object.getClass().getTypeName() + "' for type '" + p_s_type + "'");
					}
				} else {
					if (p_s_type.contentEquals("date")) {
						if (p_o_object instanceof java.util.Date) {
							s_foo = new java.text.SimpleDateFormat(this.s_dateFormat).format(java.util.Date.class.cast(p_o_object));
						} else if (p_o_object instanceof java.time.LocalDateTime) {
							s_foo = java.time.LocalDateTime.class.cast(p_o_object).toLocalDate().format(java.time.format.DateTimeFormatter.ofPattern(this.s_dateFormat));
						} else if (p_o_object instanceof java.time.LocalDate) {
							s_foo = java.time.LocalDate.class.cast(p_o_object).format(java.time.format.DateTimeFormatter.ofPattern(this.s_dateFormat));
						} else {
							throw new IllegalArgumentException("Illegal object type '" + p_o_object.getClass().getTypeName() + "' for type '" + p_s_type + "'");
						}
					} else if (p_s_type.contentEquals("time")) {
						if (p_o_object instanceof java.util.Date) {
							s_foo = new java.text.SimpleDateFormat(this.s_timeFormat).format(java.util.Date.class.cast(p_o_object));
						} else if (p_o_object instanceof java.time.LocalDateTime) {
							s_foo = java.time.LocalDateTime.class.cast(p_o_object).toLocalTime().format(java.time.format.DateTimeFormatter.ofPattern(this.s_timeFormat));
						} else if (p_o_object instanceof java.time.LocalTime) {
							s_foo = java.time.LocalTime.class.cast(p_o_object).format(java.time.format.DateTimeFormatter.ofPattern(this.s_timeFormat));
						} else {
							throw new IllegalArgumentException("Illegal object type '" + p_o_object.getClass().getTypeName() + "' for type '" + p_s_type + "'");
						}
					} else if (p_s_type.contentEquals("datetime")) {
						if (p_o_object instanceof java.util.Date) {
							s_foo = new java.text.SimpleDateFormat(this.s_dateTimeFormat).format(java.util.Date.class.cast(p_o_object));
						} else if (p_o_object instanceof java.time.LocalDateTime) {
							s_foo = java.time.LocalDateTime.class.cast(p_o_object).format(java.time.format.DateTimeFormatter.ofPattern(this.s_dateTimeFormat));
						} else {
							throw new IllegalArgumentException("Illegal object type '" + p_o_object.getClass().getTypeName() + "' for type '" + p_s_type + "'");
						}
					}
				}
			} else if (p_s_type.contentEquals("decimal")) {
				java.math.BigDecimal o_bigDecimal = java.math.BigDecimal.class.cast(p_o_object);
				s_foo = o_bigDecimal.toString();
			} else if (p_s_type.contentEquals("double")) {
				s_foo = Double.class.cast(p_o_object).toString();
			} else if (p_s_type.contentEquals("float")) {
				s_foo = Float.class.cast(p_o_object).toString();
			} else if ( (p_s_type.contentEquals("short")) || (p_s_type.contentEquals("unsignedshort")) ) {
				s_foo = Short.class.cast(p_o_object).toString();
			} else if ( (p_s_type.contentEquals("long")) || (p_s_type.contentEquals("unsignedlong")) ) {
				s_foo = Long.class.cast(p_o_object).toString();
			} else if ( (p_s_type.contentEquals("integer")) || (p_s_type.contentEquals("int")) || (p_s_type.contentEquals("positiveinteger")) || (p_s_type.contentEquals("unsignedinteger")) ) {
				s_foo = Integer.class.cast(p_o_object).toString();
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] for " + p_o_object.getClass().getSimpleName());
			}
		}
		
		return s_foo;
	}

	/**
	 * Check if xsd element restriction is valid with current value
	 * timestamp comparison in restrictions(datetime, date and time) will always be executed with java.time.LocalDateTime with internal conversion of it's values
	 * 
	 * @param p_s_value						string value for xsd element restriction, can be casted to integer as well
	 * @param p_o_xsdRestriction			xsd restriction object which holds all restriction information
	 * @param p_s_type						xml element type
	 * @throws IllegalArgumentException		unknown restriction name, restriction error or invalid type from xsd element object
	 * @throws ParseException				could not parse java.util.Date
	 */
	private boolean checkRestriction(String p_s_value, XSDRestriction p_o_xsdRestriction, String p_s_type) throws IllegalArgumentException, java.text.ParseException {
		boolean b_enumerationReturnValue = false;
		
		java.util.List<String> a_stringTypes = java.util.Arrays.asList("string", "duration", "hexbinary", "base64binary", "anyuri", "normalizedstring", "token", "language", "name", "ncname", "nmtoken", "id", "idref", "entity");
		java.util.List<String> a_integerTypes = java.util.Arrays.asList("integer", "int", "positiveinteger", "nonpositiveinteger", "negativeinteger", "nonnegativeinteger", "byte", "unsignedint", "unsignedbyte");
		String p_s_typeLower = p_s_type.toLowerCase();
		
		if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("minexclusive")) {
			if (p_s_typeLower.contentEquals("boolean")) {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			} else if (a_stringTypes.contains(p_s_typeLower)) {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			} else if (p_s_typeLower.contentEquals("date")) {
				java.time.LocalDateTime o_value = java.time.LocalDateTime.of( net.forestany.forestj.lib.Helper.fromDateString(p_s_value), java.time.LocalTime.of(0, 0) );
				java.time.LocalDateTime o_restriction = java.time.LocalDateTime.of( net.forestany.forestj.lib.Helper.fromDateString(p_o_xsdRestriction.getStrValue()), java.time.LocalTime.of(0, 0) );
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("time")) {
				java.time.LocalDateTime o_value = java.time.LocalDateTime.of( java.time.LocalDate.of(1900, 1, 1), net.forestany.forestj.lib.Helper.fromTimeString(p_s_value) );
				java.time.LocalDateTime o_restriction = java.time.LocalDateTime.of( java.time.LocalDate.of(1900, 1, 1), net.forestany.forestj.lib.Helper.fromTimeString(p_o_xsdRestriction.getStrValue()) );
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("datetime")) {
				java.time.LocalDateTime o_value = net.forestany.forestj.lib.Helper.fromDateTimeString(p_s_value);
				java.time.LocalDateTime o_restriction = net.forestany.forestj.lib.Helper.fromDateTimeString(p_o_xsdRestriction.getStrValue());
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("decimal")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_xsdRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("double")) {
				Double d_value = Double.parseDouble(p_s_value);
				Double d_restriction = Double.parseDouble(p_o_xsdRestriction.getStrValue());
				int i_compare = d_value.compareTo(d_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Value[" + d_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("float")) {
				Float f_value = Float.parseFloat(p_s_value);
				Float f_restriction = Float.parseFloat(p_o_xsdRestriction.getStrValue());
				int i_compare = f_value.compareTo(f_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Value[" + f_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (a_integerTypes.contains(p_s_typeLower)) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_xsdRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Value[" + i_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("long") || p_s_typeLower.contentEquals("unsignedlong")) {
				Long l_value = Long.parseLong(p_s_value);
				Long l_restriction = Long.parseLong(p_o_xsdRestriction.getStrValue());
				int i_compare = l_value.compareTo(l_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Value[" + l_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("short") || p_s_typeLower.contentEquals("unsignedshort")) {
				Short sh_value = Short.parseShort(p_s_value);
				Short sh_restriction = (short)p_o_xsdRestriction.getIntValue();
				int i_compare = sh_value.compareTo(sh_restriction);
				
				if (i_compare == -1) {
					throw new IllegalArgumentException("Value[" + sh_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			}
		} else if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("maxexclusive")) {
			if (p_s_typeLower.contentEquals("boolean")) {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			} else if (a_stringTypes.contains(p_s_typeLower)) {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			} else if (p_s_typeLower.contentEquals("date")) {
				java.time.LocalDateTime o_value = java.time.LocalDateTime.of( net.forestany.forestj.lib.Helper.fromDateString(p_s_value), java.time.LocalTime.of(0, 0) );
				java.time.LocalDateTime o_restriction = java.time.LocalDateTime.of( net.forestany.forestj.lib.Helper.fromDateString(p_o_xsdRestriction.getStrValue()), java.time.LocalTime.of(0, 0) );
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("time")) {
				java.time.LocalDateTime o_value = java.time.LocalDateTime.of( java.time.LocalDate.of(1900, 1, 1), net.forestany.forestj.lib.Helper.fromTimeString(p_s_value) );
				java.time.LocalDateTime o_restriction = java.time.LocalDateTime.of( java.time.LocalDate.of(1900, 1, 1), net.forestany.forestj.lib.Helper.fromTimeString(p_o_xsdRestriction.getStrValue()) );
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("datetime")) {
				java.time.LocalDateTime o_value = net.forestany.forestj.lib.Helper.fromDateTimeString(p_s_value);
				java.time.LocalDateTime o_restriction = net.forestany.forestj.lib.Helper.fromDateTimeString(p_o_xsdRestriction.getStrValue());
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("decimal")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_xsdRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("double")) {
				Double d_value = Double.parseDouble(p_s_value);
				Double d_restriction = Double.parseDouble(p_o_xsdRestriction.getStrValue());
				int i_compare = d_value.compareTo(d_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Value[" + d_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("float")) {
				Float f_value = Float.parseFloat(p_s_value);
				Float f_restriction = Float.parseFloat(p_o_xsdRestriction.getStrValue());
				int i_compare = f_value.compareTo(f_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Value[" + f_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (a_integerTypes.contains(p_s_typeLower)) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_xsdRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Value[" + i_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("long") || p_s_typeLower.contentEquals("unsignedlong")) {
				Long l_value = Long.parseLong(p_s_value);
				Long l_restriction = Long.parseLong(p_o_xsdRestriction.getStrValue());
				int i_compare = l_value.compareTo(l_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Value[" + l_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("short") || p_s_typeLower.contentEquals("unsignedshort")) {
				Short sh_value = Short.parseShort(p_s_value);
				Short sh_restriction = (short)p_o_xsdRestriction.getIntValue();
				int i_compare = sh_value.compareTo(sh_restriction);
				
				if (i_compare == 1) {
					throw new IllegalArgumentException("Value[" + sh_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			}
		} else if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("mininclusive")) {
			if (p_s_typeLower.contentEquals("boolean")) {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			} else if (a_stringTypes.contains(p_s_typeLower)) {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			} else if (p_s_typeLower.contentEquals("date")) {
				java.time.LocalDateTime o_value = java.time.LocalDateTime.of( net.forestany.forestj.lib.Helper.fromDateString(p_s_value), java.time.LocalTime.of(0, 0) );
				java.time.LocalDateTime o_restriction = java.time.LocalDateTime.of( net.forestany.forestj.lib.Helper.fromDateString(p_o_xsdRestriction.getStrValue()), java.time.LocalTime.of(0, 0) );
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare < 1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("time")) {
				java.time.LocalDateTime o_value = java.time.LocalDateTime.of( java.time.LocalDate.of(1900, 1, 1), net.forestany.forestj.lib.Helper.fromTimeString(p_s_value) );
				java.time.LocalDateTime o_restriction = java.time.LocalDateTime.of( java.time.LocalDate.of(1900, 1, 1), net.forestany.forestj.lib.Helper.fromTimeString(p_o_xsdRestriction.getStrValue()) );
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare < 1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("datetime")) {
				java.time.LocalDateTime o_value = net.forestany.forestj.lib.Helper.fromDateTimeString(p_s_value);
				java.time.LocalDateTime o_restriction = net.forestany.forestj.lib.Helper.fromDateTimeString(p_o_xsdRestriction.getStrValue());
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare < 1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("decimal")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_xsdRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare < 1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("double")) {
				Double d_value = Double.parseDouble(p_s_value);
				Double d_restriction = Double.parseDouble(p_o_xsdRestriction.getStrValue());
				int i_compare = d_value.compareTo(d_restriction);
				
				if (i_compare < 1) {
					throw new IllegalArgumentException("Value[" + d_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("float")) {
				Float f_value = Float.parseFloat(p_s_value);
				Float f_restriction = Float.parseFloat(p_o_xsdRestriction.getStrValue());
				int i_compare = f_value.compareTo(f_restriction);
				
				if (i_compare < 1) {
					throw new IllegalArgumentException("Value[" + f_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (a_integerTypes.contains(p_s_typeLower)) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_xsdRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if (i_compare < 1) {
					throw new IllegalArgumentException("Value[" + i_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("long") || p_s_typeLower.contentEquals("unsignedlong")) {
				Long l_value = Long.parseLong(p_s_value);
				Long l_restriction = Long.parseLong(p_o_xsdRestriction.getStrValue());
				int i_compare = l_value.compareTo(l_restriction);
				
				if (i_compare < 1) {
					throw new IllegalArgumentException("Value[" + l_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("short") || p_s_typeLower.contentEquals("unsignedshort")) {
				Short sh_value = Short.parseShort(p_s_value);
				Short sh_restriction = (short)p_o_xsdRestriction.getIntValue();
				int i_compare = sh_value.compareTo(sh_restriction);
				
				if (i_compare < 1) {
					throw new IllegalArgumentException("Value[" + sh_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			}
		} else if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("maxinclusive")) {
			if (p_s_typeLower.contentEquals("boolean")) {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			} else if (a_stringTypes.contains(p_s_typeLower)) {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			} else if (p_s_typeLower.contentEquals("date")) {
				java.time.LocalDateTime o_value = java.time.LocalDateTime.of( net.forestany.forestj.lib.Helper.fromDateString(p_s_value), java.time.LocalTime.of(0, 0) );
				java.time.LocalDateTime o_restriction = java.time.LocalDateTime.of( net.forestany.forestj.lib.Helper.fromDateString(p_o_xsdRestriction.getStrValue()), java.time.LocalTime.of(0, 0) );
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare > -1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("time")) {
				java.time.LocalDateTime o_value = java.time.LocalDateTime.of( java.time.LocalDate.of(1900, 1, 1), net.forestany.forestj.lib.Helper.fromTimeString(p_s_value) );
				java.time.LocalDateTime o_restriction = java.time.LocalDateTime.of( java.time.LocalDate.of(1900, 1, 1), net.forestany.forestj.lib.Helper.fromTimeString(p_o_xsdRestriction.getStrValue()) );
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare > -1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("datetime")) {
				java.time.LocalDateTime o_value = net.forestany.forestj.lib.Helper.fromDateTimeString(p_s_value);
				java.time.LocalDateTime o_restriction = net.forestany.forestj.lib.Helper.fromDateTimeString(p_o_xsdRestriction.getStrValue());
				
				if (this.b_useISO8601UTC) {
					o_value = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				}
				
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare > -1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("decimal")) {
				java.math.BigDecimal o_value = new java.math.BigDecimal(p_s_value);
				java.math.BigDecimal o_restriction = new java.math.BigDecimal(p_o_xsdRestriction.getStrValue());
				int i_compare = o_value.compareTo(o_restriction);
				
				if (i_compare > -1) {
					throw new IllegalArgumentException("Value[" + o_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("double")) {
				Double d_value = Double.parseDouble(p_s_value);
				Double d_restriction = Double.parseDouble(p_o_xsdRestriction.getStrValue());
				int i_compare = d_value.compareTo(d_restriction);
				
				if (i_compare > -1) {
					throw new IllegalArgumentException("Value[" + d_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("float")) {
				Float f_value = Float.parseFloat(p_s_value);
				Float f_restriction = Float.parseFloat(p_o_xsdRestriction.getStrValue());
				int i_compare = f_value.compareTo(f_restriction);
				
				if (i_compare > -1) {
					throw new IllegalArgumentException("Value[" + f_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (a_integerTypes.contains(p_s_typeLower)) {
				Integer i_value = Integer.parseInt(p_s_value);
				Integer i_restriction = p_o_xsdRestriction.getIntValue();
				int i_compare = i_value.compareTo(i_restriction);
				
				if (i_compare > -1) {
					throw new IllegalArgumentException("Value[" + i_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("long") || p_s_typeLower.contentEquals("unsignedlong")) {
				Long l_value = Long.parseLong(p_s_value);
				Long l_restriction = Long.parseLong(p_o_xsdRestriction.getStrValue());
				int i_compare = l_value.compareTo(l_restriction);
				
				if (i_compare > -1) {
					throw new IllegalArgumentException("Value[" + l_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
				}
			} else if (p_s_typeLower.contentEquals("short") || p_s_typeLower.contentEquals("unsignedshort")) {
				Short sh_value = Short.parseShort(p_s_value);
				Short sh_restriction = (short)p_o_xsdRestriction.getIntValue();
				int i_compare = sh_value.compareTo(sh_restriction);
				
				if (i_compare > -1) {
					throw new IllegalArgumentException("Value[" + sh_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			}
		} else if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("totaldigits")) {
			if (p_s_typeLower.contentEquals("decimal") || p_s_typeLower.contentEquals("double") || p_s_typeLower.contentEquals("float")) {
				String s_foo = p_s_value;
				
				if (s_foo.startsWith("+") || s_foo.startsWith("-")) {
					s_foo = s_foo.substring(1);
				}
				
				if (s_foo.contains(".")) {
					s_foo = s_foo.substring(0, s_foo.indexOf("."));
				} else if (s_foo.contains(",")) {
					s_foo = s_foo.substring(0, s_foo.indexOf(","));
				}
				
				int i_length = s_foo.length();
				
				if (i_length > p_o_xsdRestriction.getIntValue()) {
					throw new IllegalArgumentException("Value[" + p_s_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			} else if (a_integerTypes.contains(p_s_typeLower) || p_s_typeLower.contentEquals("long") || p_s_typeLower.contentEquals("unsignedlong") || p_s_typeLower.contentEquals("short") || p_s_typeLower.contentEquals("unsignedshort")) {
				int i_length = p_s_value.length();
				
				if (p_s_value.startsWith("+") || p_s_value.startsWith("-")) {
					i_length--;
				}
				
				if (i_length > p_o_xsdRestriction.getIntValue()) {
					throw new IllegalArgumentException("Value[" + p_s_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			}
		} else if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("fractiondigits")) {
			if (p_s_typeLower.contentEquals("decimal") || p_s_typeLower.contentEquals("double") || p_s_typeLower.contentEquals("float")) {
				String s_foo = p_s_value;
				
				if (s_foo.contains(".")) {
					s_foo = s_foo.substring(s_foo.indexOf(".") + 1);
				} else if (p_s_value.contains(",")) {
					s_foo = s_foo.substring(s_foo.indexOf(",") + 1);
				}
				
				int i_length = s_foo.length();
				
				if (i_length > p_o_xsdRestriction.getIntValue()) {
					throw new IllegalArgumentException("Value[" + p_s_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			}
		} else if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("length")) {
			if (a_stringTypes.contains(p_s_typeLower)) {
				if (p_s_value.length() != p_o_xsdRestriction.getIntValue()) {
					throw new IllegalArgumentException("Value[" + p_s_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			}
		} else if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("minlength")) {
			if (a_stringTypes.contains(p_s_typeLower)) {
				if (p_s_value.length() < p_o_xsdRestriction.getIntValue()) {
					throw new IllegalArgumentException("Value[" + p_s_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			}
		} else if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("maxlength")) {
			if (a_stringTypes.contains(p_s_typeLower)) {
				if (p_s_value.length() > p_o_xsdRestriction.getIntValue()) {
					throw new IllegalArgumentException("Value[" + p_s_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getIntValue() + "]");
				}
			} else {
				throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
			}
		} else if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("enumeration")) {
			if (p_o_xsdRestriction.getStrValue().contentEquals(p_s_value)) {
				b_enumerationReturnValue = true;
			}
		} else if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("whiteSpace")) {
			throw new IllegalArgumentException("Cannot use " + p_o_xsdRestriction.getName() + " restriction on type: " + p_s_type);
		} else if (p_o_xsdRestriction.getName().toLowerCase().contentEquals("pattern")) {
			if (!net.forestany.forestj.lib.Helper.matchesRegex(p_s_value, p_o_xsdRestriction.getStrValue())) {
				throw new IllegalArgumentException("Value[" + p_s_value + "] does not match " + p_o_xsdRestriction.getName() + " restriction[" + p_o_xsdRestriction.getStrValue() + "]");
			}
		} else {
			throw new IllegalArgumentException("Unknown Restriction: " + p_o_xsdRestriction.getName());
		}
		
		return b_enumerationReturnValue;
	}
	
	/* validate XML data with XSD schema */
	
	/**
	 * Validate xml file
	 * 
	 * @param p_s_xmlFile					full-path to xml file
	 * @return								true - content of xml file is valid, false - content of xml file is invalid
	 * @throws IllegalArgumentException		xml file does not exist
	 * @throws java.io.IOException			cannot read xml file content
	 * @throws NullPointerException			empty schema, empty xml file or root node after parsing xml content
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	public boolean validateAgainstSchema(String p_s_xmlFile) throws IllegalArgumentException, java.io.IOException, java.text.ParseException, java.time.DateTimeException {
		/* check root field */
		if (this.o_root == null) {
			throw new NullPointerException("Cannot decode data. Root is null.");
		}
		
		/* check if file exists */
		if (!File.exists(p_s_xmlFile)) {
			throw new NullPointerException("XML file[" + p_s_xmlFile + "] does not exist.");
		}
		
		/* open xml file */
		File o_file = new File(p_s_xmlFile, false);
		
												net.forestany.forestj.lib.Global.ilogFiner("read all lines from xml file '" + p_s_xmlFile + "'");
		
		/* validate xml file lines */										
		return validateAgainstSchema(o_file.getFileContentAsList());
	}
	
	/**
	 * Validate xml content
	 * 
	 * @param p_a_xmlTags					xml lines
	 * @return								true - xml content is valid, false - xml content is invalid
	 * @throws IllegalArgumentException		xml file does not exist
	 * @throws java.io.IOException			cannot read xml file content
	 * @throws NullPointerException			empty schema, empty xml file or root node after parsing xml content
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	public boolean validateAgainstSchema(java.util.List<String> p_a_xmlTags) throws IllegalArgumentException, java.io.IOException, NullPointerException, java.text.ParseException, java.time.DateTimeException {
		/* check root field */
		if (this.o_root == null) {
			throw new NullPointerException("Cannot decode data. Schema is null.");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("read all lines: '" + p_a_xmlTags.size() + "'");
		
		StringBuilder o_stringBuilder = new StringBuilder();
		
		/* read all xml schema file lines to one string builder */
		for (String s_line : p_a_xmlTags) {
			o_stringBuilder.append(s_line);
		}
		
		/* read all xml lines */
		String s_xml = o_stringBuilder.toString().replaceAll("[\\r\\n\\t]", "").replaceAll(">\\s*<", "><");
		
		/* clean up xml file */
		s_xml = s_xml.replaceAll("<\\?(.*?)\\?>", "");
		s_xml = s_xml.replaceAll("<!--(.*?)-->", "");
		
												net.forestany.forestj.lib.Global.ilogFinest("cleaned up xml file lines");
		
		/* validate xml */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("(<[^<>]*?<[^<>]*?>|<[^<>]*?>[^<>]*?>)");
		java.util.regex.Matcher o_matcher = o_regex.matcher(s_xml);
		
		/* if regex-matcher has match, the xml file is not valid */
	    while (o_matcher.find()) {
	        throw new IllegalArgumentException("Invalid xml-file. Please check xml-file at \"" + o_matcher.group(0) + "\".");
	    }
		
	    java.util.List<String> a_xmlTags = new java.util.ArrayList<String>();
	    
	    /* add all xml-tags to a list for parsing */
	    o_regex = java.util.regex.Pattern.compile("(<[^<>/]*?/>)|(<[^<>/]*?>[^<>]*?</[^<>/]*?>)|(<[^<>/]*?>)|(</[^<>/]*?>)");
	    o_matcher = o_regex.matcher(s_xml);
	    
	    /* save all xml tags in one array */
	    while (o_matcher.find()) {
	    	String s_xmlTag = o_matcher.group(0);
	    	
	    	if ( (!s_xmlTag.startsWith("<")) && (!s_xmlTag.endsWith(">")) ) {
	    		throw new IllegalArgumentException("Invalid xml-tag. Please check xml-file at \"" + s_xmlTag + "\".");
	    	}
	    	
	    	a_xmlTags.add(s_xmlTag);
	    }
	    
	    /* validate xml tree recursively */
	    this.validateXMLDocument(a_xmlTags, 0, a_xmlTags.size() - 1);
	    
	    										net.forestany.forestj.lib.Global.ilogFinest("validated xml content lines");
	    										
	    										if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("XML-Schema:" + net.forestany.forestj.lib.io.File.NEWLINE + this.o_root);
											    if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("XML:" + net.forestany.forestj.lib.io.File.NEWLINE + s_xml);
	    
	   /* validate xml tree recursively */
	   return this.validateAgainstSchemaRecursive(a_xmlTags, 0, a_xmlTags.size() - 1, this.o_root);
	}
	
	/**
	 * Recursive method to decode xml string to a java object and it's fields
	 * 
	 * @param p_a_xmlTags					xml lines
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_i_max						pointer where to end line iteration
	 * @param p_o_object					destination java object to decode xml information
	 * @param p_o_xsdElement				current xsd element of schema with information to decode xml data
	 * @return								decoded xml information as java object
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding xml correctly
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private boolean validateAgainstSchemaRecursive(java.util.List<String> p_a_xmlTags, int p_i_min, int p_i_max, XSDElement p_o_xsdElement) throws NullPointerException, IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		boolean b_return = true;
		
		/* check xml tag pointer */
		if (p_i_min > p_i_max) {
			throw new IllegalArgumentException("Xml tag pointer overflow(" + p_i_min + " >= " + p_i_max + ").");
		}
		
		/* get xml type */
    	XMLType e_xmlType = this.getXMLType(p_a_xmlTags.get(p_i_min));
		
    	/* check if we have a valid xml type */
    	if (e_xmlType == null) {
    		throw new NullPointerException("Invalid xml-type at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
    	}
    	
    	/* variable for xml element name */
		String s_xmlElementName = null;
    	
		/* get xml element name */
		if ( (e_xmlType == XMLType.BeginWithAttributes) || (e_xmlType == XMLType.ElementWithAttributes) || (e_xmlType == XMLType.EmptyWithAttributes) ) {
			s_xmlElementName = p_a_xmlTags.get(p_i_min).substring(1, p_a_xmlTags.get(p_i_min).indexOf(" "));
		} else if (e_xmlType == XMLType.Empty) {
			s_xmlElementName = p_a_xmlTags.get(p_i_min).substring(1, p_a_xmlTags.get(p_i_min).indexOf("/"));
		} else {
			s_xmlElementName = p_a_xmlTags.get(p_i_min).substring(1, p_a_xmlTags.get(p_i_min).indexOf(">"));
		}
		
		/* check if xml element name is set */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(s_xmlElementName)) {
			throw new NullPointerException("No xml element name in xml file at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
		}
		
		/* check if we expect current xml element */
    	if (!s_xmlElementName.contentEquals(p_o_xsdElement.getName())) {
    		/* maybe it is not the expected root, but just a sub tree of a valid xsd schema, so we look for it in the element definitions */
    		for (XSDElement o_temp : this.a_elementDefinitons) {
    			/* find xml element as element definition of xsd schema */
    			if (s_xmlElementName.contentEquals(o_temp.getName())) {
															net.forestany.forestj.lib.Global.ilogFiner("xs:element " + p_o_xsdElement.getName() + ", is not root element of schema, but a valid sub tree, so take this sub tree as entry point for validation");
    				
    				/* validate xml tree recursively with sub tree of xsd schema */
    				return this.validateAgainstSchemaRecursive(p_a_xmlTags, p_i_min, p_i_max, o_temp);
    			}
    		}
    		
    		throw new IllegalArgumentException(s_xmlElementName + " with type(" + e_xmlType + ") is not expected xs:element " + p_o_xsdElement.getName() + " at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
    	}
		
		/* if we have xs:element definition with no type, exact one child element and mapping contains ":"
		 * we have to iterate a list of object and must print multiple xml elements
		 * otherwise we have usual xs:element definitions for current element
		 */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_o_xsdElement.getType())) && (p_o_xsdElement.getMapping().contains(":")) && (p_o_xsdElement.getChildren().size() == 1) ) {
			/* check if current xml element has interlacing */
			if (!(e_xmlType == XMLType.BeginNoAttributes)) {
				/* if list has no children, check minOccurs attribute of xs:element child definition */
				if (p_o_xsdElement.getChildren().get(0).getMinOccurs() != 0) {
					throw new NullPointerException("List with [" + p_o_xsdElement.getChildren().get(0).getName() + "] xml tags is empty, minimum = " + p_o_xsdElement.getChildren().get(0).getMinOccurs());
				}
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("xs:element " + p_o_xsdElement.getName() + " with no type, exact one child element as definition and list as mapping");
			
			int i_tempMin = p_i_min + 1;
			int i_level = 0;
			
			/* look for end of nested xml element tag */
			while ( (!this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.Close)) || (i_level != 0) ) {
				if ( (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.BeginNoAttributes)) || (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.BeginWithAttributes)) ) {
					i_level++;
				} else if (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.Close)) {
					i_level--;
				}
				
				if (i_tempMin >= p_i_max) {
					/* forbidden state - interlacing is not valid in xml file */
					throw new IllegalArgumentException("Invalid nested xml element at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
				}
				
				i_tempMin++;
    		}
			
													net.forestany.forestj.lib.Global.ilogFiner("\tfound interlacing xml element(" + (p_i_min + 2) + " to " + (i_tempMin + 2) + ") - " + p_a_xmlTags.get(p_i_min) + " ... " + p_a_xmlTags.get(i_tempMin));
			
			/* create occurrence counter */
			int i_occurCnt = 0;
			
			int i_tempMin2 = p_i_min + 2;
			
			/* we have to iterate a list of objects, so we must check if there is another nested element */
			/* if the end of nested element not corresponds to end of interlacing, we continue to look for another nested element */
			while (i_tempMin2 < (i_tempMin - 1)) {
				i_tempMin2 = p_i_min + 2;
				i_level = 0;
				
				/* look for end of another nested xml element tag */
				while ( (!this.getXMLType(p_a_xmlTags.get(i_tempMin2)).equals(XMLType.Close)) || (i_level != 0) ) {
					if ( (this.getXMLType(p_a_xmlTags.get(i_tempMin2)).equals(XMLType.BeginNoAttributes)) || (this.getXMLType(p_a_xmlTags.get(i_tempMin2)).equals(XMLType.BeginWithAttributes)) ) {
						i_level++;
					} else if (this.getXMLType(p_a_xmlTags.get(i_tempMin2)).equals(XMLType.Close)) {
						i_level--;
					}
					
					if (i_tempMin2 >= p_i_max) {
						/* forbidden state - interlacing is not valid in xml file */
						throw new IllegalArgumentException("Invalid nested xml element at(" + (i_tempMin + 1 + 2) + ".-element) \"" + p_a_xmlTags.get(i_tempMin + 1) + "\".");
					}
					
					i_tempMin2++;
	    		}
				
														net.forestany.forestj.lib.Global.ilogFiner("\t\tfound list xml element(" + (p_i_min + 3) + " to " + (i_tempMin2 + 2) + ") - " + p_a_xmlTags.get(p_i_min + 1) + " ... " + p_a_xmlTags.get(i_tempMin2));
				
				/* handle nested xml element recursively */
				b_return = this.validateAgainstSchemaRecursive(p_a_xmlTags, ++p_i_min, i_tempMin, p_o_xsdElement.getChildren().get(0));
				
				/* add return object of recursion to list */
				if (b_return) {
					if (p_o_xsdElement.getChildren().get(0).getIsArray()) {
						/* increase occur counter with list size */
						i_occurCnt += (i_tempMin - p_i_min);
					} else {
						/* increase occur counter */
						i_occurCnt++;
					}
				}
				
				/* update xml tag pointer */
				p_i_min = i_tempMin2;
			}
			
			/* update xml tag pointer */
			p_i_min = i_tempMin;
			
													net.forestany.forestj.lib.Global.ilogFiner("\tend interlacing, continue from " + (p_i_min + 2) + " to " + (p_i_max + 2));
			
			/* check minOccurs attribute of child xs:element of current list */
			if (p_o_xsdElement.getChildren().get(0).getMinOccurs() > i_occurCnt) {
				throw new IllegalArgumentException("Not enough [" + p_o_xsdElement.getChildren().get(0).getName() + "] xml tags, minimum = " + p_o_xsdElement.getChildren().get(0).getMinOccurs());
			}
			
			/* check maxOccurs attribute of child xs:element of current list */
			if ( (p_o_xsdElement.getChildren().get(0).getMaxOccurs() >= 0) && (i_occurCnt > p_o_xsdElement.getChildren().get(0).getMaxOccurs()) ) {
				throw new IllegalArgumentException("Too many [" + p_o_xsdElement.getChildren().get(0).getName() + "] xml tags, maximum = " + p_o_xsdElement.getChildren().get(0).getMaxOccurs());
			}
		} else if (p_o_xsdElement.getName().contentEquals(p_o_xsdElement.getType())) { /* handle array elements as xml element tags */
			/* create occurrence counter */
			int i_occurCnt = 0;
			
			for (int i = p_i_min; i < p_i_max; i++) {
				/* get value if xml element is not empty */
				if (!( (e_xmlType == XMLType.Empty) || (e_xmlType == XMLType.EmptyWithAttributes) )) { 
					/* get value of xml element */
					java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("(<" + p_o_xsdElement.getName() + ">)([^<>]*?)(</" + p_o_xsdElement.getName() + ">)");
					java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xmlTags.get(i));
					
					if (o_matcher.find()) {
						String s_elementValue = o_matcher.group(2);
						
						/* check if xs:element has any restrictions */
						if (p_o_xsdElement.getRestriction()) {
							boolean b_enumerationFound = false;
							boolean b_enumerationReturnValue = false;
							
							for (XSDRestriction o_xsdRestriction : p_o_xsdElement.getRestrictions()) {
								if (o_xsdRestriction.getName().toLowerCase().contentEquals("enumeration")) {
									b_enumerationFound = true;
								}
								
								b_enumerationReturnValue = this.checkRestriction(s_elementValue, o_xsdRestriction, p_o_xsdElement.getType());
								
								if ( (b_enumerationFound) && (b_enumerationReturnValue) ) {
									break;
								}
							}
							
							if ( (b_enumerationFound) && (!b_enumerationReturnValue) ) {
								throw new IllegalArgumentException("Element[" + p_o_xsdElement.getName() + "] with value[" + s_elementValue + "] does not match enumaration restrictions defined in xsd-schema for this xs:element");
							}
						}
						
						/* check if element has value and if element value is not empty string value */
						if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(s_elementValue)) && (!( (this.b_printEmptyString) && (s_elementValue.toString().contentEquals("&#x200B;")) )) ) {
							/* cast string value to object and set returned value flag */
							@SuppressWarnings("unused")
							Object o_foo = this.castObjectFromString(s_elementValue, p_o_xsdElement.getType(), p_o_xsdElement.getType());
							i_occurCnt++;
						}
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFinest("\t\t\t\t\tarray element[" + p_o_xsdElement.getName() + "]; list size=" + i_occurCnt);
			}
			
			/* check minOccurs attribute of xs:element */
			if (p_o_xsdElement.getMinOccurs() > i_occurCnt) {
				throw new IllegalArgumentException("Not enough [" + p_o_xsdElement.getName() + "] xml tags, minimum = " + p_o_xsdElement.getMinOccurs());
			}
			
			/* check maxOccurs attribute of xs:element */
			if ( (p_o_xsdElement.getMaxOccurs() >= 0) && (i_occurCnt > p_o_xsdElement.getMaxOccurs()) ) {
				throw new IllegalArgumentException("Too many [" + p_o_xsdElement.getName() + "] xml tags, maximum = " + p_o_xsdElement.getMaxOccurs());
			}
		} else {
			/* flag if current element returned attributes */
			boolean b_returnedAttributes = false;
			
			/* check if current xml element should have attributes */
			if (p_o_xsdElement.getAttributes().size() > 0) {
				boolean b_required = false;
				
				/* check if one attribute of the xml element is required */
				for (XSDAttribute o_xsdAttribute : p_o_xsdElement.getAttributes()) {
					if (o_xsdAttribute.getRequired()) {
						b_required = true;
					}
				}
				
				/* check if current xml element has attributes */
				if ( ( (e_xmlType != XMLType.BeginWithAttributes) && (e_xmlType != XMLType.ElementWithAttributes) && (e_xmlType != XMLType.EmptyWithAttributes) ) && (b_required) ) {
					throw new IllegalArgumentException(s_xmlElementName + " has no attributes and is not compatible with xs:element (" + p_o_xsdElement.getName() + "[" + p_o_xsdElement.getType() + "])");
				}
				
				/* retrieve attributes */
				b_returnedAttributes = this.parseXMLAttributesForValidation(p_a_xmlTags, p_i_min, p_o_xsdElement);
			}
			
			/* create choice counter */
			int i_choiceCnt = 0;
			
			/* iterate all children of current xs:element */
			for (XSDElement o_xsdElement : p_o_xsdElement.getChildren()) {
														net.forestany.forestj.lib.Global.ilogFinest("\t\t\titerate children of " + p_o_xsdElement.getName() + ", choice=" + p_o_xsdElement.getChoice() + " ... child[" + o_xsdElement.getName() + "] with type[" + o_xsdElement.getType() + "] and attributes count[" + o_xsdElement.getAttributes().size() + "]");
				
				/* if xs:element has no primitive type we may have a special object definition */
				if (net.forestany.forestj.lib.Helper.isStringEmpty(o_xsdElement.getType())) {
					/* increase xml tag pointer */
					p_i_min++;
					
															net.forestany.forestj.lib.Global.ilogFinest("\t\t\t\tchild[" + o_xsdElement.getName() + "] compare to current xml tag[" + p_a_xmlTags.get(p_i_min) + "]");
					
					/* xml tag must match with expected xs:element name */
					if (!p_a_xmlTags.get(p_i_min).startsWith("<" + o_xsdElement.getName())) {
						/* if we have a choice scope or child min. occurs is lower than 1, go to next xs:element */
						if ( (p_o_xsdElement.getChoice()) || (o_xsdElement.getMinOccurs() < 1) ) {
							p_i_min--;
							continue;
						}
						
						/* xml tag does not match with xs:element name */
						throw new IllegalArgumentException(p_a_xmlTags.get(p_i_min) + " is not expected xs:element " + o_xsdElement.getName() + " for recursion");
					}
					
					/* check choice counter */
					if ( (p_o_xsdElement.getChoice()) && (++i_choiceCnt > p_o_xsdElement.getMaxOccurs()) ) {
						throw new IllegalArgumentException(p_o_xsdElement.getType() + " has to many objects(" + i_choiceCnt + ") in xs:choice " + p_o_xsdElement.getName() + "(" + p_o_xsdElement.getType() + "), maximum = " + p_o_xsdElement.getMaxOccurs());
					}
					
					int i_tempMin = p_i_min + 1;
					int i_level = 0;
					
															net.forestany.forestj.lib.Global.ilogFinest(p_i_min + "\t\t\tlook for recursion borders for [" + o_xsdElement.getName() + "] from " + (i_tempMin + 1));
					
					/* look for end of nested xml element tag */
					while ( (!this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.Close)) || (i_level != 0) ) {
																net.forestany.forestj.lib.Global.ilogFinest("\t\t\t" +i_level+ "\t"+p_a_xmlTags.get(i_tempMin));
						
						if ( (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.BeginNoAttributes)) || (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.BeginWithAttributes)) ) {
							i_level++;
						} else if (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.Close)) {
							i_level--;
						}
						
						if (i_tempMin >= p_i_max) {
							/* forbidden state - interlacing is not valid in xml file */
							throw new IllegalArgumentException("Invalid nested xml element at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
						}
						
						i_tempMin++;
		    		}
					
															net.forestany.forestj.lib.Global.ilogFinest("\t\t\t" + o_xsdElement.getName() + " has no primitive type -> new recursion (" + (p_i_min + 2) + " to " + (i_tempMin + 2) + ") - " + p_a_xmlTags.get(p_i_min) + " ... " + p_a_xmlTags.get(i_tempMin));
					
					/* handle xml element recursively */
					b_return = this.validateAgainstSchemaRecursive(p_a_xmlTags, p_i_min, i_tempMin, o_xsdElement);
					
					p_i_min = i_tempMin;
					
															net.forestany.forestj.lib.Global.ilogFinest("\t\t\tend recursion with [" + o_xsdElement.getType() + "], continue " + (p_i_min + 2) + " to " + (p_i_max + 2));
				} else { /* otherwise we have xs:elements with primitive types */
					/* flag if current element returned value */
					boolean b_returnedValue = false;
					
					/* get value if xml element is not empty */
					if (!( (e_xmlType == XMLType.Empty) || (e_xmlType == XMLType.EmptyWithAttributes) )) { 
						/* get value for xml element */
						if (b_returnedAttributes) {
							b_returnedValue = this.parseXMLElementForValidation(p_a_xmlTags, ++p_i_min, o_xsdElement, "(<" + o_xsdElement.getName() + "[^<>]*?>)([^<>]*?)(</" + o_xsdElement.getName() + ">)");
						} else {
							b_returnedValue = this.parseXMLElementForValidation(p_a_xmlTags, ++p_i_min, o_xsdElement, "(<" + o_xsdElement.getName() + ">)([^<>]*?)(</" + o_xsdElement.getName() + ">)");
						}
					}
					
															net.forestany.forestj.lib.Global.ilogFinest("\t\t\t\tchild[" + o_xsdElement.getName() + "] returned value=" + b_returnedValue);
					
					/* check minOccurs attribute of xml element and if value is empty */
					if ( (o_xsdElement.getMinOccurs() > 0) && (!b_returnedValue) ) {
						if (p_o_xsdElement.getChoice()) {
							p_i_min--;
							continue;
						} else {
							throw new NullPointerException("Missing element value for xs:element[" + o_xsdElement.getName() + "]{" + o_xsdElement.getType() + "}");
						}
					}
					
					/* check choice counter */
					if (p_o_xsdElement.getChoice()) {
						if ( (++i_choiceCnt > p_o_xsdElement.getMaxOccurs()) && (b_returnedValue) ) {
							throw new IllegalArgumentException(p_o_xsdElement.getName() + " has to many objects(" + i_choiceCnt + ") in xs:choice " + p_o_xsdElement.getType() + "." + o_xsdElement.getName() + ", maximum = " + p_o_xsdElement.getMaxOccurs());
						}
					}
					
					/* check if child xml element has attributes, because of possible simpleContent */
					if (o_xsdElement.getAttributes().size() > 0) {
						boolean b_required = false;
						
						/* check if one attribute of the xml element is required */
						for (XSDAttribute o_xsdAttribute : p_o_xsdElement.getAttributes()) {
							if (o_xsdAttribute.getRequired()) {
								b_required = true;
							}
						}
						
						/* check if current xml element has attributes */
						if ( ( (e_xmlType != XMLType.BeginWithAttributes) && (e_xmlType != XMLType.ElementWithAttributes) && (e_xmlType != XMLType.EmptyWithAttributes) ) && (b_required) ) {
							throw new IllegalArgumentException(o_xsdElement.getName() + " has no attributes");
						}
						
						/* retrieve attributes */
						b_returnedAttributes = this.parseXMLAttributesForValidation(p_a_xmlTags, p_i_min, o_xsdElement);
					}
				}
			}
			
			/* check choice counter for minimum objects */
			if (p_o_xsdElement.getChoice()) {
				if (i_choiceCnt < p_o_xsdElement.getMinOccurs()) {
					throw new IllegalArgumentException(p_o_xsdElement.getName() + " has to few objects(" + i_choiceCnt + ") in xs:choice, minimum = " + p_o_xsdElement.getMinOccurs());
				}
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("\treturn " + b_return);
		return b_return;
	}
	
	/**
	 * Parse xml element value for validation
	 * 
	 * @param p_a_xmlTags					xml lines
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_o_xsdElement				current xml schema element
	 * @param p_s_regexPattern				regex pattern where xml line must match
	 * @return								true - xml element has a value, false - could not parse value for xml element
	 * @throws IllegalArgumentException		element with value does not match structure or restrictions
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private boolean parseXMLElementForValidation(java.util.List<String> p_a_xmlTags, int p_i_min, XSDElement p_o_xsdElement, String p_s_regexPattern) throws IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		/* return value */
		boolean b_hasValue = false;
		
		/* get value of xml element */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile(p_s_regexPattern);
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xmlTags.get(p_i_min));
		
		if (o_matcher.find()) {
			String s_elementValue = o_matcher.group(2);
			
			/* check if element has value */
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_elementValue)) {
				b_hasValue = true;
			}
			
			/* check if xs:element has any restrictions */
			if (p_o_xsdElement.getRestriction()) {
				boolean b_enumerationFound = false;
				boolean b_enumerationReturnValue = false;
				
				for (XSDRestriction o_xsdRestriction : p_o_xsdElement.getRestrictions()) {
					if (o_xsdRestriction.getName().toLowerCase().contentEquals("enumeration")) {
						b_enumerationFound = true;
					}
					
					b_enumerationReturnValue = this.checkRestriction(s_elementValue, o_xsdRestriction, p_o_xsdElement.getType());
					
					if ( (b_enumerationFound) && (b_enumerationReturnValue) ) {
						break;
					}
				}
				
				if ( (b_enumerationFound) && (!b_enumerationReturnValue) ) {
					throw new IllegalArgumentException("Element[" + p_o_xsdElement.getName() + "] with value[" + s_elementValue + "] does not match enumaration restrictions defined in xsd-schema for this xs:element");
				}
			}
			    		
			@SuppressWarnings("unused")
			Object o_foo = this.castObjectFromStringForValidation(s_elementValue, p_o_xsdElement.getType());
		}
		
		return b_hasValue;
	}

	/**
	 * Parse xml attribute value for validation
	 * 
	 * @param p_a_xmlTags					xml lines
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_o_object					destination java object to decode xml information 
	 * @param p_o_xsdElement				current xml schema element
	 * @return								true - xml attribute has a value, false - could not parse value for xml attribute
	 * @throws IllegalArgumentException		element with value does not match structure or restrictions
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private boolean parseXMLAttributesForValidation(java.util.List<String> p_a_xmlTags, int p_i_min, XSDElement p_o_xsdElement) throws IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		/* return value */
		boolean b_hasAttributes = false;
		
		/* get attributes of xml element */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("[^<>\\s/=]*?=\"[^<>/\"]*?\"");
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xmlTags.get(p_i_min));
    	
		while (o_matcher.find()) {
			String s_attribute = o_matcher.group();
			String s_attributeName = s_attribute.substring(0, s_attribute.indexOf("="));
			boolean b_found = false;
			
			for (XSDAttribute o_xsdAttribute : p_o_xsdElement.getAttributes()) {
				if (s_attributeName.contentEquals(o_xsdAttribute.getName())) {
					String s_attributeValue = s_attribute.substring(s_attribute.indexOf("=") + 1);
    				s_attributeValue = s_attributeValue.substring(1, s_attributeValue.length() - 1);
    														net.forestany.forestj.lib.Global.ilogFiner("\t\t\t\tfound Attribute [" + s_attributeName + "] with value=" + s_attributeValue);
    				
					/* if attribute is required but value is empty, throw exception */
					if ( (o_xsdAttribute.getRequired()) && (net.forestany.forestj.lib.Helper.isStringEmpty(s_attributeValue)) ) {
						throw new IllegalArgumentException("Missing attribute value for xs:attribute[" + o_xsdAttribute.getName() + "." + o_xsdAttribute.getType() + "]");
					}
					
					/* check if xs:element has any restrictions */
					if (o_xsdAttribute.getRestriction()) {
						boolean b_enumerationFound = false;
						boolean b_enumerationReturnValue = false;
						
						for (XSDRestriction o_xsdRestriction : o_xsdAttribute.getRestrictions()) {
							if (o_xsdRestriction.getName().toLowerCase().contentEquals("enumeration")) {
								b_enumerationFound = true;
							}
							
							b_enumerationReturnValue = this.checkRestriction(s_attributeValue, o_xsdRestriction, o_xsdAttribute.getType());
							
							if ( (b_enumerationFound) && (b_enumerationReturnValue) ) {
								break;
							}
						}
						
						if ( (b_enumerationFound) && (!b_enumerationReturnValue) ) {
							throw new IllegalArgumentException("Attribute[" + o_xsdAttribute.getName() + "] with value[" + s_attributeValue + "] does not match enumaration restrictions defined in xsd-schema for this xs:attribute");
						}
					}
    				
					@SuppressWarnings("unused")
					Object o_foo = this.castObjectFromStringForValidation(s_attributeValue, o_xsdAttribute.getType());
	    				
    				b_found = true;
    				b_hasAttributes = true;
				}
			}
			
			if (!b_found) {
				throw new IllegalArgumentException("Xml attribute[" + s_attributeName + "] not expected and not availalbe in xsd-schema at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
			}
		}
		
		return b_hasAttributes;
	}
	
	/**
	 * Convert a string value from a xml element to an object to decode it into an object
	 * 
	 * @param p_s_value						string value of xml element from file
	 * @param p_s_type						type of destination object field, conform to xsd schema
	 * @param p_s_fieldType					field type of destination object field, java field type
	 * @return								casted object value from string
	 * @throws IllegalArgumentException		invalid type parameter
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private Object castObjectFromStringForValidation(String p_s_value, String p_s_type) throws IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		Object o_foo = null;
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			p_s_type = p_s_type.toLowerCase();
			
			java.util.List<String> a_stringTypes = java.util.Arrays.asList("string", "duration", "hexbinary", "base64binary", "anyuri", "normalizedstring", "token", "language", "name", "ncname", "nmtoken", "id", "idref", "entity");
			java.util.List<String> a_integerTypes = java.util.Arrays.asList("integer", "int", "positiveinteger", "nonpositiveinteger", "negativeinteger", "nonnegativeinteger", "byte", "unsignedint", "unsignedbyte");
			
			if (p_s_type.contentEquals("boolean")) {
				o_foo = Boolean.valueOf(p_s_value);
			} else if (a_stringTypes.contains(p_s_type)) {
				if ( (p_s_value.contentEquals("&#x200B;")) && (this.b_printEmptyString) ) {
					p_s_value = "";
				}
				
				o_foo = p_s_value;
			} else if (p_s_type.contentEquals("datetime")) {
				if (!net.forestany.forestj.lib.Helper.isDateTime(p_s_value)) {
					throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'datetime'");
				}
				
				if (this.b_useISO8601UTC) {
					o_foo = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
				} else {
					o_foo = net.forestany.forestj.lib.Helper.fromDateTimeString(p_s_value);
				}
			} else if (p_s_type.contentEquals("date")) {
				if (this.b_useISO8601UTC) {
					if (!net.forestany.forestj.lib.Helper.isDateTime(p_s_value)) {
						throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'date'");
					}
					
					o_foo = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value).toLocalDate();
				} else {
					if (!net.forestany.forestj.lib.Helper.isDate(p_s_value)) {
						throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'date'");
					}
					
					o_foo = net.forestany.forestj.lib.Helper.fromDateString(p_s_value);
				}
			} else if (p_s_type.contentEquals("time")) {
				if (this.b_useISO8601UTC) {
					if (!net.forestany.forestj.lib.Helper.isDateTime(p_s_value)) {
						throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'time'");
					}
					
					o_foo = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value).toLocalTime();
				} else {
					if (!net.forestany.forestj.lib.Helper.isTime(p_s_value)) {
						throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'time'");
					}
						
					o_foo = net.forestany.forestj.lib.Helper.fromTimeString(p_s_value);
				}
			} else if (p_s_type.contentEquals("decimal")) {
				java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat();
				o_decimalFormat.setParseBigDecimal(true);
				o_foo = o_decimalFormat.parseObject(p_s_value);
				o_foo = new java.math.BigDecimal(p_s_value);
			} else if (p_s_type.contentEquals("double")) {
				o_foo = Double.parseDouble(p_s_value);
			} else if (p_s_type.contentEquals("float")) {
				o_foo = Float.parseFloat(p_s_value);
			} else if (a_integerTypes.contains(p_s_type)) {
				o_foo = Integer.parseInt(p_s_value);
			} else if (p_s_type.contentEquals("long") || p_s_type.contentEquals("unsignedlong")) {
				o_foo = Long.parseLong(p_s_value);
			} else if (p_s_type.contentEquals("short") || p_s_type.contentEquals("unsignedshort")) {
				o_foo = Short.parseShort(p_s_value);
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] for " + p_s_value);
			}
		}
		
		return o_foo;
	}
	
	/* decoding XML to data with XSD schema */
	
	/**
	 * Decode xml file to an java object
	 * 
	 * @param p_s_xmlFile										full-path to xml file
	 * @return													xml decoded java object
	 * @throws IllegalArgumentException							xml file does not exist
	 * @throws java.io.IOException								cannot read xml file content
	 * @throws NullPointerException								empty schema, empty xml file or root node after parsing xml content
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 * @throws java.text.ParseException							exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException						exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InstantiationException							could not create new object instance returning at the end of the method
	 * @throws ClassNotFoundException							could not retrieve class by string class name
	 */
	public Object xmlDecode(String p_s_xmlFile) throws IllegalArgumentException, java.io.IOException, NullPointerException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException, InstantiationException, ClassNotFoundException {
		/* check root field */
		if (this.o_root == null) {
			throw new NullPointerException("Cannot decode data. Root is null.");
		}
		
		/* check if file exists */
		if (!File.exists(p_s_xmlFile)) {
			throw new NullPointerException("XML file[" + p_s_xmlFile + "] does not exist.");
		}
		
		/* open xml file */
		File o_file = new File(p_s_xmlFile, false);
		
												net.forestany.forestj.lib.Global.ilogFiner("read all lines from xml file '" + p_s_xmlFile + "'");
		
		/* decode xml file lines */										
		return xmlDecode(o_file.getFileContentAsList());
	}
	
	/**
	 * Decode xml file to an java object
	 * 
	 * @param p_a_xmlTags										xml lines
	 * @return													xml decoded java object
	 * @throws IllegalArgumentException							xml file does not exist
	 * @throws java.io.IOException								cannot read xml file content
	 * @throws NullPointerException								empty schema, empty xml file or root node after parsing xml content
	 * @throws NoSuchFieldException								could not retrieve field type by field name
	 * @throws NoSuchMethodException							could not retrieve method by method name
	 * @throws java.lang.reflect.InvocationTargetException 		could not invoke method from object
	 * @throws IllegalAccessException							could not invoke method, access violation
	 * @throws java.text.ParseException							exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException						exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InstantiationException							could not create new object instance returning at the end of the method
	 * @throws ClassNotFoundException							could not retrieve class by string class name
	 */
	public Object xmlDecode(java.util.List<String> p_a_xmlTags) throws IllegalArgumentException, java.io.IOException, NullPointerException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException, InstantiationException, ClassNotFoundException {
		Object o_foo = null;
		
		/* check root field */
		if (this.o_root == null) {
			throw new NullPointerException("Cannot decode data. Schema is null.");
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("read all lines: '" + p_a_xmlTags.size() + "'");
		
		StringBuilder o_stringBuilder = new StringBuilder();
		
		/* read all xml schema file lines to one string builder */
		for (String s_line : p_a_xmlTags) {
			o_stringBuilder.append(s_line);
		}
		
		/* read all xml lines */
		String s_xml = o_stringBuilder.toString().replaceAll("[\\r\\n\\t]", "").replaceAll(">\\s*<", "><");
		
		/* clean up xml file */
		s_xml = s_xml.replaceAll("<\\?(.*?)\\?>", "");
		s_xml = s_xml.replaceAll("<!--(.*?)-->", "");
		
												net.forestany.forestj.lib.Global.ilogFinest("cleaned up xml file lines");
		
		/* validate xml */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("(<[^<>]*?<[^<>]*?>|<[^<>]*?>[^<>]*?>)");
		java.util.regex.Matcher o_matcher = o_regex.matcher(s_xml);
		
		/* if regex-matcher has match, the xml file is not valid */
	    while (o_matcher.find()) {
	        throw new IllegalArgumentException("Invalid xml-file. Please check xml-file at \"" + o_matcher.group(0) + "\".");
	    }
		
	    java.util.List<String> a_xmlTags = new java.util.ArrayList<String>();
	    
	    /* add all xml-tags to a list for parsing */
	    o_regex = java.util.regex.Pattern.compile("(<[^<>/]*?/>)|(<[^<>/]*?>[^<>]*?</[^<>/]*?>)|(<[^<>/]*?>)|(</[^<>/]*?>)");
	    o_matcher = o_regex.matcher(s_xml);
	    
	    /* save all xml tags in one array */
	    while (o_matcher.find()) {
	    	String s_xmlTag = o_matcher.group(0);
	    	
	    	if ( (!s_xmlTag.startsWith("<")) && (!s_xmlTag.endsWith(">")) ) {
	    		throw new IllegalArgumentException("Invalid xml-tag. Please check xml-file at \"" + s_xmlTag + "\".");
	    	}
	    	
	    	a_xmlTags.add(s_xmlTag);
	    }
	    
	    /* validate xml tree recursively */
	    this.validateXMLDocument(a_xmlTags, 0, a_xmlTags.size() - 1);
	    
	    										net.forestany.forestj.lib.Global.ilogFinest("validated xml content lines");
	    										
	    										if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("XML-Schema:" + net.forestany.forestj.lib.io.File.NEWLINE + this.o_root);
											    if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("XML:" + net.forestany.forestj.lib.io.File.NEWLINE + s_xml);
	    
	    /* decode xml tree recursively */
	    o_foo = this.xmlDecodeRecursive(a_xmlTags, 0, a_xmlTags.size() - 1, o_foo, this.o_root);
		
	    										net.forestany.forestj.lib.Global.ilogFinest("decoded xml content lines");
	    
		return o_foo;
	}
	
	/**
	 * Check if given xml file or xml lines are valid
	 * 
	 * @param p_a_xmlTags					xml lines
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_i_max						pointer where to end line iteration
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		validation failed for decoding xml document correctly, maybe type is not valid
	 */
	private void validateXMLDocument(java.util.List<String> p_a_xmlTags, int p_i_min, int p_i_max) throws NullPointerException, IllegalArgumentException {
		boolean b_parsed = false;
    	
	    /* iterate all elements */
	    for (int i_min = p_i_min; i_min <= p_i_max; i_min++) {
	    	/* get xml type */
	    	XMLType e_xmlType = this.getXMLType(p_a_xmlTags.get(i_min));
			
	    	/* check if we have a valid xml type */
	    	if (e_xmlType == null) {
	    		throw new NullPointerException("Invalid xml-type at(" + (i_min + 2) + ".-element) \"" + p_a_xmlTags.get(i_min) + "\".");
	    	}
	    	
	    	/* variable for xml element name */
    		String s_xmlElementName = null;
	    	
    		/* get xml element name */
    		if ( (e_xmlType == XMLType.BeginWithAttributes) || (e_xmlType == XMLType.ElementWithAttributes) || (e_xmlType == XMLType.EmptyWithAttributes) ) {
    			s_xmlElementName = p_a_xmlTags.get(i_min).substring(1, p_a_xmlTags.get(i_min).indexOf(" "));
    		} else if (e_xmlType == XMLType.Empty) {
    			s_xmlElementName = p_a_xmlTags.get(i_min).substring(1, p_a_xmlTags.get(i_min).indexOf("/"));
    		} else {
    			s_xmlElementName = p_a_xmlTags.get(i_min).substring(1, p_a_xmlTags.get(i_min).indexOf(">"));
    		}
    		
    		/* check if xml element name is set */
    		if (net.forestany.forestj.lib.Helper.isStringEmpty(s_xmlElementName)) {
    			throw new NullPointerException("No xml element name in xml file at(" + (i_min + 2) + ".-element) \"" + p_a_xmlTags.get(i_min) + "\".");
    		}
    		
	    	/* we found a xml element with interlacing */
	    	if ( (e_xmlType == XMLType.BeginNoAttributes) || (e_xmlType == XMLType.BeginWithAttributes) ) {
	    		/* if we have another xs:element with interlacing we may need another recursion, if it was already parsed */
	    		if (b_parsed) {
	    			int i_oldMax = p_i_max;
	    			int i_tempMin = i_min + 1;
	    			int i_level = 0;
	    			
	    			/* look for end of nested xml element tag */
	    			while ( (!this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.Close)) || (i_level != 0) ) {
	    				if ( (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.BeginNoAttributes)) || (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.BeginWithAttributes)) ) {
	    					i_level++;
	    				} else if (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.Close)) {
	    					i_level--;
	    				}
	    				
	    				if (i_tempMin == p_i_max) {
	    					/* forbidden state - interlacing is not valid in xml file */
	    					throw new IllegalArgumentException("Invalid nested xml element at(" + (i_min + 2) + ".-element) \"" + p_a_xmlTags.get(i_min) + "\".");
	    				}
	    				
	    				i_tempMin++;
		    		}
	    			
	    													net.forestany.forestj.lib.Global.ilogFiner("found interlacing xml element(" + (i_min + 2) + ") - " + p_a_xmlTags.get(i_min));
	    			
	    			/* validate interlacing recursive */
	    			this.validateXMLDocument(p_a_xmlTags, i_min, i_tempMin);
	    			
	    			i_min = i_tempMin;
	    			p_i_max = i_oldMax;
	    			continue;
	    		}
	    		
	    		/* if we have no closing tag, then our xml file is invalid */
	    		if (!this.getXMLType(p_a_xmlTags.get(p_i_max)).equals(XMLType.Close)) {
	    			throw new IllegalArgumentException("Invalid xml element is not closed in xml file at(" + (i_min + 2) + ".-element) \"" + p_a_xmlTags.get(i_min) + "\".");
	    		}
	    		
	    		/* get xml end type */
		    	XMLType e_xmlEndType = this.getXMLType(p_a_xmlTags.get(p_i_max));
				
		    	/* check if we have a valid xml type */
		    	if (e_xmlEndType == null) {
		    		throw new NullPointerException("Invalid xml-type at(" + (p_i_max + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_max) + "\".");
		    	}
		    	
		    	/* get xml close element name */
		    	String s_xmlEndElementName = p_a_xmlTags.get(p_i_max).substring(2, p_a_xmlTags.get(p_i_max).indexOf(">"));
		    	
	    		/* xml element name and xml close element name must match */
	    		if (!s_xmlElementName.contentEquals(s_xmlEndElementName)) {
	    			throw new IllegalArgumentException("Xml-tag has no valid closing tag in xml file at(" + (i_min + 2) + ".-element) \"" + p_a_xmlTags.get(i_min) + "\".");
	    		} else {
	    			p_i_max--;
	    		}
	    	}
	    	
	    	b_parsed = true;
	    }
	}
	
	/**
	 * Recursive method to decode xml string to a java object and it's fields
	 * 
	 * @param p_a_xmlTags					xml lines
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_i_max						pointer where to end line iteration
	 * @param p_o_object					destination java object to decode xml information
	 * @param p_o_xsdElement				current xsd element of schema with information to decode xml data
	 * @return								decoded xml information as java object
	 * @throws NullPointerException			value in schema or expected element is not available
	 * @throws IllegalArgumentException		condition failed for decoding xml correctly
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InstantiationException		could not create new object instance returning at the end of the method
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	private Object xmlDecodeRecursive(java.util.List<String> p_a_xmlTags, int p_i_min, int p_i_max, Object p_o_object, XSDElement p_o_xsdElement) throws NullPointerException, IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException, InstantiationException, ClassNotFoundException {
		/* check xml tag pointer */
		if (p_i_min > p_i_max) {
			throw new IllegalArgumentException("Xml tag pointer overflow(" + p_i_min + " >= " + p_i_max + ").");
		}
		
		/* get xml type */
    	XMLType e_xmlType = this.getXMLType(p_a_xmlTags.get(p_i_min));
		
    	/* check if we have a valid xml type */
    	if (e_xmlType == null) {
    		throw new NullPointerException("Invalid xml-type at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
    	}
    	
    	/* variable for xml element name */
		String s_xmlElementName = null;
    	
		/* get xml element name */
		if ( (e_xmlType == XMLType.BeginWithAttributes) || (e_xmlType == XMLType.ElementWithAttributes) || (e_xmlType == XMLType.EmptyWithAttributes) ) {
			s_xmlElementName = p_a_xmlTags.get(p_i_min).substring(1, p_a_xmlTags.get(p_i_min).indexOf(" "));
		} else if (e_xmlType == XMLType.Empty) {
			s_xmlElementName = p_a_xmlTags.get(p_i_min).substring(1, p_a_xmlTags.get(p_i_min).indexOf("/"));
		} else {
			s_xmlElementName = p_a_xmlTags.get(p_i_min).substring(1, p_a_xmlTags.get(p_i_min).indexOf(">"));
		}
		
		/* check if xml element name is set */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(s_xmlElementName)) {
			throw new NullPointerException("No xml element name in xml file at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
		}
		
		/* check if we expect current xml element */
    	if (!s_xmlElementName.contentEquals(p_o_xsdElement.getName())) {
    		/* maybe it is not the expected root, but just a sub tree of a valid xsd schema, so we look for it in the element definitions */
    		for (XSDElement o_temp : this.a_elementDefinitons) {
    			/* find xml element as element definition of xsd schema */
    			if (s_xmlElementName.contentEquals(o_temp.getName())) {
															net.forestany.forestj.lib.Global.ilogFiner("xs:element " + p_o_xsdElement.getName() + ", is not root element of schema, but a valid sub tree, so take this sub tree as entry point for decoding");
    				
    				/* decode xml recursively with sub tree of xsd schema */
    				return this.xmlDecodeRecursive(p_a_xmlTags, p_i_min, p_i_max, p_o_object, o_temp);
    			}
    		}
    		
    		throw new IllegalArgumentException(s_xmlElementName + " with type(" + e_xmlType + ") is not expected xs:element " + p_o_xsdElement.getName() + " at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
    	}
    	
		/* if we have xs:element definition with no type, exact one child element and mapping contains ":"
		 * we have to iterate a list of object and must print multiple xml elements
		 * otherwise we have usual xs:element definitions for current element
		 */
		if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_o_xsdElement.getType())) && (p_o_xsdElement.getMapping().contains(":")) && (p_o_xsdElement.getChildren().size() == 1) ) {
			/* check if current xml element has interlacing */
			if (!(e_xmlType == XMLType.BeginNoAttributes)) {
				/* if list has no children, check minOccurs attribute of xs:element child definition */
				if (p_o_xsdElement.getChildren().get(0).getMinOccurs() != 0) {
					throw new NullPointerException("List with [" + p_o_xsdElement.getChildren().get(0).getName() + "] xml tags is empty, minimum = " + p_o_xsdElement.getChildren().get(0).getMinOccurs());
				}
			}
			
													net.forestany.forestj.lib.Global.ilogFiner("xs:element " + p_o_xsdElement.getName() + " with no type, exact one child element as definition and list as mapping");
			
			/* hold parent object value for primitive arrays */
			Object o_parentObject = p_o_object;
													
			/* create or retrieve object list data */
			if (p_o_object == null) { /* create a new object instance of xml element */
														net.forestany.forestj.lib.Global.ilogFiner(" - create new list object");
				
    			/* create list object */
				p_o_object = (Object)(new java.util.ArrayList<Object>());
			} else { /* we have to retrieve the list object */
														net.forestany.forestj.lib.Global.ilogFiner(" - retrieve list object");
														
				/* get java type */
	    		String s_javaType = p_o_xsdElement.getMapping();
	    		
				/* remove enclosure of java type if it exists */
				s_javaType = s_javaType.substring(0, s_javaType.indexOf(":"));
				
				/* check if we use property methods with invoke to set object data values */
				if (this.b_usePropertyMethods) {
    				java.lang.reflect.Method o_method = null;
	    			boolean b_methodFound = false;
	    			
	    			/* look for get-property-method for list object */
	    			for (java.lang.reflect.Method o_methodSearch : p_o_object.getClass().getDeclaredMethods()) {
	    				if (o_methodSearch.getName().contentEquals("get" + s_javaType)) {
	    					o_method = o_methodSearch;
	    					b_methodFound = true;
	    				}
	    			}
	    			
	    			/* if we have found property-method to get list object */
	    			if (b_methodFound) {
						/* invoke get-property-method */
						Object o_object = o_method.invoke(p_o_object);
						
						/* do not check if object is null or instance of java.util.List if we handle a primitive array */
						if ( !( (net.forestany.forestj.lib.Helper.isStringEmpty(p_o_xsdElement.getType())) && (p_o_xsdElement.getMapping().endsWith("[]")) ) ) {
							/* check if list object is not null */
							if (o_object == null) {
								throw new NullPointerException("List object from method[" + "get" + s_javaType + "] not initialised for object: " + p_o_object.getClass().getTypeName());
							}
							
							/* check if object is of type List */
							if (!(o_object instanceof java.util.List)) {
								throw new IllegalArgumentException("Object from method[" + "get" + s_javaType + "] is not a list object for object: " + p_o_object.getClass().getTypeName());
							}
						}
						
						/* set list object */
						p_o_object = o_object;
	    			} else {
	    				throw new NoSuchMethodException("Method[" + "get" + s_javaType + "] does not exist for object: " + p_o_object.getClass().getTypeName());
	    			}
				} else {
					/* call field directly to set object data values */
					try {
						p_o_object = p_o_object.getClass().getDeclaredField(s_javaType).get(p_o_object);
					} catch (Exception o_exc) {
						throw new NoSuchFieldException("Property[" + s_javaType + "] is not accessible for object: " + p_o_object.getClass().getTypeName());
					}
				}
    		}
			
			int i_tempMin = p_i_min + 1;
			int i_level = 0;
			
			/* look for end of nested xml element tag */
			while ( (!this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.Close)) || (i_level != 0) ) {
				if ( (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.BeginNoAttributes)) || (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.BeginWithAttributes)) ) {
					i_level++;
				} else if (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.Close)) {
					i_level--;
				}
				
				if (i_tempMin >= p_i_max) {
					/* forbidden state - interlacing is not valid in xml file */
					throw new IllegalArgumentException("Invalid nested xml element at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
				}
				
				i_tempMin++;
    		}
			
													net.forestany.forestj.lib.Global.ilogFiner("\tfound interlacing xml element(" + (p_i_min + 2) + " to " + (i_tempMin + 2) + ") - " + p_a_xmlTags.get(p_i_min) + " ... " + p_a_xmlTags.get(i_tempMin));
			
			/* create occurrence counter */
			int i_occurCnt = 0;
			
			int i_tempMin2 = p_i_min + 2;
			int i_until = i_tempMin - 1;
			
			/* if we handle an object list of primitives or a primitive array */
			if ( (p_o_xsdElement.getChildren().get(0).getIsArray()) && (p_a_xmlTags.get(p_i_min).substring(1).contentEquals(p_a_xmlTags.get(i_tempMin).substring(2))) ) {
														net.forestany.forestj.lib.Global.ilogFiner("\t\tchange border counters, because first child of current xsd-element is an array and opening+closing xml-tags are matching");
				i_tempMin2 = p_i_min + 1;
				i_until = i_tempMin;
			}
			
			/* check if we just have an empty xml-element here */
			if (p_a_xmlTags.get(p_i_min).contentEquals("<" + p_o_xsdElement.getName() + "/>")) {
				i_tempMin2 = p_i_max;
			}
			
			/* we have to iterate a list of objects, so we must check if there is another nested element */
			/* if the end of nested element not corresponds to end of interlacing, we continue to look for another nested element */
			while (i_tempMin2 < i_until) {
				i_tempMin2 = p_i_min + 2;
				i_level = 0;
				
				/* look for end of another nested xml element tag */
				while ( (!this.getXMLType(p_a_xmlTags.get(i_tempMin2)).equals(XMLType.Close)) || (i_level != 0) ) {
					if ( (this.getXMLType(p_a_xmlTags.get(i_tempMin2)).equals(XMLType.BeginNoAttributes)) || (this.getXMLType(p_a_xmlTags.get(i_tempMin2)).equals(XMLType.BeginWithAttributes)) ) {
						i_level++;
					} else if (this.getXMLType(p_a_xmlTags.get(i_tempMin2)).equals(XMLType.Close)) {
						i_level--;
					}
					
					if (i_tempMin2 >= p_i_max) {
						/* forbidden state - interlacing is not valid in xml file */
						throw new IllegalArgumentException("Invalid nested xml element at(" + (i_tempMin + 1 + 2) + ".-element) \"" + p_a_xmlTags.get(i_tempMin + 1) + "\".");
					}
					
					i_tempMin2++;
	    		}
				
														net.forestany.forestj.lib.Global.ilogFiner("\t\tfound list xml element(" + (p_i_min + 3) + " to " + (i_tempMin2 + 2) + ") - " + p_a_xmlTags.get(p_i_min + 1) + " ... " + p_a_xmlTags.get(i_tempMin2));
				
				/* variable with mapping value of element level */
				String s_oldMapping = p_o_xsdElement.getChildren().get(0).getMapping();
														
				/* if we handle a primitive array list */
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_o_xsdElement.getType()) && (p_o_xsdElement.getMapping().endsWith("[]"))) {
					/* assume mapping to element level */
					if (p_o_xsdElement.getMapping().contains(":")) {
						p_o_xsdElement.getChildren().get(0).setMapping(p_o_xsdElement.getMapping().split(":")[1]);
					} else {
						p_o_xsdElement.getChildren().get(0).setMapping(p_o_xsdElement.getMapping());
					}
				}
				
				/* handle nested xml element recursively */
				Object o_returnObject = this.xmlDecodeRecursive(p_a_xmlTags, ++p_i_min, i_tempMin, p_o_object, p_o_xsdElement.getChildren().get(0));

				/* undo change of mapping on element level */
				if (net.forestany.forestj.lib.Helper.isStringEmpty(p_o_xsdElement.getType()) && (p_o_xsdElement.getMapping().endsWith("[]"))) {
					p_o_xsdElement.getChildren().get(0).setMapping(s_oldMapping);
				}
				
				/* add return object of recursion to list */
				if (o_returnObject != null) {
					if ( (net.forestany.forestj.lib.Helper.isStringEmpty(p_o_xsdElement.getType())) && (p_o_xsdElement.getMapping().endsWith("[]")) && (p_o_xsdElement.getChildren().get(0).getIsArray()) ) {
						/* get mapping of current xsd-element */
						String s_mapping = p_o_xsdElement.getMapping();
						
						/* get first part of mapping */
						if (s_mapping.contains(":")) {
							s_mapping = s_mapping.split(":")[0];
						}
						
						/* set array elements in generic object list (o_returnObject) as elements of primitive array */
						/* access by mapping and field type and using parent object to set primitive array in parent class */
						this.setPrimitiveArrayProperty(s_mapping, p_o_xsdElement.getChildren().get(0).getType(), o_parentObject, o_returnObject);
					} else if (p_o_xsdElement.getChildren().get(0).getIsArray()) {
						/* return object of recursion must be of instance java.util.List */
						if (!(o_returnObject instanceof java.util.List)) {
							throw new IllegalArgumentException("object type '" + o_returnObject.getClass().getTypeName() + "' of return object is not of instance 'java.util.List'");
						}
						
																net.forestany.forestj.lib.Global.ilogFiner("\t\tOverwrite " + s_xmlElementName + " empty list with " + o_returnObject.getClass().getTypeName() + " returned list from recursion");
						
						/* overwrite current empty list with filled parsed list */
						p_o_object = o_returnObject;
						
						/* increase occur counter with list size */
						@SuppressWarnings("unchecked")
						java.util.List<Object> o_temp = (java.util.List<Object>)p_o_object;
						i_occurCnt += o_temp.size();
					} else {
						/* increase occur counter */
						i_occurCnt++;
						
																net.forestany.forestj.lib.Global.ilogFiner("\t\tAdd to " + s_xmlElementName + " list " + o_returnObject.getClass().getTypeName() + " returned object from recursion: " + p_o_object.getClass().getTypeName());
						@SuppressWarnings("unchecked")
						java.util.List<Object> o_temp = (java.util.List<Object>)p_o_object;
						o_temp.add(o_returnObject);
					}
				}
				
				/* update xml tag pointer */
				p_i_min = i_tempMin2;
			}
			
			/* update xml tag pointer */
			p_i_min = i_tempMin;
			
													net.forestany.forestj.lib.Global.ilogFiner("\tend interlacing, continue from " + (p_i_min + 2) + " to " + (p_i_max + 2));
			
			/* check minOccurs attribute of child xs:element of current list */
			if (p_o_xsdElement.getChildren().get(0).getMinOccurs() > i_occurCnt) {
				throw new IllegalArgumentException("Not enough [" + p_o_xsdElement.getChildren().get(0).getName() + "] xml tags, minimum = " + p_o_xsdElement.getChildren().get(0).getMinOccurs());
			}
			
			/* check maxOccurs attribute of child xs:element of current list */
			if ( (p_o_xsdElement.getChildren().get(0).getMaxOccurs() >= 0) && (i_occurCnt > p_o_xsdElement.getChildren().get(0).getMaxOccurs()) ) {
				throw new IllegalArgumentException("Too many [" + p_o_xsdElement.getChildren().get(0).getName() + "] xml tags, maximum = " + p_o_xsdElement.getChildren().get(0).getMaxOccurs());
			}
		} else if (p_o_xsdElement.getName().contentEquals(p_o_xsdElement.getType())) { /* handle array elements as xml element tags */
													net.forestany.forestj.lib.Global.ilogFiner("\t\t\t\titerate array elements from " + p_i_min + "[" + p_a_xmlTags.get(p_i_min) + "] - " + p_i_max + "[" + p_a_xmlTags.get(p_i_max - 1) + "]");
			
			/* object parameter is not of instance java.util.List */
			if (!(p_o_object instanceof java.util.List)) {
				/* xs:element must have a primitive array type */
				if (!( (!net.forestany.forestj.lib.Helper.isStringEmpty(p_o_xsdElement.getMapping())) && (p_o_xsdElement.getMapping().endsWith("[]")) )) {
					throw new IllegalArgumentException("xsd-element '" + p_o_xsdElement.getName() + "' has not a primitive array type which ends with '[]': '" + p_o_xsdElement.getMapping() + "'.");
				}
				
				/* if object is null, initialize new object list */
				if (p_o_object == null) {
					p_o_object = new java.util.ArrayList<Object>();
				}
			}
			
			/* cast object parameter to object list, so we can add new elements */
			@SuppressWarnings("unchecked")
			java.util.List<Object> o_tempList = (java.util.List<Object>)p_o_object;
			
			/* iterate elements */
			for (int i = p_i_min; i < p_i_max; i++) {
				/* object variable where value of xml element tag will be parsed in */
				Object o_value = null;
				/* flag if current element returned value */
				boolean b_returnedValue = false;
				
				/* get value if xml element is not empty */
				if (!( (e_xmlType == XMLType.Empty) || (e_xmlType == XMLType.EmptyWithAttributes) )) { 
					/* get value of xml element */
					java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("(<" + p_o_xsdElement.getName() + ">)([^<>]*?)(</" + p_o_xsdElement.getName() + ">)");
					java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xmlTags.get(i));
					
					if (o_matcher.find()) {
						String s_elementValue = o_matcher.group(2);
						
						/* check if xs:element has any restrictions */
						if (p_o_xsdElement.getRestriction()) {
							boolean b_enumerationFound = false;
							boolean b_enumerationReturnValue = false;
							
							for (XSDRestriction o_xsdRestriction : p_o_xsdElement.getRestrictions()) {
								if (o_xsdRestriction.getName().toLowerCase().contentEquals("enumeration")) {
									b_enumerationFound = true;
								}
								
								b_enumerationReturnValue = this.checkRestriction(s_elementValue, o_xsdRestriction, p_o_xsdElement.getType());
								
								if ( (b_enumerationFound) && (b_enumerationReturnValue) ) {
									break;
								}
							}
							
							if ( (b_enumerationFound) && (!b_enumerationReturnValue) ) {
								throw new IllegalArgumentException("Element[" + p_o_xsdElement.getName() + "] with value[" + s_elementValue + "] does not match enumaration restrictions defined in xsd-schema for this xs:element");
							}
						}
						
						/* check if element has value and if element value is not empty string value */
						if ( (!net.forestany.forestj.lib.Helper.isStringEmpty(s_elementValue)) && (!( (this.b_printEmptyString) && (s_elementValue.toString().contentEquals("&#x200B;")) )) ) {
							/* cast string value to object and set returned value flag */
							o_value = this.castObjectFromString(s_elementValue, p_o_xsdElement.getType(), p_o_xsdElement.getType());
							b_returnedValue = true;
						}
					}
				}
				
														net.forestany.forestj.lib.Global.ilogFinest("\t\t\t\t\tarray element[" + p_o_xsdElement.getName() + "] returned value=" + b_returnedValue);
				
				/* check minOccurs attribute of xml element and if value is empty */
				if ( (p_o_xsdElement.getMinOccurs() > 0) && (!b_returnedValue) ) {
					if (p_o_xsdElement.getChoice()) {
						p_i_min--;
						continue;
					} else {
						throw new NullPointerException("Missing element value for xs:element[" + p_o_object.getClass().getTypeName() + "." + p_o_xsdElement.getName() + "]");
					}
				}
				
				if (b_returnedValue) {
					/* add value to list */
					o_tempList.add(o_value);
				} else {
					/* add null to list */
					o_tempList.add(null);
				}
			}
			
			/* check minOccurs attribute of xs:element */
			if (p_o_xsdElement.getMinOccurs() > o_tempList.size()) {
				throw new IllegalArgumentException("Not enough [" + p_o_xsdElement.getName() + "] xml tags, minimum = " + p_o_xsdElement.getMinOccurs());
			}
			
			/* check maxOccurs attribute of xs:element */
			if ( (p_o_xsdElement.getMaxOccurs() >= 0) && (o_tempList.size() > p_o_xsdElement.getMaxOccurs()) ) {
				throw new IllegalArgumentException("Too many [" + p_o_xsdElement.getName() + "] xml tags, maximum = " + p_o_xsdElement.getMaxOccurs());
			}
		} else {
			/* check if class type of object is a inner class */
			if (p_o_xsdElement.getMapping().contains("$")) {
				/* get target class */
				Class<?> o_targetClass = Class.forName(p_o_xsdElement.getMapping());
				
				/* get instance of parent class */
				Object o_parentClass = Class.forName(p_o_xsdElement.getMapping().split("\\$")[0]).getDeclaredConstructor().newInstance();
				boolean b_found = false;
				
				/* look for declared inner classes in parent class */
				for (Class<?> o_subClass : o_parentClass.getClass().getDeclaredClasses()) {
					/* inner class must match with Class<?> type parameter of dynamic generic list */
					if (o_subClass == o_targetClass) {
						b_found = true;
						/* create new object instance of inner class, with help of parent class instance */
						p_o_object = o_targetClass.getDeclaredConstructor(o_parentClass.getClass()).newInstance(o_parentClass);
						break;
					}
				}
				
				/* throw exception if inner class could not be found */
				if (!b_found) {
					throw new ClassNotFoundException("Could not found inner class in scope '" + o_targetClass.getTypeName() + "'");
				}
			} else {
				if (!p_o_xsdElement.getMapping().contentEquals("_skipLevel_")) {
					/* create new object instance which will be returned at the end of this function */
					p_o_object = Class.forName(p_o_xsdElement.getMapping()).getDeclaredConstructor().newInstance();
				}
			}
			
			/* flag if current element returned attributes */
			boolean b_returnedAttributes = false;
			
			/* check if current xml element should have attributes */
			if (p_o_xsdElement.getAttributes().size() > 0) {
				boolean b_required = false;
				
				/* check if one attribute of the xml element is required */
				for (XSDAttribute o_xsdAttribute : p_o_xsdElement.getAttributes()) {
					if (o_xsdAttribute.getRequired()) {
						b_required = true;
					}
				}
				
				/* check if current xml element has attributes */
				if ( ( (e_xmlType != XMLType.BeginWithAttributes) && (e_xmlType != XMLType.ElementWithAttributes) && (e_xmlType != XMLType.EmptyWithAttributes) ) && (b_required) ) {
					throw new IllegalArgumentException(s_xmlElementName + " has no attributes and is not compatible with xs:element (" + p_o_xsdElement.getName() + "[" + p_o_xsdElement.getMapping() + "])");
				}
				
				/* retrieve attributes */
				b_returnedAttributes = this.parseXMLAttributes(p_a_xmlTags, p_i_min, p_o_object, p_o_xsdElement);
			}
			
			/* create choice counter */
			int i_choiceCnt = 0;
			
			/* iterate all children of current xs:element */
			for (XSDElement o_xsdElement : p_o_xsdElement.getChildren()) {
														net.forestany.forestj.lib.Global.ilogFinest("\t\t\titerate children of " + p_o_xsdElement.getName() + ", choice=" + p_o_xsdElement.getChoice() + " ... child[" + o_xsdElement.getName() + "] with type[" + o_xsdElement.getType() + "] and attributes count[" + o_xsdElement.getAttributes().size() + "]");
				
				/* if xs:element has no primitive type we may have a special object definition */
				if (net.forestany.forestj.lib.Helper.isStringEmpty(o_xsdElement.getType())) {
					/* increase xml tag pointer */
					p_i_min++;
					
															net.forestany.forestj.lib.Global.ilogFinest("\t\t\t\tchild[" + o_xsdElement.getName() + "] compare to current xml tag[" + p_a_xmlTags.get(p_i_min) + "]");
					
					/* xml tag must match with expected xs:element name or we have an empty xml-element in combination with a primitive array */
					if ( (!p_a_xmlTags.get(p_i_min).startsWith("<" + o_xsdElement.getName())) || ( (p_a_xmlTags.get(p_i_min).contentEquals("<" + o_xsdElement.getName() + "/>")) && (o_xsdElement.getMapping().endsWith("[]")) ) ) {
						/* if we have a choice scope or child min. occurs is lower than 1, go to next xs:element */
						if ( (p_o_xsdElement.getChoice()) || (o_xsdElement.getMinOccurs() < 1) ) {
							/* only decrease min. pointer if we have not an empty xml-element */
							if (!p_a_xmlTags.get(p_i_min).contentEquals("<" + o_xsdElement.getName() + "/>")) {
								p_i_min--;
							}
							
							continue;
						}
						
						/* xml tag does not match with xs:element name */
						throw new IllegalArgumentException(p_a_xmlTags.get(p_i_min) + " is not expected xs:element " + o_xsdElement.getName() + " for recursion");
					}
					
					/* check choice counter */
					if ( (p_o_xsdElement.getChoice()) && (++i_choiceCnt > p_o_xsdElement.getMaxOccurs()) ) {
						throw new IllegalArgumentException(p_o_xsdElement.getMapping() + " has to many objects(" + i_choiceCnt + ") in xs:choice " + p_o_xsdElement.getName() + "(" + p_o_object.getClass().getTypeName() + "), maximum = " + p_o_xsdElement.getMaxOccurs());
					}
					
					int i_tempMin = p_i_min + 1;
					int i_level = 0;
					
															net.forestany.forestj.lib.Global.ilogFinest(p_i_min + "\t\t\tlook for recursion borders for [" + o_xsdElement.getName() + "] from " + (i_tempMin + 1));
					
					/* look for end of nested xml element tag */
					while ( (!this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.Close)) || (i_level != 0) ) {
																net.forestany.forestj.lib.Global.ilogFinest("\t\t\t" +i_level+ "\t"+p_a_xmlTags.get(i_tempMin));
						
						if ( (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.BeginNoAttributes)) || (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.BeginWithAttributes)) ) {
							i_level++;
						} else if (this.getXMLType(p_a_xmlTags.get(i_tempMin)).equals(XMLType.Close)) {
							i_level--;
						}
						
						if (i_tempMin >= p_i_max) {
							/* forbidden state - interlacing is not valid in xml file */
							throw new IllegalArgumentException("Invalid nested xml element at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
						}
						
						i_tempMin++;
		    		}
					
															net.forestany.forestj.lib.Global.ilogFinest("\t\t\t" + o_xsdElement.getName() + " has no primitive type -> new recursion (" + (p_i_min + 2) + " to " + (i_tempMin + 2) + ") - " + p_a_xmlTags.get(p_i_min) + " ... " + p_a_xmlTags.get(i_tempMin));
					
					/* handle xml element recursively */
					Object o_returnObject = this.xmlDecodeRecursive(p_a_xmlTags, p_i_min, i_tempMin, p_o_object, o_xsdElement);
					
					p_i_min = i_tempMin;
					
															net.forestany.forestj.lib.Global.ilogFinest("\t\t\tend recursion with [" + ((o_returnObject == null) ? "null" : o_returnObject.getClass().getTypeName()) + "], continue " + (p_i_min + 2) + " to " + (p_i_max + 2));
					
					/* handle return object of recursion */
					if (o_returnObject != null) {
																net.forestany.forestj.lib.Global.ilogFinest("\t\t\tReturned " + o_xsdElement.getMapping() + " - " + o_returnObject.getClass().getTypeName() + " for " + p_o_object.getClass().getTypeName());
						
						/* get mapping type */
			    		String s_mapping = o_xsdElement.getMapping();
						
			    		if (s_mapping.contentEquals("_skipLevel_")) {
			    			if (!o_returnObject.getClass().getTypeName().contentEquals(p_o_object.getClass().getTypeName())) {
			    				throw new IllegalArgumentException("Invalid return object type '" + o_returnObject.getClass().getTypeName() + "' for current element type '" + p_o_object.getClass().getTypeName() + "'.");
			    			}
			    			
			    			p_o_object = o_returnObject;
			    		} else {
				    		/* remove enclosure of mapping type if it exists */
							if (s_mapping.contains(":")) {
								s_mapping = s_mapping.substring(0, s_mapping.indexOf(":"));
							} else {
								/* remove package prefix */
								if (s_mapping.contains(".")) {
									s_mapping = s_mapping.substring(s_mapping.lastIndexOf(".") + 1, s_mapping.length());
								}
								
								/* remove internal class prefix */
								if (s_mapping.contains("$")) {
									s_mapping = s_mapping.substring(s_mapping.lastIndexOf("$") + 1, s_mapping.length());
								}
							}
				    		
							/* check if we use property methods with invoke to set object data values */
							if (this.b_usePropertyMethods) {
								java.lang.reflect.Method o_method = null;
				    			boolean b_methodFound = false;
				    			
				    			for (java.lang.reflect.Method o_methodSearch : p_o_object.getClass().getDeclaredMethods()) {
				    				if (o_methodSearch.getName().contentEquals("set" + s_mapping)) {
				    					o_method = o_methodSearch;
				    					b_methodFound = true;
				    				}
				    			}
				    			
				    			if (!b_methodFound) {
				    				throw new NoSuchMethodException("Method[" + "set" + s_mapping + "] does not exist for object: " + p_o_object.getClass().getTypeName());
				    			}
				    			
				    			o_method.invoke(p_o_object, o_returnObject);
							} else {
								/* call field directly to set object data values */
								try {
									p_o_object.getClass().getDeclaredField(s_mapping).set(p_o_object, o_returnObject);
								} catch (NoSuchFieldException o_exc) {
									throw new NoSuchFieldException("Property[" + s_mapping + "] is not accessible for object: " + p_o_object.getClass().getTypeName());
								}
							}
			    		}
					}
				} else { /* otherwise we have xs:elements with primitive types */
					/* flag if current element returned value */
					boolean b_returnedValue = false;
					
					/* get value if xml element is not empty */
					if (!( (e_xmlType == XMLType.Empty) || (e_xmlType == XMLType.EmptyWithAttributes) )) { 
						/* get value for xml element */
						if (b_returnedAttributes) {
							b_returnedValue = this.parseXMLElement(p_a_xmlTags, ++p_i_min, p_o_object, o_xsdElement, "(<" + o_xsdElement.getName() + "[^<>]*?>)([^<>]*?)(</" + o_xsdElement.getName() + ">)");
						} else {
							b_returnedValue = this.parseXMLElement(p_a_xmlTags, ++p_i_min, p_o_object, o_xsdElement, "(<" + o_xsdElement.getName() + ">)([^<>]*?)(</" + o_xsdElement.getName() + ">)");
						}
					}
					
															net.forestany.forestj.lib.Global.ilogFinest("\t\t\t\tchild[" + o_xsdElement.getName() + "] returned value=" + b_returnedValue);
					
					/* check minOccurs attribute of xml element and if value is empty */
					if ( (o_xsdElement.getMinOccurs() > 0) && (!b_returnedValue) ) {
						if (p_o_xsdElement.getChoice()) {
							p_i_min--;
							continue;
						} else {
							throw new NullPointerException("Missing element value for xs:element[" + p_o_object.getClass().getTypeName() + "." + o_xsdElement.getName() + "]");
						}
					}
					
					/* check choice counter */
					if (p_o_xsdElement.getChoice()) {
						if ( (++i_choiceCnt > p_o_xsdElement.getMaxOccurs()) && (b_returnedValue) ) {
							throw new IllegalArgumentException(p_o_xsdElement.getName() + " has to many objects(" + i_choiceCnt + ") in xs:choice " + p_o_object.getClass().getTypeName() + "." + o_xsdElement.getName() + ", maximum = " + p_o_xsdElement.getMaxOccurs());
						}
					}
					
					/* check if child xml element has attributes, because of possible simpleContent */
					if (o_xsdElement.getAttributes().size() > 0) {
						boolean b_required = false;
						
						/* check if one attribute of the xml element is required */
						for (XSDAttribute o_xsdAttribute : p_o_xsdElement.getAttributes()) {
							if (o_xsdAttribute.getRequired()) {
								b_required = true;
							}
						}
						
						/* check if current xml element has attributes */
						if ( ( (e_xmlType != XMLType.BeginWithAttributes) && (e_xmlType != XMLType.ElementWithAttributes) && (e_xmlType != XMLType.EmptyWithAttributes) ) && (b_required) ) {
							throw new IllegalArgumentException(o_xsdElement.getName() + " has no attributes");
						}
						
						/* retrieve attributes */
						b_returnedAttributes = this.parseXMLAttributes(p_a_xmlTags, p_i_min, p_o_object, o_xsdElement);
					}
				}
			}
			
			/* check choice counter for minimum objects */
			if (p_o_xsdElement.getChoice()) {
				if (i_choiceCnt < p_o_xsdElement.getMinOccurs()) {
					throw new IllegalArgumentException(p_o_xsdElement.getName() + " has to few objects(" + i_choiceCnt + ") in xs:choice, minimum = " + p_o_xsdElement.getMinOccurs());
				}
			}
		}
		
												net.forestany.forestj.lib.Global.ilogFiner("\treturn object with type[" + ((p_o_object == null) ? "null" : p_o_object.getClass().getTypeName()) + "] for xsd-element[" + p_o_xsdElement.getName() + "]");
		return p_o_object;
	}
	
	/**
	 * Parse xml element value into destination java object field via field access or property method access
	 * 
	 * @param p_a_xmlTags					xml lines
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_o_object					destination java object to decode xml information 
	 * @param p_o_xsdElement				current xml schema element
	 * @param p_s_regexPattern				regex pattern where xml line must match
	 * @return								true - xml element has a value, false - could not parse value for xml element
	 * @throws IllegalArgumentException		element with value does not match structure or restrictions
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InstantiationException		could not create new object instance returning at the end of the method
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	private boolean parseXMLElement(java.util.List<String> p_a_xmlTags, int p_i_min, Object p_o_object, XSDElement p_o_xsdElement, String p_s_regexPattern) throws IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException, InstantiationException, ClassNotFoundException {
		/* return value */
		boolean b_hasValue = false;
		
		/* get value of xml element */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile(p_s_regexPattern);
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xmlTags.get(p_i_min));
		
		if (o_matcher.find()) {
			String s_elementValue = o_matcher.group(2);
			
			/* check if element has value */
			if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_elementValue)) {
				b_hasValue = true;
			}
			
			/* check if xs:element has any restrictions */
			if (p_o_xsdElement.getRestriction()) {
				boolean b_enumerationFound = false;
				boolean b_enumerationReturnValue = false;
				
				for (XSDRestriction o_xsdRestriction : p_o_xsdElement.getRestrictions()) {
					if (o_xsdRestriction.getName().toLowerCase().contentEquals("enumeration")) {
						b_enumerationFound = true;
					}
					
					b_enumerationReturnValue = this.checkRestriction(s_elementValue, o_xsdRestriction, p_o_xsdElement.getType());
					
					if ( (b_enumerationFound) && (b_enumerationReturnValue) ) {
						break;
					}
				}
				
				if ( (b_enumerationFound) && (!b_enumerationReturnValue) ) {
					throw new IllegalArgumentException("Element[" + p_o_xsdElement.getName() + "] with value[" + s_elementValue + "] does not match enumaration restrictions defined in xsd-schema for this xs:element");
				}
			}
			
			/* get mapping type */
    		String s_mapping = p_o_xsdElement.getMapping();
			
    		/* remove enclosure of mapping type if it exists */
			if (s_mapping.contains(":")) {
				s_mapping = s_mapping.substring(0, s_mapping.indexOf(":"));
			} else {
				/* remove package prefix */
				if (s_mapping.contains(".")) {
					s_mapping = s_mapping.substring(s_mapping.lastIndexOf(".") + 1, s_mapping.length());
				}
				
				/* remove internal class prefix */
				if (s_mapping.contains("$")) {
					s_mapping = s_mapping.substring(s_mapping.lastIndexOf("$") + 1, s_mapping.length());
				}
			}
    		
			/* check if we use property methods with invoke to set object data values */
			if (this.b_usePropertyMethods) {
				/* invoke set of element value of current object */
				java.lang.reflect.Method o_method = null;
				boolean b_methodFound = false;
				
				/* look for set-property-method */
				for (java.lang.reflect.Method o_methodSearch : p_o_object.getClass().getDeclaredMethods()) {
					if (o_methodSearch.getName().contentEquals("set" + s_mapping)) {
						o_method = o_methodSearch;
						b_methodFound = true;
					}
				}
					
				/* check if we found set-property-method */
				if (!b_methodFound) {
					throw new NoSuchMethodException("Method[" + "set" + s_mapping + "] does not exist for object: " + p_o_object.getClass().getTypeName());
				}
				
				/* use set-property-method and set element value */
				try {
					o_method.invoke(p_o_object, this.castObjectFromString(s_elementValue, p_o_xsdElement.getType(), p_o_object.getClass().getDeclaredField(s_mapping).getType().getTypeName()));
				} catch (Exception o_exc) {
					throw new IllegalAccessException(this.printIndentation() + o_exc.getMessage() + " - with type[" + s_mapping + "], mapping[" + p_o_xsdElement.getMapping() + "] and field type[" + p_o_object.getClass().getDeclaredField(s_mapping).getType().getTypeName() + "] not possible");
				}
			} else {
				/* call field directly to set object data values */
				try {
					p_o_object.getClass().getDeclaredField(s_mapping).set(p_o_object, this.castObjectFromString(s_elementValue, p_o_xsdElement.getType(), p_o_object.getClass().getDeclaredField(s_mapping).getType().getTypeName()));
				} catch (Exception o_exc) {
					throw new IllegalAccessException("Property[" + p_o_xsdElement.getName() + "] is not accessible for object: " + p_o_object.getClass().getTypeName() + " - with type[" + p_o_xsdElement.getType() + "], mapping[" + p_o_xsdElement.getMapping() + "] and field type[" + p_o_object.getClass().getDeclaredField(s_mapping).getType().getTypeName() + "] not possible");
				}
			}
		}
		
		return b_hasValue;
	}
	
	/**
	 * Parse xml attribute value into destination java object field via field access or property method access
	 * 
	 * @param p_a_xmlTags					xml lines
	 * @param p_i_min						pointer where to start line iteration
	 * @param p_o_object					destination java object to decode xml information 
	 * @param p_o_xsdElement				current xml schema element
	 * @return								true - xml attribute has a value, false - could not parse value for xml attribute
	 * @throws IllegalArgumentException		element with value does not match structure or restrictions
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 * @throws InstantiationException		could not create new object instance returning at the end of the method
	 * @throws ClassNotFoundException		could not retrieve class by string class name
	 */
	private boolean parseXMLAttributes(java.util.List<String> p_a_xmlTags, int p_i_min, Object p_o_object, XSDElement p_o_xsdElement) throws IllegalArgumentException, NoSuchFieldException, NoSuchMethodException, java.lang.reflect.InvocationTargetException, IllegalAccessException, java.text.ParseException, java.time.DateTimeException, InstantiationException, ClassNotFoundException {
		/* return value */
		boolean b_hasAttributes = false;
		
		/* get attributes of xml element */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("[^<>\\s/=]*?=\"[^<>/\"]*?\"");
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_xmlTags.get(p_i_min));
    	
		while (o_matcher.find()) {
			String s_attribute = o_matcher.group();
			String s_attributeName = s_attribute.substring(0, s_attribute.indexOf("="));
			boolean b_found = false;
			
			for (XSDAttribute o_xsdAttribute : p_o_xsdElement.getAttributes()) {
				if (s_attributeName.contentEquals(o_xsdAttribute.getName())) {
					String s_attributeValue = s_attribute.substring(s_attribute.indexOf("=") + 1);
    				s_attributeValue = s_attributeValue.substring(1, s_attributeValue.length() - 1);
    														net.forestany.forestj.lib.Global.ilogFiner("\t\t\t\tfound Attribute [" + s_attributeName + "] with value=" + s_attributeValue);
    				
					/* if attribute is required but value is empty, throw exception */
					if ( (o_xsdAttribute.getRequired()) && (net.forestany.forestj.lib.Helper.isStringEmpty(s_attributeValue)) ) {
						throw new IllegalArgumentException("Missing attribute value for xs:attribute[" + p_o_object.getClass().getSimpleName() + "." + o_xsdAttribute.getName() + "]");
					}
					
					/* check if xs:element has any restrictions */
					if (o_xsdAttribute.getRestriction()) {
						boolean b_enumerationFound = false;
						boolean b_enumerationReturnValue = false;
						
						for (XSDRestriction o_xsdRestriction : o_xsdAttribute.getRestrictions()) {
							if (o_xsdRestriction.getName().toLowerCase().contentEquals("enumeration")) {
								b_enumerationFound = true;
							}
							
							b_enumerationReturnValue = this.checkRestriction(s_attributeValue, o_xsdRestriction, o_xsdAttribute.getType());
							
							if ( (b_enumerationFound) && (b_enumerationReturnValue) ) {
								break;
							}
						}
						
						if ( (b_enumerationFound) && (!b_enumerationReturnValue) ) {
							throw new IllegalArgumentException("Attribute[" + o_xsdAttribute.getName() + "] with value[" + s_attributeValue + "] does not match enumaration restrictions defined in xsd-schema for this xs:attribute");
						}
					}
    				
					/* get mapping type of attribute */
		    		String s_mapping = o_xsdAttribute.getMapping();
					
		    		/* remove enclosure of mapping type if it exists */
					if (s_mapping.contains(":")) {
						s_mapping = s_mapping.substring(0, s_mapping.indexOf(":"));
					} else {
						/* remove package prefix */
						if (s_mapping.contains(".")) {
							s_mapping = s_mapping.substring(s_mapping.lastIndexOf(".") + 1, s_mapping.length());
						}
						
						/* remove internal class prefix */
						if (s_mapping.contains("$")) {
							s_mapping = s_mapping.substring(s_mapping.lastIndexOf("$") + 1, s_mapping.length());
						}
					}
					
    				/* check if we use property methods with invoke to set object data values */
    				if (this.b_usePropertyMethods) {
	    				/* invoke set of attribute value of current object */
	    				java.lang.reflect.Method o_method = null;
	    				boolean b_methodFound = false;
	    				
	    				/* look for set-property-method */
						for (java.lang.reflect.Method o_methodSearch : p_o_object.getClass().getDeclaredMethods()) {
		    				if (o_methodSearch.getName().contentEquals("set" + s_mapping)) {
		    					o_method = o_methodSearch;
		    					b_methodFound = true;
		    				}
						}
	    					
						/* check if we found set-property-method */
	    				if (!b_methodFound) {
	    					throw new NoSuchMethodException("Method[" + "set" + s_mapping + "] does not exist for object: " + p_o_object.getClass().getTypeName());
	    				}
	    				
	    				/* use set-property-method and set attribute value */
	    				try {
	    					o_method.invoke(p_o_object, this.castObjectFromString(s_attributeValue, o_xsdAttribute.getType(), p_o_object.getClass().getDeclaredField(s_mapping).getType().getTypeName()));
	    				} catch (Exception o_exc) {
	    					throw new IllegalAccessException(this.printIndentation() + o_exc.getMessage() + " - with type[" + o_xsdAttribute.getType() + "], mapping[" + o_xsdAttribute.getMapping() + "] and field type[" + p_o_object.getClass().getDeclaredField(s_mapping).getType().getTypeName() + "] not possible");
	    				}
					} else {
						/* call field directly to set object data values */
						try {
							p_o_object.getClass().getDeclaredField(s_mapping).set(p_o_object, this.castObjectFromString(s_attributeValue, o_xsdAttribute.getType(), p_o_object.getClass().getDeclaredField(s_mapping).getType().getTypeName()));
						} catch (Exception o_exc) {
							throw new IllegalAccessException("Property[" + s_mapping + "] is not accessible for object: " + p_o_object.getClass().getTypeName() + " - with type[" + o_xsdAttribute.getType() + "], mapping[" + o_xsdAttribute.getMapping() + "] and field type[" + p_o_object.getClass().getDeclaredField(s_mapping).getType().getTypeName() + "] not possible");
						}
					}
	    				
    				b_found = true;
    				b_hasAttributes = true;
				}
			}
			
			if (!b_found) {
				throw new IllegalArgumentException("Xml attribute[" + s_attributeName + "] not expected and not availalbe in xsd-schema at(" + (p_i_min + 2) + ".-element) \"" + p_a_xmlTags.get(p_i_min) + "\".");
			}
		}
		
		return b_hasAttributes;
	}
	
	/**
	 * Analyze xml element value and get a unique value type from it
	 * 
	 * @param p_s_xmlTag					xml element value as string
	 * @return								unique xml enumeration type
	 */
	private XMLType getXMLType(String p_s_xmlTag) {
		java.util.regex.Pattern o_regex;
    	o_regex = java.util.regex.Pattern.compile("<[^<>\\s]*?>[^<>]*?</[^<>]*?>");
		
		if (o_regex.matcher(p_s_xmlTag).find()) {
			return XMLType.ElementNoAttributes;
		}
		
		o_regex = java.util.regex.Pattern.compile("<[^<>]*?>[^<>]*?</[^<>]*?>");
		
		if (o_regex.matcher(p_s_xmlTag).find()) {
			return XMLType.ElementWithAttributes;
		}
		
		o_regex = java.util.regex.Pattern.compile("<[^<>/\\s]*?>");
    	
		if (o_regex.matcher(p_s_xmlTag).find()) {
			return XMLType.BeginNoAttributes;
		}
		
		o_regex = java.util.regex.Pattern.compile("<[^<>/]*?>");
		
		if (o_regex.matcher(p_s_xmlTag).find()) {
			return XMLType.BeginWithAttributes;
		}
		
		o_regex = java.util.regex.Pattern.compile("</[^<>\\s]*?>");
		
		if (o_regex.matcher(p_s_xmlTag).find()) {
			return XMLType.Close;
		}
		
		o_regex = java.util.regex.Pattern.compile("<[^<>\\s]*?/>");
		
		if (o_regex.matcher(p_s_xmlTag).find()) {
			return XMLType.Empty;
		}
		
		o_regex = java.util.regex.Pattern.compile("<[^<>]*?/>");
		
		if (o_regex.matcher(p_s_xmlTag).find()) {
			return XMLType.EmptyWithAttributes;
		}
		
		return null;
	}

	/**
	 * Convert a string value from a xml element to an object to decode it into an object
	 * 
	 * @param p_s_value						string value of xml element from file
	 * @param p_s_type						type of destination object field, conform to xsd schema
	 * @param p_s_fieldType					field type of destination object field, java field type
	 * @return								casted object value from string
	 * @throws IllegalArgumentException		invalid type parameter
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private Object castObjectFromString(String p_s_value, String p_s_type, String p_s_fieldType) throws IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
		Object o_foo = null;
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			p_s_type = p_s_type.toLowerCase();
			p_s_fieldType = p_s_fieldType.toLowerCase();
			
			java.util.List<String> a_stringTypes = java.util.Arrays.asList("string", "duration", "hexbinary", "base64binary", "anyuri", "normalizedstring", "token", "language", "name", "ncname", "nmtoken", "id", "idref", "entity");
			java.util.List<String> a_integerTypes = java.util.Arrays.asList("integer", "int", "positiveinteger", "nonpositiveinteger", "negativeinteger", "nonnegativeinteger", "byte", "unsignedint", "unsignedbyte");
			
			if (p_s_type.contentEquals("boolean")) {
				o_foo = Boolean.valueOf(p_s_value);
			} else if (a_stringTypes.contains(p_s_type)) {
				if ( (p_s_value.contentEquals("&#x200B;")) && (this.b_printEmptyString) ) {
					p_s_value = "";
				}
				
				o_foo = p_s_value;
			} else if (p_s_type.contentEquals("datetime")) {
				if (!net.forestany.forestj.lib.Helper.isDateTime(p_s_value)) {
					throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'datetime' and field type '" + p_s_fieldType + "'");
				}
				
				if (this.b_useISO8601UTC) {
					if (p_s_fieldType.contentEquals("java.util.date")) {
						o_foo = net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate(p_s_value);
					} else if (p_s_fieldType.contentEquals("java.time.localdatetime")) {	
						o_foo = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value);
					} else {
						throw new IllegalArgumentException("Illegal field type '" + p_s_fieldType + "' for 'datetime'");
					}
				} else {
					if (p_s_fieldType.contentEquals("java.util.date")) {
						o_foo = new java.text.SimpleDateFormat(this.s_dateTimeFormat).parse(p_s_value);
					} else if (p_s_fieldType.contentEquals("java.time.localdatetime")) {	
						o_foo = net.forestany.forestj.lib.Helper.fromDateTimeString(p_s_value);
					} else {
						throw new IllegalArgumentException("Illegal field type '" + p_s_fieldType + "' for 'datetime'");
					}
				}
			} else if (p_s_type.contentEquals("date")) {
				if (this.b_useISO8601UTC) {
					if (!net.forestany.forestj.lib.Helper.isDateTime(p_s_value)) {
						throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'date' and field type '" + p_s_fieldType + "'");
					}
					
					if (p_s_fieldType.contentEquals("java.util.date")) {
						o_foo = net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate(p_s_value);
					} else if (p_s_fieldType.contentEquals("java.time.localdate")) {	
						o_foo = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value).toLocalDate();
					} else {
						throw new IllegalArgumentException("Illegal field type '" + p_s_fieldType + "' for 'date'");
					}
				} else {
					if (!net.forestany.forestj.lib.Helper.isDate(p_s_value)) {
						throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'date' and field type '" + p_s_fieldType + "'");
					}
					
					if (p_s_fieldType.contentEquals("java.util.date")) {
						o_foo = new java.text.SimpleDateFormat(this.s_dateFormat).parse(p_s_value);
					} else if (p_s_fieldType.contentEquals("java.time.localdate")) {	
						o_foo = net.forestany.forestj.lib.Helper.fromDateString(p_s_value);
					} else if (p_s_fieldType.contentEquals("java.time.localdatetime")) {	
						o_foo = java.time.LocalDateTime.of( net.forestany.forestj.lib.Helper.fromDateString(p_s_value), java.time.LocalTime.of(0, 0) );
					} else {
						throw new IllegalArgumentException("Illegal field type '" + p_s_fieldType + "' for 'date'");
					}
				}
			} else if (p_s_type.contentEquals("time")) {
				if (this.b_useISO8601UTC) {
					if (!net.forestany.forestj.lib.Helper.isDateTime(p_s_value)) {
						throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'time' and field type '" + p_s_fieldType + "'");
					}
					
					if (p_s_fieldType.contentEquals("java.util.date")) {
						o_foo = net.forestany.forestj.lib.Helper.fromISO8601UTCToUtilDate(p_s_value);
					} else if (p_s_fieldType.contentEquals("java.time.localtime")) {	
						o_foo = net.forestany.forestj.lib.Helper.fromISO8601UTC(p_s_value).toLocalTime();
					} else {
						throw new IllegalArgumentException("Illegal field type '" + p_s_fieldType + "' for 'time'");
					}
				} else {
					if (!net.forestany.forestj.lib.Helper.isTime(p_s_value)) {
						throw new IllegalArgumentException("Illegal value '" + p_s_value + "' for 'time' and field type '" + p_s_fieldType + "'");
					}
					
					if (p_s_fieldType.contentEquals("java.util.date")) {
						o_foo = new java.text.SimpleDateFormat(this.s_timeFormat).parse(p_s_value);
					} else if (p_s_fieldType.contentEquals("java.time.localtime")) {	
						o_foo = net.forestany.forestj.lib.Helper.fromTimeString(p_s_value);
					} else if (p_s_fieldType.contentEquals("java.time.localdatetime")) {	
						o_foo = java.time.LocalDateTime.of( java.time.LocalDate.of(1900, 1, 1), net.forestany.forestj.lib.Helper.fromTimeString(p_s_value) );
					} else {
						throw new IllegalArgumentException("Illegal field type '" + p_s_fieldType + "' for 'time'");
					}
				}
			} else if (p_s_type.contentEquals("decimal")) {
				java.text.DecimalFormat o_decimalFormat = new java.text.DecimalFormat();
				o_decimalFormat.setParseBigDecimal(true);
				o_foo = o_decimalFormat.parseObject(p_s_value);
				o_foo = new java.math.BigDecimal(p_s_value);
			} else if (p_s_type.contentEquals("double")) {
				o_foo = Double.parseDouble(p_s_value);
			} else if (p_s_type.contentEquals("float")) {
				o_foo = Float.parseFloat(p_s_value);
			} else if (a_integerTypes.contains(p_s_type)) {
				o_foo = Integer.parseInt(p_s_value);
			} else if (p_s_type.contentEquals("long") || p_s_type.contentEquals("unsignedlong")) {
				o_foo = Long.parseLong(p_s_value);
			} else if (p_s_type.contentEquals("short") || p_s_type.contentEquals("unsignedshort")) {
				o_foo = Short.parseShort(p_s_value);
			} else {
				throw new IllegalArgumentException("Invalid type[" + p_s_type + "] for " + p_s_value);
			}
		}
		
		return o_foo;
	}

	/**
	 * Method to set primitive array property field of an object with simple object value, so no cast will be done
	 * 
	 * @param p_s_mapping					mapping class and type hint to cast value to object's primitive array field
	 * @param p_s_fieldType					field type of destination object field, java field type
	 * @param p_o_object					object parameter where xml data will be decoded and cast into object fields
	 * @param p_o_objectValue				object value of xml element from file line
	 * @throws NoSuchFieldException			could not retrieve field type by field name
	 * @throws NoSuchMethodException		could not retrieve method by method name
	 * @throws InvocationTargetException 	could not invoke method from object
	 * @throws IllegalAccessException		could not invoke method, access violation
	 * @throws IllegalArgumentException		invalid type parameter
	 * @throws java.text.ParseException		exception parsing BigDecimal or java.util.Date value
	 * @throws java.time.DateTimeException	exception parsing LocalDateTime, LocalDate or LocalTime value
	 */
	private void setPrimitiveArrayProperty(String p_s_mapping, String p_s_fieldType, Object p_o_object, Object p_o_objectValue) throws NoSuchMethodException, NoSuchFieldException, java.lang.reflect.InvocationTargetException, IllegalAccessException, IllegalArgumentException, java.text.ParseException, java.time.DateTimeException {
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
						o_bar[i] = (boolean)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("byte")) || (s_primitiveArrayType.contentEquals("java.lang.Byte")) ) {
					byte[] o_bar = new byte[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (byte)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("char")) || (s_primitiveArrayType.contentEquals("java.lang.Character")) ) {
					char[] o_bar = new char[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (char)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("float")) || (s_primitiveArrayType.contentEquals("java.lang.Float")) ) {
					float[] o_bar = new float[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (float)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("double")) || (s_primitiveArrayType.contentEquals("java.lang.Double")) ) {
					double[] o_bar = new double[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (double)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("short")) || (s_primitiveArrayType.contentEquals("java.lang.Short")) ) {
					short[] o_bar = new short[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (short)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("int")) || (s_primitiveArrayType.contentEquals("java.lang.Integer")) ) {
					int[] o_bar = new int[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (int)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if ( (s_primitiveArrayType.contentEquals("long")) || (s_primitiveArrayType.contentEquals("java.lang.Long")) ) {
					long[] o_bar = new long[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (long)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if (s_primitiveArrayType.contentEquals("java.util.Date")) {
					java.util.Date[] o_bar = new java.util.Date[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (java.util.Date)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if (s_primitiveArrayType.contentEquals("java.time.LocalTime")) {
					java.time.LocalTime[] o_bar = new java.time.LocalTime[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (java.time.LocalTime)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if (s_primitiveArrayType.contentEquals("java.time.LocalDate")) {
					java.time.LocalDate[] o_bar = new java.time.LocalDate[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (java.time.LocalDate)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if (s_primitiveArrayType.contentEquals("java.time.LocalDateTime")) {
					java.time.LocalDateTime[] o_bar = new java.time.LocalDateTime[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (java.time.LocalDateTime)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
					}
					
					o_method.invoke(p_o_object, new Object[] {o_bar});
				} else if (s_primitiveArrayType.contentEquals("java.math.BigDecimal")) {
					java.math.BigDecimal[] o_bar = new java.math.BigDecimal[o_foo.size()];
					
					for (int i = 0; i < o_foo.size(); i++) {
						o_bar[i] = (java.math.BigDecimal)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
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
							o_bar[i] = (boolean)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("byte")) || (s_primitiveArrayType.contentEquals("java.lang.Byte")) ) {
						byte[] o_bar = new byte[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (byte)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("char")) || (s_primitiveArrayType.contentEquals("java.lang.Character")) ) {
						char[] o_bar = new char[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (char)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("float")) || (s_primitiveArrayType.contentEquals("java.lang.Float")) ) {
						float[] o_bar = new float[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (float)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("double")) || (s_primitiveArrayType.contentEquals("java.lang.Double")) ) {
						double[] o_bar = new double[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (double)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("short")) || (s_primitiveArrayType.contentEquals("java.lang.Short")) ) {
						short[] o_bar = new short[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (short)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("int")) || (s_primitiveArrayType.contentEquals("java.lang.Integer")) ) {
						int[] o_bar = new int[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (int)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if ( (s_primitiveArrayType.contentEquals("long")) || (s_primitiveArrayType.contentEquals("java.lang.Long")) ) {
						long[] o_bar = new long[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (long)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if (s_primitiveArrayType.contentEquals("java.util.Date")) {
						java.util.Date[] o_bar = new java.util.Date[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (java.util.Date)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if (s_primitiveArrayType.contentEquals("java.time.LocalTime")) {
						java.time.LocalTime[] o_bar = new java.time.LocalTime[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (java.time.LocalTime)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if (s_primitiveArrayType.contentEquals("java.time.LocalDate")) {
						java.time.LocalDate[] o_bar = new java.time.LocalDate[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (java.time.LocalDate)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if (s_primitiveArrayType.contentEquals("java.time.LocalDateTime")) {
						java.time.LocalDateTime[] o_bar = new java.time.LocalDateTime[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (java.time.LocalDateTime)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
						}
						
						p_o_object.getClass().getDeclaredField(p_s_mapping).set(p_o_object, o_bar);
					} else if (s_primitiveArrayType.contentEquals("java.math.BigDecimal")) {
						java.math.BigDecimal[] o_bar = new java.math.BigDecimal[o_foo.size()];
						
						for (int i = 0; i < o_foo.size(); i++) {
							o_bar[i] = (java.math.BigDecimal)this.castObjectFromString( (o_foo.get(i) == null ? null : o_foo.get(i).toString()), p_s_fieldType, s_primitiveArrayType );
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
		
	/* Internal Classes */
	
	/**
	 * Enumeration of valid xsd types
	 */
	public enum XSDType {
		/**
		 * Element xsd value type
		 */
		Element,
		/**
		 * ComplexType xsd value type
		 */
		ComplexType,
		/**
		 * Sequence xsd value type
		 */
		Sequence,
		/**
		 * Attribute xsd value type
		 */
		Attribute,
		/**
		 * Choice xsd value type
		 */
		Choice,
		/**
		 * SimpleType xsd value type
		 */
		SimpleType,
		/**
		 * SimpleContent xsd value type
		 */
		SimpleContent,
		/**
		 * Restriction xsd value type
		 */
		Restriction,
		/**
		 * Extension xsd value type
		 */
		Extension,
		/**
		 * RestrictionItem xsd value type
		 */
		RestrictionItem
	}
	
	/**
	 * Enumeration of valid xml types
	 */
	public enum XMLType {
		/**
		 * ElementNoAttributes xml value type
		 */
		ElementNoAttributes,
		/**
		 * ElementWithAttributes xml value type
		 */
		ElementWithAttributes,
		/**
		 * BeginNoAttributes xml value type
		 */
		BeginNoAttributes,
		/**
		 * BeginWithAttributes xml value type
		 */
		BeginWithAttributes,
		/**
		 * Close xml value type
		 */
		Close,
		/**
		 * Empty xml value type
		 */
		Empty,
		/**
		 * EmptyWithAttributes xml value type
		 */
		EmptyWithAttributes
	}
	
	/**
	 * Encapsulation of a schema's xsd element
	 */
	public class XSDElement {

		/* Fields */
		
		private String s_name;
		private String s_type;
		private String s_mapping;
		private String s_reference;
		private int i_minOccurs = 1;
		private int i_maxOccurs = 1;
		private boolean b_choice = false;
		private boolean b_restriction = false;
		private boolean b_isArray = false;
		private int i_sequenceMinOccurs = 1;
		private int i_sequenceMaxOccurs = 1;
		private boolean b_simpleType = false;
		private boolean b_simpleContent = false;
		private java.util.List<XSDAttribute> a_attributes = new java.util.ArrayList<XSDAttribute>();
		private java.util.List<XSDElement> a_children = new java.util.ArrayList<XSDElement>();
		private java.util.List<XSDRestriction> a_restrictions = new java.util.ArrayList<XSDRestriction>();
		
		/* Properties */
		
		/**
		 * get name
		 * 
		 * @return String
		 */
		public String getName() {
			return this.s_name;
		}
		
		/**
		 * set name
		 * 
		 * @param p_s_value name value
		 */
		public void setName(String p_s_value) {
			this.s_name = p_s_value;
		}
		
		/**
		 * get type
		 * 
		 * @return String
		 */
		public String getType() {
			return this.s_type;
		}
		
		/**
		 * set type
		 * 
		 * @param p_s_value type value
		 */
		public void setType(String p_s_value) {
			this.s_type = p_s_value;
		}
		
		/**
		 * get mapping
		 * 
		 * @return String
		 */
		public String getMapping() {
			return this.s_mapping;
		}
		
		/**
		 * set mapping
		 * 
		 * @param p_s_value mapping value
		 */
		public void setMapping(String p_s_value) {
			this.s_mapping = p_s_value;
		}
		
		/**
		 * get reference
		 * 
		 * @return String
		 */
		public String getReference() {
			return this.s_reference;
		}
		
		/**
		 * set reference
		 * 
		 * @param p_s_value reference value
		 */
		public void setReference(String p_s_value) {
			this.s_reference = p_s_value;
		}
		
		/**
		 * get min occurs
		 * 
		 * @return int
		 */
		public int getMinOccurs() {
			return this.i_minOccurs;
		}
		
		/**
		 * set min occurs
		 * 
		 * @param p_i_value min occurs value
		 */
		public void setMinOccurs(int p_i_value) {
			this.i_minOccurs = p_i_value;
		}
		
		/**
		 * get max occurs
		 * 
		 * @return int
		 */
		public int getMaxOccurs() {
			return this.i_maxOccurs;
		}
		
		/**
		 * set max occurs
		 * 
		 * @param p_i_value min occurs value
		 */
		public void setMaxOccurs(int p_i_value) {
			this.i_maxOccurs = p_i_value;
		}
		
		/**
		 * get choice
		 * 
		 * @return boolean
		 */
		public boolean getChoice() {
			return this.b_choice;
		}
		
		/**
		 * set choice
		 * 
		 * @param p_b_value choice value
		 */
		public void setChoice(boolean p_b_value) {
			this.b_choice = p_b_value;
		}
		
		/**
		 * get restriction
		 * 
		 * @return boolean
		 */
		public boolean getRestriction() {
			return this.b_restriction;
		}
		
		/**
		 * set restriction
		 * 
		 * @param p_b_value restriction value
		 */
		public void setRestriction(boolean p_b_value) {
			this.b_restriction = p_b_value;
		}
		
		/**
		 * get is array
		 * 
		 * @return boolean
		 */
		public boolean getIsArray() {
			return this.b_isArray;
		}
		
		/**
		 * set is array
		 * 
		 * @param p_b_value is array value
		 */
		public void setIsArray(boolean p_b_value) {
			this.b_isArray = p_b_value;
		}
		
		/**
		 * get sequence min occurs
		 * 
		 * @return int
		 */
		public int getSequenceMinOccurs() {
			return this.i_sequenceMinOccurs;
		}
		
		/**
		 * set sequence min occurs
		 * 
		 * @param p_i_value sequence min occurs value
		 */
		public void setSequenceMinOccurs(int p_i_value) {
			this.i_sequenceMinOccurs = p_i_value;
		}
		
		/**
		 * get sequence max occurs
		 * 
		 * @return int
		 */
		public int getSequenceMaxOccurs() {
			return this.i_sequenceMaxOccurs;
		}
		
		/**
		 * set sequence max occurs
		 * 
		 * @param p_i_value sequence max occurs value
		 */
		public void setSequenceMaxOccurs(int p_i_value) {
			this.i_sequenceMaxOccurs = p_i_value;
		}
		
		/**
		 * get simple type flag
		 * 
		 * @return boolean
		 */
		public boolean getSimpleType() {
			return this.b_simpleType;
		}
		
		/**
		 * set simple type flag
		 * 
		 * @param p_b_value simple type flag
		 */
		public void setSimpleType(boolean p_b_value) {
			this.b_simpleType = p_b_value;
		}
		
		/**
		 * get simple content flag
		 * 
		 * @return boolean
		 */
		public boolean getSimpleContent() {
			return this.b_simpleContent;
		}
		
		/**
		 * set simple content flag
		 * 
		 * @param p_b_value simple content flag
		 */
		public void setSimpleContent(boolean p_b_value) {
			this.b_simpleContent = p_b_value;
		}
		
		/**
		 * get attributes of XMLElement
		 * 
		 * @return java.util.List&lt;XSDAttribute&gt;
		 */
		public java.util.List<XSDAttribute> getAttributes() {
			return this.a_attributes;
		}
		
		/**
		 * get child elements of XMLElement
		 * 
		 * @return java.util.List&lt;XSDElement&gt;
		 */
		public java.util.List<XSDElement> getChildren() {
			return this.a_children;
		}
		
		/**
		 * get restrictions of XMLElement
		 * 
		 * @return java.util.List&lt;XSDRestriction&gt;
		 */
		public java.util.List<XSDRestriction> getRestrictions() {
			return this.a_restrictions;
		}
		
		/* Methods */
		
		/**
		 * XSDElement constructor, no choice flag [= false], no max. occur value [= 1], no min. occur value [= 1], no mapping value [= null], no type value [= null], no name value [= null]
		 */
		public XSDElement() {
			this("", "", "", 1, 1, false);
		}
		
		/**
		 * XSDElement constructor, no choice flag [= false], no max. occur value [= 1], no min. occur value [= 1], no mapping value [= null], no type value [= null]
		 * 
		 * @param p_s_name			name of xsd element
		 */
		public XSDElement(String p_s_name) {
			this(p_s_name, "", "", 1, 1, false);
		}
		
		/**
		 * XSDElement constructor, no choice flag [= false], no max. occur value [= 1], no min. occur value [= 1], no mapping value [= null]
		 * 
		 * @param p_s_name			name of xsd element
		 * @param p_s_type			type of xsd element
		 */
		public XSDElement(String p_s_name, String p_s_type) {
			this(p_s_name, p_s_type, "", 1, 1, false);
		}
		
		/**
		 * XSDElement constructor, no choice flag [= false], no max. occur value [= 1], no min. occur value [= 1]
		 * 
		 * @param p_s_name			name of xsd element
		 * @param p_s_type			type of xsd element
		 * @param p_s_mapping		mapping of xsd element
		 */
		public XSDElement(String p_s_name, String p_s_type, String p_s_mapping) {
			this(p_s_name, p_s_type, p_s_mapping, 1, 1, false);
		}
		
		/**
		 * XSDElement constructor, no choice flag [= false], no max. occur value [= 1]
		 * 
		 * @param p_s_name			name of xsd element
		 * @param p_s_type			type of xsd element
		 * @param p_s_mapping		mapping of xsd element
		 * @param p_i_minOccurs		min. occur value of xsd element
		 */
		public XSDElement(String p_s_name, String p_s_type, String p_s_mapping, int p_i_minOccurs) {
			this(p_s_name, p_s_type, p_s_mapping, p_i_minOccurs, 1, false);
		}
		
		/**
		 * XSDElement constructor, no choice flag [= false]
		 * 
		 * @param p_s_name			name of xsd element
		 * @param p_s_type			type of xsd element
		 * @param p_s_mapping		mapping of xsd element
		 * @param p_i_minOccurs		min. occur value of xsd element
		 * @param p_i_maxOccurs		max. occur value of xsd element
		 */
		public XSDElement(String p_s_name, String p_s_type, String p_s_mapping, int p_i_minOccurs, int p_i_maxOccurs) {
			this(p_s_name, p_s_type, p_s_mapping, p_i_minOccurs, p_i_maxOccurs, false);
		}
		
		/**
		 * XSDElement constructor
		 * 
		 * @param p_s_name			name of xsd element
		 * @param p_s_type			type of xsd element
		 * @param p_s_mapping		mapping of xsd element
		 * @param p_i_minOccurs		min. occur value of xsd element
		 * @param p_i_maxOccurs		max. occur value of xsd element
		 * @param p_b_choice		choice flag of xsd element
		 */
		public XSDElement(String p_s_name, String p_s_type, String p_s_mapping, int p_i_minOccurs, int p_i_maxOccurs, boolean p_b_choice) {
			this(p_s_name, p_s_type, p_s_mapping, p_i_minOccurs, p_i_maxOccurs, false, false);
		}
		
		/**
		 * XSDElement constructor
		 * 
		 * @param p_s_name			name of xsd element
		 * @param p_s_type			type of xsd element
		 * @param p_s_mapping		mapping of xsd element
		 * @param p_i_minOccurs		min. occur value of xsd element
		 * @param p_i_maxOccurs		max. occur value of xsd element
		 * @param p_b_choice		choice flag of xsd element
		 * @param p_b_isArray		is array flag of xsd element
		 */
		public XSDElement(String p_s_name, String p_s_type, String p_s_mapping, int p_i_minOccurs, int p_i_maxOccurs, boolean p_b_choice, boolean p_b_isArray) {
			this.setName(p_s_name);
			this.setType(p_s_type);
			this.setMapping(p_s_mapping);
			this.setMinOccurs(p_i_minOccurs);
			this.setMaxOccurs(p_i_maxOccurs);
			this.setChoice(p_b_choice);
			this.setIsArray(p_b_isArray);
			this.setReference("");
		}
		
		/**
		 * Checks if this xs:element object is equal to another xs:element object
		 * 
		 * @param p_o_xsdElement			xs:element object for comparison
		 * @return							true - equal, false - not equal
		 */
		public boolean isEqual(XSDElement p_o_xsdElement) {
			if (this.getAttributes().size() != p_o_xsdElement.getAttributes().size()) {
				return false;
			}
			
			if (this.getChildren().size() != p_o_xsdElement.getChildren().size()) {
				return false;
			}
			
			if (this.getRestrictions().size() != p_o_xsdElement.getRestrictions().size()) {
				return false;
			}
			
			for (int i = 0; i < this.getAttributes().size(); i++) {
				if (!this.getAttributes().get(i).isEqual( p_o_xsdElement.getAttributes().get(i) )) {
					return false;
				}
			}
			
			for (int i = 0; i < this.getChildren().size(); i++) {
				if (!this.getChildren().get(i).isEqual( p_o_xsdElement.getChildren().get(i) )) {
					return false;
				}
			}
			
			for (int i = 0; i < this.getRestrictions().size(); i++) {
				if (!this.getRestrictions().get(i).isEqual( p_o_xsdElement.getRestrictions().get(i) )) {
					return false;
				}
			}
			
			if (
				(this.getName().contentEquals(p_o_xsdElement.getName())) &&
				(this.getType().contentEquals(p_o_xsdElement.getType())) &&
				(this.getMapping().contentEquals(p_o_xsdElement.getMapping())) &&
				(this.getReference().contentEquals(p_o_xsdElement.getReference())) &&
				(this.getChoice() == p_o_xsdElement.getChoice()) &&
				(this.getRestriction() == p_o_xsdElement.getRestriction()) &&
				(this.getIsArray() == p_o_xsdElement.getIsArray()) &&
				(this.getSimpleType() == p_o_xsdElement.getSimpleType()) &&
				(this.getSimpleContent() == p_o_xsdElement.getSimpleContent())
			) {
				return true;
			}
			
			return false;
		}
		
		/**
		 * Clones current xsd element object
		 */
		public XSDElement clone() {			
			XSDElement o_clone = new XSDElement();
			o_clone.setName(this.getName());
			o_clone.setType(this.getType());
			o_clone.setMapping(this.getMapping());
			o_clone.setReference(this.getReference());
			o_clone.setMinOccurs(this.getMinOccurs());
			o_clone.setMaxOccurs(this.getMaxOccurs());
			o_clone.setChoice(this.getChoice());
			o_clone.setRestriction(this.getRestriction());
			o_clone.setIsArray(this.getIsArray());
			o_clone.setSequenceMinOccurs(this.getSequenceMinOccurs());
			o_clone.setSequenceMaxOccurs(this.getSequenceMaxOccurs());
			o_clone.setSimpleType(this.getSimpleType());
			o_clone.setSimpleContent(this.getSimpleContent());
			
			for (XSDAttribute o_attribute : this.getAttributes()) {
				o_clone.getAttributes().add(o_attribute.clone());
			}
			
			for (XSDElement o_element : this.getChildren()) {
				o_clone.getChildren().add(o_element.clone());
			}
			
			for (XSDRestriction o_restriction : this.getRestrictions()) {
				o_clone.getRestrictions().add(o_restriction.clone());
			}
			
			return o_clone;
		}
		
		@Override
		public String toString() {
			String s_foo = "XSDElement: ";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				try {
					if (o_field.getName().startsWith("this$")) {
						continue;
					}
					
					if (o_field.get(this) instanceof java.util.List<?>) {
						java.util.List<?> a_objects = (java.util.List<?>)o_field.get(this);
						
						if (a_objects.size() > 0) {
							for (Object o_object : a_objects) {
								s_foo += o_object.toString();
							}
						}
						
						s_foo += "\n";
					} else {
						s_foo += o_field.getName() + " = " + o_field.get(this).toString() + "|";
					}
				} catch (Exception o_exc) {
					s_foo += o_field.getName() + " = ERR:AccessViolation|";
				}
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
					
			return s_foo;
		}
	}
	
	/**
	 * Encapsulation of a schema's xsd attribute
	 */
	public class XSDAttribute {
		
		/* Fields */
		
		private String s_name;
		private String s_type;
		private String s_mapping;
		private boolean b_required;
		private String s_default;
		private String s_fixed;
		private String s_reference;
		private boolean b_restriction = false;
		private boolean b_simpleType = false;
		private java.util.List<XSDRestriction> a_restrictions = new java.util.ArrayList<XSDRestriction>();
		
		/* Properties */
		
		/**
		 * get name
		 * 
		 * @return String
		 */
		public String getName() {
			return this.s_name;
		}
		
		/**
		 * set name
		 * 
		 * @param p_s_value name value
		 */
		public void setName(String p_s_value) {
			this.s_name = p_s_value;
		}
		
		/**
		 * get type
		 * 
		 * @return String
		 */
		public String getType() {
			return this.s_type;
		}
		
		/**
		 * set type
		 * 
		 * @param p_s_value type value
		 */
		public void setType(String p_s_value) {
			this.s_type = p_s_value;
		}
		
		/**
		 * get mapping
		 * 
		 * @return String
		 */
		public String getMapping() {
			return this.s_mapping;
		}
		
		/**
		 * set mapping
		 * 
		 * @param p_s_value mapping value
		 */
		public void setMapping(String p_s_value) {
			this.s_mapping = p_s_value;
		}
		
		/**
		 * get required
		 * 
		 * @return boolean
		 */
		public boolean getRequired() {
			return this.b_required;
		}
		
		/**
		 * set required
		 * 
		 * @param p_b_value required value
		 */
		public void setRequired(boolean p_b_value) {
			this.b_required = p_b_value;
		}
		
		/**
		 * get default
		 * 
		 * @return String
		 */
		public String getDefault() {
			return this.s_default;
		}
		
		/**
		 * set default
		 * 
		 * @param p_s_value default value
		 */
		public void setDefault(String p_s_value) {
			this.s_default = p_s_value;
		}
		
		/**
		 * get fixed
		 * 
		 * @return String
		 */
		public String getFixed() {
			return this.s_fixed;
		}
		
		/**
		 * set fixed
		 * 
		 * @param p_s_value fixed value
		 */
		public void setFixed(String p_s_value) {
			this.s_fixed = p_s_value;
		}
		
		/**
		 * get reference
		 * 
		 * @return String
		 */
		public String getReference() {
			return this.s_reference;
		}
		
		/**
		 * set reference
		 * 
		 * @param p_s_value reference value
		 */
		public void setReference(String p_s_value) {
			this.s_reference = p_s_value;
		}
		
		/**
		 * get restriction
		 * 
		 * @return boolean
		 */
		public boolean getRestriction() {
			return this.b_restriction;
		}
		
		/**
		 * set restriction
		 * 
		 * @param p_b_value restriction value
		 */
		public void setRestriction(boolean p_b_value) {
			this.b_restriction = p_b_value;
		}
		
		/**
		 * get simple type
		 * 
		 * @return boolean
		 */
		public boolean getSimpleType() {
			return this.b_simpleType;
		}
		
		/**
		 * set simple type
		 * 
		 * @param p_b_value simple type value
		 */
		public void setSimpleType(boolean p_b_value) {
			this.b_simpleType = p_b_value;
		}
		
		/**
		 * get restrictions of XSDAttribute
		 * 
		 * @return java.util.List&lt;XSDRestriction&gt;
		 */
		public java.util.List<XSDRestriction> getRestrictions() {
			return this.a_restrictions;
		}
			
		/* Methods */
		
		/**
		 * XSDAttribute constructor, no name value [= null], no type value [= null], no mapping value [= null], no required flag [= false], no default value [= null], no fixed value [= null]
		 */
		public XSDAttribute() {
			this("", "", "", false, "", "");
		}
		
		/**
		 * XSDAttribute constructor, no type value [= null], no mapping value [= null], no required flag [= false], no default value [= null], no fixed value [= null]
		 * 
		 * @param p_s_name			name of xsd element attribute
		 */
		public XSDAttribute(String p_s_name) {
			this(p_s_name, "", "", false, "", "");
		}
		
		/**
		 * XSDAttribute constructor, no mapping value [= null], no required flag [= false], no default value [= null], no fixed value [= null]
		 * 
		 * @param p_s_name			name of xsd element attribute
		 * @param p_s_type			type of xsd element attribute
		 */
		public XSDAttribute(String p_s_name, String p_s_type) {
			this(p_s_name, p_s_type, "", false, "", "");
		}
		
		/**
		 * XSDAttribute constructor, no required flag [= false], no default value [= null], no fixed value [= null]
		 * 
		 * @param p_s_name			name of xsd element attribute
		 * @param p_s_type			type of xsd element attribute
		 * @param p_s_mapping		mapping of xsd element attribute
		 */
		public XSDAttribute(String p_s_name, String p_s_type, String p_s_mapping) {
			this(p_s_name, p_s_type, p_s_mapping, false, "", "");
		}
		
		/**
		 * XSDAttribute constructor, no default value [= null], no fixed value [= null]
		 * 
		 * @param p_s_name			name of xsd element attribute
		 * @param p_s_type			type of xsd element attribute
		 * @param p_s_mapping		mapping of xsd element attribute
		 * @param p_b_required		required flag of xsd element attribute
		 */
		public XSDAttribute(String p_s_name, String p_s_type, String p_s_mapping, boolean p_b_required) {
			this(p_s_name, p_s_type, p_s_mapping, p_b_required, "", "");
		}
		
		/**
		 * XSDAttribute constructor, no fixed value [= null]
		 * 
		 * @param p_s_name			name of xsd element attribute
		 * @param p_s_type			type of xsd element attribute
		 * @param p_s_mapping		mapping of xsd element attribute
		 * @param p_b_required		required flag of xsd element attribute
		 * @param p_s_default		default value of xsd element attribute
		 */
		public XSDAttribute(String p_s_name, String p_s_type, String p_s_mapping, boolean p_b_required, String p_s_default) {
			this(p_s_name, p_s_type, p_s_mapping, p_b_required, p_s_default, "");
		}
		
		/**
		 * XSDAttribute constructor
		 * 
		 * @param p_s_name			name of xsd element attribute
		 * @param p_s_type			type of xsd element attribute
		 * @param p_s_mapping		mapping of xsd element attribute
		 * @param p_b_required		required flag of xsd element attribute
		 * @param p_s_default		default value of xsd element attribute
		 * @param p_s_fixed			fixed constant value of xsd element attribute
		 */
		public XSDAttribute(String p_s_name, String p_s_type, String p_s_mapping, boolean p_b_required, String p_s_default, String p_s_fixed) {
			this.setName(p_s_name);
			this.setType(p_s_type);
			this.setMapping(p_s_mapping);
			this.setRequired(p_b_required);
			this.setDefault(p_s_default);
			this.setFixed(p_s_fixed);
			this.setReference("");
		}

		/**
		 * Checks if this xs:attribute object is equal to another xs:attribute object
		 * 
		 * @param p_o_xsdAttribute			xs:attribute object for comparison
		 * @return							true - equal, false - not equal
		 */
		public boolean isEqual(XSDAttribute p_o_xsdAttribute) {
			if (this.getRestrictions().size() != p_o_xsdAttribute.getRestrictions().size()) {
				return false;
			}
			
			for (int i = 0; i < this.getRestrictions().size(); i++) {
				if (!this.getRestrictions().get(i).isEqual( p_o_xsdAttribute.getRestrictions().get(i) )) {
					return false;
				}
			}
			
			if (
				(this.getName().contentEquals(p_o_xsdAttribute.getName())) &&
				(this.getType().contentEquals(p_o_xsdAttribute.getType())) &&
				(this.getMapping().contentEquals(p_o_xsdAttribute.getMapping())) &&
				(this.getRequired() == p_o_xsdAttribute.getRequired()) &&
				(this.getDefault().contentEquals(p_o_xsdAttribute.getDefault())) &&
				(this.getFixed().contentEquals(p_o_xsdAttribute.getFixed())) &&
				(this.getReference().contentEquals(p_o_xsdAttribute.getReference())) &&
				(this.getRestriction() == p_o_xsdAttribute.getRestriction()) &&
				(this.getSimpleType() == p_o_xsdAttribute.getSimpleType())
			) {
				return true;
			}
			
			return false;
		}
		
		/**
		 * Clones current xsd attribute object
		 */
		public XSDAttribute clone() {			
			XSDAttribute o_clone = new XSDAttribute();
			o_clone.setName(this.getName());
			o_clone.setType(this.getType());
			o_clone.setMapping(this.getMapping());
			o_clone.setRequired(this.getRequired());
			o_clone.setDefault(this.getDefault());
			o_clone.setFixed(this.getFixed());
			o_clone.setReference(this.getReference());
			o_clone.setRestriction(this.getRestriction());
			o_clone.setSimpleType(this.getSimpleType());
			
			for (XSDRestriction o_restriction : this.getRestrictions()) {
				o_clone.getRestrictions().add(o_restriction.clone());
			}
			
			return o_clone;
		}
		
		/**
		 * Returns each field of xml/xsd attribute with name and value, separated by a pipe '|'
		 */
		@Override
		public String toString() {
			String s_foo = "\n\t" + "XSDAttribute: ";
			
			for (java.lang.reflect.Field o_field : this.getClass().getDeclaredFields()) {
				try {
					if (o_field.getName().startsWith("this$")) {
						continue;
					}
					
					if (o_field.get(this) instanceof java.util.List<?>) {
						java.util.List<?> a_objects = (java.util.List<?>)o_field.get(this);
						
						if (a_objects.size() > 0) {
							for (Object o_object : a_objects) {
								s_foo += o_object.toString();
							}
						}
						
						s_foo += "\n";
					} else {
						s_foo += o_field.getName() + " = " + o_field.get(this).toString() + "|";
					}
				} catch (Exception o_exc) {
					s_foo += o_field.getName() + " = ERR:AccessViolation|";
				}
			}
			
			s_foo = s_foo.substring(0, s_foo.length() - 1);
					
			return s_foo;
		}
	}
	
	/**
	 * Encapsulation of a schema's xsd element restrictions
	 */
	public class XSDRestriction {
		
		/* Fields */
		
		private String s_name;
		private String s_strValue;
		private int i_intValue;
		
		/* Properties */
		
		/**
		 * get name
		 * 
		 * @return String
		 */
		public String getName() {
			return this.s_name;
		}
		
		/**
		 * set name
		 * 
		 * @param p_s_value name value
		 */
		public void setName(String p_s_value) {
			this.s_name = p_s_value;
		}
		
		/**
		 * get string value
		 * 
		 * @return String
		 */
		public String getStrValue() {
			return this.s_strValue;
		}
		
		/**
		 * set string value
		 * 
		 * @param p_s_value string value
		 */
		public void setStrValue(String p_s_value) {
			this.s_strValue = p_s_value;
		}
		
		/**
		 * get int value
		 * 
		 * @return int
		 */
		public int getIntValue() {
			return this.i_intValue;
		}
		
		/**
		 * set int value
		 * 
		 * @param p_i_value int value
		 */
		public void setIntValue(int p_i_value) {
			this.i_intValue = p_i_value;
		}
		
		/* Methods */
		
		/**
		 * XSDRestriction constructor, no name value [= null], no string value [= null] and no integer value [= 0]
		 */
		public XSDRestriction() {
			this("", "", 0);
		}
		
		/**
		 * XSDRestriction constructor, no string value [= null] and no integer value [= 0]
		 * 
		 * @param p_s_name			name of xml element restriction
		 */
		public XSDRestriction(String p_s_name) {
			this(p_s_name, "", 0);
		}
		
		/**
		 * XSDRestriction constructor, no integer value [= 0]
		 * 
		 * @param p_s_name			name of xml element restriction
		 * @param p_s_strValue		string value of restriction
		 */
		public XSDRestriction(String p_s_name, String p_s_strValue) {
			this(p_s_name, p_s_strValue, 0);
		}
		
		/**
		 * XSDRestriction constructor
		 * 
		 * @param p_s_name			name of xml element restriction
		 * @param p_s_strValue		string value of restriction
		 * @param p_i_intValue		integer value of restriction
		 */
		public XSDRestriction(String p_s_name, String p_s_strValue, int p_i_intValue) {
			this.setName(p_s_name);
			this.setStrValue(p_s_strValue);
			this.setIntValue(p_i_intValue);
		}
		
		/**
		 * Checks if this xs:restriction object is equal to another xs:restriction object
		 * 
		 * @param p_o_xsdRestriction			xs:restriction object for comparison
		 * @return								true - equal, false - not equal
		 */
		public boolean isEqual(XSDRestriction p_o_xsdRestriction) {
			if ( (this.getName().contentEquals(p_o_xsdRestriction.getName())) && (this.getStrValue().contentEquals(p_o_xsdRestriction.getStrValue())) && (this.getIntValue() == p_o_xsdRestriction.getIntValue()) ) {
				return true;
			}
			
			return false;
		}
		
		/**
		 * Clones current xsd restriction object
		 */
		public XSDRestriction clone() {
			return new XSDRestriction(this.getName(), this.getStrValue(), this.getIntValue());
		}
		
		/**
		 * Returns each field of xml/xsd element restriction with name and value, separated by a pipe '|'
		 */
		@Override
		public String toString() {
			String s_foo = "\n\t\t" + "XSDRestriction: ";
			
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
