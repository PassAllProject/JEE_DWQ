<template>
  <el-card class="user-details" v-if="user">
    <div slot="header" class="clearfix">
      <span>个人信息</span>
    </div>
    <el-descriptions>
      <el-descriptions-item label="ID">{{ user.id }}</el-descriptions-item>
      <el-descriptions-item label="用户名">{{ user.username }}</el-descriptions-item>
      <el-descriptions-item label="密码">{{ user.password }}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{ user.email }}</el-descriptions-item>
      <el-descriptions-item label="角色">{{ user.role }}</el-descriptions-item>
      <el-descriptions-item label="注册时间">{{ formattedRegisterTime }}</el-descriptions-item>
    </el-descriptions>
  </el-card>
  <div v-else>
    加载中...
  </div>
</template>

<script>
import axios from 'axios';
import { ElCard, ElDescriptions, ElDescriptionsItem } from 'element-plus';

export default {
  name: "UserDetail",
  components: {
    ElCard,
    ElDescriptions,
    ElDescriptionsItem
  },
  data() {
    return {
      user: null
    };
  },
  computed: {
    formattedRegisterTime() {
      return this.user ? new Date(this.user.registerTime).toLocaleString() : '';
    }
  },
  created() {
    this.fetchUserData();
  },
  methods: {
    fetchUserData() {
      axios.get('/api/auth/one?username=' + localStorage.getItem('username'))
          .then(response => {
            this.user = response.data;
          })
          .catch(error => {
            console.error('There was an error fetching the user data:', error);
          });
    }
  }
};
</script>

<style>
.user-details {
  margin: 20px;
}
</style>
