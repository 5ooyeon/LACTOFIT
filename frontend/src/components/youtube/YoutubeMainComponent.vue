<template>
  <div class="container">
    <div>
      <p class="section-title">최근에 본 영상</p>
      <div class="recent-videos">
        <VideoList v-for="video in store.videoHistory" :key="video.id.videoId" :video="video" />
      </div>
    </div>
    <div>
      <p class="section-title">이런 운동은 어때요?</p>
      <p class="weakness-desc">가장 적게 한 <em class="my-weakness-part-video">상체</em> 운동의 영상이에요.</p>
      <div class="weakness-videos">
        <VideoList v-for="video in store.weaknessVideos" :key="video.id.videoId" :video="video" />
      </div>
    </div>
  </div>
</template>

<script setup>
import VideoList from './VideoList.vue';
import { ref, onMounted } from 'vue';
import { useYoutubeStore } from '@/stores/youtube';
import { useRouter } from 'vue-router';

const store = useYoutubeStore();
const keyword = ref('');
const router = useRouter();

function youtubeSearch(keyword, mode) {
  store.getVideosBySearchWord(keyword, mode).then(() => {
    if (mode === 'search') {
      router.push({ name: 'SearchResultComponent' });
    }
  });
}

onMounted(() => {
  if (store.weaknessVideos.length === 0) {
    store.getVideosBySearchWord('상체', null);
  }
});
console.log(store.weaknessVideos)
</script>

<style scoped>
.container {
  margin-top: 20px;
  padding: 20px;
  max-width: 1200px;
  margin-left: auto;
  margin-right: auto;
  background-color: #fff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.section-title {
  font-size: 1.5em;
  margin-bottom: 10px;
  color: #333;
  border-bottom: 2px solid #3c90e2;
  padding-bottom: 5px;
}

.weakness-desc {
  font-size: 1em;
  margin-bottom: 20px;
  color: #666;
}

.recent-videos,
.weakness-videos {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 16px;
}
</style>
