<template>
     <button class="btn btn-primary mt-3 me-2 mb-3" @click="PaymentBtn">구매</button>
</template>

<script>
import { reactive } from 'vue';
import axios from 'axios';
const { IMP } = window;
export default {
  name: "ToTest",
  props: ["price","cartId","checkList"],
  setup(props){
    const state = reactive({
      cartId : props.cartId,
      cartName: [],
      asd : "",
    });
    console.log("=aspdlasd");
 
    document.cookie = "safeCookie1=foo; SameSite=Lax";
    document.cookie = "safeCookie2=foo";
    document.cookie = "crossCookie=bar; SameSite=None; Secure";
    function PaymentBtn(BuyerEmail,jQuery){
      for (let i = 0; i < props.checkList.length; i++) {
        if(props.checkList[i] == ""){
          state.cartName[i] = props.checkList[i].cartName;
        }
        console.log(state.cartName);

      }
      // for (let i = 0; i < state.cartName.length; i++) {
      //   if(state.cartName[i] != ""){
      //     state.cartName2 = state.cartName;
      //   }
      // }
      
      IMP.init("imp04806421");
      IMP.request_pay({ // param
        pg: "html5_inicis",
        pay_method: "card",
        merchant_uid: "ORD_" + new Date().getTime(),
        name: state.cartName[0], // 상품명
        amount:  props.price, // 가격
        buyer_email: "",
        buyer_name: "테스터",
      }, rsp => { // callback
        console.log(rsp);
        if (rsp.success) {
          const url2 = "/api/cart/delete"
          const headers = {
            "Content-Type": "application/json; charset=utf-8",
          };

          axios.post(url2, state.cartId, { headers }).then(function (res) {
            alert("결제완료")
            console.log(res);
          })
          jQuery.ajax({
            url: "{}", // 예: https://www.myservice.com/payments/complete
            method: "POST",
            headers: { "Content-Type": "application/json" },
            data: {
                imp_uid: rsp.imp_uid,
                merchant_uid: rsp.merchant_uid
            }
          }).done(function (data) {
            // 가맹점 서버 결제 API 성공시 로직
            console.log(data);
          })
          console.log("결제 성공");
        } else {
          console.log("결제 실패");
        }
      });
    }
    return {state,PaymentBtn}
  }
}
</script>


<style scoped>
  input[type="number"]::-webkit-outer-spin-button,
  input[type="number"]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
  input{ width:300px; border:0; border-bottom:1px #a8a8a8 solid; margin:0 10px 0; padding:0; height:40px; line-height:40px; outline: none; }
  .test{ display:flex; justify-content: center; align-items: center; height:100vh; }
  div{ width: 200px; height:40px; background-color:#ffffff; border:1px #a8a8a8 solid; color:black; display:flex; align-items: center; justify-content: center; cursor:pointer; }
</style>