<template>
    <el-menu
        :default-active="activeIndex"
        class="el-navbar"
        mode="horizontal"
        background-color="#FBFBFB"
        :ellipsis="false"
        @select="handleSelect"
    >
        <div class="logo-text">
            <el-button class="back-btn" size="large" type="info" :icon="Back" plain/>
            <strong id="title">GustingBamboo - 智能简历分析系统</strong>
        </div>
        <div class="flex-grow" />
        <el-menu-item index="1">XXX 的您，早上好!</el-menu-item>
        <el-menu-item index="2">
            <el-popover
                width="300"
                offset="15"
                popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;">
                <template #reference>
                    <el-avatar
                        :size="50"
                        :icon="UserFilled"
                        id="user"/>
                </template>
    <!--        已登录则显示用户信息-->
                <div v-if=isLogin>
                    <el-row justify="center">
                        <el-avatar
                            :size="100"
                            id="avatar-bg"
                            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
                    </el-row>
                    &nbsp;
                    <el-row justify="center">
                        <span style="font-size: 20px">竹林风</span>
                    </el-row>
                    &nbsp;
                    <el-row justify="center">
                        <el-button type="danger" plain @click=logout>登出系统</el-button>
                    </el-row>
                </div>
    <!--        未登录显示登录-->
                <div v-else>
                    <el-row justify="center">
                        <el-avatar
                            :size="100"
                            id="avatar-bg"
                            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
                    </el-row>
                    &nbsp;
<!--                用户注册-->
                    <div v-if="isRegister">
                        <el-row>
                            <el-form :model="form">
                                <el-form-item label="用户名" label-width="5rem">
                                    <el-input v-model="form.userkey" placeholder="请输入用户名" autocomplete="off" />
                                </el-form-item>
                                <el-form-item label="用户密码" label-width="5rem">
                                    <el-input v-model="form.password" placeholder="请输入密码" type="password" show-password autocomplete="off" />
                                </el-form-item>
                                <el-form-item label="密码确认" label-width="5rem">
                                    <el-input v-model="form.password" placeholder="请再次输入密码" type="password" show-password autocomplete="off" />
                                </el-form-item>
                            </el-form>
                        </el-row>
                        <el-row justify="center"><span class="error-msg">{{error_msg}}</span></el-row>
                        <el-row>
                            <el-button class="login-btn" @click=toggle>返回登录</el-button>
                            <div class="flex-grow" />
                            <el-button class="login-btn" type="primary" @click=register>注册账号</el-button>

                        </el-row>
                    </div>
<!--                用户登录-->
                    <div v-else>
                        <el-row>
                            <el-form :model="form">
                                <el-form-item label="用户名" label-width="5rem">
                                    <el-input v-model="form.username" placeholder="请输入用户名" autocomplete="off" />
                                </el-form-item>
                                <el-form-item label="用户密码" label-width="5rem">
                                    <el-input v-model="form.password" placeholder="请输入密码" type="password" show-password autocomplete="off" />
                                </el-form-item>
                            </el-form>
                        </el-row>
                        <el-row justify="center"><span class="error-msg">{{error_msg}}</span></el-row>
                        <el-row>
                            <el-button @click=toggle>注册账号</el-button>
                            <div class="flex-grow" />
                            <el-button class="login-btn" type="primary" @click="login">登入系统</el-button>
                        </el-row>
                    </div>
                </div>
            </el-popover>
        </el-menu-item>
    </el-menu>
</template>

<script>

import {ref, reactive} from 'vue';
// import { useStore } from 'vuex'
// import router from '@/router/index'
import "@/assets/font/font.css";

export default {
    name: "NavBar",
    components: {
    },

    setup(){
        const {Back, UserFilled} = require('@element-plus/icons-vue');
        const form = reactive({
            username: '',
            password: '',
        });
        // const store = useStore();
        // const jwt_token = localStorage.getItem("jwt_token");

        let isLogin = ref(false);
        let isRegister = ref(false);
        let error_msg = ref(' ');

        const login = () => {
            isLogin.value = true;
            isRegister.value = false;
            // error_msg.value = 'good';
        }
        const register = () => {
            isRegister.value = false;
            // error_msg.value = 'good';
        }
        const logout = () => {
            isLogin.value = false;
        }
        const toggle = () => {
            isRegister.value = !isRegister.value;
            error_msg.value = ' ';
        }

        return {
            Back, UserFilled,
            form,
            isLogin,
            isRegister,
            error_msg,
            login,
            register,
            logout,
            toggle,
        }
    }
}
</script>

<style scoped>

.el-navbar {
    --el-menu-item-height: 4rem;
    --el-menu-item-font-size: 17px;
    --el-menu-active-color: "transparent" !important;
    --el-menu-hover-bg-color: "transparent" !important;
}
.flex-grow {
    flex-grow: 1;
}
.back-btn {
    height: 2.5rem;
    width: 4rem;
    margin-right: 1rem;
}
.logo-text {
    font-size: 19px;
    margin: 0.7rem 0 0 1rem;
}
user {
    margin: -3rem 0 0 0rem;
}
.user-avatar {
    margin-top: -0.7rem;
    /*vertical-align: center;*/
}
.el-input {
    padding-right: 1rem;
}
.login-btn {
    margin-right: 1rem;
}
.error-msg {
    color: red;
    margin: -0.5rem 0 1rem 0;
}

</style>
