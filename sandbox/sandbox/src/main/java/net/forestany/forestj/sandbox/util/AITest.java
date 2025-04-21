package net.forestany.forestj.sandbox.util;

public class AITest {
	public static void testAIMenu(String p_s_currentDirectory) throws Exception {
		String s_currentDirectory = p_s_currentDirectory;
		String s_resourcesAIDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "ai" + net.forestany.forestj.lib.io.File.DIR;
		String s_resourcesMNISTDirectory = s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "src" + net.forestany.forestj.lib.io.File.DIR + "main" + net.forestany.forestj.lib.io.File.DIR + "resources" + net.forestany.forestj.lib.io.File.DIR + "mnist" + net.forestany.forestj.lib.io.File.DIR;
		double d_trainingSetQuota = 0.9; /* 90% training data */
		double d_generalizationSetQuota = 0.1; /* 10% generalization data */
		boolean b_useRectifier = false;
		boolean b_useBatch = false;
		int i_input = -1;
		
		do {
			System.out.println("++++++++++++++++++++++++++++++++");
			System.out.println("+ test AI                      +");
			System.out.println("++++++++++++++++++++++++++++++++");
			
			System.out.println("");
			
			System.out.println("[1] create training set from MNIST data (10k records)");
			System.out.println("[2] create training set from MNIST data (60k records)");
			System.out.println("[3] train neural network with 10k MNIST records and 3 layers(784, 320, 10)");
			System.out.println("[4] train neural network with 60k MNIST records and 3 layers(784, 320, 10)");
			System.out.println("[5] get neural network accuracy with 10k MNIST records");
			System.out.println("[6] get neural network accuracy with 60k MNIST records");
			System.out.println("[7] test neural network with random record out of 10k MNIST records");
			System.out.println("[8] test neural network with own test image(test.png - 128x128 px - 8bit depth)");
			System.out.println("[9] create MNIST sample output (1000 images)");
			System.out.println("[0] quit");
			
			System.out.println("");
			
			i_input = net.forestany.forestj.lib.Console.consoleInputInteger("Enter menu number[1-9;0]: ", "Invalid input.", "Please enter a value.");
		
			System.out.println("");
			
			if (i_input == 1) {
				AITest.createTrainingSetFromMNISTData(s_resourcesMNISTDirectory + "t10k-images-idx3-ubyte.gz", s_resourcesMNISTDirectory + "t10k-labels-idx1-ubyte.gz", s_resourcesAIDirectory + "trainingDataSet_10k.txt", -1);
			} else if (i_input == 2) {
				AITest.createTrainingSetFromMNISTData(s_resourcesMNISTDirectory + "train-images-idx3-ubyte.gz", s_resourcesMNISTDirectory + "train-labels-idx1-ubyte.gz", s_resourcesAIDirectory + "trainingDataSet_60k.txt", -1);
			} else if (i_input == 3) {
				AITest.trainAI(java.util.Arrays.asList(784, 320, 10), b_useRectifier, b_useBatch, s_resourcesAIDirectory + "neuralNetwork.txt", s_resourcesAIDirectory + "trainingDataSet_10k.txt", d_trainingSetQuota, d_generalizationSetQuota);
			} else if (i_input == 4) {
				AITest.trainAI(java.util.Arrays.asList(784, 320, 10), b_useRectifier, b_useBatch, s_resourcesAIDirectory + "neuralNetwork.txt", s_resourcesAIDirectory + "trainingDataSet_60k.txt", d_trainingSetQuota, d_generalizationSetQuota);
			} else if (i_input == 5) {
				System.out.println("");
			
				double d_input = net.forestany.forestj.lib.Console.consoleInputDouble("Enter target accuracy(e.g. 80.0): ", "Invalid input.", "Please enter a value.");
			
				System.out.println("");
				
				AITest.getAIAccuracy(java.util.Arrays.asList(784, 320, 10), b_useRectifier, b_useBatch, s_resourcesAIDirectory + "neuralNetwork.txt", s_resourcesAIDirectory + "trainingDataSet_10k.txt", d_trainingSetQuota, d_generalizationSetQuota, d_input);
			} else if (i_input == 6) {
				System.out.println("");
			
				double d_input = net.forestany.forestj.lib.Console.consoleInputDouble("Enter target accuracy(e.g. 80.0): ", "Invalid input.", "Please enter a value.");
			
				System.out.println("");
				
				AITest.getAIAccuracy(java.util.Arrays.asList(784, 320, 10), b_useRectifier, b_useBatch, s_resourcesAIDirectory + "neuralNetwork.txt", s_resourcesAIDirectory + "trainingDataSet_60k.txt", d_trainingSetQuota, d_generalizationSetQuota, d_input);
			} else if (i_input == 7) {
				AITest.testAIRandom(s_resourcesMNISTDirectory + "t10k-images-idx3-ubyte.gz", s_resourcesMNISTDirectory + "t10k-labels-idx1-ubyte.gz", 10000, java.util.Arrays.asList(784, 320, 10), s_resourcesAIDirectory + "neuralNetwork.txt");
			} else if (i_input == 8) {
				AITest.testAI(s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "test.png", 128, 128, 28, java.util.Arrays.asList(784, 320, 10), s_resourcesAIDirectory + "neuralNetwork.txt");
			} else if (i_input == 9) {
				AITest.createMNISTOutput(s_resourcesMNISTDirectory + "train-images-idx3-ubyte.gz", s_resourcesMNISTDirectory + "train-labels-idx1-ubyte.gz", s_currentDirectory + net.forestany.forestj.lib.io.File.DIR + "samplesMNIST" + net.forestany.forestj.lib.io.File.DIR);
			}
			
			if ( (i_input >= 1) && (i_input <= 9) ) {
				System.out.println("");
				
				net.forestany.forestj.lib.Console.consoleInputString("Press any key to continue . . . ", true);
				
				System.out.println("");
			}
			
			System.out.println("");
			
		} while (i_input != 0);
	}
	
