package overclock.overclock.entity;

import lombok.Data;
import overclock.overclock.dto.ItemImgDTO;
import overclock.overclock.repository.MemberRepository;
import overclock.overclock.repository.PostsRepository;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EmbedCard {
    private Long id;
    private String title;
    private String content;
    private String price;
    private String nickname;
    private String imgUuid;
    private String imgName;
    private String imgPath;
    private String view;

    public EmbedCard(PostsRepository.getEmbedCardsInformation em){
        this.id= em.getId();
        this.title = em.getTitle();
        this.content = em.getContent();
        this.nickname = em.getNickname();
        this.imgUuid = em.getImgUuid();
        this.imgName = em.getImgName();
        this.imgPath = em.getImgPath();
        this.price = em.getPrice();
        this.view = em.getView();
    }

//    public String updateContextToString(byte[] content){
//        String result = new String(content, Charset.forName("utf-8"));
//        return result;
//    }

}
