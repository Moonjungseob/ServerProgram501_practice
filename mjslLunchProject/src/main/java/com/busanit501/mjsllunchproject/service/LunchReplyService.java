package com.busanit501.mjsllunchproject.service;

import com.busanit501.mjsllunchproject.domain.LunchReply;
import com.busanit501.mjsllunchproject.dto.LunchReplyDTO;
import com.busanit501.mjsllunchproject.dto.PageRequestDTO;
import com.busanit501.mjsllunchproject.dto.PageResponseDTO;

public interface LunchReplyService {
    Long register(LunchReplyDTO lunchReplyDTO);
    LunchReplyDTO read(Long rno);
    void update(LunchReplyDTO lunchReplyDTO);
    void delete(Long rno);
    PageResponseDTO<LunchReplyDTO> getListOfLunch(Long rno, PageRequestDTO pageRequestDTO);
}
