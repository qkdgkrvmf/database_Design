package com.database_Design.Database_Design.controller;

import com.database_Design.Database_Design.entity.User;
import com.database_Design.Database_Design.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;
import com.database_Design.Database_Design.entity.Study_group;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(
        @RequestParam String loginId,
        @RequestParam String password,
        @RequestParam String passwordCheck,
        @RequestParam String name,
        @RequestParam String birth, // yyyy-MM-dd 형식으로 전달
        @RequestParam String phoneNumber
    ) {
        LocalDate birthDate = LocalDate.parse(birth); // String을 LocalDate로 변환
        User newUser = userService.registerUser(loginId, password, passwordCheck, name, birthDate, phoneNumber);
        return ResponseEntity.ok(newUser);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<User> login(
        @RequestParam String loginId,
        @RequestParam String password
    ) {
        User user = userService.login(loginId, password);
        return ResponseEntity.ok(user);
    }

    // 회원 정보 조회
//    @GetMapping("/research-user")
//    public ResponseEntity<User> getUserInfo(@RequestParam String loginId) {
//        User user = userService.getUserInfo(loginId);
//        return ResponseEntity.ok(user);
//    }

    @GetMapping("/research-user")
    public ResponseEntity<Map<String, Object>> getUserInfo(@RequestParam String loginId) {
        User user = userService.getUserInfo(loginId);

        // study_group에서 std_Id만 추출
        List<Long> studyGroupIds = user.getStudy_group().stream()
            .map(Study_group::getstd_Id) // Study_group 클래스의 메서드 사용
            .collect(Collectors.toList());

        // 응답 데이터 생성
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("loginId", user.getLoginId());
        response.put("password", user.getPassword());
        response.put("passwordCheck", user.getPasswordCheck());
        response.put("name", user.getName());
        response.put("birth", user.getBirth());
        response.put("phoneNumber", user.getPhoneNumber());
        response.put("grade", user.getGrade());
        response.put("point", user.getPoint());
        response.put("total_study", user.getTotal_study());
        response.put("std_Id", studyGroupIds); // std_Id 리스트 추가

        return ResponseEntity.ok(response);
    }



    // 회원 삭제
    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(@RequestParam String loginId) {
        userService.deleteUser(loginId);
        return ResponseEntity.ok("회원이 성공적으로 삭제되었습니다.");
    }


    // 포인트 및 등급 업데이트
    @PostMapping("/updatePG")
    public ResponseEntity<String> updatePoints(
        @RequestParam String loginId,
        @RequestParam Long totalStudy
    ) {
        // 데이터 검증
        if (loginId == null || loginId.isEmpty()) {
            return ResponseEntity.badRequest().body("loginId를 제공해야 합니다.");
        }
        if (totalStudy == null) {
            return ResponseEntity.badRequest().body("total_study를 제공해야 합니다.");
        }

        // 서비스 호출
        userService.updatePointsAndGrade(loginId, totalStudy);
        return ResponseEntity.ok("포인트 및 등급이 성공적으로 업데이트되었습니다.");
    }
}
