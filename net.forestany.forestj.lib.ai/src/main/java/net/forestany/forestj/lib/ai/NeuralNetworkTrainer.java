package net.forestany.forestj.lib.ai;

/**
 * Trainer class to train a neural network with backpropagation algorithm.
 */
public class NeuralNetworkTrainer {
	
	/* Delegates */
	
	/**
	 * interface delegate definition which can be instanced outside of NeuralNetworkTrainer class to post progress anywhere when a neural network will be trained
	 */
	public interface IDelegate {
		/**
		 * progress method for training a neural network
		 * 
		 * @param p_l_epoch epoch value
		 * @param p_l_maxEpochs max epoch value
		 * @param p_d_desiredAccuracy desired accuracy in percentage
		 * @param p_d_trainingSetAccuracy training set accuracy in percentage
		 * @param p_d_generalizationSetAccuracy generalization set accuracy in percentage
		 * @param p_d_validationSetAccuracy validation set accuracy in percentage
		 */
		void PostProgress(long p_l_epoch, long p_l_maxEpochs, Double p_d_desiredAccuracy, Double p_d_trainingSetAccuracy, Double p_d_generalizationSetAccuracy, Double p_d_validationSetAccuracy);
	}
	
	/* Fields */
	
	/**
	 * neural network
	 */
	private NeuralNetwork o_neuralNetwork;
	
	/**
	 * learning rate
	 */
	private Double d_learningRate;
	/**
	 * momentum
	 */
	private Double d_momentum;
	/**
	 * max epochs
	 */
	private long l_maxEpochs;
	/**
	 * desired accuracy
	 */
	private Double d_desiredAccuracy;
	
	/**
	 * delta list
	 */
	private java.util.List<java.util.List<java.util.List<Double>>> a_delta;
	/**
	 * error gradient list
	 */
	private java.util.List<java.util.List<Double>> a_errorGradients;
	
	/**
	 * training set accuracy
	 */
	private Double d_trainingSetAccuracy;
	/**
	 * generalization set accuracy
	 */
	private Double d_generalizationSetAccuracy;
	/**
	 * validation set accuracy
	 */
	private Double d_validationSetAccuracy;
	
	/**
	 * use batch flag
	 */
	private boolean b_useBatch;
	
	/**
	 * delegate variable
	 */
	private IDelegate itf_delegate;
	
	/* Properties */
	
	/**
	 * get training set accuracy
	 * 
	 * @return Double
	 */
	public Double getTrainingSetAccuracy() {
		return this.d_trainingSetAccuracy;
	}
	
	/**
	 * get generalization set accuracy
	 * 
	 * @return Double
	 */
	public Double getGeneralizationSetAccuracy() {
		return this.d_generalizationSetAccuracy;
	}
	
	/**
	 * get validation set accuracy
	 * 
	 * @return Double
	 */
	public Double getValidationSetAccuracy() {
		return this.d_validationSetAccuracy;
	}
	
	/**
	 * get desired  accuracy
	 * 
	 * @return Double
	 */
	public Double getDesiredAccuracy() {
		return this.d_desiredAccuracy;
	}
	
	/**
	 * get max epochs
	 * 
	 * @return long
	 */
	public long getMaxEpochs() {
		return this.l_maxEpochs;
	}
	
	/**
	 * set learning rate
	 * 
	 * @param p_d_value learning rate value
	 * @throws IllegalArgumentException must be between 0.0 and 1.0
	 */
	public void setLearningRate(Double p_d_value) throws IllegalArgumentException {
		if (p_d_value <= 0.0d) {
			throw new IllegalArgumentException("Learning rate parameter must be greater than 0.0, but it is '" + p_d_value + "'");
		}
		
		if (p_d_value > 1.0d) {
			throw new IllegalArgumentException("Learning rate parameter must be lower than 1.0, but it is '" + p_d_value + "'");
		}
		
		this.d_learningRate = p_d_value;
	}
	
	/**
	 * set momentum
	 * 
	 * @param p_d_value momentum value
	 * @throws IllegalArgumentException must be between 0.0 and 1.0
	 */
	public void setMomentum(Double p_d_value) throws IllegalArgumentException {
		if (p_d_value <= 0.0d) {
			throw new IllegalArgumentException("Momentum parameter must be greater than 0.0, but it is '" + p_d_value + "'");
		}
		
		if (p_d_value > 1.0d) {
			throw new IllegalArgumentException("Momentum parameter must be lower than 1.0, but it is '" + p_d_value + "'");
		}
		
		this.d_momentum = p_d_value;
	}
	
