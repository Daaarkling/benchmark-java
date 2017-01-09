/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.metrics.json;


import com.bluelinelabs.logansquare.LoganSquare;
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
public class LoganSquareMetric extends AMetric {
	
	
	@Override
	public boolean encode(Object data, OutputStream output) {
		
		try {
			LoganSquare.serialize(data, output);
			return true;
		} catch (IOException ex) {
			Logger.getLogger(LoganSquareMetric.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	@Override
	public Object decode(InputStream input, byte[] bytes) {
		
		try {
			PersonCollection personCollection = LoganSquare.parse(input, PersonCollection.class);
			return personCollection;
		} catch (IOException ex) {
			Logger.getLogger(LoganSquareMetric.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
