<template>
    <section id="hero" class="d-flex align-items-center justify-content-center">
        <form class="form-floating input-form9 validation-form" @submit.prevent>
            <h5 class="mt-4">중고 거래 등록</h5>
            <div class="form-group">
                <label>제목</label>
                <input type="text" class="form-control" v-model="state.title" placeholder="Enter Title" required autofocus>
            </div>
            <div class="form-group">
                <label>상세내용</label>
                <textarea class="form-control" v-model="state.content" rows="5" name="content" required autofocus></textarea>
            </div>
            <div class="form-group">
                <label>가격</label>
                <input type="text" class="form-control" v-model="state.price" rows="5" name="price" required autofocus>
            </div>
            <div class="form-group">
                <label>수량</label>
                <input type="text" class="form-control" v-model="state.stock" name="stock" placeholder="stock" required autofocus>
            </div>
            <div class="box"></div>
            <FileUpload />

            <input type="hidden" class="form-control" v-model="state.type" name="type" placeholder="type" required autofocus><br>
            <button class="btn btn-primary btn7" @click="joinHandler">등록</button>
        </form>
    </section>
</template>

<script>
import { reactive } from '@vue/reactivity'
import axios from 'axios'
import { useRouter } from 'vue-router';
import FileUpload from '@/components/FileUpload.vue'
import store from '@/store';
export default {
    name: 'ToRegister',
    components: { FileUpload },
    setup() {
        const router = useRouter();
        const state = reactive({
            title: '',
            content: '',
            memberId: store.state.id,
            name: '',
            itemDetail: '',
            type: 'used',
            stock: '',
            price: '',
            imageDTOList: new Array(),
        })
        const joinHandler = async () => {
            let str = "";
            const liArr = document.querySelectorAll(".uploadResult ul li")
            for (let i = 0; i < liArr.length; i++) {
                const target = liArr[i];
                str += `
                <input type="hidden" name="imageDTOList[${i}].imgName" 
                value="${target.dataset.name}">
                <input type="hidden" name="imageDTOList[${i}].path" 
                value="${target.dataset.path}">
                <input type="hidden" name="imageDTOList[${i}].uuid" 
                value="${target.dataset.uuid}">          
                `
                const obj = {
                    imgName: target.dataset.name,
                    path: target.dataset.path,
                    uuid: target.dataset.uuid,
                }
                state.imageDTOList.push(obj)
            }
            document.querySelector(".box").innerHTML = str

            const url = './register/posting'
            const headers = {
                "Content-Type": "application/json",
                "Authorization": store.state.token

            }
            console.log("=================");
            console.log(store.state.token);
            const body = {
                title: state.title,
                content: state.content,
                memberId: state.memberId,
                imageDTOList: state.imageDTOList,
                itemDetail: state.itemDetail,
                partsType: state.type,
            }
            if (state.title === '') {
                alert('상품명을 입력해주세요');
                state.title.value.focus(); return false;
            } else if (state.content === '') {
                alert('내용을 입력해주세요');
                state.content.value.focus(); return false;
            } else if (state.price === '') {
                alert('가격을 입력해주세요');
                state.price.value.focus(); return false;
            } else if (state.stock === '') {
                alert('수량을 입력해주세요');
                state.stock.value.focus(); return false;
            } else if (liArr.length <= 0) {
                alert('이미지를 선택하세요')
                liArr.length.focus(); return false;
            }
            const response = await axios.post(url, body, { headers })

            if (response.status === 200) {
                alert('글 등록이 완료되었습니다')
            } else {
                alert('글 등록이 실패하였습니다.')
            }
            router.push({ name: "Used" })
        }

        return { state, joinHandler }
    }

}
</script>

<style scoped>
.input-form9 {

    text-align: center;

    max-width: 680px;
    max-height: 1200px;

    margin-top: 200px;
    padding: 32px;

    background: #fff;
    -webkit-border-radius: 10px;
    -moz-border-radius: 10px;
    border-radius: 10px;
}

.btn7 {
    margin-top: 0rem;
}

#hero {
    overflow: scroll;
}

#hero:before {
    height: 1200px;
}
</style>