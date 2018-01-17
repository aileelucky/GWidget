
[![](https://jitpack.io/v/aileelucky/GWidget.svg)](https://jitpack.io/#aileelucky/GWidget)
# VerticalScrollTextView

##描述
自定义一个循环滚动的textview,类似淘宝热点
```java
compile 'com.china.gu:vertical-scroll-textview:1.1.0'
```
##使用方式 ##
* 设置数据
```java
public void setList(ArrayList<Hot> list) {}
```
* 停止滚动
```java
public void stop() {}
```

##扩展（xml里面设置）
```xml
<vctextview.VerticalScrollTextView
            android:id="@+id/vst"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#ebebeb"
            verticalTv:vTextColor="@color/colorAccent"//自定义字体颜色
            verticalTv:vTextHeight="50dp" //自定义控件高度
            verticalTv:vTextHint="暂无热点" //自定义默认文案
            verticalTv:vTextSize="15sp" //自定义字体大小
            />
```

## 更新点 
>v1.1.0
- 添加点击事件;<br/>
- 去掉Hot的使用，可以适合任何项目;<br />
