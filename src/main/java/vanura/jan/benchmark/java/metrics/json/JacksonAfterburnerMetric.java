/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.metrics.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
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
public class JacksonAfterburnerMetric extends AMetric {
	
	private ObjectMapper mapper;

	@Override
	protected void prepareBenchmark() {
		mapper = new ObjectMapper();
		mapper.registerModule(new AfterburnerModule());
	}

	
	
	
	@Override
	public boolean encode(Object data, OutputStream output) {
		
		try {
			mapper.writeValue(output, data);
			return true;
		} catch (IOException ex) {
			Logger.getLogger(JacksonAfterburnerMetric.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	@Override
	public Object decode(InputStream input, byte[] bytes) {
		
		try {
			return mapper.readValue(input, PersonCollection.class);
		} catch (IOException ex) {
			Logger.getLogger(JacksonAfterburnerMetric.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
