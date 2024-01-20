package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
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
        log.info(form.toString());
        Member memberEntity = form.toEntity();
        log.info(memberEntity.toString());
        // 2. Entity를 db에 저장
        Member member = memberRepository.save(memberEntity);
        log.info(member.toString());
        return "redirect:/members/" + member.getId();
    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member",member);
        return "members/show";
    }

    @GetMapping("/members")
    public String index(Model model){
        List<Member> memberList = (ArrayList<Member>)memberRepository.findAll();
        model.addAttribute("memberList",memberList);
        return "members/index";
    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id,Model model){
        // 1. id 로 DB 조회
        Member memberEntity = memberRepository.findById(id).orElse(null);
        // 2. model에 등록
        model.addAttribute("member",memberEntity);
        // 3. edit 페이지에 데이터 넘기기
        return "/members/edit";
    }
}
