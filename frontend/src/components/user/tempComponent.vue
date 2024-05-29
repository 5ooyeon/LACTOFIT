<template></template>
<script setup>
import { onMounted }from 'vue'

onMounted(() => {
  const queryString = window.location.search;
  console.log("did!")
  const urlParams = new URLSearchParams(queryString);
  console.log(urlParams)
  const code = urlParams.get('code');
  console.log(code)
  console.log('Authorization code:', code);
  if(code) {
    authStore.fetchUserOpenId(code)
  }
})
</script>