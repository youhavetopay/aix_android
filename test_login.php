<?php
header("Content-Type: text/html;charset=UTF-8");
$con = mysqli_connect("localhost","zkwpdlxm","flrakxj!2","zkwpdlxm");

if(mysqli_connect_errno($con)){
    echo "Failed to connect to MySQL: ".mysqli_connect_error();

}

if($user_id = $_POST['Data1']){
    echo "1";
}
else{
    echo "-1";
}


?>