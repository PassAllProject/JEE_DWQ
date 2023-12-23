<template>
  <div>
    <!-- 查询表单 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="收货人">
        <el-input v-model="search.consignee" placeholder="姓名"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchAddresses">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 新增地址按钮 -->
    <el-button type="primary" @click="addNewAddress">新增地址</el-button>

    <!-- 地址表格 -->
    <el-table :data="addresses" style="width: 100%">
      <el-table-column prop="addressId" label="地址ID" width="120"></el-table-column>
      <el-table-column prop="userId" label="用户ID" width="120">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model="scope.row.userId" placeholder="用户ID"></el-input>
          <span v-else>{{ scope.row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="consignee" label="收货人">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model="scope.row.consignee" placeholder="收货人"></el-input>
          <span v-else>{{ scope.row.consignee }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="address" label="详细地址">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model="scope.row.address" placeholder="详细地址"></el-input>
          <span v-else>{{ scope.row.address }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button size="mini" v-if="!scope.row.isEditing" @click="editRow(scope.row)">编辑</el-button>
          <el-button size="mini" v-if="scope.row.isEditing" type="success" @click="saveRow(scope.row)">保存</el-button>
          <el-button size="mini" v-if="!scope.row.isEditing && !scope.row.isNew" type="danger" @click="handleDelete(scope.row.addressId)">删除</el-button>
          <el-button size="mini" v-if="scope.row.isEditing || scope.row.isNew" type="info" @click="cancelEdit(scope.row)">取消</el-button>
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
      search: {
        consignee: ''
      },
      addresses: [],
    };
  },
  methods: {
    fetchAddresses() {
      axios.get('/api/auth/addresses', { params: this.search }).then(response => {
        this.addresses = response.data.map(address => ({ ...address, isEditing: false, isNew: false }));
      });
    },
    addNewAddress() {
      this.addresses.unshift({
        addressId: null,
        userId: '',
        consignee: '',
        address: '',
        isEditing: true,
        isNew: true
      });
    },
    editRow(row) {
      row.isEditing = true;
    },
    saveRow(row) {
      const url = row.isNew ? '/api/auth/addresses' : `/api/auth/addresses/${row.addressId}`;
      axios[row.isNew ? 'post' : 'put'](url, row).then(() => {
        this.fetchAddresses();
      });
    },
    handleDelete(addressId) {
      axios.delete(`/api/auth/addresses/${addressId}`).then(() => {
        this.fetchAddresses();
      });
    },
    cancelEdit(row) {
      if (row.isNew) {
        this.addresses.shift();
      } else {
        row.isEditing = false;
      }
    }
  },
  mounted() {
    this.fetchAddresses();
  }
};
</script>

<style>
/* 添加所需的样式 */
</style>