	public static void createMNISTOutput(String p_s_filePathImage, String p_s_filePathLabel, String p_s_testDirectory) throws Exception {
		if ( net.forestany.forestj.lib.io.File.folderExists(p_s_testDirectory) ) {
			net.forestany.forestj.lib.io.File.deleteDirectory(p_s_testDirectory);
		}
		
		net.forestany.forestj.lib.io.File.createDirectory(p_s_testDirectory);
		
		MNISTHandling o_mnistHandling = new MNISTHandling(new java.io.File(p_s_filePathImage), new java.io.File(p_s_filePathLabel));
		
		int i = 0;
		
		while (o_mnistHandling.hasNext()) {
			if (i % 1000 == 0) {
				System.out.println(i);
			}
			
			o_mnistHandling.selectNext();
			
			if (i < 1000) {
				byte by_label = o_mnistHandling.getCurrentLabelValue();
				byte[] a_data = o_mnistHandling.getCurrentImageData();
				
				if (i < 50) {
					System.out.println(i + " -> " + by_label);
				}
				
				if (i < 10) {
					byte[] a_binary = o_mnistHandling.getDataAsBinaryArray(a_data);
					int i_index = 0;
		
					for (int y = 0; y < o_mnistHandling.getImageHeight(); y++) {
						for (int x = 0; x < o_mnistHandling.getImageWidth(); x++, i_index++) {
							if (a_binary[i_index] == 0) {
								System.out.print("  ");
							} else {
								System.out.print("8 ");
							}
						}
						
						System.out.print(net.forestany.forestj.lib.io.File.NEWLINE);
					}
				}
				
				java.awt.image.BufferedImage o_image = new java.awt.image.BufferedImage(o_mnistHandling.getImageWidth(), o_mnistHandling.getImageHeight(), java.awt.image.BufferedImage.TYPE_BYTE_GRAY);
				int i_index = 0;
				
				for (int y = 0; y < o_mnistHandling.getImageHeight(); y++) {
					for (int x = 0; x < o_mnistHandling.getImageWidth(); x++, i_index++) {
						final byte data = a_data[i_index];
						int i_gray = 255 - ( ( (int) data ) & 0xFF );
						int i_rgb = i_gray | (i_gray << 8) | (i_gray << 16);
						o_image.setRGB( x, y, i_rgb );
					}
				}
				
				javax.imageio.ImageIO.write(o_image, "png", new java.io.File(p_s_testDirectory + "img" + by_label + "." + i + ".png"));
			}
			
			i++;
		}
		
		System.out.println(i);
	}
	
