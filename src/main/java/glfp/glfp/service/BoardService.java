package glfp.glfp.service;

import glfp.glfp.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    Page<BoardDto> getBoardList(Long bId, Pageable pageable);

    BoardDto getBoard(Long pId);

    Long savePost(BoardDto boardDto);

    void revisePost(BoardDto boardDto);

    void deletePost(Long id);

    Page<BoardDto> searchPosts(Long bId, String keyword, Pageable pageable);
}
