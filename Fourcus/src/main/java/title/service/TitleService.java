package title.service;

import member.service.MemberService;
import title.dao.TitleDao;
import title.vo.Title;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class TitleService {
    public static TitleService titleService = new TitleService();
    public static TitleDao titleDao;

    private TitleService() {
        this.titleDao = new TitleDao();
    }

    public static TitleService getInstance() {
        return titleService;
    }

    public void getTitleList() {
        List<Title> titles = titleDao.findAllByMemberId(MemberService.loginId);

        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + "." + titles.get(i) + " ");
        }
    }

    public String getTitle() {
        String title = titleDao.currentTitle(MemberService.loginId);
        if(Objects.nonNull(title)){
            return "<" + title + ">";
        }

        return "";
    }

    public void selectTitle(BufferedReader br) throws IOException {
        List<Title> titles = titleDao.findAllByMemberId(MemberService.loginId);

        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + "." + titles.get(i) + " ");
        }

        System.out.print("적용할 타이틀을 선택하세요 : ");

        titleDao.initTitle(MemberService.loginId);
        titleDao.modify(Long.parseLong(br.readLine()));
    }
}
