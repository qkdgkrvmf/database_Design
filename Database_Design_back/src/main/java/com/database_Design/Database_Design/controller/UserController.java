//package com.database_Design.Database_Design.controller;
//
//
//import com.database_Design.Database_Design.entity.User;
//import com.database_Design.Database_Design.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/users")
//@RequiredArgsConstructor
//public class UserController {
//    private final UserService userService;
//
//    // 회원가입
//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@RequestBody Map<String, Object> userDetails) {
//        String loginId = (String) userDetails.get("loginId");
//        String password = (String) userDetails.get("password");
//        String passwordCheck = (String) userDetails.get("passwordCheck");
//        String name = (String) userDetails.get("name");
//        // 수정
//        String birthString = (String) userDetails.get("birth");
//        LocalDate birth = LocalDate.parse(birthString); // ISO-8601 포맷 (yyyy-MM-dd)으로 변환
//        String phoneNumber = (String) userDetails.get("phoneNumber");
//
//        User newUser = userService.registerUser(loginId, password, passwordCheck, name, birth, phoneNumber);
//        return ResponseEntity.ok(newUser);
//    }
//
//    // 로그인
//    @PostMapping("/login")
//    public ResponseEntity<User> login(@RequestBody Map<String, String> loginDetails) {
//        String loginId = loginDetails.get("loginId");
//        String password = loginDetails.get("password");
//        User user = userService.login(loginId, password);
//        return ResponseEntity.ok(user);
//    }
//
//    // 회원 정보 조회
//    @GetMapping("/research-user/{loginId}")
//    public ResponseEntity<User> getUserInfo(@PathVariable String loginId) {
//        User user = userService.getUserInfo(loginId);
//        return ResponseEntity.ok(user);
//    }
//
//    // 회원 삭제
//    @DeleteMapping("/delete-user/{loginId}")
//    public ResponseEntity<String> deleteUser(@PathVariable String loginId) {
//        userService.deleteUser(loginId);
//        return ResponseEntity.ok("회원이 성공적으로 삭제되었습니다.");
//    }
//
//    // 포인트 및 등급 업데이트
//    @PostMapping("/updatePoints")
//    public ResponseEntity<String> updatePoints(@RequestBody Map<String, Object> updateDetails) {
//        String loginId = (String) updateDetails.get("loginId");
//        Long totalStudy = updateDetails.get("total_study") instanceof Number
//                ? ((Number) updateDetails.get("total_study")).longValue()
//                : null;
//
//        // 데이터 검증
//        if (loginId == null || loginId.isEmpty()) {
//            return ResponseEntity.badRequest().body("loginId를 제공해야 합니다.");
//        }
//        if (totalStudy == null) {
//            return ResponseEntity.badRequest().body("total_study를 제공해야 합니다.");
//        }
//
//        // 서비스 호출
//        userService.updatePointsAndGrade(loginId, totalStudy);
//        return ResponseEntity.ok("포인트 및 등급이 성공적으로 업데이트되었습니다.");
//    }
//
//}

package com.database_Design.Database_Design.controller;

import com.database_Design.Database_Design.entity.User;
import com.database_Design.Database_Design.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
    @GetMapping("/research-user")
    public ResponseEntity<User> getUserInfo(@RequestParam String loginId) {
        User user = userService.getUserInfo(loginId);
        return ResponseEntity.ok(user);
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
