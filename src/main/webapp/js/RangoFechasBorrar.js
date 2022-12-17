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
	thisTime = validacionEdad(primeraParsed,actualDate)

	if(time <= 0){
		errorMessage.innerText = "La segunda fecha de finalizacion no puede ser menor que la de inicio"
		popUp.style.display = 'none'
		rango = false
	}else{
		errorMessage.innerText = ""
		rango = true
		if(Math.abs(thisTime)<=1 && Math.abs(thisTime)>=0){
			resolution = true
			popUp.style.display = 'block'
		}else{
			resolution = false
			popUp.style.display = 'none'
		}
		
	}
	
	console.log("tiempo:"+time)
	console.log("tiempo actual: " + thisTime)
	console.log("primera fecha: "+primeraParsed)
	console.log("segunda fecha: "+segundaParsed)
	
	
 }
 

primeraFecha.addEventListener('keyup',validarFecha)
primeraFecha.addEventListener('blur',validarFecha);
segundaFecha.addEventListener('keyup',validarFecha)
segundaFecha.addEventListener('blur',validarFecha);
si.addEventListener('click',(e) =>{
	resolution = false
	
});

no.addEventListener('click',(e) =>{
	resolution = false
});

formulario.addEventListener('submit', (e) => {
	
	if(rango && !resolution){
		
	}else{
		e.preventDefault()
	}
});