Tapestry-xiti
=================

Tapestry-xiti is light tapestry integration for [Xiti](http://www.xiti.com/) (a Web analytics solution).
Compatible with Tapestry 5.3.8 and Xiti 4.5.4 and above.

Usage
=======

Xiti provides Web analytics for your website. For that, by default, you have to add some js scripts and call the Xiti API in your HTML pages (mainly js function calls on links and forms).

Tapestry-xiti library eases the integration with the Web analytics framework by loading the appropriate scripts.
 
It also allows you to use the Xiti API easily by providing mixins that can be declared on links (actionlink, eventlink, pagelink) and forms.

#### How to use it ####

The module provides a way to add the Xiti stack (xtcore.js and tapestry-xiti.js) and instrument your links and form submits.


#### Module Settings ####

Set the module parameters provided by Xiti in your AppModule : 
- XitiConstants.XITI_XTCORE_JS_PATH : _xtcore.js_ path (script provided by Xiti) 
- XitiConstants.XT_SITE : _xt\_site_ your site's identifier (provided by Xiti)
- XitiConstants.XT_SD : _xt\_sd_ the Xiti sub-domain (provided by Xiti)
- XITI_ENABLE_SCRIPT_AUTOLOAD : enable/disable Xiti script auto-load. By defaut, true : the script is loaded for all pages (annotated or not  with @Xiti annotation). If you set it to false, only annotated pages will load the Xiti script 
 
 For instance, if you put the _xtcore.js_ in _src/main/webapp/static/js_ : 

	@Contribute(SymbolProvider.class)
	@ApplicationDefaults
	public static void contributeSymboleProvider(MappedConfiguration<String, String> configuration)
	{
		/**
		 * Set the xtcore.js path. 
		 * If xtcore.js is under src/main/webapp/static/js, should be something like "context:static/js/xtcore.js"
		 * */
		configuration.add(XitiConstants.XITI_XTCORE_JS_PATH, "context:static/js/xtcore.js"); 
		
		/**
		 * Set mandatory "xtsite" param (provided by Xiti) for the whole application
		 * This value can be overridden as an Application defaults configuration or as a @Xiti annotation attribute
		 * */  
		configuration.add(XitiConstants.XT_SITE, "1234"); 
		
		/**
		 * Set mandatory "xtsd" param (provided by Xiti) for the whole application
		 * This value can be overridden as an Application defaults configuration or as a @Xiti annotation attribute
		 * */  
		configuration.add(XitiConstants.XT_SD, "5678"); 
	}

Note : Some Xiti parameters (_xt\_site_, _xt\_sd_ and _xt\_nv_) can be overridden by the @Xiti annotation attributes on a page level, if provided (See "Xiti annotation" section)

#### Xiti annotation  ####

The _@Xiti_ annotation allows you to set or override Xiti parameters.  

Note that, by default, all pages load the Xiti script, even those without annotation @Xiti.

    package com.example.testapp.pages;
    ...
    @Xiti(xtpage="SubscribeView", xt_ac="5678", xt_an="8765", xt_multc="&x1=12345&x2=49367", xtdi="3", xtn2="354")
    public class Subscribe {
    ...
    }

In this sample, the _@Xiti_ annotation is used to set a number of Xiti variables (which values are provided by Xiti) and records the page view with label "SubscribeView" 

Note : Some global parameters (as _xt\_site_ and _xt\_sd_) can be set on an application level in the AppModule (See "Module settings" section)

#### How to record a click on a link ####

The module provides also a way to record a page view or an event (click, form submit)

###### XtClick Mixins ######

To record a hit on an event with a label and a level, use the _xiti/XtClick_ mixins :

in SomePage.tml

    <html t:type="layout" title="Some Page"  
          xmlns:t="http://tapestry.apache.org/schema/tapestry_5_3.xsd">
        ...
        <t:actionlink t:id="addToBasket" 
                t:mixins="xiti/XtClick" 
                t:label="addedToBasket" 
                t:level="3">
            Add to basket
        </t:actionlink>
        ...
    </html>

Here, the "addedToBasket" action will be recorded with level 3 on the click event.

Note that you can also record an event in an ajax context and use some javascript callbacks

###### XtForm Mixins ######

To record a hit on a form submit event with a label and a level, use the _xiti/XtForm_ mixins :

in SomePage.tml

    <html t:type="layout" title="Some Page"  
          xmlns:t="http://tapestry.apache.org/schema/tapestry_5_3.xsd">
        ...
        <t:form t:id="subscription" t:mixins="xiti/XtForm" t:label="subscribe" t:level="5">
			<p>Name : <t:textfield t:id="myText" t:value="text"/></p>
			<t:submit/>
		</t:form>
        ...
    </html>

Here, the form submit will be recorded as a Xiti hit with label "subscribe" and level 5. 

Note that you can also record a form submit in an ajax context.

Installation
============

Launch the maven command *mvn clean install*, then add this maven dependency in your webapp :
 
    <dependency>
       <groupId>org.got5.tapestry5.xiti</groupId>
       <artifactId>tapestry-xiti</artifactId>
       <version>_version_</version>
    </dependency>

Where version is the version you've installed. 

License
=======

This project is distributed under Apache 2 License. See LICENSE.txt for more information.

More
====

For more examples on usage, see the test webapp under src/test
