package overclock.overclock.entity;

import lombok.Data;
import overclock.overclock.repository.MemberRepository;
import overclock.overclock.repository.PostsRepository;

@Data
public class EmbedCard2 {
    private String nickname;
    private String email;
    private String name;
    private Long id;

    public EmbedCard2(MemberRepository.getEmbedCardsInformation em){
        this.id= em.getId();
        this.email = em.getEmail();
        this.name = em.getName();
        this.nickname = em.getNickname();
    }

//    public String updateContextToString(byte[] content){
//        String result = new String(content, Charset.forName("utf-8"));
//        return result;
//    }

}
