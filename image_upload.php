<?php

    $conn = mysqli_connect("localhost","zkwpdlxm","flrakxj!2","zkwpdlxm");
    mysqli_query($conn, 'SET NAMES utf8');

    if(mysqli_connect_errno($con)){
        echo "Failed to connect to MySQL: ".mysqli_connect_error();
    
    }

    

    $get_img = $_POST["files"];
    echo $get_img;




?>