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
package vanura.jan.benchmark.java.units;

import java.io.File;
import vanura.jan.benchmark.java.Config;

/**
 *
 * @author Jan
 */
public abstract class AUnitBenchmark implements IUnitBenchmark {
	
	
	protected Object testData;
	protected File testDataFile;


	@Override
	public UnitResult run(Object testData, File testDataFile, int repetitions, Config.Mode method) {
		
		this.testData = testData;
		this.testDataFile = testDataFile;

		if (method == Config.Mode.OUTER) {
			return this.runOuter(repetitions);
		}
		return runInner(repetitions);
	}

	
	private UnitResult runInner(int repetitions) {
		
		UnitResult result = new UnitResult();
		prepareBenchmark();
		Object testDataLocal = prepareTestDataForEncode();
		String output = null;
		Object encodedTestData = null;

		long start, time;
		for (int i = 1; i <= repetitions; i++) {
			start = System.nanoTime();
			output = encode(testDataLocal);
			time = System.nanoTime() - start;

			if (output == null) {
				break;
			}
			result.addTimeEncode(time);
		}

		// size of string is always same (at least it should be), there is no need to repeat the process
		if (output != null) {
			int size = output.length();
			result.setSize(size);
			encodedTestData = output;
		} else {
			encodedTestData = prepareTestDataForDecode();
			if (encodedTestData == null) {
				return result;
			}
		}

		boolean decodeImplemented;
		for (int i = 1; i <= repetitions; i++) {
			start = System.nanoTime();
			decodeImplemented = decode(encodedTestData);
			time = System.nanoTime() - start;

			if (!decodeImplemented) {
				break;
			}
			result.addTimeDecode(time);
		}

		return result;
	}

	
	
	private UnitResult runOuter(int repetitions) {
		
		UnitResult result = new UnitResult();
		prepareBenchmark();
		Object testDataLocal = prepareTestDataForEncode();
		String output = null;
		Object encodedTestData = null;
		
		long start = System.nanoTime();
		for (int i = 1; i <= repetitions; i++) {
			output = encode(testDataLocal);
		}
		long time = System.nanoTime() - start;

		if (output != null) {
			result.addTimeEncode(time);
			int size = output.length();
			result.setSize(size);
			encodedTestData = output;
		} else {
			encodedTestData = prepareTestDataForDecode();
			if (encodedTestData == null) {
				// decode is not implemented
				return result;
			}
		}

		boolean decodeImplemented = false;
		start = System.nanoTime();
		for (int i = 1; i <= repetitions; i++) {
			decodeImplemented = decode(encodedTestData);
		}
		time = System.nanoTime() - start;
		if (decodeImplemented) {
			result.addTimeDecode(time);
		}

		return result;
	}


	/**
	 * Its called once before encode()
	 */
	protected void prepareBenchmark()
	{

	}


	/**
	 * @return mixed
	 */
	protected Object prepareTestDataForEncode()
	{
		return this.testData;
	}


	/**
	 * If encode returns void this method must return string, otherwise decode() wont proceed
	 *
	 * @return mixed
	 */
	protected Object prepareTestDataForDecode()
	{
		return null;
	}


}
