<?php
$hostname_localhost = "localhost";
$database_localhost = "gastronomia";
$username_localhost = "root";
$password_localhost = "";

$json = array();

	if(isset($_GET["NoCuenta"])){

		$id = $_GET["NoCuenta"];

		$conexion = mysqli_connect( $hostname_localhost, $username_localhost, $password_localhost, $database_localhost);

		$consulta ="SELECT * FROM usuarios WHERE NoCuenta = '{$id}'";
		$resultado = mysqli_query($conexion, $consulta);

		if ($registro = mysqli_fetch_array($resultado)) {
			
			$result["NoCuenta"] = $registro['NoCuenta'];
			$result["Nombre"] = $registro['Nombre'];
			$result["Apellido"] = $registro['Apellido'];
			$result["Email"] = $registro['Email'];
			$result["Grado"] = $registro['Grado'];
			$result["Grupo"] = $registro['Grupo'];
			$result["Sexo"] = $registro['Sexo'];
			$result["Foto"] = base64_encode($registro['Foto']);
			$json['usuarios'][] = $result;


		}else{

			$resultar["NoCuenta"] = 0;
			$resultar["Nombre"] = 'No registra';
			$resultar["Apellido"] = 'No Registra';
			$resultar["Email"] = 'No registra';
			$resultar["Grado"] = 0;
			$resultar["Grupo"] = 'No registra';
			$resultar["Sexo"] = 'No Registra';
			$resultar["Foto"] = 'No Registra';
			$json['usuarios'][]=$resultar;
		}

		mysqli_close($conexion);
		echo json_encode($json);
	}else{

		$resultar["success"] = 0;
		$resultar["massage"] = 'Ws no retorna';
		$json['usuarios'][] = $resultar;
		echo json_encode($json);
	}

?>