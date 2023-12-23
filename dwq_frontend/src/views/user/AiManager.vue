<template>
  <el-container style="height: 100vh;">
    <el-container>
      <el-main>
        <div class="chat-content">
          <!-- 显示用户的问题 -->
          <div class="message user-message">{{ aiData.promote }}</div>
          <!-- 显示回复或等待信息 -->
          <div class="message ai-message" v-if="waitingForReply">等待回复...</div>
          <div class="message ai-message" v-else>{{ aiData.answer }}</div>
        </div>
      </el-main>
      <el-footer>
        <el-row>
          <el-col :span="20">
            <el-input type="textarea" placeholder="输入消息..." v-model="aiData.promote"></el-input>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" @click="saveData">发送</el-button>
          </el-col>
        </el-row>
      </el-footer>
    </el-container>
  </el-container>
</template>

<script>
import axios from "axios";

export default {
  name: 'ChatPage',
  data() {
    return {
      aiData: {
        id: null,
        username: '',
        promote: '',
        answer: '你好，这是一个消息示例。',
        time: null,
      },
      waitingForReply: false, // 新增等待回复的标志
    };
  },
  methods: {
    async saveData() {
      try {
        this.waitingForReply = true; // 开始等待回复
        const response = await axios.post('http://43.157.28.135:3000/getai', {
          prompt: this.aiData.promote
        });
        this.aiData.answer = response.data.generatedText.message.content;
        this.aiData.username = localStorage.getItem('username');
        await axios.post(`/api/auth/ai`, this.aiData);
        this.aiData.promote = '';
      } catch (error) {
        console.error('请求失败:', error);
      } finally {
        this.waitingForReply = false; // 结束等待回复
      }
    }
  }
}
</script>

<style scoped>
.chat-content {
  padding: 20px;
  background-color: #fff;
  height: calc(100% - 60px);
  overflow-y: auto;
}

.message {
  margin-bottom: 20px;
  padding: 10px;
  background-color: #f0f0f0;
  border-radius: 4px;
}

.user-message {
  color: blue; /* 用户消息的样式 */
}

.ai-message {
  color: black; /* AI回复的样式 */
}

</style>
