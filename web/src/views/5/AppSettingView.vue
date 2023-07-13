<template>
    <div>
        <el-row>
            <p style="font-size: 25px; color: #666666">应用配置</p>
        </el-row>
        <el-row>
            <el-col :span="11">
                <!--    输入表单-->
                <el-form label-position="right" label-width="140px" style="max-width: 100vw">
                    <el-row>
                        <!--            subtitle-->
                        <!--                <h2 id="基础用法" class="subtitle">基础用法<a class="header-anchor vp-link" href="#基础用法" aria-hidden="true">#</a></h2>-->
                        <span class="subtitle">接口设置</span>
                        <el-space fill>
                            <el-alert type="info" show-icon :closable="false" align="left">
                                <p>移动云API服务对每个请求进行身份验证，提交请求需要在请求中包含签名信息。 其中：</p>
                                <p>&nbsp;&nbsp;&nbsp;&nbsp;· AK用于标识访问者的身份</p>
                                <p>&nbsp;&nbsp;&nbsp;&nbsp;· SK用于对请求签名字符串进行摘要加密，用户应谨慎保管SK</p>
                                <p>AK和SK由移动云官方颁发给访问者，可通过移动云官方网站申请和管理</p>
                            </el-alert>
                            <el-form-item label="目标服务请求url">
                                <el-input v-model="settingForm.ocrUrl" />
                            </el-form-item>
                            <el-form-item label="AccessKey">
                                <el-input v-model="settingForm.ocrAccessKey" />
                            </el-form-item>
                            <el-form-item label="SecretKey">
                                <el-input v-model="settingForm.ocrSecretKey" type="password" show-password="true"/>
                            </el-form-item>
                        </el-space>
                        &nbsp;
                        <el-space fill>
                            <el-alert type="info" show-icon :closable="false" align="left">
                                <p>文档(doc, docx, ppt, pptx)需要转为PDF格式以进行内容解析</p>
                                <p>&nbsp;&nbsp;&nbsp;&nbsp;· 使用MS-Office套件进行转换，要求系统Windows，可能涉及授权</p>
                                <p>&nbsp;&nbsp;&nbsp;&nbsp;· 使用LibreOffice进行转换</p>
                                <p>&nbsp;&nbsp;&nbsp;&nbsp;· 使用第三方转换服务进行转换，需手动设置接口</p>
                            </el-alert>
                            <el-form-item label="PDF转换服务">
                                <el-radio-group v-model="settingForm.pdfImplement">
                                    <el-radio label="ms-office">MS-Office</el-radio>
                                    <el-radio label="lib-office">LibreOffice</el-radio>
                                    <el-radio label="api">第三方转换服务</el-radio>>
                                </el-radio-group>
                            </el-form-item>
                            <el-form-item label="第三方服务API">
                                <el-input v-model="settingForm.pdfUrl"/>
                            </el-form-item>
                        </el-space>
                        &nbsp;
    
                    </el-row>
                </el-form>
            </el-col>
            <el-col :span="1"/>
            <el-col :span="12" align="left">
                <el-button
                    type="warning" plain
                >暂停分析</el-button>
            </el-col>
        </el-row>
    </div>
</template>


<script>

import {ref} from "vue";
export default {
    name: "AppSettingView",

    setup() {
        const settingForm = ref({
            ocrUrl: '',
            ocrAccessKey: '',
            ocrSecretKey: '',
            pdfImplement: '',
            pdfUrl: '',
        });

        return {
            settingForm,

        }
    }
}
</script>

<style scoped>

.subtitle {
    color: #666666;
    font-size: 18px;
    font-weight: bold;
    padding-bottom: 1rem;
}

</style>
