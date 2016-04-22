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
import org.primefaces.event.amap.OverlaySelectEvent;
import org.primefaces.event.amap.StateChangedEvent;
import org.primefaces.event.amap.PointSelectedEvent;
import org.primefaces.event.amap.MarkerDragEvent;
import org.primefaces.event.amap.GeocodeEvent;
import org.primefaces.event.amap.ReverseGeocodeEvent;
import org.primefaces.model.amap.Bounds;
import org.primefaces.model.amap.LngLat;
import org.primefaces.model.amap.MapModel;
import org.primefaces.model.amap.Marker;
import org.primefaces.model.amap.InfoWindow;
import org.primefaces.model.amap.GeocodeResult;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.util.Constants;
import org.primefaces.context.RequestContext;

@ResourceDependencies({
	@ResourceDependency(library="primefaces", name="jquery/jquery.js"),
	@ResourceDependency(library="primefaces", name="primefaces.js"),
	@ResourceDependency(library="primefaces", name="amap/amap.js")
})
public class AMap extends UIComponentBase implements org.primefaces.component.api.Widget,javax.faces.component.behavior.ClientBehaviorHolder {


	public static final String COMPONENT_TYPE = "org.primefaces.component.AMap";
	public static final String COMPONENT_FAMILY = "org.primefaces.component";
	private static final String DEFAULT_RENDERER = "org.primefaces.component.AMapRenderer";

	protected enum PropertyKeys {

