package org.zerock.controller;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration //Servlet의 Context를 이용하기 위해, 스프링에서는 WebApplicationContext라는 존재를 이용하기 위해
@ContextConfiguration({"file:/Users/yeonjinoh/Documents/springStudy/ex02/src/main/webapp/WEB-INF/spring/applicationContext.xml",
                        "file:/Users/yeonjinoh/Documents/springStudy/ex02/src/main/webapp/WEB-INF/spring/appServlet/dispatcher-servlet.xml"})
@Log4j
public class BoardControllerTests {

    @Setter(onMethod_ = {@Autowired})
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }
    @Test
    public void testList() throws Exception{
        log.info(
                mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))//가짜MVC: 가짜 url, 파라미터, get 방식 호출
                .andReturn()
                .getModelAndView()
                .getModelMap()
        );
    }//DB에 저장된 게시물 확인 가능
    @Test
    public void testRegister() throws Exception{
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")//post방식 전달
                .param("title","테스트 새글 제목")//param()을 이용해 전달해야 하는 파라미터 지정-input 태그와 유사한 역
                .param("content", "테스트 새글 내용")
                .param("writer", "user00")
                ).andReturn().getModelAndView().getViewName();
        log.info(resultPage);
    }
    @Test
    public void testGet() throws Exception{
        log.info(mockMvc.perform(MockMvcRequestBuilders
            .get("/board/get")
            .param("bno", "4"))
            .andReturn()
            .getModelAndView().getModelMap());
    }
    @Test
    public void testModify() throws Exception{
        String resultPage = mockMvc
                .perform(MockMvcRequestBuilders.post("/board/modify")
                .param("bno", "1")
                .param("title", "수정된 테스트 새글 제목")
                .param("content", "수정된 테스트 새글 내용")
                .param("writer", "user00"))
                .andReturn().getModelAndView().getViewName();
        log.info(resultPage);
    }
    @Test
    public void testRemove()throws Exception{
        //삭제 전 데이터베이스에 게시물 번호 확인
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
        .param("bno", "25")
        ).andReturn().getModelAndView().getViewName();

        log.info(resultPage);
    }//MockMvc를 이용해서 파라미터를 전달할 때에는 문자열로만 처리해야 함.

    @Test
    public void testListPaging() throws Exception{
        log.info(mockMvc
                .perform(MockMvcRequestBuilders.get("/board/list")
                .param("pageNum", "2")
                .param("amount", "50"))
                .andReturn().getModelAndView().getModelMap());
    }

}
