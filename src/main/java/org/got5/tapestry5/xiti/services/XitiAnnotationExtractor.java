package org.got5.tapestry5.xiti.services;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.meta.MetaDataExtractor;
import org.got5.tapestry5.xiti.annotations.Xiti;
import org.got5.tapestry5.xiti.utils.XitiConstants;

public class XitiAnnotationExtractor implements MetaDataExtractor<Xiti>{
	
	@Inject
	@Symbol(XitiConstants.XT_SITE)
	private String xt_site;

	@Inject
	@Symbol(XitiConstants.XT_SD)
	private String xt_sd;

	@Inject
	@Symbol(XitiConstants.XT_NV)
	private String xt_nv;

	public void extractMetaData(MutableComponentModel model, Xiti annotation) {
		
		/**
		 * A simple flag to detect if the @Xiti annotation is present in the page
		 * */
		model.setMeta(XitiConstants.XITI_IS_ANNOTATED, "true");
		
		/**
		 * Use annotation attribute values if provided. Otherwise, use symbol values
		 * */
		xt_site = (annotation.xtsite() == null || annotation.xtsite().length() == 0)? xt_site : annotation.xtsite();
		if(InternalUtils.isNonBlank(xt_site)) 
			model.setMeta(XitiConstants.XT_SITE, xt_site);
		
		xt_sd = (annotation.xtsd() == null || annotation.xtsd().length() == 0)? xt_sd : annotation.xtsd();
		if(InternalUtils.isNonBlank(xt_sd)) 
			model.setMeta(XitiConstants.XT_SD, xt_sd);
		
		xt_nv = (annotation.xtnv() == null || annotation.xtnv().length() == 0)? xt_nv : annotation.xtnv();
		if(InternalUtils.isNonBlank(xt_nv)) 
			model.setMeta(XitiConstants.XT_NV, xt_nv);
		
		// Unfortunately, there is no possibility to store an Object as a value (only String)
		
		if(InternalUtils.isNonBlank(annotation.xtpage())) 
			model.setMeta(XitiConstants.XT_PAGE, annotation.xtpage());
		if(InternalUtils.isNonBlank(annotation.xt_ac())) 
			model.setMeta(XitiConstants.XT_AC, annotation.xt_ac());
		if(InternalUtils.isNonBlank(annotation.xt_an())) 
			model.setMeta(XitiConstants.XT_AN, annotation.xt_an());
		if(InternalUtils.isNonBlank(annotation.xt_multc())) 
			model.setMeta(XitiConstants.XT_MULTC, annotation.xt_multc());
		if(InternalUtils.isNonBlank(annotation.xtdi())) 
			model.setMeta(XitiConstants.XT_DI, annotation.xtdi());
		if(InternalUtils.isNonBlank(annotation.xtn2())) 
			model.setMeta(XitiConstants.XT_N2, annotation.xtn2());
		
	}

}
