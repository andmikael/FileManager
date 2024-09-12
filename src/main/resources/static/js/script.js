/*const uploadButton = document.getElementById('uploadbutton');
const uploadForm = document.getElementById('file');

uploadButton.addEventListener('click',  
 (event) => {
    event.preventDefault(); // Prevent default form submission


    fetch('/api/video', {
        method: 'POST',
        body: uploadForm.getAttribute.toString()
    })
    .then(response => response.text())
    .then(data => {
        // Handle successful upload response (e.g., display success message)
        console.log('File uploaded successfully!');
    })
    .catch(error => {
        // Handle upload errors
        console.error('Error uploading file:', error);
    });
});*/