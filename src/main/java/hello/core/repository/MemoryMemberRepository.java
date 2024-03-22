package hello.core.repository;


import hello.core.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    //실무에서는 동시성 이슈가 발생할 수 있어 ConcurrentHashMap을 써야한다.
    private static Map<Long, Member> store = new HashMap<>();

    //마찬가지로 long 대신 AtomicLong 을 써야한다.
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //만약 결과가 없으면 null이다.
        //store.get(id);
        //요즘은 그래서 Optional로 감싼다
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                    .filter(member -> member.getName().equals(name))
                    .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
