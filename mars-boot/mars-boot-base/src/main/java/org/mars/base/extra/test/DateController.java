package org.mars.base.extra.test;

import java.util.Date;

import org.mars.base.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 日期测试
 * 
 * @author 薛超
 * @since 2021年5月13日
 * @version 1.0.0
 */
@RestController
@RequestMapping("/test/date")
public class DateController {

    @GetMapping
    public Result<Date> list(Date date) {
        return Result.data(date);
    }

    @GetMapping("a")
    public Result<String> a(String a) {
        return Result.data(a);
    }

    @GetMapping("b")
    public Result<DateBody> b(DateBody b) {
        return Result.data(b);
    }

    @PostMapping("p")
    public Result<DateBody> p(@RequestBody DateBody p) {
        return Result.data(p);
    }

    public static void main(String[] args) {
        String post = HttpUtil.post("http://localhost:8233/aisino-newtaxcontrol/test/date/p",
                JSONUtil.toJsonStr(DateBody.of().setA(DateUtil.now()).setDate(new Date())));
        System.out.println(post);
    }

    @Data(staticConstructor = "of")
    @Accessors(chain = true)
    static class DateBody {
        private Date date;
        private String a;
    }

}