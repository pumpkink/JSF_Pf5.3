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
package org.primefaces.model.amap;

public class Marker extends Overlay {
	
	private boolean clickable = true;
	
	private String cursor;
	
	private boolean draggable = false;
	
	private boolean flat = false;
	
	private String icon;
	
	private LngLat lnglat;

	private String shadow;
	
	private String title;
        
        private String animation;
	
	private boolean visible = true;
        
        

	public Marker(LngLat lnglat) {
		this.lnglat = lnglat;
	}
	
	public Marker(LngLat lnglat, String title, String animation) {
	   	
                this.lnglat = lnglat;
		this.title = title;
                this.animation = animation;
                
	}
	
	public Marker(LngLat lnglat, String title, Object data) {
		super(data);
		this.lnglat = lnglat;
		this.title = title;
	}
	
	public Marker(LngLat lnglat, String title, Object data, String icon) {
		super(data);
		this.lnglat = lnglat;
		this.title = title;
		this.icon = icon;
                
	}
	
	public Marker(LngLat lnglat, String title, Object data, String icon, String shadow) {
		super(data);
		this.lnglat = lnglat;
		this.title = title;
		this.icon = icon;
		this.shadow = shadow;
	}

	public boolean isClickable() {
		return clickable;
	}

	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
	}
	
	public LngLat getPosition() {
		return lnglat;
	}

	public void setPosition(LngLat position) {
		this.lnglat = position;
	}

	public boolean isFlat() {
		return flat;
	}

	public void setFlat(boolean flat) {
		this.flat = flat;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getShadow() {
		return shadow;
	}

	public void setShadow(String shadow) {
		this.shadow = shadow;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
        
        public String getAnimation() {
		return animation;
	}

	public void setAnimation(String animation) {
		this.animation = animation;
	}
}