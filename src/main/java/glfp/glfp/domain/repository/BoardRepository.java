package glfp.glfp.domain.repository;

import glfp.glfp.domain.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByBoardId(Long bId, Pageable pageable);
    Page<Board> findByBoardIdAndPostTitleContaining(Long bid, String keyword, Pageable pageable);
}
