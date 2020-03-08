package com.young.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright © 2020 YOUNG. All rights reserved.
 *
 * @Package com.young.gateway.filter
 * @ClassName MyFilter
 * @Description 自定义zuul过滤器 验证参数是否含有Token
 * @Author young
 * @Modify young
 * @Date 2020/3/8 16:14
 * @Version 1.0.0
 **/
@Slf4j
@Component
public class TokenFilter extends ZuulFilter {


    @Override
    public String filterType() {
        //定义filter的类型，有pre、route、post、error四种
        return FilterConstants.PRE_TYPE;
    }


    @Override
    public int filterOrder() {
        //定义filter的顺序，数字越小表示顺序越高，越先执行
        return 0;
    }


    @Override
    public boolean shouldFilter() {
        //表示是否需要执行该filter，true表示执行，false表示不执行
        return true;
    }


    @Override
    public Object run()  {
        //filter需要执行的具体操作
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        log.info("TokenFilter method : {}, url : {}", request.getMethod(), request.getRequestURL().toString());
        String token = request.getParameter("token");// 获取请求的参数

        if (StringUtils.isNotBlank(token)) {
            currentContext.setSendZuulResponse(true);//对请求进行路由
            currentContext.setResponseStatusCode(200);
            currentContext.set("isSuccess", true);
        } else {
            currentContext.setSendZuulResponse(false); //不对其进行路由
            currentContext.setResponseStatusCode(400);
            currentContext.setResponseBody("token is empty");
            currentContext.set("isSuccess", false);
        }
        return null;
    }
}
