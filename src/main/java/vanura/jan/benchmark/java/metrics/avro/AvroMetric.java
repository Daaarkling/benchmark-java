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
package vanura.jan.benchmark.java.metrics.avro;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.avro.file.DataFileStream;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import vanura.jan.benchmark.java.converters.AvroConvertor;
import vanura.jan.benchmark.java.converters.IDataConvertor;
import vanura.jan.benchmark.java.metrics.AMetric;

/**
 *
 * @author Jan
 */
public class AvroMetric extends AMetric {
	
	private DataFileWriter<PersonCollection> dataFileWriter;
	private DatumReader<PersonCollection> datumReader;

	
	
	@Override
	protected void prepareBenchmark() {
		
		DatumWriter<PersonCollection> userDatumWriter = new SpecificDatumWriter<>(PersonCollection.class);
		dataFileWriter = new DataFileWriter<>(userDatumWriter);
		
		datumReader = new SpecificDatumReader<>(PersonCollection.class);
	}

	@Override
	protected Object prepareTestDataForEncode() {
		
		IDataConvertor convertor = new AvroConvertor();
		return convertor.convertData(testDataFile);
	}
	
	
	
	
	
	@Override
	public boolean encode(Object data, OutputStream output) {
		
		try {
			PersonCollection persons = (PersonCollection) data;
			dataFileWriter.create(persons.getSchema(), output);
			dataFileWriter.append(persons);
			dataFileWriter.close();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(AvroMetric.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	
	@Override
	public Object decode(InputStream input, byte[] bytes) {

		try {
			DataFileStream<PersonCollection> dataReader = new DataFileStream<>(input, datumReader);
			return dataReader.next();
		} catch (IOException ex) {
			Logger.getLogger(AvroMetric.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	
}
