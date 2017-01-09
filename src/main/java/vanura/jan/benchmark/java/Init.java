/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vanura.jan.benchmark.java;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import vanura.jan.benchmark.java.utils.ConfigValidator;
import vanura.jan.benchmark.java.utils.JsonLoader;

/**
 *
 * @author Jan
 */
public class Init {
	
	private static final String OUTPUT_CONSOLE = "console";
	private static final String OUTPUT_CSV = "csv";
	private static final String OUTPUT_FILE = "file";
	private static final String OUTPUT_DUMP = "dump";
	
	private static final String MODE_OUTER = "outer";
	private static final String MODE_INNER = "inner";

	private String[] modes = {MODE_OUTER, MODE_INNER};
	private String[] outputs = {OUTPUT_CONSOLE, OUTPUT_CSV, OUTPUT_FILE, OUTPUT_DUMP};
	
	
	

	public Init(String[] args) {
		
		try {
			Options options = setOptions();
			
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(options, args);
			
			Config.Mode mode = Config.Mode.OUTER;
			if (cmd.hasOption("m")) {
				String modeGiven = cmd.getOptionValue("m");
				if (Arrays.asList(modes).contains(modeGiven)) {
					mode = modeGiven.equals(MODE_OUTER) ? Config.Mode.OUTER : Config.Mode.INNER;
				} else {
					System.err.println("Mode must be one of these options: " + String.join(", ", modes));
					System.exit(1);
				}
			}

			String output = OUTPUT_CONSOLE;
			if (cmd.hasOption("o")) {
				String outputGiven = cmd.getOptionValue("o");
				if (Arrays.asList(outputs).contains(outputGiven)) {
					output = outputGiven;
				} else {
					System.err.println("Output must be one of these options: " + String.join(", ", outputs));
					System.exit(1);
				}
			}
			
			String outputDir = ".";
			if ((output.equals(OUTPUT_CSV) || output.equals(OUTPUT_FILE)) && cmd.hasOption("od")) {
				outputDir = cmd.getOptionValue("od");
				File outputDirFile = new File(outputDir);
				if (!outputDirFile.isDirectory() || !outputDirFile.canWrite()) {
					System.err.println("Output path is not directory or is not writable.");
					System.exit(1);
				}
			}

			int repetitions = 10;
			if (cmd.hasOption("r")) {
				String repGiven = cmd.getOptionValue("r");
				try {
					repetitions = Integer.parseInt(repGiven);
					if (repetitions < 1) {
						System.err.println("Repetitions must be whole number greater than zero.");
						System.exit(1);
					}
				} catch (NumberFormatException ex) {
					System.err.println("Repetitions must be whole number greater than zero.");
					System.exit(1);
				}
			}

			File testDataFile = new File(Config.testDataPath);
			if (cmd.hasOption("d")) {
				String dataGiven = cmd.getOptionValue("d");
				testDataFile = new File(dataGiven);
			}

			JsonNode configNode = null;
			try {
				if (cmd.hasOption("c")) {
					String configGiven = cmd.getOptionValue("c");
					configNode = JsonLoader.loadResource(configGiven);
				} else {
					configNode = JsonLoader.loadResource(Config.configPath);
				}
			} catch (IOException ex) {
				System.err.println("Config file not found.");
				System.exit(1);
			}

			
			Config config = new Config(configNode, testDataFile, repetitions, mode);

			// Validation
			ConfigValidator configValidator = new ConfigValidator(config);
			configValidator.validate();

			for (String message : configValidator.getErrors()) {
				System.out.println(message);
			}
			if (!configValidator.isValid()) {
				System.out.println("Errors!");
				System.exit(1);
			}

			// Validation is OK
			System.out.println("Validation succeeded!");
			
			Benchmark benchmark;
			switch (output) {
				case OUTPUT_DUMP:
					benchmark = new BenchmarkDumpOutput(config);
					break;
				case OUTPUT_CONSOLE:
					benchmark = new BenchmarkConsoleOutput(config);
					break;
				case OUTPUT_FILE:
					benchmark = new BenchmarkFileOutput(config, outputDir);
					break;
				default:
					benchmark = new BenchmarkCsvOutput(config, outputDir);
					break;
			}
			benchmark.run();

			System.out.println("Benchmark processed successfully!");
			
		} catch (ParseException exp) {
			// oops, something went wrong
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
			System.exit(1);
		}
	}
	

	
	private Options setOptions() {
		
		Options options = new Options();
		
		Option modeOption = Option.builder("m")
				.hasArg()
				.argName("mode")
				.desc("You can choose from two options: " + String.join(", ", modes))
				.build();
		options.addOption(modeOption);
		
		Option outputOption = Option.builder("o")
				.hasArg()
				.argName("output")
				.desc("You can choose from several choices: " + String.join(", ", outputs))
				.build();
		options.addOption(outputOption);
		
		Option repOption = Option.builder("r")
				.hasArg()
				.argName("repetitions")
				.desc("Number of repetitions.")
				.build();
		options.addOption(repOption);
		
		Option dataOption = Option.builder("d")
				.hasArg()
				.argName("data")
				.desc("Test data.")
				.build();
		options.addOption(dataOption);
		
		Option configOption = Option.builder("c")
				.hasArg()
				.argName("config")
				.desc("Config file.")
				.build();
		options.addOption(configOption);
		
		Option outputDirOption = Option.builder("od")
				.hasArg()
				.argName("out_dir")
				.desc("Output directory.")
				.build();
		options.addOption(outputDirOption);
		
		
		return options;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		
		Init init = new Init(args);
	}
}
