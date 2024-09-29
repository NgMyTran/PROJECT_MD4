package ra.projectmd4.config;

import org.springframework.web.servlet.HandlerInterceptor;
import ra.projectmd4.model.dto.response.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfo u = (UserInfo) request.getSession().getAttribute("userLogin");
        if (u == null) {
            response.sendRedirect("/login");
            return false;
        }
        if(!u.isRole()){
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
