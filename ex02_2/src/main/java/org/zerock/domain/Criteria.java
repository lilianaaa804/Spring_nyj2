package org.zerock.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
@ToString
public class Criteria {

    private int pageNum;
    private int amount;

    private String type;
    private String keyword;
    //기본값 1페이지, 글 10개로 지정
    public Criteria(){
        this(1,10);
    }

    public Criteria (int pageNum, int amount){
        this.pageNum = pageNum;
        this.amount = amount;
    }

    public String[] getTypeArr(){
        return type == null? new String[]{}: type.split("");
    }//검색 조건이 각 글자(T,W,C)로 구성되어 있으므로 배열로 만들어서 한번에 처리

    public String getListLink(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("pageNum", this.pageNum)
                .queryParam("amount", this.getAmount())
                .queryParam("type", this.getType())
                .queryParam("keyword", this.getKeyword());

        return builder.toUriString();
    }
}
