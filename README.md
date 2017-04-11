# httpd
* mvn clean package
* java -jar target/was.jar

# Spec 1,2,3
* /resource/application.json 설정에 따라 Host별로 동작하도록 구현.
* host별로 DocumentRoot를 접근가능한 어느 디렉토리로 설정하여도 되도록 구현하여 과제와 같이 해당 폴더만 세팅하면 동작.
* 각 에러관련 HTML들은 host의 DocumentRoot 폴더에서 찾도록 구현.

# Spec 4
* 구현. ( 상위 디렉토리는 접근 불가하게 validation )

# Spec 5
* logback 설정.

# Spec 6
* annotation을 이용하였으나, 클래스 매핑이어서 reflection을 이용하여 servlet 패키지안의 SimpleServlet 구현체들이 URL이 매핑되도록 구현

# Spec 7
* SimpleServlet 을 구현한 Time 클래스로 구현. 같은 WAS 안에 존재.

# Spec 8
* Request 헤더와 Host 분석 정도   
