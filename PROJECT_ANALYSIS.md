# MSA 프로젝트 분석 보고서

## 📋 프로젝트 개요

**프로젝트 명**: MSA (Microservice Architecture) 기반 통합 예매 애플리케이션  
**플랫폼**: Android  
**주요 기능**: 테마파크 티켓 예매, 놀이기구 예약, 실시간 혼잡도 확인  
**아키텍처**: 마이크로서비스 아키텍처 (MSA) 기반 클라이언트 애플리케이션

## 🎯 주요 기능

### 1. 사용자 인증 (Authentication)
- **Google OAuth 로그인**: Firebase Authentication과 Google Sign-In을 사용한 소셜 로그인
- **자동 로그인**: Firebase를 통한 세션 관리
- **사용자 프로필 관리**: 닉네임, 회원 번호, 이메일 정보 표시

### 2. 티켓 예매 (Ticket Booking)
- **티켓 유형**:
  - 종합이용권: 성인(59,000원), 청소년(52,000원), 어린이(46,000원)
  - 파크이용권: 성인(47,000원), 청소년(41,000원), 어린이(35,000원)
- **날짜 선택**: CalendarView를 통한 방문 날짜 선택
- **인원 선택**: 성인/청소년/어린이 인원 수 조절
- **결제 통합**: Bootpay 결제 시스템 연동
- **QR 코드 생성**: ZXing 라이브러리를 사용한 티켓 QR 코드 생성

### 3. 놀이기구 예약 (Ride Reservation)
- **실시간 예약**: 백엔드 API를 통한 놀이기구 예약
- **대기열 관리**: Queue 시스템을 통한 대기 관리
- **예약 정보 표시**: 예약한 놀이기구, 시간, 인원 수 표시
- **예약 취소**: 예약 취소 기능 제공

### 4. 지도 및 혼잡도 (Map & Congestion)
- **Google Maps 통합**: 테마파크 내 시설 위치 표시
- **실시간 혼잡도**: 각 시설의 혼잡도를 시각적으로 표시 (Circle Overlay)
- **카테고리 필터링**:
  - ALL (전체)
  - RIDING (놀이기구)
  - RESTAURANT (레스토랑)
  - CAFE (카페)
  - CONVENIENCE (편의시설)
- **장소 검색**: Google Places API를 통한 장소 검색
- **상세 정보**: BottomSheet를 통한 시설 상세 정보 표시

### 5. 마이페이지 (My Page)
- **사용자 정보 표시**: 닉네임, 회원 번호, 이메일
- **로그아웃**: Google Sign-In 및 Firebase 로그아웃
- **회원 탈퇴**: 계정 삭제 기능

### 6. 홈 화면 (Home/Reservation Fragment)
- **QR 코드 표시**: 구매한 티켓의 QR 코드
- **티켓 정보**: 티켓 종류, 날짜, 시간, 좌석 정보
- **실시간 업데이트**: LiveData를 통한 실시간 UI 업데이트

## 🏗 기술 스택

### Android Framework
- **최소 SDK**: 31 (Android 12)
- **타겟 SDK**: 34 (Android 14)
- **컴파일 SDK**: 34
- **빌드 도구**: Gradle 8.4, AGP 8.3.1
- **언어**: Java
- **아키텍처 패턴**: MVVM (Model-View-ViewModel)

### 주요 라이브러리

#### UI 컴포넌트
- **AndroidX AppCompat**: 1.6.1 - 하위 호환성 지원
- **Material Design**: 1.11.0 - Material Design 컴포넌트
- **ConstraintLayout**: 2.1.4 - 유연한 레이아웃
- **RecyclerView**: 1.1.0 - 리스트 표시
- **CardView**: 1.0.0 - 카드형 UI
- **ViewPager Transforms**: 2.0.24 - ViewPager 애니메이션 효과
- **Material CalendarView**: 2.0.1 - 캘린더 UI

#### 네트워킹 & API
- **Retrofit**: 2.9.0 - REST API 통신
- **Gson Converter**: 2.9.0 - JSON 직렬화/역직렬화
- **OkHttp Logging Interceptor**: 4.9.3 - HTTP 로깅

#### 인증 & 데이터베이스
- **Firebase Authentication**: 23.0.0 - 사용자 인증
- **Firebase Realtime Database**: 21.0.0 - 실시간 데이터베이스
- **Firebase BOM**: 33.4.0 - Firebase 종속성 관리
- **Firebase Analytics** - 분석 도구
- **Google Play Services Auth**: 20.5.0 - Google 로그인

