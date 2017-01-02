/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.units;

import java.io.File;
import vanura.jan.benchmark.java.Config;

/**
 *
 * @author Jan
 */
public interface IUnitBenchmark {
	
	/**
	 * Method for run the benchmark, should execute encode() and decode() methods and deliver result
	 * 
	 * @param testData
	 * @param testDataFile
	 * @param repetitions
	 * @param method 
	 */
	public UnitResult run(Object testData, File testDataFile, int repetitions, Config.Mode method);

	public String encode(Object data);


	public boolean decode(Object data);
}
