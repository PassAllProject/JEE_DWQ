<template>
  <div>
    <!-- 查询表单 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="评价ID">
        <el-input v-model.number="search.reviewId" placeholder="评价ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchReviews">查询</el-button>
      </el-form-item>
    </el-form>

    <!-- 新增评价按钮 -->
    <el-button type="primary" @click="addNewReview">新增评价</el-button>

    <!-- 评价表格 -->
    <el-table :data="reviews" style="width: 100%">
      <el-table-column prop="reviewId" label="评价ID" width="120"></el-table-column>
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
      <el-table-column prop="rating" label="评分">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model.number="scope.row.rating" placeholder="评分"></el-input>
          <span v-else>{{ scope.row.rating }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="reviewDate" label="评价日期" width="180">
        <template v-slot="scope">
          <span>{{ formatDate(scope.row.reviewDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button size="mini" v-if="!scope.row.isEditing" @click="editRow(scope.row)">编辑</el-button>
          <el-button size="mini" v-if="scope.row.isEditing" type="success" @click="saveRow(scope.row)">保存</el-button>
          <el-button size="mini" v-if="!scope.row.isEditing" type="danger" @click="deleteReview(scope.row.reviewId)">删除</el-button>
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
        reviewId:''
      },
      reviews: [],
    };
  },
  methods: {
    fetchReviews() {
      axios.get(`/api/auth/reviews/${this.search.reviewId}`).then(response => {
        this.reviews = response.data.map(review => ({ ...review, isEditing: false, isNew: false }));
      });
    },
    addNewReview() {
      this.reviews.unshift({
        reviewId: null,
        userId: null,
        petId: null,
        rating: null,
        reviewDate: new Date().toISOString(),
        isEditing: true,
        isNew: true
      });
    },
    editRow(review) {
      review.isEditing = true;
    },
    saveRow(review) {
      const url = review.isNew ? '/api/auth/reviews' : `/api/auth/reviews/${review.reviewId}`;
      axios[review.isNew ? 'post' : 'put'](url, review).then(() => {
        this.fetchReviews();
      });
    },
    deleteReview(reviewId) {
      axios.delete(`/api/auth/reviews/${reviewId}`).then(() => {
        this.fetchReviews();
      });
    },
    cancelEdit(review) {
      if (review.isNew) {
        this.reviews.shift();
      } else {
        review.isEditing = false;
      }
    },
    formatDate(dateString) {
      return moment(dateString).format('YYYY-MM-DD HH:mm:ss');
    }
  },
  mounted() {
    this.fetchReviews();
  }
};
</script>
