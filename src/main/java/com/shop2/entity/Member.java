package com.shop2.entity;

import com.shop2.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="member")
@Getter @Setter
@ToString
public class Member{

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    private String roles; //USER,ADMIN

    public List<String> getRolesList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>(); //null 방지
    }

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setUsername(memberFormDto.getUsername());
        member.setEmail(memberFormDto.getEmail());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRoles("ROLE_ADMIN");
        return member;
    }

}