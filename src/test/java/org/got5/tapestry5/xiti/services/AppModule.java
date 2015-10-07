package org.got5.tapestry5.xiti.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ApplicationDefaults;
import org.apache.tapestry5.ioc.services.SymbolProvider;
import org.got5.tapestry5.xiti.utils.XitiConstants;

@SubModule({XitiModule.class})
public class AppModule {
	
	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void contributeSymboleProvider(MappedConfiguration<String, String> configuration)	{
		configuration.add(XitiConstants.XITI_XTCORE_JS_PATH, "classpath:/js/xtcore.js"); 
		configuration.add(XitiConstants.XITI_ENABLE_SCRIPT_AUTOLOAD, "false"); 
		configuration.add(XitiConstants.XT_SITE, "9999"); 
		configuration.add(XitiConstants.XT_SD, "8888"); 
	}

}