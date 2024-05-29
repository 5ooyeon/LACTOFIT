<template>
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Upload Image</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="submitForm">
            <div class="mb-3">
              <label for="formFile" class="form-label">Choose an image</label>
              <input class="form-control" type="file" id="formFile" @change="onFileChange">
            </div>
            <div v-if="imageUrl">
              <p>Preview:</p>
              <img :src="imageUrl" class="img-fluid" alt="Image preview">
            </div>
            <button type="submit" class="btn btn-primary mt-3">Upload</button>
          </form>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  
  const file = ref(null);
  const imageUrl = ref(null);
  
  const onFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile && selectedFile.type.startsWith('image/')) {
      file.value = selectedFile;
      imageUrl.value = URL.createObjectURL(selectedFile);
    } else {
      file.value = null;
      imageUrl.value = null;
    }
  };
  
  const submitForm = () => {
    if (!file.value) {
      alert('Please select an image file to upload.');
      return;
    }
    
    // Form submission logic, e.g., sending the file to a server
    const formData = new FormData();
    formData.append('image', file.value);
  
    // Example: using axios to send the file to a server
    // axios.post('/upload', formData)
    //   .then(response => {
    //     console.log('Image uploaded successfully:', response.data);
    //     // Clear the form after successful upload
    //     file.value = null;
    //     imageUrl.value = null;
    //   })
    //   .catch(error => {
    //     console.error('Error uploading image:', error);
    //   });
  };
  </script>
  
  <style scoped>
  .modal-dialog {
    max-width: 800px;
    margin: 30px auto;
  }
  
  .img-fluid {
    max-width: 100%;
    height: auto;
  }
  </style>
  