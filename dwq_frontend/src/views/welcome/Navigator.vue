<template>
  <nav class="navbar">
    <div class="navbar-content">
      <span class="nav-button" @click="toggleLanguage">{{ currentLanguage }}</span>
      <div class="user-menu">
        <span class="username">{{ username }}</span>
        <div class="user-dropdown">
          <a @click="goToUserCenter">用户中心</a>
          <a @click="changePassword">修改密码</a>
          <a @click="exit">退出登录</a>
        </div>
        <span class="nav-button"></span>
        <span class="nav-button"></span>
        <span class="nav-button"></span>
        <span class="nav-button"></span>
      </div>
    </div>
  </nav>
</template>

<script>
import App from "@/App.vue";
import {logout} from "@/net/index.js";
import router from "@/router/index.js";
export default {
  components: {App},
  data() {
    return {
      currentLanguage: '中文',
      username: localStorage.getItem('username'),
    };
  },
  methods: {
    toggleLanguage() {
      this.currentLanguage = this.currentLanguage === '中文' ? 'English' : '中文';
      if(this.currentLanguage==='中文') this.$emit('valueChanged', 'zh-cn');
      else  this.$emit('valueChanged', 'en');
    },
    goToUserCenter() {
      router.push('/admin/center')
    },
    changePassword() {
      router.push('/admin/rest')
    },
    exit() {
      localStorage.removeItem('user')
      logout( () => router.push("/"))
    },
  },
};
</script>

<style>
.navbar {
  padding: 8px 16px;
}

.navbar-content {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.navbar-content > * {
  margin-left: 12px;
}

.username {
  font-weight: bold;
  color: #333;
  cursor: pointer;
}

.user-menu {
  position: relative;
  display: inline-block;
}

.user-dropdown {
  display: none;
  position: absolute;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.user-dropdown a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.user-dropdown a:hover {background-color: #f1f1f1}

.user-menu:hover .user-dropdown {
  display: block;
}

.nav-button {
  color: #333;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
}

.nav-button:hover {
  background-color: #f5f5f5;
  color: #000;
}
</style>