	public static byte[] imageToBinaryPixelArray(String p_s_filePath, int p_i_height, int p_i_width, int p_i_destinationDimension) throws java.io.IOException {
		if (!net.forestany.forestj.lib.io.File.exists(p_s_filePath)) {
			throw new java.io.IOException("file '" + p_s_filePath + "' does not exists");
		}
		
		java.awt.image.BufferedImage o_image = javax.imageio.ImageIO.read(new java.io.File(p_s_filePath));
		byte[] a_pixels = ((java.awt.image.DataBufferByte) o_image.getRaster().getDataBuffer()).getData();
		
		if (p_i_width != p_i_destinationDimension) {
			a_pixels = squareScaling(a_pixels, p_i_width, p_i_height, p_i_destinationDimension);
		}
		
		byte[] a_return = new byte[p_i_destinationDimension * p_i_destinationDimension];
		
		int k = 0;
		
		for (int i = 0; i < p_i_destinationDimension; i++) {
			for (int j = 0; j < p_i_destinationDimension; j++) {
				
				if (a_pixels[k++] == -1) {
					a_return[(k - 1)] = 0;
				} else {
					a_return[(k - 1)] = 1;
				}
			}
		}
		
		return a_return;
	}
	
	public static void printBinaryPixelArray(byte[] p_a_pixels, int p_i_height, int p_i_width) {
		int k = 0;
		
		for (int i = 0; i < p_i_width; i++) {
			System.out.print("- ");
		}
		
		System.out.print(net.forestany.forestj.lib.io.File.NEWLINE);
		
		for (int i = 0; i < p_i_height; i++) {
			for (int j = 0; j < p_i_width; j++) {
				if (p_a_pixels[k++] == 0) {
					System.out.print(" ");
				} else {
					System.out.print("8 ");
				}
			}
			
			System.out.print(net.forestany.forestj.lib.io.File.NEWLINE);
		}
		
		for (int i = 0; i < p_i_width; i++) {
			System.out.print("- ");
		}
		
		System.out.print(net.forestany.forestj.lib.io.File.NEWLINE);
	}
	
	public static byte[] squareScaling(byte[] p_a_sourceImagePixelByteArray, int p_i_sourceWidth, int p_i_sourceHeight, int p_i_destinationDimension) {
		byte[] a_return = new byte[(p_i_destinationDimension * p_i_destinationDimension) + 1];
		
	    double xFactor = (double)p_i_sourceWidth / (double)p_i_destinationDimension;
	    double yFactor = (double)p_i_sourceHeight / (double)p_i_destinationDimension;

	    double fraction_x, fraction_y, minus1_x, minus1_y;
	    int top_x, top_y, bottom_x, bottom_y;

	    byte g11, g12, g21, g22;
		byte g1, g2;
	    byte gray = -1;

	    for (int x = 0; x < p_i_destinationDimension; ++x) {
	        for (int y = 0; y < p_i_destinationDimension; ++y) {
				bottom_x = (int)java.lang.Math.floor(x * xFactor);
	            bottom_y = (int)java.lang.Math.floor(y * yFactor);

	            top_x = bottom_x + 1;
	            
				if (top_x >= p_i_sourceWidth)
	                top_x = bottom_x;

	            top_y = bottom_y + 1;
				
	            if (top_y >= p_i_sourceHeight)
	                top_y = bottom_y;

				/* determine proportions */
	            fraction_x = x * xFactor - bottom_x;
	            fraction_y = y * yFactor - bottom_y;
	            minus1_x = 1.0 - fraction_x;
	            minus1_y = 1.0 - fraction_y;

	            /* extract pixel */
	            g11 = p_a_sourceImagePixelByteArray[bottom_x + 1 + (bottom_y * p_i_sourceWidth)];
	            g12 = p_a_sourceImagePixelByteArray[top_x + 1 + (bottom_y * p_i_sourceWidth)];
	            g21 = p_a_sourceImagePixelByteArray[bottom_x + 1 + (top_y * p_i_sourceWidth)];
	            
	            /* check for out of bound */
	            if (top_x + 1 + (top_y * p_i_sourceWidth) < (p_i_sourceHeight * p_i_sourceWidth)) {
	            	g22 = p_a_sourceImagePixelByteArray[top_x + 1 + (top_y * p_i_sourceWidth)];
	            } else {
	            	g22 = p_a_sourceImagePixelByteArray[(p_i_sourceHeight * p_i_sourceWidth) - 1];
	            }
	            
	            /* mix pixel */
	            g1 = (byte)(minus1_x * g11 + fraction_x * g12);
	            g2 = (byte)(minus1_x * g21 + fraction_x * g22);

	            gray = (byte)(minus1_y * (double)(g1) + fraction_y * (double)(g2));

	            /* set pixel */
	            a_return[x + 1 + (y * p_i_destinationDimension)] = gray;
	        }
	    }
	    
	    // upper left corner pixel scaling error
	    a_return[0] = -1;
	    
	    return a_return;
	}
	
