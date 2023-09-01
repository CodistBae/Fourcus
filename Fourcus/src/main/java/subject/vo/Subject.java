package subject.vo;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class Subject {


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
        return
               "번" +  "과목명 : " + subjectName + '\'';
    }
}
