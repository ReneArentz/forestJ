package net.forestany.forestj.lib.ai;

/**
 * Class for storing a complete training data set with training, generalization and validation proportion.
 */
public class TrainingDataSet {
	
	/* Fields */
	
	/**
	 * training set
	 */
	private java.util.List<DataEntry> a_trainingSet;
	/**
	 * generalization set
	 */
	private java.util.List<DataEntry> a_generalizationSet;
	/**
	 * validation set
	 */
	private java.util.List<DataEntry> a_validationSet;
	
	/* Properties */
	
	/**
	 * get training set
	 * 
	 * @return java.util.List&lt;DataEntry&gt;
	 */
	public java.util.List<DataEntry> getTrainingSet() {
		return this.a_trainingSet;
	}
	
	/**
	 * get generalization set
	 * 
	 * @return java.util.List&lt;DataEntry&gt;
	 */
	public java.util.List<DataEntry> getGeneralizationSet() {
		return this.a_generalizationSet;
	}
	
	/**
	 * get validation set
	 * 
	 * @return java.util.List&lt;DataEntry&gt;
	 */
	public java.util.List<DataEntry> getValidationSet() {
		return this.a_validationSet;
	}
	
	/* Methods */
	
	/**
	 * constructor, initializing all sets with empty data entry lists
	 */
	public TrainingDataSet() {
		this.a_trainingSet = new java.util.ArrayList<DataEntry>();
		this.a_generalizationSet = new java.util.ArrayList<DataEntry>();
		this.a_validationSet = new java.util.ArrayList<DataEntry>();
	}
}
