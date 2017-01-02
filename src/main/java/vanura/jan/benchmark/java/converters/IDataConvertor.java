/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.converters;

import java.io.File;

/**
 *
 * @author Jan
 */
public interface IDataConvertor {
	
	/**
	 * Convert given test data into desirable format such as json, xml, pojo,...
	 * 
	 * @param testDataFile
	 * @return data in desirable format json, xml, pojo,...
	 */
	public Object convertData(File testDataFile);
}
