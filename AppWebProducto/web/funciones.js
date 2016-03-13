function eliminiar(id){
	if(confirm("Desea eliminar esta Imagen?")){
		window.location="Crud?id="+id+"&accion=eliminar";
	}
}