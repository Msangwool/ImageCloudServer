# ImageCloudServer
AWS S3 저장소를 활용한 HTTP 통신 기반의 Image Server 입니다.

## Config
- AWS S3에 접근하기 위해서는 IAM 사용자 등록을 통해 발급받은 AccessKey, SecretKey가 필요합니다.
- AmazonS3를 반환하는 Bean을 등록해야 AWS S3에 접근해 Resource를 사용할 수 있습니다.

<br>

## Controller
- uploadFile : /v1/aws/create
- findFile : /v1/aws/read/{파일이름}
- upadteFile : /v1/aws/update
- deleteFile : /v1/aws/delete

각 URL에 대한 요청을 처리합니다.

<br>

## Repository
### Interface
- create : 파일을 AWS S3에 업로드합니다.
- read : 파일을 AWS S3에서 읽습니다.
- update : AWS S3에 저장되어 있는 파일을 삭제합니다.
- delete : AWS S3에 저장되어 있는 파일을 삭제하고 새롭게 업로드합니다.

<br>

### Implementation
- create : implements
- read : implements
- update : implements
- delete : implements
- createHttpHeaders : Content-Type을 List<MediaType>으로 받아 HttpHeaders를 반환합니다.
- getExt : 파일 명을 받아 확장자를 분리해 반환합니다.

<br>

## Dto
- FileDeleteDto : 파일 명과 MultipartFile을 객체로 받기 위한 클래스입니다.
- FileNameDto : 파일 명을 객체로 받기 위한 클래스입니다.

<br>

### application.properties
AWS에서 실제 사용되는 Key 값들을 Github에 노출하면 문제가 될 수 있어 블로그에 올려놓은 상태입니다.

<br>

### Velog
https://velog.io/@tkddnwkdb/AWS-S3-Spring-%EC%97%B0%EB%8F%99
