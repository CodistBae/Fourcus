package groupmember.vo;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GroupMember {
    private Long Id;
    private Long Member_id;
    private Long Group_id;
    private Long Cumulative_time;

}
