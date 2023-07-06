<template>
    <el-row>
        <p style="font-size: 25px; color: #666666">简历分析</p>
    </el-row>
    <el-row>
        <!--    表格部分-->
        <el-col :span="13">
            <el-table
                style="width: 100%" border="true"
                max-height=60vh
                :data=uploadList
            >
                <el-table-column
                    prop="index" label="序号"
                    fixed="left" width="100" align="right"
                ></el-table-column>
                <el-table-column
                    prop="type" label="类型"
                    fixed="left" align="center" width="100"
                ></el-table-column>
                <el-table-column prop="filename" label="文件名" width="400"></el-table-column>
                <el-table-column
                    prop="status" label="分析状态"
                    fixed="right" align="center" width="100"
                    :filters=uploadStatusDict
                    :filter-method=statusFilterHandler
                    filter-placement="bottom-end"
                ></el-table-column>
            </el-table>
            &nbsp;
            <el-row>

                <span style="font-size: 20px; color: #666666; margin-top: 0.5rem">总分析进度</span>
                <span style="color: #888888; margin: 0.7rem 0 0 3rem">
                    估计剩余时间 : <el-tag type="info" size="small" style="margin-top: -0.2rem">{{estimateTime}} s</el-tag>
                </span>
                <div class="flex-grow" />
                <el-button
                    type="warning" plain
                    :icon=VideoPause
                    @click="statusChangeHandler('pause')"
                >暂停分析</el-button>
                <el-button
                    type="danger" plain
                    :icon=CircleClose
                    @click="statusChangeHandler('stop')"
                >中止分析</el-button>
                <!-- @click="" -->
                <el-button
                    type="success" plain
                    :icon=Finished
                >查看结果</el-button>
            </el-row>
            &nbsp;
            <el-row>
                <el-progress
                    :percentage="30"
                    :stroke-width="15"
                    striped
                    striped-flow
                    duration="5"
                />
            </el-row>

        </el-col>
        <el-col :span="1"/>
        <!--    详情细节-->
        <el-col :span="10">
            <el-collapse v-model=activeCollapse>
                <el-collapse-item name="1">
                    <template #title>
                        <span style="font-size: 20px; color: #666666">运行细节</span>
                    </template>
                    &nbsp;
                    <el-row>
<!--                    单个进度条-->
                        <el-col :span="6">
                            <el-progress type="dashboard" :percentage="80">
                                <template #default="{ percentage }">
                                    <span class="percentage-value">{{ percentage }}%</span>
                                    <span class="percentage-label">Progressing</span>
                                </template>
                            </el-progress>
                        </el-col>
                        <el-col :span="1"/>
<!--                    单个信息-->
                        <el-col :span="17">
                            <el-descriptions
                                :column="2"
                                :size="large"
                                :style="blockMargin">
                                <template #title>
                                    <el-tag size="small" type="info" style="margin: -0.2rem 0.5rem 0 0">{{debugForm.index}}</el-tag>{{debugForm.filename}}
                                </template>
                                <el-descriptions-item label="文件类型" :span="2">
                                    <el-tag size="small" style="margin: -0.2rem 0.5rem 0 0">{{debugForm.type}}</el-tag>
                                </el-descriptions-item>
                                <el-descriptions-item label="处理阶段">{{debugForm.stage}}</el-descriptions-item>
                                <el-descriptions-item label="已运行时间">{{debugForm.time}}</el-descriptions-item>
                            </el-descriptions>
                        </el-col>
                    </el-row>
                </el-collapse-item>
<!--            代码细节-->
                <el-collapse-item  name="2">
                    <template #title>
                        <span style="font-size: 20px; color: #666666">程序终端输出</span>
                    </template>
                    &nbsp;
                    <el-input
                        class="terminal"
                        v-model=debugTerminal
                        :autosize="{ minRows: 12, maxRows: 12 }"
                        type="textarea" readonly="true" resize="none"
                        placeholder="(Output Empty)"
                    />
                </el-collapse-item>
            </el-collapse>
            &nbsp;

        </el-col>
    </el-row>
</template>


<script>

import {reactive, ref} from "vue";
import router from '@/router';

