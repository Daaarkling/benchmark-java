package vanura.jan.benchmark.java.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vanura.jan.benchmark.java.entities.PersonCollection;

/**
 *
 * @author Jan
 */
public class EntityConvertor implements IDataConvertor {
	
	/**
	 * 
	 * @param testDataFile
	 * @return PersonCollection POJO object
	 */
	@Override
	public Object convertData(File testDataFile) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			PersonCollection collection = mapper.readValue(testDataFile, PersonCollection.class);
			return collection;
		} catch (IOException ex) {
			Logger.getLogger(EntityConvertor.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
