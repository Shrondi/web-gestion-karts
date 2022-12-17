/**
 * 
 */
 const formulario = document.getElementById('formReservasUsuario')
 var primeraFecha = document.getElementById('fechaInicio')
 var segundaFecha = document.getElementById('fechaFin')
 var errorMessage = document.getElementById('dateErrorText')
 var primeraParsed
 var segundaParsed
 
 var rango = false
 
 function validacionEdad(actualMillis , inputMillis){
	
	diff = inputMillis-actualMillis
	diff = diff/86400000
	console.log("days: "+diff)
	return diff
}
 
  const validarFecha = (e) => {
	
	
	if(e.target.name == 'fechaInicio'){
		primeraParsed = Date.parse(e.target.value)
	}
	if(e.target.name == 'fechaFin'){
		segundaParsed = Date.parse(e.target.value)
	}
	
	time = validacionEdad(primeraParsed,segundaParsed)
	
	if(time <= 0){
		 errorMessage.innerText = "La fecha final no puede ser menor que la inicial"
		 rango = false
	}else{
		errorMessage.innerText = ""
		 rango = true
	}
	
	console.log("tiempo:"+time)
	console.log("primera fecha: "+primeraParsed)
	console.log("segunda fecha: "+segundaParsed)
	
	
 }
 

primeraFecha.addEventListener('keyup',validarFecha)
primeraFecha.addEventListener('blur',validarFecha);
segundaFecha.addEventListener('keyup',validarFecha)
segundaFecha.addEventListener('blur',validarFecha);

formulario.addEventListener('submit', (e) => {
	
	if(rango){
		
	}else{
		e.preventDefault()
	}
});