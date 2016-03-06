package il.org.yadvashem.analyze_batch_collections_for_workshop;

import java.util.HashMap;

public class Entity {
	String type;
	int token_begin;
	int token_end;
	int char_begin;
	int char_end;
	HashMap<Integer, String> words = new HashMap<Integer,String>();
}

