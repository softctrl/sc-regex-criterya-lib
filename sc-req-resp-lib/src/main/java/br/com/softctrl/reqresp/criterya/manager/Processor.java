package br.com.softctrl.reqresp.criterya.manager;

import com.google.gson.annotations.Expose;

import br.com.softctrl.reqresp.criterya.IQueryProcessor;
import br.com.softctrl.reqresp.criterya.handler.IHandler;
import br.com.softctrl.reqresp.criterya.handler.impl.DefaultRuleHandler;

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
public class Processor {

    @Expose
    private String name;
    @Expose
    private String pattern;
    @Expose
    private int countParams;
    @Expose
    private String splitParams = "";
    @Expose
    private String format;
    @Expose
    private Rule[] rules;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the pattern
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * @param pattern
     *            the pattern to set
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the countParams
     */
    public int getCountParams() {
        return countParams;
    }

    /**
     * @param countParams
     *            the countParams to set
     */
    public void setCountParams(int countParams) {
        this.countParams = countParams;
    }

    /**
     * @return the format
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param format
     *            the format to set
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * @return the rules
     */
    public Rule[] getRules() {
        return rules;
    }

    /**
     * @param rules
     *            the rules to set.
     */
    public void setRules(Rule[] rules) {
        this.rules = rules;
    }

    private static final DefaultRuleHandler DEFAULT_RULE = new DefaultRuleHandler();

    /**
     * 
     * @return
     * @throws ClassNotFoundException
     */
    public IQueryProcessor createQueryProcessor() throws ClassNotFoundException {

        IQueryProcessor processor = null;

        if (getCountParams() > 1) {
            final Object[] params = new Object[getCountParams()];
            final IHandler<?>[] rules = new IHandler<?>[getCountParams()];
            if (getRules() != null && getRules().length > 0) {
                for (int idx = 0; idx < getCountParams(); idx++) {
                    rules[idx] = getRules()[idx].getRule();
                }
            } else {
                for (int idx = 0; idx < getCountParams(); idx++) {
                    rules[idx] = DEFAULT_RULE;
                }
            }
            if ("".equals(this.splitParams)) {
                processor = new IQueryProcessor() {
                    @Override
                    public String parseQuery(String userQuery) {
                        for (int idx = 0; idx < getCountParams(); idx++) {
                            params[idx] = rules[idx].process(userQuery);
                        }
                        return String.format(Processor.this.getFormat(), params);
                    }

                    @Override
                    public boolean matches(String userQuery) {
                        return userQuery.matches(Processor.this.getPattern());
                    }
                };
            } else {
                processor = new IQueryProcessor() {
                    @Override
                    public String parseQuery(String userQuery) {
                        String[] values = userQuery.split(Processor.this.splitParams);
                        for (int idx = 0; idx < getCountParams(); idx++) {
                            params[idx] = rules[idx].process(values[idx]);
                        }
                        return String.format(Processor.this.getFormat(), params);
                    }

                    @Override
                    public boolean matches(String userQuery) {
                        return userQuery.matches(Processor.this.getPattern());
                    }
                };
            }
        } else {
            final IHandler<?> rule;
            if (getRules() != null && getRules().length == 1) {
                rule = getRules()[0].getRule();
            } else {
                rule = DEFAULT_RULE;
            }
            processor = new IQueryProcessor() {
                @Override
                public String parseQuery(String userQuery) {
                    return String.format(Processor.this.getFormat(), rule.process(userQuery));
                }

                @Override
                public boolean matches(String userQuery) {
                    return userQuery.matches(Processor.this.getPattern());
                }
            };
        }
        return processor;

    }

}
