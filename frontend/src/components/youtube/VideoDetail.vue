<template>
    <div class="container">
        <p class="video-title" v-html="lastVideo.snippet.title"></p>
        <div class="video-src">
            <iframe :src="`https://www.youtube.com/embed/${lastVideo.id.videoId}`" frameborder="0" allowfullscreen></iframe>
        </div>

        <div class="comment-section">
            <p class="comment-title">댓글</p>
            <div class="comment-write-form">
                <textarea class="form-control" rows="3" v-model="myComment" placeholder="Leave a comment..."></textarea>
                <button class="write-comment" @click="writeComment()">입력</button>
            </div>
            <div class="comments-list">
                <CommentList v-for="comment in store.commentList" :key="comment.id" :comment="comment"/>
            </div>
        </div>
    </div>
</template>
<script setup>
import { ref, computed, onMounted } from 'vue';
import { useYoutubeStore } from '@/stores/youtube';
import CommentList from './CommentList.vue';

const store = useYoutubeStore();
const myComment = ref('');

// Computed property to get the last video from history
const lastVideo = computed(() => {
    if (store.videoHistory.length > 0) {
        return store.videoHistory[store.videoHistory.length - 1];
    }
    return null;  // Return null to handle checks more consistently
});

const writeComment = () => {
  if (myComment.value.trim() === '') {
    return; // Do not allow empty comments
  }
  store.writeComment(myComment.value, lastVideo.value.id.videoId)
  myComment.value = '';
}

onMounted(() => {
    // window.reload()
    window.scrollTo(0, 0);
})
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

.video-title {
    font-size: 1.5em;
    margin-bottom: 20px;
    color: #333;
    border-bottom: 2px solid #3c90e2;
    padding-bottom: 5px;
}

.video-src iframe {
    width: 100%;
    height: 450px;
    border-radius: 10px;
    margin-bottom: 20px;
}

.comment-section {
    margin-top: 30px;
}

.comment-title {
    font-size: 1.5em;
    margin-bottom: 10px;
    color: #333;
    border-bottom: 2px solid #3c90e2;
    padding-bottom: 5px;
}

.comment-write-form {
    display: flex;
    flex-direction: column;
    gap: 10px;
    margin-bottom: 20px;
}

.comment-write-form textarea {
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ddd;
    font-size: 1em;
    resize: none;
}

.write-comment {
    align-self: flex-end;
    padding: 10px 20px;
    background-color: #3c90e2;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
}

.write-comment:hover {
    background-color: #3169b1;
}

.comments-list {
    display: flex;
    flex-direction: column;
    gap: 20px;
}
</style>
