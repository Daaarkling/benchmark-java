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
package vanura.jan.benchmark.java.metrics.msgpack;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.OutputStream;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import vanura.jan.benchmark.java.entities.PersonCollection;
import vanura.jan.benchmark.java.metrics.AMetric;

/**
 *
 * @author Jan
 */
public class MsgPackMetric extends AMetric {
	
	private ObjectMapper objectMapper;

	
	
	@Override
	protected void prepareBenchmark() {
		objectMapper = new ObjectMapper(new MessagePackFactory());
	}

		
	@Override
	public boolean encode(Object data, OutputStream output) throws Exception {

		objectMapper.writeValue(output, data);
		return true;
	}
	
	
	@Override
	public Object decode(InputStream input, byte[] bytes) throws Exception {
		
		return objectMapper.readValue(input, PersonCollection.class);
	}

	
	
	
}
