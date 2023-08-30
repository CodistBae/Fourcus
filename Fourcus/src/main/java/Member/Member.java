package Member;

import lombok.Getter;

import java.sql.Date;

public class Member {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String title;
    private Date production;
    private Long category_id;

    public Member(Long id, String username, String password, String nickname, String email, String title){
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.title = title;
    }

    public Member(Long id, String nickname, String title) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
    }

    public Member(String username, String password, String nickname, String email, Long category_id) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.category_id = category_id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public Date getProduction() {
        return production;
    }

    public Long getCategory_id() {
        return category_id;
    }

    @Override
    public String toString() {
        return "Member{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

}
