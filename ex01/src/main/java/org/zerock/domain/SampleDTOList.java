package org.zerock.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//전달하는 데이터가 SampleDTO와 같이 객체타입이고 여러개라면 아래 코드를 통해 한번에 처리

@Data
public class SampleDTOList {
    private List<SampleDTO> list;

    public SampleDTOList(){
        list = new ArrayList<>();
    }
}
