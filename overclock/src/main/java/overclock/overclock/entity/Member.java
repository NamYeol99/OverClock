package overclock.overclock.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import overclock.overclock.dto.JoinFormDTO;
import overclock.overclock.dto.MemberDTO;
import overclock.overclock.model.Address;
import overclock.overclock.model.MemberRole;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String password;
    private String nickname;
    private String phone;
    @Embedded
    private Address address;

    private boolean fromSocial;

    private int crn; //사업자등록번호
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Posts> posts = new ArrayList<>();

    @Builder
    public Member(Long id, String email, String name, String password, String nickname,
                  String phone, Address address, int crn) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.address = address;
        this.crn = crn;
    }

    public static Member createMember(MemberDTO memberDTO) {
        Member member = new Member();
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setAddress(memberDTO.getCity(), memberDTO.getStreet(), memberDTO.getZipcode());
        member.setPassword(memberDTO.getPassword());
        return member;
    }

    private void setAddress(String city, String street, String zipcode) {}
    public void addMemberRole(MemberRole role){
        roleSet.add(role);
    }

}
