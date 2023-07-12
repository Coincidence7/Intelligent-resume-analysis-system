<template>
    <el-row>
        <p style="font-size: 25px; color: #666666">简历导入</p>
    </el-row>
    <el-row>
<!--    表格部分-->
        <el-col :span="14">
            <el-table
                ref="multipleTable"
                style="width: 100%"  max-height=65vh
                :data="filteredUploadList()"
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
                <el-table-column prop="filename" label="文件名" width="450" show-overflow-tooltip></el-table-column>
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
                            @click="previewHandler(scope.$index, scope.row)"
                        >预览</el-button>
                        <el-button
                            size="small" type="danger" plain
                            :icon=Delete
                            @click="uploadDeleteHandler(scope.$index, scope.row)"
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
                    @click="uploadDeleteHandler(-1, -1)"
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
        <el-col :span="9">
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
                :auto-upload="false"
                :on-change="onUploadChange"
                :multiple="true"
                :show-file-list="true"
                :on-success="getRetData"
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

/* 参考: https://blog.csdn.net/shuaigg001/article/details/106628410 */

import { ref } from 'vue';
import { UploadFilled } from '@element-plus/icons-vue'

import router from '@/router';
import $ from 'jquery'
import { useStore } from 'vuex'
import { getCurrentInstance } from "vue";

