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

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesListener;
import org.primefaces.model.amap.LngLat;

public class ReverseGeocodeEvent extends AjaxBehaviorEvent {
	
    private final LngLat lnglat;
	private final List<String> addresses;
	
	public ReverseGeocodeEvent(UIComponent component, Behavior behavior, LngLat lnglat, List<String> addresses) {
		super(component, behavior);
        this.lnglat = lnglat;
		this.addresses = addresses;
	}

	@Override
	public boolean isAppropriateListener(FacesListener faceslistener) {
		return (faceslistener instanceof AjaxBehaviorListener);
	}

	@Override
	public void processListener(FacesListener faceslistener) {
		((AjaxBehaviorListener) faceslistener).processAjaxBehavior(this);
	}
    
    public LngLat getPosition() {
        return lnglat;
    }

    public List<String> getAddresses() {
        return addresses;
    }
}

