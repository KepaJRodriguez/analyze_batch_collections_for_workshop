package il.org.yadvashem.analyze_batch_collections_for_workshop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class ParseEAD {

	
	public static Collection parseEAD (String ead_file) throws XMLStreamException, IOException{
	
		// print text in a file
		// print NERs in the table
		String collection_id = ead_file.replace(".xml", "").replace("EADs/", "");
		Collection col = new Collection();
		String title = "";
		String dates = "";
		String textContent = "";
		List<String> text_fragments = new ArrayList<String>();
		
		List<String> person = new ArrayList<String>();
		List<String> location = new ArrayList<String>();
		List<String> organization = new ArrayList<String>();
		
		FileInputStream fileInputStream = new FileInputStream(ead_file);
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader xmlStreamReader = inputFactory
				.createXMLStreamReader(fileInputStream);
		
		boolean titleproper = false;
		boolean scopecontent = false;
		boolean bioghist = false;
		boolean custodhist = false;
		boolean odd = false;
		boolean langmaterial = false;

		while (xmlStreamReader.hasNext()) {
			
			int event = xmlStreamReader.next();
			switch (event) {
			
			case XMLStreamConstants.START_ELEMENT:
				switch (xmlStreamReader.getLocalName()) {
				case "titleproper":
					titleproper = true;
					break;
				case "scopecontent":
					scopecontent = true;
					break;
				case "bioghist":
					bioghist = true;
					break;
				case "custodhist":
					custodhist = true;
					break;
				case "odd":
					odd = true;
					break;
				}
			break;
			
			
			case XMLStreamConstants.CHARACTERS:
				textContent = xmlStreamReader.getText();
				break;
				
			case XMLStreamConstants.CDATA:
				textContent = xmlStreamReader.getText();
				if (scopecontent == true) {
					text_fragments.add(textContent);
				}
				if (bioghist == true) {
					text_fragments.add(textContent);
				}
				if (odd == true) {
					text_fragments.add(textContent);
				}
				break;
				
			case XMLStreamConstants.END_ELEMENT:
				switch (xmlStreamReader.getLocalName()) {
				case "titleproper":
					title = textContent;
					break;
				case "p":
					if (scopecontent == true) {
						text_fragments.add(textContent);
					}
					if (bioghist == true) {
						text_fragments.add(textContent);
					}
					if (custodhist == true) {
						text_fragments.add(textContent);
					}
					if (odd == true) {
						text_fragments.add(textContent);
					}
					break;

				case "unittitle":
					text_fragments.add(textContent);
					break;
				case "scopecontent":
					scopecontent = false;
					break;
				case "bioghist":
					bioghist = false;
					break;
				case "odd":
					odd = false;
					break;
				case "langmaterial":
					langmaterial = false;
					break;
				case "physdesc":
					text_fragments.add(textContent);
					break;
				case "custodhist":
					text_fragments.add(textContent);
					break;

				}

			}

		}
		
		String text_file_name = "text/" + collection_id + ".txt";
		File textfile = new File(text_file_name);
		BufferedWriter text_writer = new BufferedWriter(new FileWriter(textfile));		
		for (int i = 0; i < text_fragments.size(); i++){
			text_writer.write(text_fragments.get(i));
			text_writer.newLine();
			String analysis = ProcessText.processDescriptions(text_fragments.get(i));
			List<Token> tokens = ExtractTokens.extractTokens(analysis);
			 HashMap<String, List<String>> entities = ExtractEntities.extractEntities(tokens);
			 person.addAll(entities.get("person"));
			 location.addAll(entities.get("location"));
			 organization.addAll(entities.get("organization"));
		}
		text_writer.close();
		
		col.title = title;
		col.organization.addAll(AuxiliaryMethods.removeDuplicates(organization));
		col.person.addAll(AuxiliaryMethods.removeDuplicates(person));
		col.places.addAll(AuxiliaryMethods.removeDuplicates(location));
		
		return col;
		
	}
	
}
