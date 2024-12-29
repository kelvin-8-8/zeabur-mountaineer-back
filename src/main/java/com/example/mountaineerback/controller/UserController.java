package com.example.mountaineerback.controller;

import com.example.mountaineerback.model.request.ChangeRequest;
import com.example.mountaineerback.model.request.LoginRequest;
import com.example.mountaineerback.model.request.RegisterRequest;
import com.example.mountaineerback.model.dto.UserDTO;
import com.example.mountaineerback.model.response.ApiResponse;
import com.example.mountaineerback.model.response.LoginResponse;
import com.example.mountaineerback.model.response.RegisterResponse;
import com.example.mountaineerback.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/*
 * WEB REST API
 * ----------------------------------
 * Servlet-Path: /auth
 * ----------------------------------
 * POST /login      登入
 * GET  /logout     登出
 * POST /register   註冊
 * POST /change     修改
 *
 * */

@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    // 登入
    // FIXME 回傳值不應該有password
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        // login 判斷比對
        log.info("使用者:{}. 開始登入", loginRequest.getUsername());
        Optional<UserDTO> optUserDTO = userService.login(loginRequest);
        if(optUserDTO.isEmpty()) {
            return ResponseEntity.status(403).body(ApiResponse.error(403, "登入失敗"));
        }
        // 存入 HttpSession 中
        session.setAttribute("userDTO", optUserDTO.get());
        log.info("使用者:{} email:{}. 登入成功", optUserDTO.get().getUsername(), optUserDTO.get().getEmail());
        return ResponseEntity.ok(ApiResponse.success("登入成功", optUserDTO.get()));

    }

    // 登出
    @GetMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpSession session) {
//        session.removeAttribute("userDTO");
        session.invalidate();
        return ResponseEntity.ok(ApiResponse.success("登出成功", "null"));
    }

    // 註冊
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<LoginResponse>> register(@RequestBody RegisterRequest registerRequest, HttpSession session) {

        // 先確認資料庫
        log.info("使用者:{} email:{}. 開始註冊", registerRequest.getUsername(), registerRequest.getEmail());
        Optional<UserDTO> optUserDTO = userService.register(registerRequest);
        if(optUserDTO.isEmpty()) {
            return ResponseEntity.status(403).body(ApiResponse.error(403, "未知錯誤"));
        }

        if(optUserDTO.get().getEmail().equals(registerRequest.getEmail()) && optUserDTO.get().getUsername().equals(registerRequest.getUsername())) {

            log.info("使用者:{} email:{}. 註冊成功.", registerRequest.getUsername(), registerRequest.getEmail());
            LoginResponse loginResponse = new LoginResponse(optUserDTO.get().getId(),optUserDTO.get().getUsername(),optUserDTO.get().getTrueName(),optUserDTO.get().getEmail(),optUserDTO.get().getRole(),optUserDTO.get().getCreatedAt());
            return ResponseEntity.ok(ApiResponse.success("註冊成功", loginResponse));
        }

        if(optUserDTO.get().getEmail().equals(registerRequest.getEmail())) {
            return ResponseEntity.status(403).body(ApiResponse.error(403, "信箱已被註冊"));
        }

        if(optUserDTO.get().getUsername().equals(registerRequest.getUsername())) {
            return ResponseEntity.status(403).body(ApiResponse.error(403, "使用者名稱已被註冊"));
        }

        // 要有個 return 在 if 之外，不會運行到這邊
        log.info("使用者:{} email:{}. 不會跑到這邊才對啊.", registerRequest.getUsername(), registerRequest.getEmail());
        LoginResponse loginResponse = new LoginResponse(optUserDTO.get().getId(),optUserDTO.get().getUsername(),optUserDTO.get().getTrueName(),optUserDTO.get().getEmail(),optUserDTO.get().getRole(),optUserDTO.get().getCreatedAt());
        return ResponseEntity.ok(ApiResponse.success("不會跑到這邊才對啊", loginResponse));
    }
    // 確認登入狀態
    @GetMapping("/checkLogin")
    public ResponseEntity<ApiResponse<Boolean>> checkLogin(HttpSession session) {
        UserDTO userdto = (UserDTO) session.getAttribute("userDTO");
        if(userdto == null) {
            return ResponseEntity.ok(ApiResponse.success("未登入", false));
        }
        return ResponseEntity.ok(ApiResponse.success("已登入", true));
    }

    // 確認身分
    @GetMapping("/checkRole")
    public ResponseEntity<ApiResponse<RegisterResponse>> checkRole(HttpSession session) {
        UserDTO userdto = (UserDTO) session.getAttribute("userDTO");
        if(userdto == null) {
            return ResponseEntity.ok(ApiResponse.error(403, "沒有Session 或 Session已過期"));
        }
        RegisterResponse registerResponse = new RegisterResponse(userdto.getId(), userdto.getUsername(), userdto.getTrueName(),userdto.getEmail(),userdto.getRole());
        return ResponseEntity.ok(ApiResponse.success("已確認身分", registerResponse));
    }


    // 修改個人資料
    @PostMapping("/change")
    public ResponseEntity<ApiResponse<RegisterResponse>> changeProfile(@RequestBody ChangeRequest changeRequest, HttpSession session) {

        UserDTO userdto = (UserDTO) session.getAttribute("userDTO");
        if(userdto == null) {
            return ResponseEntity.ok(ApiResponse.error(403, "沒有Session 或 Session已過期"));
        }
        Long userId = ((UserDTO) session.getAttribute("userDTO")).getId();

        Optional<UserDTO> optUserDTO = userService.update(userId, changeRequest);

        if(optUserDTO.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.error(403, "修改錯誤"));
        }

        RegisterResponse registerResponse = new RegisterResponse(optUserDTO.get().getId(), optUserDTO.get().getUsername(),optUserDTO.get().getTrueName(),optUserDTO.get().getEmail(),optUserDTO.get().getRole());
        return ResponseEntity.ok(ApiResponse.success("修改資料成功", registerResponse));
    }

    // 管理員
    // 權限修改(升級 降級 自降)
}
