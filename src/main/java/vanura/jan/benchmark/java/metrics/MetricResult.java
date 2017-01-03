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
package vanura.jan.benchmark.java.metrics;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan
 */
public class MetricResult {
	
	private String name;
	private int size;
	private List<Long> timeEncode = new ArrayList<>();
	private List<Long> timeDecode = new ArrayList<>();

	public boolean hasEncode() {
		
		return !timeEncode.isEmpty();
	}
	
	public boolean hasDecode() {

		return !timeDecode.isEmpty();
	}
	
	
	public Long getMeanEncode() {
		
		return computeMean(timeEncode);
	}
	
	
	public Long getMeanDecode() {
		
		return computeMean(timeDecode);
	}
	
	private Long computeMean(List<Long> list) {
		
		long sum = 0;
		for (Long time : list) {
			sum += time;
		}
		return sum / list.size();		
	}
	
	public void addTimeEncode(Long time) {
		timeEncode.add(time);
	}
	
	public void addTimeDecode(Long time) {
		timeDecode.add(time);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Long> getTimeEncode() {
		return timeEncode;
	}

	public List<Long> getTimeDecode() {
		return timeDecode;
	}

	public void setTimeEncode(List<Long> timeEncode) {
		this.timeEncode = timeEncode;
	}

	public void setTimeDecode(List<Long> timeDecode) {
		this.timeDecode = timeDecode;
	}
	
	
}
