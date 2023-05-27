import { createStore } from 'vuex'
import jsCookie from 'js-cookie'

export default createStore({
  state: {
    isCollapse: true,
    token: '',
    isFile: false
  },
  getters: {
  },
  mutations: {
    collapseMenu(state){
      state.isCollapse = !state.isCollapse
    },
    setToken(state, value){
      state.token = value
      jsCookie.set('token', value)
    },
    getToken(state){
      state.token = state.token || jsCookie.get('token')
    },
    claerToken(state){
      state.token = ''
      jsCookie.remove('token')
    },
  },
  actions: {
  },
  modules: {
  }
})
