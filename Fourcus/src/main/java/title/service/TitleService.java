package title.service;

import title.dao.TitleDao;
import title.vo.Title;

import java.util.List;
import java.util.Scanner;

public class TitleService {
    private final TitleDao titleDao;

    public TitleService() {
        this.titleDao = new TitleDao();
    }

    public String selectTitle(Scanner sc) {
        // todo : 멤버 아이디 받아서 넣을것
        List<Title> titles = titleDao.findAllByMemberId(1);

        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + "." + titles.get(i) + " ");
        }

        System.out.print("적용할 타이틀을 선택하세요 : ");
        return "<" + titles.get(sc.nextInt()) + ">";
    }
}
