package com.yxdtyut.common;

import com.yxdtyut.exception.ParamException;
import com.yxdtyut.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author : yangxudong
 * @Description :   全局的异常处理
 * @Date : 上午10:43 2018/7/18
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        String requestUrl = request.getRequestURI();
        String defaultMsg = "system error";
        ModelAndView mv = null;
        // 这里我们要求项目中所有请求json数据，都使用.json结尾
        if (requestUrl.endsWith(".json")) {
            if (e instanceof PermissionException) {
                PermissionException permissionException = (PermissionException)e;
                mv = new ModelAndView("jsonView", (Map<String, ?>) Result.objectToMap(Result.error(permissionException.getCodeMsg())));
            } else if (e instanceof ParamException) {
                ParamException permissionException = (ParamException)e;
                mv = new ModelAndView("jsonView", (Map<String, ?>) Result.objectToMap(Result.error(new CodeMsg(permissionException.getMessage()))));
            } else {
                log.error("unkown json exception :{}, url:{},", e, requestUrl);
                mv = new ModelAndView("jsonView", (Map<String, ?>) Result.objectToMap(Result.error(new CodeMsg(defaultMsg))));
            }
        } else if (requestUrl.endsWith(".page")) {  // 这里我们要求项目中所有请求page页面，都使用.page结尾
            log.error("unkown page exception :{}, url:{},",e, requestUrl);
            mv = new ModelAndView("exception", (Map<String, ?>) Result.objectToMap(Result.error(new CodeMsg(defaultMsg))));
        } else {
            log.error("unkown exception :{}, url:{},",e, requestUrl);
            mv = new ModelAndView("jsonView", (Map<String, ?>) Result.objectToMap(Result.error(new CodeMsg(defaultMsg))));
        }
        return mv;
    }
}