	public static void createTrainingSetFromMNISTData(String p_s_filePathImage, String p_s_filePathLabel, String p_s_filePathTrainingSet, int p_i_maxAmount) throws Exception {
		if (!net.forestany.forestj.lib.io.File.exists(p_s_filePathImage)) {
			throw new Exception("MNIST image file[" + p_s_filePathImage + "] does not exists");
		}
		
		if (!net.forestany.forestj.lib.io.File.exists(p_s_filePathLabel)) {
			throw new Exception("MNIST label file[" + p_s_filePathLabel + "] does not exists");
		}
		
		net.forestany.forestj.lib.io.File o_fileTrainingSet = new net.forestany.forestj.lib.io.File(p_s_filePathTrainingSet, (!net.forestany.forestj.lib.io.File.exists(p_s_filePathTrainingSet)));
		o_fileTrainingSet.truncateContent();
		
		MNISTHandling o_mnistHandling = new MNISTHandling(new java.io.File(p_s_filePathImage), new java.io.File(p_s_filePathLabel));
		
		net.forestany.forestj.lib.ConsoleProgressBar o_consoleProgressBar = new net.forestany.forestj.lib.ConsoleProgressBar();
		o_consoleProgressBar.init("Creating training data set file . . .", "Training data set file created.");
		
		int i = 0;
		
		while (o_mnistHandling.hasNext()) {
			if (i % 100 == 0) {
				o_consoleProgressBar.report((double)i/o_mnistHandling.getAmount());
			}
			
			/* read data from MNIST */
			o_mnistHandling.selectNext();
			
			if ( (p_i_maxAmount >= 0) && (i >= p_i_maxAmount) ) {
				break;
			}
			
			byte by_label = o_mnistHandling.getCurrentLabelValue();
			byte[] a_data = o_mnistHandling.getCurrentImageData();
			
			StringBuilder o_stringBuilder = new StringBuilder();
			
			byte[] a_binary = o_mnistHandling.getDataAsBinaryArray(a_data);
			int i_index = 0;

			for (int y = 0; y < o_mnistHandling.getImageHeight(); y++) {
				for (int x = 0; x < o_mnistHandling.getImageWidth(); x++, i_index++) {
					if (a_binary[i_index] == 0) {
						o_stringBuilder.append("0.0;");
					} else {
						o_stringBuilder.append("1.0;");
					}
				}
			}
			
			String s_pattern = o_stringBuilder.toString();
			
			if (net.forestany.forestj.lib.Helper.isStringEmpty(s_pattern)) {
				throw new Exception("Could not convert #" + i + " label[" + by_label + "] to training data set");
			}
			
			String s_target = null;
			
			switch (by_label) {
				case 0:
					s_target = "1.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0";
				break;
				case 1:
					s_target = "0.0;1.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0";
				break;
				case 2:
					s_target = "0.0;0.0;1.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0";
				break;
				case 3:
					s_target = "0.0;0.0;0.0;1.0;0.0;0.0;0.0;0.0;0.0;0.0";
				break;
				case 4:
					s_target = "0.0;0.0;0.0;0.0;1.0;0.0;0.0;0.0;0.0;0.0";
				break;
				case 5:
					s_target = "0.0;0.0;0.0;0.0;0.0;1.0;0.0;0.0;0.0;0.0";
				break;
				case 6:
					s_target = "0.0;0.0;0.0;0.0;0.0;0.0;1.0;0.0;0.0;0.0";
				break;
				case 7:
					s_target = "0.0;0.0;0.0;0.0;0.0;0.0;0.0;1.0;0.0;0.0";
				break;
				case 8:
					s_target = "0.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0;1.0;0.0";
				break;
				case 9:
					s_target = "0.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0;1.0";
				break;
				default:
					s_target = null;
				break;
			}
			
			if (s_target == null) {
				throw new Exception("Invalid label[" + by_label + "] found");
			}
			
			o_fileTrainingSet.appendLine(s_pattern + s_target);
			
			i++;
		}
		
		o_consoleProgressBar.close();
	}
	
