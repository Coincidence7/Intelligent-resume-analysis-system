<template>
    <el-row class="tac">
        <el-col>
            <el-menu
                class="el-sidebar"
                :default-active="2"
                :default-openeds="openeds"
                @select=currentSelect
            >
                <el-menu-item index="1" @click=handleSelect>
                    <el-icon><DataAnalysis /></el-icon>
                    <span>总览看板</span>
                </el-menu-item>

                <el-sub-menu index="2">
                    <template #title><el-icon><Histogram /></el-icon>简历分析</template>
                    <el-menu-item index="2-1"><el-icon><Download /></el-icon>简历导入</el-menu-item>
                    <el-menu-item index="2-2"><el-icon><Connection /></el-icon>人岗匹配</el-menu-item>
                    <el-menu-item index="2-3"><el-icon><DocumentCopy /></el-icon>历史记录</el-menu-item>
                </el-sub-menu>

                <el-menu-item index="3">
                    <el-icon><Memo /></el-icon>
                    <span>人才库</span>
                </el-menu-item>

                <el-sub-menu index="4">
                    <template #title>
                        <el-icon><OfficeBuilding /></el-icon>
                        <span>企业设置</span>
                    </template>
                    <el-menu-item index="4-1"><el-icon><Message /></el-icon>邮件设置</el-menu-item>
                    <el-menu-item index="4-2"><el-icon><SetUp /></el-icon>岗位管理</el-menu-item>
                </el-sub-menu>

                <el-menu-item index="5">
                    <el-icon><Setting /></el-icon>
                    <span>应用设置</span>
                </el-menu-item>

            </el-menu>
        </el-col>
    </el-row>
</template>

<script>

import router from '@/router';
import {
    Connection,
    DataAnalysis,
    DocumentCopy,
    Download,
    Histogram,
    Memo,
    OfficeBuilding,
    Message,
    Setting,
    SetUp,
} from '@element-plus/icons-vue';

export default {
    name: "SideBar",
    components: {
        // PieChart,
        Connection,
        DataAnalysis,
        DocumentCopy,
        Download,
        Histogram,
        Memo,
        OfficeBuilding,
        Message,
        Setting,
        SetUp,
    },

    setup() {
        const urlDict = {
            '1':   'overview',
            '2-1': 'resume_import',
            '2-2': 'person_post_match',
            '2-3': 'history',
            '3':   'talent_pool',
            '4-1': 'email_setting',
            '4-2': 'post_manage',
            '5':   'app_setting'
        };

        const currentSelect = (index, indexPath, item) => {
            // index: str       eg: 2-1
            // indexPath: array eg: ['2', '2-1']
            //item: array       eg: [index, indexPath]
            console.log('# Sidebar::currentSelect()', 'index:', index, ' path:', indexPath, ' item:', item);
            router.push({name: urlDict[index]})
        }
        const handleSelect = (item) => {
            // item: array[index, __, __]
            console.log('# Sidebar::handleSelect()', item);
        }

        return {
            currentSelect,
            handleSelect,
        }
    }

}

</script>

<style scoped>
.el-menu {
    margin-top: 1rem;
}
.el-menu-item {
    --el-menu-item-font-size: 15px;
}
.el-sub-menu {
    --el-menu-item-font-size: 15px;
}
.el-menu-item.is-active {
    background-color: #f2f8ff;

}
</style>
