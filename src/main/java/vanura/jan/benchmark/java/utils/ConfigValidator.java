/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java.utils;

import vanura.jan.benchmark.java.Config;
import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import vanura.jan.benchmark.java.metrics.IMetric;


/**
 *
 * @author Jan
 */
public class ConfigValidator {

	private JsonSchema validator;
	private Config config;
	private List<String> errors = new ArrayList<>();
	
	public ConfigValidator(Config config) {
		try {
			this.config = config;
			errors.clear();
			
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream schemaStream = classloader.getResourceAsStream("schema.json");

			JsonNode schema = JsonLoader.loadResource(schemaStream);
			JsonSchemaFactory factory = new JsonSchemaFactory();
			validator = factory.getSchema(schema);
		} catch (IOException ex) {
			errors.add("Schema file not found.");
		}

	}
	
	
	public void validate() {
		
		// schema is loaded we can proceed
		if(isValid()) {
			validateConfig();
		}
		
		// there is no point to continue if config is not valid against the schema
		if(isValid()) {
			validateClasses();
			validateTestData();
		}
	}

	private void validateConfig() {
		
		Set<ValidationMessage> messages = validator.validate(config.getConfigNode());
		for (ValidationMessage message : messages) {
			errors.add(message.getMessage());
		}
	}

	private void validateClasses() {
		
		JsonNode configNode = config.getConfigNode();
		for (JsonNode format : configNode){
			for (JsonNode lib : format) {
				String className = lib.path("class").asText();
				if (ClassHelper.instantiateClass(className, IMetric.class) == null ){
					errors.add("Class " + className + " not found or is not instantiable.");
				}
			}
		}
		
	}

	private void validateTestData() {
		
		File testData = config.getTestData();
		if(testData == null || !testData.isFile()) {
			errors.add("Test data not found.");
		}
	}
	
	
	

	

	public boolean isValid() {
		return errors.isEmpty();
	}
	
	public List<String> getErrors() {
		return errors;
	}

	public void setConfig(Config config) {
		this.config = config;
		errors.clear();
	}
	
	
}
