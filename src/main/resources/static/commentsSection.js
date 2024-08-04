document.getElementById('postButton').addEventListener('click', function() {
    var contenido = document.getElementById('contenido').value;
    var usuarioId = 1; // Reemplazar con el ID del usuario actual
    fetch('/api/posts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            usuarioId: usuarioId,
            contenido: contenido
        })
    })
    .then(response => response.json())
    .then(data => {
        // Actualiza la sección de posts con el nuevo post
        var postsDiv = document.getElementById('posts');
        var newPostDiv = document.createElement('div');
        newPostDiv.className = 'comment';
        newPostDiv.innerHTML = `<div class="comment-author">
                                    <span>${data.usuario.username}</span>
                                    <span>${new Date(data.fechaPublicacion).toLocaleString()}</span>
                                </div>
                                <div class="comment-text">${data.contenido}</div>`;
        postsDiv.prepend(newPostDiv);
        document.getElementById('contenido').value = '';
    });
});