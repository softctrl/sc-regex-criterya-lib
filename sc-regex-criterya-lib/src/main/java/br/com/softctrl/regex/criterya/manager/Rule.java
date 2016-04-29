package br.com.softctrl.regex.criterya.manager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import br.com.softctrl.regex.criterya.handler.IHandler;
import br.com.softctrl.regex.criterya.handler.impl.AHandler;
import br.com.softctrl.utils.json.GsonUtils;

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
public class Rule {

    private static final String PACKAGE = "br.com.softctrl.regex.criterya.handler.impl.%sHandler";

    @Expose
    private int param;
    @Expose
    @SerializedName("class")
    private String clazz;

    @Expose
    private String json = "{}";

    @Expose
    @SerializedName("rule")
    private Rule innerRule;

    /**
     * 
     * @param json
     * @return
     * @throws ClassNotFoundException
     */
    public IHandler<?> getRule() throws ClassNotFoundException {
        return createRule(this);
    }

    /**
     * 
     * @param rule
     * @return
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    private IHandler<?> createRule(Rule rule) throws ClassNotFoundException {
        AHandler<?> result = (AHandler<?>) GsonUtils.fromJson(rule.json,
                Class.forName(String.format(PACKAGE, rule.clazz)));
        if (rule.innerRule != null) {
            result.setInnerRule((IHandler<String>) createRule(rule.innerRule));
        }
        return result;
    }

}
