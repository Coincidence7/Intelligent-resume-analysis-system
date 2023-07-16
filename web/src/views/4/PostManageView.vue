<template>
    <el-row>
        <p style="font-size: 25px; color: #666666">岗位管理</p>
    </el-row>
    <el-row>
<!--    岗位列表-->
        <el-col :span="7"  align="left">
            <el-row>
                <p style="font-size: 20px; color: #666666">岗位列表</p>
                <div class="flex-grow" />
                <el-button
                    type="info" plain
                    style="margin-top: 1rem"
                    :icon="Plus"
                    @click="postAddHandler">
                    新增岗位
                </el-button>
            </el-row>
            <br/>
            <el-table
                :data=postList
                style="width: 100%" :border="true"
                max-height=65vh
                :scrollbar-always-on="true"
                highlight-current-row
                @row-click="postSelectHandler">
                <el-table-column
                    prop="postId" label="序号"
                    width="100" align="right"
                    sortable column-key="index"/>
                <el-table-column
                    prop="postName" label="岗位名称"
                    align="left"/>
            </el-table>
        </el-col>
        <el-col :span="1"/>
<!--    岗位信息编辑-->
        <el-col :span="10" align="left" v-if="(postEditStatus!=='unselect')">
            <el-row>
                <p style="font-size: 20px; color: #666666">岗位信息编辑</p>
                <div class="flex-grow" />
                <el-button
                    type="primary" plain
                    style="margin-top: 1rem"
                    :icon="Check"
                    @click="postSubmitHandler">
                    提交岗位信息
                </el-button>
            </el-row>
            <br/>
            <el-form :model="postForm" label-width="5rem">
                <el-form-item label="岗位名称">
                    <el-input
                        v-model="postForm.postName"
                        :maxlength="20" show-word-limit
                        :clearable="true"
                        class="content"/>
                </el-form-item>
                <el-form-item label="岗位薪酬">
                    <el-row>
                        <el-input-number
                            v-model="postForm.postSalaryMin"
                            :min="0" :max="99999999"
                            class="content"
                            controls-position="right"
                            style="margin-left: 1rem; width:10rem"/>
                        <span style="margin-left: 1rem">-</span>
                        <el-input-number
                            v-model="postForm.postSalaryMax"
                            :min="postForm.postSalaryMin" :max="99999999"
                            class="content"
                            controls-position="right"
                            style="width:10rem"/>
                    </el-row>
                </el-form-item>
                <el-form-item label="岗位技能">
                    <el-input
                        v-model="postForm.postSkill"
                        :rows="5"
                        :maxlength="200" show-word-limit
                        class="content" type="textarea"
                        placeholder="请输入岗位所需技能"/>
                </el-form-item>
                <el-form-item label="岗位职责">
                    <el-input
                        v-model="postForm.postResponsibility"
                        :rows="3"
                        :maxlength="150"
                        show-word-limit
                        class="content"
                        type="textarea"
                        placeholder="请输入岗位职责"/>
                </el-form-item>
                <el-divider />
                <el-form-item label="性别要求">
                    <el-radio-group v-model="postForm.sexReq" class="content">
                        <el-radio label="不限">不限性别</el-radio>
                        <el-radio label="男">男</el-radio>
                        <el-radio label="女">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="学历要求">
                    <el-select
                        v-model="postForm.titleReq"
                        placeholder="最低学历要求"
                        class="content">
                        <el-option-group
                            v-for="group in formDegreeOption"
                            :key="group.value"
                            :label="group.label">
                            <el-option
                                v-for="item in group.options"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"/>
                        </el-option-group>
                    </el-select>
                </el-form-item>
                <el-form-item label="专业要求">
                    <el-input
                        v-model="postForm.majorReq"
                        :maxlength="20" show-word-limit
                        :clearable="true"
                        class="content"/>
                </el-form-item>
                <el-form-item label="城市要求">
                    <el-select
                        v-model="postForm.workCity"
                        placeholder="工作地区要求"
                        class="content"
                        filterable>
                        <el-option-group
                            v-for="group in formCityOption"
                            :key="group.value"
                            :label="group.label">
                            <el-option
                                v-for="item in group.options"
                                :key="item.value"
                                :value="item.value"/>
                        </el-option-group>
                    </el-select>
                </el-form-item>
                <el-form-item label="经验要求">
                    <el-row>
                        <el-switch
                            v-model="postForm.isTimeReq"
                            inline-prompt
                            :active-icon="Check"
                            :inactive-icon="Close"
                            class="content"
                        />
                        <el-input-number
                            v-model="postForm.workTime"
                            :disabled="!postForm.isTimeReq"
                            :min="0"
                            :max="100"
                            controls-position="right"
                            style="margin-left: 1rem; width:7rem"
                        />
                        <span style="margin-left: 8px; color: #666666">(年)</span>
                    </el-row>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<script>

