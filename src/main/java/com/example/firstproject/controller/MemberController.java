package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/signup")
    public String signup(){
        return "members/new";
    }

    @PostMapping("join")
    public String join(MemberForm form){
        // 1. dto -> entity 변환
        System.out.println(form.toString());    //form 확인
        Member memberEntity = form.toEntity();
        System.out.println(memberEntity.toString());    //entity 변환 확인
        Member member = memberRepository.save(memberEntity);
        System.out.println(member.toString());
        return "";
    }
}