#### 지도 & 위치
- **Google Play Services Maps**: 19.0.0 - Google Maps
- **Google Play Services Location**: 21.3.0 - 위치 서비스
- **Google Maps Utils**: 2.3.0 - 지도 유틸리티
- **Google Places**: 2.7.0 - 장소 API
- **Daum Map SDK** (libDaumMapAndroid.jar) - 다음 지도 (주석 처리됨)

#### 결제 & QR 코드
- **Bootpay Android**: latest - 결제 시스템
- **ZXing Core**: 3.5.1 - QR 코드 생성/인식

#### 이미지 처리
- **Glide**: 4.15.0 - 이미지 로딩 및 캐싱

#### 테스트
- **JUnit**: 4.13.2 - 단위 테스트
- **AndroidX Test JUnit**: 1.1.5 - Android 단위 테스트
- **Espresso Core**: 3.5.1 - UI 테스트

## 🌐 백엔드 API 통합

### 1. Ticket Service
**Base URL**: `https://parkservice.mintcocoa.cc/tickets/`

**엔드포인트**:
- `POST /enqueue` - 대기열 등록
- `POST /api/tickets/validate` - 티켓 검증

**주요 모델**:
- `QueueRequest`: 놀이기구 ID, 사용자 ID
- `QueueResponse`: 응답 메시지
- `TicketRequest`: 사용자 ID, 파크 ID, 티켓 타입, 시설 ID 목록
- `TicketResponse`: 상태, 티켓 ID, 인코딩된 QR 코드, 티켓 데이터

### 2. Reservation Service
**Base URL**: `https://parkservice.mintcocoa.cc/reservations/`

**엔드포인트**:
- `POST /api/reserve/{ride_id}` - 놀이기구 예약
- `POST /api/cancel/{ride_id}` - 예약 취소

**주요 모델**:
- `ReservationRequest`: 인원 수, 예약 시간, 사용자 ID
- `CancelRequest`: 취소 사유

## 📱 화면 구성

### 네비게이션 구조
Bottom Navigation Bar를 통한 4개 주요 화면:

1. **홈 (tab_home)** - `ReservationFragment`
   - QR 코드 표시
   - 티켓 정보 확인

2. **예약 (tab_reservation)** - `InfoFragment`
   - 놀이기구 예약 정보
   - 예약 취소
   - 예약 페이지 이동

3. **혼잡도 (tab_congestion)** - `MapFragment`
   - Google Maps 기반 지도
   - 실시간 혼잡도 표시
   - 카테고리별 필터링

4. **마이페이지 (tab_mypage)** - `MypageFragment`
   - 사용자 정보
   - 로그아웃/회원 탈퇴

### 추가 화면
- **LoginActivity** - Google 로그인
- **GoogleLogin** - Google 인증 처리
- **PaymentFragment** - 티켓 구매 및 결제
- **InfoFragment2, InfoFragment3** - 추가 정보 화면
- **TicketSelectFragment** - 티켓 선택

## 🔐 보안 설정

