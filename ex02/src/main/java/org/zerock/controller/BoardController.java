package org.zerock.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

    private BoardService service;

//    @GetMapping("/list")
//    public void list(Model model){
//        log.info("list");
//
//        model.addAttribute("list", service.getList());
//    }

    @GetMapping("/list")
    public void list(Criteria cri, Model model) throws Exception{

        log.info("list: " + cri);
        model.addAttribute("list", service.getList(cri));
        //model.addAttribute("pageMaker", new PageDTO(cri, 123));

        int total = service.getTotal(cri);

        log.info("total: "+total);

        model.addAttribute("pageMaker", new PageDTO(cri, total));
    }

    @PostMapping("/register")
    public String register(BoardVO board, RedirectAttributes rttr){ //등록 작업 끝난 후 다시 목록화면으로 돌아올때 추가적으로 새롭게 등록된 게시물의 번호를 같이 전달
        log.info("register: " + board);
        service.register(board);
        rttr.addFlashAttribute("result", board.getBno()); //보관된 데이터는 단 한번만 사용할 수 있게 보관된다.

        return "redirect:/board/list"; //redirect 접두어: 스프링 MVC가 내부적으로 response.sendRedirect()를 처리
    }
    @GetMapping({"/get", "/modify"})
    public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model){ //@RequestParam은 bno값을 좀 더 명시적으로 처리/화면쪽으로 해당 번호의 게시물을 전달해야 하므로 Model을 파라미터로 지정
        log.info("/get or modify");
        model.addAttribute("board", service.get(bno));
    }
    @PostMapping("/modify")
    public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr){
        log.info("modify:" + board);

        if(service.modify(board)){// 수정 여부를 boolean으로 처리하므로 이를 이용해서 성공한 경우에만 RedirectAttribute에 추가
            rttr.addFlashAttribute("result", "success");
        }
//        rttr.addAttribute("pageNum", cri.getPageNum());
//        rttr.addAttribute("amount", cri.getAmount());
//        rttr.addAttribute("type", cri.getType());
//        rttr.addAttribute("keyword", cri.getKeyword());
//
//        return "redirect:/board/list";
        return "redirect:/board/list" + cri.getListLink();
    }
    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr){
        log.info("remove..."+bno);
        if(service.remove(bno)){
            rttr.addFlashAttribute("result", "success");
        }
//        rttr.addAttribute("pageNum", cri.getPageNum());
//        rttr.addAttribute("amount", cri.getAmount());
//        rttr.addAttribute("type", cri.getType());
//        rttr.addAttribute("keyword", cri.getKeyword());
//
//        return "redirect:/board/list";
        return "redirect:/board/list" + cri.getListLink();
    }

    @GetMapping("/register")
    public void register(){

    }


}
