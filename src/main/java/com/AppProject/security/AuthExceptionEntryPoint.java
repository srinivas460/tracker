package com.AppProject.security;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint{

	/*@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		final Map<String, Object> mapBodyException = new HashMap<>() ;

        mapBodyException.put("error"    , "Error from AuthenticationEntryPoint") ;
        mapBodyException.put("message"  , "Message from AuthenticationEntryPoint") ;
        mapBodyException.put("exception", "My stack trace exception") ;
        mapBodyException.put("path"     , request.getServletPath()) ;
        mapBodyException.put("timestamp", (new Date()).getTime()) ;

        response.setContentType("application/json") ;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;

        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), mapBodyException) ;
		
	}*/
	
	@Override
	  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
	      throws IOException, ServletException {
	    // 401
//	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
	    final Map<String, Object> mapBodyException = new HashMap<>() ;

        mapBodyException.put("status"    ,1003) ;
        mapBodyException.put("message"  , "You are not Authorized to access the App") ;
        mapBodyException.put("exception", "SC_UNAUTHORIZED") ;
        mapBodyException.put("path"     , request.getServletPath()) ;
        mapBodyException.put("timestamp", (new Date()).getTime()) ;

        response.setContentType("application/json") ;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;

        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), mapBodyException) ;
//	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
	  }

	  @ExceptionHandler (value = {AccessDeniedException.class})
	  public void commence(HttpServletRequest request, HttpServletResponse response,
	      AccessDeniedException accessDeniedException) throws IOException {
	    // 403
//	    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Authorization Failed : " + accessDeniedException.getMessage());
		  final Map<String, Object> mapBodyException = new HashMap<>() ;

	        mapBodyException.put("status"    ,1003) ;
	        mapBodyException.put("message"  , "You are FORBIDDEN to access the App") ;
	        mapBodyException.put("exception", "SC_UNAUTHORIZED") ;
	        mapBodyException.put("path"     , request.getServletPath()) ;
	        mapBodyException.put("timestamp", (new Date()).getTime()) ;

	        response.setContentType("application/json") ;
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED) ;

	        final ObjectMapper mapper = new ObjectMapper() ;
	        mapper.writeValue(response.getOutputStream(), mapBodyException) ;
	  }

	  @ExceptionHandler (value = {Exception.class})
	  public void commence(HttpServletRequest request, HttpServletResponse response,
	      Exception exception) throws IOException {
	     // 500
//	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error : " + exception.getMessage());
	    final Map<String, Object> mapBodyException = new HashMap<>() ;

        mapBodyException.put("status"    ,1003) ;
        mapBodyException.put("message"  , "Internal Server Error has occured : "+exception.getMessage()) ;
        mapBodyException.put("exception", "SC_INTERNAL_SERVER_ERROR") ;
        mapBodyException.put("path"     , request.getServletPath()) ;
        mapBodyException.put("timestamp", (new Date()).getTime()) ;

        response.setContentType("application/json") ;
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR) ;

        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), mapBodyException) ;
	  }

}
