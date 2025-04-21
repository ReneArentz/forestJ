package net.forestany.forestj.lib.ai;

/**
 * Class which represents a neural network with input, output and hidden neurons.
 * structure should be seen as layers for a feed forward ann.
 */
public class NeuralNetwork {
	
	/* Delegates */
	
	/**
	 * interface delegate definition which can be instanced outside of NeuralNetwork class to post progress anywhere when neural network accuracy will be calculated
	 */
	public interface IDelegate {
		/**
		 * progress method for get accuracy of a neural network
		 * 
		 * @param p_l_dataSet value of dataset
		 * @param p_l_amountDataSet amount of values in dataset
		 * @param p_d_incorrectResults percentage of incorrect results
		 */
		void PostProgress(long p_l_dataSet, long p_l_amountDataSet, double p_d_incorrectResults);
	}
	
	/* Fields */
	
	/**
	 * layers
	 */
	private java.util.List<Integer> a_layers;
	/**
	 * neurons
	 */
	private java.util.List<java.util.List<Double>> a_neurons;
	/**
	 * weights
	 */
	private java.util.List<java.util.List<java.util.List<Double>>> a_weights;
	
	/**
	 * delimiter
	 */
	private String s_delimiter;
	
	/**
	 * rectifier
	 */
	private boolean b_rectifier;
	
	/**
	 * training set quota
	 */
	private Double d_trainingSetQuota;
	/**
	 * generalization set quota
	 */
	private Double d_generalizationSetQuota;
	/**
	 * training data set
	 */
	private TrainingDataSet o_trainingDataSet;
	
	/**
	 * delegate variable
	 */
	private IDelegate itf_delegate;
	
	/* Properties */
	
	/**
	 * get layers
	 * 
	 * @return java.util.List&lt;Integer&gt;
	 */
	public java.util.List<Integer> getLayers() {
		return this.a_layers;
	}
	
	/**
	 * get neurons
	 * 
	 * @return java.util.List&lt;java.util.List&lt;Double&gt;&gt;
	 */
	public java.util.List<java.util.List<Double>> getNeurons() {
		return this.a_neurons;
	}
	
	/**
	 * get weights
	 * 
	 * @return java.util.List&lt;java.util.List&lt;java.util.List&lt;Double&gt;&gt;&gt;
	 */
	public java.util.List<java.util.List<java.util.List<Double>>> getWeights() {
		return this.a_weights;
	}
	
	/**
	 * get delimiter
	 * 
	 * @return String
	 */
	public String getDelimiter() {
		return this.s_delimiter;
	}
	
	/**
	 * set delimiter
	 * 
	 * @param p_s_value delimiter value
	 * @throws IllegalArgumentException must have length of '1'
	 */
	public void setDelimiter(String p_s_value) throws IllegalArgumentException {
		if (p_s_value.length() != 1) {
			throw new IllegalArgumentException("Delimiter must have length(1) - ['" + p_s_value + "' -> length(" + p_s_value.length() + ")]");
		}
		
		this.s_delimiter = p_s_value;
	}
	
	/**
	 * use rectifier activation function flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setUseRectifierActivationFunction(boolean p_b_value) {
		this.b_rectifier = p_b_value;
	}
	
	/**
	 * set training quotas
	 * 
	 * @param p_d_trainingSetQuota training set quota
	 * @param p_d_generalizationSetQuota generalization set quota
	 * @throws IllegalArgumentException sum of both parameters must be between 0.0 and 1.0
	 */
	public void setTrainingQuotas(double p_d_trainingSetQuota, double p_d_generalizationSetQuota) throws IllegalArgumentException {
		double d_sum = p_d_trainingSetQuota + p_d_generalizationSetQuota; 
		
		if (d_sum <= 0.0d) {
			throw new IllegalArgumentException("Sum of training set quota and generalization set quota must be greater than '0.0', but it is '" + d_sum + "'");
		}
		
		if (d_sum > 1.0d) {
			throw new IllegalArgumentException("Sum of training set quota and generalization set quota must be lower equal '1.0', but it is '" + d_sum + "'");
		}
		
		this.d_trainingSetQuota = p_d_trainingSetQuota;
		this.d_generalizationSetQuota = p_d_generalizationSetQuota;
	}
	
