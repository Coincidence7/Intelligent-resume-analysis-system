<template>
    <el-row>
        <p style="font-size: 25px; color: #666666">人岗匹配</p>
    </el-row>
    <el-row>
        <!--        左侧岗位选择和描述-->
        <el-col :span="9" align="left">
            <el-form v-model="valueForm" label-width="5rem" label-position="top">
                <el-form-item label="岗位名称">
                    <el-select class="select" v-model="valueForm" filterable placeholder="输入检索或下拉选择岗位">
                        <el-option
                            v-for="post in postSelectForm.postList"
                            :key="post.id"
                            :label="post.name"
                            :value="post.id"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="岗位标签">
                    <el-tag class="tag" v-for="tag in postSelectForm.postList[valueForm].tags" :key="tag">
                        {{tag}}
                    </el-tag>
                </el-form-item>
                <el-form-item label="岗位描述信息">
                    <el-input
                        v-model="postSelectForm.postList[valueForm].description"
                        :rows="12"
                        type="textarea"
                        placeholder="[该岗位无描述信息]"
                        :readonly="true"
                    />
                </el-form-item>
            </el-form>
            <el-row>
                <el-button
                    type="info" plain
                    :icon="Edit"
                    >
                    编辑或添加岗位信息</el-button>
                <div class="flex-grow" />
                <el-button
                    type="primary" plain
                    :icon="Search"
                    @click="matchClickedHandler">
                    开始人才匹配</el-button>
            </el-row>

        </el-col>
        <el-col :span="2"/>
        <!--        右侧人岗人才列表-->
        <el-col :span="12" align="left">
            <p style="font-size: 20px; color: #666666">匹配结果</p>
            <el-table
                style="width: 100%" max-height="60vh"
                align="left"
                :data="resultList"
                @selection-change="selectionChangeHandler"
            >
                <el-table-column type="selection"/>
                <el-table-column
                    prop="id" label="序号"
                    width="80" align="right"
                    sortable column-key="id"
                ></el-table-column>
                <el-table-column prop="name" label="应聘者" width="100"/>
                <el-table-column prop="score" label="匹配度" width="70"/>
                <el-table-column align="right" fixed="right">
                    <template #header>
                        <el-input
                            v-model="filterForm.text"
                            placeholder="输入以筛选人才"
                            clearable
                            class="input"/>
                    </template>
                    <template #default="scope">
                        <el-button
                            size="small" plain
                            :icon=Folder
                            @click="detailHandler(scope.row.id)"
                        >个人详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-col>
    </el-row>
    <ResumeDetail
        :is-visible="detailInfo.visible"
        :resume-info="resultList[detailInfo.index]"
        @update:visible="detailInfo.visible=false"/>
</template>

<script>

import { ref } from 'vue';
import { useStore } from 'vuex';
import ResumeDetail from "@/components/ResumeDetail";
import $ from 'jquery'

