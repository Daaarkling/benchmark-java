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

import com.opencsv.CSVWriter;
import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthLongestLine;
import de.vandermeer.asciitable.v2.render.WidthLongestWord;
import de.vandermeer.asciitable.v2.row.ContentRow;
import de.vandermeer.asciitable.v2.row.V2_Row;
import de.vandermeer.asciitable.v2.themes.V2_E_TableThemes;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import vanura.jan.benchmark.java.metrics.MetricResult;
import vanura.jan.benchmark.java.utils.Formatters;

/**
 *
 * @author Jan
 */
public class BenchmarkConsoleOutput extends Benchmark {

	public static String timeFormat = "yyyy-MM-dd-HH-mm-ss";
	
	
	public BenchmarkConsoleOutput(Config config) {
		super(config);
	}

	
	@Override
	protected void handleResult(Map<String, List<MetricResult>> result) {
		
		List<String> headersEncode = new ArrayList<>();		
		List<String> headersDecode = new ArrayList<>();
		
		List<List<String>> rowsEncode = new ArrayList<>();
		List<List<String>> rowsDecode = new ArrayList<>();
		
		List<String> rowEncodeMean = new ArrayList<>();
		List<String> rowDecodeMean = new ArrayList<>();
		List<String> rowSize = new ArrayList<>();
		
		int count = config.getMode() == Config.Mode.INNER ? config.getRepetitions() : 1;
		
		for (int i = 0; i < count; i++) {
			List<String> rowEncode = new ArrayList<>();
			List<String> rowDecode = new ArrayList<>();
			Iterator it = result.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				// it.remove(); // avoids a ConcurrentModificationException
				List<MetricResult> unitResults = (List<MetricResult>) pair.getValue();
				for (MetricResult unitResult : unitResults) {
					if (i == 0){
						
						// headers
						if (unitResult.hasEncode()) {
							headersEncode.add(pair.getKey() + " - " + unitResult.getName());
						}
						if (unitResult.hasDecode()) {
							headersDecode.add(pair.getKey() + " - " + unitResult.getName());
						}
						
						// means
						if(count > 1) {
							rowEncodeMean.add(Formatters.seconds(unitResult.getMeanEncode()));
							rowDecodeMean.add(Formatters.seconds(unitResult.getMeanDecode()));
						}
						
						// sizes
						rowSize.add(Formatters.bytes(unitResult.getSize()));
					}
					
					// times
					int sizeEncode = unitResult.getTimeEncode().size();
					if (sizeEncode > 0 && i < sizeEncode){
						rowEncode.add(Formatters.seconds(unitResult.getTimeEncode().get(i)));
					}
					int sizeDecode = unitResult.getTimeDecode().size();
					if (sizeDecode > 0 && i < sizeDecode) {
						rowDecode.add(Formatters.seconds(unitResult.getTimeDecode().get(i)));
					}
				}
			}
			rowsEncode.add(rowEncode);
			rowsDecode.add(rowDecode);
		}

		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
		
		String nameEncode = "Encode - " + dateTime.format(formatter);
		printTable(nameEncode, headersEncode, rowsEncode, rowEncodeMean, rowSize);
		
		String nameDecode = "Decode - " + dateTime.format(formatter);
		printTable(nameDecode, headersDecode, rowsDecode, rowDecodeMean, new ArrayList<>());
		
		System.out.println("Succes");
		
		
	}
	
	
	private void printTable(String name, List<String> headers, List<List<String>> times, List<String> means, List<String> sizes) {
		

		V2_AsciiTable table = new V2_AsciiTable();
		
		table.addRow(headers.toArray());
		table.addStrongRule();

		Iterator<List<String>> timesIterator = times.iterator();
		while(timesIterator.hasNext()) {
			table.addRow(timesIterator.next().toArray());
			if(timesIterator.hasNext()){
				table.addRule();
			}
		}
		
		if (!means.isEmpty()) {
			table.addStrongRule();
			table.addRow(means.toArray());
		}
		
		if(!sizes.isEmpty()) {
			table.addStrongRule();
			table.addRow(sizes.toArray());
		}
		
		V2_AsciiTableRenderer renderer = new V2_AsciiTableRenderer();
		renderer.setTheme(V2_E_TableThemes.PLAIN_7BIT_STRONG.get());
//		renderer.setTheme(V2_E_TableThemes.UTF_DOUBLE.get());
		renderer.setWidth(new WidthLongestLine());

		RenderedTable renderedTable = renderer.render(table);
		
		System.out.println("");
		System.out.println("");
		System.out.println(name);
		System.out.println("");
		System.out.println(renderedTable);	
		System.out.println("");
	}
	
	
}
