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
  
function validacionEdad(actualMillis , inputMillis){
	
	diff = inputMillis-actualMillis
	diff = diff/86400000
	console.log("days: "+diff)
	return diff
}
 
 
 const validarFecha = (e) => {
	
	inputFechaMillis = Date.parse(e.target.value)
	time = validacionEdad(fechaActualMillis,inputFechaMillis)
	if(time >= 1){
		errorfecha.innerText = ""
		correctDate = true
	}else{
		errorfecha.innerText = "Se deben realizar reservas con al menos un dia de antelación"
		correctDate = false
	}
	
 }
 

inputFecha.addEventListener('keyup',validarFecha)
inputFecha.addEventListener('blur',validarFecha);
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
	

formulario.addEventListener('submit', (e) => {
	
	if(correctDate && (ratAd || ratIn || ratFa) && option){
		
	}else{
		e.preventDefault()
	}
});