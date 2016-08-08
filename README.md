# RxBus

Easy to use events bus based on RxJava.

# Usage

1. Define event classes: 
```Java
class SampleEvent { /* Additional fields if needed */ }
```
2. Register an object that you want to receive events:
```Java
RxBus.getInstance().register(this);
```

3. Declare a method and annotate it with Event annotation:
```Java
@Event(SampleEvent.class)
public void onSampleEventReceived(SampleEvent event) { /* Do your work */}
```

4. Send an event:
```Java
RxBus.getInstance().send(new SampleEvent());
```

5. Unregister the subscribed object by:
```Java
RxBus.getInstance().unregister(this);
```
