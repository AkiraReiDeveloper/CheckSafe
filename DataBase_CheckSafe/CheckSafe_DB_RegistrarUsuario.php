<?PHP
$hostname_localhost="localhost";
$database_localhost="gastronomia";
$username_localhost="root";
$password_localhost="";
$conexion=mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	$noCuenta = $_POST["NoCuenta"];
	$nombre = $_POST["Nombre"];
	$apellido = $_POST["Apellido"];
	$email = $_POST["Email"];
	$grado = $_POST["Grado"];
	$grupo = $_POST["Grupo"];
	$sexo = $_POST["Sexo"];
	$imagen = $_POST["Foto"];
	$path = "imagenes/$noCuenta.jpg";
	$url = "http://$hostname_localhost/DataBase_CheckSafe/$path";
	//$url = "imagenes/".$noCuenta.".jpg";
	file_put_contents($path,base64_decode($imagen));
	$bytesArchivo=file_get_contents($path);
	$sql="INSERT INTO usuarios VALUES (?,?,?,?,?,?,?,?)";
	$stm=$conexion->prepare($sql);
	$stm->bind_param("isssisss",$noCuenta,$nombre,$apellido,$email,$grado,$grupo,$sexo,$bytesArchivo);	

	if($stm->execute()){
		echo "registra";
	}else{
		echo "noRegistra";
	}
	
	mysqli_close($conexion);
?>