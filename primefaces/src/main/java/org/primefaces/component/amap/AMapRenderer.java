/*
 * Copyright 2009-2014 PrimeTek.
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

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.behavior.ajax.AjaxBehavior;

import org.primefaces.model.amap.Circle;
import org.primefaces.model.amap.LngLat;
import org.primefaces.model.amap.MapModel;
import org.primefaces.model.amap.Marker;
import org.primefaces.model.amap.Polygon;
import org.primefaces.model.amap.Polyline;
import org.primefaces.model.amap.InfoWindow;
//import org.primefaces.model.amap.Rectangle;
import org.primefaces.renderkit.CoreRenderer;

public class AMapRenderer extends CoreRenderer {
	
	@Override
	public void decode(FacesContext context, UIComponent component) {
        decodeBehaviors(context, component);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
		AMap map = (AMap) component;
		
		encodeMarkup(facesContext, map);
		encodeScript(facesContext, map);
	}
	
	protected void encodeMarkup(FacesContext context, AMap map) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = map.getClientId(context);
		
		writer.startElement("div", map);
		writer.writeAttribute("id", clientId, null);
		if(map.getStyle() != null) writer.writeAttribute("style", map.getStyle(), null);
		if(map.getStyleClass() != null) writer.writeAttribute("class", map.getStyleClass(), null);
		
		writer.endElement("div");
	}
	
	protected void encodeScript(FacesContext context, AMap map) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = map.getClientId(context);
                String widgetVar = map.resolveWidgetVar();
                AMapInfoWindow infoWindow = map.getInfoWindow();
		
                startScript(writer, clientId);
                
                //if(map.isScale()) writer.write("var scale=new AMap.Scale({ visible:true})");
		//if(map.isToolBar()) writer.write(",toolBar=new AMap.ToolBar({ visible:true});");
                
                writer.write("$(function() {");
        
                writer.write("PrimeFaces.cw('AMap','" + widgetVar + "',{");
                writer.write("id:'" + clientId + "'");
     
                
		//Required configuration
		//writer.write(",mapTypeId:AMap.MapTypeId." + amap.getType().toUpperCase());
		writer.write(",center:new AMap.LngLat(" + map.getCenter() + ")");
		writer.write(",zoom:" + map.getZoom());
        
                if(!map.isFitBounds())writer.write(",fitBounds:false");

                //Overlays
		encodeOverlays(context, map);
		
		//Controls
		if(map.isDisableDefaultUI()) writer.write(",disableDefaultUI:true");
		if(map.isStreetView()) writer.write(",streetViewControl:true");
		
		//Options
		if(!map.isDraggable()) writer.write(",draggable:false");
		if(map.isDisableDoubleClickZoom()) writer.write(",disableDoubleClickZoom:true");
		
		//Client events
		if(map.getOnPointClick() != null) writer.write(",onPointClick:function(event) {" + map.getOnPointClick() + ";}");

        /*
         * Behaviors
         * - Adds hook to show info window if one defined
         * - Encodes behaviors
         */
        /*if(infoWindow != null) {
            Map<String,List<ClientBehavior>> behaviorEvents = map.getClientBehaviors();
            List<ClientBehavior> overlaySelectBehaviors = behaviorEvents.get("overlaySelect");
            for(ClientBehavior clientBehavior : overlaySelectBehaviors) {
                ((AjaxBehavior) clientBehavior).setOnsuccess("PF('" + widgetVar + "').openWindow(data)");
            }
        }*/
             if(infoWindow != null) {
                            Map<String,List<ClientBehavior>> behaviorEvents = map.getClientBehaviors();
                            List<ClientBehavior> overlaySelectBehaviors = behaviorEvents.get("overlaySelect");
                            for(ClientBehavior clientBehavior : overlaySelectBehaviors) {
                ((AjaxBehavior) clientBehavior).setOnsuccess("PF('" + widgetVar + "').openWindow(data)");
            }
        }               

        encodeClientBehaviors(context, map);
		
		writer.write("});});");
                
		endScript(writer);
	}

	protected void encodeOverlays(FacesContext context, AMap map) throws IOException {
		MapModel model = map.getModel();
		ResponseWriter writer = context.getResponseWriter();
		
		//Overlays
		if(model != null) {
			if(!model.getMarkers().isEmpty()) 
				encodeMarkers(context, map);
			if(!model.getPolylines().isEmpty()) 
				encodePolylines(context, map);
			if(!model.getPolygons().isEmpty()) 
				encodePolygons(context, map);
 			if(!model.getCircles().isEmpty()) 
				encodeCircles(context, map);
			//if(!model.getRectangles().isEmpty()) 
			//	encodeRectangles(context, map);
		}
        
        AMapInfoWindow infoWindow = map.getInfoWindow();

        if(infoWindow != null) {
            writer.write(",infoWindow: new AMap.InfoWindow({");
            writer.write("id:'" + infoWindow.getClientId(context) + "'");//id: String, j_idt5:infoWindow
            writer.write(",extData:'" + infoWindow.getExtData()+ "'");
            if(infoWindow.isCustom()) writer.write(",isCustom:true"); 
            if(infoWindow.isCloseWhenClickMap()) writer.write(",closeWhenClickMap:true");
            if(infoWindow.getOffset()!= null) writer.write(",offset: new AMap.Pixel(" + infoWindow.getOffset()+")");
            if((infoWindow.getContent()!= null))writer.write(",content:" + infoWindow.getContent());
            writer.write("})");
        }
	}

	protected void encodeMarkers(FacesContext context, AMap map) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		MapModel model = map.getModel();
	
		writer.write(",markers:[");
		
		for(Iterator<Marker> iterator = model.getMarkers().iterator(); iterator.hasNext();) {
			Marker marker = (Marker) iterator.next();
			encodeMarker(context, marker);
			
			if(iterator.hasNext()) 
                            writer.write(",");                       
		}	
		writer.write("]");
	}
	
        protected void encodeMarker(FacesContext context, Marker marker) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		
		writer.write("new AMap.Marker({");
		writer.write("position:new AMap.LngLat(" + marker.getPosition().getLng() + ", " + marker.getPosition().getLat() + ")");
		
		writer.write(",id:'" + marker.getExtData() + "'");//Id für marker
		writer.write(",extData:'" + marker.getExtData() + "'");//Id für marker as ext-data
                
		if(marker.getTitle() != null) writer.write(",title:\"" + escapeText(marker.getTitle()) + "\"");
		if(marker.getIcon() != null) writer.write(",icon:'" + marker.getIcon() + "'");
		if(marker.getShadow() != null) writer.write(",shadow:'" + marker.getShadow() + "'");
		if(marker.getCursor() != null) writer.write(",cursor:'" + marker.getCursor() + "'");
		if(marker.isDraggable()) writer.write(",draggable: true");
		if(!marker.isVisible()) writer.write(",visible: false");
		if(marker.isFlat()) writer.write(",flat: true");
		if(marker.getzIndex() > Integer.MIN_VALUE) writer.write(",zIndex:" + marker.getzIndex());
		
		writer.write("})"); 
	}
	
	protected void encodePolylines(FacesContext context, AMap map) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		MapModel model = map.getModel();
		
		writer.write(",polylines:[");
		for(Iterator<Polyline> lines = model.getPolylines().iterator(); lines.hasNext();) {
			Polyline polyline = (Polyline) lines.next();
			
			writer.write("new AMap.Polyline({");
			writer.write("id:'" + polyline.getExtData() + "'");
			
			encodePaths(context, polyline.getPaths());
			
			writer.write(",strokeOpacity:" + polyline.getStrokeOpacity());
			writer.write(",strokeWeight:" + polyline.getStrokeWeight());
			
			if(polyline.getStrokeColor() != null) writer.write(",strokeColor:'" + polyline.getStrokeColor() + "'");
			if(polyline.getzIndex() > Integer.MIN_VALUE) writer.write(",zIndex:" + polyline.getzIndex());

			writer.write("})");
			
			if(lines.hasNext())
				writer.write(",");
		}
		
		writer.write("]");
	}
	
	protected void encodePolygons(FacesContext context, AMap map) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		MapModel model = map.getModel();
		
		writer.write(",polygons:[");
		
		for(Iterator<Polygon> polygons = model.getPolygons().iterator(); polygons.hasNext();) {
			Polygon polygon = (Polygon) polygons.next();
			
			writer.write("new AMap.Polygon({");
			writer.write("id:'" + polygon.getExtData() + "'");
			
			encodePaths(context, polygon.getPaths());
			
			writer.write(",strokeOpacity:" + polygon.getStrokeOpacity());
			writer.write(",strokeWeight:" + polygon.getStrokeWeight());
			writer.write(",fillOpacity:" + polygon.getFillOpacity());
			
			if(polygon.getStrokeColor() != null) writer.write(",strokeColor:'" + polygon.getStrokeColor() + "'");
			if(polygon.getFillColor() != null) writer.write(",fillColor:'" + polygon.getFillColor() + "'");
			if(polygon.getzIndex() > Integer.MIN_VALUE) writer.write(",zIndex:" + polygon.getzIndex());
	
            writer.write("})");
			
			if(polygons.hasNext())
				writer.write(",");
		}
		
		writer.write("]");
	}
        
	protected void encodeCircles(FacesContext context, AMap map) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		MapModel model = map.getModel();
		
		writer.write(",circles:[");
		
		for(Iterator<Circle> circles = model.getCircles().iterator(); circles.hasNext();) {
			Circle circle = (Circle) circles.next();
			
			writer.write("new AMap.Circle({");
			writer.write("id:'" + circle.getExtData() + "'");
                        
			writer.write(",center:new AMap.LngLat(" +circle.getCenter().getLng()  + ", " + circle.getCenter().getLat() + ")");
			writer.write(",radius:" + circle.getRadius());
                        
			writer.write(",strokeOpacity:" + circle.getStrokeOpacity());
			writer.write(",strokeWeight:" + circle.getStrokeWeight());
			writer.write(",fillOpacity:" + circle.getFillOpacity());
			
			if(circle.getStrokeColor() != null) writer.write(",strokeColor:'" + circle.getStrokeColor() + "'");
			if(circle.getFillColor() != null) writer.write(",fillColor:'" + circle.getFillColor() + "'");
			if(circle.getzIndex() > Integer.MIN_VALUE) writer.write(",zIndex:" + circle.getzIndex());

                        
            writer.write("})");
			
			if(circles.hasNext())
				writer.write(",");
		}
		
		writer.write("]");
	}
        
	/*protected void encodeRectangles(FacesContext context, AMap amap) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		MapModel model = amap.getModel();
		
		writer.write(",rectangles:[");
		
		for(Iterator<Rectangle> rectangles = model.getRectangles().iterator(); rectangles.hasNext();) {
			Rectangle rectangle = (Rectangle) rectangles.next();
			
			writer.write("new google.maps.Rectangle({");
			writer.write("id:'" + rectangle.getId() + "'");
			
            LatLng ne = rectangle.getBounds().getNorthEast();
            LatLng sw = rectangle.getBounds().getSouthWest();
                        
			writer.write(",bounds:new google.maps.LatLngBounds( new google.maps.LatLng(" + ne.getLat() + "," + ne.getLng() +"), new google.maps.LatLng(" + sw.getLat() + "," + sw.getLng() +"))");
			
			writer.write(",strokeOpacity:" + rectangle.getStrokeOpacity());
			writer.write(",strokeWeight:" + rectangle.getStrokeWeight());
			writer.write(",fillOpacity:" + rectangle.getFillOpacity());
			
			if(rectangle.getStrokeColor() != null) writer.write(",strokeColor:'" + rectangle.getStrokeColor() + "'");
			if(rectangle.getFillColor() != null) writer.write(",fillColor:'" + rectangle.getFillColor() + "'");
			if(rectangle.getZindex() > Integer.MIN_VALUE) writer.write(",zIndex:" + rectangle.getZindex());

            writer.write("})");
			
			if(rectangles.hasNext())
				writer.write(",");
		}
		
		writer.write("]");
	}*/
	
	protected void encodePaths(FacesContext context, List<LngLat> paths) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		
		writer.write(",path:[");
		for(Iterator<LngLat> coords = paths.iterator(); coords.hasNext();) {
			LngLat coord = coords.next();
			
			writer.write("new AMap.LngLat(" + coord.getLng() + ", " + coord.getLat() + ")");
			
			if(coords.hasNext())
				writer.write(",");
			
		}
		writer.write("]");
	}
        
    @Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		//Do Nothing
	}

    @Override
	public boolean getRendersChildren() {
		return true;
	}
}