import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FilesController {

	// Variables
	private String dataFile;
    private double dataPercent;
    private ArrayList<ArrayList<Integer>> dataset;
    
	// Constructor 
    public FilesController(String dataFile, double dataPercent) {
    	this.dataFile = dataFile;
    	this.dataPercent = dataPercent;
    	this.dataset = new ArrayList<ArrayList<Integer>>();
    }
    
    // Read data from file
 	public void readDataset() {
 		int linesReaded = 0;
     	BufferedReader data_in;
 		ArrayList<Integer> transation;
 		try {
 			double numOfLines = calcNumOfLines(dataFile);
 			double maxRead = (int) numOfLines * dataPercent;
 			data_in = new BufferedReader(new FileReader(dataFile));
 			while (data_in.ready() && linesReaded < maxRead) {    
 				transation = new ArrayList<Integer>();
 	    		String line=data_in.readLine();
 	    		linesReaded++;
 	    		StringTokenizer t = new StringTokenizer(line," ");
 	    		while (t.hasMoreTokens()) {
 	    			int x = Integer.parseInt(t.nextToken());
 	    			transation.add(x);
 	    		}
 	    		dataset.add(transation);
 	    	}
 		} catch (NumberFormatException | IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 	}
    
 	// Calculate number of lines 
 	public double calcNumOfLines(String filename) {
 		InputStream is;
 		 double count = 0.00;
 			try {
 				is = new BufferedInputStream(new FileInputStream(filename));
 		        byte[] c = new byte[1024];
 		        int readChars = 0;
 		        while ((readChars = is.read(c)) != -1) {
 		            for (int i = 0; i < readChars; ++i) {
 		                if (c[i] == '\n') {
 		                ++count;
 		            }
 		        }
 		    }
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		return count;
 	}

 	
 	public static void main(String args[]){
		Clustering obj = new Clustering("amr.txt", 2, 70);
		obj.initializeMeans();
		obj.mapDataToClusters();
		obj.print();
	}
    
}
