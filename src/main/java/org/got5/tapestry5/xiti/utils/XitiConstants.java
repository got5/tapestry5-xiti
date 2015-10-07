package org.got5.tapestry5.xiti.utils;

public class XitiConstants {
	
	/**
	* Enable or not Xiti script load for all pages (no need to annotation @Xiti by default)
	*/
	public static final String XITI_ENABLE_SCRIPT_AUTOLOAD = "XitiEnableScriptAutoLoad";
	
	/**
	 * A simple flag to detect if the @Xiti annotation is present in the page
	 * */
	public static final String XITI_IS_ANNOTATED = "XitiIsAnnotated";
	
	/**
	* Xiti Xtcore.js path (provided by Xiti and expected to be in webapp context)
	*/
	public static final String XITI_XTCORE_JS_PATH = "XitiXtCoreJsPath";
	
	/**
	* Xiti parameters
	*/
	public static final String XT_SITE 		= "xtsite";
	public static final String XT_AC 		= "xt_ac";
	public static final String XT_AN 		= "xt_an"; 
	public static final String XT_MULTC 	= "xt_multc"; 
	public static final String XT_DI 		= "xtdi"; 
	public static final String XT_N2 		= "xtn2"; 
	public static final String XT_PAGE		= "xtpage"; 
	public static final String XT_SD 		= "xtsd"; 
	public static final String XT_NV 		= "xtnv";	
}
