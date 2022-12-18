/**
 * 
 */
 const formulario = document.getElementById('formReserva')
 const inputFecha = document.getElementById('fecha')
 const fechaActual = new Date()
 const fechaActualMillis = Date.parse(fechaActual)
 const ratioAdultos = document.getElementById('adultos')
 const ratioFamiliar = document.getElementById('familiar')
 const ratioKids = document.getElementById('infantil')
 const label1 = document.getElementById('labelninios')
 const label2 = document.getElementById('labeladultos')
 const infantes = document.getElementById('numeroNinios')
 const adultos = document.getElementById('numeroAdultos')
 const errorfecha = document.getElementById('textErrorFecha')
 var selector = document.getElementById('duracion')
 label1.style.display = 'none'
 label2.style.display = 'none'
 infantes.style.display = 'none'
 adultos.style.display = 'none'
 var inputFechaMillis
 var correctDate = false
 var ratAd = false
 var ratIn = false
 var ratFa = false
 var option = false
  
  
//una funcion simple que calcula que el tiemmpo entre dos fechas en dias
function validacionEdad(actualMillis , inputMillis){
	
	diff = inputMillis-actualMillis
	diff = diff/86400000
	console.log("days: "+diff)
	return diff
}
 
 //funcion usada para validar que la fecha en la que se realiza/modifica una reserva es por lo menos un dia despues de la fecha actual
 const validarFecha = (e) => {
	
	inputFechaMillis = Date.parse(e.target.value)
	time = validacionEdad(fechaActualMillis,inputFechaMillis)
	if(time >= 1){
		errorfecha.innerText = ""
		correctDate = true
	}else{
		errorfecha.innerText = "Se deben realizar reservas con al menos un día de antelación"
		correctDate = false
	}
	
 }
 
//implementacion de los listener para los eventos de importancia en de la vista

//listener de los eventos de pulsación y clicado fuera del input en el que se comprobará el valor de la fecha
inputFecha.addEventListener('keyup',validarFecha)
inputFecha.addEventListener('blur',validarFecha);


/*listener de los eventos de clicado en los inputs tipo ratio que indican el tipo de reserva a hacer, en funcion del ratio pulsado
	se ocultará un input de participantes u otro
*/

ratioAdultos.addEventListener('click',(e) =>{
	ratAd = true
    ratIn = false
	ratFa = false
	label1.style.display = 'none'
	infantes.style.display = 'none'
	label2.style.display = 'block'
	adultos.style.display = 'block'
	adultos.setAttribute("min","1")
	infantes.setAttribute("min","0")
	if(adultos.value < 1){
		adultos.value = 1
	}
	
	infantes.value = 0
	
});

ratioKids.addEventListener('click',(e) =>{
	ratAd = false
    ratIn = true
	ratFa = false
	label1.style.display = 'block'
	infantes.style.display = 'block'
	label2.style.display = 'none'
	adultos.style.display = 'none'
	infantes.setAttribute("min","1")
	adultos.setAttribute("min","0")
	if(infantes.value < 1){
		infantes.value = 1
	}
	adultos.value = 0
	
});


//listener del clicado fuera del input en el que se comprobará que se ha elegido un valor diferente al predefinido
selector.addEventListener('blur', (e) =>{
	
	if(selector.value != ""){
		option = true
	}else{
		option = false
	}
	
});

ratioFamiliar.addEventListener('click',(e) =>{
	ratAd = false
    ratIn = false
	ratFa = true
	label1.style.display = 'block'
	infantes.style.display = 'block'
	label2.style.display = 'block'
	adultos.style.display = 'block'
	infantes.setAttribute("min","1")
	adultos.setAttribute("min","1")
	if(infantes.value < 1 ){
		infantes.value = 1
	}
	if(adultos.value < 1){
		adultos.value = 1
	}
	
	
});
	
// evento de escucha en el que se verá si se cumplen las condiciones para pasar al controlador
formulario.addEventListener('submit', (e) => {
	
	if(correctDate && (ratAd || ratIn || ratFa) && option){
		
	}else{
		e.preventDefault()
	}
});