<template>
  <Navigator @valueChanged="handleValueChange"/>
  <el-config-provider :locale="chang">
  <el-container style="height: 100vh;">
    <el-aside width="200px" class="aside-menu">
      <el-menu default-active="0" background-color="transparent" text-color="#333" active-text-color="#409EFF">
        <el-menu-item index="0" @click="goTo('')">首页</el-menu-item>
        <el-menu-item index="1" @click="goTo('address')">地址管理</el-menu-item>
        <el-menu-item index="2" @click="goTo('admin')">用户管理</el-menu-item>
        <el-menu-item index="3" @click="goTo('ai')">自助问答</el-menu-item>
        <el-menu-item index="4" @click="goTo('order-detail')">订单详情</el-menu-item>
        <el-menu-item index="5" @click="goTo('orders')">订单管理</el-menu-item>
        <el-menu-item index="6" @click="goTo('pet')">宠物管理</el-menu-item>
        <el-menu-item index="7" @click="goTo('pet-kind')">宠物种类</el-menu-item>
        <el-menu-item index="8" @click="goTo('review')">评价管理</el-menu-item>
        <el-menu-item index="9" @click="goTo('shopping')">购物管理</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <div class="path-display">{{ currentPath }}</div>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
  </el-config-provider>
</template>

<script>
import Navigator from "@/views/welcome/Navigator.vue";
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import en from 'element-plus/dist/locale/en.mjs'
import {computed} from "vue";
export default {
  components: { Navigator },
  data() {
    return {
      currentPath: '',
      childValue:'zh-cn'
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
      this.$router.push(`/admin/${route}`);
      this.currentPath = this.getPathName(route);
    },
    getPathName(route) {
      const pathMap = {
        '/admin/':'首页',
        '/admin/address': '地址管理',
        '/admin/admin': '用户管理',
        '/admin/ai': '自助问答',
        '/admin/order-detail': '订单详情',
        '/admin/orders': '订单管理',
        '/admin/pet': '宠物管理',
        '/admin/pet-kind': '宠物管理',
        '/admin/review': '评价管理',
        '/admin/shopping': '购物管理',
        '/admin/center': '用户详情',
        '/admin/rest': '修改密码'
      };
      return pathMap[route]||this.currentPath;
    }
  },
  created() {
    this.currentPath = this.getPathName(this.$route.path);
  },
  computed:{
    chang(){
      return this.childValue === 'zh-cn' ? zhCn : en
    }
  }
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
</style>
