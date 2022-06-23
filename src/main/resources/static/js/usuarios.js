// Call the dataTables jQuery plugin
$(document).ready(function () {
    chargeUsers();
    $('#usuarios').DataTable();
});

async function chargeUsers() {
    const request = await fetch('api/usuarios', {
        method: 'GET',
        headers: getHeaders()
    });
    const usuarios = await request.json();

    //console.log(usuarios);
    
    var text;
    usuarios.forEach((item)=>{
        //eliminar
        var btn_eliminar = `<a href="#" onclick="deleteUser(${item.id})" class="btn btn-danger btn-circle btn-sm">
                            <i class="fas fa-trash"></i>
                        </a>`;
        //lista
        text += `<tr>
                    <th>${item.id}</th>
                    <th>${item.nombre} ${item.apellido}</th>
                    <th>${item.email}</th>
                    <th>${item.telefono}</th>
                    <th>
                        ${btn_eliminar}
                    </th>
                </tr>`
    });
    document.querySelector('#usuarios tbody').outerHTML = text;
}

async function deleteUser(id){
    if(!confirm('¿Esta seguro de querer eliminar este usuario?')){
        return;
    }
    
    const request = await fetch(`api/usuario/${id}`, {
        method: 'DELETE',
        headers: getHeaders()
    });
    const status = await request.json();
    alert(status);
    location.reload()
}

function getHeaders(){
    return {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.token
        }
}