package org.hierarchicalClustering.dataTypes;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DataPoint implements Comparable <DataPoint>{
	
	private int name;
	private double[] coordinates;
//private int clusterID;
	
	public DataPoint(){
		this.name = 0;
		this.coordinates = new double[1];
	}
	
	public DataPoint(double[] coordinates, int label){
		this.name = label;
		this.coordinates = coordinates;	
	}
	
	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}

	public String coordinatesToString(){
		DecimalFormatSymbols separator = new DecimalFormatSymbols();
		separator.setDecimalSeparator('.');
		DecimalFormat numberFormat = new DecimalFormat("#.###", separator);
		StringBuilder sb = new StringBuilder();
		sb.append("( ");
		for(int i = 0; i < this.coordinates.length; i++){
			sb.append(numberFormat.format(this.coordinates[i]));
			if(i != this.coordinates.length - 1) sb.append(", ");	
		}
		sb.append(" )");
		return sb.toString();
	}
	
	@Override
	public String toString(){
		String message = String.format("Point %d", this.name);
		return message;
	}

	@Override
	public int compareTo(DataPoint o) {
		return this.name - o.name;
	}
	
	@Override
	public Object clone(){
		DataPoint clonedPoint = new DataPoint();
		clonedPoint.setName(this.name);
		clonedPoint.setCoordinates(this.coordinates.clone());
		return clonedPoint;
	}
}