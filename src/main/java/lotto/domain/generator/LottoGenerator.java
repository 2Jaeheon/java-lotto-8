package lotto.domain.generator;

import java.util.List;

// 테스트 용이성과 확장성을 위해 번호 생성 로직을 인터페이스로 분리했습니다.
// 랜덤 대신 고정된 번호 생성기를 주입해 테스트할 수 있으며, 추후 정책 변경에도 LottoIssuer 수정이 필요 없습니다.
public interface LottoGenerator {
    List<Integer> generate();
}
