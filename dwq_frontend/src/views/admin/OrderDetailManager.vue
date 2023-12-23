<template>
  <div>
    <!-- 查询表单 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="订单ID">
        <el-input v-model.number="search.detailId" placeholder="订单ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchOrderDetails">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 新增订单详情按钮 -->
    <el-button type="primary" @click="addNewOrderDetail">新增订单详情</el-button>

    <!-- 订单详情表格 -->
    <el-table :data="orderDetails" style="width: 100%">
      <el-table-column prop="detailId" label="订单详情ID" width="120"></el-table-column>
      <el-table-column prop="orderId" label="订单ID" width="120">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model.number="scope.row.orderId" placeholder="订单ID"></el-input>
          <span v-else>{{ scope.row.orderId }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="petId" label="宠物ID" width="120">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model.number="scope.row.petId" placeholder="宠物ID"></el-input>
          <span v-else>{{ scope.row.petId }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="quantity" label="数量">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model.number="scope.row.quantity" placeholder="数量"></el-input>
          <span v-else>{{ scope.row.quantity }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="price" label="单价">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model.number="scope.row.price" placeholder="单价"></el-input>
          <span v-else>{{ scope.row.price }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button size="mini" v-if="!scope.row.isEditing" @click="editRow(scope.row)">编辑</el-button>
          <el-button size="mini" v-if="scope.row.isEditing" type="success" @click="saveRow(scope.row)">保存</el-button>
          <el-button size="mini" v-if="!scope.row.isNew" type="danger" @click="deleteOrderDetail(scope.row.detailId)">删除</el-button>
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
        detailId: null
      },
      orderDetails: [],
    };
  },
  methods: {
    fetchOrderDetails() {
      console.log(this.search)
      axios.get('/api/auth/orderDetails', { params: this.search }).then(response => {
        this.orderDetails = response.data.map(detail => ({ ...detail, isEditing: false, isNew: false }));
      });
    },
    addNewOrderDetail() {
      this.orderDetails.unshift({
        detailId: null,
        orderId: null,
        petId: null,
        quantity: null,
        price: null,
        isEditing: true,
        isNew: true
      });
    },
    editRow(detail) {
      detail.isEditing = true;
    },
    saveRow(detail) {
      const url = detail.isNew ? '/api/auth/orderDetails' : `/api/auth/orderDetails/${detail.detailId}`;
      axios[detail.isNew ? 'post' : 'put'](url, detail).then(() => {
        this.fetchOrderDetails();
      });
    },
    deleteOrderDetail(detailId) {
      axios.delete(`/api/auth/orderDetails/${detailId}`).then(() => {
        this.fetchOrderDetails();
      });
    },
    cancelEdit(detail) {
      if (detail.isNew) {
        this.orderDetails.shift();
      } else {
        detail.isEditing = false;
      }
    }
  },
  mounted() {
    this.fetchOrderDetails();
  }
};
</script>

<style>
/* 添加所需的样式 */
</style>
