/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.metrics;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import vanura.jan.benchmark.java.Config;

/**
 *
 * @author Jan
 */
public interface IMetric {
	
	/**
	 * Method for run the benchmark, should execute encode() and decode() methods and deliver result
	 * 
	 * @param testData
	 * @param testDataFile
	 * @param repetitions
	 * @param method 
	 * @return object that holds results
	 */
	public MetricResult run(Object testData, File testDataFile, int repetitions, Config.Mode method);

	public boolean encode(Object data, OutputStream output);

	public Object decode(InputStream input);
}
