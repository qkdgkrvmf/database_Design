package com.database_Design.Database_Design.service;


import com.database_Design.Database_Design.Repository.UserRepository;
import com.database_Design.Database_Design.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class UserService {
    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder passwordEncoder; // 비밀번호 암호화를 위해 사용


    // 회원가입
    public User registerUser(String loginId, String password, String passwordCheck, String name, Long birth, String phoneNumber) {
        // 기존 회원 중복 확인
        Optional<User> existingUser = userRepository.findByLoginId(loginId);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원 ID입니다.");
        }

        // 새 회원 생성 및 초기값 설정
        User newUser = User.builder()
                .loginId(loginId)
                .password(password)
                .passwordCheck(passwordCheck)
                .name(name)
                .birth(birth)
                .phoneNumber(phoneNumber)
                .grade("알") // 기본 등급 - 알
                .point(0L) // 기본 0포인트
                .total_study(0L) // 기본 학습량 - 0
                .build();
        return userRepository.save(newUser);
    }

    // 로그인
    public User login(String userid, String password) {
        User user = userRepository.findByLoginId(userid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user; // 로그인 성공 시 User 객체 반환
    }

//    // 회원가입 시 비밀번호 암호화
//    public User SecretPassword(String loginId, String password, String passwordCheck, String name, Long birth, String phoneNumber) {
//        if (userRepository.findByUserid(loginId).isPresent()) {
//            throw new IllegalArgumentException("이미 존재하는 회원 ID입니다.");
//        }
//
//        if (!password.equals(passwordCheck)) {
//            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
//        }
//
//        User newUser = User.builder()
//                .loginId(loginId)
//                .password(passwordEncoder.encode(password)) // 암호화된 비밀번호 저장
//                .passwordCheck(passwordEncoder.encode(passwordCheck))
//                .name(name)
//                .birth(birth)
//                .phoneNumber(phoneNumber)
//                .grade("알")
//                .point(0L)
//                .total_study(0L)
//                .build();
//
//        return userRepository.save(newUser);
//    }

    // 회원 정보 조회
    public User getUserInfo(String userid) {
        return userRepository.findByLoginId(userid)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }

    // 회원 삭제
    public void deleteUser(String userid) {
        User user = getUserInfo(userid);
        userRepository.delete(user);
    }

    // 등급 계산 (1000pt마다 등급 업그레이드)
    private String calculateGrade(Long point) {
        if (point >= 5000) {
            return "알";
        } else if (point >= 4000) {
            return "금간알";
        } else if (point >= 3000) {
            return "깨진알1";
        } else if (point >= 2000) {
            return "깨진알2";
        } else if (point >= 1000) {
            return "병아리";
        } else {
            return "닭";
        }
    }

    // 포인트 계산 (총 학습량 10분 당 1pt 추가)
    public void updatePointsAndGrade(String userid, Long studyMinutes) {
        User user = userRepository.findByLoginId(userid)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));

        // 학습량 업데이트
        user.setTotal_study(user.getTotal_study() + studyMinutes);

        // 포인트 계산: 총 학습량 10분당 1pt
        user.setPoint(user.getTotal_study() / 10);

        // 등급 계산
        user.setGrade(calculateGrade(user.getPoint()));

        // 업데이트된 유저 정보 저장
        userRepository.save(user);
    }
}
