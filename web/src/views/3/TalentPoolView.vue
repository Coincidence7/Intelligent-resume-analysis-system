<template>
    <el-row>
        <p style="font-size: 25px; color: #666666">人才库</p>
    </el-row>
    <el-row>
<!--    左侧检索栏-->
        <el-col :span="7" align="left">
            <p style="font-size: 20px; color: #666666">检索条件</p>
            <el-form :model="filterForm" label-width="5rem">
                <el-form-item label="检索词">
                    <el-input v-model="filterForm.keyword" :clearable="true" width="100%"/>
                </el-form-item>
                <el-form-item label="学历要求">
                    <el-select v-model="filterForm.maxDegree" placeholder="最低学历要求">
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
                <el-form-item label="工作经验">
                    <el-row>
                        <el-switch
                            v-model="filterForm.workYearReq"
                            inline-prompt
                            :active-icon="Check"
                            :inactive-icon="Close"
                        />
                        <el-input-number
                            v-model="filterForm.workYear"
                            :disabled="!filterForm.workYearReq"
                            :min="0"
                            :max="100"
                            controls-position="right"
                            style="margin-left: 1rem; width:7rem"
                        />
                        <span style="margin-left: 8px; color: #666666">(年)</span>
                    </el-row>
                </el-form-item>
                <el-form-item label="项目经验">
                    <el-row>
                        <el-switch
                            v-model="filterForm.experienceReq"
                            inline-prompt
                            :active-icon="Check"
                            :inactive-icon="Close"
                        />
                        <el-radio-group
                            v-model="filterForm.experience"
                            :disabled="!filterForm.experienceReq"
                            style="margin-left: 1rem">
                            <el-radio :label="1">较少</el-radio>
                            <el-radio :label="3">较多</el-radio>
                        </el-radio-group>
                    </el-row>
                </el-form-item>
            </el-form>
            <br/>
            <el-row>
                <el-button
                    type="danger" plain
                    :icon=Refresh
                    @click="filterResetHandler"
                >重置筛选条件</el-button>
                <div class="flex-grow" />
                <el-button
                    type="primary" plain
                    :icon=Search
                    @click="resumeFilterHandler"
                >执行人岗匹配</el-button>
            </el-row>
        </el-col>
        <el-col :span="2"/>
<!--    右侧卡片-->
        <el-col :span="15" align="left">
            <p style="font-size: 20px; color: #666666">检索结果</p>
            <div style="overflow: auto; height: 65vh; padding-left: 0">
                
                <el-row v-for="i in listLayout.rowCount" :key="i">
                    <el-card
                        v-for="j in (i === listLayout.rowCount && listLayout.isOdd) ? 1: 2" :key="j"
                        class="box-card-list-item" shadow="hover" style="width: 20vw">
                        
                        <template #header>
                            
                            <div class="card-header">
                                
                                <div style="align-items: center">
                                    <el-avatar :icon="Avatar" />
                                    <span style="margin-left: 1rem">{{listLayout.data[2 * (i - 1) + j - 1].candname}}</span>
                                </div>
                                <el-button
                                    plain
                                    :icon=Folder
                                    @click="detailHandler(2 * (i) + j)"
                                >个人详情</el-button>
                            </div>
                        </template>
                        <el-descriptions
                            class="margin-top"
                            :title="listLayout.data[2 * (i - 1) + j - 1].candschool"
                            :column="1"
                            :style="blockMargin">
                            <el-descriptions-item label="最高学历 :">
                                <el-tag type="info">
                                    {{ listLayout.data[2 * (i - 1) + j - 1].candtitle }}
                                </el-tag>
                            </el-descriptions-item>
                            <el-descriptions-item label="工作时长 :">{{ listLayout.data[2 * (i - 1) + j - 1].candjob }}</el-descriptions-item>
                            <el-descriptions-item label="联系方式 :">{{ listLayout.data[2 * (i - 1) + j - 1].email }}</el-descriptions-item>
                            <el-descriptions-item label="标   签 :">
                                <el-tag size="small">{{ listLayout.data[2 * (i - 1) + j - 1].candtitle }}</el-tag>
                                &nbsp;
                                <el-tag size="small">{{ listLayout.data[2 * (i - 1) + j - 1].candschool }}</el-tag>
                            </el-descriptions-item>
                        </el-descriptions>
                    </el-card>
                </el-row>
            </div>
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
import $ from 'jquery'

