<template>
    <el-row>
        <p style="font-size: 25px; color: #666666">简历导入</p>
    </el-row>
    <el-row>
<!--    表格部分-->
        <el-col :span="13">
            <el-table
                style="width: 100%"  max-height=65vh
                :data=uploadList
                @selection-change=selectionChangeHandler
            >
                <el-table-column type="selection" fixed="left"/>
                <el-table-column
                    prop="index" label="序号"
                    fixed="left" width="80" align="right"
                    sortable column-key="index"
                ></el-table-column>
                <el-table-column
                    prop="type" label="类型"
                    fixed="left" align="center" width="70"
                    :filters=typeFilterDict
                    :filter-method=typeFilterHandler
                    filter-placement="bottom-end"
                ></el-table-column>
                <el-table-column prop="filename" label="文件名" width="450"></el-table-column>
                <el-table-column
                    prop="status" label="上传状态"
                    fixed="right" align="center" width="100"
                    :filters=uploadStatusDict
                    :filter-method=statusFilterHandler
                    filter-placement="bottom-end"
                ></el-table-column>
                <el-table-column fixed="right" width="170">
                    <template #header>
                        <el-input v-model="search" placeholder="输入即可检索" />
                    </template>
                    <template #default="scope">
                        <el-button
                            size="small" plain
                            :icon=Document
                            @click="uploadDeleteHandler(scope.$index, scope.row)"
                        >预览</el-button>
                        <el-button
                            size="small" type="danger" plain
                            :icon=Delete
                            @click="previewHandler(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            &nbsp;
            <el-row>
                <el-button
                    plain
                    :icon=Filter
                    @click=filterResetHandler
                >重置筛选条件</el-button>
                <el-button
                    type="danger" plain
                    :icon=Delete
                    @click="uploadDeleteHandler(scope.$index, scope.row)"
                >删除所选</el-button>
                <div class="flex-grow" />
                <el-button
                    type="primary" plain
                    :icon=PieChart
                    @click=startAnalyse
                >开始简历分析</el-button>
            </el-row>
        </el-col>
        <el-col :span="1"/>
<!--    上传部分-->
        <el-col :span="10">
            <el-form :inline="true" :model=uploadInfo class="demo-form">
                <el-form-item label="上传名称">
                    <el-input
                        v-model="uploadInfo.name"
                        placeholder="RESUME-SET-001"
                        clearable />
                </el-form-item>
                <el-form-item label="存储路径">
                    <el-select
                        v-model=uploadInfo.path
                        placeholder="./resume/set-001"
                        clearable
                    />
                </el-form-item>
            </el-form>
            &nbsp;
            <el-upload
                class="upload"
                drag
                action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                multiple="true"
                show-file-list="true"
            >
                <el-icon class="el-icon--upload"><upload-filled /></el-icon>
                <div class="el-upload__text">
                    拖拽文件至此处 或 <em>点击选择文件</em>
                </div>
                <template #tip>
                    <div class="el-upload__tip">
                        jpg/png files with a size less than 500kb
                    </div>
                </template>
            </el-upload>
            &nbsp;

        </el-col>
    </el-row>
</template>

<script>

/*参考: https://blog.csdn.net/shuaigg001/article/details/106628410*/

import {ref} from 'vue';
// import {TableInstance} from 'element-plus'
import {UploadFilled} from '@element-plus/icons-vue'
import {computed} from "@vue/reactivity";
import router from '@/router';

export default {
    name: "ResumeImportView",
    components: {
        UploadFilled,
    },

    setup() {
        const {Delete, Document, Filter, PieChart} = require('@element-plus/icons-vue');
        const typeFilterDict = ref([
            {text: '图片', value: 'img'},
            {text: 'PDF', value: 'pdf'},
            {text: '文档', value: 'doc'},
            {text: '幻灯片', value: 'ppt'},
            {text: '文本', value: 'txt'},
        ]);
        const uploadStatusDict = ref([
            {text: '未上传', value: 'todo'},
            {text: '上传中', value: 'doing'},
            {text: '已上传', value: 'done'},
            {text: '错误', value: 'error'},
        ]);
        // const uploadList = ref<TableInstance>();
        const uploadInfo = ref({
            name: '',
            path: '',
        });
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
        const search = ref('');
        const filteredUploadList = computed(() => {
            // uploadList.value.filter((data) => {
            //     !search.value ||
            //         data.name.toLowerCase().includes(search.value.toLowerCase())
            // });
        });
        const selectionChangeHandler = () => {

        };
        const typeFilterHandler = () => {

        };
        const statusFilterHandler = () => {

        };
        const uploadDeleteHandler = (index, row) => {
            console.log('# ResumeImport::uploadDeleteHandler', index, row);
        };
        const filterResetHandler = () => {

        }
        const previewHandler = () => {

        }
        const startAnalyse = () => {
            router.push({name: 'analyse_waiting'});
        }

        return {
            Delete, Document, Filter, PieChart,
            uploadInfo,
            uploadList,
            typeFilterDict,
            uploadStatusDict,
            filteredUploadList,
            search,
            selectionChangeHandler,
            typeFilterHandler,
            statusFilterHandler,
            uploadDeleteHandler,
            filterResetHandler,
            previewHandler,
            startAnalyse
        }
    }
}



</script>

<style scoped>

.flex-grow {
    flex-grow: 1;
}

</style>
