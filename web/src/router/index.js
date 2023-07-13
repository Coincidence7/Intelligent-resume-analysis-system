import { createRouter, createWebHashHistory } from 'vue-router'
import UserLoginView from "@/views/user/account/UserLoginView";
import UserRegisterView from "@/views/user/account/UserRegisterView";
import mainPage from "@/views/mainPage";
import TestView from "@/views/TestView";

import OverviewView from "@/views/1/OverviewView";
import ResumeImportView from "@/views/2/ResumeImportView";
import PersonPostMatchView from "@/views/2/PersonPostMatchView";
import HistoryView from "@/views/2/HistoryView";
import TalentPoolView from "@/views/3/TalentPoolView";
import EmailSettingView from "@/views/4/EmailSettingView";
import PostManageView from "@/views/4/PostManageView";
import AppSettingView from "@/views/5/AppSettingView";
import NotFoundView from "@/views/NotFoundView";
import AnalyseWaitingView from "@/views/2/AnalyseWaitingView";
import AnalyseResultView from "@/views/2/AnalyseResultView";

const routes = [
  {
    path: "/",
    name: "index",
    redirect:"/Login",
  },
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
    path: '/main',
    name: 'mainPage',
    component: mainPage
  },
  {
    path: '/test',
    name: 'testPage',
    component: TestView
  },

  {
    path: "/OverViewView",
    name: "overview",
    component: OverviewView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/ResumeImportView",
    name: "resume_import",
    component: ResumeImportView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/AnalyseWaitingView",
    name: "analyse_waiting",
    component: AnalyseWaitingView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/analyseResultView",
    name: "analyse_result",
    component: AnalyseResultView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/PersonPostMatchView",
    name: "person_post_match",
    component: PersonPostMatchView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/HistoryView",
    name: "history",
    component: HistoryView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/TalentPoolView",
    name: "talent_pool",
    component: TalentPoolView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/EmailSettingView",
    name: "email_setting",
    component: EmailSettingView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/PostManageView",
    name: "post_manage",
    component: PostManageView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/AppSettingView",
    name: "app_setting",
    component: AppSettingView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/NotFoundView",
    name: "404_page",
    component: NotFoundView,
    meta: {
      requestAuth: false
    }
  },
  {
    path: "/:pathMatch(.*)",
    redirect: "/NotFoundView"
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
