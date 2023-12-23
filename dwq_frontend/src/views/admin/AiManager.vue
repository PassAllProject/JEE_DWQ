<template>
  <div class="ai-crud">
    <el-form :model="aiData" label-width="120px"  v-show="isView">
      <el-form-item label="ID" v-if="isEditing">
        <el-input v-model="aiData.id" disabled></el-input>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="aiData.username"></el-input>
      </el-form-item>
      <el-form-item label="提问">
        <el-input v-model="aiData.promote"></el-input>
      </el-form-item>
      <el-form-item label="答案">
        <el-input v-model="aiData.answer" :disabled="!isEditing"></el-input>
      </el-form-item>
      <el-form-item label="时间">
        <el-date-picker v-model="aiData.time" type="datetime"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveData">保存</el-button>
        <el-button @click="cancelEdit">取消</el-button>
      </el-form-item>
    </el-form>
    <el-button @click="addNew">新增</el-button>
    <el-button @click="fetchData">查询</el-button>
    <!-- 表格显示 Ai 数据 -->
    <el-table :data="aiList" style="width: 100%">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="username" label="用户名"></el-table-column>
      <el-table-column prop="promote" label="提问"></el-table-column>
      <el-table-column prop="answer" label="答案"></el-table-column>
      <el-table-column label="时间" width="180">
        <template #default="scope">
          {{ formatDate(scope.row.time) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="mini" @click="editData(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deleteData(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';
export default {
  name: "root",
  data() {
    return {
      aiData: {
        id: null,
        username: '',
        promote: '',
        answer: '',
        time: null,
      },
      isView:false,
      aiList: [],
      isEditing: false,
    };
  },
  methods: {
    formatDate(date) {
      return moment(date).format('YYYY-MM-DD HH:mm:ss');
    },
    addNew() {
      this.aiData = { id: null, username: '', promote: '', answer: '', time: null };
      this.isEditing = false;
      this.isView=true
    },
    async saveData() {
      try {
        if(!this.isEditing){
          await axios.post('http://43.157.28.135:3000/getai', {
            prompt: this.aiData.promote}).then(response=>{
            this.aiData.answer=response.data.generatedText.message.content
          })
        }
        const response = this.isEditing
            ? await axios.put(`/api/auth/ai/${this.aiData.id}`, this.aiData)
            : await axios.post('/api/auth/ai', this.aiData);
        this.aiData=''
        this.isView=false
        this.fetchData()
      } catch (error) {
        console.error('保存失败', error);
      }
    },
    async deleteData(row) {
      try {
        await axios.delete(`/api/auth/ai/${row.id}`);
        console.log('删除成功');
        this.fetchData()
      } catch (error) {
        console.error('删除失败', error);
      }
    },
    async fetchData() {
      try {
        const response = await axios.get(`/api/auth/ai`);
        this.aiList = response.data;
        console.log(this.aiList);
      } catch (error) {
        console.error('查询失败', error);
      }
    },
    cancelEdit() {
      this.isView=false
      console.log('取消编辑');
    },
    editData(row) {
      this.aiData = { ...row };
      this.isEditing = true;
      this.isView=true
    },
  },
  mounted() {
    this.fetchData();
  },
};
</script>

<style scoped>

</style>