### 권한 (Permissions)
```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

### 네트워크 보안
- **usesCleartextTraffic**: true (HTTP 통신 허용)
- **networkSecurityConfig**: 커스텀 네트워크 보안 설정

### API 키
- **Google Maps API Key**: `AIzaSyDn4xd4BADBaX_ee5Nt3hjQPsncaZW0DlI`
- **Google OAuth Client ID**: `224209448398-7isv0sefkcqovsgc61vkaj6j9mo9hkeb.apps.googleusercontent.com`

⚠️ **보안 권고**: API 키와 OAuth 클라이언트 ID가 코드에 하드코딩되어 있습니다. 프로덕션 환경에서는 환경 변수나 보안 저장소를 사용해야 합니다.

## 🎨 아키텍처 패턴

### MVVM (Model-View-ViewModel)
프로젝트는 MVVM 패턴을 따릅니다:

**Model**:
- Data 클래스들: `TicketRequest`, `TicketResponse`, `ReservationRequest`, `QueueRequest` 등
- Retrofit 인터페이스: `ApiService`, `ReservationsApiService`

**View**:
- Activity: `MainActivity`, `LoginActivity`, `GoogleLogin`
- Fragment: `ReservationFragment`, `MapFragment`, `InfoFragment`, `MypageFragment` 등

**ViewModel**:
- `SharedViewModel`: 공유 데이터 관리 (QR 코드, 티켓 정보, 인원 수 등)
- `ReservationViewModel`: 예약 정보 관리 (SharedPreferences 사용)
- `TicketViewModel`: 티켓 정보 관리

### LiveData 활용
- UI와 데이터 간 반응형 프로그래밍
- 생명주기 인식 데이터 관찰
- 자동 UI 업데이트

### Repository 패턴
- `RetrofitClient`: 티켓 서비스 Retrofit 인스턴스
- `ReservationsRetrofitClient`: 예약 서비스 Retrofit 인스턴스

## 🔄 데이터 흐름

### 티켓 구매 프로세스
1. 사용자가 `PaymentFragment`에서 티켓 선택 및 날짜 선택
2. Bootpay를 통한 결제 처리
3. 백엔드 API로 티켓 검증 요청 (`/api/tickets/validate`)
4. 서버로부터 인코딩된 QR 코드 수신
5. `SharedViewModel`에 QR 코드 및 티켓 정보 저장
6. `ReservationFragment`에서 QR 코드 표시

### 놀이기구 예약 프로세스
1. 사용자가 놀이기구 선택
2. 대기열 등록 (`/enqueue`)
3. 예약 API 호출 (`/api/reserve/{ride_id}`)
4. `ReservationViewModel`에 예약 정보 저장 (SharedPreferences)
5. `InfoFragment`에서 예약 정보 표시

### 혼잡도 확인 프로세스
1. `MapFragment`에서 Google Maps 초기화
2. 로컬 JSON 파일에서 시설 정보 로드
3. 지도에 마커 및 Circle Overlay 표시
4. 카테고리별 필터링 기능 제공
5. BottomSheet로 시설 상세 정보 표시

## 📊 상태 관리

### SharedPreferences
- `ReservationViewModel`에서 예약 정보 영구 저장
- Key: `ride_name`, `ride_time`, `ride_people`

### LiveData
- `SharedViewModel`: 앱 전역 상태 (QR 코드, 티켓 정보, 인원 수)
- `ReservationViewModel`: 예약 정보 상태
- `TicketViewModel`: 티켓 상태

### ViewModelProvider
- Activity 범위의 ViewModel 공유
- Fragment 간 데이터 공유

## 🧪 테스트 구조

### 테스트 디렉토리
- `src/androidTest`: Android Instrumentation 테스트
- `src/test`: 단위 테스트

### 테스트 의존성
- JUnit 4.13.2
- AndroidX Test JUnit 1.1.5
- Espresso Core 3.5.1

## 📦 빌드 설정

### Gradle 설정
- **Gradle 버전**: 8.4
- **Android Gradle Plugin**: 8.3.1
- **Java 버전**: 1.8 (SOURCE & TARGET)

### 저장소
- Google
- Maven Central
- JitPack
- Naver Maps Repository (코드에 포함되어 있으나 사용되지 않음)

### ProGuard
- Release 빌드에서 난독화 비활성화 (`isMinifyEnabled = false`)

## 🎯 개선 제안

### 1. 보안
- [ ] API 키를 환경 변수나 BuildConfig로 이동
- [ ] OAuth 클라이언트 ID 보호
- [ ] ProGuard 활성화 (Release 빌드)
- [ ] SSL Pinning 구현 (HTTPS 통신 보안 강화)

### 2. 코드 품질
- [ ] Kotlin 마이그레이션 고려 (현재 Java)
- [ ] 에러 핸들링 강화 (Retrofit 콜백)
- [ ] 로깅 전략 개선 (프로덕션에서 로그 제거)
- [ ] 주석 처리된 코드 정리

### 3. 아키텍처
- [ ] Repository 패턴 완전 구현
- [ ] UseCase 레이어 추가
- [ ] Dependency Injection (Hilt/Dagger) 도입
- [ ] 단일 책임 원칙 준수 (일부 Fragment가 너무 많은 책임을 가짐)

### 4. UI/UX
- [ ] 로딩 인디케이터 추가
- [ ] 에러 상태 UI 개선
- [ ] 오프라인 모드 지원
- [ ] 접근성 개선 (Content Description 등)

### 5. 테스트
- [ ] 단위 테스트 커버리지 확대
- [ ] UI 테스트 추가
- [ ] Integration 테스트 추가
- [ ] CI/CD 파이프라인 구축

### 6. 성능
- [ ] 이미지 최적화 (Glide 설정)
- [ ] 메모리 누수 확인
- [ ] 네트워크 요청 최적화 (캐싱 전략)
- [ ] 지도 성능 최적화

### 7. 기능
- [ ] 오프라인 티켓 저장
- [ ] 푸시 알림 (예약 시간 알림)
- [ ] 다국어 지원
- [ ] 다크 모드 지원
- [ ] 소셜 공유 기능

## 📝 주요 파일 구조

```
app/src/main/
├── java/com/example/msa/
│   ├── MainActivity.java (메인 액티비티, Bottom Navigation 관리)
│   ├── LoginActivity.java (로그인 화면)
│   ├── GoogleLogin.java (Google 인증 처리)
│   │
│   ├── Fragments/
│   │   ├── ReservationFragment.java (홈 화면, QR 코드 표시)
│   │   ├── InfoFragment.java (예약 정보)
│   │   ├── MapFragment.java (지도 및 혼잡도)
│   │   ├── MypageFragment.java (마이페이지)
│   │   ├── PaymentFragment.java (티켓 구매)
│   │   └── TicketFragment.java (티켓 정보)
│   │
│   ├── ViewModels/
│   │   ├── SharedViewModel.java (공유 상태)
│   │   ├── ReservationViewModel.java (예약 상태)
│   │   └── TicketViewModel.java (티켓 상태)
│   │
│   ├── API/
│   │   ├── ApiService.java (티켓 API 인터페이스)
│   │   ├── ReservationsApiService.java (예약 API 인터페이스)
│   │   ├── RetrofitClient.java (티켓 서비스 클라이언트)
│   │   └── ReservationsRetrofitClient.java (예약 서비스 클라이언트)
│   │
│   └── Models/
│       ├── QueueRequest.java, QueueResponse.java
│       ├── TicketRequest.java, TicketResponse.java
│       ├── ReservationRequest.java
│       └── CancelRequest.java
│
├── res/
│   ├── layout/ (XML 레이아웃 파일들)
│   ├── values/ (색상, 문자열, 스타일)
│   ├── drawable/ (이미지 리소스)
│   └── xml/ (네트워크 보안 설정 등)
│
└── AndroidManifest.xml (앱 설정 및 권한)
```

## 🌟 프로젝트 특징

1. **마이크로서비스 아키텍처 통합**: 여러 MSA 백엔드 서비스와 통합
2. **실시간 기능**: LiveData를 통한 실시간 UI 업데이트
3. **지도 기반 서비스**: Google Maps를 활용한 시각적 정보 제공
4. **결제 통합**: Bootpay를 통한 간편한 결제
5. **QR 코드 기술**: 디지털 티켓 시스템
6. **Firebase 통합**: 인증 및 실시간 데이터베이스

## 📈 프로젝트 성숙도

- **아키텍처**: ⭐⭐⭐⭐ (MVVM 패턴 잘 구현됨)
- **코드 품질**: ⭐⭐⭐ (개선 여지 있음)
- **UI/UX**: ⭐⭐⭐⭐ (Material Design 적용)
- **보안**: ⭐⭐ (API 키 하드코딩 등 보안 개선 필요)
- **테스트**: ⭐ (테스트 커버리지 부족)
- **문서화**: ⭐⭐ (기본 README만 존재)

## 🎓 결론

이 프로젝트는 **마이크로서비스 아키텍처를 기반으로 한 테마파크 통합 예매 시스템**의 Android 클라이언트입니다. MVVM 패턴을 적용하여 구조화되어 있으며, Firebase, Google Maps, 결제 시스템 등 다양한 서드파티 서비스와 통합되어 있습니다.

주요 강점:
- 깔끔한 MVVM 아키텍처
- 다양한 최신 Android 라이브러리 활용
- 실용적인 기능들 (티켓 예매, 예약, 지도, QR 코드)

개선 필요 사항:
- 보안 강화 (API 키 관리)
- 테스트 커버리지 확대
- 에러 핸들링 개선
- 코드 문서화

전반적으로 **학습용 프로젝트 또는 MVP 단계의 상용 프로젝트**로 적합한 수준이며, 위에서 제안한 개선사항들을 적용하면 프로덕션 레벨로 발전시킬 수 있을 것으로 판단됩니다.

---

**분석 일자**: 2025-11-06  
**분석자**: GitHub Copilot AI Agent
