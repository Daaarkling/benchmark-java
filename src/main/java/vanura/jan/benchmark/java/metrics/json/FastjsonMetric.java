/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.metrics.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.InputStream;
import java.io.OutputStream;
import vanura.jan.benchmark.java.entities.PersonCollection;
import vanura.jan.benchmark.java.metrics.AMetric;

/**
 *
 * @author Jan
 */
public class FastjsonMetric extends AMetric {
	

	
	
	@Override
	public boolean encode(Object data, OutputStream output) throws Exception {

		JSON.writeJSONString(output, data, SerializerFeature.EMPTY);
		return true;
	}

	@Override
	public Object decode(InputStream input, byte[] bytes) throws Exception {
		
		PersonCollection personCollection = JSON.parseObject(input, PersonCollection.class);
		return personCollection;
	}
}
