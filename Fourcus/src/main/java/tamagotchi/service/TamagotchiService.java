package tamagotchi.service;

import tamagotchi.dao.TamagotchiDao;
import tamagotchi.vo.Tamagotchi;
import tamagotchi.vo.Type;

import java.util.Scanner;

public class TamagotchiService {

    private final TamagotchiDao tamagotchiDao;

    public TamagotchiService() {
        this.tamagotchiDao = new TamagotchiDao();
    }

    public void createTamagotchi(Scanner sc) {
        //todo memberId를 인자로 넣어주어야함.
        tamagotchiDao.insert(
                Tamagotchi.builder()
                        .id(1)
                        .memberId(1)
                        .tamagotchiName("이름을 바꿔줘욤")
                        .level(1)
                        .type(Type.tree)
                        .build()
        );
    }

    public void levelUpdate() {
        //todo 누적 시간에 따라 레벨이 달라짐.
        // 누적시간 가져오고 /5 해서 레벨 구할것임.
        Tamagotchi tamagotchi = getTamagotchi();

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
            case 1 -> Type.valueOf("사과");
            case 2 -> Type.valueOf("바나나");
            case 3 -> Type.valueOf("나무");
            default -> throw new IllegalStateException("Unexpected value: " + select);
        };

        tamagotchi.setType(type);

        tamagotchiDao.modify(tamagotchi);
    }

    public Tamagotchi getTamagotchi() {
        //todo memberId를 인자로 넣어주어야함.
        return tamagotchiDao.findByMemberId(1);
    }

    public void deleteTamagotchi() {
        //todo memberId를 인자로 넣어주어야함.
        tamagotchiDao.deleteByMemberId(1);
    }
}
