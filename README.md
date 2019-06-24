# APP
mobile app
## 서버랑 통신하는법

### 1.	JSON 객체 생성
	
```java
JSON json = new JSON() ;
```


### 2.	JSONObject 생성 (try catch 필요)

```java
JSONObject jsonobject = new JSONObject() ;

jsonObject.accumulate(“ID”, “test1”);

jsonObject.accumulate(“name”, “test1”);

jsonObject.accumulate(“password”, “test1”);
```
            

### 3.	PostURL로 연결, JsonElement (gson)으로 return된 값 파싱 후 사용

```java
JsonElement result=json.PostURL(http://54.180.81.90:3000/UserInform,jsonObject);

String tier = result.getAsJsonObject().get(“tier”).toString();
```
