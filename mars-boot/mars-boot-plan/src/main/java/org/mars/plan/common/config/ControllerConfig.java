package org.mars.plan.common.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateException;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 控制层拓展配置<br>
 * 1.全局格式化BigDecimal字段返回值（四舍五入，并保留2位小数）<br>
 * 2.全局date类型转换
 * 
 * @author 薛超
 * @since 2020年3月25日
 * @version V1.0
 */
@Configuration
@ControllerAdvice
@Slf4j
public class ControllerConfig {

    private final static int SCALE = 2;
    @Value("${spring.jackson.dateFormat:yyyy-MM-dd HH:mm:ss}")
    private String dateFormat;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 方法1，注册converter
        GenericConversionService conversionService = (GenericConversionService) binder.getConversionService();
        if (conversionService != null) {
            conversionService.addConverter(new Converter<String, Date>() {
                @Override
                public Date convert(String source) {
                    return toDate(source);
                }
            });
        }
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定BigDecimal类型字段使用自定义的JsonSerializer序列化器
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BigDecimal.class, new JsonSerializer<BigDecimal>() {
            @Override
            public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
                    throws IOException {
                if (value == null) {
                    return;
                }
                // 判断结尾是否不足2位小数
                //value = value != null ? value : BigDecimal.ZERO;
                //
                String decimal = NumberUtil.toStr(value);
                // 小数位长度
                int decimalLen = StrUtil.subAfter(decimal, StrUtil.DOT, true).length();
                if (decimalLen < SCALE) {
                    // 补零
                    decimal = NumberUtil.round(value, SCALE).toPlainString();
                }
                gen.writeString(decimal);
            }
        });
        simpleModule.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if (date != null) {
                    FastDateFormat format = FastDateFormat.getInstance(dateFormat);
                    gen.writeString(format.format(date));
                }
            }
        });
        simpleModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                    throws IOException, JsonProcessingException {
                return toDate(jsonParser.getText());
            }
        });
        objectMapper.registerModule(simpleModule);
        // 忽略字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }

    private Date toDate(String dateStr) {
        if (StrUtil.isBlank(dateStr)) {
            return null;
        }
        try {
            return Convert.convert(Date.class, dateStr);
        } catch (DateException e) {
            log.error("日期转换失败:{};将尝试为long类型进行转换！", dateStr);
            if (NumberUtil.isLong(dateStr)) {
                return Convert.convert(Date.class, NumberUtil.parseLong(dateStr));
            }
            throw e;
        }

    }

}
