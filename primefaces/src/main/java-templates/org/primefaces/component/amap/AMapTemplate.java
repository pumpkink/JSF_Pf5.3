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

    