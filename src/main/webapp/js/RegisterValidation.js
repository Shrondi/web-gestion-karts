/**
 * 
 */
 const formulario = document.getElementById('registroFormulario');
 const inputs = document.querySelectorAll('#registroFormulario input');
 const date = Date.parse(new Date())
 const localDate = new Date()
 var inputDate
 
 
 correctName = false
 correctSurname = false
 correctPassWord = false
 correctEmail = false
 correctDate = false
 
 
const RegularExpressions = {
	firstname: /^[a-zA-ZÀ-ÿ\s]{1,32}$/, // letters, numbers and - _
	surname: /^[a-zA-ZÀ-ÿ\s]{1,32}$/, // 
	password: /^.{4,16}$/, // 4 a 16 caracteres
	email: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
	date: /^([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))$/
}

function validacionEdad(actualMillis , inputMillis){
	
	diff = actualMillis-inputMillis
	diff = diff/31536000000
	console.log("years: "+diff)
	return diff
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
				inputDate = Date.parse(e.target.value)
				if(validacionEdad(date,inputDate)>=18){
					correctDate=true
				}else{
					correctDate=false
				}
				console.log("parsed fechaNacimiento: "+ inputDate)
			}else{
				correctDate=false
			}
			console.log("fechaNacimiento: "+ correctDate)
			console.log("dateValue: "+ localDate)
			console.log("date: "+ date)
			console.log("fechaNacimiento_VALUE: "+e.target.value)
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


