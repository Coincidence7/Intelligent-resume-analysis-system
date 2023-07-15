<template>
    <el-row>
        <p style="font-size: 25px; color: #666666">分析记录</p>
    </el-row>
    <el-row>
        <el-col :span="20">
            <el-table
                ref="multipleTable"
                style="width: 100%"  max-height=80vh
                :data="filteredHistoryList()"
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
                <el-table-column prop="filename" label="文件名" show-overflow-tooltip></el-table-column>
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
                            @click="historyDeleteHandler(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
    </el-row>
</template>

<script>

import { ref, getCurrentInstance } from 'vue';
import { useStore } from 'vuex';
import $ from 'jquery';

export default {
    name: "HistoryView",

    setup() {
        const {Delete, Document} = require('@element-plus/icons-vue');
        let { proxy } = getCurrentInstance();
        let selection = [];
        const store = useStore();
        const search = ref('');
        const historyList = ref([
            {   index:      1,
                type:       'doc',
                filename:   'fdsjkanrbad.doc',
                status:     'todo',            },
        ]);
        const typeFilterDict = ref([
            {text: '图片', value: 'img'},
            {text: 'PDF', value: 'pdf'},
            {text: '文档', value: 'doc'},
            {text: '幻灯片', value: 'ppt'},
            {text: '其他', value: 'other'}
        ]);
        const filterMap = [];
        filterMap['img'] = ['png', 'jpg', 'jpeg'];
        filterMap['pdf'] = ['pdf'];
        filterMap['doc'] = ['doc', 'docx'];
        filterMap['ppt'] = ['ppt', 'pptx'];

        filterMap['todo'] = ['todo'];
        filterMap['doing'] = ['doing'];
        filterMap['done'] = ['finish'];
        filterMap['error'] = ['fail', 'stopped'];

        const selectionChangeHandler = (selections) => {
            selection = selections.map(row => row.index)
        };
        const filterResetHandler = () => {
            proxy.$refs.multipleTable.clearFilter()
        }
        const filteredHistoryList = () =>{
            console.log('filteredHistoryList:');
            return historyList.value
        }
        const typeFilterHandler = (value, row) => {
            console.log(search.value)
            if(value !== 'other')
                return filterMap[value].indexOf(row.type) !== -1
            return Object.values(filterMap).flat(2).indexOf(row.type) === -1

        };
        const getHistoryList = () =>{
            $.ajax({
                type: 'post',
                url: store.state.httpUrl + "resume/List/",
                success: (resp) =>{
                    historyList.value = [];
                    JSON.parse(resp.data).forEach(element => {
                        historyList.value.push({
                            index:      element.resumekey,
                            type:       element.type,
                            filename:   element.filename,
                            status:     element.state,
                        })
                    });
                    filteredHistoryList.value = historyList.value
                },
                error: (resp) =>{
                    console.log(resp)
                }
            });
        }
        const previewHandler = (index, row) => {
            console.log(index, row);
          if(historyList.value[index].status == 'finish'){
            if(filterMap['img'].indexOf(historyList.value[index].type) != -1){
              window.open(store.state.httpUrlRes + "image/?resumeKey=" + row.index, "_blank", "charset=utf-8");
            }else {
              window.open(store.state.httpUrlRes + "resume/path/?resumeKey=" + row.index + '?response-content-type=application/pdf', "_blank", "charset=utf-8");
            }
          }
        }
        const historyDeleteHandler = (index, row) => {
            console.log('# ResumeImport::historyDeleteHandler', index, row);
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
                    getHistoryList();
                },
                error: (resp) => {
                    console.log(resp)
                }
            })
        };

        getHistoryList();

        return {
            Delete, Document,
            search,
            selection,
            filterMap,
            historyList,
            typeFilterDict,
            selectionChangeHandler,
            filteredHistoryList,
            filterResetHandler,
            typeFilterHandler,
            getHistoryList,
            previewHandler,
            historyDeleteHandler,
        }
    }
}
</script>

<style scoped>

</style>
