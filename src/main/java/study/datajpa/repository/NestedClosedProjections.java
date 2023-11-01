package study.datajpa.repository;

public interface NestedClosedProjections {

    String getUsername(); // 첫번째는 최적화가 되지만
    TeamInfo getTeam(); // 두번째부터는 최적화가 되지 않고 엔티티 자체를 불러옴

    interface TeamInfo{
        String getName();
    }
}
