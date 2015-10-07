/**
 * 
 */
package org.got5.tapestry5.xiti.pages;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.got5.tapestry5.xiti.annotations.Xiti;

@Xiti(xtpage="AjaxForm", xtsite="1234", xtsd="4321", xt_ac="5678", xt_an="8765", xt_multc="&x1=12345&x2=49367", xtdi="3", xtn2="354", xtnv="document")
public class AjaxForm {

	@Property
	private String text;
	
	@Property
	@Persist(PersistenceConstants.FLASH)
	private String ajaxMessage;
	
	@InjectComponent
    private Zone myZone;
	
	@OnEvent(value= EventConstants.SUCCESS, component="myAjaxForm")
	public Object xitiAjaxForm(){
		ajaxMessage = "Xiti form submitted successfully !";
		return myZone;
	}
}