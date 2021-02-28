package org.freecash.controller.interceptor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.freecash.constant.ConstantKey;
import org.freecash.utils.HttpResult;
import org.freecash.utils.HttpResultCode;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Log4j2
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object tmp = request.getSession().getAttribute(ConstantKey.SESSION_USER);
        if(Objects.isNull(tmp)){
            HttpResult result = HttpResult.FAIL(HttpResultCode.LOGIN_REQUIRE,"登陆已超时，请重新登陆","");
            response.getWriter().write(JSONObject.toJSONString(result));
            return false;
        }
        return true;
    }

    /**
     * 返回modelAndView之前执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //log.error("postHandle返回modelAndView之前");
    }

    /**
     * 执行Handler完成执行此方法
     * @param request
     * @param response
     * @param handler
     * @param ex
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //log.error("afterCompletion执行完请求方法完全返回之后");
    }
}
