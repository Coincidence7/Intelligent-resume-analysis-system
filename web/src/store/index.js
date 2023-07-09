import { createStore } from 'vuex'

import ModuleUser from './user';
import ModuleCellResume from './cell_resume';
export default createStore({
  state: {
    httpUrl: 'http://127.0.0.1:3000/'
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: ModuleUser,
    cell_resume: ModuleCellResume
  }
})
