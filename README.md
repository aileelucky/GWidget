
[![](https://jitpack.io/v/aileelucky/GWidget.svg)](https://jitpack.io/#aileelucky/GWidget)
# VerticalScrollTextView

***描述*** 自定义一个循环滚动的textview,类似淘宝热点

<pre><code>compile 'com.china.gu:vertical-scroll-textview:1.1.0'</code></pre>
#### 使用方式 ####
* 设置数据<pre><code>public void setList(ArrayList<Hot> list) {}</code></pre>
* 停止滚动<pre><code>public void stop() {}</code></pre>

##扩展（xml里面设置）
1.自定义字体颜色
<pre><code>verticalTv:vTextColor="@color/colorAccent"</code></pre>
1.自定义控件高度
<pre><code>verticalTv:vTextHeight="50dp"</code></pre>
1.自定义默认文案
<pre><code>verticalTv:vTextHint="暂无热点"</code></pre>
1.自定义字体大小
<pre><code>verticalTv:vTextSize="15sp"</code></pre>

## V1.1.0更新点
1.添加点击事件；
2.去掉Hot的使用，可以适合任何项目
