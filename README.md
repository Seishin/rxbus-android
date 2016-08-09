RxBus
======
Easy to use events bus based on RxJava.

Usage
------
1. Define event classes:

  ```java
class SampleEvent { /* Additional fields if needed */ }
```

2. Register an object that you want to receive events:

  ```java
RxBus.register(this);
```

3. Declare a method and annotate it with Event annotation:

  ```java
@Event(SampleEvent.class)
public void onSampleEventReceived(SampleEvent event) { /* Do your work */}
```

4. Send an event:

  ```java
RxBus.getInstance().send(new SampleEvent());
```

5. Unregister the subscribed object by:
 
  ```java
RxBus.getInstance().unregister(this);
```

Contribution
-----
Everyone is welcomed to contribute on this project! :)
