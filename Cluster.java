package com.iteso.hierarchical_clustering;

import java.io.Reader;
import java.io.FileReader;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import org.apache.commons.csv.*;	//API para manejo de CVS

import java.io.FileNotFoundException;
import java.io.IOException;


//Podría ser una lista
public class Cluster{		//Otra clase cluster
	
	public static int label = 1;
	//***********************************************************************************************************//
	public class DataPoint{
		
		private String name;
		private double[] coordinates;
		
		public DataPoint(double[] coordinates){
			this.name = "Point " + Integer.toString(label);
			this.coordinates = coordinates;
		}
		
		public String coordinatesToString(){
			DecimalFormatSymbols separator = new DecimalFormatSymbols();
			separator.setDecimalSeparator('.');
			DecimalFormat numberFormat = new DecimalFormat("#.###", separator);
			String format = "[ ";
			for(int i = 0; i < this.coordinates.length; i++){
				format += numberFormat.format(this.coordinates[i]);
				if(i != this.coordinates.length - 1) format += " ,   ";	
			}
			format += " ]";
			return format;
		}
		
		@Override
		public String toString(){
			String message = String.format("%s\nCoordinates %s", this.name, this.coordinatesToString());
			return message;
		}
	}
	//***********************************************************************************************************//
	
	private DataPoint point;
	private int clusterID;
	private double[] centroidCoordinates;
//	private ArrayList<Double> maxDissimilarities;
//	private PriorityQueue<DataPoint> minDissimilarities;
	//Los DataPoints se deben de guardar en una Lista de Sets de CLUSTERS
	
	public Cluster(double[] coordinates){
		this.point = new DataPoint(coordinates);
		this.clusterID = label;
		label++;
	}
	
	public static void agglomerativeClustering(double matrix[][], int linkageType, int distantType){
		LinkedList< HashSet<Cluster> > dendogram = new LinkedList< HashSet<Cluster> >();
		for(int i = 0; i < matrix.length; i++){
			HashSet<Cluster> cluster = new HashSet<Cluster>();
			cluster.add( new Cluster(matrix[i]) );
			dendogram.add(cluster);
		}
		
		
		
		
		
	}
	
	@Override
	public String toString(){
		String message = String.format("%s is inside Cluster %d.", this.point.toString(), this.clusterID);
		return message;
	}
		
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
	
	public static void printMatrix(double[][] matrix){
		
		for(int i = 0; i < matrix.length; i++){
			System.out.printf("[\t");
			for(int j = 0; j < matrix[i].length; j++){
				System.out.printf("%f\t",matrix[i][j]);
			}
			System.out.printf("]\n");
		}
	}
	
	//------------------------------------------------------------------------------------------------------//	
	//Método que saca la distancia mínima entre dos puntos. Devuelve un arreglo con dos puntos.
	public static int[] distMin(double[][] values){
		
		double[][] distancesVec = new double[values.length*(values.length-1)/2][values[0].length];
		int[][] pointsList = new int[distancesVec.length][2];
		int[] minPoints = new int[2];
		HashMap<Double, Integer> invPoints = new HashMap<Double, Integer>();
		HashMap<Integer, Double> points = new HashMap<Integer, Double>();
		PriorityQueue<Double> magnitude = new PriorityQueue<Double>();
		int cont = 0;
				
		
		//ALGORITMO PARA CALCULAR VECTORES
		for(int x = 0; x < values.length; x++) {
			for(int i = x+1; i < values.length; i++) {
				for(int n = 0; n < values[x].length; n++) {
					distancesVec[cont][n] = (values[i][n]-values[x][n]);
//					System.out.println(String.format("Distance[%d][%d] = %f\n", cont, n, distancesVec[cont][n]));
				}
				//GUARDA LOS PUNTOS QUE CORRESPONDEN A CADA POSICIÓN DEL ARREGLO DE VECTORES
				pointsList[cont][0] = x;
				pointsList[cont][1] = i;
//				System.out.println(pointsList[cont][0] + " " + pointsList[cont][1]);
				cont++;

			}
		}		

		//IMPRIME EL ARREGLO DE VECTORES
		for(int i = 0; i < distancesVec.length; i++){
		System.out.printf("[\t");
		for(int j = 0; j < distancesVec[i].length; j++){
			System.out.printf("%f\t",distancesVec[i][j]);
		}
		System.out.printf("]\n");
		}
		
		
		//ALGORITMO QUE SACA LA MAGNITUD DE CADA VECTOR
		double acum = 0;
		for(int i = 0; i < distancesVec.length; i++) {
			for(int j = 0; j < distancesVec[i].length; j++) {
				acum += Math.pow(distancesVec[i][j], 2);
			}
			//AGREGA AL HASHMAP puntos
			points.put((int)i, Math.sqrt(acum));
			//AGREGA AL PRIORITYQUEUE UN NUEVO DATO
			magnitude.add(Math.sqrt(acum));
			acum = 0;
		}
		
		
		//HACEMOS UN HASHMAP DE PUNTOS INVERTIDO PARA PODER BUSCAR LA LLAVE CON EL VALOR
		for(double i = 0; i < points.size(); i++) {
			invPoints.put(points.get((int)i), (int)i);
		}
		
		
		System.out.println("Min magnitude: " + magnitude.peek());
		System.out.println("Vector Array Position" + invPoints.get(magnitude.peek())); //EL PEEK ARROJARÁ UNA POSICIÓN
		System.out.println("Closest points are: " + "[" + pointsList[invPoints.get(magnitude.peek())][0] + ", " + pointsList[invPoints.get(magnitude.peek())][1] + "]");
		
		minPoints[0] = pointsList[invPoints.get(magnitude.peek())][0];
		minPoints[1] = pointsList[invPoints.get(magnitude.peek())][1];
		
		return minPoints;

	}
//--------------------------------------------------------------------------------------------//		


	
}
