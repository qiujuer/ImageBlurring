[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ImageBlurring-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1464)

[`中文`](README-ZH.md) [`English`](README.md) 

ImageBlurring
=============

Android Blurring Image (Bitmap) By Java And JNI.


## Thanks
[blurring](https://github.com/paveldudka/blurring)


## About Article

> *  [csdn](http://blog.csdn.net/qiujuer/article/details/24282047)
> *  [oschina](http://www.oschina.net/question/1377710_151988)


## Note

Has been integrated into the new project: [Genius-Android](https://github.com/qiujuer/Genius-Android.git)


## Screenshots

##### RenderScript
![RenderScript](images/001.jpg)

##### Fast Blur
![Fast Blur](images/002.jpg)

##### JniArray
![JniArray](images/003.jpg)

##### JniBitMap
![JniBitMap](images/004.jpg)

##### Animation
![Animation](images/005.gif)


## Use

* `RenderScript`
  > *  Call Android's `RenderScript` to blur image
  > *  Speed is generally, with direct blur little difference in the Java layer
  > *  This need Android >= `4.4` 

* `Fast Blur`
  > *  Call Java class to blur iamge
  > *  `Fast Blur` is blurring project's methods
  > *  `Fast Blur` is stack blur

* `JniArray`
  > *  Fuzzy method is blur C language implementation of the `stack blur` 
  > *  After the blur JNI layer, and then back to the blur data
  > *  In the Java layer of class parsed pixel array passed to JNI layer
  > *  After the blur back to the Array

* `JniBitMap`
  > *  Fuzzy method is fuzzy C language implementation of the `stack blur` 
  > *  After the blur JNI layer, and then back to the blur data
  > *  After the blur back to the Bitmap


## Need

Download project, if you need import should `Android Studio` >= 1.0.0

Project inside contain an instance of project, project with JNI source implementation, as well as the JNI generated file.

'Eclipse' cannot import directly in the program, please create a project in accordance with the corresponding category replacement to their projects.


## Feedback

You in use if you have any question, please timely feedback to me, you can use the following contact information to communicate with me

* Project: [`Submit Bug or Idea`](https://github.com/qiujuer/ImageBlurring/issues)
* Email: [`qiujuer@live.cn`](mailto:qiujuer@live.cn)
* QQ: `756069544`
* QQ Group: [`387403637`](http://shang.qq.com/wpa/qunwpa?idkey=3f1ed8e41ed84b07775ca593032c5d956fbd8c3320ce94817bace00549d58a8f)
* WeiBo: [`@qiujuer`](http://weibo.com/qiujuer)
* WebSit:[`www.qiujuer.net`](http://www.qiujuer.net)


## Giving developers

Are interested in and write a `free`, have joy, also there is sweat, I hope you like my work, but also can support it.
Of course, rich holds a money (AliPay: `qiujuer@live.cn`); No money holds personal field, thank you.


## About me

```javascript
  var info = {
    nickName  : "qiujuer",
    site : "http://www.qiujuer.net"
  }
```


License
--------

    Copyright 2014 CengaLabs.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

