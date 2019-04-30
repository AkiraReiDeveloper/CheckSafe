<?PHP
$hostname_localhost ="localhost";
$database_localhost ="gastronomia";
$username_localhost ="root";
$password_localhost ="";
$json=array();
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		$consulta="SELECT * from usuarios";
		$resultado=mysqli_query($conexion,$consulta);
		
		while($registro=mysqli_fetch_array($resultado)){

			
			$result["NoCuenta"] = $registro['NoCuenta'];
			$result["Nombre"] = $registro['Nombre'];
			$result["Apellido"] = $registro['Apellido'];
			$result["Email"] = $registro['Email'];
			$result["Grado"] = $registro['Grado'];
			$result["Grupo"] = $registro['Grupo'];
			$result["Sexo"] = $registro['Sexo'];
			$result["Foto"] = base64_encode($registro['Foto']);
			$json['usuarios'][] = $result;
			//echo $registro['id'].' - '.$registro['nombre'].'<br/>';
		}
		mysqli_close($conexion);
		echo json_encode($json);
?>