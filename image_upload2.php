<?php

    //$uploads_dir . basename( $_FILES['uploaded_file']['name']);
    $uploads_dir = "img_upload/";
    $img_name =  $_FILES['uploaded_file']['name'];
    $now_date = date("YmdHms");
    
    $file_path = $uploads_dir . $_FILES['uploaded_file']['name'];
    if(move_uploaded_file($img_name, $file_path)) {
        
        $result = array("result" => "success");
        
        
    } 
    else{
        $result = array("result" => "error");
    }
    echo json_encode($result);
?>

