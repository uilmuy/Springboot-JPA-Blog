package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;	
	
	@GetMapping({"","/"})
	public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {   //@PageableDefault 페이징
		//@AuthenticationPrincipal PrincipalDetail principalDetail index의 파리미터 값으로 사용해 세션을 확인 할 수 있다.
		/*
		 * if (principalDetail != null)
		 * System.out.println("로그인 사용자 아이디:"+principalDetail.getUsername());
		 */
		model.addAttribute("boards", boardService.글목록(pageable)); //jsp에서는 request정보라고 보면 된다.
		
		return "index"; //리턴시 viewResolver라는 것이 작동 하는데 해당 페이지로 model객채를 들고 이동 한다.
		
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/detail"; //jsp호출
	}
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id,Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/updateForm"; //jsp호출
	}
	
	//유저 권한 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		
		return "board/saveForm"; //jsp호출
	}
		
}
