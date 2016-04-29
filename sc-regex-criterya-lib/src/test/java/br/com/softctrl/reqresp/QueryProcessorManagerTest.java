package br.com.softctrl.reqresp;

import static br.com.softctrl.utils.json.GsonUtils.fromJson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.softctrl.regex.criterya.impl.QueryProcessorManager;
import br.com.softctrl.regex.criterya.manager.Processor;
import br.com.softctrl.utils.io.FileUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
 * Unit test for QueryProcessorManager.
 * 
 * @author carlostimoshenkorodrigueslopes@gmail.com
 */
public class QueryProcessorManagerTest extends TestCase {

    private static final List<String> REQUEST = new ArrayList<String>();
    private static final List<String> RESPONSE = new ArrayList<String>();

    /**
     * 
     * @return
     */
    private static final List<String> getTestValues(File file) {
        BufferedReader fileTests;
        List<String> values = null;
        try {
            fileTests = new BufferedReader(new FileReader(file));
            values = new ArrayList<String>();
            if (fileTests.ready()) {
                String row;
                while ((row = fileTests.readLine()) != null) {
                    values.add(row);
                }
                fileTests.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public QueryProcessorManagerTest(String testName) {
        super(testName);

        Processor[] processors = fromJson(FileUtils.getFileInClasspath("rules.json"), Processor[].class);
        QueryProcessorManager.setup(processors);

        REQUEST.clear();
        REQUEST.addAll(getTestValues(FileUtils.getFileInClasspath("rules_request.txt")));

        RESPONSE.clear();
        RESPONSE.addAll(getTestValues(FileUtils.getFileInClasspath("rules_response.txt")));
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(QueryProcessorManagerTest.class);
    }

    /**
     * A reall rigourous Test =D
     */
    public void testQueryProcessorManager() {

        int count = 0;
        String response = null;
        for (int idx = 0; idx < REQUEST.size(); idx++) {
            System.out.println("====================================================>>");
            System.out.println("User Query: [" + REQUEST.get(idx) + "]");
            System.out.println("Returns: [" + (response = QueryProcessorManager.parse(REQUEST.get(idx))) + "]");
            System.out.println("====================================================<<");
            assertEquals(response, RESPONSE.get(idx));
            count++;
        }
        System.out.println("Total de testes: " + count);

        assertTrue(true);
    }

}
