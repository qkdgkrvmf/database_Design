package com.database_Design.Database_Design.controller;


import com.database_Design.Database_Design.entity.User;
import com.database_Design.Database_Design.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody Map<String, Object> userDetails) {
        String loginId = (String) userDetails.get("loginId");
        String password = (String) userDetails.get("password");
        String passwordCheck = (String) userDetails.get("passwordCheck");
        String name = (String) userDetails.get("name");
        Long birth = Long.valueOf((String) userDetails.get("birth"));
        String phoneNumber = (String) userDetails.get("phoneNumber");

        User newUser = userService.registerUser(loginId, password, passwordCheck, name, birth, phoneNumber);
        return ResponseEntity.ok(newUser);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> loginDetails) {
        String loginId = loginDetails.get("loginId");
        String password = loginDetails.get("password");
        User user = userService.login(loginId, password);
        return ResponseEntity.ok(user);
    }

    // 회원 정보 조회
    @GetMapping("/{loginId}")
    public ResponseEntity<User> getUserInfo(@PathVariable String loginId) {
        User user = userService.getUserInfo(loginId);
        return ResponseEntity.ok(user);
    }

    // 회원 삭제
    @DeleteMapping("/{loginId}")
    public ResponseEntity<String> deleteUser(@PathVariable String loginId) {
        userService.deleteUser(loginId);
        return ResponseEntity.ok("회원이 성공적으로 삭제되었습니다.");
    }

    // 포인트 및 등급 업데이트
    @PostMapping("/update-points")
    public ResponseEntity<String> updatePoints(@RequestBody Map<String, Object> updateDetails) {
        String loginId = (String) updateDetails.get("loginId");
        Long studyMinutes = Long.valueOf((String) updateDetails.get("studyMinutes"));
        userService.updatePointsAndGrade(loginId, studyMinutes);
        return ResponseEntity.ok("포인트 및 등급이 성공적으로 업데이트되었습니다.");
    }
}
