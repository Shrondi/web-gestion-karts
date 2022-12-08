/**
 * 
 */
 
 const formulario = document.getElementById('formModificarUsuario');
 const inputs = document.querySelectorAll('#formModificarUsuario input');
 
 correctName = false
 correctSurname = false
 //correctPassWord = false
 
 
const RegularExpressions = {
	firstname: /^[a-zA-ZÀ-ÿ\s]{1,20}$/, // letters, numbers and - _
	surname: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // 
	//password: /^.{4,32}$/, // 4 a 12 digitos.
	
}


const validarFormulario = (e) => {
	switch(e.target.name){
		case "nombre":
			if(RegularExpressions.firstname.test(e.target.value)){
				correctName=true
			}else{
				correctName=false
			}
			console.log("usuario: "+correctName)
		break;
		case "apellidos":
			if(RegularExpressions.surname.test(e.target.value)){
				correctSurname=true
			}else{
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