	private static java.util.List<java.util.List<Double>> getAILinearTrainingSettings() {
		java.util.List<java.util.List<Double>> a_simpleAITrainingSettings = new java.util.ArrayList<java.util.List<Double>>();
		
		int i = 0;
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.999d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.001d); // momentum
		a_simpleAITrainingSettings.get(i).add(25.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.9d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.1d); // momentum
		a_simpleAITrainingSettings.get(i).add(50.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.8d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.2d); // momentum
		a_simpleAITrainingSettings.get(i).add(55.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.7d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.3d); // momentum
		a_simpleAITrainingSettings.get(i).add(60.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.6d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.4d); // momentum
		a_simpleAITrainingSettings.get(i).add(65.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.5d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.5d); // momentum
		a_simpleAITrainingSettings.get(i).add(70.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.4d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.6d); // momentum
		a_simpleAITrainingSettings.get(i).add(75.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.3d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.7d); // momentum
		a_simpleAITrainingSettings.get(i).add(80.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.2d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.8d); // momentum
		a_simpleAITrainingSettings.get(i).add(85.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.1d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.9d); // momentum
		a_simpleAITrainingSettings.get(i).add(90.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.05d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.95d); // momentum
		a_simpleAITrainingSettings.get(i).add(95.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.025d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.97d); // momentum
		a_simpleAITrainingSettings.get(i).add(97.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.0125d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.98d); // momentum
		a_simpleAITrainingSettings.get(i).add(98.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
			
		a_simpleAITrainingSettings.add(new java.util.ArrayList<Double>());
		a_simpleAITrainingSettings.get(i).add(0.00625d); // learning rate
		a_simpleAITrainingSettings.get(i).add(0.99d); // momentum
		a_simpleAITrainingSettings.get(i).add(99.0d); // desired accuracy
		a_simpleAITrainingSettings.get(i++).add(100.0d); // max epochs
		
		return a_simpleAITrainingSettings;
	}
	
