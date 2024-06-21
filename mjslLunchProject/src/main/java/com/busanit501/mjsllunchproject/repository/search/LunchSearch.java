package com.busanit501.mjsllunchproject.repository.search;

import com.busanit501.mjsllunchproject.domain.Lunch;
import com.busanit501.mjsllunchproject.dto.LunchListReplyCountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LunchSearch {
    Page<Lunch> search(Pageable pageable) ;
    Page<Lunch> searchAll(String[] types, String keyword, Pageable pageable) ;

    Page<LunchListReplyCountDTO> searchWithReplyCount(
            String[] types, String keyword ,Pageable pageable
    );

}
