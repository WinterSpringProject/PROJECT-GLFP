package glfp.glfp.domain.repository;

import glfp.glfp.domain.entity.Board;
import glfp.glfp.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByBoardId(Long bId, Pageable pageable);
}