		widgetVar
		,model
		,style
		,styleClass
		,layers
		,center
		,zoom
		,streetView
		,disableDefaultUI
		,scale
		,toolBar
		,draggable
		,disableDoubleClickZoom
		,onPointClick
		,fitBounds;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
}
	}

	public AMap() {
		setRendererType(DEFAULT_RENDERER);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public java.lang.String getWidgetVar() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.widgetVar, null);
	}
	public void setWidgetVar(java.lang.String _widgetVar) {
		getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
	}

	public org.primefaces.model.amap.MapModel getModel() {
		return (org.primefaces.model.amap.MapModel) getStateHelper().eval(PropertyKeys.model, null);
	}
	public void setModel(org.primefaces.model.amap.MapModel _model) {
		getStateHelper().put(PropertyKeys.model, _model);
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

	public java.lang.String getLayers() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.layers, null);
	}
	public void setLayers(java.lang.String _layers) {
		getStateHelper().put(PropertyKeys.layers, _layers);
	}

	public java.lang.String getCenter() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.center, null);
	}
	public void setCenter(java.lang.String _center) {
		getStateHelper().put(PropertyKeys.center, _center);
	}

	public int getZoom() {
		return (java.lang.Integer) getStateHelper().eval(PropertyKeys.zoom, 12);
	}
	public void setZoom(int _zoom) {
		getStateHelper().put(PropertyKeys.zoom, _zoom);
	}

	public boolean isStreetView() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.streetView, false);
	}
	public void setStreetView(boolean _streetView) {
		getStateHelper().put(PropertyKeys.streetView, _streetView);
	}

	public boolean isDisableDefaultUI() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.disableDefaultUI, false);
	}
	public void setDisableDefaultUI(boolean _disableDefaultUI) {
		getStateHelper().put(PropertyKeys.disableDefaultUI, _disableDefaultUI);
	}

	public boolean isScale() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.scale, true);
	}
	public void setScale(boolean _scale) {
		getStateHelper().put(PropertyKeys.scale, _scale);
	}

	public boolean isToolBar() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.toolBar, true);
	}
	public void setToolBar(boolean _toolBar) {
		getStateHelper().put(PropertyKeys.toolBar, _toolBar);
	}

	public boolean isDraggable() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.draggable, true);
	}
	public void setDraggable(boolean _draggable) {
		getStateHelper().put(PropertyKeys.draggable, _draggable);
	}

	public boolean isDisableDoubleClickZoom() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.disableDoubleClickZoom, false);
	}
	public void setDisableDoubleClickZoom(boolean _disableDoubleClickZoom) {
		getStateHelper().put(PropertyKeys.disableDoubleClickZoom, _disableDoubleClickZoom);
	}

	public java.lang.String getOnPointClick() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.onPointClick, null);
	}
	public void setOnPointClick(java.lang.String _onPointClick) {
		getStateHelper().put(PropertyKeys.onPointClick, _onPointClick);
	}

	public boolean isFitBounds() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.fitBounds, false);
	}
	public void setFitBounds(boolean _fitBounds) {
		getStateHelper().put(PropertyKeys.fitBounds, _fitBounds);
	}


    private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("overlaySelect","stateChanged", "pointSelected", "markerDrag", "geocode", "reverseGeocode"));

    @Override
    public void queueEvent(FacesEvent event) {
        FacesContext context = getFacesContext();
        Map<String,String> params = context.getExternalContext().getRequestParameterMap();
        String eventName = params.get(Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM);
        String clientId = this.getClientId(context);

        if(isSelfRequest(context)) {

            AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;
            FacesEvent wrapperEvent = null;

            if(eventName.equals("overlaySelect")) {
                wrapperEvent = new OverlaySelectEvent(this, behaviorEvent.getBehavior(), this.getModel().findOverlay(params.get(clientId + "_overlayId")));

                //if there is info window, update and show it
                AMapInfoWindow infoWindow = getInfoWindow();
                if(infoWindow != null) {
                RequestContext.getCurrentInstance().update(infoWindow.getClientId(context));
                  
                }
            }
            else if(eventName.equals("stateChanged")) {
                String[] centerLoc = params.get(clientId + "_center").split(",");
                String[] northeastLoc = params.get(clientId + "_northeast").split(",");
                String[] southwestLoc = params.get(clientId + "_southwest").split(",");
                int zoomLevel = Integer.valueOf(params.get(clientId + "_zooms"));

                LngLat center = new LngLat(Double.valueOf(centerLoc[0]), Double.valueOf(centerLoc[1]));
                LngLat northeast = new LngLat(Double.valueOf(northeastLoc[0]), Double.valueOf(northeastLoc[1]));
                LngLat southwest = new LngLat(Double.valueOf(southwestLoc[0]), Double.valueOf(southwestLoc[1]));

                wrapperEvent = new StateChangedEvent(this, behaviorEvent.getBehavior(), new Bounds(southwest, northeast), zoomLevel, center);
            }
            else if(eventName.equals("pointSelected")) {
                String[] lnglat = params.get(clientId + "_pointLngLat").split(",");
                LngLat position = new LngLat(Double.valueOf(lnglat[0]), Double.valueOf(lnglat[1]));

                wrapperEvent = new PointSelectedEvent(this, behaviorEvent.getBehavior(), position);
            }
            else if(eventName.equals("markerDrag")) {
                Marker marker = (Marker) this.getModel().findOverlay(params.get(clientId + "_markerId"));
                double lng = Double.valueOf(params.get(clientId + "_lng"));                
                double lat = Double.valueOf(params.get(clientId + "_lat"));
                LngLat position = new LngLat(lng,lat);
                marker.setPosition(position);
                wrapperEvent = new MarkerDragEvent(this, behaviorEvent.getBehavior(), marker);
            }
            else if(eventName.equals("geocode")) {
                List<GeocodeResult> results = new ArrayList<GeocodeResult>();
                String query = params.get(clientId + "_query");
                String[] addresses = params.get(clientId + "_addresses").split("_primefaces_");                
                String[] lats = params.get(clientId + "_lat").split(",");
                String[] lngs = params.get(clientId + "_lng").split(",");

                for(int i = 0; i < addresses.length; i++) {
                    results.add(new GeocodeResult(addresses[i], new LngLat(Double.valueOf(lngs[i]), Double.valueOf(lats[i]))));
                }
                
                wrapperEvent = new GeocodeEvent(this, behaviorEvent.getBehavior(), query, results);
            }
            else if(eventName.equals("reverseGeocode")) {                
                List<String> addresses = new ArrayList<String>();
                String[] results = params.get(clientId + "_address").split("_primefaces_");
                for (int i = 0; i < results.length; i++) {
                    addresses.add(results[i]);
                }

                double lat = Double.valueOf(params.get(clientId + "_lat"));
                double lng = Double.valueOf(params.get(clientId + "_lng"));
                LngLat coord = new LngLat(lng, lat);

                wrapperEvent = new ReverseGeocodeEvent(this, behaviorEvent.getBehavior(), coord, addresses);
            }

            wrapperEvent.setPhaseId(behaviorEvent.getPhaseId());

            super.queueEvent(wrapperEvent);
        }
        else {
            super.queueEvent(event);
        }
    }
	
	public AMapInfoWindow getInfoWindow() {
		for(UIComponent kid : getChildren()) {
			if(kid instanceof AMapInfoWindow)
				return (AMapInfoWindow) kid;
		}
		
		return null;
	}
	
    private boolean isSelfRequest(FacesContext context) {
        return this.getClientId(context).equals(context.getExternalContext().getRequestParameterMap().get(Constants.RequestParams.PARTIAL_SOURCE_PARAM));
    }

    @Override
    public Collection<String> getEventNames() {
        return EVENT_NAMES;
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