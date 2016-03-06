package il.org.yadvashem.analyze_batch_collections_for_workshop;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class ParseEAD {

	
	public static String parseEAD (String ead_file) throws FileNotFoundException, XMLStreamException{
	
		// print text in a file
		// print NERs in the table
		
		FileInputStream fileInputStream = new FileInputStream(ead_file);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader xmlStreamReader = inputFactory
				.createXMLStreamReader(fileInputStream);
		

		while (xmlStreamReader.hasNext()) {
			
			int event = xmlStreamReader.next();
			switch (event) {
			
			
			
			
			}
			
			
			
		}
		
		
		return null;
		
	}
	
}
