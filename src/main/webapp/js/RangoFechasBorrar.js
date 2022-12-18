/**
 * 
 */
 const formulario = document.getElementById('formReservasUsuario')
 var primeraFecha = document.getElementById('fechaInicio')
 var segundaFecha = document.getElementById('fechaFin')
 var errorMessage = document.getElementById('dateErrorText')
 var si = document.getElementById('si')
 var no = document.getElementById('no')
 var popUp = document.getElementById('popup')
 var primeraParsed
 var segundaParsed
 popUp.style.display = 'none'
 var rango = false
 var actualDate = Date.parse(new Date())
 var resolution = false
 
 //funcion simple para calcular el tiempo entre dos fechas en dias
 function validacionEdad(actualMillis , inputMillis){
	
	diff = inputMillis-actualMillis
	diff = diff/86400000
	console.log("days: "+diff)
	return diff
}

  /*funcion usada para validar que la fecha en la que se borra una reserva es valida, es decir, que la primera fecha no sea superior a la segunda 
  y que si la fecha de una reserva coincide con la fecha del sistema (entendiendo como fecha el dia entero) se de la oportunidad de listar las reservas que se encuentran a un intervalo de fecha menor a 1 dia
  en caso de que se muestren estas opciones será necesario elegir una de las dos opciones mostradas
  */
  const validarFecha = (e) => {
	
	
	if(e.target.name == 'fechaInicio'){
		primeraParsed = Date.parse(e.target.value)
	}
	if(e.target.name == 'fechaFin'){
		segundaParsed = Date.parse(e.target.value)
	}
	
	time = validacionEdad(primeraParsed,segundaParsed)
	//thisTime = validacionEdad(primeraParsed,actualDate)

	if(time <= 0){
		errorMessage.innerText = "La fecha de fin no puede ser menor que la de inicio"
		popUp.style.display = 'none'
		rango = false
	}else{
		errorMessage.innerText = ""
		rango = true
		if((primeraParsed < actualDate) && (actualDate < segundaParsed)){
			resolution = true
			popUp.style.display = 'block'
		}else{
			resolution = false
			popUp.style.display = 'none'
		}
		
	}
	
	console.log("tiempo:"+time)
	console.log("primera fecha: "+primeraParsed)
	console.log("fecha del sistema: "+actualDate)
	console.log("segunda fecha: "+segundaParsed)
	
	
 }
 
//asignación de eventos de escucha a los inputs de las fechas

primeraFecha.addEventListener('keyup',validarFecha)
primeraFecha.addEventListener('blur',validarFecha);
segundaFecha.addEventListener('keyup',validarFecha)
segundaFecha.addEventListener('blur',validarFecha);

//eventos de escucha para saber si se ha pulsado alguna de las opciones en caso de que la fecha actual sea igual a la del input

si.addEventListener('click',(e) =>{
	resolution = false
	
});

no.addEventListener('click',(e) =>{
	resolution = false
});

// evento de escucha en el que se verá si se cumplen las condiciones para pasar al controlador

formulario.addEventListener('submit', (e) => {
	
	if(rango && !resolution){
		
	}else{
		e.preventDefault()
	}
});