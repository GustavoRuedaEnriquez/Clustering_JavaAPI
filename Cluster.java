package com.iteso.hierarchical_clustering;

import java.io.Reader;
import java.io.FileReader;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.*;	//API para manejo de CVS




import java.io.FileNotFoundException;
import java.io.IOException;


//Podría ser una lista
public class Cluster{		//Otra clase cluster
	private String name;
	private double[] coordinates;
	private double[] centroidCoordinates;
	private PriorityQueue<Double> minDissimilarities;
	private Cluster[] containedClusters = { };	//Change
	
	
	public static double cvsMinValue(PriorityQueue <Double> pqueue){
		return pqueue.peek();
	}
	
	public static double cvsMaxValue(PriorityQueue <Double> pqueue){
		PriorityQueue <Double> pqueueClone = pqueue;
		double maxValue = 0;
		int size = pqueueClone.size();
		for(int i = 0; i < size; i++){
			maxValue = pqueue.poll();
		}
		return maxValue;
	}
	
	public static void printMatrix(double[][] matrix){
		for(int i = 0; i < matrix.length; i++){
			System.out.printf("[\t");
			for(int j = 0; j < matrix[i].length; j++){
				System.out.printf("%f\t",matrix[i][j]);
			}
			System.out.printf("]\n");
		}
	}
	
	public static double[][] cvsReader(String filePath) throws IOException, FileNotFoundException{
		int numberOfObjects    = 0;
		int numberOfAttributes = 0;
	
		Reader in = new FileReader(filePath);
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
		CSVRecord record;
		records.iterator();
		Iterator<CSVRecord> iterator = records.iterator();
		ArrayList<CSVRecord>valuesArrayList = new ArrayList<CSVRecord>();
	
		while(iterator.hasNext()){
			record = iterator.next();
			valuesArrayList.add(record);
		}
	
		numberOfObjects    = valuesArrayList.size();
		numberOfAttributes = valuesArrayList.get(0).size();
		double normalMatrix[][]     = new double[numberOfObjects][numberOfAttributes];
		
		for(int i = 0; i < numberOfAttributes; i++) {	
			for(int j = 0; j < numberOfObjects; j++){	
				CSVRecord row = valuesArrayList.get(j);
				String strColumnData   = row.get(i);
				int asciiValue = strColumnData.charAt(0);
				if(asciiValue >= 63 && asciiValue <= 165)
					strColumnData = String.valueOf(asciiValue);
				double data = Double.parseDouble(strColumnData);
				normalMatrix[j][i] = data;
			}
		}
		return normalMatrix;
	}
	
	public static double[][] normalize(double[][] matrix){
		double minValue        = 0;
		double maxValue        = 0;
		int numberOfObjects    = matrix.length;
		int numberOfAttributes = matrix[0].length;
		
		PriorityQueue<Double> cvsValues = new PriorityQueue<Double>(1);

		double normalizedMatrix[][] = new double[numberOfObjects][numberOfAttributes];
		
		for(int i = 0; i < numberOfAttributes; i++) {	
			for(int j = 0; j < numberOfObjects; j++){	
				cvsValues.add(matrix[j][i]);
			}
			minValue = cvsMinValue(cvsValues);
			maxValue = cvsMaxValue(cvsValues);
			for(int j = 0; j < numberOfObjects; j++){
				if( maxValue - minValue == 0) //Special Case: There is no max or min.
					normalizedMatrix[j][i] = 0;
				else
					normalizedMatrix[j][i] = (matrix[j][i] - minValue) / (maxValue - minValue);
			}
		}
		return normalizedMatrix;
	}
	
		
}
