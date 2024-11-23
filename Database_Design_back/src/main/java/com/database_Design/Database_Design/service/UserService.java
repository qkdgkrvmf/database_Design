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

    // 회원가입
    public User registerUser(String userid, String password, String passwordCheck, String name, Long birth, String phoneNumber) {
        // 기존 회원 중복 확인
        Optional<User> existingUser = userRepository.findByUserid(userid);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 회원 ID입니다.");
        }

        // 새 회원 생성 및 초기값 설정
        User newUser = User.builder()
                .userid(userid)
                .password(password)
                .passwordCheck(passwordCheck)
                .name(name)
                .birth(birth)
                .phoneNumber(phoneNumber) // 수정된 필드
                .build();
        return userRepository.save(newUser);
    }

    // 로그인
    public User login(String userid, String password) {
        User user = userRepository.findByUserid(userid)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user; // 로그인 성공 시 User 객체 반환
    }

    // 회원 정보 조회
    public User getUserInfo(String userid) {
        return userRepository.findByUserid(userid)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }

    // 회원 삭제
    public void deleteUser(String userid) {
        User user = getUserInfo(userid);
        userRepository.delete(user);
    }
}
