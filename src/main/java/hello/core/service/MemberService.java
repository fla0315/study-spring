package hello.core.service;

import hello.core.domain.Member;
import hello.core.repository.MemberRepository;
import hello.core.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;

    //DI -> dependency injection : 외부에서 넣어줌
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        //같은 이름이 있는 중복 회원 X
        //기본 방법.
//        Optional<Member> result = memberRepository.findByName(member.getName());
//
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });

        //깔끔한 방식이지만 메서드로 따로 빼는게 좋다. Ctrl + Alt + m
        validateDuplicateMember(member); //중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m ->
                                                                {
                                                                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                                                                });
    }

    /**
     * 전제 회원 조회
     */
    //service 에서는 비즈니스에 가까운 용어들을 써야한다.
    //repository -> 최대한 간결하게
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
