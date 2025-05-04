package net.forestany.forestj.lib.test.nettest.sock.https;

/**
 * class to test ForestREST
 */
public class TestREST extends net.forestany.forestj.lib.net.https.rest.ForestREST {
	
	/* Fields */
	
	private java.util.List<Person> a_persons = new java.util.ArrayList<Person>();
	private java.util.List<Message> a_messages = new java.util.ArrayList<Message>();
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * method to test ForestREST
	 */
	public TestREST() {
		this.a_persons.add( new Person(1, 643532, "John Smith", 32, "New York", "US") );
		this.a_persons.add( new Person(2, 284255, "Elizabeth Miller", 21, "Hamburg", "DE") );
		this.a_persons.add( new Person(3, 116974, "Jennifer Garcia", 48, "London", "UK") );
		this.a_persons.add( new Person(4, 295556, "Jakub Kowalski", 39, "Warsaw", "PL") );
		
		this.a_messages.add( new Message(1, 1, 4, "Subject #1", "Message #1") );
		this.a_messages.add( new Message(1, 4, 3, "Subject #2", "Message #2") );
		this.a_messages.add( new Message(1, 2, 1, "Subject #3", "Message #3") );
		this.a_messages.add( new Message(2, 4, 1, "Subject #4", "Message #4") );
		this.a_messages.add( new Message(2, 2, 4, "Subject #5", "Message #5") );
		this.a_messages.add( new Message(2, 2, 3, "Subject #6", "Message #6") );
		this.a_messages.add( new Message(3, 4, 3, "Subject #7", "Message #7") );
		this.a_messages.add( new Message(1, 3, 2, "Subject #8", "Message #8") );
	}
	
