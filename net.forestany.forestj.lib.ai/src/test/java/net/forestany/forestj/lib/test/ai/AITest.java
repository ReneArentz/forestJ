package net.forestany.forestj.lib.test.ai;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * class to test ai library
 */
public class AITest {
	/**
	 * method to test ai library
	 */
	/* @org.junit.jupiter.api.Disabled */
	@Test
	public void testAI() {
		try {
			net.forestany.forestj.lib.ai.TrainingDataSet o_trainingDataset = null;
			double d_trainingSetQuota = 1.0; /* always 100% because with these simple examples we have very few training data */
			double d_generalizationSetQuota = 0.0; /* 0% because training quota is 100% */
			boolean b_useRectifier = false;
			boolean b_useBatch = false;
			
			/* AND with 3 inputs and 1 output, 4 hidden */
			o_trainingDataset = new net.forestany.forestj.lib.ai.TrainingDataSet();
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 1.0d), java.util.Arrays.asList(1.000d) ) );
			
			testAILogicGate(java.util.Arrays.asList(3, 4, 1), b_useRectifier, b_useBatch, o_trainingDataset, d_trainingSetQuota, d_generalizationSetQuota, 2);
			
			/* NAND with 3 inputs and 1 output, 4 hidden */
			o_trainingDataset = new net.forestany.forestj.lib.ai.TrainingDataSet();
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 0.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 1.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 0.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 1.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 0.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 1.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 0.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			
			testAILogicGate(java.util.Arrays.asList(3, 4, 1), b_useRectifier, b_useBatch, o_trainingDataset, d_trainingSetQuota, d_generalizationSetQuota, 2);
			
			/* OR with 3 inputs and 1 output, 4 hidden */
			o_trainingDataset = new net.forestany.forestj.lib.ai.TrainingDataSet();
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 1.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 0.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 1.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 0.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 1.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 0.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 1.0d), java.util.Arrays.asList(1.000d) ) );
			
			testAILogicGate(java.util.Arrays.asList(3, 4, 1), b_useRectifier, b_useBatch, o_trainingDataset, d_trainingSetQuota, d_generalizationSetQuota, 2);
			
			/* NOR with 3 inputs and 1 output, 4 hidden */
			o_trainingDataset = new net.forestany.forestj.lib.ai.TrainingDataSet();
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 0.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			
			testAILogicGate(java.util.Arrays.asList(3, 4, 1), b_useRectifier, b_useBatch, o_trainingDataset, d_trainingSetQuota, d_generalizationSetQuota, 2);
			
			/* XOR with 3 inputs and 1 output, 4 hidden */
			o_trainingDataset = new net.forestany.forestj.lib.ai.TrainingDataSet();
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 1.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 0.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 0.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 1.0d), java.util.Arrays.asList(1.000d) ) );
			
			testAILogicGate(java.util.Arrays.asList(3, 4, 1), b_useRectifier, b_useBatch, o_trainingDataset, d_trainingSetQuota, d_generalizationSetQuota, 2);
			
			/* XNOR with 3 inputs and 1 output, 4 hidden */
			o_trainingDataset = new net.forestany.forestj.lib.ai.TrainingDataSet();
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 0.0d), java.util.Arrays.asList(1.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 0.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(0.0d, 1.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 0.0d, 1.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 0.0d), java.util.Arrays.asList(0.000d) ) );
			o_trainingDataset.getTrainingSet().add( new net.forestany.forestj.lib.ai.DataEntry( java.util.Arrays.asList(1.0d, 1.0d, 1.0d), java.util.Arrays.asList(1.000d) ) );
			
			testAILogicGate(java.util.Arrays.asList(3, 4, 1), b_useRectifier, b_useBatch, o_trainingDataset, d_trainingSetQuota, d_generalizationSetQuota, 2);

			/* XOR with training data set from file, 3 inputs and 1 output, 4 hidden */
			String s_trainingSetFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "ai" + net.forestany.forestj.lib.io.File.DIR + "xor.txt";
			
			testAIWithTrainingDataInFile(java.util.Arrays.asList(3, 4, 1), b_useRectifier, b_useBatch, s_trainingSetFile, d_trainingSetQuota, d_generalizationSetQuota, 2);

			/* test ridiculous ANN with training data set from file */
			String s_neuralNetworkFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "ai" + net.forestany.forestj.lib.io.File.DIR + "RidiculousAI.txt";
			s_trainingSetFile = net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "test" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "ai" + net.forestany.forestj.lib.io.File.DIR + "RidiculousTrainingSet.txt";
			
			testAIWithTrainingDataInFile(java.util.Arrays.asList(3, 32, 16, 8, 3), b_useRectifier, b_useBatch, s_trainingSetFile, d_trainingSetQuota, d_generalizationSetQuota, 3);
			
			/* test ridiculous ANN without training, loading directly from file */
			testAIFromFile(java.util.Arrays.asList(3, 32, 16, 8, 3), b_useRectifier, b_useBatch, s_neuralNetworkFile, s_trainingSetFile, 3);
		} catch (Exception o_exc) {
			fail(o_exc.getMessage());
		}
	}
	
	private static java.util.List<java.util.List<Double>> getAILinearTrainingSettings() {
		java.util.List<java.util.List<Double>> a_simpleAITrainingSettings = new java.util.ArrayList<java.util.List<Double>>();
		
		int i = 0;
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.999d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.001d); // momentum
		a_simpleAITrainingSettings.get(i).add(25.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.9d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.1d); // momentum
		a_simpleAITrainingSettings.get(i).add(50.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.8d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.2d); // momentum
		a_simpleAITrainingSettings.get(i).add(55.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.7d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.3d); // momentum
		a_simpleAITrainingSettings.get(i).add(60.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.6d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.4d); // momentum
		a_simpleAITrainingSettings.get(i).add(65.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.5d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.5d); // momentum
		a_simpleAITrainingSettings.get(i).add(70.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.4d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.6d); // momentum
		a_simpleAITrainingSettings.get(i).add(75.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.3d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.7d); // momentum
		a_simpleAITrainingSettings.get(i).add(80.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.2d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.8d); // momentum
		a_simpleAITrainingSettings.get(i).add(85.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.1d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.9d); // momentum
		a_simpleAITrainingSettings.get(i).add(90.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.05d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.95d); // momentum
		a_simpleAITrainingSettings.get(i).add(95.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.025d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.97d); // momentum
		a_simpleAITrainingSettings.get(i).add(97.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.0125d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.98d); // momentum
		a_simpleAITrainingSettings.get(i).add(99.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(40000.0d); // max epochs
		
		return a_simpleAITrainingSettings;
	}
	
	private static void testAILogicGate(java.util.List<Integer> p_a_layers, boolean p_b_useRectifier, boolean p_b_useBatch, net.forestany.forestj.lib.ai.TrainingDataSet p_o_trainigDataSet, double p_d_trainingSetQuota, double p_d_generalizationSetQuota, int p_i_resultPrecision) throws Exception {
		java.util.List<java.util.List<Double>> a_trainingSettings = getAILinearTrainingSettings();
		
		net.forestany.forestj.lib.ai.NeuralNetwork o_neuralNetwork = null;
		int i_pointer = 0;
		int i_detectEndlessLoop = 0;
		int i_restarts = 0;
		boolean b_once = false;
		
		do {
			//System.out.println(i_pointer);
			
			if (i_restarts > 5) {
				fail("AI training failed");
				break;
			}
			
			if (i_detectEndlessLoop > 50) {
				i_detectEndlessLoop = 0;
				i_pointer = 0;
				b_once = false;
				i_restarts++;
			}
			
			if (!b_once) {
				o_neuralNetwork = new net.forestany.forestj.lib.ai.NeuralNetwork(p_a_layers);
				o_neuralNetwork.setTrainingDataSet(p_o_trainigDataSet);
				o_neuralNetwork.setTrainingQuotas(p_d_trainingSetQuota, p_d_generalizationSetQuota);
				o_neuralNetwork.setUseRectifierActivationFunction(p_b_useRectifier);
				b_once = true;
			}
			
			net.forestany.forestj.lib.ai.NeuralNetworkTrainer o_neuralNetworkTrainer = new net.forestany.forestj.lib.ai.NeuralNetworkTrainer(o_neuralNetwork);
			
			o_neuralNetworkTrainer.setLearningRate(a_trainingSettings.get(i_pointer).get(0));
			o_neuralNetworkTrainer.setMomentum(a_trainingSettings.get(i_pointer).get(1));
			o_neuralNetworkTrainer.setUseBatch(p_b_useBatch);
			o_neuralNetworkTrainer.setMaxEpochs(Double.valueOf(a_trainingSettings.get(i_pointer).get(3)).longValue());
			o_neuralNetworkTrainer.setDesiredAccuracy(a_trainingSettings.get(i_pointer).get(2));
				
			o_neuralNetworkTrainer.trainNetwork();
			
			if ((o_neuralNetworkTrainer.getTrainingSetAccuracy() >= a_trainingSettings.get(i_pointer).get(2)) || (o_neuralNetworkTrainer.getGeneralizationSetAccuracy() >= a_trainingSettings.get(i_pointer).get(2))) {
				i_pointer++;
			}
			
			i_detectEndlessLoop++;
		} while (i_pointer <= (a_trainingSettings.size() - 1));
		
		for (net.forestany.forestj.lib.ai.DataEntry o_dataEntry : p_o_trainigDataSet.getTrainingSet()) {
			java.util.List<Double> a_output = o_neuralNetwork.feedForwardPattern(o_dataEntry.getPattern());
			
			for (int i = 0; i < a_output.size(); i++) {
				//System.out.println(i + ": " + a_output.get(i) + " - " + o_dataEntry.getTarget().get(i) + " | " + new java.math.BigDecimal( a_output.get(i) ).setScale(p_i_resultPrecision, java.math.RoundingMode.HALF_EVEN).doubleValue() + " - " + new java.math.BigDecimal( o_dataEntry.getTarget().get(i) ).setScale(p_i_resultPrecision, java.math.RoundingMode.HALF_EVEN).doubleValue() );
				assertTrue(o_neuralNetwork.clampOutput(a_output.get(i), o_dataEntry.getTarget().get(i), 99.0d), "output[" + a_output.get(i) + "] does not match expected output[" + o_dataEntry.getTarget().get(i) + "]");
			}
		}
	}
	
	private static void testAIWithTrainingDataInFile(java.util.List<Integer> p_a_layers, boolean p_b_useRectifier, boolean p_b_useBatch, String p_s_trainigDataSetFile, double p_d_trainingSetQuota, double p_d_generalizationSetQuota, int p_i_resultPrecision) throws Exception {
		java.util.List<java.util.List<Double>> a_trainingSettings = getAILinearTrainingSettings();
		
		net.forestany.forestj.lib.ai.NeuralNetwork o_neuralNetwork = null;
		int i_pointer = 0;
		int i_detectEndlessLoop = 0;
		int i_restarts = 0;
		boolean b_once = false;
		
		do {
			if (i_restarts > 5) {
				fail("AI training failed");
				break;
			}
			
			if (i_detectEndlessLoop > 50) {
				i_detectEndlessLoop = 0;
				i_pointer = 0;
				b_once = false;
				i_restarts++;
			}
			
			if (!b_once) {
				o_neuralNetwork = new net.forestany.forestj.lib.ai.NeuralNetwork(p_a_layers);
				o_neuralNetwork.loadTrainingDataSetFromFile(p_s_trainigDataSetFile, p_d_trainingSetQuota, p_d_generalizationSetQuota);
				o_neuralNetwork.setUseRectifierActivationFunction(p_b_useRectifier);
				b_once = true;
			}
			
			net.forestany.forestj.lib.ai.NeuralNetworkTrainer o_neuralNetworkTrainer = new net.forestany.forestj.lib.ai.NeuralNetworkTrainer(o_neuralNetwork);
			
			o_neuralNetworkTrainer.setLearningRate(a_trainingSettings.get(i_pointer).get(0));
			o_neuralNetworkTrainer.setMomentum(a_trainingSettings.get(i_pointer).get(1));
			o_neuralNetworkTrainer.setUseBatch(p_b_useBatch);
			o_neuralNetworkTrainer.setMaxEpochs(Double.valueOf(a_trainingSettings.get(i_pointer).get(3)).longValue());
			o_neuralNetworkTrainer.setDesiredAccuracy(a_trainingSettings.get(i_pointer).get(2));
				
			o_neuralNetworkTrainer.trainNetwork();
			
			if ((o_neuralNetworkTrainer.getTrainingSetAccuracy() >= a_trainingSettings.get(i_pointer).get(2)) || (o_neuralNetworkTrainer.getGeneralizationSetAccuracy() >= a_trainingSettings.get(i_pointer).get(2))) {
				i_pointer++;
			}
			
			i_detectEndlessLoop++;
		} while (i_pointer <= (a_trainingSettings.size() - 1));
		
		/* comment in for saving neural network to a file */
		//o_neuralNetwork.SaveWeights(net.forestany.forestj.lib.io.File.getCurrentDirectory() + net.forestany.forestj.lib.io.File.DIR + "ai.txt");
		
		for (net.forestany.forestj.lib.ai.DataEntry o_dataEntry : o_neuralNetwork.getTrainingDataSet().getTrainingSet()) {
			java.util.List<Double> a_output = o_neuralNetwork.feedForwardPattern(o_dataEntry.getPattern());
			
			for (int i = 0; i < a_output.size(); i++) {
				//System.out.println(i + ": " + a_output.get(i) + " - " + o_dataEntry.getTarget().get(i) + " | " + new java.math.BigDecimal( a_output.get(i) ).setScale(p_i_resultPrecision, java.math.RoundingMode.HALF_EVEN).doubleValue() + " - " + new java.math.BigDecimal( o_dataEntry.getTarget().get(i) ).setScale(p_i_resultPrecision, java.math.RoundingMode.HALF_EVEN).doubleValue() );
				assertTrue(o_neuralNetwork.clampOutput(a_output.get(i), o_dataEntry.getTarget().get(i), 99.0d), "output[" + a_output.get(i) + "] does not match expected output[" + o_dataEntry.getTarget().get(i) + "]");
			}
		}
	}
	
	private static void testAIFromFile(java.util.List<Integer> p_a_layers, boolean p_b_useRectifier, boolean p_b_useBatch, String p_s_neuralNetworkFile, String p_s_trainigDataSetFile, int p_i_resultPrecision) throws Exception {
		net.forestany.forestj.lib.ai.NeuralNetwork o_neuralNetwork = new net.forestany.forestj.lib.ai.NeuralNetwork(p_a_layers);
		o_neuralNetwork.loadWeights(p_s_neuralNetworkFile);
		o_neuralNetwork.loadTrainingDataSetFromFile(p_s_trainigDataSetFile, 1.0, 0.0);
		o_neuralNetwork.setUseRectifierActivationFunction(p_b_useRectifier);
		
		for (net.forestany.forestj.lib.ai.DataEntry o_dataEntry : o_neuralNetwork.getTrainingDataSet().getTrainingSet()) {
			java.util.List<Double> a_output = o_neuralNetwork.feedForwardPattern(o_dataEntry.getPattern());
			
			for (int i = 0; i < a_output.size(); i++) {
				//System.out.println(i + ": " + a_output.get(i) + " - " + o_dataEntry.getTarget().get(i) + " | " + new java.math.BigDecimal( a_output.get(i) ).setScale(p_i_resultPrecision, java.math.RoundingMode.HALF_EVEN).doubleValue() + " - " + new java.math.BigDecimal( o_dataEntry.getTarget().get(i) ).setScale(p_i_resultPrecision, java.math.RoundingMode.HALF_EVEN).doubleValue() );
				assertTrue(o_neuralNetwork.clampOutput(a_output.get(i), o_dataEntry.getTarget().get(i), 99.0d), "output[" + a_output.get(i) + "] does not match expected output[" + o_dataEntry.getTarget().get(i) + "]");
			}
		}
	}
}
