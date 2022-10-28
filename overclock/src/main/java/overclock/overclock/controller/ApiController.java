package overclock.overclock.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import overclock.overclock.dto.*;
import overclock.overclock.entity.*;
import overclock.overclock.model.search;
import overclock.overclock.repository.MemberRepository;
import overclock.overclock.service.*;

import java.util.*;

@RestController
@Log4j2
@RequestMapping("api/")
@RequiredArgsConstructor
public class ApiController {
    private final PostsService postsService;
    private final MemberService memberService;

    private final ItemService itemService;
    private final CommentService commentService;
    private final SendEmailService sendEmailService;
    private final CartService cartService;

    private final PasswordEncoder encoder;
    /**
     * 멤버 회원가입
     */
    @RequestMapping(value = "/memberRegister", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> register(@RequestBody MemberDTO dto){
        log.info("asd");
        log.info("api/memberRegister...:" + dto);
        Long id = memberService.memberRegister(dto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    /**
     * 업체 회원가입
     */
    @RequestMapping(value = "/companyRegister", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> companyRegister(@RequestBody MemberDTO dto){
        log.info("api/companyRegister...:" + dto);
        String email = memberService.companyRegister(dto);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @RequestMapping(value = "/crn", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> crn(@RequestBody MemberDTO dto){
        log.info("api/crn...:" + dto);
        int crn = memberService.emailCrn(dto);
        return new ResponseEntity<>(crn, HttpStatus.OK);
    }



    /**
     * 일반 게시판 목록 처리
     */
    @RequestMapping(value = "/getlist", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResultDTO<PostsDTO, Posts>> getList(@RequestBody PageRequestDTO dto) {
        PageResultDTO<PostsDTO,Posts> result = postsService.getPageList(dto);
        log.info("postsDTO : {}", dto);
        log.info("List result : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 부품 게시판 목록 처리
     */
    @RequestMapping(value = "/partsList", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResultDTO<PostsDTO, Posts>> partsList(@RequestBody PageRequestDTO dto) {
        PageResultDTO<PostsDTO,Posts> result = postsService.partsCategoryPageList(dto);
        log.info("postsDTO : {}", dto);
        log.info("List result : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * partsItem price 값 받아옴
     */
    @RequestMapping(value = "/partsItemList", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Integer>> partsItemList(@RequestBody PostsDTO dto) {
        List<Integer> result = itemService.partsItemList(dto);

        log.info("csacacsacsac : {}", dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 주변기기 게시판 출력
     */
    @RequestMapping(value = "/periList", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResultDTO<PostsDTO, Posts>> periList(@RequestBody PageRequestDTO dto) {
        PageResultDTO<PostsDTO,Posts> result = postsService.partsCategoryPageList(dto);
        log.info("postsDTO : {}", dto);
        log.info("List result : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    /**
     * 주변기기 상세 게시판
     */
    @RequestMapping(value = "/periDetail", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, Object>> periDetail(@RequestBody PostsDTO id) {
        log.info("api id : {}", id);
        HashMap<String, Object> result = postsService.periDetail(id);
        log.info("peri result : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 부품 상세 게시판
     */
    @RequestMapping(value = "/partsDetail", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, Object>> mbDetail(@RequestBody PostsDTO dto) {
        log.info("postsDTO Id: {}", dto);
        HashMap<String,Object> result = postsService.periDetail(dto);
        log.info("List result : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/postsDetail", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> postsDetail(@RequestBody PostsDTO dto) {
        log.info("postsDetail : {}", dto.getId());
        List<String> result = postsService.postsDetail(dto);
        log.info("List result : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 조회수 처리
     */
    @RequestMapping(value = "/read/{id}", method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateView(@ModelAttribute("id") Long id) {
        log.info("asdasd : {}", id);
        PostsDTO postsDTO = postsService.updateView(id);

        return new ResponseEntity<>(postsDTO, HttpStatus.OK);
    }

    /**
     * 검색 처리
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, Object>> ArticleCardsSearch(@RequestBody search vo) {
        log.info("------------------------------------search--------------------");
        log.info(vo);
        return new ResponseEntity<>(postsService.getSearchList(vo), HttpStatus.OK);
    }

    /**
     * 댓글 등록
     */
    @RequestMapping(value = "/comment/add", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> comment(@RequestBody CommentDTO dto) {
        log.info("CommentDTO : {}", dto);
        Long commentDTO = commentService.addComment(dto);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    /**
     * 댓글 목록 처리
     */
    @RequestMapping(value = "/comment/list", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResultDTO<CommentDTO, Comment>> commentList(@RequestBody PageRequestDTO dto) {
        PageResultDTO<CommentDTO,Comment> result = commentService.commentPageList(dto);
//        log.info("comment Id : {}", commentDTO);
        log.info("PageRequestDTO : {}", dto);
        log.info("Comment List result : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //댓글 작성자 찾기
    @RequestMapping(value = "/comment/name", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> commentName(@RequestBody MemberDTO dto) {
        log.info("MemberDTO : {}", dto);
        String name = commentService.commentName(dto);
        log.info("name MemberDTO :: {}", name);
        return new ResponseEntity<>(name , HttpStatus.OK);
    }
    /**
     * 글 수정
     */
    @RequestMapping(value = "/modify/send", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> PostsModify(@RequestBody PostsDTO dto) {
        log.info("asasaas :" + dto);
        String articleInfo = postsService.PostsModify(dto);
        return new ResponseEntity<>(articleInfo, HttpStatus.OK);
    }
    
    /**
     * 글 삭제
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> PostsDelete(@RequestBody PostsDTO dto) {
        log.info("ppppppppppppppppp :" + dto);
        Long articleInfo = postsService.PostsDelete(dto);
        return new ResponseEntity<>(articleInfo, HttpStatus.OK);
    }

    /**
     * 댓글 수정
     * @param dto
     * @return
     */
    @RequestMapping(value = "/cModify/send", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> CommentModify(@RequestBody CommentDTO dto) {
        log.info("asasaas :" + dto);
        String commentInfo = commentService.CommentModify(dto);
        return new ResponseEntity<>(commentInfo, HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     * @param dto
     * @return
     */
    @RequestMapping(value = "/cDelete", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> CommentDelete(@RequestBody CommentDTO dto) {
        Long commentInfo = commentService.CommentDelete(dto);
        return new ResponseEntity<>(commentInfo, HttpStatus.OK);
    }

    /**
     * 회원 정보 수정
     * @param dto
     * @return
     */
    @RequestMapping(value = "/mModify/send", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> MemberModify(@RequestBody MemberDTO dto) {
        log.info("asasaas :" + dto);
        String memberInfo = memberService.modify(dto);
        return new ResponseEntity<>(memberInfo, HttpStatus.OK);
    }

    //소셜 로그인 후 회원정보수정
//    @RequestMapping(value = "/modify", method = RequestMethod.POST,
//            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> modify(@RequestBody MemberDTO memberDTO) {
//        String email = memberService.modify(memberDTO);
//        log.info("postsDTO : {}", memberDTO);
//        log.info("List result : {}", email);
//        return new ResponseEntity<>(email, HttpStatus.OK);
//    }

    /**
     * 회원정보
     * @param dto
     * @return
     */
    @RequestMapping(value = "/mList", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional> mList(@RequestBody MemberDTO dto) {
        log.info("asasaas :" + dto);
        Optional email = memberService.mList(dto);
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    /**
     * 회원정보 수정 전 패스워드 검증
     * @param dto
     * @return
     */
    @RequestMapping(value = "/passCheck", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody boolean checkPw(@RequestBody MemberDTO dto){
        log.info("dto : {} ", dto);
        log.info("checkPw 진입");
        Long id = dto.getId();

        return memberService.checkPass(id, dto.getPassword());
    }


    /**
     * 회원가입 이메일 검증
     * @param email
     * @return
     */
    @RequestMapping(value = "/passFind", method = RequestMethod.GET,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, Boolean> passFind(String email){
        log.info("pw_find-----------------" + email);
        Map<String,Boolean> json = new HashMap<>();
        boolean pwFindCheck = memberService.userEmailCheck(email);

        System.out.println(pwFindCheck);
        json.put("check", pwFindCheck);
        return json;
    }

    /**
     * 등록된 이메일로 임시비밀번호를 발송
     * @param mailDTO
     * @return
     */
    @RequestMapping(value = "/passFind/send", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MailDTO> sendEmail(@RequestBody MailDTO mailDTO){
        MailDTO dto = sendEmailService.createMailAndChangePassword(mailDTO.getEmail());
        log.info("MailDTO : "+ dto);
        sendEmailService.mailSend(dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * 회원 비밀번호 찾기 후 변경
     * @param memberDTO
     * @return
     */
    @RequestMapping(value = "/passFind/passChange", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> passChange(@RequestBody MemberDTO memberDTO) {
        log.info("memberDTO {}", memberDTO);
        String newPass = memberService.passChange(memberDTO);
        return new ResponseEntity<>(newPass,HttpStatus.OK);
    }

    /**
     * 회원 비밀번호 찾기 이메일 검증
     * @param memberDTO
     * @return
     */
    @RequestMapping(value = "/passFind/email", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional> passEmail(@RequestBody MemberDTO memberDTO) {
        log.info("memberDTO : {}", memberDTO);
            Optional findEmail = memberService.findByEmail(memberDTO);
        return new ResponseEntity<>(findEmail,HttpStatus.OK);
    }

    /**
     * 회원가입 이메일 인증번호 발송
     * @param mailDTO
     * @return
     */
    @RequestMapping(value = "/join/emailCheck", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MailDTO> sendEmailCheck(@RequestBody MailDTO mailDTO){
        MailDTO dto = sendEmailService.createMail(mailDTO.getEmail());
        log.info("sendEmailCheck : "+ dto);
        sendEmailService.mailSend(dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * 회원가입 이메일 중복 검증
     * @param dto
     * @return
     */
    @RequestMapping(value = "/emailVali", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, Boolean> validateEmail(@RequestBody MemberDTO dto){
        log.info("memberDTO : {}", dto);
        Map<String,Boolean> json = new HashMap<>();
        boolean valiEmail = memberService.userEmailCheck(dto.getEmail());
        json.put("validate", valiEmail);
        log.info("json : " + json);
        return json;
    }

    /**
     * 회원가입 닉네임 중복 검증
     * @param
     * @return
     */
    @RequestMapping(value = "/nickVali", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> validateNickname(@RequestBody Map<String, Object> mapObj){
        String nickname = mapObj.get("nickname").toString();
        MemberDTO dto = memberService.userNicknameCheck(nickname);

        Map<String, Long> mapForResult = new HashMap<>();
        mapForResult.put("result", (dto == null)?0L:1L);

        return new ResponseEntity<Map<String, Long>>(mapForResult, HttpStatus.OK);
    }

    /**
     * 회원 이메일 찾기 전화번호 검증
     * @param dto
     * @return
     */
    @RequestMapping(value = "/phoneCheck", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, Boolean> emailPass(@RequestBody MemberDTO dto) {
        Map<String,Boolean> json = new HashMap<>();
        boolean valiPhone = memberService.findByPhone(dto.getPhone());
        json.put("validate", valiPhone);
        log.info("json : " + json);
        return json;
    }

    /**
     * 장바구니 리스트 출력
     */
    @RequestMapping(value = "/cartList", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResultDTO<CartDTO, Cart>> cartList(@RequestBody PageRequestDTO dto) {
        PageResultDTO<CartDTO,Cart> result = cartService.cartList(dto);
        log.info("CartDTO : {}", dto);
        log.info("List result : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 휴대폰번호 인증
     * @param
     * @return
     */
    @RequestMapping(value = "/sendSMS", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailFindDTO> sendSMS(@RequestBody EmailFindDTO dto) {
        Random rand = new Random();
        System.out.println("PHONE : " + dto);
        EmailFindDTO emailFind2 = sendEmailService.certifiedPhoneNumber(dto.getPhone());

        log.info("crn : ", emailFind2);
        return new ResponseEntity<>(emailFind2, HttpStatus.OK);
    }

    /**
     * 작성자 조회
     */
    @RequestMapping(value = "/DetailName", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional> DetailName(@RequestBody PostsDTO dto) {
        log.info("DetailName DTO :" + dto);
        Optional findById = memberService.DetailName(dto);
        return new ResponseEntity<>(findById, HttpStatus.OK);
    }

    /**
     * 내 정보 수정
     * @param memberDTO
     * @return
     */
    @RequestMapping(value = "/profileChange", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> profileChange(@RequestBody MemberDTO memberDTO) {
        log.info("memberDTO {}", memberDTO);
        String newProfile = memberService.profileChange(memberDTO);
        return new ResponseEntity<>(newProfile, HttpStatus.OK);
    }

    /**
     * 장바구니 선택한 항목 삭제
     * @param a
     * @return
     */
    @RequestMapping(value = "/cart/delete", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> cartDelete(@RequestBody int[] a) {
        log.info("===================================================");
        int id = 0;
        for (int i = 0; i < a.length; i++) {
            log.info(a[i]);
            if(a[i] != 0) {
                id = cartService.delete(a[i]);
            }
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @RequestMapping(value = "/phoneVali", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String, Boolean> phoneCheck(@RequestBody MemberDTO dto){
        log.info("memberDTO : {}", dto);
        Map<String,Boolean> json = new HashMap<>();
        boolean phoneCheck = memberService.findByPhone(dto.getPhone());
        json.put("validate", phoneCheck);
        log.info("json : " + json);
        return json;
    }

    /**
     * 관리자 전체 회원 조회
     * @return
     */
    @RequestMapping(value = "/adminMem", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, Object>> getAllUser(){
        HashMap<String, Object> result = memberService.getAllUser();
        log.info("List result : {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 관리자 회원 검색
     */
    @RequestMapping(value = "/searchMember", method = RequestMethod.POST,
            consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HashMap<String, Object>> MemberSearch(@RequestBody search vo) {
        log.info("------------------------------------memberSearch--------------------");
        log.info(vo);
        return new ResponseEntity<>(memberService.getMemberSearch(vo), HttpStatus.OK);
    }
}


