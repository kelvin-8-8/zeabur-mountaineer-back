package com.example.mountaineerback.aop;

import com.example.mountaineerback.exceptions.UnauthorizedException;
import com.example.mountaineerback.model.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect // 宣告此類別為 AOP 切面
@Component // 由 Spring 來管理此物件
public class UserSessionCheckAspect {
    @Autowired
    private HttpSession session; // 自動注入 HttpSession

    @Before("@annotation(com.example.mountaineerback.aop.CheckUserSession)")
    public void checkUserSession() throws UnauthorizedException {
        // 取得 user 資訊
        UserDTO userDTO = (UserDTO)session.getAttribute("userDTO");
        // 檢查用戶是否已經登入
        if(userDTO == null) {
            // 未登入, 拋出未授權例外
            throw new UnauthorizedException("未登入或登入已過期");
        }
    }

}
