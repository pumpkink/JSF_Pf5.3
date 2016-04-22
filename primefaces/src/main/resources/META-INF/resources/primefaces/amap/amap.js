/* 
 * PrimeFaces Gaode Maps Widget 
 */
/* global AMap */

PrimeFaces.widget.AMap = PrimeFaces.widget.DeferredWidget.extend({
    
    init: function(cfg) {
        this._super(cfg);

        this.renderDeferred();
    },
    
    _render: function() {
        this.map = new AMap.Map(document.getElementById(this.id), this.cfg);
        //this.cfg.fitBounds = !(this.cfg.fitBounds === false); //Bei AMap gibt es kein fitBounds Parameter
        //this.viewport = this.map.getBounds();
        
        //conf markers
        if(this.cfg.markers) {
            this.configureMarkers();
        }

        //add polylines
        if(this.cfg.polylines) {
            this.configurePolylines();
        }

        //add polygons
        if(this.cfg.polygons) {
            this.configurePolygons();
        }

        //add circles
        if(this.cfg.circles) {
            this.configureCircles();
        }

        //general map events
        this.configureEventListeners();

        //bind infowindow domready for dynamic content.
        if(this.cfg.infoWindow){
            var _self = this;
            //this.loadWindow(this.cfg.infoWindow.He.content);
            AMap.event.addListener(this.cfg.infoWindow, 'domready', function() {
                _self.loadWindow(_self.cfg.infoWindow.content);
            }); 
        }
    },
    
    getMap: function() {
        return this.map;
    },
    
    getInfoWindow: function() {
        return this.cfg.infoWindow;
    },
    
    loadWindow: function(content){
        this.jq.find(PrimeFaces.escapeClientId(this.getInfoWindow().getExtData() + '_content')).html(content||'');
    },    
    
    openWindow: function(responseXML) {
        var infoWindow = this.getInfoWindow();
        var $this = this;

        PrimeFaces.ajax.Response.handle(responseXML, null, null, {
            widget: infoWindow,
            handle: function(content) {
                $this.cfg.infoWindowContent = content;
                //$this.cfg.infoWindow.getContent(content);
                infoWindow.setContent('<div id="' + infoWindow.getExtData() + '_content">' + content + '</div>');
                //infoWindow.open($this.getMap(), $this.selectedOverlay);
                infoWindow.open($this.getMap(), $this.selectedOverlay.getPosition());
            }
        });

        return true;
    },
    
    /*closeInfoWindow: function() {
       this.map.clearInfoWindow();
    },
    
    configureInfoWindow: function(title, content) {
        var info = document.createElement("div");
        info.className = "info";
        
        //info.style.width = "400px";
        // Definition for top Subject
        var top = document.createElement("div");
        var titleD = document.createElement("div");
        var closeX = document.createElement("img");
        top.className = "info-top";
        titleD.innerHTML = title;
        closeX.src = "http://webapi.amap.com/images/close2.gif";
        if(closeX.onclick) this.map.clearInfoWindow();
                
        top.appendChild(titleD);
        top.appendChild(closeX);
        info.appendChild(top);

        // Definition for middle Content
        var middle = document.createElement("div");
        middle.className = "info-middle";
        middle.style.backgroundColor = 'white';
        middle.innerHTML = content;
        info.appendChild(middle);

        // Definition for bottom Content 
        var bottom = document.createElement("div");
        bottom.className = "info-bottom";
        bottom.style.position = 'relative';
        bottom.style.top = '0px';
        bottom.style.margin = '0 auto';
        var sharp = document.createElement("img");
        sharp.src = "http://webapi.amap.com/images/sharp.png";
        bottom.appendChild(sharp);
        info.appendChild(bottom);
        return info;
    }, */
   
    
    /*loadWindow: function(content){
        this.jq.find(PrimeFaces.escapeClientId(this.getInfoWindow().He.id + '_content')).html(content||'');
    },*/

    
    configureMarkers: function() {
        var _self = this;

        for(var i=0; i < this.cfg.markers.length; i++) {
            var marker = this.cfg.markers[i];
            marker.setMap(this.map);

            //extend viewport
            if(this.cfg.fitBounds)
                this.extendView(marker);

            //overlay select
            AMap.event.addListener(marker, 'click', function(event) {
                _self.fireOverlaySelectEvent(event, this);

            });

            //marker drag
            AMap.event.addListener(marker, 'dragend', function(event) {
                _self.fireMarkerDragEvent(event, this);
            });
        }
    },
    
    fireMarkerDragEvent: function(event, marker) {
        if(this.hasBehavior('markerDrag')) {
            var markerDragBehavior = this.cfg.behaviors['markerDrag'];
            //point = marker.Ae.position;
            var point = event.lnglat;

            var ext = {
                params: [
                    {name: this.id + '_markerId', value: marker.getExtData()},
                    {name: this.id + '_lng', value: point.getLng()},
                    {name: this.id + '_lat', value: point.getLat()}
                    
                ]
            };

            markerDragBehavior.call(this, ext);
        }
    },
            
    geocode: function(address) {
        var $this = this;
        if(this.hasBehavior('geocode')) {
            var geocodeBehavior = this.cfg.behaviors['geocode'],
                geocoder,
                lngs = [],
                lats = [],
                addresses = [];
            
            AMap.service(["AMap.Geocoder"], function() {
                geocoder = new AMap.Geocoder();
                geocoder.getLocation(address, function(status, result) {
                    if (status === 'complete' && result.info === 'OK') { 
                       //aufrufen Callback Funktion
                       geocoder_Callback(result);
                   }
                else {
                    PrimeFaces.error('Geocode was not found');
                  }
                });
            });
        
            //Callback Function, show the Output of Geocode
            function geocoder_Callback(Resultdata){
              geocoderArray = [];
              geocoderArray = Resultdata.geocodes;
              for(var i = 0; i < geocoderArray.length; i++) {
                        lats.push(geocoderArray[i].location.getLat());
                        lngs.push(geocoderArray[i].location.getLng());
                        addresses.push(geocoderArray[i].formattedAddress);
                    }
                    
                    if(geocoderArray.length) {
                        var ext = {
                            params: [
                                {name: $this.id + '_query', value: address},
                                {name: $this.id + '_addresses', value: addresses.join('_primefaces_')},
                                {name: $this.id + '_lng', value: lngs.join()},
                                {name: $this.id + '_lat', value: lats.join()}
                                
                            ]
                        };
                        
                        geocodeBehavior.call(this, ext);
                    }
            }
        }
    },
        
    reverseGeocode: function(lng, lat) {
        var $this = this;
        if(this.hasBehavior('reverseGeocode')) {
            var reverseGeocoder = this.cfg.behaviors['reverseGeocode'],
                geocoder,
                lnglat = new AMap.LngLat(lng, lat),
                addresses = [];
        
            AMap.service(["AMap.Geocoder"], function() {
                geocoder = new AMap.Geocoder();
                geocoder.getAddress(lnglat, function(status, result) {
                    if (status === 'complete' && result.info === 'OK') { 
                       //aufrufen Callback Funktion
                       reverseGeocoder_Callback(result);
                   }
                else {
                    PrimeFaces.error('Geocode was not found');
                  }
                });
            });
            
            function reverseGeocoder_Callback(Resultdata){
              //addresses = Resultdata.regeocode.formattedAddress;
              for(var i = 0; i < Resultdata.regeocode.pois.length; i++) {
                 if (Resultdata.regeocode.pois[i]) {
                 addresses[i] = Resultdata.regeocode.pois[i].address;
                        } 
                    }
                    
                    if(0<addresses.length) {
                       var ext = {
                            params: [
                                {name: $this.id + '_address', value: addresses.join('_primefaces_')},
                                {name: $this.id + '_lng', value: lng},    
                                {name: $this.id + '_lat', value: lat}
                                
                            ]
                        };
                        
                        reverseGeocoder.call(this, ext);
                    }
                    else {
                        PrimeFaces.error('No results found');    
                    }
            }
           
        }
    },         
    
    configurePolylines: function() {
        this.addOverlays(this.cfg.polylines);
    },
    
    configureCircles: function() {
        this.addOverlays(this.cfg.circles);
    },
    
    configureRectangles: function() {
        this.addOverlays(this.cfg.rectangles);
    },
    
    configurePolygons: function() {
        this.addOverlays(this.cfg.polygons);
    },
    
    fireOverlaySelectEvent: function(event, overlay) {
        this.selectedOverlay = overlay;

        if(this.hasBehavior('overlaySelect')) {
            var overlaySelectBehavior = this.cfg.behaviors['overlaySelect'];

            var ext = {
                params: [
                    {name: this.id + '_overlayId', value: overlay.getExtData()}
                ]
            };

            overlaySelectBehavior.call(this, ext);
        }
    },
    
    configureEventListeners: function() {
        var _self = this;

        this.cfg.formId = $(PrimeFaces.escapeClientId(this.id)).parents('form:first').attr('id');

        //client side events
        if(this.cfg.onPointClick) {
            AMap.event.addListener(this.map, 'click', function(event) {
                _self.cfg.onPointClick(event);
            });
        }

        //behaviors
        this.configureStateChangeListener();
        this.configurePointSelectListener();
    },
    
    configureStateChangeListener: function() {
        var _self = this,
        onStateChange = function(event) {
            _self.fireStateChangeEvent(event);
        };

        AMap.event.addListener(this.map, 'zoomchange', onStateChange);
        AMap.event.addListener(this.map, 'dragend', onStateChange);
    },
    
    fireStateChangeEvent: function(event) {
        if(this.hasBehavior('stateChanged')) {
            var stateChangeBehavior = this.cfg.behaviors['stateChanged'],
            bounds = this.map.getBounds();

            var ext = {
                params: [
                    {name: this.id + '_northeast', value: bounds.getNorthEast().getLng() + ',' + bounds.getNorthEast().getLat()},
                    {name: this.id + '_southwest', value: bounds.getSouthWest().getLng() + ',' + bounds.getSouthWest().getLat()},
                    {name: this.id + '_center', value: bounds.getCenter().getLng() + ',' + bounds.getCenter().getLat()},
                    {name: this.id + '_zooms', value: this.map.getZoom()}
                ]
            };

            stateChangeBehavior.call(this, ext);
        }
    },
    
    configurePointSelectListener: function() {	
        var _self = this;

        AMap.event.addListener(this.map, 'click', function(event) {
            _self.firePointSelectEvent(event);
        });
    },
    
    firePointSelectEvent: function(event) {
        if(this.hasBehavior('pointSelected')) {
            var pointSelectBehavior = this.cfg.behaviors['pointSelected'];
            var point = event.lnglat;
 
            var ext = {
                params: [
                    {name: this.id + '_pointLngLat', value: point.getLng() + ',' + point.getLat()}
                ]
            };

            pointSelectBehavior.call(this, ext);
        }
    },
    
    addOverlay: function(overlay) {
        overlay.setMap(this.map);
    },
    
    addOverlays: function(overlays) {
        var _self = this;

        $.each(overlays, function(zIndex, item){
            item.setMap(_self.map);

            _self.extendView(item);

            //bind overlay click event
            AMap.event.addListener(item, 'click', function(event) {
                _self.fireOverlaySelectEvent(event, item);
            });
        });
    },
    
    extendView: function(overlay){
        if( this.cfg.fitBounds && overlay){
            var _self = this;
            this.viewport = this.viewport || new AMap.Bounds();
            if(overlay instanceof AMap.Marker)
                this.viewport.extend(overlay.getPosition());

            else if(overlay instanceof AMap.Circle || overlay instanceof AMap.Rectangle)//No Rectangle Class in GaodeMap
                this.viewport.union(overlay.getBounds());

            else if(overlay instanceof AMap.Polyline || overlay instanceof AMap.Polygon)
                overlay.getPath().forEach(function(item, zIndex){
                    _self.viewport.extend(item);
                });
        }
    },
    
    checkResize: function() {
        AMap.event.trigger(this.map, 'resize');
        this.map.setZoom(this.map.getZoom());
    },
    
    hasBehavior: function(event) {
        if(this.cfg.behaviors) {
            return this.cfg.behaviors[event] !== undefined;
        }

        return false;
    }
 
});