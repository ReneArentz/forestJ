package net.forestany.forestj.lib.sql;

import net.forestany.forestj.lib.sqlcore.SqlType;

/**
 * SQL class to generate a join sql clause based on join type and relation objects, called by toString method.
 */
public class Join extends QueryAbstract {
	
	/* Fields */
	
	/**
	 * join type
	 */
	private String s_joinType = "";
	/**
	 * list of relations
	 */
	public java.util.List<Relation> a_relations = new java.util.ArrayList<Relation>();
	
	/* Properties */
	
	/**
	 * set join type, depending if string value matching a valid join type defined in QueryAbstract class
	 * @param p_s_value						string value for join type
	 * @throws IllegalArgumentException		invalid join type found
	 */
	public void setJoinType(String p_s_value) throws IllegalArgumentException {
		boolean b_accept = false;
		
		/* check if p_s_value is a valid sql join type */
		for (int i = 0; i < this.a_joinTypes.length; i++) {
			if (this.a_joinTypes[i] == p_s_value) {
				b_accept = true;
			}
		}
		
		if (b_accept) {
			this.s_joinType = p_s_value;
		} else {
			throw new IllegalArgumentException("Value[" + p_s_value + "] is not in defined list[" + String.join(", ", this.a_joinTypes) + "]");
		}
	}
	
	/* Methods */
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object
	 */
	public Join(Query<?> p_o_query) throws IllegalArgumentException {
		this(p_o_query, "");
	}
	
	/**
	 * constructor will be called with Query object, because all necessary database gateway, sql type and table information are part of Query class
	 * 
	 * @param p_o_query						query object with table and sql type information
	 * @param p_s_joinType					sets join type of join sql object
	 * @throws IllegalArgumentException		invalid database gateway value from query parameter object or invalid join type found
	 */
	public Join(Query<?> p_o_query, String p_s_joinType) throws IllegalArgumentException {
		super(p_o_query);
		
		if (!net.forestany.forestj.lib.Helper.isStringEmpty(p_s_joinType))
			this.setJoinType(p_s_joinType);
	}
	
	/**
	 * create join sql clause as string
	 */
	@Override	
	public String toString() {
		String s_foo = "";
		
		try {
			/* check if we have relation objects for join clause */
			if (this.a_relations.size() == 0) {
				throw new Exception("Relation object list is empty");
			}
			
			/* only select sql type allowed */
			if (this.e_sqlType != SqlType.SELECT) {
				throw new Exception("SqlType must be SELECT, but is '" + this.e_sqlType + "'");		
			}
			
			boolean b_exc = false;
			
			switch (this.e_base) {
				case MARIADB:
				case SQLITE:
				case NOSQLMDB:
					s_foo = this.s_joinType + " " + "`" + this.s_table + "`";
				break;
				case MSSQL:
					s_foo = this.s_joinType + " " + "[" + this.s_table + "]";
				break;
				case PGSQL:
				case ORACLE:
					s_foo = this.s_joinType + " " + "\"" + this.s_table + "\"";
				break;
				default:
					b_exc = true;
				break;
			}
			
			if (b_exc) {
				throw new Exception("BaseGateway[" + this.e_base + "] not implemented");
			}
			
			s_foo += " ON ";
			
			/* add all relation object to join clause */
			for (Relation o_relation : this.a_relations) {
				s_foo += o_relation.toString();
			}
		} catch (Exception o_exc) { /* just set exception as query return, so database interface will have an exception as well */
			s_foo = " >>>>> Join class Exception: [" + o_exc.toString() + "] <<<<< ";
		}
		
		return s_foo;
	}
}
