<template>
    <el-dialog
        v-model="dialogVisible"
        width="85%"
        title="人才详情title"
        :close-on-press-escape="false"
        :show-close="false"
        >
        <template #header>
            <div class="my-header">
                <p style="font-size: 25px; color: #666666">人才详情</p>
            </div>
        </template>
        <el-row style="margin: -4rem 1rem 1rem 1rem">
            <!--    左侧个人信息-->
            <el-col :span="12" align="left">
                <p style="font-size: 20px; color: #888888">基础信息</p>
                <el-descriptions
                    class="margin-top"
                    :column="2"
                    border>
                    <el-descriptions-item label="姓名">{{resumeInfo.name}}</el-descriptions-item>
                    <el-descriptions-item label="性别">{{resumeInfo.gender}}</el-descriptions-item>
                    <el-descriptions-item label="年龄">{{resumeInfo.age}}</el-descriptions-item>
                    <el-descriptions-item label="最高学历">{{resumeInfo.maxDegree}}</el-descriptions-item>
                    <el-descriptions-item label="最高毕业院校">{{resumeInfo.maxSchool}}</el-descriptions-item>
                    <el-descriptions-item label="工作起始年限">{{resumeInfo.workStartYear}}</el-descriptions-item>


                    <el-descriptions-item label="E-mail">{{resumeInfo.email}}</el-descriptions-item>
                    <el-descriptions-item label="电话号码">{{resumeInfo.phone}}</el-descriptions-item>
                    <el-descriptions-item label="标签">
                        <el-tag class="tag" v-for="tag in resumeInfo.tags" :key="tag">{{tag}}</el-tag>
                    </el-descriptions-item>
                </el-descriptions>

                <p style="font-size: 20px; color: #888888">工作/项目经历</p>
                <el-collapse v-model="activeCollapse">
                    <el-collapse-item v-for="exp in resumeInfo.experience" :key="exp" align="left">
                        <template #title>
                            <span class="collapse-item-title">{{exp.title}}</span>
                            <div class="flex-grow" />
                            <span class="collapse-item-title">{{exp.time}}</span>
                        </template>
                        <el-descriptions
                            class="margin-top"
                            :column="2"
                            border>
                            <el-descriptions-item label="名称">{{exp.title}}</el-descriptions-item>
                            <el-descriptions-item label="时间">{{exp.time}}</el-descriptions-item>
                            <el-descriptions-item label="详情描述">{{exp.description}}</el-descriptions-item>
                        </el-descriptions>
                    </el-collapse-item>
                </el-collapse>
            </el-col>
            <el-col :span="1"/>
            <!--    右侧分析结果-->
            <el-col :span="10" align="left">
                <!--        匹配岗位以及匹配度-->
                <el-row>
                    <p style="font-size: 20px; color: #888888">岗位匹配度</p>
                    <el-table :data="resumeInfo.predict" :row-class-name="rowClassHandler" striped style="width: 100%">
                        <el-table-column prop="postId" label="编号" width="100" sortable/>
                        <el-table-column prop="postName" label="岗位"/>
                        <el-table-column prop="score" label="匹配度" width="100" sortable/>

                    </el-table>
                </el-row>
                &nbsp;
                <!--        人才画像-->
                <el-row  style="max-height: 60vh">
                    <p style="font-size: 20px; color: #888888">人才画像</p>
                </el-row>
            </el-col>
        </el-row>
        <el-button @click="setHide">关闭页面</el-button>
    </el-dialog>
</template>

<script>
// eslint-disable-next-line no-unused-vars
import { ref, toRef } from 'vue';

export default {

    name: "ResumeDetail",
    props: {
        isVisible: Boolean,
        resumeInfo: Object,
    },
    emits: [
        'update:visible'
    ],

    setup(props, context) {
        const dialogVisible = toRef(props, 'isVisible');
        const activeCollapse = ref([]);
        // const resumeInfo = ref({
        //     // 必须字段
        //     name:       '嫩爹我啊',
        //     age:        25,
        //     maxDegree:  '研究生',
        //     maxSchool:     '白晶耶工业带专',
        //     workStartYear:  '-',
        //     // 额外字段
        //     gender:     '男',
        //     email:      '1145141919@aaa.com',
        //     phone:      '4080120160',
        //     maxMajor:   '赛博打灰技术',
        //     desiredPostName:'打灰总设计师',
        //     tags:       ['nb', '嗯嗯', '儒雅随和'],
        //     experience: [
        //         {
        //             title:  '第一个什么鬼经历',
        //             time:   '2022-10',
        //             description: '项目简介: 对教师上课的语言进行识别，对语言中提及的不同姓名实体进行识别，根据语气分为表扬、批评、鼓励、辱骂、授课五个类别。主要工作如下:● 通过爬虫技术获取校园场景数据、表扬、鼓励、批评数据，通过翻译池对数据增强。②利用 JointBERT 模型思路，将实体识',
        //         },
        //         {
        //             title:  '在这个地方实习还不如...',
        //             time:   '2023-05',
        //             description: '在湘潭广电中心的时政新闻部实习记者的期间，在实习老师的的指导下初步掌握了相关的新闻记者工作流程，包括采访、摄像、新闻稿的编辑、后期编辑等。并积累了一定的实习经验，完善了所学习的专业知识\\n',
        //         },
        //     ],
        //     // 预测数据
        //     predict:    [
        //         // id 为0的为 岗位意愿项 以及匹配度
        //         {
        //             postId: 0,
        //             postName:   '填报岗位',
        //             score:  0.4
        //         },
        //         {
        //             postId: 1,
        //             postName:   '垃圾岗位',
        //             score:  0.4
        //         },
        //         {
        //             postId: 2,
        //             postName:   '带专人上人专享职位',
        //             score:  0.99
        //         },
        //         ],
        // });
        const rowClassHandler = (rowInfo) => {
            if(rowInfo.rowIndex === 0){
                // console.log('!! 0', rowInfo.rowIndex);
                return 'expect-post-row';
            }
            // console.log('!! no 0', rowInfo.rowIndex);
            return '';
        };
        const setHide = () => {
            context.emit('update:visible', false);
        }
        return {
            activeCollapse,
            // resumeInfo,
            dialogVisible,
            rowClassHandler,
            setHide,
        }
    }
}
</script>

<style scoped>
el-dialog {
    max-height: 80vh;
    overflow-y: auto;
}
.tag{
    margin-right: 0.5rem;
}

.collapse-item-title {
    font-size: 15px;
    font-weight: bold;
    color: #666666;
    margin-right: 1rem;
    margin-left: 1rem;
}

.flex-grow {
    flex-grow: 1;
}

.el-table .expect-post-row {
    --el-table-tr-bg-color: var(--el-color-primary);
}
</style>
