package br.com.softctrl.reqresp;

import static br.com.softctrl.regex.criterya.utils.GsonUtils.fromJsonFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.softctrl.regex.criterya.impl.QueryProcessorManager;
import br.com.softctrl.regex.criterya.manager.Processor;
import br.com.softctrl.regex.criterya.utils.FileUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
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

        Processor[] processors = fromJsonFile(FileUtils.getFileInClasspath("rules.json"), Processor[].class);
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
