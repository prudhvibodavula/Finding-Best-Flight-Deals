package com.asu.model;


public class DealsFilter {
	String source;
	String travelDate;
	String noOfTickets;
	String destination;
	String price;
	String _401K;
	String airlinesName;
	String duration;
	
	public DealsFilter(String source, String travelDate, String noOfTickets, String destination, String price, String airlinesName, String duration,String _401k) {
		super();
		this.source = source;
		this.travelDate = travelDate;
		this.noOfTickets = noOfTickets;
		this.destination = destination;
		this.price = price;
		this.airlinesName = airlinesName;
		this.duration = duration;
		_401K = _401k;		
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTravelDate() {
		return travelDate;
	}
	public void setCompanyName(String travelDate) {
		this.travelDate = travelDate;
	}
	public String getNoOfTickets() {
		return noOfTickets;
	}
	public void setNoOfTickets(String noOfTickets) {
		this.noOfTickets = noOfTickets;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getPrice() {
		return price;
	}
	public void setState(String price) {
		this.price = price;
	}
	
	public String get_401K() {
		return _401K;
	}
	public void set_401K(String _401k) {
		_401K = _401k;
	}
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getAirlinesName() {
		return airlinesName;
	}
	public void setAirlinesName(String airlinesName) {
		this.airlinesName = airlinesName;
	}
	
	@Override
	public String toString() {
		return "JobFilter [source=" + source + ", travelDate=" + travelDate + ", noOfTickets=" + noOfTickets + ", destination="
				+ destination + ", price=" + price + ", _401K =" + _401K +",  airlinesName =" + airlinesName +",  duration =" + duration +"]";
	}
	

	
	

	
	

}