import { createApp } from 'vue'
import App from './App.vue'
import router from "./router";
import axios from "axios";
import 'element-plus/theme-chalk/dark/css-vars.css'
import ElementPlus from 'element-plus';
axios.defaults.baseURL = 'http://localhost:8080'
const app=createApp(App)
app.config.productionTip = false;
app.use(router)
app.use(ElementPlus)
app.mount('#app')