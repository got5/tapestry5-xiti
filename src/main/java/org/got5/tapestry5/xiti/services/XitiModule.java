package org.got5.tapestry5.xiti.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.services.FactoryDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.apache.tapestry5.services.ClasspathAssetAliasManager;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.meta.MetaDataExtractor;
import org.apache.tapestry5.services.meta.MetaWorker;
import org.got5.tapestry5.xiti.annotations.Xiti;
import org.got5.tapestry5.xiti.utils.XitiConstants;

public class XitiModule {
	
	@Contribute(SymbolProvider.class)
	@FactoryDefaults
	public static void contributeSymboleProvider(MappedConfiguration<String, String> configuration)	{
		
		/**
		* Enable (or not) Xiti script load for all pages (no need to annotation @Xiti by default)
		* Set it to false if you want to load script only for annotated pages (@Xiti) 
		*/
		configuration.add(XitiConstants.XITI_ENABLE_SCRIPT_AUTOLOAD, "true"); 
		
		/**
		 * Set the xtcore.js path. 
		 * If xtcore.js is under src/main/webapp/static/js, should be something like "context:static/js/xtcore.js"
		 * */  
		configuration.add(XitiConstants.XITI_XTCORE_JS_PATH, "classpath:js/xtcore.js"); 
		
		/**
		 * Set mandatory "xtsite" param (provided by Xiti) for the whole application
		 * This value can be overridden as an Application defaults configuration or as a @Xiti annotation attribute
		 * */  
		configuration.add(XitiConstants.XT_SITE, ""); 
		
		/**
		 * Set mandatory "xtsd" param (provided by Xiti) for the whole application
		 * This value can be overridden as an Application defaults configuration or as a @Xiti annotation attribute
		 * */  
		configuration.add(XitiConstants.XT_SD, ""); 
		
		/**
		 * Set mandatory "xtnv" param for the whole application - Should be "document" for most cases
		 * This value can be overridden as an Application defaults configuration or as a @Xiti annotation attribute
		 * */  
		configuration.add(XitiConstants.XT_NV, "document"); 
	}

	@Contribute(ClasspathAssetAliasManager.class)
	public static void addApplicationAndTapestryMappings(MappedConfiguration<String, String> configuration) {
		configuration.add("js", "js");
	}

	@Contribute(MetaWorker.class)
	public static void addMetaWorker(MappedConfiguration<Class, MetaDataExtractor> configuration) {
	        configuration.addInstance(Xiti.class, XitiAnnotationExtractor.class);
	}
	
	@Contribute(MarkupRenderer.class)
	public static void addFilter(
	    OrderedConfiguration<MarkupRendererFilter> configuration) {
	  configuration.addInstance("XitiFilter", XitiRendererFilter.class,
	      "after:JavaScriptSupport");
	}
	
	@Contribute(ComponentClassResolver.class)
	public static void addVirtualFolder(Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("xiti", "org.got5.tapestry5.xiti"));
	}

}