export default {
    name: "PersonPostMatchView",
    components: {
        ResumeDetail,
    },

    setup() {
        const {Edit, Folder, Search} = require('@element-plus/icons-vue');
        const store = useStore();
        const valueForm = ref(0);
        const filterForm = ref({
            text: ''
        });
        const postSelectForm = ref({
            selectedPost: '',
            postList: [
                {
                    id: 0,
                    name: 'Java后端开发工程师',
                    tags: ['框架', 'JAVA', '程序'],
                    description: '作为一名Java后端开发工程师，您将负责设计、开发和维护高性能、可扩展的后端系统。您将参与从需求分析到部署的整个软件开发生命周期，并与跨功能团队合作，确保交付高质量的软件解决方案\n' +
                        '职责：\n' +
                        '设计、开发和测试高质量的Java后端应用程序，满足业务需求。\n' +
                        '编写清晰、可维护的代码，并进行代码审查，确保代码质量。\n' +
                        '开发和维护数据库模型和持久化层。\n' +
                        '与前端开发人员密切合作，确保前后端的无缝集成。\n' +
                        '优化应用程序的性能和可扩展性，保证系统的稳定性和可靠性。\n' +
                        '解决生产环境中的技术问题和故障，并提供相应的修复和优化方案。\n' +
                        '遵守编码标准、软件开发流程和团队规范。\n' +
                        '技能要求：\n' +
                        '拥有扎实的Java编程能力和面向对象设计思想。\n' +
                        '熟悉常用的Java开发框架和技术，如Spring、Spring Boot等。\n' +
                        '熟悉关系数据库和SQL语言，有良好的数据库设计和优化能力。\n' +
                        '熟悉Web服务的开发和部署，如RESTful API。\n' +
                        '了解分布式系统和微服务架构的设计原理和实践经验。\n' +
                        '具备良好的问题解决能力和调试技巧，能够快速定位和修复代码中的bug。\n' +
                        '具备团队合作精神和良好的沟通能力，能够与其他团队成员紧密合作'
                },
                {
                    id: 1,
                    name: '产品运营',
                    tags: ['产品策划', '市场推广'],
                    description: '作为一名产品运营，您将负责产品的策划、推广、分析和改进。您将与产品团队、市场团队和用户紧密合作，确保产品在市场上的成功推出和持续增长。以下是您的主要职责和技能要求：\n' +
                        '职责：\n' +
                        '产品策划与路线图：根据市场需求、用户反馈和公司战略，制定产品策略和发展路线图。\n' +
                        '用户研究与洞察：通过市场调研、用户反馈和数据分析，深入了解用户需求、行为和偏好，为产品决策提供洞察和建议。\n' +
                        '市场推广和推广计划：制定并执行产品推广计划，包括营销策略、渠道选择、广告宣传、社交媒体等，提高产品的知名度和用户获取。\n' +
                        '用户体验优化：关注用户在使用产品时的体验和满意度，收集用户反馈，与产品团队合作改进产品功能、界面和交互设计。\n' +
                        '数据分析与指标跟踪：收集、分析和解读产品相关数据，包括用户行为数据、市场趋势、竞争对手数据等，通过数据驱动的方法优化产品性能和决策。\n' +
                        'A/B测试与优化：设计和执行 A/B 测试，评估不同功能、设计和策略的效果，持续优化产品的用户体验和业务指标。\n' +
                        '用户培训与支持：制定用户培训计划，提供产品培训和支持，解决用户问题，提高用户满意度和忠诚度。\n' +
                        '竞争分析与市场洞察：持续跟踪竞争对手的产品和策略，分析市场动态和趋势，为产品定位和差异化提供洞察和建议。\n' +
                        '项目管理与协调：与产品团队、技术团队和市场团队合作，协调项目进度和资源，确保产品按时交付和达到预期目标。\n' +
                        '技能要求：\n' +
                        '熟悉产品管理和运营的基本原理和方法，具备产品思维和商业敏感度。\n' +
                        '拥有市场调研和用户研究的经验，能够获取和分析用户需求和市场趋势。\n' +
                        '具备市场推广和品牌传播的知识和实践经验，能够制定和执行有效的推广策略。\n' +
                        '熟练运用数据分析工具和方法，能够收集、解读和利用数据指标进行决策和优化。\n' +
                        '具备良好的项目管理和协调能力，能够有效推进跨部门合作和项目交付。\n' +
                        '出色的沟通和表达能力，能够与不同团队和利益相关者进行有效沟通和合作。\n' +
                        '对用户体验和用户中心设计有一定的理解和实践经验。\n' +
                        '对新技术和市场趋势保持敏感，能够持续学习和提升自己的产品运营能力'
                },
                {
                    id: 2,
                    name: '项目主管',
                    tags: ['项目管理', '项目计划', '范围管理'],
                    description: '作为项目主管，您将负责项目的规划、执行和管理。您将领导项目团队，协调资源，确保项目按时、按质、按预算完成。以下是您的主要职责和技能要求：\n' +
                        '职责：\n' +
                        '项目规划与计划：制定项目计划，定义项目目标、交付物和里程碑，确保项目进度可控。\n' +
                        '团队管理与协调：领导和管理项目团队，分配任务、协调资源，推动团队合作和目标达成。\n' +
                        '项目执行与监控：监督项目执行进展，跟踪项目进度、质量和成本，及时处理项目风险和问题。\n' +
                        '范围管理与变更控制：明确项目范围和需求，控制和管理项目变更，确保项目交付符合客户期望。\n' +
                        '风险管理与决策：识别和评估项目风险，制定风险应对策略，做出合理的决策和调整。\n' +
                        '资源管理与优化：管理项目所需的人力、物力和财力资源，合理分配和利用资源，优化项目执行效率。\n' +
                        '沟通与协调：与项目相关的各方进行有效的沟通和协调，包括团队成员、利益相关者和高层管理者。\n' +
                        '质量管理与改进：制定和执行项目质量计划，确保项目交付的质量达到预期标准，并进行持续改进。\n' +
                        '报告与汇报：定期撰写项目进展报告和汇报，向上级管理层和利益相关者沟通项目状态和关键问题。\n' +
                        '跨部门合作与冲突解决：与其他部门或团队紧密合作，协调项目依赖和交付，解决跨部门的问题和冲突。\n' +
                        '技能要求：\n' +
                        '具备扎实的项目管理知识和经验，熟悉项目管理流程和方法。\n' +
                        '出色的团队管理和领导能力，能够激励团队成员，推动团队目标的实现。\n' +
                        '良好的沟通和协调能力，能够与不同层级和部门的人员进行有效的沟通和合作。\n' +
                        '强大的问题解决和决策能力，能够迅速识别问题、制定解决方案并做出决策。\n' +
                        '具备项目风险管理和变更控制的能力，能够有效评估和应对项目风险。\n' +
                        '熟练运用项目管理工具和软件，如Microsoft Project、JIRA等。\n' +
                        '对质量管理和持续改进有一定的理解和实践经验。\n' +
                        '跨部门合作和冲突解决能力，能够协调不同部门和团队之间的合作和冲突。\n' +
                        '具备良好的时间管理和组织能力，能够处理多个项目和任务的优先级'
                },
                {
                    id: 3,
                    name: '大数据分析师',
                    tags: ['持续学习', '大数据', '数据挖掘'],
                    description: '作为一名大数据分析师，您将负责处理和分析大规模数据集，提取有价值的信息和洞察，为业务决策和战略制定提供支持。您将使用各种数据分析工具和技术，对数据进行清洗、转换、建模和可视化，从中发现业务趋势、挖掘潜在机会，并提供数据驱动的建议。以下是您的主要职责和技能要求：\n' +
                        '职责：\n' +
                        '数据收集与清洗：收集、整理和清洗大规模的结构化和非结构化数据，确保数据的准确性和完整性。\n' +
                        '数据分析与建模：应用统计分析和机器学习算法，对数据进行探索性分析、模型建立和预测，发现数据中的模式和趋势。\n' +
                        '数据可视化与报告：使用数据可视化工具，将分析结果以易于理解和呈现的方式展示，撰写报告和演示，向相关团队和利益相关者传递洞察和建议。\n' +
                        '业务洞察与决策支持：与业务部门合作，理解业务需求，为业务决策和战略制定提供数据驱动的洞察和建议。\n' +
                        '数据质量与安全性：确保数据的质量和安全性，制定和实施数据保护和隐私措施，遵守相关法规和政策。\n' +
                        '技能要求：\n' +
                        '数据分析工具和技术：熟悉使用大数据处理和分析工具，如Hadoop、Spark、SQL、Python等。\n' +
                        '统计和机器学习：掌握统计分析方法和机器学习算法，能够应用到数据分析和建模中。\n' +
                        '数据可视化与报告：具备数据可视化工具的使用经验，如Tableau、Power BI等，能够将数据结果可视化并撰写清晰、准确的报告。\n' +
                        '数据库和查询语言：熟悉关系型数据库和查询语言，如SQL，能够进行数据提取和转换。\n' +
                        '编程和脚本语言：具备编程和脚本语言的基础知识，如Python、R等，能够进行数据处理和分析。\n' +
                        '业务理解和沟通能力：理解业务需求，具备良好的沟通和解释分析结果的能力，与非技术人员进行有效的合作和沟通。\n' +
                        '数据管理和质量控制：了解数据管理和数据质量控制的基本原理和实践，能够确保数据的准确性和一致性'
                },
                {
                    id: 4,
                    name: '算法工程师',
                    tags: ['算法', '时间复杂度', '效率'],
                    description: '作为一名算法工程师，您将负责设计、开发和优化各种算法和模型，用于解决复杂的计算问题和数据挖掘任务。您将与团队合作，研究和应用最新的算法和技术，为产品和项目提供技术支持和解决方案。以下是您的主要职责和技能要求：\n' +
                        '职责：\n' +
                        '算法设计与开发：根据需求，设计和开发创新的算法和模型，解决特定的计算和数据挖掘问题。\n' +
                        '数据分析与预处理：分析和处理大规模数据集，包括数据清洗、特征提取、降维等预处理步骤。\n' +
                        '模型选择与优化：选择合适的机器学习、深度学习或其他算法模型，并对其进行优化和调整，以提高性能和准确性。\n' +
                        '算法实现与编码：将设计好的算法和模型实现为可运行的代码，保证其效率、可扩展性和可维护性。\n' +
                        '算法性能评估：评估和分析算法的性能、准确性和效率，进行性能优化和改进。\n' +
                        '技术支持与解决方案：为产品和项目提供算法技术支持，与团队合作解决技术问题和挑战。\n' +
                        '技术研究与创新：跟踪最新的算法和技术研究，探索新的解决方案和创新应用。\n' +
                        '技能要求：\n' +
                        '数学和统计学基础：具备扎实的数学和统计学基础，理解和应用相关的算法和模型。\n' +
                        '编程和数据结构：熟练掌握至少一种编程语言，如Python、C++等，了解常用的数据结构和算法。\n' +
                        '机器学习和深度学习：熟悉常用的机器学习和深度学习算法，如决策树、支持向量机、神经网络等。\n' +
                        '数据处理和分析：具备数据处理和分析的能力，包括数据清洗、特征提取、降维等。\n' +
                        '算法优化和性能调优：有算法优化和性能调优的经验，能够提升算法的效率和准确性。\n' +
                        '研究和学习能力：具备良好的研究和学习能力，能够跟踪最新的算法和技术进展。\n' +
                        '沟通和团队合作：良好的沟通和团队合作能力，能够与团队成员和其他利益相关者有效合作'
                },
                {
                  id: 5,
                  name: '市场调研',
                  tags: ['市场需求', '理性消费', '调研分析'],
                  description: '作为一名市场调研员，您将负责进行市场调研和分析，以帮助公司了解市场趋势、竞争环境和客户需求。您将收集、整理和分析各种市场数据和信息，并提供有关市场机会和策略的建议。以下是您的主要职责和技能要求：\n' +
                      '职责：\n' +
                      '市场调研计划：制定市场调研计划，确定调研目标、方法和时间表，确保调研的有效性和可执行性。\n' +
                      '数据收集与整理：收集和整理市场数据和信息，包括市场规模、竞争对手、客户需求、趋势等，确保数据的准确性和完整性。\n' +
                      '数据分析与解读：使用统计分析和数据挖掘方法，对市场数据进行分析和解读，发现市场机会和趋势，并提供洞察和建议。\n' +
                      '竞争分析与定位：分析竞争对手的产品、定价、市场份额等，评估公司在市场中的竞争优势和定位。\n' +
                      '客户洞察与需求分析：了解客户需求和行为，通过调研和反馈机制，收集和分析客户洞察，为产品和营销策略提供支持。\n' +
                      '市场机会评估：评估市场机会的可行性和潜力，为公司的市场战略和产品开发提供建议。\n' +
                      '报告撰写与呈现：撰写市场调研报告和演示文稿，向管理层和团队成员传达市场分析结果和建议。\n' +
                      '技能要求：\n' +
                      '市场调研方法和技术：熟悉各种市场调研方法和技术，包括定性和定量研究方法、调研问卷设计、访谈技巧等。\n' +
                      '数据收集与分析：具备数据收集和分析的能力，包括数据整理、统计分析、数据可视化等。\n' +
                      '竞争分析和定位：了解竞争分析的基本原理和方法，能够评估公司的竞争优势和定位。\n' +
                      '市场洞察与客户需求分析：具备市场洞察和客户需求分析的能力，能够理解和应用市场数据和客户反馈。\n' +
                      '报告撰写与呈现：具备良好的报告撰写和呈现能力，能够清晰、准确地传达市场分析结果和建议。\n' +
                      '市场趋势与行业知识：关注市场趋势和行业动态，了解相关的市场和行业知识。\n' +
                      '数据工具和软件：熟练使用常见的数据分析工具和软件，如Excel、SPSS、Tableau等'
                }
            ]
        });
        const resultList = ref([]);
        const selectionList = ref([]);
        const detailInfo = ref({
            visible: false,
            index:  0
        });
        const matchClickedHandler = () => {
            console.log('matchClickedHandler');
            filteredResultList();
        }
        const calAge = (year) =>{
            if(year == undefined){
                return ''
            }
            let birth = new Date(year)
            if (isNaN(birth)) return ''
            let age = new Date().getFullYear() - birth.getFullYear()
            return age == undefined ? '' : age
        }

        const calExp = (Exp) => {
            let list = []
            Exp.forEach(item =>{
                list.push({
                    title:  item,
                    time:   '',
                    description: item
                })
            })

            return list
        }

        const filteredResultList = () => {
            console.log('filteredResultList');
            // let list = store.state.resume_info.list;
            resultList.value = [];
            
            $.ajax({
                url: store.state.httpUrl + 'position/matching/',
                type: 'POST',
                data:{
                    jobName: postSelectForm.value.postList[valueForm.value].name
                },
                success: (resp) =>{
                    // console.log(resp)
                    let list = JSON.parse(resp.data)
                    resultList.value = []
                    let idx = 0
                    list.forEach(item => {
                        let result = JSON.parse(item.parseresult)
                        // console.log(result[0])
                        resultList.value.push({
                            id: idx++,
                            name: result[0].姓名[0] == undefined ? '无名氏': result[0].姓名[0],
                            score: Math.floor(Math.random() * 100),
                            gender: '男',
                            age:   calAge(result[0].年龄[0]),
                            maxDegree: result[0].学历[0] == '' ? '本科': result[0].学历[0],
                            maxSchool: result[0].学校[0],
                            maxMajor:  result[0].专业[0],
                            desiredPostName:'大声叫兽',
                            workStartYear:  result[0].工作日期[0],
                            email:      result[0].邮箱[0],
                            phone:      result[0].电话[0],
                            experience: calExp(result[0].工作经历)
                        })
                    })
                    // console.log(result)
                    
                   
                },
                error: (resp) =>{
                    console.log(resp)
                }
            })
            
            // console.log('filteredResultList:', resultList.value);
        }
        const selectionChangeHandler = (selection) => {
            selectionList.value = [];
            for(let i  = 0; i < selection.length; i++) {
                selectionList.value.push(selection[i].index);
            }
        }
        const detailHandler = (resumeId) => {
            console.log('AnalyseResultView::detailHandler:rowIndex', resumeId);
            detailInfo.value.visible = true;
            detailInfo.value.index = resumeId;
        }
        return {
            Edit, Folder, Search,
            store,
            valueForm,
            filterForm,
            postSelectForm,
            resultList,
            selectionList,
            detailInfo,
            filteredResultList,
            matchClickedHandler,
            selectionChangeHandler,
            detailHandler,
        }
    }
}
</script>

<style scoped>

.tag {
    margin-right: 0.3rem;
}

.select {
    width: 100%;
}
.input {
    width: 80%;
}
.el-form-item {
    margin-bottom: 2rem;
}
.flex-grow {
    flex-grow: 1;
}
</style>
