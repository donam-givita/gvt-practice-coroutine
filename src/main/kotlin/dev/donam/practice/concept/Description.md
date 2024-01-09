아침 식사를 위해 아래와 같은 준비가 필요하다

 Pouring Coffee |  3초  
 Fry Eggs       |  7초  
 Fry bacon      | 10초  
 Toast Bread    |  5초  
 Jam on Bread   |  2초  
 Pour Juice     |  3초   
=======================  
 Total          | 30초  
 
순차적으로 아침 식사를 준비하면 30초가 걸린다

SyncCommon에 있는 함수를 사용해서 식사를 준비하는 BlockingBreakfast가 있다.

1. SyncCommon의 Breakfast 이용해서 NonBlockingBreakfast를 작성하라.  
   e.g. Non-blocking의 기준은 main 함수이다  
  
2. SyncCommon의 Breakfast 바탕으로 AsyncCommon의 AsyncBreakfast를 작성하고  
   싱글 쓰레드 기반의 SingleAsyncNonBlockingBreakfast를 작성하라.

3. 3번에서 작성한 코드를 수정해서 멀티 쓰레드 기반의 MultiAsyncNonBlockingBreakfast를 작성하라.
