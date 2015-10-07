package org.got5.tapestry5.xiti.services;


import org.apache.tapestry5.Asset;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.MessagesImpl;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.AssetSource;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.apache.tapestry5.services.MetaDataLocator;
import org.apache.tapestry5.services.RequestGlobals;
import org.got5.tapestry5.xiti.XitiScriptsMessages;
import org.got5.tapestry5.xiti.utils.XitiConstants;
import org.slf4j.Logger;

public class XitiRendererFilter implements MarkupRendererFilter {

	private final static Messages SCRIPTS = MessagesImpl.forClass(XitiScriptsMessages.class);

	@Inject
	private Logger logger;

	@Inject
	private RequestGlobals requestGlobals;

	@Inject
	private MetaDataLocator metaDataLocator;

	@Inject
	private AssetSource assetSource;
	
	@Inject
	@Symbol(XitiConstants.XITI_XTCORE_JS_PATH)
	private String xtCoreJsPath;

	@Inject
	@Symbol(XitiConstants.XITI_ENABLE_SCRIPT_AUTOLOAD)
	private String automaticScriptLoadEnabled;
	
	@Inject
	private SymbolSource symbolSource;
	
	private void addScript(Document document) {
		
		String pageName = requestGlobals.getActivePageName();
		
		boolean isAnnotationPresent = false;
		
		try{
			isAnnotationPresent = metaDataLocator.findMeta(
					XitiConstants.XITI_IS_ANNOTATED, pageName, boolean.class);
		}catch(RuntimeException e){
			//catch generic exception from Tapestry when the meta data is missing. Only possibility to check if the data is stored or not in the model !
			logger.debug("The page ["+pageName+"] is not annotated with @Xiti");
		}
		
		if(! Boolean.valueOf(automaticScriptLoadEnabled) && ! isAnnotationPresent) {
			logger.debug("The page ["+pageName+"] is not annotated with @Xiti and AUTOMATIC_SCRIPT_LOAD is disabled, so the Xiti script will not be loaded.");
			return;
		}
		
		logger.debug( "["+pageName+"] automaticScriptLoadEnabled : "+automaticScriptLoadEnabled);
		logger.debug( "["+pageName+"] isAnnotationPresent : "+isAnnotationPresent);
		
		String xt_site = symbolSource.valueForSymbol(XitiConstants.XT_SITE);
		String xt_sd = symbolSource.valueForSymbol(XitiConstants.XT_SD);
		String xt_nv = symbolSource.valueForSymbol(XitiConstants.XT_NV);
		
		logger.debug("Default xt_site 	= "+xt_site);
		logger.debug("Default xt_sd 	= "+xt_sd);
		logger.debug("Default xt_nv		= "+xt_nv);
		
		String xt_ac = "", xt_an = "", xt_multc = "", xt_di = "", xt_n2 = "", xt_page = "";
		
		if(isAnnotationPresent){
			
			String msg = "A problem occured when trying to add the Xiti script in the page .";
			
			// This part is very ugly but we can not do it in a different way because : 
			// 		- if a key does not exists in the meta model, a RuntimeException is thrown
			// 		- Meta data values are necessary stored as Strings
			try{
				xt_ac = metaDataLocator.findMeta(
						XitiConstants.XT_AC, pageName, String.class);
			}catch(RuntimeException e){
				//catch generic exception from Tapestry when the meta data is missing. Only possibility to check if the data is stored or not in the model !
				logger.debug(msg + e.getMessage());
			}
			
			try{
				xt_an = metaDataLocator.findMeta(
						XitiConstants.XT_AN, pageName, String.class);
			}catch(RuntimeException e){
				//catch generic exception from Tapestry when the meta data is missing. Only possibility to check if the data is stored or not in the model !
				logger.debug(msg + e.getMessage());
			}
			
			try{
				xt_multc = metaDataLocator.findMeta(
						XitiConstants.XT_MULTC, pageName, String.class);
			}catch(RuntimeException e){
				//catch generic exception from Tapestry when the meta data is missing. Only possibility to check if the data is stored or not in the model !
				logger.debug(msg + e.getMessage());
			}
			
			try{
				xt_di = metaDataLocator.findMeta(
						XitiConstants.XT_DI, pageName, String.class);
			}catch(RuntimeException e){
				//catch generic exception from Tapestry when the meta data is missing. Only possibility to check if the data is stored or not in the model !
				logger.debug(msg + e.getMessage());
			}
			
			try{
				xt_n2 = metaDataLocator.findMeta(
						XitiConstants.XT_N2, pageName, String.class);
			}catch(RuntimeException e){
				//catch generic exception from Tapestry when the meta data is missing. Only possibility to check if the data is stored or not in the model !
				logger.debug(msg + e.getMessage());
			}
			
			try{
				xt_page = metaDataLocator.findMeta(
						XitiConstants.XT_PAGE, pageName, String.class);
			}catch(RuntimeException e){
				//catch generic exception from Tapestry when the meta data is missing. Only possibility to check if the data is stored or not in the model !
				logger.debug(msg + e.getMessage());
			}
			
			try{
				xt_sd = metaDataLocator.findMeta(
						XitiConstants.XT_SD, pageName, String.class);
			}catch(RuntimeException e){
				//catch generic exception from Tapestry when the meta data is missing. Only possibility to check if the data is stored or not in the model !
				logger.debug(msg + e.getMessage());
			}
			
			try{
				xt_nv = metaDataLocator.findMeta(
						XitiConstants.XT_NV, pageName, String.class);
			}catch(RuntimeException e){
				//catch generic exception from Tapestry when the meta data is missing. Only possibility to check if the data is stored or not in the model !
				logger.debug(msg + e.getMessage());
			}
			
			try{
				xt_site = metaDataLocator.findMeta(
						XitiConstants.XT_SITE, pageName, String.class);
			}catch(RuntimeException e){
				//catch generic exception from Tapestry when the meta data is missing. Only possibility to check if the data is stored or not in the model !
				logger.debug(msg + e.getMessage());
			}
		}
		
		Asset XtcoreJs = assetSource.getAsset(null, xtCoreJsPath, null);

		Element root = document.getRootElement();
		if (root == null)
			return;
		Element body = root.find("body");
		if (body == null) {
			body = root.element("body");
		}
		body.raw(SCRIPTS.format("script",
				xt_nv,
				xt_sd, 
				xt_site,
				xt_n2,
				xt_page,
				xt_di,
				xt_multc,
				xt_an,
				xt_ac,
				XtcoreJs.toClientURL(),
				xt_sd,
				xt_site,
				xt_multc));
	}

	public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer) {
		renderer.renderMarkup(writer);
		this.addScript(writer.getDocument());
	}
}
