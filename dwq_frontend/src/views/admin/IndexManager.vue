<template>
  <div ref="chart" style="width: 600px; height: 400px;"></div>
</template>

<script>
import * as echarts from 'echarts';
import axios from "axios";

export default {
  name: 'MyChart',
  mounted() {
    this.initChart();
  },
  methods: {
    async initChart() {
      // 基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(this.$refs.chart);
      const user= await axios.get('/api/auth/search');
      const orders = await axios.get('/api/auth/orders');
      const ai = await axios.get("/api/auth/ai");
      const pets = await axios.get("/api/auth/pets?name=-1");

      const reviews = await axios.get("/api/auth/reviews/");
      console.log(reviews)

      // 指定图表的配置项和数据
      const option = {
        title: {
          text: '数据分析'
        },
        tooltip: {},
        xAxis: {
          data: ['用户总数', '订单总数', '评价总数', '答疑总数', '宠物总数']
        },
        yAxis: {},
        series: [{
          name: '数量',
          type: 'bar',
          data: [user.data.length, orders.data.length, reviews.data.length, ai.data.length, pets.data.data.total]
        }]
      };

      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    }
  }
};
</script>
