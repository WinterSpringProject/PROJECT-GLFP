package glfp.glfp.service;

import glfp.glfp.domain.entity.Board;
import glfp.glfp.domain.repository.BoardRepository;
import glfp.glfp.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void revisePost(BoardDto boardDto){
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
    public void deletePost(Long id){
        boardRepository.deleteById(id);
    }

    @Transactional
    public Page<Board> getPageList(Pageable pageable, Long bId) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10);
        return boardRepository.findByBoardId(bId, pageable);
    }
    
    @Transactional
    public List<BoardDto> searchPosts(String keyword, Long bId, Long SearchType)
    {
        List<Board> boards = null;
        if (SearchType == 0) //글 제목
            boards = boardRepository.findByPostTitleContaining(keyword);
        else if (SearchType == 1) //글 내용
            boards = boardRepository.findByContentContaining(keyword);
//        else
//            boards = boardRepository.findByNicknameContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();
        if (boards.isEmpty()) return boardDtoList;
        for(Board board : boards){
            if (board.getBoardId() == bId)
                boardDtoList.add(board.toDto(board));
        }
        return boardDtoList;
    }
}
