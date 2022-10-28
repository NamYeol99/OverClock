package overclock.overclock.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import overclock.overclock.model.Address;
import overclock.overclock.model.BoardType;
import overclock.overclock.model.PartsType;
import overclock.overclock.repository.PostsRepository;

import javax.persistence.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "member")
@Builder
@AllArgsConstructor

public class Posts extends BaseEntity{ //게시물

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated
    private BoardType boardType;

    @ManyToOne
    private Member member;
    private String title; //제목
    private String content;
    @ColumnDefault("1")
    private Long view; //조회수

    private String partsType; //게시판 카테고리
    @Embedded
    private Address address; //주소

    @Builder.Default
    @OneToMany(mappedBy = "ItemImg", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ItemImg> itemImgList = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "posts2", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();




        @Builder
        public Posts(Member member, String title, String content,
                    Long view, BoardType boardType, Address address) {
            this.member = member;
            this.title = title;
            this.content = content;
            this.view = view;
            this.address = address;
            this.boardType = boardType;
        }

    public Posts(PostsRepository.getEmbedCardsInformation em){
        this.id = em.getId();
        this.title = em.getTitle();
        this.content = em.getContent();

    }
//    public String updateContextToString(byte[] context){
//        String result = new String(context, Charset.forName("utf-8"));
//        return result;
//    }
}
