package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor
//@RequiredArgsConstructor
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

//	@Autowired
//	public void setMemberRepository(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}

//	@Autowired
//	public MemberService(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}

	/**
	 * 회원 가입
	 */
	@Transactional
	public Long join(Member member) {

		validateDuplicateMember(member); //중복 회원 검증
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateMember(Member member) {
		List<Member> findMembers = memberRepository.findByName(member.getName());
		if (!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	//회원 전체 조회
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}

	public Member findOne(Long memberId) {
		return memberRepository.findById(memberId).get();
	}

	@Transactional
	public void update(Long id, String name) {
		Member member = memberRepository.findById(id).get();
		member.setName(name);
	}
}
