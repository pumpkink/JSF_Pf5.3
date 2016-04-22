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
package org.primefaces.component.amap;

import javax.faces.component.UIComponentBase;
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
public class AMapInfoWindow extends UIComponentBase {


	public static final String COMPONENT_TYPE = "org.primefaces.component.AMapInfoWindow";
	public static final String COMPONENT_FAMILY = "org.primefaces.component";

	protected enum PropertyKeys {

		Custom
		,content
		,offset
		,closeWhenClickMap
		,extData;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
}
	}

	public AMapInfoWindow() {
		setRendererType(null);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public boolean isCustom() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.Custom, true);
	}
	public void setCustom(boolean _Custom) {
		getStateHelper().put(PropertyKeys.Custom, _Custom);
	}

	public java.lang.String getContent() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.content, null);
	}
	public void setContent(java.lang.String _content) {
		getStateHelper().put(PropertyKeys.content, _content);
	}

	public java.lang.String getOffset() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.offset, null);
	}
	public void setOffset(java.lang.String _offset) {
		getStateHelper().put(PropertyKeys.offset, _offset);
	}

	public boolean isCloseWhenClickMap() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.closeWhenClickMap, true);
	}
	public void setCloseWhenClickMap(boolean _closeWhenClickMap) {
		getStateHelper().put(PropertyKeys.closeWhenClickMap, _closeWhenClickMap);
	}

	public java.lang.String getExtData() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.extData, "j_idt5:infoWindow");
	}
	public void setExtData(java.lang.String _extData) {
		getStateHelper().put(PropertyKeys.extData, _extData);
	}

}