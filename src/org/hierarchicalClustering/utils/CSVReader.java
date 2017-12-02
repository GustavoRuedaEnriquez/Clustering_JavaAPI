package org.hierarchicalClustering.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class CSVReader {
	
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
}
