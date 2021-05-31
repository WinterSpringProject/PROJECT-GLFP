package glfp.glfp.service;

import glfp.glfp.domain.entity.Board;
import glfp.glfp.domain.repository.BoardRepository;
import glfp.glfp.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Page<BoardDto> getBoardList(Long bId, Pageable pageable) {
        Page<Board> boardList = boardRepository.findByBoardId(bId, pageable);
        return boardList.map(b -> new BoardDto(b.getId(), b.getUser(),
                b.getPostTitle(), b.getBoardId(), b.getMatchStatus(), b.getContent()));
    }

    @Transactional
    public BoardDto getBoard(Long pId) {
        Optional<Board> findPost = boardRepository.findById(pId);
        if(findPost.isPresent())
        {
            Board board = findPost.get();
            return board.toDto(board);
        }
        return null;
    }

    @Transactional
    public Long savePost(BoardDto boardDto) {
        Board board = boardDto.toEntity(boardDto);
        boardRepository.save(board);
        return board.getId();
    }

    @Transactional
    public void revisePost(BoardDto boardDto) {
        Optional<Board> findBoard = boardRepository.findById(boardDto.getId());
        if (findBoard.isPresent()) {
            Board board = findBoard.get();
            board.setId(boardDto.getId());
            board.setContent(boardDto.getContent());
            board.setPostTitle(boardDto.getPostTitle());
            board.setUser(boardDto.getUser());
            board.setMatchStatus(boardDto.getMatchStatus());
            board.setBoardId(boardDto.getBoardId());
        }
    }

    @Transactional
    public void deletePost(Long id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public Page<BoardDto> searchPosts(Long bId, String keyword, Pageable pageable) {
        Page<Board> findPost = boardRepository.findByBoardIdAndPostTitleContaining(bId, keyword, pageable);
        return findPost.map(p -> new BoardDto(p.getId(), p.getUser(), p.getPostTitle(),
                p.getBoardId(), p.getMatchStatus(), p.getContent()));
    }
}
