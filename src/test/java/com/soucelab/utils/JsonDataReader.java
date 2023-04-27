package com.soucelab.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonDataReader {
	
	
	 /**
	  *@method readJSONSpecifiedParentKey() which helps to fetch the key-value pairs
	  *@return object of TreeMap<String,String>
	  *@param Parentkey name
	 * @throws org.json.simple.parser.ParseException 
	  */
	 @SuppressWarnings("rawtypes")
	public static TreeMap<String,String> readJSONSpecifiedParentKey(String Parentkey, String jsonFilePath) throws IOException, org.json.simple.parser.ParseException {
	        TreeMap<String,String> map=new TreeMap<String,String>();
	        String obj2 = null;
	        Set locator=null;
	        JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(jsonFilePath));
	        Set keyset = obj.keySet();
	        Iterator itr = keyset.iterator();
	        while (itr.hasNext()) {
	             obj2 = (String) itr.next();
	            if(obj2.equalsIgnoreCase(Parentkey))
	            {
	                JSONObject insideObj= (JSONObject)obj.get(obj2);
	                locator=insideObj.keySet();
	                for(Object key:locator)
	                {
	                    map.put((String) key,(String)insideObj.get(key));
	                }
	            }
	        }
	        return map;
	    }

}
