package org.hierarchicalClustering.utils;

import java.util.Collections;
import java.util.LinkedList;


public class CSVNormalizer {

	public static double[][] normalize(double[][] matrix){
		double minValue        = 0;
		double maxValue        = 0;
		int numberOfObjects    = matrix.length;
		int numberOfAttributes = matrix[0].length;
		
		LinkedList<Double> cvsValues = new LinkedList<Double>();
		double normalizedMatrix[][] = new double[numberOfObjects][numberOfAttributes];
		
		for(int i = 0; i < numberOfAttributes; i++) {	
			for(int j = 0; j < numberOfObjects; j++){	
				cvsValues.add(matrix[j][i]);
			}
			Collections.sort(cvsValues);
			minValue = cvsValues.peekFirst();
			maxValue = cvsValues.peekLast();
			for(int j = 0; j < numberOfObjects; j++){
				if( maxValue - minValue == 0) //Special Case: There is no max or min.
					normalizedMatrix[j][i] = 0;
				else
					normalizedMatrix[j][i] = (matrix[j][i] - minValue) / (maxValue - minValue);
			}
			cvsValues.clear();
		}
		cvsValues = null;
		System.gc();
		return normalizedMatrix;
	}
}
