package studyhour.vo;

import lombok.Getter;
import lombok.Setter;


import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@Setter

public class StudyHour {

    private Long id;     // 공부 시간 => 자동생성, 날짜별로 저장하는 곳  // 시작한 날이 1
    private Long subjectId;      // 과목 아이디
    private Time startTime;     // 공부 시작 시각
    private Time reStartTime;   // 공부 재시작 시각
    private Time stopTime ;  // 종료 시각
    private long cumulativeTime;   // 누적 시간
    private long maxFocusTime;    // 최대 집중 시간
    private String startDate;        // 시작일


    public StudyHour() {
    }
    public StudyHour(Long id, Long subjectId, String startDate, Time startTime, Time reStartTime, Time stopTime, long cumulativeTime, long maxFocusTime) {
        this.id = id;
        this.subjectId = subjectId;
        this.startDate = startDate;
        this.startTime = startTime;
        this.reStartTime = reStartTime;
        this.stopTime = stopTime;
        this.cumulativeTime = cumulativeTime;
        this.maxFocusTime = maxFocusTime;

    }

    @Override
    public String toString() {
        return "Studyhour{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", startDate=" + startDate +
                ", startTime=" + startTime +
                ", reStartTime=" + reStartTime +
                ", stopTime=" + stopTime +
                ", cumulativeTime=" + cumulativeTime +
                ", maxFocusTime=" + maxFocusTime +

                '}';
    }
}
