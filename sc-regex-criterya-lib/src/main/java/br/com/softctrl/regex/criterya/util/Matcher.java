package br.com.softctrl.regex.criterya.util;

import br.com.softctrl.regex.criterya.IQueryProcessor;
import br.com.softctrl.utils.Objects;

/*
The MIT License (MIT)

Copyright (c) 2016 Carlos Timoshenko Rodrigues Lopes
http://www.0x09.com.br

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

/**
 * 
 * @author carlostimoshenkorodrigueslopes@gmail.com
 */
public class Matcher {

	/**
	 * 
	 * @author carlostimoshenkorodrigueslopes@gmail.com
	 *
	 */
	public interface IMatcher { }
	
	/**
	 * 
	 * @author carlostimoshenkorodrigueslopes@gmail.com
	 *
	 */
	public static class Pattern implements IMatcher {

		private IQueryProcessor processor = null;
		public Pattern(IQueryProcessor processor) { this.processor = Objects.requireNonNull(processor); }
		public IQueryProcessor getProcessor() { return processor; }

	}

	/**
	 * @author carlostimoshenkorodrigueslopes@gmail.com
	 */
	public static class Query implements IMatcher {

		private String value;
		public Query(String value) { this.value = value; }
		@Override public boolean equals(Object pattern) {
			return ((Pattern)pattern).processor.matches(value);
		}

	}

}
