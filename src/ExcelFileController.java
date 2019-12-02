import java.io.File;  
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
public class ExcelFileController  {
	
	// Variables
	private String dataFile;
    private double dataPercent;
    private int numOfLines;
    
	// Constructor 
    public ExcelFileController(String dataFile, double dataPercent, int numOfLines) {
    	this.dataFile = dataFile;
    	this.dataPercent = dataPercent;
    	this.numOfLines = numOfLines;
    }
    
    // Function to read dataset
	public ArrayList<ArrayList<Double>> readData() { 
    	ArrayList<ArrayList<Double>> dataset = new ArrayList<ArrayList<Double>>();
		try  {  
			File file = new File(dataFile);   //creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file 
			//creating Workbook instance that refers to .xlsx file  
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);     //creating a Sheet object to retrieve object  
			Iterator<Row> itr = sheet.iterator();    //iterating over excel file
			double maxData = numOfLines * dataPercent;
			int itrCount = 0;
			ArrayList<Double> dataRow;
			System.out.println("\n\nNumber of lines to read: " + maxData + "\nData Percent: " + dataPercent);
			while (itr.hasNext() && itrCount<=maxData) {  
				Row row = itr.next();
				itrCount++;
				Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
				dataRow = new ArrayList<Double>();
				boolean numeric = false;
				while (cellIterator.hasNext()) {  
					Cell cell = cellIterator.next();  
					if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {    //field that represents number cell type  
						dataRow.add((double) cell.getNumericCellValue());
						numeric = true;
					}
				}
				if(numeric)
					dataset.add(dataRow);
			}
			return dataset;  
		}  
		catch(Exception e)  {  
			e.printStackTrace();
			return null;  
		}  
	}  
}  
