package tamagotchi.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Tamagotchi {
    private long id;
    private long memberId;
    private String tamagotchiName;
    private int level;
    private Type type;
}
