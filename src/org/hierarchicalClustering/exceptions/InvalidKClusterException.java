package org.hierarchicalClustering.exceptions;

public class InvalidKClusterException extends RuntimeException{
	private int kEntry;
	private int limit;
	
	public InvalidKClusterException(int kEntry, int limit){
		super("InvalidKClusterException");
		this.kEntry = kEntry;
		this.limit = limit;
	}
	public String toString(){
		String text = String.format(" KClusters must be equal or lower than the number of elementes in rawData (%d) and bigger than 0.", this.limit);
		return getMessage() + text;
	}
}
