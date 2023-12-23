<template>
  <div>
    <!-- 订单表格 -->
    <el-table :data="orderDetails" style="width: 100%">
      <el-table-column prop="detailId" label="订单详情ID" width="120"></el-table-column>
      <el-table-column prop="orderId" label="订单ID" width="120"></el-table-column>
      <el-table-column prop="petId" label="宠物ID" width="120"></el-table-column>
      <el-table-column prop="quantity" label="数量" width="120"></el-table-column>
      <el-table-column prop="price" label="单价">
        <template v-slot="scope">
          <span>{{ scope.row.price }}</span>
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
      orderDetails: [],
    };
  },
  methods: {
    async fetchOrders() {
      const user= await axios.get('/api/auth/one?username=' + localStorage.getItem('username'));
      const orders = await axios.get('/api/auth/orders');
      const orderDetailResponse = await axios.get('/api/auth/orderDetails');
      for (let j = 0; j < orders.data.length; j++) {
        if (user.data.id == orders.data[j].userId) {
          for (let k = 0; k < orderDetailResponse.data.length; k++) {
            if (orders.data[j].orderId == orderDetailResponse.data[k].orderId) {
              this.orderDetails.push(orderDetailResponse.data[k])
            }
          }
        }
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
