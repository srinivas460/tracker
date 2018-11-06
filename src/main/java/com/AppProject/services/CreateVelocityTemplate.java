package com.AppProject.services;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class CreateVelocityTemplate {

	public String getTemplate(String template, VelocityContext velocityContext) {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty("file.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		/* next, get the Template */
		Template t = ve.getTemplate("templates/" + template);
		/* create a context and add data */
		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(velocityContext, writer);
		/* show the World */
		return (writer.toString());
	}

}
