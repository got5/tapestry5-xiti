package org.got5.tapestry5.xiti.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.internal.test.TestableResponse;
import org.apache.tapestry5.test.PageTester;
import org.got5.tapestry5.xiti.services.AppAutoLoadModule;
import org.got5.tapestry5.xiti.services.AppModule;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * Test the @Xiti annotation with its attributes
 * Test the "XtClick" and "XtForm" mixins
 * Check that the appropriate stack is imported.
 * */
public class XitiTest {
	
	private PageTester tester;
	
	private PageTester tester2;
	
	private static final String[] defaultParams 		= {"document", "9999", "8888", "", "", "", "", ""}; 
	
	private static final String[] annotationParams 	= {"document", "1234", "4321", "354", "3", "&x1=12345&x2=49367", "8765", "5678"}; 
	
	public XitiTest() {
		tester = new PageTester("org.got5.tapestry5.xiti", "AppAutoLoad",
				"src/test/webapp", AppAutoLoadModule.class);
		tester2 = new PageTester("org.got5.tapestry5.xiti", "App",
				"src/test/webapp", AppModule.class);
	}
	
	@AfterClass
	public void shutdown() {
		tester.shutdown();
		tester2.shutdown();
	}
	
	/**
	 * Check that the "Xiti" script is loaded with default params set in AppModule
	 * */
	@Test
	public void checkXitiDefaultParams() {
		String pageName = "EmptyPage";
		Document result = tester.renderPage(pageName);
		assert result.getRootElement() != null : "The document is empty";
		String markup = result.getRootElement().getChildMarkup();
		checkJsStack(markup, "" /* no page label for default params */, false, defaultParams);
	}
	

	/**
	 * Check that the "Xiti" script is NOT loaded
	 * */
	@Test
	public void checkXitiScriptNotLoaded() {
		String pageName = "EmptyPage";
		Document result = tester2.renderPage(pageName);
		assert result.getRootElement() != null : "The document is empty";
		String markup = result.getRootElement().getChildMarkup();
		assert !markup.contains("/js/xtcore.js") : "'xtcore.js' is loaded even that the page is not annotated and automatic script load is disabled";
	}
	
	/**
	 * Check that the "Xiti" annotation adds effectively the appropriate script with parameters 
	 * according to annotation attributes (override default paremeters set in AppModule)
	 * */
	@Test
	public void checkXitiAnnotation() {
		checkXitiAnnotation(tester);
	}
	
	/**
	 * Check that the "Xiti" annotation adds effectively the appropriate script with parameters 
	 * according to annotation attributes (override default paremeters set in AppModule)
	 * Even that automatic script load is disabled
	 * */
	@Test
	public void checkXitiAnnotationWithAutoLoadDisabled() {
		checkXitiAnnotation(tester2);
	}

	private void checkXitiAnnotation(PageTester tester){  
		String pageName = "Index";
		Document result = tester.renderPage(pageName);
		assert result.getRootElement() != null : "The document is empty";
		String markup = result.getRootElement().getChildMarkup();
		checkJsStack(markup, pageName, false, annotationParams);
	}	

	/**
	 * Check that the function "xtClick()" is called with the appropriate parameters
	 * Check that the actionlink without ajax works correctly 
	 * */
	@Test
	public void checkXtSimpleClickMixins(){
		String pageName = "SimpleClick";
		Document result = tester.renderPage(pageName);
		assert result.getRootElement() != null : "The document is empty";
		
		// Test the XtClick mixins with simple request
		checkXitiClickMixins(result, pageName, "click", "myXitiLabel", "32", false /* no ajax */, false /* no js callback */);
	}
	
	
	/**
	 * Check that the function "xtClick()" is called with the appropriate parameters
	 * Check that the actionlink with ajax works correctly 
	 * */
	@Test
	public void checkXtAjaxClickMixins(){
		String pageName = "AjaxClick";
		Document result = tester.renderPage(pageName);
		assert result.getRootElement() != null : "The document is empty";
		
		// Test the XtClick mixins with an ajax request
		checkXitiClickMixins(result, pageName, "ajaxSimpleClick", "myXitiSimpleAjaxLabel", "45", true /* ajax */, false/* no js callback */);
				
	}
	
	
	/**
	 * Check that the function "xtClick()" is called with the appropriate parameters
	 * Check that the actionlink with ajax and Javascript Callback works correctly 
	 * */
	//@Test 
	// Unfortunately, PageTester seems to not be able to handle Javascript Callbacks ! 
	// When clicking at the link via PageTester, it says : "Page must be specified before initializing for partial page render" 
	// BUT * the same test works perfectly manually * 
	public void checkXtAjaxClickWithCallBackMixins(){
		String pageName = "AjaxClickWithCallBack";
		Document result = tester.renderPage(pageName);
		assert result.getRootElement() != null : "The document is empty";
		
		// Test the XtClick mixins with an ajax request and Javascript callback 
		checkXitiClickMixins(result, pageName, "ajaxClickWithJsCallback", "myXitiAjaxClickWithJsCallback", "62", true /* ajax */, true /* js callback */);
				
	}
	
	
	/**
	 * Check that the function "xtForm()" is called with the appropriate parameters
	 * Check that the simple form works correctly
	 * */
	@Test
	public void checkXtSimpleFormMixins(){
		String pageName = "SimpleForm";
		Document result = tester.renderPage(pageName);
		assert result.getRootElement() != null : "The document is empty";
		
		// Test the XtForm mixins with simple request
		checkXitiFormMixins(result, pageName, "myForm", "myXitiFormlabel", "89", false /* no ajax */);
	}
	
