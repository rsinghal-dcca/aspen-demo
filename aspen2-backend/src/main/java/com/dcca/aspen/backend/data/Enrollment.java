package com.dcca.aspen.backend.data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Enrollment implements Serializable {
	
	 @NotNull
	    private int id = -1;
	 
	    private int trackingid = -1;
	    @NotNull
	    private int npi = -1;

	    @NotNull
	    @Size(min = 2, message = "Business name must have at least two characters")
	    private String busName = "";

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getTrackingid() {
			return trackingid;
		}

		public void setTrackingid(int trackingid) {
			this.trackingid = trackingid;
		}

		public int getnpi() {
			return npi;
		}

		public void setnpi(int nPI) {
			npi = nPI;
		}

		public String getBusName() {
			return busName;
		}

		public void setBusName(String busName) {
			this.busName = busName;
		}
	    
	    
}
