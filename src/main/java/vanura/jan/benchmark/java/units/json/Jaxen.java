/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.units.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vanura.jan.benchmark.java.entities.PersonCollection;
import vanura.jan.benchmark.java.units.AUnitBenchmark;
import vanura.jan.benchmark.java.units.IUnitBenchmark;

/**
 *
 * @author Jan
 */
public class Jaxen extends AUnitBenchmark implements IUnitBenchmark {
	
	private ObjectMapper mapper;

	@Override
	protected void prepareBenchmark() {
		mapper = new ObjectMapper();
	}

	
	
	
	@Override
	public String encode(Object data) {
		
		try {
			return mapper.writeValueAsString(data);
		} catch (JsonProcessingException ex) {
			Logger.getLogger(Jaxen.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	@Override
	public boolean decode(Object data) {
		
		try {
			mapper.readValue((String) data, PersonCollection.class);
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Jaxen.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}
}
