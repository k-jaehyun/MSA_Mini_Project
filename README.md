
# OverView

![img.png](img.png)


| 서비스                   | 패키지명                          | 포트                |
|-----------------------|-------------------------------|-------------------|
| 유레카서버-**eureka server** | `com.sparta.msa_exam`         | `19090`           |
| 게이트웨이-**gateway**     | `com.sparta.msa_exam.gateway` | `19091`           |
| 상품-**product**        | `com.sparta.msa_exam.product` | `19093` , `19094` |
| 주문-**order**          | `com.sparta.msa_exam.order`   | `19092`           |
| 인증-**auth**           | `com.sparta.msa_exam.auth`    | `19095`           |
| 설정-**config**         | `com.sparta.msa_exam.config`  | `18080`           |

# DB
- H2 (인메모리)

# Cache
- Redis Session Clustering