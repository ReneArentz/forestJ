package net.forestany.forestj.lib.net.https.soap;

/**
 * WSDL class to parse a wsdl file and having all information within class instances and stored properties available after parsing.
 */
public class WSDL {
	
	/* Interfaces */
	
	/**
	 * Interface declaration to define the shell of a SOAP operation which must be used to execute SOAP operations within a tiny SOAP server
	 */
	public interface SOAPOperationInterface {
		/**
		 * Empty method shell for a SOAP operation method
		 * 
		 * @param p_o_inputMessage		soap input message as parameter
		 * @param p_o_seed				seed instance as parameter
		 * @return						soap output message as parameter, will be controlled by tiny SOAP server if it matches the WSDL
		 * @throws Exception			any kind of exception which can happen during the operation
		 */
		Object SOAPOperation(Object p_o_inputMessage, net.forestany.forestj.lib.net.https.Seed p_o_seed) throws Exception;
	}
	
	/* Fields */
	
	private String s_path;
	private String s_wsdlFile;
	private String s_xsdFile;
	private String s_documentation;
	private net.forestany.forestj.lib.io.XML o_schema;
	private java.util.List<Message> a_messages;
	private java.util.List<PortTypeOperation> a_portTypeOperations;
	private java.util.List<Binding> a_bindings;
	private Service o_service;
	private java.util.Map<String, SOAPOperationInterface> m_soapOperations;
	
	/* Properties */
	
	/**
	 * get wsdl file
	 * 
	 * @return String
	 */
	public String getWSDLFile() {
		return this.s_wsdlFile;
	}
	
	/**
	 * get xsd file
	 * 
	 * @return String
	 */
	public String getXSDFile() {
		return this.s_xsdFile;
	}
	
	/**
	 * get documentation
	 * 
	 * @return String
	 */
	public String getDocumentation() {
		return this.s_documentation;
	}
	
	/**
	 * get schema
	 * 
	 * @return net.forestany.forestj.lib.io.XML
	 */
	public net.forestany.forestj.lib.io.XML getSchema() {
		return this.o_schema;
	}
	
	/**
	 * get messages
	 * 
	 * @return java.util.List&lt;Message&gt;
	 */
	public java.util.List<Message> getMessages() {
		return this.a_messages;
	}
	
	/**
	 * get port type operations
	 * 
	 * @return java.util.List&lt;PortTypeOperation&gt;
	 */
	public java.util.List<PortTypeOperation> getPortTypeOperations() {
		return this.a_portTypeOperations;
	}
	
	/**
	 * get bindings
	 * 
	 * @return java.util.List&lt;Binding&gt;
	 */
	public java.util.List<Binding> getBindings() {
		return this.a_bindings;
	}
	
	/**
	 * get service
	 * 
	 * @return String
	 */
	public Service getService() {
		return this.o_service;
	}
	
	/**
	 * get soap operations
	 * 
	 * @return java.util.Map&lt;String, SOAPOperationInterface&gt;
	 */
	public java.util.Map<String, SOAPOperationInterface> getSOAPOperations() {
		return this.m_soapOperations;
	}
	
	/**
	 * set SOAP operation with key name and SOAP operation interface. key names are defined by PortType Operation name
	 * 
	 * @param p_s_soapOperation					key value of SOAP operation
	 * @param p_o_soi							SOAP operation interface, which implements a method which can be executed
	 * @throws NullPointerException				key value or SOAP operation interface parameter are null or empty
	 * @throws IllegalArgumentException			SOAP operation does not exist in WSDL list
	 */
	public void setSOAPOperation(String p_s_soapOperation, SOAPOperationInterface p_o_soi) throws NullPointerException, IllegalArgumentException {
		/* check if key parameter is not null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_soapOperation)) {
			throw new NullPointerException("SOAP operation name parameter is empty or null");
		}
		
		/* check if SOAP operation interface parameter is not null */
		if (p_o_soi == null) {
			throw new NullPointerException("SOAP operation parameter is null");
		}
		
		/* check if key value already exists in SOAP operation map, origin is PortType Operation names in WSDL */
		if (!this.m_soapOperations.containsKey(p_s_soapOperation)) {
			throw new IllegalArgumentException("SOAP operation does not exist in WSDL list");
		}
		
		/* add SOAP operation with it's key value */
		this.m_soapOperations.put(p_s_soapOperation, p_o_soi);
	}
	
	/* Methods */
	
	/**
	 * WSDL constructor, giving a wsdl file as information input
	 * 
	 * @param p_s_file								full-path to wsdl schema file
	 * @throws IllegalArgumentException				value/structure within wsdl schema file invalid
	 * @throws NullPointerException					wsdl schema, root node is null
	 * @throws java.io.IOException					cannot access or open wsdl file and it's content
	 */
	public WSDL(String p_s_file) throws IllegalArgumentException, NullPointerException, java.io.IOException {
		this.s_xsdFile = null;
		this.m_soapOperations = new java.util.HashMap<String, SOAPOperationInterface>();
		
		/* check if file exists */
		if (!net.forestany.forestj.lib.io.File.exists(p_s_file)) {
			throw new IllegalArgumentException("File[" + p_s_file + "] does not exist.");
		}
		
		/* get path of wsdl and possible xsd schema location */
		if (p_s_file.contains("/")) { /* unix directory separator */
			this.s_path = p_s_file.substring(0, p_s_file.lastIndexOf("/") + 1);
			this.s_wsdlFile = p_s_file.substring(p_s_file.lastIndexOf("/") + 1);
		} else if (p_s_file.contains("\\")) { /* windows directory separator */
			this.s_path = p_s_file.substring(0, p_s_file.lastIndexOf("\\") + 1);
			this.s_wsdlFile = p_s_file.substring(p_s_file.lastIndexOf("\\") + 1);
		}
		
		/* open wsdl-schema file */
		net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(p_s_file, false);
		
		StringBuilder o_stringBuilder = new StringBuilder();
		
		/* read all wsdl schema file lines to one string builder */
		for (String s_line : o_file.getFileContentAsList()) {
			o_stringBuilder.append(s_line);
		}
		
		/* read all wsdl-schema file lines and delete all line-wraps and tabs and values only containing white spaces */
		String s_wsdl = o_stringBuilder.toString().replaceAll("[\\r\\n\\t]", "").replaceAll(">\\s*<", "><");
		
		/* do not allow 'soap' or 'soap12' namespace tags, only SOAP 1.1 in a reduced primitive implementation is allowed */
		if ( (s_wsdl.indexOf("<soap:") >= 0) && (s_wsdl.indexOf("<soap12:") >= 0) ) {
			throw new IllegalArgumentException("SOAP for forestJ can only handle SOAP 1.1 in a reduced primitive implementation.");
		}
		
		/* clean up wsdl-schema */
		s_wsdl = s_wsdl.replaceAll("<\\?(.*?)\\?>", "");
		s_wsdl = s_wsdl.replaceAll("<!--(.*?)-->", "");
		s_wsdl = s_wsdl.replaceAll("<xs:annotation>(.*?)</xs:annotation>", "");
		s_wsdl = s_wsdl.replaceAll("<soap:", "<");
		s_wsdl = s_wsdl.replaceAll("</soap:", "</");
		s_wsdl = s_wsdl.replaceAll("<wsdl:", "<");
		s_wsdl = s_wsdl.replaceAll("</wsdl:", "</");
		
		/* make documentation tags readable for this class */
		s_wsdl = s_wsdl.replaceAll("<documentation>", "<documentation value=\"");
		s_wsdl = s_wsdl.replaceAll("</documentation>", "\"/>");
		
		/* validate wsdl */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("(<[^<>]*?<[^<>]*?>|<[^<>]*?>[^<>]*?>)");
		java.util.regex.Matcher o_matcher = o_regex.matcher(s_wsdl);
		
		/* if regex-matcher has match, the wsdl-schema is not valid */
	    while (o_matcher.find()) {
	        throw new IllegalArgumentException("Invalid wsdl-schema. Please check wsdl-schema at \"" + o_matcher.group(0) + "\".");
	    }
		
	    java.util.List<String> a_wsdlTags = new java.util.ArrayList<String>();
	    
	    /* add all wsdl-schema-tags to a list for parsing */
	    o_regex = java.util.regex.Pattern.compile("<[^<>]*?>");
	    o_matcher = o_regex.matcher(s_wsdl);
	    
	    while (o_matcher.find()) {
	        a_wsdlTags.add(o_matcher.group(0));
	    }
	    
	    /* check if wsdl-schema starts with <definitions>-tag */
	    if (!a_wsdlTags.get(0).toLowerCase().startsWith("<definitions")) {
    		throw new IllegalArgumentException("wsdl-schema must start with <definitions>-tag");
    	}
	    
	    this.a_messages = new java.util.ArrayList<Message>();
	    this.a_portTypeOperations = new java.util.ArrayList<PortTypeOperation>();
	    this.a_bindings = new java.util.ArrayList<Binding>();
	    
	    /* call method to parse wsdl */
	    this.parseWSDL(a_wsdlTags, 0, a_wsdlTags.size());
	}
	
