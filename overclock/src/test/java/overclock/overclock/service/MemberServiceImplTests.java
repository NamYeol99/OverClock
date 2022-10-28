package overclock.overclock.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import overclock.overclock.entity.Member;
import overclock.overclock.repository.MemberRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceImplTests {

    @Autowired
    MemberRepository memberRepository;
    @Test
    void memberList() {

            List<Member> result = memberRepository.findAll();  // 전체 member를 조회(위에서 2개 생성)

            Assertions.assertThat(result.size()).isEqualTo(7); // 회원의 총 수가 2와 같은지 테스트

    }
}