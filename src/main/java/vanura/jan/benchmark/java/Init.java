/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.io.IOException;
import vanura.jan.benchmark.java.converters.EntityConvertor;
import vanura.jan.benchmark.java.utils.ConfigValidator;
import vanura.jan.benchmark.java.utils.JsonLoader;

/**
 *
 * @author Jan
 */
public class Init {

	public Init() throws IOException {
		
		JsonNode configNode = JsonLoader.loadResource(Config.configPath);
		File testDataFile = new File(Config.testDataPath);
		
		Config config = new Config(configNode, testDataFile);
		
		
		ConfigValidator configValidator = new ConfigValidator(config);
		configValidator.validate();
		
		for (String message : configValidator.getErrors()) {
			System.out.println(message);
		}
		if(!configValidator.isValid()){
			System.out.println("NOPE");
			System.exit(1);
		}
		
		// Validation is OK
		
//		Benchmark benchmark = new DumpBenchmark(config, new EntityConvertor());
		Benchmark benchmark = new BenchmarkCsvOutput(config, new EntityConvertor());
		benchmark.run();
	}
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		
		new Init();
	}
}
