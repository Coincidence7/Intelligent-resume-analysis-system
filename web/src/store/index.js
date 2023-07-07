import { createStore } from 'vuex'

import ModuleUser from './user';
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
  }
})
