import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class GroupMember {
    private long Id;
    private String Member_id;
    private String Group_id;
    private LocalDateTime Cumulative_time;

}
