package il.org.yadvashem.analyze_batch_collections_for_workshop;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class ExtractTokens {

	
	public static List<Token> extractTokens(String nlp_out)
			throws FileNotFoundException, XMLStreamException {
		
		
		List<Token> tokenList = new ArrayList<Token>();
		Token currentToken = null;
		String textContent = null;
		
		InputStream is = new ByteArrayInputStream(nlp_out.getBytes());
		
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader xmlStreamReader = inputFactory.createXMLStreamReader(is);
		

		while (xmlStreamReader.hasNext()) {
			int event = xmlStreamReader.next();
			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				if ("token".equals(xmlStreamReader.getLocalName())) {
					currentToken = new Token();
					currentToken.id = Integer.parseInt(xmlStreamReader.getAttributeValue(0));					
				}
				break;
				
			case XMLStreamConstants.CHARACTERS:
				textContent = xmlStreamReader.getText();
				break;
				
			case XMLStreamConstants.END_ELEMENT:
				switch (xmlStreamReader.getLocalName()) {
				case "token":
					tokenList.add(currentToken);
					break;
				case "word":
					if(textContent.equals("-LRB-")){
						currentToken.word = "(";
					} else{
						if(textContent.equals("-RRB-")){
							currentToken.word = ")";
						} else {
							currentToken.word = textContent;
						}
					}					
					break;
				case "CharacterOffsetBegin":
					currentToken.char_begin = Integer.parseInt(textContent) ;
					break;
				case "CharacterOffsetEnd":
					currentToken.char_end = Integer.parseInt(textContent) ;
					break;
				case "NER":
					currentToken.ner = textContent;
					break;
				case "NormalizedNER":
					currentToken.normalized = textContent;
					break;
				}
				break;
			}

		}
				
		return tokenList;		
	}
	
	
}
