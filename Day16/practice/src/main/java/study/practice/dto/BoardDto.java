package study.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.practice.entity.Member;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {

    // 작성자 받아올 변수
    private Long memberId;

    private Long boardId;

    private String title;

    // 작성자
    private Member writer;

    private String content;

    private LocalDateTime createdDate;
}
