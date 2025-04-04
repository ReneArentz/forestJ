package net.forestany.forestj.lib.io;

/**
 * 
 * ZIP class with static methods to get size of an archive or check archive if its content is valid.
 * can zip and unzip archives, only supporting zip format.
 *
 */
public class ZIP {

	/* Delegates */
	
	/**
	 * interface delegate definition which can be instanced outside of ZIP class to post progress anywhere of ZIP methods
	 */
	public interface IDelegate {
		/**
		 * method to post progress while zip/unzip method is executed
		 * 
		 * @param p_d_progress		percent progress as a double
		 */
		void PostProgress(double p_d_progress);
	}
	
	/* Fields */
	
	/* Properties */
	
	/* Methods */
	
	/**
	 * empty constructor
	 */
	public ZIP() {
		
	}
	
	/**
	 * get uncompressed size of an zip archive file
	 * 
	 * @param p_s_sourceLocation			full path to zip archive file
	 * @return								byte length of uncompressed archive file content as long value
	 * @throws IllegalArgumentException		full path does not end with '.zip' or file does not exist
	 * @throws java.io.IOException			error reading zip archive content
	 * @throws IllegalStateException		could not close zip input stream
	 * @see Long
	 */
	public static long getSize(String p_s_sourceLocation) throws IllegalArgumentException, java.io.IOException, IllegalStateException {
		long l_return = 0;
		
		/* check if source location ends with '.zip' */
    	if (!p_s_sourceLocation.endsWith(".zip")) {
    		throw new IllegalArgumentException("Invalid source location[" + p_s_sourceLocation + "] - must end with '.zip'");
    	}
    	
    	/* check if source location really exists */
    	if (!net.forestany.forestj.lib.io.File.exists(p_s_sourceLocation)) {
    		throw new IllegalArgumentException("Source location[" + p_s_sourceLocation + "] does not exist");
    	}
    	
		/* stream to read out of zip file */
		java.util.zip.ZipInputStream o_zipInputStream = null;
        
		try {
        	/* create input stream instance */
            o_zipInputStream = new java.util.zip.ZipInputStream(new java.io.FileInputStream(p_s_sourceLocation));
            
            /* get first zip entry */
            java.util.zip.ZipEntry o_zipEntry = o_zipInputStream.getNextEntry();
            
            /* iterate all files within zip */
            while (o_zipEntry != null) {
            	/* help variables to read and write stream instances */
            	byte[] a_buffer = new byte[8192];
                int i_length;
                
                										net.forestany.forestj.lib.Global.ilogMass("start reading file length of '" + o_zipEntry.getName() + "'");
                
                /* read from input zip stream */
                while ((i_length = o_zipInputStream.read(a_buffer)) > 0) {
                	/* sum up size */
                	l_return += i_length;
                }
            	
                										net.forestany.forestj.lib.Global.ilogMass("finished reading file length of '" + o_zipEntry.getName() + "'");
                
                /* iterate to next zip file in archive */
                o_zipEntry = o_zipInputStream.getNextEntry();
            }
        } finally {
            /* check if input stream instance is not null */
            if (o_zipInputStream != null) {
                try {
                	/* close all handles */
                    o_zipInputStream.closeEntry();
                    o_zipInputStream.close();
                } catch (Exception o_exc) {
                	throw new IllegalStateException("Could not close zip input stream: " + o_exc.getMessage());
                }
            }
        }
		
		return l_return;
	}
	
	/**
	 * check an zip archive file if it's content is valid
	 * 
	 * @param p_s_sourceLocation			full path to zip archive file
	 * @return								boolean, true - archive is valid, false - archive is invalid
	 * @throws IllegalArgumentException		full path does not end with '.zip' or file does not exist
	 * @throws java.io.IOException			error reading zip archive content
	 * @throws IllegalStateException		could not close zip input stream or zip file object
	 * @see Long
	 */
	public static boolean checkArchive(String p_s_sourceLocation) throws IllegalArgumentException, java.io.IOException, IllegalStateException {
		return ZIP.checkArchive(p_s_sourceLocation, null);
	}
	
