# ExpressView
A micro-interaction library which enables animation for various actions or reactions. Similar to Twitter like button or other social media apps.
![Platform][5] ![API][6]

![ExpressView sample gif][4]
### Gradle Dependency
```groovy
dependencies {
    implementation "co.ankurg.expressview:expressview:0.0.3"
}
```

### XML
```xml
<co.ankurg.expressview.ExpressView
        android:id="@+id/likeButton"
        android:layout_width="64dp"
        android:layout_height="64dp" />
```

### Observing check state
View reacts to clicks and maintains the state on its own. To listen to the check state change, implement the following callback.
```kotlin
import co.ankurg.expressview.OnCheckListener
...
likeButton.setOnCheckListener(object : OnCheckListener {
    override fun onChecked(view: ExpressView?) {
        Log.d(TAG, "Checked")
    }
    override fun onUnChecked(view: ExpressView?) {
        Log.d(TAG, "Unchecked")
    }
})
```

### Attributes
|Name|Description|Value|Default|
|---|---|---|---|
|uncheckedIcon|Uncheked icon sate|Drawable|Heart Icon|
|checkedIcon|Checked icon state|Drawable|Heart Icon|
|uncheckedIconTint|Unchecked icon color|Color|#A1A1A1A1|
|checkedIconTint|Checked icon color|Color|#FFFF0000|
|iconSize|Size of icon, applied to both widht & height|Dimension|55% of ExpressView|
|burstColor|Color of line burst behind the icon|Color|#FFFF0000|
|setChecked|Initial check state of icon|Boolean|false|
Note:- Deafult ExpressView size is 48dp and iconSize is 26dp (55% of 48dp) 
### Public Kotlin/Java properties
|Name|Description|Value|Default|
|---|---|---|---|
|isChecked|Initial check state of icon|Boolean|false|
|animationStartDelay|Delay between the tap and animation, applied to both icon and burst animations|Long|50L|
|iconAnimationDuration|Animation duration of icon inside ExpressView|Long|700L|
|burstAnimationDuration|Animation duration of line burst behind the icon|Long|500L|
|interpolator|Animation interpolator for the icon inside ExpressView|[Interpolator][1]|[BounceInterpolator][2]|
|onCheckListener|Check state change listener/callback|[OnCheckListener][3]|null|

[1]: https://developer.android.com/reference/android/view/animation/Interpolator
[2]: https://developer.android.com/reference/android/view/animation/BounceInterpolator
[3]: expressview/src/main/java/co/ankurg/expressview/OnCheckListener.kt
[4]: gif/like-button-animation-express-view.gif
[5]: https://img.shields.io/badge/Platform-android-blue.svg
[6]: https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat