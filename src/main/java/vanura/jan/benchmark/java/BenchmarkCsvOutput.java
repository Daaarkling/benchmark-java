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
import java.io.File;
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

/**
 *
 * @author Jan
 */
public class BenchmarkCsvOutput extends Benchmark {

	public static String fileName = "java-output";
	public static String timeFormat = "yyyy-MM-dd-HH-mm-ss";
	private final String outputDir;
	
	
	public BenchmarkCsvOutput(Config config, String outputDir) {
		super(config);
		this.outputDir = outputDir;
	}

	
	@Override
	protected void handleResult(Map<String, List<MetricResult>> result) {
		
		List<String> headersEncode = new ArrayList<>();		
		List<String> headersDecode = new ArrayList<>();
		
		List<List<Long>> rowsEncode = new ArrayList<>();
		List<List<Long>> rowsDecode = new ArrayList<>();
		
		List<Long> rowSize = new ArrayList<>();
		
		int count = config.getMode() == Config.Mode.INNER ? config.getRepetitions() : 1;
		
		for (int i = 0; i < count; i++) {
			List<Long> rowEncode = new ArrayList<>();
			List<Long> rowDecode = new ArrayList<>();
			Iterator it = result.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				List<MetricResult> metricResults = (List<MetricResult>) pair.getValue();
				for (MetricResult metricResult : metricResults) {
					if (i == 0){
						if (metricResult.hasEncode()) {
							headersEncode.add(pair.getKey() + " - " + metricResult.getName());
							rowSize.add(Long.valueOf(metricResult.getSize()));
						}
						if (metricResult.hasDecode()) {
							headersDecode.add(pair.getKey() + " - " + metricResult.getName());
						}		
					}					
					int sizeEncode = metricResult.getTimeEncode().size();
					if (sizeEncode > 0 && i < sizeEncode){
						rowEncode.add(metricResult.getTimeEncode().get(i));
					}
					int sizeDecode = metricResult.getTimeDecode().size();
					if (sizeDecode > 0 && i < sizeDecode) {
						rowDecode.add(metricResult.getTimeDecode().get(i));
					}
				}
			}
			rowsEncode.add(rowEncode);
			rowsDecode.add(rowDecode);
		}
		rowsEncode.add(rowSize);
		
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
		
		String nameEncode = outputDir + File.separator + fileName + "-encode-" + dateTime.format(formatter) + ".csv";
		writeCsv(nameEncode, headersEncode, rowsEncode);
		
		String nameDecode = outputDir + File.separator + fileName + "-decode-" + dateTime.format(formatter) + ".csv";
		writeCsv(nameDecode, headersDecode, rowsDecode);
	}
	
	
	private void writeCsv(String name, List<String> headers, List<List<Long>> rows) {
		
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(name), ';');
			String[] entries = new String[headers.size()];
			headers.toArray(entries);
			writer.writeNext(entries);
			
			for (List<Long> row :rows) {
				entries = new String[row.size()];
				for (int i = 0; i < row.size(); i++) {
					entries[i] = String.valueOf(row.get(i));
				}
				writer.writeNext(entries);
			}
			writer.close();
		} catch (IOException ex) {
			Logger.getLogger(BenchmarkCsvOutput.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	
}
