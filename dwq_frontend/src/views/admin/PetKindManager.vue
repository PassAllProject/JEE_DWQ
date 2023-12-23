<template>
  <div>
    <!-- 新增宠物种类按钮 -->
    <el-button type="primary" @click="addNewPetKind">新增宠物种类</el-button>

    <!-- 宠物种类表格 -->
    <el-table :data="petKinds" style="width: 100%">
      <el-table-column prop="kindId" label="种类ID" width="120"></el-table-column>
      <el-table-column prop="kindName" label="类别名">
        <template v-slot="scope">
          <el-input v-if="scope.row.isEditing" v-model="scope.row.kindName" placeholder="类别名"></el-input>
          <span v-else>{{ scope.row.kindName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button size="mini" v-if="!scope.row.isEditing" @click="editRow(scope.row)">编辑</el-button>
          <el-button size="mini" v-if="scope.row.isEditing" type="success" @click="saveRow(scope.row)">保存</el-button>
          <el-button size="mini" v-if="!scope.row.isEditing" type="danger" @click="deletePetKind(scope.row.kindId)">删除</el-button>
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
      petKinds: [],
    };
  },
  methods: {
    fetchPetKinds() {
      axios.get('/api/auth/petKinds').then(response => {
        this.petKinds = response.data.map(kind => ({ ...kind, isEditing: false, isNew: false }));
      });
    },
    addNewPetKind() {
      this.petKinds.unshift({
        kindId: null,
        kindName: '',
        isEditing: true,
        isNew: true
      });
    },
    editRow(kind) {
      kind.isEditing = true;
    },
    saveRow(kind) {
      const url = kind.isNew ? '/api/auth/petKinds' : `/api/auth/petKinds/${kind.kindId}`;
      axios[kind.isNew ? 'post' : 'put'](url, kind).then(() => {
        this.fetchPetKinds();
      });
    },
    deletePetKind(kindId) {
      axios.delete(`/api/auth/petKinds/${kindId}`).then(() => {
        this.fetchPetKinds();
      });
    },
    cancelEdit(kind) {
      if (kind.isNew) {
        this.petKinds.shift();
      } else {
        kind.isEditing = false;
      }
    }
  },
  mounted() {
    this.fetchPetKinds();
  }
};
</script>
