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
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, testData, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package vanura.jan.benchmark.java.metrics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import vanura.jan.benchmark.java.Config;

/**
 *
 * @author Jan
 */
public abstract class AMetric implements IMetric {
	
	
	protected Object testData;
	protected File testDataFile;


	@Override
	public MetricResult run(Object testData, File testDataFile, int repetitions, Config.Mode method) {
		
		try {
			this.testData = testData;
			this.testDataFile = testDataFile;
			
			if (method == Config.Mode.OUTER) {
				return this.runOuter(repetitions);
			}
			return runInner(repetitions);
		} catch (IOException ex) {
			Logger.getLogger(AMetric.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	
	private MetricResult runInner(int repetitions) throws IOException {
		
		prepareBenchmark();
		
		MetricResult result = new MetricResult();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Object dataForEncode = prepareTestDataForEncode();
		byte[] dataForDecode;
		boolean encodeImplemented = false;
		Object decodeImplemented = null;
		long start, time;
		
		try {
			// ENCODE
			// Do it once to warm up.
			encode(dataForEncode, output);

			for (int i = 1; i <= repetitions; i++) {
				output.reset();
				start = System.nanoTime();
				encodeImplemented = encode(dataForEncode, output);
				time = System.nanoTime() - start;

				if (!encodeImplemented) {
					break;
				}
				result.addTimeEncode(time);
			}
		} catch (Exception ex) {
			Logger.getLogger(AMetric.class.getName()).log(Level.SEVERE, null, ex);
			encodeImplemented = false;
		}

		// Size of string is always same (at least it should be), there is no need to repeat the process
		if (encodeImplemented && output.size() > 0) {
			result.setSize(output.size());
			dataForDecode = output.toByteArray();
		} else {
			dataForDecode = prepareTestDataForDecode();
			if (dataForDecode == null) {
				return result;
			}
		}
		//output.close();
		
		try {
			// DECODE
			// Do it once to warm up.
			decode(new ByteArrayInputStream(dataForDecode), dataForDecode);

			for (int i = 1; i <= repetitions; i++) {
				start = System.nanoTime();
				decodeImplemented = decode(new ByteArrayInputStream(dataForDecode), dataForDecode);
				time = System.nanoTime() - start;

				if (decodeImplemented == null) {
					break;
				}
				result.addTimeDecode(time);
			}
		}
		catch (Exception ex) {
			Logger.getLogger(AMetric.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return result;
	}

	
	
	private MetricResult runOuter(int repetitions) throws IOException {
		
		prepareBenchmark();
		
		MetricResult result = new MetricResult();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		Object dataForEncode = prepareTestDataForEncode();
		byte[] dataForDecode;
		boolean encodeImplemented = false;
		Object decodeImplemented = null;
		long start, time;
		
		try {
			// ENCODE
			// Do it once to warm up.
			encode(dataForEncode, output);
			output.reset();

			start = System.nanoTime();
			for (int i = 1; i <= repetitions; i++) {
				encodeImplemented = encode(dataForEncode, output);
			}
			time = System.nanoTime() - start;

			if (encodeImplemented && output.size() > 0) {
				result.addTimeEncode(time);
				result.setSize(output.size());

				// Decode just one data not multiple data stacked on each other
				output.reset();
				encode(dataForEncode, output);
				dataForDecode = output.toByteArray();
			} else {
				dataForDecode = prepareTestDataForDecode();
				if (dataForDecode == null) {
					return result;
				}
			}
			output.close();
			
		} catch (Exception ex) {
			Logger.getLogger(AMetric.class.getName()).log(Level.SEVERE, null, ex);
			
			dataForDecode = prepareTestDataForDecode();
			if (dataForDecode == null) {
				return result;
			}
		}


		try {
			// DECODE
			// Do it once to warm up.
			decode(new ByteArrayInputStream(dataForDecode), dataForDecode);

			start = System.nanoTime();
			for (int i = 1; i <= repetitions; i++) {
				decodeImplemented = decode(new ByteArrayInputStream(dataForDecode), dataForDecode);
			}
			time = System.nanoTime() - start;

			if (decodeImplemented != null) {
				result.addTimeDecode(time);
			}
		} catch (Exception ex) {
			Logger.getLogger(AMetric.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return result;
	}


	/**
	 * Its called once before encode()
	 */
	protected void prepareBenchmark(){
	}

	@Override
	public boolean encode(Object data, OutputStream output) throws Exception {
		return false;
	}

	@Override
	public Object decode(InputStream input, byte[] bytes) throws Exception {
		return null;
	}
	
	
	protected Object prepareTestDataForEncode() {
		return this.testData;
	}


	protected byte[] prepareTestDataForDecode() {
		return null;
	}


}
