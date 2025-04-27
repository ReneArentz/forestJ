package net.forestany.forestj.lib.net.mail;

/**
 * Class to map an email folder with sub folders as child items and a parent item as Folder class.
 */
public class Folder {

	/* Constants */
	
	/**
	 * ROOT constant
	 */
	public static final String ROOT = "%__root__%";
	/**
	 * INBOX constant
	 */
	public static final String INBOX = "INBOX";
	/**
	 * SENT constant
	 */
	public static final String SENT = "Sent";
	
	/* Fields */
	
	private String s_name;
	private Folder o_parent;
	private java.util.List<Folder> a_children;
	
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
	 * @throws IllegalArgumentException parameter is empty or null
	 */
	public void setName(String p_s_value) throws IllegalArgumentException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("Empty value for name");
		} else {
			/* root mail folder has no name value */
			if (p_s_value.contentEquals(Folder.ROOT)) {
				p_s_value = "";
			}
			
			this.s_name = p_s_value;
		}
	}
	
	/**
	 * get parent folder instance
	 * 
	 * @return net.forestany.forestj.lib.net.mail.Folder
	 */
	public Folder getParent() {
		return this.o_parent;
	}
	
	/**
	 * set parent folder instance
	 * 
	 * @param p_o_value net.forestany.forestj.lib.net.mail.Folder
	 */
	public void setParent(Folder p_o_value) {
		this.o_parent = p_o_value;
	}
	
	/**
	 * get children
	 * 
	 * @return java.util.List&lt;net.forestany.forestj.lib.net.mail.Folder&gt;
	 */
	public java.util.List<Folder> getChildren() {
		return this.a_children;
	}
	
	/**
	 * Overwrite children list
	 * 
	 * @param p_a_value						list of child items for current folder object
	 * @throws IllegalArgumentException		parameter is null or has no items
	 */
	public void setChildren(java.util.List<Folder> p_a_value) throws IllegalArgumentException {
		if ( (p_a_value == null) || (p_a_value.size() < 1) ) {
			throw new IllegalArgumentException("Empty children list parameter");
		} else {
			this.a_children = p_a_value;
		}
	}
	
	/**
	 * add child item as object to current folder object
	 * 
	 * @param p_o_value							children folder object
	 * @throws IllegalArgumentException			children folder object is null
	 */
	public void addChildren(Folder p_o_value) throws IllegalArgumentException {
		if (p_o_value == null) {
			throw new IllegalArgumentException("Empty mail folder parameter");
		} else {
			if (this.a_children == null) { /* instance new children list if it is null */
				this.a_children = new java.util.ArrayList<Folder>();
			}
			
			/* set parameter object parent as this folder object */
			p_o_value.setParent(this);
			/* add parameter object as child item to list */
			this.a_children.add(p_o_value);
		}
	}
	
	/**
	 * add child item as string to current folder object
	 * 
	 * @param p_s_value							children folder name
	 * @throws IllegalArgumentException			children folder name is null or empty or child already exists
	 */
	public void addChildren(String p_s_value) throws IllegalArgumentException {
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_value)) {
			throw new IllegalArgumentException("Empty mail folder name parameter");
		} else {
			if (this.a_children == null) { /* instance new children list if it is null */
				this.a_children = new java.util.ArrayList<Folder>();
			}
			
			/* check if children folder does not already exists with that name */
			if (this.getSubFolder(p_s_value) == null) {
				/* add child as new folder object item to list */
				this.a_children.add(new Folder(p_s_value, this));
			}
		}
	}
	
	/* Methods */
	
	/**
	 * Constructor of mail folder object, no child items and no parent mail folder instance
	 * 
	 * @param p_s_name						name of mail folder as string
	 * @throws IllegalArgumentException		invalid name for mail folder
	 */
	public Folder(String p_s_name) throws IllegalArgumentException {
		this(p_s_name, null, null);
	}
	
	/**
	 * Constructor of mail folder object, no child items
	 * 
	 * @param p_s_name						name of mail folder as string
	 * @param p_o_parent					object instance of parent mail folder
	 * @throws IllegalArgumentException		invalid name for mail folder
	 */
	public Folder(String p_s_name, Folder p_o_parent) throws IllegalArgumentException {
		this(p_s_name, p_o_parent, null);
	}
	
	/**
	 * Constructor of mail folder object
	 * 
	 * @param p_s_name						name of mail folder as string
	 * @param p_o_parent					object instance of parent mail folder
	 * @param p_a_children					list of children items
	 * @throws IllegalArgumentException		invalid name for mail folder or invalid parameter for children
	 */
	public Folder(String p_s_name, Folder p_o_parent, java.util.List<Folder> p_a_children) throws IllegalArgumentException {
		this.a_children = new java.util.ArrayList<Folder>();
		this.setName(p_s_name);
		this.setParent(p_o_parent);
		
		if (p_a_children != null) {
			this.setChildren(p_a_children);
		}
	}

	/**
	 * Search for folder object in children list
	 * 
	 * @param p_s_name						folder name in children list
	 * @return Folder						found Folder instance or null
	 * @throws IllegalArgumentException		parameter name for search is null or empty
	 */
	public Folder getSubFolder(String p_s_name) throws IllegalArgumentException {
		Folder o_return = null;
		
		/* check parameter value */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_name)) {
			throw new IllegalArgumentException("Please enter a name to get a sub folder of '" + this.getName() + "'");
		}
		
												net.forestany.forestj.lib.Global.ilogFinest("iterate each folder object in children list to find sub folder '" + p_s_name + "'");
		
		/* iterate each folder object in children list */
		for (Folder o_folder : this.a_children) {
													if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("check folder object name corresponds to parameter name:\t" + o_folder.getName().toLowerCase() + " == " + p_s_name.toLowerCase());
			
			/* if folder object name corresponds to the name we are looking for */
			if (o_folder.getName().toLowerCase().contentEquals(p_s_name.toLowerCase())) {
				/* set folder object as return value and abort the loop */
				o_return = o_folder;
				break;
			}
		}
		
		return o_return;
	}

	/**
	 * Returns full path from root folder to current mail folder
	 * 	
	 * @return String				full path
	 */
	public String getFullPath() {
		String s_parentPath = "";
		
		/* get parent folder instance */
		Folder o_foo = this.getParent();
		
		/* iterate all parent instances reverse */
		while (o_foo != null) {
			/* add parent instance name to path */
			if (o_foo.getName().length() > 0) {
				s_parentPath = o_foo.getName() + "/" + s_parentPath;
				
														if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("add parent instance name to path:\t" + s_parentPath);
			}
			
			/* while we find a parent instance we will continue the loop */
			o_foo = o_foo.getParent();
		}
		
												if (net.forestany.forestj.lib.Global.isILevel(net.forestany.forestj.lib.Global.MASS)) net.forestany.forestj.lib.Global.ilogMass("return generated path with name of current folder instance:\t" + s_parentPath + this.getName());
		
		/* return generated path with name of current folder instance */
		return s_parentPath + this.getName();
	}

	/**
	 * clear children list
	 */
	public void clearChildren() {
		this.a_children.clear();
	}
}
