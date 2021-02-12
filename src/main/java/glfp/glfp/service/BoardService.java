package glfp.glfp.service;

import glfp.glfp.domain.entity.Board;
import glfp.glfp.domain.repository.BoardRepository;
import glfp.glfp.dto.BoardDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    @Transactional
    public List<BoardDto> getBoardList(Long bId){
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : boardList){       // 함수화하여 최적화할 수 있음
            if(board.getBoardId() == bId) {
                BoardDto boardDto = BoardDto.builder()
                        .id(board.getId())
                        .fkId(board.getFkId())
                        .postTitle(board.getPostTitle())
                        .postCreatedTime(board.getPostCreatedTime())
                        .postModifiedTime(board.getPostModifiedTime())
                        .matchStatus(board.getMatchStatus())
                        .boardId(board.getBoardId())
                        .content(board.getContent())
                        .build();
                boardDtoList.add(boardDto);
            }
        }
        return boardDtoList;
    }

    @Transactional
    public BoardDto getBoard(Long mId){
        Board board = boardRepository.findById(mId).get();
        return board.toDto(board);
    }

    @Transactional
    public Long savePost(BoardDto boardDto){
        Board board = boardDto.toEntity(boardDto);
        boardRepository.save(board);
        return board.getId();
    }

    @Transactional
    public void revise(BoardDto boardDto){
        Optional<Board> res = boardRepository.findById(boardDto.getId());
        try{
            res.ifPresent(m -> {
                Board board = res.get();
                board.setId(boardDto.getId());
                board.setFkId(boardDto.getFkId());
                board.setPostTitle(boardDto.getPostTitle());
                board.setPostModifiedTime(boardDto.getPostModifiedTime());
                board.setMatchStatus(boardDto.getMatchStatus());
                board.setBoardId(boardDto.getBoardId());
                board.setContent(boardDto.getContent());
                boardRepository.save(board);
            });
        }catch(Exception e){

        }
    }

    @Transactional
    public void delete(Long id){
        boardRepository.deleteById(id);
    }
}

