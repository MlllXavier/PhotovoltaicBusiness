import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import 'element-plus/es/components/message/style/css'
import VueParticles from 'vue-particles'  

const app = createApp(App)
app.use(router)
app.use(store)
app.mount('#app')
app.use(VueParticles)