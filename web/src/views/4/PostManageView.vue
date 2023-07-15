<template>
    <div>
        <el-row>
            <p style="font-size: 25px; color: #666666">岗位管理</p>
        </el-row>
    </div>

    <el-select v-model="sex" placeholder="请选择性别要求">
      <el-option
          v-for="item in options_sex"
          :key="item.value"
          :label="item.label"
          :value="item.value">
      </el-option>
    </el-select>
    <el-input
        v-model="posname"
        placeholder="请输入岗位名称">
    </el-input>
    <el-input
        v-model="possalary"
        placeholder="请输入薪资待遇">
    </el-input>
    <el-input
        v-model="title"
        placeholder="请输入学历要求">
    </el-input>
    <el-input
        v-model="major"
        placeholder="请输入专业要求">
    </el-input>
    <el-input
        v-model="workcity"
        placeholder="请输工作城市要求">
    </el-input>
    <el-input
        v-model="worktime"
        placeholder="请输工作年限要求">
    </el-input>
    <el-input
        type="textarea"
        :rows="2"
        placeholder="请输入岗位的工作描述"
        v-model="posdescription">
    </el-input>
    <el-input
        type="textarea"
        :rows="2"
        placeholder="请输入岗位的工作技能要求"
        v-model="posskill">
    </el-input>
    <el-input
        type="textarea"
        :rows="2"
        placeholder="请输入岗位的职责"
        v-model="posresponsibility">
    </el-input>
    <el-button
        type="primary"
        @click="pos_information">
        提交岗位信息
    </el-button>
</template>

<script>

import {ref} from 'vue';
import $ from "jquery";
// import router from "@/router";
import { useStore } from 'vuex';

export default {
    name: "PostManageView",

    setup(){
      const store = useStore();
      const options_sex = ref([{
        value: '男',
        label: '男'
      }, {
        value: '女',
        label: '女'
      }],)
      const sex = ref('')
      const posname = ref('');
      const possalary = ref('');
      const title = ref('');
      const major = ref('');
      const workcity = ref('');
      const worktime = ref('');
      const posdescription = ref('');
      const posskill = ref('');
      const posresponsibility = ref('');
      const error_message = ref('')
      const pos_information = () =>{
        console.log(sex.value)
        console.log(posresponsibility.value)
        $.ajax({
          url: store.state.httpUrl + "position/submit/",
          type: 'post',
          data: {
            sex: sex.value,
            posname: posname.value,
            possalary: possalary.value,
            title: title.value,
            major: major.value,
            workcity: workcity.value,
            posdescription: posdescription.value,
            workexp: posskill.value,
            projcetexp: posresponsibility.value,
          },
          success(resp) {
            if (resp.error_message === "success") {
                console.log("666")
            } else {
              error_message.value = resp.error_message;
            }
          },
          error(resp) {
            console.log(resp);
          }
        });
      }
      return{
        options_sex,
        sex,
        major,
        title,
        posname,
        possalary,
        workcity,
        worktime,
        posdescription,
        posskill,
        posresponsibility,
        error_message,
        pos_information
      }
    }

}

</script>

<style scoped>

</style>
