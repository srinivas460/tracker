package com.AppProject.Utils;

import java.util.regex.Pattern;

public class Constants {

	//Response Status codes below

	public static final int CODE_ERROR	=1003;
	public static final int CODE_INFO	=1004;
	public static final int CODE_SUCCESS=1005;
	public static final int CODE_RESET=1006;
	
	public static boolean isEmail(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    }
	
	public static boolean isMobile(String mobilenumber) 
    { 
        String emailRegex = "^\\+[1-9]{1}[0-9]{3,14}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (mobilenumber == null) 
            return false; 
        return pat.matcher(mobilenumber).matches(); 
    }
}
