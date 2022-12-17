/**
 * 
 */
 const formulario = document.getElementById('formCrearPista');
 const nombre = document.getElementById('nombrePista');
 var nameErrorText = document.getElementById('nameErrorText')
 
 correctName = false

//expresiones regulares usadas para validar el contenido de los inputs
 
const RegularExpressions = {
	pistaName: /^.{1,64}$/, // 1 a 64 digitos.
	
}

// funcion usada para ver si el nombre asignado a la pista es válido segun las limitaciones impuestas

 const validarFormulario = (e) => {
			if(RegularExpressions.pistaName.test(e.target.value)){
				nameErrorText.innerText = ""
				correctName=true
			}else{
				var name = e.target.value
				if(name.length < 1){
					nameErrorText.innerText = "el nombre no puede estar vacío"
				}else if(name.length > 64){
					nameErrorText.innerText = "el nombre no puede tener mas de 64 caracteres"
				}
				correctName=false
			}
			console.log("pista: "+correctName)
		
}

//asignación de evento de escucha para el nombre de la pista cuando se presione la tecla o se clique fuera

nombre.addEventListener('keyup',validarFormulario)
nombre.addEventListener('blur',validarFormulario);
	
// evento de escucha en el que se verá si se cumplen las condiciones para pasar al controlador
formulario.addEventListener('submit', (e) => {
	
	if(correctName){
		
	}else{
		e.preventDefault()
	}
});

