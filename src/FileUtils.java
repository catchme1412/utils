package com.raj.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

public class FileUtils {
	
	public static File[] getFiles(String path) {
		// It is also possible to filter the list of returned files.
	    // This example does not return any files that start with `.'.
	    FilenameFilter filter = new FilenameFilter() {
	        public boolean accept(File dir, String name) {
	            return !name.startsWith(".");
	        }
	    };
	    
	    File dir = new File(path);

	    return dir.listFiles(filter);
	}

	public static File[] getFiles(String path, final String fileNameEndingWith) {
		// It is also possible to filter the list of returned files.
	    // This example does not return any files that start with `.'.
	    FilenameFilter filter = new FilenameFilter() {
	        public boolean accept(File dir, String name) {
	            return name.endsWith(fileNameEndingWith);
	        }
	    };
	    
	    File dir = new File(path);

	    return dir.listFiles(filter);
	}
	
	public static FileInputStream getFileStream(String fileName) throws FileNotFoundException{
		return new FileInputStream(fileName);
	}
	
}
