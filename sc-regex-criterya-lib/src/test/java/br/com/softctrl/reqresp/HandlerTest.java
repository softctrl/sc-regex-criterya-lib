package br.com.softctrl.reqresp;

import java.util.Arrays;
import java.util.Random;

import br.com.softctrl.regex.criterya.handler.impl.ChangeCaseHandler;
import br.com.softctrl.regex.criterya.handler.impl.RemoveAccentsHandler;
import br.com.softctrl.regex.criterya.handler.impl.ReplaceAllHandler;
import br.com.softctrl.regex.criterya.handler.impl.SplitHandler;
import br.com.softctrl.regex.criterya.handler.impl.SubStringHandler;
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
 * Unit test for the Handlers.
 * 
 * @author carlostimoshenkorodrigueslopes@gmail.com
 */
public class HandlerTest extends TestCase {

    /**
     * 
     * @param expected
     * @param actual
     */
    static public void assertEquals(String expected, String actual) {
        System.out.println(String.format(" [%s]\n=[%s]\n", expected, actual));
        assertTrue(expected.equals(actual));
    }

    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public HandlerTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(HandlerTest.class);
    }

    /**
     * Test ChangeCaseHandler class
     */
    public void testChangeCaseHandler() {
        final String str1 = "KJDGFALSHKFGASHKLDFGAKFFFFFFFFFFFFFFFFFFFFFFFFASFAFEWRGLKHWAELÇDFJKAGHDFJKLAGSDKFGASDHFG";
        final String str2 = "qwerkjgqwerklhqgwrfhqwrhqwkjergqwkerhqwjklerhwkerçqklwrhlqwejhr qwelrjh qwlkjerh qwklerq";
        final String str3 = "asdfASDFASdfasDFasgsdfGhDHjFJuyJKtyuktykFYkjfgjdfHDRyherthDghbdfghNDTujETbvrtgWcrfwERtfg";

        ChangeCaseHandler handler = new ChangeCaseHandler(false);
        assertEquals(str1.toLowerCase(), handler.process(str1));
        assertEquals(str3.toLowerCase(), handler.process(str3));
        handler = new ChangeCaseHandler(true);
        assertEquals(str1, handler.process(str1));
        assertEquals(str2.toUpperCase(), handler.process(str2));
        assertEquals(str3.toUpperCase(), handler.process(str3));

    }

    /**
     * Test ReplaceAllHandler class
     */
    public void testReplaceAllHandler() {

        String str1 = "2345kl2tg35k2g4k2jg44kjg42k34g52345uy2t423uyrt54uirt4ui2t 34iu52t34iu5t 2ui5 2523452345";
        String str2 = "ASFNAGBAGOAJGKAGAKFGAHDFGKAFDGAFGASDFKBAGJKABDGÇJAEKHROIGAHROIYHPTUORQPTHQWEJTHQBJWRKETBQTMNQVBRN,TVQWMBCBTMQRTQVNCRAVSDBNDSF23458972569023478956151895612897561348975612389456134958734657892356724YTIQURAKLJRB";

        String pattern = "[^\\p{Punct}\\d]";
        String replace = "";
        assertEquals(str1.replaceAll(pattern, replace), (new ReplaceAllHandler(pattern, replace)).process(str1));

        pattern = "[\\p{Punct}\\d]";
        assertEquals(str1.replaceAll(pattern, replace), (new ReplaceAllHandler(pattern, replace)).process(str1));

        pattern = "(A|G|Y|T|5|7|5|7)";
        replace = "*";
        assertEquals(str2.replaceAll(pattern, replace), (new ReplaceAllHandler(pattern, replace)).process(str2));

    }

    /**
     * Test SplitHandler class
     */
    public void testSplitHandler() {

        String str1 = "000.000.000-00";

        String pattern = "(\\.|-)";
        assertTrue(Arrays.equals(str1.split(pattern), (new SplitHandler(pattern)).process(str1)));

        pattern = "[\\d]";
        assertTrue(Arrays.equals(str1.split(pattern), ((new SplitHandler(pattern)).process(str1))));

        pattern = "[\\p{Punct}\\d]";
        assertTrue(Arrays.equals(str1.split(pattern), ((new SplitHandler(pattern)).process(str1))));

    }

    /**
     * Test SubStringHandler class
     */
    public void testSubStringHandler() {

        String str1 = "1234ujy123ffr 1gg4jh23j1ddder34dh1tfgd4h123fgd4123fgd41h2g3fd4123fgd412fgd4h123fgd41123ghd412fgd123ghd41234rqklwjehtfajlçkrgvblkzdfjgblçjxzdfbhkdjfhsd";
        Random random = new Random(230928475l);
        int length = str1.length();
        int begin = 0;
        int end = 0;
        for (int idx = 0; idx < 10000; idx++) {
            begin = random.nextInt(length);
            end = begin + random.nextInt(length - begin);
            assertEquals(str1.substring(begin, end), (new SubStringHandler(begin, end)).process(str1));
        }

    }

    /**
     * Test RemoveAccentsHandler class.
     */
	public void testRemoveAccentsHandler() {

		String VALUES_WITH_ACCENTS = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ";
		String VALUES_WITHOUT_ACCENTS = "AAAAAAÆCEEEEIIIIÐNOOOOOØUUUUYÞßaaaaaaæceeeeiiiiðnoooooøuuuuyþy";
		String result = new RemoveAccentsHandler().process(VALUES_WITH_ACCENTS);
		assertEquals(VALUES_WITHOUT_ACCENTS, result);

	}

}
