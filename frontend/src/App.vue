<script setup>
import { RouterLink, RouterView } from 'vue-router'
import { useRoute } from 'vue-router';
import headerNav from "@/components/common/headerNav.vue";

const route = useRoute()
</script>

<template>
  <headerNav></headerNav>

  <RouterView></RouterView>

</template>

<style>
* {
  font-family: 'Pretendard';
  font-weight: 400;
}

::-webkit-scrollbar-button {
  display: none;
}

::-webkit-scrollbar {
  width: 12px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: whitesmoke;
  border-radius: 4px;
}

::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background: #a1a1a1;
}


.object-fit-cover {
  object-fit: cover;
}


@import url("https://fonts.googleapis.com/css2?family=Lora:wght@400;500;600&display=swap");

/*=============== VARIABLES CSS ===============*/
:root {
  --header-height: 3.5rem;

  /*========== Colors ==========*/
  /*Color mode HSL(hue, saturation, lightness)*/
  --bg-color: hsl(220, 18%, 78%);
  --bg-color-light: hsl(220, 18%, 86%);
  --title-color: hsl(220, 8%, 10%);
  --text-color: hsl(220, 4%, 32%);
  --text-color-light: hsl(220, 4%, 55%);
  --body-color: hsl(220, 18%, 99%);
  --container-color: hsl(220, 18%, 97%);

  /*========== Font and typography ==========*/
  /*.5rem = 8px | 1rem = 16px ...*/
  --body-font: 'jua'; /* 기본 폰트 설정 */

  --biggest-font-size: 2.25rem;
  --h1-font-size: 1.5rem;
  --h2-font-size: 1.25rem;
  --h3-font-size: 1rem;
  --normal-font-size: .938rem;
  --small-font-size: .813rem;
  --smaller-font-size: .75rem;

  /*========== Font weight ==========*/
  --font-regular: 400;
  --font-medium: 500;
  --font-semi-bold: 600;

  /*========== z index ==========*/
  --z-tooltip: 10;
  --z-fixed: 100;
}

@media screen and (min-width: 1124px) {
    :root {
      --biggest-font-size: 4.5rem;
      --h1-font-size: 2.25rem;
      --h2-font-size: 1.5rem;
      --h3-font-size: 1.25rem;
      --normal-font-size: 1rem;
      --small-font-size: .875rem;
      --smaller-font-size: .813rem;
      
    }
}

/*=============== BASE ===============*/
* {
    box-sizing: border-box;
    padding: 0;
    margin: 0;
}

html {
    scroll-behavior: smooth;
    /* font-family: var(--body-font); */
}

body {
    /* font-family: 'jua'; */
    font-size: var(--normal-font-size);
    background-color: var(--body-color);
    color: var(--text-color);
    transition: .4s; /*For animation dark mode*/
}

h1, h2, h3 {
    color: var(--title-color);
    font-weight: var(--font-medium);
}

ul {
    list-style: none;
}

a {
    text-decoration: none;
}

img {
    max-width: 100%;
    height: auto;
}

/*=============== REUSABLE CSS CLASSES ===============*/
/* .container {
    max-width: 1024px;
    margin-right: 1.5rem;
    margin-left: 1.5rem;
} */
  
.grid {
    display: grid;
    gap: 1.5rem;
}
  
.section {
    margin-top: 5rem;
    padding: 4.5rem 0 2rem;
}
  
.section__title {
    font-size: var(--h1-font-size);
    text-align: center;
    margin-bottom: 3rem;
}
  
.main {
    overflow: hidden; /* For animation ScrollReveal */
}

/* .navbar {
    position:fixed;
    top: 0;
    left: 0;
    width: 100%;
    background-color: white;
    z-index: var(--z-fixed);
    transition: .4s;
}

.navbar-brand, .nav-link {
    color: #333;
}

.navbar-brand:hover, .nav-link:hover {
    color: #000;
} */

html {
    scroll-behavior: smooth; /* 부드러운 스크롤 */
}

@font-face {
  font-family: 'jua';
  src: url('assets/fonts/BMJUA_ttf.ttf') format('truetype');
}
@font-face {
  font-family: 'GmarketSansLight';
  src: url('assets/fonts/GmarketSansTTFLight.ttf') format('truetype');
}
@font-face {
  font-family: 'Pretendard';
  src: url('assets/fonts/Pretendard-ExtraLight.ttf') format('truetype');
  font-weight: 200;
}
@font-face {
  font-family: 'Pretendard';
  src: url('assets/fonts/Pretendard-Light.ttf') format('truetype');
  font-weight: 300;
}
@font-face {
  font-family: 'Pretendard';
  src: url('assets/fonts/Pretendard-Regular.ttf') format('truetype');
  font-weight: 400;
}
@font-face {
  font-family: 'Pretendard';
  src: url('assets/fonts/Pretendard-SemiBold.ttf') format('truetype');
  font-weight: 500;
}
/* .container {
  display: flex;
  width: 80%;
  justify-content: center;
} */
</style>
