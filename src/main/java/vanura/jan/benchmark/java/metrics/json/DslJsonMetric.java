/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.metrics.json;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import vanura.jan.benchmark.java.entities.PersonCollection;
import vanura.jan.benchmark.java.metrics.AMetric;

/**
 *
 * @author Jan
 */
public class DslJsonMetric extends AMetric {

	private JsonWriter writer;
	private DslJson<Object> dslJson;

	
	
	@Override
	protected void prepareBenchmark() {
	
		dslJson = new DslJson<>();
		writer = dslJson.newWriter();
	}

	
	
	
	@Override
	public boolean encode(Object data, OutputStream output) {
		
		try {
			dslJson.serialize(writer, data);
			writer.toStream(output);
			return true;
		} catch (IOException ex) {
			Logger.getLogger(DslJsonMetric.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	@Override
	public Object decode(InputStream input, byte[] bytes) {
		
		try {
			PersonCollection personCollection = dslJson.deserialize(PersonCollection.class, input, bytes);
			return personCollection;
		} catch (IOException ex) {
			Logger.getLogger(DslJsonMetric.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
