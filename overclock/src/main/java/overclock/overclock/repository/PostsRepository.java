package overclock.overclock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import overclock.overclock.entity.Member;
import overclock.overclock.entity.Posts;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Posts, String > {

    @EntityGraph(attributePaths = { "member" }, type = EntityGraph.EntityGraphType.LOAD)
    @Query(
            value = "select c from Posts c ",
            countQuery = "select count(c) from Posts c"
    )
    Page<Posts> getPageList(Pageable pageable);

    @Query(value = "SELECT p FROM Posts p WHERE p.partsType =:category")
    Page<Posts> getPartsByCategoryPageList(Pageable pageable, String category);

    @Modifying
    @Query("update Posts p set p.view = p.view + 1 where p.id = :id ")
    void updateView(Long id);

    Posts getByid(Long id);

    @Query("SELECT p.id as id, p.title as title, p.content as content, m.nickname as nickname, i.uuid as imgUuid, " +
            "i.imgName as imgName, i.path as imgPath " +
            "FROM Posts p " +
            "LEFT JOIN Member m ON p.member.id = m.id " +
            "left join ItemImg i ON i.ItemImg.id = p.id " +
            "WHERE p.title LIKE CONCAT('%',:search,'%') AND p.partsType =:postsType " )
    Optional<List<getEmbedCardsInformation>> getSearchList(String search, String postsType);

    @Query("SELECT p.content as content, p.title as title, p.view as view, i.price as price, m.uuid as imgUuid, " +
            "m.imgName as imgName, m.path as imgPath " +
            "FROM Posts p " +
            "LEFT JOIN Item i ON i.posts2.id = p.id " +
            "left join ItemImg m ON m.ItemImg.id = p.id " +
            "WHERE p.id =:id")
    Optional<List<getEmbedCardsInformation>> getPeriByIdPageList(Long id);

    @Query("SELECT p.id, p.title " +
            "FROM Posts p left join Member m on m.id = p.id " +
            "where p.title LIKE CONCAT('%',:search,'%') " +
            "group by p.id")
    Page<Posts> getListAndAuthorByAuthorOrTitlePage(String search, Pageable pageable);

    void deleteById(Long id);

    @Query(value = "SELECT i.price, s.title, s.content, s.viewCount " +
            "FROM Item i LEFT JOIN (SELECT p.id as id, p.title AS title, p.content AS content, p.parts_type as partsType, p.view as viewCount FROM Posts p) s " +
            "ON s.id = i.posts2_id " +
            "WHERE s.id =:id ", nativeQuery = true)
    List<String> getPostsDetail(Long id);



    public interface getEmbedCardsInformation {
        Long getId();

        String getTitle();

        String getContent();

        String getNickname();

        String getPrice();

        String getItemImgList();

        String getImgUuid();
        String getImgName();
        String getImgPath();
        String getView();
        String getEmail();
        String getName();

    }



}