export default {
    name: "ResumeImportView",
    components: {
        UploadFilled,
    },
    setup() {
        const {Delete, Document, Filter, PieChart} = require('@element-plus/icons-vue');
        let myFileList = []
        const filterMap = []

        let { proxy } = getCurrentInstance();

        filterMap['img'] = ['png', 'jpg', 'jpeg']
        filterMap['pdf'] = ['pdf']
        filterMap['doc'] = ['doc', 'docx']
        filterMap['ppt'] = ['ppt', 'pptx']

        filterMap['todo'] = ['todo']
        filterMap['doing'] = ['doing']
        filterMap['done'] = ['finish']
        filterMap['error'] = ['fail', 'stopped']

        const store = useStore();
        const typeFilterDict = ref([
            {text: '图片', value: 'img'},
            {text: 'PDF', value: 'pdf'},
            {text: '文档', value: 'doc'},
            {text: '幻灯片', value: 'ppt'},
            {text: '其他', value: 'other'}
        ]);
        const uploadStatusDict = ref([
            {text: '未上传', value: 'todo'},
            {text: '上传中', value: 'doing'},
            {text: '已上传', value: 'done'},
            {text: '错误', value: 'error'},
        ]);
        const uploadInfo = ref({
            name: '',
            path: '',
        });
        const uploadList = ref([
            {   index:      1,
                type:       'doc',
                filename:   'fdsjkanrbad.doc',
                status:     'todo',            },
        ]);
        const search = ref('');
        let selection = []
        const filteredUploadList = () =>{
            return uploadList.value.filter(
                (data) =>
                    !search.value ||
                    data.filename.toLowerCase().includes(search.value.toLowerCase())
            )
        }
        const getUploadList = () =>{
            $.ajax({
                type: 'post',
                url: store.state.httpUrl + "resume/List/",
                success: (resp) =>{
                    uploadList.value = []
                    JSON.parse(resp.data).forEach(element => {
                        uploadList.value.push({
                            index:      element.resumekey,
                            type:       element.type,
                            filename:   element.filename,
                            status:     element.state,
                        })
                    });
                    filteredUploadList.value = uploadList.value
                },
                error: (resp) =>{
                    console.log(resp)
                }
            })
        }

        getUploadList();


        const onUploadChange = (file, fileList) =>{
            // console.log(file.status)
            myFileList = []
            fileList.forEach(element => {
                myFileList.push(element)
            });
            // console.log(myFileList)
        }
        const selectionChangeHandler = (selections) => {
            selection = selections.map(row => row.index)
        };
        const typeFilterHandler = (value, row) => {
            console.log(search.value)

            if(value != 'other')
                return filterMap[value].indexOf(row.type) != -1
            return Object.values(filterMap).flat(2).indexOf(row.type) == -1

        };
        const statusFilterHandler = (value, row) => {
            if(value != 'other')
                return filterMap[value].indexOf(row.status) != -1
            return Object.values(filterMap).flat(2).indexOf(row.status) == -1
        };
        const uploadDeleteHandler = (index, row) => {
            console.log('# ResumeImport::uploadDeleteHandler', index, row);
            let param = []
            if(index != -1 && row != -1){
                param.push(row.index)
            }else{
                param = selection
            }
            console.log(param)
            let arrFormData = new FormData()
            arrFormData.append('resumeKey', param)
            $.ajax({
                url: store.state.httpUrl + "resume/record/delete/",
                type: 'POST',
                data: arrFormData,
                processData: false,
                contentType: false,
                success: (resp) => {
                    console.log(resp)
                    getUploadList();
                },
                error: (resp) => {
                    console.log(resp)
                }
            })
        };
        const filterResetHandler = () => {
           proxy.$refs.multipleTable.clearFilter()
        }
        const previewHandler = (index, row) => {
            console.log(index, row);
        }
        const startAnalyse = () => {

            // 获取待检测列表
            let files_proccess = []
            for(let i = 0; i < myFileList.length; i++){
                let str = myFileList[i].name
                files_proccess.push({
                    index: i + 1,
                    filename: str,
                    type: str.substring(str.lastIndexOf('.') + 1),
                    stage: "doing",
                    time: 0
                })
            }

            store.commit("updateResumeRecord", {
                list: files_proccess
            })
            const randomString = (len) => {
                len = len || 32;
                var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
                var maxPos = $chars.length;
                var pwd = '';
                for (let i = 0; i < len; i++) {
                    pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
                }
                return pwd;
            }
            const hashMap = [];
            // 新增一条数据
            for(let i = 0; i < myFileList.length; i++){
                let file = myFileList[i]

                let myFormData = new FormData()

                let alias = randomString(13);

                myFormData.append(alias, file.raw)


                // 每个文件有自己的计时器
                hashMap[alias] = i;
                let item = setInterval(() => {
                    store.commit("updateTime", {
                        idx: hashMap[alias]
                    })
                }, 1000)
                store.commit("addInterval", {
                        interval: item,
                        idx: hashMap[alias]
                    }
                );

                $.ajax({
                    type: 'POST',
                    url: store.state.httpUrl + "upload/",
                    data: myFormData,
                    processData: false,
                    contentType: false,
                    success: (resp) =>{
                        // console.log(resp)
                        let idxs = JSON.parse(resp.resume_keys)
                        let original_data = JSON.parse(resp.original_data)
                        let msg = resp.error_message

                        let str = original_data[0].original_name

                        let file_proccess = {
                            index: 0,
                            filename: str,
                            type: str.substring(str.lastIndexOf('.') + 1),
                            stage: msg == 'fail' ? msg : "finish",
                            time: "/"
                        }

                        let alias_str = original_data[0].alias
                        // 结束指定计时器

                        store.commit("updateOneResumeRecord", {
                            element: file_proccess,
                            idx: hashMap[alias_str]
                        })

                        // 存入数据库
                        if(!store.state.cell_resume.stop_flag){
                            console.log(new Date().getTime())
                            $.ajax({
                                url: store.state.httpUrl + "parser/result/",
                                type: 'POST',
                                data:{
                                    error_message: msg,
                                    resumekey: idxs[0],
                                    resumename: "简历",
                                    parseresult: resp.data,
                                    state: store.state.cell_resume.list[hashMap[alias_str]].stage,
                                    createdtime: new Date().getTime()
                                },
                                success: (resp) => {
                                    console.log(resp)
                                },
                                error: (resp) => {
                                    console.log(resp)
                                }
                            })
                        }



                    },
                    error: (resp)=>{
                        console.log(resp)
                    }
                })
            }


            router.push({name: 'analyse_waiting'});

        }



        const getRetData = (response, file, fileList) =>{
            // response.data 是解析后的json
            console.log(response)
            console.log(file)
            console.log(fileList)
        }
        return {
            onUploadChange,
            getRetData,
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
