package org.hierarchicalClustering.dataTypes;

import java.util.Collections;
import java.util.LinkedList;

import org.hierarchicalClustering.clusterAlgorithms.DistanceType;

public class Distance implements Comparable<Distance> {
	private double distance;
	private int pointA;
	private int pointB;

	public Distance(double distance, int pointA, int pointB) {
		setDistance(distance);
		setPointA(pointA);
		setPointB(pointB);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getPointA() {
		return pointA;
	}

	public void setPointA(int pointA) {
		this.pointA = pointA;
	}

	public int getPointB() {
		return pointB;
	}

	public void setPointB(int pointB) {
		this.pointB = pointB;
	}

	@Override
	public String toString() {
		String message = String.format("Distance from Point %d to Point %d : %f", this.pointA, this.pointB,
				this.distance);
		return message;
	}

	public int compareTo(Distance d) {
		if (this.getDistance() < d.getDistance())
			return -1;
		if (this.getDistance() > d.getDistance())
			return 1;
		return 0;
	}

	public static LinkedList<Distance>  getDistances(DistanceType distanceType, double[][] matrix){		//Excepción cuando la distancia no es válida
		LinkedList<Distance> distances = new LinkedList<Distance>();
		switch(distanceType){
			case EUCLIDEAN:
				distances = getEcludianDistances(matrix);
				break;
				
			case MANHATTAN:
				distances = getManhattanDistances(matrix);
				break;
				
			default:
				break;
		}
		return distances;
	}
	
	public static LinkedList<Distance> getEcludianDistances(double[][] matrix) {
		int objects = matrix.length;
		int attributes = matrix[0].length;
		LinkedList<Distance> distances = new LinkedList<Distance>();
		for (int h = 0; h < objects; h++) {
			double summation = 0;
			for (int i = 0; i < objects; i++) {
				if (h == i || h > i)
					continue;
				for (int j = 0; j < attributes; j++) {
					summation += (matrix[h][j] - matrix[i][j]) * (matrix[h][j] - matrix[i][j]);
				}
				double distance = Math.sqrt(summation);
				distances.add(new Distance(distance, h + 1, i + 1));
			}
		}
		Collections.sort(distances);
		return distances;
	}

	public static LinkedList<Distance> getManhattanDistances(double[][] matrix) {
		int objects = matrix.length;
		int attributes = matrix[0].length;
		LinkedList<Distance> distances = new LinkedList<Distance>();
		for (int h = 0; h < objects; h++) {
			double summation = 0;
			for (int i = 0; i < objects; i++) {
				if (h == i || h > i)
					continue;
				for (int j = 0; j < attributes; j++) {
					summation += Math.abs(matrix[h][j] - matrix[i][j]);
				}
				double distance = summation;
				distances.add(new Distance(distance, h + 1, i + 1));
			}
		}
		Collections.sort(distances);
		return distances;
	}
}
