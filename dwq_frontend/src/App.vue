<script setup>
import { useDark, useToggle } from '@vueuse/core'

useDark({
  selector: 'html',
  attribute: 'class',
  valueDark: 'dark',
  valueLight: 'light'
})

useDark({
  onChanged(dark) { useToggle(dark) }
})
</script>

<template>
  <header>
    <div class="wrapper">
        <router-view/>
    </div>
  </header>
</template>

<style scoped>
header {
  line-height: 1.5;
}
.button-container {
  display: flex;
  justify-content: flex-end; /* 将子元素对齐到容器的末端（右边） */
}
</style>
