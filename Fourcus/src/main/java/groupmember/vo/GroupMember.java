package groupmember.vo;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GroupMember {
    private long Id;
    private long Member_id;
    private long Group_id;
    private long Cumulative_time;

}
