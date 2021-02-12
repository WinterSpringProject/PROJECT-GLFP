package glfp.glfp.dto;

import glfp.glfp.domain.entity.Board;
import glfp.glfp.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class BoardDto {
    private Long id;
    private Member fkId;
    private String postTitle;
    private LocalDateTime postCreatedTime;
    private LocalDateTime postModifiedTime;
    private int matchStatus;
    private Long boardId;
    private String content;
    public Board toEntity(BoardDto boardDto){
        Board build = Board.builder()
                .id(boardDto.getId())
                .fkId(boardDto.getFkId())
                .postTitle(boardDto.getPostTitle())
                .postCreatedTime(boardDto.getPostCreatedTime())
                .postModifiedTime(boardDto.getPostModifiedTime())
                .matchStatus(boardDto.getMatchStatus())
                .boardId(boardDto.getBoardId())
                .content(boardDto.getContent())
                .build();
        return build;
    }
    @Builder
    public BoardDto(Long id, Member fkId, String postTitle, LocalDateTime postCreatedTime, LocalDateTime postModifiedTime, int matchStatus, Long boardId,String content) {
        this.id = id;
        this.fkId = fkId;
        this.postTitle = postTitle;
        this.postCreatedTime = postCreatedTime;
        this.postModifiedTime = postModifiedTime;
        this.matchStatus = matchStatus;
        this.boardId = boardId;
        this.content = content;
    }


}
