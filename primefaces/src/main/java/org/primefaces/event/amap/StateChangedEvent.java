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
package org.primefaces.event.amap;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;

import org.primefaces.model.amap.LngLat;
import org.primefaces.model.amap.Bounds;

public class StateChangedEvent extends AjaxBehaviorEvent {
    private static final long serialVersionUID = 1L;

	private final Bounds bounds;
	
	private final int zoomLevel;

	private LngLat center;

	public StateChangedEvent(UIComponent component, Behavior behavior, Bounds bounds, int zoomLevel, LngLat center) {
		super(component, behavior);
		this.bounds = bounds;
		this.zoomLevel = zoomLevel;
                this.center = center;
	}

	@Override
	public boolean isAppropriateListener(FacesListener faceslistener) {
		return (faceslistener instanceof AjaxBehaviorListener);
	}

	@Override
	public void processListener(FacesListener faceslistener) {
		((AjaxBehaviorListener) faceslistener).processAjaxBehavior(this);
	}

	public Bounds getBounds() {
		return bounds;
	}
	
	public int getZooms() {
		return zoomLevel;
	}
        
        public LngLat getCenter() {
            return center;
        }

        public void setCenter(LngLat center) {
            this.center = center;
        }
}
