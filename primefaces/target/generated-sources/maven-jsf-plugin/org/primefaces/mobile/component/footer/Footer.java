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
package org.primefaces.mobile.component.footer;

import javax.faces.component.UIPanel;
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

@ResourceDependencies({

})
public class Footer extends UIPanel {


	public static final String COMPONENT_TYPE = "org.primefaces.mobile.Footer";
	public static final String COMPONENT_FAMILY = "org.primefaces.mobile.component";
	private static final String DEFAULT_RENDERER = "org.primefaces.mobile.component.FooterRenderer";

	protected enum PropertyKeys {

		title
		,fixed
		,swatch
		,tapToggle
		,style
		,styleClass;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
}
	}

	public Footer() {
		setRendererType(DEFAULT_RENDERER);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public java.lang.String getTitle() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.title, null);
	}
	public void setTitle(java.lang.String _title) {
		getStateHelper().put(PropertyKeys.title, _title);
	}

	public boolean isFixed() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.fixed, false);
	}
	public void setFixed(boolean _fixed) {
		getStateHelper().put(PropertyKeys.fixed, _fixed);
	}

	public java.lang.String getSwatch() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.swatch, null);
	}
	public void setSwatch(java.lang.String _swatch) {
		getStateHelper().put(PropertyKeys.swatch, _swatch);
	}

	public boolean isTapToggle() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.tapToggle, true);
	}
	public void setTapToggle(boolean _tapToggle) {
		getStateHelper().put(PropertyKeys.tapToggle, _tapToggle);
	}

	public java.lang.String getStyle() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.style, null);
	}
	public void setStyle(java.lang.String _style) {
		getStateHelper().put(PropertyKeys.style, _style);
	}

	public java.lang.String getStyleClass() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.styleClass, null);
	}
	public void setStyleClass(java.lang.String _styleClass) {
		getStateHelper().put(PropertyKeys.styleClass, _styleClass);
	}

}