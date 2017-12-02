package tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.hierarchicalClustering.clusterAlgorithms.*;

public class TestCluster {

	public static void main(String[] args) throws FileNotFoundException, IOException{
		DistanceType dist = DistanceType.MANHATTAN;
		CompleteLinkageAgglomerative d1 = new CompleteLinkageAgglomerative("C:/Users/Gus/Downloads/CSV Files/Easy_Dataset.csv", dist);
		System.out.println(d1);
		d1.clusterize(5);
		System.out.println();
		System.out.println(d1);
		System.out.println(d1.listWithPoints());
		
	}

}
