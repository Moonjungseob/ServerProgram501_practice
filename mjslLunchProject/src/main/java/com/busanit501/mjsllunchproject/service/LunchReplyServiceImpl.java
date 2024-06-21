package com.busanit501.mjsllunchproject.service;

import com.busanit501.mjsllunchproject.domain.Lunch;
import com.busanit501.mjsllunchproject.domain.LunchReply;
import com.busanit501.mjsllunchproject.dto.LunchReplyDTO;
import com.busanit501.mjsllunchproject.dto.PageRequestDTO;
import com.busanit501.mjsllunchproject.dto.PageResponseDTO;
import com.busanit501.mjsllunchproject.repository.LunchReplyRepository;
import com.busanit501.mjsllunchproject.repository.LunchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class LunchReplyServiceImpl implements LunchReplyService {
    private final LunchReplyRepository lunchReplyRepository;
    private final LunchRepository lunchRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(LunchReplyDTO lunchReplyDTO) {
        LunchReply lunchReply = modelMapper.map(lunchReplyDTO, LunchReply.class);
        Optional<Lunch> result = lunchRepository.findById(lunchReplyDTO.getMno());
        Lunch lunch = result.orElseThrow();
        lunchReply.setLunch(lunch);
        Long rno = lunchReplyRepository.save(lunchReply).getRno();
        return rno;
    }

    @Override
    public LunchReplyDTO read(Long rno) {
        Optional<LunchReply> replyOptional = lunchReplyRepository.findById(rno);
        LunchReply lunchReply = replyOptional.orElseThrow();
        LunchReplyDTO lunchReplyDTO = modelMapper.map(lunchReply, LunchReplyDTO.class);
        return lunchReplyDTO;
    }

    @Override
    public void update(LunchReplyDTO lunchReplyDTO) {
        Optional<LunchReply> replyOptional = lunchReplyRepository.findById(lunchReplyDTO.getRno());
        LunchReply lunchReply = replyOptional.orElseThrow();
        lunchReply.changeText(lunchReplyDTO.getReplyText());
        lunchReplyRepository.save(lunchReply);
    }

    @Override
    public void delete(Long rno) {
        lunchReplyRepository.deleteById(rno);

    }

    @Override
    public PageResponseDTO<LunchReplyDTO> getListOfLunch(Long rno, PageRequestDTO pageRequestDTO) {
           Pageable pageable = PageRequest.of(pageRequestDTO.getPage()-1 <=0 ? 0 :pageRequestDTO.getPage()-1, pageRequestDTO.getSize(), Sort.by("rno").descending());
           Page<LunchReply> result = lunchReplyRepository.listOfLunch(rno, pageable);

        List<LunchReplyDTO> dtoList = result.getContent().stream()
                .map(lunchReply -> modelMapper.map(lunchReply,LunchReplyDTO.class))
                .collect(Collectors.toList());
        PageResponseDTO<LunchReplyDTO> pageResponseDTO = PageResponseDTO.<LunchReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();

            return pageResponseDTO;
        }
}
