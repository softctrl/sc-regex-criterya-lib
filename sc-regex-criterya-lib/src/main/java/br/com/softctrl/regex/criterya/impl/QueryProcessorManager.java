package br.com.softctrl.regex.criterya.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.softctrl.regex.criterya.IQueryProcessor;
import br.com.softctrl.regex.criterya.manager.Processor;
import br.com.softctrl.regex.criterya.util.Matcher;
import br.com.softctrl.regex.criterya.util.Matcher.Pattern;
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
public class QueryProcessorManager {
	
	/**
	 * A pool of Query processors. 
	 */
    private final static List<Pattern> PARSERS = new ArrayList<Pattern>();

    /**
     * The length (count) of the parsers available.
     */
    private static int LENGTH = 0;
    

    /**
     * 
     * @return
     */
    public static final List<Pattern> getParsers() {
        return PARSERS;
    }

    /**
     * Update the processors available for you project.
     *  
     * @param processors
     */
    public static final synchronized void setup(final Processor[] processors) {
        PARSERS.clear();
        for (Processor processor : processors) {
            try {
            	if (!processor.isDeprecated()) {
            		PARSERS.add(new Pattern(processor.createQueryProcessor()));
            	}
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        LENGTH = PARSERS.size();
    }

    /**
     * Perform a parser in a string value informed.
     * 
     * @param userQuery
     * @return The string value parsed.
     */
    public static final String parse(final String userQuery) {
    	return parseOrDefault(userQuery, userQuery);
    }

    /**
     * Perform a parser in a string value informed or return a default value.
     * 
     * @param userQuery
     * @param def
     * @return
     */
    public static final String parseOrDefault(final String userQuery, final String def) {

		int idx = PARSERS.indexOf(new Matcher.Query(userQuery));
		IQueryProcessor processor = (Objects.inRange(idx, 0, LENGTH) ? PARSERS.get(idx).getProcessor() : null);
		return (Objects.isNull(processor) ? def : processor.parseQuery(userQuery));

    }

}
