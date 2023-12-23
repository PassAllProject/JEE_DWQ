<template>
  <div class="pet-box" v-for="pet in pets" :key="pet.id">
    <el-image :src="getUrl(pet.avatar)" fit="cover"></el-image>
    <div class="pet-info">
      <h2>{{ pet.name }}</h2>
      <p>{{ pet.species }}</p>
      <p>{{ pet.temperament }}</p>
      <el-tag type="success">￥{{ pet.price }}</el-tag>
    </div>
    <div class="pet-actions">
      <el-button type="primary" @click.once="buyPet(pet.id)">购买</el-button>
      <el-button type="info" v-show="pet.buy_ed">已售出</el-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import shoppingManager from "@/views/admin/ShoppingManager.vue";

export default {
  name: 'PetBox',
  data() {
    return {
      pets: [], // 假设这是从外部获取的宠物数据
      currentPage: 1,
      pageSize: 10,
    };
  },
  methods: {
    async buyPet(petId) {
      try {
        // 2. 检查宠物是否还有库存
        const pet = await axios.get(`/api/auth/pets/${petId}`);
        // 5. 将订单和订单详情存储到数据库中
        const user= await axios.get('/api/auth/one?username=' + localStorage.getItem('username'));
        const orderId = await axios.post('/api/auth/orders', {
          userId: user.data.id,
          totalPrice:pet.data.data.price
        });
        const orderDetailResponse = await axios.post('/api/auth/orderDetails', {
          orderId: orderId.data,
          petId: pet.data.data.id,
          quantity: 1,
          price: pet.data.data.price
        });
        // 7. 向用户显示购买成功的提示信息
        alert('购买成功！');
        for (let i = 0; i < this.pets.length; i++) {
            if(this.pets[i].id==pet.data.data.id){
                this.pets[i].buy_ed=true
            }
        }
        // 8. 刷新页面，以便更新宠物列表
      } catch (error) {
        console.error('购买失败', error);
        // 显示购买失败的消息
        alert('购买失败！请稍后再试。');
      }
    },
    async commentOnPet(petId) {
      try {
        const comment = '这是一个评论'; // 实际应用中，这应该是从用户输入获取的
        const response = await axios.post('/api/comment', { petId, comment });
        console.log('评论成功', response.data);
        // 这里可以添加更多的处理逻辑
      } catch (error) {
        console.error('评论失败', error);
      }
    },
    fetchPets() {
      console.log()
      axios.get('/api/auth/pets',{params:{
          name: '',
          currentPage: this.currentPage,
          pageSize: this.pageSize
        }})
          .then(response => {
            this.pets = response.data.data.data;// 假设响应的数据是宠物数组
            this.totalPets=response.data.data.total
            this.pets=this.pets.map((pet)=>{
               return {
                 ...pet,
                 buy_ed:false
               }
            })
            console.log(this.pets)
          })
          .catch(error => {
            console.error('Error fetching pets:', error);
          });
    },
    getUrl(value){
      return "http://116.62.32.192:3000/uploads/"+value
    }
  },
  mounted() {
    this.fetchPets()
  }
}
</script>




<style scoped>
.pet-box {
  border: 1px solid #eee;
  padding: 20px;
  margin-bottom: 20px;
}

.pet-info h2 {
  margin: 0;
  font-size: 20px;
}

.pet-info p {
  margin: 5px 0;
}

.pet-actions {
  margin-top: 20px;
}

.el-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  margin-bottom: 20px;
}
</style>
