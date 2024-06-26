package com.busanit501.boot501.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

//시큐리티 필터 설정이 되어 있고
//로그인 처리를 우리가 하는게 아니라 시큐리티가 함
//시큐리티는 그냥 클래스를 요구하지 않고
//자기들이 정해둔 룰이 UserDetails를 반환하는 클래스를 요구를 해요
//시큐리티에 정의해둔 특정 클래스를 상속 받으면 됨
@Data
//@AllArgsConstructor 대신에 권한도 시큐리티에서 가져와서 사용자정의해야되서 안씀
public class MemberSecurityDTO extends User {
    private String mid;

    private String mpw;
    private String email;
    private boolean del;

    private boolean social;
    //생성자
    public MemberSecurityDTO(
            //로그인한 유저이름
            String username, String password, String email, boolean del, boolean social,
            //GrantedAuthority를 상속한 클래스는 아무나 올 수 있다. 타입으로
            Collection<? extends GrantedAuthority> authorities

    ) {
        super(username, password, authorities);
        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.del = del;
        this.social = social;
    }
}
