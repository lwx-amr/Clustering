import java.util.ArrayList;
import java.util.Random;

public class Clustering {
	
	// Variables
	private String dataFile; 
    private int numOfClusters; 
    private double dataPercent;
    private ArrayList<ArrayList<Double>> dataset, centroids;
    private ArrayList<Integer>[] finalClusters;
    
    
	// Constructor 
    public Clustering(String dataFile, int k, double dataPercent) {
    	this.setDataFile(dataFile);
    	this.setNumOfClusters(k);
    	this.setDataPercent(dataPercent / 100.00);
    	this.centroids = new ArrayList<ArrayList<Double>>();
    }
    
    /* ----------------------- Functions -----------------------------*/
    // Function Call ExcelFileController to read dataset
    public void initializeDataSet() {
		ExcelFileController objEx = new ExcelFileController(dataFile, dataPercent,1000);
		dataset = objEx.readData();
    }
    
    // Initialize k centroid to start with
 	public void initializeMeans() {	
 		// To save all old taken records to don't repeat
 		ArrayList<Integer> takenRecords = new ArrayList<Integer>();
 		
 		// Get random number
      	Random rg = new Random();
      	int randQ;
 		
      	//Loop to get initial centroids
      	for(int i=0; i<numOfClusters; i++) {
 			randQ = rg.nextInt(dataset.size());
 			while(takenRecords.contains(randQ))
 				randQ = rg.nextInt(dataset.size());
 			centroids.add(dataset.get(randQ));
 			takenRecords.add(randQ);
 		}
 	}
 	
 	// Add each of row to it's mean based on distance function
 	public ArrayList<Integer>[] mapDataToClusters(ArrayList<ArrayList<Double>> currentCentroids) {
 		// // initializing array to set clusters;
 		ArrayList<Integer>[] clusters = new ArrayList[numOfClusters];
        for (int i = 0; i < numOfClusters; i++) { 
            clusters[i] = new ArrayList<Integer>(); 
        }
        
 		double minDist, dist;
 		int index;
 		for(int i=0;i<dataset.size();i++) {
 			minDist = Double.MAX_VALUE;
 			dist = 0.00;
 			index = 0;
 			for(int j=0;j<numOfClusters;j++) {
 				dist = calcDistanceBetweenTwo(currentCentroids.get(j), dataset.get(i));
 	 			if(dist<minDist) {
 	 				minDist = dist;
 	 				index = j;
 	 			}
 	 		}
 			clusters[index].add(i);
 		}
 		return clusters;
 	}
 	
 	// Calculate distance between two points with Manhattan formula |x1-x2| + |y1-y2|
  	public double calcDistanceBetweenTwo(ArrayList<Double> a, ArrayList<Double> b) {
  		double distance = 0.00;
  		for(int i=0;i<a.size();i++)
  			distance += Math.abs(a.get(i)-b.get(i));
  		return distance;
  	}
 	
  	// Calculate new centroid values
  	public ArrayList<ArrayList<Double>> calcNewCentroids(ArrayList<Integer>[] currentClusters){
  		ArrayList<ArrayList<Double>> newCetroids = new ArrayList<ArrayList<Double>>();
  		ArrayList<Double> centroid;
  		ArrayList<ArrayList<Double>> clusterContent;
  		double colSum;
  		for(int i=0; i<currentClusters.length; i++) {
  			centroid = new ArrayList<Double>();
  			clusterContent = new ArrayList<ArrayList<Double>>();
  			for(int j=0; j<currentClusters[i].size(); j++) {
  				clusterContent.add(dataset.get(currentClusters[i].get(j)));
  	  		}
  			for(int m=0; m<dataset.get(0).size(); m++) {
  				colSum = 0;
  	  			for(int k=0; k<clusterContent.size(); k++) {
  	  				if(clusterContent.get(k).size()==0) // if we have empty cluster
   	  					continue;
  	  				colSum += clusterContent.get(k).get(m);
  	  			}
  	  			centroid.add(colSum/clusterContent.size());
  	  		}
  			newCetroids.add(centroid);
  		}
  		return newCetroids;
  	}
  	
  	// Check if current centroids are equal to last one
  	public boolean checkEqualty(ArrayList<Integer>[] currentClusters) {
  		for(int i=0; i<currentClusters.length;i++) {
  			if(!currentClusters[i].toString().equals(finalClusters[i].toString())) {
  				return false;
  			}
  		}
  		return true;
  	}
  	
  	// Output execution details 
  	public void output() {
  		System.out.println("\n\n//---- Centroids ------//");
  		for(int i=0; i<centroids.size();i++){
  			System.out.println(centroids.get(i).toString());
  		}
  		System.out.println("\n\n//---- Clusters ------//");
  		for(int i=0; i<finalClusters.length;i++){  			
  			for(int j=0; j<finalClusters[i].size(); j++) {
  				System.out.print("User"+ (finalClusters[i].get(j)+1) + " ");
  			}
  			System.out.println("");
  		}
  	}
 	
  	// Execute the full algorithm
  	public void execute() {
  		
  		// Step 1: Initialization
  		initializeDataSet();
		initializeMeans();
		
		// Step 2: Find first clusters
		ArrayList<Integer>[] currentClusters = mapDataToClusters(centroids);
		finalClusters = currentClusters;
		
		// Step 3: Repeat till no change
		boolean noChange = false;
		while(!noChange) {
			output();
			ArrayList<ArrayList<Double>> newCentroids = calcNewCentroids(currentClusters);
			currentClusters = mapDataToClusters(newCentroids);
			if(checkEqualty(currentClusters))
				noChange = true;
			finalClusters = currentClusters;
			centroids = newCentroids;
		}
		
		// Step 4: Print final output
		output();
  	}
  	
  	/* -------------------- Setters & Getters ----------------------*/
 	public int getNumOfClusters() {
		return numOfClusters;
	}

	public void setNumOfClusters(int numOfClusters) {
		this.numOfClusters = numOfClusters;
	}

	public String getDataFile() {
		return dataFile;
	}

	public void setDataFile(String dataFile) {
		this.dataFile = dataFile;
	}

	public double getDataPercent() {
		return dataPercent;
	}

	public void setDataPercent(double dataPercent) {
		this.dataPercent = dataPercent;
	}
	
	public static void main(String args[]){
		Clustering obj = new Clustering("review_ratings.xlsx", 2, 1);
		obj.execute();	
	}
}