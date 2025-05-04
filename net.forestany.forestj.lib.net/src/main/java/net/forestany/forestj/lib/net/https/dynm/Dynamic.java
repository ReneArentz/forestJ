package net.forestany.forestj.lib.net.https.dynm;

/**
 * Class which uses ForestSeed implementations to create dynamic content. Methods to handle HTTP POST, PUT or DELETE requests and their body data with boundary etc. 
 */
public class Dynamic {
	
	/* Fields */
	
	private net.forestany.forestj.lib.net.https.Seed o_seed;
	private java.util.List<String> a_includeHashes;
	private String s_linebreak;
	
	/* Properties */
	
	private net.forestany.forestj.lib.net.https.Seed getSeed() {
		return this.o_seed;
	}
	
	/* Methods */
	
	/**
	 * Dynamic constructor with seed instance object as parameter, so anything within handling dynamic content has access to all request/response/other resources
	 * 
	 * @param p_o_seed			seed instance object
	 */
 	public Dynamic(net.forestany.forestj.lib.net.https.Seed p_o_seed) {
		this.o_seed = p_o_seed;
		this.a_includeHashes = new java.util.ArrayList<String>();
		this.s_linebreak = null;
	}
	
 	/**
 	 * handling a general POST, PUT or DELETE request with post and file data
 	 * 
 	 * @return		HTTP status code as integer value, in case for invalid request
 	 */
	public int handlePostRequest() {
		if (
			(this.getSeed().getRequestHeader().getContentType().contentEquals(net.forestany.forestj.lib.net.http.PostType.HTMLATTACHMENTS.getContentType()))
			||
			(
				(this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.REST)
				&&
				(!net.forestany.forestj.lib.Helper.isStringEmpty(this.getSeed().getRequestHeader().getBoundary()))
			)
		) {
			/* post type 'multipart/form-data' or mode is REST and boundary value from request header content type is not empty */
			return this.handlePostFileRequest();
		} else if (
			(this.getSeed().getRequestHeader().getContentType().contentEquals(net.forestany.forestj.lib.net.http.PostType.HTML.getContentType()))
			||
			(
				(this.getSeed().getConfig().getMode() == net.forestany.forestj.lib.net.https.Mode.REST)
				&&
				(net.forestany.forestj.lib.Helper.isStringEmpty(this.getSeed().getRequestHeader().getBoundary()))
			)
		) {
			/* post type 'application/x-www-form-urlencoded' or mode is REST and boundary value from request header content type is empty */
			
			/* get request body with configuration incoming encoding and split all post values by '&' */
			String[] a_foo = new String(this.getSeed().getRequestBody(), this.getSeed().getConfig().getInEncoding()).split("&");
			
			/* iterate each post key value pair */
			for (String s_foo : a_foo) {
				/* split key and value by '=' */
				String[] a_foo2 = s_foo.split("=");
				
				if (a_foo2.length == 2) { /* handle key and value */
					/* add key and value and decode both with incoming encoding configuration */
					this.getSeed().getPostData().put(
						java.net.URLDecoder.decode( a_foo2[0], this.getSeed().getConfig().getInEncoding() ),
						java.net.URLDecoder.decode( a_foo2[1], this.getSeed().getConfig().getInEncoding() )
					);
				} else if (a_foo2.length == 1) { /* handle key only */
					/* add key only and decode both with incoming encoding configuration */
					this.getSeed().getPostData().put(
						java.net.URLDecoder.decode( a_foo2[0], this.getSeed().getConfig().getInEncoding() ),
						null
					);
				} else {
					net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: HTTP POST key value pair does not contain a '=' -> '" + s_foo + "'");
					return 400;
				}
			}
		} else {
			net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "501 Not Implemented: POST request with content type '" + this.getSeed().getRequestHeader().getContentType() + "' is not implemented");
			return 501;
		}
		