	/**
	 * get training data set
	 * 
	 * @return TrainingDataSet
	 */
	public TrainingDataSet getTrainingDataSet() {
		return this.o_trainingDataSet;
	}
	
	/**
	 * set training data set
	 * 
	 * @param p_o_value training data set instance
	 */
	public void setTrainingDataSet(TrainingDataSet p_o_value) {
		this.o_trainingDataSet = p_o_value;
	}
	
	/**
	 * set delegate
	 * 
	 * @param p_itf_delegate delegate instance
	 */
	public void setDelegate(IDelegate p_itf_delegate) {
		this.itf_delegate = p_itf_delegate;
	}
	
	/* Methods */
	
	/**
	 * Constructor of neural network class, creating an ann with amount of input, hidden and output neurons within several layers.
	 * linebreak will be read from system, delimiter will be ';'.
	 * 
	 * @param p_a_layers						list of integers, each value tells us the amount of neurons in a layer - first layer is the input layer and last layer is the output layer
	 * @throws IllegalArgumentException			parameter of constructor have an illegal argument
	 */
	public NeuralNetwork(java.util.List<Integer> p_a_layers) throws IllegalArgumentException {
		this(p_a_layers, ";");
	}
	
	/**
	 * Constructor of neural network class, creating an ann with amount of input, hidden and output neurons within several layers.
	 * linebreak will be read from system, delimiter will be ';'.
	 * 
	 * @param p_a_layers						list of integers, each value tells us the amount of neurons in a layer - first layer is the input layer and last layer is the output layer
	 * @param p_s_delimiter						delimiter which will be used to separate neurons value from each other if we save to a file
	 * 											should not be [0-9] 'e' ',' '.' '+' or '-'
	 * @throws IllegalArgumentException			parameter of constructor have an illegal argument, or value in hidden layer list is not greater equal 1
	 */
	public NeuralNetwork(java.util.List<Integer> p_a_layers, String p_s_delimiter) throws IllegalArgumentException {
		/* check for minimum value for amount of hidden neurons */
		if (p_a_layers.size() < 3) {
			throw new IllegalArgumentException("Parameter for 'layers' must have at least '3' as size, but size is '" + p_a_layers.size() + "'");
		}
		
		/* check delimiter parameter */
		if (net.forestany.forestj.lib.Helper.isStringEmpty(p_s_delimiter)) {
			throw new IllegalArgumentException("Parameter for 'delimiter' is null or empty");
		}
		
		this.a_layers = p_a_layers;
		
		/* set delimiter */
		this.setDelimiter(p_s_delimiter);
		
		/* default: using sigmoid function */
		this.b_rectifier = false;
		
		/* default to training set properties, must be set later */
		this.d_trainingSetQuota = 0.0d;
		this.d_generalizationSetQuota = 0.0d;
		this.o_trainingDataSet = null;
		
		/* initialize neuron array */
		this.a_neurons = new java.util.ArrayList<java.util.List<Double>>(this.a_layers.size());
		
		/* initialize neuron arrays for hidden layers */
		for (int i_foo : this.a_layers) {
			if (i_foo <= 0) {
				throw new IllegalArgumentException("Value of a 'layer' must be at least greater equal '1', but is '" + i_foo + "'");
			}
			
			this.a_neurons.add(new java.util.ArrayList<Double>(i_foo + 1));
		}
		
		for (int i = 0; i < this.a_layers.size(); i++) {
			/* set layer neuron array to zero values */
			for (int j = 0; j < this.a_layers.get(i); j++) {
				this.a_neurons.get(i).add(0.0);
			}
		
			/* add bias neuron in layer array */
			this.a_neurons.get(i).add(-1.0);
		}
		
		/* initialize weight array 1st dimension */
		this.a_weights = new java.util.ArrayList<java.util.List<java.util.List<Double>>>(this.a_layers.size() - 1);
		
		/* we iterate each layer to get to know how many weights we must store and how many weights explicitly are between them */
		for (int i = 0; i < this.a_layers.size() - 1; i++) { /* size - 1, cause there are just size - 1 weight layers between all layers */
			/* initialize weight array 2nd dimension */
			this.a_weights.add(new java.util.ArrayList<java.util.List<Double>>(this.a_layers.get(i) + 1));
			
			/* set neuron(i) between neuron(i+1) weights array to zero values */
			for (int j = 0; j <= this.a_layers.get(i); j++) {
				/* initialize weight array 3rd dimension */
				this.a_weights.get(i).add(new java.util.ArrayList<Double>(this.a_layers.get(i + 1)));
				
				/* add 3rd dimension weights as zero values */
				for (int k = 0; k < this.a_layers.get(i + 1); k++) {
					this.a_weights.get(i).get(j).add(0.0d);
				}
			}
		}
		
		/* initialize weights with random values */
		this.initializeWeights();
	}
	
