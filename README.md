# NBE1_1_TEAM6


# 프로젝트 소개

커피 메뉴를 주문할 수 있는 프로그램을 만든다. <br><br>


# 명세서

- 관리자가 상품을 추가할 수 있다.
    - 상품 이름, 카테고리, 가격, 설명, 재고, 상태, 이미지를 사용해 추가 할 수 있다.
- 관리자가 상품 정보를 수정할 수 있다.
    - 상품의 상태는 `SALE, NOT_FOR_SALE, SOLD_OUT, READY_FOR_SALE` 중에서 변경할 수 있다
- 관리자가 상품을 삭제할 수 있다.
    - 상품 삭제시 DB에서 삭제되는 것이 아닌 Status를 `NOT_FOR_SALE`로 변경한다.
- 관리자가 주문된 내역 전체를 볼 수 있다.
<br>

- 사용자가 상품 이름으로 상품을 검색할 수 있다.
    - 상품 이름은 중복이 가능하기에 여러 개의 정보를 보여준다.
- 사용자가 원하는 가격대의 상품을 검색할 수 있다.
    - 최소 금액, 최대 금액을 정해 이 사이 금액의 상품을 보여준다.
- 사용자가 판매중인 상품만 조회할 수 있다.
    - 상품의 상태가 `SALE` 인 상품만 보여준다.
<br>

- 장바구니에 상품을 담을 수 있다.
    - 세션을 이용해 장바구니를 관리한다.
- 장바구니에 담긴 물품을 삭제할 수 있다.
<br>


- 사용자가 상품을 주문할 수 있다.
    - 주소, 우편번호, 이메일을 입력하면 장바구니에 담긴 상품을 구매할 수 있다.
- 사용자가 이메일로 주문 내역을 검색할 수 있다.
- 사용자가 주문을 삭제할 수 있다.
<br><br>
# API 문서

[API 문서](https://www.notion.so/1d16411b731c49309eb7503e2dd194bb?pvs=21)

<br>

# ERD
<img width="860" alt="image" src="https://github.com/user-attachments/assets/cf69b55b-a63a-4517-a2d7-bbcbd89be08c">

<br>


### [DB 수정사항]

- products : product_id → BIGINT
    - 물품의 정보는 민감한 정보가 아니기 때문에 UUID가 아닌 GenerateType.IDENTITY로 해도 상관이 없을거라고 생각해서 변경
- 상품의 재고를 미리 관리자가 정할 수 있게 해서 주문 관리가 쉬워지도록
- 주문을 수정하는게 아닌 주문을 삭제하고 다시 주문하는게 올바른 로직이라고 생각해서 삭제
- orders : order_status 추가
    - 주문 상태 관리
    - `*TODAY_DELIVERY, PENDING_DELIVERY, DELIVERING, DELIVERED`* 중 관리
    
- 반정규화가 필요할 정도로 많이 접근하는 속성이 아니라고 생각하였으며, 주문 조회를 할 때는 항상 orders도 같이 접근하기에 orders테이블에서 받아도면 됨.
- 주문 관련에서는 상품의 카테고리가 필요하지 않다고 생각함
  
<br>

# 역할

| 이름 | Github | 기능 |
| --- | --- | --- |
| 김가현 | [kahyun0255](https://github.com/kahyun0255) | 카트 |
| 김연수 | [yeonsu00](https://github.com/yeonsu00) | 페이징, 상품 검색 |
| 반길현 | [ban-gilhyeon](https://github.com/Ban-gilhyeon) | 주문 취소 |
| 장준우 | [highjjjw](https://github.com/highjjjw) |  |
| 황건하 | [hgh1472](https://github.com/hgh1472) | 상품 이미지 관리 |

<br><br>

# 사용 기능

- Spring Boot + Java
- Mysql + JPA

<br>

# 규칙

- https://velog.io/@chojs28/Git-커밋-메시지-규칙
- [feature/기능]으로 브랜치 생성
