/**
 * 
 */
 
 const formulario = document.getElementById('formModificarUsuario');
 const inputs = document.querySelectorAll('#formModificarUsuario input');
 
 correctName = true
 correctSurname = true
 //correctPassWord = false
 
 
const RegularExpressions = {
	firstname: /^[a-zA-ZÀ-ÿ\s]{1,20}$/, // letters, numbers and - _
	surname: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // 
	//password: /^.{4,32}$/, // 4 a 12 digitos.
	
}


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
					nameErrorText.innerText = "el nombre no puede estar vacío"
				}else if(name.length > 32){
					nameErrorText.innerText = "el nombre no puede tener mas de 32 caracteres"
				}else{
					nameErrorText.innerText = "el nombre no puede usar caracteres especiales"
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
					surnameErrorText.innerText = "el apellido no puede estar vacío"
				}else if(surname.length > 32){
					surnameErrorText.innerText = "el apellido no puede tener mas de 32 caracteres"
				}else{
					surnameErrorText.innerText = "el apellido no puede usar caracteres especiales"
				}
				correctSurname=false
			}
			console.log("apellido: "+correctSurname)
		break;
	}
}

inputs.forEach((input) => {
	input.addEventListener('keyup',validarFormulario)
	input.addEventListener('blur',validarFormulario);
	
});

formulario.addEventListener('submit', (e) => {
	
	if(correctName && correctSurname){
		
	}else{
		e.preventDefault()
	}
});

