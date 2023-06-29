import { createRouter, createWebHashHistory } from 'vue-router'
import UserLoginView from "@/views/user/account/UserLoginView";
import UserRegisterView from "@/views/user/account/UserRegisterView";
import mainPage from "@/views/mainPage";
import Home from "@/views/Home";
const routes = [
  {
    path: '/Login',
    name: 'UserLoginView',
    component: UserLoginView
  },
  {
    path: '/Register',
    name: 'UserRegisterView',
    component: UserRegisterView
  },
  {
    path: '/Home',
    name: 'Home',
    component: Home
  },
  {
    path: '/main',
    name: 'mainPage',
    component: mainPage
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
