<template>
    <el-dialog
        v-model="dialogVisible"
        width="80%"
        title="人才详情title"
        >
        <template #header>
            <div class="my-header">
                <p style="font-size: 25px; color: #666666">人才详情</p>
            </div>
        </template>
        <el-row>
            <!--    左侧个人信息-->
            <el-col :span="11" align="left">
                <el-descriptions
                    class="margin-top"
                    :column="2"
                    border>
                    <el-descriptions-item label="姓名">{{resumeInfo.name}}</el-descriptions-item>
                    <el-descriptions-item label="年龄">{{resumeInfo.age}}</el-descriptions-item>
                    <el-descriptions-item label="最高学历">{{resumeInfo.maxDegree}}</el-descriptions-item>
                    <el-descriptions-item label="最高毕业院校">{{resumeInfo.school}}</el-descriptions-item>
                    <el-descriptions-item label="工作起始年限">{{resumeInfo.workStartYear}}</el-descriptions-item>

                    <el-descriptions-item label="性别">{{resumeInfo.gender}}</el-descriptions-item>
                    <el-descriptions-item label="E-mail">{{resumeInfo.email}}</el-descriptions-item>
                    <el-descriptions-item label="电话号码">{{resumeInfo.phone}}</el-descriptions-item>
                    <el-descriptions-item label="标签">
                        <el-tag class="tag" v-for="tag in resumeInfo.tags">{{tag}}</el-tag>
                    </el-descriptions-item>
                </el-descriptions>
            </el-col>
            <el-col :span="1"/>
            <!--    右侧分析结果-->
            <el-col :span="11" align="left">
                <!--        匹配岗位以及匹配度-->
                <el-row  style="max-height: 40vh">
                    <el-table :data="resumeInfo.predict" stripe style="width: 100%">
                        <el-table-column prop="postId" label="编号" width="100" sortable/>
                        <el-table-column prop="post" label="岗位"/>
                        <el-table-column prop="score" label="匹配度" width="100" sortable/>

                    </el-table>
                </el-row>
                &nbsp;
                <!--        人才画像-->
                <el-row  style="max-height: 60vh">

                </el-row>
            </el-col>
        </el-row>
    </el-dialog>
</template>

<script>
import {ref} from 'vue';

export default {

    name: "ResumeDetail",

    setup() {
        const resumeInfo = ref({
            // 必须字段
            name:       '嫩爹我啊',
            age:        25,
            maxDegree:  '研究生',
            school:     '白晶耶工业带专',
            workStartYear:  '-',
            // 额外字段
            gender:     '男',
            email:      '1145141919@aaa.com',
            phone:      '4080120160',
            maxMajor:   '赛博打灰技术',
            desiredPost:'打灰总设计师',
            tags:       ['nb', '嗯嗯', '儒雅随和'],
            // 预测数据
            predict:    [
                {
                    postId: 1,
                    post:   '垃圾岗位',
                    score:  0.4
                },
                {
                    postId: 2,
                    post:   '带专人上人专享职位',
                    score:  0.99
                },
                ],
        });
        const dialogVisible = ref(true);
        return {
            resumeInfo,
            dialogVisible,
        }
    }
}
</script>

<style scoped>

.tag{
    margin-right: 0.5rem;
}

</style>
