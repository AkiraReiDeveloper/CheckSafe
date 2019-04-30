<?PHP
$hostname_localhost = "localhost";
$database_localhost = "gastronomia";
$username_localhost = "root";
$password_localhost = "";

$json = array();

if (isset($_GET["NoCuenta"]) && isset($_GET["Nombre"]) && isset($_GET["Apellido"]) && isset($_GET["Email"]) && isset($_GET["Grado"]) && isset($_GET["Grupo"]) && isset($_GET["Sexo"])) {
	
	$nocuenta = $_GET['NoCuenta'];
	$nombre = $_GET['Nombre'];
	$apellido = $_GET['Apellido'];
	$email = $_GET['Email'];
	$grado = $_GET['Grado'];
	$grupo = $_GET['Grupo'];
	$sexo = $_GET['Sexo'];

	$conexion = mysqli_connect($hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

	$insert = "INSERT INTO usuarios(NoCuenta, Nombre, Apellido, Email, Grado, Grupo, Sexo) VALUES ('{$nocuenta}', '{$nombre}', '{$apellido}', '{$email}', '{$grado}', '{$grupo}', '{$sexo}')";
	$resultado_insert = mysqli_query( $conexion, $insert );

	if ($resultado_insert) {
		
		$consulta = "SELECT * FROM usuarios WHERE NoCuenta = '{$nocuenta}'";
		$resultado = mysqli_query($conexion, $consulta);

		if ($registro = mysqli_fetch_array($resultado)) {
			
			$json['usuarios'][] = $registro;
		}

		mysqli_close($conexion);
		echo json_encode($json);
	}else{

		$resulta["NoCuenta"] = 0;
		$resulta["Nombre"] = 'No Registra';
		$resulta["Apellido"] = 'No Registra';
		$resulta["Email"] = 'No Registra';
		$resulta["Grado"] = 'No Registra';
		$resulta["Grupo"] = 'No Registra';
		$resulta["Sexo"] = 'No Registra';
		$json['usuarios'][] = $resulta;
		echo json_encode($json);
	}
}else{

	$resulta["NoCuenta"] = 0;
	$resulta["Nombre"] = 'Ws No Registra';
	$resulta["Apellido"] = 'Ws No Registra';
	$resulta["Email"] = 'Ws No Registra';
	$resulta["Grado"] = 'Ws No Registra';
	$resulta["Grupo"] = 'Ws No Registra';
	$resulta["Sexo"] = 'Ws No Registra';
	$json['usuarios'][] = $resulta;
	echo json_encode($json);
}

?>