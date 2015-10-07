package org.got5.tapestry5.xiti.mixins;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ClientElement;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.xiti.utils.XitiConstants;

public class XtForm {
	/**
	 * The tapestry-xiti js script
	 * */
	@Inject
	@Path(value="classpath:js/tapestry-xiti.js")
	private Asset script;
	
	/**
	 * The goal's id
	 * */
	@Parameter(required=true, defaultPrefix = BindingConstants.LITERAL)
	private String label;
	
	/**
	 * The goal's score
	 * */
	@Parameter(required=true, defaultPrefix = BindingConstants.LITERAL)
	private int level;
	
	/**
	 * The xiti 'xt_custom' param
	 * */
	@Parameter(required=false, defaultPrefix = BindingConstants.LITERAL)
	private JSONObject custom;
	
	@Inject
	private JavaScriptSupport javascriptSupport;
	
	@Inject
	private Request request;
	
	@Inject
	private ComponentResources resources;
	
	@InjectContainer
	private ClientElement element;
	
	/**
	 * Add the tapestry-xiti.js script
	 * */
	@SetupRender
	void addLibraries(){
		javascriptSupport.importJavaScriptLibrary(script);
	}
	
	@AfterRender
	void addScriptInitialization() {
		JSONObject spec = new JSONObject();
		spec.put("elementId", element.getClientId());
		spec.put("label", label);
		spec.put("level", level);
		String type = "N";
		// set the xiti click type to 'A' if we have to handle an ajax request 
		if(resources.getContainerResources().isBound("zone")){
			type = "A";
		}
		spec.put("type", type);
		spec.put("custom", custom);
		javascriptSupport.addInitializerCall("xtForm", spec);
		//xt_form(this ,'C',level, label, type ,false);
	}

}
