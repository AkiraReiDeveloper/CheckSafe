<?PHP
$hostname_localhost="localhost";
$database_localhost="gastronomia";
$username_localhost="root";
$password_localhost="";
$conexion=mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	//$noCuenta = $_POST["NoCuenta"];
	$horaSalida = $_POST["HoraSalida"];
	$idUsuario = $_POST["IdUsuario"];
	//$url = "imagenes/".$noCuenta.".jpg";
	// Attempt insert query execution
	$sql = "INSERT INTO asistencia_salida(horaSalida, idUsuarios) VALUES ('$horaSalida', '$idUsuario')";

	if(mysqli_query($conexion, $sql)){
		echo "registra";
	}else{
		echo "noRegistra";
	}
	// Close connection
	mysqli_close($conexion);
	?>
