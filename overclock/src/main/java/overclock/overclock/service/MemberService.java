package overclock.overclock.service;

import overclock.overclock.dto.MemberDTO;
import overclock.overclock.dto.PageRequestDTO;
import overclock.overclock.dto.PageResultDTO;
import overclock.overclock.dto.PostsDTO;
import overclock.overclock.entity.Member;
import overclock.overclock.entity.Posts;
import overclock.overclock.model.Address;
import overclock.overclock.model.MemberRole;
import overclock.overclock.model.search;
import overclock.overclock.repository.MemberRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface MemberService {
    String modify(MemberDTO dto);
    Optional mList(MemberDTO memberDTO);
    Long memberRegister(MemberDTO memberDTO);
    boolean findByPhone(String phone);
    Optional findByEmail(MemberDTO email);
    boolean userEmailCheck(String email);
    boolean PhoneCheck(String phone);
    MemberDTO userNicknameCheck(String nickname);
    String passChange(MemberDTO dto);
    boolean checkPass(Long id, String password);
    String companyRegister(MemberDTO memberDTO);
    int emailCrn(MemberDTO memberDTO);
    Optional DetailName(PostsDTO postsDTO);
    String profileChange(MemberDTO dto);
    HashMap<String, Object> getAllUser();
    HashMap<String, Object> getMemberSearch(search vo); // 회원 검색


    default Member dtoToEntity(MemberDTO dto) {
        Address address = new Address(dto.getCity(), dto.getStreet(), dto.getZipcode());
        Member member = Member.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .address(address)
                .crn(dto.getCrn())
                .fromSocial(dto.isFromSocial())

                .roleSet(dto.getRoleSet().stream().map(
                        t -> {
                            if (t.equals("ROLE_MEMBER"))
                                return MemberRole.USER;
                            else if (t.equals("ROLE_ADMIN"))
                                return MemberRole.ADMIN;
                            else
                                return MemberRole.USER;
                        }).collect(Collectors.toSet()))
                .build();
        return member;
    }

    default MemberDTO EntityToDTO(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .password(member.getPassword())
                .crn(member.getCrn())
                .fromSocial(member.isFromSocial())
                .build();
                return memberDTO;
    }


}