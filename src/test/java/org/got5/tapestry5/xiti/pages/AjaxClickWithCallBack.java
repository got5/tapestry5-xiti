/**
 * 
 */
package org.got5.tapestry5.xiti.pages;

import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.ajax.JavaScriptCallback;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.got5.tapestry5.xiti.annotations.Xiti;

@Xiti(xtpage="AjaxClickWithCallBack", xtsite="1234", xtsd="4321", xt_ac="5678", xt_an="8765", xt_multc="&x1=12345&x2=49367", xtdi="3", xtn2="354", xtnv="document")
public class AjaxClickWithCallBack {

	@Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
	
	@OnEvent(component="ajaxClickWithJsCallback")
	public void xitiAjaxClickWithJsCallback(){
		ajaxResponseRenderer.addCallback(new JavaScriptCallback() {
            public void run(JavaScriptSupport javascriptSupport) {
                javascriptSupport.addScript(
                		String.format("document.getElementById('%s').innerHTML='%s';", "spanMessage", "JavaScript callback success !"));
            }
        });
	}
}