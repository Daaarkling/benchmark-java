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
import vanura.jan.benchmark.java.converters.PojoConvertor;
import vanura.jan.benchmark.java.metrics.IMetric;
import vanura.jan.benchmark.java.metrics.MetricResult;
import vanura.jan.benchmark.java.utils.ClassHelper;

/**
 *
 * @author Jan
 */
public abstract class Benchmark {
	
	protected Config config;

	
	
	public Benchmark(Config config) {
		
		this.config = config;
	}
	
	/**
	 * Run benchmark
	 */
	public void run() {
		
		Map<String, List<MetricResult>> result = new HashMap<>();
		Object data = prepareData();
		JsonNode configNode = config.getConfigNode();
		
		Iterator<Map.Entry<String, JsonNode>> formatsNode = configNode.fields();
		while (formatsNode.hasNext()) {
			Map.Entry<String, JsonNode> formatNode = (Map.Entry<String, JsonNode>) formatsNode.next();
			List<MetricResult> unitResults = new ArrayList<>();
			for (JsonNode lib : formatNode.getValue()) {
				
				String className = lib.path("class").asText();
				IMetric classUnit = (IMetric) ClassHelper.instantiateClass(className, IMetric.class);
				
				// run unit benchmark
				MetricResult metricResult = classUnit.run(data, config.getTestData(), config.getRepetitions(), config.getMode());
				
				if (metricResult == null) {
					continue;
				}
				
				String name = lib.path("name").asText() + " " + lib.path("version").asText();
				name = name.trim();
				metricResult.setName(name);
				
				unitResults.add(metricResult);
			}
			result.put(formatNode.getKey(), unitResults);
		}
		
		handleResult(result);
	}
	
	protected abstract void handleResult(Map<String, List<MetricResult>> result);
	
	
	protected Object prepareData() {
		File testData = config.getTestData();
		IDataConvertor convertor = new PojoConvertor();
		return convertor.convertData(testData);
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}
}
