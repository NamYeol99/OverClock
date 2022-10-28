<template>
  <div>
    <div class="form-group centerz" >
      <input type="file" @change="fileChange" ref="fileInput" class="form-control" multiple />
      <label ref="fileLabel" class="custom-file-label" data-browse="Browse"></label>
    </div>
    <div class="uploadResult">
      <ul ref="upResult"></ul>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import {reactive, ref} from '@vue/reactivity'

export default {
  name: 'FileUpload',
  props: [  ],
  data() { return {} },
  setup() {
    const regex = new RegExp("(.*?).(exe|sh|zip|alz|tiff)$");
    const maxSize = 1024*1024*10; //10MB
    const state = reactive({
      fileInput   : '',
      fileLabel   : '',
      upResult    : '',
    })   
    const fileInput = ref('')
    const fileLabel = ref('')
    const upResult  = ref('')
 
    const checkExtension = (fileName, fileSize) => {
      if(fileSize > maxSize){
        alert('파일사이즈초과')
        return false;
      }
      if(regex.test(fileName)){
        alert('해당파일은 업로드 될 수 없습니다.')
        return false;
      }
      return true;
    }
    const fileChange = async() => {
      const fileName = fileInput.value.value.split("\\").pop() //맨끝만 나옴.
      console.log(fileName)
      fileLabel.value.innerHTML = (fileInput.value.files.length-1)==0?"":
                  fileName+" 외 "+(fileInput.value.files.length-1)+"개";

      var formData = new FormData();
      var files = fileInput.value.files;
      var appended = false;
      
      for(let i=0;i<files.length;i++){
        if(!checkExtension(files[i].name, files[i].size)) {
          fileLabel.value.innerHTML = ""
          this.value = ''
          return false;
        }
        formData.append("uploadFiles", files[i]);
        appended = true;
      }

      //전송할 파일이 없으면 여기서 끝.If there are no files to transfer, end here.
      if(!appended) return;
      
      //전송할 파일이 있으면 목록 보기.It there are files to transfer,show list.
      for(let value of formData.values()) console.log(value)
      
      const url = './api/uploadAjax/'
      await axios.post(url, formData, {
        headers: {
          "Content-Type" : "multipart/form-data",
          "process-data" : false
          },
      }).then(function(res){
        console.log(res.data)
        showResult(res.data);
      }).catch(function(err){
        console.log(err)
      })
    }
    const showResult = async(arr) => {
      const uploadUL = upResult.value;
      console.log("arr");
      console.log(arr);
      const displayUrl = '/overclock/display'
      const url = `http://localhost:9090${displayUrl}`
      let str = ""
      for(let i=0;i<arr.length;i++){
        str += `
        <li data-name='${arr[i].fileName}' data-path='${arr[i].folderPath}' 
            data-uuid='${arr[i].uuid}' data-file='${arr[i].imageURL}'>
          <div>
            <button type='button' class='removeBtn'>X</button>
            <br><img src='${url}?fileName=${arr[i].thumbnailURL}'>
          </div>
        </li>`
      }
      uploadUL.innerHTML = str;
      const removeBtns = document.querySelectorAll(".removeBtn");
      for (let i = 0; i < removeBtns.length; i++) {
        removeBtns[i].onclick = function(){
          const removeUrl = '/api/removeFile'
          const targetLI = this.parentElement.closest('li')
          const fileName = targetLI.dataset.file
          const headers = {
            "Content-Type" : "application/json"}

          axios({
            method: "POST",
            url: `${removeUrl}?fileName=${fileName}`,
            headers:headers,
          }).then( function(res){
            if(res.data === true) targetLI.remove();
          }).catch(function(err){console.log(err)});
        }
      }
    }
    return { state,fileInput,fileLabel,upResult,fileChange,checkExtension }
  },
}
</script>

<style>
.centerz {
  display: inline-block;width:500px;text-align: right;color: rgb(138, 156, 189);
  margin: 10px 0;
}
.uploadResult {
  width: 100%;
  background: gray;
  margin-top: 10px;
}
.uploadResult ul {
  display: flex;
  flex-flow: row;
  justify-content: center;
  align-items: center;
  vertical-align: top;
  overflow: auto;
}
.uploadResult ul li {
  list-style: none;
  padding: 10px;
  margin-left: 2em;
}
.uploadResult ul li img {
  width: 100px;
}
</style>
