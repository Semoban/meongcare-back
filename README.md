# meongcare-back

## ⚒️ 기술 스택
<b>사용기술</b>
- Java 11
- Spring Boot 2.7.17
- Spring Data JPA + QueryDSL
- MySQL, Redis
- AWS EC2, Docker, Github Actions, Nginx

## ⚙ Infra, CI/CD
<img width="1192" alt="스크린샷 2023-11-20 오후 3 17 14" src="https://github.com/meongCare/meongcare-back/assets/62296495/4d162b2f-34a2-4564-9cf6-089061e62df3">

## 📂 패키지 구조 
```
common
├── jwt
├── util
domain
├── auth
├── dog
├── excreta
├── feed
├── medicalrecord
├── member
├── notice
├── supplements
├── symptom
├── weight
│   ├── application
│   │   └── service
│   ├── domain
│   │   ├── entity
│   │   └── repository
│   └── presentation
│       ├── controller
│       └── dto
infra
├── config
└── image
    └── s3
```

## 📄 ERD
<img width="1544" alt="image" src="https://github.com/meongCare/meongcare-back/assets/62296495/9af284f5-6fb8-46ee-aa54-7c0a7ef4dbf5">
