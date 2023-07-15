<template>
    <el-col :span="12">
        <div id="resume-radar-info"
             style="padding-top: 1rem"
             :style="{ width: this.width, height: this.height}"/>
    </el-col>
    <el-col :span="12">
        <div id="resume-radar-post"
             :style="{ width: this.width, height: this.height}"/>
    </el-col>
</template>

<script>
import { onMounted, toRef } from 'vue';
import * as echarts from "echarts";

export default {
    name: "ResumeRadar",
    props: {
        height: String,
        width: String,
        data: Array,
    },
    setup(props) {
        // eslint-disable-next-line no-unused-vars
        const radarDataInfo = toRef(props, 'data').value[0];
        const radarDataPost = toRef(props, 'data').value[1];
        onMounted(() => {
            initChart();
        });
        function initChart() {
            let chartInfo = echarts.init(document.getElementById('resume-radar-info'));
            let chartPost = echarts.init(document.getElementById('resume-radar-post'));
            let optionInfo = {
                radar: {
                    indicator: [
                        { name: '教育背景', max: 100 },
                        { name: '工作经验', max: 100 },
                        { name: '社会能力', max: 100 },
                        { name: '领导能力', max: 100 },
                        { name: '语言基础', max: 100 },
                    ]
                },
                series: [{
                    type: 'radar',
                    itemStyle: {
                        color: '#505050',
                        borderColor: '#505050',
                    },
                        areaStyle: {
                        color: '#c7daff',
                    },
                    data: [{
                        name: '兔兔',
                        value: radarDataPost,
                    }]
                }]
            };
            let optionPost = {
                radar: {
                    indicator: [
                        { name: '行政文员', max: 100 },
                        { name: '市场营销', max: 100 },
                        { name: '产品经理', max: 100 },
                        { name: '开发工程师', max: 100 },
                        { name: '软件工程师', max: 100 },
                        { name: '金融分析师', max: 100 },
                        { name: '销售经理', max: 100 },
                        { name: '市场专员', max: 100 },
                        { name: '财务会计', max: 100 },
                        { name: '项目经理', max: 100 },
                    ]
                },
                series: [{
                    type: 'radar',
                    itemStyle: {
                        color: '#505050',
                        borderColor: '#505050',
                    },
                    areaStyle: {
                        color: '#dcd6ff',
                    },
                    data: [{
                        name: '兔兔',
                        value: radarDataInfo,
                    }]
                }]
            };
            chartInfo.setOption(optionInfo);
            chartPost.setOption(optionPost);

            window.onresize = function () {
                chartInfo.resize();
                chartPost.resize();
            }
        }
    }
}
</script>

<style scoped>

</style>
