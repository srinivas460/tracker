package com.AppProject.Utils;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class Utils {

	public static char[] generatePassword(int len) 
    { 
        System.out.println("Generating password using random() : "); 
        System.out.print("Your new password is : "); 
  
        // A strong password has Cap_chars, Lower_chars, 
        // numeric value and symbols. So we are using all of 
        // them to generate our password 
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
        String Small_chars = "abcdefghijklmnopqrstuvwxyz"; 
        String numbers = "0123456789"; 
                String symbols = "!@#$%^&*_=+-/.?<>)"; 
  
  
        String values = Capital_chars + Small_chars + 
                        numbers + symbols; 
  
        // Using random method 
        Random rndm_method = new Random(); 
  
        char[] password = new char[len]; 
  
        for (int i = 0; i < len; i++) 
        { 
            // Use of charAt() method : to get character value 
            // Use of nextInt() as it is scanning the value as int 
            password[i] = 
              values.charAt(rndm_method.nextInt(values.length())); 
  
        } 
        return password; 
    }
	
	public static String getPassword(int length,boolean useLetters,boolean useNumbers) {
	   return RandomStringUtils.random(length, useLetters, useNumbers);
	}
	public static String generateRandomObjectKey() {
		return "JOB_" + RandomStringUtils.random(7, true, true).toUpperCase();
	}
}
