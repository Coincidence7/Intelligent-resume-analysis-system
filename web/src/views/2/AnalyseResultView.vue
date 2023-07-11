<template>
    <el-row>
        <p style="font-size: 25px; color: #666666">分析结果</p>
    </el-row>
    <el-row>
<!--    左侧-->
        <el-col :span="11" align="left">
<!--        待处理列表-->
            <p style="font-size: 20px; color: #666666">未归类列表</p>
            <el-table
                style="width: 110%" max-height="70vh"
                align="left"
                :data="filteredResumeList(0)"
                @selection-change="(selection)=>selectionChangeHandler(selection, 0)"
            >
                <el-table-column type="selection"/>
                <el-table-column
                    prop="index" label="序号"
                    width="80" align="right"
                    sortable column-key="index"
                ></el-table-column>
                <el-table-column prop="name" label="应聘者" width="100"/>
                <el-table-column prop="tags" label="标签" width="200">
                    <template #default="scope">
                        <el-tag v-for="tag in filteredResumeList(0)[scope.$index].tags" :key="tag" class="tag">
                                {{tag}}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column width="150"  align="right" fixed="right">
                    <template #header>
                        <el-input v-model="keyword" placeholder="输入即可检索" />
                    </template>
                    <template #default="scope">
                        <el-button
                            size="small" plain
                            :icon=Folder
                            @click="detailHandler(scope.row.index)"
                        >个人详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
            &nbsp;
<!--        操作台-->
            <el-row align="middle">
                <el-button
                    type="warning" plain
                    :icon=Refresh
                    @click="resumeListReset"
                >重置为分析结果</el-button>
                <el-button
                    type="success" plain
                    :icon=Select
                    @click="statusChangeHandler('stop')"
                >保存并提交结果</el-button>
            </el-row>
        </el-col>
        <el-col :span="1"/>
<!--    右侧匹配岗位-->
        <el-col :span="12" align="left">
            <p style="font-size: 20px; color: #666666">已归类列表</p>
            <el-form :model=filterForm  label-width="4.5rem">
                <el-form-item label="岗位筛选">
                    <el-input
                        v-model="filterForm.text"
                        placeholder="输入以筛选岗位"
                        clearable
                        class="input"/>
                </el-form-item>
            </el-form>
            <el-collapse class="collapse" v-model=activeCollapse>
                <el-collapse-item v-for="post in PostList" :key="post.id" align="left">
                    <template #title>
                        <span class="collapse-item-title">{{ post.title }}</span>
                    </template>
                    <p></p>
                    <span class="post-description">{{post.description}}</span>
                    <el-table
                        style="width: 100%" max-height="30vh"
                        align="left"
                        :data="filteredResumeList(post.id)"
                        @selection-change="(selection)=>selectionChangeHandler(selection, 1)"
                    >
                        <el-table-column type="selection"/>
                        <el-table-column
                            prop="index" label="序号"
                            width="80" align="right"
                            sortable column-key="index"
                        ></el-table-column>
                        <el-table-column prop="name" label="应聘者" width="100"/>
                        <el-table-column prop="tags" label="标签" width="200">
                            <template #default="scope">
                                <el-tag v-for="tag in filteredResumeList(post.id)[scope.$index].tags" :key="tag" class="tag">
                                    {{tag}}
                                </el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column width="125"  align="right" fixed="right">
                            <template #header>
                                <el-input v-model="keyword" placeholder="输入即可检索" />
                            </template>
                            <template #default="scope">
                                <el-button
                                    size="small" plain
                                    :icon=Folder
                                    @click="detailHandler(scope.row.index)"
                                >个人详情</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    &nbsp;
                    <!--                    移入移出按钮-->
                    <el-row align="middle">
                        <el-button
                            type="danger" plain
                            :icon=Back
                            @click="PPTransfer('L', post.id, post.selection)"
                        >移出所选</el-button>
                        <el-button
                            type="primary" plain
                            :icon=Right
                            @click="PPTransfer('R', post.id, post.selection)"
                        >移入所选</el-button>
                    </el-row>
                </el-collapse-item>
            </el-collapse>
        </el-col>
    </el-row>
    <ResumeDetail
        :is-visible="detailInfo.visible"
        :resume-info="store.state.resume_info.list[detailInfo.index-1]"
        @update:visible="detailInfo.visible=false"/>
</template>

<script>

import {ref} from 'vue';
import { useStore } from 'vuex';
import ResumeDetail from "@/components/ResumeDetail";

