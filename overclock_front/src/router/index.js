import { createRouter, createWebHistory } from "vue-router"

import Main from "../components/Main.vue"

import AdminMain from "@/components/Admin/AdminMain.vue"
import AdminMem from "@/components/Admin/AdminMem.vue"

import Logout from "../components/MemberShip/Logout.vue"
import Login from "../components/MemberShip/Login.vue"
import Join from "@/components/MemberShip/Join.vue"

import MbLogin from "@/components/MemberShip/MbLogin.vue"
import MbJoin from "@/components/MemberShip/MbJoin.vue"

import Social from "@/components/MemberShip/Social.vue"

import CpLogin from "@/components/MemberShip/MbLogin.vue"
import CpJoin from "@/components/MemberShip/MbJoin.vue"

import Profil from "@/components/Profil.vue"

import EmailCheck from "@/components/MemberShip/EmailCheck"
import EmailFind from "@/components/MemberShip/EmailFind"
import PhoneCheck from "@/components/MemberShip/PhoneCheck"
import PassFind from "@/components/MemberShip/PassFind"
import PassCheck from "@/components/MemberShip/PassCheck"
import PassChange from "@/components/MemberShip/PassChange"
import PassChange2 from "@/components/MemberShip/PassChange2"
import ProfileChange from "@/components/MemberShip/ProfileChange"


//고객센터
import QnA from "@/components/QnA.vue"
import QnAPost from "@/components/QnAPost.vue"

// //결제
import PcPay from '@/components/Pay/PcPay.vue'
// import PayComplete from '@/components/Pay/PayComplete.vue'

//부품
import Parts from "../components/Posts/Parts/Parts.vue"
import PartsDetail from "@/components/Posts/Parts/PartsDetail.vue"
import PartsDetail2 from "@/components/Posts/Parts/PartsDetail2.vue"
import PartsRegister from "@/components/Posts/Parts/PartsRegister.vue"
import PartsModify from "@/components/Posts/Parts/PartsModify.vue"

//주변기기
import Peri from "@/components/Posts/Peripherals/Peripherals.vue"
import PeriDetail from "@/components/Posts/Peripherals/PeriDetail.vue"
import PeriRegister from "@/components/Posts/Peripherals/PeriRegister.vue"

//중고거래
import Used from "@/components/Posts/Used/Used.vue"
import UsedDetail from "@/components/Posts/Used/UsedDetail.vue"
import UsedRegister from "@/components/Posts/Used/UsedRegister.vue"

//자유게시판
import FreeDetail from "@/components/Posts/Free/FreeDetail.vue"
import Free from "@/components/Posts/Free/Free.vue"
import FreePost from "@/components/Posts/Free/FreePost.vue"
import FreeRePost from "@/components/Posts/Free/FreeRePost.vue"

//댓글
import CommentModify from "@/components/Posts/Comment/CommentModify.vue"

//검색
import SearchList from "@/components/SearchList.vue"
import MemSearchList from "@/components/MemSearchList.vue"

import Cart from "@/components/Cart.vue"
import Auction from "@/components/Auction.vue"




const routes = [
    {path:"/",name:"Main", component: Main}, 
    {path:"/parts",name:"Parts", component: Parts}, 
    {path:"/peri",name:"Peri", component: Peri},
    {path:"/used",name:"Used", component: Used},
    {path:"/partsdetail",name:"PartsDetail", component: PartsDetail, props: { newsletterPopup: false }}, 
    {path:"/partsdetail2",name:"PartsDetail2", component: PartsDetail2}, 
    {path:"/peridetail",name:"PeriDetail", component: PeriDetail, props: { newsletterPopup: false }}, 
    {path:"/useddetail",name:"UsedDetail", component: UsedDetail}, 
    {path:"/freedetail",name:"FreeDetail", component: FreeDetail}, 
    {path:"/free",name:"Free", component: Free}, 
    {path:"/freepost",name:"FreePost", component: FreePost}, 
    {path:"/freerepost",name:"FreeRePost", component: FreeRePost}, 
    {path:"/mblogin",name:"MbLogin", component: MbLogin}, 
    {path:"/mbjoin",name:"MbJoin", component: MbJoin},
    {path:"/cplogin",name:"CpLogin", component: CpLogin}, 
    {path:"/cpjoin",name:"CpJoin", component: CpJoin},
    {path:"/social",name:"Social", component: Social},
    {path:"/login",name:"Login", component: Login}, 
    {path:"/logout",name:"Logout", component: Logout}, 
    {path:"/join",name:"Join", component: Join},
    {path:"/qna",name:"QnA", component: QnA},
    {path:"/qnapost",name:"QnAPost", component: QnAPost},
    {path:"/profil",name:"Profil", component: Profil},
    {path:"/usedregister", name:"UsedRegister", component: UsedRegister},
    {path:"/periregister", name:"PeriRegister", component: PeriRegister},
    {path:"/partsregister", name:"PartsRegister", component: PartsRegister},
    {path:"/search", name: 'SearchList', component: SearchList},
    {path:"/searchMember", name: 'MemSearchList', component: MemSearchList},
    {path:"/passChange", name: "PassChange", component: PassChange},
    {path:"/passChange2", name: "PassChange2", component: PassChange2},
    {path:"/profileChange", name: "ProfileChange", component: ProfileChange},
    {path:"/cart", name: 'Cart', component: Cart},
    {path:"/auction", name: 'Auction', component: Auction},
    {path:"/emailCheck", name: "EmailCheck", component: EmailCheck},
    {path:"/emailFind", name: 'EmailFind', component: EmailFind},
    {path:"/passFind", name: 'PassFind', component: PassFind},
    {path:"/passCheck", name: 'PassCheck', component: PassCheck},
    {path:"/phoneCheck", name: 'PhoneCheck', component: PhoneCheck},
    {path:"/partsModify", name: 'PartsModify', component: PartsModify},
    {path:"/commentModify", name: 'CommentModify', component: CommentModify},
    {path:'/PcPay', name: 'PcPay', component: PcPay},
    {path:"/adminMain", name: 'AdminMain', component: AdminMain},
    {path:"/adminMem", name: 'AdminMem', component: AdminMem},
    ]
  
  // const router = createRouter({
  //   scrollBehavior() {
  //     // always scroll to top
  //     return { top: 0 }
  //   },
  //   history: createWebHistory(), routes
  // })
  const router = createRouter({
    history: createWebHistory(process.env.BASE_URL), routes
  })
  export default router
