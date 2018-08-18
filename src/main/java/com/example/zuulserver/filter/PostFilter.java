package com.example.zuulserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * 后置过滤器
 */
@Component
public class PostFilter extends ZuulFilter {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        response.setHeader("content-type", "application/json;charset=utf-8");
        try{
            String resBody = IOUtils.toString(context.getResponseDataStream(), "UTF-8");
            logger.info("返回明文内容->{}", resBody);
            context.setResponseBody(resBody);
        }catch(Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return null;
    }
}
