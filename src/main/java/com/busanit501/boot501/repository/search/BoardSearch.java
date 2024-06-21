package com.busanit501.boot501.repository.search;

import com.busanit501.boot501.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {
  Page<Board> search(Pageable pageable);

  Page<Board> searchAll(String[] types, String keyword ,Pageable pageable);

}