	public static void trainAI(java.util.List<Integer> p_a_layers, boolean p_b_useRectifier, boolean p_b_useBatch, String p_s_neuralNetworkFile, String p_s_trainigDataSetFile, double p_d_trainingSetQuota, double p_d_generalizationSetQuota) throws Exception {
		java.util.List<java.util.List<Double>> a_trainingSettings = getAILinearTrainingSettings();
		
		net.forestany.forestj.lib.ai.NeuralNetwork o_neuralNetwork = null;
		int i_pointer = 0;
		int i_detectEndlessLoop = 0;
		int i_restarts = 0;
		boolean b_once = false;
		
		net.forestany.forestj.lib.ConsoleProgressBar o_consoleProgressBar = new net.forestany.forestj.lib.ConsoleProgressBar();
		
		net.forestany.forestj.lib.ai.NeuralNetworkTrainer.IDelegate itf_delegate = new net.forestany.forestj.lib.ai.NeuralNetworkTrainer.IDelegate() { 
			@Override public void PostProgress(long p_l_epoch, long p_l_maxEpochs, Double p_d_desiredAccuracy, Double p_d_trainingSetAccuracy, Double p_d_generalizationSetAccuracy, Double p_d_validationSetAccuracy) {
				o_consoleProgressBar.report((double)p_l_epoch / p_l_maxEpochs);
				o_consoleProgressBar.setMarqueeText("reached tra. " + new java.text.DecimalFormat("0.00").format(p_d_trainingSetAccuracy) + "%|gen. " + new java.text.DecimalFormat("0.00").format(p_d_generalizationSetAccuracy) + "%");
			}
		};
		
		do {
			System.out.println(java.time.LocalDateTime.now());
			
			if (i_restarts > 5) {
				throw new Exception("AI training failed");
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
				
				if (net.forestany.forestj.lib.io.File.exists(p_s_neuralNetworkFile)) {
					o_neuralNetwork.loadWeights(p_s_neuralNetworkFile);
					
					do {
						double d_trainingAcc = o_neuralNetwork.getNetworkAccuracy(o_neuralNetwork.getTrainingDataSet().getTrainingSet(), a_trainingSettings.get(i_pointer).get(2));
						
						if (d_trainingAcc >= 97.0d) {
							i_pointer = a_trainingSettings.size() - 1;
							break;
						} else if (d_trainingAcc >= a_trainingSettings.get(i_pointer).get(2)) {
							System.out.println("neural network with '" + new java.text.DecimalFormat("0.00").format(d_trainingAcc) + "%' accuracy is greater equal des. accuracy '" + new java.text.DecimalFormat("0.00").format(a_trainingSettings.get(i_pointer).get(2)) + "%' of training settings");
							System.out.println("update training settings");
							System.out.println();
							i_pointer++;
						} else {
							break;
						}
					} while (true);
				}
				
				b_once = true;
			}
			
			net.forestany.forestj.lib.ai.NeuralNetworkTrainer o_neuralNetworkTrainer = new net.forestany.forestj.lib.ai.NeuralNetworkTrainer(o_neuralNetwork);
			
			o_neuralNetworkTrainer.setLearningRate(a_trainingSettings.get(i_pointer).get(0));
			o_neuralNetworkTrainer.setMomentum(a_trainingSettings.get(i_pointer).get(1));
			o_neuralNetworkTrainer.setUseBatch(p_b_useBatch);
			o_neuralNetworkTrainer.setMaxEpochs(Double.valueOf(a_trainingSettings.get(i_pointer).get(3)).longValue());
			o_neuralNetworkTrainer.setDesiredAccuracy(a_trainingSettings.get(i_pointer).get(2));
			o_neuralNetworkTrainer.setDelegate(itf_delegate);	
			
			o_consoleProgressBar.init(
				"Train neural network with des. accuracy of '" + new java.text.DecimalFormat("0.00").format(a_trainingSettings.get(i_pointer).get(2)) + "%'" + " . . .",
				"Training finished after max. " + Double.valueOf(a_trainingSettings.get(i_pointer).get(3)).longValue() + " epochs. Target des. accuracy was '" + new java.text.DecimalFormat("0.00").format(a_trainingSettings.get(i_pointer).get(2)) + "%'",
				"reached tra. " + new java.text.DecimalFormat("0.00").format(0.0d) + "%|gen. " + new java.text.DecimalFormat("0.00").format(0.0d) + "%"
			);
			
			o_neuralNetworkTrainer.trainNetwork();
			
			o_consoleProgressBar.close();
			
			if ((o_neuralNetworkTrainer.getTrainingSetAccuracy() >= a_trainingSettings.get(i_pointer).get(2)) || (o_neuralNetworkTrainer.getGeneralizationSetAccuracy() >= a_trainingSettings.get(i_pointer).get(2))) {
				/* saving neural network to a file */
				o_neuralNetwork.saveWeights(p_s_neuralNetworkFile);
				
				i_pointer++;
				System.out.println("update training settings");
			} else {
				System.out.println("reuse current training settings");
			}
			
			System.out.println("");
			
			i_detectEndlessLoop++;
		} while (i_pointer <= (a_trainingSettings.size() - 1));
	}
	
