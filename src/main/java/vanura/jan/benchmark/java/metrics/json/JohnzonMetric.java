/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.metrics.json;


import java.io.InputStream;
import java.io.OutputStream;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;
import vanura.jan.benchmark.java.entities.PersonCollection;
import vanura.jan.benchmark.java.metrics.AMetric;

/**
 *
 * @author Jan
 */
public class JohnzonMetric extends AMetric {
	private Mapper mapper;

	
	

	@Override
	protected void prepareBenchmark() {
	
		mapper = new MapperBuilder().build();
	}
	

	
	
	@Override
	public boolean encode(Object data, OutputStream output) {
		
		mapper.writeObject(data, output);
		return true;
	}

	@Override
	public Object decode(InputStream input, byte[] bytes) {
		
		PersonCollection personCollection = mapper.readObject(input, PersonCollection.class);
		return personCollection;
	}
}
