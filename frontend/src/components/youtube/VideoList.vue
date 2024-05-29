<template>
  <div class="video-card" @click="watchVideo">
    <img :src="video.snippet.thumbnails.high.url" class="video-thumbnail">
    <div class="video-info">
      <p class="video-title" v-html="video.snippet.title"></p>
    </div>
  </div>
</template>

<script setup>
import { useYoutubeStore } from '@/stores/youtube';
import { defineProps } from 'vue';
import { useRouter } from 'vue-router';

const store = useYoutubeStore();

const props = defineProps({
  video: {
    type: Object,
    required: true
  }
});

const router = useRouter();

const watchVideo = () => {
  store.addToHistory(props.video);
  router.push({ name: 'VideoDetail' });
};
</script>

<style scoped>
.video-card {
  display: flex;
  flex-direction: column;
  margin: 15px;
  cursor: pointer;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
  background-color: #fff;
}

.video-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.video-thumbnail {
  width: 100%;
  /* height: fit-content; */
  /* height: auto; */
}

.video-info {
  padding: 10px;
  background-color: #fff;
}

.video-title {
  font-size: 14px;
  color: #333;
  margin: 0;
}
</style>
