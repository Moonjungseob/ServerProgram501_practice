package com.busanit501.boot501.service;

import com.busanit501.boot501.dto.MemberJoinDTO;

public interface MemberService {
    // 중복 아이디 예외처리
    static class MidExistException extends Exception {

    }
    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;
}
