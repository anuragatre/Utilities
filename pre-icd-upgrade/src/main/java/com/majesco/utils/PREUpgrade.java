package com.majesco.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class PREUpgrade {
	
	private static String PREHome;
	private static String utilHome;
	

	public static void main(String[] args) {
		System.out.println("Test PREUpgrade main");
		
		
		
		try {
			
			String jarPath = args[0];
			System.out.println("incoming jarPath-"+jarPath);
			
			File fPath = new File(jarPath);
			if(fPath.exists()) {
				System.out.println("valid jarPath-"+jarPath);
	            String jarName = jarPath.substring(jarPath.lastIndexOf("\\") + 1);
	            System.out.println("Jar Name-"+jarName);
	            utilHome = jarPath.substring(0, jarPath.lastIndexOf("\\"));
			}else {
				throw new Exception("Input Jar path is not valid");
			}
			
			if(args.length>1) {
				String prePath = args[1];
				System.out.println("incoming prePath-"+prePath);
				File f = new File(prePath);
				if(f.exists()) {
					System.out.println("valid prePath-"+prePath);	
				}else {
					System.out.println("prePath to be derived..inner.");
					derivePrePath();
				}
				
			}else {
				System.out.println("prePath to be derived...");
				derivePrePath();
			}
			
			//Replace the ojdbc8.jar with latest one.
			if(PREHome!=null) {
			
	            String toFile = PREHome+"/lib/ojdbc8.jar";
	            
				Path fromFilePath = Paths.get(jarPath);
		        Path toFilePath = Paths.get(toFile);
		        
		        System.out.println("Jar parent folder -"+utilHome);
	
		        System.out.println("JAR Parent Folder: " + utilHome);
		        
		        System.out.println("From -"+fromFilePath.toAbsolutePath().toString() +" , To-"+toFilePath.toAbsolutePath().toString());
		        // if target or destination file exists, replace it.
		        Files.copy(fromFilePath, toFilePath, StandardCopyOption.REPLACE_EXISTING);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void derivePrePath() throws Exception {
		System.out.println("Inside derivePrePath...");
		DeriveUtil.deviveHomes();
		PREHome = DeriveUtil.getPREHome();
		String coverAllHome = DeriveUtil.getCoverAllHome();
		System.out.println("Inside PREUPgrade.java , PREHome is - "+PREHome);
		System.out.println("Inside PREUPgrade.java , coverallHome is - "+coverAllHome);
		
		//Put the PREHome to a input.dat file
		
		String inputParam = "PREHomePath="+PREHome;
		System.out.println("Util home-"+utilHome+"\\input.dat");
		File inputDat = new File(utilHome+"\\input.dat");
		inputDat.createNewFile();
		FileWriter fw = new FileWriter(inputDat);
		try {
			fw.write(inputParam);
		} finally {
			fw.close();
		}
		
	
	}

}