export default {
    name: "AnalyseResultView",
    components: {
        ResumeDetail
    },

    setup() {
        const {Back, Folder, Right, Refresh, Select} = require('@element-plus/icons-vue');
        const store = useStore();
        const keyword = ref('');
        const activeCollapse = ref([]);
        const filterForm = ref({
            text:   '',
        });
        // 所有的简历 index start with 1 !!!
        const resumeList = ref([
            {
                index:      1,
                name:       'WalterMa',
                predict:    0,
                score:      0.7,
                current:    0,
                tags:       ['tag1',],
            },
            {
                index:      2,
                name:       '发烧友',
                predict:    0,
                score:      0.6,
                current:    0,
                tags:       [],
            },
            {
                index:      3,
                name:       'WalterMa',
                predict:    0,
                score:      0.9,
                current:    0,
                tags:       ['tag1', 'tag2', 'tag3'],
            },
            {
                index:      4,
                name:       '4354trh',
                predict:    0,
                score:      0.3,
                current:    0,
                tags:       ['tag1', ],
            },
            {
                index:      5,
                name:       '09wnvh',
                predict:    1,
                score:      0.8,
                current:    1,
                tags:       ['tag1','tag3',],
            },
            {
                index:      6,
                name:       '幅度萨芬和',
                predict:    1,
                score:      0.6,
                current:    1,
                tags:       ['tag3'],
            },
            {
                index:      7,
                name:       'i8ujgf',
                predict:    2,
                score:      0.1,
                current:    2,
                tags:       ['tag1', 'tag2'],
            },
        ]);
        // 所有的岗位
        const PostList = ref([
            {
                id:     1,
                title:  '垃圾岗位',
                description:'呃呃呃呃呃呃呃',
            },
            {
                id:     2,
                title:  '某个岗位',
                description:'这是一个垃圾岗位',
            },
        ]);
        // 0为未归类列表的selection 1为右侧列表的selection
        const selectionList = ref([
            [],
            [],
        ]);
        const detailInfo = ref({
            visible: false,
            index:  0
        });
        // 返回tableId的内容
        const filteredResumeList = (tableId) => {
            // console.log('AnalyseResultView::filteredResumeList:tableId', tableId);
            // console.log('AnalyseResultView::filteredResumeList:keyword', keyword.value);
            return resumeList.value.filter(
                resume =>
                    resume.current === tableId &&
                    (!keyword.value || resume.name.toLowerCase().includes(keyword.value.toLowerCase())));
        }
        const selectionChangeHandler = (selection, tableType) => {
            // tableType 0为左, 1为右
            // console.log('AnalyseResultView::selectionChangeHandler:selection', selection);
            // console.log('AnalyseResultView::selectionChangeHandler:tablaType', tableType);
            selectionList.value[tableType] = [];
            for(let i  = 0; i < selection.length; i++) {
                selectionList.value[tableType].push(selection[i].index);
            }
            console.log('AnalyseResultView::selectionChangeHandler:list', selectionList.value);
        }
        const detailHandler = (rowIndex) => {
            console.log('AnalyseResultView::detailHandler:rowIndex', rowIndex);
            detailInfo.value.visible = true;
            detailInfo.value.index = rowIndex;
        }
        const PPTransfer = (LorR, tableId) => {
            // console.log('AnalyseResultView::PPTransfer:LorR', LorR);
            // console.log('AnalyseResultView::PPTransfer:tableId', tableId);
            let indexToMove = (LorR === 'L') ? 1 : 0;
            let targetTable =   (LorR === 'L') ? 0 : tableId;

            for(let i = 0; i<selectionList.value[indexToMove].length; i++){
                resumeList.value[selectionList.value[indexToMove][i] - 1].current = targetTable;
            }
            selectionList.value[indexToMove] = [];
            console.log(resumeList.value);
        }
        const resumeListReset = () => {
            for(let i = 0; i<resumeList.value.length; i++) {
                resumeList.value[i].current = resumeList.value[i].predict;
            }
        }

        return {
            Back, Folder, Right, Refresh, Select,
            store,
            keyword,
            filterForm,
            resumeList,
            selectionList,
            PostList,
            activeCollapse,
            detailInfo,
            filteredResumeList,
            detailHandler,
            selectionChangeHandler,
            PPTransfer,
            resumeListReset,
        }
    }
}

</script>

<style scoped>

.tag {
    margin-right: 0.3rem;
}
.collapse {
    height: 60vh;
    overflow-y: auto;
}
.input {
    width: 80%;
}
.collapse-item-title {
    font-size: 20px;
    color: #666666;
}
.post-description {
    text-align: left;
    color: #888888;
    margin: 1rem 0 3rem 0;
}
</style>
