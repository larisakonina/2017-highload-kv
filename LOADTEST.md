# Нагрузочное тестирование
Тестирование производилось на виртуальной машине с ОС Ubuntu с использованием утилиты wrk и скриптов на языке Lua. Тестировались методы put (с/без перезаписи) и get (с/без повторов) на 2/4 потоках и 4 соединениях в течении пяти минут. Перед тестированием метода get осуществлялась запись с помощью put в течении 20 минут.
## Тестирование

2 потока 4 соединения
### PUT без перезаписи

larisa@larisa:~/2017-highload-kv$ wrk --latency -t2 -c4 -d5m -s /home/larisa/loadtest/putWithoutOverwrite.lua  http://localhost:8080/
Running 5m test @ http://localhost:8080/
  2 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    59.80ms   77.32ms   1.58s    93.85%
    Req/Sec    19.18     11.68    50.00     64.80%
  Latency Distribution
     50%   45.46ms
     75%   65.35ms
     90%  113.57ms
     99%  248.69ms
  10048 requests in 5.00m, 0.90MB read
Requests/sec:     33.48
Transfer/sec:      3.07KB

### PUT с перезаписью

larisa@larisa:~/2017-highload-kv$ wrk --latency -t2 -c4 -d5m -s /home/larisa/loadtest/putWithOverwrite.lua  http://localhost:8080/
Running 5m test @ http://localhost:8080/
  2 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    48.40ms   35.94ms 396.70ms   70.31%
    Req/Sec    21.58     11.64    50.00     55.54%
  Latency Distribution
     50%   44.30ms
     75%   58.72ms
     90%   87.55ms
     99%  182.95ms
  11732 requests in 5.00m, 1.05MB read
Requests/sec:     39.10
Transfer/sec:      3.59KB

### GET без повторов

larisa@larisa:~/2017-highload-kv$ wrk --latency -t2 -c4 -d5m -s /home/larisa/loadtest/getWithoutRepeats.lua  http://localhost:8080/
Running 5m test @ http://localhost:8080/
  2 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    47.53ms   11.18ms 430.25ms   94.05%
    Req/Sec    42.25      7.80    60.00     87.37%
  Latency Distribution
     50%   44.09ms
     75%   47.72ms
     90%   51.71ms
     99%   96.56ms
  25312 requests in 5.00m, 97.46MB read
  Non-2xx or 3xx responses: 259
Requests/sec:     84.36
Transfer/sec:    332.61KB

### GET с повторами

larisa@larisa:~/2017-highload-kv$ wrk --latency -t2 -c4 -d5m -s /home/larisa/loadtest/getWithRepeats.lua  http://localhost:8080/
Running 5m test @ http://localhost:8080/
  2 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    45.16ms    7.79ms 359.82ms   96.60%
    Req/Sec    44.38      6.79    60.00     91.12%
  Latency Distribution
     50%   43.89ms
     75%   44.28ms
     90%   47.11ms
     99%   68.15ms
  26571 requests in 5.00m, 103.34MB read
Requests/sec:     88.54
Transfer/sec:    352.61KB


4 потока 4 соединения
### PUT без перезаписи

larisa@larisa:~/2017-highload-kv$ wrk --latency -t4 -c4 -d5m -s /home/larisa/loadtest/putWithoutOverwrite.lua  http://localhost:8080/
Running 5m test @ http://localhost:8080/
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    23.69ms   17.43ms 109.97ms   50.82%
    Req/Sec     8.36      5.45    30.00     75.44%
  Latency Distribution
     50%   19.82ms
     75%   42.07ms
     90%   45.62ms
     99%   62.35ms
  8519 requests in 5.00m, 782.02KB read
Requests/sec:     28.39
Transfer/sec:      2.61KB

### PUT с перезаписью

larisa@larisa:~/2017-highload-kv$ wrk --latency -t4 -c4 -d5m -s /home/larisa/loadtest/putWithOverwrite.lua  http://localhost:8080/
Running 5m test @ http://localhost:8080/
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    22.40ms   17.82ms 242.35ms   47.92%
    Req/Sec    10.71      5.52    30.00     60.57%
  Latency Distribution
     50%   15.30ms
     75%   42.46ms
     90%   44.94ms
     99%   51.66ms
  11752 requests in 5.00m, 1.05MB read
Requests/sec:     39.16
Transfer/sec:      3.59KB

### GET без повторов

larisa@larisa:~/2017-highload-kv$ wrk --latency -t4 -c4 -d5m -s /home/larisa/loadtest/getWithoutRepeats.lua  http://localhost:8080/
Running 5m test @ http://localhost:8080/
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    44.68ms    3.66ms 166.04ms   94.59%
    Req/Sec    22.24      4.54    30.00     73.70%
  Latency Distribution
     50%   43.88ms
     75%   44.24ms
     90%   46.29ms
     99%   59.69ms
  26761 requests in 5.00m, 104.08MB read
Requests/sec:     89.19
Transfer/sec:    355.18KB

### GET с повторами

larisa@larisa:~/2017-highload-kv$ wrk --latency -t4 -c4 -d5m -s /home/larisa/loadtest/getWithRepeats.lua  http://localhost:8080/
Running 5m test @ http://localhost:8080/
  4 threads and 4 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    45.06ms    6.56ms 312.94ms   95.47%
    Req/Sec    22.13      4.63    30.00     73.55%
  Latency Distribution
     50%   43.88ms
     75%   44.22ms
     90%   46.81ms
     99%   67.97ms
  26600 requests in 5.00m, 103.45MB read
Requests/sec:     88.65
Transfer/sec:    353.05KB

### Вывод

По результатам тестирования можно увидеть, что запросы с перезаписью и повторами выполняются быстрей (количество запросов в секунду), и имеют меньшую задержку,
кроме случая тестирования метода GET в 4 потока.
