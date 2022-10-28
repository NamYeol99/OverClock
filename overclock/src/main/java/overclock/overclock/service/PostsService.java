package overclock.overclock.service;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import overclock.overclock.dto.*;

import overclock.overclock.entity.*;
import overclock.overclock.model.search;
import overclock.overclock.repository.PostsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface PostsService {

    Long posting(PostsDTO dto); // 게시판 글쓰기
    PageResultDTO<PostsDTO, Posts> getPageList(PageRequestDTO dto); //일반 게시판 리스트
    PageResultDTO<PostsDTO, Posts> getMyPostsList(PageRequestDTO dto); //내가 쓴 글 리스트

    PageResultDTO<PostsDTO, Posts>  partsCategoryPageList (PageRequestDTO dto); //부품,주변기기 게시판 리스트
    PostsDTO updateView(Long id); //조회수 처리
    HashMap<String, Object> getSearchList(search vo); //검색

    String PostsModify(PostsDTO dto);
    Long PostsDelete(PostsDTO dto);
    HashMap<String, Object> periDetail(PostsDTO id);
    List<String> postsDetail(PostsDTO dto);


    default Posts dtoToEntity(PostsDTO dto) {
        Member member = Member.builder()
                .id(dto.getMemberId())
                .build();
        Posts posts = Posts.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .view((long) dto.getViewCount())
                .partsType(dto.getPartsType())
                .member(member)
                .build();
        return posts;
    }

    default PostsDTO entityToDTO(Posts posts) {
        PostsDTO postsDTO = PostsDTO.builder()
                .id(posts.getId())
                .title(posts.getTitle())
                .content(posts.getContent())
                .regDate(posts.getRegDate())
                .modDate(posts.getModDate())
                .viewCount(Math.toIntExact(posts.getView()))
                .partsType(posts.getPartsType())
                .memberId(posts.getMember().getId())
                .imageDTOList(posts.getItemImgList().stream().map(new Function<ItemImg,ItemImgDTO>() {
                    @Override
                    public ItemImgDTO apply(ItemImg t) {
                        ItemImgDTO result = ItemImgDTO.builder()
                                .imgName(t.getImgName())
                                .uuid(t.getUuid())
                                .path(t.getPath())
                                .build();

                        return result;
                    }
                }).collect(Collectors.toList()))
                .build();
        return postsDTO;
    }

    default ItemImg imageDtoToEntity(ItemImgDTO dto, Long id){
        Posts posts = Posts.builder()
                .id(dto.getPostsId())
                .build();
        ItemImg ii = ItemImg.builder()
                .uuid(dto.getUuid())
                .path(dto.getPath())
                .imgName(dto.getImgName())
                .build();
        return ii;
    }

    default ItemImg imageDtoToEntity2(ItemImgDTO dto){
        ItemImg ii = ItemImg.builder()
                .uuid(dto.getUuid())
                .path(dto.getPath())
                .imgName(dto.getImgName())
                .build();
        return ii;
    }


}

