/**
 * 
 */
package br.com.softctrl.regex.criterya.handler.impl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import br.com.softctrl.regex.criterya.handler.IHandler;

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
public abstract class AHandler<T> implements IHandler<T> {

    @Expose
    @SerializedName("rule")
    private IHandler<String> innerRule = new IHandler<String>() {

        /*
         * (non-Javadoc)
         * @see br.com.softctrl.regex.criterya.handler.IHandler#process(java.lang.String)
         */
        @Override
        public String process(String value) {
            return value.trim();
        }

        /*
         * (non-Javadoc)
         * @see br.com.softctrl.regex.criterya.handler.IHandler#getInnerRule()
         */
        @Override
        public IHandler<String> getInnerRule() {
            return null;
        }
    };

    /*
     * (non-Javadoc)
     * @see br.com.softctrl.regex.criterya.handler.IHandler#getInnerRule()
     */
    @Override
    public IHandler<String> getInnerRule() {
        return this.innerRule;
    }

    /**
     * 
     * @param innerRule
     * @return
     */
    public AHandler<T> setInnerRule(IHandler<String> innerRule) {
        this.innerRule = innerRule;
        return this;
    }

    /**
     * 
     * @param value
     * @return
     */
    protected String processInnerRule(String value) {
        if (this.getInnerRule() != null) {
            value = this.getInnerRule().process(value);
        }
        return value;

    }

}