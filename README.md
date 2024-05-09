# android-movie-theater

## 기능 목록

- [x] 상영작 지금 예매를 누르면 해당 영화를 상영하는 영화관을 선택할 수 있다.
    - [x] 상영작을 상영하고 있는 영화관 정보를 불러온다. 
    - [x] 상영작을 가진 각 영화관의 총 상영 시간 개수를 계산하여 보여준다.
    - [x] 모든 영화관 정보를 불러온다.
    - [x] 특정 영화관 정보를 불러온다.
    - [x] 상영작 지금 예매를 누르면 bottomSheet 가 나타난다.
      - [x] bottomSheet 는 극장 이름을 보여준다.
      - [x] bottomSheet 는 상영 시간 개수를 보여준다.
      - [x] 영화를 상영하는 영화관을 표시한다.
      - [x] 영화관 별로 영화의 상영시간을 보여준다.

- [x] 영화관을 선택하면 영화 예매(날짜, 시간, 개수를 정할 수 있는) 화면으로 이동.
- [x] 영화관 정보 필요
    - [x] `영화관 id`, `영화관 이름`, `상영작들`
 
- [x] 예매 완료 화면에서 극장 정보를 추가로 보여준다.
- [x] 예매 정보
    - [x] `영화관 이름` 이 추가되어야 함.

- [x] 영화 예매 내역, 홈, 설정 화면으로 이동(navigate)할 수 있다.
    - [x] 바텀 네비게이션을 보여준다
    - [x] 영화 예매 내역 화면
    - [x] 설정 화면
    - [x] 홈 화면(상영작 목록)

- [x] 좌석 선택 후 확인을 누르면 다이얼로그가 뜬다.

### step 3
- 예매 내역을 저장한다.
    - 데이터 베이스에 저장한다.
- 모든 예매 내역을 불러온다.
    - 예매 preview 내역은 날짜, 시간, 영화관 이름, 영화 제목을 보여준다.
    - 예매 상세 내역은 영화 제목, 날짜, 시간, 인원, 좌석, 영화관 이름, 결제 금액을 보여준다.

## 프로그래밍 요구사항 
- DataBinding 을 사용해야 한다.
