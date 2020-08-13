# Docker_Android_test

Test communication with url(http://your_current_ip_address:5000/acne/pictures) and 
Android emulator using okhttp3 on Android. 

If the url is set to 127.0.0.1 instead of the own IP address, 
an error occurs because the emulator thinks it is itself. Use the ip address of your pc, not the local host name.

상단 메뉴바의 View>Tool Windows>Device File Explorer를 선택해 sdcard폴더에 올리고, 해당 파일의 이름을 name_a 어레이리스트에, 해당 파일의 경로를 path_a 어레이 리스트에 add해준다.

프로젝트를 실행하면 okhttp통신을 통해 서버로 해당 이미지를 post해 이미지 분석된 스트링 값을 받아 화면에 
