<template>
  <div>
    <section id="hero" class="d-flex align-items-center justify-content-center">
      <div class="container" data-aos="fade-up">
        <div
          class="row justify-content-center"
          data-aos="fade-up"
          data-aos-delay="150"
        >
          <div class="col-xl-6 col-lg-8">
            <h1>Powerful Digital Hardware With OverClock<span>.</span></h1>
            <h2>우리는 최고의 상품을 최적의 가격에 판매합니다</h2>
          </div>
        </div>
      </div>
    </section>
    <!-- End Hero -->


    <main id="main">
      <section class="ftco-section ftco-cart">
        <div class="container">
          <div class="row">
            <div class="col-md-12 ftco-animate">
              <div class="cart-list">
                <table class="table">
                  <thead class="thead-primary">
                    <tr class="text-center iii">
                      <th>글 번호</th>
                      <th>사진</th>
                      <th>제목</th>
                      <th>작성일</th>
                      <th>조회수</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr class="text-center" v-for="(list, i) in state.dtoList" :key="(list, i)">
                        <td class="price">{{ list.id }} </td>

                        <td>
                          <img v-bind:src="state.img[i]" />
                        </td>
                      
                        <td class="product-name">
                          <h3>
                            <a :href="'./usedDetail?id=' + list.id" @click="Join(list,i)"> {{ list.title }} </a>
                          </h3>
                        </td>

                        <td class="price">{{ list.regDate }}</td>
                        <td class="price">{{ list.viewCount }}</td>
                      </tr>
                    <!-- END TR-->
                  </tbody>
                </table>
                <form class="searching-area d-flex align-items-center gap-1 w-50" @submit.prevent="searchingAxios()">
          <label for="searching"><i class="bi bi-search"></i></label>
          <input id="searching" v-model="search.context" type="text" class="form-control border-0 bg-white" @submit="searchingAxios()">
        </form>
	         </div>
           <router-link to="/UsedRegister" class="btn btn-primary"> 글쓰기</router-link>
            </div><br>
            
            <div class="page">
              <ul class="pagination">
                <li class="page-item"><a class="page-link" @click="getUserList(state.page-1)" v-if="state.page!=1">Prev</a></li>
                <li :class="state.page == page?'page-item active':'page-item'" v-for="page in state.pageList" :key="page"><a class="page-link" @click="getUserList(page)">{{page}}</a></li>
                <li class="page-item" ><a class="page-link" @click="getUserList(state.page+1)" v-if="state.page!=state.totalPage">Next</a></li>
              </ul>
            </div>
          </div>
        </div>
	</section>
</main>
  </div>
  <Contact/>
</template>

<script>
import { reactive } from "@vue/reactivity";
import axios from "axios";
import Contact from "@/components/Contact.vue";
import { useRouter } from "vue-router";
import { useMeta } from "vue-meta";
import { useStore } from 'vuex';
export default {
    name: "ToUsed",
    setup() {
      const store = useStore();

      const router = useRouter()

      let search = reactive({
      context:"",
    })

    function searchingAxios(){
      if (search.context.trim().length == 0){
        return
      }

      async function routing (){
        await router.push(`/search?cards=${search.context}&postsType=${state.partsType}`);
        await router.go(0);
        // console.log("이동(app)")
      }
    routing();
    }
    const { meta } = useMeta({
                title:  ':: OverClock',
            })
      const state = reactive({
        id: "",
      upResult: "",
      img: [],
	    dtoList: [],
      end: null,
      next: null,
      page: null,
      pageList: null,
      prev: null,
      size: null,
      start: null,
      totalPage: null,
      partsType: "used",
          price: [],

      });

      const url = "./api/getlist";
      const headers = {
        "Content-Type": "application/json; charset=utf-8",
      "Authorization": store.state.token,
      "id": store.state.id
      };
      function getUserList(page) {
          axios.post(url, { page: page, category: "used" }, { headers })
              .then(function (res) {
              // console.log(page + "asdasdasd");
              // console.log(res);
              state.dtoList = res.data.dtoList,
                  state.end = res.data.end,
                  state.next = res.data.next,
                  state.page = res.data.page,
                  state.pageList = res.data.pageList,
                  state.prev = res.data.prev,
                  state.size = res.data.size,
                  state.start = res.data.start,
                  state.totalPage = res.data.totalPage;
          });
      }
      axios.post(url, { page: 1, category: "used" }, { headers })
          .then(function (res) {
          console.log(res);
          state.dtoList = res.data.dtoList,
              state.end = res.data.end,
              state.next = res.data.next,
              state.page = res.data.page,
              state.pageList = res.data.pageList,
              state.prev = res.data.prev,
              state.size = res.data.size,
              state.start = res.data.start,
              state.totalPage = res.data.totalPage;
              for (let i = 0; i < state.dtoList.length; i++) {
                console.log(search.context);
                if(search.context == state.dtoList[i].title){
                  store.state.img[i] = state.dtoList[i].imageDTOList[0]
                  console.log(i + "번쨰");
                  console.log(store.state.img[i]);
                }
              }
          showResult(res.data);
      });
      const showResult = async (arr) => {
          const displayUrl = "/display";
          const url = `http://localhost:9090${displayUrl}`;
          let str2 = "";
          for (let i = 0; i < arr.dtoList.length; i++) {
              str2 = `${url}?fileName=${arr.dtoList[i].imageDTOList[0].thumbnailURL}`;
              state.img[i] = str2;
          }
      };
      const body = {
    category:"used"
  }

  axios.post("./api/partsItemList", body, {headers}).then(function(res){
    store.state.price = res.data[0].price;
  })

  function Join(list,i){
    store.commit('setdtoList', ...[list]);
    store.commit("setPrice", ...[state.price[i]]);

      //조회수 처리
      const url2 = `./api/read/${list.id}`;
	    const headers2 = {
	      "Content-Type": "application/json; charset=utf-8"
	    };
      
      axios.get(url2, {page: 1, category: "used" }, { headers2 }).then(function(){

      })
    }

      return { searchingAxios, search, state, getUserList, meta, Join };
  },
  components: { Contact }
};
</script>

<style scoped>
	.btn-primary{
		float: right;
	}
	.pagination{
		width: 100px;
		margin: auto;
	}
	.table tbody tr td{
		padding: 10px;
	}
  .text-center a{
    display:block;
    text-decoration-line: none;
    width: 100%;
    height: 100%;
  }
  .text-center a:hover{
    background-color: #a0d4ff;
  }
.product-name{
  width:100%;
    line-height: 2.5em;
  }
  

	#hero:before{
		height: 500px;
	}
	#hero{
		height: inherit;
	}
</style>