	/**
	 * Check that the function "xtForm()" is called with the appropriate parameters
	 * Check that the ajax form works correctly
	 * */
	@Test
	public void checkXtAjaxFormMixins(){
		String pageName = "AjaxForm";
		Document result = tester.renderPage(pageName);
		assert result.getRootElement() != null : "The document is empty";
		
		// Test the XtForm mixins with an ajax request
		checkXitiFormMixins(result, pageName, "myAjaxForm", "myXitiAjaxFormlabel", "22", true /* ajax */);
	}
	
	private void checkXitiClickMixins(Document document, String pageName, String id, String label, String level, boolean isAjax, boolean callback) {
		String markup = document.getRootElement().getChildMarkup();
		
		checkJsStack(markup, pageName, true, annotationParams);
		
		checkFunctionCall(markup, "xtClick", id, label, level, isAjax);
		
		// Test the actionlink (ajax or not, with javascript callback or without)
		TestableResponse response = tester.clickLinkAndReturnResponse(document.getElementById(id));
		String responseMarkup = response.getRenderedDocument().getRootElement().getChildMarkup(); 

		assert responseMarkup.contains(
				"Xiti click processed successfully !") : "A problem occured, the Xiti click did not process as expected";
		if (callback){		
			assert responseMarkup.contains(
					"JavaScript callback success !") : "A problem occured, the Xiti click with js callback did not process as expected";
		}
		
	}

	private void checkXitiFormMixins(Document document, String pageLabel, String id, String label, String level, boolean isAjax) {
		String markup = document.getRootElement().getChildMarkup();
		
		checkJsStack(markup, pageLabel, true, annotationParams);
		
		checkFunctionCall(markup, "xtForm", id, label, level, isAjax);
		
		// Test the form submit (ajax or not)
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("name", "adaptivui");
		TestableResponse response = tester.submitFormAndReturnResponse(document.getElementById(id), parameters);
		assert response.getRenderedDocument().getRootElement().getChildMarkup().contains(
				"Xiti form submitted successfully !") : "A problem occured, the Xiti form has not been submitted as expected";
		
	}

	// Check that js stack is correctly imported
	private void checkJsStack(String markup, String pageName, boolean checkMixins, String... params) {
		
		assert markup != null : "The markup is empty";
		
		assert markup.contains("/js/xtcore.js") : "'xtcore.js' missing";
		
		if(checkMixins){
			assert markup.contains("/js/tapestry-xiti.js") : "'tapestry-xiti.js' missing";
		}
		
		String xtnv 	= (params!=null && params.length>0)? params[0] : annotationParams[0];
		String xtsite 	= (params!=null && params.length>1)? params[1] : annotationParams[1];
		String xtsd 	= (params!=null && params.length>2)? params[2] : annotationParams[2];
		String xtn2 	= (params!=null && params.length>3)? params[3] : annotationParams[3];
		String xtdi 	= (params!=null && params.length>4)? params[4] : annotationParams[4];
		String xt_mult 	= (params!=null && params.length>5)? params[5] : annotationParams[5];
		String xt_an	= (params!=null && params.length>6)? params[6] : annotationParams[6];
		String xt_ac 	= (params!=null && params.length>7)? params[7] : annotationParams[7];
		
		assert markup.contains("xtnv = document; //parent.document or top.document or document" + "\n"
				+ "xtsd = \"http://log"+xtsd+"\";" + "\n"
				+ "xtsite = \""+xtsite+"\"; //site number" + "\n"
				+ "xtn2 = \""+xtn2+"\"; //level 2 site" + "\n"
				+ "xtpage = \""+pageName+"\"; //page name (with the use of :: to create chapters)" + "\n"
				+ "xtdi = \""+xtdi+"\"; //implication degree" + "\n"
				+ "xt_multc = \""+xt_mult+"\"; //customized indicators" + "\n"
				+ "xt_an = \""+xt_an+"\"; //numeric identifier" + "\n"
				+ "xt_ac = \""+xt_ac+"\"; //category" + "\n"
				+ "//do not modify below" + "\n"
				+ "if (window.xtparam!=null){window.xtparam+=\"&ac=\"+xt_ac+\"&an=\"+xt_an+xt_multc;}" + "\n"
				+ "else{window.xtparam = \"&ac=\"+xt_ac+\"&an=\"+xt_an+xt_multc;};") : "the syntax of the first part of the Xiti script is not as expected"; 
		
		assert markup.contains("<noscript>" + "\n"
				+ "<img width=\"1\" height=\"1\" src=\"http://log"+xtsd+".com/hit.xiti?s="+xtsite+"&s2=&p=&di=&ac=&an="+xt_mult+"\" >" + "\n"
				+ "</noscript>") : "the syntax of the second part of the Xiti script is not as expected";
	}

	// Check that the XtClick initialization function is called with some expected parameters
	private void checkFunctionCall(String markup, String fctName, String elementId, String label, String level, boolean isAjax){
		
		String type = isAjax ? "A": "N";
		
		String token = "\""+fctName+"\":[{\"elementId\":\""+elementId+"\","+
						"\"level\":"+level+","+
						"\"custom\":{\"param1\":\"value1\"},"+
						"\"label\":\""+label+"\","+
						"\"type\":\""+type+"\""+
						"}";
		
		assert markup.contains(token):
			fctName+" function initialization with parameters : " +
			((elementId!=null)?"elementId ["+elementId+"], ":"")+
			"label ["+label+"], level ["+level+"], type ["+type+"] is missing";
	}
}