import {ref} from 'vue';
import $ from "jquery";
import { useStore } from 'vuex';
import { toRaw } from'@vue/reactivity';
import { ElMessage } from 'element-plus';

export default {
    name: "PostManageView",

    setup(){
        const { Check, Close, Plus } = require('@element-plus/icons-vue');
        const store = useStore();
        const postEditStatus = ref('unselect');  // unselect, selected, new
        const postList = ref([
            {
                postId: 0,
                postName: '啦啦啦',
                postSalaryMin: 5000,
                postSalaryMax: 5000,
                postDescription: '的付款就啥都发ui夫人饿啊开发节节课恢复健康发',
                postSkill: '',
                postResponsibility: '犯得上看见浩方丢失; 发的是客户发u; 发的哈萨克;',

                sexReq: '男',
                titleReq: '学士',
                majorReq: '',
                workCity: '北京',
                workTime: 0,

                isTimeReq: false,
            },
            {
                postId: 1,
                postName: '呜呜呜乙',
                postSalaryMin: 3950,
                postSalaryMax: 3950,
                postDescription: '范德萨范德萨课恢复健康发',
                postSkill: '',
                postResponsibility: '犯得上的; 发的哈萨克;',

                sexReq: '不限',
                titleReq: '不限',
                majorReq: '',
                workCity: '不限',
                workTime: 0,

                isTimeReq: false,
            },
            {
                postId: 3,
                postName: '乙',
                postSalaryMin: 12,
                postSalaryMax: 12,
                postDescription: '范德萨健康发',
                postSkill: '',
                postResponsibility: '发的哈萨克;',

                sexReq: '女',
                titleReq: '不限',
                majorReq: '',
                workCity: '台湾',
                workTime: 0,

                isTimeReq: false,
            },
        ]);
        const postForm = ref({
            postId: 5,
            postName: '',
            postSalaryMin: 0,
            postSalaryMax: 0,
            postDescription: '',
            postSkill: '',
            postResponsibility: '',

            sexReq: '',
            titleReq: '',
            majorReq: '',
            workCity: '',
            workTime: 0,

            isTimeReq: false,
        });
        const formSexOption = ref([{
            value: '不限',
            label: '不限',
        },{
            value: '男',
            label: '男'
        }, {
            value: '女',
            label: '女'
        }],)
        const formDegreeOption = [
            {
                label: '默认选项',
                options: [
                    { value: '不限' },
                ],
            },
            {
                label: '最低学历要求',
                options: [
                    { value: '专科' },
                    { value: '学士' },
                    { value: '硕士' },
                    { value: '博士' },
                ],
            },
        ];
        const formCityOption = [
            {
                label: '默认选项',
                options: [
                    { value: '不限' },
                ],
            },
            {
                label: '工作地区要求',
                options: [
                    { value: '北京' },
                    { value: '上海' },
                    { value: '天津' },
                    { value: '重庆' },
                    { value: '黑龙江' },
                    { value: '吉林' },
                    { value: '辽宁' },
                    { value: '内蒙古' },
                    { value: '河北' },
                    { value: '新疆' },
                    { value: '甘肃' },
                    { value: '青海' },
                    { value: '陕西' },
                    { value: '宁夏' },
                    { value: '河南' },
                    { value: '山东' },
                    { value: '山西' },
                    { value: '安徽' },
                    { value: '湖北' },
                    { value: '湖南' },
                    { value: '江苏' },
                    { value: '四川' },
                    { value: '贵州' },
                    { value: '云南' },
                    { value: '广西' },
                    { value: '西藏' },
                    { value: '浙江' },
                    { value: '江西' },
                    { value: '广东' },
                    { value: '福建' },
                    { value: '台湾' },
                    { value: '海南' },
                    { value: '香港' },
                    { value: '澳门' },
                ],
            },
        ];
        const error_message = ref('');
        const getPostList = () => {
            $.ajax({
                type: 'post',
                url: store.state.httpUrl + "position/list/",
                success: (resp) =>{
                    postList.value = [];
                    JSON.parse(resp.data).forEach(element => {
                        postList.value.push({
                            postId:             element.positionkey,
                            postName:           element.posname,
                            postSalaryMin:      Number(element.possalary.split('-')[0]),
                            postSalaryMax:      Number(element.possalary.split('-')[1]),
                            postDescription:    element.posdescription,
                            postSkill:          element.posskill,
                            postResponsibility: element.posresponsibility,

                            sexReq:             element.sex,
                            titleReq:           element.title,
                            majorReq:           element.major,
                            workCity:           element.workcity,
                            workTime:           Number(element.worktime),

                            isTimeReq:          Number(element.worktime) !== 0,
                        })
                    });
                    ElMessage.info('岗位列表已同步为最新');
                },
                error: (resp) =>{
                    console.log(resp);
                    ElMessage.error('getPostList: ' + JSON.stringify(toRaw(resp)));
                }
            });
        }
        const postSelectHandler = (row) => {
            postEditStatus.value = 'selected';
            console.log(toRaw(row));
            postForm.value = postList.value.filter(
                (item) =>
                    item.postId === toRaw(row).postId
            )[0];
        }
        const postAddHandler = () => {
            postForm.value = {
                postId: 5,
                postName: '',
                postSalaryMin: 0,
                postSalaryMax: 0,
                postDescription: '',
                postSkill: '',
                postResponsibility: '',

                sexReq: '',
                titleReq: '',
                majorReq: '',
                workCity: '',
                workTime: 0,

                isTimeReq: false,
            };
            postEditStatus.value = 'new';
        }
        const postSubmitHandler = () => {
            // 选择更新
            if(postEditStatus.value === 'selected') {
                ElMessage.info('该功能仍在维护中');
            }
            // 新增岗位
            else if(postEditStatus.value === 'new') {
                $.ajax({
                    url: store.state.httpUrl + "position/submit/",
                    type: 'post',
                    data: {
                        name:           postForm.value.postName,
                        salary:         postForm.value.postSalaryMin + '-' + postForm.value.postSalaryMax,
                        skill:          postForm.value.postSkill,
                        responsibility: postForm.value.postResponsibility,

                        sex:            postForm.value.sexReq,
                        title:          postForm.value.titleReq,
                        major:          postForm.value.majorReq,
                        workCity:       postForm.value.workCity,
                        workTime:       postForm.value.workTime,
                    },
                    success(resp) {
                        if (resp.error_message === "success") {
                            ElMessage.success('岗位添加成功!');
                            getPostList();
                        } else {
                            error_message.value = resp.error_message;
                            ElMessage.error(JSON.stringify(toRaw(resp)));
                        }
                    },
                    error(resp) {
                        console.log(resp);
                        ElMessage.error(JSON.stringify(toRaw(resp)));
                    }
                });
            }
            postEditStatus.value = 'unselect';
        }

        getPostList();

        return{
            Check, Close, Plus,
            postEditStatus,
            postList,
            formSexOption,
            formDegreeOption,
            formCityOption,
            postForm,
            error_message,
            postAddHandler,
            postSelectHandler,
            postSubmitHandler,
        }
    }

}

</script>

<style scoped>
.content {
    margin-left: 1rem;
}
.flex-grow {
    flex-grow: 1;
}
</style>
