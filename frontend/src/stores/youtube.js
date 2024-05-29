import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { setCookie, getCookie } from '@/utils/cookies';
import { useAuthStore } from './auth';

const API_KEY = import.meta.env.VITE_YOUTUBE_API_KEY;
const CommentURL = 'https://www.googleapis.com/youtube/v3/commentThreads';

function parseJSON(value, defaultValue) {
  try {
    return JSON.parse(value) || defaultValue;
  } catch (error) {
    console.error('Error parsing JSON:', error);
    return defaultValue;
  }
}

export const useYoutubeStore = defineStore('youtube', () => {
  const weaknessVideos = ref([]);
  const searchedVideos = ref([]);
  const searchWord = ref(null);
  const videoHistory = ref(parseJSON(getCookie('videoHistory'), []));
  const commentList = ref([]);

  const getVideosBySearchWord = async (keyword, mode) => {
    const URL = 'https://www.googleapis.com/youtube/v3/search';

    console.log('start--');

    try {
      const response = await axios.get(URL, {
        params: {
          key: API_KEY,
          part: 'snippet',
          maxResults: 30,
          q: keyword + ' 운동',
          type: 'video'
        }
      });
      console.log('success');
      if (mode === 'search') {
        console.log('search Method');
        searchedVideos.value = response.data.items;
        searchWord.value = keyword;
      } else {
        console.log('default loading');
        weaknessVideos.value = response.data.items;
      }
      console.log(response.data);
    } catch (error) {
      console.log('error');
      console.error('Error fetching videos:', error);
    }
  };

  const addToHistory = (video) => {
    console.log('Adding video to history');
    if (!Array.isArray(videoHistory.value)) {
      console.error('videoHistory is not an array:', videoHistory.value);
      videoHistory.value = [];
    }
    videoHistory.value.push(video);
    setCookie('videoHistory', JSON.stringify(videoHistory.value), 7);

    axios.get(CommentURL, {
      params: {
        part: 'snippet',
        videoId: video.id.videoId,
        key: API_KEY
      }
    }).then((response) => {
      commentList.value = response.data.items;
      console.log(commentList.value);
    }).catch((error) => {
      console.error('Error fetching comments:', error);
    });
  };


  const writeComment = async (comment, videoId) => {
    try {
      const accessToken = useAuthStore().user.accessToken;
      console.log(accessToken)
      if (!accessToken) {
        throw new Error('No access token found');
      }

      const response = await axios.post('https://www.googleapis.com/youtube/v3/commentThreads', {
        snippet: {
          videoId: videoId,
          topLevelComment: {
            snippet: {
              textOriginal: comment,
            },
          },
        },
      }, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          Accept: 'application/json',
        },
        params: {
          part: 'snippet',
        },
      });

      console.log(response.data);
    } catch (error) {
      console.error('Error inserting comment thread:', error);
    }
  };

  return {
    weaknessVideos,
    searchedVideos,
    getVideosBySearchWord,
    searchWord,
    addToHistory,
    videoHistory,
    commentList,
    writeComment
  };
});
