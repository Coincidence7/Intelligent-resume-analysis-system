<template>
    <el-menu
        class="el-navbar"
        mode="horizontal"
        background-color="#FBFBFB"
        :ellipsis="false"
    >
        <div class="logo-text">
            <el-button
                class="back-btn"
                size="large"
                type="info"
                :icon="Back" plain
                @click="pageBack"
            />
            <strong id="title">GustingBamboo - 智能简历分析系统</strong>
        </div>
        <div class="flex-grow" />
        <el-menu-item index="1">尊敬的{{store.state.user.username}}，您好!</el-menu-item>
        <el-menu-item index="2">
            <el-popover
                :width=300
                :offset=30
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
                        <span style="font-size: 20px">{{ store.state.user.username }}</span>
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
                                    <el-input v-model="form.username" placeholder="请输入用户名" autocomplete="off" />
                                </el-form-item>
                                <el-form-item label="用户密码" label-width="5rem">
                                    <el-input v-model="form.password" placeholder="请输入密码" type="password" show-password autocomplete="off" />
                                </el-form-item>
                                <el-form-item label="密码确认" label-width="5rem">
                                    <el-input v-model="form.comfirmedpassword" placeholder="请再次输入密码" type="password" show-password autocomplete="off" />
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
import { useStore } from 'vuex'

// import router from '@/router/index'

import "@/assets/font/font.css";
import $ from 'jquery'
import {ElNotification} from "element-plus";

export default {
    name: "NavBar",
    components: {
    },

    setup(){
      const store = useStore();
      // let username = ref('');
      // let password = ref('');
      // let confirmedPassword = ref('');
        const {Back, UserFilled} = require('@element-plus/icons-vue');
        const form = reactive({
            username: '',
            password: '',
            comfirmedpassword: ''
        });

        let isLogin = ref(false);
        let isRegister = ref(false);
        let error_msg = ref(' ');
        const pageBack = () => {
            window.location.href="javascript:history.go(-1)";
        }

      const login = () => {
        console.log(form.username)
        if (form.username === '' || form.password === ''){
          ElNotification({
            title: 'Error',
            message: '登陆信息不可为空!',
            type: 'error',
          });
          return;
        }
        error_msg.value = " ";
        store.dispatch("login", {
          username: form.username,
          password: form.password,
          success(resp) {
            console.log("登录成功", resp)
            ElNotification({
              title: 'Success',
              message: '登陆成功,下午好!',
              type: 'success',
            });
            isLogin.value = true
            store.commit("showUser",{
              username: form.username,
              is_login: true
            })
          },
          error() {
            error_msg.value = "用户名或密码错误";
            ElNotification({
              title: 'Error',
              message: '用户名或密码错误，请检查后重新输入!',
              type: 'error',
            });
          }
        })
      }
      const register = () => {
          if (form.username === '' || form.password === '' || form.comfirmedpassword === ''){
            ElNotification({
              title: 'Error',
              message: '注册信息不完整，请输入完整的注册信息!',
              type: 'error',
            });
            return;
          }
          if(form.password !== form.comfirmedpassword){
            ElNotification({
              title: 'Error',
              message: '两次输入的密码不相同，请检查后重新输入!',
              type: 'error',
            });
            return;
          }
          $.ajax({
            url: "http://127.0.0.1:3000/user/account/register/",
            type: 'post',
            data: {
              username: form.username,
              password: form.password,
              confirmedPassword: form.comfirmedpassword,
            },
            success(resp) {
              console.log(resp)
              error_msg.value = resp.error_message;
              ElNotification({
                title: 'Success',
                message: '注册成功',
                type: 'success',
              });
            },
            error(resp) {
              console.log(resp);
              ElNotification({
                title: 'Error',
                message: '注册信息冲突，请更换用户名!',
                type: 'error',
              });
            }
          });

        }
        const logout = () => {
          isLogin.value = false;
          ElNotification({
            title: 'Info',
            message: '用户已登出，请工作结束后关闭该会话',
            type: 'info',
          });
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
            pageBack,
            login,
            register,
            logout,
            toggle,
            store
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
