# EdSlider
## Android library for icon slider (Facebook-like emoji button)
![](https://github.com/Emadoki/edslider/raw/master/edslider.gif)

## How to use

Refer to sample project for more info

#### Import project
Add this to your root gradle
```java
allprojects {
    repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

Add this to your app gradle
```java
compile 'com.github.Emadoki:edslider:1.0.0'
```

#### Create EdSliderManager
```java
private EdSliderManager manager;

protected void onCreate(Bundle savedInstanceState)
{
    ...
    // initialize manager
    manager = new EdSliderManager(new OnSliderSelectedListener());
}

@Override
public boolean dispatchTouchEvent(MotionEvent event)
{
    if (manager.dispatched(event))
        return false;

    return super.dispatchTouchEvent(event);
}
```

#### Build EdSlider
```java
((Button) findViewById(R.id.button)).setOnLongClickListener(new OnLongClickListener(
@Override
        public boolean onLongClick(View button)
        {
            new EdSliderBuilder(MainActivity.this)
                    .set(manager)
                    .on(button)
                    .setAlignment(Align.LEFT, Align.TOP)
                    .addIcon(R.drawable.ic_android)
                    .addIcon(R.drawable.ic_heart)
                    .addIcon(R.drawable.ic_camera)
                    .build()
                    .show();
            return false;
        }));
```

#### Align
```java
// make sure you import the correct package
import com.emadoki.edslider.Align;

public enum Align
{
    // horizontal
    LEFT, RIGHT, CENTER,
    // vertical
    TOP, BOTTOM
}
```

## License
```java
Copyright 2017 Emadoki

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```