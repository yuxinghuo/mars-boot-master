package org.mars.plan.common.util;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 导入导出通用工具
 */
@Slf4j
public class ImportExportUtil {

    /**
     * 导出Excel模板
     *
     * @return
     */
    public static Boolean exportTemplate(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try {
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
            // 告诉浏览器要下载文件而不是直接打开文件
            response.setContentType("application/-download");
            response.setCharacterEncoding("UTF-8");
            InputStream in = ResourceUtil.getStream("public/" + fileName);
            ServletUtil.write(response, in, null, fileName);
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }


    public static Boolean exportByInputStream(InputStream in, HttpServletResponse response, String fileName) {
        try {
            // 字符串转字节
            response.setCharacterEncoding(CharsetUtil.UTF_8);
            ServletUtil.write(response,in, ContentType.TEXT_XML.getValue(), fileName + ".xml");

//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
//            // 告诉浏览器要下载文件而不是直接打开文件
//            response.setContentType("application/-download");
//            response.setCharacterEncoding("UTF-8");
//            ServletUtil.write(response, in, null, fileName);
        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性<br>
     *
     * @param agent request.getHeader("USER-AGENT");
     */
    public static String encodeChineseDownloadFileName(String agent, String pFileName) {
        try {
            if (null != agent && -1 != agent.indexOf("MSIE")) {
                pFileName = URLEncoder.encode(pFileName, "utf-8");
            } else {
                pFileName = new String(pFileName.getBytes("utf-8"), "iso8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return pFileName;
    }

}