	public static void getAIAccuracy(java.util.List<Integer> p_a_layers, boolean p_b_useRectifier, boolean p_b_useBatch, String p_s_neuralNetworkFile, String p_s_trainigDataSetFile, double p_d_trainingSetQuota, double p_d_generalizationSetQuota, double p_d_desiredAccuracy) throws Exception {
		net.forestany.forestj.lib.ai.NeuralNetwork o_neuralNetwork = new net.forestany.forestj.lib.ai.NeuralNetwork(p_a_layers);
		o_neuralNetwork.loadTrainingDataSetFromFile(p_s_trainigDataSetFile, p_d_trainingSetQuota, p_d_generalizationSetQuota);
		o_neuralNetwork.setUseRectifierActivationFunction(p_b_useRectifier);
		o_neuralNetwork.loadWeights(p_s_neuralNetworkFile);
		
		net.forestany.forestj.lib.ConsoleProgressBar o_consoleProgressBar = new net.forestany.forestj.lib.ConsoleProgressBar();
		
		net.forestany.forestj.lib.ai.NeuralNetwork.IDelegate itf_delegate = new net.forestany.forestj.lib.ai.NeuralNetwork.IDelegate() { 
			@Override public void PostProgress(long p_l_dataSet, long p_l_amountDataSet, double p_d_incorrectResults) {
				o_consoleProgressBar.report((double)p_l_dataSet / p_l_amountDataSet);
				o_consoleProgressBar.setMarqueeText("calculated accuracy: '" + new java.text.DecimalFormat("0.00").format(100.0d - (p_d_incorrectResults/Long.valueOf(p_l_amountDataSet).doubleValue() * 100.0d)) + "%'");
			}
		};
		
		o_neuralNetwork.setDelegate(itf_delegate);
		
		System.out.println(java.time.LocalDateTime.now());
		
		o_consoleProgressBar.init(
			"Calculate neural network accuracy with des. accuracy of '" + new java.text.DecimalFormat("0.00").format(p_d_desiredAccuracy) + "%'" + " . . .",
			"Neural network accuracy calculated.",
			"calculated accuracy: '100%'"
		);
		
		double d_trainingAcc = o_neuralNetwork.getNetworkAccuracy(o_neuralNetwork.getTrainingDataSet().getTrainingSet(), p_d_desiredAccuracy);
		
		o_consoleProgressBar.close();
		
		System.out.println("neural network has '" + new java.text.DecimalFormat("0.00").format(d_trainingAcc) + "%' accuracy with des. accuracy of '" + new java.text.DecimalFormat("0.00").format(p_d_desiredAccuracy) + "%'");
		
		System.out.println(java.time.LocalDateTime.now());
	}
	
	public static void testAI(String p_s_filePath, int p_i_height, int p_i_width, int p_i_destinationDimension, java.util.List<Integer> p_a_layers, String p_s_neuralNetworkFile) throws Exception {
		if (!net.forestany.forestj.lib.io.File.exists(p_s_filePath)) {
			throw new Exception("File[" + p_s_filePath + "] does not exists");
		}
		
		// convert image file to 16 square pixel dimension array as input
		byte[] a_pixels = AITest.imageToBinaryPixelArray(p_s_filePath, p_i_height, p_i_width, p_i_destinationDimension);
		
		AITest.printBinaryPixelArray(a_pixels, p_i_destinationDimension, p_i_destinationDimension);
		
		java.util.List<Double> a_input = new java.util.ArrayList<Double>();
		
		int k = 0;
		
		for (int i = 0; i < p_i_destinationDimension; i++) {
			for (int j = 0; j < p_i_destinationDimension; j++) {
				if (a_pixels[k++] == 0) {
					a_input.add(0.0d);
				} else {
					a_input.add(1.0d);
				}
			}
		}
		
		if (!net.forestany.forestj.lib.io.File.exists(p_s_neuralNetworkFile)) {
			throw new Exception("Neural network file[" + p_s_neuralNetworkFile + "] does not exists");
		}
		
		net.forestany.forestj.lib.ai.NeuralNetwork o_neuralNetwork = new net.forestany.forestj.lib.ai.NeuralNetwork(p_a_layers);
		o_neuralNetwork.loadWeights(p_s_neuralNetworkFile);
		
		java.util.List<Double> a_output = o_neuralNetwork.feedForwardPattern(a_input);
		java.util.List<String> a_keys = java.util.Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
		java.util.Map<String, Double> a_result = new java.util.LinkedHashMap<String, Double>();
		
		// transfer result
		for (int i = 0; i < a_output.size(); i++) {
			a_result.put(a_keys.get(i), a_output.get(i) * 100.0d);
		}
		
		// sort result
		java.util.Map<String, Double> a_resultSorted = new java.util.LinkedHashMap<>();
		a_result.entrySet().stream()
                .sorted(java.util.Map.Entry.<String, Double>comparingByValue().reversed())
                .forEachOrdered(x -> a_resultSorted.put(x.getKey(), x.getValue()));
		
		// print  best 5 result
		int i = 0;
		
		for (java.util.Map.Entry<String, Double> o_result : a_resultSorted.entrySet()) {
			System.out.println(o_result.getKey() + ": " + new java.text.DecimalFormat("0.0000").format(o_result.getValue()) + " %");
			
			i++;
			
			if (i >= 5) {
				break;
			}
		}
	}
	
