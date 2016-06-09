/**
 * 
 */
package br.com.softctrl.regex.criterya.handler.impl;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * @author timoshenko
 *
 */
public class RemoveAccentsHandler extends AHandler<String> {
	
	/**
	 * 
	 */
	private static final String PATTERN = "\\p{InCombiningDiacriticalMarks}+";

	/**
	 * 
	 */
	private static final String EMPTY_STRING = "";
	
	/**
	 * 
	 */
	private final Pattern pattern;	
	
	public RemoveAccentsHandler() {
		this.pattern = Pattern.compile(PATTERN);
	}

	/* (non-Javadoc)
	 * @see br.com.softctrl.regex.criterya.handler.IHandler#process(java.lang.String)
	 */
	@Override
	public String process(String value) {
        String _value = Normalizer.normalize(this.processInnerRule(value), Normalizer.Form.NFD); 
        return this.pattern.matcher(_value).replaceAll(EMPTY_STRING);
	}

}