		/* post data has been processed successfully, returning 200 */
		return 200;
	}
	
	/**
 	 * handling a general POST, PUT or DELETE request with post and file data and boundary structure
 	 * 
 	 * @return		HTTP status code as integer value, in case for invalid request
 	 */
	private int handlePostFileRequest() {
		/* check if boundary value in request header is not empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.getSeed().getRequestHeader().getBoundary())) {
			net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: HTTP POST request with content type '" + net.forestany.forestj.lib.net.http.PostType.HTMLATTACHMENTS.getContentType() + "' has no boundary for post data separation");
			return 400;
		}
		
		/* recognize line break from body by reading byte after first boundary, it is "\n" or '\r\n' */
		this.recognizeLineBreakAfterFirstBoundary(this.getSeed().getRequestBody(), this.getSeed().getConfig().getInEncoding(), this.getSeed().getRequestHeader().getBoundary());
		
		int i_pointer = -10;
		int i_lastPointer = -1;
		
		/* iterate all boundaries within request body data */
		while ((i_pointer = Dynamic.getNextPostDataBoundary(this.getSeed().getRequestBody(), this.getSeed().getConfig().getInEncoding(), i_pointer, this.getSeed().getRequestHeader().getBoundary(), this.s_linebreak)) >= 0) {
			/* continue only if we have found a new boundary which is not at the beginning of the request body */
			if (i_pointer > 0) {
				/* look for next line break */
				int i_nextLineBreak = Dynamic.getNextLineBreak(this.getSeed().getRequestBody(), this.getSeed().getConfig().getInEncoding(), i_lastPointer, false, this.s_linebreak);
				
				/* check if next line break is not at the current position */
				if (i_nextLineBreak > i_pointer) {
					net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: HTTP POST request with file data; next line break is outside of post data");
    				return 400;
				}

				/* read next line */
				String s_expectedContentDisposition = new String(
					Dynamic.readBytePartOfPostData(
						this.getSeed().getRequestBody(),
						i_lastPointer,
						i_nextLineBreak
					),
					this.getSeed().getConfig().getInEncoding()
				);
				
				/* check if line is expected content disposition line with field name and file name */
				if ( net.forestany.forestj.lib.Helper.matchesRegex(s_expectedContentDisposition, "Content-Disposition: form-data; name=\".*\"; filename=\".*\"" ) ) {
					/* read field name and file name out of content disposition line */
					java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("(Content-Disposition: form-data; name=\")(.*)(\"; filename=\")(.*)(\")");
					java.util.regex.Matcher o_matcher = o_regex.matcher(s_expectedContentDisposition);
					String s_fieldName = null;
    		        String s_fileName = null;
    		        
    		        /* store values in variables or return a bad request if they are not found */
					if ( (o_matcher.find()) && (o_matcher.groupCount() == 5) ) {
	    		        s_fieldName = o_matcher.group(2);
	    		        s_fileName = o_matcher.group(4);
	    		    } else {
	    		    	net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: HTTP POST request with file data with invalid content disposition: 'name' and 'filename'");
	    				return 400;
	    		    }
					
					/* read next line */
					String s_expectedContentType = new String(
						Dynamic.readBytePartOfPostData(
							this.getSeed().getRequestBody(),
							i_nextLineBreak + this.s_linebreak.length(),
							Dynamic.getNextLineBreak(
								this.getSeed().getRequestBody(),
								this.getSeed().getConfig().getInEncoding(),
								i_nextLineBreak + this.s_linebreak.length(),
								false,
								this.s_linebreak
							)
						),
						this.getSeed().getConfig().getInEncoding()
					);
					
					/* check if line is expected content type line */
					if ( !net.forestany.forestj.lib.Helper.matchesRegex(s_expectedContentType, "Content-Type:\\s?.*" ) ) {
						net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: HTTP POST request with file data with invalid content type '" + s_expectedContentType + "'");
	    				return 400;
					}
					
					/* pull content type value out of content type line */
					s_expectedContentType = s_expectedContentType.substring(13).trim();
					
					/* look for upcoming two line breaks */
					int i_nextTwoLineBreaks = Dynamic.getNextLineBreak(this.getSeed().getRequestBody(), this.getSeed().getConfig().getInEncoding(), i_lastPointer, true, this.s_linebreak);
	    		    
					/* check if two line breaks are not at the current position */
	    		    if (i_nextLineBreak > i_pointer) {
	    		    	net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: HTTP POST request with file data, could not find two following line breaks");
	    				return 400;
					}
					
	    		    /* store all file data from current position to upcoming two line breaks */
	    		    byte[] a_fileData = Dynamic.readBytePartOfPostData(
	    		    	this.getSeed().getRequestBody(),
	    		    	i_nextTwoLineBreaks + this.s_linebreak.length() + this.s_linebreak.length(),
	    		    	i_pointer
	    		    );
	    		    
	    		    /* check if content type is not forbidden, if we have read file data from request body and that the file name is not empty */
	    		    if (
	    		    	(!net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.containsValue(s_expectedContentType)) && 
	    		    	(a_fileData.length >= 0) && (!net.forestany.forestj.lib.Helper.isStringEmpty(s_fileName))
	    		    ) {
						net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "403 Forbidden: HTTP POST request with file data has unkown or not allowed content type '" + s_expectedContentType + "'");
	    				return 403;
					}
					
	    		    /* add read file data to seed file data list if we have read file data and that the file name is not empty */
	    		    if ( (a_fileData.length >= 0) && (!net.forestany.forestj.lib.Helper.isStringEmpty(s_fileName))) {
	    		    	this.getSeed().getFileData().add(new FileData(s_fieldName, s_fileName, s_expectedContentType, a_fileData));
	    		    }   
	    		} else if ( net.forestany.forestj.lib.Helper.matchesRegex(s_expectedContentDisposition, "Content-Disposition: form-data; name=\".*\"" ) ) { /* check if line is expected content disposition line with field name only */
					/* read field name out of content disposition line */
					java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("(Content-Disposition: form-data; name=\")(.*)(\")");
					java.util.regex.Matcher o_matcher = o_regex.matcher(s_expectedContentDisposition);
					String s_fieldName = null;
					
					/* store field name in variable or return a bad request if it is not found or empty */
	    		    if ( (o_matcher.find()) && (o_matcher.groupCount() == 3) && (!net.forestany.forestj.lib.Helper.isStringEmpty(o_matcher.group(2))) ) {
	    		        s_fieldName = o_matcher.group(2);
	    		    } else {
	    		    	net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: HTTP POST request with file data with invalid content disposition: 'name'");
	    				return 400;
	    		    }
	    		    
	    		    /* look for upcoming two line breaks */
	    		    int i_nextTwoLineBreaks = Dynamic.getNextLineBreak(this.getSeed().getRequestBody(), this.getSeed().getConfig().getInEncoding(), i_lastPointer, true, this.s_linebreak);
	    		    
	    		    /* check if two line breaks are not at the current position */
	    		    if (i_nextLineBreak > i_pointer) {
	    		    	net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: HTTP POST request with file data, could not find two following line breaks");
	    				return 400;
					}
					
	    		    /* read next line as field value */
					String s_fieldValue = new String(
						Dynamic.readBytePartOfPostData(
							this.getSeed().getRequestBody(),
							i_nextTwoLineBreaks + this.s_linebreak.length() + this.s_linebreak.length(),
							i_pointer),
						this.getSeed().getConfig().getInEncoding()
					);
					
					/* add field data to seed post data list if we have read decoded field name and field value is not empty */
					this.getSeed().getPostData().put(
						java.net.URLDecoder.decode( s_fieldName, this.getSeed().getConfig().getInEncoding() ),
						(!net.forestany.forestj.lib.Helper.isStringEmpty(s_fieldValue)) ? java.net.URLDecoder.decode( s_fieldValue, this.getSeed().getConfig().getInEncoding() ) : null
					);
				} else {
					net.forestany.forestj.lib.Global.ilogWarning(this.getSeed().getSalt() + " " + "400 Bad Request: HTTP POST request with file data, invalid content disposition '" + s_expectedContentDisposition + "'");
    				return 400;
				}
			}
			
			/* +2 is the double hyphen */
			if (i_lastPointer < 0) {
				/* set last pointer as current pointer + boundary value length + line break length + double hyphen length */
				i_lastPointer = i_pointer + this.getSeed().getRequestHeader().getBoundary().length() + this.s_linebreak.length() + 2;
			} else {
				/* set last pointer as current pointer + boundary value length + two line breaks length + double hyphen length */
				i_lastPointer = i_pointer + this.getSeed().getRequestHeader().getBoundary().length() + this.s_linebreak.length() + this.s_linebreak.length() + 2;
			}
			
			/* increase current pointer with boundary value length + two line breaks length + double hyphen length */
			i_pointer += this.getSeed().getRequestHeader().getBoundary().length() + this.s_linebreak.length() + this.s_linebreak.length() + 2;
		}
		
		/* post data and file data have been processed successfully, returning 200 */
		return 200;
	}
	
	/**
	 * recognize line break from body by reading byte after first boundary, it is "\n" or '\r\n'
	 * 
	 * @param p_a_bytes					post body data
	 * @param p_o_charset				charset for reading bytes
	 * @param p_s_boundary				looking for boundary value and the next one or two bytes after this
	 * @throws NullPointerException		could not find line break after first boundary value or post body data parameter is null or empty
	 * @throws IllegalArgumentException	invalid pointer value to get line break after first boundary value
	 */
	private void recognizeLineBreakAfterFirstBoundary(byte[] p_a_bytes, java.nio.charset.Charset p_o_charset, String p_s_boundary) throws NullPointerException, IllegalArgumentException {
		String s_foo1 = null;
		String s_foo2 = null;
		String s_hyphen = new String("--".getBytes(p_o_charset));
		
		/* '--' + boundary + '\r\n' or '\n' */
		int i_lengthFirstBoundary = s_hyphen.length() + p_s_boundary.length();
		
		int i_pointer = 0;
		
		do {
			/* read so many bytes until we found '--' + boundary + '\r\n' */
			s_foo1 = new String(Dynamic.readBytePartOfPostData(p_a_bytes, i_pointer, i_pointer + i_lengthFirstBoundary + 2), p_o_charset);
			/* read so many bytes until we found '--' + boundary + '\n' */
			s_foo2 = new String(Dynamic.readBytePartOfPostData(p_a_bytes, i_pointer, i_pointer + i_lengthFirstBoundary + 1), p_o_charset);

			if (s_foo1.contentEquals(s_hyphen + p_s_boundary + new String(net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK.getBytes(p_o_charset)))) {
				/* we found that line break after first boundary is '\r\n' */
				this.s_linebreak = new String(net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK.getBytes(p_o_charset));
				break;
			} else if (s_foo2.contentEquals(s_hyphen + p_s_boundary + new String("\n".getBytes(p_o_charset)))) {
				/* we found that line break after first boundary is "\n" */
				this.s_linebreak = new String("\n".getBytes(p_o_charset));
				break;
			}
			
			/* increase pointer for reading within post body data bytes */
			i_pointer++;
		} while (i_pointer < p_a_bytes.length - i_lengthFirstBoundary + 2); /* until the end of post body data bytes - possible length of first boundary and '\r\n' */

		/* check if line break has been found */
		if ( (this.s_linebreak == null) || (this.s_linebreak.length() < 1) ) {
			throw new NullPointerException("Could not find line break after first boundary '" + p_s_boundary + "' in post body data");
		}
	}
	
	/**
	 * get position of next boundary value within post body data bytes
	 * 
	 * @param p_a_bytes						post body data bytes
	 * @param p_o_charset					charset for reading bytes
	 * @param p_i_pointer					current pointer position within post body data
	 * @param p_s_boundary					boundary value
	 * @param p_s_lineBreak					line break value
	 * @throws NullPointerException			post body data parameter is null or empty
	 * @throws IllegalArgumentException		invalid pointer value to get next boundary value
	 */
	private static int getNextPostDataBoundary(byte[] p_a_bytes, java.nio.charset.Charset p_o_charset, int p_i_pointer, String p_s_boundary, String p_s_lineBreak) throws NullPointerException, IllegalArgumentException {
		String s_foo = null;
		String s_hyphen = new String("--".getBytes(p_o_charset));
		String s_lineBreak = new String(p_s_lineBreak.getBytes(p_o_charset));
		
		/* '--' + boundary + '\r\n' */
		int i_lengthFirstBoundary = s_hyphen.length() + p_s_boundary.length() + s_lineBreak.length();
		/* '\r\n' + '--' + boundary + '\r\n' */
		int i_lengthBoundary = s_lineBreak.length() + s_hyphen.length() + p_s_boundary.length() + s_lineBreak.length();
		/* '\r\n' + '--' + boundary + '--' */
		int i_lengthLastBoundary = s_lineBreak.length() + s_hyphen.length() + p_s_boundary.length() + s_hyphen.length();
		
		/* correct negative pointer value to zero */
		if (p_i_pointer < 0) {
			p_i_pointer = 0;
		}
		
		do {
			/* read so many bytes until we found '--' + boundary + '\r\n' or '\n' */
			s_foo = new String(Dynamic.readBytePartOfPostData(p_a_bytes, p_i_pointer, p_i_pointer + i_lengthFirstBoundary), p_o_charset);

			/* return current pointer if it matches '--' + boundary + '\r\n' or '\n' */
			if (s_foo.contentEquals(s_hyphen + p_s_boundary + s_lineBreak)) {
				return p_i_pointer;
			}
			
			/* check if we still have enough bytes left to read normal boundary */
			if (p_i_pointer <= p_a_bytes.length - i_lengthBoundary) {
				/* read so many bytes until we found '\r\n' or '\n' + '--' + boundary + '\r\n' or '\n' */
				s_foo = new String(Dynamic.readBytePartOfPostData(p_a_bytes, p_i_pointer, p_i_pointer + i_lengthBoundary), p_o_charset);
				
				/* return current pointer if it matches '\r\n' or '\n' + '--' + boundary + '\r\n' or '\n' */
				if (s_foo.contentEquals(s_lineBreak + s_hyphen + p_s_boundary + s_lineBreak)) {
					return p_i_pointer;
				}
			}
			
			/* check if we still have enough bytes left to read last boundary */
			if (p_i_pointer <= p_a_bytes.length - i_lengthLastBoundary) {
				/* read so many bytes until we found '\r\n' or '\n' + '--' + boundary + '--' */
				s_foo = new String(Dynamic.readBytePartOfPostData(p_a_bytes, p_i_pointer, p_i_pointer + i_lengthLastBoundary), p_o_charset);
				
				/* return current pointer if it matches '\r\n' or '\n' + '--' + boundary + '--' */
				if (s_foo.contentEquals(s_lineBreak + s_hyphen + p_s_boundary + s_hyphen)) {
					return p_i_pointer;
				}
			}
			
			/* increase pointer for reading within post body data bytes */
			p_i_pointer++;
		} while (p_i_pointer <= p_a_bytes.length - i_lengthLastBoundary); /* until the end of post body data bytes - possible length of last boundary and '\r\n' and two double hyphen */
		
		/* return -1 if we found nothing at all */
		return -1;
	}
	
	/**
	 * get position of next line break value within a byte data array
	 * 
	 * @param p_a_bytes						byte data array
	 * @param p_o_charset					charset for reading bytes
	 * @param p_i_pointer					current pointer position within byte data array
	 * @param p_b_twoLineBreaks				true - look for two following line breaks, false - look for one line break
	 * @param p_s_lineBreak					line break value
	 * @return int
	 * @throws NullPointerException			byte data array parameter is null or empty
	 * @throws IllegalArgumentException		invalid pointer value to get next line break value
	 */
	public static int getNextLineBreak(byte[] p_a_bytes, java.nio.charset.Charset p_o_charset, int p_i_pointer, boolean p_b_twoLineBreaks, String p_s_lineBreak) throws NullPointerException, IllegalArgumentException {
		String s_foo = null;
		/* assume line break parameter with encoding parameter */
		String s_lineBreak = new String(p_s_lineBreak.getBytes(p_o_charset));
		
		/* extend variable with additional line break */
		if (p_b_twoLineBreaks) {
			s_lineBreak = new String(p_s_lineBreak.getBytes(p_o_charset)) + new String(p_s_lineBreak.getBytes(p_o_charset));
		}
		
		/* correct negative pointer value to zero */
		if (p_i_pointer < 0) {
			p_i_pointer = 0;
		}
		
		do {
			/* read so many bytes until we found '\r\n' or '\n' */
			s_foo = new String(Dynamic.readBytePartOfPostData(p_a_bytes, p_i_pointer, p_i_pointer + s_lineBreak.length()), p_o_charset);
			
			/* we found that line break and return pointer position */
			if (s_foo.contentEquals(s_lineBreak)) {
				return p_i_pointer;
			}
			
			/* increase pointer for reading within byte data array */
			p_i_pointer++;
		} while (p_i_pointer <= p_a_bytes.length - s_lineBreak.length()); /* until the end of byte data array - possible length line break '\r\n' or '\n' */
		
		/* return -1 if we found nothing at all */
		return -1;
	}
	
	/**
	 * get sub part of a byte array and return it
	 * 
	 * @param p_a_bytes						byte data array where we want to cut a sub part
	 * @param p_i_start						start position within byte data array
	 * @param p_i_end						end position within byte data array
	 * @throws NullPointerException			byte data array parameter is null or empty
	 * @throws IllegalArgumentException		invalid pointer value to get sub part within byte data array
	 */
	private static byte[] readBytePartOfPostData(byte[] p_a_bytes, int p_i_start, int p_i_end) throws NullPointerException, IllegalArgumentException {
		/* check if byte data array parameter is not null or empty */
		if ((p_a_bytes == null) || (p_a_bytes.length < 1)) {
			throw new NullPointerException("Byte content is null or empty");
		}
		
		/* correct negative pointer value to zero */
		if (p_i_start < 0) {
			p_i_start = 0;
		}
		
		/* correct negative pointer value to zero */
		if (p_i_end < 0) {
			p_i_end = 0;
		}
		
		/* check if start position is not greater than end position */
		if (p_i_start > p_i_end) {
			throw new IllegalArgumentException("Start pointer '" + p_i_start + "' is greater to end pointer '" + p_i_end + "'");
		}
		
		/* create sub part byte array */
		byte[] a_return = new byte[p_i_end - p_i_start];
		
		/* if end position is greater equal complete length of byte data array, set it to correct end position */
		if (p_i_end >= p_a_bytes.length) {
			p_i_end = p_a_bytes.length;
		}
		
		/* copy bytes to sub part byte array */
		for (int i = p_i_start; i < p_i_end; i++) {
			a_return[i - p_i_start] = p_a_bytes[i];
		}
		
		/* return sub part byte array */
		return a_return;
	}
	
	/**
	 * core method to render and convert dynamic content within html or htm files
	 * 
	 * @return rendered content as string
	 */
	public String renderDynamic() {
		try {
			/* check if we have a ForestSeed instance within our tiny server configuration */
			if (this.getSeed().getConfig().getForestSeed() == null) {
				throw new NullPointerException("Index branch object instance from config is null");
			}
			
			/* add current response body byte array as hash to our internal list, so we do not get an include loop */
			this.a_includeHashes.add( net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", this.getSeed().getResponseBody()) );

			/* fetch content from ForestSeed instance, passing current Seed instance */
			this.getSeed().getConfig().getForestSeed().fetchContent(this.getSeed());
			
			/* render content, by using current response body byte array of html or htm file. our seed buffer will be filled we the correct dynamic content */
			this.renderContent( this.getSeed().getResponseBody() );
			
			/* update response body with our seed buffer */
			this.getSeed().setResponseBody( new String(this.getSeed().getBuffer().toString().getBytes(), this.getSeed().getConfig().getOutEncoding()).getBytes() );
			/* update content length in response header */
			this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
		} catch (Exception o_exc) {
			if (this.getSeed().getConfig().getPrintExceptionStracktrace()) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
			
			/* return exception message for return message */
			return o_exc.getMessage();
		}
		
		/* return 'OK' for return message */
		return "OK";
	}
	
	/**
	 * render content, by using current response body byte array of html or htm file. our seed buffer will be filled we the correct dynamic content
	 * 
	 * @param p_a_responseBody			retrieved response body byte array of html or htm file
	 * @throws Exception				any exception which occurred while render content
	 */
	private void renderContent(byte[] p_a_responseBody) throws Exception {
		String s_start = "<!--#fAny";
		String s_end = "#fAny-->";
		String s_linebreak = this.getSeed().getConfig().getForestSeed().getLineBreak();
		String s_content = new String( p_a_responseBody );
		
		int i_startPointer = -1;
		int i_endPointer = -1;
		int i_pointer = 0;
		int i_line = 1;
		
		/* look for sub string in response body starting with '<!--#fAny' and ending with '#fAny-->' */
		while ( ((i_startPointer = s_content.indexOf(s_start)) >= 0) && ((i_endPointer = s_content.indexOf(s_end)) >= 0) ) {
			/* check if end pointer is not lower than start pointer */
			if (i_endPointer < i_startPointer) {
				throw new Exception("Tags for forestAny dynamic code are in wrong order. '" + s_end + "' read before '" + s_start + "'");
			}
			
			/* if start pointer is not at the beginning at the response body */
			if (i_startPointer != 0) {
				/* assume all of response body content until start pointer to our buffer */
				this.getSeed().getBuffer().append( s_content.substring(0, i_startPointer) );
				/* update our internal line number within response body, in case we have to throw an exception we have this additional information */
				i_line += net.forestany.forestj.lib.Helper.countSubStrings(s_content.substring(0, i_startPointer), s_linebreak);
			}
			
			/* now we have found out the exact start and end point of our dynamic content part within response body, next we can handle and parse this part */
			this.handleForestAnyDynamicCode( s_content.substring(i_startPointer + s_start.length(), i_endPointer), i_line, null );
			
			/* update our internal line number within response body, in case we have to throw an exception we have this additional information */
			i_line += net.forestany.forestj.lib.Helper.countSubStrings(s_content.substring(i_startPointer + s_start.length(), i_endPointer), s_linebreak);
			
			/* increase our pointer to end of dynamic content of current line */
			i_pointer = i_endPointer + s_end.length();
			/* update our content variable, so we can continue with all the content after the dynamic part and look with our while loop for other dynamic parts */
			s_content = s_content.substring(i_pointer);
		}
		
		/* assume rest of response body content to our buffer */
		this.getSeed().getBuffer().append( s_content );
	}
	
	/**
	 * handle and substitute dynamic forestAny content which will be append to our internal buffer
	 * 
	 * @param p_s_forestAny				dynamic content command
	 * @param p_i_line					which line we are within response body
	 * @param p_o_foreachElement		handling dynamic content with a Collection or Map object
	 * @throws Exception				any exception which occurred while handling dynamic content
	 */
	private void handleForestAnyDynamicCode(String p_s_forestAny, int p_i_line, Object p_o_foreachElement) throws Exception {
		String s_linebreak = this.getSeed().getConfig().getForestSeed().getLineBreak();
		String s_forestAny = p_s_forestAny;
		
		/* remove all line breaks within our forestAny command */
		s_forestAny = s_forestAny.replace(s_linebreak, "");
		
		boolean b_withinQuotes = false;
		String s_withinQuotesTemp = "";
		boolean b_withinKeyword = false;
		String s_withinKeywordTemp = "";
		
		/* iterate each character within forestAny command */
		for (int i = 0; i < s_forestAny.length(); i++) {
			/* '"' indicates that we have static content here */
			if (s_forestAny.charAt(i) == '"') {
				if (b_withinQuotes) { /* we already are within double quotes (end), so we can add the static content to our internal buffer */
					this.getSeed().getBuffer().append( s_withinQuotesTemp );
					/* reset variable for static content */
					s_withinQuotesTemp = "";
					/* set double quotes flag to false */
					b_withinQuotes = false;
				} else { /* we are not within double quotes (start) */
					/* set double quotes flag to true */
					b_withinQuotes = true;
				}
				
				/* continue with next character */
				continue;
			}
			
			if (b_withinQuotes) { /* we are within static content, so we can add character to our variable for static content */
				s_withinQuotesTemp += s_forestAny.charAt(i);
			} else { /* handle forestAny command */
				/* skip whitespace or tab */
				if ( (s_forestAny.charAt(i) == ' ') || (s_forestAny.charAt(i) == '\t') ) {
					continue;
				}
				
				/* '%' indicates that we are handling a keyword here */
				if (s_forestAny.charAt(i) == '%') {
					if (b_withinKeyword) { /* we already are within keyword (end), so we can handle they forestAny keyword with our method */
						/* handle forestAny keyword and add the result to our internal buffer */
						Object o_foo = this.handleRecognizedKeyword(s_withinKeywordTemp, false, p_i_line);
						this.getSeed().getBuffer().append( o_foo.toString() );
						
						/* reset variable for keyword */
						s_withinKeywordTemp = "";
						/* set keyword flag to false */
						b_withinKeyword = false;
					} else { /* we are not within keyword (start) */
						/* set keyword flag to true */
						b_withinKeyword = true;
					}
					
					/* continue with next character */
					continue;
				}
				
				if (b_withinKeyword) { /* we are within a keyword, so we can add character to our variable for keyword */
					s_withinKeywordTemp += s_forestAny.charAt(i);
				} else { /* handle keyword */
					/* check if keyword starts with '?' so we have an if construct, INCLUDE or FOREACH */
					if (
						(s_forestAny.charAt(i) == '?') ||
						(
							((i + 7) < s_forestAny.length()) &&
							(
								(s_forestAny.substring(i, i + 7).contentEquals("INCLUDE")) ||
								(s_forestAny.substring(i, i + 7).contentEquals("FOREACH"))
							)
						)
					) {	
						String s_forestDynamicCode = "";
						boolean b_foundSemicolon = false;
						int i_withinCurlyBrackets = 0;
						int j;
						
						/* iterate each character within keyword, to get our dynamic code */
						for (j = i; j < s_forestAny.length(); j++) {
							/* check for semicolon at current position, if we are not within curly brackets */
							if ( (s_forestAny.charAt(j) == ';') && (i_withinCurlyBrackets == 0) ) {
								b_foundSemicolon = true;
								/* add last found semicolon to dynamic code */
								s_forestDynamicCode += ";";
								
								/* handle dynamic code */
								this.handleForestDynamicCodeCommand(s_forestDynamicCode, p_i_line);
								
								/* abort for loop */
								break;
							} else {
								if (s_forestAny.charAt(j) == '{') { /* increase curly brackets counter */
									i_withinCurlyBrackets++;
								} else if (s_forestAny.charAt(j) == '}') { /* decrease curly brackets counter */
									i_withinCurlyBrackets--;
								}
								
								/* add character to dynamic code variable */
								s_forestDynamicCode += s_forestAny.charAt(j);
							}
						}
						
						/* jump after dynamic code */
						i = j;
						
						/* check if semicolon has been used at the end of dynamic code */
						if ( (i == s_forestAny.length()) && (!b_foundSemicolon) ) {
							throw new Exception("Error: Missing ';' within forestAny dynamic code in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
						}
					} else if ( (i > 0) && (s_forestAny.charAt(i - 1) == '[') && (s_forestAny.charAt(i) == ']') ) { /* check if we have empty brackets '[]' */
						/* foreach element must not be null */
						if (p_o_foreachElement == null) {
							throw new Exception("Error: Missing array element within forestAny dynamic code in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
						}
						
						/* foreach element must NOT be a collection or a map */
						if (
							(p_o_foreachElement instanceof java.util.Collection<?>) ||
							(p_o_foreachElement instanceof java.util.Map<?, ?>)
						) {
							throw new Exception("Error: Array element is an iterable object within forestAny dynamic code in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
						}
						
						/* append foreach element as string value to our internal buffer */
						this.getSeed().getBuffer().append( p_o_foreachElement.toString() );
					} else if ( (i < s_forestAny.length() - 1) && (s_forestAny.charAt(i) == '[') && (s_forestAny.charAt(i + 1) != ']') ) { /* check if we have brackets with an index '[42]' */
						/* foreach element must not be null */
						if (p_o_foreachElement == null) {
							throw new Exception("Error: Missing array element within forestAny dynamic code in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
						}
						
						/* foreach element MUST BE a collection or a map */
						if (!(
							(p_o_foreachElement instanceof java.util.Collection<?>) ||
							(p_o_foreachElement instanceof java.util.Map<?, ?>)
						)) {
							throw new Exception("Error: Array element is not an iterable object within forestAny dynamic code in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
						}
						
						String s_index = "";
						boolean b_foundClosingBrackets = false;
						int j;
						
						/* iterate each character within index value */
						for (j = i + 1; j < s_forestAny.length(); j++) {
							if (s_forestAny.charAt(j) == ']') { /* we found our closing bracket, so we can handle the index value now */
								b_foundClosingBrackets = true;
								
								java.util.regex.Pattern o_regex = java.util.regex.Pattern.compile("^([a-zA-Z0-9]*)$");
								java.util.regex.Matcher o_matcher = o_regex.matcher(s_index);
								
								/* index value must be an alphanumeric value */
								if (!o_matcher.find()) {
									throw new Exception("Error: Invalid index '" + s_index + "' found in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
								} else {
									/* our general temporary list is of type Map<string, object> so we cast our foreach element the same way */
									@SuppressWarnings("unchecked")
									java.util.Map<String, Object> a_foo = ( (java.util.Map<String, Object>)p_o_foreachElement );
									
									/* check if our index is within temporary list */
									if (!a_foo.containsKey(s_index)) {
										throw new Exception("Error: Index '[" + s_index + "]' not found in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
									}
									
									/* value behind index must NOT be a collection or a map */
									if (
										(a_foo.get(s_index) instanceof java.util.Collection<?>) ||
										(a_foo.get(s_index) instanceof java.util.Map<?, ?>)
									) {
										throw new Exception("Error: Cannot render iterable object behind index '[" + s_index + "]' within foreach loop in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
									}
									
									/* append value behind index as string value to our internal buffer */
									this.getSeed().getBuffer().append( a_foo.get(s_index).toString() );
								}
								
								break;
							} else {
								/* add character to index variable */
								s_index += s_forestAny.charAt(j);
							}
						}
						
						/* jump after index value */
						i = j;
						
						/* check if closing bracket has been used at the end of index value */
						if ( (i == s_forestAny.length()) && (!b_foundClosingBrackets) ) {
							throw new Exception("Error: Missing ']' to read index for an interable object in a foreach loop within forestAny dynamic code in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
						}
					}
				}
			}
		}
	}

	/**
	 * handle and substitute dynamic forestAny command which will be append to our internal buffer
	 * 
	 * @param p_s_forestAnyDynamicCommand		dynamic content command
	 * @param p_i_line							which line we are within response body
	 * @throws Exception						any exception which occurred while handling dynamic content
	 */
	private void handleForestDynamicCodeCommand(String p_s_forestAnyDynamicCommand, int p_i_line) throws Exception {
		java.util.regex.Pattern o_regex = null;
		java.util.regex.Matcher o_matcher = null;
		
		if (p_s_forestAnyDynamicCommand.startsWith("?")) { /* check for if construct */
			o_regex = java.util.regex.Pattern.compile("^\\?\\s?\\(([a-zA-Z0-9\\[\\]]*)\\)\\s?\\{\\s?([a-zA-Z0-9\\s\\.\\_\\-\\+\\\\/\\[\\]\"\\{\\};:\\(\\)<>\\|%]*)\\s?\\}\\s?:\\s?\\{\\s?(([a-zA-Z0-9\\s\\.\\_\\-\\+\\\\/\\[\\]\"\\{\\};:\\(\\)<>\\|%]*))\\s?\\};$");
			o_matcher = o_regex.matcher(p_s_forestAnyDynamicCommand);
			
			/* check for if and else */
			if (o_matcher.find()) {
	        	/* handle if else */
				this.handleIfConstruct(o_matcher.group(1), o_matcher.group(2), o_matcher.group(3), p_i_line);
	        } else {
	        	o_regex = java.util.regex.Pattern.compile("^\\?\\s?\\(([a-zA-Z0-9\\[\\]]*)\\)\\s?\\{\\s?([a-zA-Z0-9\\s\\.\\_\\-\\+\\\\/\\[\\]\"\\{\\};:\\(\\)<>\\|%]*)\\s?\\};$");
	        	o_matcher = o_regex.matcher(p_s_forestAnyDynamicCommand);
	        	
	        	/* check for if only */
	        	if (o_matcher.find()) {
	        		/* handle if only */
		        	this.handleIfConstruct(o_matcher.group(1), o_matcher.group(2), null, p_i_line);
		        } else {
		        	throw new Exception("Error: Invalid syntax for if construct '" + p_s_forestAnyDynamicCommand + "' within forestAny dynamic code in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
		        }
	        }
		} else if (p_s_forestAnyDynamicCommand.startsWith("FOREACH")) { /* check for FOREACH */
			o_regex = java.util.regex.Pattern.compile("^FOREACH\\s?\\(([a-zA-Z0-9\\[\\]]*)\\)\\s?\\{\\s?([a-zA-Z0-9\\s\\.\\_\\-\\+\\\\/\\[\\]\"\\{\\};:\\(\\)<>\\|%]*)\\s?\\};$");
			o_matcher = o_regex.matcher(p_s_forestAnyDynamicCommand);
			
			/* check for FOREACH */
	        if ( (o_matcher.find()) && (!o_matcher.group(2).contains("FOREACH")) && (!o_matcher.group(2).contains("INCLUDE")) ) {
	        	/* handle FOREACH */
	        	this.handleForEachLoop(o_matcher.group(1), o_matcher.group(2), p_i_line);
	        } else {
	        	throw new Exception("Error: Invalid syntax for foreach loop '" + p_s_forestAnyDynamicCommand + "' within forestAny dynamic code in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
	        }
		} else if (p_s_forestAnyDynamicCommand.startsWith("INCLUDE")) { /* check for INCLUDE */
			o_regex = java.util.regex.Pattern.compile("^INCLUDE\\s([a-zA-Z0-9\\s\\.\\_\\-\\+\\/]*);$");
			o_matcher = o_regex.matcher(p_s_forestAnyDynamicCommand);
			
			/* check for INCLUDE */
	        if (o_matcher.find()) {
	        	/* handle INCLUDE */
	        	this.handleInclude(o_matcher.group(1), p_i_line);
	        } else {
	        	throw new Exception("Error: Invalid syntax for include (invalid path) '" + p_s_forestAnyDynamicCommand + "' within forestAny dynamic code in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
	        }
		}
	}
	
	/**
	 * handle keyword and get it's value from preparing content and return it as object
	 * 
	 * @param p_s_keyword					recognized keyword value		
	 * @param p_b_allowArrayAsReturn		true - allow collection or map as return value
	 * @param p_i_line						which line we are within response body
	 * @throws Exception					any exception which occurred while handling dynamic content
	 */
	private Object handleRecognizedKeyword(String p_s_keyword, boolean p_b_allowArrayAsReturn, int p_i_line) throws Exception {
		String s_keyword = null;
		String s_firstIndex = null;
		String s_secondIndex = null;
		
		java.util.regex.Pattern o_regex = null;
		java.util.regex.Matcher o_matcher = null;
		
		o_regex = java.util.regex.Pattern.compile("^([a-zA-Z0-9]*)(\\[([a-zA-Z0-9]*)\\])(\\[(([a-zA-Z0-9]*))\\])$");
		o_matcher = o_regex.matcher(p_s_keyword);
		
		/* try to recognize keyword, first index and second index */
		if (o_matcher.find()) {
			s_keyword = o_matcher.group(1);
			s_firstIndex = o_matcher.group(3);
			s_secondIndex = o_matcher.group(5);
		} else {
			o_regex = java.util.regex.Pattern.compile("^([a-zA-Z0-9]*)(\\[(([a-zA-Z0-9]*))\\])$");
			o_matcher = o_regex.matcher(p_s_keyword);
			
			/* try to recognize keyword and first index */
			if (o_matcher.find()) {
				s_keyword = o_matcher.group(1);
				s_firstIndex = o_matcher.group(3);
			} else {
				o_regex = java.util.regex.Pattern.compile("^([a-zA-Z0-9]*)$");
				o_matcher = o_regex.matcher(p_s_keyword);
				
				/* try to recognize keyword only */
				if (o_matcher.find()) {
					s_keyword = o_matcher.group(0);
				} else {
					throw new Exception("Error: Invalid keyword '" + p_s_keyword + "' found in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
				}
			}
		}
		
		/* check if key exists with our keyword in out temporary list */
		if (!this.getSeed().getTemp().containsKey(s_keyword)) {
			throw new Exception("Error: Keyword '" + s_keyword + "' not found in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
		}
		
		/* get object value behind keyword */
		Object o_currentObject = this.getSeed().getTemp().get(s_keyword);
		
		/* if we have a first index */
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_firstIndex)) {
			if (o_currentObject instanceof java.util.Map<?, ?>) { /* current object is a map */
				/* get map object with first index */
				o_currentObject = this.getMapObject(o_currentObject, s_firstIndex, p_i_line);
				
				/* if we have a second index */
				if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_secondIndex)) {
					if (o_currentObject instanceof java.util.Map<?, ?>) {
						/* get map object with second index */
						o_currentObject = this.getMapObject(o_currentObject, s_secondIndex, p_i_line);
					} else if (o_currentObject instanceof java.util.List<?>) {
						/* get collection list object with second index */
						o_currentObject = this.getListObject(o_currentObject, s_secondIndex, p_i_line);
					}
				}
			} else if (o_currentObject instanceof java.util.List<?>) { /* current object is a collection list */
				/* get collection list object with first index */
				o_currentObject = this.getListObject(o_currentObject, s_firstIndex, p_i_line);
				
				/* if we have a second index */
				if (!net.forestany.forestj.lib.Helper.isStringEmpty(s_secondIndex)) {
					if (o_currentObject instanceof java.util.Map<?, ?>) {
						/* get map object with second index */
						o_currentObject = this.getMapObject(o_currentObject, s_secondIndex, p_i_line);
					} else if (o_currentObject instanceof java.util.List<?>) {
						/* get collection list object with second index */
						o_currentObject = this.getListObject(o_currentObject, s_secondIndex, p_i_line);
					}
				}
			}
		}
		
		/* check if return value is iterable and throw exception if it is, or we want allow iterable as return value */
		if (
			(!p_b_allowArrayAsReturn) &&
			(
				(o_currentObject instanceof java.util.Collection<?>) ||
				(o_currentObject instanceof java.util.Map<?, ?>)
			)
		) {
			throw new Exception("Error: Cannot render iterable object behind keyword '" + s_keyword + "[" + s_firstIndex + "]" + "[" + s_secondIndex + "]' in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
		}
		
		return o_currentObject;
	}
	
	/**
	 * get object out of map object with key value
	 * 
	 * @param p_o_object		parametr which contains map object
	 * @param p_s_index			key value
	 * @param p_i_line			which line we are within response body
	 * @throws Exception		any exception which occurred while handling dynamic content
	 */
	private Object getMapObject(Object p_o_object, String p_s_index, int p_i_line) throws Exception {
		@SuppressWarnings("unchecked")
		java.util.Map<String, Object> a_map = ( (java.util.Map<String, Object>)p_o_object );
		
		/* check if key exists in our map */
		if (!a_map.containsKey(p_s_index)) {
			throw new Exception("Error: Invalid index 'Map[" + p_s_index + "]' not found in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
		}
		
		/* return map object with key index */
		return a_map.get(p_s_index);
	}
	
	/**
	 * get object out of list collection object with key value
	 * 
	 * @param p_o_object		parametr which contains list collection object
	 * @param p_s_index			key value
	 * @param p_i_line			which line we are within response body
	 * @throws Exception		any exception which occurred while handling dynamic content
	 */
	private Object getListObject(Object p_o_object, String p_s_index, int p_i_line) throws Exception {
		@SuppressWarnings("unchecked")
		java.util.List<Object> a_list = ( (java.util.List<Object>)p_o_object );
		
		/* check if key exists in our list collection */
		if (a_list.size() <= Integer.valueOf(p_s_index)) {
			throw new Exception("Error: Invalid index 'List[" + p_s_index + "]' not found in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
		}
		
		/* return list collection object with key index */
		return a_list.get(Integer.valueOf(p_s_index));
	}

	/**
	 * determine which dynamic code should be executed by looking at boolean values behind keywords
	 * 
	 * @param p_s_expression			expression keyword which will by checked as boolean value
	 * @param p_s_if					execute dynamic code if expression keyword is true
	 * @param p_s_else					execute dynamic code if expression keyword is false, and this parameter is not null
	 * @param p_i_line					which line we are within response body
	 * @throws Exception				any exception which occurred while handling dynamic content
	 */
	private void handleIfConstruct(String p_s_expression, String p_s_if, String p_s_else, int p_i_line) throws Exception {
		/* remove last semicolon from dynamic code for positive expression keyword (if) */
		if (p_s_if.endsWith(";")) {
			p_s_if = p_s_if.substring(0, p_s_if.length() - 1);
		}
		
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_else)) { /* else expression keyword is null or empty */
			/* check if expression keyword is true */
			if (Boolean.valueOf( this.handleRecognizedKeyword(p_s_expression, false, p_i_line).toString() )) {
				/* execute dynamic code for positive expression keyword */
				this.handleForestAnyDynamicCode(p_s_if, p_i_line, null);
			}
		} else { /* else expression keyword is NOT null or empty */
			/* remove last semicolon from dynamic code for negative expression keyword (else) */
			if (p_s_else.endsWith(";")) {
				p_s_else = p_s_else.substring(0, p_s_else.length() - 1);
			}
			
			/* check if expression keyword is true */
			if (Boolean.valueOf( this.handleRecognizedKeyword(p_s_expression, false, p_i_line).toString() )) {
				/* execute dynamic code for positive expression keyword */
				this.handleForestAnyDynamicCode(p_s_if, p_i_line, null);
			} else {
				/* execute dynamic code for negative expression keyword */
				this.handleForestAnyDynamicCode(p_s_else, p_i_line, null);
			}
		}
	}
	
	/**
	 * handle foreach loop as dynamic content and execute dynamic code handling for each element
	 * 
	 * @param p_s_array					expression keyword with collection or map
	 * @param p_s_loopCommand			dynamic code for loop command
	 * @param p_i_line					which line we are within response body
	 * @throws Exception				any exception which occurred while handling dynamic content
	 */
	private void handleForEachLoop(String p_s_array, String p_s_loopCommand, int p_i_line) throws Exception {
		/* handle expression keyword for collection or map object */
		Object o_foo = this.handleRecognizedKeyword(p_s_array, true, p_i_line);
		
		if (o_foo instanceof java.util.Set<?>) { /* we have set object for iteration */
			/* we will always render Sets ordered */
			@SuppressWarnings("unchecked")
			java.util.Collection<Object> a_foo = ((java.util.Set<Object>)o_foo).stream().sorted().collect(java.util.stream.Collectors.toList());
			
			/* iterate each set object */
			for (Object o_element : a_foo) {
				/* handle collection object with loop command */
				this.handleForestAnyDynamicCode(p_s_loopCommand, p_i_line, o_element);
			}
		} else if (o_foo instanceof java.util.Collection<?>) { /* we have a collection object for iteration */
			@SuppressWarnings("unchecked")
			java.util.Collection<Object> a_foo = (java.util.Collection<Object>)o_foo;
			
			/* iterate each collection object */
			for (Object o_element : a_foo) {
				/* handle collection object with loop command */
				this.handleForestAnyDynamicCode(p_s_loopCommand, p_i_line, o_element);
			}
		} else if (o_foo instanceof java.util.Map<?, ?>) { /* we have a map object for iteration */
			@SuppressWarnings("unchecked")
			java.util.Map<Object, Object> a_foo = (java.util.Map<Object, Object>)o_foo;
			
			/* iterate each map object */
			for (java.util.Map.Entry<Object, Object> o_element : a_foo.entrySet()) {
				/* handle map object with loop command */
				this.handleForestAnyDynamicCode(p_s_loopCommand, p_i_line, o_element.getValue());
			}
		} else {
			throw new Exception("Error: Array element '" + p_s_array + "' is not a List, Set or Map for foreach loop in '" + this.getSeed().getRequestHeader().getFile() + "' in line " + p_i_line);
		}
	}
	
	/**
	 * handle include for dynamic content
	 * 
	 * @param p_s_includeFilePath		html or htm file which will be included
	 * @param p_i_line					which line we are within response body
	 * @throws Exception				any exception which occurred while handling dynamic content
	 */
	private void handleInclude(String p_s_includeFilePath, int p_i_line) throws Exception {
		/* '..' within include file path is not allowed */
		if (p_s_includeFilePath.contains("..")) {
			throw new Exception("Error: Invalid including resource path: '" + p_s_includeFilePath + "'");
		}
		
		if (p_s_includeFilePath.startsWith("./")) {
			/* remove './' from beginning of file path */
			p_s_includeFilePath = p_s_includeFilePath.substring(2);
		} else if ( (p_s_includeFilePath.startsWith(".")) || (p_s_includeFilePath.startsWith("/")) ) {
			/* remove '.' or '/' from beginning of file path */
			p_s_includeFilePath = p_s_includeFilePath.substring(1);
		}
		
		/* check if file path is not null or empty */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_includeFilePath)) {
			throw new Exception("Error: Resource path is null or empty; line number '" + p_i_line + "'");
		}
		
		String s_path = "";
		String s_file = "";
		
		if (p_s_includeFilePath.contains("/")) { /* we have directory characters */
			/* get path value from file path, and replace all directory characters with system-dependent default name-separator character */
			s_path = p_s_includeFilePath.substring(0, p_s_includeFilePath.lastIndexOf("/")).replace("/", net.forestany.forestj.lib.io.File.DIR);
			/* get file value from file path */
			s_file = p_s_includeFilePath.substring(p_s_includeFilePath.lastIndexOf("/") + 1);
		} else { /* we just have a file name */
			s_file = p_s_includeFilePath;
		}
		
		/* file value must end with '.html' or '.htm' */
		if ( (!s_file.endsWith(".html")) && (!s_file.endsWith(".htm")) ) {
			throw new Exception("Error: Including resource '" + p_s_includeFilePath + "' file extension is not supported; line number '" + p_i_line + "'");
		}
		
		/* create absolute file path on server */
		String s_includeFileAbsolutePath = this.getSeed().getConfig().getRootDirectory() + s_path + net.forestany.forestj.lib.io.File.DIR + s_file;

		/* check if file really exists */
		if (net.forestany.forestj.lib.io.File.exists(s_includeFileAbsolutePath)) {
			/* check if included file does not exceed max. payload */
			if (net.forestany.forestj.lib.io.File.fileLength(s_includeFileAbsolutePath) > this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) {
				throw new Exception("Error: Payload Too Large - File length for include is to long, max. payload is '" + net.forestany.forestj.lib.Helper.formatBytes(this.getSeed().getConfig().getMaxPayload() * 1024 * 1024) + "'; line number '" + p_i_line + "'");
			}
			
			/* read including file as byte array */
			byte[] a_includeContent = net.forestany.forestj.lib.io.File.readAllBytes(s_includeFileAbsolutePath, this.getSeed().getConfig().getOutEncoding());
			/* create hash of byte array */
			String s_hash = net.forestany.forestj.lib.Helper.hashByteArray("SHA-256", a_includeContent);
			
			/* check if hash does not exist in current dynamic instance to prevent include loops */
			if (this.a_includeHashes.contains(s_hash)) {
				throw new Exception("Error: Resource '" + p_s_includeFilePath + "' has already been included by current request, recursive includes are not supported; line number '" + p_i_line + "'");
			} else {
				/* add hash to list for following checks */
				this.a_includeHashes.add(s_hash);
			}
			
			/* save old file name */
			String s_oldFile = this.getSeed().getRequestHeader().getFile();
			/* set new file name from including file */
			this.getSeed().getRequestHeader().setFile(s_file);
			
			/* render including file content */
			this.renderContent(a_includeContent);
			
			/* restore old file name */
			this.getSeed().getRequestHeader().setFile(s_oldFile);
		} else { /* file does not exist */
			throw new Exception("Error: Including resource '" + p_s_includeFilePath + "' not found; line number '" + p_i_line + "'");
		}
	}

	/**
	 * core method to render and execute REST instance with implemented methods
	 * 
	 * @return rendered content as string
	 */
	public String renderREST() {
		try {
			/* check if we have a ForestREST instance within our tiny server configuration */
			if (this.getSeed().getConfig().getForestREST() == null) {
				throw new NullPointerException("REST object instance from config is null");
			}
			
			/* execute REST implementation from ForestREST instance, passing current Seed instance and expecting REST response as string value */
			String s_restResponse = this.getSeed().getConfig().getForestREST().handleREST(this.getSeed());
			
			/* update response body with returning REST string value */
			this.getSeed().setResponseBody( new String(s_restResponse.getBytes(), this.getSeed().getConfig().getOutEncoding()).getBytes() );
			/* update content length in response header */
			this.getSeed().getResponseHeader().setContentLength(this.getSeed().getResponseBody().length);
			
			/* if no response content type has been defined */
			if (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getConfig().getForestREST().getResponseContentType() )) {
				/* we will just use .txt as response content type + outgoing charset encoding value */
				this.getSeed().getResponseHeader().setContentType( net.forestany.forestj.lib.net.https.Config.KNOWN_EXTENSION_LIST.get( ".txt" ) + "; charset=" + this.getSeed().getConfig().getOutEncoding().name() );
			} else {
				/* if we respond with text|json|xml data we are using our outgoing encoding setting */
				if ( this.getSeed().getConfig().getForestREST().getResponseContentType().startsWith("text") || this.getSeed().getConfig().getForestREST().getResponseContentType().contains("json") || this.getSeed().getConfig().getForestREST().getResponseContentType().contains("xml") ) {
					this.getSeed().getResponseHeader().setContentType(this.getSeed().getConfig().getForestREST().getResponseContentType() + "; charset=" + this.getSeed().getConfig().getOutEncoding().name());
				} else { /* otherwise we just use found content type */
					this.getSeed().getResponseHeader().setContentType(this.getSeed().getConfig().getForestREST().getResponseContentType());
				}
			}
			
			/* reset content type for next request */
			this.getSeed().getConfig().getForestREST().setResponseContentTypeByFileExtension(null);
			
			if (s_restResponse.startsWith("400;")) {
				return s_restResponse;
			}
		} catch (Exception o_exc) {
			if (this.getSeed().getConfig().getPrintExceptionStracktrace()) {
				net.forestany.forestj.lib.Global.logException(o_exc);
			}
			
			/* return exception message for return message */
			return o_exc.getMessage();
		}
		
		/* return 'OK' for return message */
		return "OK";
	}
}
