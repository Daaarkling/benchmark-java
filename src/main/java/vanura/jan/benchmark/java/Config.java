/*
 * Copyright (c) 2017, Jan
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package vanura.jan.benchmark.java;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;

/**
 *
 * @author Jan
 */
public class Config {
	
	public enum Mode {
		OUTER, INNER
	};
	
	public static String configPath = "config/config.json";
	public static String testDataPath = "config/testdata.json";
	
	private JsonNode configNode;
	private File testData;
	private int repetitions = 10;
	private Mode mode = Mode.INNER;

	public Config(JsonNode configFile, File testDataPath, int repetitions, Mode mode) {
		this.configNode = configFile;
		this.testData = testDataPath;
		this.repetitions = repetitions;
		this.mode = mode;
	}

	public Config(JsonNode configFile, File testDataPath, int repetitions) {
		this.configNode = configFile;
		this.testData = testDataPath;
		this.repetitions = repetitions;
	}

	public Config(JsonNode configFile, File testDataPath) {
		this.configNode = configFile;
		this.testData = testDataPath;
	}

	public JsonNode getConfigNode() {
		return configNode;
	}

	public void setConfigNode(JsonNode configNode) {
		this.configNode = configNode;
	}

	public File getTestData() {
		return testData;
	}

	public void setTestData(File testData) {
		if (testData.isFile()) {
			this.testData = testData;
		} else {
			this.testData = null;
		}	
	}

	public int getRepetitions() {
		return repetitions;
	}

	public void setRepetitions(int repetitions) {
		if (repetitions >= 1) {
			this.repetitions = repetitions;
		} else {
			this.repetitions = 10;
		}
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	
	
	
	
	
}
