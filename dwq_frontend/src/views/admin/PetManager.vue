<template>
  <div>
    <el-form inline>
      <el-form-item>
        <el-input v-model="searchQuery" placeholder="输入宠物名进行搜索"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchPets">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 新增宠物按钮 -->
    <el-button type="primary" @click="addPet">新增宠物</el-button>

    <el-table :data="pets" style="width: 100%">
      <el-table-column prop="name" label="宠物名" width="120"></el-table-column>
      <el-table-column prop="gender" label="性别" width="100"></el-table-column>
      <el-table-column prop="age" label="年龄" width="80"></el-table-column>
      <el-table-column prop="species" label="种类" width="120"></el-table-column>
      <el-table-column prop="temperament" label="性格" width="120"></el-table-column>
      <el-table-column prop="price" label="价格" width="100"></el-table-column>
      <el-table-column prop="avatar" label="头像" width="250"></el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button size="mini" @click="editPet(row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="deletePet(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalPets">
    </el-pagination>

    <template>
      <div>
        <el-button mb-2 @click="toggle">Switch Language</el-button>
        <br />

        <el-config-provider :locale="locale">
          <el-table mb-1 :data="[]" />
          <el-pagination :total="100" />
        </el-config-provider>
      </div>
    </template>

    <!-- 新增宠物对话框 -->
    <el-dialog :title="dialogMode === 'add' ? '新增宠物' : '编辑宠物'" v-model="showAddPetDialog">
      <el-form ref="petForm" :model="newPet" label-width="100px">
        <el-form-item label="宠物名">
          <el-input v-model="newPet.name"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="newPet.gender">
            <el-radio label="雄">雄</el-radio>
            <el-radio label="雌">雌</el-radio>
            <el-radio label="未知">未知</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model.number="newPet.age" type="number"></el-input>
        </el-form-item>
        <el-form-item label="种类">
          <el-input v-model="newPet.species"></el-input>
        </el-form-item>
        <el-form-item label="性格">
          <el-input v-model="newPet.temperament"></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input v-model.number="newPet.price" type="number"></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
              :key="uploadKey"
              class="avatar-uploader"
              :action="imageUrl('upload')"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              :on-remove="handleRemove"
              name="file"
          >
            <img v-if="newPet.avatar" :src="imageUrl('uploads/'+newPet.avatar)" class="avatar">
            <el-button v-else>点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="savePet">添加</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      showAddPetDialog: false,
      newPet: {
        name: '',
        gender: '',
        age: null,
        species: '',
        temperament: '',
        price: null,
        avatar: ''
      },
      remove_image:'',
      pets: [],
      dialogMode:'',
      uploadKey:0,
      searchQuery:'',
      currentPage: 1,
      pageSize: 10,
      totalPets: 0,
    };
  },
  methods: {
    imageUrl(name){
      return `http://116.62.32.192:3000/${name}`
    },
    fetchPets() {
      console.log()
      axios.get('/api/auth/pets',{params:{
          name: this.searchQuery,
          currentPage: this.currentPage,
          pageSize: this.pageSize
      }})
          .then(response => {
            this.pets = response.data.data.data;// 假设响应的数据是宠物数组
            this.totalPets=response.data.data.total
            console.log(this.pets)
          })
          .catch(error => {
            console.error('Error fetching pets:', error);
          });
    },
    addPet() {
      this.resetForm()
      this.dialogMode = 'add';
      this.showAddPetDialog = true;
    },
    editPet(row){
      this.newPet = { ...row };
      this.dialogMode = 'edit';
      this.showAddPetDialog = true;
    },
    savePet() {
      if (this.dialogMode === 'add') {
        // 新增宠物的逻辑
        axios.post('/api/auth/pets', this.newPet)
            .then(() => {
              this.showAddPetDialog = false;
              this.fetchPets(); // 重新获取宠物列表
            })
            .catch(error => {
              console.error('Error adding pet:', error);
            });
      } else if (this.dialogMode === 'edit') {
        // 编辑宠物的逻辑
        axios.put(`/api/auth/pets/${this.newPet.id}`, this.newPet)
            .then(() => {
              this.showAddPetDialog = false;
              this.fetchPets(); // 重新获取宠物列表
            })
            .catch(error => {
              console.error('Error updating pet:', error);
            });
      }
    },
    handleAvatarSuccess(response) {
      this.remove_image=response.filename
      this.newPet.avatar =response.filename; // 假设响应中包含图片的 URL
    },
    beforeAvatarUpload(file) {
      // 可以在这里添加上传前的检查逻辑
    },
    handleRemove() {
      axios.post('http://116.62.32.192:3000/deleteFile', { fileName: this.remove_image })
          .then(response => {
            this.newPet.avatar=''
          })
          .catch(error => {
            console.error('删除文件失败', error);
          });
    },
    deletePet(pet) {
      this.$confirm('确定要删除这个宠物吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.post('http://116.62.32.192:3000/deleteFile', { fileName: pet.avatar })
        axios.delete(`/api/auth/pets/${pet.id}`)
            .then(() => {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.fetchPets(); // 重新获取宠物列表
            })
            .catch(error => {
              this.$message.error('删除失败');
              console.error('Error deleting pet:', error);
            });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleSizeChange(newSize) {
      this.pageSize = newSize;
      this.fetchPets();
    },
    handleCurrentChange(newPage) {
      this.currentPage = newPage;
      this.fetchPets();
    },
    resetForm() {
      this.uploadKey++
      this.newPet = {
        name: '',
        gender: '',
        age: null,
        species: '',
        temperament: '',
        price: null,
        avatar: ''
      };
    }
  },
  mounted() {
    this.fetchPets();
  }
};
</script>

<style>
.avatar-uploader .avatar {
  width: 100px;
  height: 100px;
}
</style>
