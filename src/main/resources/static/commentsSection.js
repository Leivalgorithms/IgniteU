document.getElementById('postButton').addEventListener('click', function(event) {
    event.preventDefault(); // Evita el comportamiento por defecto del formulario

    var contenido = document.getElementById('contenido').value;
    var formData = new FormData();
    formData.append('contenido', contenido);

    fetch('/home', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data) {
            // Actualiza la sección de posts con el nuevo post
            var postsDiv = document.getElementById('posts');
            var newPostDiv = document.createElement('div');
            newPostDiv.className = 'comment';
            newPostDiv.innerHTML = `<div class="comment-author">
                                        <span>${data.usuario ? data.usuario.username : 'Unknown'}</span>
                                        <span>${new Date(data.fecha_publicacion).toLocaleString()}</span>
                                    </div>
                                    <div class="comment-text">${data.contenido}</div>
                                    <div>
                                        <img src="${data.imageURL ? '/images/' + data.imageURL : ''}" class="post-image" alt="Post Image">
                                    </div>`;
            postsDiv.prepend(newPostDiv);
            document.getElementById('contenido').value = '';
        } else {
            console.error('Error al crear el post.');
        }

    })
    .catch(error => console.error('Error:', error));
});
