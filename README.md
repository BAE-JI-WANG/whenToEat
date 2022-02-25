# 언제무꼬 
## whenToEat

### 소비자용 유통관리 어플리케이션
<p align="center">
    <img src='https://user-images.githubusercontent.com/59479926/154832664-7d655104-d7e0-402e-b577-35dd4ddc569d.gif'/>
</p>

## Why need this project

<p align="center">
    <img src='https://user-images.githubusercontent.com/59479926/154832842-f4ff6707-8f21-408e-ba53-886f820728a0.png', width=30%/>
    <img src='https://user-images.githubusercontent.com/59479926/154832845-889e3d07-46af-4d5d-b214-e5cfcface6ec.png', width=40%/>
    <br/>
    <br/>
    - 가정의 엄청난 음식물쓰레기 배출량. 
    <br/>
    - 전 세계적인 문제 음식물쓰레기.
</p>

## UI/UX
    
### SignUp, SignIn
<img width="703" alt="로그인,회원가입" src="https://user-images.githubusercontent.com/59479926/155656321-a5ee3a6f-ce75-46e1-95b3-cf582f7d5b57.png"/>
<br/>
- 어플리케이션 시작 페이지, 회원가입 페이지, 로그인페이지
<br/><br/>
    1. 로그인 페이지 이동
<br/>
    2. 회원가입 페이지 이동
<br/>
    3. 이메일, 패스워드를 이용하여 로그인, 회원정보가 없을 경우 버튼아래 텍스트를 터치하여 회원가입 페이지 이동
<br/>
    4. 간단한 개인정보를 입력하여 회원가입, 이미 회원인 경우 버튼아래 텍스트를 터치하여 로그인 페이지 이동
<br><br>

### List, List Detail
<img width="507" alt="상품리스트, 상세" src="https://user-images.githubusercontent.com/59479926/155656627-f98ded63-0ef8-48e9-bbd3-d9370d0547fb.png">
<br>
- 유통기한 리스트 페이지, 식품 상세정보 페이지
<br/>
<br/>
1. 해당 상품 리스트 터치 시 상품 상세 페이지로 이동
<br/>
2. 상세 페이지에서 등록한 상품의 상세정보 확인 가능
<br/>
3. 상세 페이지에서 유통기한이 지난 상품의 삭제 가능
<br><br>

### Insert Food
<img width="510" alt="상품등록" src="https://user-images.githubusercontent.com/59479926/155663267-f081740e-9d22-4f01-862b-6a81a925cf72.png">
<br>
- 바코드 인식, 상품등록 페이지
<br><br>
1. 바코드 인식시 식품등록 페이지로 이동
<br>
2. 바코드가 없는 상품일 경우 바로 식품등록 페이지 이동가능
<br> 
3. 바코드 인식 프래그먼트 이동 버튼 
<br>
4. 바코드 인식 프래그먼트에서 인식한 바코드번호 자동입력, 직접추가 시 널 허용
<br>
5. 등록할 식품에 대한 사진등록
<br>
6. 인식한 바코드 정보를 토대로 공공 API에서 조회하여 등록된 상품일 경우 자동입력
<br>
7. 아래 캘린더에서 제품의 유통기한을 선택하여 저장
<br><br>

### Edit Profile
<img width="708" alt="개인정보 페이지" src="https://user-images.githubusercontent.com/59479926/155656855-1e5bc215-b53c-4222-be2c-b90d15cff60a.png">
<br>
- 프로필, 회원정보 수정, 옵션 페이지
<br><br>
1. 회원의 이름
<br>
2. 프로필 사진
<br> 
3. 회원의 닉네임과 간단한 코멘트 
<br>
4. 회원정보 수정 페이지 이동
<br>
5. 옵션 페이지 이동
<br>
6. 설정 및 로그아웃 가능
<br>
7. 수정한 회원정보 저장
<br>
8. 프로필 사진 추가 및 회원정보 수정
<br><br>

<!-- ## Stacks
- java
- android studio
- firebase
- theartofdev.edmodo:android-image-cropper 2.7.x
- barcodescanner:zxing 1.9.13
- hdodenhof:circleimageview 2.2.0
- bumptech.glide 4.12.0
 -->