	/**
	 * This method will initialize all weights with random values
	 */
	public void initializeWeights() {
		/* initialize layer weights array to random values - iterate each hidden layer, but not the last */
		for (int i = 0; i < this.a_layers.size() - 1; i++) { /* size - 1, cause there are just size - 1 weight layers between all layers */
			/* set range for generating random values with 1/sqrt(x), high value x means small range cause we have enough to randomize */
			double d_range = 1 / java.lang.Math.sqrt( Integer.valueOf(this.a_layers.get(i)).doubleValue() );
			
			/* set neuron(i) between neuron(i+1) weights array to random values */
			for (int j = 0; j <= this.a_layers.get(i); j++) {
				/* set weights with random values - ( random(0..99) + 1 / 100 * 2 * range ) - range */
				for (int k = 0; k < this.a_layers.get(i + 1); k++) {
					this.a_weights.get(i).get(j).set(k, ( ( (net.forestany.forestj.lib.Helper.randomDoubleRange(0.0d, 99.0d)) + 1.0d) / 100.0d * 2.0d * d_range ) - d_range );
				}
			}
		}
	}
	
	/**
	 * Activation function for neurons of neural network.
	 * sigmoid function as default, or rectifier function via class property.
	 * 
	 * @param p_d_value				value which will be checked if neuron will fire or not
	 * @return						activation value for neuron
	 */
	private double activationFunction(double p_d_value) {
		if (this.b_rectifier) {
			/* rectifier function */
			return java.lang.Math.max(0.0d, p_d_value);
		} else {
			/* sigmoid function */
			return ( 1.0d / (1.0d + java.lang.Math.exp(-1.0d * p_d_value)) );
		}
	}	
	
	/**
	 * This method tells us if a output value of our neural network has reached it's desired value with the correct accuracy
	 * 
	 * @param p_d_value					output value we want to check
	 * @param p_d_desiredValue			our ideal desired value
	 * @param p_d_desiredAccuracy		our desired accuracy
	 * @return boolean					true - output value has reached desired value, false - output value not near desired value
	 */
	public boolean clampOutput(double p_d_value, double p_d_desiredValue, double p_d_desiredAccuracy) {
		/* create difference between output value and desired value */
		double d_diff = p_d_desiredValue - p_d_value;
		
		/* correct negative values */
		if (d_diff < 0.0d) {
			d_diff *= -1.0d;
		}
		
		/* calculate desired deviation */
		double d_desiredDeviation = p_d_desiredValue - ( p_d_desiredValue * ( p_d_desiredAccuracy / 100.0d ) );
		
		if (p_d_desiredValue == 0.0d) {
			/* calculate desired deviation towards zero */
			d_desiredDeviation = ( 1.0d - ( p_d_desiredAccuracy / 100.0d ));
		}
		
		/* if difference is lower equal desired deviation */
		if (d_diff <= d_desiredDeviation) {
			return true; /* matches expectation */
		} else {
			return false; /* not matches expectation */
		}
	}

