package group.vo;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Group {
    private long Id;
    private long Member_id;
    private String Group_name;
    private String notice;

    public Group(){}

    public Group(Long Member_id, String Group_name){
        this.Member_id = Member_id;
        this.Group_name = Group_name;
    }
}