	/**
	 * Parsing complete wsdl file
	 * 
	 * @param p_a_wsdlTags						all wsdl xml/xsd-tags
	 * @param p_i_min							xml/xsd-tag in parameter list where we start
	 * @param p_i_max							xml/xsd-tag in parameter list where we stop
	 * @throws IllegalArgumentException			invalid xml/xsd-tag found within wsdl file
	 * @throws java.io.IOException				cannot access or open xsd file and it's content for schema location import
	 */
	private void parseWSDL(java.util.List<String> p_a_wsdlTags, int p_i_min, int p_i_max) throws IllegalArgumentException, java.io.IOException {
		if (p_i_min == 0) { /* expecting <definitions> */
			/* check if we have <definitions> */
			if (!p_a_wsdlTags.get(0).startsWith("<definitions")) {
				throw new IllegalArgumentException("Invalid wsdl document. Expected <wsdl:definitions>-tag, but found \"" + p_a_wsdlTags.get(0) + "\".");
			}
			
			/* check if <definitions> will be closed */
			if (!this.lookForEndTag(p_a_wsdlTags.get(0), p_a_wsdlTags, 1, p_i_max - 1)) {
				throw new IllegalArgumentException("Invalid wsdl document. <wsdl:definitions>-tag is not closed in wsdl file");
			}
			
			/* go to next xml/xsd-tag */
			p_i_min++;
		}
		
		if (p_i_min == 1) { /* expecting <documentation /> or <types> */
			if ( (p_a_wsdlTags.get(1).startsWith("<documentation")) && (p_a_wsdlTags.get(1).endsWith("/>")) ) { /* check if we have <documentation /> */
				/* parse <documentation /> tag */
				this.s_documentation = this.parseDocumentationTag(p_a_wsdlTags.get(1));
				
				/* parse <types> */
				p_i_min = this.parseTypes(p_a_wsdlTags, (p_i_min + 2), p_i_max);
			} else if (p_a_wsdlTags.get(1).contentEquals("<types>")) { /* check if we have <types> */
				/* parse <types> */
				p_i_min = this.parseTypes(p_a_wsdlTags, (p_i_min + 1), p_i_max);
			}
		}
		
		/* check if parsing until now was valid */
		if (p_i_min < 0) {
			throw new IllegalArgumentException("Invalid wsdl document. <types> could not be parsed");
		}
		
		/* parse <message> tags */
		p_i_min = this.parseMessages(p_a_wsdlTags, p_i_min, p_i_max);
		
		/* parse <portType> with <operation> tags */
		p_i_min = this.parsePortType(p_a_wsdlTags, p_i_min, p_i_max);
		
		/* parse <binding> tags */
		do {
			p_i_min = this.parseBinding(p_a_wsdlTags, p_i_min, p_i_max);
		} while (p_a_wsdlTags.get(p_i_min).startsWith("<binding"));
		
		/* parse <service> with <port> tags */
		this.parseService(p_a_wsdlTags, p_i_min, p_i_max);
	}
	
	/**
	 * method to check if a xml/xsd-tag will be closed within wsdl file
	 * 
	 * @param p_s_wsdlTag				xml/xsd-tag which must be closed
	 * @param p_a_wsdlTags				list of following xml/xsd-tags
	 * @param p_i_min					xml/xsd-tag in parameter list where we start
	 * @param p_i_max					xml/xsd-tag in parameter list where we stop
	 */
	private boolean lookForEndTag(String p_s_wsdlTag, java.util.List<String> p_a_wsdlTags, int p_i_min, int p_i_max) {
		/* get xml/xsd-tag definition name */
		if (p_s_wsdlTag.indexOf(" ") > 1) {
			/* get xml/xsd-tag definition name until first appearance of a whitespace */
			p_s_wsdlTag = p_s_wsdlTag.substring(1, p_s_wsdlTag.indexOf(" "));
		} else {
			/* get xml/xsd-tag definition name until end of tag */
			p_s_wsdlTag = p_s_wsdlTag.substring(1, p_s_wsdlTag.indexOf(">"));
		}
		
		/* iterate each following xml/xsd-tag */
		for (int i_min = p_i_min; i_min <= p_i_max; i_min++) {
			/* if we find our definition name as closing tag */
			if (p_a_wsdlTags.get(i_min).contentEquals("</" + p_s_wsdlTag + ">")) {
				/* we have our end tag */
				return true;
			}
		}
		
		/* no end tag found */
		return false;
	}
	
	/**
	 * get documentation value out of xml/xsd-tag
	 * 
	 * @param p_s_wsdlTag						xml/xsd-tag with documenation value
	 * @throws IllegalArgumentException			Invalid wsdl-tag xs:attribute without a documentation value
	 */
	private String parseDocumentationTag(String p_s_wsdlTag) throws IllegalArgumentException {
		/* read documentation tag */
		java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("value=\"([^\"]*)\"");
		java.util.regex.Matcher o_matcher = o_regex.matcher(p_s_wsdlTag);
		
	    if (o_matcher.find()) {
	    	/* look for and find value attribute */
    		return o_matcher.group(0).substring(7, o_matcher.group(0).length() - 1);
	    } else {
	    	/* no documentation attribute found */
	    	throw new IllegalArgumentException("Invalid wsdl-tag xs:attribute without a documentation value");
	    }
	}

