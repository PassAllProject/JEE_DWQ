<template>
  <div>
    <!-- 账户表格 -->
    <el-table :data="accounts" style="width: 100%">
      <el-table-column prop="id" label="ID" width="120"></el-table-column>
      <el-table-column prop="username" label="用户名">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model="scope.row.username" placeholder="用户名"></el-input>
          <span v-else>{{ scope.row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="email" label="邮箱">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model="scope.row.email" placeholder="邮箱"></el-input>
          <span v-else>{{ scope.row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="role" label="角色">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model="scope.row.role" placeholder="角色"></el-input>
          <span v-else>{{ scope.row.role }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="registerTime" label="注册时间">
        <template v-slot="scope">
          {{ formatTime(scope.row.registerTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button size="mini" v-if="!scope.row.isEditing" @click="editRow(scope.row)">编辑</el-button>
          <el-button size="mini" v-if="scope.row.isEditing" type="success" @click="saveRow(scope.row)">保存</el-button>
          <el-button size="mini" v-if="!scope.row.isEditing" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          <el-button size="mini" v-if="scope.row.isEditing" type="info" @click="cancelEdit(scope.row)">取消</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      accounts: [],
    };
  },
  methods: {
    fetchAccounts() {
      axios.get('/api/auth/search')
          .then(response => {
            this.accounts = response.data.map(account => ({ ...account, isEditing: false }));
          })
          .catch(error => {
            this.$message.error('获取账户失败');
            console.error('Error fetching accounts:', error);
          });
    },
    formatTime(time) {
      if (!time) return '';
      const date = new Date(time);
      return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
    },
    editRow(row) {
      row.isEditing = true;
    },
    saveRow(row) {
      axios.put(`/api/auth/update/${row.id}`, row)
          .then(() => {
            this.$message.success('账户更新成功');
            row.isEditing = false;
          })
          .catch(error => {
            this.$message.error('账户更新失败');
            console.error('更新账户失败:', error);
          });
    },
    handleDelete(id) {
      this.$confirm('确定要删除这个账户吗？', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.delete(`/api/auth/delete/${id}`)
            .then(() => {
              this.$message.success('删除成功');
              this.fetchAccounts();
            })
            .catch(error => {
              this.$message.error('删除失败');
              console.error('删除失败:', error);
            });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
    cancelEdit(row) {
      if (row.isEditing) {
        if (row.id) {
          // 如果是已存在的账户，取消编辑并恢复原始数据
          this.fetchAccounts();
        } else {
          // 如果是新添加的账户，直接移除
          const index = this.accounts.indexOf(row);
          if (index > -1) {
            this.accounts.splice(index, 1);
          }
        }
        row.isEditing = false;
      }
    }
  },
  mounted() {
    this.fetchAccounts();
  }
};
</script>

<style>
/* 添加所需的样式 */
</style>
