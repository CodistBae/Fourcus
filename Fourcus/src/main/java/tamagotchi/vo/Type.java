package tamagotchi.vo;

public enum Type {
    apple("사과"), banana("바나나"), tree("나무");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
