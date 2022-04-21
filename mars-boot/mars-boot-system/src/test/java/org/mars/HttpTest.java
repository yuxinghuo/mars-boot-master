package org.mars;

import cn.hutool.core.lang.Console;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;

/**
 * http 测试
 * 
 * @author 薛超
 * @since 2021年6月28日
 * @version 1.0.0
 */
public class HttpTest {

    public static void main(String[] args) {
        String referer = "http://172.19.1.60:1144/manuallyMakeInvoice/manualMakeInvoiceSpecialInvoice";
        // 截取端口之后的路径
        referer = StrUtil.removeAll(referer, "://");
        referer = StrUtil.subSuf(referer, StrUtil.indexOf(referer, StrUtil.C_SLASH));
        Console.log("referer={}", referer);
        HttpRequest post = HttpUtil
                .createPost("http://localhost:8233/aisino-newtaxcontrol/taxControl/invoicePrepare/list");
        post.addHeaders(MapUtil.<String, String>builder()//
                .put("Referer", "http://172.19.1.60:1144/manuallyMakeInvoice/manualMakeInvoiceSpecialInvoice")//
                .put("X-Access-Token",
                        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MjQ4NjU1NDEsInVzZXJuYW1lIjoieGNtMSJ9.E4D86CyiKzP2FMZwwhz8-5bSOEKfK6eiaIwjVEeJNHg")//
                .build());
        post.body(
                "{\"invoiceMark\":\"0\",\"invoiceDataSources\":\"1\",\"pageNum\":10,\"pageNo\":1,\"auditStatus\":\"4\",\"invoiceTypeCode\":\"004\",\"createTimeOrder\":false,\"startDate\":1622476800000,\"stopDate\":1624809600000}");
        System.out.println(post.execute().body());
    }

}
