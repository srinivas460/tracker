package AppProject.AppProject;

import java.util.Base64;

import org.apache.commons.lang3.RandomStringUtils;

import com.AppProject.entities.User;

public class TestCae {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String encoding = Base64.getEncoder().encodeToString(("9676462929:"+User.PASSWORD_ENCODER.encode("dummy")).getBytes());
		System.err.println("TOKEN GENERATED : "+encoding);
		
		 int length = 6;
		    boolean useLetters = true;
		    boolean useNumbers = false;
		    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		 
		    System.out.println(generatedString);
		
	}

}
