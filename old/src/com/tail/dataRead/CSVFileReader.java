package dataRead;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.csvreader.CsvReader;

/**
 * 
 * 
 * @author Marcio
 *
 */
public  class CSVFileReader {
	
	public static List<List<String>> read(String fileName) throws IOException {
		
		List<List<String>> llObj = new ArrayList<List<String>>();
		CsvReader reader = new CsvReader(fileName);
		reader.readHeaders();
		while (reader.readRecord())
		{
			List<String> lObj = new ArrayList<String>();
			for (int i = 0; i < reader.getColumnCount(); i++) {
				lObj.add(reader.get(i).toString());
			}
			llObj.add(lObj);
		}
		
		reader.close();
		
		
		
		return llObj;
		
	}
}
