package subject.service;

import member.service.MemberService;
import subject.dao.Subjectdao;
import subject.vo.Subject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubjectService {
    private Subjectdao sjdao;
    public SubjectService() {
        sjdao = new Subjectdao();
    }

    // 과목 추가
    public void insertSubject(BufferedReader br) throws IOException {
        System.out.println("====과목을 추가====");
        System.out.println("과목명을 입력하세요.");
        String subjectName = br.readLine();
        Long memberId = MemberService.loginId;
        sjdao.insert(memberId, subjectName);
        System.out.println("과목이 추가되었습니다.");
    }

    // 과목 수정
    public void editSubjectName(BufferedReader br) throws IOException {
        System.out.println("====과목 수정====");
        System.out.println("====과목 리스트====");
        List<Subject> list = sjdao.selectAll(MemberService.loginId);
        for (int i = 0; i < list.size() ; i++) {
            System.out.println(i + "  " + list.get(i));
        }
        System.out.println("수정할 과목 번호를 입력하세요.");
        int num = Integer.parseInt(br.readLine());
        if(num < 0 || num > list.size()-1) {
            System.out.println("잘못 입력하셨습니다.");
        } else{
            System.out.println("새로운 과목명을 입력하세요.");
            String newSubjectName = br.readLine();

            sjdao.update(newSubjectName, list.get(num).getId());
        }


    }
    // 과목 선택

    // 과목 전체 출력
    public void printAll(){
        List<Subject> list = sjdao.selectAll(MemberService.loginId);
        if(list.isEmpty()){
            System.out.println("과목을 먼저 추가해주세요.");
        } else{
            for(Subject s : list){
                System.out.println(s.toString2());
            }
        }

    }

    // 과목 비어있는지 확인
    public ArrayList<Subject> checkSubject(){
        ArrayList<Subject> list = sjdao.selectAll(MemberService.loginId);
        return list;
    }


    // 과목 삭제
    public void delSubject(BufferedReader br) throws IOException {
        System.out.println("====과목 삭제====");
        System.out.println("====과목 리스트====");
        List<Subject> list = sjdao.selectAll(MemberService.loginId);
        for (int i = 0; i < list.size() ; i++) {
            System.out.println(i + "  " + list.get(i));
        }

        System.out.println("삭제할 과목 번호를 입력하세요.");
        int num = Integer.parseInt(br.readLine());
        // list.size() = 3 인데 num = 3 해버리면 3>3이 x 삭제가 되어버림
        if(num <0 || num > list.size()-1) {
            System.out.println("잘못 입력하셨습니다.");
        } else {
            sjdao.delete(list.get(num).getId());

            System.out.println("삭제완료.");
        }

    }

}
