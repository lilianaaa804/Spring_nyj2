package org.zerock.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class TodoDTO {

    private String title;
    //@InitBinder는 필요하지 않음.-> 주석처리 해야 한다.
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date dueDate;

}
