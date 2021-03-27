package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {
    /*
    //자동 변환 안되는 데이터는 파라미터를 변환해서 처리
    @InitBinder
    public void initBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
    }
    */

    @RequestMapping("")
    public void basic(){

        log.info("basic................");
    }

    @RequestMapping(value = "/basic", method = {RequestMethod.GET, RequestMethod.POST})
    public void basicGet(){
        log.info("basic get............");
    }

    @GetMapping("/basicOnlyGet")
    public void basicGet2(){
        log.info("basic get only get............");
    }

    @GetMapping("/ex01")
    public String ex01(SampleDTO dto){
        log.info("" + dto);

        return "ex01";
    }
    //파라미터의 수집과 변환: Controller가 파라미터를 수집하는 방식은 파라미터 타입에 따라 자동으로 변환하는 방식을 이용한다.
    @GetMapping("/ex02")
    public String ex02(@RequestParam("name") String name, @RequestParam("age") int age){
        log.info("name: " + name);
        log.info("name: " + age);

        return "ex02";
    }
    //리스트 처리
    @GetMapping("/ex02List")
    public String ex02List(@RequestParam("ids")ArrayList<String> ids){
        log.info("ids: " +ids);

        return "ex02List";
    }
    //배열처리
    @GetMapping("/ex02Array")
    public String ex02Array(@RequestParam("ids") String[] ids){
        log.info("array ids: " + Arrays.toString(ids));

        return "ex02Array";
    }
    //객체 리스트
    @GetMapping("/ex02Bean")
    public String ex02Bean(SampleDTOList list){
        log.info("list dtos: " + list);

        return "ex02Bean";
    }
    //자동 변환 안되는 데이터리는 파라미터를 변환해서 처리 @InitBinder 또는 @DateTimeFormat
    @GetMapping("/ex03")
    public String ex03(TodoDTO todo){
        log.info("todo: " + todo);
        return "ex03";
    }
    //ModelAttribute
    @GetMapping("/ex04")
    public String ex04(SampleDTO dto, @ModelAttribute("page") int page){
        log.info("dto: " + dto);
        log.info("page: " + page);

        return "/sample/ex04";
    }
    //void 타
    @GetMapping("/ex05")
    public void ex05(){
        log.info("/ex05............");
    }
    //객체타입
    @GetMapping("/ex06")
    public @ResponseBody SampleDTO ex06(){
        log.info("/ex06.............");
        SampleDTO dto = new SampleDTO();
        dto.setAge(10);
        dto.setName("홍길동");

        return dto;
    }

    @GetMapping("/ex07")
    public ResponseEntity<String> ex07(){
        log.info("/ex07..............");
        //{"name": "홍길동"}
        String msg = "{\"name\": \"홍길동\"}";

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json;charset=UTF-8");

        return new ResponseEntity<>(msg, header, HttpStatus.OK);
    }
    //파일 업로드
    @GetMapping("/exUpload")
    public void exUpload(){
        log.info("/exUpload............");
    }
    @PostMapping("/exUploadPost")
    public void exUploadPost(ArrayList<MultipartFile> files){
        files.forEach(file -> {
            log.info("-----------------------------");
            log.info("name:" + file.getOriginalFilename());
            log.info("size:" + file.getSize());
        });
    }
}