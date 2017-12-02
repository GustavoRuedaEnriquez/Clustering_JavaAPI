package org.hierarchicalClustering.clusterAlgorithms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import org.hierarchicalClustering.dataTypes.Cluster;
import org.hierarchicalClustering.dataTypes.Distance;
import org.hierarchicalClustering.utils.CSVNormalizer;
import org.hierarchicalClustering.utils.CSVReader;

public abstract class AgglomerativeCluster{
	//atributos
	protected double[][] rawData;
	protected LinkedList<Distance> distances = new LinkedList<Distance>();
	protected LinkedList<Cluster> dendogram = new LinkedList<Cluster>();
	
	//constructores
	public AgglomerativeCluster(){
		this.rawData = new double[1][1];
	}
	
	public AgglomerativeCluster(String path, DistanceType dType) throws FileNotFoundException, IOException{
		this.rawData = CSVNormalizer.normalize(CSVReader.cvsReader(path));
		this.distances = Distance.getDistances(dType, rawData);
		this.dendogram = Cluster.createClusterList(rawData);
	}

	public double[][] getRawData() {
		return rawData;
	}

	public void setRawData(double[][] rawData) {
		this.rawData = rawData;
	}

	public LinkedList<Distance> getDistances() {
		return distances;
	}

	public void setDistances(LinkedList<Distance> distances) {
		this.distances = distances;
	}

	public LinkedList<Cluster> getDendogram() {
		return dendogram;
	}

	public void setDendogram(LinkedList<Cluster> dendogram) {
		this.dendogram = dendogram;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.dendogram.size(); i++){
			sb.append("Cluster " + this.dendogram.get(i).getClusterID() +  " contains " + this.dendogram.get(i).points.size() + " point(s) in it\n");
		}
		return sb.toString();
	}
	
	
	
	public String listWithPoints(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.dendogram.size(); i++){
			sb.append(this.dendogram.get(i).toString());
		}
		return sb.toString();
	}
	
	public abstract void clusterize(int kClusters);
	
	
	
}