package com.busanit501.mjsllunchproject.service;

import com.busanit501.mjsllunchproject.dto.LunchDTO;
import com.busanit501.mjsllunchproject.dto.LunchListReplyCountDTO;
import com.busanit501.mjsllunchproject.dto.PageRequestDTO;
import com.busanit501.mjsllunchproject.dto.PageResponseDTO;
import org.springframework.data.domain.Pageable;

public interface LunchService {
    Long register(LunchDTO lunchDTO);
    LunchDTO read(Long mno);
    void update(LunchDTO lunchDTO);
    void delete(Long mno);

    PageResponseDTO<LunchDTO> list(PageRequestDTO pageRequestDTO);
    PageResponseDTO<LunchListReplyCountDTO> listWithREplyCount(PageRequestDTO pageRequestDTO);
}
