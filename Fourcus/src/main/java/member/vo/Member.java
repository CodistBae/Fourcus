package member.vo;

import lombok.Getter;

import java.sql.Date;

@Getter
public class Member {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String title;
    private Date production;
    private Long category_id;

    public Member(String username, String password, String nickname, String email, Long category_id){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.category_id = category_id;
    }

    public Member(Long id, String username, String password, String nickname, String email, String title, Long category_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.title = title;
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        String category = "";
        if (category_id == 1)
            category = "학생";
        else if (category_id == 2)
            category = "취준생";
        else if (category_id == 3)
            category = "자격증 준비";

        return "ID: " + username + '\n' + '\'' +
                "<" + title + ">" + " " + nickname + '\n' + '\'' +
                "email: " + email + '\n' + '\'' +
                "category: " + category;
    }

}
