package br.com.softctrl.regex.criterya.impl;

import java.util.ArrayList;
import java.util.List;

import br.com.softctrl.regex.criterya.IQueryProcessor;
import br.com.softctrl.regex.criterya.manager.Processor;

/*
The MIT License (MIT)

Copyright (c) 2015 Carlos Timoshenko Rodrigues Lopes
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

    private final static List<IQueryProcessor> PARSERS = new ArrayList<IQueryProcessor>();

    /**
     * 
     * @return
     */
    public static final List<IQueryProcessor> getParsers() {
        return PARSERS;
    }

    /**
     * 
     */
    public static final synchronized void setup(final Processor[] processors) {
        PARSERS.clear();
        for (Processor processor : processors) {
            try {
                PARSERS.add(processor.createQueryProcessor());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @param userQuery
     * @return
     */
    public static final String parse(final String userQuery) {
        String query = userQuery;
        for (IQueryProcessor iQueryProcessor : PARSERS) {
            if (iQueryProcessor.matches(userQuery)) {
                query = iQueryProcessor.parseQuery(userQuery);
                break;
            }
        }
        return query;
    }

}
