<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:h="http://java.sun.com/jsf/html"
	xmlns:f="http://java.sun.com/jsf/core"
	xmlns:ui="http://java.sun.com/jsf/facelets"
	xmlns:p="http://primefaces.org/ui">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="initial-scale=1">
<title>Sati System</title>

<link href="css/csslogin/bootstrap.css" rel="stylesheet" />
<link href="css/csslogin/bootstrap-min.css" rel="stylesheet" />

<script>
	function focar() {
		document.getElementById("usuario").focus();
	}
</script>

</head>
<body>
	<form action="j_spring_security_check" method="post">
		<div class="container content">
			<div class="col-xs-12 col-md-4 col-md-offset-4">
				<div class="row-fluid user-row">
					<img src="imagens/satilogomarca.jpg" class="img-responsive"
						alt="Conxole Admin" />
				</div>
				<h2 class="form-signin-heading">Acesso ao Sistema</h2>
				<label for="inputEmail" class="sr-only">Login</label> <input
					type="number"  id="inputEmail" class="form-control" 
					placeholder="Informe seu Cpf aqui! Somente números" required autofocus
					name="j_username" onkeypress="if(this.value.length > 10) return false;" >
				<label for="inputPassword" class="sr-only">Senha</label> <input
					type="password" id="inputPassword" class="form-control" maxlength="8"
					placeholder="Senha" name="j_password" required>
				<button class="btn btn-lg btn-success btn-block" type="submit">Entrar</button>	

				<%
					if (request.getParameter("msg") != null) {
						out.print("<span style='color: red;font-weight: bold;'>Usuário ou senha incorretos</span>");
					}
				%>
			</div>
		</div>
	</form>
</body>
</html>
<!-- By Silas A. -->