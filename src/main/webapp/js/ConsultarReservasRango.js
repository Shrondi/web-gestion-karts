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
 
  //funcion simple para calcular el tiempo entre dos fechas en dias
 
 function validacionEdad(actualMillis , inputMillis){
	
	diff = inputMillis-actualMillis
	diff = diff/86400000
	console.log("days: "+diff)
	return diff
}
 
 
 /* funcion usada para ver si los valores de los inputs de la consulta de reservas es válida,
 	para que sea valida, la segunda fecha no debe ser inferior a la segunda nunca
 */ 

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
 
// asignación de eventos de escucha a los inputs de fechas

primeraFecha.addEventListener('keyup',validarFecha)
primeraFecha.addEventListener('blur',validarFecha);
segundaFecha.addEventListener('keyup',validarFecha)
segundaFecha.addEventListener('blur',validarFecha);

// evento de escucha en el que se verá si se cumplen las condiciones para pasar al controlador
formulario.addEventListener('submit', (e) => {
	
	if(rango){
		
	}else{
		e.preventDefault()
	}
});