	/**
	 * check an zip archive file if it's content is valid
	 * 
	 * @param p_s_sourceLocation			full path to zip archive file
	 * @param p_itf_delegate				interface delegate to post progress of check archive method
	 * @return								boolean, true - archive is valid, false - archive is invalid
	 * @throws IllegalArgumentException		full path does not end with '.zip' or file does not exist
	 * @throws java.io.IOException			error reading zip archive content
	 * @throws IllegalStateException		could not close zip input stream or zip file object
	 * @see Long
	 */
	public static boolean checkArchive(String p_s_sourceLocation, ZIP.IDelegate p_itf_delegate) throws IllegalArgumentException, java.io.IOException, IllegalStateException {
		boolean b_return = true;
		
		/* check if source location ends with '.zip' */
    	if (!p_s_sourceLocation.endsWith(".zip")) {
    		throw new IllegalArgumentException("Source location[" + p_s_sourceLocation + "] must end with '.zip'");
    	}
    	
    	/* check if source location really exists */
    	if (!net.forestany.forestj.lib.io.File.exists(p_s_sourceLocation)) {
    		throw new IllegalArgumentException("Source location[" + p_s_sourceLocation + "] does not exist");
    	}
    	
		/* stream to read out of zip file */
		java.util.zip.ZipInputStream o_zipInputStream = null;
        
		/* zip file variable */
		java.util.zip.ZipFile o_zipFile = null;
		
        try {
        	/* create new instance of zip file variable */
        	o_zipFile = new java.util.zip.ZipFile(p_s_sourceLocation);

        	/* variable for uncompressed zip archive file size  */
        	long l_overallSum = 0;
        	
        	/* get size of zip file container content, but only if we need to show progress - otherwise it is wasting resources */
        	if (p_itf_delegate != null) {
        		l_overallSum = ZIP.getSize(p_s_sourceLocation);
        	}
        	
        	/* create input stream instance */
            o_zipInputStream = new java.util.zip.ZipInputStream(new java.io.FileInputStream(p_s_sourceLocation));
            
            /* get first zip entry */
            java.util.zip.ZipEntry o_zipEntry = o_zipInputStream.getNextEntry();
            
            /* variable for sum of all read bytes in zip */
            long l_sum = 0;
            
            /* iterate all files within zip */
            while (o_zipEntry != null) {
                /* help variables to read and write stream instances */
                byte[] a_buffer = new byte[8192];
                int i_length;
                
                										net.forestany.forestj.lib.Global.ilogMass("start checking file in archive '" + o_zipEntry.getName() + "'");
                
                /* read from input zip stream */
                while ((i_length = o_zipInputStream.read(a_buffer)) > 0) {
                	if (p_itf_delegate != null) {
                		l_sum += i_length;
                		
                		/* post progress of check archive method */
                    	p_itf_delegate.PostProgress( (double)l_sum / l_overallSum );
        			}
                }
                
                										net.forestany.forestj.lib.Global.ilogMass("finished checking file in archive '" + o_zipEntry.getName() + "'");
                
                /* iterate to next zip file in archive */
                o_zipEntry = o_zipInputStream.getNextEntry();
            }
        } catch (Exception o_exc) {
        	/* some failure happened during reading archive, so we set our return value to false */
        	b_return = false;
        } finally {
            /* check if input stream instance is not null */
            if (o_zipInputStream != null) {
                try {
                	/* close all handles */
                    o_zipInputStream.closeEntry();
                    o_zipInputStream.close();
                } catch (java.io.IOException o_exc) {
                	throw new IllegalStateException("Could not close zip input stream: " + o_exc.getMessage());
                }
            }
            
            /* check if zip file variable is not null */
            if (o_zipFile != null) {
                try {
                	/* close all handles */
                    o_zipFile.close();
                    o_zipFile = null;
                } catch (java.io.IOException o_exc) {
                	throw new IllegalStateException("Could not close zip file object: " + o_exc.getMessage());
                }
            }
        }
		
		return b_return;
	}
	
	/**
	 * zip a source file or directory location to an archive file
	 * 
	 * @param p_s_sourceLocation			full path to source file/directory location
	 * @param p_s_destinationLocation		full path to destination zip archive file
	 * @throws IllegalArgumentException		full path does not end with '.zip'
	 * @throws java.io.IOException			error creating zip archive
	 * @throws IllegalStateException		could not close zip input stream or zip file object
	 * @see Long
	 */
	public static void zip(String p_s_sourceLocation, String p_s_destinationLocation) throws IllegalArgumentException, java.io.IOException, IllegalStateException {
		ZIP.zip(p_s_sourceLocation, p_s_destinationLocation, null);
	}
	
