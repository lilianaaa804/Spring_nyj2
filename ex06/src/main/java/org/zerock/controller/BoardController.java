package org.zerock.controller;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

    private BoardService service;

    @GetMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public void register(){

    }
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
    @PreAuthorize("isAuthenticated()")
    public String register(BoardVO board, RedirectAttributes rttr){ //등록 작업 끝난 후 다시 목록화면으로 돌아올때 추가적으로 새롭게 등록된 게시물의 번호를 같이 전달
        
        log.info("==========================");
        log.info("register: " + board);
        if(board.getAttachList() != null){
            board.getAttachList().forEach(attach -> log.info(attach));
        }
        log.info("==========================");
        service.register(board);
        rttr.addFlashAttribute("result", board.getBno()); //보관된 데이터는 단 한번만 사용할 수 있게 보관된다.

        return "redirect:/board/list"; //redirect 접두어: 스프링 MVC가 내부적으로 response.sendRedirect()를 처리
    }
    @GetMapping({"/get", "/modify"})
    public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model){ //@RequestParam은 bno값을 좀 더 명시적으로 처리/화면쪽으로 해당 번호의 게시물을 전달해야 하므로 Model을 파라미터로 지정
        log.info("/get or modify");
        model.addAttribute("board", service.get(bno));
    }
    @PreAuthorize("principal.username == #board.writer")
    @PostMapping("/modify")
    public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr){
        log.info("modify:" + board);

        if(service.modify(board)){// 수정 여부를 boolean으로 처리하므로 이를 이용해서 성공한 경우에만 RedirectAttribute에 추가
            rttr.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list" + cri.getListLink();
    }
    @PreAuthorize("principal.username == #writer")
    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr){
        log.info("remove..."+bno);

        List<BoardAttachVO> attachList = service.getAttachList(bno);

        if(service.remove(bno)){
            //delete attach files
            deleteFiles(attachList);

            rttr.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list" + cri.getListLink();
    }

    private void deleteFiles(List<BoardAttachVO> attachList){
        if(attachList == null || attachList.size() == 0) {
            return;
        }

        log.info("delete attach files...................");
        log.info(attachList);

        attachList.forEach(attach -> {
            try{
                Path file = Paths.get("/Users/yeonjinoh/Documents/upload/"+attach.getUploadPath()+"/"+attach.getUuid()+"_"+attach.getFileName());
                Files.deleteIfExists(file);

                if(Files.probeContentType(file).startsWith("image")){
                    Path thumbnail = Paths.get("/Users/yeonjinoh/Documents/upload/"+attach.getUploadPath()+"/s_"+attach.getUuid()+"_"+attach.getFileName());
                    Files.delete(thumbnail);
                }
            }catch (Exception e){
                log.error("delete file error"+ e.getMessage());
            }
        });
    }

    @GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
        log.info("getAttachList"+bno);
        return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
    }



}