	/**
	 * method to test ForestREST GET
	 */
	@Override
	public String handleGET() throws Exception {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.getSeed().getRequestHeader().getPath())) {
			if (this.getSeed().getRequestHeader().getFile().contentEquals("persons")) {
				String s_foo = "";
				
				for (Person o_person: this.a_persons) {
					if (this.SkipPersonRecord(o_person)) {
						continue;
					}
					
					s_foo += o_person.toString() + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
				}
				
				if (s_foo.length() >= net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK.length()) {
					s_foo = s_foo.substring(0, s_foo.length() - net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK.length());
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(s_foo)) {
					return "No results.";
				}
				
				return s_foo;
			} else if (this.getSeed().getRequestHeader().getFile().contentEquals("messages")) {
				String s_foo = "";
				
				for (Message o_message: this.a_messages) {
					if (this.SkipMessageRecord(o_message)) {
						continue;
					}
					
					s_foo += o_message.toString(this.a_persons) + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
				}
				
				if (s_foo.length() >= net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK.length()) {
					s_foo = s_foo.substring(0, s_foo.length() - net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK.length());
				}
				
				if (net.forestany.forestj.lib.Helper.isStringEmpty(s_foo)) {
					return "No results.";
				}
				
				return s_foo;
			} else {
				return "400;Invalid request.";
			}
		} else {
			if ( (this.getSeed().getRequestHeader().getPath().contentEquals("persons")) && (net.forestany.forestj.lib.Helper.isInteger(this.getSeed().getRequestHeader().getFile())) ) {
				for (Person o_person: this.a_persons) {
					if (o_person.ID == Integer.valueOf(this.getSeed().getRequestHeader().getFile())) {
						return o_person.toString();
					}
				}
				
				return "No results.";
			} else if ( (this.getSeed().getRequestHeader().getPath().contentEquals("messages")) && (net.forestany.forestj.lib.Helper.isInteger(this.getSeed().getRequestHeader().getFile())) ) {
				for (Message o_message: this.a_messages) {
					if (o_message.ID == Integer.valueOf(this.getSeed().getRequestHeader().getFile())) {
						return o_message.toString(this.a_persons);
					}
				}
				
				return "No results.";
			} else if ( (this.getSeed().getRequestHeader().getRequestPath().contains("persons")) && (this.getSeed().getRequestHeader().getRequestPath().contains("messages")) ) {
				String[] a_path = this.getSeed().getRequestHeader().getRequestPath().substring(1).split("/");
				
				if ( (a_path.length == 4) && (a_path[0].contentEquals("persons")) && (net.forestany.forestj.lib.Helper.isInteger(a_path[1])) && (a_path[2].contentEquals("messages")) && (net.forestany.forestj.lib.Helper.isInteger(a_path[3])) ) {
					int i_personID = Integer.valueOf(a_path[1]);
					int i_messageID = Integer.valueOf(a_path[3]);
					
					for (Person o_person: this.a_persons) {
						if (o_person.ID == i_personID) {
							for (Message o_message: this.a_messages) {
								if ( (o_message.To == o_person.ID) && (o_message.ID == i_messageID) ) {
									return o_message.toString(this.a_persons);
								}
							}
						}
					}
					
					return "No results.";
				} else if ( (a_path.length == 3) && (a_path[0].contentEquals("persons")) && (net.forestany.forestj.lib.Helper.isInteger(a_path[1])) && (a_path[2].startsWith("messages")) ) {
					int i_personID = Integer.valueOf(a_path[1]);
					String s_foo = "";
					
					for (Message o_message: this.a_messages) {
						if ( (o_message.To != i_personID) || (this.SkipMessageRecord(o_message)) ) {
							continue;
						}
						
						s_foo += o_message.toString(this.a_persons) + net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK;
					}
					
					if (s_foo.length() >= net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK.length()) {
						s_foo = s_foo.substring(0, s_foo.length() - net.forestany.forestj.lib.net.https.Config.HTTP_LINEBREAK.length());
					}
					
					if (net.forestany.forestj.lib.Helper.isStringEmpty(s_foo)) {
						return "No results.";
					}
					
					return s_foo;
				} else {
					return "400;Invalid request.";
				}
			} else {
				return "400;Invalid request.";
			}
		}
	}
	
	private boolean SkipPersonRecord(Person p_o_person) throws Exception {
		boolean b_skip = false;
		
		for (java.util.Map.Entry<String, String> o_paramPair : this.getSeed().getRequestHeader().getParameters().entrySet()) {
			b_skip = false;
			
			String s_key = o_paramPair.getKey();
			String s_value = o_paramPair.getValue();
			String s_operator = "eq";
			
			if ( (s_key.contains("[")) && (s_key.contains("]")) ) {
				s_operator = s_key.substring(s_key.indexOf("[") + 1, s_key.indexOf("]")).toLowerCase();
				s_key = s_key.substring(0, s_key.indexOf("["));
			}
			
			boolean b_exc = false;
			
			switch (s_operator) {
				case "eq":
					switch (s_key) {
						case "ID":
							if (p_o_person.ID != Integer.valueOf(s_value)) b_skip = true;
							break;
						case "PersonalIdentificationNumber":
							if (p_o_person.PersonalIdentificationNumber != Integer.valueOf(s_value)) b_skip = true;
							break;
						case "Name":
							if (!p_o_person.Name.contentEquals(s_value)) b_skip = true;
							break;
						case "Age":
							if (p_o_person.Age != Integer.valueOf(s_value)) b_skip = true;
							break;
						case "City":
							if (!p_o_person.City.contentEquals(s_value)) b_skip = true;
							break;
						case "Country":
							if (!p_o_person.Country.contentEquals(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "gt":
					switch (s_key) {
						case "ID":
							if (p_o_person.ID <= Integer.valueOf(s_value)) b_skip = true;
							break;
						case "Age":
							if (p_o_person.Age <= Integer.valueOf(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "gte":
					switch (s_key) {
						case "ID":
							if (p_o_person.ID < Integer.valueOf(s_value)) b_skip = true;
							break;
						case "Age":
							if (p_o_person.Age < Integer.valueOf(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "lt":
					switch (s_key) {
						case "ID":
							if (p_o_person.ID >= Integer.valueOf(s_value)) b_skip = true;
							break;
						case "Age":
							if (p_o_person.Age >= Integer.valueOf(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "lte":
					switch (s_key) {
						case "ID":
							if (p_o_person.ID > Integer.valueOf(s_value)) b_skip = true;
							break;
						case "Age":
							if (p_o_person.Age > Integer.valueOf(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "ne":
					switch (s_key) {
						case "ID":
							if (p_o_person.ID == Integer.valueOf(s_value)) b_skip = true;
							break;
						case "PersonalIdentificationNumber":
							if (p_o_person.PersonalIdentificationNumber == Integer.valueOf(s_value)) b_skip = true;
							break;
						case "Name":
							if (p_o_person.Name.contentEquals(s_value)) b_skip = true;
							break;
						case "Age":
							if (p_o_person.Age == Integer.valueOf(s_value)) b_skip = true;
							break;
						case "City":
							if (p_o_person.City.contentEquals(s_value)) b_skip = true;
							break;
						case "Country":
							if (p_o_person.Country.contentEquals(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "starts":
					switch (s_key) {
						case "Name":
							if (!p_o_person.Name.startsWith(s_value)) b_skip = true;
							break;
						case "City":
							if (!p_o_person.City.startsWith(s_value)) b_skip = true;
							break;
						case "Country":
							if (!p_o_person.Country.startsWith(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "ends":
					switch (s_key) {
						case "Name":
							if (!p_o_person.Name.endsWith(s_value)) b_skip = true;
							break;
						case "City":
							if (!p_o_person.City.endsWith(s_value)) b_skip = true;
							break;
						case "Country":
							if (!p_o_person.Country.endsWith(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				default:
					b_exc = true;
					break;
			}
			
			if (b_exc) {
				throw new Exception("Invalid request.");
			}
			
			if (b_skip) {
				break;
			}
		}
		
		return b_skip;
	}
	
	private boolean SkipMessageRecord(Message p_o_message) throws Exception {
		boolean b_skip = false;
		
		for (java.util.Map.Entry<String, String> o_paramPair : this.getSeed().getRequestHeader().getParameters().entrySet()) {
			b_skip = false;
			
			String s_key = o_paramPair.getKey();
			String s_value = o_paramPair.getValue();
			String s_operator = "eq";
			
			if ( (s_key.contains("[")) && (s_key.contains("]")) ) {
				s_operator = s_key.substring(s_key.indexOf("[") + 1, s_key.indexOf("]")).toLowerCase();
				s_key = s_key.substring(0, s_key.indexOf("["));
			}
			
			boolean b_exc = false;
			
			switch (s_operator) {
				case "eq":
					switch (s_key) {
						case "ID":
							if (p_o_message.ID != Integer.valueOf(s_value)) b_skip = true;
							break;
						case "From":
							if (p_o_message.From != Integer.valueOf(s_value)) b_skip = true;
							break;
						case "To":
							if (p_o_message.To != Integer.valueOf(s_value)) b_skip = true;
							break;
						case "Subject":
							if (!p_o_message.Subject.contentEquals(s_value)) b_skip = true;
							break;
						case "Message":
							if (!p_o_message.Message.contentEquals(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "gt":
					switch (s_key) {
						case "ID":
							if (p_o_message.ID <= Integer.valueOf(s_value)) b_skip = true;
							break;
						case "From":
							if (p_o_message.From <= Integer.valueOf(s_value)) b_skip = true;
							break;
						case "To":
							if (p_o_message.To <= Integer.valueOf(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "gte":
					switch (s_key) {
						case "ID":
							if (p_o_message.ID < Integer.valueOf(s_value)) b_skip = true;
							break;
						case "From":
							if (p_o_message.From < Integer.valueOf(s_value)) b_skip = true;
							break;
						case "To":
							if (p_o_message.To < Integer.valueOf(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "lt":
					switch (s_key) {
						case "ID":
							if (p_o_message.ID >= Integer.valueOf(s_value)) b_skip = true;
							break;
						case "From":
							if (p_o_message.From >= Integer.valueOf(s_value)) b_skip = true;
							break;
						case "To":
							if (p_o_message.To >= Integer.valueOf(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "lte":
					switch (s_key) {
						case "ID":
							if (p_o_message.ID > Integer.valueOf(s_value)) b_skip = true;
							break;
						case "From":
							if (p_o_message.From > Integer.valueOf(s_value)) b_skip = true;
							break;
						case "To":
							if (p_o_message.To > Integer.valueOf(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "ne":
					switch (s_key) {
						case "ID":
							if (p_o_message.ID == Integer.valueOf(s_value)) b_skip = true;
							break;
						case "From":
							if (p_o_message.From == Integer.valueOf(s_value)) b_skip = true;
							break;
						case "To":
							if (p_o_message.To == Integer.valueOf(s_value)) b_skip = true;
							break;
						case "Subject":
							if (p_o_message.Subject.contentEquals(s_value)) b_skip = true;
							break;
						case "Message":
							if (p_o_message.Message.contentEquals(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "starts":
					switch (s_key) {
						case "Subject":
							if (!p_o_message.Subject.startsWith(s_value)) b_skip = true;
							break;
						case "Message":
							if (!p_o_message.Message.startsWith(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				case "ends":
					switch (s_key) {
						case "Subject":
							if (!p_o_message.Subject.endsWith(s_value)) b_skip = true;
							break;
						case "Message":
							if (!p_o_message.Message.endsWith(s_value)) b_skip = true;
							break;
						default:
							b_exc = true;
							break;
					}
					break;
				default:
					b_exc = true;
					break;
			}
			
			if (b_exc) {
				throw new Exception("Invalid request.");
			}
			
			if (b_skip) {
				break;
			}
		}
		
		return b_skip;
	}

	/**
	 * method to test ForestREST POST
	 */
	@Override
	public String handlePOST() throws Exception {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(this.getSeed().getRequestHeader().getPath())) {
			if (this.getSeed().getRequestHeader().getFile().contentEquals("persons")) {
				if (this.getSeed().getPostData().size() != 5) {
					return "400;Invalid request. Not enough input data [" + this.getSeed().getPostData().size() + "], need [PIN, Name, Age, City, Country].";
				}
				
				if ( (!this.getSeed().getPostData().containsKey("PIN")) || (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("PIN") )) ) {
					return "400;Invalid request. No input data 'PIN'.";
				}
				
				if ( (!this.getSeed().getPostData().containsKey("Name")) || (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("Name") )) ) {
					return "400;Invalid request. No input data 'Name'.";
				}
				
				if ( (!this.getSeed().getPostData().containsKey("Age")) || (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("Age") )) ) {
					return "400;Invalid request. No input data 'Age'.";
				}
				
				if ( (!this.getSeed().getPostData().containsKey("City")) || (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("City") )) ) {
					return "400;Invalid request. No input data 'City'.";
				}
				
				if ( (!this.getSeed().getPostData().containsKey("Country")) || (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("Country") )) ) {
					return "400;Invalid request. No input data 'Country'.";
				}
				
				if (this.getSeed().getPostData().get("PIN").length() != 6) {
					return "400;Invalid request. Input data 'PIN' must be a 6-digit integer.";
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger( this.getSeed().getPostData().get("PIN") )) {
					return "400;Invalid request. Input data 'PIN' is not an integer.";
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger( this.getSeed().getPostData().get("Age") )) {
					return "400;Invalid request. Input data 'Age' is not an integer.";
				}
				
				if (Integer.valueOf( this.getSeed().getPostData().get("Age") ) < 0) {
					return "400;Invalid request. Input data 'Age' must be a positive integer.";
				}
				
				if (this.getSeed().getPostData().get("Country").length() != 2) {
					return "400;Invalid request. Input data 'Country' must be country code with length of '2'.";
				}
				
				int i_id = this.a_persons.size() + 1;
				
				this.a_persons.add(
					new Person(
						i_id,
						Integer.valueOf(this.getSeed().getPostData().get("PIN")),
						this.getSeed().getPostData().get("Name"),
						Integer.valueOf(this.getSeed().getPostData().get("Age")),
						this.getSeed().getPostData().get("City"),
						this.getSeed().getPostData().get("Country")
					)
				);
				
				return this.getSeed().getRequestHeader().getRequestPath() + "/" + i_id;
			} else {
				return "400;Invalid request.";
			}
		} else {
			String[] a_path = this.getSeed().getRequestHeader().getRequestPath().substring(1).split("/");
			
			if ( (a_path.length == 3) && (a_path[0].contentEquals("persons")) && (net.forestany.forestj.lib.Helper.isInteger(a_path[1])) && (a_path[2].contentEquals("messages")) ) {
				int i_fromId = Integer.valueOf(a_path[1]);
				
				if (this.getSeed().getPostData().size() != 3) {
					return "400;Invalid request. Not enough input data [" + this.getSeed().getPostData().size() + "], need [To, Subject, Message].";
				}
				
				if ( (!this.getSeed().getPostData().containsKey("To")) || (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("To") )) ) {
					return "400;Invalid request. No input data 'To'.";
				}
				
				if ( (!this.getSeed().getPostData().containsKey("Subject")) || (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("Subject") )) ) {
					return "400;Invalid request. No input data 'Subject'.";
				}
				
				if ( (!this.getSeed().getPostData().containsKey("Message")) || (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("Message") )) ) {
					return "400;Invalid request. No input data 'Message'.";
				}
				
				int i_toID = 0; 
				
				for (Person o_person : this.a_persons) {
					if (o_person.Name.contentEquals( this.getSeed().getPostData().get("To") )) {
						i_toID = o_person.ID;
					}
				}
				
				if (i_toID < 1) {
					return "400;Invalid request. Invalid input data 'To'. '" + this.getSeed().getPostData().get("To") + "' not found.";
				}
				
				int i_id = 1;
				
				for (Message o_message : this.a_messages) {
					if (o_message.To == i_toID) {
						i_id++;
					}
				}
				
				this.a_messages.add(
					new Message(
						i_id,
						i_fromId,
						i_toID,
						this.getSeed().getPostData().get("Subject"),
						this.getSeed().getPostData().get("Message")
					)
				);

				return "/persons/" + i_toID + "/messages/" + i_id;
			} else {
				return "400;Invalid request.";
			}
		}
	}

	/**
	 * method to test ForestREST PUT
	 */
	@Override
	public String handlePUT() throws Exception {
		String[] a_path = this.getSeed().getRequestHeader().getRequestPath().substring(1).split("/");
		
		if ( (a_path.length == 2) && (a_path[0].contentEquals("persons")) && (net.forestany.forestj.lib.Helper.isInteger(a_path[1])) ) {
			int i_personID = Integer.valueOf(a_path[1]) - 1;
			Person o_person = this.a_persons.get(i_personID);
			
			if (this.getSeed().getPostData().containsKey("PIN")) {
				if (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("PIN") )) {
					return "400;Invalid request. Input data 'PIN' for change is empty.";
				}
				
				if (this.getSeed().getPostData().get("PIN").length() != 6) {
					return "400;Invalid request. Input data 'PIN' for change must be a 6-digit integer.";
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger( this.getSeed().getPostData().get("PIN") )) {
					return "400;Invalid request. Input data 'PIN' for change is not an integer.";
				}
				
				o_person.PersonalIdentificationNumber = Integer.valueOf(this.getSeed().getPostData().get("PIN"));
			}
			
			if (this.getSeed().getPostData().containsKey("Name")) {
				if (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("Name") )) {
					return "400;Invalid request. Input data 'Name' for change is empty.";
				}
				
				o_person.Name = this.getSeed().getPostData().get("Name");
			}
			
			if (this.getSeed().getPostData().containsKey("Age")) {
				if (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("Age") )) {
					return "400;Invalid request. Input data 'Age' for change is empty.";
				}
				
				if (!net.forestany.forestj.lib.Helper.isInteger( this.getSeed().getPostData().get("Age") )) {
					return "400;Invalid request. Input data 'Age' is not an integer.";
				}
				
				if (Integer.valueOf( this.getSeed().getPostData().get("Age") ) < 0) {
					return "400;Invalid request. Input data 'Age' must be a positive integer.";
				}
				
				o_person.Age = Integer.valueOf(this.getSeed().getPostData().get("Age"));
			}
			
			if (this.getSeed().getPostData().containsKey("City")) {
				if (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("City") )) {
					return "400;Invalid request. Input data 'City' for change is empty.";
				}
				
				o_person.City = this.getSeed().getPostData().get("City");
			}
			
			if (this.getSeed().getPostData().containsKey("Country")) {
				if (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("Country") )) {
					return "400;Invalid request. Input data 'Country' for change is empty.";
				}
				
				if (this.getSeed().getPostData().get("Country").length() != 2) {
					return "400;Invalid request. Input data 'Country' must be country code with length of '2'.";
				}
				
				o_person.Country = this.getSeed().getPostData().get("Country");
			}
			
			this.a_persons.set(i_personID, o_person);
			
			return this.getSeed().getRequestHeader().getRequestPath();
		} else if ( (a_path.length == 4) && (a_path[0].contentEquals("persons")) && (net.forestany.forestj.lib.Helper.isInteger(a_path[1])) && (a_path[2].contentEquals("messages")) && (net.forestany.forestj.lib.Helper.isInteger(a_path[3])) ) {
			int i_personID = Integer.valueOf(a_path[1]);
			int i_messageID = Integer.valueOf(a_path[3]);
			int i_foo = 0;
			Message o_message = null;
			
			for (Message o_foo : this.a_messages) {
				if ( (o_foo.To == i_personID) && (o_foo.ID == i_messageID) ) {
					o_message = o_foo;
					break;
				}
				
				i_foo++;
			}
			
			if (o_message == null) {
				return "400;Invalid request. Message for change not found.";
			}
			
			if (this.getSeed().getPostData().containsKey("Subject")) {
				if (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("Subject") )) {
					return "400;Invalid request. Input data 'Subject' for change is empty.";
				}
				
				o_message.Subject = this.getSeed().getPostData().get("Subject");
			}
			
			if (this.getSeed().getPostData().containsKey("Message")) {
				if (net.forestany.forestj.lib.Helper.isStringEmpty( this.getSeed().getPostData().get("Message") )) {
					return "400;Invalid request. Input data 'Message' for change is empty.";
				}
				
				o_message.Message = this.getSeed().getPostData().get("Message");
			}
			
			this.a_messages.set(i_foo, o_message);
			
			return this.getSeed().getRequestHeader().getRequestPath();
		} else {
			return "400;Invalid request.";
		}
	}

	/**
	 * method to test ForestREST DELETE
	 */
	@Override
	public String handleDELETE() throws Exception {
		String[] a_path = this.getSeed().getRequestHeader().getRequestPath().substring(1).split("/");
		
		if ( (a_path.length == 2) && (a_path[0].contentEquals("persons")) && (net.forestany.forestj.lib.Helper.isInteger(a_path[1])) ) {
			int i_personID = Integer.valueOf(a_path[1]);
			Person o_person = this.a_persons.get(i_personID - 1);
			
			this.a_persons.remove(o_person);
			
			java.util.List<Message> a_foo = new java.util.ArrayList<Message>();
			
			for (Message o_message : this.a_messages) {
				if ( (o_message.From != i_personID) && (o_message.To != i_personID) ) {
					a_foo.add(o_message);
				}
			}
			
			this.a_messages = a_foo;
			
			return "/persons";
		} else if ( (a_path.length == 4) && (a_path[0].contentEquals("persons")) && (net.forestany.forestj.lib.Helper.isInteger(a_path[1])) && (a_path[2].contentEquals("messages")) && (net.forestany.forestj.lib.Helper.isInteger(a_path[3])) ) {
			int i_personID = Integer.valueOf(a_path[1]);
			int i_messageID = Integer.valueOf(a_path[3]);
			Message o_message = null;
			
			for (Message o_foo : this.a_messages) {
				if ( (o_foo.To == i_personID) && (o_foo.ID == i_messageID) ) {
					o_message = o_foo;
					break;
				}
			}
			
			if (o_message == null) {
				return "400;Invalid request. Message for deletion not found.";
			}
			
			this.a_messages.remove(o_message);
			
			return "/persons/" + i_personID + "/messages";
		} else {
			return "400;Invalid request.";
		}
	}
	
	/* Internal Classes */
	
	/**
	 * person class
	 */
	public class Person {
		
		/* Fields */
		
		/**
		 * id
		 */
		public int ID;
		/**
		 * personal identification number
		 */
		public int PersonalIdentificationNumber;
		/**
		 * name
		 */
		public String Name;
		/**
		 * age
		 */
		public int Age;
		/**
		 * city
		 */
		public String City;
		/**
		 * country
		 */
		public String Country;
				
		/* Properties */
		
		/* Methods */
		
		/**
		 * person constructor
		 * @param p_i_ID			id
		 * @param p_i_PIN			personal identification number
		 * @param p_s_name			name
		 * @param p_i_age			age
		 * @param p_s_city			city
		 * @param p_s_country		country
		 */
		public Person(int p_i_ID, int p_i_PIN, String p_s_name, int p_i_age, String p_s_city, String p_s_country) {
			this.ID = p_i_ID;
			this.PersonalIdentificationNumber = p_i_PIN;
			this.Name = p_s_name;
			this.Age = p_i_age;
			this.City = p_s_city;
			this.Country = p_s_country;
		}
		
		/**
		 * person instance as string
		 */
		public String toString() {
			return this.ID + ";" + this.PersonalIdentificationNumber + ";" + this.Name + ";" + this.Age + ";" + this.City + ";" + this.Country;
		}
	}
	
	/**
	 * message class
	 */
	public class Message {
		
		/* Fields */
		
		/**
		 * id
		 */
		public int ID;
		/**
		 * from
		 */
		public int From;
		/**
		 * to
		 */
		public int To;
		/**
		 * subject
		 */
		public String Subject;
		/**
		 * message
		 */
		public String Message;
				
		/* Properties */
		
		/* Methods */
		
		/**
		 * message constructor
		 * 
		 * @param p_i_ID			id
		 * @param p_i_from			from
		 * @param p_i_to			to
		 * @param p_s_subject		subject
		 * @param p_s_message		message
		 */
		public Message(int p_i_ID, int p_i_from, int p_i_to, String p_s_subject, String p_s_message) {
			this.ID = p_i_ID;
			this.From = p_i_from;
			this.To = p_i_to;
			this.Subject = p_s_subject;
			this.Message = p_s_message;
		}
		
		/**
		 * message instance as string
		 */
		public String toString() {
			return this.toString(null);
		}
		
		/**
		 * message instance as string
		 * 
		 * @param p_a_persons		list of person instances
		 * @return String
		 */
		public String toString(java.util.List<Person> p_a_persons) {
			if (p_a_persons == null) {
				return "Person list parameter is null";
			}
			
			String s_from = String.valueOf(this.From);
			String s_to = String.valueOf(this.To);
			
			for (Person o_person : p_a_persons) {
				if (o_person.ID == this.From) {
					s_from = o_person.Name;
				}
				
				if (o_person.ID == this.To) {
					s_to = o_person.Name;
				}
			}
			
			return this.ID + ";" + s_from + ";" + s_to + ";" + this.Subject + ";" + this.Message;
		}
	}
}
