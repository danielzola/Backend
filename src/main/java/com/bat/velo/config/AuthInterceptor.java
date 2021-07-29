package com.bat.velo.config;

import com.bat.velo.dto.CommonRs;
import com.bat.velo.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yevdo.jwildcard.JWildcard;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Autowired
    protected JwtService jwtService;
    protected String[] notSecured = {"/api/v1/user/login",
                                        "/api/v1/user/fueler",
                                        "/api/v1/user/file",
                                        "/api/v1/user/filebase64",
                                        "/api/v1/user/otp",
                                        "/api/v1/values/province",
                                        "/api/v1/values/district",
                                        "/api/v1/values/city",
                                        "/api/v1/user/forgotpassword",
                                        "/api/v1/user/changeemail",
                                        "/api/v1/user/invite",
                                        "/api/v1/news",
                                        "/api/v1/news/status",
                                        "/api/v1/dashboard/data",
                                        "/api/v1/dashboard/dto",
                                        "/api/v1/dashboard/xls",
                                        "/api/v1/invitation",
                                        "/api/v1/invitation/search",
                                        "/api/v1/redemption",
                                        "/api/v1/redemption/history",
                                        "/api/v1/incentive/contribution",
                                        "/api/v1/incentive/history",
                                        "/api/v1/parameter"};

    
    protected ObjectMapper objectMapper = new ObjectMapper();
            
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        boolean result = false;
        String errMsg = "Not authorized";
        
        String auth = request.getHeader("Authorization");
        String path = request.getRequestURI();
        
        if (auth == null)
            auth = "";
        
        if (auth.startsWith("Bearer "))
            auth = auth.substring(7);
        
        if (auth.length() > 0) {
        
            if (!jwtService.isTokenExpired(auth)) {
                
                System.out.println("==>" + jwtService.getUsernameFromToken(auth));
                request.setAttribute("userId", jwtService.getUsernameFromToken(auth));
                result = true;
            }
            else
                errMsg = "Token expired";
        }
        else {
            //System.out.println("prehandle : " + path);
            
            if (JWildcard.matches("/api*", path)) {
                
                for (String patt : notSecured) {

                    if (JWildcard.matches(patt, path)) {

                        result = true;
                        break;
                    }
                }
            }
            else
                result = true;
        }
        
        if (!result) {
            
            response.setStatus(200);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper
                                        .writerWithDefaultPrettyPrinter()
                                        .writeValueAsString(new CommonRs(HttpStatus.FORBIDDEN.value(), errMsg)));
        }
        
        return result;
    }
}