export default {
    name: "AnalyseWaitingView",

    setup() {
        const {PieChart, CircleClose, Finished, VideoPlay, VideoPause} = require('@element-plus/icons-vue');
        const status = ref('');
        const estimateTime = ref(0);
        const activeCollapse = ref(['1', '2']);
        const debugForm = reactive({
            index: '5',
            filename: '发的考试辅导.doc',
            type: 'doc',
            stage: '输出为PDF',
            time: '7s',
        });
        const debugTerminal = ref('TOPIC\n    Windows PowerShell Help System\n\nSHORT DESCRIPTION\n    Displays help about Windows PowerShell cmdlets and concepts.\nSHORT DESCRIPTION\n'+
            '    Displays help about Windows PowerShell cmdlets and concepts.\n' +
            '\n' +
            'LONG DESCRIPTION\n' +
            '    Windows PowerShell Help describes Windows PowerShell cmdlets,\n' +
            '    functions, scripts, and modules, and explains concepts, including\n' +
            '    the elements of the Windows PowerShell language.\n' +
            '\n' +
            '    Windows PowerShell does not include help files, but you can read the\n' +
            '    help topics online, or use the Update-Help cmdlet to download help files\n' +
            '    to your computer and then use the Get-Help cmdlet to display the help\n' +
            '    topics at the command line.\n' +
            '\n' +
            '    You can also use the Update-Help cmdlet to download updated help files\n' +
            '    as they are released so that your local help content is never obsolete.\n' +
            '\n' +
            '    Without help files, Get-Help displays auto-generated help for cmdlets,\n' +
            '    functions, and scripts.\n' +
            '# 程序有没有在运行?');
        const uploadStatusDict = ref([
            {text: '未上传', value: 'todo'},
            {text: '上传中', value: 'doing'},
            {text: '已上传', value: 'done'},
            {text: '错误', value: 'error'},
        ]);
        const uploadList = ref([
            {   index:      1,
                type:       'doc',
                filename:   'fdsjkanrbad.doc',
                status:     'todo',            },
            {   index:      2,
                type:       'pdf',
                filename:   '489trhj.pdf',
                status:     'doing',            },
            {   index:      3,
                type:       'img',
                filename:   '23.png',
                status:     'done',            },
            {   index:      1,
                type:       'doc',
                filename:   'fdsjkanrbad.doc',
                status:     'todo',            },
            {   index:      2,
                type:       'pdf',
                filename:   '489trhj.pdf',
                status:     'doing',            },
            {   index:      3,
                type:       'img',
                filename:   '23.png',
                status:     'done',            },
            {   index:      1,
                type:       'doc',
                filename:   'fdsbegaad.doc',
                status:     'todo',            },
            {   index:      2,
                type:       'pdf',
                filename:   '489trhj.pdf',
                status:     'doing',            },
            {   index:      3,
                type:       'img',
                filename:   '23.png',
                status:     'done',            },
            {   index:      1,
                type:       'doc',
                filename:   'fdsjkanrbad.doc',
                status:     'todo',            },
            {   index:      2,
                type:       'pdf',
                filename:   '489trhj.pdf',
                status:     'doing',            },
            {   index:      3,
                type:       'img',
                filename:   '23.png',
                status:     'done',            },
            {   index:      1,
                type:       'doc',
                filename:   'fdsjkanrbad.doc',
                status:     'todo',            },
            {   index:      2,
                type:       'pdf',
                filename:   '489trhj.pdf',
                status:     'doing',            },
            {   index:      3,
                type:       'img',
                filename:   '23.png',
                status:     'done',            },
            {   index:      1,
                type:       'doc',
                filename:   'fdsbegaad.doc',
                status:     'todo',            },
            {   index:      2,
                type:       'pdf',
                filename:   '489trhj.pdf',
                status:     'doing',            },
            {   index:      3,
                type:       'img',
                filename:   '23.png',
                status:     'done',            },
            {   index:      1,
                type:       'doc',
                filename:   'fdsjkanrbad.doc',
                status:     'todo',            },
            {   index:      2,
                type:       'pdf',
                filename:   '489trhj.pdf',
                status:     'doing',            },
            {   index:      3,
                type:       'img',
                filename:   '23.png',
                status:     'done',            },
            {   index:      1,
                type:       'doc',
                filename:   'fdsjkanrbad.doc',
                status:     'todo',            },
            {   index:      2,
                type:       'pdf',
                filename:   '489trhj.pdf',
                status:     'doing',            },
            {   index:      3,
                type:       'img',
                filename:   '23.png',
                status:     'done',            },
            {   index:      1,
                type:       'doc',
                filename:   'fdsbegaad.doc',
                status:     'todo',            },
            {   index:      2,
                type:       'pdf',
                filename:   '489trhj.pdf',
                status:     'doing',            },
            {   index:      3,
                type:       'img',
                filename:   '23.png',
                status:     'done',            },
        ]);
        const statusFilterHandler = () => {

        };
        const resumeHandler = () => {

        }
        const pauseHandler = () => {

        }
        const abortHandler = () => {

        }
        const statusChangeHandler = (newStatus) => {
            status.value = newStatus;
        }
        const viewResultHandler = () => {
            router.push({ name:"mainPage" });
        }

        return {
            PieChart, CircleClose, Finished, VideoPlay, VideoPause,
            activeCollapse,
            debugForm,
            debugTerminal,
            estimateTime,
            status,
            uploadStatusDict,
            uploadList,
            statusChangeHandler,
            resumeHandler,
            pauseHandler,
            abortHandler,
            statusFilterHandler,
            viewResultHandler,
        };
    }
}

</script>

<style scoped>

.el-progress .el-progress--line {
    margin-bottom: 15px;
    width: 10vw;
}
.flex-grow {
    flex-grow: 1;
}
.percentage-value {
    display: block;
    margin-top: 10px;
    font-size: 28px;
}
.percentage-label {
    display: block;
    margin-top: 10px;
    font-size: 12px;
}
.terminal {
    /*background-color: #282c34;*/
    font-family: 'YaHei-Consolas';
    font-size: 15px;
    color: #CCCCCC;
}
</style>
