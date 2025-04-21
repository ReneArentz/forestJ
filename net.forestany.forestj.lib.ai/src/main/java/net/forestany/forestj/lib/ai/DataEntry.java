package net.forestany.forestj.lib.ai;

/**
 * Class for storing training data entries with input patterns and expected target output patterns.
 */
public class DataEntry {
	
	/* Fields */
	
	/**
	 * pattern
	 */
	private java.util.List<Double> a_pattern;
	/**
	 * target
	 */
	private java.util.List<Double> a_target;
	
	/* Properties */
	
	/**
	 * get pattern
	 * 
	 * @return java.util.List&lt;Double&gt;
	 */
	public java.util.List<Double> getPattern() {
		return this.a_pattern;
	}
	
	/**
	 * set pattern
	 * 
	 * @param p_a_value list of double values
	 * @throws IllegalArgumentException parameter is null
	 */
	private void setPattern(java.util.List<Double> p_a_value) throws IllegalArgumentException {
		if (p_a_value == null) {
			throw new IllegalArgumentException("Parameter value for 'pattern' is null");
		}
		
		this.a_pattern = p_a_value;
	}
	
	/**
	 * get target
	 * 
	 * @return java.util.List&lt;Double&gt;
	 */
	public java.util.List<Double> getTarget() {
		return this.a_target;
	}
	
	/**
	 * set target
	 * 
	 * @param p_a_value list of double values
	 * @throws IllegalArgumentException parameter is null
	 */
	private void setTarget(java.util.List<Double> p_a_value) throws IllegalArgumentException {
		if (p_a_value == null) {
			throw new IllegalArgumentException("Parameter value for 'target' is null");
		}
		
		this.a_target = p_a_value;
	}
	
	/* Methods */
	
	/**
	 * constructor, set pattern and target with empty lists
	 * 
	 * @throws IllegalArgumentException parameter is null
	 */
	public DataEntry() throws IllegalArgumentException {
		this.setPattern(new java.util.ArrayList<Double>());
		this.setTarget(new java.util.ArrayList<Double>());
	}
	
	/**
	 * constructor, set pattern and target with parameters
	 * 
	 * @param p_a_pattern list of double values
	 * @param p_a_target list of double values
	 * @throws IllegalArgumentException parameter is null
	 */
	public DataEntry(java.util.List<Double> p_a_pattern, java.util.List<Double> p_a_target) throws IllegalArgumentException {
		this.setPattern(p_a_pattern);
		this.setTarget(p_a_target);
	}
}
