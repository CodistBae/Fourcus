package groupmember.vo;

import lombok.*;

import java.sql.Time;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GroupMember {
    private long Id;
    private String Member_id;
    private String Group_id;
    private Time Cumulative_time;

}
