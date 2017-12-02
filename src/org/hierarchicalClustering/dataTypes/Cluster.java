package org.hierarchicalClustering.dataTypes;

import java.util.LinkedList;
import java.util.TreeSet;

public class Cluster {
	private static int label = 1;
	public TreeSet<DataPoint> points = new TreeSet<DataPoint>() ;
	private int clusterID;	
	private double[] centroidCoordinates;
	
	public Cluster(){
		this.points = new TreeSet<DataPoint>();
		this.centroidCoordinates = new double[1];
	}
	
	public Cluster(double[] coordinates){
		this.points.add(new DataPoint(coordinates, label));
		this.clusterID = label;
		this.centroidCoordinates = new double[1];
		label++;
	}
	
	public TreeSet<DataPoint> getPoints() {
		return points;
	}

	public void setPoints(TreeSet<DataPoint> points) {
		this.points = points;
	}

	public int getClusterID() {
		return clusterID;
	}

	public void setClusterID(int clusterID) {
		this.clusterID = clusterID;
	}

	public double[] getCentroidCoordinates() {
		return centroidCoordinates;
	}

	public void setCentroidCoordinates(double[] centroidCoordinates) {
		this.centroidCoordinates = centroidCoordinates;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone(){
		Cluster clonedCluster = new Cluster();
		clonedCluster.setCentroidCoordinates(this.centroidCoordinates.clone());
		clonedCluster.setClusterID(this.clusterID);
		clonedCluster.setPoints((TreeSet<DataPoint>) this.points.clone());
		return clonedCluster;
	}
	
	@Override
	public String toString(){
		String message = String.format("\n%s is inside Cluster %d", this.points.toString(), this.clusterID);
		return message;
	}
	
	
	public static LinkedList< Cluster > createClusterList(double[][] matrix){
		LinkedList< Cluster > dendogram = new LinkedList< Cluster >();
		for(int i = 0; i < matrix.length; i++){
			dendogram.add(new Cluster(matrix[i]));		
		}
		return dendogram;
	}
}
