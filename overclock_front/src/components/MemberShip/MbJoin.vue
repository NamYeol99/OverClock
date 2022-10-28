<template>
  <div class="input-form2 col-md-12 mx-auto">
    <h4 class="mb-3">개인 회원가입</h4>
    <form class="validation-form" @submit.prevent>
      <div class="row">
        <div class="col-md-6 mb-3">
          <label for="name"></label>
          <input type="text" required autofocus class="form-control" v-model="state.name" id="name" placeholder="이름">
        </div>
        <div class="col-md-6 mb-3">
          <label for="nickname"></label>
          <input type="text" required autofocus class="form-control" v-model="state.nickname" id="nickname"
            placeholder="닉네임" @keyup="nickCheckHandler">
          {{ state.nickCheck }}
        </div>
      </div>
      <div class="mb-3">
        <label for="email"></label>
        <input type="email" class="form-control" v-model="state.email" id="email" placeholder="이메일">
      </div>
      <button @click="emailVali()" class="btn btn-outline-primary w-100" v-if="(state.ch2 == 0)">
        이메일 중복 확인
      </button>
      <button @click="emailCheck()" class="btn btn-outline-primary w-100 mt-3" v-if="(state.ch == 1)">
        인증번호 전송
      </button>
      <EmailCheck v-if="(state.change == 1)" :keys="state.keys" :email="state.email" />
      <div class="mb-3">
        <label for="password"></label>
        <input type="password" class="form-control" v-model="state.password" id="password" placeholder="비밀번호">

      </div>
      <div class="mb-3">
        <label for="repassword"></label>
        <input type="password" class="form-control" v-model="state.repassword" id="repassword" placeholder="비밀번호 확인">
      </div>
      <div class="mb-3">
        <label for="phone"></label>
        <input type="text" class="form-control" v-model="state.phone" id="phone" placeholder="전화번호 예) 01080094125"
          @blur="phoneCheck">
        {{ state.phoneCheck }}
      </div>
      <div class="mb-3">
        <label for="city"></label>
        <input type="text" class="form-control" v-model="state.city" id="city" placeholder="도시 예) 부산광역시 사하구">
      </div>
      <div class="mb-3">
        <label for="street"></label>
        <input type="text" class="form-control" v-model="state.street" id="street" placeholder="도로명 예) 낙동대로123번길">
      </div>
      <div class="mb-3">
        <label for="zipcode"></label>
        <input type="text" class="form-control" v-model="state.zipcode" id="zipcode" placeholder="우편번호 예) 12345">
      </div>

      <hr class="mb-4">
      <div class="custom-control custom-checkbox">
        <input type="checkbox" class="custom-control-input" v-model="state.aggrement" id="aggrement">
        <label class="custom-control-label" for="aggrement">개인정보 수집 및 이용에 동의합니다.</label>
      </div>
      <div class="mb-4"></div>
      <button class="btn btn-primary btn-lg btn-block" type="submit" @click="joinHandler">가입 완료</button>
    </form>
  </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import axios from 'axios'