export default {
    name: "TalentPoolView",
    components: {
        ResumeDetail
    },

    setup() {
        const { Avatar, Check, Close, Folder, Refresh, Search } = require('@element-plus/icons-vue');
        const formDegreeOption = [
            {
                label: '默认选项',
                options: [
                    {
                        value: 0,
                        label: '无学历要求',
                    },
                ],
            },
            {
                label: '最低学历要求',
                options: [
                    {
                        value: 1,
                        label: '专科',
                    },
                    {
                        value: 2,
                        label: '学士',
                    },
                    {
                        value: 3,
                        label: '硕士',
                    },
                    {
                        value: 4,
                        label: '博士',
                    },
                ],
            },
        ];

        let hashMapDegree = []
        hashMapDegree[0] = ''
        hashMapDegree[1] = '专科'
        hashMapDegree[2] = '学士'
        hashMapDegree[3] = '硕士'
        hashMapDegree[4] = '博士'

        const filterForm = ref({
            keyword: '',
            age: [0, 100],
            maxDegree: 0,
            workYearReq: false,
            workYear: 0,
            experienceReq: false,
            experience: 0,
        });
        const listLayout = ref({
            rowCount: 0,
            isOdd: false,
            data: []
        });
        const store = useStore();
        const detailInfo = ref({
            visible: false,
            index:  1
        });
        const filteredResumeList = ref([]);
        const filterResetHandler = () => {
            filterForm.value.keyword = '';
            filterForm.value.age = [0, 100];
            filterForm.value.maxDegree = 0;
            filterForm.value.workYearReq = false;
            filterForm.value.workYear = 0;
            filterForm.value.experienceReq = false;
            filterForm.value.experience = 0;
            resumeFilterHandler();
        }
        const resumeFilterHandler = () => {
            // 执行条件筛选 允许部分条件不使用
            // 设置 filteredResumeList 内容
            // 设置 listLayout
            listLayout.value.rowCount = 0;
            listLayout.value.isOdd = false;
            console.log(filterForm.value)

            $.ajax({
                url: store.state.httpUrl + "position/matching/select/",
                type: 'POST',
                data:{
                   workYear: filterForm.value.workYear,
                   maxDegree: hashMapDegree[filterForm.value.maxDegree],
                   keyword: filterForm.value.keyword
                },
                success: (resp) => {
                    console.log(resp)
                    let data = JSON.parse(resp.data)
                    console.log(data)
                    listLayout.value.data = data
                    listLayout.value.rowCount = Math.ceil(data.length / 2);
                    listLayout.value.isOdd = data.length % 2 == 1;
                    // console.log(JSON.parse(data))
                },
                error: (resp) =>{
                    console.log(resp)
                }
            })
        }
        const detailHandler = (resumeId) => {
            console.log('detailHandler:rowIndex', resumeId);
            detailInfo.value.visible = true;
            // detailInfo.value.index = resumeId;
            
        }
        return {
            Avatar, Check, Close, Folder, Refresh, Search,
            listLayout,
            filterForm,
            formDegreeOption,
            store,
            detailInfo,
            filteredResumeList,
            detailHandler,
            filterResetHandler,
            resumeFilterHandler,
        }
    }
}
</script>

<style scoped>

.flex-grow {
    flex-grow: 1;
}
.box-card-list-item{
    margin-right: 1rem;
    margin-bottom: 1rem;
}
.card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
</style>
