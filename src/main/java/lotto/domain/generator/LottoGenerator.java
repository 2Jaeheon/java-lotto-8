package lotto.domain.generator;

import java.util.List;
/**
 * 로또 번호 생성 전략에 대한 계약(Interface)을 정의합니다.
 * 테스트 용이성과 확장성을 위해 번호 생성 로직을 인터페이스로 분리했습니다.
 */
public interface LottoGenerator {
    List<Integer> generate();
}
