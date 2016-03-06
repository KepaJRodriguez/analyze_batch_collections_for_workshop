package il.org.yadvashem.analyze_batch_collections_for_workshop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


public class ExtractEntities {
	public static HashMap<String, List<String>> extractEntities(List<Token> tokens) {

		HashMap<Integer, Token> hash_of_tokens = new HashMap<Integer, Token>();
		List<Integer> token_ids = new ArrayList<Integer>();
		Entity entity = null;
		List<Entity> entities = new ArrayList<Entity>();
		
		HashMap<String, List<String>> entity_map = new HashMap<String, List<String>>();
		List<String> person = new ArrayList<String>();
		List<String> organization = new ArrayList<String>();
		List<String> location = new ArrayList<String>();
		

		for (int i = 0; i < tokens.size(); i++) {
			hash_of_tokens.put(tokens.get(i).id, tokens.get(i));
			token_ids.add(tokens.get(i).id);
		}

		Collections.sort(token_ids);

		boolean entity_inside = false;
		for (int i = 0; i < token_ids.size(); i++) {
			if (entity_inside == false) {
				if (hash_of_tokens.get(token_ids.get(i)).ner
								.equals("LOCATION")
						|| hash_of_tokens.get(token_ids.get(i)).ner
								.equals("ORGANIZATION")
						|| hash_of_tokens.get(token_ids.get(i)).ner
								.equals("PERSON")) {

					entity = new Entity();
					entity.type = hash_of_tokens.get(token_ids.get(i)).ner;
					entity.char_begin = hash_of_tokens.get(token_ids.get(i)).char_begin;
					entity.words.put(token_ids.get(i),
							hash_of_tokens.get(token_ids.get(i)).word);

					if (i == token_ids.size() - 1) {
						entity.char_end = hash_of_tokens.get(token_ids.get(i)).char_end;
						entities.add(entity);
					}
					entity_inside = true;
				}
			} else {
				if (entity_inside == true) {// && i > 0) {
					if (hash_of_tokens.get(token_ids.get(i)).ner
							.equals(hash_of_tokens.get(token_ids.get(i - 1)).ner)) {
						entity.words.put(token_ids.get(i),
								hash_of_tokens.get(token_ids.get(i)).word);
						if (i == token_ids.size() - 1) {
							entity.char_end = hash_of_tokens.get(token_ids
									.get(i)).char_end;
							entities.add(entity);

						}
					} else {
						if (hash_of_tokens.get(token_ids.get(i)).ner
								.equals("O")) {
							entity.char_end = hash_of_tokens.get(token_ids
									.get(i - 1)).char_end;
							entities.add(entity);
							entity_inside = false;
						} else {
							if (hash_of_tokens.get(token_ids.get(i)).ner
											.equals("LOCATION")
									|| hash_of_tokens.get(token_ids.get(i)).ner
											.equals("ORGANIZATION")
									|| hash_of_tokens.get(token_ids.get(i)).ner
											.equals("PERSON")) {
								entity.char_end = hash_of_tokens.get(token_ids
										.get(i - 1)).char_end;
								entities.add(entity);
								entity = new Entity();
								entity.type = hash_of_tokens.get(token_ids
										.get(i)).ner;
								entity.char_begin = hash_of_tokens
										.get(token_ids.get(i)).char_begin;
								entity.words
										.put(token_ids.get(i), hash_of_tokens
												.get(token_ids.get(i)).word);
							}
						}
					}
				}
			}
		}

		
		for (int i = 0; i < entities.size(); i++){
			
			if (entities.get(i).type.equals("LOCATION")) {
				String entity_string = "";
				TreeMap<Integer, String> sortedWords = new TreeMap<Integer, String>(entities.get(i).words);				
				for (int key : sortedWords.keySet()) {
					if (entity_string.length() > 0) {
						entity_string = entity_string + " " + sortedWords.get(key);
					} else {
						entity_string = sortedWords.get(key);
					}
				}
				location.add(entity_string);
			}
			if (entities.get(i).type.equals("ORGANIZATION")) {
				String entity_string = "";
				TreeMap<Integer, String> sortedWords = new TreeMap<Integer, String>(entities.get(i).words);				
				for (int key : sortedWords.keySet()) {
					if (entity_string.length() > 0) {
						entity_string = entity_string + " " + sortedWords.get(key);
					} else {
						entity_string = sortedWords.get(key);
					}
				}
				organization.add(entity_string);
			}
			if (entities.get(i).type.equals("PERSON")) {
				String entity_string = "";
				TreeMap<Integer, String> sortedWords = new TreeMap<Integer, String>(entities.get(i).words);				
				for (int key : sortedWords.keySet()) {
					if (entity_string.length() > 0) {
						entity_string = entity_string + " " + sortedWords.get(key);
					} else {
						entity_string = sortedWords.get(key);
					}
				}
				person.add(entity_string);
			}
		}
		
		entity_map.put("person", person);
		entity_map.put("organization", organization);
		entity_map.put("location", location);
		
		
		return entity_map;
	}
}
