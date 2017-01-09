/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.metrics.json;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import java.io.InputStream;
import java.io.OutputStream;
import vanura.jan.benchmark.java.entities.PersonCollection;
import vanura.jan.benchmark.java.metrics.AMetric;

/**
 *
 * @author Jan
 */
public class JsonIOMetric extends AMetric {
	

	
	
	@Override
	public boolean encode(Object data, OutputStream output) {
		
		JsonWriter jsonWriter = new JsonWriter(output);
		jsonWriter.write(data);
		return true;
	}

	@Override
	public Object decode(InputStream input, byte[] bytes) {
		
		PersonCollection personCollection = (PersonCollection) JsonReader.jsonToJava(input, null);
		return personCollection;
	}
}
