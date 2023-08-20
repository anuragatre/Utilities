package com.majesco.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class DeriveUtil {

	private static String coverallHome;
	private static int coverallHomeCount=0;
	
	private static String PREHome;
	private static int PREHomeCount=0;
	
	private static String majescoHome;
	private static String javaPath;
	
	
	private static List<String> coverallHomeList=new ArrayList<String>();
	private static List<String> PREHomeList=new ArrayList<String>();
	
	public static void deviveHomes() throws Exception {
		File[] rootDrive = File.listRoots();
		   
		//Get cover-all home
		   for(File sysDrive : rootDrive){
			   System.out.println("Drive : " + sysDrive.getAbsolutePath());
			   
			   deriveCoverAllHome(sysDrive);
			   validateCoverAllHome();
			   derivePREHome(sysDrive);
			   validatePREHome();
		   
		   } 
	}

	private static void deriveCoverAllHome(File sysDrive) throws Exception {
		String coverAllPath = sysDrive.toString()+"cover-all";
		   String coverAllCheckPath = coverAllPath+File.separator+"config"+File.separator+"jdk.properties";
		   System.out.println("Coverall Home for search "+coverAllPath);
		   System.out.println("Coverall Home Check path "+coverAllCheckPath);
		   File f1 = new File(coverAllCheckPath);
		   

		   if(f1.exists()) {
			   coverallHome=coverAllPath.toString();
			   coverallHomeCount++;
			   coverallHomeList.add(coverallHome);

			   System.out.println("Cover all home found-"+coverallHome);
		   }
		   
		    
	}
	
	public static void derivePREHome(File sysDrive) throws Exception {
		String PREPath = sysDrive.toString()+"OfflineReports";
		   String PRECheckPath = PREPath+File.separator+"pre"+File.separator+"startEng.bat";
		   System.out.println("PRE Home for search "+PREPath);
		   System.out.println("PRE Home Check path "+PRECheckPath);
		   File f1 = new File(PRECheckPath);
		   

		   if(f1.exists()) {
			   PREHome=PREPath.toString();
			   PREHomeCount++;
			   PREHomeList.add(PREHome);

			   System.out.println("PRE home found-"+PREHome);
		   }
		   
		   validatePREHome();
		    
	}
	
	private static boolean validateCoverAllHome() throws Exception {
		boolean isValid = false;
	    if(null !=coverallHome) {
	        System.out.println("Cover all home - "+coverallHome);		        	
	        System.out.println("Cover all home instances found - "+coverallHomeList.size());		
	        
	        if(coverallHomeList.size()>1) {
	        	throw new Exception("More than one cover-all instance found");
	        }
	    }else {
	        	System.out.println("CoverAll Home not found...");
	    }
	    return isValid;
		
	}

	private static boolean validatePREHome() throws Exception {
		
		boolean isValid = false;
		
	    if(null !=PREHome) {
	        System.out.println("PRE home - "+PREHome);		        	
	        System.out.println("PRE Home instances found - "+PREHomeList.size());		
	        
	        if(PREHomeList.size()>1) {
	        	System.out.println("More than one PRE instance found");
	        }
	        
	    }else{
	        	System.out.println("PRE Home not found...");
	    }
	    return isValid;
	}	
	
	
	public static String getCoverAllHome() {
		if(coverallHomeList.size()==1) {
			return coverallHome;
		}else {
			return null;
		}
	}
	
	public static String getPREHome() {
		if(PREHomeList.size()==1) {
			return PREHome;
		}else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		try {
			deviveHomes();
			printHomes();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printHomes() {
		System.out.println("***********Printing Homes***********");
		if(null!=coverallHome) {
			System.out.println("CoverAllHome - "+coverallHome);
		}else {
			System.out.println("CoverAllHome Not Found");
		}
		
		if(null!=PREHome) {
			System.out.println("PREHome - "+PREHome);
		}else {
			System.out.println("PRE Home Not Found");
		}		
		
	}
	
}
