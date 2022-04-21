package org.mars;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.script.ScriptUtil;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.util.List;

/**
 * js调用测试
 * 
 * @author 薛超
 * @since 2022年1月25日
 * @version 1.0.0
 */
public class JavaScriptTest {

    public static void main(String[] args) throws ScriptException, NoSuchMethodException {
        BufferedReader reader = ResourceUtil.getReader("js/index1.js", CharsetUtil.CHARSET_UTF_8);
        ScriptEngine jsEngine = ScriptUtil.getJsEngine();
        jsEngine.eval(reader);
        // 执行一段函数
        jsEngine.eval("function getCitys(){ "//
                + " var newArr = [];\r\n" //
                + "   for(var i = 0;i<citys.length;i++){\r\n"//
                + "      var obj = {};\r\n" //
                + "      obj.name = citys[i].sfmc; \r\n"//
                + "      obj.code = citys[i].code; \r\n"//
                + "      obj.Ip  =   citys[i].Ip; \r\n"//
                + "      obj.address =  citys[i].address;  \r\n"//
                + "      newArr.push(obj);\r\n" //
                + "    }\r\n" //
                + " return newArr;}");
        Invocable inv = (Invocable) jsEngine;
        JsFunc func = inv.getInterface(JsFunc.class);
        List<JSONObject> citys = func.getCitys();
        System.out.println(JSONUtil.toJsonPrettyStr(citys));
    }

    public static interface JsFunc {
        List<JSONObject> getCitys();
    }

}
