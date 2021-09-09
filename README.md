#STEP 1
##질문 삭제하기 요구사항
* 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
* 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
* 답변이 없는 경우 삭제가 가능하다.
* 질문자와 답변 글의 모든 답변자 같은 경우 삭제가 가능하다.
* 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경 한다.
* 질문자와답변자가다른경우답변을삭제할수없다.
* 질문과 답변 삭제 이력에 대한 정보를 DeleteHistory를 활용해 남긴다.

##프로그래밍 요구사항
* qna.service.QnaService의 deleteQuestion()는 앞의 질문 삭제 기능을 구현한 코드이다. 
  이 메소드는 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드가 섞여 있다.
* 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드를 분리해 단위 테스트 가능한 코드 에 대해 단위 테스트를 구현한다.

##STEP 2
###기능 요구사항
* 최종 목표는 볼링 점수를 계산하는 프로그램을 구현한다. 1단계 목표는 점수 계산을 제외한 볼링 게임 점수판을 구현하는 것이다.
* 각 프레임이 스트라이크이면 "X", 스페어이면 "9 | /", 미스이면 "8 | 1", 과 같이 출력하도록 구현한다.
* 스트라이크(strike) : 프레임의 첫번째 투구에서 모든 핀(10개)을 쓰러트린 상태
* 스페어(spare) : 프레임의 두번재 투구에서 모든 핀(10개)을 쓰러트린 상태
* 미스(miss) : 프레임의 두번재 투구에서도 모든 핀이 쓰러지지 않은 상태
* 거터(gutter) : 핀을 하나도 쓰러트리지 못한 상태. 거터는 "-"로 표시
* 10 프레임은 스트라이크이거나 스페어이면 한 번을 더 투구할 수 있다.

###구현 시작 방법
* 볼링 게임의 점수 계산 방식 아는 사람은 바로 구현을 시작한다.
* 점수 계산 방식을 모르는 사람은 구글에서 "볼링 점수 계산법"과 같은 키워드로 검색해 볼링 게임의 점수 계산 방식을 학습한 후 구현을 시작한다.

### Pins 의 책임 과 역할..
* 숫자 기록
* 남은 핀 알려주는 기능

### Pitch(두개의 Pins 들로) 책임 과 역할..
* bowl 메시지 로 첫번째구 firstPins 두번째구 secondPins 기록
* 끝났니 ? 메세지
* 상태 바로 구할수 있음 ..Gutter,Strike,Spare ?
* Pitch 를 상속해서 NormalFrame 에서 던질때 FinalFrame 에서 던질때 Pitch

###Frame 의 책임 과 역할..

* isFinish 메시지로 Frame이 끝났는지 알려줌
* bowl 메세지 첫번째 Pins들에 기록 끝난거 아니면
* bowl 메세지 두번째 Pins들에 기록
* Pitch 간 이루어진 첫번째 던졌을때, 두번째 던졌을 때 상태들(Gutter,Strike,Spare )을 가지고 있음
* NormalFrame , FinalFrame 이 있다. Frame을 상속
* 다음 Frame 을 알고 있어야함

###Frames 의 책임 과 역할
* 모든 Frame 들을 가지고 있음
* LinkedList<Frame> frames 

### 협력
* Frame -> isNotFinish ->첫번째공 bowl 메시지 -> State -> bowl 메세지 -> Pins 기록
* State -> isNotFinish -> Frame 두번째공 -> State -> 두번째 Pins 기록

* Frame -> 첫번째공 bowl 메시지 -> State -> bowl 메세지 -> Pins 기록
* Frame -> inFinish -> 다음 프레임..

##STEP 3
###기능 요구사항

* 스트라이크는 다음 2번의 투구까지 점수를 합산해야 한다. 스페어는 다음 1번의 투구까지 점수를 합산해야 한다.

Score 책임 역할
getScore 메세지 가짐 ..
State 가 Normal 이면 합산
스페어나 스트라이크면 geScore 해도  비어있고 다음 프레임으로 넘김
다음 프레임에서 getScore() 하면 스페어 스트라이크에 따라 합산..

Finished true score 를 add
getScore는 strike나 spare 일때는 안함



Frames 돌면서 getScore 
9, 8, 10 + 4,

Frame Finish -> Get Score -> Spare 나 Strike 아니었으면 -> Pitch 합산
                          -> Spare 면 Frame.next.getFirstPin 을 더함
