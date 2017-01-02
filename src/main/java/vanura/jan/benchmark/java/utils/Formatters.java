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
package vanura.jan.benchmark.java.utils;

/**
 *
 * @author Jan
 */
public class Formatters {
	
	/**
	 * @link https://latte.nette.org/cs/filters#toc-bytes
	 *
	 * Converts to human readable file size and add units
	 * @param  bytes
	 * @param  precision
	 * @return String
	 */
	public static String bytes(float bytes, int precision) {

		String unitGo = "";
		String[] units = {"B", "kB", "MB", "GB", "TB", "PB"};
		for (String unit : units) {
			if (Math.abs(bytes) < 1024 || unit.equals(units[units.length - 1])) {
				unitGo = unit;
				break;
			}
			bytes = (float) bytes / 1024;
		}
		double prec = Math.pow(10, precision);
		return Math.round(bytes * prec) / prec + " " + unitGo;
	}
	
	public static String bytes(int bytes) {
		
		return bytes(bytes, 2);
	}
	
	
	/**
	 * Converts seconds to human readable format and add units
	 *
	 * @param  nanoseconds
	 * @param  precision
	 * @return string
	 */
	public static String seconds(Long nanoseconds, int precision) {
		
		float time = nanoseconds;
		String unitGo = "";
		String[] units = {"ns", "μs", "ms", "s"};
		for (String unit : units) {
			if (Math.abs(time) < 1000 || unit.equals(units[units.length - 1])) {
				unitGo = unit;
				break;
			}
			time = (float) time / 1000;
		}
		double prec = Math.pow(10, precision);
		return Math.round(time * prec) / prec + " " + unitGo;
	}

	public static String seconds(Long nanoseconds) {
		
		return seconds(nanoseconds, 5);
	}
}
