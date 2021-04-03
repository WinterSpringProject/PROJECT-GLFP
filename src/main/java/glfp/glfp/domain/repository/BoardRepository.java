package glfp.glfp.domain.repository;

import glfp.glfp.domain.entity.Board;
import glfp.glfp.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByBoardId(Long bId, Pageable pageable);
    List<Board> findByPostTitleContaining(String keyword);
    //TODO : JPQL을 써서 특정 게시판의 글 내에서 검색하기
    List<Board> findByContentContaining(String keyword);
}
