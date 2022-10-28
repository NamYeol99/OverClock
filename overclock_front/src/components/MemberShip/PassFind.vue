<template>
  <section id="hero" class="d-flex align-items-center justify-content-center">
    <div class="container2" data-aos="fade-up">
      <div class="container">
        <div class="input-form2 col-md-12 mx-auto">

          <form class="d-flex flex-column validation-form" action="login" method="post">

            <span class="answer text-center" i-d-b-database="answerForRecovery1">이메일</span>
            <div class="input-group idsection px-5 my-2">
              <input class="form-control" type="text" name="id" id="id" required placeholder="email"
                v-model="state.email" />
            </div>
            <div class="px-5 my-4">
              <button type="button" class="btn btn-outline-primary w-100" @click="submit()">인증번호 발송</button>
            </div>
            <span class="answer text-center" i-d-b-database="answerForRecovery1">임시 비밀번호</span>
            <input class="form-control" type="text" name="pw" id="pw" required placeholder="임시 비밀번호"
              v-model="state.tempPassInput" />
            <div class="px-5 my-4">
              <button type="button" class="btn btn-outline-primary w-100" @click="passCheck()">임시 비밀번호 확인</button>
            </div>
          </form>
          <PassChange v-if="(state.change == 1)" :email="state.email" />
        </div>
      </div>
    </div>
  </section>
</template>
<script>
import { reactive } from "@vue/reactivity";
import axios from "axios";
import PassChange from "./PassChange.vue";
// import store from "@/store";

export default {
  setup() {
    const state = reactive({
      email: "",
      tempPass: "",
      tempPassInput: "",
      change: 0
    });
    const submit = () => {
      if (state.email === "") {
        alert("이메일을 입력해주세요");
        state.email.value.focus();
        return false;
      }
      // else if (!(state.email.includes("@") && state.email.includes("."))) {
      //   alert('이메일 양식이 맞지 않습니다.');
      //   email.value.focus();
      //   return false;
      // }
      const url = "./api/passFind/email";
      const headers = {
        "Content-Type": "application/json",
      };
      console.log("---------------------");
      console.log(state.email);
      const body = { email: state.email };
      axios.post(url, body, { headers }).then(function (res) {
        console.log("==========================");
        console.log(res.data);
        if (res.data != "" || res.data != state.email) {
          console.log(state.email);
          console.log(res.data);
          alert("입력하신 이메일로 임시 비밀번호가 발송되었습니다.");
          axios.post("./api/passFind/send", body, { headers }).then(function (res) {
            console.log("---------------------------");
            console.log(res);
            console.log(res.data.tempPass);
            state.tempPass = res.data.tempPass;
          });
        }
        else {
          alert("존재하지 않는 이메일입니다");
        }
      });
    };
    const passCheck = () => {
      if (state.tempPassInput === "" || state.tempPass !== state.tempPassInput) {
        alert("임시 비밀번호가 일치하지 않습니다");
        return false;
      }
      state.change = 1;
    };
    return { state, submit, passCheck };
  },
  components: { PassChange }
}
</script>
<style scoped>
.container2 {
  height: 100vh;
}

.input-form2 {
  text-align: center;
  padding: 32px;

  margin-top: 45px;

  background: #fff;
  -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
  border-radius: 10px;
}

#hero {
  overflow: scroll;

  max-height: 1100px;
}

#hero::before {
  height: 1100px;
}
</style>
  
