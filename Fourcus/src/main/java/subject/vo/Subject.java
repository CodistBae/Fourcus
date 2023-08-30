package subject.vo;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Stream;

public class Subject {
    @Getter
    @Setter

    private long id;    // 과목 아이디
    private long memberId;  // 멤버 아이디
    private String subjectName;     // 과목명

    public Subject() {}
    public Subject(long id, long memberId, String subjectName) {
        this.id = id;
        this.memberId = memberId;
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", memberId=" + memberId +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}
