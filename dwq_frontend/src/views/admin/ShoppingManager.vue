<template>
  <div>
    <!-- 查询表单 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="购物ID">
        <el-input v-model.number="search.shoppingId" placeholder="购物ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchShoppings">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 新增购物项按钮 -->
    <el-button type="primary" @click="addNewShopping">新增购物项</el-button>

    <!-- 购物项表格 -->
    <el-table :data="shoppings" style="width: 100%">
      <el-table-column prop="shoppingId" label="购物ID" width="120"></el-table-column>
      <el-table-column prop="userId" label="用户ID" width="120">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model.number="scope.row.userId" placeholder="用户ID"></el-input>
          <span v-else>{{ scope.row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="petId" label="宠物ID" width="120">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model.number="scope.row.petId" placeholder="宠物ID"></el-input>
          <span v-else>{{ scope.row.petId }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="totalPrice" label="总价">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model.number="scope.row.totalPrice" placeholder="总价"></el-input>
          <span v-else>{{ scope.row.totalPrice }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="加入时间" width="180">
        <template v-slot="scope">
          <span>{{ formatDate(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button size="mini" v-if="!scope.row.isEditing" @click="editRow(scope.row)">编辑</el-button>
          <el-button size="mini" v-if="scope.row.isEditing" type="success" @click="saveRow(scope.row)">保存</el-button>
          <el-button size="mini" v-if="!scope.row.isEditing" type="danger" @click="deleteShopping(scope.row.shoppingId)">删除</el-button>
          <el-button size="mini" v-if="scope.row.isEditing" type="info" @click="cancelEdit(scope.row)">取消</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import axios from 'axios';
import moment from 'moment';

export default {
  data() {
    return {
      search: {
        shoppingId:''
      },
      shoppings: [],
    };
  },
  methods: {
    fetchShoppings() {
      axios.get(`/api/auth/shoppings/${this.search.shoppingId}`).then(response => {
        this.shoppings = response.data.map(shopping => ({ ...shopping, isEditing: false, isNew: false }));
      });
    },
    addNewShopping() {
      this.shoppings.unshift({
        shoppingId: null,
        userId: null,
        petId: null,
        totalPrice: null,
        createTime: new Date().toISOString(),
        isEditing: true,
        isNew: true
      });
    },
    editRow(shopping) {
      shopping.isEditing = true;
    },
    saveRow(shopping) {
      const url = shopping.isNew ? '/api/auth/shoppings' : `/api/auth/shoppings/${shopping.shoppingId}`;
      axios[shopping.isNew ? 'post' : 'put'](url, shopping).then(() => {
        this.fetchShoppings();
      });
    },
    deleteShopping(shoppingId) {
      axios.delete(`/api/auth/shoppings/${shoppingId}`).then(() => {
        this.fetchShoppings();
      });
    },
    cancelEdit(shopping) {
      if (shopping.isNew) {
        this.shoppings.shift();
      } else {
        shopping.isEditing = false;
      }
    },
    formatDate(dateString) {
      return moment(dateString).format('YYYY-MM-DD HH:mm:ss');
    }
  },
  mounted() {
    this.fetchShoppings();
  }
};
</script>
