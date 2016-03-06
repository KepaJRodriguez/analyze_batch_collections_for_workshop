package il.org.yadvashem.analyze_batch_collections_for_workshop;

import java.io.File;
import java.io.IOException;

import javax.xml.stream.XMLStreamException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws XMLStreamException, IOException
    {
        System.out.println( "Hello World!" );
        
        String folder = args[0];
        
    	File directory = new File(folder);
    	
    	File [] listOfFiles = directory.listFiles();
        
    	for (int i = 0 ; i < listOfFiles.length; i++){
    		
    		System.out.println(listOfFiles[i].getName());
    		//ParseEAD.parseEAD(listOfFiles[i]);
    		
    	}
        
        
    }
}
