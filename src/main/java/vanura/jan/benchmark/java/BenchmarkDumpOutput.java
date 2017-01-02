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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import vanura.jan.benchmark.java.converters.IDataConvertor;
import vanura.jan.benchmark.java.units.UnitResult;
import vanura.jan.benchmark.java.utils.Formatters;

/**
 *
 * @author Jan
 */
public class BenchmarkDumpOutput extends Benchmark {

	public BenchmarkDumpOutput(Config config, IDataConvertor convertor) {
		super(config, convertor);
	}

	
	@Override
	protected void handleResult(Map<String, List<UnitResult>> result) {
		
		Iterator it = result.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey() + ":");
			it.remove(); // avoids a ConcurrentModificationException
			
			List<UnitResult> unitResults = (List<UnitResult>) pair.getValue();
			for (UnitResult unitResult : unitResults) {
				System.out.println(unitResult.getName() + ":");
				System.out.println("Encode time:");
				for (Long time : unitResult.getTimeEncode()){
					System.out.println(Formatters.seconds(time));
				}
				System.out.println("Size: " + Formatters.bytes(unitResult.getSize()));
				System.out.println("Decode time:");
				for (Long time : unitResult.getTimeDecode()){
					System.out.println(Formatters.seconds(time));
				}
			}
		}
	}
	
	
}
