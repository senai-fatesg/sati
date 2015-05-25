function SomenteNumero(e) {
	var tecla = (window.event) ? event.keyCode : e.which;
	if ((tecla > 47 && tecla < 58))
		return true;
	else {
		if (tecla == 8 || tecla == 0)
			return true;
		else
			return false;
	}
}

function letras() {
	tecla = event.keyCode;
	if (tecla >= 48 && tecla <= 57) {
		return false;
	} else {
		return true;
	}
}

function valida_email() {
	var filter = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if (!filter.test(document.getElementById("email").value)) {
		alert('Por favor, digite o email corretamente');
		document.getElementById("email").focus();
		return false
	}
}

function validacaoEmail(field) {
	usuario = field.value.substring(0, field.value.indexOf("@"));
	dominio = field.value.substring(field.value.indexOf("@") + 1,
			field.value.length);
	if ((usuario.length >= 1) && (dominio.length >= 3)
			&& (usuario.search("@") == -1) && (dominio.search("@") == -1)
			&& (usuario.search(" ") == -1) && (dominio.search(" ") == -1)
			&& (dominio.search(".") != -1) && (dominio.indexOf(".") >= 1)
			&& (dominio.lastIndexOf(".") < dominio.length - 1)) {
		document.getElementById("msgemail").innerHTML = "E-mail válido";
		alert("E-mail valido");
	} else {
		document.getElementById("msgemail").innerHTML = "<font color='red'>E-mail inválido </font>";
		alert("E-mail invalido");
	}
}




