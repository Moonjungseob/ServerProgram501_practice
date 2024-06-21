package com.busanit501.mjsllunchproject.service;

import com.busanit501.mjsllunchproject.domain.Lunch;
import com.busanit501.mjsllunchproject.dto.LunchDTO;
import com.busanit501.mjsllunchproject.dto.LunchListReplyCountDTO;
import com.busanit501.mjsllunchproject.dto.PageRequestDTO;
import com.busanit501.mjsllunchproject.dto.PageResponseDTO;
import com.busanit501.mjsllunchproject.repository.LunchRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class LunchServiceImpl implements LunchService {
    private final LunchRepository lunchRepository;
    private final ModelMapper modelMapper;
    @Override
    public Long register(LunchDTO lunchDTO) {
        Lunch lunch = modelMapper.map(lunchDTO, Lunch.class);
        Long mno = lunchRepository.save(lunch).getMno();
        return mno;
    }

    @Override public LunchDTO read(Long mno) {
        Optional<Lunch> result = lunchRepository.findById(mno);
        Lunch lunch = result.orElseThrow();
        LunchDTO lunchDTO = modelMapper.map(lunch, LunchDTO.class);
        return lunchDTO;
    }

    @Override
    public void update(LunchDTO lunchDTO) {
        Optional<Lunch> result = lunchRepository.findById(lunchDTO.getMno());
        Lunch lunch = result.orElseThrow();
        lunch.changeTitleAndContent(lunchDTO.getLunchTitle(),lunchDTO.getContent());
        lunchRepository.save(lunch);
    }

    @Override
    public void delete(Long mno) {
        lunchRepository.deleteById(mno);
    }

    @Override
    public PageResponseDTO<LunchDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("mno");
        Page<Lunch> result = lunchRepository.searchAll(types,keyword,pageable);
        List<LunchDTO> dtoList = result.getContent().stream()
                .map(lunch -> modelMapper.map(lunch,LunchDTO.class))
                .collect(Collectors.toList());
        PageResponseDTO pageResponseDTO = PageResponseDTO.<LunchDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();

        return pageResponseDTO;
    }

    @Override
    public PageResponseDTO<LunchListReplyCountDTO> listWithREplyCount(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("mno");
        Page<LunchListReplyCountDTO> result = lunchRepository.searchWithReplyCount(types,keyword,pageable);

        PageResponseDTO pageResponseDTO = PageResponseDTO.<LunchListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int) result.getTotalElements())
                .build();

        return pageResponseDTO;
    }
}
