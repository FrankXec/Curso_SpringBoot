$(document).ready(function () {
    //init
});

async function registrarUsuario() {
    let datos = {};
    datos.nombre = document.getElementById('txtNombre').value;
    datos.apellido = document.getElementById('txtApellido').value;
    datos.email = document.getElementById('txtEmail').value;
    datos.telefono = document.getElementById('txtTelefono').value;
    datos.password = document.getElementById('txtPassword').value;
    
    let passwordConfirmation = document.getElementById('txtPasswordConfirmation').value;
    
    if(passwordConfirmation !== datos.password){
        alert('La contraseña no coincide!');
        return
    }
    
    const request = await fetch('api/usuario', {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
    });
    const status = await request.json();
    alert('La cuenta fue registrada con Exito!');
    window.location.href='login.html';
}