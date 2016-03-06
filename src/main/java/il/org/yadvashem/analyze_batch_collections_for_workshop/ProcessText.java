package il.org.yadvashem.analyze_batch_collections_for_workshop;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.xml.stream.XMLStreamException;

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class ProcessText {

	// public static ByteArrayOutputStream processDescriptions(

	public static String processDescriptions(String collectionDescriptions)
			throws IOException, XMLStreamException {

		// NLPCore annotators
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit,pos, lemma, ner");

		HashMap<String, List<String>> listsOfLemmas = new HashMap<String, List<String>>();

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		Annotation document = new Annotation(collectionDescriptions);

		pipeline.annotate(document);

		ByteArrayOutputStream ab = new ByteArrayOutputStream();

		// ab as XML OutputStream
		pipeline.xmlPrint(document, ab);

		return ab.toString();
	}

}