	/**
	 * parsing types with schema xsd or schema location import
	 * 
	 * @param p_a_wsdlTags						list of following xml/xsd-tags
	 * @param p_i_min							xml/xsd-tag in parameter list where we start
	 * @param p_i_max							xml/xsd-tag in parameter list where we stop
	 * @throws IllegalArgumentException			invalid xml/xsd-tag found within wsdl file
	 * @throws java.io.IOException				cannot access or open xsd file and it's content for schema location import
	 */
	private int parseTypes(java.util.List<String> p_a_wsdlTags, int p_i_min, int p_i_max) throws IllegalArgumentException, java.io.IOException {
		/* check if <types> will be closed */
		if (!this.lookForEndTag("<types>", p_a_wsdlTags, p_i_min, p_i_max)) {
			throw new IllegalArgumentException("Invalid wsdl document. <types>-tag is not closed in wsdl file");
		}
		
		/* expect <xs:schema> or <schema> tag */
		if (!( (p_a_wsdlTags.get(p_i_min).startsWith("<xs:schema ")) || (p_a_wsdlTags.get(p_i_min).endsWith("schema>")) )) {
			throw new IllegalArgumentException("Invalid wsdl document. <xs:schema>-tag not detected after <types>-tag");
		}
		
		if (p_a_wsdlTags.get(p_i_min + 1).startsWith("<xs:import ")) { /* import xsd schema from other file */
			/* read schemaLocation attribute */
			java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("schemaLocation=\"([^\"]*)\"");
			java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(p_i_min + 1));
			
			if (o_matcher.find()) {
	    		String s_schemaLocation = o_matcher.group(0).substring(16, o_matcher.group(0).length() - 1);
	    		
	    		/* remove './' from schema location value */
	    		if (s_schemaLocation.startsWith("./")) {
	    			s_schemaLocation = s_schemaLocation.substring(2);
	    		}
	    		
	    		/* xsd schema must be in the same folder as wsdl file */
	    		if ( (s_schemaLocation.contains("/")) || (s_schemaLocation.contains("\\")) ) {
	    			throw new IllegalArgumentException("Invalid wsdl document. xsd schema in types, <xs:import>-tag has invalid schema location. xsd-file must stay next to wsdl-file");
	    		}
	    		
	    		/* store xsd file value */
	    		this.s_xsdFile = s_schemaLocation;
	    		
	    		/* create xml instance and store it */
	    		this.o_schema = new net.forestany.forestj.lib.io.XML(this.s_path + s_schemaLocation);
	    		
	    		/* find end of <types> part */
	    		for (int i = p_i_min + 2; i < p_i_max; i++) {
	    			if (p_a_wsdlTags.get(i).contentEquals("</types>")) {
	    				/* return xml/xsd-tag position to continue parsing */
	    				return i + 1;
	    			}
	    		}
	    	} else {
		    	/* no schemaLocation attribute found */
		    	throw new IllegalArgumentException("Invalid wsdl-tag xs:import without a schemaLocation attribute value");
		    }
		} else { /* read xsd schema tags */
			java.util.List<String> a_schemaLines = new java.util.ArrayList<String>();
			int i_endOfTypes = -1;
			
			for (int i = p_i_min; i < p_i_max; i++) {
				/* find end of <types> part */
				if (p_a_wsdlTags.get(i).contentEquals("</types>")) {
					i_endOfTypes = i;
					break;
				}
				
				/* add xml/xsd-tag to our schema */
				a_schemaLines.add(p_a_wsdlTags.get(i));
			}
			
			/* create xml instance and store it */
			this.o_schema = new net.forestany.forestj.lib.io.XML(a_schemaLines);
			
			/* return xml/xsd-tag position to continue parsing */
			return (i_endOfTypes + 1);
		}
		
