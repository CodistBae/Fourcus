package tamagotchi.service;

import member.service.MemberService;
import tamagotchi.dao.TamagotchiDao;
import tamagotchi.vo.Tamagotchi;
import tamagotchi.vo.Type;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;

public class TamagotchiService {

    private final TamagotchiDao tamagotchiDao;

    public TamagotchiService() {
        this.tamagotchiDao = TamagotchiDao.getInstance();
    }

    public void levelUpdate() {
        List<Long> totalStudyTime = tamagotchiDao.getTotalStudyTime(MemberService.loginId);
        long sumTime = totalStudyTime.stream()
                .mapToLong(Long::longValue)
                .sum();

        Duration duration = Duration.ofSeconds(sumTime);
        int level = (int) (duration.toHours() / 5 + 1);

        Tamagotchi tamagotchi = getTamagotchi();
        tamagotchi.setLevel(level);

        tamagotchiDao.modify(tamagotchi);
    }

    public void nickNameChange(Scanner sc) {
        Tamagotchi tamagotchi = getTamagotchi();
        System.out.print("변경할 타마고치 이름을 입력 : ");
        tamagotchi.setTamagotchiName(sc.nextLine());

        tamagotchiDao.modify(tamagotchi);
    }

    public void typeChange(Scanner sc) {
        Tamagotchi tamagotchi = getTamagotchi();
        System.out.println("변경할 타마고치 타입 선택 1.사과 2.바나나 3.나무");

        int select = sc.nextInt();
        Type type = switch (select) {
            case 1 -> Type.valueOf("apple");
            case 2 -> Type.valueOf("banana");
            case 3 -> Type.valueOf("tree");
            default -> throw new IllegalStateException("Unexpected value: " + select);
        };

        tamagotchi.setType(type);

        tamagotchiDao.modify(tamagotchi);
    }

    public Tamagotchi getTamagotchi() {
        return tamagotchiDao.findByMemberId(MemberService.loginId);
    }

    public void deleteTamagotchi() {
        // TODO: 2023/08/30 삭제할때 타마고치도 같이 삭제.
        tamagotchiDao.deleteByMemberId(MemberService.loginId);
    }
}