	/**
	 * set max epochs
	 * 
	 * @param p_l_value max epochs value
	 * @throws IllegalArgumentException must be a positive long value
	 */
	public void setMaxEpochs(Long p_l_value) throws IllegalArgumentException {
		if (p_l_value <= 0) {
			throw new IllegalArgumentException("Max epochs parameter must be greater than 0, but it is '" + p_l_value + "'");
		}
		
		this.l_maxEpochs = p_l_value;
	}
	
	/**
	 * set desired accuracy
	 * 
	 * @param p_d_value desired accuracy value
	 * @throws IllegalArgumentException must be between 0.0 and 100.0
	 */
	public void setDesiredAccuracy(Double p_d_value) throws IllegalArgumentException {
		if (p_d_value <= 0.0d) {
			throw new IllegalArgumentException("Desired accuracy parameter must be greater than 0.0, but it is '" + p_d_value + "'");
		}
		
		if (p_d_value > 100.0d) {
			throw new IllegalArgumentException("Desired accuracy parameter must be lower than 100.0, but it is '" + p_d_value + "'");
		}
		
		this.d_desiredAccuracy = p_d_value;
	}
	
	/**
	 * set use batch flag
	 * 
	 * @param p_b_value boolean
	 */
	public void setUseBatch(boolean p_b_value) {
		this.b_useBatch = p_b_value;
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
	 * Constructor of neural network trainer class - default values: learning rate (0.001), momentum (0.9), max epochs (1500), desired accuracy (90), useBatch (false)
	 * 
	 * @param p_o_neuralNetwork					neural network which will be trained with this trainer class
	 * @throws IllegalArgumentException			invalid neural network parameter
	 */
	public NeuralNetworkTrainer(NeuralNetwork p_o_neuralNetwork) throws IllegalArgumentException {
		this(p_o_neuralNetwork, null);
	}
	
	/**
	 * Constructor of neural network trainer class - default values: learning rate (0.001), momentum (0.9), max epochs (1500), desired accuracy (90), useBatch (false)
	 * 
	 * @param p_o_neuralNetwork					neural network which will be trained with this trainer class
	 * @param p_itf_delegate					interface delegate to post progress of neural network training after each epoch
	 * @throws IllegalArgumentException			invalid neural network parameter
	 */
	public NeuralNetworkTrainer(NeuralNetwork p_o_neuralNetwork, IDelegate p_itf_delegate) throws IllegalArgumentException {
		/* check for a valid neural network parameter */
		if (p_o_neuralNetwork == null) {
			throw new IllegalArgumentException("Parameter neural network is null");
		} else {
			this.o_neuralNetwork = p_o_neuralNetwork;
		}
		
		/* use delegate parameter if not null */
		if (p_itf_delegate != null) {
			this.itf_delegate = p_itf_delegate;
		}
		
		/* set default values */
		this.d_learningRate = 0.001d;
		this.d_momentum = 0.9d;
		
		this.l_maxEpochs = Integer.valueOf(1500).longValue();
		
		this.d_desiredAccuracy = 90.0d;
		
		this.d_trainingSetAccuracy = 0.0d;
		this.d_generalizationSetAccuracy = 0.0d;
		this.d_validationSetAccuracy = 0.0d;
		
		this.b_useBatch = false;
		
		/* initialize delta array 1st dimension  */
		this.a_delta = new java.util.ArrayList<java.util.List<java.util.List<Double>>>(this.o_neuralNetwork.getLayers().size() - 1);
		
		/* we iterate each layer to get to know how many deltas we must store and how many deltas explicitly are between them */
		for (int i = 0; i < this.o_neuralNetwork.getLayers().size() - 1; i++) { /* size - 1, cause there are just size - 1 delta layers between all layers */
			/* initialize delta array 2nd dimension */
			this.a_delta.add( new java.util.ArrayList<java.util.List<Double>>(this.o_neuralNetwork.getLayers().get(i) + 1) );
			
			/* for each neuron in layer i */
			for (int j = 0; j <= this.o_neuralNetwork.getLayers().get(i); j++) {
				/* initialize delta array 3rd dimension */
				this.a_delta.get(i).add( new java.util.ArrayList<Double>(this.o_neuralNetwork.getLayers().get(i + 1)) );
				
				/* add 3rd dimension as zero values, for each neuron in layer i + 1 */
				for (int k = 0; k < this.o_neuralNetwork.getLayers().get(i + 1); k++) {
					this.a_delta.get(i).get(j).add(0.0d);
				}
			}
		}
		
		/* initialize error gradient 1st dimension */
		this.a_errorGradients = new java.util.ArrayList<java.util.List<Double>>(this.o_neuralNetwork.getLayers().size() - 1);
		
		/* we iterate each layer to get to know how many error gradients we must store, but not the first layer */
		for (int i = 0; i < this.o_neuralNetwork.getLayers().size() - 1; i++) { /* size - 1, cause there are just size - 1 error gradient layers between all layers */
			/* initialize delta array 2nd dimension */
			this.a_errorGradients.add( new java.util.ArrayList<Double>(this.o_neuralNetwork.getLayers().get(i + 1) + 1) );
			
			/* set error gradients array as zero values */
			for (int j = 0; j <= this.o_neuralNetwork.getLayers().get(i + 1); j++) {
				this.a_errorGradients.get(i).add(0.0d);
			}
		}
	}
	
	/**
	 * Method to start training of the neural network
	 */
	public void trainNetwork() {
		/* set training epoch value to zero */
		long l_epoch = Integer.valueOf(0).longValue();
		
		/* train the network using the training dataset and the generalization dataset for testing */
		while (	( this.d_trainingSetAccuracy < this.d_desiredAccuracy || ( (this.d_generalizationSetAccuracy < this.d_desiredAccuracy) && (this.d_trainingSetAccuracy != 100.0d) ) ) && (l_epoch < this.l_maxEpochs) ) {	
			/* train neural network with training set */
			this.executeTrainingEpoch(this.o_neuralNetwork.getTrainingDataSet().getTrainingSet());

			/* get generalization accuracy of current neural network by generalization set and desired accuracy */
			this.d_generalizationSetAccuracy = this.o_neuralNetwork.getNetworkAccuracy(this.o_neuralNetwork.getTrainingDataSet().getGeneralizationSet(), this.d_desiredAccuracy);
			
			/* increase training epoch value after trained one epoch */
			l_epoch++;
			
			/* post progress of current training */
			if (this.itf_delegate != null) {
				this.itf_delegate.PostProgress(l_epoch, this.l_maxEpochs, this.d_desiredAccuracy, this.d_trainingSetAccuracy, this.d_generalizationSetAccuracy, this.d_validationSetAccuracy);
			}
		}
		
		/* get validation accuracy of current neural network by validation set and desired accuracy */
		this.d_validationSetAccuracy = this.o_neuralNetwork.getNetworkAccuracy(this.o_neuralNetwork.getTrainingDataSet().getValidationSet(), this.d_desiredAccuracy);
	}
	
	/**
	 * method which executed one training epoch with training set
	 * 
	 * @param a_trainingSet		training set with input patterns and expected output patterns
	 */
	private void executeTrainingEpoch(java.util.List<DataEntry> a_trainingSet) {
		/* variable for the number of false patterns */
		double d_incorrectPatterns = 0;
			
		/* iterate each training data entry */
		for (int i = 0; i < (int) a_trainingSet.size(); i++) {
			/* process input pattern through neural network */
			@SuppressWarnings("unused")
			java.util.List<Double> a_return = this.o_neuralNetwork.feedForwardPattern(a_trainingSet.get(i).getPattern());
			
			/* use backpropagate algorithm with expected output pattern */
			this.backpropagate(a_trainingSet.get(i).getTarget());

			/* compare neural network output with expected output from training data entry */
			for (int j = 0; j < this.o_neuralNetwork.getLayers().get(this.o_neuralNetwork.getLayers().size() - 1); j++) {
				/* pattern is wrong if calculated output differs from known output */
				if ( ! this.o_neuralNetwork.clampOutput( this.o_neuralNetwork.getNeurons().get(this.o_neuralNetwork.getLayers().size() - 1).get(j), a_trainingSet.get(i).getTarget().get(j), this.d_desiredAccuracy ) ) {
					/* comparison failed, so we increase incorrect result counter and break the for loop */
					d_incorrectPatterns++;
					break;
				}
			}
			
		}

		/* useBatch = true, update weights after training epoch finished with all training data entries */
		if (this.b_useBatch) {
			this.updateWeights();
		}
		
		/* calculate training set accuracy with current incorrect result counter */
		this.d_trainingSetAccuracy = 100.0d - (d_incorrectPatterns/Integer.valueOf(a_trainingSet.size()).doubleValue() * 100.0d);
	}
	
	/**
	 * backpropagation algorithm method
	 * 
	 * @param a_desiredOutputs			desired output pattern from training data entry
	 */
	private void backpropagate(java.util.List<Double> a_desiredOutputs) {
		int i_lastLayerIndex = this.o_neuralNetwork.getLayers().size() - 1;
		int i_secondToLastLayerIndex = this.o_neuralNetwork.getLayers().size() - 2;
		
		/* modify the deltas between second to last layer and last layer */
		for (int i = 0; i < this.o_neuralNetwork.getLayers().get(i_lastLayerIndex); i++) {
			/* get error gradients for each output neuron */
			double d_outputErrorGradient = this.o_neuralNetwork.getNeurons().get(i_lastLayerIndex).get(i) * ( 1.0d - this.o_neuralNetwork.getNeurons().get(i_lastLayerIndex).get(i) ) * ( a_desiredOutputs.get(i) - this.o_neuralNetwork.getNeurons().get(i_lastLayerIndex).get(i) );
			/* set output error gradient */
			this.a_errorGradients.get(i_secondToLastLayerIndex).set(i, d_outputErrorGradient);
			
			/* for all neurons in the second to last layer and the bias neuron */
			for (int j = 0; j <= this.o_neuralNetwork.getLayers().get(i_secondToLastLayerIndex); j++) {
				/* calculation of weight changes, for batch learning (with momentum) and normal (stochastic) learning */
				if (!this.b_useBatch) {
					this.a_delta.get(i_secondToLastLayerIndex).get(j).set(i, this.d_learningRate * this.o_neuralNetwork.getNeurons().get(i_secondToLastLayerIndex).get(j) * this.a_errorGradients.get(i_secondToLastLayerIndex).get(i) + this.d_momentum * this.a_delta.get(i_secondToLastLayerIndex).get(j).get(i));
				} else {
					this.a_delta.get(i_secondToLastLayerIndex).get(j).set(i, this.d_learningRate * this.o_neuralNetwork.getNeurons().get(i_secondToLastLayerIndex).get(j) * this.a_errorGradients.get(i_secondToLastLayerIndex).get(i) + this.a_delta.get(i_secondToLastLayerIndex).get(j).get(i));
				}
			}
		}

		/* iterate each layer from second to last to first one */
		for (int i = i_secondToLastLayerIndex; i > 0; i--) {
			/* modify the deltas between layer i and layer i-1 */
			for (int j = 0; j < this.o_neuralNetwork.getLayers().get(i); j++) {
				/* the sum of the weights between both layers multiplied by the error gradient */
				double d_sum = 0;
				
				/* sum it up for each neuron */
				for(int k = 0; k < this.o_neuralNetwork.getLayers().get(i + 1); k++) {
					d_sum += this.o_neuralNetwork.getWeights().get(i).get(j).get(k) * this.a_errorGradients.get(i).get(k);
				}
	
				/* set error gradients for the neuron */
				this.a_errorGradients.get(i - 1).set(j, (this.o_neuralNetwork.getNeurons().get(i).get(j) * ( 1.0d - this.o_neuralNetwork.getNeurons().get(i).get(j) ) * d_sum) );
				
				/* for all neurons in layer i - 1 and the bias neuron */
				for(int k = 0; k <= this.o_neuralNetwork.getLayers().get(i - 1); k++) {
					/* calculation of weight changes, for batch learning (with momentum) and normal (stochastic) learning */
					if (!this.b_useBatch) {
						this.a_delta.get(i - 1).get(k).set(j, this.d_learningRate * this.o_neuralNetwork.getNeurons().get(i - 1).get(k) * this.a_errorGradients.get(i - 1).get(j) + this.d_momentum * this.a_delta.get(i - 1).get(k).get(j));
					} else {
						this.a_delta.get(i - 1).get(k).set(j, this.d_learningRate * this.o_neuralNetwork.getNeurons().get(i - 1).get(k) * this.a_errorGradients.get(i - 1).get(j) + this.a_delta.get(i - 1).get(k).get(j));
					}
				}
			}
		}
	
		/* useBatch = false, update weights after each training data entry */
		if (!b_useBatch) {
			this.updateWeights();
		}
	}

	/**
	 * method which updates all weights within the backpropagate algorithm
	 * can be used after each training epoch or each training data entry
	 */
	private void updateWeights() {
		/* iterate each layer */
		for (int i = 0; i < this.o_neuralNetwork.getLayers().size() - 1; i++) { /* size - 1, cause there are just size - 1 delta layers between all layers */
			/* update weights from layer i to layer i+1 */
			for (int j = 0; j <= this.o_neuralNetwork.getLayers().get(i); j++) {
				for (int k = 0; k < this.o_neuralNetwork.getLayers().get(i + 1); k++) {
					/* update weights, add up delta values */
					this.o_neuralNetwork.getWeights().get(i).get(j).set(k, this.o_neuralNetwork.getWeights().get(i).get(j).get(k) + this.a_delta.get(i).get(j).get(k));
					
					/* if batch learning, then delta value is zero otherwise previous delta is needed for momentum */
					if (this.b_useBatch) {
						this.a_delta.get(i).get(j).set(k, 0.0d);
					}
				}
			}
		}
	}
}
