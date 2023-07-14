import { createStore } from 'vuex'

import ModuleUser from './user';
import ModuleCellResume from './cell_resume';
import ModuleResumeInfo from './resume_info';
export default createStore({
  state: {
    httpUrl: 'http://127.0.0.1:3000/',
    httpUrlRes: 'http://127.0.0.1:3002/'
    // httpUrl: 'http://192.168.137.247:3000/'
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: ModuleUser,
    cell_resume: ModuleCellResume,
    resume_info: ModuleResumeInfo,
  }
})
