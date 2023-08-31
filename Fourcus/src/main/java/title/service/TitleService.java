package title.service;

import Member.MemberService;
import title.dao.TitleDao;
import title.vo.Title;

import java.util.List;
import java.util.Scanner;

public class TitleService {
    private final TitleDao titleDao;

    public TitleService() {
        this.titleDao = new TitleDao();
    }

    public void getTitleList() {
        List<Title> titles = titleDao.findAllByMemberId(MemberService.loginId);

        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + "." + titles.get(i) + " ");
        }
    }

    public void selectTitle(Scanner sc) {
        List<Title> titles = titleDao.findAllByMemberId(MemberService.loginId);

        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + "." + titles.get(i) + " ");
        }

        System.out.print("적용할 타이틀을 선택하세요 : ");

        titleDao.initTitle(MemberService.loginId);
        titleDao.modify(sc.nextLong());
    }
}
