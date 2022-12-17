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
 
// expresiones regulares usadas para la validación del valor de los inputs del registro
const RegularExpressions = {
	firstname: /^[a-zA-ZÀ-ÿ\s]{1,32}$/, // letters, numbers and - _
	surname: /^[a-zA-ZÀ-ÿ\s]{1,32}$/, // 
	password: /^.{4,16}$/, // 4 a 16 caracteres
	email: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
	date: /^([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))$/
}

//funcion simple para calcular el tiempo entre dos fechas en años

function validacionEdad(actualMillis , inputMillis){
	
	diff = actualMillis-inputMillis
	diff = diff/31536000000
	console.log("years: "+diff)
	return diff
}

// función usada para hacer las comprobaciones necesarias a los diferentes campos del formulario de registro

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
		case "correo":
		var emailErrorText = document.getElementById('mailErrorText')
			if(RegularExpressions.email.test(e.target.value)){
				emailErrorText.innerText = ""
				correctEmail=true
			}else{
				var email= e.target.value
				emailErrorText.innerText = "El correo no es válido"
				correctEmail=false
			}
			console.log("Email: "+ correctEmail)
		break;
		case "fechaNacimiento":
		var dateErrorText = document.getElementById('dateErrorText')
		if(RegularExpressions.date.test(e.target.value)){
				inputDate = Date.parse(e.target.value)
				if(validacionEdad(date,inputDate)>=18){
					dateErrorText.innerText = ""
					correctDate=true
				}else{
					dateErrorText.innerText = "No se permite el registro a menores de edad"
					correctDate=false
				}
				console.log("parsed fechaNacimiento: "+ inputDate)
			}else{
				dateErrorText.innerText = "La fecha no es válida"
				correctDate=false
			}
			console.log("fechaNacimiento: "+ correctDate)
			console.log("dateValue: "+ localDate)
			console.log("date: "+ date)
			console.log("fechaNacimiento_VALUE: "+e.target.value)
		break;
		case "passWord":
		var passwordErrorText = document.getElementById('passwordErrorText')
			if(RegularExpressions.password.test(e.target.value)){
				passwordErrorText.innerText = ""
				correctPassWord=true
			}else{
				var password = e.target.value
				if(password.length < 4){
					passwordErrorText.innerText = "La contraseña debe tener mas de 4 carecteres"
				}else if(password.length > 32){
					passwordErrorText.innerText = "La contraseña no puede tener mas de 32 caracteres"
				}
				correctPassWord=false
			}
			console.log("contraseña: "+correctPassWord)
		break;
	}
}

// un bucle sencillo para asignar un listener a los eventos de pulsación y clicado fuera a cada input del formulario
inputs.forEach((input) => {
	input.addEventListener('keyup',validarFormulario)
	input.addEventListener('blur',validarFormulario);
	
});


// evento de escucha en el que se verá si se cumplen las condiciones para pasar al controlador
formulario.addEventListener('submit', (e) => {
	
	if(correctName && correctSurname && correctPassWord && correctEmail && correctDate){
		
	}else{
		e.preventDefault()
	}
});


