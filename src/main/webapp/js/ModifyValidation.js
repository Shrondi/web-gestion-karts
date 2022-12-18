/**
 * 
 */
 
 const formulario = document.getElementById('formModificarUsuario');
 const inputs = document.querySelectorAll('#formModificarUsuario input');
 
 correctName = true
 correctSurname = true
 correctPassWord = false
 
 //expresiones regulares usadas para validar el contenido de los inputs

const RegularExpressions = {
	firstname: /^[a-zA-ZÀ-ÿ\s]{1,20}$/, // letters, numbers and - _
	surname: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // 
	password: /^.{4,16}$/, // 4 a 16 digitos.
	
}

// funcion usada para ver si los valores de los inputs de la modificación del perfil de usuario son validos

const validarFormulario = (e) => {
	switch(e.target.name){
		case "nombre":
		var nameErrorText = document.getElementById('nameErrorText')
			if(RegularExpressions.firstname.test(e.target.value)){
				nameErrorText.innerText = ""
				correctName=true
			}else{
				var name = e.target.value
				if(name.length < 1){
					nameErrorText.innerText = "Nombre no puede estar vacío"
				}else if(name.length > 32){
					nameErrorText.innerText = "Nombre no puede tener más de 32 caracteres"
				}else{
					nameErrorText.innerText = "Nombre no puede usar caracteres especiales"
				}
				correctName=false
			}
			console.log("usuario: "+correctName)
		break;
		case "apellidos":
		var surnameErrorText = document.getElementById('surnameErrorText')
			if(RegularExpressions.surname.test(e.target.value)){
				surnameErrorText.innerText = ""
				correctSurname=true
			}else{
				var surname = e.target.value
				if(surname.length < 1){
					surnameErrorText.innerText = "Apellidos no pueden estar vacío"
				}else if(surname.length > 32){
					surnameErrorText.innerText = "Apellidos no pueden tener más de 32 caracteres"
				}else{
					surnameErrorText.innerText = "Apellidos no pueden usar caracteres especiales"
				}
				correctSurname=false
			}
			console.log("apellido: "+correctSurname)
		break;
		case "passWord":
		var passwordErrorText = document.getElementById('passwordErrorText')
			if(RegularExpressions.password.test(e.target.value)){
				passwordErrorText.innerText = ""
				correctPassWord=true
			}else{
				var password = e.target.value
				if(password.length < 4){
					passwordErrorText.innerText = "Contraseña debe tener más de 4 carecteres"
				}else if(password.length > 32){
					passwordErrorText.innerText = "Contraseña no puede tener más de 32 caracteres"
				}
				correctPassWord=false
			}
			console.log("contraseña: "+correctPassWord)
		break;
	}
}

// asignación de eventos de escucha a todos inputs del formulario de modificación de usuario

inputs.forEach((input) => {
	input.addEventListener('keyup',validarFormulario)
	input.addEventListener('blur',validarFormulario);
	
});

// evento de escucha en el que se verá si se cumplen las condiciones para pasar al controlador


formulario.addEventListener('submit', (e) => {
	
	if(correctName && correctSurname && correctPassWord){
		
	}else{
		e.preventDefault()
	}
});

