package com.busanit501.boot501.service;

import com.busanit501.boot501.dto.ReplyDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class ReplyServiceTest {
    @Autowired
    private ReplyService replyService;

    @Test
    public void testInsert() {
//        Board board = Board.builder()
//                .bno(907L)
//                .build();

        //작성할 더미 댓글 필요함
        ReplyDTO replyDTO = ReplyDTO.builder()
                .bno(102L)
                .replyText("오늘 점심? 치킨마요")
                .replyer("문정섭")
                .build();
        Long rno = replyService.register(replyDTO);
        log.info("작성한 댓글 번호 : " + rno);
    }
}
