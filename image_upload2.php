<?php

    $conn = mysqli_connect("localhost","zkwpdlxm","flrakxj!2","zkwpdlxm");

    //$uploads_dir . basename( $_FILES['uploaded_file']['name']);
    $file_path = "./img_upload/";
    $file_path = $file_path . basename( $_FILES['uploaded_file']['name']);
    $now_date = date("YmdHis",time());
    
    if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
        // 동일한 파일명이면 덮어쓰기를 한다.
        if(rename($file_path, "./img_upload/".$now_date.".jpg")){
            
            $file_name = "http://zkwpdlxm.dothome.co.kr/img_upload/".$now_date.".jpg";
            $res = "INSERT INTO USER_IMAGE (image_path) VALUES('".$file_name."')";
            $row = mysqli_query($conn, $res);

            $result = array("result" => "success");


        }
        //$result = array("result" => "success");
        else{
            $result = array("result" => "error");
        }
    } 
    
    else{
        $result = array("result" => "error");
    }



    echo json_encode($result);

    mysqli_close($conn);


?>

