<template>
  
  <div class="searchlist">
<!--  <div class="all">-->
    <!-- 검색필터 -->
    <div>
    <form class="searching-area d-flex align-items-center gap-1 w-50" @submit.prevent="searchingAxios()">
          <label for="searching"><i class="bi bi-search btn btn-primary"></i></label>
          <input id="searching" type="text" v-model="search.context" class="form-control bg-white" @submit="searchingAxios()">
        </form>
      </div>
    <!-- 검색카드 -->
    <div class="mb-4 mt-5 ms-4 ps-1 text-center" v-if="state.notSearchWord">
      <h1>검색 결과가 없습니다</h1>
    </div>
    <hr>
      <Cards 
        :card="state.cards" :imgUrl="state.imgUrl">
      </Cards>
    <div class="page">
      <ul class="pagination">
        <li class="page-item"><a class="page-link" @click="getUserList(state.page-1)" v-if="state.page!=1">Prev</a></li>
        <li :class="state.page == page?'page-item active':'page-item'" v-for="page in state.pageList" :key="page"><a class="page-link" @click="getUserList(page)">{{page}}</a></li>
        <li class="page-item" ><a class="page-link" @click="getUserList(state.page+1)" v-if="state.page!=state.totalPage">Next</a></li>
      </ul>
    </div>  
  </div>
  <!--  </div>-->
</template>

<script>
// @ is an alias to /src

import Cards from "@/components/MainCards.vue";
// import store from "@/store";
import {reactive} from "@vue/reactivity";
import axios from "axios";
import {useRouter} from "vue-router/dist/vue-router";

export default {
  name: "SearchList",
  components: {
    Cards,
  },
  setup() {
    let search = reactive({
      context:"",
    }) 
    const state = reactive({
      imgName : [],
      imgPath: [],
      imgUuid : [],
      imgUrl: [],
      cards: [],
      notSearchWord : false,
      reqPage: 0,
      pageTotalCount: 0,
      a:"",
      end: null,
      next: null,
      page: null,
      pageList: null,
      prev: null,
      size: null,
      start: null,
      totalPage: null
    })

    const router = useRouter()

    let searchword = new URLSearchParams(window.location.search).get("cards");
    let postsType = new URLSearchParams(window.location.search).get("postsType");
  


    // let search = reactive({
    //   context:"",
    // })

    function searchingAxios(){
      if (search.context.trim().length == 0){
        return
      }
      async function routing (){
        await router.push(`/search?cards=${search.context}&postsType=${postsType}`)
        // await router.go(0);
        console.log("이동(app)")
      }
    routing();
    }

    //getCards
    async function getCardsInformation(){
      const url= "./api/search"
      const headers = {
        "Content-Type": "application/json; charset=utf-8",
      }
      const body = {
        search: searchword,
        postsType: postsType,
        reqPage: state.reqPage
      }
      // console.log(body)
      // if (state.reqPage == 0) state.cards = null;

      await axios.post(url, body, {headers}).then(function (res){
        const url = `http://localhost:9090/display`;
        // state.pageTotalCount = res.data.pageTotalCount;
        // console.log("======================+===")
        // console.log(res.data);
        let str = "";
        for (let i = 0; i < res.data.articles.length; i++) {
          state.imgName[i] = res.data.articles[i].imgName;
          state.imgPath[i] = res.data.articles[i].imgPath;
          state.imgUuid[i] = res.data.articles[i].imgUuid;
          str = state.imgPath[i] + "/s_" +state.imgUuid[i] + "_" + state.imgName[i];
          state.imgUrl[i] = `${url}?fileName=${str}`;
        }
        // console.log(state.imgName);
        // console.log(state.imgPath);
        // console.log(state.imgUuid);
        // console.log("===============");
        // console.log(state.imgUrl);
        
        if (body.reqPage == 0) {
          console.log("===========");
          console.log("===========");
          console.log("===========");
          // console.log(res.data)
          state.cards = res.data.articles;
          console.log(state.cards);
        }
      })
    }

    // async function view (){
    //   state.reqPage=0
    //   await router.push(`${store.state.axiosLink}/search?cards=${searchword}&order=view`);
    //   getCardsInformation()
    // }

    // async function latest(){
    //   state.reqPage=0
    //   await router.push(`${store.state.axiosLink}/search?cards=${searchword}&order=new`);
    //   getCardsInformation()
    // }

    // async function star(){
    //   state.reqPage=0
    //   await router.push(`${store.state.axiosLink}/search?cards=${searchword}&order=star`);
    //   getCardsInformation()
    // }

    // async function like(){
    //   state.reqPage=0
    //   await router.push(`${store.state.axiosLink}/search?cards=${searchword}&order=like`);
    //   getCardsInformation()
    // }

    // function getMoreCards(){
    //   state.reqPage+=1
    //   getCardsInformation()
    // }

    // window.addEventListener("scroll", windowSize)
    // function windowSize(){
    //   if(window.innerHeight+window.scrollY>=document.body.offsetHeight){
    //     getMoreCards()
    //   }
    // }

      getCardsInformation()

  return {state, searchingAxios ,search}
  },
};
</script>

<style scoped>
.searching-area{
  margin-top: 100px;
}
.pagination{
		width: 100px;
		margin: auto;
	}
  .page{
    margin-top: 30px;
  }
  #searching{
    border: 1px solid black;
  }
</style>