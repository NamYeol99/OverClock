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
        :card="state.cards">
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

import Cards from "@/components/MainCards2.vue";
import {reactive} from "@vue/reactivity";
import axios from "axios";

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

    let searchword = new URLSearchParams(window.location.search).get("cards");

    //getCards
    function getCardsInformation(){
      const url= "./api/searchMember"
      const headers = {
        "Content-Type": "application/json; charset=utf-8",
      }
      const body = {
        search: searchword,
        reqPage: state.reqPage
      }

      axios.post(url, body, {headers}).then(function (res){
        // if (body.reqPage == 0) {
          console.log("===========");
          console.log("===========");
          console.log("===========");
          console.log(res.data)
          state.cards = res.data.members;
          console.log(state.cards);
        // }
      })
    }
      getCardsInformation()

  return {state, search}
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