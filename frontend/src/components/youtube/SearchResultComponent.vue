<template>
    <div class="container">
        <p>"{{ store.searchWord }}" 에 관한 검색 결과입니다.</p>
        <div class="searched-videos">
            <VideoList v-for="video in store.searchedVideos" :key="video.id.videoId" :video="video" />
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useYoutubeStore } from '@/stores/youtube';
import VideoList from './VideoList.vue';

const store = useYoutubeStore()


</script>

<style scoped>

.container {
    margin-top: 20px;

}

.searched-videos {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 16px;
}
</style>