import router from '@/router'
import store from '@/store'
import EmailCheck from './EmailCheck.vue'
export default {
  name: 'ToJoin',
  setup() {

    const state = reactive({
      form: {
        id: "1",
        email: "",
        password: "",
        role: "2",
      },
      id: '',
      email: '',
      password: '',
      repassword: '',
      name: '',
      nickname: '',
      phone: '',
      city: '',
      street: '',
      zipcode: '',
      change: 0,
      keys: '',
      emailVal: '',
      ch: 0,
      ch2: 0
    })

    const emailCheck = () => {

      state.change = 1;
      if (state.email === "") {
        alert("이메일을 입력해주세요");
        state.email.value.focus();
        return false;
      }
      const url = "./api/passFind";
      const headers = {
        "Content-Type": "application/json",
      };
      console.log("---------------------");
      console.log(state.email);
      const body = { email: state.email };
      axios.get(url, body, { headers }).then(function (res) {
        console.log(res);
        if (res.data.email === state.email) {
          alert("이미 존재하는 이메일입니다.")
          state.change = 0;
        } else if (res.data != "" || res.data.email != state.email) {
          state.ch2 = 1;
          console.log(typeof (res.data));
          alert("입력하신 이메일로 인증번호가 발송되었습니다.");
          document.getElementById('email').readOnly = true;
          axios.post("./api/join/emailCheck", body, { headers }).then(function (res) {
            console.log("---------------------------");
            console.log(res);
            console.log(res.data.keys);
            state.keys = res.data.keys;
            state.emailVal = res.data.email;
          });
          state.ch = 0;
        } else {
          alert("존재하지 않는 이메일입니다");
        }
      });
    }

    const asd = async () => {
      alert("asd");
      const url = "./member/login"
      const headers = { "Content-Type": "application/json; charset=utf-8;" }
      const body = { id: state.form.id, email: state.form.email, password: state.form.password, role: state.form.role };
      await axios.post(url, body, { headers }).then(function (res) {
        store.commit("setToken", res.data.token);
        store.commit("setId", state.form.id);
        store.commit("setEmail", res.data.email);
        store.commit("setRole", "2")
        console.log(res.data);
        alert("로그인되었습니다.");
      })
    }
    const nickCheckHandler = async () => {
      const url = './api/nickVali'
      const headers = { "Content-Type": "application/json" }
      const body = { nickname: state.nickname }
      const response = await axios.post(url, body, { headers })
      if (response.status === 200) {
        state.nickCheck = (response.data.result === 1) ? "사용불가" : "사용가능"
      } else {
        state.nickCheck = "중복 확인"
      }
    }

    const emailVali = async () => {
      const url = "./api/emailVali"
      const headers = { "Content-Type": "application/json; charset=utf-8;" }
      console.log(state.email)
      console.log("===============")
      const body = { email: state.email };
      console.log(state.email);
      await axios.post(url, body, { headers }).then(function (res) {
        if (res.data.validate === true) {
          alert("이미 존재하는 이메일입니다.")
          return false;
        } else if (state.email === '') {
          alert('이메일을 입력해주세요');
          state.email.value.focus();
          return false;
        } else if (!(state.email.includes("@") && state.email.includes("."))) {
          alert('이메일 양식이 맞지 않습니다.');
          state.email.value.focus(); return false;
        } else (res.data.validate === false)
        alert("가입 가능한 이메일입니다.")
        state.ch = 1
      })
    }

    const phoneCheck = async () => {

      const url = './api/phoneVali'
      const headers = { "Content-Type": "application/json" }
      const body = { phone: state.phone }
      await axios.post(url, body, { headers }).then(function (res) {
        if (res.data.validate === true) {
          state.phoneCheck = "사용불가"
        } else if (res.data.validate === false && state.phone.length >= 10) {
          state.phoneCheck = "사용가능"
        }
      })
    }

    const joinHandler = async () => {
      const url = './api/memberRegister'
      const headers = {
        "Content-Type": "application/json",
      }
      const body = {
        id: state.id,
        email: state.email,
        password: state.password,
        name: state.name,
        phone: state.phone,
        city: state.city,
        street: state.street,
        zipcode: state.zipcode,
        nickname: state.nickname,
      }

      var n_RegExp = /^[가-힣]{2,15}$/;

      if (state.name === '') {
        alert('이름을 입력해주세요');
        state.name.value.focus(); return false;
      } else if (state.name.length >= 8) {
        alert('이름은 8자 이하로 입력해주세요')
        state.name = null;
        state.name.value.focus(); return false;
      } else if (!n_RegExp.test(state.name)) {
        alert("특수문자,영어,숫자는 사용할수 없습니다. 한글만 입력하여주세요.");
        state.name = null;
        state.name.value.focus(); return false;
      } else if (state.nickname === '') {
        alert('닉네임을 입력해주세요');
        state.nickname.value.focus(); return false;
      } else if (state.nickname.length >= 8) {
        alert('닉네임은 8자 이하로 입력해주세요')
        state.nickname = null;
        state.nickname.value.focus(); return false;
      } else if (state.email === '') {
        alert('이메일을 입력해주세요');
        state.email.value.focus(); return false;
      } else if (!(state.email.includes("@") && state.email.includes("."))) {
        alert('이메일 양식이 맞지 않습니다.');
        state.email = null;
        state.email.value.focus(); return false;
      } else if (state.password === '') {
        alert('비밀번호를 입력해주세요');
        state.password.value.focus(); return false;
      } else if (state.repassword === '') {
        alert('비밀번호를 입력해주세요');
        state.repassword.value.focus(); return false;
      } else if (state.phone === '') {
        alert('전화번호를 입력해주세요');
        state.phone.value.focus(); return false;
      } else if (state.phone.length <= 10) {
        alert('전화번호는 10자 이상으로 입력해주세요')
        state.phone = null;
        state.phone.value.focus(); return false;
      } else if (state.city === '') {
        alert('도시명을 입력해주세요');
        state.city.value.focus(); return false;
      } else if (state.street === '') {
        alert('도로명주소를 입력해주세요');
        state.street.value.focus(); return false;
      } else if (state.zipcode === '') {
        alert('우편번호를 입력해주세요');
        state.zipcode.value.focus(); return false;
      } else if (state.password != state.repassword) {
        alert('비밀번호가 일치하지 않습니다. 다시 입력해주세요');
        state.password.value.value = '';
        state.repassword.value.value = '';
        state.password.value.focus(); return false;
      }

      console.log(store.state.email);
      if (store.state.email === 0) {
        return false;
      } else {
        const response = await axios.post(url, body, { headers })
        // console.log(response.data)
        if (response.status === 200) {
          alert('회원가입이 되었습니다.');
        } else {
          alert('회원가입에 실패하였습니다.')
        }
        router.push({ name: "Login" });
      }
    }
    return { joinHandler, state, emailCheck, emailVali, asd, nickCheckHandler, phoneCheck }
  },
  components: { EmailCheck }
};
</script>

<style scoped>
.container2 {
  height: 100vh;
}

.input-form2 {
  text-align: center;
  padding: 32px;

  /* margin-top: 45px; */

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