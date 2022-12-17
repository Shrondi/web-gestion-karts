/**
 * 
 */
 const formulario = document.getElementById('formCrearPista');
 const nombre = document.getElementById('nombrePista');
 var nameErrorText = document.getElementById('nameErrorText')
 
 correctName = false
 
const RegularExpressions = {
	pistaName: /^.{1,64}$/, // 1 a 64 digitos.
	
}
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

nombre.addEventListener('keyup',validarFormulario)
nombre.addEventListener('blur',validarFormulario);
	

formulario.addEventListener('submit', (e) => {
	
	if(correctName){
		
	}else{
		e.preventDefault()
	}
});

