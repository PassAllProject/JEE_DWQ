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
  <el-container style="height: 100vh;">
    <el-aside width="200px" class="aside-menu">
      <el-menu default-active="0" background-color="transparent" text-color="#333" active-text-color="#409EFF">
        <el-menu-item index="0">首页</el-menu-item>
        <el-menu-item index="1" @click="goTo('shopping')">购物管理</el-menu-item>
        <el-menu-item index="2" @click="goTo('order-detail')">订单详情</el-menu-item>
        <el-menu-item index="3" @click="goTo('ai')">自助问答</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-container>
        <el-header class="header">
          <div class="path-display">{{ currentPath }}</div>
        </el-header>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>

  </el-container>


</template>

<script>
import Navigator from "@/views/welcome/Navigator.vue";
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import en from 'element-plus/dist/locale/en.mjs'
import {computed} from "vue";
import App from "@/App.vue";
import router from "@/router/index.js";
import {logout} from "@/net/index.js";
export default {
  components: { Navigator },
  data() {
    return {
      currentPath: '',
      childValue:'zh-cn',
      currentLanguage: '中文',
      username: localStorage.getItem('username'),
    };
  },
  watch: {
    $route(to, from) {
      this.currentPath = this.getPathName(to.path);
    }
  },
  methods: {
    handleValueChange(value) {
      this.childValue = value;
    },
    goTo(route) {
      this.$router.push(`/user/${route}`);
      this.currentPath = this.getPathName(route);
    },
    getPathName(route) {
      const pathMap = {
        '/user/index':'首页',
        '/user/ai': '自助问答',
        '/user/order-detail': '订单详情',
        '/user/review': '评价管理',
        '/user/shopping': '购物管理',
        '/user/center': '用户详情',
        '/user/rest': '修改密码'
      };
      return pathMap[route]||this.currentPath;
    },
    toggleLanguage() {
      this.currentLanguage = this.currentLanguage === '中文' ? 'English' : '中文';
      if(this.currentLanguage==='中文') this.$emit('valueChanged', 'zh-cn');
      else  this.$emit('valueChanged', 'en');
    },
    goToUserCenter() {
      router.push('/user/center')
    },
    changePassword() {
      router.push('/user/rest')
    },
    exit() {
      localStorage.removeItem('username')
      logout( () => router.push("/"))
    },
  },
  created() {
    this.currentPath = this.getPathName(this.$route.path);
  },
  computed:{
    chang(){
      return this.childValue === 'zh-cn' ? zhCn : en
    }
  },


};
</script>

<style>
.aside-menu {
  background-color: #fff;
  border-right: 1px solid #ebeef5;
}
.header {
  background-color: #fff;
  color: #333;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #ebeef5;
  margin-top: 5px;
}
.path-display {
  font-size: 16px;
  color: #333;
}
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
