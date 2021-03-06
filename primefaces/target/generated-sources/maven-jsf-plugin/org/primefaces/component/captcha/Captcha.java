/*
 * Generated, Do Not Modify
 */
/*
 * Copyright 2009-2013 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.component.captcha;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.component.UINamingContainer;
import javax.el.ValueExpression;
import javax.el.MethodExpression;
import javax.faces.render.Renderer;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.event.AbortProcessingException;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.primefaces.util.MessageFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.faces.application.FacesMessage;
import javax.faces.FacesException;
import org.primefaces.context.RequestContext;
import org.primefaces.component.captcha.Captcha;
import org.primefaces.context.PrimeExternalContext;
import org.primefaces.json.JSONObject;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="primefaces.js"),
	@ResourceDependency(library="primefaces", name="captcha/captcha.js")
})
public class Captcha extends UIInput implements org.primefaces.component.api.Widget {


	public static final String COMPONENT_TYPE = "org.primefaces.component.Captcha";
	public static final String COMPONENT_FAMILY = "org.primefaces.component";
	private static final String DEFAULT_RENDERER = "org.primefaces.component.CaptchaRenderer";

	protected enum PropertyKeys {

		theme
		,language
		,tabindex
		,label;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
}
	}

	public Captcha() {
		setRendererType(DEFAULT_RENDERER);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public java.lang.String getTheme() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.theme, "light");
	}
	public void setTheme(java.lang.String _theme) {
		getStateHelper().put(PropertyKeys.theme, _theme);
	}

	public java.lang.String getLanguage() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.language, "en");
	}
	public void setLanguage(java.lang.String _language) {
		getStateHelper().put(PropertyKeys.language, _language);
	}

	public int getTabindex() {
		return (java.lang.Integer) getStateHelper().eval(PropertyKeys.tabindex, 0);
	}
	public void setTabindex(int _tabindex) {
		getStateHelper().put(PropertyKeys.tabindex, _tabindex);
	}

	public java.lang.String getLabel() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.label, null);
	}
	public void setLabel(java.lang.String _label) {
		getStateHelper().put(PropertyKeys.label, _label);
	}


    public final static String PUBLIC_KEY = "primefaces.PUBLIC_CAPTCHA_KEY";
    public final static String PRIVATE_KEY = "primefaces.PRIVATE_CAPTCHA_KEY";
    public final static String INVALID_MESSAGE_ID = "primefaces.captcha.INVALID";

    @Override
	protected void validateValue(FacesContext context, Object value) {
		super.validateValue(context, value);

        if(isValid()) {
            
            boolean result = false;
            
            try {
                URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                String postBody = createPostParameters(context, value);

                OutputStream out = conn.getOutputStream();
                out.write(postBody.getBytes());
                out.flush();
                out.close();

                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = rd.readLine()) != null) {
                    response.append(inputLine);
                }
                
                JSONObject json = new JSONObject(response.toString());
                result = json.getBoolean("success");
                
                rd.close();
            }catch(Exception exception) {
                throw new FacesException(exception);
            }

            if(!result) {
                setValid(false);

                String validatorMessage = getValidatorMessage();
                FacesMessage msg = null;

                if(validatorMessage != null) {
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, validatorMessage, validatorMessage);
                }
                else {
                    Object[] params = new Object[2];
                    params[0] = MessageFactory.getLabel(context, this);
                    params[1] = (String)value;

                    msg = MessageFactory.getMessage(Captcha.INVALID_MESSAGE_ID, FacesMessage.SEVERITY_ERROR, params);
                }

                context.addMessage(getClientId(context), msg);
            }
        }
        
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if(requestContext.isAjaxRequest()) {
            requestContext.execute("grecaptcha.reset()");
        }
	}

    private String createPostParameters(FacesContext facesContext, Object value) throws UnsupportedEncodingException {
        String privateKey = facesContext.getExternalContext().getInitParameter(Captcha.PRIVATE_KEY);

        if(privateKey == null) {
            throw new FacesException("Cannot find private key for catpcha, use primefaces.PRIVATE_CAPTCHA_KEY context-param to define one");
        }

		StringBuilder postParams = new StringBuilder();
		postParams.append("secret=").append(URLEncoder.encode(privateKey, "UTF-8"));
		postParams.append("&response=").append(URLEncoder.encode((String) value, "UTF-8"));

        String params = postParams.toString();
        postParams.setLength(0);
        
		return params;
	}
	public String resolveWidgetVar() {
		FacesContext context = getFacesContext();
		String userWidgetVar = (String) getAttributes().get("widgetVar");

		if(userWidgetVar != null)
			return userWidgetVar;
		 else
			return "widget_" + getClientId(context).replaceAll("-|" + UINamingContainer.getSeparatorChar(context), "_");
	}
}