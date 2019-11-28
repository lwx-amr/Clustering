import java.util.ArrayList;
import java.util.Random;

public class Clustering {
	
	// Variables
	private String dataFile; 
    private int numOfClusters; 
    private double dataPercent;
    private ArrayList<ArrayList<Integer>> dataset, means;
    private ArrayList<Integer>[] clusters;
    
    
	// Constructor 
    public Clustering(String dataFile, int k, double dataPercent) {
    	this.setDataFile(dataFile);
    	this.setNumOfClusters(k);
    	this.setDataPercent(dataPercent);
    	this.dataset = new ArrayList<ArrayList<Integer>>();
    	this.means = new ArrayList<ArrayList<Integer>>();
    	this.clusters = new ArrayList[k];
    	// initializing array;
        for (int i = 0; i < k; i++) { 
            this.clusters[i] = new ArrayList<Integer>(); 
        }
        ArrayList<Integer> obj1= new ArrayList<Integer>(), obj2= new ArrayList<Integer>(),obj3= new ArrayList<Integer>(), obj4= new ArrayList<Integer>(), obj5= new ArrayList<Integer>(),obj6= new ArrayList<Integer>();
        obj1.add(185);
        obj1.add(72);
        obj2.add(170);
        obj2.add(56);
        obj3.add(168);
        obj3.add(60);
        obj4.add(179);
        obj4.add(68);
        obj5.add(182);
        obj5.add(72);
        obj6.add(188);
        obj6.add(77);
        dataset.add(obj1);
        dataset.add(obj2);
        dataset.add(obj3);
        dataset.add(obj4);
        dataset.add(obj5);
        dataset.add(obj6);
        /*means.add(dataset.get(0));
        means.add(dataset.get(1));*/
    }
    
    /* ----------------------- Functions -----------------------------*/
    // Initialize k means to start with
 	public void initializeMeans() {	
 		// Get random number
      	Random rg = new Random();
      	int randQ, oldRand = -1;
 		
      	//Loop to get initial means
      	for(int i=0; i<numOfClusters; i++) {
 			randQ = rg.nextInt(dataset.size());
 			while(randQ==oldRand)
 				randQ = rg.nextInt(dataset.size());
 			means.add(dataset.get(randQ));
 			oldRand = randQ;
 		}
 	}
 	
 	// Add each of row to it's mean based on distance function
 	public void mapDataToClusters() {
 		double minDist, dist;
 		int index;
 		for(int i=0;i<dataset.size();i++) {
 			minDist = Double.MAX_VALUE;
 			dist = 0.00;
 			index = 0;
 			for(int j=0;j<numOfClusters;j++) {
 				dist = calcDistanceBetweenTwo(means.get(j), dataset.get(i));
 	 			if(dist<minDist) {
 	 				minDist = dist;
 	 				index = j;
 	 			}
 	 		}
 			clusters[index].add(i);
 		}
 	}
 	
 	// Calculate distance between two points with Manhattan formula |x-a| + |y-b|
  	public double calcDistanceBetweenTwo(ArrayList<Integer> a, ArrayList<Integer> b) {
  		double distance = 0.00;
  		for(int i=0;i<a.size();i++) {
  			distance += Math.abs(a.get(i)-b.get(i));
  		}
  		return distance;
  	}
 	
  	// Print Clusters
  	public void print() {
  		for(int i=0; i<clusters.length;i++){
  			System.out.println(clusters[i].toString());
  		}
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
		Clustering obj = new Clustering("amr.txt", 2, 70);
		obj.initializeMeans();
		obj.mapDataToClusters();
		obj.print();
	}
}