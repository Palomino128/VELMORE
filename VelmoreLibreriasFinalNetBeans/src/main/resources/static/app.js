function copiarMensaje() {
    const mensaje = document.getElementById("mensajeGenerado");
    if (mensaje && navigator.clipboard) {
        navigator.clipboard.writeText(mensaje.textContent);
        alert("Mensaje copiado para WhatsApp");
    }
}

function confirmarEliminacion() {
    return confirm("¿Seguro que deseas eliminar este producto?");
}
