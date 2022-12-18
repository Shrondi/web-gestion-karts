/**
 * 
 */
 const formulario = document.getElementById('formConsultTipo')
 const divTipoPista = document.getElementById('tipo')
 const divMinKarts = document.getElementById('min_karts')
 const tipo_select = document.getElementById('tipo_select')
 const inputAdultos = document.getElementById('min_karts_adult')
 const inputKids = document.getElementById('min_karts_inf')
 const labelAdultos = document.getElementById('label2')
 const labelNinos = document.getElementById('label1')
 const chTipo = document.getElementById('tipo_check')
 const chMin = document.getElementById('minKarts_check')
 const tipoErrorMessage = document.getElementById('tipoErrorMessage')
 var optSelected = false
 var tipoChecked = false
 var minChecked = false
 
 divTipoPista.style.display = 'none'
 divMinKarts.style.display = 'none'
 
 //eventos de escucha de las checkboxes que conforman las opciones de busqueda de la consulta de pistas
chTipo.addEventListener('click',(e) => {
	if(chTipo.checked){
		divTipoPista.style.display = 'block'
		tipoChecked = true
	}else{
		divTipoPista.style.display = 'none'
		tipoChecked = false
		if(chMin.checked){
			labelAdultos.style.display = 'block'
			inputAdultos.style.display = 'block'
			labelNinos.style.display = 'block'
			inputKids.style.display = 'block'
		}
	}
	
});

chMin.addEventListener('click',(e) => {
	if(chMin.checked){
		divMinKarts.style.display = 'block'
		minChecked = true
		if(chTipo.checked){
			if(tipo_select.value == "INFANTIL"){
			labelAdultos.style.display = 'none'
			inputAdultos.style.display = 'none'
			labelNinos.style.display = 'block'
			inputKids.style.display = 'block'
			optSelected = true
		}
		if(tipo_select.value == "FAMILIAR"){
			labelAdultos.style.display = 'block'
			inputAdultos.style.display = 'block'
			labelNinos.style.display = 'block'
			inputKids.style.display = 'block'
			optSelected = true
		}
		if(tipo_select.value == "ADULTOS"){
			labelAdultos.style.display = 'block'
			inputAdultos.style.display = 'block'
			labelNinos.style.display = 'none'
			inputKids.style.display = 'none'
			optSelected = true
		}
		
	}
		
	}else{
		divMinKarts.style.display = 'none'
		minChecked = false
	}
	
});
 
 
 //Evento de escucha del selector de tipo, hace aparecer los inputs de karts si tambien esa opción ha sido aceptada
tipo_select.addEventListener('click',(e)=>{
	if(chMin.checked){
		if(tipo_select.value == "INFANTIL"){
			labelAdultos.style.display = 'none'
			inputAdultos.style.display = 'none'
			labelNinos.style.display = 'block'
			inputKids.style.display = 'block'
			optSelected = true
		}
		if(tipo_select.value == "FAMILIAR"){
			labelAdultos.style.display = 'block'
			inputAdultos.style.display = 'block'
			labelNinos.style.display = 'block'
			inputKids.style.display = 'block'
			optSelected = true
		}
		if(tipo_select.value == "ADULTOS"){
			labelAdultos.style.display = 'block'
			inputAdultos.style.display = 'block'
			labelNinos.style.display = 'none'
			inputKids.style.display = 'none'
			optSelected = true
		}
		if(tipo_select.value == ""){
			optSelected = false
			//tipoErrorMessage.innerText = "Debe elegir una opción"
		}
	}
	if(tipo_select.value == ""){
		//tipoErrorMessage.innerText = "Debe elegir una opción"
		optSelected = false
	}else{
		optSelected = true
	}
	
	
});
 
 
// evento de escucha en el que se verá si se cumplen las condiciones para pasar al controlador
formulario.addEventListener('submit', (e) => {
	
	if((tipoChecked && optSelected) || minChecked){
		
		
	}else{
		e.preventDefault()
	}
});