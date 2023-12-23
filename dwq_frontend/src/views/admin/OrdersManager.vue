<template>
  <div>
    <!-- 查询表单 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="订单号">
        <el-input v-model.number="search.orderId" placeholder="订单ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchOrders">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 新增订单按钮 -->
    <el-button type="primary" @click="addNewOrder">新增订单</el-button>

    <!-- 订单表格 -->
    <el-table :data="orders" style="width: 100%">
      <el-table-column prop="orderId" label="订单ID" width="120"></el-table-column>
      <el-table-column prop="userId" label="用户ID" width="120">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model.number="scope.row.userId" placeholder="用户ID"></el-input>
          <span v-else>{{ scope.row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="orderTime" label="订单时间" width="180">
        <template v-slot="scope">
          <span>{{ formatDate(scope.row.orderTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="totalPrice" label="总价">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model.number="scope.row.totalPrice" placeholder="总价"></el-input>
          <span v-else>{{ scope.row.totalPrice }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button size="mini" v-if="!scope.row.isEditing" @click="editRow(scope.row)">编辑</el-button>
          <el-button size="mini" v-if="scope.row.isEditing" type="success" @click="saveRow(scope.row)">保存</el-button>
          <el-button size="mini" v-if="!scope.row.isEditing" type="danger" @click="deleteOrder(scope.row.orderId)">删除</el-button>
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
        orderId: null
      },
      orders: [],
    };
  },
  methods: {
    fetchOrders() {
      axios.get('/api/auth/orders', { params: this.search }).then(response => {
        this.orders = response.data.map(order => ({ ...order, isEditing: false, isNew: false }));
      });
    },
    addNewOrder() {
      this.orders.unshift({
        orderId: null,
        userId: null,
        orderTime: new Date().toISOString(), // 自动生成当前时间
        totalPrice: null,
        isEditing: true,
        isNew: true
      });
    },
    editRow(order) {
      order.isEditing = true;
    },
    saveRow(order) {
      const url = order.isNew ? '/api/auth/orders' : `/api/auth/orders/${order.orderId}`;
      axios[order.isNew ? 'post' : 'put'](url, order).then(() => {
        this.fetchOrders();
      });
    },
    deleteOrder(orderId) {
      axios.delete(`/api/auth/orders/${orderId}`).then(() => {
        this.fetchOrders();
      });
    },
    cancelEdit(order) {
      if (order.isNew) {
        this.orders.shift();
      } else {
        order.isEditing = false;
      }
    },
    formatDate(dateString) {
      return moment(dateString).format('YYYY-MM-DD HH:mm:ss');
    }
  },
  mounted() {
    this.fetchOrders();
  }
};
</script>

<style>
/* 添加所需的样式 */
</style>
