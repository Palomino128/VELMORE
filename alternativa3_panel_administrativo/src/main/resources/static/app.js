function generarMensaje(producto){
  const nombre = document.getElementById("nombre")?.value || "Cliente";
  const salida = document.getElementById("salida");
  salida.textContent = "Hola, soy " + nombre + ". Deseo información sobre " + producto + ".";
  salida.className = "alert";
}
