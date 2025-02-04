## AWS dms로 msk에 cdc kafka event 생성

---

- rds, msk 는 이미 구성 되어 있는 것으로 가정한다. 

1. secrets manager - msk 접근 권한 생성 
```text
[보안암호 유형 선택]
- 다른 유형의 보안 암호

[키/값 페어]
- username: hope-dms
- password: ${password}

[암호화 키]
- aws/secretsmanager

[보안 암호 이름 및 설명]
- 보안 암호 이름: AmazonMSK_hope-dms
- 설명: dms-msk 접근 정보

[태그]
- 생략

[리소스 권한]
- 생략

[교체 구성]
- 생략
```

2. msk - hope-msk - 속성 - AWS Secrets Manager의 연결된 보안암호 - 보안 암호 연결
```text
[보안 암호]
- {1 에서 생성한 보안암호 arn}

kms - 고객관리형 key 생성

[키 구성]
- 키 유형: 대칭
- 키 사용: 암호화 및 해독

[레이블 추가]
- 별칭: hope-dms-key
- 설명: hope rds schema dms를 위한 key입니다.

[키 관리 권한 정의]
- 생략

[키 사용 권한 정의]
- 생략

[키 정책 편집]
- 생략
```

3. kms - 고객 관리형 key 생성
```text
[키 구성]
- 키 유형: 대칭
- 키 사용: 암호화 및 해독

[레이블 추가]
- 별칭: hope-dms-key
- 설명: hope schema msk kafka event dms key

[키 관리 권한 정의]
- 생략

[키 사용 권한 정의]
- 생략

[키 정책 편집]
- 생략
```

4. dms - 서브넷 그룹 생성
```text
[서브넷 그룹 구성]
- 이름: hope-subnet-group
- 설명: hope-dms-replication subnet group
- VPC: ${custom VPC}

[서브넷 추가]
- ${msk kafka region subnet}
```

5. dms - 복제 인스턴스 생성
```text
[설정]
- 이름: hope-dms-replication
- 설명: hope schema 변경사항을 kafka event로 전달하기 위한 dms replication instance

[인스턴스 구성]
- 인스턴스 클래스: dms.ts.small
- 엔진 버전: 3.5.3
- 고가용성: x

[스토리지]
- 할당 스토리지: 50

[연결성 및 보안]
- 네트워크 유형: IPv4
- IPv4 용 VPC: ${custom VPC}
- 복제 서브넷 그룹: hope-subnet-group (4에서 생성)
- 퍼블릭 액세스: false

[연결성 및 보안 - 고급설정]
- AWS KMS 키: hope-dms-key (3에서 생성)
```

6. dms - 엔드포인트 생성 (source)
```text
[엔드포인트 유형]
- 소스 엔드포인트
- RDS DB 인스턴스 선택: false

[엔드포인트 구성]
- 엔드포인트 식별자: hope-dms-source
- 소스 엔진: Amazon Aurora MySQL
- 엔드포인트 데이터베이스에 액세스: 수동으로 액세스 정보 제공
- 서버 이름: ${rds cluster endpoint} // binlog를 읽기 때문에 read only로 사용 불가
- 포트: 3306
- 사용자 이름/암호: ${db_user}/${db_password} // binlog 조회를 위한 권한이 필요함

[KMS 키]
- hope-dms-key (3에서 생성)

[엔드포인트 연결 테스트]
- hope-dms-replication (5에서 생성)
```

7. dms 엔드포인트 생성 (target)
```text
[엔드포인트 유형]
- 대상 엔드포인트
- RDS DB 인스턴스 선택: false

[엔드포인트 구성]
- 엔드포인트 식별자: hope-dms-target
- 대상 엔진: Kafka
- 브로커: ${msk kafka broker}
- 주제: msk.kafka.hope

[엔드포인트 설정]
- 편집기
  {
  "Broker": ${msk kafka broker},
  "Topic": "msk.kafka.hope",
  "MessageFormat": "json",
  "IncludeTransactionDetails": false,
  "IncludePartitionValue": false,
  "PartitionIncludeSchemaTable": false,
  "IncludeTableAlterOperations": false,
  "IncludeControlDetails": false,
  "MessageMaxBytes": 1000000,
  "IncludeNullAndEmpty": false,
  "SecurityProtocol": "sasl-ssl",
  "SaslUsername": ${1에서 생성한 username}, // sasl 인증을 위한 username, msk sasl/scram 인증 사용
  "SaslPassword": ${1에서 생성한 password}, // sasl 인증을 위한 password, msk sasl/scram 인증 사용
  "NoHexPrefix": false
  }

[KMS 키]
- hope-dms-key (3에서 생성)

[엔드포인트 연결 테스트]
- hope-dms-replication (5에서 생성)
```

8. dms - db migration task 생성
```text
[태스크 구성]
- 태스크 식별자: hope-dms-task-0
- 복제 인스턴스: hope-dms-replication (5에서 생성)
- 소스 데이터베이스 엔드포인트: hope-dms-source (6에서 생성)
- 대상 데이터베이스 엔드포인트: hope-dms-target (7에서 생성)

[마이그레이션 유형]
- 복제

[태스크 설정]
- 편집모드: 마법사
- 소스 트랜잭션에 대한 사용자 지정 CDC 중지 모드: 사용자 지정 CDC 중지 모드 비활성화
- 대상 DB에 복구 테이블 생성: false
- 대상 테이블 준비 모드: 아무 작업 안 함
- 전체 로드 완료 후 태스크 중지: 중지 안 함
- LOB 컬럼 설정: LOB 열 포함 안 함
- 데이터 검증: 끄기

[테이블 매핑]
- 편집모드: 마법사
- 선택 규칙
    - where 스키마 이름 is like 'hope' and 소스 테이블 이름 is like 'park', include

[마이그레이션 전 평가]
- 마이그레이션 전 평가 켜기

[마이그레이션 태스크 시작 구성]
- 나중에 수동으로 시작
```

10. dms - db migration task 실행
```text
마이그레이션 전 평가 완료 후, hope-dms-task-0 (8에서 생성) 실행
```
