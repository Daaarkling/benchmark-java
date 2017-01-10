/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.metrics.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.OutputStream;
import vanura.jan.benchmark.java.entities.PersonCollection;
import vanura.jan.benchmark.java.metrics.AMetric;

/**
 *
 * @author Jan
 */
public class JacksonMetric extends AMetric {
	
	private ObjectMapper mapper;

	@Override
	protected void prepareBenchmark() {
		mapper = new ObjectMapper();
	}

	
	
	@Override
	public boolean encode(Object data, OutputStream output) throws Exception {

		mapper.writeValue(output, data);
		return true;
	}

	@Override
	public Object decode(InputStream input, byte[] bytes) throws Exception {
		
		PersonCollection personCollection = mapper.readValue(input, PersonCollection.class);
		return personCollection;
	}
}
