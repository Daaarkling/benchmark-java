/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java;


import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import vanura.jan.benchmark.java.converters.IDataConvertor;
import vanura.jan.benchmark.java.units.IUnitBenchmark;
import vanura.jan.benchmark.java.units.UnitResult;
import vanura.jan.benchmark.java.utils.ClassHelper;

/**
 *
 * @author Jan
 */
public abstract class Benchmark {
	
	protected Config config;
	protected IDataConvertor convertor;

	
	
	public Benchmark(Config config, IDataConvertor convertor) {
		
		this.config = config;
		this.convertor = convertor;
	}
	
	/**
	 * Run benchmark
	 */
	public void run() {
		
		Map<String, List<UnitResult>> result = new HashMap<>();
		Object data = prepareData();
		JsonNode configNode = config.getConfigNode();
		
		Iterator<Map.Entry<String, JsonNode>> formatsNode = configNode.path("benchmark").fields();
		while (formatsNode.hasNext()) {
			Map.Entry<String, JsonNode> formatNode = (Map.Entry<String, JsonNode>) formatsNode.next();
			List<UnitResult> unitResults = new ArrayList<>();
			for (JsonNode lib : formatNode.getValue()) {
				
				String className = lib.path("class").asText();
				IUnitBenchmark classUnit = (IUnitBenchmark) ClassHelper.instantiateClass(className, IUnitBenchmark.class);
				
				// run unit benchmark
				UnitResult unitResult = classUnit.run(data, config.getTestData(), config.getRepetitions(), config.getMode());
				
				String name = lib.path("name").asText() + " " + lib.path("version").asText();
				name = name.trim();
				unitResult.setName(name);
				
				unitResults.add(unitResult);
			}
			result.put(formatNode.getKey(), unitResults);
		}
		
		handleResult(result);
	}
	
	protected abstract void handleResult(Map<String, List<UnitResult>> result);
	
	
	protected Object prepareData() {
		File testData = config.getTestData();
		return convertor.convertData(testData);
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public IDataConvertor getConvertor() {
		return convertor;
	}

	public void setConvertor(IDataConvertor convertor) {
		this.convertor = convertor;
	}
	
}