		return -1;
	}
	
	/**
	 * parsing messages tags
	 * 
	 * @param p_a_wsdlTags						list of following xml/xsd-tags
	 * @param p_i_min							xml/xsd-tag in parameter list where we start
	 * @param p_i_max							xml/xsd-tag in parameter list where we stop
	 * @throws IllegalArgumentException			invalid xml/xsd-tag found within wsdl file
	 */
	private int parseMessages(java.util.List<String> p_a_wsdlTags, int p_i_min, int p_i_max) throws IllegalArgumentException {
		int i_endOfMessages = -1;
		
		/* find end of message tags */
		for (int i = p_i_min; i < p_i_max; i++) {
			if (p_a_wsdlTags.get(i).startsWith("<portType")) {
				i_endOfMessages = i - 1;
				break;
			}
		}
		
		/* could not find next <portType> after message tags */
		if (i_endOfMessages < 0) {
			throw new IllegalArgumentException("Invalid wsdl document. <wsdl:message> tags could not be parsed");
		}
		
		/* iterate all message xml/xsd-tags */
		for (int i = p_i_min; i <= i_endOfMessages; i++) {
			String s_messageName;
			String s_messagePartName;
			String s_messagePartElement;
			
			if (p_a_wsdlTags.get(i).startsWith("<message")) {
				/* read name tag */
				java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
				java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
				
			    if (o_matcher.find()) {
			    	/* store message name */
			    	s_messageName = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
			    } else {
			    	/* no name attribute found */
			    	throw new IllegalArgumentException("Invalid <wsdl:message>-tag without a name attribute");
			    }
			    
			    /* look for end of message tag */
			    if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, i_endOfMessages)) {
					throw new IllegalArgumentException("Invalid wsdl document. <wsdl:message>-tag is not closed in wsdl file");
				}
			    
			    i++;
			    
			    /* expect <part> tag */
			    if (p_a_wsdlTags.get(i).startsWith("<part")) {
			    	/* read name tag */
					o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
					o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
					
				    if (o_matcher.find()) {
				    	/* store part name of message */
				    	s_messagePartName = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
				    	
				    	/* part name of message must be 'parameters' */
				    	if (!s_messagePartName.contentEquals("parameters")) {
				    		throw new IllegalArgumentException("<wsdl:part>-tag name attribute must have the value 'parameters'");
				    	}
				    } else {
				    	/* no name attribute found */
				    	throw new IllegalArgumentException("Invalid <wsdl:part>-tag without a name attribute");
				    }
				    
				    /* read element tag */
					o_regex = java.util.regex.Pattern.compile("element=\"([^\"]*)\"");
					o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
					
				    if (o_matcher.find()) {
				    	/* store part element of message */
				    	s_messagePartElement = o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1);
				    	
				    	/* add new message object to our wsdl instance */
				    	this.a_messages.add( new Message(s_messageName, s_messagePartName, s_messagePartElement) );
				    } else {
				    	/* no element attribute found */
				    	throw new IllegalArgumentException("Invalid <wsdl:part>-tag without an element attribute");
				    }
				    
				    if (p_a_wsdlTags.get(i).endsWith("/>")) { /* <part> tag was self closing */
				    	i--;
				    } else if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, i_endOfMessages)) { /* check if <part> tag is closed */
			    		throw new IllegalArgumentException("Invalid wsdl document. <wsdl:part>-tag is not closed in wsdl file");
			    	}
				    
				    i++;
				    i++;
			    } else {
					/* no <wsdl:part>-tag found */
			    	throw new IllegalArgumentException("No <wsdl:part>-tag found");
				}
			} else {
				/* no <wsdl:message>-tag found */
		    	throw new IllegalArgumentException("No <wsdl:message>-tag found");
			}
		}
		
		/* return xml/xsd-tag position to continue parsing */
		return i_endOfMessages + 1;
	}
	
	/**
	 * parsing portType tag and all it's operation tags
	 * 
	 * @param p_a_wsdlTags						list of following xml/xsd-tags
	 * @param p_i_min							xml/xsd-tag in parameter list where we start
	 * @param p_i_max							xml/xsd-tag in parameter list where we stop
	 * @throws IllegalArgumentException			invalid xml/xsd-tag found within wsdl file
	 */
	private int parsePortType(java.util.List<String> p_a_wsdlTags, int p_i_min, int p_i_max) throws IllegalArgumentException {
		int i_endOfPortTypes = -1;
		
		/* find end of portType tags */
		for (int i = p_i_min; i < p_i_max; i++) {
			if (p_a_wsdlTags.get(i).startsWith("<binding")) {
				i_endOfPortTypes = i - 1;
				break;
			}
		}
		
		/* could not find next <binding> after portType tag */
		if (i_endOfPortTypes < 0) {
			throw new IllegalArgumentException("Invalid wsdl document. <wsdl:portType> tags could not be parsed");
		}
		
		String s_portTypeName;
		
		/* first one must be our portType tag */
		if (p_a_wsdlTags.get(p_i_min).startsWith("<portType")) {
			/* read name tag */
			java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
			java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(p_i_min));
			
		    if (o_matcher.find()) {
		    	/* store portType name */
		    	s_portTypeName = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
		    } else {
		    	/* no name attribute found */
		    	throw new IllegalArgumentException("Invalid <wsdl:portType>-tag without a name attribute");
		    }
		    
		    /* look for end of portType tag */
		    if (!this.lookForEndTag(p_a_wsdlTags.get(p_i_min), p_a_wsdlTags, p_i_min + 1, i_endOfPortTypes)) {
				throw new IllegalArgumentException("Invalid wsdl document. <wsdl:portType>-tag is not closed in wsdl file");
			}
		} else {
			/* no <wsdl:portType>-tag found */
	    	throw new IllegalArgumentException("No <wsdl:portType>-tag found");
		}
		
		/* iterate following tags which will include all operations of portType */
		for (int i = p_i_min + 1; i < i_endOfPortTypes; i++) {
			String s_portTypeOperationName;
			String s_inputMessage;
			String s_outputMessage;
			Message o_inputMessage = null;
			Message o_outputMessage = null;
			String s_portTypeDocumentation = null;
			
			/* expect operation tag */
			if (p_a_wsdlTags.get(i).startsWith("<operation")) {
				/* read name tag */
				java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
				java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
				
			    if (o_matcher.find()) {
			    	/* store operation name */
			    	s_portTypeOperationName = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
			    } else {
			    	/* no name attribute found */
			    	throw new IllegalArgumentException("Invalid <wsdl:operation>-tag without a name attribute");
			    }
			    
			    /* look for end of operation tag */
			    if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, i_endOfPortTypes)) {
					throw new IllegalArgumentException("Invalid wsdl document. <wsdl:operation>-tag is not closed in wsdl file");
				}
			    
			    i++;
			    
			    /* handle and parse possible documentation tag */
			    if (p_a_wsdlTags.get(i).startsWith("<documentation")) {
			    	s_portTypeDocumentation = this.parseDocumentationTag(p_a_wsdlTags.get(i));
			    	i++;
			    }
			    
			    /* expect input tag */
			    if (p_a_wsdlTags.get(i).startsWith("<input")) {
			    	/* read message tag */
					o_regex = java.util.regex.Pattern.compile("message=\"([^\"]*)\"");
					o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
					
				    if (o_matcher.find()) {
				    	/* store input message */
				    	s_inputMessage = o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1);
				    	
				    	/* check if we find our message value in our message list */
				    	for (Message o_message : this.a_messages) {
				    		if (o_message.getName().contentEquals(s_inputMessage)) {
				    			o_inputMessage = o_message;
				    		}
				    	}
				    	
				    	/* if message value does not exist in our message list, it is unknown */
				    	if (o_inputMessage == null) {
				    		throw new IllegalArgumentException("<wsdl:input>-tag has unknown message value '" + s_inputMessage + "'");
				    	}
				    } else {
				    	/* no message attribute found */
				    	throw new IllegalArgumentException("Invalid <wsdl:input>-tag without a message attribute");
				    }
				    
				    if (p_a_wsdlTags.get(i).endsWith("/>")) { /* <input> tag was self closing */
				    	i--;
				    } else if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, i_endOfPortTypes)) { /* check if <input> tag is closed */
			    		throw new IllegalArgumentException("Invalid wsdl document. <wsdl:input>-tag is not closed in wsdl file");
			    	}
				    
				    i++;
				    i++;
			    } else {
					/* no <wsdl:input>-tag found */
			    	throw new IllegalArgumentException("No <wsdl:input>-tag found");
				}
			    
			    /* expect output tag */
			    if (p_a_wsdlTags.get(i).startsWith("<output")) {
			    	/* read message tag */
					o_regex = java.util.regex.Pattern.compile("message=\"([^\"]*)\"");
					o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
					
				    if (o_matcher.find()) {
				    	/* store output message */
				    	s_outputMessage = o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1);
				    	
				    	/* check if we find our message value in our message list */
				    	for (Message o_message : this.a_messages) {
				    		if (o_message.getName().contentEquals(s_outputMessage)) {
				    			o_outputMessage = o_message;
				    		}
				    	}
				    	
				    	/* if message value does not exist in our message list, it is unknown */
				    	if (o_outputMessage == null) {
				    		throw new IllegalArgumentException("<wsdl:output>-tag has unknown message value '" + s_outputMessage + "'");
				    	}
				    } else {
				    	/* no message attribute found */
				    	throw new IllegalArgumentException("Invalid <wsdl:output>-tag without a message attribute");
				    }
				    
				    if (p_a_wsdlTags.get(i).endsWith("/>")) { /* <output> tag was self closing */
				    	i--;
				    } else if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, i_endOfPortTypes)) { /* check if <output> tag is closed */
			    		throw new IllegalArgumentException("Invalid wsdl document. <wsdl:output>-tag is not closed in wsdl file");
			    	}
				    
				    i++;
				    i++;
			    } else {
					/* no <wsdl:output>-tag found */
			    	throw new IllegalArgumentException("No <wsdl:output>-tag found");
				}
			} else {
				/* no <wsdl:message>-tag found */
		    	throw new IllegalArgumentException("No <wsdl:operation>-tag found");
			}
			
			/* add portType object to our list, with name, operation name, input and output message */
			this.a_portTypeOperations.add(new PortTypeOperation(s_portTypeName, s_portTypeOperationName, o_inputMessage, o_outputMessage, s_portTypeDocumentation));
			/* store portType operation name as one of our SOAP operation which must be implemented */
			this.m_soapOperations.put(s_portTypeOperationName, null);
		}
		
		/* return xml/xsd-tag position to continue parsing */
		return i_endOfPortTypes + 1;
	}
	
	/**
	 * parsing binding tags and all it's operation input and output tags
	 * 
	 * @param p_a_wsdlTags						list of following xml/xsd-tags
	 * @param p_i_min							xml/xsd-tag in parameter list where we start
	 * @param p_i_max							xml/xsd-tag in parameter list where we stop
	 * @throws IllegalArgumentException			invalid xml/xsd-tag found within wsdl file
	 */
	private int parseBinding(java.util.List<String> p_a_wsdlTags, int p_i_min, int p_i_max) throws IllegalArgumentException {
		int i_endOfBinding = -1;
		boolean b_bindingEndingOnce = false;
		
		/* check if soap:binding has closed itself, so we can continue with wsdl:binding */
		if (p_a_wsdlTags.get(p_i_min + 1).endsWith("/>")) {
			b_bindingEndingOnce = true;
		}
		
		/* find end of binding part */
		for (int i = p_i_min; i < p_i_max; i++) {
			/* found binding closing tag */
			if (p_a_wsdlTags.get(i).startsWith("</binding")) {
				/* ignore closed soap:binding tag */
				if (!b_bindingEndingOnce) {
					b_bindingEndingOnce = true;
					continue;
				}
				
				i_endOfBinding = i;
				break;
			}
		}
		
		/* could not find end of <binding> part */
		if (i_endOfBinding < 0) {
			throw new IllegalArgumentException("Invalid wsdl document. <wsdl:binding> tags could not be parsed");
		}
		
		String s_bindingName;
		String s_bindingType;
		java.util.List<PortTypeOperation> a_bindingPortTypeOperations = new java.util.ArrayList<PortTypeOperation>();
		
		/* expect wsdl binding tag */
		if (p_a_wsdlTags.get(p_i_min).startsWith("<binding")) {
			/* read name tag */
			java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
			java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(p_i_min));
			
		    if (o_matcher.find()) {
		    	/* store binding name */
		    	s_bindingName = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
		    } else {
		    	/* no name attribute found */
		    	throw new IllegalArgumentException("Invalid <wsdl:binding>-tag without a name attribute");
		    }
		    
		    /* read name tag */
			o_regex = java.util.regex.Pattern.compile("type=\"([^\"]*)\"");
			o_matcher = o_regex.matcher(p_a_wsdlTags.get(p_i_min));
			
		    if (o_matcher.find()) {
		    	/* store binding type */
		    	s_bindingType = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
		    	boolean b_found = false;
		    	
		    	/* check if binding type exists in our portType list */
		    	for (PortTypeOperation o_foo : this.a_portTypeOperations) {
		    		if (o_foo.getPortTypeName().contentEquals(s_bindingType)) {
		    			b_found = true;
		    			break;
		    		}
		    	}
		    	
		    	/* if binding type has not been found in our portType list */
		    	if (!b_found) {
		    		throw new IllegalArgumentException("<wsdl:binding>-tag has unknown type value '" + s_bindingType + "' within wsdl document");
		    	}
		    } else {
		    	/* no type attribute found */
		    	throw new IllegalArgumentException("Invalid <wsdl:binding>-tag without a type attribute");
		    }
		    
		    /* check if wsdl binding tag will be closed */
		    if (!this.lookForEndTag(p_a_wsdlTags.get(p_i_min), p_a_wsdlTags, p_i_min + 1, i_endOfBinding)) {
				throw new IllegalArgumentException("Invalid wsdl document. <wsdl:binding>-tag is not closed in wsdl file");
			}
		} else {
			/* no <wsdl:binding>-tag found */
	    	throw new IllegalArgumentException("No <wsdl:binding>-tag found");
		}
		
		/* expect soap binding tag */
		if (p_a_wsdlTags.get(p_i_min + 1).startsWith("<binding")) {
			/* read name tag */
			java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("style=\"([^\"]*)\"");
			java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(p_i_min + 1));
			
		    if (o_matcher.find()) {
		    	String s_foo = o_matcher.group(0).substring(7, o_matcher.group(0).length() - 1);
		    	
		    	/* name attribute of soap binding must be 'document' */
		    	if (!s_foo.contentEquals("document")) {
		    		throw new IllegalArgumentException("Invalid wsdl document. <soap:binding>-tag has not a style attribute with value 'document', but '" + s_foo + "'");
		    	}
		    } else {
		    	/* no style attribute found */
		    	throw new IllegalArgumentException("Invalid <wsdl:binding>-tag without a style attribute");
		    }
		    
		    /* read transport tag */
			o_regex = java.util.regex.Pattern.compile("transport=\"([^\"]*)\"");
			o_matcher = o_regex.matcher(p_a_wsdlTags.get(p_i_min + 1));
			
		    if (o_matcher.find()) {
		    	String s_foo = o_matcher.group(0).substring(11, o_matcher.group(0).length() - 1);
		    	
		    	/* transport attribute of soap binding must be 'http://schemas.xmlsoap.org/soap/http' */
		    	if (!s_foo.contentEquals("http://schemas.xmlsoap.org/soap/http")) {
		    		throw new IllegalArgumentException("Invalid wsdl document. <soap:binding>-tag has not a transport attribute with value 'http://schemas.xmlsoap.org/soap/http'");
		    	}
		    } else {
		    	/* no transport attribute found */
		    	throw new IllegalArgumentException("Invalid <wsdl:binding>-tag without a transport attribute");
		    }
		    
		    /* check if soap binding tag will be closed */
		    if ( (!p_a_wsdlTags.get(p_i_min + 1).endsWith("/>")) && (!this.lookForEndTag(p_a_wsdlTags.get(p_i_min + 1), p_a_wsdlTags, p_i_min + 2, i_endOfBinding)) ) {
				throw new IllegalArgumentException("Invalid wsdl document. <soap:binding>-tag is not closed in wsdl file");
			}
		} else {
			/* no <soap:binding>-tag found */
	    	throw new IllegalArgumentException("No <soap:binding>-tag found");
		}

		int i_operationsStart = p_i_min + 2;
		
		/* check when next binding operation tag is starting */
		if (!p_a_wsdlTags.get(p_i_min + 1).endsWith("/>")) {
			i_operationsStart++;
		}
		
		/* iterate following tags for wsdl operation and soap operation */
		for (int i = i_operationsStart; i < i_endOfBinding; i++) {
			String s_bindingOperationName;
			
			/* expect wsdl binding tag */
			if (p_a_wsdlTags.get(i).startsWith("<operation")) {
				/* read name tag */
				java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
				java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
				
			    if (o_matcher.find()) {
			    	/* store binding operation name */
			    	s_bindingOperationName = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
			    	
			    	/* look for our portType operation with our binding operation name and add it to our binding list */
			    	for (PortTypeOperation o_foo : this.a_portTypeOperations) {
			    		if (o_foo.getPortTypeOperationName().contentEquals(s_bindingOperationName)) {
			    			a_bindingPortTypeOperations.add(o_foo);
			    		}
			    	}
			    } else {
			    	/* no name attribute found */
			    	throw new IllegalArgumentException("Invalid <wsdl:operation>-tag without a name attribute");
			    }
			    
			    /* check if wsdl binding tag is closed */
			    if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, i_endOfBinding)) {
					throw new IllegalArgumentException("Invalid wsdl document. <wsdl:operation>-tag is not closed in wsdl file");
				}
			    
			    i++;
			} else {
				/* no <wsdl:operation>-tag found */
		    	throw new IllegalArgumentException("No <wsdl:operation>-tag found");
			}
			
			/* expect soap operation tag */
			if (p_a_wsdlTags.get(i).startsWith("<operation")) {
				/* read name tag */
				java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("style=\"([^\"]*)\"");
				java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
				
			    if (o_matcher.find()) {
			    	String s_foo = o_matcher.group(0).substring(7, o_matcher.group(0).length() - 1);
			    	
			    	/* soap operation tag attribute style must be 'document' */
			    	if (!s_foo.contentEquals("document")) {
			    		throw new IllegalArgumentException("Invalid wsdl document. <soap:operation>-tag has not a style attribute with value 'document', but '" + s_foo + "'");
			    	}
			    } else {
			    	/* no style attribute found */
			    	throw new IllegalArgumentException("Invalid <soap:operation>-tag without a style attribute");
			    }
			    
			    if (p_a_wsdlTags.get(i).endsWith("/>")) { /* soap operation tag was self-closing */
			    	i--;
			    } else if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, i_endOfBinding)) { /* check if soap operation tag is closed */
					throw new IllegalArgumentException("Invalid wsdl document. <soap:operation>-tag is not closed in wsdl file");
				}
			    
			    i++;
			    i++;
			} else {
				/* no <soap:operation>-tag found */
		    	throw new IllegalArgumentException("No <soap:operation>-tag found");
			}
			
			/* expect input tag */
			if (p_a_wsdlTags.get(i).startsWith("<input")) {
				/* check if input tag is closed */
				if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, i_endOfBinding)) {
					throw new IllegalArgumentException("Invalid wsdl document. <wsdl:input>-tag is not closed in wsdl file");
				}
			    
			    i++;
			} else {
				/* no <wsdl:input>-tag found */
		    	throw new IllegalArgumentException("No <wsdl:input>-tag found");
			}
			
			/* expect body tag */
			if (p_a_wsdlTags.get(i).startsWith("<body")) {
				/* read use tag */
				java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("use=\"([^\"]*)\"");
				java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
				
			    if (o_matcher.find()) {
			    	String s_foo = o_matcher.group(0).substring(5, o_matcher.group(0).length() - 1);
			    	
			    	/* body tag use attribute must be 'literal' */
			    	if (!s_foo.contentEquals("literal")) {
			    		throw new IllegalArgumentException("Invalid wsdl document. <soap:body>-tag has not a use attribute with value 'literal', but '" + s_foo + "'");
			    	}
			    } else {
			    	/* no use attribute found */
			    	throw new IllegalArgumentException("Invalid <soap:body>-tag without a use attribute");
			    }
			    				
				if (p_a_wsdlTags.get(i).endsWith("/>")) { /* body tag was self-closing */
					i--;
				} else if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, i_endOfBinding)) { /* check if body tag is closed */
					throw new IllegalArgumentException("Invalid wsdl document. <soap:body>-tag is not closed in wsdl file");
				}
			    
			    i++;
			    i++;
			    i++;
			} else {
				/* no <soap:body>-tag found */
		    	throw new IllegalArgumentException("No <soap:body>-tag found");
			}
			
			/* expect output tag */
			if (p_a_wsdlTags.get(i).startsWith("<output")) {
				/* check if output tag is closed */
				if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, i_endOfBinding)) {
					throw new IllegalArgumentException("Invalid wsdl document. <wsdl:output>-tag is not closed in wsdl file");
				}
			    
			    i++;
			} else {
				/* no <wsdl:output>-tag found */
		    	throw new IllegalArgumentException("No <wsdl:output>-tag found");
			}
			
			/* expect body tag */
			if (p_a_wsdlTags.get(i).startsWith("<body")) {
				/* read use tag */
				java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("use=\"([^\"]*)\"");
				java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
				
			    if (o_matcher.find()) {
			    	String s_foo = o_matcher.group(0).substring(5, o_matcher.group(0).length() - 1);
			    	
			    	/* body tag use attribute must be 'literal' */
			    	if (!s_foo.contentEquals("literal")) {
			    		throw new IllegalArgumentException("Invalid wsdl document. <soap:body>-tag has not a use attribute with value 'literal', but '" + s_foo + "'");
			    	}
			    } else {
			    	/* no use attribute found */
			    	throw new IllegalArgumentException("Invalid <soap:body>-tag without a use attribute");
			    }
			    				
				if (p_a_wsdlTags.get(i).endsWith("/>")) { /* body tag was self-closing */
					i--;
				} else if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, i_endOfBinding)) { /* check if body tag is closed */
					throw new IllegalArgumentException("Invalid wsdl document. <soap:body>-tag is not closed in wsdl file");
				}
			    
			    i++;
			    i++;
			    i++;
			} else {
				/* no <soap:body>-tag found */
		    	throw new IllegalArgumentException("No <soap:body>-tag found");
			}
			
			/* add new binding instance with name and portType operations list */
			this.a_bindings.add(new Binding(s_bindingName, a_bindingPortTypeOperations));
		}
		
		/* return xml/xsd-tag position to continue parsing */
		return i_endOfBinding + 1;
	}
	
	/**
	 * parsing servoce tag and all it's port tags
	 * 
	 * @param p_a_wsdlTags						list of following xml/xsd-tags
	 * @param p_i_min							xml/xsd-tag in parameter list where we start
	 * @param p_i_max							xml/xsd-tag in parameter list where we stop
	 * @throws IllegalArgumentException			invalid xml/xsd-tag found within wsdl file
	 */
	private void parseService(java.util.List<String> p_a_wsdlTags, int p_i_min, int p_i_max) throws IllegalArgumentException {
		String s_serviceName;
		String s_serviceDocumentation = null;
		java.util.List<ServicePort> a_servicePorts = new java.util.ArrayList<ServicePort>();
		
		/* expect service tag */
		if (p_a_wsdlTags.get(p_i_min).startsWith("<service")) {
			/* read name tag */
			java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
			java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(p_i_min));
			
		    if (o_matcher.find()) {
		    	/* store service name */
		    	s_serviceName = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
		    } else {
		    	/* no name attribute found */
		    	throw new IllegalArgumentException("Invalid <wsdl:service>-tag without a name attribute");
		    }
		    
		    /* check if service tag is closed */
		    if (!this.lookForEndTag(p_a_wsdlTags.get(p_i_min), p_a_wsdlTags, p_i_min + 1, p_i_max)) {
				throw new IllegalArgumentException("Invalid wsdl document. <wsdl:service>-tag is not closed in wsdl file");
			}
		} else {
			/* no <wsdl:service>-tag found */
	    	throw new IllegalArgumentException("No <wsdl:service>-tag found");
		}
		
		/* iterate all following tags */
		for (int i = p_i_min + 1; i < p_i_max; i++) {
			String s_servicePortName;
			String s_servicePortBinding;
			String s_serviceAddressLocation;
			Binding o_serviceBinding = null;
			
			/* abort for loop if closing service tag is found */
			if (p_a_wsdlTags.get(i).contentEquals("</service>")) {
				break;
			}
			
			/* parse optional documentation tag */
			if (p_a_wsdlTags.get(i).startsWith("<documentation")) {
				s_serviceDocumentation = this.parseDocumentationTag(p_a_wsdlTags.get(i));
		    	i++;
		    }
			
			/* expect port tag */
			if (p_a_wsdlTags.get(i).startsWith("<port")) {
				/* read name tag */
				java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("name=\"([^\"]*)\"");
				java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
				
			    if (o_matcher.find()) {
			    	/* store service port name */
			    	s_servicePortName = o_matcher.group(0).substring(6, o_matcher.group(0).length() - 1);
			    } else {
			    	/* no name attribute found */
			    	throw new IllegalArgumentException("Invalid <wsdl:port>-tag without a name attribute");
			    }
			    
			    /* read binding tag */
				o_regex = java.util.regex.Pattern.compile("binding=\"([^\"]*)\"");
				o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
				
			    if (o_matcher.find()) {
			    	/* store service port binding name */
			    	s_servicePortBinding = o_matcher.group(0).substring(9, o_matcher.group(0).length() - 1);
			    	
			    	/* check our binding list if we find service port binding name there */
			    	for (Binding o_binding : this.a_bindings) {
			    		if (o_binding.getName().contentEquals(s_servicePortBinding)) {
			    			o_serviceBinding = o_binding;
			    			break;
			    		}
			    	}
			    	
			    	/* service port binding not found, although we have parsed binding part */
			    	if (o_serviceBinding == null) {
			    		throw new IllegalArgumentException("<wsdl_port>-tag has unknown binding value '" + s_servicePortBinding + "' within wsdl document");
			    	}
			    } else {
			    	/* no binding attribute found */
			    	throw new IllegalArgumentException("Invalid <wsdl:port>-tag without a binding attribute");
			    }
			    
			    /* check if port tag is closed */
			    if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, p_i_max)) {
					throw new IllegalArgumentException("Invalid wsdl document. <wsdl:port>-tag is not closed in wsdl file");
				}
			    
			    i++;
			} else {
				/* no <wsdl:port>-tag found */
		    	throw new IllegalArgumentException("No <wsdl:port>-tag found");
			}
			
			/* expect address tag */
			if (p_a_wsdlTags.get(i).startsWith("<address")) {
				/* read location tag */
				java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("location=\"([^\"]*)\"");
				java.util.regex.Matcher o_matcher = o_regex.matcher(p_a_wsdlTags.get(i));
				
			    if (o_matcher.find()) {
			    	/* store service address location */
			    	s_serviceAddressLocation = o_matcher.group(0).substring(10, o_matcher.group(0).length() - 1);
			    } else {
			    	/* no location attribute found */
			    	throw new IllegalArgumentException("Invalid <soap:address>-tag without a location attribute");
			    }
			    
			    if (p_a_wsdlTags.get(i).endsWith("/>")) { /* address tag was self-closing */
			    	i--;
			    } else if (!this.lookForEndTag(p_a_wsdlTags.get(i), p_a_wsdlTags, i + 1, p_i_max)) { /* check if address tag is closed */
					throw new IllegalArgumentException("Invalid wsdl document. <soap:address>-tag is not closed in wsdl file");
				}
			    
			    i++;
			    i++;
			} else {
				/* no <soap:address>-tag found */
		    	throw new IllegalArgumentException("No <soap:address>-tag found");
			}
			
			/* add new service port instance to list */
			a_servicePorts.add(new ServicePort(s_servicePortName, s_serviceAddressLocation, o_serviceBinding));
		}
		
		/* store new service instane with documentation(optional) and service port list */
		this.o_service = new Service(s_serviceName, s_serviceDocumentation, a_servicePorts);
	}
	
	/**
	 * check if message name exists in our port type operation list
	 * 
	 * @param p_s_inputMessagePartElementValue			input message name from incoming/outgoing SOAP message
	 * @return 											true - message name exists in port type operation list
	 * @throws NullPointerException						input message part element value parameter is null or empty
	 */
	public boolean containsOperationByInputMessagePartElementValue(String p_s_inputMessagePartElementValue) throws NullPointerException {
		/* check input message part element value parameter */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_inputMessagePartElementValue)) {
			throw new NullPointerException("input message part element value parameter is empty or null");
		}
		
		/* iterate each portType operation */
		for (PortTypeOperation o_operation : this.a_portTypeOperations) {
			/* input message part element value parameter must match with operation input message name */
			if (o_operation.getInputMessage().getPartElement().contentEquals(p_s_inputMessagePartElementValue)) {
				/* found portType operation */
				return true;
			}
		}
		
		/* portType operation not found by input message part element value */
		return false;
	}
	
	/**
	 * return portType operation object with input message part element value from our port type operation list
	 * 
	 * @param p_s_inputMessagePartElementValue				input message part element value from incoming/outgoing SOAP message
	 * @return												port type operation instance
	 * @throws NullPointerException							input message part element value parameter is null or empty
	 */
	public PortTypeOperation getOperationByInputMessagePartElementValue(String p_s_inputMessagePartElementValue) throws NullPointerException {
		/* check input message part element value parameter */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_inputMessagePartElementValue)) {
			throw new NullPointerException("input message part element value parameter is empty or null");
		}
		
		/* iterate each portType operation */
		for (PortTypeOperation o_operation : this.a_portTypeOperations) {
			/* input message part element value parameter must match with operation input message name */
			if (o_operation.getInputMessage().getPartElement().contentEquals(p_s_inputMessagePartElementValue)) {
				/* return found portType operation */
				return o_operation;
			}
		}
		
		/* portType operation not found by input message part element value */
		return null;
	}

	/* Internal Classes */
	
	/**
	 * Encapsulation of wsdl message object and toString method for debug usage
	 */
	public class Message {
		
		/* Fields */
		
		private String s_name;
		private String s_partName;
		private String s_partElement;
		
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
		 * @param p_s_value String
		 */
		public void setName(String p_s_value) {
			this.s_name = p_s_value;
		}
		
		/**
		 * get part name
		 * 
		 * @return String
		 */
		public String getPartName() {
			return this.s_partName;
		}
		
		/**
		 * set part name
		 * 
		 * @param p_s_value String
		 */
		public void setPartName(String p_s_value) {
			this.s_partName = p_s_value;
		}
		
		/**
		 * get part element
		 * 
		 * @return String
		 */
		public String getPartElement() {
			return this.s_partElement;
		}
		
		/**
		 * set part element
		 * 
		 * @param p_s_value String
		 */
		public void setPartElement(String p_s_value) {
			this.s_partElement = p_s_value;
		}
		
		/* Methods */
		
		/**
		 * message constructor
		 * 
		 * @param p_s_name				name
		 * @param p_s_partElement		part element
		 */
		public Message(String p_s_name, String p_s_partElement) {
			this(p_s_name, p_s_partElement, null);
		}
		
		/**
		 * message constructor
		 * 
		 * @param p_s_name				name
		 * @param p_s_partName			part name
		 * @param p_s_partElement		part element
		 */
		public Message(String p_s_name, String p_s_partName, String p_s_partElement) {
			this.setName(p_s_name);
			this.setPartName(p_s_partName);
			this.setPartElement(p_s_partElement);
		}
	
		/**
		 * message instance as string
		 */
		public String toString() {
			String s_foo = "\t\t\t\t";
			
			s_foo += "MessageName = " + this.getName();
			s_foo += " | PartName = " + this.getPartName();
			s_foo += " | PartElement = " + this.getPartElement();
			
			return s_foo;
		}
	}
	
	/**
	 * Encapsulation of wsdl portType operation object and toString method for debug usage
	 */
	public class PortTypeOperation {
		
		/* Fields */
		
		private String s_portTypeName;
		private String s_portTypeOperationName;
		private String s_documentation;
		private Message o_inputMessage;
		private Message o_outputMessage;
		
		/* Properties */
		
		/**
		 * get port type name
		 * 
		 * @return String
		 */
		public String getPortTypeName() {
			return this.s_portTypeName;
		}
		
		/**
		 * set port type name
		 * 
		 * @param p_s_value String
		 */
		public void setPortTypeName(String p_s_value) {
			this.s_portTypeName = p_s_value;
		}
		
		/**
		 * get port type operation name
		 * 
		 * @return String
		 */
		public String getPortTypeOperationName() {
			return this.s_portTypeOperationName;
		}
		
		/**
		 * set port type operation name
		 * 
		 * @param p_s_value String
		 */
		public void setPortTypeOperationName(String p_s_value) {
			this.s_portTypeOperationName = p_s_value;
		}
		
		/**
		 * get documentation
		 * 
		 * @return String
		 */
		public String getDocumentation() {
			return this.s_documentation;
		}
		
		/**
		 * set documentation
		 * 
		 * @param p_s_value String
		 */
		public void setDocumentation(String p_s_value) {
			this.s_documentation = p_s_value;
		}
		
		/**
		 * get input message
		 * 
		 * @return Message
		 */
		public Message getInputMessage() {
			return this.o_inputMessage;
		}
		
		/**
		 * set input message
		 * 
		 * @param p_o_value Message
		 */
		public void setInputMessage(Message p_o_value) {
			this.o_inputMessage = p_o_value;
		}
		
		/**
		 * get output message
		 * 
		 * @return Message
		 */
		public Message getOutputMessage() {
			return this.o_outputMessage;
		}
		
		/**
		 * set output message
		 * 
		 * @param p_o_value Message
		 */
		public void setOutputMessage(Message p_o_value) {
			this.o_outputMessage = p_o_value;
		}
		
		/* Methods */
		
		/**
		 * port type operation constructor
		 * 
		 * @param p_s_portTypeName				port type name
		 * @param p_s_portTypeOperationName		port type operation name
		 * @param p_o_inputMessage				input message
		 * @param p_o_outputMessage				output message
		 */
		public PortTypeOperation(String p_s_portTypeName, String p_s_portTypeOperationName, Message p_o_inputMessage, Message p_o_outputMessage) {
			this(p_s_portTypeName, p_s_portTypeOperationName, p_o_inputMessage, p_o_outputMessage, null);
		}
		
		/**
		 * port type operation constructor
		 * 
		 * @param p_s_portTypeName				port type name
		 * @param p_s_portTypeOperationName		port type operation name
		 * @param p_o_inputMessage				input message
		 * @param p_o_outputMessage				output message
		 * @param p_s_documentation				documentation
		 */
		public PortTypeOperation(String p_s_portTypeName, String p_s_portTypeOperationName, Message p_o_inputMessage, Message p_o_outputMessage, String p_s_documentation) {
			this.setPortTypeName(p_s_portTypeName);
			this.setPortTypeOperationName(p_s_portTypeOperationName);
			this.setInputMessage(p_o_inputMessage);
			this.setOutputMessage(p_o_outputMessage);
			this.setDocumentation(p_s_documentation);
		}
	
		/**
		 * port type operation instance as string
		 */
		public String toString() {
			String s_foo = "\t\t\t";
			
			s_foo += "PortTypeName = " + this.getPortTypeName();
			s_foo += " | PortTypeOperationName = " + this.getPortTypeOperationName();
			s_foo += " | Documentation = " + this.getDocumentation();
			s_foo += " | InputMessage = [" + net.forestany.forestj.lib.io.File.NEWLINE;
			s_foo += this.o_inputMessage.toString();
			s_foo += net.forestany.forestj.lib.io.File.NEWLINE + "\t\t\t" + "]";
			s_foo += " | OutputMessage = [" + net.forestany.forestj.lib.io.File.NEWLINE;
			s_foo += this.o_outputMessage.toString();
			s_foo += net.forestany.forestj.lib.io.File.NEWLINE + "\t\t\t" + "]" + net.forestany.forestj.lib.io.File.NEWLINE;
			
			return s_foo;
		}
	}

	/**
	 * Encapsulation of wsdl binding object and toString method for debug usage
	 */
	public class Binding {
		
		/* Fields */
		
		private String s_name;
		private java.util.List<PortTypeOperation> a_operations;
		
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
		 * @param p_s_value String
		 */
		public void setName(String p_s_value) {
			this.s_name = p_s_value;
		}
		
		/**
		 * get operations
		 * 
		 * @return java.util.List&lt;PortTypeOperation&gt;
		 */
		public java.util.List<PortTypeOperation> getOperations() {
			return this.a_operations;
		}
		
		/**
		 * set operations
		 * 
		 * @param p_a_value java.util.List&lt;PortTypeOperation&gt;
		 */
		public void setOperations(java.util.List<PortTypeOperation> p_a_value) {
			this.a_operations = p_a_value;
		}
		
		/* Methods */
		
		/**
		 * bindung constructor
		 * 
		 * @param p_s_name				name
		 * @param p_a_operations		port type operation list
		 */
		public Binding(String p_s_name, java.util.List<PortTypeOperation> p_a_operations) {
			this.setName(p_s_name);
			this.setOperations(p_a_operations);
		}
		
		/**
		 * binding instance as string
		 */
		public String toString() {
			String s_foo = "\t\t";
			
			s_foo += "BindingName = " + this.getName();
			s_foo += " | PortTypeOperations = [" + net.forestany.forestj.lib.io.File.NEWLINE;
			
			for (PortTypeOperation o_foo : this.a_operations) {
				s_foo += o_foo.toString();
			}
			
			s_foo += net.forestany.forestj.lib.io.File.NEWLINE + "\t\t" + "]";
			
			return s_foo;
		}
	}

	/**
	 * Encapsulation of wsdl service object and toString method for debug usage
	 */
	public class Service {
		
		/* Fields */
		
		private String s_name;
		private String s_documentation;
		private java.util.List<ServicePort> a_servicePorts;
		
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
		 * @param p_s_value String
		 */
		public void setName(String p_s_value) {
			this.s_name = p_s_value;
		}
		
		/**
		 * get documentation
		 * 
		 * @return String
		 */
		public String getDocumentation() {
			return this.s_documentation;
		}
		
		/**
		 * set documentation
		 * 
		 * @param p_s_value String
		 */
		public void setDocumentation(String p_s_value) {
			this.s_documentation = p_s_value;
		}
		
		/**
		 * get service ports
		 * 
		 * @return java.util.List&lt;ServicePort&gt;
		 */
		public java.util.List<ServicePort> getServicePorts() {
			return this.a_servicePorts;
		}
		
		/**
		 * set service ports
		 * 
		 * @param p_a_value java.util.List&lt;ServicePort&gt;
		 */
		public void setServicePorts(java.util.List<ServicePort> p_a_value) {
			this.a_servicePorts = p_a_value;
		}
		
		/* Methods */
		
		/**
		 * service constructor
		 * 
		 * @param p_s_name					name
		 * @param p_s_documentation			documentation
		 * @param p_a_servicePorts			service port list
		 */
		public Service(String p_s_name, String p_s_documentation, java.util.List<ServicePort> p_a_servicePorts) {
			this.setName(p_s_name);
			this.setDocumentation(p_s_documentation);
			this.setServicePorts(p_a_servicePorts);
		}
	
		/**
		 * service instance as string
		 */
		public String toString() {
			String s_foo = "";
			
			s_foo += "ServiceName = " + this.getName();
			s_foo += " | Documentation = " + this.getDocumentation();
			s_foo += " | ServicePorts = [" + net.forestany.forestj.lib.io.File.NEWLINE;
			
			for (ServicePort o_foo : this.a_servicePorts) {
				s_foo += o_foo.toString() + net.forestany.forestj.lib.io.File.NEWLINE;
			}
			
			s_foo += net.forestany.forestj.lib.io.File.NEWLINE + "]";
			
			return s_foo;
		}
	}
	
	/**
	 * Encapsulation of wsdl service port object and toString method for debug usage
	 */
	public class ServicePort {
		
		/* Fields */
		
		private String s_name;
		private String s_addressLocation;
		private Binding o_binding;
		
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
		 * @param p_s_value String
		 */
		public void setName(String p_s_value) {
			this.s_name = p_s_value;
		}
		
		/**
		 * get address location
		 * 
		 * @return String
		 */
		public String getAddressLocation() {
			return this.s_addressLocation;
		}
		
		/**
		 * set address location
		 * 
		 * @param p_s_value String
		 */
		public void setAddressLocation(String p_s_value) {
			this.s_addressLocation = p_s_value;
		}
		
		/**
		 * get binding
		 * 
		 * @return Binding
		 */
		public Binding getBinding() {
			return this.o_binding;
		}
		
		/**
		 * set binding
		 * 
		 * @param p_o_value Binding
		 */
		public void setBinding(Binding p_o_value) {
			this.o_binding = p_o_value;
		}
		
		/* Methods */
		
		/**
		 * service port constructor
		 * 
		 * @param p_s_name					name
		 * @param p_s_addressLocation		address location
		 * @param p_o_binding				binding instance
		 */
		public ServicePort(String p_s_name, String p_s_addressLocation, Binding p_o_binding) {
			this.setName(p_s_name);
			this.setAddressLocation(p_s_addressLocation);
			this.setBinding(p_o_binding);
		}
	
		/**
		 * service port instance as string
		 */
		public String toString() {
			String s_foo = "\t";
			
			s_foo += "ServicePortName = " + this.getName();
			s_foo += " | AddressLocation = " + this.getAddressLocation();
			s_foo += " | Binding = [" + net.forestany.forestj.lib.io.File.NEWLINE + this.o_binding.toString() + net.forestany.forestj.lib.io.File.NEWLINE + "\t" + "]";
			
			return s_foo;
		}
	}
}
