package study.datajpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get(); // findById : 트랜잭션이 있는 범위에서 엔티티 조회
        return member.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member){ // 도메인 클래스 컨버터가 동작 : 트랜잭션이 없는 범위에서 엔티티 조회 (단순 조회용)
        // id를 받아서 Data JPA가 자동으로 Repository 에서 member를 찾아와줌
        return member.getUsername();
    }

    @GetMapping("/members") // Http 파라미터들이 컨트롤러에서 바인딩 될 때, pageable이 PageRequest 객체를 생성해서, 값을 채워주고 인젝션
    public Page<MemberDto> list(@PageableDefault(size = 5, direction = Sort.Direction.DESC) Pageable pageable){ // Pageable : 파라미터 정보, Page : 결과 정보
        return memberRepository.findAll(pageable)
                .map(MemberDto::new);
    }

//    @PostConstruct
    public void init(){
        for (int i=0; i<100; i++){
            memberRepository.save(new Member("user" + i, i));
        }
    }
}
