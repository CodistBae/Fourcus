package title.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Title {
    private long id;
    private long memberId;
    private String titleName;
}
