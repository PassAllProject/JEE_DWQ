<template>
  <div class="login-reminder-box">
    <h2>温馨提示</h2>
    <p></p>
    <a @click="goLogin" class="login-link">后台</a>
    <a @click="logout" class="login-link">退出</a>
  </div>
</template>
<script>
import axios from "axios";
import {logout} from "@/net/index.js";
import router from "@/router/index.js";

export default {
  name: 'NotLoggedIn',
  methods:{
    goLogin(){
      location.href='/admin'
    },
    logout(){
      logout(()=>{router.push('/')})
    }
  }
}
</script>
<style>
.login-reminder-box {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  text-align: center;
}

.login-reminder-box h2 {
  color: #333;
  margin-bottom: 15px;
}

.login-reminder-box p {
  color: #666;
  margin-bottom: 20px;
}

.login-link {
  color: #535353;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

.login-link:hover {
  background-color: gray;
}
</style>