	/**
	 * send a pattern of input neuron values through the neural network
	 * 
	 * @param p_a_pattern		input pattern values
	 */
	private void feedForward(java.util.List<Double> p_a_pattern) throws IllegalArgumentException {
		/* check input amount with amount of first layer neurons, -1 because of bias neuron */
		if (p_a_pattern.size() != this.a_neurons.get(0).size() - 1) {
			throw new IllegalArgumentException("Illegal amount of input neurons '" + p_a_pattern.size() + "'. Neural network's first layer has '" + (this.a_neurons.get(0).size() - 1) + "' neurons.");
		}
		
		/* insert input pattern to the network */
		for (int i = 0; i < this.a_layers.get(0); i++) {
			this.a_neurons.get(0).set(i, p_a_pattern.get(i));
		}
		
		/* calculating the values between layers, including bias neuron - iterate each hidden layer */
		for (int i = 1; i < this.a_layers.size(); i++) {
			/* get layer(i + 1) between layer(i) neurons */
			for (int j = 0; j < this.a_layers.get(i); j++) {
				/* delete old value, by setting to zero */
				this.a_neurons.get(i).set(j, 0.0d);
				
				/* calculate weights sum */
				for (int k = 0; k <= this.a_layers.get(i - 1); k++) {
					/* neuron = neuron + ( previous_neruon * weight_between_both ) */
					this.a_neurons.get(i).set(j, this.a_neurons.get(i).get(j) + ( this.a_neurons.get(i - 1).get(k) * this.a_weights.get(i - 1).get(k).get(j) ));
				}
				
				/* determine results by the activation function */
				this.a_neurons.get(i).set(j, activationFunction(this.a_neurons.get(i).get(j)));
			}
		}
	}
	
	/**
	 * Method to determine the current neural network accuracy with an amount of input data and expected result data sets
	 * 
	 * @param p_a_dataSet				amount of data entries with input patterns and expected output patterns
	 * @param p_d_desiredAccuracy		desired accuracy we want to claim with our data entries parameter
	 * @return							return the network accuracy in percentage
	 */
	public double getNetworkAccuracy(java.util.List<DataEntry> p_a_dataSet, double p_d_desiredAccuracy) {
		double d_incorrectResults = 0;
			
		/* iterate each data entry */
		for (int i = 0; i < p_a_dataSet.size(); i++) {	
			/* send data entry pattern through the neural network */
			this.feedForward(p_a_dataSet.get(i).getPattern());

			/* compare neural network output with expected output from data entry */
			for (int j = 0; j < this.a_layers.get(this.a_layers.size() - 1); j++) {
				/* pattern is wrong if calculated output differs from known output */
				if ( ! this.clampOutput(this.a_neurons.get(this.a_layers.size() - 1).get(j), p_a_dataSet.get(i).getTarget().get(j), p_d_desiredAccuracy) ) {
					/* comparison failed, so we increase incorrect result counter and break the for loop */
					d_incorrectResults++;
					break;
				}
			}
			
			/* post progress of calculating neural network accuracy */
			if (this.itf_delegate != null) {
				this.itf_delegate.PostProgress((i + 1), p_a_dataSet.size(), d_incorrectResults);
			}
		}
		
		/* return the network accuracy in percentage with our incorrect result counter and amount of our data entries */
		return 100.0d - (d_incorrectResults/Integer.valueOf(p_a_dataSet.size()).doubleValue() * 100.0d);
	}
	
	/**
	 * send a pattern of input neuron values through the neural network
	 * 
	 * @param p_a_pattern		input pattern values
	 * @return all output neuron values of this neural network
	 */
	public java.util.List<Double> feedForwardPattern(java.util.List<Double> p_a_pattern) {
		/* send pattern through neural network */
		this.feedForward(p_a_pattern);
	
		/* create list of output neurons as our result value */
		java.util.List<Double> a_results = new java.util.ArrayList<Double>(this.a_layers.get(this.a_layers.size() - 1));
		
		/* create a copy of neural network output neurons after the network processed the input pattern */
		for (int i = 0; i < this.a_layers.get(this.a_layers.size() - 1); i++) {
			a_results.add(this.a_neurons.get(this.a_layers.size() - 1).get(i));
		}
		
		/* return list of output neurons */
		return a_results;
	}
	
