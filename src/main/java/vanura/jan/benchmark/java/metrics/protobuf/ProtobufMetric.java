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
package vanura.jan.benchmark.java.metrics.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import vanura.jan.benchmark.java.converters.IDataConvertor;
import vanura.jan.benchmark.java.converters.ProtobufConvertor;
import vanura.jan.benchmark.java.metrics.AMetric;

/**
 *
 * @author Jan
 */
public class ProtobufMetric extends AMetric {

	@Override
	protected Object prepareTestDataForEncode() {
	
		IDataConvertor convertor = new ProtobufConvertor();
		return convertor.convertData(testDataFile);
	}

	
	
	
	@Override
	public boolean encode(Object data, OutputStream output) {
	
		try {
			PersonCollectionOuterClass.PersonCollection personCollection = (PersonCollectionOuterClass.PersonCollection) data;
			personCollection.writeTo(output);
			return true;
		} catch (IOException ex) {
			Logger.getLogger(ProtobufMetric.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	
	
	@Override
	public Object decode(InputStream input, byte[] bytes) {
		
		try {
			PersonCollectionOuterClass.PersonCollection personCollection = PersonCollectionOuterClass.PersonCollection.parseFrom(input);
			return personCollection;
		} catch (IOException ex) {
			Logger.getLogger(ProtobufMetric.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