	/**
	 * zip a source file or directory location to an archive file
	 * 
	 * @param p_s_sourceLocation			full path to source file/directory location
	 * @param p_s_destinationLocation		full path to destination zip archive file
	 * @param p_itf_delegate				interface delegate to post progress of zip method
	 * @throws IllegalArgumentException		full path does not end with '.zip'
	 * @throws java.io.IOException			error creating zip archive
	 * @throws IllegalStateException		could not close zip input stream or zip file object
	 * @see Long
	 */
	public static void zip(String p_s_sourceLocation, String p_s_destinationLocation, ZIP.IDelegate p_itf_delegate) throws IllegalArgumentException, java.io.IOException, IllegalStateException {
		/* check if destination location ends with '.zip' */
		if (!p_s_destinationLocation.endsWith(".zip")) {
    		throw new IllegalArgumentException("Destination location[" + p_s_destinationLocation + "] must end with '.zip'");
    	}
    	
		/* stream to write into zip file */
    	java.util.zip.ZipOutputStream o_zipOutputStream = null;
        
        try {
        	/* create output stream instance */
            o_zipOutputStream = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(p_s_destinationLocation));
            
            /* variable for sum of all bytes which will be zipped */
            long l_overallSum = 0;
			
            /* only sum up all bytes if we want progress, otherwise it's wasting resources */
            if (p_itf_delegate != null) {
				for (ListingElement o_listingElement : net.forestany.forestj.lib.io.File.listDirectory(p_s_sourceLocation, true)) {
					/* add file size to sum */
					l_overallSum += o_listingElement.getSize();
				}
            }
            
            /* variable for sum of all read bytes in zip */
            long l_sum = 0;
            
            /* iterate file or all files of a directory */
            for (ListingElement o_listingElement : net.forestany.forestj.lib.io.File.listDirectory(p_s_sourceLocation, true)) {
            	/* skip directory listing elements */
            	if (o_listingElement.getIsDirectory()) {
            		continue;
            	}
            	
            	/* skip zip file we want to create, if it is in our source location itself */
                if (o_listingElement.getFullName().contentEquals(p_s_destinationLocation))
                {
                    continue;
                }
            	
            	/* create a new zip entry with filename */
            	java.util.zip.ZipEntry o_zipEntry = new java.util.zip.ZipEntry(o_listingElement.getName());
            	
            											net.forestany.forestj.lib.Global.ilogMass("start zipping file to archive '" + o_zipEntry.getName() + "'");
            	
            	/* overwrite zip entry file name, if we have multiple files and recursive directories */
            	if (!o_listingElement.getFullName().contentEquals(p_s_sourceLocation)) {
            		o_zipEntry = new java.util.zip.ZipEntry(o_listingElement.getFullName().substring(p_s_sourceLocation.length()));
            	}
            	
            	/* set size information of zip entry */
            	o_zipEntry.setSize(o_listingElement.getSize());
            	
            	/* add zip entry to output stream instance */
            	o_zipOutputStream.putNextEntry(o_zipEntry);
                
                /* create input stream instance */
                java.io.FileInputStream o_fileInputStream = new java.io.FileInputStream(o_listingElement.getFullName());
                
                /* help variables to read and write stream instances */
                byte[] a_buffer = new byte[8192];
                int i_length;
                
                /* read from input file stream */
                while ((i_length = o_fileInputStream.read(a_buffer)) > 0) {
                	/* write in output zip stream */
                    o_zipOutputStream.write(a_buffer, 0, i_length);
                    
                    if (p_itf_delegate != null) {
                		l_sum += i_length;
                		
                		/* post progress of zip method */
                    	p_itf_delegate.PostProgress( (double)l_sum / l_overallSum );
        			}
                }
                
                /* close input stream instance to finish zip entry file */
                o_fileInputStream.close();
                
                /* set input stream instance to null for cleaning up memory */
                o_fileInputStream = null;
                
                										net.forestany.forestj.lib.Global.ilogMass("finished zipping file to archive '" + o_zipEntry.getName() + "'");
            }
        } finally {
        	/* check if output stream instance is not null */
            if (o_zipOutputStream != null) {
                try {
                	/* close all handles */
                    o_zipOutputStream.closeEntry();
                    o_zipOutputStream.close();
                } catch (java.io.IOException o_exc) {
                	throw new IllegalStateException("Could not close zip input stream: " + o_exc.getMessage());
                }
            }
        }
    }
	
	/**
	 * unzip a source zip file archive to a destination location, does not create destination path or delete source zip archive file
	 * 
	 * @param p_s_sourceLocation			full path to source zip archive file
	 * @param p_s_destinationLocation		full path to destination file/directory location
	 * @throws IllegalArgumentException		source location does not end with '.zip' or does not exist, destination location does not exist or is not a directory
	 * @throws java.io.IOException			error uncompressing content of source zip archive
	 * @throws IllegalStateException		could not close zip input stream or zip file object
	 * @see Long
	 */
	public static void unzip(String p_s_sourceLocation, String p_s_destinationLocation) throws IllegalArgumentException, java.io.IOException, IllegalStateException {
		ZIP.unzip(p_s_sourceLocation, p_s_destinationLocation, false, false, null);
	}
	
	/**
	 * unzip a source zip file archive to a destination location, does not create destination path or delete source zip archive file
	 * 
	 * @param p_s_sourceLocation			full path to source zip archive file
	 * @param p_s_destinationLocation		full path to destination file/directory location
	 * @param p_itf_delegate				interface delegate to post progress of zip method
	 * @throws IllegalArgumentException		source location does not end with '.zip' or does not exist, destination location does not exist or is not a directory
	 * @throws java.io.IOException			error uncompressing content of source zip archive
	 * @throws IllegalStateException		could not close zip input stream or zip file object
	 * @see Long
	 */
	public static void unzip(String p_s_sourceLocation, String p_s_destinationLocation, ZIP.IDelegate p_itf_delegate) throws IllegalArgumentException, java.io.IOException, IllegalStateException {
		ZIP.unzip(p_s_sourceLocation, p_s_destinationLocation, false, false, p_itf_delegate);
	}
	
	/**
	 * unzip a source zip file archive to a destination location
	 * 
	 * @param p_s_sourceLocation			full path to source zip archive file
	 * @param p_s_destinationLocation		full path to destination file/directory location
	 * @param p_b_createDestinationPath		flag to create destination path automatically
	 * @param p_b_deleteSourceLocation		flag to delete zip file after content has been uncompressed
	 * @throws IllegalArgumentException		source location does not end with '.zip' or does not exist, destination location does not exist or is not a directory
	 * @throws java.io.IOException			error uncompressing content of source zip archive
	 * @throws IllegalStateException		could not close zip input stream or zip file object
	 * @see Long
	 */
	public static void unzip(String p_s_sourceLocation, String p_s_destinationLocation, boolean p_b_createDestinationPath, boolean p_b_deleteSourceLocation) throws IllegalArgumentException, java.io.IOException, IllegalStateException {
		ZIP.unzip(p_s_sourceLocation, p_s_destinationLocation, p_b_createDestinationPath, p_b_deleteSourceLocation, null);
	}
	
	/**
	 * unzip a source zip file archive to a destination location
	 * 
	 * @param p_s_sourceLocation			full path to source zip archive file
	 * @param p_s_destinationLocation		full path to destination file/directory location
	 * @param p_b_createDestinationPath		flag to create destination path automatically
	 * @param p_b_deleteSourceLocation		flag to delete zip file after content has been uncompressed
	 * @param p_itf_delegate				interface delegate to post progress of zip method
	 * @throws IllegalArgumentException		source location does not end with '.zip' or does not exist, destination location does not exist or is not a directory
	 * @throws java.io.IOException			error uncompressing content of source zip archive
	 * @throws IllegalStateException		could not close zip input stream or zip file object
	 * @see Long
	 */
    public static void unzip(String p_s_sourceLocation, String p_s_destinationLocation, boolean p_b_createDestinationPath, boolean p_b_deleteSourceLocation, ZIP.IDelegate p_itf_delegate) throws IllegalArgumentException, java.io.IOException, IllegalStateException {
    	/* check if source location ends with '.zip' */
    	if (!p_s_sourceLocation.endsWith(".zip")) {
    		throw new IllegalArgumentException("Source location[" + p_s_sourceLocation + "] must end with '.zip'");
    	}
    	
    	/* check if source location really exists */
    	if (!net.forestany.forestj.lib.io.File.exists(p_s_sourceLocation)) {
    		throw new IllegalArgumentException("Source location[" + p_s_sourceLocation + "] does not exist");
    	}
    	
    	/* check if destination location really exists */
    	if (!net.forestany.forestj.lib.io.File.folderExists(p_s_destinationLocation)) {
    		/* if create flag has not been set, throw exception */
    		if (!p_b_createDestinationPath) {
    			throw new IllegalArgumentException("Destination location[" + p_s_destinationLocation + "] does not exist");
    		} else {
    			/* otherwise create destination location */
    			net.forestany.forestj.lib.io.File.createDirectory(p_s_destinationLocation);
    		}
    	}
    	    	
    	/* check if destination location is a directory */
		if (!net.forestany.forestj.lib.io.File.isDirectory(p_s_destinationLocation)) {
			throw new IllegalArgumentException("Destination location[" + p_s_destinationLocation + "] must be a 'directory'");
		}
		
		/* stream to read out of zip file */
		java.util.zip.ZipInputStream o_zipInputStream = null;
        
		try {
			/* variable for uncompressed zip archive file size  */
        	long l_overallSum = 0;
        	
        	/* get size of zip file container content, but only if we need to show progress - otherwise it is wasting resources */
        	if (p_itf_delegate != null) {
        		l_overallSum = ZIP.getSize(p_s_sourceLocation);
        	}
			
        	/* create input stream instance */
            o_zipInputStream = new java.util.zip.ZipInputStream(new java.io.FileInputStream(p_s_sourceLocation));
            
            /* get first zip entry */
            java.util.zip.ZipEntry o_zipEntry = o_zipInputStream.getNextEntry();
            
            /* variable for sum of all read bytes in zip */
            long l_sum = 0;
            
            /* iterate all files within zip */
            while (o_zipEntry != null) {
            	/* get zip entry path */
            	String s_pathParentFolder = java.nio.file.Paths.get(p_s_destinationLocation + net.forestany.forestj.lib.io.File.DIR + o_zipEntry.getName()).toFile().getParent();
            	
            	/* check if path to parent folder ends with directory separator */
            	if (!s_pathParentFolder.endsWith(net.forestany.forestj.lib.io.File.DIR)) {
            		s_pathParentFolder += net.forestany.forestj.lib.io.File.DIR;
            	}
            	
            	/* check if path exists */
				if (!net.forestany.forestj.lib.io.File.folderExists(s_pathParentFolder)) {
					/* create path */
					net.forestany.forestj.lib.io.File.createDirectory(s_pathParentFolder);
				}
            	
                /* check if destination file can be created */
            	java.nio.file.Path o_path = java.nio.file.Files.createFile(java.nio.file.Paths.get(p_s_destinationLocation + net.forestany.forestj.lib.io.File.DIR + o_zipEntry.getName()));
				
            	/* check if destination file exists */
				if (!net.forestany.forestj.lib.io.File.exists(p_s_destinationLocation + net.forestany.forestj.lib.io.File.DIR + o_zipEntry.getName())) {
					throw new java.io.IOException("Could not create file[" + p_s_destinationLocation + net.forestany.forestj.lib.io.File.DIR + o_zipEntry.getName() + "]");
				}
                
				/* create output stream instance */
				java.io.FileOutputStream o_fileOutputStream = new java.io.FileOutputStream(o_path.toFile().getAbsoluteFile().toString());
                
				/* help variables to read and write stream instances */
                byte[] a_buffer = new byte[8192];
                int i_length;
                
                										net.forestany.forestj.lib.Global.ilogMass("start unzipping file from archive '" + o_zipEntry.getName() + "'");
                
                /* read from input zip stream */
                while ((i_length = o_zipInputStream.read(a_buffer)) > 0) {
                	/* write in output file stream */
                    o_fileOutputStream.write(a_buffer, 0, i_length);
                    
                    if (p_itf_delegate != null) {
                		l_sum += i_length;
                    	
                    	/* post progress of unzip method */
                    	p_itf_delegate.PostProgress( (double)l_sum / l_overallSum );
        			}
                }
                
                										net.forestany.forestj.lib.Global.ilogMass("finished unzipping file from archive '" + o_zipEntry.getName() + "'");
                
                /* close output stream instance to finish destination file */
                o_fileOutputStream.close();
                
                /* iterate to next zip file in archive */
                o_zipEntry = o_zipInputStream.getNextEntry();
            }
        } finally {
            /* check if input stream instance is not null */
            if (o_zipInputStream != null) {
                try {
                	/* close all handles */
                    o_zipInputStream.closeEntry();
                    o_zipInputStream.close();
                } catch (java.io.IOException o_exc) {
                	throw new IllegalStateException("Could not close zip input stream: " + o_exc.getMessage());
                }
            }
        }
        
        /* check if we should delete source location */
        if (p_b_deleteSourceLocation) {
        	/* delete source location */
        	net.forestany.forestj.lib.io.File.deleteFile(p_s_sourceLocation);
        }
    }
}
