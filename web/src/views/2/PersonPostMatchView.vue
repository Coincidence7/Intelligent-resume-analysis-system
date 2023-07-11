<template>
    <el-row>
        <p style="font-size: 25px; color: #666666">人岗匹配</p>
    </el-row>
    <el-row>
        <!--        左侧岗位选择和描述-->
        <el-col :span="9" align="left">
            <el-form v-model="valueForm" label-width="5rem" label-position="top">
                <el-form-item label="岗位名称">
                    <el-select class="select" v-model="valueForm" filterable placeholder="输入检索或下拉选择岗位">
                        <el-option
                            v-for="post in postSelectForm.postList"
                            :key="post.id"
                            :label="post.name"
                            :value="post.id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="岗位标签">
                    <el-tag class="tag" v-for="tag in postSelectForm.postList[valueForm].tags" :key="tag">
                        {{tag}}
                    </el-tag>
                </el-form-item>
                <el-form-item label="岗位描述信息">
                    <el-input
                        v-model="postSelectForm.postList[valueForm].description"
                        :rows="12"
                        type="textarea"
                        placeholder="[该岗位无描述信息]"
                        :readonly="true"
                    />
                </el-form-item>
            </el-form>
            <el-row>
                <el-button
                    type="info" plain
                    :icon="Edit"
                    >
                    编辑或添加岗位信息</el-button>
                <div class="flex-grow" />
                <el-button
                    type="primary" plain
                    :icon="Search"
                    @click="matchClickedHandler">
                    开始人才匹配</el-button>
            </el-row>

        </el-col>
        <el-col :span="2"/>
        <!--        右侧人岗人才列表-->
        <el-col :span="12" align="left">
            <p style="font-size: 20px; color: #666666">匹配结果</p>
            <el-table
                style="width: 100%" max-height="60vh"
                align="left"
                :data="resultList"
                @selection-change="selectionChangeHandler"
            >
                <el-table-column type="selection"/>
                <el-table-column
                    prop="id" label="序号"
                    width="80" align="right"
                    sortable column-key="id"
                ></el-table-column>
                <el-table-column prop="name" label="应聘者" width="100"/>
                <el-table-column prop="score" label="匹配度" width="70"/>
                <el-table-column align="right" fixed="right">
                    <template #header>
                        <el-input
                            v-model="filterForm.text"
                            placeholder="输入以筛选人才"
                            clearable
                            class="input"/>
                    </template>
                    <template #default="scope">
                        <el-button
                            size="small" plain
                            :icon=Folder
                            @click="detailHandler(scope.row.id)"
                        >个人详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
    </el-row>
    <ResumeDetail
        :is-visible="detailInfo.visible"
        :resume-info="resultList[detailInfo.index]"
        @update:visible="detailInfo.visible=false"/>
</template>

<script>

import {ref} from 'vue';
import { useStore } from 'vuex';
import ResumeDetail from "@/components/ResumeDetail";

export default {
    name: "PersonPostMatchView",
    components: {
        ResumeDetail,
    },

    setup() {
        const {Edit, Folder, Search} = require('@element-plus/icons-vue');
        const store = useStore();
        const valueForm = ref(0);
        const filterForm = ref({
            text: ''
        });
        const postSelectForm = ref({
            selectedPost: '',
            postList: [
                {
                    id: 0,
                    name: '示例岗位1',
                    tags: ['tag1', 'tag2'],
                    description: '法恩法律角度上来讲阿飞巴拉科夫等哈就发话哦和v如今看来vnfkdv,苏东坡， 哦ii\n劳费\n。昂阿基拉放大免费的感觉鹅绒该 。给，。我通过i五年'
                },
                {
                    id: 1,
                    name: '示岗位发大水了2',
                    tags: ['tag1',],
                    description: ''
                },
                {
                    id: 2,
                    name: '浮点数3但是',
                    tags: ['tag13', 'tfag2', 'fh76r'],
                    description: ''
                },
                {
                    id: 3,
                    name: '佛4发货的',
                    tags: ['tag1', 'tag2', '6498hwtb', 'gih6958ehr', 'r8h4f'],
                    description: ''
                },
                {
                    id: 4,
                    name: '发调遂滚进客户5',
                    tags: [],
                    description: ''
                },
                ]
        });
        const resultList = ref([]);
        const selectionList = ref([]);
        const detailInfo = ref({
            visible: false,
            index:  0
        });
        const matchClickedHandler = () => {
            console.log('matchClickedHandler');
            filteredResultList();
        }
        const filteredResultList = () => {
            console.log('filteredResultList');
            let list = store.state.resume_info.list;
            resultList.value = [];
            list.forEach(resume => {
                resume.predict.forEach(pre => {
                    if(pre.postId === valueForm.value){
                        resultList.value.push(resume);
                    }
                })
            });
            console.log('filteredResultList:', resultList.value);
        }
        const selectionChangeHandler = (selection) => {
            selectionList.value = [];
            for(let i  = 0; i < selection.length; i++) {
                selectionList.value.push(selection[i].index);
            }
        }
        const detailHandler = (resumeId) => {
            console.log('AnalyseResultView::detailHandler:rowIndex', resumeId);
            detailInfo.value.visible = true;
            detailInfo.value.index = resumeId;
        }
        return {
            Edit, Folder, Search,
            store,
            valueForm,
            filterForm,
            postSelectForm,
            resultList,
            selectionList,
            detailInfo,
            filteredResultList,
            matchClickedHandler,
            selectionChangeHandler,
            detailHandler,
        }
    }
}
</script>

<style scoped>

.tag {
    margin-right: 0.3rem;
}

.select {
    width: 100%;
}
.input {
    width: 80%;
}
.el-form-item {
    margin-bottom: 2rem;
}
.flex-grow {
    flex-grow: 1;
}
</style>
