package com.busanit501.mjsllunchproject.repository;

import com.busanit501.mjsllunchproject.domain.LunchReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LunchReplyRepository extends JpaRepository<LunchReply, Long> {
    @Query("select r from LunchReply r where r.lunch.mno = :mno")
    Page<LunchReply> listOfLunch(Long mno, Pageable pageable);
}
