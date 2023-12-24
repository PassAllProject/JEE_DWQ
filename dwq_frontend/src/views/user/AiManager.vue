<template>
  <el-container style="height: 100vh; background-color: white;">
    <el-main>
      <div class="chat-window">
        <!-- 对话记录 -->
        <div v-for="(item, index) in conversation" :key="index" :class="{'message': true, 'user-message': item.sender === 'user', 'ai-message': item.sender === 'ai'}">
          <div class="message-content">{{ item.content }}</div>
          <div class="message-time">{{ item.time }}</div>
        </div>
      </div>
    </el-main>
    <el-footer>
      <!-- 输入框和发送按钮 -->
      <el-input
          type="textarea"
          placeholder="请输入您的问题..."
          v-model="userInput"
          class="input-area"
          @keyup.enter="saveData"
      ></el-input>
      <el-button type="primary" @click="saveData">发送</el-button>
    </el-footer>
  </el-container>
</template>

<script>
import axios from "axios";

export default {
  name: 'ChatPage',
  data() {
    return {
      userInput: '',
      conversation: [],
      waitingForReply: false,
      aiData: {
        id: null,
        username: '',
        promote: '',
        answer: '你好，这是一个消息示例。',
        time: null,
      },
    };
  },
  methods: {
    async saveData() {
      if (!this.userInput) return; // 防止发送空消息
      const currentTime = new Date().toLocaleString();
      // 添加用户提问到对话
      this.conversation.push({
        sender: 'user',
        content: this.userInput,
        time: currentTime,
      });
      this.waitingForReply = true;
      try {
        const response = await axios.post('http://43.157.28.135:3000/getai', {
          prompt: this.userInput
        });
        this.aiData.promote=this.userInput
        this.aiData.answer = response.data.generatedText.message.content;
        this.aiData.username = localStorage.getItem('username');
        await axios.post(`/api/auth/ai`, this.aiData);
        // 添加AI回答到对话
        this.conversation.push({
          sender: 'ai',
          content: response.data.generatedText.message.content,
          time: currentTime,
        });
        this.userInput = ''; // 清空输入框
      } catch (error) {
        console.error('请求失败:', error);
        // 在对话中显示错误消息
        this.conversation.push({
          sender: 'ai',
          content: '抱歉，无法获取回答。',
          time: currentTime,
        });
      } finally {
        this.waitingForReply = false;
      }
    }
  }
}
</script>

<style scoped>
.chat-window {
  padding: 10px;
  max-height: 80vh;
  overflow-y: auto;
}

.message {
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 10px;
  max-width: 80%;
}

.user-message {
  background-color: #d0ebff;
  align-self: flex-start;
}

.ai-message {
  background-color: #f0f0f0;
  align-self: flex-end;
  text-align: right;
}

.message-content {
  word-wrap: break-word;
}

.message-time {
  font-size: 0.8em;
  color: #888;
}

.input-area {
  width: calc(100% - 100px);
  display: inline-block;
}

.el-footer {
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.el-button {
  margin: 0 10px;
}
</style>
