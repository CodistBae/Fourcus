package subject.vo;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class Subject {


    private Long id;    // 과목 아이디
    private Long memberId;  // 멤버 아이디
    private String subjectName;     // 과목명

    public Subject() {}
    public Subject(Long id, Long memberId, String subjectName) {
        this.id = id;
        this.memberId = memberId;
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return
               "번" +  "과목명 : " + subjectName + '\'';
    }

    public String toString2() {
        return "과목 {" +
                "Id =" + memberId +
                ", 과목명 ='" + subjectName + '\'' +
                '}';
    }
}
