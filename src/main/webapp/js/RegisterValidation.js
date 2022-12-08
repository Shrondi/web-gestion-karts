/**
 * 
 */
 const formulario = document.getElementById('registroFormulario');
 const inputs = document.querySelectorAll('#registroFormulario input');
 
 correctName = false
 correctSurname = false
 correctPassWord = false
 correctEmail = false
 correctDate = false
 
 
const RegularExpressions = {
	firstname: /^[a-zA-ZÀ-ÿ\s]{1,20}$/, // letters, numbers and - _
	surname: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // 
	password: /^.{4,32}$/, // 4 a 12 digitos.
	email: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
	date: /^[0-9][0-9]\/[0-9][0-9]\/[0-9][0-9][0-9][0-9]$/ 
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
		case "correo":
			if(RegularExpressions.email.test(e.target.value)){
				correctEmail=true
			}else{
				correctEmail=false
			}
			console.log("Email: "+ correctEmail)
		break;
		case "fechaNacimiento":
		if(RegularExpressions.date.test(e.target.value)){
				correctDate=true
			}else{
				correctDate=false
			}
			console.log("fechaNacimiento: "+ correctDate)
		break;
		case "passWord":
			if(RegularExpressions.password.test(e.target.value)){
				correctPassWord=true
			}else{
				correctPassWord=false
			}
			console.log("contraseña: "+correctPassWord)
		break;
	}
}

inputs.forEach((input) => {
	input.addEventListener('keyup',validarFormulario)
	input.addEventListener('blur',validarFormulario);
	
});

formulario.addEventListener('submit', (e) => {
	
	if(correctName && correctSurname && correctPassWord && correctEmail && correctDate){
		
	}else{
		e.preventDefault()
	}
});



