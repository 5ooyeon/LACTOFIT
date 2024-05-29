<template>
  <div class="comment-item">
    <img :src="comment.snippet.topLevelComment.snippet.authorProfileImageUrl" alt="Profile image" class="profile-image">
    <div class="comment-content">
      <strong class="author-name">{{ comment.snippet.topLevelComment.snippet.authorDisplayName }}</strong>
      <p class="comment-date">{{ formatDate(comment.snippet.topLevelComment.snippet.publishedAt) }}</p>
      <p class="comment-text">{{ comment.snippet.topLevelComment.snippet.textOriginal }}</p>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
  comment: {
    type: Object,
    required: true
  }
});

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'short', day: 'numeric' };
  return new Date(dateString).toLocaleDateString(undefined, options);
};
</script>


<style scoped>
.comment-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 20px;
  padding-bottom: 20px;
  border-bottom: 1px solid #ddd;
}

.profile-image {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 15px;
}

.comment-content {
  flex: 1;
}

.author-name {
  font-size: 1em;
  color: #333;
  font-weight: bold;
  margin-bottom: 5px;
}

.comment-date {
  font-size: 0.8em;
  color: #888;
  margin-bottom: 10px;
}

.comment-text {
  font-size: 1em;
  color: #555;
}
</style>
