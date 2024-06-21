package com.busanit501.mjsllunchproject.service;

import com.busanit501.mjsllunchproject.dto.LunchReplyDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class LunchReplyServiceTest {
    @Autowired
    private LunchReplyService lunchReplyService;

    @Test
    public void testInsert() {
        // 작성할 더미 댓글 필요함.
        LunchReplyDTO lunchReplyDTO = LunchReplyDTO.builder()
                .mno(8233L)
                .replyText("오늘 점심? 회밀면")
                .replyer("이상용")
                .build();
        Long rno = lunchReplyService.register(lunchReplyDTO);
        log.info("작성한 댓글 번호 : " + rno);
    }
}
