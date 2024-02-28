package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/save")
    public String saveForm(){

        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){

        int saveResult = memberService.save(memberDTO);

        if (saveResult > 0){
            return "login";
        } else {
            return "save";
        }
    }

    @GetMapping("/login")
    public String loginForm(){

        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        HttpSession session) {

        boolean loginResult = memberService.login(memberDTO);

        if (loginResult) {
            session.setAttribute("loginEmail", memberDTO.getMemberEmail());
            return "main";
        } else {
            return "login";
        }
    }

    @GetMapping("/")
    public String findAll(Model model){

        List<MemberDTO> memberDTOList = memberService.findAll();

        model.addAttribute("memberList", memberDTOList);

        return "list";
    }

    @GetMapping // /member?id=1
    public String findById(@RequestParam("id") Long id, Model model){ // Model로 view에 데이터 넘겨줌.

        MemberDTO memberDTO = memberService.findById(id);

        model.addAttribute("member", memberDTO);

        return "detail";

    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id){

        memberService.delete(id);

        return "redirect:/member/"; //redirect 뒤에는 주소값(url 호출) 값이 와야 함.
    }

    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model){
        //세션에 저장된 나의 이메일 가져오기.
        String loginEmail = (String)session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(loginEmail);

        model.addAttribute("member", memberDTO);

        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO){

        boolean updateResult = memberService.update(memberDTO);

        if(updateResult) {
            return "redirect:/member?id="+memberDTO.getId();
        } else {
            return "index";
        }
    }

    @PostMapping("/email-check")
    //json 형식으로 응답 시에는 @ResponseBody나 @ResponseEntity를 사용함
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {

        System.out.println("memberEmail = " + memberEmail);

        String checkResult = memberService.emailCheck(memberEmail);

        return checkResult;

    }
}
