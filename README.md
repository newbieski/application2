# application2
> COC API를 이용하여 대시보드를 구성
> - springboot
> - mustache
> - JPA
> - postgreDB
> - AWS
> - API G/W (현재는 proxy 역할만 수행)

## 특징
> - 현황과 통계를 보여주는 것이 목표임
> - COC REST API 호출을 통해 데이터를 가져옴
> - JSON 결과값의 일부를 추출해서 DB에 저장함
> - 중복된 값을 저장하고, 저장 시간을 생성해서 구분함 => 일단 저장하고 보자
> - 대시보드 구성 시점에는 DB에 저장된 값을 기준으로 해서 구성함
> 
## 개선점
> - COC REST API 호출 후 데이터 저장시 많은 시간이 소요됨
> - 아직은 배치로 COC REST API를 호출하지 못함
> - 예외처리, 로그가 상당히 부족함
> - DB 설계가 거의 안되어있음
> - 코드가 난잡함
> - 기능 확장/변경에 취약함 -> 새로 모듈을 만드는게 아직은 빠름
> - 이 상태에서 클랜전 경기 결과를 집계하는 기능을 추가/확장한다면?
> - REST API 설계가 이상하고 정리가 안되어있음


## 시사점
> - DB 설계가 중요하다
> - JSON에서 데이터 추출하는 것이 어렵다
> - 결과적으로 어떤 데이터를 처리할지에 대한 기초적인 고민 부족
> - service, controller, domain의 역할!
> - 일감을 잘 나눠주는 고민이 필요함
> - 이런 유형의 데이터 + DB 처리는 어떻게 하나
> - JPA를 이렇게 쓰는게 맞나?
> - REST API로 데이터를 가져오는 모듈은 service일까 repository 일까
> - service에서 일을 이렇게 많이 해도 될까?

## URL
https://hh3m0ctbab.execute-api.ap-northeast-1.amazonaws.com