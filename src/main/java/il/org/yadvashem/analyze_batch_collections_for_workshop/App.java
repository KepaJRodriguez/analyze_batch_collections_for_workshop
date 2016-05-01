package il.org.yadvashem.analyze_batch_collections_for_workshop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import com.opencsv.CSVWriter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws XMLStreamException, IOException
    {
        
        String folder = args[0];
        
		String new_csv = "collection_table.csv";
		CSVWriter writer = new CSVWriter(new FileWriter(new_csv), ',');
		String header = "ID#Title#Creator#dates#keywords#person#places#organization";
		String[] header_line = header.split("#");
		writer.writeNext(header_line);

        
    	File directory = new File(folder);
    	
    	File [] listOfFiles = directory.listFiles();
        
    	for (int i = 0 ; i < listOfFiles.length; i++){
    		
    		System.out.println(listOfFiles[i].getName());
    		Collection col = ParseEAD.parseEAD(folder + "/" + listOfFiles[i].getName());
    		String collection_id = listOfFiles[i].getName();

    		String line = "";
    		String[] nextRow;
    		String per = "";
    		String org = "";
    		String loc = "";
    		if (col.person.size()>0){
    			for (int idx = 0; idx < col.person.size(); idx++){
    				if (per.length() > 0){
    					per = per +  "\n"+ col.person.get(idx);
    				} else {
    					per = col.person.get(idx);
    				}
    			}
    		}
    		if (col.organization.size()>0){
    			for (int idx = 0; idx < col.organization.size(); idx++){
    				if (org.length() > 0){
    					org = org +  "\n"+ col.organization.get(idx);
    				} else {
    					org = col.organization.get(idx);
    				}
    			}
    		}
    		if (col.places.size()>0){
    			for (int idx = 0; idx < col.places.size(); idx++){
    				if (loc.length() > 0){
    					loc = loc + "\n"+ col.places.get(idx);
    				} else {
    					loc = col.places.get(idx);
    				}
    			}
    		}
    		
    		// print out!!!
    		// "ID#Title#Creator#dates#keywords#person#organization#places";
			line = collection_id.replace(".xml", "") + "#" + col.title + "#" + col.creator + "#"
					+ col.dates + "#" + "" + "#" + per + "#" + loc + "#" + org;		
    		
			String[] row = line.split("#");
			writer.writeNext(row);
			
    	}
        writer.close();
        
    }
}
