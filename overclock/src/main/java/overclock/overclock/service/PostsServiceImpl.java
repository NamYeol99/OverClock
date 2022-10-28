
//package overclock.overclock.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Service;
//import overclock.overclock.dto.PostsDTO;
//import overclock.overclock.entity.Posts;
//import overclock.overclock.model.BoardType;
//import overclock.overclock.repository.PostsRepository;
//
//@Service
//@RequiredArgsConstructor
//@Log4j2
//public class PostsServiceImpl implements PostsService {
//
//    private final PostsRepository repository;
//
//    @Override
//    public Long register(PostsDTO dto) {
//        log.info(dto);
//        Posts posts = dtoToEntity(dto);
//        repository.save(posts);
//        posts.setBoardType(BoardType.SALE);
//        return posts.getId();
//    }
//}

package overclock.overclock.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import overclock.overclock.dto.*;
import overclock.overclock.entity.*;
import overclock.overclock.model.search;
import overclock.overclock.repository.CommentRepository;
import overclock.overclock.repository.ItemImgRepository;
import overclock.overclock.repository.PostsRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@ToString
public class PostsServiceImpl implements PostsService {

    private final PostsRepository repository;
    private final ItemImgRepository itemImgRepository;
    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public Long posting(PostsDTO dto) {
        log.info("dto : {}", dto);
        Posts posts = dtoToEntity(dto);
        repository.save(posts);
        List<ItemImgDTO> imgList = dto.getImageDTOList();
        log.info("imgList : {}", imgList);
        imgList.forEach(new Consumer<ItemImgDTO>() {
            @Override
            public void accept(ItemImgDTO itemImgDTO) {
                ItemImg ii = imageDtoToEntity2(itemImgDTO);
                ii.updateItemImg(Posts.builder().id(posts.getId()).build());
                log.info("ItemImg : {}", ii);
                itemImgRepository.save(ii);
            }
        });
        return posts.getId();
    }

    @Override
    public PageResultDTO<PostsDTO, Posts> getPageList(PageRequestDTO dto) {
        log.info("PageRequestDTO: " + dto);
        Pageable pageable = dto.getPageable(Sort.by("id").descending());

//        Page<Posts> result = repository.getPageList2(pageable);
        Page<Posts> result = repository.getPartsByCategoryPageList(pageable, dto.getCategory());
        log.info("result2 : {}", result);
        Function<Posts, PostsDTO> fn = new Function<Posts, PostsDTO>() {
            @Override
            public PostsDTO apply(Posts t) {
                return entityToDTO(t);
            }
        };
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<PostsDTO, Posts> getMyPostsList(PageRequestDTO dto) {
        log.info("PageRequestDTO: " + dto);
        Pageable pageable = dto.getPageable(Sort.by("id").descending());
        Page<Posts> result = repository.getPartsByCategoryPageList(pageable, dto.getCategory());
        log.info("result2 : {}", result);
        Function<Posts, PostsDTO> fn = new Function<Posts, PostsDTO>() {
            @Override
            public PostsDTO apply(Posts t) {
                return entityToDTO(t);
            }
        };
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PageResultDTO<PostsDTO, Posts> partsCategoryPageList (PageRequestDTO dto){
        log.info("PageRequestDTO: " + dto);
        Pageable pageable = dto.getPageable(Sort.by("id").descending());
        BooleanBuilder booleanBuilder = getSearch(dto);
        Page<Posts> result = repository.getPartsByCategoryPageList(pageable, dto.getCategory());
//        Page<Posts> result2 = repository.findAll(booleanBuilder, pageable);
        Function<Posts, PostsDTO> fn = new Function<Posts, PostsDTO>() {
            @Override
            public PostsDTO apply(Posts t) {
                log.info("asd : {}", t);
                return entityToDTO(t);
            }
        };
        return new PageResultDTO<>(result, fn);
    };

    @Override
    public HashMap<String, Object> periDetail(PostsDTO dto) {
        log.info("peri id : {}", dto);
        Long id = dto.getId();
        List<EmbedCard> result = repository.getPeriByIdPageList(id).get().stream().map(v->{
            return new EmbedCard(v);
        }).collect(Collectors.toList());
        HashMap<String, Object> cardInfo = new HashMap<>();
        cardInfo.put("articles", result);
        log.info("peri result : {}", result);
        return cardInfo;
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QPosts qPosts = QPosts.posts;
        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qPosts.id.gt(0L);
        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        //검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qPosts.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qPosts.content.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }

    @Transactional
    @Override
    public PostsDTO updateView(Long id) {
        log.info("id : {}", id);
        Posts result = repository.getByid(id);
        PostsDTO postsDTO = entityToDTO(result);
        repository.updateView(id);
        return postsDTO;
    }

    @Override
    public HashMap<String, Object> getSearchList(search vo) {
        log.info(vo);

        Pageable pageable = PageRequest.of(vo.getReqPage(), 9);
        Page<Posts> page = repository.getListAndAuthorByAuthorOrTitlePage(vo.getSearch(), pageable);
        log.info("=====page======");
        log.info(page);
        List<EmbedCard> result = repository.getSearchList(vo.getSearch(), vo.getPostsType()).get().stream().map(v -> {
            return new EmbedCard(v);
        }).collect(Collectors.toList());
        HashMap<String, Object> cardInfo = new HashMap<>();
        cardInfo.put("articles", result);
        cardInfo.put("page", pageable.getPageNumber());
        cardInfo.put("pageTotalCount", page.getTotalPages());
        log.info("===================result===========================");
        log.info(result);
        log.info("===================cardinfo===========================");
        log.info(cardInfo);
        return cardInfo;
    }



    @Override
    public String PostsModify(PostsDTO dto) {
        log.info("dto--------------------- :" + dto);
        Posts entity = repository.getByid(dto.getId());
        PostsDTO getById = entityToDTO(entity);

        entity.getItemImgList().forEach(image -> {
            itemImgRepository.deleteById(image.getId());
            ;
        });

        getById.setTitle(dto.getTitle());
        getById.setContent(dto.getContent());
        Posts modifiedArticle = dtoToEntity(getById);
        repository.save(modifiedArticle);

        List<ItemImgDTO> lists = dto.getImageDTOList();
        lists.forEach(new Consumer<ItemImgDTO>() {
            @Override
            public void accept(ItemImgDTO itemImgDTO) {
                if (!itemImgRepository.findById(itemImgDTO.getId()).isPresent()) {
                    ItemImgDTO.builder().imgName(itemImgDTO.getImgName()).posts(modifiedArticle).build();
                }
            }
        });

        return modifiedArticle.getId().toString();
    }

    @Transactional
    @Override
    public Long PostsDelete(PostsDTO dto) {
        Long id = dto.getId();
        log.info("id----------- :" + id);
        repository.deleteById(id);
        return id;
    }

    @Transactional
    @Override
    public List<String> postsDetail(PostsDTO dto) {
        log.info("PostsDetail DTO : {}", dto.getId());
        Long id = dto.getId();
        List<String> result = repository.getPostsDetail(id);
        log.info("Posts Detail : {}", result);

        return result;
    }

}





