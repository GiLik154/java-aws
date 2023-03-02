package com.example.demo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Component
public class CheckPassBookKindsInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Optional<Object> nullCheckSession = Optional.ofNullable(session.getAttribute("pbKindsNum"));

        if (!nullCheckSession.isPresent() || session.getAttribute("pbKindsNum").equals(0)) {
            session.setAttribute("isNotChoice", true);
            response.sendRedirect("/choice");
            return false;
        }

        session.removeAttribute("isNotChoice");
        return true;
    }
}
