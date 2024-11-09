# 편의점 🏠💸

## 용어 사전 📘

- Item : 물리적인 수량의 단위
- Product : 논리적인 상품의 의미
- Inventory : 물류 창고 -> 상품에 대한 아이템들 (수량) 을 갖는 공간

## 가게는 상품과 할인 정보들을 갖고 있다.

- [x] promotions.md 를 저장한다.
- [x] productions.md 를 저장한다.

## 손님이 오면 가게에서는 상품과 할인 정보들을 보여준다

- [x] 다음과 같은 형태로 출력한다.

```
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
```

## 손님은 상품을 구매할 수 있다.

```구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])```

- [x] 잘못된 형태로 입력하는 경우 오류 메시지를 출력한다.
    - [x] [ 로 시작하지 않는 경우
    - [x] ] 로 끝나지 않는 경우
    - [x] 상품명-수량의 형태로 입력하지 않는 경우
    - [x] 상품명에 한글이 아닌 문자가 입력되는 경우
    - [x] 빈 문자열인 경우
    - [x] 수량이 정수가 아닌 경우

- [x] 손님의 주문을 저장한다

### 손님은 품절되지 않은 상품만 구매할 수 있다.

- [x] 구매할 때마다, 결제된 수량만큼 재고에서 차감된다.
- [x] 재고 수량을 매번 확인해서 결제 가능 여부를 확인한다.

### 손님은 프로모션 재고와 기본 재고를 합한 개수의 이하만 구매할 수 있다.

- [ ] 프로모션 재고와 기본 재고를 합친 수량보다 많은 수량을 주문하는 경우, 다시 주문을 받는다.

### 손님은 프로모션 할인을 받을 수 있다.

- [x] 프로모션 기간 내에 오늘 날짜가 포함된 경우에만 할인을 적용한다.
- [x] 동일 상품에는 여러 프로모션이 적용되지 않는다.
- [x] 재고 내에서만 진행할 수 있다.
- [x] 프로모션 기간 중에는 해당 재고를 우선적으로 차감하고, 프로모션 재고가 부족하면 일반 재고를 사용한다.
- [x] 프로모션 적용이 가능한 상품에 대해 더 적게 가져오면 필요한 수량을 가져오면 혜택을 받을 수 있음을 안내한다.<br>
  ```현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)```
    - [x] 혜택에 대해 안내받은 뒤에 응답에 따라 수량을 추가하거나 추가하지 않도록 한다
    - [x] 추가적으로 가져와야 하는 수량은 현재 보유하고 있는 프로모션 재고에 따라 결정된다.
        - ex) 사이다가 8개 있는데, 8개를 주문하는 경우에는 추가 수량에 대한 안내를 하지 않는다.
        - ex) 사이다가 8개 있고, 5개를 주문하는 경우에는 추가 수량에 대한 안내를 한다.
- [x] 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내한다.<br>
  ```현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)```
    - [x] 만약 프로모션 혜택 없이는 결제를 하지 않겠다고 하는 경우, 프로모션이 적용되지 않는 수량만큼을 빼고 주문을 진행한다.

### 손님은 멤버십 할인을 받을 수 있다.

- [x] 프로모션이 적용되지 않은 금액의 30% 를 할인 받는다.
    - [x] 최대 한도는 8000원이다.

## 가게에서는 영수증을 발행한다.

```
==============W 편의점================
상품명		수량	금액
콜라		10 	10,000
=============증	정===============
콜라		2
====================================
총구매액		10	10,000
행사할인			-2,000
멤버십할인			-0
내실돈			 8,000
```

- [ ] 구매한 상품과 수량을 계산한다
- [ ] 증정 상품과 수량을 계산한다
- [ ] 총 구매액을 계산한다.
- [x] 행사 할인 금액을 계산한다.
- [ ] 멤버십 할인 금액을 계산한다
- [ ] 낼 돈을 계산한다

## 손님은 원할 때 편의점에서 나갈 수 있다.

- [ ] 추가 구매 여부를 확인하기 위해 안내 문구를 출력한다.<br>
  ```감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)```
- [ ] 나가질 원하지 않는 경우 계속해서 주문을 받는다.
    - 이 때, 구매한 제품들의 수량은 감소시켜 보여줘야 한다.