	/**
	 * Save all weights values of neural network to a file, separated by delimiter property 
	 * 
	 * @param p_s_filename						filePath + file which will contain all weight values
	 * @throws java.io.IOException				could not save weight values to file
	 * @throws java.io.FileNotFoundException 	invalid filePath or fileName
	 */
	public void saveWeights(String p_s_filename) throws java.io.FileNotFoundException, java.io.IOException {
		/* open (new) file */
		net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(p_s_filename, !net.forestany.forestj.lib.io.File.exists(p_s_filename));
		
		/* temp variable for list of weights */
		java.util.List<Double> a_weights = new java.util.ArrayList<Double>();
			
		/* get all weights between layers, iterate each layer */
		for (int i = 0; i < this.a_layers.size() - 1; i++) { /* size - 1, cause there are just size - 1 weight layers between all layers */
			for (int j = 0; j <= this.a_layers.get(i); j++) {
				for (int k = 0; k < this.a_layers.get(i + 1); k++) {
					a_weights.add(this.a_weights.get(i).get(j).get(k));
				}
			}
		}
		
		/* save weights data into file, separated by delimiter property */
		o_file.replaceContent(a_weights.stream().map(String::valueOf).collect(java.util.stream.Collectors.joining(this.s_delimiter)));
	}
	
	/**
	 * Load weights for neural network from a file
	 * 
	 * @param p_s_filename						filePath + file which contains all weight values
	 * @throws java.io.IOException				could not load weight values from file
	 * @throws IllegalStateException			amount of stored weights is not correct
	 */
	public void loadWeights(String p_s_filename) throws java.io.IOException, IllegalStateException {
		/* check if file exists, if not just create a new one with current initialized random weights */
		if (!net.forestany.forestj.lib.io.File.exists(p_s_filename)) {
			this.saveWeights(p_s_filename);
			net.forestany.forestj.lib.Global.logWarning("File[" + p_s_filename + "] does not exist; created file with initialized random weights");
		}
		
		/* open file */
		net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(p_s_filename, false);
		
		/* temp list of weights */
		java.util.List<Double> a_weights = new java.util.ArrayList<Double>();
		
		/* iterate each file line */
		for (int i_line = 1; i_line <= o_file.getFileLines(); i_line++) {
			/* separate stored weight values with delimiter property */
			String[] a_lineValues = o_file.readLine(i_line).split(this.s_delimiter);
			
			/* store each weight value in temp list */
			for (int i = 0; i < a_lineValues.length; i++) {
				a_weights.add(Double.valueOf(a_lineValues[i]));
			}
		}
		
		/* counting all weights */
		int i_expectedAmount = 0;
		
		/* iterate each layer for counting all expected weights */
		for (int i = 0; i < this.a_layers.size() - 1; i++) {
			i_expectedAmount += (this.a_layers.get(i) + 1) * this.a_layers.get(i + 1); 
		}
		
		/* check amount of read weights from file with settings from current neural network */
		if (a_weights.size() != i_expectedAmount) {
			throw new IllegalStateException("Invalid amount of weights[" + a_weights.size() + "]. Expected amount of weights = [" + i_expectedAmount + "]");
		}
		
		int l = 0;
		
		/* load weights from file to layers, iterate each layer */
		for (int i = 0; i < this.a_layers.size() - 1; i++) { /* size - 1, cause there are just size - 1 weight layers between all layers */
			for (int j = 0; j <= this.a_layers.get(i); j++) {
				for (int k = 0; k < this.a_layers.get(i + 1); k++) {
					this.a_weights.get(i).get(j).set(k, a_weights.get(l++));
				}
			}
		}
	}
	
	/**
	 * Loading training data entries from a file, using quotas to split entries as training, generalization and validation entries.
	 * sum of both quotas must be lower equal 1.0.
	 * if the sum of both is lower than 1.0, rest will be used for validation.
	 * 
	 * @param p_s_filename						filePath + file which contains all training data set entries
	 * @throws IllegalArgumentException			wrong quota values
	 * @throws java.io.FileNotFoundException 	invalid filePath or fileName
	 * @throws java.io.IOException 				file could not be read
	 */
	public void loadTrainingDataSetFromFile(String p_s_filename) throws IllegalArgumentException, java.io.FileNotFoundException, java.io.IOException {
		this.loadTrainingDataSetFromFile(p_s_filename, -1.0d, -1.0d);
	}
	