	public static void testAIRandom(String p_s_filePathImage, String p_s_filePathLabel, int p_i_maxAmount, java.util.List<Integer> p_a_layers, String p_s_neuralNetworkFile) throws Exception {
		if (!net.forestany.forestj.lib.io.File.exists(p_s_filePathImage)) {
			throw new Exception("MNIST image file[" + p_s_filePathImage + "] does not exists");
		}
		
		if (!net.forestany.forestj.lib.io.File.exists(p_s_filePathLabel)) {
			throw new Exception("MNIST label file[" + p_s_filePathLabel + "] does not exists");
		}
		
		if (p_i_maxAmount < 1) {
			throw new Exception("Invalid max. amount parameter '" + p_i_maxAmount + "', must be at least '1'");
		}
		
		MNISTHandling o_mnistHandling = new MNISTHandling(new java.io.File(p_s_filePathImage), new java.io.File(p_s_filePathLabel));
		int i_random = net.forestany.forestj.lib.Helper.randomIntegerRange(1, p_i_maxAmount);
		int i_cnt = 0;
		
		while (o_mnistHandling.hasNext()) {
			/* read data from MNIST */
			o_mnistHandling.selectNext();
			
			if ((i_cnt++ + 1) == i_random) {
				break;
			}
		}
		
		byte by_label = o_mnistHandling.getCurrentLabelValue();
		byte[] a_pixels = o_mnistHandling.getDataAsBinaryArray(o_mnistHandling.getCurrentImageData());
		
		
		AITest.printBinaryPixelArray(a_pixels, o_mnistHandling.getImageHeight(), o_mnistHandling.getImageWidth());
		
		System.out.println();
		System.out.println("#" + (i_cnt + 1) + " of MNIST database is a '" + by_label + "'");
		System.out.println();
		
		java.util.List<Double> a_input = new java.util.ArrayList<Double>();
		
		int k = 0;
		
		for (int i = 0; i < o_mnistHandling.getImageHeight(); i++) {
			for (int j = 0; j < o_mnistHandling.getImageWidth(); j++) {
				if (a_pixels[k++] == 0) {
					a_input.add(0.0d);
				} else {
					a_input.add(1.0d);
				}
			}
		}
		
		if (!net.forestany.forestj.lib.io.File.exists(p_s_neuralNetworkFile)) {
			throw new Exception("Neural network file[" + p_s_neuralNetworkFile + "] does not exists");
		}
		
		net.forestany.forestj.lib.ai.NeuralNetwork o_neuralNetwork = new net.forestany.forestj.lib.ai.NeuralNetwork(p_a_layers);
		o_neuralNetwork.loadWeights(p_s_neuralNetworkFile);
		
		java.util.List<Double> a_output = o_neuralNetwork.feedForwardPattern(a_input);
		java.util.List<String> a_keys = java.util.Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
		java.util.Map<String, Double> a_result = new java.util.LinkedHashMap<String, Double>();
		
		// transfer result
		for (int i = 0; i < a_output.size(); i++) {
			a_result.put(a_keys.get(i), a_output.get(i) * 100.0d);
		}
		
		// sort result
		java.util.Map<String, Double> a_resultSorted = new java.util.LinkedHashMap<>();
		a_result.entrySet().stream()
                .sorted(java.util.Map.Entry.<String, Double>comparingByValue().reversed())
                .forEachOrdered(x -> a_resultSorted.put(x.getKey(), x.getValue()));
		
		// print  best 5 result
		int i = 0;
		
		for (java.util.Map.Entry<String, Double> o_result : a_resultSorted.entrySet()) {
			System.out.println(o_result.getKey() + ": " + new java.text.DecimalFormat("0.0000").format(o_result.getValue()) + " %");
			
			i++;
			
			if (i >= 5) {
				break;
			}
		}
	}
}
