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

public class Bounds {
	
	private LngLat northEast;
	
	private LngLat southWest;
	
	public Bounds(LngLat southWest, LngLat northEast) {
	        this.southWest = southWest;	
                this.northEast = northEast;
	}

	public LngLat getNorthEast() {
		return northEast;
	}

	public void setNorthEast(LngLat northEast) {
		this.northEast = northEast;
	}

	public LngLat getSouthWest() {
		return southWest;
	}

	public void setSouthWest(LngLat southWest) {
		this.southWest = southWest;
	}
}