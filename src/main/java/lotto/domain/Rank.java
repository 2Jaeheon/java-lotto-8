package lotto.domain;

public enum Rank {
    FIRST(6, 2_000_000_000L),
    SECOND(5, 30_000_000L),
    THIRD(5, 1_500_000L),
    FOURTH(4, 50_000L),
    FIFTH(3, 5_000L),
    LOSING(0, 0L);

    private final int matchCount;
    private final long prize;

    Rank(int matchCount, long prize) {
        this.matchCount = matchCount;
        this.prize = prize;
    }

    /**
     * Rank 인스턴스의 상태에 의존하지 않고, Enum 전체 집합이 수행해야 하는 판단 로직이므로 static 메서드로 정의되었습니다.
     * 즉, 아직 결정되지 않은 Rank 를 판단하는 팩토리 메서드의 역할을 수행합니다.
     */
    public static Rank decide(int matchCount, boolean bonusMatched) {
        if (matchCount == 6) {
            return FIRST;
        }
        if (matchCount == 5 && bonusMatched) {
            return SECOND;
        }
        if (matchCount == 5) {
            return THIRD;
        }
        if (matchCount == 4) {
            return FOURTH;
        }
        if (matchCount == 3) {
            return FIFTH;
        }
        return LOSING;
    }

    public long calculatePrize() {
        return prize;
    }
}
