package org.example.miniforum.service;

import lombok.RequiredArgsConstructor;
import org.example.miniforum.domain.Board;
import org.example.miniforum.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Iterable<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    // 페이징 처리된 목록 조회
    @Transactional(readOnly = true)
    public Page<Board> findAllBoards(Pageable pageable) {
        // created_at 기준으로 내림차순 정렬
        Pageable sortedByCreatedAt = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("created_at").descending());
        return boardRepository.findAll(sortedByCreatedAt);
    }

    // id에 해당하는 게시글 조회
    @Transactional(readOnly = true)
    public Board findBoardById(long id) {
        return boardRepository.findById(id).orElse(null);
    }

}
