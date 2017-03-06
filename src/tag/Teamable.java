package tag;

public interface Teamable {
    enum Team {
        RED, BLUE, GREEN, YELLOW
    }

    Team getTeam();
}
