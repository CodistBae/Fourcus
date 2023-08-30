package studyhour.vo;

import lombok.Getter;
import lombok.Setter;


import java.sql.Time;
import java.time.LocalDateTime;

public class StudyHour {
    @Getter
    @Setter

    private long id;     // 공부 시간 => 자동생성, 날짜별로 저장하는 곳  // 시작한 날이 1
    private long subjectId;      // 과목 아이디
    private LocalDateTime startTime;     // 공부 시작 시각
    private LocalDateTime reStartTime;   // 공부 재시작 시각
    private LocalDateTime stopTime;  // 종료 시각
    private long cumulativeTime;   // 누적 시간
    private long maxFocusTime;    // 최대 집중 시간

    public StudyHour() {}
    public StudyHour(long id, long subjectId, LocalDateTime startTime, LocalDateTime reStartTime, LocalDateTime stopTime, long cumulativeTime, long maxFocusTime) {
        this.id = id;
        this.subjectId = subjectId;
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
                ", startTime=" + startTime +
                ", reStartTime=" + reStartTime +
                ", stopTime=" + stopTime +
                ", cumulativeTime=" + cumulativeTime +
                ", maxFocusTime=" + maxFocusTime +
                '}';
    }
}
