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
}