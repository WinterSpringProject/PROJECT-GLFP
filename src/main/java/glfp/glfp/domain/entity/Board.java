package glfp.glfp.domain.entity;

import glfp.glfp.dto.BoardDto;
import glfp.glfp.dto.MemberDto;
import jdk.jshell.Snippet;
import lombok.*;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)  // 시간에 대한 정보를 저장

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 15, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member fkId;

    @Column(length = 30, nullable = false)
    private String postTitle;

    @Column(length = 15, updatable = false, nullable = false)
    private LocalDateTime postCreatedTime;

    @Column(length = 15)
    private LocalDateTime postModifiedTime;

    @Column(nullable = false)
    private int matchStatus;

    @Column(nullable = false)
    private Long boardId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public Board(Long id, Member fkId, String postTitle, LocalDateTime postCreatedTime, LocalDateTime postModifiedTime,int matchStatus, Long boardId,String content) {
        this.id = id;
        this.fkId = fkId;
        this.postTitle = postTitle;
        this.postCreatedTime = postCreatedTime;
        this.postModifiedTime = postModifiedTime;
        this.matchStatus = matchStatus;
        this.boardId = boardId;
        this.content = content;
    }

    public BoardDto toDto(Board board){
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
        return boardDto;
    }
}
