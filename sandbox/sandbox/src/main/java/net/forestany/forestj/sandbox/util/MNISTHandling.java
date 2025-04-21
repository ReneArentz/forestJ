package net.forestany.forestj.sandbox.util;

public class MNISTHandling {
		
	/* Fields */
	
	private java.io.InputStream o_inputStreamFromFileImage;
	private java.io.InputStream o_inputStreamFromFileLabel;
	
	private int i_amount;
	private int i_index = -1;
	
	private int i_amountImage;
	private int i_imageWidth;
	private int i_imageHeight;
	private byte[] a_currentImageData;
	
	private int i_amountLabel;
	
	private byte by_currentLabelValue;
	
	/* Properties */
	
	public int getAmount() {
		return this.i_amount;
	}
	
	public boolean hasNext() {
		return this.i_index + 1 < this.i_amount;
	}
	
	
	public int getImageWidth() {
		return i_imageWidth;
	}

	public int getImageHeight() {
		return i_imageHeight;
	}
	
	public byte[] getCurrentImageData() {
		return a_currentImageData;
	}
	
	
	public byte getCurrentLabelValue() {
		return by_currentLabelValue;
	}
	
	/* Methods */
	
	public MNISTHandling(java.io.File p_o_fileImage, java.io.File p_o_fileLabel) throws java.io.IOException, RuntimeException {
		this.openFileImage(p_o_fileImage, 2051);

		/* read image height + width of all records */
		this.i_imageHeight = read_u_int32(this.o_inputStreamFromFileImage);
		this.i_imageWidth = read_u_int32(this.o_inputStreamFromFileImage);

		//System.out.println(p_o_fileImage.getName() + " contains " + this.i_amountImage + " images with " + this.i_imageWidth + "x" + this.i_imageHeight + " pixels");
		
		this.openFileLabel(p_o_fileLabel, 2049);
		
		//System.out.println(p_o_fileLabel.getName() + " contains " + this.i_amountLabel + " labels" );
		
		if (this.i_amountImage != this.i_amountLabel) {
			throw new RuntimeException("Amount of images[" + this.i_amountImage + "] differs from amount of labels[" + this.i_amountLabel + "]");
		} else {
			this.i_amount = this.i_amountImage;
		}
	}
	
	private void openFileImage(java.io.File p_o_file, int p_i_magic) throws java.io.IOException {
		/* open file input streams */
		if (p_o_file.getName().endsWith(".gz")) {
			this.o_inputStreamFromFileImage = new java.util.zip.GZIPInputStream(new java.io.FileInputStream(p_o_file));
		} else {
			this.o_inputStreamFromFileImage = new java.io.FileInputStream(p_o_file);
		}

		/* check header(magic number) */
		if (this.read_u_int32(this.o_inputStreamFromFileImage) != p_i_magic) {
			throw new java.io.IOException("Header(magic number) is invalid");
		}

		/* read amount of records */
		this.i_amountImage = this.read_u_int32(this.o_inputStreamFromFileImage);
	}
	
	private void openFileLabel(java.io.File p_o_file, int p_i_magic) throws java.io.IOException {
		/* open file input streams */
		if (p_o_file.getName().endsWith(".gz")) {
			this.o_inputStreamFromFileLabel = new java.util.zip.GZIPInputStream(new java.io.FileInputStream(p_o_file));
		} else {
			this.o_inputStreamFromFileLabel = new java.io.FileInputStream(p_o_file);
		}

		/* check header(magic number) */
		if (read_u_int32(this.o_inputStreamFromFileLabel) != p_i_magic) {
			throw new java.io.IOException("Header(magic number) is invalid");
		}

		/* read amount of records */
		this.i_amountLabel = read_u_int32(this.o_inputStreamFromFileLabel);
	}
	
	public void close() throws java.io.IOException {
		this.o_inputStreamFromFileLabel.close();
		this.o_inputStreamFromFileImage.close();
	}

	private void incrementtIndex() throws java.io.IOException {
		if (!this.hasNext()) {
			throw new java.io.IOException("cannot increment index - eof");
		}
		
		this.i_index++;
	}

	private int read_u_int32(java.io.InputStream p_o_inputStream) throws java.io.IOException {
		/* read u_int32 with 4 bytes */
		final byte[] by_temp = this.read(p_o_inputStream, new byte[4]);
		
		/* we must reverse byte order to read u_int32 to int value */
		return
			( ( (int) by_temp[0] ) & 0xFF ) << 24 |
			( ( (int) by_temp[1] ) & 0xFF ) << 16 |
			( ( (int) by_temp[2] ) & 0xFF ) << 8 |
			( ( (int) by_temp[3] ) & 0xFF );
	}

	private byte[] read(java.io.InputStream p_o_inputStream, byte[] data) throws java.io.IOException {
		int i = 0;
		
		/* read data from file stream, until all bytes have been set */
		do {
			int i_length = p_o_inputStream.read( data, i, data.length - i );
			
			/* check return value */
			if ( i_length < 1 ) {
				throw new java.io.IOException("cannot read data - eof");
			}
			
			i += i_length;
		} while (i < data.length);

		return data;
	}

	public byte[] getDataAsBinaryArray(byte[] p_a_values) {
		byte[] a_return = new byte[p_a_values.length];
		int i_index = 0;
		
		for (int y = 0; y < this.getImageHeight(); y++) {
			for (int x = 0; x < this.getImageWidth(); x++, i_index++) {
				if ( ( 255 - ( ( (int) p_a_values[i_index] ) & 0xFF ) ) >= 255) {
					a_return[i_index] = 0;
				} else {
					a_return[i_index] = 1;
				}
			}
		}
		
		return a_return;
	}
		
	public void selectNext() throws java.io.IOException {
		this.incrementtIndex();
		
		/* read next image data record */
		this.a_currentImageData = read(this.o_inputStreamFromFileImage, new byte[this.i_imageWidth * this.i_imageHeight]);
		
		/* read label with one byte */
		this.by_currentLabelValue = read(this.o_inputStreamFromFileLabel, new byte[1])[0];
	}
}