	/**
	 * Loading training data entries from a file, using quotas to split entries as training, generalization and validation entries.
	 * sum of both quotas must be lower equal 1.0.
	 * if the sum of both is lower than 1.0, rest will be used for validation.
	 * 
	 * @param p_s_filename						filePath + file which contains all training data set entries
	 * @param p_d_trainingSetQuota				percentage as double how many entries will be used for training
	 * @param p_d_generalizationSetQuota		percentage as double how many entries will be used for generalization
	 * @throws IllegalArgumentException			wrong quota values or wrong values from file for a data entry
	 * @throws java.io.FileNotFoundException 	invalid filePath or fileName
	 * @throws java.io.IOException 				file could not be read
	 */
	public void loadTrainingDataSetFromFile(String p_s_filename, double p_d_trainingSetQuota, double p_d_generalizationSetQuota) throws IllegalArgumentException, java.io.FileNotFoundException, java.io.IOException {
		java.util.ArrayList<DataEntry> a_data = new java.util.ArrayList<DataEntry>();
		this.o_trainingDataSet = new TrainingDataSet();
		
		/* check if file exists */
		if (!net.forestany.forestj.lib.io.File.exists(p_s_filename)) {
			throw new java.io.FileNotFoundException("File[" + p_s_filename + "] does not exist");
		}
		
		/* use quota parameters if there are not -1.0 */
		if ( (p_d_trainingSetQuota != -1.0d) && (p_d_generalizationSetQuota != -1.0d) ) {
			this.setTrainingQuotas(p_d_trainingSetQuota, p_d_generalizationSetQuota);
		}
		
		/* open file */
		net.forestany.forestj.lib.io.File o_file = new net.forestany.forestj.lib.io.File(p_s_filename, false);
		
		/* iterate each file line */
		for (int i_line = 1; i_line <= o_file.getFileLines(); i_line++) {
			/* separate stored training data set values with delimiter property */
			String[] a_lineValues = o_file.readLine(i_line).split(this.s_delimiter);
			
			/* add file line to array, if amount of elements in line is correct */
			if (a_lineValues.length == ( this.a_layers.get(0) + this.a_layers.get(this.a_layers.size() - 1) )) {
				DataEntry o_dataEntry = new DataEntry();
				
				for (int i = 0; i < ( this.a_layers.get(0) + this.a_layers.get(this.a_layers.size() - 1) ); i++) {
					if (i < this.a_layers.get(0)) {
						o_dataEntry.getPattern().add(Double.valueOf(a_lineValues[i]));
					} else {
						o_dataEntry.getTarget().add(Double.valueOf(a_lineValues[i]));
					}
				}
				
				a_data.add(o_dataEntry);
			} else {
				throw new IllegalArgumentException("Training set line has '" + a_lineValues.length + "' values which is not '" + ( this.a_layers.get(0) + this.a_layers.get(this.a_layers.get(this.a_layers.size() - 1)) ) + "', because our neural network has '" + this.a_layers.get(0) + "' input values and '" + this.a_layers.get(this.a_layers.get(this.a_layers.size() - 1)) + "' output values");
			}
		}
		
		/* shuffle data entries, so training will be more effective */
		java.util.Collections.shuffle(a_data);
		
		/* calculate our training, generalization and validation entry size */
		int i_trainingDataEndIndex = Double.valueOf( java.lang.Math.round( this.d_trainingSetQuota * Integer.valueOf(a_data.size()).doubleValue() ) ).intValue();
		int i_generalizationSize = Double.valueOf( java.lang.Math.round( this.d_generalizationSetQuota * Integer.valueOf(a_data.size()).doubleValue() ) ).intValue();
		
		/* load data entries to training set */
		for (int i = 0; i < i_trainingDataEndIndex; i++) {
			this.o_trainingDataSet.getTrainingSet().add( a_data.get(i) );	
		}
		
		/* load data entries to generalization set */
		for (int i = i_trainingDataEndIndex; i < i_trainingDataEndIndex + i_generalizationSize; i++) {
			this.o_trainingDataSet.getGeneralizationSet().add( a_data.get(i) );	
		}
		
		/* load data entries to validation set */
		for (int i = i_trainingDataEndIndex + i_generalizationSize; i < a_data.size(); i++) {
			this.o_trainingDataSet.getValidationSet().add( a_data.get(i) );	
		}
	}
}
